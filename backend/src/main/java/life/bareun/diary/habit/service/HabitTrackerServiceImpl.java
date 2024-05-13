package life.bareun.diary.habit.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.global.config.ImageConfig;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerModifyDto;
import life.bareun.diary.habit.dto.HabitTrackerScheduleDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyReqDto;
import life.bareun.diary.habit.dto.response.HabitTrackerDetailResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerTodayResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerWeekResDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.exception.HabitErrorCode;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitTrackerServiceImpl implements HabitTrackerService {

    private final ImageConfig imageConfig;

    private final HabitTrackerRepository habitTrackerRepository;

    private final MemberHabitRepository memberHabitRepository;

    private final StreakService streakService;

    @Value("${cloud.aws.default}")
    private String IMAGE_BASIC;

    @Override
    // 요일 기준으로 해빗 트래커 생성
    public void createHabitTrackerByDay(HabitTrackerCreateDto habitTrackerCreateDto) {
        habitTrackerRepository.save(
            HabitTracker.builder().member(habitTrackerCreateDto.member())
                .memberHabit(habitTrackerCreateDto.memberHabit())
                .createdYear(habitTrackerCreateDto.targetDay().getYear())
                .createdMonth(habitTrackerCreateDto.targetDay().getMonthValue())
                .createdDay(habitTrackerCreateDto.targetDay().getDayOfMonth())
                .day(habitTrackerCreateDto.amount()).build());
    }

    @Override
    // 주기 기준으로 해빗 트래커 생성
    public void createHabitTrackerByPeriod(HabitTrackerCreateDto habitTrackerCreateDto) {
        habitTrackerRepository.save(
            HabitTracker.builder().member(habitTrackerCreateDto.member())
                .memberHabit(habitTrackerCreateDto.memberHabit())
                .createdYear(habitTrackerCreateDto.targetDay().getYear())
                .createdMonth(habitTrackerCreateDto.targetDay().getMonthValue())
                .createdDay(habitTrackerCreateDto.targetDay().getDayOfMonth())
                .day(habitTrackerCreateDto.targetDay().getDayOfWeek().getValue()).build());
    }

    @Override
    // 모든 해빗 트래커들을 삭제
    public void deleteAllHabitTracker(Long memberHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(memberHabitId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER_HABIT));
        // 사진도 삭제해야하기 때문에 목록을 불러와서 하나씩 삭제
        List<HabitTracker> habitTrackerList = habitTrackerRepository
            .findAllByMemberHabit(memberHabit);
        for (HabitTracker habitTracker : habitTrackerList) {
            if (habitTracker.getImage() != null) {
                imageConfig.deleteImage(habitTracker.getImage());
            }
            habitTrackerRepository.delete(habitTracker);
        }
    }

    @Override
    // 이후 해빗 트래커들을 삭제
    public void deleteAfterHabitTracker(Long memberHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(memberHabitId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER_HABIT));
        LocalDate today = LocalDate.now();
        habitTrackerRepository.deleteAfterHabitTracker(
            HabitTrackerDeleteDto.builder().memberHabit(memberHabit).year(today.getYear())
                .month(today.getMonth().getValue()).day(today.getDayOfMonth()).build());
    }

    @Override
    // 해빗 트래커 완료
    public void modifyHabitTracker(MultipartFile image,
        HabitTrackerModifyReqDto habitTrackerModifyReqDto) {
        HabitTracker habitTracker = habitTrackerRepository.findById(
                habitTrackerModifyReqDto.habitTrackerId())
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT_TRACKER));

        if (habitTracker.getSucceededTime() != null || habitTrackerModifyReqDto.content() == null) {
            throw new HabitException(HabitErrorCode.INVALID_PARAMETER_HABIT_TRACKER);
        }

        String imageUrl = IMAGE_BASIC;
        if (image != null && !image.isEmpty()) {
            imageUrl = imageConfig.uploadImage(image);
        }
        habitTrackerRepository.modifyHabitTracker(HabitTrackerModifyDto.builder()
            .habitTrackerId(habitTrackerModifyReqDto.habitTrackerId()).image(imageUrl)
            .content(habitTrackerModifyReqDto.content()).build());

        streakService.achieveStreak(habitTracker.getMemberHabit(), LocalDate.now());
    }

    @Override
    // 오늘 사용자의 해빗 트래커 리스트를 반환
    public HabitTrackerTodayResDto findAllTodayHabitTracker() {
        LocalDate localDate = LocalDate.now();
        return HabitTrackerTodayResDto.builder()
            .habitTrackerTodayDtoList(habitTrackerRepository.findAllTodayHabitTracker(
                HabitTrackerTodayFactorDto.builder()
                    .memberId(AuthUtil.getMemberIdFromAuthentication())
                    .createdYear(localDate.getYear())
                    .createdMonth(localDate.getMonthValue())
                    .createdDay(localDate.getDayOfMonth()).build())).build();
    }

    @Override
    // 특정 habitTracker 상세 조회
    public HabitTrackerDetailResDto findDetailHabitTracker(Long habitTrackerId) {
        HabitTracker habitTracker = habitTrackerRepository.findById(habitTrackerId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT_TRACKER));
        String createdAt = loadCreatedDate(habitTracker.getCreatedYear(),
            habitTracker.getCreatedMonth(), habitTracker.getCreatedDay());
        return HabitTrackerDetailResDto.builder().habitTrackerId(habitTrackerId)
            .alias(habitTracker.getMemberHabit().getAlias()).content(habitTracker.getContent())
            .image(habitTracker.getImage()).day(habitTracker.getDay())
            .succeededTime(habitTracker.getSucceededTime())
            .createdAt(LocalDate.parse(createdAt)).build();
    }

    @Override
    // 요일 별 해빗 트래커 개수 리스트 조회
    public HabitTrackerWeekResDto findAllWeekHabitTracker() {
        int[] dayList = new int[8];
        for (int i = 1; i < 8; i++) {
            dayList[i] = habitTrackerRepository.countByDay(i);
        }
        return HabitTrackerWeekResDto.builder().monday(dayList[1]).tuesday(dayList[2])
            .wednesday(dayList[3]).thursday(dayList[4]).friday(dayList[5]).saturday(dayList[6])
            .sunday(dayList[7]).build();
    }

    @Override
    // 한 번이라도 스트릭 기록한 적이 있는지 확인
    public Boolean existsByMemberHabitAndSucceededTimeIsNotNull(MemberHabit memberHabit) {
        return habitTrackerRepository.existsByMemberHabitAndSucceededTimeIsNotNull(memberHabit);
    }

    @Override
    // 가장 마지막에 작성한 해빗 트래커 찾기
    public HabitTracker findLastHabitTracker(HabitTrackerLastDto habitTrackerLastDto) {
        return habitTrackerRepository.findLastHabitTracker(habitTrackerLastDto);
    }

    @Override
    public Long getHabitTrackerCount(MemberHabit memberHabit, LocalDate date) {
        return habitTrackerRepository.getHabitTrackerCountByMemberHabitAndDate(
            HabitTrackerScheduleDto.builder()
                .memberHabit(memberHabit)
                .date(date)
                .build()
        );
    }

    // 날짜 문자열 만들기
    private String loadCreatedDate(int year, int month, int day) {
        String createdDate = year + "-";
        createdDate += month >= 10 ? month : "0" + month;
        createdDate += "-";
        createdDate += day >= 10 ? day : "0" + day;
        return createdDate;
    }
}

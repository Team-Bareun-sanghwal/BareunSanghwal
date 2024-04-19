package life.bareun.diary.habit.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.config.ImageConfig;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyReqDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.exception.HabitErrorCode;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .day(habitTrackerCreateDto.amount()).build());
    }

    @Override
    // 모든 해빗 트래커들을 삭제
    public void deleteAllHabitTracker(Long memberHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(memberHabitId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
        // 사진도 삭제해야하기 때문에 목록을 불러와서 하나씩 삭제
        List<HabitTracker> habitTrackerList = habitTrackerRepository.findAllByMemberHabit(memberHabit);
        for (HabitTracker habitTracker : habitTrackerList) {
            imageConfig.deleteImage(habitTracker.getImage());
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
        String imageUrl = null;
        if (image != null) {
            imageUrl = imageConfig.uploadImage(image);
        }

        habitTrackerRepository.modifyHabitTracker(HabitTrackerModifyDto.builder()
            .habitTrackerId(habitTrackerModifyReqDto.habitTrackerId()).image(imageUrl)
            .content(habitTrackerModifyReqDto.content()).build());
    }
}

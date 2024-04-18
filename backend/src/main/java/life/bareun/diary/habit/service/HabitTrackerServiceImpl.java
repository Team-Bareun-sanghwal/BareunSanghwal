package life.bareun.diary.habit.service;

import java.time.LocalDate;
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
    public void deleteAllHabitTracker(Long memberHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(memberHabitId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
        habitTrackerRepository.deleteAllByMemberHabit(memberHabit);
    }

    @Override
    public void deleteAfterHabitTracker(Long memberHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(memberHabitId)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER_HABIT));
        LocalDate today = LocalDate.now();
        habitTrackerRepository.deleteAfterHabitTracker(
            HabitTrackerDeleteDto.builder().memberHabit(memberHabit).year(today.getYear())
                .month(today.getMonth().getValue()).day(today.getDayOfMonth()).build());
    }

    @Override
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

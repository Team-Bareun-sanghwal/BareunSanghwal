package life.bareun.diary.streak.dto;

import lombok.Builder;

@Builder
/*
 * TODO: 전영빈 // 원래 필드가 int, int, int 였는데 레포지토리에서 findStreakInfo로 받은 반환 값이
 *  Integer, Long, Long 이었다. 이유는 아직 모르고 알아봐야 한다.
 */
public record StreakInfoByDayDto(Integer dayNumber, Long achieveCount, Long totalCount) {

}

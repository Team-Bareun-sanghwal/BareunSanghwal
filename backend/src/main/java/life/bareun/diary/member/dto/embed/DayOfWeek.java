package life.bareun.diary.member.dto.embed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DayOfWeek {

    MON("월", 1),
    TUE("화", 2),
    WED("수", 3),
    THU("목", 4),
    FRI("금", 5),
    SAT("토", 6),
    SUN("일", 7);

    private final String value;
    private final int index;

    public static String getValueByIndex(int index) {
        rangeCheck(index);
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.index == index) {
                return day.getValue();
            }
        }
        // 모든 enum을 확인했음에도 불구하고 해당 index가 없는 경우, 오류를 처리합니다.
        throw new IllegalArgumentException("Invalid index: " + index);
    }

    private static void rangeCheck(int index) {
        if ((index < MON.index) || (SUN.index < index)) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }
    }
}
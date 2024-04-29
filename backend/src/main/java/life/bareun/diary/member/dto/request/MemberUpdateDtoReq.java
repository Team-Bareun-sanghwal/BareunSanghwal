package life.bareun.diary.member.dto.request;

import java.time.LocalDate;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MemberUpdateDtoReq {
    private String nickname;
    private LocalDate birthDate;
    private Gender gender;
    private Job job;
}

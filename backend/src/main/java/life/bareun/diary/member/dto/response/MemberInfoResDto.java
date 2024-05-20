package life.bareun.diary.member.dto.response;

import java.time.LocalDate;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberInfoResDto {

    private String nickname;
    private Gender gender;
    private Job job;
    private LocalDate birthDate;
}

package life.bareun.diary.member.dto.request;

import java.time.LocalDate;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;

public record MemberUpdateReq(
    String nickname,
    LocalDate birthDate,
    Gender gender,
    Job job
) {

}

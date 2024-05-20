package life.bareun.diary.recap.dto;

import life.bareun.diary.member.entity.Member;
import lombok.Builder;

@Builder
public record RecapMemberMonthDto(

    Member member,

    int year,

    int month

) { }

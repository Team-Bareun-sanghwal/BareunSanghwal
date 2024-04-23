package life.bareun.diary.recap.dto;

import life.bareun.diary.member.entity.Member;
import lombok.Builder;

@Builder
public record RecapMemberDto(

    Member member

) { }

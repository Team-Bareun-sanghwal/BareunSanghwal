package life.bareun.diary.member.dto;

import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.member.entity.Tree;
import lombok.Builder;

@Builder
public record MemberRegisterDto(
    String sub,
    OAuth2Provider oAuth2Provider,
    Tree defaultTree,
    Integer defaultStreakColorId,
    Integer defaultTreeColorId
) {

}

package life.bareun.diary.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class MemberLoginRes {

    private String accessToken;
    // private String refreshToken;
}

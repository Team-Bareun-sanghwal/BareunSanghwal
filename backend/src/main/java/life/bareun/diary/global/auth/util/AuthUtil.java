package life.bareun.diary.global.auth.util;

import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.AuthErrorCode;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthUtil {

    public static OAuth2MemberPrincipal getAuthentication() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new AuthException(AuthErrorCode.UNAUTHENTICATED);
        }

        System.out.println("Principal: " + authentication.getPrincipal());
        log.debug("Hel authentication: {}", authentication.getPrincipal());
        return (OAuth2MemberPrincipal) authentication.getPrincipal();
    }

    public static Long getMemberIdFromAuthentication() throws AuthException {
        try {
            return Long.parseLong(getAuthentication().getName());
        } catch (NumberFormatException | NullPointerException e) {
            throw new AuthException(AuthErrorCode.UNAUTHENTICATED);
        }
    }
}

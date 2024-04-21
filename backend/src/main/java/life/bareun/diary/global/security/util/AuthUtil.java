package life.bareun.diary.global.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;

public class AuthUtil {
	public static OAuth2MemberPrincipal getAuthentication() throws CustomSecurityException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.isAuthenticated()) {
			throw new CustomSecurityException(SecurityErrorCode.UNAUTHENTICATED);
		}

		return (OAuth2MemberPrincipal) authentication.getPrincipal();
	}

	public static Long getMemberIdFromAuthentication() throws CustomSecurityException {
		try {
			return Long.parseLong(getAuthentication().getName());
		} catch (NumberFormatException | NullPointerException e) {
			throw new CustomSecurityException(SecurityErrorCode.UNAUTHENTICATED);
		}
	}
}

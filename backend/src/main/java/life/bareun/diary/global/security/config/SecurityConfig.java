package life.bareun.diary.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import life.bareun.diary.global.security.filter.AuthTokenFilter;
import life.bareun.diary.global.security.handler.CustomOAuth2FailureHandler;
import life.bareun.diary.global.security.handler.CustomOAuth2SuccessHandler;
import life.bareun.diary.global.security.handler.JwtAccessDeniedHandler;
import life.bareun.diary.global.security.handler.JwtAuthenticationEntryPoint;
import life.bareun.diary.global.security.service.CustomOAuth2MemberService;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthTokenProvider authTokenProvider;
	private final MemberService memberService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Stateless -> CSRF 방어 불필요
		http.sessionManagement(
			(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		http.csrf(AbstractHttpConfigurer::disable);

		// Filter 순서 설정
		// http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		// 요청 필터링
		// 개발 초기이므로 모든 요청에 대해 인증 요구를 해제한다.
		http.authorizeHttpRequests(
			authorizationManagerRequestMatcherRegistry ->
				authorizationManagerRequestMatcherRegistry
					.anyRequest()
					.permitAll()
		);

		// OAuth 설정
		http
			.oauth2Login( // OAuth2.0 로그인 활성화
				httpSecurityOAuth2LoginConfigurer ->
					httpSecurityOAuth2LoginConfigurer
						.userInfoEndpoint(
							userInfoEndpointConfig ->
								userInfoEndpointConfig.userService(customOAuth2MemberService())
						)
						.successHandler(oAuth2SuccessHandler())
						.failureHandler(oAuth2FailureHandler())
			);

		// 예외 처리 설정
		http
			.exceptionHandling(
				httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
					.accessDeniedHandler(jwtAccessDeniedHandler())
					.authenticationEntryPoint(jwtAuthenticationEntryPoint())
			);

		return http.build();
	}

	// @Bean
	// public AuthTokenFilter authTokenFilter() {
	// 	return new AuthTokenFilter(authTokenProvider);
	// }

	@Bean
	public CustomOAuth2MemberService customOAuth2MemberService() {
		return new CustomOAuth2MemberService(memberService);
	}

	@Bean
	public CustomOAuth2SuccessHandler oAuth2SuccessHandler() {
		return new CustomOAuth2SuccessHandler(authTokenProvider);
	}

	@Bean
	public CustomOAuth2FailureHandler oAuth2FailureHandler() {
		return new CustomOAuth2FailureHandler();
	}

	@Bean
	public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
		return new JwtAccessDeniedHandler();
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

}

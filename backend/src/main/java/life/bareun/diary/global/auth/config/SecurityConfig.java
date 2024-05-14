package life.bareun.diary.global.auth.config;

import java.util.Arrays;
import java.util.List;
import life.bareun.diary.global.auth.filter.AuthTokenFilter;
import life.bareun.diary.global.auth.handler.CustomOAuth2FailureHandler;
import life.bareun.diary.global.auth.handler.CustomOAuth2SuccessHandler;
import life.bareun.diary.global.auth.handler.JwtAccessDeniedHandler;
import life.bareun.diary.global.auth.handler.JwtAuthenticationEntryPoint;
import life.bareun.diary.global.auth.service.CustomOAuth2MemberService;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {

    public final static String ACCESS_TOKEN_HEADER = "Authorization";
    public final static String REFRESH_TOKEN_HEADER = "RefreshToken";

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
        http.addFilterBefore(authTokenFilter(), OAuth2LoginAuthenticationFilter.class);

        // 요청 필터링
        // 개발 초기이므로 모든 요청에 대해 인증 요구를 해제한다.
        http.authorizeHttpRequests(
            authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .anyRequest()
                    .permitAll()
        );

        // CORS 설정
        http.cors(
            httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer
                    .configurationSource(corsConfigurationSource())
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


    // 개발 완료 시점에 허용해야 함
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setExposedHeaders(
            Arrays.asList(
                "Content-Type",
                "Set-Cookie",
                ACCESS_TOKEN_HEADER,
                REFRESH_TOKEN_HEADER
            )
        );
        // corsConfiguration.addAllowedOrigin("http://localhost:3000");
        // corsConfiguration.addAllowedOrigin("https://localhost:3000");
        corsConfiguration.addAllowedOriginPattern("https://bareun.life");
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L); // 1h
        corsConfiguration.setAllowedHeaders(
            Arrays.asList(
                "Origin",
                "X-Requested-With",
                "Content-Type",
                "Accept",
                "Authorization"
            )
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(authTokenProvider);
    }

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

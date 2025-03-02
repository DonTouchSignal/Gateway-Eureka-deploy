package com.example.apigateway.config;

import com.example.apigateway.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
// spring webflux  방식의 시큐리티 설정 필요
// webflux 리액티트 웹 프레임웍 -> 보안설정 어노테이션
// @EnableWebFluxSecurity <-> @EnableWebSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 구체적인 Origin 설정
        config.addAllowedOrigin("http://54.69.96.52");

        // 모든 메서드 허용
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // 필요한 모든 헤더 명시적 허용
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("X-Auth-User");
        config.addAllowedHeader("Accept");

        // 노출할 헤더 설정
        config.addExposedHeader("Authorization");
        config.addExposedHeader("X-Auth-User");
        config.addExposedHeader("AccessToken");
        config.addExposedHeader("RefreshToken");

        // credentials 허용
        config.setAllowCredentials(true);

        // preflight 요청의 캐시 시간 설정
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(source -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("http://54.69.96.52");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");

                    // WebSocket 관련 헤더 추가
                    config.addAllowedHeader("Upgrade");
                    config.addAllowedHeader("Connection");
                    config.addAllowedHeader("Sec-WebSocket-Key");
                    config.addAllowedHeader("Sec-WebSocket-Version");
                    config.addAllowedHeader("Sec-WebSocket-Extensions");
                    config.addAllowedHeader("Sec-WebSocket-Protocol");

                    config.addExposedHeader("Upgrade");
                    config.addExposedHeader("Connection");
                    config.addExposedHeader("Sec-WebSocket-Accept");
                    // 노출할 헤더 명시적 설정
                    config.addExposedHeader("Authorization");
                    config.addExposedHeader("X-Auth-User");
                    config.addExposedHeader("AccessToken");
                    config.addExposedHeader("RefreshToken");
                    config.addExposedHeader("Upgrade");
                    config.addExposedHeader("Connection");
                    config.addExposedHeader("Sec-WebSocket-Accept");
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers(
                            "/",
                            "/auth/login",
                            "/auth/reissue",
                            "/user/signup",
                            "/user/valid",
                            "/api/news/**",
                            "/chat/all/**",
                                "/alert/test/trigger",
                            "/ws/**",
                            "/ws/info/**",
                            "/ws/*/info/**",
                            "/ws/connect/**",
                            "/ws/websocket/**",
                            "/ws/*/websocket/**",
                            "/ws/*/transport/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package com.x1.frans.security.config;

import com.x1.frans.redis.service.RedisService;
import com.x1.frans.security.AuthenticationFilter;
import com.x1.frans.security.JwtAuthenticationProvider;
import com.x1.frans.security.config.properties.TokenProperties;
import com.x1.frans.security.handler.CustomAccessDeniedHandler;
import com.x1.frans.security.handler.CustomAuthenticationEntryPoint;
import com.x1.frans.security.handler.CustomAuthenticationFailureHandler;
import com.x1.frans.security.util.JwtUtil;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final JwtUtil jwtUtil;
    private final TokenProperties tokenProperties;
    private final RedisService redisService;

    @Autowired
    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider,
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler,
                          CustomAuthenticationFailureHandler authenticationFailureHandler,
                          JwtUtil jwtUtil,
                          TokenProperties tokenProperties,
                          RedisService redisService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.jwtUtil = jwtUtil;
        this.tokenProperties = tokenProperties;
        this.redisService = redisService;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize ->

                        // TODO: 개발용 설정. 배포 시 변경 필요
                        authorize.requestMatchers("/**").permitAll())
            .authenticationManager(authenticationManager())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager()));

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, jwtUtil,
                tokenProperties, redisService);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return filter;
    }
}

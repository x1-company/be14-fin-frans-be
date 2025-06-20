package com.x1.frans.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.redis.service.RedisService;
import com.x1.frans.security.AuthenticationFilter;
import com.x1.frans.security.JwtAuthenticationFilter;
import com.x1.frans.security.JwtAuthenticationProvider;
import com.x1.frans.security.config.properties.TokenProperties;
import com.x1.frans.security.handler.CustomAccessDeniedHandler;
import com.x1.frans.security.handler.CustomAuthenticationEntryPoint;
import com.x1.frans.security.handler.CustomAuthenticationFailureHandler;
import com.x1.frans.security.util.JwtUtil;
import com.x1.frans.user.query.service.UserQueryService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final JwtUtil jwtUtil;
    private final TokenProperties tokenProperties;
    private final RedisService redisService;
    private final UserQueryService userQueryService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider,
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler,
                          CustomAuthenticationFailureHandler authenticationFailureHandler,
                          JwtUtil jwtUtil,
                          TokenProperties tokenProperties,
                          RedisService redisService,
                          UserQueryService userQueryService,
                          ObjectMapper objectMapper) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.jwtUtil = jwtUtil;
        this.tokenProperties = tokenProperties;
        this.redisService = redisService;
        this.userQueryService = userQueryService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // TODO: 배포 시 변경 필요
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:5173")); // 개발 중 origin
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 쿠키, 인증정보 허용
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("""
        ROLE_ADMIN > ROLE_HQ
        ROLE_HQ > ROLE_FRANCHISE
        ROLE_HQ > ROLE_SUPPLIER
    """);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(authorize ->

                // TODO: 개발용 설정. 배포 시 변경 필요
                authorize
                        // ADMIN 전용 API 보호
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")


                        // Swagger 관련 리소스 전부 허용
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api-docs/**"
                        ).permitAll()

                        // auth 관련 기능
                        .requestMatchers("/api/auth/**").permitAll()

                        // 본사 전용
                        .requestMatchers("/api/hq/**").hasRole("HQ")

                        // 가맹점 전용
                        .requestMatchers("/api/franchise/**").hasRole("FRANCHISE")

                        // 공급사 전용 예시
                        .requestMatchers("/api/supplier/**").hasRole("SUPPLIER")

                        // AWS 관련 기능
                        .requestMatchers("/api/upload/**").permitAll()

                        // 알림 구독
                        .requestMatchers("/api/notification/**").authenticated()

        );

        http.authenticationManager(authenticationManager());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userQueryService, objectMapper, redisService),
                AuthenticationFilter.class);

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
package com.x1.frans.security.config;

import com.x1.frans.security.config.properties.TokenProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JwtTestConfig {
    @Bean
    public TokenProperties tokenProperties() {
        TokenProperties p = new TokenProperties();
        p.setSecret("testSecretKeyForJwtUtil1234567890123456"); // 테스트용 시크릿 키
        p.setAccessExpirationTime(10000L); // 테스트용 만료 시간
        p.setRefreshExpirationTime(20000L);
        return p;
    }
}

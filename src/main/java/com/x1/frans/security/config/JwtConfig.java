package com.x1.frans.security.config;

import com.x1.frans.security.config.properties.TokenProperties;
import com.x1.frans.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil(TokenProperties tokenProperties) {
        return new JwtUtil(tokenProperties);
    }
}

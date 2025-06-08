package com.x1.frans.security.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    private String secret;
    private Long accessExpirationTime;
    private Long refreshExpirationTime;
}

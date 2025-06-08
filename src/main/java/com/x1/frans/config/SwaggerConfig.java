package com.x1.frans.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi group1() {
        return GroupedOpenApi.builder()
                .group("유저")
                .pathsToMatch("/user/*")
                .build();
    }

    @Bean
    public GroupedOpenApi group2() {
        return GroupedOpenApi.builder()
                .group("주문")
                .pathsToMatch("/api/order/**")
                .build();
    }
}

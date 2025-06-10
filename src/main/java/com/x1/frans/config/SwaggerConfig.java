package com.x1.frans.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("유저")
                .pathsToMatch("/user/*")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("주문")
                .pathsToMatch("/api/hq/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi franchiseApi() {
        return  GroupedOpenApi.builder()
                .group("가맹점")
                .pathsToMatch("/api/franchise/**")
                .build();
    }
}

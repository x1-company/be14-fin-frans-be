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
                .pathsToMatch("/api/hq/user/**")
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
    public GroupedOpenApi hqFranchiseApi() {
        return GroupedOpenApi.builder()
                .group("가맹점(본사)")
                .pathsToMatch("/api/hq/franchise/**")
                .build();
    }

    @Bean
    public GroupedOpenApi franchiseApi() {
        return GroupedOpenApi.builder()
                .group("가맹점")
                .pathsToMatch("/api/franchise/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hqSupplierApi() {
        return GroupedOpenApi.builder()
                .group("공급처(본사)")
                .pathsToMatch("/api/hq/suppliers/**")
                .build();
    }


    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("인증, 인가")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("자재")
                .pathsToMatch("/api/hq/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi frProductApi() {
        return GroupedOpenApi.builder()
                .group("자재(가맹점)")
                .pathsToMatch("/api/franchise/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi warehouseApi() {
        return GroupedOpenApi.builder()
                .group("창고")
                .pathsToMatch("/api/hq/warehouses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi approvalApi() {
        return GroupedOpenApi.builder()
                .group("결재")
                .pathsToMatch("/api/hq/approvals/**")
                .build();
    }

    @Bean
    public GroupedOpenApi supplierApi() {
        return GroupedOpenApi.builder()
                .group("공급처")
                .pathsToMatch("/api/supplier/**")
                .build();
    }
}

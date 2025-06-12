package com.x1.frans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FransApplication {

    public static void main(String[] args) {
        SpringApplication.run(FransApplication.class, args);
    }

}

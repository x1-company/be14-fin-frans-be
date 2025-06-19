package com.x1.frans;

import com.x1.frans.security.config.JwtTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JwtTestConfig.class)
class FransApplicationTests {

    @Test
    void contextLoads() {
    }

}

package com.x1.frans;

import com.x1.frans.aws.service.AmazonS3Service;
import com.x1.frans.security.config.JwtTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JwtTestConfig.class)
class FransApplicationTests {

    @MockBean
    AmazonS3Service amazonS3Service;

    @Test
    void contextLoads() {
    }

}

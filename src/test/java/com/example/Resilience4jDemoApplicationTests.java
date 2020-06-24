package com.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Disabled
@SpringBootTest(webEnvironment = RANDOM_PORT)
class Resilience4jDemoApplicationTests {

    @Test
    void contextLoads() {
    }

}

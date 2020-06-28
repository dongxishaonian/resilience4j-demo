package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

public class TimeLimiterTests extends Resilience4jDemoApplicationTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testTimeLimiter() {
        given().get("/timeLimiter").then().log().body();
        await().atMost(1, TimeUnit.MINUTES).until(() -> 1 == 8);
    }


}

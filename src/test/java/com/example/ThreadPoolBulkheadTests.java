package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class ThreadPoolBulkheadTests extends Resilience4jDemoApplicationTests {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void 多并发访问情况下的ThreadPoolBulkhead测试() {
        CopyOnWriteArrayList<Integer> statusList = new CopyOnWriteArrayList<>();
        IntStream.range(0, 8).forEach(i -> CompletableFuture.runAsync(() -> {
                statusList.add(given().get("/json-object-by-threadpool").statusCode());
            }
        ));
        await().atMost(1, TimeUnit.MINUTES).until(() -> statusList.size() == 8);
        System.out.println(statusList);
        assertThat(statusList.stream().filter(i -> i == 200).count()).isEqualTo(6);
        assertThat(statusList.stream().filter(i -> i == 500).count()).isEqualTo(2);
    }

    @Test
    public void 多并发访问情况下的ThreadPoolBulkhead测试使用回退方法() {
        CopyOnWriteArrayList<Integer> statusList = new CopyOnWriteArrayList<>();
        IntStream.range(0, 8).forEach(i -> CompletableFuture.runAsync(() -> {
                statusList.add(given().get("/json-object-by-threadpool-with-fallback").statusCode());
            }
        ));
        await().atMost(1, TimeUnit.MINUTES).until(() -> statusList.size() == 8);
        System.out.println(statusList);
        assertThat(statusList.stream().filter(i -> i == 200).count()).isEqualTo(8);
    }
}

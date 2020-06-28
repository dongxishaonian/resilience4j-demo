package com.example.service;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TimeLimiterService {
    private final Logger logger = LoggerFactory.getLogger("TimeLimiterService");

    @TimeLimiter(name = "backendA")
    public CompletableFuture<String> doSomeThing() {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
                Thread.sleep(1000L);
                Thread.sleep(1000L);
                logger.info("33333");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "hello world";
        });
    }
}

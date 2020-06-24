package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BulkheadService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BulkheadRegistry bulkheadRegistry;
    @Autowired
    private ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry;

    @Bulkhead(name = "backendA")
    public JsonNode getJsonObject() throws InterruptedException {
        io.github.resilience4j.bulkhead.Bulkhead.Metrics metrics = bulkheadRegistry.bulkhead("backendA").getMetrics();
        logger.info("now i enter the method!!!,{}<<<<<<{}", metrics.getAvailableConcurrentCalls(), metrics.getMaxAllowedConcurrentCalls());
        Thread.sleep(1000L);
        logger.info("now i exist the method!!!");
        return new ObjectMapper().createObjectNode().put("file", System.currentTimeMillis());
    }

    @Bulkhead(name = "backendA", fallbackMethod = "fallback")
    public JsonNode getJsonObjectWithFallback() throws InterruptedException {
        io.github.resilience4j.bulkhead.Bulkhead.Metrics metrics = bulkheadRegistry.bulkhead("backendA").getMetrics();
        logger.info("now i enter the method!!!,{}<<<<<<{}", metrics.getAvailableConcurrentCalls(), metrics.getMaxAllowedConcurrentCalls());
        Thread.sleep(1000L);
        logger.info("now i exist the method!!!");
        return new ObjectMapper().createObjectNode().put("file", System.currentTimeMillis());
    }

    private JsonNode fallback(BulkheadFullException exception) {
        return new ObjectMapper().createObjectNode().put("errorFile", System.currentTimeMillis());
    }

    @Bulkhead(name = "backendA", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<JsonNode> getJsonObjectByThreadPool() throws InterruptedException {
        io.github.resilience4j.bulkhead.ThreadPoolBulkhead.Metrics metrics = threadPoolBulkheadRegistry.bulkhead("backendA").getMetrics();
        logger.info("now i enter the method!!!,{}", metrics);
        Thread.sleep(1000L);
        logger.info("now i exist the method!!!");
        return CompletableFuture.supplyAsync(() -> new ObjectMapper().createObjectNode().put("file", System.currentTimeMillis()));
    }

    @Bulkhead(name = "backendA", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "fallbackByThreadPool")
    public CompletableFuture<JsonNode> getJsonObjectByThreadPoolWithFallback() throws InterruptedException {
        io.github.resilience4j.bulkhead.ThreadPoolBulkhead.Metrics metrics = threadPoolBulkheadRegistry.bulkhead("backendA").getMetrics();
        logger.info("now i enter the method!!!,{}", metrics);
        Thread.sleep(1000L);
        logger.info("now i exist the method!!!");
        return CompletableFuture.supplyAsync(() -> new ObjectMapper().createObjectNode().put("file", System.currentTimeMillis()));
    }


    private CompletableFuture<JsonNode> fallbackByThreadPool(BulkheadFullException exception) {
        return CompletableFuture.supplyAsync(() -> new ObjectMapper().createObjectNode().put("errorFile", System.currentTimeMillis()));
    }
}

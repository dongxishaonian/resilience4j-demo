package com.example.resource;

import com.example.service.BulkheadService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class BulkheadResource {
    @Autowired
    private BulkheadService bulkheadService;

    @GetMapping("/json-object")
    public ResponseEntity<JsonNode> getJsonObject() throws InterruptedException {
        return ResponseEntity.ok(bulkheadService.getJsonObject());
    }


    @GetMapping("/json-object-with-fallback")
    public ResponseEntity<JsonNode> getJsonObjectWithFallback() throws InterruptedException {
        return ResponseEntity.ok(bulkheadService.getJsonObjectWithFallback());
    }

    @GetMapping("/json-object-by-threadpool")
    public ResponseEntity<JsonNode> getJsonObjectWithThreadPool() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(bulkheadService.getJsonObjectByThreadPool().get());
    }


    @GetMapping("/json-object-by-threadpool-with-fallback")
    public ResponseEntity<JsonNode> getJsonObjectWithThreadPoolWithFallback() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(bulkheadService.getJsonObjectByThreadPoolWithFallback().get());
    }
}

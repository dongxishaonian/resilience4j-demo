package com.example.resource;

import com.example.service.TimeLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class TimeLimiterResource {
    @Autowired
    private TimeLimiterService timeLimiterService;

    @GetMapping("/timeLimiter")
    public ResponseEntity<String> getStringRes() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(timeLimiterService.doSomeThing().get());
    }
}

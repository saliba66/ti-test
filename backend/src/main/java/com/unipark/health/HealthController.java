package com.unipark.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public Map<String, Object> getHealth() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("app", "unipark-backend");
        status.put("timestamp", Instant.now().toString());
        return status;
    }
}

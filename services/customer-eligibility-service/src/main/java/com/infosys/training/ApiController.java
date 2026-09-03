package com.infosys.training;

import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of("service", "customer-eligibility", "status", "UP");
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> body) {
        String correlationId = UUID.randomUUID().toString();
        return ResponseEntity.ok(Map.of(
            "service", "customer-eligibility",
            "correlationId", correlationId,
            "status", "PROCESSED",
            "message", "Evaluates whether the account/customer qualifies for processing.",
            "payload", body
        ));
    }
}

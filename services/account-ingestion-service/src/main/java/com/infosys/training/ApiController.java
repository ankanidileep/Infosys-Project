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
        return Map.of("service", "account-ingestion", "status", "UP");
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> body) {
        String correlationId = UUID.randomUUID().toString();
        return ResponseEntity.ok(Map.of(
            "service", "account-ingestion",
            "correlationId", correlationId,
            "status", "PROCESSED",
            "message", "Receives account/customer payloads and assigns a correlation ID.",
            "payload", body
        ));
    }
}

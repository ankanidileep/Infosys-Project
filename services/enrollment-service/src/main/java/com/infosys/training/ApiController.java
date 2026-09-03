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
        return Map.of("service", "enrollment", "status", "UP");
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> body) {
        String correlationId = UUID.randomUUID().toString();
        return ResponseEntity.ok(Map.of(
            "service", "enrollment",
            "correlationId", correlationId,
            "status", "PROCESSED",
            "message", "Determines CREATE/UPTIER/DOWNGRADE/UPDATE/NO_CHANGE action.",
            "payload", body
        ));
    }
}

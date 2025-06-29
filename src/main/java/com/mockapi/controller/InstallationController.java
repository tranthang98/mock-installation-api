package com.mockapi.controller;

import com.mockapi.dto.CreateOrderRequest;
import com.mockapi.dto.RequestInstallationResponse;
import com.mockapi.service.TrackingCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/vendor/installation")
@RequiredArgsConstructor
@Validated
public class InstallationController {

    private final TrackingCodeService trackingCodeService;

    /**
     * ✅ Mock API endpoint for installation registration
     * Matches your interface: /api/vendor/installation/register
     */
    @PostMapping("/register")
    public ResponseEntity<RequestInstallationResponse> registerInstallation(
            @Valid @RequestBody CreateOrderRequest request) {

        log.info("📦 Received installation registration request:");
        log.info("   - Products: {}", request.getProducts().size());
        log.info("   - Address: {}", request.getAddress().getCustomerFirstAddress());
        log.info("   - Install Date: {}", request.getDateInstall());

        // ✅ Generate tracking code
        String trackingCode = trackingCodeService.generateTrackingCode();

        log.info("✅ Generated tracking code: {}", trackingCode);

        // ✅ Build response
        RequestInstallationResponse response = RequestInstallationResponse.builder()
                .trackingCode(trackingCode)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "service", "Mock Installation API",
                "version", "1.0.0"
        ));
    }

    /**
     * ✅ Get tracking info (bonus endpoint)
     */
    @GetMapping("/tracking/{trackingCode}")
    public ResponseEntity<Map<String, Object>> getTrackingInfo(@PathVariable String trackingCode) {
        return ResponseEntity.ok(Map.of(
                "tracking_code", trackingCode,
                "status", "REGISTERED",
                "created_at", LocalDateTime.now(),
                "estimated_install_date", LocalDateTime.now().plusDays(3)
        ));
    }
}

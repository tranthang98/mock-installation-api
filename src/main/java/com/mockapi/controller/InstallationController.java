package com.mockapi.controller;

import com.mockapi.dto.CreateOrderRequest;
import com.mockapi.dto.HttpResponse;
import com.mockapi.dto.RequestInstallationResponse;
import com.mockapi.service.TrackingCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Returns HttpResponse format that matches client expectation
     */
    @PostMapping("/register")
    public ResponseEntity<HttpResponse<RequestInstallationResponse>> registerInstallation(
            @Valid @RequestBody CreateOrderRequest request) {

        log.info("📦 Received installation registration request:");
        log.info("   - Products: {}", request.getProducts().size());
        log.info("   - Address: {}", request.getAddress().getCustomerFirstAddress());
        log.info("   - Install Date: {}", request.getDateInstall());

        try {
            // ✅ Generate tracking code
            String trackingCode = trackingCodeService.generateTrackingCode();
            log.info("✅ Generated tracking code: {}", trackingCode);

            // ✅ Build response data
            RequestInstallationResponse responseData = RequestInstallationResponse.builder()
                    .trackingCode(trackingCode)
                    .build();

            // ✅ Wrap in HttpResponse format
            HttpResponse<RequestInstallationResponse> response = HttpResponse.success(responseData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("❌ Error processing registration request", e);

            HttpResponse<RequestInstallationResponse> errorResponse =
                    HttpResponse.error("Failed to process registration: " + e.getMessage(), 500);

            return ResponseEntity.status(500).body(errorResponse);
        }
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
    public ResponseEntity<HttpResponse<Map<String, Object>>> getTrackingInfo(@PathVariable String trackingCode) {
        Map<String, Object> trackingData = Map.of(
                "tracking_code", trackingCode,
                "status", "REGISTERED",
                "created_at", LocalDateTime.now(),
                "estimated_install_date", LocalDateTime.now().plusDays(3)
        );

        return ResponseEntity.ok(HttpResponse.success(trackingData));
    }
}

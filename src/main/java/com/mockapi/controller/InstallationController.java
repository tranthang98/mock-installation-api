package com.mockapi.controller;

import com.mockapi.dto.HttpResponse;
import com.mockapi.dto.request.CancelOrderRequest;
import com.mockapi.dto.request.CreateOrderRequest;
import com.mockapi.dto.response.RequestInstallationResponse;
import com.mockapi.service.TrackingCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
            HttpResponse<RequestInstallationResponse> response = HttpResponse.success("Register successfully", responseData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("❌ Error processing registration request", e);

            HttpResponse<RequestInstallationResponse> errorResponse =
                    HttpResponse.error("Failed to process registration: " + e.getMessage(), 500);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<HttpResponse<?>> cancelInstallOrder(@Valid @RequestBody CancelOrderRequest request) {
        try {
            HttpResponse<Void> response = HttpResponse.success("Cancel successfully!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            HttpResponse<?> errorResponse =
                    HttpResponse.error("Failed to process registration: " + e.getMessage(), 500);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "service", "Mock Installation API",
                "version", "1.0.0"
        ));
    }
}

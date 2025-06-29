package com.mockapi.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class TrackingCodeService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String PREFIX = "INST";
    private static final int CODE_LENGTH = 8;
    private final Random random = new SecureRandom();

    /**
     * ✅ Generate unique tracking code
     * Format: INST-YYYYMMDD-XXXXXXXX
     * Example: INST-20241225-A1B2C3D4
     */
    public String generateTrackingCode() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomCode = generateRandomString(CODE_LENGTH);

        return String.format("%s-%s-%s", PREFIX, dateStr, randomCode);
    }

    /**
     * ✅ Alternative: Simple random tracking code
     * Format: INST_XXXXXXXXXX
     * Example: INST_A1B2C3D4E5
     */
    public String generateSimpleTrackingCode() {
        String randomCode = generateRandomString(10);
        return PREFIX + "_" + randomCode;
    }

    /**
     * ✅ Generate UUID-style tracking code
     * Format: INST-XXXXXXXX-XXXX-XXXX
     * Example: INST-A1B2C3D4-E5F6-G7H8
     */
    public String generateUuidStyleTrackingCode() {
        return String.format("%s-%s-%s-%s",
                PREFIX,
                generateRandomString(8),
                generateRandomString(4),
                generateRandomString(4)
        );
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

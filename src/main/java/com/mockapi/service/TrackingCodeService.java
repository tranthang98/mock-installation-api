package com.mockapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class TrackingCodeService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final String prefix;
    private static final int CODE_LENGTH = 8;
    private final Random random = new SecureRandom();

    public TrackingCodeService(@Value("${app.tracking.prefix:INST}") String prefix) {
        this.prefix = prefix;
    }

    public String generateTrackingCode() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomCode = generateRandomString();
        return String.format("%s-%s-%s", prefix, dateStr, randomCode);
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder(TrackingCodeService.CODE_LENGTH);
        for (int i = 0; i < TrackingCodeService.CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

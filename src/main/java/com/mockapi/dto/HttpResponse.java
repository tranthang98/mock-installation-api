package com.mockapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse<T> {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("status")
    private Integer status;

    public static <T> HttpResponse<T> success(String message, T data) {
        return HttpResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(200)
                .build();
    }

    public static <T> HttpResponse<T> success(String message) {
        return HttpResponse.<T>builder()
                .success(true)
                .message(message)
                .status(200)
                .build();
    }

    public static <T> HttpResponse<T> error(String message, Integer status) {
        return HttpResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .status(status)
                .build();
    }
}

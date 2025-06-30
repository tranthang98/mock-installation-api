package com.mockapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateOrderRequest {

    private List<Product> products;

    @NotNull(message = "Address is required")
    private Address address;

    @NotNull(message = "Date install is required")
    private LocalDateTime dateInstall;

    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Product {
        private String name;
        private String type;
        private Integer quantity;
    }

    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Address {
        @NotNull(message = "Customer first address is required")
        private String customerFirstAddress;

        private String customerWard;
        private String customerDistrict;
        private String customerProvince;
    }
}

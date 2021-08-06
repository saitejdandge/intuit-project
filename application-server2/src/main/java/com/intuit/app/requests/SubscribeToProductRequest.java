package com.intuit.app.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubscribeToProductRequest {
    @NotBlank(message = "customerId is mandatory")
    String customerId;
    @NotBlank(message = "productId is mandatory")
    String productId;
}

package com.intuit.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Document(collection = "subscriptions")
public class Subscription {

    private String customerId;
    private List<ProductSub> productsSubscribed;

    public Subscription(String customerId) {
        this.customerId = customerId;
        this.productsSubscribed = new ArrayList<>();
    }

    public void addProductSubscription(ProductSub productSub) {
        if (productsSubscribed == null)
            productsSubscribed = new ArrayList<>();
        this.productsSubscribed.add(productSub);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductSub {
        private String productId;
        private long subscribedDate;
    }
}

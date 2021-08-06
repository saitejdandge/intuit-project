package com.intuit.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String serviceName;
    private boolean isActive = true;

    public Product(String id) {
        this.id = id;
    }


}

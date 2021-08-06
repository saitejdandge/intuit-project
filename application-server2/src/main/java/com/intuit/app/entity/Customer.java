package com.intuit.app.entity;

import com.intuit.app.entity.business_profile.BusinessProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private BusinessProfile businessProfile;

    public Customer(String id) {
        this.id = id;
    }
}

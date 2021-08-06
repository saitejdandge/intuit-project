package com.intuit.app.entity;


import com.intuit.app.entity.business_profile.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@Document(collection = "records")
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @Id
    private String id;
    private String customerId;
    private ApprovalStatus overallStatus;
    private HashMap<String, ProductApproval> productApprovals;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductApproval {
        private String productId;
        private String productName;
        private String productServiceName;
        private ApprovalStatus approvalStatus;
        private long lastModified;
    }
}

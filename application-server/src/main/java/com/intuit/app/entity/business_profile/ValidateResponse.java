package com.intuit.app.entity.business_profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidateResponse {


    private ApprovalStatus approvalStatus;
    private BusinessProfile businessProfile;
    private String productId;


    public static ValidateResponse getFailureResponse(String productId, BusinessProfile businessProfile) {
        return new ValidateResponse(ApprovalStatus.NOT_REACHABLE, businessProfile, productId);
    }

    public boolean isApproved() {
        return this.approvalStatus == ApprovalStatus.APPROVED;
    }

}

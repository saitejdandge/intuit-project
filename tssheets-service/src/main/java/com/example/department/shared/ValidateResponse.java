package com.example.department.shared;

import com.example.department.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateResponse {

    private ApprovalStatus approvalStatus;
    private BusinessProfile businessProfile;
    private String productId;

    public boolean isApproved() {
        return this.approvalStatus == ApprovalStatus.APPROVED;
    }


}

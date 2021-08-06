package com.example.department.service;

import com.example.department.ApprovalStatus;
import com.example.department.Constants;
import com.example.department.shared.BusinessProfile;
import com.example.department.shared.ValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentsService {
    public ValidateResponse validate(BusinessProfile businessProfile) {
        return new ValidateResponse(ApprovalStatus.APPROVED, businessProfile, Constants.SERVICE_ID);
    }
}

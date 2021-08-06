package com.example.userservice.service;

import com.example.userservice.shared.BusinessProfile;
import com.example.userservice.shared.ValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountingService {
    public ValidateResponse validate(BusinessProfile businessProfile) {
        return new ValidateResponse(ValidateResponse.ValidationStatus.APPROVED, businessProfile);
    }
}

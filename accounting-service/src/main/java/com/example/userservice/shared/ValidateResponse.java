package com.example.userservice.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResponse {

    private ValidationStatus validationStatus;
    private BusinessProfile businessProfile;

    public enum ValidationStatus {
        APPROVED, DENIED, NOT_REACHABLE;
    }
}

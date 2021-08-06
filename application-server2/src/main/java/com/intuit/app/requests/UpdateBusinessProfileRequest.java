package com.intuit.app.requests;

import com.intuit.app.entity.business_profile.BusinessProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateBusinessProfileRequest {
    @NotBlank(message = "customerId is mandatory")
    private String customerId;

    @NotNull(message = "businessProfile is mandatory")
    private BusinessProfile businessProfile;
}

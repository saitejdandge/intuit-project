package com.example.userservice.controller;

import com.example.userservice.service.AccountingService;
import com.example.userservice.shared.BusinessProfile;
import com.example.userservice.shared.ValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/accounting")
public class AccountingController {
    @Autowired
    private AccountingService accountingService;

    @PostMapping("/validate")
    public ValidateResponse validate(@RequestBody BusinessProfile businessProfile) {
        return accountingService.validate(businessProfile);
    }

}

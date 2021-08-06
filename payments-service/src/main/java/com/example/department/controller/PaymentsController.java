package com.example.department.controller;

import com.example.department.service.PaymentsService;
import com.example.department.shared.BusinessProfile;
import com.example.department.shared.ValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class PaymentsController {
    @Autowired
    private PaymentsService paymentsService;

    public int count;

    @PostMapping("/validate")
    public ValidateResponse validate(@RequestBody BusinessProfile businessProfile) {
        System.out.println("Request Received " + count);
        count++;
        return paymentsService.validate(businessProfile);
    }

}

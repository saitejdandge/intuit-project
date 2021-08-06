package com.example.department.controller;

import com.example.department.service.TSSheetService;
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
public class TSSheetController {
    @Autowired
    private TSSheetService TSSheetService;

    @PostMapping("/validate")
    public ValidateResponse validate(@RequestBody BusinessProfile businessProfile) {
        return TSSheetService.validate(businessProfile);
    }

}

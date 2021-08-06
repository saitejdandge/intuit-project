package com.intuit.app.controller;

import com.intuit.app.entity.Customer;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.requests.UpdateBusinessProfileRequest;
import com.intuit.app.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/findOneById/{id}")
    public Mono<Customer> findOneById(@Valid @NotBlank @PathVariable("id") String id) {
        return customerService.findOneById(id);
    }

    @GetMapping("/findAll")
    public Flux<Customer> findAll() {
        return customerService.findAll();
    }

    @PostMapping("/save")
    public ResponseWrapper<Customer> save(@Valid @RequestBody UpdateBusinessProfileRequest updateBusinessProfileRequest) {
        return customerService.updateCustomerBusinessProfile(updateBusinessProfileRequest);
    }

}

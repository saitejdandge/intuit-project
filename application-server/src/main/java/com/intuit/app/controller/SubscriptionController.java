package com.intuit.app.controller;

import com.intuit.app.entity.Subscription;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.requests.SubscribeToProductRequest;
import com.intuit.app.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController extends BaseController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public Mono<ResponseWrapper<Subscription>> subscribeToProduct(@Valid @RequestBody SubscribeToProductRequest subscribeToProductRequest) {
        return subscriptionService.subscribeToProduct(subscribeToProductRequest);
    }

}

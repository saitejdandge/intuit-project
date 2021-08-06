package com.intuit.app.service;

import com.intuit.app.ValidationUtils;
import com.intuit.app.entity.Subscription;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.repository.SubscriptionRepository;
import com.intuit.app.requests.SubscribeToProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class SubscriptionService {
    @Autowired
    ValidationUtils validationUtils;
    @Autowired
    ProductService productService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription findSubscriptionOfCustomer(String customerId) {
        return subscriptionRepository.findByCustomerId(customerId).block();
    }

    public Mono<ResponseWrapper<Subscription>> subscribeToProduct(SubscribeToProductRequest subscribeToProductRequest) {
        String customerId = subscribeToProductRequest.getCustomerId();
        String productId = subscribeToProductRequest.getProductId();
        this.validationUtils.validateCustomer(customerId);
        this.productService.validateProduct(productId);
        Subscription customerSubscription = this.checkIfAlreadySubscribed(customerId, productId);
        customerSubscription.addProductSubscription(new Subscription.ProductSub(productId, new Date().getTime()));
        return subscriptionRepository.save(customerSubscription).map(ResponseWrapper::success);
    }

    private Subscription checkIfAlreadySubscribed(String customerId, String productId) {
        Subscription customerSubscription = findSubscriptionOfCustomer(customerId);
        if (customerSubscription != null && customerSubscription.getProductsSubscribed() != null) {
            for (Subscription.ProductSub productSub : customerSubscription.getProductsSubscribed())
                if (productId.equals(productSub.getProductId()))
                    throw new BaseException(ErrorCodes.PRODUCT_ALREADY_SUBSCRIBED);
        }
        if (customerSubscription == null)
            customerSubscription = new Subscription(customerId);
        return customerSubscription;
    }
}

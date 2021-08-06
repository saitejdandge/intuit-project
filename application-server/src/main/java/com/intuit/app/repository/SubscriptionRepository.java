package com.intuit.app.repository;

import com.intuit.app.entity.Subscription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {
    Mono<Subscription> findByCustomerId(String customerId);
}

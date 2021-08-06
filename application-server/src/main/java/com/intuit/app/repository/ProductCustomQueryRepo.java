package com.intuit.app.repository;

import com.intuit.app.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductCustomQueryRepo {
    Flux<Product> findOneByServiceName(String serviceName);
}

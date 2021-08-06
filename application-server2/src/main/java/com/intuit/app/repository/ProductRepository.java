package com.intuit.app.repository;

import com.intuit.app.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ProductCustomQueryRepo {
}

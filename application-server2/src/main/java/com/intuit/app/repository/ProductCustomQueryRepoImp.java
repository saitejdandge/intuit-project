package com.intuit.app.repository;

import com.intuit.app.entity.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class ProductCustomQueryRepoImp implements ProductCustomQueryRepo {

    private final MongoTemplate mongoTemplate;

    ProductCustomQueryRepoImp(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Product> findOneByServiceName(String serviceName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("serviceName").is(serviceName));
        return Flux.just(Objects.requireNonNull(mongoTemplate.findOne(query, Product.class)));
    }
}

package com.intuit.app.repository;

import com.intuit.app.entity.Record;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends ReactiveMongoRepository<Record, String> {
}

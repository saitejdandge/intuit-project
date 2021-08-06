package com.intuit.app.service;

import com.intuit.app.Constants;
import com.intuit.app.ValidationUtils;
import com.intuit.app.entity.Customer;
import com.intuit.app.entity.Record;
import com.intuit.app.entity.Subscription;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.repository.CustomerRepository;
import com.intuit.app.requests.UpdateBusinessProfileRequest;
import com.intuit.app.entity.business_profile.ApprovalStatus;
import com.intuit.app.entity.business_profile.BusinessProfile;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final MongoTemplate mongoTemplate;
    private final SubscriptionService subscriptionService;
    private final RecordService recordService;
    private final RecordUtils recordUtils;
    private final ValidationUtils validationUtils;


    public CustomerService(CustomerRepository customerRepository, MongoTemplate mongoTemplate, SubscriptionService subscriptionService, RecordService recordService, RecordUtils recordUtils, ValidationUtils validationUtils) {
        this.customerRepository = customerRepository;
        this.mongoTemplate = mongoTemplate;
        this.subscriptionService = subscriptionService;
        this.recordService = recordService;
        this.recordUtils = recordUtils;
        this.validationUtils = validationUtils;
    }

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findOneById(String id) {
        return customerRepository.findById(id);
    }

    private Customer updateCustomer(String customerId, BusinessProfile businessProfile) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.id).is(customerId));
        Update update = new Update();
        update.set(Constants.BUSINESS_PROFILE, businessProfile);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Customer.class);
    }

    public ResponseWrapper<Customer> updateCustomerBusinessProfile(UpdateBusinessProfileRequest updateBusinessProfileRequest) {
        /*Validate the customer*/
        this.validationUtils.validateCustomer(updateBusinessProfileRequest.getCustomerId());

        /*Get the subscription of the customer*/
        Subscription subscriptionOfCustomer = subscriptionService.findSubscriptionOfCustomer(updateBusinessProfileRequest.getCustomerId());

        /*Creating a record for business profile update*/
        Record record = this.recordUtils.createRecord(updateBusinessProfileRequest, subscriptionOfCustomer);
        recordService.save(record);

        /*Calling multiple servers for validation*/
        Record updatedRecord = this.recordService.requestForProductApprovals(record, updateBusinessProfileRequest);
        recordService.save(updatedRecord);
        if (updatedRecord.getOverallStatus() == ApprovalStatus.APPROVED) {
            Customer customer = this.updateCustomer(updateBusinessProfileRequest.getCustomerId(), updateBusinessProfileRequest.getBusinessProfile());
            return ResponseWrapper.success(customer);
        } else {
            return ResponseWrapper.success(this.findOneById(updatedRecord.getCustomerId()).block());
        }
    }

}

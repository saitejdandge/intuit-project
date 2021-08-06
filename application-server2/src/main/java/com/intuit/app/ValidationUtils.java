package com.intuit.app;

import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    private final CustomerRepository customerRepository;

    public ValidationUtils(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*Throws exception when no customer is found*/
    public void validateCustomer(String customerId) {
        if (this.customerRepository.findById(customerId).block() == null)
            throw new BaseException(ErrorCodes.NO_CUSTOMER_FOUND);
    }


}

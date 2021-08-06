package com.intuit.app.service;

import com.intuit.app.ValidationUtils;
import com.intuit.app.entity.Customer;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @SpyBean
    CustomerService customerService;

    @SpyBean
    ValidationUtils validationUtils;

    @MockBean
    CustomerRepository mockRepo;


    @Test
    void validateCustomerTest() {
        /*When no customer exists in db*/
        when(mockRepo.findById(anyString())).thenReturn(Mono.empty());
        BaseException thrown = assertThrows(
                BaseException.class,
                () -> validationUtils.validateCustomer(anyString())
        );
        assertEquals(ErrorCodes.NO_CUSTOMER_FOUND, thrown.getErrorCode());


        /*When customer exist in db*/
        Customer customerMock = Mockito.mock(Customer.class);
        when(mockRepo.findById(anyString())).thenReturn(Mono.just(customerMock));
        assertDoesNotThrow(() -> validationUtils.validateCustomer(anyString()));
    }

    @Test
    void findAllTest() {
        int size = 10;
        Customer[] customers =
                Arrays.stream(IntStream.rangeClosed(1, size).toArray())
                        .mapToObj(id -> new Customer(String.valueOf(id)))
                        .toArray(value -> new Customer[size]);

        when(mockRepo.findAll()).thenReturn(Flux.fromArray(customers));

        StepVerifier.create(customerService.findAll())
                .expectNext(customers)
                .verifyComplete();


        /*If the repo layer throws exception, it should be propagated to service layer*/
        BaseException expectedException = new BaseException(ErrorCodes.STANDARD_ERROR);
        when(mockRepo.findAll()).thenReturn(Flux.error(expectedException));

        StepVerifier.create(customerService.findAll())
                .expectError(BaseException.class)
                .verify();
    }


    @Test
    void findOneByIdTest() {
        Customer mockCustomer = new Customer("1");
        when(mockRepo.findById(anyString())).thenReturn(Mono.just(mockCustomer));
        StepVerifier.create(customerService.findOneById(anyString()))
                .expectNext(mockCustomer)
                .verifyComplete();

        BaseException expectedException = new BaseException(ErrorCodes.STANDARD_ERROR);
        when(mockRepo.findById(anyString())).thenReturn(Mono.error(expectedException));

        StepVerifier.create(customerService.findOneById(anyString()))
                .expectError(BaseException.class)
                .verify();


    }

}
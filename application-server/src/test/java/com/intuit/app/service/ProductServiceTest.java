package com.intuit.app.service;

import com.intuit.app.entity.Product;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.exceptions.ResponseStatus;
import com.intuit.app.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {ProductService.class, ProductRepository.class})
class ProductServiceTest {

    @SpyBean
    ProductService productService;

    @MockBean
    ProductRepository mockRepo;


    @Test
    void validateProductTest() {

        Product productMock = Mockito.mock(Product.class);
        when(mockRepo.findById(anyString())).thenReturn(Mono.just(productMock));

        /*Product is active*/
        when(productMock.isActive()).thenReturn(true);
        assertEquals(productMock, productService.validateProduct(anyString()));

        /*Product is inactive*/
        when(productMock.isActive()).thenReturn(false);
        BaseException thrown = assertThrows(
                BaseException.class,
                () -> productService.validateProduct(anyString())
        );
        assertEquals(ErrorCodes.INVALID_PRODUCT, thrown.getErrorCode());

        /*product is null*/
        when(mockRepo.findById(anyString())).thenReturn(Mono.empty());
        BaseException thrown2 = assertThrows(
                BaseException.class,
                () -> productService.validateProduct(anyString())
        );
        assertEquals(ErrorCodes.INVALID_PRODUCT, thrown2.getErrorCode());
    }

    @Test
    void validateServiceNameTest() {
        /*When product exists in db*/
        Product productMock = Mockito.mock(Product.class);
        when(mockRepo.findOneByServiceName(anyString())).thenReturn(Flux.just(productMock));

        BaseException thrown = assertThrows(
                BaseException.class,
                () -> productService.validateServiceName(anyString())
        );
        assertEquals(ErrorCodes.SERVICE_ALREADY_RUNNING, thrown.getErrorCode());


        /*When product doesn't exist in db*/
        Mockito.when(mockRepo.findOneByServiceName(anyString())).thenReturn(null);
        assertDoesNotThrow(() -> productService.validateServiceName(anyString()));
    }

    @Test
    void getProductsTest() {
        int size = 10;
        Product[] products =
                Arrays.stream(IntStream.rangeClosed(1, size).toArray())
                        .mapToObj(id -> new Product(String.valueOf(id)))
                        .toArray(value -> new Product[size]);

        when(mockRepo.findAll()).thenReturn(Flux.fromArray(products));

        StepVerifier.create(productService.getProducts())
                .expectNext(products)
                .verifyComplete();


        /*If the repo layer throws exception, it should be propagated to service layer*/
        BaseException expectedException = new BaseException(ErrorCodes.STANDARD_ERROR);
        when(mockRepo.findAll()).thenReturn(Flux.error(expectedException));

        StepVerifier.create(productService.getProducts())
                .expectError(BaseException.class)
                .verify();
    }

    @Test
    void saveProductTest() {
        Product product = new Product();
        when(mockRepo.save(any())).thenReturn(Mono.just(product));

        StepVerifier.create(productService.saveProduct(product))
                .assertNext(productResponseWrapper -> {
                    Assertions.assertEquals(productResponseWrapper.getStatus(), ResponseStatus.SUCCESS);
                    assertEquals(productResponseWrapper.getData(), product);
                })
                .verifyComplete();
    }

    @Test
    void findOneByServiceNameTest() {
        Product mockProduct = new Product("1");
        Mockito.when(mockRepo.findOneByServiceName(anyString())).thenReturn((Flux.just(mockProduct)));
        assertEquals(productService.findOneByServiceName(anyString()), mockProduct);

        BaseException expected = new BaseException(ErrorCodes.STANDARD_ERROR);
        Mockito.when(mockRepo.findOneByServiceName(anyString())).thenThrow(expected);
        BaseException actual = assertThrows(
                BaseException.class,
                () -> productService.findOneByServiceName(anyString())
        );
        assertEquals(expected, actual);


    }

}
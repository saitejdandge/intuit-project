package com.intuit.app.service;

import com.intuit.app.entity.Product;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> findOneById(@Valid @NotBlank String id) {
        return productRepository.findById(id);
    }

    public Product findOneByServiceName(String serviceName) {
        Flux<Product> productFlux = productRepository.findOneByServiceName(serviceName);
        return productFlux != null ? productFlux.blockFirst() : null;
    }


    public Mono<ResponseWrapper<Product>> saveProduct(Product product) {
        this.validateServiceName(product.getServiceName());
        return productRepository.save(product).map(ResponseWrapper::success);
    }

    /*Checks if the service name is unique i.e not exists in db*/
    public void validateServiceName(String serviceName) {
        Product product = this.findOneByServiceName(serviceName);
        if (product != null)
            throw new BaseException(ErrorCodes.SERVICE_ALREADY_RUNNING);
    }

    public Product validateProduct(String productId) {
        Product product = this.findOneById(productId).block();
        if (product == null || !product.isActive())
            throw new BaseException(ErrorCodes.INVALID_PRODUCT);
        return product;
    }
}

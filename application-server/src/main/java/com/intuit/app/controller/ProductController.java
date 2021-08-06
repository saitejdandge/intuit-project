package com.intuit.app.controller;

import com.intuit.app.entity.Product;
import com.intuit.app.exceptions.ResponseWrapper;
import com.intuit.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/findOneById/{id}")
    public Mono<Product> findOneById(@Valid @NotBlank @PathVariable("id") String id) {
        return productService.findOneById(id);
    }

    @GetMapping("/findAll")
    public Flux<Product> findAll() {
        return productService.getProducts();
    }

    @PostMapping("/save")
    public Mono<ResponseWrapper<Product>> save(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

}

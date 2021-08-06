package com.example.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PaymentsServerApp {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsServerApp.class, args);
    }

}

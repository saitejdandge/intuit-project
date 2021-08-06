package com.example.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TSheetServerApp {

    public static void main(String[] args) {
        SpringApplication.run(TSheetServerApp.class, args);
    }

}

package com.intuit.app.service;

import com.intuit.app.exception_filter.ExceptionFilter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMongoConfig {

    @Autowired
    private ExceptionFilter exceptionFilter;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://dbuser:dbpassword@primary.ofp4b.mongodb.net/primarydb?retryWrites=true&w=majority");
    }

}

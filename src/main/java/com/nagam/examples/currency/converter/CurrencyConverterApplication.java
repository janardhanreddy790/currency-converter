package com.nagam.examples.currency.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring boot application start point
 */
@SpringBootApplication
@EnableCaching // Enable caching support
public class CurrencyConverterApplication {


    /**
     * Start point
     *
     * @param args system arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }
}

package com.nagam.examples.currency.converter.web.dto;

import lombok.Data;

/**
 * Represents a conversion request with base currency, target currency, and amount.
 */
@Data
public class ConversionRequest {
    private String base;
    private String target;
    private Double amount; // Change the type to Double to allow for null values
}

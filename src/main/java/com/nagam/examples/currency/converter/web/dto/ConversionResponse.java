package com.nagam.examples.currency.converter.web.dto;

import lombok.Data;

import java.util.Map;

/**
 * Represents a conversion response containing the base currency and exchange rates.
 */
@Data
public class ConversionResponse {
    private String base;
    private Map<String, Double> rates;
}

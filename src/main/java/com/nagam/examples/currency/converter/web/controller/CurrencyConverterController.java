package com.nagam.examples.currency.converter.web.controller;

import com.nagam.examples.currency.converter.web.dto.ConversionRequest;
import com.nagam.examples.currency.converter.web.service.CurrencyConverterService;
import com.nagam.examples.currency.converter.web.dto.ConversionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final RestTemplate restTemplate;

    private static final Logger logger = Logger.getLogger(CurrencyConverterController.class.getName());

    @Value("${external.forex.url}")
    private String forexUrl;

    private final CurrencyConverterService currencyConverterService;

    /**
     * Endpoint to retrieve currency data from an external service.
     *
     * @return ResponseEntity containing the response data from the external service.
     */
    @GetMapping("/forex/currencies")
    public ResponseEntity<ConversionResponse> getCurrencies() {
        try {
            // Retrieve currency data from the currency converter service
            ConversionResponse conversionResponse = currencyConverterService.getAllCurrencies();
            return ResponseEntity.ok(conversionResponse);
        } catch (Exception e) {
            // Log error and return internal server error response if an exception occurs
            logger.log(Level.SEVERE, "Error retrieving currencies: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint to convert currency based on the provided conversion request.
     *
     * @param request The conversion request containing amount, from currency, and to currency.
     * @return ResponseEntity containing the converted amount.
     */
    @PutMapping("/forex/convert")
    public ResponseEntity<Double> convertCurrency(@RequestBody ConversionRequest request) {
        try {
            ResponseEntity<ConversionResponse> responseEntity = restTemplate.getForEntity(forexUrl, ConversionResponse.class);
            ConversionResponse conversionResponse = responseEntity.getBody();

            Map<String, Double> currencyRates = conversionResponse.getRates();
            double fromRate = currencyRates.getOrDefault(request.getBase(), 1.0);
            double toRate = currencyRates.getOrDefault(request.getTarget(), 1.0);

            double convertedAmount = request.getAmount() * (toRate / fromRate);

            logger.log(Level.INFO, "Conversion request: {0} {1} to {2}", new Object[]{request.getAmount(), request.getBase(), request.getTarget()});
            logger.log(Level.INFO, "Conversion rate: {0} -> {1}", new Object[]{request.getBase(), fromRate});
            logger.log(Level.INFO, "Conversion rate: {0} -> {1}", new Object[]{request.getTarget(), toRate});
            logger.log(Level.INFO, "Converted amount: {0}", convertedAmount);

            return ResponseEntity.ok(convertedAmount);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred during currency conversion: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

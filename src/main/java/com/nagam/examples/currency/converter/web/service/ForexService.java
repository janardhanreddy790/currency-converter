package com.nagam.examples.currency.converter.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nagam.examples.currency.converter.web.dto.ConversionResponse;

import java.util.Map;

@Service
public class ForexService {

    private final RestTemplate restTemplate;

    @Value("${external.forex.url}")
    private String forexApiUrl;

    public ForexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> getCurrencyRates() {
        ResponseEntity<Map<String, Double>> response = restTemplate.exchange(
                forexApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Double>>() {}
        );
        return response.getBody();
    }

    public ConversionResponse getAllCurrencies() {
        // Assuming the external service provides a ConversionResponse object with all currencies
        return restTemplate.getForObject(forexApiUrl, ConversionResponse.class);
    }
}

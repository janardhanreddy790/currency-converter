package com.nagam.examples.currency.converter.web.service;

import com.nagam.examples.currency.converter.web.dto.ConversionResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@CacheConfig(cacheNames = "currencyRates")
public class CurrencyConverterService {

    private final RestTemplate restTemplate;
    private final ForexService forexService;

    public CurrencyConverterService(RestTemplate restTemplate, ForexService forexService) {
        this.restTemplate = restTemplate;
        this.forexService = forexService;
    }

    @Cacheable
    public ConversionResponse getAllCurrencies() {
        return forexService.getAllCurrencies();
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> currencyRates = getCurrencyRates();
        double fromRate = currencyRates.getOrDefault(fromCurrency, 1.0);
        double toRate = currencyRates.getOrDefault(toCurrency, 1.0);
        return amount * (toRate / fromRate);
    }

    @Cacheable
    public Map<String, Double> getCurrencyRates() {
        return forexService.getCurrencyRates();
    }

    // Evict the cache after a specified time interval to refresh the currency rates
    @CacheEvict(allEntries = true, beforeInvocation = true, cacheManager = "cacheManager")
    public void evictCurrencyRates() {
        // This method will be invoked periodically to evict the cache and refresh the currency rates
    }
}

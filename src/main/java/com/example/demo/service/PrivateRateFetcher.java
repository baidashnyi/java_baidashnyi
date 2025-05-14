package com.example.demo.service;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PrivateRateFetcher {
    
    private static final String API_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private final RestTemplate template;

    public PrivateRateFetcher() {
        this.template = new RestTemplate();
    }

    public float getDollarRate() {
        String response = fetchExchangeRate();
        if (response == null) {
            return -1;
        }
        
        return getUsdBuyRateFromResponse(response);
    }

    private String fetchExchangeRate() {
        try {
            return template.getForObject(API_URL, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    private float getUsdBuyRateFromResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            String buyRate = jsonArray.getJSONObject(1).getString("buy");
            return Float.parseFloat(buyRate);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

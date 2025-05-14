package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VacancyUrlBuilder {
    private final String baseUrl;

    public VacancyUrlBuilder(@Value("${parser.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String buildUrl(String jobName, int page) {
        return String.format("%s/jobs-%s?page=%d", baseUrl, jobName, page);
    }
    
    public String getBaseUrl() {
    	return baseUrl;
    }
}

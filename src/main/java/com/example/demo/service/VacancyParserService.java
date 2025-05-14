package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.demo.entity.Vacancy;

@Service
public class VacancyParserService {
    
	private final VacancyParser listParser;
    private final VacancyDetailsParser detailsParser;
    
    public VacancyParserService(VacancyParser listParser, VacancyDetailsParser detailsParser) {
    	this.listParser = listParser;
    	this.detailsParser = detailsParser;
    }

    public List<Vacancy> parseVacancies(String jobName) throws IOException {
        List<String> vacancyLinks = listParser.getVacancyLinks(jobName);
        
        return vacancyLinks.stream()
                .map(link -> {
                    try {
                        return detailsParser.parseVacancy(link);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(vacancy -> vacancy != null)
                .collect(Collectors.toList());
    }
}

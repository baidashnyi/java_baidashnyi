package com.example.demo.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class WorkUaVacancyParser implements VacancyParser{
    private static final int MAX_PAGES = 5;
    private final VacancyUrlBuilder urlBuilder;

    public WorkUaVacancyParser(VacancyUrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    @Override
    public List<String> getVacancyLinks(String jobName){
        List<String> vacancyLinks = new ArrayList<>();

        for (int page = 1; page <= MAX_PAGES; page++) {
            String url = urlBuilder.buildUrl(jobName, page);
            
            Document doc = new Document("");
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

            Elements jobElements = doc.select("#pjax-jobs-list h2 a");

            if (jobElements.isEmpty()) {
                break;
            }

            for (Element job : jobElements) {
                String jobUrl = urlBuilder.getBaseUrl() + job.attr("href");
                vacancyLinks.add(jobUrl);
            }
        }

        return vacancyLinks;
    }
}

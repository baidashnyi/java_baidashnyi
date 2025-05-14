package com.example.demo.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Vacancy;

@Component
public class VacancyDetailsParser {
    public Vacancy parseVacancy(String jobUrl) throws IOException {
        Document jobDoc = Jsoup.connect(jobUrl).get();

        String title = jobDoc.selectFirst("h1").text();
        
        String company = getElementText(jobDoc, "span[title='Дані про компанію'] + a");
        String salary = getElementText(jobDoc, "span[title='Зарплата'] + span");
        String location = getElementText(jobDoc, "li:has(span[title='Місце роботи'])");
        String address = getElementText(jobDoc, "li:has(span[title='Умови й вимоги'])");

        return new Vacancy(title, jobUrl, company, salary, location, address);
    }

    private String getElementText(Document doc, String selector) {
        Element element = doc.selectFirst(selector);
        return (element != null) ? element.text() : "Не вказано";
    }
}

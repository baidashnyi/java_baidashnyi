package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Vacancy;
import com.example.demo.repository.IVacancyRepository;
import com.example.demo.service.PrivateRateFetcher;
import com.example.demo.service.VacancyParserService;

@Controller
public class VacancyPageController {
    private final VacancyParserService vacancyParserService;
    private final IVacancyRepository vacancyRepository;
    private final PrivateRateFetcher rateFetcher;

    public VacancyPageController(VacancyParserService vacancyParserService, IVacancyRepository vacancyRepository, PrivateRateFetcher rateFetcher) {
        this.vacancyParserService = vacancyParserService;
        this.vacancyRepository = vacancyRepository;
        this.rateFetcher = rateFetcher;
    }

    @GetMapping
    public String showVacanciesPage(Model model) {
        List<Vacancy> vacancies = vacancyRepository.findAll(); 
        model.addAttribute("vacancies", vacancies); 
        model.addAttribute("dollarRate", rateFetcher.getDollarRate());
        return "vacancies"; 
    }

    @PostMapping("/import")
    public String importVacancies(@RequestParam String job) throws IOException {
        List<Vacancy> vacancies = vacancyParserService.parseVacancies(job); 
        if (!vacancies.isEmpty()) {
            vacancyRepository.saveAll(vacancies);
        }

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteVacancy(@RequestParam Long id) {
        if (vacancyRepository.existsById(id)) {
            vacancyRepository.deleteById(id);
        }

        return "redirect:/"; 
    }
}

package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Vacancy;
import com.example.demo.repository.IVacancyRepository;
import com.example.demo.service.ExcelExportService;
import com.example.demo.service.VacancyParserService;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {
    private final VacancyParserService vacancyParserService;
    private final IVacancyRepository vacancyRepository;
    private final ExcelExportService excelExportService;

    public VacancyController(VacancyParserService vacancyParserService, IVacancyRepository vacancyRepository, ExcelExportService excelExportService) {
        this.vacancyParserService = vacancyParserService;
        this.vacancyRepository = vacancyRepository;
        this.excelExportService = excelExportService;
    }

    @GetMapping
    public List<Vacancy> getVacancies(@RequestParam String job) throws IOException {
        return vacancyParserService.parseVacancies(job);
    }
    
    @PostMapping
    public ResponseEntity<List<Vacancy>> addVacancies(@RequestParam String job) throws IOException {
        List<Vacancy> vacancies = vacancyParserService.parseVacancies(job);

        if (vacancies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        vacancyRepository.saveAll(vacancies);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancies);
    }
    
    @PostMapping("/delete")
    public ResponseEntity<String> deleteVacancy(@RequestParam Long id) {
        if (vacancyRepository.existsById(id)) {
            vacancyRepository.deleteById(id);
            return ResponseEntity.ok("Vacancy with ID " + id + " deleted successfully.");
        } 
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vacancy with ID " + id + " not found.");
    
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportVacanciesToExcel() throws IOException {
        List<Vacancy> vacancies = vacancyRepository.findAll();

        byte[] excelFile = excelExportService.exportVacanciesToExcel(vacancies);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=vacancies.xlsx");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}

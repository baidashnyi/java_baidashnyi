package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Vacancy;

@Service
public class ExcelExportService {
    public byte[] exportVacanciesToExcel(List<Vacancy> vacancies) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Vacancies");

        
        XSSFRow headerRow = sheet.createRow(0);
        String[] columnNames = {"ID", "Title", "URL", "Company", "Salary", "Location", "Address"};
        for (int i = 0; i < columnNames.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames[i]);
        }

        
        int rowNum = 1;
        for (Vacancy vacancy : vacancies) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vacancy.getId());
            row.createCell(1).setCellValue(vacancy.getTitle());
            row.createCell(2).setCellValue(vacancy.getUrl());
            row.createCell(3).setCellValue(vacancy.getCompany());
            row.createCell(4).setCellValue(vacancy.getSalary());
            row.createCell(5).setCellValue(vacancy.getLocation());
            row.createCell(6).setCellValue(vacancy.getAddress());
        }

        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        baos.close();
        return baos.toByteArray();
    }
}

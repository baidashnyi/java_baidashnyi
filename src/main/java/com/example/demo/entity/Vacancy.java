package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String company;
    private String salary;
    private String location;
    private String address;

    public Vacancy() {}
   
    public Vacancy(String title, String url, String company, String salary, String location, String address) {
        this.title = title;
        this.url = url;
        this.company = company;
        this.salary = salary;
        this.location = location;
        this.address = address;
    }	
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url;}

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

}

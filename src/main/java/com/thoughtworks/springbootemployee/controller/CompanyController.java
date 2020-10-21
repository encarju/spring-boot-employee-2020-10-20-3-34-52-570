package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAll() {
        return companies;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        companies.add(company);
        return company;
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable Integer companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable Integer companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null).getEmployees();
    }

}

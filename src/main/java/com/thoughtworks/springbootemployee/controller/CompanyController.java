package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null).getEmployees();
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId,  @RequestBody Company updatedCompany){
        if(companies.stream()
                .anyMatch(company -> company.getCompanyId().equals(companyId))){
            companies.stream()
                    .filter(company -> company.getCompanyId().equals(companyId))
                    .findFirst()
                    .ifPresent(company -> {
                        companies.remove(company);
                        companies.add(updatedCompany);
                    });
            return updatedCompany;
        }
        return null;
    }

    @DeleteMapping("/{companyId}")
    public Company removeCompany(@PathVariable Integer companyId){
        companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .ifPresent(company -> {
                    company.getEmployees().removeAll(company.getEmployees());
                });
        return companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companies.stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}

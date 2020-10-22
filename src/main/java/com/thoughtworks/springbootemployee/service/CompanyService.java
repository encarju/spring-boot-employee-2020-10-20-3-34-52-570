package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> getAll() {
        return repository.findAll();
    }

    public Company create(Company company) {
        return repository.save(company);
    }

    public Company getById(Integer companyId) {
        return repository.findById(companyId).orElse(null);
    }

    public Company update(Integer companyId, Company updatedCompany) {
        return repository.findById(companyId)
                .map(company -> repository.save(updatedCompany))
                .orElse(null);
    }

    public Company remove(Integer companyId) {
        return repository.findById(companyId)
                .map(company -> {
                    company.getEmployees().clear();
                    repository.save(company);

                    return company;
                })
                .orElse(null);
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return repository.findAll(PageRequest.of(page, pageSize))
                .toList();
    }

    public List<Employee> getCompanyEmployees(Integer companyID) {
        return repository.findById(companyID)
                .map(Company::getEmployees)
                .orElse(Collections.emptyList());
    }
}

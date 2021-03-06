package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class CompanyService {
    private final CompanyRepository repository;

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
        return repository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public Company update(Integer companyId, Company updatedCompany) {
        return repository.findById(companyId)
                .map(company -> {
                    company.setName(updatedCompany.getName());

                    return repository.save(company);
                })
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public Company removeCompanyEmployees(Integer companyId) {
        return repository.findById(companyId)
                .map(company -> {
                    company.getEmployees().clear();
                    repository.save(company);

                    return company;
                })
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return repository.findAll(of(page, pageSize))
                .toList();
    }

    public List<Employee> getCompanyEmployees(Integer companyID) {
        return repository.findById(companyID)
                .map(Company::getEmployees)
                .orElse(emptyList());
    }
}

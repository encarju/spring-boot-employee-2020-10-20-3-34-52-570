package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

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
        return repository.getById(companyId);
    }

    public Company update(Integer companyId, Company updatedCompany) {
        return repository.update(companyId, updatedCompany);
    }

    public Company remove(Integer companyId) {
        return repository.remove(companyId);
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return repository.getByPage(page, pageSize);
    }

    public List<Employee> getCompanyEmployees(Integer companyID) {
        return repository.getCompanyEmployees(companyID);
    }
}

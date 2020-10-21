package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    public Company getById(Integer companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    public Company update(Integer companyId, Company updatedCompany) {
        return null;
    }

    public void remove(Integer companyId) {

    }
}

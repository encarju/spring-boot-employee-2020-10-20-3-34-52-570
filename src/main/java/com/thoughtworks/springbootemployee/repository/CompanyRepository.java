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
}

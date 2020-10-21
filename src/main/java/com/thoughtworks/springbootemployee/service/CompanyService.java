package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {
    public CompanyService(CompanyRepository repository) {
    }

    public List<Company> getAll() {
        return null;
    }
}

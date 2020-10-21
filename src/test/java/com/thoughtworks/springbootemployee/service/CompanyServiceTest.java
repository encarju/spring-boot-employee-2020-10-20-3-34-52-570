package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CompanyServiceTest {
    @Test
    public void should_return_companies_when_get_all_companies() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        List<Company> expectedCompanies = asList(new Company(), new Company());
        when(repository.findAll()).thenReturn(expectedCompanies);
        CompanyService service = new CompanyService(repository);

        //when
        List<Company> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_companies_when_create_given_one_companies() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                2, asList(new Employee(),new Employee()));
        when(repository.save(company)).thenReturn(company);

        //when
        Company actual  = service.create(company);

        //then
        assertEquals(1, actual.getCompanyId());
    }
}
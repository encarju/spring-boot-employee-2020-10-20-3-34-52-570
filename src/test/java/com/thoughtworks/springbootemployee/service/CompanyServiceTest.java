package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.TestConstants.ALIBABA;
import static com.thoughtworks.springbootemployee.TestConstants.ALIBABAS;
import static com.thoughtworks.springbootemployee.TestConstants.FEMALE;
import static com.thoughtworks.springbootemployee.TestConstants.JUSTINE;
import static com.thoughtworks.springbootemployee.TestConstants.LILY;
import static com.thoughtworks.springbootemployee.TestConstants.MALE;
import static com.thoughtworks.springbootemployee.TestConstants.ONCE;
import static com.thoughtworks.springbootemployee.TestConstants.SALARY;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    public void should_return_companies_when_get_all_companies() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        List<Company> expectedCompanies = asList(new Company(), new Company());

        when(repository.findAll()).thenReturn(expectedCompanies);

        //when
        List<Company> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_companies_when_create_given_one_companies() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        Company company = new Company(1, ALIBABA, asList(new Employee(), new Employee()));

        when(repository.save(company)).thenReturn(company);

        //when
        Company actual = service.create(company);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_company_when_get_company_give_company_id() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        Company company = new Company(1, ALIBABA, asList(new Employee(), new Employee()));
        Integer companyId = company.getId();

        when(repository.findById(companyId)).thenReturn(of(company));

        //when
        Company actual = service.getById(companyId);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_company_when_update_company_given_company_id_updated_name() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        Company company = new Company(1, ALIBABA,
                asList(new Employee(), new Employee()));
        Integer companyId = company.getId();

        Company updatedCompany = new Company(1, ALIBABAS,
                asList(new Employee(), new Employee()));

        when(repository.findById(companyId)).thenReturn(of(company));
        when(repository.save(updatedCompany)).thenReturn(updatedCompany);

        //when
        Company actual = service.update(companyId, updatedCompany);

        //then
        assertEquals(ALIBABAS, actual.getName());
    }

    @Test
    void should_delete_company_when_delete_company_given_company_id() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());

        Company company = new Company(1, ALIBABA, employees);

        company.getEmployees().clear();
        Company expectedCompany = company;

        Integer companyId = company.getId();

        when(repository.findById(companyId)).thenReturn(of(company));
        when(repository.save(expectedCompany)).thenReturn(expectedCompany);

        //when
        Company actualCompany = service.remove(companyId);

        //then
        verify(repository, times(ONCE)).findById(companyId);
        verify(repository, times(ONCE)).save(expectedCompany);
        assertEquals(expectedCompany, actualCompany);
    }

    @Test
    public void should_return_2_company_when_get_by_page_given_2_page_size() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        List<Company> returnedCompanies = asList(
                new Company(1, ALIBABA, asList(new Employee(), new Employee())),
                new Company(2, ALIBABAS, asList(new Employee(), new Employee())));

        Integer page = 1;
        Integer pageSize = 2;

        Page<Company> companyPage = mock(Page.class);

        when(repository.findAll(PageRequest.of(page, pageSize))).thenReturn(companyPage);
        when(companyPage.toList()).thenReturn(returnedCompanies);

        //when
        List<Company> actual = service.getByPage(page, pageSize);

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_return_all_employee_when_get_employees_given_company_id() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);

        List<Employee> employees = asList(
                new Employee(1, JUSTINE, 2, MALE, SALARY),
                new Employee(2, LILY, 2, FEMALE, SALARY)
        );

        Company company = new Company(1, ALIBABA, employees);
        Integer companyID = company.getId();

        when(repository.findById(companyID)).thenReturn(of(company));

        //when
        List<Employee> actual = service.getCompanyEmployees(companyID);

        //then
        assertEquals(2, actual.size());
    }
}
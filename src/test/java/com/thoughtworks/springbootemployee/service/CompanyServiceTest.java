package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.thoughtworks.springbootemployee.TestHelper.ALIBABA;
import static com.thoughtworks.springbootemployee.TestHelper.ALIBABAS;
import static com.thoughtworks.springbootemployee.TestHelper.FEMALE;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.LILY;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.ONCE;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static com.thoughtworks.springbootemployee.TestHelper.generateDummyEmployees;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    public static final String COMPANY_WITH_ID_D_DOES_NOT_EXIST = "Company with ID %d does not exist";
    private CompanyRepository repository;
    private CompanyService service;

    @BeforeEach
    void setUp() {
        repository = mock(CompanyRepository.class);
        service = new CompanyService(repository);
    }

    @Test
    public void should_return_companies_when_get_all_companies() {
        //given
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
        Company company = new Company(1, ALIBABA, generateDummyEmployees(2));

        when(repository.save(company)).thenReturn(company);

        //when
        Company actual = service.create(company);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_company_when_get_company_give_company_id() {
        //given
        Company company = new Company(1, ALIBABA, generateDummyEmployees(2));
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
        Company company = new Company(1, ALIBABA, generateDummyEmployees(2));
        Integer companyId = company.getId();

        Company updatedCompany = new Company(1, ALIBABAS,
                asList(new Employee(), new Employee()));

        when(repository.findById(companyId)).thenReturn(of(company), of(updatedCompany));
        when(repository.save(updatedCompany)).thenReturn(updatedCompany);

        //when
        Company actual = service.update(companyId, updatedCompany);

        //then
        assertEquals(ALIBABAS, actual.getName());
    }

    @Test
    void should_delete_company_when_delete_company_given_company_id() {
        //given
        Company company = new Company(1, ALIBABA, generateDummyEmployees(2));

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

    @Test
    public void should_company_not_found_exception_when_get_company_given_company_id_does_not_exist() {
        //given
        Integer wrongCompanyId = 2;
        String expectedMessage = String.format(COMPANY_WITH_ID_D_DOES_NOT_EXIST, wrongCompanyId);

        when(repository.findById(wrongCompanyId)).thenReturn(Optional.empty());

        //when
        Executable executable = () -> service.getById(wrongCompanyId);

        //then
        Exception exception = assertThrows(CompanyNotFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_return_company_not_found_exception_when_update_company_given_wrong_company_id() {
        //given
        Company updatedCompany = new Company(2, ALIBABAS,
                asList(new Employee(), new Employee()));
        Integer wrongCompanyId = updatedCompany.getId();
        String expectedMessage = String.format(COMPANY_WITH_ID_D_DOES_NOT_EXIST, wrongCompanyId);

        when(repository.findById(wrongCompanyId)).thenReturn(Optional.empty());

        //when
        Executable executable = () -> service.update(wrongCompanyId, updatedCompany);

        //then
        Exception exception = assertThrows(CompanyNotFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_return_company_not_found_exception_company_when_delete_company_given_wrong_company_id() {
        //given
        Integer wrongCompanyId = 2;
        String expectedMessage = String.format(COMPANY_WITH_ID_D_DOES_NOT_EXIST, wrongCompanyId);

        when(repository.findById(wrongCompanyId)).thenReturn(Optional.empty());

        //when
        Executable executable = () -> service.remove(wrongCompanyId);

        //then
        Exception exception = assertThrows(CompanyNotFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
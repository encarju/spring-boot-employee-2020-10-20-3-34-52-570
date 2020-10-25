package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.TestHelper.AGE_23;
import static com.thoughtworks.springbootemployee.TestHelper.COSCO;
import static com.thoughtworks.springbootemployee.TestHelper.JOHN;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.OOCL;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static com.thoughtworks.springbootemployee.mapper.CompanyMapper.COMPANY_MAPPER;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompanyMapperTest {

    @Test
    void should_return_company_response_when_to_response_given_company() {
        //Given
        List<Employee> employeeList = new ArrayList<>();

        Integer companyId = 1;
        Company company = new Company(companyId, OOCL, employeeList);

        employeeList.add(new Employee(1, JUSTINE, AGE_23, MALE, SALARY, companyId));
        employeeList.add(new Employee(2, JOHN, AGE_23, MALE, SALARY, companyId));

        //When
        CompanyResponse companyResponse = COMPANY_MAPPER.toResponse(company);

        //Then
        assertEquals(OOCL, companyResponse.getName());
        assertEquals(companyId, companyResponse.getId());
        assertEquals(employeeList.size(), companyResponse.getEmployeesNumber());

        assertEquals(JUSTINE, companyResponse.getEmployees().get(0).getName());
        assertEquals(AGE_23, companyResponse.getEmployees().get(0).getAge());
        assertEquals(MALE, companyResponse.getEmployees().get(0).getGender());
        assertEquals(SALARY, companyResponse.getEmployees().get(0).getSalary());
        assertEquals(company.getId(), companyResponse.getEmployees().get(0).getCompanyId());

        assertEquals(JOHN, companyResponse.getEmployees().get(1).getName());
        assertEquals(AGE_23, companyResponse.getEmployees().get(1).getAge());
        assertEquals(MALE, companyResponse.getEmployees().get(1).getGender());
        assertEquals(SALARY, companyResponse.getEmployees().get(1).getSalary());
        assertEquals(company.getId(), companyResponse.getEmployees().get(1).getCompanyId());
    }


    @Test
    void should_return_company_when_to_entity_given_company_request() {
        //Given
        CompanyRequest companyRequest = new CompanyRequest(OOCL);

        //When
        Company company = COMPANY_MAPPER.toEntity(companyRequest);

        //Then
        assertEquals(OOCL, company.getName());
        assertTrue(company.getEmployees().isEmpty());
    }

    @Test
    void should_return_company_responses_when_to_response_given_companies() {
        //Given
        List<Employee> employeeList = new ArrayList<>();

        Integer firstCompanyId = 1;
        Integer secondCompanyId = 2;

        employeeList.add(new Employee(1, JUSTINE, AGE_23, MALE, SALARY, firstCompanyId));
        employeeList.add(new Employee(2, JOHN, AGE_23, MALE, SALARY, firstCompanyId));

        List<Company> companies = asList(
                new Company(firstCompanyId, OOCL, employeeList),
                new Company(secondCompanyId, COSCO, emptyList()));

        //When
        List<CompanyResponse> companyResponses = COMPANY_MAPPER.toResponse(companies);

        //Then
        assertEquals(2, companyResponses.size());

        assertEquals(OOCL, companyResponses.get(0).getName());
        assertEquals(firstCompanyId, companyResponses.get(0).getId());
        assertEquals(employeeList.size(), companyResponses.get(0).getEmployeesNumber());

        assertEquals(JUSTINE, companyResponses.get(0).getEmployees().get(0).getName());
        assertEquals(AGE_23, companyResponses.get(0).getEmployees().get(0).getAge());
        assertEquals(MALE, companyResponses.get(0).getEmployees().get(0).getGender());
        assertEquals(SALARY, companyResponses.get(0).getEmployees().get(0).getSalary());
        assertEquals(firstCompanyId, companyResponses.get(0).getEmployees().get(0).getCompanyId());

        assertEquals(JOHN, companyResponses.get(0).getEmployees().get(1).getName());
        assertEquals(AGE_23, companyResponses.get(0).getEmployees().get(1).getAge());
        assertEquals(MALE, companyResponses.get(0).getEmployees().get(1).getGender());
        assertEquals(SALARY, companyResponses.get(0).getEmployees().get(1).getSalary());
        assertEquals(firstCompanyId, companyResponses.get(0).getEmployees().get(1).getCompanyId());

        assertEquals(COSCO, companyResponses.get(1).getName());
        assertEquals(secondCompanyId, companyResponses.get(1).getId());
        assertEquals(0, companyResponses.get(1).getEmployeesNumber());
    }

    @Test
    void should_return_null_company_response_when_to_response_given_null_company() {
        //Given
        Company company = null;

        //When
        CompanyResponse companyResponse = COMPANY_MAPPER.toResponse(company);

        //Then
        assertNull(companyResponse);
    }

    @Test
    void should_return_null_employee_response_when_to_response_given_company_with_null_employee() {
        //Given
        Company company = new Company();
        company.setEmployees(asList(null, new Employee()));

        //When
        CompanyResponse companyResponse = COMPANY_MAPPER.toResponse(company);

        //Then
        assertNull(companyResponse.getEmployees().get(0));
        assertNotNull(companyResponse.getEmployees().get(1));
    }

    @Test
    void should_return_null_employee_response_when_to_response_given_company_with_null_employee_list() {
        //Given
        Company company = new Company();
        company.setEmployees(null);

        //When
        CompanyResponse companyResponse = COMPANY_MAPPER.toResponse(company);

        //Then
        assertNull(companyResponse.getEmployees());
    }

    @Test
    void should_return_null_company_when_to_entity_given_null_company_response() {
        //Given
        CompanyRequest companyRequest = null;

        //When
        Company company = COMPANY_MAPPER.toEntity(companyRequest);

        //Then
        assertNull(company);
    }

    @Test
    void should_return_null_company_response_list_when_to_response_given_null_company_list() {
        //Given
        List<Company> companies = null;

        //When
        List<CompanyResponse> companyResponses = COMPANY_MAPPER.toResponse(companies);

        //Then
        assertNull(companyResponses);
    }
}
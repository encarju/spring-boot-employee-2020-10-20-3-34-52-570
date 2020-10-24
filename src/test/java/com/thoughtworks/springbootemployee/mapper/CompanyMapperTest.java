package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.TestConstants.AGE_23;
import static com.thoughtworks.springbootemployee.TestConstants.COSCO;
import static com.thoughtworks.springbootemployee.TestConstants.JOHN;
import static com.thoughtworks.springbootemployee.TestConstants.JUSTINE;
import static com.thoughtworks.springbootemployee.TestConstants.MALE;
import static com.thoughtworks.springbootemployee.TestConstants.OOCL;
import static com.thoughtworks.springbootemployee.TestConstants.SALARY;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CompanyMapperTest {

    @Test
    void should_return_company_response_when_to_response_given_company() {
        //Given
        CompanyMapper companyMapper = new CompanyMapper();
        List<Employee> employeeList = new ArrayList<>();
        Company company = new Company(1, OOCL, employeeList);
        employeeList.add(new Employee(1, JUSTINE, AGE_23, MALE, SALARY, company));
        employeeList.add(new Employee(2, JOHN, AGE_23, MALE, SALARY, company));

        //When
        CompanyResponse companyResponse = companyMapper.toResponse(company);

        //Then
        assertEquals(OOCL, companyResponse.getName());
        assertEquals(1, companyResponse.getId());
        assertEquals(2, companyResponse.getEmployeesNumber());

        assertEquals(JUSTINE, companyResponse.getEmployeeResponses().get(0).getName());
        assertEquals(AGE_23, companyResponse.getEmployeeResponses().get(0).getAge());
        assertEquals(MALE, companyResponse.getEmployeeResponses().get(0).getGender());
        assertEquals(SALARY, companyResponse.getEmployeeResponses().get(0).getSalary());
        assertEquals(company.getId(), companyResponse.getEmployeeResponses().get(0).getCompanyId());

        assertEquals(JOHN, companyResponse.getEmployeeResponses().get(1).getName());
        assertEquals(AGE_23, companyResponse.getEmployeeResponses().get(1).getAge());
        assertEquals(MALE, companyResponse.getEmployeeResponses().get(1).getGender());
        assertEquals(SALARY, companyResponse.getEmployeeResponses().get(1).getSalary());
        assertEquals(company.getId(), companyResponse.getEmployeeResponses().get(1).getCompanyId());
    }


    @Test
    void should_return_company_when_to_entity_given_company_request() {
        //Given
        CompanyMapper companyMapper = new CompanyMapper();
        List<EmployeeRequest> employeeRequests = new ArrayList<>();
        employeeRequests.add(new EmployeeRequest(JUSTINE, AGE_23, MALE, SALARY, null));
        employeeRequests.add(new EmployeeRequest(JOHN, AGE_23, MALE, SALARY, null));
        CompanyRequest companyRequest = new CompanyRequest(OOCL, employeeRequests);

        //When
        Company company = companyMapper.toEntity(companyRequest);

        //Then
        assertEquals(OOCL, company.getName());
        assertEquals(2, companyRequest.getEmployeeRequests().size());

        assertEquals(JUSTINE, company.getEmployees().get(0).getName());
        assertEquals(AGE_23, company.getEmployees().get(0).getAge());
        assertEquals(MALE, company.getEmployees().get(0).getGender());
        assertEquals(SALARY, company.getEmployees().get(0).getSalary());

        assertEquals(JOHN, company.getEmployees().get(1).getName());
        assertEquals(AGE_23, company.getEmployees().get(1).getAge());
        assertEquals(MALE, company.getEmployees().get(1).getGender());
        assertEquals(SALARY, company.getEmployees().get(1).getSalary());
    }

    @Test
    void should_return_company_responses_when_to_response_given_companies() {
        //Given
        CompanyMapper companyMapper = new CompanyMapper();
        List<Employee> employeeList = new ArrayList<>();
        Company firstCompany = new Company(1, OOCL, employeeList);
        employeeList.add(new Employee(1, JUSTINE, AGE_23, MALE, SALARY, firstCompany));
        employeeList.add(new Employee(2, JOHN, AGE_23, MALE, SALARY, firstCompany));

        List<Company> companies = asList(firstCompany, new Company(2, COSCO, emptyList()));

        //When
        List<CompanyResponse> companyResponses = companyMapper.toResponse(companies);

        //Then
        assertEquals(2, companyResponses.size());

        assertEquals(OOCL, companyResponses.get(0).getName());
        assertEquals(1, companyResponses.get(0).getId());
        assertEquals(2, companyResponses.get(0).getEmployeesNumber());

        assertEquals(JUSTINE, companyResponses.get(0).getEmployeeResponses().get(0).getName());
        assertEquals(AGE_23, companyResponses.get(0).getEmployeeResponses().get(0).getAge());
        assertEquals(MALE, companyResponses.get(0).getEmployeeResponses().get(0).getGender());
        assertEquals(SALARY, companyResponses.get(0).getEmployeeResponses().get(0).getSalary());
        assertEquals(firstCompany.getId(), companyResponses.get(0).getEmployeeResponses().get(0).getCompanyId());

        assertEquals(JOHN, companyResponses.get(0).getEmployeeResponses().get(1).getName());
        assertEquals(AGE_23, companyResponses.get(0).getEmployeeResponses().get(1).getAge());
        assertEquals(MALE, companyResponses.get(0).getEmployeeResponses().get(1).getGender());
        assertEquals(SALARY, companyResponses.get(0).getEmployeeResponses().get(1).getSalary());
        assertEquals(firstCompany.getId(), companyResponses.get(0).getEmployeeResponses().get(1).getCompanyId());

        assertEquals(COSCO, companyResponses.get(1).getName());
        assertEquals(2, companyResponses.get(1).getId());
        assertEquals(0, companyResponses.get(1).getEmployeesNumber());
    }
}
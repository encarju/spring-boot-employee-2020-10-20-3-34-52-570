package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.TestHelper.AGE_23;
import static com.thoughtworks.springbootemployee.TestHelper.JOHN;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static com.thoughtworks.springbootemployee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeMapperTest {

    @Test
    void should_return_employee_response_when_to_response_given_employee() {
        //Given
        Integer companyId = 10;

        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, JUSTINE, AGE_23, MALE, SALARY, companyId);

        //When
        EmployeeResponse employeeResponse = EMPLOYEE_MAPPER.toResponse(employee);

        //Then
        assertEquals(employeeId, employeeResponse.getId());
        assertEquals(JUSTINE, employeeResponse.getName());
        assertEquals(AGE_23, employeeResponse.getAge());
        assertEquals(MALE, employeeResponse.getGender());
        assertEquals(SALARY, employeeResponse.getSalary());
        assertEquals(companyId, employeeResponse.getCompanyId());
    }

    @Test
    void should_return_employee_when_to_entity_given_employee_request() {
        //Given
        Integer companyId = 10;

        EmployeeRequest employeeRequest = new EmployeeRequest(JUSTINE, AGE_23, MALE, SALARY, companyId);

        //When
        Employee employee = EMPLOYEE_MAPPER.toEntity(employeeRequest);

        //Then
        assertEquals(JUSTINE, employee.getName());
        assertEquals(AGE_23, employee.getAge());
        assertEquals(MALE, employee.getGender());
        assertEquals(SALARY, employee.getSalary());
    }

    @Test
    void should_return_employee_responses_when_to_response_given_employees() {
        //Given
        Integer companyId = 1;

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, JUSTINE, AGE_23, MALE, SALARY, companyId));
        employeeList.add(new Employee(2, JOHN, AGE_23, MALE, SALARY, companyId));

        //When
        List<EmployeeResponse> employeeResponses = EMPLOYEE_MAPPER.toResponse(employeeList);

        //Then
        assertEquals(employeeList.size(), employeeResponses.size());

        assertEquals(1, employeeResponses.get(0).getId());
        assertEquals(JUSTINE, employeeResponses.get(0).getName());
        assertEquals(AGE_23, employeeResponses.get(0).getAge());
        assertEquals(MALE, employeeResponses.get(0).getGender());
        assertEquals(SALARY, employeeResponses.get(0).getSalary());
        assertEquals(companyId, employeeResponses.get(0).getCompanyId());

        assertEquals(2, employeeResponses.get(1).getId());
        assertEquals(JOHN, employeeResponses.get(1).getName());
        assertEquals(AGE_23, employeeResponses.get(1).getAge());
        assertEquals(MALE, employeeResponses.get(1).getGender());
        assertEquals(SALARY, employeeResponses.get(1).getSalary());
        assertEquals(companyId, employeeResponses.get(1).getCompanyId());
    }
}
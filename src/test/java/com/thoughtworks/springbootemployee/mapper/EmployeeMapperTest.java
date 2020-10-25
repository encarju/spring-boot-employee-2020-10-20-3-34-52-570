package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static com.thoughtworks.springbootemployee.TestHelper.AGE_23;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    void should_return_employee_response_when_to_response_given_employee() {
        //Given
        Integer companyId = 10;

        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, JUSTINE, AGE_23, MALE, SALARY, companyId);

        //When
        EmployeeResponse employeeResponse = employeeMapper.toResponse(employee);

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
        Employee employee = employeeMapper.toEntity(employeeRequest);

        //Then
        assertEquals(JUSTINE, employee.getName());
        assertEquals(AGE_23, employee.getAge());
        assertEquals(MALE, employee.getGender());
        assertEquals(SALARY, employee.getSalary());
    }
}
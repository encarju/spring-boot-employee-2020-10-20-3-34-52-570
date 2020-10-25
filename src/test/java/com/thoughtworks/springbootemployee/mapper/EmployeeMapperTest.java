package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static com.thoughtworks.springbootemployee.TestHelper.AGE_23;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.OOCL;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static org.junit.jupiter.api.Assertions.assertEquals;


class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    void should_return_employee_response_when_to_response_given_employee() {
        //Given
        Integer companyId = 10;
        Company company = new Company(companyId, OOCL);

        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, JUSTINE, AGE_23, MALE, SALARY, company);

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
}
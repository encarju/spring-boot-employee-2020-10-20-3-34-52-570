package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.management.openmbean.CompositeDataSupport;
import javax.swing.plaf.basic.BasicTextUI;
import javax.xml.crypto.dsig.Reference;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    @Test
    public void should_return_employees_when_get_all_employee() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        List<Employee> expectedEmployees = asList(new Employee(), new Employee());
        when(repository.findAll()).thenReturn(expectedEmployees);
        EmployeeService service = new EmployeeService(repository);

        //when
        List<Employee> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_employee_when_create_given_one_employee() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        when(repository.save(employee)).thenReturn(employee);

        //when
        Employee actual  = service.create(employee);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_employee_when_get_employee_give_employee_id() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        Integer employeeId = employee.getId();
        when(repository.getById(employeeId)).thenReturn(employee);
        //when
        //then
        Employee actual = service.getById(employeeId);
        assertEquals(1, actual.getId());
    }
}
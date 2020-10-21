package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.management.openmbean.CompositeDataSupport;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
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
}
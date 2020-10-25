package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.thoughtworks.springbootemployee.TestConstants.AGE_23;
import static com.thoughtworks.springbootemployee.TestConstants.BRYAN;
import static com.thoughtworks.springbootemployee.TestConstants.FEMALE;
import static com.thoughtworks.springbootemployee.TestConstants.JUSTINE;
import static com.thoughtworks.springbootemployee.TestConstants.LILY;
import static com.thoughtworks.springbootemployee.TestConstants.MALE;
import static com.thoughtworks.springbootemployee.TestConstants.ONCE;
import static com.thoughtworks.springbootemployee.TestConstants.SALARY;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    private EmployeeRepository repository;
    private EmployeeService service;

    @BeforeEach
    public void setUp() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeService(repository);
    }

    @Test
    public void should_return_employees_when_get_all_employee() {
        //given
        List<Employee> expectedEmployees = asList(new Employee(), new Employee());

        when(repository.findAll()).thenReturn(expectedEmployees);

        //when
        List<Employee> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_employee_when_create_given_one_employee() {
        //given
        Employee employee = new Employee(1, JUSTINE, AGE_23, MALE, SALARY);

        when(repository.save(employee)).thenReturn(employee);

        //when
        Employee actual = service.create(employee);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_employee_when_get_employee_give_employee_id() {
        //given
        Employee employee = new Employee(1, JUSTINE, AGE_23, MALE, SALARY);
        Integer employeeId = employee.getId();

        when(repository.findById(employeeId)).thenReturn(of(employee));

        //when
        Employee actual = service.getById(employeeId);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_employee_when_update_employee_given_employee_id_updated_name() {
        //given
        Employee employee = new Employee(1, JUSTINE, AGE_23, MALE, SALARY);
        Integer employeeId = employee.getId();

        Employee updatedEmployee = new Employee(1, BRYAN, AGE_23, MALE, SALARY);

        when(repository.findById(employeeId)).thenReturn(of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);

        //when
        Employee actual = service.update(employeeId, updatedEmployee);

        //then
        assertEquals(BRYAN, actual.getName());
    }

    @Test
    void should_delete_employee_when_delete_employee_given_employee_id() {
        //given
        Employee employee = new Employee(1, JUSTINE, AGE_23, MALE, SALARY);
        Integer employeeId = employee.getId();

        when(repository.findById(employeeId)).thenReturn(of(employee));

        //when
        service.remove(employeeId);

        //then
        verify(repository, times(ONCE)).delete(employee);
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender_given_employee_gender() {
        //given
        List<Employee> returnedEmployees = asList(
                new Employee(1, JUSTINE, AGE_23, MALE, SALARY));

        when(repository.findByGender(MALE)).thenReturn(returnedEmployees);

        //when
        List<Employee> actual = service.getByGender(MALE);

        //then
        assertSame(returnedEmployees, actual);
        verify(repository, times(ONCE)).findByGender(MALE);
    }

    @Test
    public void should_return_2_employee_when_get_by_page_given_2_page_size() {
        //given
        List<Employee> returnedEmployees = asList(
                new Employee(1, JUSTINE, AGE_23, MALE, SALARY),
                new Employee(2, LILY, AGE_23, FEMALE, SALARY));

        Integer page = 0;
        Integer pageSize = 2;

        Page<Employee> employeePage = mock(Page.class);

        when(repository.findAll(PageRequest.of(page, pageSize))).thenReturn(employeePage);
        when(employeePage.toList()).thenReturn(returnedEmployees);

        //when
        List<Employee> actual = service.getByPage(page, pageSize);

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_return_exception_employee_when_get_employee_given_wrong_employee_id() {
        //given
        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, JUSTINE, AGE_23, MALE, SALARY);

        Integer wrongEmployeeId = employeeId + 1;

        String expectedMessage = format("Employee with ID %d does not exist", employeeId);

        when(repository.findById(wrongEmployeeId)).thenReturn(of(employee));

        //when
        Executable executable = () -> service.getById(employeeId);

        //then
        Exception exception = assertThrows(NoEmployeeFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void should_return_exception_employee_when_update_given_wrong_employee_id() {
        //given
        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, JUSTINE, AGE_23, MALE, SALARY);

        Employee updatedEmployee = new Employee(employeeId, BRYAN, AGE_23, MALE, SALARY);

        Integer wrongEmployeeId = employeeId + 1;

        String expectedMessage = format("Employee with ID %d does not exist", employeeId);

        when(repository.findById(wrongEmployeeId)).thenReturn(of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);

        //when
        Executable executable = () -> service.update(employeeId, updatedEmployee);

        //then
        Exception exception = assertThrows(NoEmployeeFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
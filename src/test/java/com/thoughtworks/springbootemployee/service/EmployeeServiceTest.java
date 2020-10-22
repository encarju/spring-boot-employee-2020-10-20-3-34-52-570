package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    private static final String JUSTINE = "Justine";
    private static final int AGE_2 = 2;
    private static final String MALE = "Male";
    private static final int SALARY = 2000;
    private static final String BRYAN = "Bryan";
    private static final String LILY = "Lily";
    private static final String FEMALE = "Female";

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
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        when(repository.save(employee)).thenReturn(employee);

        //when
        Employee actual = service.create(employee);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_employee_when_get_employee_give_employee_id() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Integer employeeId = employee.getId();
        when(repository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

        //when
        Employee actual = service.getById(employeeId);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_employee_when_update_employee_given_employee_id_updated_name() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Employee updatedEmployee = new Employee(1, BRYAN, AGE_2, MALE, SALARY);
        Integer employeeId = employee.getId();
        when(repository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);

        //when
        Employee actual = service.update(employeeId, updatedEmployee);

        //then
        assertEquals(BRYAN, actual.getName());
    }

    @Test
    void should_delete_employee_when_delete_employee_given_employee_id() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Integer employeeId = employee.getId();
        when(repository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

        //when
        service.remove(employeeId);

        //then
        Mockito.verify(repository, Mockito.times(1)).delete(employee);
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender_given_employee_gender() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        List<Employee> returnedEmployees = asList(
                new Employee(1, JUSTINE, AGE_2, MALE, SALARY));

        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Employee employee2 = new Employee(2, LILY, AGE_2, FEMALE, SALARY);
        when(repository.save(employee)).thenReturn(employee);
        when(repository.save(employee)).thenReturn(employee2);
        String employeeGender = MALE;
        when(repository.findByGender(employeeGender)).thenReturn(returnedEmployees);

        //when
        List<Employee> actual = service.getByGender(employeeGender);

        //then
        assertSame(returnedEmployees, actual);
    }

    @Test
    public void should_return_2_employee_when_get_by_page_given_2_page_size() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        List<Employee> returnedEmployees = asList(
                new Employee(1, JUSTINE, AGE_2, MALE, SALARY),
                new Employee(2, LILY, AGE_2, FEMALE, SALARY));

        Integer page = 0;
        Integer pageSize = 2;
        Page<Employee> employeePage = Mockito.mock(Page.class);
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
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Integer employeeId = employee.getId();
        String expectedMessage = "No Employee Found in the List";
        when(repository.findById(2)).thenReturn(java.util.Optional.of(employee));

        //when
        Executable executable = () -> service.getById(employeeId);

        //then
        Exception exception = assertThrows(NoEmployeeFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void should_return_exception_employee_when_update_given_wrong_employee_id() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, JUSTINE, AGE_2, MALE, SALARY);
        Employee updatedEmployee = new Employee(1, BRYAN, AGE_2, MALE, SALARY);
        Integer employeeId = employee.getId();
        Integer wrongEmployeeId = 2;
        String expectedMessage = "No Employee Found in the List";
        when(repository.findById(wrongEmployeeId)).thenReturn(java.util.Optional.of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);

        //when
        Executable executable = () -> service.update(employeeId, updatedEmployee);

        //then
        Exception exception = assertThrows(NoEmployeeFoundException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
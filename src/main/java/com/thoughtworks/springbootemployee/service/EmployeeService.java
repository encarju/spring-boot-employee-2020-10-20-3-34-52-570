package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee getById(Integer employeeId) {
        return repository.getById(employeeId);
    }

    public Employee update(Integer employeeId, Employee updatedEmployee) {
        return repository.update(employeeId,updatedEmployee);
    }

    public void remove(Integer employeeId) {
        repository.remove(employeeId);
    }

    public List<Employee> getByGender(String employeeGender) {
        return repository.getByGender(employeeGender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return null;
    }
}

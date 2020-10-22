package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
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
        return repository.findById(employeeId).orElse(null);
    }

    public Employee update(Integer employeeId, Employee updatedEmployee) {
        return repository.findById(employeeId).map(employee -> repository.save(updatedEmployee)).orElse(null);
    }

    public void remove(Integer employeeId) {
        repository.findById(employeeId).ifPresent(repository::delete);
    }

    public List<Employee> getByGender(String employeeGender) {
        return repository.findByGender(employeeGender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return repository.findAll(PageRequest.of(page, pageSize)).toList();
    }
}

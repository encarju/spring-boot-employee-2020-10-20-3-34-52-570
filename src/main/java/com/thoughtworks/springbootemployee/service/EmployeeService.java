package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee employee) {
        Integer companyId = employee.getCompanyId();
        if (nonNull(companyId)) {
            companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException(companyId));
        }

        return employeeRepository.save(employee);
    }

    public Employee getById(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Employee update(Integer employeeId, Employee updatedEmployee) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    Integer companyId = updatedEmployee.getCompanyId();
                    if (nonNull(companyId)) {
                        companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));
                    }

                    updatedEmployee.setId(employeeId);

                    return employeeRepository.save(updatedEmployee);
                })
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public void remove(Integer employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException(employeeId);
        }

        employeeRepository.delete(optionalEmployee.get());
    }

    public List<Employee> getByGender(String employeeGender) {
        return employeeRepository.findByGender(employeeGender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(of(page, pageSize)).toList();
    }
}

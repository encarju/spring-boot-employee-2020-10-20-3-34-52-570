package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private final List<Employee> employees = new ArrayList<>();

    //Getting all employees
    @GetMapping
    public List<Employee> getAll(){
        return employees;
    }

    //Add an employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee){
        employees.add(employee);
        return employee;
    }

    //Get specific employee
    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Integer employeeId){
        return employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst().orElse(null);

    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId,@RequestBody Employee employeeUpdate){
        employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(employeeUpdate);
        });
        return employeeUpdate;
    }

}

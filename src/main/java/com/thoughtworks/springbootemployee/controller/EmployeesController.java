package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private final List<Employee> employees = new ArrayList<>();

    private EmployeeService employeeService = new EmployeeService(new EmployeeRepository());

    public EmployeesController() {
    }

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Getting all employees
    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    //Add an employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    //Get specific employee
    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return employeeService.getById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeUpdate) {
        return employeeService.update(employeeId,employeeUpdate);
    }

    @DeleteMapping("/{employeeId}")
    public void removeEmployee(@PathVariable Integer employeeId) {
        employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employees::remove);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employees.stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}

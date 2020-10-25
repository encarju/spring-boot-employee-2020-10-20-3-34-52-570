package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.thoughtworks.springbootemployee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return EMPLOYEE_MAPPER.toResponse(employeeService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.create(EMPLOYEE_MAPPER.toEntity(employeeRequest));

        return EMPLOYEE_MAPPER.toResponse(employee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable Integer employeeId) {
        return EMPLOYEE_MAPPER.toResponse(employeeService.getById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = EMPLOYEE_MAPPER.toEntity(employeeRequest);

        return EMPLOYEE_MAPPER.toResponse(employeeService.update(employeeId, employee));
    }

    @DeleteMapping("/{employeeId}")
    public void removeEmployee(@PathVariable Integer employeeId) {
        employeeService.remove(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam String gender) {
        return EMPLOYEE_MAPPER.toResponse(employeeService.getByGender(gender));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return EMPLOYEE_MAPPER.toResponse(employeeService.getByPage(page, pageSize));
    }
}

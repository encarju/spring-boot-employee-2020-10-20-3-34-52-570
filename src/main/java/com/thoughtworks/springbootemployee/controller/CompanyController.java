package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
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

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
        companyMapper = new CompanyMapper();
        employeeMapper = new EmployeeMapper();
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyMapper.toResponse(companyService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);

        return companyMapper.toResponse(companyService.create(company));
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable Integer companyId) {
        return companyMapper.toResponse(companyService.getById(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployees(@PathVariable Integer companyId) {
        return employeeMapper.toResponse(companyService.getCompanyEmployees(companyId));
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Integer companyId, @RequestBody CompanyRequest updatedCompanyRequest) {
        Company updatedCompany = companyMapper.toEntity(updatedCompanyRequest);

        return companyMapper.toResponse(companyService.update(companyId, updatedCompany));
    }

    @DeleteMapping("/{companyId}")
    public CompanyResponse removeCompanyEmployees(@PathVariable Integer companyId) {
        return companyMapper.toResponse(companyService.removeCompanyEmployees(companyId));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyMapper.toResponse(companyService.getByPage(page, pageSize));
    }
}
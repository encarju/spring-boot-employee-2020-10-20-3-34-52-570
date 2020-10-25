package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
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

import static com.thoughtworks.springbootemployee.mapper.CompanyMapper.COMPANY_MAPPER;
import static com.thoughtworks.springbootemployee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        return COMPANY_MAPPER.toResponse(companyService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = COMPANY_MAPPER.toEntity(companyRequest);

        return COMPANY_MAPPER.toResponse(companyService.create(company));
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable Integer companyId) {
        return COMPANY_MAPPER.toResponse(companyService.getById(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployees(@PathVariable Integer companyId) {
        return EMPLOYEE_MAPPER.toResponse(companyService.getCompanyEmployees(companyId));
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Integer companyId, @RequestBody CompanyRequest updatedCompanyRequest) {
        Company updatedCompany = COMPANY_MAPPER.toEntity(updatedCompanyRequest);

        return COMPANY_MAPPER.toResponse(companyService.update(companyId, updatedCompany));
    }

    @DeleteMapping("/{companyId}")
    public CompanyResponse removeCompanyEmployees(@PathVariable Integer companyId) {
        return COMPANY_MAPPER.toResponse(companyService.removeCompanyEmployees(companyId));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return COMPANY_MAPPER.toResponse(companyService.getByPage(page, pageSize));
    }
}
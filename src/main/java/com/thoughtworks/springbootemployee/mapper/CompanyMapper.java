package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompanyMapper {
    private final EmployeeMapper employeeMapper;

    public CompanyMapper() {
        employeeMapper = new EmployeeMapper();
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        copyProperties(company, companyResponse);

        List<EmployeeResponse> employeeResponses = company.getEmployees()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(toList());
        companyResponse.setEmployees(employeeResponses);

        return companyResponse;
    }

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        copyProperties(companyRequest, company);

        List<Employee> employees = companyRequest.getEmployees()
                .stream()
                .map(employeeMapper::toEntity)
                .collect(toList());
        company.setEmployees(employees);

        return company;

    }

    public List<CompanyResponse> toResponse(List<Company> companies) {
        return companies.stream()
                .map(this::toResponse)
                .collect(toList());
    }
}

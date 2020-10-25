package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.thoughtworks.springbootemployee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        copyProperties(company, companyResponse);

        List<EmployeeResponse> employeeResponses = company.getEmployees()
                .stream()
                .map(EMPLOYEE_MAPPER::toResponse)
                .collect(toList());
        companyResponse.setEmployees(employeeResponses);

        return companyResponse;
    }

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        copyProperties(companyRequest, company);

        return company;

    }

    public List<CompanyResponse> toResponse(List<Company> companies) {
        return companies.stream()
                .map(this::toResponse)
                .collect(toList());
    }
}

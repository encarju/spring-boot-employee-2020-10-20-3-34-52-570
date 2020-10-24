package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
        EmployeeMapper employeeMapper = new EmployeeMapper();

        CompanyResponse companyResponse = new CompanyResponse();
        copyProperties(company, companyResponse);

        List<EmployeeResponse> employeeResponses = company.getEmployees()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(toList());
        companyResponse.setEmployeeResponses(employeeResponses);

        return companyResponse;
    }

    public Company toEntity(CompanyRequest companyRequest) {
        return null;
    }
}

package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.thoughtworks.springbootemployee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Mapper
public interface CompanyMapper {

    CompanyMapper COMPANY_MAPPER = Mappers.getMapper(CompanyMapper.class);

    default CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        copyProperties(company, companyResponse);

        List<EmployeeResponse> employeeResponses = company.getEmployees()
                .stream()
                .map(EMPLOYEE_MAPPER::toResponse)
                .collect(toList());
        companyResponse.setEmployees(employeeResponses);

        return companyResponse;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Company toEntity(CompanyRequest companyRequest);

    default List<CompanyResponse> toResponse(List<Company> companies) {
        return companies.stream()
                .map(this::toResponse)
                .collect(toList());
    }
}

package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompanyMapper {

    CompanyMapper COMPANY_MAPPER = Mappers.getMapper(CompanyMapper.class);

    @Mapping(source = "company.employees", target = "employees")
    CompanyResponse toResponse(Company company);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Company toEntity(CompanyRequest companyRequest);

    List<CompanyResponse> toResponse(List<Company> companies);
}

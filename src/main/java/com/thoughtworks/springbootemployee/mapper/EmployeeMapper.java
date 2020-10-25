package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = getMapper(EmployeeMapper.class);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequest employeeRequest);

    default List<EmployeeResponse> toResponse(List<Employee> employeeList) {
        return employeeList.stream()
                .map(this::toResponse)
                .collect(toList());
    }
}

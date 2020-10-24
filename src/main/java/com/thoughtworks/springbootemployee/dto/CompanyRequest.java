package com.thoughtworks.springbootemployee.dto;

import java.util.List;


public class CompanyRequest {

    private String name;
    private List<EmployeeRequest> employees;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, List<EmployeeRequest> employees) {
        this.name = name;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeRequest> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeRequest> employees) {
        this.employees = employees;
    }
}

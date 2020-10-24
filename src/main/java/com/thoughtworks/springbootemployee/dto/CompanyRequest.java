package com.thoughtworks.springbootemployee.dto;

import java.util.List;


public class CompanyRequest {

    private String name;
    private List<EmployeeRequest> employeeRequests;

    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeRequest> getEmployeeRequests() {
        return employeeRequests;
    }

    public void setEmployeeRequests(List<EmployeeRequest> employeeRequests) {
        this.employeeRequests = employeeRequests;
    }
}

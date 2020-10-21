package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

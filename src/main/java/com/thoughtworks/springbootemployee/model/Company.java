package com.thoughtworks.springbootemployee.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Company {
    @Id
    private Integer companyId;
    private String companyName;

    @OneToMany(mappedBy = "company_id", fetch = FetchType.LAZY)
    private List<Employee> employees;

    public Company(Integer companyId, String companyName, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Company() {

    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employees.size();
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

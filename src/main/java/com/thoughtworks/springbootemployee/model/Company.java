package com.thoughtworks.springbootemployee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Company {
    public static final int ZERO = 0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "companyId", fetch = LAZY)
    private List<Employee> employees;

    public Company(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Company(Integer id, String name) {
        this(id, name, emptyList());
    }

    public Company(String name, List<Employee> employees) {
        this(null, name, employees);
    }

    public Company(String name) {
        this(null, name);
    }

    public Company() {
        employees = emptyList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getEmployeesNumber() {
        return (isNull(employees)) ? ZERO : employees.size();
    }
}

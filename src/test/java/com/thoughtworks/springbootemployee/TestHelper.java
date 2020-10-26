package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public final class TestHelper {

    public static final String ALIBABA = "Alibaba";
    public static final String ALIBABAS = "Alibabas";
    public static final String COSCO = "COSCO";
    public static final String OOCL = "OOCL";

    public static final int AGE_23 = 23;
    public static final int SALARY = 233;

    public static final int NONE = 0;
    public static final int ONCE = 1;

    public static final String BRYAN = "Bryan";
    public static final String JOHN = "John";
    public static final String JUSTINE = "Justine";
    public static final String LILY = "Lily";

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    public static final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";
    public static final String FORMATTED_COMPANY_EXCEPTION_MESSAGE = "Company with ID %d does not exist";
    public static final String FORMATTED_EMPLOYEE_EXCEPTION_MESSAGE = "Employee with ID %d does not exist";
    public static final String FORMATTED_COMPANY_ASSOCIATION_EXCEPTION_MESSAGE = "Unable to associate with non-existing company of ID %d";

    private TestHelper() {
    }

    public static List<Employee> generateDummyEmployees(int count) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            employees.add(new Employee());
        }

        return employees;
    }
}

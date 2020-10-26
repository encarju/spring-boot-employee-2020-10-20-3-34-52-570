package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.TestHelper.AGE_23;
import static com.thoughtworks.springbootemployee.TestHelper.COSCO;
import static com.thoughtworks.springbootemployee.TestHelper.FORMATTED_COMPANY_EXCEPTION_MESSAGE;
import static com.thoughtworks.springbootemployee.TestHelper.JOHN;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.NOT_FOUND_ERROR;
import static com.thoughtworks.springbootemployee.TestHelper.OOCL;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_get_all_company_when_get_all() throws Exception {
        // given
        Company company = new Company(OOCL);
        company.setEmployees(emptyList());
        companyRepository.save(company);

        // when
        // then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(OOCL))
                .andExpect(jsonPath("$[0].employees").isArray());
    }

    @Test
    public void should_create_company_when_create_given_company_request() throws Exception {
        // given
        String employeeJson = "{\n" +
                "    \"name\" : \"" + OOCL + "\",\n" +
                "    \"employees\" : [\n" +
                "        {\n" +
                "            \"name\" : \"" + JUSTINE + "\",\n" +
                "            \"age\" : " + AGE_23 + ",\n" +
                "            \"gender\" : \"" + MALE + "\",\n" +
                "            \"salary\" : " + SALARY + "\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // when
        // then
        mockMvc.perform(post("/companies")
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(OOCL))
                .andExpect(jsonPath("$.employees.length()").value(0))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    public void should_get_company_when_get_company_given_company_id() throws Exception {
        // given
        Company company = new Company(OOCL, emptyList());
        Integer returnedCompanyId = companyRepository.save(company).getId();

        // when
        // then
        mockMvc.perform(get(format("/companies/%d", returnedCompanyId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(OOCL))
                .andExpect(jsonPath("$.employees").isArray());
    }

    @Test
    public void should_get_employees_when_get_employees_given_company_id() throws Exception {
        // given
        Company company = new Company(OOCL, emptyList());
        Integer returnedCompanyId = companyRepository.save(company).getId();

        employeeRepository.save(new Employee(JUSTINE, AGE_23, MALE, SALARY, returnedCompanyId));
        employeeRepository.save(new Employee(JOHN, AGE_23, MALE, SALARY, returnedCompanyId));

        // when
        // then
        mockMvc.perform(get(format("/companies/%d/employees", returnedCompanyId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(JUSTINE))
                .andExpect(jsonPath("$[0].age").value(AGE_23))
                .andExpect(jsonPath("$[0].gender").value(MALE))
                .andExpect(jsonPath("$[0].salary").value(SALARY))
                .andExpect(jsonPath("$[0].companyId").value(returnedCompanyId))
                .andExpect(jsonPath("$[1].name").value(JOHN))
                .andExpect(jsonPath("$[1].age").value(AGE_23))
                .andExpect(jsonPath("$[1].gender").value(MALE))
                .andExpect(jsonPath("$[1].salary").value(SALARY))
                .andExpect(jsonPath("$[1].companyId").value(returnedCompanyId));
    }

    @Test
    public void should_return_not_found_status_when_get_given_wrong_company_id() throws Exception {
        // given
        Integer wrongCompanyId = 0;

        // when
        // then
        mockMvc.perform(get(format("/companies/%d", wrongCompanyId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(NOT_FOUND_ERROR))
                .andExpect(jsonPath("$.errorMessage")
                        .value(format(FORMATTED_COMPANY_EXCEPTION_MESSAGE, wrongCompanyId)))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));
    }

    @Test
    public void should_delete_company_when_delete_given_company_id() throws Exception {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(JOHN, AGE_23, MALE, SALARY));

        Company company = new Company(OOCL, employees);
        Integer returnedCompanyId = companyRepository.save(company).getId();

        // when
        // then
        mockMvc.perform(delete(format("/companies/%d", returnedCompanyId)))
                .andExpect(status().isOk());

        mockMvc.perform(get(format("/companies/%d", returnedCompanyId)))
                .andExpect(jsonPath("$.id").value(returnedCompanyId))
                .andExpect(jsonPath("$.name").value(OOCL))
                .andExpect(jsonPath("$.employeesNumber").value(0))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    public void should_return_not_found_status_when_delete_given_wrong_company_id() throws Exception {
        // given
        Integer wrongCompanyId = 0;

        // when
        // then
        mockMvc.perform(delete(format("/companies/%d", wrongCompanyId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(NOT_FOUND_ERROR))
                .andExpect(jsonPath("$.errorMessage")
                        .value(format(FORMATTED_COMPANY_EXCEPTION_MESSAGE, wrongCompanyId)))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));
    }

    @Test
    public void should_get_companies_when_get_companies_given_page_and_page_size() throws Exception {
        // given
        Integer page = 1;
        Integer pageSize = 2;

        companyRepository.save(new Company("Oracle", emptyList()));
        companyRepository.save(new Company("Google", emptyList()));
        companyRepository.save(new Company(COSCO, emptyList()));

        Company company = new Company(OOCL, emptyList());
        Integer returnedCompanyId = companyRepository.save(company).getId();

        // when
        // then
        mockMvc.perform(get(format("/companies?page=%d&pageSize=%d", page, pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(COSCO))
                .andExpect(jsonPath("$[1].id").value(returnedCompanyId))
                .andExpect(jsonPath("$[1].name").value(OOCL));
    }

    @Test
    public void should_update_company_when_update_given_company_id_and_company_request() throws Exception {
        // given
        Company company = new Company(OOCL);
        Integer returnedCompanyId = companyRepository.save(company).getId();

        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY, returnedCompanyId);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        String employeeJson = "{\n" +
                "    \"name\" : \"" + COSCO + "\",\n" +
                "    \"employees\" : [\n" +
                "        {\n" +
                "            \"id\" : \"" + returnedEmployeeId + "\",\n" +
                "            \"name\" : \"" + JOHN + "\",\n" +
                "            \"age\" : " + AGE_23 + ",\n" +
                "            \"gender\" : \"" + MALE + "\",\n" +
                "            \"salary\" : " + SALARY + "\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // when
        // then
        mockMvc.perform(put(format("/companies/%d", returnedCompanyId))
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(returnedCompanyId))
                .andExpect(jsonPath("$.name").value(COSCO))
                .andExpect(jsonPath("$.employees.length()").value(1))
                .andExpect(jsonPath("$.employees[0].id").value(returnedEmployeeId))
                .andExpect(jsonPath("$.employees[0].name").value(JUSTINE))
                .andExpect(jsonPath("$.employees[0].age").value(AGE_23))
                .andExpect(jsonPath("$.employees[0].gender").value(MALE))
                .andExpect(jsonPath("$.employees[0].salary").value(SALARY))
                .andExpect(jsonPath("$.employees[0].companyId").value(returnedCompanyId));
    }

    @Test
    public void should_return_not_found_status_when_update_given_wrong_company_id_and_company_request() throws Exception {
        // given
        Integer wrongCompanyId = 0;

        String employeeJson = "{\n" +
                "    \"name\" : \"" + COSCO + "\",\n" +
                "    \"employees\" : [\n" +
                "        {\n" +
                "            \"name\" : \"" + JOHN + "\",\n" +
                "            \"age\" : " + AGE_23 + ",\n" +
                "            \"gender\" : \"" + MALE + "\",\n" +
                "            \"salary\" : " + SALARY + "\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // when
        // then
        mockMvc.perform(put(format("/companies/%d", wrongCompanyId))
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(NOT_FOUND_ERROR))
                .andExpect(jsonPath("$.errorMessage")
                        .value(format(FORMATTED_COMPANY_EXCEPTION_MESSAGE, wrongCompanyId)))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));
    }
}

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
import static com.thoughtworks.springbootemployee.TestHelper.FEMALE;
import static com.thoughtworks.springbootemployee.TestHelper.JUSTINE;
import static com.thoughtworks.springbootemployee.TestHelper.MALE;
import static com.thoughtworks.springbootemployee.TestHelper.OOCL;
import static com.thoughtworks.springbootemployee.TestHelper.SALARY;
import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void should_get_all_employees_when_get_all() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        employeeRepository.save(employee);

        // when
        // then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(JUSTINE))
                .andExpect(jsonPath("$[0].age").value(AGE_23))
                .andExpect(jsonPath("$[0].gender").value(MALE))
                .andExpect(jsonPath("$[0].salary").value(SALARY));
    }

    @Test
    public void should_create_employee_when_create_given_employee_request() throws Exception {
        // given
        String employeeJson = "{\n" +
                "            \"name\": \"" + JUSTINE + "\",\n" +
                "            \"age\": " + AGE_23 + ",\n" +
                "            \"gender\": \"" + MALE + "\",\n" +
                "            \"salary\": " + SALARY + ",\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(post("/employees")
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(JUSTINE))
                .andExpect(jsonPath("$.age").value(AGE_23))
                .andExpect(jsonPath("$.gender").value(MALE))
                .andExpect(jsonPath("$.salary").value(SALARY))
                .andExpect(jsonPath("$.companyId").isEmpty());
    }

    @Test
    public void should_create_employee_when_create_given_employee_request_with_company_id() throws Exception {
        // given
        Company company = new Company(OOCL);
        Integer returnedCompanyId = companyRepository.save(company).getId();

        String employeeJson = "{\n" +
                "            \"name\": \"" + JUSTINE + "\",\n" +
                "            \"age\": " + AGE_23 + ",\n" +
                "            \"gender\": \"" + MALE + "\",\n" +
                "            \"salary\": " + SALARY + ",\n" +
                "            \"companyId\": " + returnedCompanyId + "\n" +
                "        }";

        // when
        // then
        mockMvc.perform(post("/employees")
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(JUSTINE))
                .andExpect(jsonPath("$.age").value(AGE_23))
                .andExpect(jsonPath("$.gender").value(MALE))
                .andExpect(jsonPath("$.salary").value(SALARY))
                .andExpect(jsonPath("$.companyId").value(returnedCompanyId));
    }

    @Test
    public void should_get_employee_when_get_given_employee_id() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        // when
        // then
        mockMvc.perform(get(format("/employees/%d", returnedEmployeeId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(JUSTINE))
                .andExpect(jsonPath("$.age").value(AGE_23))
                .andExpect(jsonPath("$.gender").value(MALE))
                .andExpect(jsonPath("$.salary").value(SALARY));
    }

    @Test
    public void should_update_employee_when_update_given_employee_id_and_updated_employee_request() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, 22, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        String employeeJson = "{\n" +
                "            \"name\": \"" + JUSTINE + "\",\n" +
                "            \"age\": " + AGE_23 + ",\n" +
                "            \"gender\": \"" + MALE + "\",\n" +
                "            \"salary\": " + SALARY + ",\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(put(format("/employees/%d", returnedEmployeeId))
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(returnedEmployeeId))
                .andExpect(jsonPath("$.name").value(JUSTINE))
                .andExpect(jsonPath("$.age").value(AGE_23))
                .andExpect(jsonPath("$.gender").value(MALE))
                .andExpect(jsonPath("$.salary").value(SALARY));
    }

    @Test
    public void should_update_employee_when_update_given_employee_id_and_updated_employee_request_with_company_id() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        Company company = new Company(OOCL);
        Integer returnedCompanyId = companyRepository.save(company).getId();

        String employeeJson = "{\n" +
                "            \"name\": \"" + JUSTINE + "\",\n" +
                "            \"age\": " + AGE_23 + ",\n" +
                "            \"gender\": \"" + MALE + "\",\n" +
                "            \"salary\": " + SALARY + ",\n" +
                "            \"companyId\": " + returnedCompanyId + "\n" +
                "        }";

        // when
        // then
        mockMvc.perform(put(format("/employees/%d", returnedEmployeeId))
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(returnedEmployeeId))
                .andExpect(jsonPath("$.name").value(JUSTINE))
                .andExpect(jsonPath("$.age").value(AGE_23))
                .andExpect(jsonPath("$.gender").value(MALE))
                .andExpect(jsonPath("$.salary").value(SALARY))
                .andExpect(jsonPath("$.companyId").value(returnedCompanyId));
    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        // when
        // then
        mockMvc.perform(delete(format("/employees/%d", returnedEmployeeId)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_employees_when_get_given_gender() throws Exception {
        // given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(JUSTINE, AGE_23, MALE, SALARY));
        employeeList.add(new Employee(JUSTINE, AGE_23, MALE, SALARY));
        employeeList.add(new Employee(JUSTINE, AGE_23, FEMALE, SALARY));
        employeeRepository.saveAll(employeeList);

        // when
        // then
        mockMvc.perform(get(format("/employees?gender=%s", MALE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void should_get_employees_when_get_given_page() throws Exception {
        // given
        Integer page = 1;
        Integer pageSize = 2;

        employeeRepository.save(new Employee(JUSTINE, AGE_23, MALE, SALARY));
        employeeRepository.save(new Employee(JUSTINE, AGE_23, MALE, SALARY));

        Employee thirdEmployee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        Integer thirdEmployeeId = employeeRepository.save(thirdEmployee).getId();

        // when
        // then
        mockMvc.perform(get(format("/employees?page=%d&pageSize=%d", page, pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(thirdEmployeeId));
    }

    @Test
    public void should_return_exception_when_get_given_wrong_employee_id() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, AGE_23, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        Integer wrongEmployeeId = returnedEmployeeId + 1;

        // when
        // then
        mockMvc.perform(get(format("/employees/%d", wrongEmployeeId)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_exception_when_update_given_wrong_employee_id_and_updated_employee_request() throws Exception {
        // given
        Employee employee = new Employee(JUSTINE, 22, MALE, SALARY);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        Integer wrongEmployeeId = returnedEmployeeId + 1;

        String employeeJson = "{\n" +
                "            \"id\": 4,\n" +
                "            \"name\": \"" + JUSTINE + "\",\n" +
                "            \"age\": " + AGE_23 + ",\n" +
                "            \"gender\": \"" + MALE + "\",\n" +
                "            \"salary\": " + SALARY + ",\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(put(format("/employees/%d", wrongEmployeeId))
                .contentType(APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isNotFound());
    }
}

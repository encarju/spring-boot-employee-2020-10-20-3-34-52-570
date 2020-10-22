package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void should_get_all_employees_when_get_all() throws Exception {
        // given
        Employee employee = new Employee(1, "Justine", 23, "Male", 2000000);
        employeeRepository.save(employee);

        // when
        // then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Justine"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(2000000));
    }

    @Test
    public void should_create_employee_when_create_given_employee_request() throws Exception {
        // given
        String employeeJson = "{\n" +
                "            \"name\": \"Justine\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 2000000,\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Justine"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2000000));
    }

    @Test
    public void should_get_employee_when_get_given_employee_id() throws Exception {
        // given
        Integer id = 2;
        Employee employee = new Employee(id, "Justine", 23, "Male", 2000000);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        // when
        // then
        mockMvc.perform(get("/employees/" + returnedEmployeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Justine"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2000000));
    }

    @Test
    public void should_update_employee_when_update_given_employee_id_and_updated_employee_request() throws Exception {
        // given
        Integer id = 4;
        Employee employee = new Employee(id, "Justine", 22, "Male", 2000000);
        Integer returnedEmployeeId = employeeRepository.save(employee).getId();

        String employeeJson = "{\n" +
                "            \"id\": 4,\n" +
                "            \"name\": \"Justine\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 2000000,\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(put(String.format("/employees/%d", returnedEmployeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Justine"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2000000));
    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() throws Exception {
        // given
        Integer id = 4;
        Employee employee = new Employee(id, "Justine", 22, "Male", 2000000);
        employeeRepository.save(employee);

        // when
        // then
        mockMvc.perform(delete(String.format("/employees/%d", id)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_employees_when_get_given_gender() throws Exception {
        // given
        String gender = "Male";
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Justine", 23, "Male", 2000000));
        employeeList.add(new Employee(2, "Justine", 23, "Male", 2000000));
        employeeList.add(new Employee(3, "Justine", 23, "Female", 2000000));
        employeeRepository.saveAll(employeeList);

        // when
        // then
        mockMvc.perform(get("/employees?gender=" + gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void should_get_employees_when_get_given_page() throws Exception {
        // given
        Integer page = 1;
        Integer pageSize = 2;
        employeeRepository
                .save(new Employee(1, "Justine", 23, "Male", 2000000))
                .getId();
        employeeRepository.save(new Employee(2, "Justine", 23, "Male", 2000000))
                .getId();
        Integer thirdEmployeeId = employeeRepository
                .save(new Employee(2, "Justine", 23, "Male", 2000000))
                .getId();

        // when
        // then
        mockMvc.perform(get(String.format("/employees?page=%d&pageSize=%d", page, pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(thirdEmployeeId));
    }
}

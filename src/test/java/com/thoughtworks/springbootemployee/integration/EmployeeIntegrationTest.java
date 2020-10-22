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
        Integer id = 1;
        Employee employee = new Employee(id, "Justine", 23, "Male", 2000000);
        employeeRepository.save(employee);

        // when
        // then
        mockMvc.perform(get("/employees/" + id))
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
        Integer id = 1;
        Employee employee = new Employee(id, "Justine", 22, "Male", 2000000);
        employeeRepository.save(employee);

        String employeeJson = "{\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Justine\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 2000000,\n" +
                "            \"companyId\": null\n" +
                "        }";

        // when
        // then
        mockMvc.perform(put(String.format("/employees/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Justine"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(2000000));
    }
}

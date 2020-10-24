package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    private static final String JUSTINE = "Justine";
    private static final int AGE_23 = 23;
    private static final String MALE = "Male";
    private static final int SALARY = 2000000;
    private static final String OOCL = "OOCL";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_get_all_company_when_get_all() throws Exception {
        // given
        Company company = new Company("OOCL", emptyList());
        companyRepository.save(company);

        // when
        // then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("OOCL"))
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
                .andExpect(jsonPath("$.employees.length()").value(1))
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value(JUSTINE))
                .andExpect(jsonPath("$.employees[0].age").value(AGE_23))
                .andExpect(jsonPath("$.employees[0].gender").value(MALE))
                .andExpect(jsonPath("$.employees[0].salary").value(SALARY))
                .andExpect(jsonPath("$.employees[0].companyId").isNumber());
    }

    @Test
    public void should_get_company_when_get_company_given_company_id() throws Exception {
        // given
        Company company = new Company("OOCL", emptyList());
        Integer returnedCompanyId = companyRepository.save(company).getId();

        // when
        // then
        mockMvc.perform(get(format("/companies/%d", returnedCompanyId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("OOCL"))
                .andExpect(jsonPath("$.employees").isArray());
    }
}

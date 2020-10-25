package com.thoughtworks.springbootemployee;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.thoughtworks.springbootemployee.SpringBootEmployeeApplication.main;

@SpringBootTest
class SpringBootEmployeeApplicationTests {

    @Test
    void contextLoads() {
        main(new String[]{""});
    }

}

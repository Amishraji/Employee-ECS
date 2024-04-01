package com.pranshu.crudproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.pranshu.crudproject.model.Employee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CrudProjectApplicationTests {

    @Test
    void contextLoads() {
        // Create an Employee object
        Employee employee = new Employee();
        
        // Assert that the employee object is not null
        assertNotNull(employee, "Employee object should not be null");
        
        // Add more assertions as needed
    }
}

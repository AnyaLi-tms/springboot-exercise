package com.oocl.training.integration;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import com.oocl.training.repository.EmployeeDBRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeTest {

    @Autowired
    private MockMvc client;

    @Autowired
    EmployeeDBRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setup() throws Exception {
//        employeeRepository.save(new Employee("John Smith", 32, Gender.MALE, 5000.0));
//        employeeRepository.save(new Employee("Jane Johnson", 28, Gender.FEMALE, 6000.0));
//        employeeRepository.save(new Employee("David Williams", 35, Gender.MALE, 5500.0));
//        employeeRepository.save(new Employee("Emily Brown", 23, Gender.FEMALE, 4500.0));
//        employeeRepository.save(new Employee("Michael Jones", 40, Gender.MALE, 7000.0));
    }

//    @AfterAll
//    public void teardown() throws Exception {
//        employeeRepository.deleteAll();
//    }

    @Test
    public void should_return_employee_when_create_employee() throws Exception {
        // Given
        Employee givenEmployee = new Employee("Bob Black", 22, Gender.MALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isCreated());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(givenEmployee.getName()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(givenEmployee.getAge()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(givenEmployee.getGender().name()));
//        perform.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(givenEmployee.getSalary()));
    }

    @Test
    public void should_throw_exception_when_create_employee_age_less_18() throws Exception {
        // Given
        Employee givenEmployee = new Employee("Bob Black", 2, Gender.MALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee age must be between 18 and 65"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_throw_exception_when_create_employee_age_greater_65() throws Exception {
        // Given
        Employee givenEmployee = new Employee("Bob Black", 82, Gender.MALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee age must be between 18 and 65"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_throw_exception_when_create_employee_age_greater_30_and_salary_less_20000() throws Exception {
        // Given
        Employee givenEmployee = new Employee("Bob Black", 33, Gender.MALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employees over 30 ages must have a salary more than 20000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_return_all_employees_when_get_all_employees_exist() throws Exception {
        // Given
        List<Employee> givenEmployees = employeeRepository.get();

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees"));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(givenEmployees.getFirst().getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(givenEmployees.getFirst().getName()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(givenEmployees.getFirst().getAge()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].gender").value(givenEmployees.getFirst().getGender().name()));
//        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].salary").value(givenEmployees.getFirst().getSalary()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(givenEmployees.get(1).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").value(givenEmployees.get(2).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[3].id").value(givenEmployees.get(3).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[4].id").value(givenEmployees.get(4).getId()));
    }

    @Test
    public void should_set_active_false_when_delete_employee() throws Exception {
        // Given
        Integer givenId = 250;

        // When
        ResultActions perform_delete = client.perform(MockMvcRequestBuilders.delete("/employees/{id}", givenId));
        ResultActions perform_get = client.perform(MockMvcRequestBuilders.get("/employees/{id}", givenId));

        // Then
        perform_delete.andExpect(MockMvcResultMatchers.status().isNoContent());
        perform_get.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }

    @Test
    public void should_throw_exception_when_delete_employee_not_exist() throws Exception {
        // Given
        Integer givenId = 100;

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.delete("/employees/{id}", givenId));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee with id " + givenId + " does not exist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_update_employee_when_update_employee() throws Exception {
        // Given
        Employee employee = new Employee("John Mark", 32, Gender.MALE, 5000.0);
        Employee exsitingEmployee = employeeRepository.save(employee);
        Integer givenId = exsitingEmployee.getId();

        Employee givenEmployee = new Employee("John Mark", 32, Gender.FEMALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform_update = client.perform(MockMvcRequestBuilders.put("/employees/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));
        ResultActions perform_get = client.perform(MockMvcRequestBuilders.get("/employees/{id}", givenId));

        // Then
        perform_update.andExpect(MockMvcResultMatchers.status().isOk());
        perform_get.andExpect(MockMvcResultMatchers.status().isOk());
        perform_get.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(givenEmployee.getGender().name()));
    }

    @Test
    public void should_throw_exception_when_update_employee_not_exist() throws Exception {
        // Given
        Integer givenId = 100;
        Employee givenEmployee = new Employee("John Smith", 32, Gender.FEMALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.put("/employees/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee with id " + givenId + " does not exist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_throw_exception_when_update_employee_not_active() throws Exception {
        // Given
        Employee employee = new Employee("John Mark", 32, Gender.MALE, 5000.0);
        Employee exsitingEmployee = employeeRepository.save(employee);
        exsitingEmployee.setActive(false);
        employeeRepository.save(exsitingEmployee);
        Integer givenId = exsitingEmployee.getId();
        Employee givenEmployee = new Employee("John Mark", 32, Gender.FEMALE, 5000.0);
        String employeeJson = objectMapper.writeValueAsString(givenEmployee);

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.put("/employees/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee is not active"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    public void should_return_employee_when_get_employee() throws Exception {
        // Given
        Employee employee = new Employee("John Mark", 32, Gender.MALE, 5000.0);
        Employee exsitingEmployee = employeeRepository.save(employee);
        Integer givenId = exsitingEmployee.getId();

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/{id}", givenId));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(givenId));
    }

    @Test
    public void should_throw_exception_when_get_employee_not_exist() throws Exception {
        // Given
        Integer givenId = 100;

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/{id}", givenId));

        // Then
        perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid employee."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee with id " + givenId + " does not exist"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }
}

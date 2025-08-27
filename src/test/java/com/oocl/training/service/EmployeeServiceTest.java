package com.oocl.training.service;

import com.oocl.training.exception.InvalidEmployeeException;
import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import com.oocl.training.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_create_employee_successfully() {
        // Given
        Employee reqEmployee = new Employee("Bill Gill", 28, Gender.MALE, 20000.0);
        Employee mockedEmployee = new Employee(1, reqEmployee.getName(), reqEmployee.getAge(), reqEmployee.getGender(), reqEmployee.getSalary());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(mockedEmployee);

        // When
        Employee respEmployee = employeeService.createEmployee(reqEmployee);

        // Then
        assertEquals(true, respEmployee.getActive());
        assertNotNull(respEmployee.getId());
        assertEquals(reqEmployee.getName(), respEmployee.getName());
        assertEquals(reqEmployee.getAge(), respEmployee.getAge());
        assertEquals(reqEmployee.getGender(), respEmployee.getGender());
        assertEquals(reqEmployee.getSalary(), respEmployee.getSalary());
    }

    @Test
    void should_create_employee_throws_exception_age_less_18() {
        // Given
        Employee reqEmployee = new Employee("Bill Gill", 10, Gender.MALE, 0.0); // -> "Employee age must be between 18 and 65"

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.createEmployee(reqEmployee));
        assertEquals("Employee age must be between 18 and 65", exp.getMessage());
    }

    @Test
    void should_create_employee_throws_exception_age_greater_65() {
        // Given
        Employee reqEmployee = new Employee("Bill Gill", 66, Gender.MALE, 0.0); // -> "Employee age must be between 18 and 65"

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.createEmployee(reqEmployee));
        assertEquals("Employee age must be between 18 and 65", exp.getMessage());
    }

    @Test
    void should_create_employee_throws_exception_age_greater_30_and_salary_less_20000() {
        // Given
        Employee reqEmployee = new Employee("Bill Gill", 33, Gender.MALE, 0.0); // -> "Employees over 30 ages must have a salary more than 20000"

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.createEmployee(reqEmployee));
        assertEquals("Employees over 30 ages must have a salary more than 20000", exp.getMessage());
    }

    @Test
    void should_get_all_employees() {
        // Given
        List<Employee> reqAllEmployees = new ArrayList<>(List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
                new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
                new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));
        List<Employee> mockedAllEmployees = new ArrayList<>(reqAllEmployees);
        Mockito.when(employeeRepository.get()).thenReturn(mockedAllEmployees);

        // When
        List<Employee> respAllEmployees = employeeService.getAllEmployees(null, null, null);

        // Then
        assertEquals(reqAllEmployees, respAllEmployees);
    }

    @Test
    void should_get_all_employees_by_gender() {
        // Given
        List<Employee> reqAllEmployees = new ArrayList<>(List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
                new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
                new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));
        List<Employee> expectedEmployees = reqAllEmployees.stream()
                .filter(e -> e.getGender() == Gender.FEMALE)
                .toList();

        List<Employee> mockedAllEmployees = new ArrayList<>(List.of(
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
                new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0)));
        Mockito.when(employeeRepository.get()).thenReturn(mockedAllEmployees);

        // When
        List<Employee> respAllEmployees = employeeService.getAllEmployees(null, null, Gender.FEMALE);

        // Then
        assertEquals(expectedEmployees.size(), respAllEmployees.size());
        for (int i = 0; i < expectedEmployees.size(); i++) {
            Employee expected = expectedEmployees.get(i);
            Employee actual = respAllEmployees.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getAge(), actual.getAge());
            assertEquals(expected.getGender(), actual.getGender());
            assertEquals(expected.getSalary(), actual.getSalary());
        }
    }

    @Test
    void should_get_employees_by_page_and_size() {
        // Given
        List<Employee> allEmployees = List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
                new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
                new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)
        );
        Mockito.when(employeeRepository.get()).thenReturn(new ArrayList<>(allEmployees));

        List<Employee> page1 = employeeService.getAllEmployees(1, 2, null);
        List<Employee> page2 = employeeService.getAllEmployees(2, 2, null);
        List<Employee> page3 = employeeService.getAllEmployees(3, 2, null);

        // Then
        assertEquals(2, page1.size());
        assertEquals(1, page3.size());
        assertEquals("John Smith", page1.getFirst().getName());
        assertEquals("David Williams", page2.getFirst().getName());
        assertEquals("Michael Jones", page3.getFirst().getName());
    }

    @Test
    void should_return_empty_list_when_page_out_of_range() {
        // Given
        List<Employee> allEmployees = List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0)
        );
        Mockito.when(employeeRepository.get()).thenReturn(new ArrayList<>(allEmployees));

        // When
        List<Employee> result = employeeService.getAllEmployees(10, 5, null);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_employees_by_gender_and_page_and_size() {
        // Given
        List<Employee> allEmployees = List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
                new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
                new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0),
                new Employee(6, "Alice Green", 29, Gender.FEMALE, 4800.0)
        );
        Mockito.when(employeeRepository.get()).thenReturn(new ArrayList<>(allEmployees));

        // When: gender=FEMALE, page=1, size=2
        List<Employee> page1 = employeeService.getAllEmployees(1, 2, Gender.FEMALE);
        // When: gender=FEMALE, page=2, size=2
        List<Employee> page2 = employeeService.getAllEmployees(2, 2, Gender.FEMALE);

        // Then
        assertEquals(2, page1.size());
        assertEquals(1, page2.size());
        assertEquals("Jane Johnson", page1.getFirst().getName());
        assertEquals("Alice Green", page2.getFirst().getName());
    }

    @Test
    void should_handle_invalid_page_or_size() {
        List<Employee> allEmployees = List.of(
                new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0)
        );
        Mockito.when(employeeRepository.get()).thenReturn(new ArrayList<>(allEmployees));

        List<Employee> result1 = employeeService.getAllEmployees(0, 5, null);
        List<Employee> result2 = employeeService.getAllEmployees(1, 0, null);
        List<Employee> result3 = employeeService.getAllEmployees(-1, -5, null);

        assertEquals(allEmployees.size(), result1.size());
        assertEquals(allEmployees.size(), result2.size());
        assertEquals(allEmployees.size(), result3.size());
    }

    @Test
    void should_delete_employee_successfully() {
        // Given
        Integer reqId = 1;
        Employee reqEmployee = new Employee(reqId, "John Smith", 32, Gender.MALE, 5000.0);
        Mockito.when(employeeRepository.get(reqId)).thenReturn(reqEmployee);
        Mockito.doNothing().when(employeeRepository).update(reqId, reqEmployee);

        // When
        employeeService.deleteEmployee(reqId);

        // Then
        assertFalse(reqEmployee.getActive());
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        Mockito.verify(employeeRepository, Mockito.times(1)).update(reqId, reqEmployee);
    }

    @Test
    void should_delete_employee_throw_exception_employee_not_exist() {
        // Given
        Integer reqId = 1;
        Mockito.when(employeeRepository.get(reqId)).thenReturn(null);

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.deleteEmployee(reqId));
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        assertEquals("Employee with id " + reqId + " does not exist", exp.getMessage());
    }

    @Test
    void should_update_employee_successfully() {
        // Given
        Employee existingEmployee = new Employee(1, "John Smith", 32, Gender.MALE, 5000.0);
        Integer reqId = existingEmployee.getId();
        Employee reqEmployee = new Employee("Michael Jones", 40, Gender.MALE, 7000.0);
        Employee mockedEmployee = new Employee(reqId, reqEmployee.getName(), reqEmployee.getAge(), reqEmployee.getGender(), reqEmployee.getSalary());
        Mockito.when(employeeRepository.get(reqId)).thenReturn(existingEmployee);
        Mockito.doNothing().when(employeeRepository).update(reqId, reqEmployee);

        // When
        employeeService.updateEmployee(reqId, reqEmployee);

        // Then
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        Mockito.verify(employeeRepository, Mockito.times(1)).update(reqId, reqEmployee);
    }

    @Test
    void should_update_employee_throws_exception_employee_not_exist() {
        // Given
        Employee existingEmployee = new Employee(1, "John Smith", 32, Gender.MALE, 5000.0);
        Integer reqId = existingEmployee.getId();
        Employee reqEmployee = new Employee("Michael Jones", 40, Gender.MALE, 7000.0);
        Employee mockedEmployee = new Employee(reqId, reqEmployee.getName(), reqEmployee.getAge(), reqEmployee.getGender(), reqEmployee.getSalary());
        Mockito.when(employeeRepository.get(reqId)).thenReturn(null);
        Mockito.doNothing().when(employeeRepository).update(reqId, reqEmployee);

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.updateEmployee(reqId, reqEmployee));
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        assertEquals("Employee with id " + reqId + " does not exist", exp.getMessage());
    }

    @Test
    void should_update_employee_throws_exception_employee_not_active() {
        // Given
        Employee existingEmployee = new Employee(1, "John Smith", 32, Gender.MALE, 5000.0);
        Integer reqId = existingEmployee.getId();
        existingEmployee.setActive(false);
        Employee reqEmployee = new Employee("Michael Jones", 40, Gender.MALE, 7000.0);
        Mockito.when(employeeRepository.get(reqId)).thenReturn(existingEmployee);
        Mockito.doNothing().when(employeeRepository).update(reqId, reqEmployee);

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.updateEmployee(reqId, reqEmployee));
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        assertEquals("Employee is not active", exp.getMessage());
    }

    @Test
    void should_get_employee_successfully() {
        // Given
        Integer reqId = 1;
        Employee reqEmployee = new Employee(reqId, "John Smith", 32, Gender.MALE, 5000.0);
        Mockito.when(employeeRepository.get(reqId)).thenReturn(reqEmployee);

        // When
        Employee respEmployee = employeeService.getEmployee(reqId);

        // Then
        Mockito.verify(employeeRepository, Mockito.times(2)).get(reqId);
        assertEquals(reqEmployee.getId(), respEmployee.getId());
        assertEquals(reqEmployee.getName(), respEmployee.getName());
        assertEquals(reqEmployee.getAge(), respEmployee.getAge());
        assertEquals(reqEmployee.getGender(), respEmployee.getGender());
        assertEquals(reqEmployee.getSalary(), respEmployee.getSalary());
    }

    @Test
    void should_get_employee_throws_exception_employee_not_exist() {
        // Given
        Integer reqId = 1;
        Employee reqEmployee = new Employee(reqId, "John Smith", 32, Gender.MALE, 5000.0);
        Mockito.when(employeeRepository.get(reqId)).thenReturn(null);

        // When & Then
        InvalidEmployeeException exp = assertThrows(InvalidEmployeeException.class, () -> employeeService.getEmployee(reqId));
        Mockito.verify(employeeRepository, Mockito.times(1)).get(reqId);
        assertEquals("Employee with id " + reqId + " does not exist", exp.getMessage());
    }
}
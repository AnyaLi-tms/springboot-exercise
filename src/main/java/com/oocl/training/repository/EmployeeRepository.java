package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepository {
    private static final Map<Integer, Employee> employeeDb = new HashMap<>(Map.of(
            1, new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
            2, new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
            3, new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
            4, new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
            5, new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));

    public Employee save(Employee newEmployee) {
        return employeeDb.put(newEmployee.getId(), newEmployee);
    }

    public List<Employee> get() {
        return new ArrayList<>(employeeDb.values());
    }

    public Employee get(Integer id) {
        return employeeDb.get(id);
    }

    public void delete(Integer id) {
        employeeDb.remove(id);
    }

    public void update(Integer id, Employee newEmployee) {
        employeeDb.put(id, newEmployee);
    }

    public Integer getMaxId() {
        return employeeDb.keySet().stream().max(Integer::compareTo).orElse(0);
    }
}

package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmployeeInMemoryRepository implements EmployeeRepository {
    private static final Map<Integer, Employee> employeeDb = new HashMap<>(Map.of(
            1, new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
            2, new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
            3, new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
            4, new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
            5, new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));

    @Override
    public Employee save(Employee newEmployee) {
        int newId = getMaxId() + 1;
        newEmployee.setId(newId);
        employeeDb.put(newId, newEmployee);
        return employeeDb.get(newId);
    }

    @Override
    public List<Employee> get() {
        return new ArrayList<>(employeeDb.values());
    }

    @Override
    public List<Employee> getByGender(Gender gender) {
        return employeeDb.values().stream()
                .filter(e -> e.getGender() == gender)
                .collect(Collectors.toList());
    }

    @Override
    public Employee get(Integer id) {
        return employeeDb.get(id);
    }

    @Override
    public void delete(Integer id) {
        employeeDb.remove(id);
    }

    @Override
    public void update(Integer id, Employee newEmployee) {
        employeeDb.put(id, newEmployee);
    }

    @Override
    public Integer getMaxId() {
        return employeeDb.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    @Override
    public void deleteAll() {
    }
}

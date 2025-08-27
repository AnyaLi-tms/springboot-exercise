package com.oocl.training.controller;


import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    static final Map<Integer, Employee> employeeDb = new HashMap<>(Map.of(
            1, new Employee(1, "John Smith", 32, "male", 5000.0),
            2, new Employee(2, "Jane Johnson", 28, "female", 6000.0),
            3, new Employee(3, "David Williams", 35, "male", 5500.0),
            4, new Employee(4, "Emily Brown", 23, "female", 4500.0),
            5, new Employee(5, "Michael Jones", 40, "male", 7000.0)));

    @GetMapping()
    public List<Employee> getAllEmployees(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {
        List<Employee> allEmployees = new ArrayList<>(employeeDb.values());
        if (page == null || size == null) return allEmployees;
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allEmployees.size());
        if (fromIndex >= allEmployees.size()) return new ArrayList<>();
        return allEmployees.subList(fromIndex, toIndex);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@RequestBody Employee employee) {
        int id  = employee.setId(employeeDb.size()+1);
        employeeDb.put(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeDb.remove(id);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        if(employeeDb.containsKey(id)) {
            employee.setId(id);
            employeeDb.put(id, employee);
        }
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeDb.get(id);
    }
}

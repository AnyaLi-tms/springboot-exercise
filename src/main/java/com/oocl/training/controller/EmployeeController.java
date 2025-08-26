package com.oocl.training.controller;


import com.oocl.training.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    final static Map<Integer, Employee> db = new HashMap<>();

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(db.values());
    }

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@RequestBody Employee employee) {
        int id  = employee.setId(db.size()+1);
        db.put(id, employee);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable int id) {
        db.remove(id);
    }

    @PutMapping("/employee/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        if(db.containsKey(id)) {
            employee.setId(id);
            db.put(id, employee);
        }
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return db.get(id);
    }
}

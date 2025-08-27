package com.oocl.training.service;

import com.oocl.training.repository.EmployeeRepository;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(Integer page, Integer size) {
        List<Employee> allEmployees = new ArrayList<>(employeeRepository.get());
        if (page == null || size == null) return allEmployees;
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allEmployees.size());
        if (fromIndex >= allEmployees.size()) return new ArrayList<>();
        return allEmployees.subList(fromIndex, toIndex);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(id);
    }

    public void updateEmployee(int id, Employee employee) {
        employeeRepository.update(id, employee);
    }

    public Employee getEmployee(int id) {
        return employeeRepository.get(id);
    }
}
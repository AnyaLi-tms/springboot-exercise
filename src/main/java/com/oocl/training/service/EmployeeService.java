package com.oocl.training.service;

import com.oocl.training.exception.InvalidEmployeeException;
import com.oocl.training.model.Gender;
import com.oocl.training.repository.EmployeeRepository;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidEmployeeException("Employee age must be between 18 and 65");
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000) {
            throw new InvalidEmployeeException("Employees over 30 ages must have a salary more than 20000");
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(Integer page, Integer size, Gender gender) {
        List<Employee> allEmployees = new ArrayList<>(employeeRepository.get());

        if (gender != null) {
            allEmployees = allEmployees.stream()
                    .filter(e -> e.getGender() == gender)
                    .collect(Collectors.toList());
        }

        if (page == null || size == null || page < 1 || size < 1) {
            return allEmployees;
        }

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allEmployees.size());

        if (fromIndex >= allEmployees.size()) {
            return new ArrayList<>();
        }
        return allEmployees.subList(fromIndex, toIndex);
    }

    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.get(id);
        if (employee == null) {
            throw new InvalidEmployeeException("Employee with id " + id + " does not exist");
        }
        employee.setActive(false);
        employeeRepository.update(id, employee);
    }

    public void updateEmployee(Integer id, Employee employee) {
        Employee existingEmployee = employeeRepository.get(id);
        if (existingEmployee == null) {
            throw new InvalidEmployeeException("Employee with id " + id + " does not exist");
        }
        if (!existingEmployee.getActive()) {
            throw new InvalidEmployeeException("Employee is not active");
        }
        employeeRepository.update(id, employee);
    }

    public Employee getEmployee(Integer id) {
        if (employeeRepository.get(id) == null) {
            throw new InvalidEmployeeException("Employee with id " + id + " does not exist");
        }
        return employeeRepository.get(id);
    }
}
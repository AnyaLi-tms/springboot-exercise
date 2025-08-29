package com.oocl.training.service;

import com.oocl.training.exception.InvalidEmployeeException;
import com.oocl.training.model.Company;
import com.oocl.training.model.Gender;
import com.oocl.training.repository.EmployeeDBRepository;
import com.oocl.training.repository.EmployeeRepository;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeDBRepository employeeDBRepository) {
        this.employeeRepository = employeeDBRepository;
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
        return employeeRepository.get(page, size, gender);
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
        employee.setId(id);
        employeeRepository.update(id, employee);
    }

    public Employee getEmployee(Integer id) {
        if (employeeRepository.get(id) == null) {
            throw new InvalidEmployeeException("Employee with id " + id + " does not exist");
        }
        return employeeRepository.get(id);
    }

    public List<Employee> getEmployeesByGender(Gender gender) {
        return employeeRepository.getByGender(gender);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return employeeRepository.getByCompanyId(companyId);
    }
}
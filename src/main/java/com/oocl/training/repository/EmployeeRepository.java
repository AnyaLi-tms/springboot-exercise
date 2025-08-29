package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;

import java.util.List;

public interface EmployeeRepository {
    Employee save(Employee employee);

    List<Employee> get();

    Employee get(Integer id);

    List<Employee> getByGender(Gender gender);

    void delete(Integer id);

    void update(Integer id, Employee employee);

    Integer getMaxId();

    void deleteAll();
}
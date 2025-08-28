package com.oocl.training.repository;

import com.oocl.training.model.Employee;

import java.util.List;

public class EmployeeDBRepository implements EmployeeRepository {
    @Override
    public Employee save(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> get() {
        return List.of();
    }

    @Override
    public Employee get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Employee employee) {

    }

    @Override
    public Integer getMaxId() {
        return 0;
    }
}

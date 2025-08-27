package com.oocl.training.model;

import java.util.List;

public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees;

    public Company() {}

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public Integer setId(Integer id) {
        this.id = id;
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

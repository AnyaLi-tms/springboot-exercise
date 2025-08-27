package com.oocl.training.model;

import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Employee> employees;

    public Company() {}

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
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

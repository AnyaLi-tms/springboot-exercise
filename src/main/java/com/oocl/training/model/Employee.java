package com.oocl.training.model;

public class Employee {
    private int id;
    private String name;
    private int age;
    private int companyId;

    public Employee() {}

    public Employee(int id, String name, int age, int companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.companyId = companyId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}

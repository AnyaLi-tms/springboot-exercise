package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;

import java.util.List;

public interface EmployeeRepository {
    Employee save(Employee employee);

    List<Employee> get();
    /**
     * 获取员工列表，支持分页和按性别过滤
     *
     * @param page 页码，从1开始
     * @param size 每页大小
     * @param gender 性别过滤条件，null表示不过滤
     * @return 员工列表
     */
    List<Employee> get(Integer page, Integer size, Gender gender);

    Employee get(Integer id);

    List<Employee> getByGender(Gender gender);

    void delete(Integer id);

    void update(Integer id, Employee employee);

    Integer getMaxId();

    void deleteAll();

    List<Employee> getByCompanyId(Integer companyId);
}
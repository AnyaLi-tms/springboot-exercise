package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> getEmployeeByGender(Gender gender);
}

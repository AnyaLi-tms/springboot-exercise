package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByGender(Gender gender);
    void deleteAll();
    List<Employee> findByCompanyId(Integer companyId);

    Page<Employee> findByGender(Gender gender, Pageable pageable);
}

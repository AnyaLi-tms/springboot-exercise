package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDBRepository implements EmployeeRepository {
    JpaEmployeeRepository repository;

    public EmployeeDBRepository(JpaEmployeeRepository jpaEmployeeRepository) {
        this.repository = jpaEmployeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public List<Employee> get() {
        return repository.findAll();
    }

    @Override
    public List<Employee> get(Integer page, Integer size, Gender gender) {
        Pageable pageable = PageRequest.of(page == null || page < 1 ? 0 : page - 1,
                size == null || size < 1 ? 5 : size);
        Page<Employee> employeePage;
        if (gender == null) {
            employeePage = repository.findAll(pageable);
        } else {
            employeePage = repository.findByGender(gender, pageable);
        }
        return employeePage.getContent();
    }

    @Override
    public Employee get(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getByGender(Gender gender) {
        return repository.findByGender(gender);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, Employee employee) {
        if (repository.existsById(id)) {
            employee.setId(id);
            repository.save(employee);
        }
    }

    @Override
    public Integer getMaxId() {
        return repository.findAll().stream()
                .map(Employee::getId)
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Employee> getByCompanyId(Integer companyId) {
        return repository.findByCompanyId(companyId);
    }
}

package com.oocl.training.repository;

import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;

import java.util.List;

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
    public Employee get(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getByGender(Gender gender) {
        return repository.getEmployeeByGender(gender);
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
}

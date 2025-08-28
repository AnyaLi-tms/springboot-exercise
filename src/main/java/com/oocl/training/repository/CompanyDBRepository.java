package com.oocl.training.repository;

import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDBRepository implements CompanyRepository {
    JpaCompanyRepository repository;

    public CompanyDBRepository(JpaCompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company save(Company company) {
        return repository.save(company);
    }

    @Override
    public List<Company> get() {
        return repository.findAll();
    }

    @Override
    public Company get(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, Company company) {
        if (repository.existsById(id)) {
            company.setId(id);
            repository.save(company);
        }
    }

    @Override
    public Integer getMaxId() {
        return repository.findAll().stream()
                .map(Company::getId)
                .max(Integer::compareTo)
                .orElse(0);
    }
}

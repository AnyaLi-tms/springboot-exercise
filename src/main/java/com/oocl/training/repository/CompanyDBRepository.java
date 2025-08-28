package com.oocl.training.repository;

import com.oocl.training.model.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDBRepository implements CompanyRepository {
    @Override
    public Company save(Company company) {
        return null;
    }

    @Override
    public List<Company> get() {
        return List.of();
    }

    @Override
    public Company get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Company company) {

    }

    @Override
    public Integer getMaxId() {
        return 0;
    }
}

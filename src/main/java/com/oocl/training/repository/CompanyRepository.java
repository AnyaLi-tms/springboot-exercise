package com.oocl.training.repository;

import com.oocl.training.model.Company;

import java.util.List;

public interface CompanyRepository {
    Company save(Company company);

    List<Company> get();

    Company get(Integer id);

    void delete(Integer id);

    void update(Integer id, Company company);

    Integer getMaxId();
}

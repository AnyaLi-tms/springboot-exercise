package com.oocl.training.repository;

import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyInMemoryRepository {
    private final HashMap<Integer, Company> companyDb = new HashMap<>(Map.of(
            1, new Company(1, "Acme Corporation", List.of(
                    new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
                    new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0)
            )),
            2, new Company(2, "TechCom Solutions", List.of(
                    new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
                    new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                    new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)
            )),
            3, new Company(3, "Global Innovators"),
            4, new Company(4, "Stellar Enterprises"),
            5, new Company(5, "Nexus Industries")
    ));

    public Company save(Company newCompany) {
        return companyDb.put(getMaxId() + 1, newCompany);
    }

    public List<Company> get() {
        return new ArrayList<>(companyDb.values());
    }

    public Company get(Integer id) {
        return companyDb.get(id);
    }

    public void delete(Integer id) {
        companyDb.remove(id);
    }

    public void update(Integer id, Company newCompany) {
        companyDb.put(id, newCompany);
    }

    public Integer getMaxId() {
        return companyDb.keySet().stream().max(Integer::compareTo).orElse(0);
    }
}

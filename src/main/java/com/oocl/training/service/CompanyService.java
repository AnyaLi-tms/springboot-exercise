package com.oocl.training.service;

import com.oocl.training.exception.InvalidCompanyException;
import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import com.oocl.training.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies(Integer page, Integer size) {
        List<Company> allCompanies = new ArrayList<>(companyRepository.get());

        if (page == null || size == null || page < 1 || size < 1) {
            return allCompanies;
        }

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allCompanies.size());

        if (fromIndex >= allCompanies.size()) {
            return new ArrayList<>();
        }
        return allCompanies.subList(fromIndex, toIndex);
    }

    public void deleteCompany(Integer id) {
        companyRepository.delete(id);
    }

    public void updateCompany(Integer id, Company company) {
        Company existingCompany = companyRepository.get(id);
        if (existingCompany == null) {
            throw new InvalidCompanyException("Company with id " + id + " does not exist");
        }
        companyRepository.update(id, company);
    }

    public Company getCompany(Integer id) {
        return companyRepository.get(id);
    }

//    public List<Employee> getEmployeesByCompanyId(Integer id) {
//        Company company = companyRepository.get(id);
//        if (company == null) {
//            return new ArrayList<>();
//        }
//        return company.getEmployees() != null ? company.getEmployees() : new ArrayList<>();
//    }
}

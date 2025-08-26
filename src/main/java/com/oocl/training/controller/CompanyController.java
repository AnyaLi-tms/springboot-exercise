package com.oocl.training.controller;
import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final static Map<Integer, Company> db = new HashMap<>();

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return new ArrayList<Company>(db.values());
    }

    @GetMapping("/companies")
    public List<Company> getCompaniesByPage(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {

        List<Company> allCompanies = new ArrayList<>(db.values());
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allCompanies.size());

        if (fromIndex >= allCompanies.size()) {
            // 页码超出总数，返回空列表
            return new ArrayList<>();
        }

        return allCompanies.subList(fromIndex, toIndex);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody Company company) {
        int id = company.setId(db.size() + 1);
        db.put(id, company);
    }

    @DeleteMapping("/companies/{id}")
    public void deleteCompany(@PathVariable int id) {
        db.remove(id);
    }

    @PutMapping("/companies/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        if(db.containsKey(id)) {
            company.setId(id);
            db.put(id, company);
        }
    }

    @GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable int id) {
        return db.get(id);
    }

    @GetMapping("/companies/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable int id) {
        return EmployeeController.db.values().stream()
                .filter(employee -> employee.getCompanyId() == id)
                .toList();
    }
}

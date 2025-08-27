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
@RequestMapping("/companies")
public class CompanyController {
    private final static Map<Integer, Company> db = new HashMap<>();

    @GetMapping("")
    public List<Company> getAllCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<Company> allCompanies = new ArrayList<>(db.values());
        if (page == null || size == null) return allCompanies;
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allCompanies.size());
        if (fromIndex >= allCompanies.size()) return new ArrayList<>();
        return allCompanies.subList(fromIndex, toIndex);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody Company company) {
        int id = company.setId(db.size() + 1);
        db.put(id, company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        db.remove(id);
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        if(db.containsKey(id)) {
            company.setId(id);
            db.put(id, company);
        }
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        return db.get(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable int id) {
        return EmployeeController.db.values().stream()
                .filter(employee -> employee.getCompanyId() == id)
                .toList();
    }
}

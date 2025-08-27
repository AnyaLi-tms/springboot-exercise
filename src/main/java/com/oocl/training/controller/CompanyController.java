package com.oocl.training.controller;
import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import com.oocl.training.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
//    // 先定义员工列表
//    static final List<Employee> employeesForCompany1 = List.of(
//            EmployeeRepository.employeeDb.get(1),
//            EmployeeController.employeeDb.get(2)
//    );
//
//    static final List<Employee> employeesForCompany2 = List.of(
//            EmployeeController.employeeDb.get(3),
//            EmployeeController.employeeDb.get(4),
//            EmployeeController.employeeDb.get(5)
//    );

//    // 初始化 companyDb
//    static final Map<Integer, Company> companyDb = new HashMap<>(Map.of(
//            1, new Company(1, "Tech Corp", employeesForCompany1),
//            2, new Company(2, "Business Inc", employeesForCompany2)
//    ));
    private final Map<Integer, Company> companyDb = new HashMap<>();
    @GetMapping()
    public List<Company> getAllCompanies(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {
        List<Company> allCompanies = new ArrayList<>(companyDb.values());
        if (page == null || size == null) return allCompanies;
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allCompanies.size());
        if (fromIndex >= allCompanies.size()) return new ArrayList<>();
        return allCompanies.subList(fromIndex, toIndex);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody Company company) {
        int id = company.setId(companyDb.size() + 1);
        companyDb.put(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable int id) {
        companyDb.remove(id);
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        if(companyDb.containsKey(id)) {
            company.setId(id);
            companyDb.put(id, company);
        }
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        return companyDb.get(id);
    }

//    @GetMapping("/{id}/employees")
//    public List<Employee> getEmployeesByCompanyId(@PathVariable int id) {
//        return EmployeeController.employeeDb.values().stream()
//                .filter(employee -> employee.getCompanyId() == id)
//                .toList();
//    }
}

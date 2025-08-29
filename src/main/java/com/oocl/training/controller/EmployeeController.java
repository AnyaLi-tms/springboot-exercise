package com.oocl.training.controller;


import com.oocl.training.controller.dto.EmployeeRequest;
import com.oocl.training.controller.dto.EmployeeResponse;
import com.oocl.training.controller.mapper.EmployeeMapper;
import com.oocl.training.model.Employee;
import com.oocl.training.model.Gender;
import com.oocl.training.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.createEmployee(employee));
    }

    @GetMapping()
    public List<EmployeeResponse> getAllEmployees(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) Gender gender) {
        return employeeMapper.toResponse(employeeService.getAllEmployees(page, size, gender));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        employeeService.updateEmployee(id, employee);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployee(@PathVariable Integer id) {
        return employeeMapper.toResponse(employeeService.getEmployee(id));
    }
}

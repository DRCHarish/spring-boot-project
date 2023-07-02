package com.employeecrud.springboot.project.controller;

import com.employeecrud.springboot.project.entity.Employee;
import com.employeecrud.springboot.project.error.EmployeeNotFoundException;
import com.employeecrud.springboot.project.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addempdetails")
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }
    @GetMapping(value = {"/fetchemp", "/fetchemp/{id}"})
    public Object getEmployee(@PathVariable(required = false) Integer id) throws EmployeeNotFoundException {
        if (id==null) {
            return employeeService.getEmployee();
        } else {
            return employeeService.getEmployee(id);
        }
    }
    @PutMapping("/updateemp/{id}")
    public Employee updateEmployee(@PathVariable(name="id") Integer id, @RequestBody Employee employee) throws EmployeeNotFoundException {
        return employeeService.updateEmployee(id,employee);
    }
    @GetMapping("/searchemp")
    public List<Employee> searchEmployeeByDesignation(@RequestParam(name = "designation") String empDesignation) throws EmployeeNotFoundException {
        return employeeService.searchEmployeeByDesignation(empDesignation);
    }
    @DeleteMapping("/deleteemp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully with Id:"+id;
    }
}


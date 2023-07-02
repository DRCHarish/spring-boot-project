package com.employeecrud.springboot.project.service;

import com.employeecrud.springboot.project.entity.Employee;
import com.employeecrud.springboot.project.error.EmployeeNotFoundException;

import java.util.List;


public interface EmployeeService {
    public Employee saveEmployee(Employee employee);


    public Employee getEmployee(Integer id) throws EmployeeNotFoundException;
    public List<Employee> getEmployee();

   public void deleteEmployee(Integer id) throws EmployeeNotFoundException;

   public List<Employee> searchEmployeeByDesignation(String empDesignation) throws EmployeeNotFoundException;

   public Employee updateEmployee(Integer id, Employee employee) throws EmployeeNotFoundException;
}

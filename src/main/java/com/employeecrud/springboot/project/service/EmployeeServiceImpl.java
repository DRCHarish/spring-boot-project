package com.employeecrud.springboot.project.service;

import com.employeecrud.springboot.project.entity.Employee;
import com.employeecrud.springboot.project.error.EmployeeNotFoundException;
import com.employeecrud.springboot.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(Integer id) throws EmployeeNotFoundException {
                Optional<Employee> emp = employeeRepository.findById(id);
                if(!emp.isPresent())
                {
                    throw new EmployeeNotFoundException("Employee not found with given Id...");
                }
                return emp.get();
    }

    @Override
    public List<Employee> getEmployee() {

        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(!emp.isPresent())
        {
            throw new EmployeeNotFoundException("No employee found with given ID");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployeeByDesignation(String empDesignation) throws EmployeeNotFoundException {
        List<Employee> foundemployees = employeeRepository.findByEmpDesignationIgnoreCase(empDesignation);
        if(foundemployees.isEmpty())
        {
            throw new EmployeeNotFoundException("No employee found with given designation");
        }
            return foundemployees;
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) throws EmployeeNotFoundException {
        Optional<Employee> empdb=employeeRepository.findById(id);
        if(!empdb.isPresent())
        {
            throw new EmployeeNotFoundException("No employee found with given id");
        }
        if(Objects.nonNull(employee.getEmpName())&&!"".equalsIgnoreCase(employee.getEmpName()))
        {
            empdb.get().setEmpName(employee.getEmpName());
        }
        if(Objects.nonNull(employee.getEmpDesignation())&&!"".equalsIgnoreCase(employee.getEmpDesignation()))
        {
            empdb.get().setEmpDesignation(employee.getEmpDesignation());
        }
        employeeRepository.save(empdb.get());
        return empdb.get();
    }


}

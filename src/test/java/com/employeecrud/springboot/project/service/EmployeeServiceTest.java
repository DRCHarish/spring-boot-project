package com.employeecrud.springboot.project.service;

import com.employeecrud.springboot.project.entity.Employee;
import com.employeecrud.springboot.project.error.EmployeeNotFoundException;
import com.employeecrud.springboot.project.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.will;
import static org.mockito.BDDMockito.willDoNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void whenValidEmpDesignation_thenEmployeesShouldFound() throws EmployeeNotFoundException {
        List<Employee> foundemp = new ArrayList<>();
        foundemp.add(new Employee(1,"Ramu","staff"));
        foundemp.add(new Employee(2,"Rakesh","staff"));

        Mockito.when(employeeRepository.findByEmpDesignationIgnoreCase("staff")).thenReturn(foundemp);
        String empDesignation="staff";
        List<Employee> found=employeeService.searchEmployeeByDesignation(empDesignation);
        for(Employee e : found) {
            assertEquals(empDesignation,e.getEmpDesignation());
        }
    }
    @Test
    public void whenInvalidEmpDesignation_thenEmployeesNotFound() throws EmployeeNotFoundException{
        String empDesignation ="staff";
        Mockito.when(employeeRepository.findByEmpDesignationIgnoreCase(empDesignation)).thenReturn(Collections.emptyList());
        //Verifying output
        assertThrows(EmployeeNotFoundException.class,() ->employeeService.searchEmployeeByDesignation(empDesignation));
    }
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject()
    {
        //setup
        Employee emp = Employee.builder().empName("Ramu").empDesignation("staff").id(1).build();
        Mockito.when(employeeRepository.save(emp)).thenReturn(emp);


        Employee savedEmployee = employeeService.saveEmployee(emp);


        assertEquals(emp,savedEmployee);
        org.assertj.core.api.Assertions.assertThat(savedEmployee.getEmpName()).isNotNull();

    }
    @Test
    public void givenInvalidEmployeeObject_whenSaveEmployee_thenThrowsException()
    {

       Employee emp = Employee.builder().id(1).empDesignation("").empName(" ").build();
       Mockito.when(employeeRepository.save(emp)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,()->employeeService.saveEmployee(emp));


    }
    @Test
    public void givenValidId_thenReturnEmployee() throws EmployeeNotFoundException {
        int id =1;
        Employee foundemp =Employee.builder().id(1).empName("Ramu").empDesignation("staff").build();

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(foundemp));

        Employee dbemp =employeeService.getEmployee(id);
        Assertions.assertThat(dbemp.getId().equals(foundemp.getId()));
    }
    @Test
    public void givenNoId_thenReturnAllEmployees()
    {
        List<Employee> emp = new ArrayList<>();
        emp.add(Employee.builder().id(1).empDesignation("staff").empName("ramu").build());
        emp.add(Employee.builder().id(2).empName("sathish").empDesignation("staff").build());

        Mockito.when(employeeRepository.findAll()).thenReturn(emp);

        assertEquals(emp,employeeService.getEmployee());

    }
    @Test
    public void givenEmployeedetails_whenUpdateEmployee_thenReturnEmployeeObject() throws EmployeeNotFoundException {
        Employee emp=Employee.builder().empName("ramu").empDesignation("staff").id(1).build();
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));
        emp.setEmpDesignation("teacher");
        emp.setEmpName("rakesh");
        Employee updatedEmployee= employeeService.updateEmployee(1,emp);
        Assertions.assertThat(updatedEmployee.getEmpName()).isEqualTo("rakesh");
    }
    @Test
    public void deleteEmployee() throws EmployeeNotFoundException {
        Employee emp=new Employee(1,"ramu","staff");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp)).thenReturn(null);
        employeeService.deleteEmployee(emp.getId());
        verify(employeeRepository,times(1)).deleteById(emp.getId());
        assertNull(employeeRepository.findById(1));
    }
}
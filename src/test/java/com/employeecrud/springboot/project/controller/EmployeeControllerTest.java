package com.employeecrud.springboot.project.controller;

import com.employeecrud.springboot.project.entity.Employee;
import com.employeecrud.springboot.project.error.EmployeeNotFoundException;
import com.employeecrud.springboot.project.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    @Test
    public void givenEmployeeDetails_whenSaveEmployee_thenReturnEmployeeObject() throws Exception {
        Employee employee = Employee.builder().empName("Ramu").empDesignation("Staff").build();
        Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/emp/addempdetails").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void givenEmployeeId_thenReturnEmployee() throws Exception {
        Employee employee = Employee.builder().id(1).empName("Ramu").empDesignation("Staff").build();
        Mockito.when(employeeService.getEmployee(employee.getId())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/emp/fetchemp/{id}",1).contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.empName").value("Ramu"));

    }
    @Test
    public void givenNoId_thenReturnAllEmployees() throws Exception {
        List<Employee> employeeList= new ArrayList<>();
        employeeList.add(new Employee(1,"Ramu","Staff"));
        employeeList.add(new Employee(2,"Ravi","Teacher"));

        Mockito.when(employeeService.getEmployee()).thenReturn(employeeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/emp/fetchemp").contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].empName").value("Ramu"));
    }
    @Test
    public void givenId_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        Employee emp=new Employee(1,"Ramu","Staff");
        emp.setEmpName("Ravi");
        emp.setEmpDesignation("Teacher");
        Mockito.when(employeeService.updateEmployee(1,emp)).thenReturn(emp);
        mockMvc.perform(MockMvcRequestBuilders.put("/emp/updateemp/{id}",1).contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(emp))).andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.empName").value("Ravi"));
    }
    @Test
    public void givenId_thenDeleteEmployee() throws Exception {
        int id=1;
        willDoNothing().given(employeeService).deleteEmployee(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/emp/deleteemp/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
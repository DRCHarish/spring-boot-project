package com.employeecrud.springboot.project.repository;

import com.employeecrud.springboot.project.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Employee employee;
    @Test
    public void whenFindById_thenReturnEmployee()
    {
        Employee employee = new Employee(1,"Harish","Employee");
        employeeRepository.save(employee);
        Employee empdb = employeeRepository.findById(1).get();
        assertEquals(empdb.getEmpName(),"Harish");
    }
    @Test
    public void givenEmpoyeeObject_whenSave_thenReturnEmployee()
    {
        Employee employee2=new Employee(1,"vadivel","lecturer");
        Employee savedEmployee=employeeRepository.save(employee2);
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
    @Test
    public void givenEmployeeDetails_whenUpdateEmployee_thenReturnUpdatedEmployee()
    {
        Employee employee3= Employee.builder().id(2).empName("rahul").empDesignation("staff").build();
        employeeRepository.save(employee3);
        employee3.setEmpName("ravi");
        employee3.setEmpDesignation("Teacher");
        Employee updatedEmployee = employeeRepository.save(employee3);

        Assertions.assertThat(updatedEmployee.getEmpName()).isEqualTo("ravi");
    }
    @Test
    public void givenId_whenDelete_thenRemoveEmployee()
    {
        Employee employee = Employee.builder().id(2).empDesignation("professor").empName("lakshmi").build();
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> optionalEmployee=employeeRepository.findById(employee.getId());

        Assertions.assertThat(optionalEmployee).isEmpty();
    }
}
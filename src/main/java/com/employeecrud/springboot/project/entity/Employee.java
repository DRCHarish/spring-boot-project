package com.employeecrud.springboot.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "EMP_NAME")
    @NotBlank(message = "Employee Name is required...")
    private String empName;
    @Column(name = "EMP_DESIGNATION")
    @NotBlank(message = "Please enter designation...")
    private String empDesignation;
}

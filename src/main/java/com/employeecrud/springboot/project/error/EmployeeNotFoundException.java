package com.employeecrud.springboot.project.error;

public class EmployeeNotFoundException extends Exception{

    public EmployeeNotFoundException()
    {
        super();
    }
    public EmployeeNotFoundException(String message)
    {
        super(message);
    }
}

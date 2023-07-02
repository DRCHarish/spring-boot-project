package com.employeecrud.springboot.project.error;

import com.employeecrud.springboot.project.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ResponseStatus
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity employeeNotFound(EmployeeNotFoundException e)
    {
        String message=e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}

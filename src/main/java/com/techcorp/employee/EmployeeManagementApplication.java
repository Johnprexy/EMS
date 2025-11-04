package com.techcorp.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
        System.out.println("===========================================");
        System.out.println("Employee Management System is running!");
        System.out.println("Access API at: http://localhost:8081/api/employees");
        System.out.println("Health Check: http://localhost:8081/api/health");
        System.out.println("===========================================");
    }
}

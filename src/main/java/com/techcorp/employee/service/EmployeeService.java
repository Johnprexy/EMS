package com.techcorp.employee.service;

import com.techcorp.employee.model.Employee;
import java.util.List;

public interface EmployeeService {
    
    Employee saveEmployee(Employee employee);
    
    List<Employee> getAllEmployees();
    
    Employee getEmployeeById(Long id);
    
    Employee updateEmployee(Long id, Employee employee);
    
    void deleteEmployee(Long id);
    
    List<Employee> getEmployeesByDepartment(String department);
}

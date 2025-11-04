package com.techcorp.employee.service;

import com.techcorp.employee.exception.EmployeeNotFoundException;
import com.techcorp.employee.model.Employee;
import com.techcorp.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    
    @Mock
    private EmployeeRepository employeeRepository;
    
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    
    private Employee employee;
    
    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@techcorp.com");
        employee.setDepartment("Engineering");
        employee.setSalary(75000.0);
        employee.setHireDate(LocalDate.of(2023, 1, 15));
    }
    
    @Test
    void testSaveEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        
        Employee savedEmployee = employeeService.saveEmployee(employee);
        
        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        
        List<Employee> result = employeeService.getAllEmployees();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }
    
    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        
        Employee result = employeeService.getEmployeeById(1L);
        
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).findById(anyLong());
    }
    
    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
    }
    
    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        
        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);
        
        assertNotNull(updatedEmployee);
        assertEquals("John", updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    @Test
    void testDeleteEmployee() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));
        
        employeeService.deleteEmployee(1L);
        
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }
    
    @Test
    void testGetEmployeesByDepartment() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findByDepartment(anyString())).thenReturn(employees);
        
        List<Employee> result = employeeService.getEmployeesByDepartment("Engineering");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getDepartment());
    }
}

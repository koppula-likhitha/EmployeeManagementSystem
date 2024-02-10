package com.example.Employee.services;

import java.util.List;

import com.example.Employee.model.Employee;

public interface EmployeeService {

	Employee createEmployee(Employee employee);

	List<Employee> getAllEmployees();

	boolean deleteEmployee(Long id);

	Employee getEmployeeByid(Long id);

	Employee updateEmployee(Long id, Employee employee);

	Employee createEmployeeForUser(Employee employee, String userEmail);

	List<Employee> getAllEmployeesForUser(String userEmail);

}

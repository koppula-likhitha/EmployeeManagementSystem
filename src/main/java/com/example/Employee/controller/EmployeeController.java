package com.example.Employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.model.Employee;
import com.example.Employee.services.EmployeeService;
import com.example.Employee.utility.JWTUtility;

import jakarta.websocket.server.PathParam;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
     private EmployeeService employeeService;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee, @RequestHeader(value="Authorization",
			defaultValue = "") String token)
	{
		employeeService.createEmployee(employee);
		return employee;
		
	}
	
	@PostMapping("/employees/employeesForUser")
	public Employee createEmployeeForUser(@RequestBody Employee employee, @RequestHeader(value="Authorization",
			defaultValue = "") String token) {
		
		String userEmail= jwtUtility.getUserEmailFromToken(token);
		employeeService.createEmployeeForUser(employee,userEmail);
		return employee;
		
	}
	
	@GetMapping("/employees/hello")
	public String helloe() {
		return "Hello Likhitha Employees";
	}

	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(@RequestHeader(value="Authorization",
			defaultValue = "") String token){
		//String userEmail= jwtUtility.getUserEmailFromToken(token);
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/employees/employeesForUser")
	public List<Employee> getAllEmployeesForUser(@RequestHeader(value="Authorization",
			defaultValue = "") String token){
		String userEmail= jwtUtility.getUserEmailFromToken(token);
		return employeeService.getAllEmployeesForUser(userEmail);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable("id") Long id, @RequestHeader(value="Authorization",
			defaultValue = "") String token){
	       boolean deleted=false;
	       deleted = employeeService.deleteEmployee(id);
	       Map<String,Boolean> response = new HashMap<>();
	       response.put("deleted", deleted);
	       return ResponseEntity.ok(response);	       
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id, @RequestHeader(value="Authorization",
	defaultValue = "") String token)
	 {
		
		Employee employee = null;
		employee = employeeService.getEmployeeByid(id);
		return ResponseEntity.ok(employee);	
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee){
		
		 employee = employeeService.updateEmployee(id,employee);
		return ResponseEntity.ok(employee);		
	}
}

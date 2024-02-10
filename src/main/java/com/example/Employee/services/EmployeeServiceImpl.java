package com.example.Employee.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.entity.UserEntity;
import com.example.Employee.model.Employee;
import com.example.Employee.repository.EmployeeRepository;
import com.example.Employee.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Employee createEmployee(Employee employee) {
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		
		BeanUtils.copyProperties(employee, employeeEntity);
		
		employeeRepository.save(employeeEntity);
		return employee;
		
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<EmployeeEntity>employeeEnities = employeeRepository.findAll();
		List<Employee> employees = employeeEnities.stream().map(emp -> new Employee(emp.getId(), emp.getFirstName(),emp.getLastName(),
				emp.getEmailId())).collect(Collectors.toList());
		
		return employees;
	}

	@Override
	public boolean deleteEmployee(Long id) {
		EmployeeEntity employee= employeeRepository.findById(id).get();
		employeeRepository.delete(employee);
		return true;
	}

	@Override
	public Employee getEmployeeByid(Long id) {
		
		EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeEntity, employee);
		
		return employee;
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		EmployeeEntity employeeEntity= employeeRepository.findById(id).get();
		employeeEntity.setEmailId(employee.getEmailId());
		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());
		
		employeeRepository.save(employeeEntity);
		return employee;
	}

	@Override
	public Employee createEmployeeForUser(Employee employee, String userEmail) {
         EmployeeEntity employeeEntity = new EmployeeEntity();
         
        userRepository.findByEmailId(userEmail);

		//UserEntity userEntity = new UserEntity();
        // employeeEntity.setUserEmail(user) ;
        employeeEntity.setUserEmail(userRepository.findByEmailId(userEmail));
		
		BeanUtils.copyProperties(employee, employeeEntity);
		employee.setUser(userRepository.findByEmailId(userEmail));
		
		employeeRepository.save(employeeEntity);
		return employee;
	}

	@Override
	public List<Employee> getAllEmployeesForUser(String emailId) {
		
		UserEntity userEntity = userRepository.findByEmailId(emailId);
		
		List<EmployeeEntity>employeeEnities = employeeRepository.findByUserEmail(userEntity);
		
		List<Employee> employees = employeeEnities.stream().map(emp -> new Employee(emp.getId(), emp.getFirstName(),emp.getLastName(),
				emp.getEmailId())).collect(Collectors.toList());
		
		return employees;
	}


	

}

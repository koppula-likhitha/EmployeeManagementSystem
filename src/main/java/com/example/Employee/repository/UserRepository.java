package com.example.Employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByEmailId(String emailId);

	//UserEntity findByUserEmail(String userEmail);


}

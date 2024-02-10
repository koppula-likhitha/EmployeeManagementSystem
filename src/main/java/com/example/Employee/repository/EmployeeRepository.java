package com.example.Employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.entity.UserEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>{



	List<EmployeeEntity> findByUserEmail(UserEntity userEntity);

}

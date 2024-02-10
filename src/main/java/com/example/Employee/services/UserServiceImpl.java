package com.example.Employee.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Employee.entity.UserEntity;
import com.example.Employee.model.User;
import com.example.Employee.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(User user) {
     UserEntity userEntity = new UserEntity();
        
		
		//BeanUtils.copyProperties(user, userEntity);
     
		userEntity.setEmailId(user.getEmailId());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setRole(user.getRole());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(userEntity);
		return user;
		
	}

}

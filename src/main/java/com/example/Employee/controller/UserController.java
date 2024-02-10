package com.example.Employee.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Employee.entity.UserEntity;
import com.example.Employee.model.Employee;
import com.example.Employee.model.JwtResponse;
import com.example.Employee.model.User;
import com.example.Employee.repository.UserRepository;
import com.example.Employee.services.UserService;
import com.example.Employee.utility.JWTUtility;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello Likhitha";
	}

	@PostMapping("/users")
	public User createEmployee(@RequestBody User user)
	{
		userService.createUser(user);
		return user;
		
	}
	
//	@PostMapping("/users/validateUser")
//	public ResponseEntity<String> validateUser(@RequestBody User user){
//		
//		
//		if(isValidUser(user))
//		{
//		return ResponseEntity.ok("Login Successful");
//		}
//		else
//		{
//			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//		}
//		
//	}
//
//	private boolean isValidUser(User user) {
//		
//		Optional<UserEntity> userEntity  =  userRepository.findByEmailId (user.getEmailId());
//		//.orElse(null);
//		
//		if(userEntity!=null)
//		return (passwordEncoder.matches(user.getPassword(),userEntity.getPassword()));
//		
//		return false;
//	}
	
	@PostMapping("/users/authenticate")
	public JwtResponse authenticateUser(@RequestBody User user) throws Exception
	{

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							user.getEmailId(),
							user.getPassword())
					);
		}
		catch(Exception e){
			throw new Exception("Invalid Credentials",e);
		}
		final String token= jwtUtility.generateToken(user);
		
		System.out.println(token);
		return new JwtResponse(token);
		
		
	}
}

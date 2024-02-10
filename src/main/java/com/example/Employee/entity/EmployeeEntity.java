package com.example.Employee.entity;

import java.util.Optional;

import com.example.Employee.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="employees")
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String emailId;
	@ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEmail;
	
	
	public EmployeeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeEntity(long id, String firstName, String lastName, String emailId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	
	public UserEntity getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(UserEntity user) {
		this.userEmail = user;
	}
	public EmployeeEntity(long id, String firstName, String lastName, String emailId, UserEntity userEmail) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.userEmail = userEmail;
	}
	
}


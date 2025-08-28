package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="User_Details")
public class User {
	
	public enum UserStatus {
	    ACTIVE,
	    INACTIVE,
	    DELETED,
	    BLOCKED
	}
	
  @Id
  @Column(name="userId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;
  
  @Column(name="name", length = 30)
  private String name;
  
  @Column(name="email", length = 40)
  private String email;
  
  @Column(name="phone", length = 10)
  private String phone;
  
  @Column(name="password", length = 100)
  private String password;
  
  @Column(name="address", length = 100)
  private String address;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20)
  private UserStatus status = UserStatus.ACTIVE;
  
  @Column(name = "member_since", updatable = false)
  private LocalDate memberSince;
  
  public User(int userid, String name, String email, String phone, String password, String address)
  {
	  this.userId = userid;
	  this.email = email;
	  this.phone = phone;
	  this.name = name;
	  this.password = password;
	  this.address = address;
	  this.status = UserStatus.ACTIVE;
	  this.memberSince = LocalDate.now();
  }
  
  public User(){
  }

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public UserStatus getStatus() {
    return status;
}

public void setStatus(UserStatus status) {
    this.status = status;
}

@Override
public String toString() {
	return "User [userid=" + userId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", password="
			+ password + ", address=" + address + "]";
}

public LocalDate getMemberSince() {
	return memberSince;
}

public void setMemberSince(LocalDate memberSince) {
	this.memberSince = memberSince;
}

@PrePersist
public void prePersist() {
    if (memberSince == null) {
        this.memberSince = LocalDate.now();
    }
}
  
}
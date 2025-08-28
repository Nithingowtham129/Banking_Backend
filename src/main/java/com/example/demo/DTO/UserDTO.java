package com.example.demo.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserDTO {

	  private int userid;
	  private String name;
	  private String email;
	  private String phone;
	  private String password;
	  private String address;
	  
	public UserDTO(int userid, String name, String email, String phone, String password, String address) {
		
		this.userid = userid;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
	}
	
	public UserDTO() {
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	@Override
	public String toString() {
		return "UserDTO [userid=" + userid + ", name=" + name + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", address=" + address + "]";
	}
	
}

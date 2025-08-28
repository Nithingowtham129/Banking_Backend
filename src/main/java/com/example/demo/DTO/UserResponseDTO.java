package com.example.demo.DTO;

import java.util.List;
import com.example.demo.entity.User.UserStatus;

public class UserResponseDTO {
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private UserStatus status;
    private String memberSince; // Not from DB
    private List<AccountDTO> accounts; // All user's accounts
    // Getters and Setters
    

    public UserResponseDTO(int userId, String name, String email, String phone, String address, UserStatus status, String memberSince, List<AccountDTO> accounts) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.memberSince = memberSince;
        this.accounts = accounts;
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
	
	public String getMemberSince() {
	    return memberSince;
	}

	public List<AccountDTO> getAccounts() {
	    return accounts;
	}

	public UserResponseDTO() {}
    
    // All getters and setters...
}

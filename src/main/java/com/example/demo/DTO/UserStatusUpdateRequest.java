package com.example.demo.DTO;

import com.example.demo.entity.User.UserStatus;

public class UserStatusUpdateRequest {
    private Integer userId;
    private UserStatus status;
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
    
}
package com.example.demo.services;

import com.example.demo.DTO.Logindto;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Response.Loginmessage;
import com.example.demo.entity.User.UserStatus;


public interface userService {

	String addUser(UserDTO userDTO);

	Loginmessage loginUser(Logindto logindto);
	
	String changePassword(String email, String newPassword);
	
	String updateUserStatus(Integer userId, UserStatus newStatus);
	
}

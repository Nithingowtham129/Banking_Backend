package com.example.demo.services;

import com.example.demo.DTO.DashboardDTO;
import com.example.demo.DTO.UserResponseDTO;

public interface DashboardService {
	
	DashboardDTO getDashboardData(Integer userId);
	UserResponseDTO getUserDetailsById(int userId);
	
	
}

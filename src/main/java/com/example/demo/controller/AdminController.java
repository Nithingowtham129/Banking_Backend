package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Logindto;
import com.example.demo.Response.LoginResponse;
import com.example.demo.config.JwtUtil;
import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.AdminService;

@RestController
@RequestMapping("/api/auth/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody Logindto loginRequest) {
	    Optional<Admin> adminOpt = adminRepository.findByEmailAndPassword(
	        loginRequest.getEmail(),
	        loginRequest.getPassword()
	    );

	    if (adminOpt.isPresent()) {
	        Admin admin = adminOpt.get();

	        String token = jwtUtil.generateToken(admin.getEmail());

	        return new LoginResponse(true, "Login successful", token);
	    } else {
	        return new LoginResponse(false, "Invalid credentials", null);
	    }
	}
	
	@PostMapping("/send-message")
    public ResponseEntity<String> sendMessageToAll(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        String subject = payload.get("subject");
        if ((message == null || message.trim().isEmpty()) && (subject == null || subject.trim().isEmpty())) {
            return ResponseEntity.badRequest().body("Message content is required.");
        }

        adminService.sendAnnouncementToAll(message,subject);
        return ResponseEntity.ok("Message sent to all users.");
    }
	
}

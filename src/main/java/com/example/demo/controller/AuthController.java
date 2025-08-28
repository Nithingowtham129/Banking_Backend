package com.example.demo.controller;


import java.security.SecureRandom;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ChangePasswordRequest;
import com.example.demo.DTO.Logindto;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Response.Loginmessage;
import com.example.demo.config.JwtUtil;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.EmailService;
import com.example.demo.services.userService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
    @Autowired private userService userService;
    
    @Autowired private EmailService emailService;
    
    @Autowired private UserRepository userRepository;
    
    @Autowired private JwtUtil jwtUtil;

    @Operation(summary="register a user")
    @PostMapping("/register")
    public String RegisterUser(@RequestBody UserDTO userDTO) {
        String id = userService.addUser(userDTO);
        return id;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Logindto logindto) {
        Loginmessage message = userService.loginUser(logindto);

        if (message.getMessage().equals("Login Success")) {
            String token = jwtUtil.generateToken(logindto.getEmail());

            return ResponseEntity.ok(Map.of(
                "message", "Login Success",
                "token", token,
                "userId", message.getUserId()
            ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "message", message.getMessage()
                    ));
        }
    }

    
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        
        
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        
        SecureRandom secureRandom = new SecureRandom();
        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
        otpStorage.put(email, otp);

        emailService.sendOtpEmail(email, otp);
        
        return ResponseEntity.ok("OTP sent successfully");
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String inputOtp = request.get("otp");

        String storedOtp = otpStorage.get(email);

        if (storedOtp != null && storedOtp.equals(inputOtp)) {
            otpStorage.remove(email); 
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }
    
    @PostMapping("/passwordReset/send-otp")
    public ResponseEntity<String> passwordResetsendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        
        if (userRepository.existsByEmail(email)) {
        	SecureRandom secureRandom = new SecureRandom();
            String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
            otpStorage.put(email, otp);

             emailService.forgotPasswordEmail(email, otp);
             
             return ResponseEntity.ok("OTP sent successfully");
        }
        else
        {
        	return ResponseEntity.badRequest().body("Email does not exist");
        }
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        String result = userService.changePassword(request.getEmail(), request.getNewPassword());

        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }
    
}


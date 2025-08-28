package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Logindto;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Response.Loginmessage;
import com.example.demo.entity.User;
import com.example.demo.entity.User.UserStatus;
import com.example.demo.repository.UserRepository;

import com.example.demo.services.EmailService;
import com.example.demo.services.userService;


@Service
public class Userimpl implements userService{

    @Autowired private UserRepository userRepository;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private EmailService emailService;
	
	@Override
	public String addUser(UserDTO userDTO)
	{
		if (userRepository.existsByEmail(userDTO.getEmail())) {
	        return "Email already registered";
	    }
		
		User user = new User(
				userDTO.getUserid(),
				userDTO.getName(),
				userDTO.getEmail(),
				userDTO.getPhone(),
				this.passwordEncoder.encode(userDTO.getPassword()),
				userDTO.getAddress()
				);
		
		userRepository.save(user);
		
		
		emailService.sendUserRegistrationEmail(user.getEmail(), user.getName());
		
		return "User registered successfully.";
	}
	
	@Override
	public Loginmessage loginUser(Logindto logindto) {
	    Optional<User> optionalUser = userRepository.findByEmail(logindto.getEmail());

	    if (optionalUser.isEmpty()) {
	        return new Loginmessage("Email does not exist", false, null);
	    }

	    User user1 = optionalUser.get();

	    System.out.println("RAW: " + logindto.getPassword());
	    System.out.println("ENCODED: " + user1.getPassword());
	    System.out.println("MATCH RESULT: " + passwordEncoder.matches(logindto.getPassword(), user1.getPassword()));

	    String password = logindto.getPassword();
	    String encodedPassword = user1.getPassword();
	    boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

	    if (isPwdRight) {
	        Optional<User> user = userRepository.findOneByEmailAndPassword(logindto.getEmail(), encodedPassword);
	        if (user.isPresent()) {
	            return new Loginmessage("Login Success", true, user1.getUserId());
	        } else {
	            return new Loginmessage("Login Failed", false, null);
	        }
	    } else {
	        return new Loginmessage("Password does not match", false, null);
	    }
	}

	
	@Override
	public String changePassword(String email, String newPassword) {
	    Optional<User> optionalUser = userRepository.findByEmail(email);

	    if (optionalUser.isEmpty()) {
	        return "User with the provided email not found.";
	    }

	    User user = optionalUser.get();
	    String hashedPassword = passwordEncoder.encode(newPassword);
	    user.setPassword(hashedPassword);
	    userRepository.save(user);

	    return "Password updated successfully.";
	}

	 
	 @Override
	    public String updateUserStatus(Integer userId, UserStatus newStatus) {
	        Optional<User> optionalUser = userRepository.findById(userId);

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            user.setStatus(newStatus);
	            userRepository.save(user);
	            return "User status updated to " + newStatus;
	        } else {
	            throw new RuntimeException("User not found with ID: " + userId);
	        }
	    }
	
}

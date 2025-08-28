package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private EmailService emailService;
	
	public void sendAnnouncementToAll(String messageBody, String subject) {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            emailService.sendEmail(user.getEmail(), subject, messageBody);
        }
    }

}

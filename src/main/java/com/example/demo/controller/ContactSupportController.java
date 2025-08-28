package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.SupportRequest;
import com.example.demo.repository.SupportRequestRepository;
import com.example.demo.services.EmailService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/userRequest")
public class ContactSupportController {

    @Autowired
    private SupportRequestRepository supportRepo;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/contact-support")
    public ResponseEntity<String> contactSupport(@RequestBody SupportRequest request) {
        request.setCreatedAt(java.time.LocalDateTime.now());
        emailService.sendSupportEmail(request.getName(), request.getEmail(), request.getDescription());
        supportRepo.save(request);
        return ResponseEntity.ok("Support request submitted successfully.");
    }
}


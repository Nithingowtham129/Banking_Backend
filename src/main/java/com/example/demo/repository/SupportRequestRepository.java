package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SupportRequest;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Long> {
    
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4200") 
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    
    @GetMapping("/credit-monthly/{userId}")
    public ResponseEntity<Double> getMonthlyCredits(@PathVariable Integer userId) {
        Double amount = accountService.getMonthlyCreditedAmountByUserId(userId);
        return ResponseEntity.ok(amount);
    }
    
    @GetMapping("/debit-monthly/{userId}")
    public ResponseEntity<Double> getMonthlyDebits(@PathVariable Integer userId) {
        Double amount = accountService.getMonthlyDebitedAmountByUserId(userId);
        return ResponseEntity.ok(amount);
    }
}
package com.example.demo.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.AccountStatusUpdateRequest;
import com.example.demo.DTO.DashboardDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserStatusUpdateRequest;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.DashboardService;
import com.example.demo.services.TransactionService;
import com.example.demo.services.userService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Integer userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }
    
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable int userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/admin/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
    	List<Account> accounts = accountService.getAllAccounts();
    	return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    
    @GetMapping("/admin/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
    	List<Transaction> transaction = transactionService.getAllTransaction();
    	return ResponseEntity.ok(transaction);
    }
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/admin/update-account-status")
    public ResponseEntity<String> updateStatus(@RequestBody AccountStatusUpdateRequest request) {
        String response = accountService.updateAccountStatus(request.getAccountId(), request.getStatus());
        return ResponseEntity.ok(response);
    }
    
    @Autowired
    private userService userservice;
    
    @PostMapping("/admin/update-user-status")
    public ResponseEntity<String> updateUserStatus(@RequestBody UserStatusUpdateRequest request) {
        String result = userservice.updateUserStatus(request.getUserId(), request.getStatus());
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/profile")
    public ResponseEntity<?> getUserById(@RequestBody Map<String, Integer> payload) {
        int userId = payload.get("userId");

        UserResponseDTO dto = dashboardService.getUserDetailsById(userId);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
    
}

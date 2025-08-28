package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.TransactionDTO;
import com.example.demo.DTO.TransferRequest;
import com.example.demo.services.AccountService;
import com.example.demo.services.TransactionService;


@CrossOrigin
@RestController
@RequestMapping("/api/accounts")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO dto) {
        String result = transactionService.processTransaction(dto);
        if (result.equals("Transaction successful.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @Autowired
    private AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest request) {
        try {
            accountService.transferFunds(request);
            return ResponseEntity.ok("Transfer successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transfer failed");
        }
    }
    
}

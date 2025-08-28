package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.TransferRequest;
import com.example.demo.Exceptions.DuplicateAccountTypeException;
import com.example.demo.entity.Account;
import com.example.demo.entity.Account.AccountStatus;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private EmailService emailService;

    public Account createAccount(Account account) {
    	
        // fetch user from DB using the ID
        Integer userId = account.getUser().getUserId();
        String accountType = account.getAccountType();
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
        	
        	boolean exists = accountRepository.existsByUserUserIdAndAccountType(userId, accountType);
        	
        	if (exists) {
                throw new DuplicateAccountTypeException("you already have a " + accountType + " account.");
            }
        	
        	User user = userOpt.get();
            emailService.sendAccountCreationEmail(user.getEmail(), user.getName(), accountType);
        	
            account.setUser(userOpt.get());
            account.setStatus(AccountStatus.ACTIVE);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    
    public Double getMonthlyCreditedAmountByUserId(Integer userId) {
        Double creditedAmount = accountRepository.getThisMonthCreditedAmount(userId);
        return creditedAmount != null ? creditedAmount : 0.0;
    }
    
    public Double getMonthlyDebitedAmountByUserId(Integer userId) {
        Double debitedAmount = accountRepository.getThisMonthDebitedAmount(userId);
        return debitedAmount != null ? debitedAmount : 0.0;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    @Transactional
    public void transferFunds(TransferRequest request) {
    	
        Account fromAcc = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("From Account not found"));

        Account toAcc = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Recepient Account number Does not exist in out Database."));

        double amount = request.getAmount();

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if (fromAcc.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        // Update account balances
        fromAcc.setBalance(fromAcc.getBalance() - amount);
        toAcc.setBalance(toAcc.getBalance() + amount);

        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);

        Transaction debitTx = new Transaction();
        debitTx.setAccount(fromAcc);
        debitTx.setAmount(amount);
        debitTx.setType("debit");
        debitTx.setDescription("Transferred to Account: " + toAcc.getAccountNumber());
        debitTx.setDate(LocalDate.now());

        Transaction creditTx = new Transaction();
        creditTx.setAccount(toAcc);
        creditTx.setAmount(amount);
        creditTx.setType("credit");
        creditTx.setDescription("Received from Account: " + fromAcc.getAccountNumber());
        creditTx.setDate(LocalDate.now());

        transactionRepository.save(debitTx);
        transactionRepository.save(creditTx);
    }
    
    public String updateAccountStatus(Long accountId, AccountStatus newStatus) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setStatus(newStatus);
            accountRepository.save(account);
            return "Status updated to " + newStatus;
        } else {
            throw new RuntimeException("Account not found with ID: " + accountId);
        }
    }
    
}
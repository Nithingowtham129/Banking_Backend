package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.TransactionDTO;
import com.example.demo.Exceptions.AccountNotFoundException;
import com.example.demo.Exceptions.InsufficientBalanceException;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private EmailService emailservice;

    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.findByAccountUserUserIdOrderByIdAsc(userId);
    }
    

    public String processTransaction(TransactionDTO dto) {
    	
    	
        Account account = accountRepo.findById(dto.getAccountId())
                                     .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        String status = transactionRepository.findStatusByAccountId(account.getId());
 
        if(!"ACTIVE".equals(status))
        {
        	System.out.println("Your Account is not Active, Contact Support !!");
        	return "Your Account is not Active, Contact Support !!";
        }

        if (dto.getType().equalsIgnoreCase("Debit")) {
            if (account.getBalance() < dto.getAmount()) {
            	return "Insufficient balance";
            }
            account.setBalance(account.getBalance() - dto.getAmount());
        } else if (dto.getType().equalsIgnoreCase("Credit")) {
            account.setBalance(account.getBalance() + dto.getAmount());
        } else {
            return "Invalid transaction type.";
        }

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(LocalDate.now());
        transaction.setAccount(account);

        transactionRepo.save(transaction);
        accountRepo.save(account);
        
        String userEmail = account.getUser().getEmail();
        String accountType = account.getAccountType();
        
        emailservice.transactionEmail(userEmail, dto.getAmount(), dto.getType(), accountType);

        return "Transaction successful.";
    }


	public List<Transaction> getAllTransaction() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}
}
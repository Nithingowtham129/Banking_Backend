package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.AccountDTO;
import com.example.demo.DTO.DashboardDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired private UserRepository userRepo;
    @Autowired private AccountRepository accountRepo;
    @Autowired private TransactionRepository transactionRepo;
    @Autowired
    private UserRepository userRepository;

    public DashboardDTO getDashboardData(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<Account> accounts = accountRepo.findByUserUserId(userId);
        List<Transaction> transactions = transactionRepo.findByAccountUserUserId(userId);

        DashboardDTO dto = new DashboardDTO();
        dto.setUserName(user.getName());
        dto.setAccounts(accounts);
        dto.setTransactions(transactions);

        return dto;
    }
    
    @Override
    public UserResponseDTO getUserDetailsById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            String memberSince = user.getMemberSince() != null ? user.getMemberSince().toString() : null;
            
            List<AccountDTO> accountDTOs = accountRepo.findByUserUserId(userId)
                    .stream()
                    .map(account -> new AccountDTO(
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance()
                    ))
                    .collect(Collectors.toList());

            return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getStatus(),
                memberSince,
                accountDTOs
            );
        }

        return null;
    }

}


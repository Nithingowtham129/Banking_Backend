package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUserUserId(int userId);
	
	Optional<Account> findById(Long id);
	
	Optional<Account> findByAccountNumber(String accountNumber);
	
	boolean existsByUserUserIdAndAccountType(Integer userId, String accountType);
	
	 @Query(value = "SELECT sum(t.amount) FROM transaction t JOIN account a ON(t.account_id = a.id) WHERE type = 'credit' and a.userid = :userId AND MONTH(t.date) = MONTH(CURRENT_DATE())\r\n"
	 		+ "      AND YEAR(t.date) = YEAR(CURRENT_DATE())", nativeQuery = true)
	 Double getThisMonthCreditedAmount(@Param("userId") int userId);
	 
	 @Query(value = "SELECT sum(t.amount) FROM transaction t JOIN account a ON(t.account_id = a.id) WHERE type = 'debit' and a.userid = :userId AND MONTH(t.date) = MONTH(CURRENT_DATE())\r\n"
		 		+ "      AND YEAR(t.date) = YEAR(CURRENT_DATE())", nativeQuery = true)
		 Double getThisMonthDebitedAmount(@Param("userId") int userId);

}
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccountUserUserId(int userId);
	List<Transaction> findByAccountUserUserIdOrderByIdAsc(int userId);
	
	@Query(value="Select status from account where id = ?1", nativeQuery=true)
	String findStatusByAccountId(Long accountId);
}

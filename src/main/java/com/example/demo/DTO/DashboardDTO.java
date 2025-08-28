package com.example.demo.DTO;

import java.util.List;

import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;

public class DashboardDTO {
    private String userName;
    private List<Account> accounts;
    private List<Transaction> transactions;

    // Constructors, Getters, Setters
    
    public DashboardDTO ()
    {
    	
    }
    
    public DashboardDTO(String userName, List<Account> accounts, List<Transaction> transactions) {
        this.userName = userName;
        this.accounts = accounts;
        this.transactions = transactions;
    }


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
    
}

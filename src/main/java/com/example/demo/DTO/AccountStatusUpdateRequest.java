package com.example.demo.DTO;

import com.example.demo.entity.Account.AccountStatus;

public class AccountStatusUpdateRequest {
    private Long accountId;
    private AccountStatus status;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}

    
}
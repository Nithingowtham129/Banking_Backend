package com.example.demo.DTO;

public class TransactionDTO {
    private String type;
    private Double amount;
    private String description;
    private Long accountId;
    
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}

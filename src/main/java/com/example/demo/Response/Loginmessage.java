package com.example.demo.Response;


public class Loginmessage {

    private String message;
    private boolean status;
    private Integer userId;

    // Constructor
    public Loginmessage(String message, boolean status, Integer userId) {
        this.message = message;
        this.status = status;
        this.setUserId(userId);
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

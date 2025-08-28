package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "support_request")
public class SupportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=15)
    private String name;

    @Column(name="email", length=30)
    private String email;

    @Column(name="description", length = 1000)
    private String description;

    @Column(name = "datetime")
    private java.time.LocalDateTime createdAt;

    public SupportRequest() {}

    public SupportRequest(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.createdAt = java.time.LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.time.LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.time.LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
}

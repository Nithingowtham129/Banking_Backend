package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findOneByEmailAndPassword(String email, String Password);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<User> findById(Integer userId);
}

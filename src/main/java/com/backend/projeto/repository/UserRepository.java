package com.backend.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.projeto.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String username);

}

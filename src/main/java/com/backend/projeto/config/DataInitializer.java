package com.backend.projeto.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.projeto.entity.Roles;
import com.backend.projeto.entity.User;
import com.backend.projeto.repository.RoleRepository;
import com.backend.projeto.repository.UserRepository;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			this.createUsers("Felipe", "Felipe123@gmail.com", passwordEncoder.encode("123"), "ROLE_ALUNO");
			this.createUsers("ADMIN", "admin@gmail.com", passwordEncoder.encode("admin"), "ROLE_ADMIN");

		}

	}

	public void createUsers(String nome, String email, String pass, String role) {
		
		Roles roles = new Roles();
		roles.setName(role);
		
		this.roleRepository.save(roles);
		
		User user = new User(nome, email, pass, Arrays.asList(roles));
		userRepository.save(user);
		
		
	}

}

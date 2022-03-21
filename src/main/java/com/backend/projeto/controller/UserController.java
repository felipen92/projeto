package com.backend.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.projeto.entity.User;
import com.backend.projeto.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping
	public List<User> listUsers() {
		return userRepository.findAll(); 
	}

//	@GetMapping("/{id}")
//	public Optional<User> detailUsers(@PathVariable Long id) {
//		return userRepository.findById(id);
//	}
//
//	@GetMapping
//	public Page<User> listUsersPage(@RequestParam("page") int page, @RequestParam("size") int size) {
//
//		return userRepository.findAll(PageRequest.of(page, size));
//	}

	@PostMapping
	public User saveUsers(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping
	public User editUsers(@RequestBody User user) {
		return userRepository.save(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUsers(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

}

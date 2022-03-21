package com.backend.projeto.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Roles> roles;

	public User() {

	}
	
	public User(User user) {
		super();
		this.name = user.getName();
		this.email = user.getEmail();
		this.roles = user.getRoles();
		this.password = user.getPassword();
	}

	public User(String name, String email, String password, List<Roles> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

}

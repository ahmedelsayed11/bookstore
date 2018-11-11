package com.domains.security;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.domains.users;

@Entity
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private users user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
	//Default Construcor
	public UserRole() {}
	
	public UserRole(users user , Role role)
	{
		this.user = user;
		this.role = role;
	}

	//Getter And setter 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}














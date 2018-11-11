package com.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.domains.users;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<users , Long> {

	users findByUsername(String username);
	
	users findByEmail(String email);

	
	
}

package com.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.domains.book;

@EnableJpaRepositories
public interface BookRepository extends JpaRepository<book, Long> {
	
	List<book> findAll();

	
	
	
}

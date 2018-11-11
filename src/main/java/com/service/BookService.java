package com.service;

import java.util.List;
import java.util.Optional;

import com.domains.book;


public interface BookService {
	
	List<book> findAll();

	Optional<book> findOne(Long id);

}

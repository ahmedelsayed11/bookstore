package com.services.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domains.book;
import com.repositories.BookRepository;
import com.service.BookService;

@Service
public class BookServiceImp implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<book> findAll() {
		
		return  bookRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<book> findOne(Long id) {
		  
		Optional<book> book =  bookRepository.findById(id);
		 if(book.isPresent()) {
			 return book;
		 }
		return null;
		   
	}

	

}

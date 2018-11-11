package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domains.UserPayment;

public interface UserPaymentRepository  extends CrudRepository<UserPayment, Long>{
	
	
}

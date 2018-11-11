package com.service;

import java.util.Optional;

import com.domains.UserPayment;

public interface UserPaymentService {
	
	Optional<UserPayment> findById(Long id);

	void deleteById(Long id);

}

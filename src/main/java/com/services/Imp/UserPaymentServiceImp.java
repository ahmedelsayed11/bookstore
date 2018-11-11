package com.services.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domains.UserPayment;
import com.repositories.UserPaymentRepository;
import com.service.UserPaymentService;

@Service
public class UserPaymentServiceImp implements UserPaymentService {

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Override
	public Optional<UserPayment> findById(Long id) {
		Optional<UserPayment> userPayment = userPaymentRepository.findById(id);
		return userPayment ;
	}

	@Override
	public void deleteById(Long id) {
		
		userPaymentRepository.deleteById(id);
	}

}

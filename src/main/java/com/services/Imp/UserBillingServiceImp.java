package com.services.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domains.UserBilling;
import com.repositories.UserBillingRepository;
import com.service.UserBillingService;

@Service
public class UserBillingServiceImp implements UserBillingService {

	@Autowired
	private UserBillingRepository userBillingRepository;

	@Override
	public Optional<UserBilling> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

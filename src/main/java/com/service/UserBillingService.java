package com.service;

import java.util.Optional;

import com.domains.UserBilling;

public interface UserBillingService {
	
	Optional<UserBilling> findById(Long id);

}

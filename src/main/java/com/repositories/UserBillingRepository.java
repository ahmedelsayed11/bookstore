package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.domains.UserBilling;

@Repository
public interface UserBillingRepository extends CrudRepository<UserBilling, Long> {
	
	

}

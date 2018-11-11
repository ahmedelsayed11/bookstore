package com.service;

import java.util.Set;

import com.domains.UserBilling;
import com.domains.UserPayment;
import com.domains.UserShipping;
import com.domains.users;
import com.domains.security.PasswordResetToken;
import com.domains.security.UserRole;

public interface UserService {
	
	 PasswordResetToken getPasswordResetToken(final String token);
	 
	 void createPasswordResetTokenForUser(final users user , String token);

	users findByUsername(String username); 
	users findByEmail(String email);

	users createUser(users user, Set<UserRole> userRoles) throws Exception;

	users save(users user);
	
	void saveUserBillingAndPayment(UserBilling userBilling ,UserPayment userPayment ,users user);

	void setPaymentDefault(Long id, users user);

	void saveUserShipping(UserShipping userShipping, users user);

	

	 

}

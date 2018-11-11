package com.services.Imp;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domains.UserBilling;
import com.domains.UserPayment;
import com.domains.UserShipping;
import com.domains.users;
import com.domains.security.PasswordResetToken;
import com.domains.security.UserRole;
import com.repositories.RoleRepository;
import com.repositories.UserPaymentRepository;
import com.repositories.UserRepository;
import com.repositories.passwordResetTokenRepository;
import com.service.UserService;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	passwordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Autowired 
	private RoleRepository  roleRepository;
	

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(final users user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(user, token);
		passwordResetTokenRepository.save (myToken);
		
		
	}

	@Override
	public users findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public users findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public users createUser(users user, Set<UserRole> userRoles) throws Exception {
		
		users localUser = userRepository.findByUsername(user.getUsername());
		 if(localUser != null) {
			 throw new Exception ("User already Exists");
			 
		 }else {
			 
			 for(UserRole ur : userRoles) {
				 roleRepository.save(ur.getRole());
			 }
			 
			 user.getUserRoles().addAll(userRoles);
			 
			 localUser = userRepository.save(user);
					 
			 
		 }
		 
		 return localUser;
	
	}

	@Override
	public users save(users user) {
		return userRepository.save(user);
	}

	@Override
	public void saveUserBillingAndPayment(UserBilling userBilling, UserPayment userPayment, users user) {
		 
		 userPayment.setUser(user);
		 userPayment.setUserBilling(userBilling);
		 userPayment.setDefaultPayment(true);
		 userBilling.setUserPayment(userPayment);
		 user.getUserPaymentList().add(userPayment);
		 
		 save(user);
		
	}

	@Override
	public void setPaymentDefault(Long id, users user) {
			List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
			 for( UserPayment userPayment : userPaymentList) {
				 
				 if(userPayment.getId() == id) {
					 userPayment.setDefaultPayment(true);
					 userPaymentRepository.save(userPayment);
				 }else {
					 userPayment.setDefaultPayment(false);
					 userPaymentRepository.save(userPayment);
				 }
			 }
		
	}

	@Override
	public void saveUserShipping(UserShipping userShipping, users user) {
		
		userShipping.setUser(user);
		userShipping.setDefaultShipping(true);
		user.getUserShippingList().add(userShipping);
		
		save(user);
	
	}

	

	
	 

}

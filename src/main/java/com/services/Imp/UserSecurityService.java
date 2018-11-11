package com.services.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domains.users;
import com.repositories.UserRepository;


@Service
@ComponentScan(basePackages="com.repositories")
public class UserSecurityService implements UserDetailsService {

	@Autowired
	 private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		users user = userRepository.findByUsername(username);
		
		 if(null == user) {
			 throw new  UsernameNotFoundException("User not Found");
			 
		 }
		 else {
			 return user;
		 }
		
	}
	
	

}

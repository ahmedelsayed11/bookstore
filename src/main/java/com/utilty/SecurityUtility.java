package com.utilty;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {
	 
	
	private static final String SALT = "salt"; // should be secure or more secure
	
	  @Bean
	   public static BCryptPasswordEncoder passwordEncoder() {
		  
		  return new BCryptPasswordEncoder(12 , new SecureRandom(SALT.getBytes()));
	  } 

	   
	  @Bean
	   public static String randomPassword() {
		  String SALTCHAR = "ANCBFJNDIKHFJIN1251541254SDFFFJKDIMD";
		   StringBuilder salt = new StringBuilder(); 
		    Random rnd = new Random();
		    
		     while(salt.length()<18) {
		    	 int index = (int) (rnd.nextFloat()*SALTCHAR.length());
		    	 salt.append(SALTCHAR.charAt(index));
		     }
		  
		  String SaltStr = salt.toString();
		   return SaltStr;
		  
		  
	  }
}

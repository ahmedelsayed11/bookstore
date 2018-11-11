package com.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.domains.users;
import com.domains.security.PasswordResetToken;

@EnableJpaRepositories
public interface passwordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
 
	 PasswordResetToken findByToken(String token) ;
	
	 
	 PasswordResetToken findByUser(users user);
	 
	   // this for find all expiry date in the past
	 Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	 
	 //this for deleting the past expiration
	 @Modifying
	  @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
	 
	 void deleteAllExpiryDate(Date now);
	 
	 
}

package com.domains.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.domains.users;

@Entity
public class PasswordResetToken {
	
       //this for reset password time
	 private static final int EXPIRATION = 60 * 24 ;
	  
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	   private Long id;
	  
	  private String token;
	   
	  // make the relation between user and password token
	  @OneToOne(targetEntity = users.class , fetch = FetchType.EAGER)
	  @JoinColumn(nullable = false , name="user_id")
	  private users user;
	  
	  //define a date field
	  private Date expiryDate;
	  
	  public PasswordResetToken() {}
	  
	  //making constructor
	    public PasswordResetToken(final users user , final String token)
	    {
	    	this.user = user;
	    	this.token = token;
	    	this.expiryDate = calculateExpiryDate(EXPIRATION);
	    }

	    
	    //method to calculate date in minutes
		private Date calculateExpiryDate(final int expiryTimeInMinutes) {
			final Calendar cal =  Calendar.getInstance();
			cal.setTimeInMillis(new Date().getTime());
			 cal.add(Calendar.MINUTE, expiryTimeInMinutes);
			return new Date(cal.getTime().getTime());
		}
		
		//make a method to update Token
		 public void updateToken(final String token) {
			 this.token = token;
			 this.expiryDate = calculateExpiryDate(EXPIRATION);
		 }


		    // Will make getter and setter for the fields
		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getToken() {
			return token;
		}


		public void setToken(String token) {
			this.token = token;
		}


		public users getUser() {
			return user;
		}


		public void setUser(users user) {
			this.user = user;
		}


		public Date getExpiry() {
			return expiryDate;
		}


		public void setExpiry(Date expiry) {
			this.expiryDate = expiry;
		}


		public static int getExpiration() {
			return EXPIRATION;
		}


		 //make ToString Method

		@Override
		public String toString() {
			return "PasswordResetToken [id=" + id + ", token=" + token + ", user=" + user + ", expiry=" + expiryDate + "]";
		}
	  
}

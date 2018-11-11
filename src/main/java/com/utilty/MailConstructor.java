package com.utilty;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.domains.users;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;

	public SimpleMailMessage constructResetToken(String appUrl, Locale locale, String token, users user,
			String password) {
		
		String url = appUrl + "/newAccount?token="+token;
		String message = "\n Please click on the link to varify your Email and edit yout personal information and your password is: "
				+ password;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Le`s Book Store - New User");
		email.setText(url + message);
		email.setFrom(env.getProperty("support.email"));

		return email;
	}

}

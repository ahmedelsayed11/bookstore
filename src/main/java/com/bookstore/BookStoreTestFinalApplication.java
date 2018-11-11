package com.bookstore;


/*
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*import com.domains.users;
import com.domains.security.Role;
import com.domains.security.UserRole;
import com.service.UserService;
import com.utilty.SecurityUtility;*/



@SpringBootApplication
@ComponentScan(basePackages = "com/**")
@EntityScan("com.domains/**")
@EnableJpaRepositories(basePackages="com.repositories" )

public class BookStoreTestFinalApplication /*implements CommandLineRunner */{
	


	public static void main(String[] args) {
		SpringApplication.run(BookStoreTestFinalApplication.class, args);
	}
	
	/*@Autowired
	private SecurityUtility securityUtility;
	
	@Autowired
	private UserService userService;*/
	

	/*@Override
	public void run(String... args) throws Exception {
		users user = new users();
		user.setFirstName("Ahmed");
		 user.setLastName("Elsyed");
		 user.setEmail("a7med2022@gmail.com");
		 user.setUsername("Ahmed");
		 user.setPassword(securityUtility.passwordEncoder().encode("p"));
		 Set<UserRole> userRole = new HashSet<>();
		  Role role = new Role();
		   role.setId(1);
		   role.setName("ROLE_USER");
		  userRole.add(new UserRole(user , role));
		  userService.createUser(user , userRole);
		
		
	}*/
	
	
	
}

package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.services.Imp.UserSecurityService;
import com.utilty.SecurityUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.services.Imp")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @SuppressWarnings("unused")
	@Autowired
	  private Environment env;
	 
	 @Autowired
	  private UserSecurityService userSecurityService;
	 
	 private BCryptPasswordEncoder passwordEncoder()
	 {
		 return SecurityUtility.passwordEncoder();
	 }
	  
	 public static final String[] PUBLIC_MATCHERS = {
			 
			 "/css/**" ,
			 "/js/**" , 
			 "/image/**" , 
			 "/fonts/**" ,
			 "/" , 
			 "/newUser",
			 "/forgetPassword" ,
			 "/bookShelf",
			  "/login",
			  "/logout",
			  "/bookDetails/**"
			  
	 } ;
	 
	 @Override
	  protected void configure(HttpSecurity http) throws Exception{
		 http
		     .authorizeRequests().
		       /* antMatchers("/") */ 
		     antMatchers(PUBLIC_MATCHERS)
		       .permitAll().anyRequest().authenticated();
		 
		 http
		       .csrf().disable().cors().disable().authorizeRequests().
		          and().formLogin().failureUrl("/login?error").
		              defaultSuccessUrl("/").
		            loginPage("/login").permitAll()
		             .and()
		              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		               .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
		               .and()
		                .rememberMe()
		                .and()
		                   .exceptionHandling()
		                      .accessDeniedPage("/access-denied");
	           }
	 
	 @Autowired
	  public void configureGlobale(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
		 
	 }
	 
}

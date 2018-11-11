package com.domains;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.domains.security.Authority;
import com.domains.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class users implements UserDetails {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// make the fields
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id" , nullable = false , updatable = false)
	private Long id;
	 
     @Pattern(regexp="^[a-zA-Z0-9]+(?:[_ -]?[a-zA-Z0-9])*$")
	private String username;
	 
	 
	 private String  password;
	private String firstName;
	private String lastName;
	
	 @Column(name="email" , nullable = false , updatable = false)
	private String email;
	 
	private String phone;
	private boolean enabled  = true;
	
	@OneToMany(mappedBy="user" , cascade = CascadeType.ALL , fetch= FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
	private List<UserShipping> UserShippingList;
	
	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, mappedBy = "user")
	private List<UserPayment> UserPaymentList;
	
	//setter and getter

	public Long getId() {
		return id;
	}
	public List<UserShipping> getUserShippingList() {
		return UserShippingList;
	}
	public void setUserShippingList(List<UserShipping> userShippingList) {
		UserShippingList = userShippingList;
	}
	public List<UserPayment> getUserPaymentList() {
		return UserPaymentList;
	}
	public void setUserPaymentList(List<UserPayment> userPaymentList) {
		UserPaymentList = userPaymentList;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	  public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	  
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	//toString Method
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", enabled=" + enabled + "]";
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 Set<GrantedAuthority> authorities = new HashSet<>();
		 userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		 
		return authorities;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	  //toString Method
	
	 
	
	
	

}

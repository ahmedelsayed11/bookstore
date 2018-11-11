package com.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.domains.UserBilling;
import com.domains.UserPayment;
import com.domains.UserShipping;
import com.domains.book;
import com.domains.users;
import com.domains.security.PasswordResetToken;
import com.domains.security.Role;
import com.domains.security.UserRole;
import com.service.BookService;
import com.service.UserBillingService;
import com.service.UserPaymentService;
import com.service.UserService;
import com.services.Imp.UserSecurityService;
import com.utilty.EGConstant;
import com.utilty.MailConstructor;
import com.utilty.SecurityUtility;

@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private UserBillingService userBillingService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String Login(Model model) {
		model.addAttribute("classActiveLogin", true);

		return "myAccount";
	}

	@PostMapping("/newUser")
	public String NewUserPost(HttpServletRequest request,@Valid@ModelAttribute("user") users user1 ,BindingResult bindResult ,  @RequestParam("username") String username,
			  @RequestParam("email") String email, Model model) throws Exception {

		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		if(bindResult.hasErrors()) {
			
			model.addAttribute("patterns" , true);
			return "myAccount";
		}

		if (userService.findByUsername(username) != null) {

			model.addAttribute("UserNameExists", true);

			return "myAccount";
		}
		if (userService.findByEmail(email) != null) {

			model.addAttribute("EmailExists", true);
			return "myAccount";
		}

		users user = new users();
		user.setUsername(username);
		user.setEmail(email);

		String password = SecurityUtility.randomPassword();
		String bCryptPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(bCryptPassword);

		Role role = new Role();

		role.setId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();

		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();

		userService.createPasswordResetTokenForUser(user, token);

		try {
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			SimpleMailMessage useremail = mailConstructor.constructResetToken(appUrl, request.getLocale(), token, user,
					password);
			mailSender.send(useremail);
			model.addAttribute("EmailSent", true);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("NotSent", true);
		}

		return "myAccount";
	}

	@RequestMapping("/newAccount")
	public String newUser(Locale local, @RequestParam("token") String token, Model model) {

		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid User";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		users user = passToken.getUser();

		String userName = user.getUsername();
		UserDetails userDetails = userSecurityService.loadUserByUsername(userName);
		Authentication authenication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenication);
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}

	@RequestMapping("/forgetPassword")
	public String ForgetPassword(HttpServletRequest request, @RequestParam("email") String email, Model model) {

		model.addAttribute("classActiveForgetPassword", true);

		users user = userService.findByEmail(email);
		if (user == null) {
			model.addAttribute("EmailNotExists", true);

			return "myAccount";

		}

		String password = SecurityUtility.randomPassword();
		String bCryptPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(bCryptPassword);

		userService.save(user);

		String token = UUID.randomUUID().toString();

		userService.createPasswordResetTokenForUser(user, token);

		try {
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			SimpleMailMessage useremail = mailConstructor.constructResetToken(appUrl, request.getLocale(), token, user,
					password);
			mailSender.send(useremail);
			model.addAttribute("ForgetEmailSent", true);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("NotSent", true);
		}

		return "myAccount";
	}

	@GetMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		users user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
		model.addAttribute("userShippingtList" , user.getUserShippingList());
		//model.addAttribute("orederList" , user.getOrderList());
		
		
		
	/*	UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping" ,  userShipping);*/
		
		model.addAttribute("listOfCreditsCards", true);
		model.addAttribute("listOfShippingAddresse", true);


		
		List<String> stateList = EGConstant.ListOfStatesCodes;
		Collections.sort(stateList);
		model.addAttribute("stateList" , stateList);
		
		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}
	
	@RequestMapping("/listOfCreditsCards")
	public String ListOfCreditCard( 
			 Model model , Principal principal , HttpServletRequest request
			) {
		users user = userService.findByUsername(principal.getName());
		model.addAttribute("user" , user);
		
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
		model.addAttribute("userShippingList" , user.getUserShippingList());
		//model.addAttribute("orederList" , user.getOrderList());
		model.addAttribute("listOfCreditsCards", true);
		model.addAttribute("classActiveBilling", true);
    	model.addAttribute("listOfShippingAddresse" , true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfShippingAddresse")
	public String ListOfShippingAddresses( 
			 Model model , Principal principal , HttpServletRequest request
			) {
		users user = userService.findByUsername(principal.getName());
		model.addAttribute("user" , user);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresse", true);
		model.addAttribute("listOfCreditsCards" , true);
		model.addAttribute("userShippingList" , user.getUserShippingList());
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
//      model.addAttribute("orederList" , user.getOrderList());
		
		
		return "myProfile";
	}
	
	@GetMapping("/addNewCreditsCards")
	public String addNewCreditCard(
			Model model , Principal principal , HttpServletRequest request
			) {
		users user = userService.findByUsername(principal.getName());
		model.addAttribute("user" , user);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("addNewCreditsCards", true);
		/*model.addAttribute("listOfShippingAddresse" , true);*/
		
		UserBilling userBilling = new UserBilling();
		model.addAttribute("userBilling" , userBilling);
		
		UserPayment userPayment = new UserPayment();
		model.addAttribute("userPayment" , userPayment);
		
		List<String> stateList = EGConstant.ListOfStatesNames;
		Collections.sort(stateList);
		model.addAttribute("stateList" , stateList);
		
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
		model.addAttribute("userShippingList" , user.getUserShippingList());
		//model.addAttribute("orederList" , user.getOrderList());
				
		return "myProfile";
	}
	
	@GetMapping("/addNewShippingAddresse")
	public String addNewShippingAddresse(
			Model model , Principal principal , HttpServletRequest request
			) {
		users user = userService.findByUsername(principal.getName());
		model.addAttribute("user" , user);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("addNewShippingAddresse", true);
		model.addAttribute("listOfCreditsCards", true);
		
		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping" , userShipping);
		
		
		
		List<String> stateList = EGConstant.ListOfStatesNames;
		Collections.sort(stateList);
		model.addAttribute("stateList" , stateList);
		model.addAttribute("userShippingtList" , user.getUserShippingList());
		//model.addAttribute("orederList" , user.getOrderList());
				
		return "myProfile";
	}
	
	@RequestMapping(value="/addNewCreditsCards" , method = RequestMethod.POST)
	 public String addNewCredidCard(
			 @ModelAttribute("userPayment") UserPayment userPayment ,
			 @ModelAttribute("userBilling") UserBilling userBilling ,
			 Model model , 
			 Principal principal
			 ) {
		  
		users user = userService.findByUsername(principal.getName());
		 
		userService.saveUserBillingAndPayment(userBilling , userPayment , user);
		
		model.addAttribute("user" , user);
		model.addAttribute("userPayment" , userPayment);
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
		model.addAttribute("classActiveBilling" , true);
		model.addAttribute("listOfCreditsCards" ,true);
//		model.addAttribute("listOfShippingAddresse" , true);
		
		return "redirect:/listOfCreditsCards";
	} 
	
	@PostMapping("/addNewShippingAddresse")
	public String addNewShippingAddresse(
			@ModelAttribute("userShipping") UserShipping userShipping,
			Model model ,
			Principal principal
			) {
		
		users user = userService.findByUsername(principal.getName());
		 userService.saveUserShipping(userShipping , user);
		 
		 model.addAttribute("user" , user);
		 model.addAttribute("classActiveShipping" , true);
		 model.addAttribute("userShippingList" , user.getUserShippingList());
		 model.addAttribute("listOfShippingAddresse" , true);
		
		return "redirect:/listOfShippingAddresse";
	}
	
	@GetMapping("/updateCreditCard")
	public String UpdateCreditCard(
			@RequestParam("id") Long id , 
			Model model ,
			Principal principal
			) {
		  
		users user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(id).get();
		UserBilling userBilling = userPayment.getUserBilling();

			if(userPayment.getId() ==  userBilling.getUserPayment().getId()) {
			model.addAttribute("user", user);
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);
			model.addAttribute("userPaymentList" , user.getUserPaymentList());
			
			List<String> stateList = EGConstant.ListOfStatesNames;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			model.addAttribute("addNewCreditsCards", true);
			model.addAttribute("classActiveBilling", true);
			}else {
				return "redirect:/error";
			}
			
			
			return "myProfile";		
	}
	
	@PostMapping("/updateCreditCard")
	public String updateCreditCardPost(
			@ModelAttribute("id") Long id,
			@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			@ModelAttribute("user") users user ,
			Model model
			) {
		  
		if(user.getId() == userPayment.getUser().getId()) {
		userPayment = userPaymentService.findById(id).get();
		userService.saveUserBillingAndPayment(userBilling, userPayment, user);
		model.addAttribute("classActiveBilling" , true);
		model.addAttribute("userPaymentList" , user.getUserPaymentList());
		model.addAttribute("listOfCreditsCards" ,true);
		
		return "redirect/listOfCreditsCards";
		}else {
			return "badRequestPage";
		}
	}
	
	
	 @RequestMapping("/removeCreditCard")
	  public String RemoveCreditCard(
			  @PathParam("id") Long id, 
			  Model model ,
			  Principal principal
			  ) {
		 
		    users user = userService.findByUsername(principal.getName());
		    UserPayment userPayment = userPaymentService.findById(id).get();
		   if(user.getId() != userPayment.getUser().getId()) {
			   return "badRequestPage";
		   }else {
			   
			model.addAttribute("user" , user);
		    userPaymentService.deleteById(id);
		    
		    model.addAttribute("userPaymentList" , user.getUserPaymentList());
		    model.addAttribute("classActiveBilling" , true);
		    model.addAttribute("listOfCreditsCards" , true);
		    
				 return "myProfile";
		   }
	 }		 
		
	 @PostMapping("/setDefaultPayment")
	  public String SetDesfaultPayment(
			  @RequestParam("id") Long id,
			  Model model,
			  Principal principal
			  ) {
		 
		 users user = userService.findByUsername(principal.getName());
		 userService.setPaymentDefault(id , user);
		 
		 	model.addAttribute("user" , user);
		    model.addAttribute("userPaymentList" , user.getUserPaymentList());
		    model.addAttribute("classActiveBilling" , true);
		    model.addAttribute("listOfCreditsCards" , true);
		    
		 return "redirect:/listOfCreditsCards";
	 }
	 
	
	
	@RequestMapping("/bookShelf")
	 public String BookShelf(Model model) {
		if(bookService.findAll() != null) {
		List<book> bookList = bookService.findAll();
		model.addAttribute("bookList" , bookList);
		}else{
			
			model.addAttribute("EmptyList" , true);
		}
		return "bookShelf";
	}
	
	@GetMapping("/bookDetails")
	 public String bookDetails(
			 @PathParam("id") Long id , Model model , Principal principal
			 ) {
		
		if(principal != null) {
			users user = userService.findByUsername(principal.getName());
			model.addAttribute("user" , user);
		}
		book book = bookService.findOne(id).get();
		model.addAttribute("book" , book);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		model.addAttribute("qtyList" , qtyList);
		model.addAttribute("qty" , 1);
		
		return "bookDetail";
	}

}

package com.cg.capstore.controllers;


import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CouponsBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.PaymentDetailsBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.GeneratingCouponsException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.IAdminService;
@RestController
public class AdminController {
	@Autowired
	private IAdminService service;
	@Autowired
	private JavaMailSender sender;

	
	
	
	
	@RequestMapping("/validateadmin")
	public AdminBean validateAdmin(String email, String password) throws UserNotFoundException {
		return service.validateAdmin(email, password);
	}
	
	
	

	@RequestMapping(value="/changepasswordadmin",method=RequestMethod.POST)
	public Boolean changePasswordAdmin(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordAdmin(oldPassword, newPassword);
	}
	
	
	
	
	@RequestMapping(value="/viewallcust",method=RequestMethod.GET)
	public List<CustomerBean> viewAllCustomerDetails(){	
		return service.getAllCustomerDetails();
	}
	@RequestMapping(value="/viewallmer",method=RequestMethod.GET)
	public List<MerchantBean> viewAllMerchant(){
		return service.getAllMerchants();
	}
	
	
	
	/*
	 * Admin
	 * */
	@RequestMapping(value="/searchbycustomername",method=RequestMethod.GET)
	public CustomerBean searchByCustomerName(String name)throws SearchException {
		return service.searchCustomerByName(name);
}
	@RequestMapping(value="/searchbymerchantname",method=RequestMethod.GET)
	public MerchantBean searchByMerchantName(String name)throws SearchException {
		return service.searchMerchantByName(name);
	
}
	 @RequestMapping(value="/generatingcoupons",method=RequestMethod.POST)
	  public String generateCoupon(String emailId,CouponsBean coupon) throws GeneratingCouponsException {
	  
	  return service.createCoupon(emailId,coupon);
	 
	  }
	  
	 

	@RequestMapping(value = "/sendPromo", method = RequestMethod.GET)
	public String sendPromo(String email, String Email) throws MessagingException {
		String customerEmail = service.sendPromo(email);
		// sendInvitationToFriend(Email);
		return "Success, Sending promo to " + Email + "from your mail " + customerEmail;
	}

	public void sendPromo(String email) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		// helper.setFrom(customerEmail);
		helper.setTo("sadsa@gmail.com");
		helper.setText("Test Message...");
		helper.setSubject("Inviting You to use Promo for this website");

		sender.send(message);
	}

	
	
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	public String refund(String orderId)throws OrderDetailsNotFoundException {
		return service.refund(orderId);
		
	}

	@RequestMapping(value = "/statusUpdate", method = RequestMethod.POST)
	public OrderBean setStatus(OrderBean o) throws OrderDetailsNotFoundException {
		return service.updateStatus(o);

	}
	
	@RequestMapping(value="/generateanalysis",method=RequestMethod.GET)
	public List<PaymentDetailsBean> transactionAnalysis(Date start, Date end){
 
		return service.transactionAnalysis(start, end);
	}
	
	@RequestMapping(value="/revenue1",method=RequestMethod.GET)
	public String updateRevenue (String payment) throws UserNotFoundException {
		System.err.println("IN BACK REST CALLING");
		//System.err.println(payment);
		Double rev = service.updateRevenue(payment);
		
		return "Capstore revenur is "+rev;
	}
	@RequestMapping(value="/addMerchant",method=RequestMethod.POST)
	public String merchantAddition(MerchantBean merchant) throws UserNotFoundException
	{
		
		
			service.addMerchant(merchant);
		
		
		return "Merchant with Id "+merchant.getEmailId()+" Added";
	}
	@RequestMapping(value="/DeleteMerchant",method=RequestMethod.DELETE)
	public String merchantDelete( String emailId) throws UserNotFoundException
	{
		
			service.deleteMerchant(emailId);
		
		return "Merchant with Id "+emailId+" Deleted";
	}


	
	@RequestMapping(value="/AddThirdPartyMerchant",method=RequestMethod.POST)
	public void thirdPartyMerchantAddition( MerchantBean merchant) throws UserNotFoundException
	{
		
			service.addThirdPartyMerchant(merchant);
		
	}

	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendRegisteredMail( String name) throws UserNotFoundException, MessagingException {
		
		String customerEmail = service.regiteredNewUser(name);

		sendRegisteredNewUser(name);
		
		if (customerEmail != null) {
			return "Scheme details have been sent to " + customerEmail;
		} else {
			return "customer does not exist";
		}
	}

	private void sendRegisteredNewUser(String name) throws MessagingException, UserNotFoundException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(service.regiteredNewUser(name));
		helper.setText("you are registered successfully");
		helper.setSubject("Registered Successfully");
		sender.send(message);
	}

	// invitation to customer
	@RequestMapping(value = "/sendInvitationToCustomer", method = RequestMethod.POST)
	public String inviteCustomer( String email) throws MessagingException, UserNotFoundException {
		
		CustomerBean customeBean = service.sentInvitationToCustomer(email);
		String name = customeBean.getCustomerName();
	
		sendInvitationToCustomer(email);
		if (customeBean.getEmail() != null) {
			return name + ",invitation has been sent to" + email;
		} else {
			return "customer does not exist";
		}
	}

	private void sendInvitationToCustomer(String email) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText("Check out the products in capstore");
		helper.setSubject("Invitaiton From CapStore");
		sender.send(message);
	}

	// invitation to merchant
	@RequestMapping(value = "/sendInvitationToMerchant", method = RequestMethod.POST)
	public String inviteMerchant(String email) throws MessagingException, UserNotFoundException {
		
		MerchantBean merchantBean = service.sendInvitationToMerchant(email);
		String name = merchantBean.getMerchantName();
		
		sendNewInvitationToMerchant(email);
		if (merchantBean.getEmailId() != null) {
			return name + ",invitation has been sent to" + email;
		} else {
			return "Merchant does not exist";
		}

	}

	private void sendNewInvitationToMerchant(String email) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText("Check out the products in capstore");
		helper.setSubject("Invitaiton From CapStore");
		sender.send(message);
	}
	// scheme to customer

	@RequestMapping(value = "/sendSchemeToCustomer", method = RequestMethod.POST)
	public String sendSchemeToCustomer(String name) throws MessagingException, UserNotFoundException {
		System.err.println("customer scheme bqack");
		System.err.println("In Back Controller--- Name:" + name);
		String customerEmail = service.sendSchemeToCustomer(name);
		System.err.println("Out Back Controller--- Name:" + customerEmail);
		sendNewSchemeToCustomer(name);
		if (customerEmail != null) {
			return "Scheme details have been sent to " + customerEmail;
		} else {
			return "customer does not exist";
		}
	}

	private void sendNewSchemeToCustomer(String name) throws MessagingException, UserNotFoundException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(service.sendSchemeToCustomer(name));
		helper.setText("Check out the new scheme in capstore");
		helper.setSubject("New Scheme in CapStore");
		sender.send(message);
	}
	// scheme to merchant

	@RequestMapping(value = "/sendSchemeToMerchant", method = RequestMethod.POST)
	public String sendSchemeToMerchant( String name) throws MessagingException, UserNotFoundException {
		System.out.println("merchant");
		System.err.println("In Back Controller--- Name:" + name);
		String merchantEmail = service.sendSchemeToMerchant(name);
		sendNewSchemeToMerchant(name);
		if (merchantEmail != null) {
			return "Scheme details have been sent to " + merchantEmail;
		} else {
			return "merchant does not exist";
		}
	}

	private void sendNewSchemeToMerchant(String name) throws MessagingException, UserNotFoundException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(service.sendSchemeToMerchant(name));
		helper.setText("Check out the new scheme in capstore");
		helper.setSubject("New Scheme in CapStore");
		sender.send(message);
	}

	// promo to customer
	@RequestMapping(value = "/sendPromoToCustomer", method = RequestMethod.POST)
	public String sendPromoToCustomer( String name) throws MessagingException , UserNotFoundException{
		
		String customerEmail = service.sendPromoToCustomer(name);
		System.err.println("Out Back Controller--- Name:" + customerEmail);
		sendNewPromoToCustomer(name);
		if (customerEmail != null) {
			return "Promo details have been sent to " + customerEmail;
		} else {
			return "customer does not exist";
		}
	}

	private void sendNewPromoToCustomer(String name) throws MessagingException , UserNotFoundException{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(service.sendPromoToCustomer(name));
		helper.setText("Check out the new promo in capstore");
		helper.setSubject("New promo in CapStore");
		sender.send(message);
	}

	// promo to merchant

	@RequestMapping(value = "/sendPromoToMerchant", method = RequestMethod.POST)
	public String sendPromoToMerchant(String name) throws MessagingException , UserNotFoundException{
		System.err.println("merchant scheme back");
		System.err.println("In Back Controller--- Name:" + name);
		String merchantEmail = service.sendPromoToMerchant(name);
		sendNewPromoToMerchant(name);
		System.err.println("Out Back Controller--- Name:" + merchantEmail);
		if (merchantEmail != null) {

			return "promo details have been sent to " + merchantEmail;
		} else {
			return "merchant does not exist";
		}
	}

	private void sendNewPromoToMerchant(String name) throws MessagingException, UserNotFoundException {
		MimeMessage message =sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(service.sendPromoToMerchant(name));
		helper.setText("Check out the new promo in capstore");
		helper.setSubject("New promo in CapStore");
		sender.send(message);
	}
	

	@RequestMapping(value="/sendInvitationToExistingMerchant", method=RequestMethod.POST)
	public String inviteExistingMerchant( String Email) throws MessagingException, UserNotFoundException {
		System.err.println("IN BACK CONTROLLER...");
		System.err.println("email of customer:"+Email);
		MerchantBean merchantBean= service.sendInvitationToMerchant(Email);
		sendExistingInvitationToMerchant(Email);
		//String name=merchantBean.getMerchantName();
		//System.err.println("Back--- Name:"+name);
		return "Invitation has been sent successfully to "+Email;
	
	}


	private void sendExistingInvitationToMerchant(String email) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText("Check out the products in capstore");
		helper.setSubject("Invitaiton From CapStore");
		sender.send(message);
	}
	
	/**
	 * Deepraj modules
	 * */
	    
	   
	     
	     @RequestMapping(value="/add",method=RequestMethod.POST)
	 	public ProductBean addProduct( Map<String, String> data)
	 	{
	 		
	 		return service.addProduct(data.get("productId"), data.get("customerId"));
	 	}
	 	
	 
	 	
}

package com.cg.capstore.controllers;

import java.util.HashMap;
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

import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.beans.WishlistBean;
import com.cg.capstore.exception.CartException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.TransactionException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.ICustomerService;

@RestController
public class CustomerController {
	@Autowired
	private ICustomerService service;
	@Autowired
	private JavaMailSender sender;

	@RequestMapping("/signupcustomer")
	public Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address) throws UserNotFoundException{
		return service.confirmSignUpCustomer(customerName, mobileNo, email, password, address);
	}
	
	@RequestMapping("/validatecustomer")
	public CustomerBean validateCustomer(String email, String password) throws UserNotFoundException {
		return service.validateCustomer(email, password);
	}
	@RequestMapping(value="/changepassword",method=RequestMethod.POST)
	public Boolean changePasswordCustomer(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordCustomer(oldPassword, newPassword);
	}
	@RequestMapping(value="/getcustomer")
	public CustomerBean changePassword(String email) throws UserNotFoundException {
		return service.getCustomer(email);
	}
	@RequestMapping(value="/editprofilecustomer",method=RequestMethod.POST)
	public CustomerBean editProfileCustomer(String email,String customerName,String address, String mobileNo) throws UserNotFoundException {
		return service.editProfileCustomer(email,customerName, address, mobileNo);
	}
	
		
		@RequestMapping(value="/placingorder",method=RequestMethod.POST)
		public String placingOrderFunctionality(OrderBean orderBean,String couponCode) throws OrderDetailsNotFoundException {
			//try {
				service.placingOrderOfProduct(orderBean,couponCode);
			/*} catch (OrderDetailsNotFoundException e) {
				throw e;
			}*/
			//System.out.println(couponCode);
			return orderBean.getOrderId()+" Order is Confirmed";
		}
		@RequestMapping(value = "/addproducttocart", method = RequestMethod.POST)
		public List<ProductBean> addProductToCart(String emailId, String productId) throws CartException {

			return service.addProductToCart(emailId, productId);
		}
		@RequestMapping(value = "/deleteproductfromcart", method = RequestMethod.POST)
		public List<ProductBean> removeProductFromCart(String emailId, String productId) throws CartException {

		return	service.removeProductFromProduct(emailId, productId);
		}
		

		@RequestMapping(value = "/displaycart", method = RequestMethod.GET)
		public List<ProductBean> displayCart(String emailId) throws CartException {

			return service.displayCart(emailId);
		}
		
		@RequestMapping(value = "/gettransactionaldetails", method = RequestMethod.GET)
		public OrderBean getTransactionalDetails(String orderId) throws TransactionException{
			return service.getTransactionalDetails(orderId);

		}
		
		@RequestMapping(value="/generateInvoice",method=RequestMethod.POST)
		public String generateInvoice(String customerId) throws UserNotFoundException
		{
			return service.generateInvoice(customerId);
		}
		@RequestMapping(value="/mailNow", method=RequestMethod.POST)
		public Map<String, String> sendInvitation( Map<String, String> data) throws MessagingException {
			System.err.println("INTO BACKEND CONTROLLER");
			
			Map<String, String> data2 = data;
			
			System.err.println("Data CAME IN : "+data2);
			
			//sendEmail(data.get("friendEmail"));
			
			System.err.println("Success!! \n Invitation Sent to "+data2.get("friendEmail"));
			
			return data;
		}
		private void sendEmail(String friendEmail) throws MessagingException {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
							
			//helper.setFrom(customerEmail);
			helper.setTo(friendEmail);
			helper.setSubject("Invitation from your friend...");
			helper.setText("Hi "+friendEmail+"\n Buy Products from this website..."
					+ "\n CapStore...");
			
			sender.send(message);
		}

		 @RequestMapping("/simpleemail")
		    public String home(String email) {
			 
			 System.err.println("given:"+email);
		         try {
		        	 System.err.println(service.shippingDetailsMsg(email));
		            String customerEmail=service.shippingDetailsMsg(email).getEmail();
		         //   sendEmail(customerEmail);
		             return "Email Sent! to "+customerEmail;
		         }catch(Exception ex) {
		             return "Error in sending email: "+ex;
		         }
		     }
		 private void sendEmailCustomer(String customerEmail) throws Exception{
	         MimeMessage message = sender.createMimeMessage();
	         MimeMessageHelper helper = new MimeMessageHelper(message);
	         CustomerBean customer=service.shippingDetailsMsg(customerEmail);
	         OrderBean order=customer.getOrder();
	         helper.setTo(customerEmail);
	         helper.setText("Hello " +customer.getCustomerName() +",\n Your delivery date is on: " +order.getDeliveryDate()
	         +"\n Your order will be send to the address:" +customer.getAddress());
	         helper.setSubject("Hi");
	         sender.send(message);
	     }
			@RequestMapping(value="/display",method=RequestMethod.GET)
		 	public Map<String, Object> displayProductsFromWishlist(String email)
		 	{
		 		
		 		WishlistBean bean = service.displayProductsFromWishlist(email);
		 		System.err.println("Controller: Wishlist "+bean);
		 		System.err.println("Controller: Product List..."+bean.getProduct());
		 		
		 		Map<String, Object> map = new HashMap<>();
		 		map.put("wishlistId", bean.getWishlistId());
		 		map.put("productId", bean.getProduct());
		 		return map;
		 	}
		 	
			@RequestMapping(value = "/checkingStatus", method = RequestMethod.GET)
			public OrderBean checkingStatus(String orderId) throws OrderDetailsNotFoundException, TransactionException {
				return service.getTransactionalDetails(orderId);

			}
		 	
		 	
		 	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
		 	public WishlistBean deleteProductsFromWishlist(String email,String productId)
		 	{
		 		return service.deleteProductsFromWishlist(email, productId);
		 	}

}

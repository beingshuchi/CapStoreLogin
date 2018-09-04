package com.cg.capstore.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CouponsBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.DiscountBean;
import com.cg.capstore.beans.FeedbackProductBean;
import com.cg.capstore.beans.ImageBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.PaymentDetailsBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.beans.PromosBean;
import com.cg.capstore.beans.WishlistBean;
import com.cg.capstore.exception.CartException;
import com.cg.capstore.exception.CategoryNotFoundException;
import com.cg.capstore.exception.DiscountDateExceedException;
import com.cg.capstore.exception.DiscountException;
import com.cg.capstore.exception.FeedbackException;
import com.cg.capstore.exception.GeneratingCouponsException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.PromoCodeInvalidException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.SortingException;
import com.cg.capstore.exception.TransactionException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.ICapstoreServerService;

@RestController
public class BackController {
	@Autowired
	private ICapstoreServerService service;
	@Autowired
	private JavaMailSender sender;

	@RequestMapping("/")
	public String test() {
		return "Spring backend";
	}
	
	@RequestMapping("/validatecustomer")
	public CustomerBean validateCustomer(String email, String password) throws UserNotFoundException {
		return service.validateCustomer(email, password);
	}
	@RequestMapping("/signupcustomer")
	public Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address) throws UserNotFoundException{
		return service.confirmSignUpCustomer(customerName, mobileNo, email, password, address);
	}
	@RequestMapping("/signupmerchant")
	public Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password, String merchantType) throws UserNotFoundException {
		return service.confirmSignUpMerchant(customerName, mobileNo, email, password, merchantType);
	}
	@RequestMapping("/validateadmin")
	public AdminBean validateAdmin(String email, String password) throws UserNotFoundException {
		return service.validateAdmin(email, password);
	}
	@RequestMapping("/validatemerchant")
	public MerchantBean validateMerchant(String email, String password) throws UserNotFoundException {
		return service.validateMerchant(email, password);
	}
	
	@RequestMapping(value="/changepassword",method=RequestMethod.POST)
	public Boolean changePasswordCustomer(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordCustomer(oldPassword, newPassword);
	}
	@RequestMapping(value="/changepasswordmerchant",method=RequestMethod.POST)
	public Boolean changePasswordMerchant(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordMerchant(oldPassword, newPassword);
	}
	@RequestMapping(value="/changepasswordadmin",method=RequestMethod.POST)
	public Boolean changePasswordAdmin(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordAdmin(oldPassword, newPassword);
	}
	//surya team module
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String getPassword(String email) throws UserNotFoundException {
		
		return service.forgotPassword(email);
		
	}
	@RequestMapping(value="/getcustomer")
	public CustomerBean changePassword(String email) throws UserNotFoundException {
		return service.getCustomer(email);
	}
	@RequestMapping(value="/getmerchant")
	public MerchantBean getMechant(String email) throws UserNotFoundException {
		return service.getMechant(email);
	}
	@RequestMapping(value="/editprofilecustomer",method=RequestMethod.POST)
	public CustomerBean editProfileCustomer(String email,String customerName,String address, String mobileNo) throws UserNotFoundException {
		return service.editProfileCustomer(email,customerName, address, mobileNo);
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
	 * SEARCH -- DIVYA
	 * */
/*
 * Customer-- app
 * */
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public List<ProductBean> searchByProductName(String name)throws SearchException {
		return service.searchProductByName(name); 
	}
	/*
	 * for merchant and customer
	 * */
	@RequestMapping(value="/searchcategory",method=RequestMethod.GET)
	public List<ProductBean> searchByProductCategory(String category) throws SearchException {
		return service.searchProductByCategory(category);
		
	}
	@RequestMapping(value="/searchprice",method=RequestMethod.GET)
	public List<ProductBean> searchByPrice(Double price) throws SearchException{
		return service.searchProductByPrice(price);
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
	/*
	 * for merchant or customer any one
	 * */
	@RequestMapping(value="/searchorder",method=RequestMethod.GET)
	public OrderBean searchByOrderId(String id)throws SearchException {
		return service.searchOrderById(id);	
		
}
	/*
	 * customer and merchant
	 * */
	@RequestMapping(value="/searchorderstatus",method=RequestMethod.GET)
	public List<OrderBean> searchOrderByStatus(String Status)throws SearchException {
		return service.searchOrderByStatus(Status);	
}
	@RequestMapping(value="/searchpromocode",method=RequestMethod.GET)
	public PromosBean searchPromosByCode(String Code)throws SearchException {
		return service.searchPromosByCode(Code);	
}
	@RequestMapping(value="/searchdiscountid",method=RequestMethod.GET)
	public DiscountBean searchDiscountById(String Id)throws SearchException {
		return service.searchDiscountById(Id);	
}
	
	
	
	/*
	 * DISCOUNT MODULES for merchant and admin
	 * */
	@RequestMapping(value = "/adddiscount", method = RequestMethod.POST)
	public DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException {
	
			return service.addDiscount(discount);
		
	}
	
	@RequestMapping(value = "/viewdiscountbyid", method = RequestMethod.GET)
	public DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException {
		
			return service.viewDiscountById(discountId);
		
	}
	
	@RequestMapping(value = "/viewalldiscount", method = RequestMethod.GET)
	public List<DiscountBean> findAllDiscounts() throws DiscountDateExceedException{
		
			return service.findAllDiscounts();
		 
	}
	
	/*
	 * Merchant managing inventory
	 * */
	
	@RequestMapping(value = "/displayallproducts",method=RequestMethod.POST)
	List<ProductBean> displayAllProducts() throws ProductNotFoundException {
		
		//try {
			return service.displayAllProducts();
		//} catch (ProductNotFoundException e) {
			//throw e;
		//}
		
	}
	
	@RequestMapping(value = "/addnewproduct", method = RequestMethod.POST)
	ProductBean addNewProduct(ProductBean product) throws ProductNotFoundException  {
		//try {
			return service.addNewProduct(product);
		/*} catch (ProductNotFoundException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/updateproductdetails",method=RequestMethod.PUT)
	public ProductBean updateProductDetails( ProductBean product) throws ProductNotFoundException {
		
		//try {
			return service.updateProductDetails(product);
		/*} catch (ProductNotFoundException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/deleteproduct",method=RequestMethod.DELETE)
	public String deleteProduct(String productId) throws ProductNotFoundException {
		
		//try {
			return service.deleteProduct(productId);
		/*} catch (ProductNotFoundException e) {
			throw e;
		}*/
	}
	
	
	
	@RequestMapping(value = "/getproductdetailsbyid",method=RequestMethod.POST)
	public ProductBean getProductdetails(String productId) throws ProductNotFoundException {
		
		//try {
			return service.getProductdetailsById(productId);
		/*} catch (ProductNotFoundException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/removeExistingCategory",method=RequestMethod.DELETE)
	public String removeExistingCategory(String category) throws CategoryNotFoundException {
		
		//try {
			return service.removeExistingCategory(category);
		/*} catch (CategoryNotFoundException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value="/displayAllCategory", method=RequestMethod.POST)
	public List<ProductBean> displayAllCategory(String category) throws CategoryNotFoundException {
		//try {
			return service.displayAllCategory();
		/*} catch (CategoryNotFoundException e) {
			throw e;
		}*/
	}
	
	/*
	 * PLACING ORDER
	 * */
	
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
	
	@RequestMapping(value="/finalizeorder", method=RequestMethod.POST)
	public OrderBean checkProductAvailability(String id) throws OrderDetailsNotFoundException {
		
		//try {
			return service.CheckProductAvailability(id);
		/*} catch (OrderDetailsNotFoundException e) {
			throw e;
		}*/
	}
	
	/*
	 * PROMOS
	 * */
	
	@RequestMapping(value = "/addpromo", method = RequestMethod.POST)
	public String promoDb(PromosBean promoss) throws PromoCodeInvalidException {
		System.err.println("Back"+promoss.toString());
		//try {
			return service.addPromo(promoss);
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/viewpromobyid", method = RequestMethod.GET)
	public PromosBean viewById(PromosBean pId) throws PromoCodeInvalidException {
		//System.out.println(pId.getPromoCode());
		//try {
			return service.viewByPromoCode(pId.getPromoCode());
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/viewallpromos", method = RequestMethod.GET)
	public List<PromosBean> viewAllPromos() throws PromoCodeInvalidException{
		//try {
			return service.viewAllPromos();
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
	
	
	/*
	 * Return goods and delivery status
	 * */
	@RequestMapping(value="/returnpurchasedproduct", method=RequestMethod.POST)
	public OrderBean returnProduct(String id) throws ProductNotFoundException {
		
		
			//try {
				return service.returnProduct(id);
			/*} catch (ProductNotFoundException e) {
				throw e;
			}*/
		
	}
	
	@RequestMapping(value="/getdeliverystatus", method=RequestMethod.GET)
	public String getDeliveryStatus(String orderId) throws ProductNotFoundException {
		
		//try {
			return service.getDeliveryStatus(orderId);
		/*} catch (ProductNotFoundException e) {
			throw e;
		}*/
		
	}
	
	
	/**
	 * Vineesha Team modules
	 * */
	 @RequestMapping(value="/generatingcoupons",method=RequestMethod.POST)
	  public String generateCoupon(String emailId,CouponsBean coupon) throws GeneratingCouponsException {
	  
	  return service.createCoupon(emailId,coupon);
	 
	  }
	  
	  
	  
	  @RequestMapping(value="/addfeedback",method=RequestMethod.POST)
		public FeedbackProductBean addFeedback(String productId,FeedbackProductBean feedbackProductBean) throws FeedbackException {
			//try {
				
				feedbackProductBean=service.addAnFeedback(productId, feedbackProductBean);
			/*} catch (CapstoreException e) {
				
				throw new Exception(e.getMessage());
			}*/
			return feedbackProductBean;
			
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

	
	@RequestMapping(value = "/addimage", method = RequestMethod.POST)
	public String addImage(String productId, ImageBean image) throws FileNotFoundException, IOException {

		
		return service.addImage(productId,image);
	}

	
	
	@RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
	public ImageBean showImage(String productId, HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {
		ImageBean image = service.get(productId);
		System.out.println(image);
		response.setContentType("image/jpeg; image/jpg; image/png; image/gif");
		// response.getOutputStream().write(image.getImg_data());
		response.getOutputStream().write(image.getImageData());
		response.getOutputStream().close();
 return image;
	}
	
	
	/**
	 * Surya Team modules
	 * */
	@RequestMapping(value = "/sorthightolow", method = RequestMethod.GET)
	public List<ProductBean> sortHighToLow(String category) throws SortingException {
		return service.sortByHighToLow(category);

	}

	@RequestMapping(value = "/sortlowtohigh", method = RequestMethod.GET)
	public List<ProductBean> sortLowToHigh(String category) throws SortingException {
		return service.sortByLowToHigh(category);

	}

	@RequestMapping(value = "/rangesort", method = RequestMethod.POST)
	public List<ProductBean> rangeSort(Double min, Double max, String category) throws SortingException {

		return service.rangeSort(min, max,category);

	}

	
	@RequestMapping(value = "/sortbymostviewed", method = RequestMethod.GET)
	public List<ProductBean> sortByMostViewed(String category) throws SortingException {
		return service.sortByMostViewed(category);
	
	}
	

	@RequestMapping(value = "/gettransactionaldetails", method = RequestMethod.GET)
	public OrderBean getTransactionalDetails(String orderId) throws TransactionException{
		return service.getTransactionalDetails(orderId);

	}

	@RequestMapping(value = "/Avg", method = RequestMethod.GET)
	public Double ratingAvg() throws ProductNotFoundException {
		return service.ratingAvg();

	}

	@RequestMapping(value = "/statusUpdate", method = RequestMethod.POST)
	public OrderBean setStatus(OrderBean o) throws OrderDetailsNotFoundException {
		return service.updateStatus(o);

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

	@RequestMapping(value = "/count", method = RequestMethod.GET)
		public ProductBean count(ProductBean productId) throws ProductNotFoundException {
			return service.count(productId);
			
		}
	
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	public String refund(String orderId)throws OrderDetailsNotFoundException {
		return service.refund(orderId);
		
	}

	@RequestMapping(value = "/checkingStatus", method = RequestMethod.GET)
	public OrderBean checkingStatus(String orderId) throws OrderDetailsNotFoundException, TransactionException {
		return service.getTransactionalDetails(orderId);

	}
	
	/*
	 * Vineela mteam modules
	 * */
	
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

	@RequestMapping(value="/generateInvoice",method=RequestMethod.POST)
	public String generateInvoice(String customerId) throws UserNotFoundException
	{
		return service.generateInvoice(customerId);
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
		MerchantBean merchantBean;
		merchantBean = service.sendInvitationToMerchant(Email);
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
	@RequestMapping(value="/discount")
	public String  Discount(String pid) throws DiscountException
	{
		
			return service.discount(pid);
		
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
	     @RequestMapping(value="/displayAllSimilarProducts", method=RequestMethod.GET)
	 	public List<ProductBean> SimilarProductsInterface(String category) {
	 		return service.displaySimilarProducts(category);
	 	}
	     
	     @RequestMapping(value="/add",method=RequestMethod.POST)
	 	public ProductBean addProduct( Map<String, String> data)
	 	{
	 		
	 		return service.addProduct(data.get("productId"), data.get("customerId"));
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
	 	
	 
	 	
	 	
	 	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	 	public WishlistBean deleteProductsFromWishlist(String email,String productId)
	 	{
	 		return service.deleteProductsFromWishlist(email, productId);
	 	}
	 	
}

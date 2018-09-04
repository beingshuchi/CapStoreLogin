package com.cg.capstore.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cg.capstore.repo.IAdminRepo;
import com.cg.capstore.repo.ICouponRepo;
import com.cg.capstore.repo.ICustomerRepo;
import com.cg.capstore.repo.IDiscountRepo;
import com.cg.capstore.repo.IFeedbackProductRepo;
import com.cg.capstore.repo.IImageRepo;
import com.cg.capstore.repo.IMerchantRepo;
import com.cg.capstore.repo.IOrderRepo;
import com.cg.capstore.repo.IPaymentRepo;
import com.cg.capstore.repo.IProductRepo;
import com.cg.capstore.repo.IPromoRepo;
import com.cg.capstore.repo.IWishlistRepo;
@Service(value="service")
public class CapstoreServerServiceImpl implements ICapstoreServerService {
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IAdminRepo adminRepo;
	@Autowired
	private IMerchantRepo merchanRepo;
	@Autowired
	private IDiscountRepo discountRepo;
	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private IOrderRepo orderRepo;
	@Autowired
	private ICouponRepo couponRepo;
	@Autowired
	private IPromoRepo promoRepo;
	@Autowired
	private IImageRepo imageRepo;
	@Autowired
	private IFeedbackProductRepo feedbackRepo;
	@Autowired
	private IPaymentRepo paymentRepo;
	@Autowired
	private IWishlistRepo wishlistRepo;
	
	
	@Override
	public CustomerBean validateCustomer(String email, String password) throws UserNotFoundException  {
		// TODO Auto-generated method stub
		password=encryption(password);
		CustomerBean customer=customerRepo.findCustomer(email);
		if(customer!=null && customer.getPassword().equals(password)) {
			return customer;
		}/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
		
	}
	
	private String encryption(String password) {
		StringBuilder sb=new StringBuilder(password);
		sb.reverse().append(password);
		return sb.toString();
	}

	@Override
	public AdminBean validateAdmin(String email, String password) throws UserNotFoundException {
		password=encryption(password);
		AdminBean admin=adminRepo.findAdmin(email);
		if(admin!=null && admin.getPassword().equals(password)) {
			return admin;
		}
		/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
		
	}

	@Override
	public MerchantBean validateMerchant(String email, String password) throws UserNotFoundException {
		password=encryption(password);
		MerchantBean merchant=merchanRepo.findMerchant(email);
		if(merchant!=null && merchant.getPassword().equals(password)) {
			return merchant;
		}
		/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
	}

	@Override
	@Transactional
	public Boolean changePasswordCustomer(String oldPassword, String newPassword) throws UserNotFoundException {
		// TODO Auto-generated method stub
		oldPassword=encryption(oldPassword);
		CustomerBean customer= customerRepo.findPassword(oldPassword);
		if(oldPassword.equals(customer.getPassword())) {
			newPassword=encryption(newPassword);
			customer.setPassword(newPassword);
			customerRepo.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public CustomerBean getCustomer(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return customerRepo.findCustomer(email);
	}

	@Override
	@Transactional
	public Boolean changePasswordMerchant(String oldPassword, String newPassword) throws UserNotFoundException {
		oldPassword=encryption(oldPassword);
		MerchantBean merchant= merchanRepo.findPassword(oldPassword);
		if(oldPassword.equals(merchant.getPassword())) {
			newPassword=encryption(newPassword);
			merchant.setPassword(newPassword);
			merchanRepo.save(merchant);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean changePasswordAdmin(String oldPassword, String newPassword) throws UserNotFoundException {
		oldPassword=encryption(oldPassword);
		AdminBean admin= adminRepo.findPassword(oldPassword);
		if(oldPassword.equals(admin.getPassword())) {
			newPassword=encryption(newPassword);
			admin.setPassword(newPassword);
			adminRepo.save(admin);
			return true;
		}
		return false;
	}
	
	@Override
	public String forgotPassword(String email) throws UserNotFoundException {

		AdminBean admin = adminRepo.findAdmin(email);

		MerchantBean merchant = merchanRepo.findMerchant(email);
		CustomerBean cust = customerRepo.findCustomer(email);

		if (admin != null) {
			String a1 = admin.getPassword();
			int l1 = a1.length();
			String pass = (a1.substring(l1 / 2, l1));
			return pass;
		} else if (merchant != null) {
			String a1 = merchant.getPassword();
			int l1 = a1.length();
			// System.out.println(l1);
			String pass = (a1.substring(l1 / 2, l1));
			// System.out.println(pass);
			return pass;
		} else if (cust != null) {
			// return cust.getPassword();
			String a1 = cust.getPassword();
			int l1 = a1.length();
			String pass = (a1.substring(l1 / 2, l1));
			return pass;
		} else {
			throw new UserNotFoundException( "email does not exist");
		}

	}

	@Override
	@Transactional
	public CustomerBean editProfileCustomer(String email,String customerName, String address, String mobileNo)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		CustomerBean customer=customerRepo.findCustomer(email);
		if(customer!=null) {
			customer.setCustomerName(customerName);
			customer.setAddress(address);
			customer.setMobileNo(mobileNo);
			customerRepo.save(customer);
			return customer;
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		password=encryption(password);
		CustomerBean customer = new CustomerBean();
		customer.setCustomerName(customerName);
		customer.setAddress(address);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setMobileNo(mobileNo);
		customerRepo.save(customer);
		return true;
	}

	@Override
	public MerchantBean getMechant(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return merchanRepo.findMerchant(email);
	}

	@Override
	@Transactional
	public Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password,
			String merchantType) throws UserNotFoundException {
		password=encryption(password);
		MerchantBean merchant = new MerchantBean();
		merchant.setMerchantName(customerName);
		merchant.setEmailId(email);
		merchant.setPassword(password);
		merchant.setPhoneNo(mobileNo);
		merchant.setType(merchantType);
		merchanRepo.save(merchant);
		return true;
	}
	
	@Override
	public List<CustomerBean> getAllCustomerDetails() {
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}

	@Override
	public List<MerchantBean> getAllMerchants() {
		// TODO Auto-generated method stub
		return merchanRepo.findAll();
	}
	
	
	/*
	 * SEARCH---BY DIVYA
	 * */
	@Override
	public List<ProductBean> searchProductByName(String name)throws SearchException {
		// TODO Auto-generated method stub
		return productRepo.searchProductByName(name);
	}

	@Override
	public List<ProductBean> searchProductByCategory(String category)throws SearchException {
		// TODO Auto-generated method stub
		return productRepo.searchProductByCategory(category);
	}

	@Override
	public List<ProductBean> searchProductByPrice(Double price) throws SearchException{
		// TODO Auto-generated method stub
		return productRepo.searchProductByPrice(price);
	}
	//Admin search by customer name
	@Override
	public CustomerBean searchCustomerByName(String name) throws SearchException{
		
		return customerRepo.searchCustomerByName(name);
	}
//Merchant search by merchant name
	@Override
	public MerchantBean searchMerchantByName(String name)throws SearchException {
		// TODO Auto-generated method stub
		return merchanRepo.searchMerchantByName(name);
	}

	
	

	@Override
	public List<OrderBean> searchOrderByStatus(String Status)throws SearchException {
		// TODO Auto-generated method stub
		return orderRepo.searchOrderByStatus(Status);
	}

	@Override
	public OrderBean searchOrderById(String id)throws SearchException {
		// TODO Auto-generated method stub
		return orderRepo.getOne(id);
		
	}

	@Override
	public PromosBean searchPromosByCode(String Code)throws SearchException {
		// TODO Auto-generated method stub
		return promoRepo.searchPromosByCode(Code);
	}

	@Override
	public DiscountBean searchDiscountById(String Id)throws SearchException {
		// TODO Auto-generated method stub
		return discountRepo.searchDiscountById(Id);
	}
	
	
	/*
	 * Discount modules for merchant and admin
	 * */
	
	@Override
	@Transactional
	public DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException {
		/*if(discount.getDiscountId()!= null) {
			throw new DiscountDateExceedException("Discount is already available");
		}else {*/
		return discountRepo.save(discount);
		//}
	}

	@Override
	public DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException {
		/*if(discountId==null) {
			throw new DiscountDateExceedException("Discount Id is not available");
		}*/
		
		return discountRepo.getOne(discountId);
	}

	@Override
	public List<DiscountBean> findAllDiscounts() throws DiscountDateExceedException {
		/*if(DiscountBean.class== null) {
			throw new DiscountDateExceedException("Discount is not available");
		}else {*/
		return discountRepo.findAll();
	//}
	}
	
	/*
	 * Merchant managing inventory
	 * */
	
	@Override
	public List<ProductBean> displayAllProducts() throws ProductNotFoundException {
		/*if(ProductBean.class == null) {
			throw new ProductNotFoundException("Inventory is empty");
		}*/
		return productRepo.findAll();
	}
	@Override
	@Transactional
	public ProductBean addNewProduct(ProductBean product) throws ProductNotFoundException {
		if(product.getProductId()!=null) {
			throw new ProductNotFoundException("Product Id should have valid credential ");
		}
		return productRepo.save(product);
	}
	@Override
	@Transactional
	public ProductBean updateProductDetails(ProductBean product) throws ProductNotFoundException {
		
		ProductBean product1= productRepo.getOne(product.getProductId());
		if(product1.getProductId()==null) {
			throw new ProductNotFoundException("Product is not available");
		}else {
			
		
		product1.setProductName(product.getProductName());
		product1.setCategory(product.getCategory());
		product1.setDiscount(product.getDiscount());
		product1.setProductId(product.getProductId());
		product1.setPrice(product.getPrice());
		product1.setQuantity(product.getQuantity());
		product1.setImageId(product.getImageId());
		return productRepo.save(product1);
		}
	}
	@Override
	@Transactional
	public String deleteProduct(String productId) throws ProductNotFoundException {
		productRepo.getOne(productId);
		if(productId==null) {
			throw new ProductNotFoundException("Product is not available");
		}else {
			
		productRepo.deleteById(productId);
		
		return productId;
		}
		}
	@Override
	public ProductBean getProductdetailsById(String productId) throws ProductNotFoundException {
		if(productId==null) {
			throw new ProductNotFoundException("Product is not available");
		}else {
		return productRepo.getOne(productId);
	}
	}
	
	
	@Override
	@Transactional
	public String removeExistingCategory(String categoryId) throws CategoryNotFoundException {
		if(categoryId==null) {
			throw new CategoryNotFoundException("Category is not available");
		}else {
		productRepo.deleteById(categoryId);
		return categoryId+" category is deleted";
		}
	}
	@Override
	public List<ProductBean> displayAllCategory() throws CategoryNotFoundException {
	/*	if(ProductBean.class==null) {
			throw new CategoryNotFoundException("Inventory with category is empty");
		}*/
		return productRepo.getSimilarCategory();
	}
	/*
	 * PLACING ORDER
	 * */
	
	@Override
	@Transactional
	public OrderBean placingOrderOfProduct(OrderBean orderBean,String couponCode) throws OrderDetailsNotFoundException {
		if(orderBean.getOrderId()== null) {
			throw new OrderDetailsNotFoundException("Product is not available");
		}
		
		else {
		if(orderBean.getMinBillingAmount()<orderBean.getTotalPrice()) {
		double price=applyCoupons(couponCode, orderBean.getTotalPrice());
		orderBean.setTotalPrice(price);
		}
		
		return orderRepo.save(orderBean);
	}}
	/*
	 * Vineesha team
	 * */
	private Double applyCoupons(String couponCode,Double price)
	{
		CouponsBean coupon1=couponRepo.getCouponDetails(couponCode);
		LocalDate startDate=coupon1.getStartDate().toLocalDate();
		LocalDate endDate=coupon1.getEndDate().toLocalDate();
		Double totalPrice=0.0;
		if(coupon1.getCouponCode().equals(couponCode)&&startDate.isBefore(LocalDate.now())&&endDate.isAfter(LocalDate.now()))
		{
			totalPrice=price-((price*coupon1.getCouponAmount())/100);
		}
			return totalPrice;
	}

	@Override
	@Transactional
	public OrderBean CheckProductAvailability(String id) throws OrderDetailsNotFoundException {
		
	 OrderBean orderBean= orderRepo.getOne(id);
	 if(orderBean.getOrderId()== null) {
		 throw new OrderDetailsNotFoundException("Product is not available");
	 }
	 else {
	 
	 if(orderBean.getOrderStatus().equalsIgnoreCase("Order is Confirmed")) {
		 
		 List<ProductBean> placingOrder = orderBean.getProduct();
		 
		 for (ProductBean productBean : placingOrder) {
			 orderBean.getQuantity();
			
			 int quantity = productBean.getQuantity() - orderBean.getQuantity();
			 productBean.setQuantity(quantity);
			 
		}
	 }
	 return orderRepo.save(orderBean);
	 }	
	}
	
	/*
	 * PROMO CODE
	 * */
	
	@Override
	@Transactional
	public String addPromo(PromosBean promos) throws PromoCodeInvalidException {
		
		/*if(promoss.getPromoCode()==null) {
			throw new PromoCodeInvalidException("PromoCode is Not Valid");
		}*/
		//else {
		promoRepo.save(promos);
		return promos.getPromoCode()+" is added ";
	//}
	}

	@Override
	public PromosBean viewByPromoCode(String pId) throws PromoCodeInvalidException{
		/*if(pId==null) {
			throw new PromoCodeInvalidException("Promo code is not a valid one");
		}else*/
		return promoRepo.getOne(pId);
	}

	@Override
	public List<PromosBean> viewAllPromos() throws PromoCodeInvalidException {
		/*if(PromosBean.class==null) {
			throw new PromoCodeInvalidException("No Promos Available");
		}
		else*/
		return promoRepo.findAll();
	}
	/*
	 * Return goods + delivery
	 * */
	
	@Override
	@Transactional
	public OrderBean returnProduct(String order) throws ProductNotFoundException {
		OrderBean orderBean;
		
			orderBean =orderRepo.getOne(order);
			if (orderBean.getOrderStatus().equalsIgnoreCase("Delivered")) {
				orderBean.setOrderStatus("Returned");

				List<ProductBean> returningProcess = orderBean.getProduct();

				for (ProductBean productBean : returningProcess) {

					int quantity = productBean.getQuantity() + orderBean.getQuantity();
					productBean.setQuantity(quantity);

				}
				return orderRepo.save(orderBean);
			
		} /*else {
			throw new ProductNotFoundException("Order Not delivered yet");
		}*/

		
		return null;
	}

	@Override
	public String getDeliveryStatus(String orderId) throws ProductNotFoundException {
		OrderBean oDetails = orderRepo.getOne(orderId);

		if (orderId == null) {
			throw new ProductNotFoundException("Invalid OrderId");
		}
		String status = oDetails.getOrderStatus();
		return status;
	}
	
	
	/**
	 * Vineesha Team modules
	 * */
	@Transactional
	@Override
	public String createCoupon(String emailId, CouponsBean coupon) throws GeneratingCouponsException {
		CouponsBean coupon1=new CouponsBean();
		AdminBean admin1=adminRepo.findAdmin(emailId);
		if(admin1.getEmailId().equals(emailId)) {
		coupon1.setCouponCode(coupon.getCouponCode());
		coupon1.setCouponAmount(coupon.getCouponAmount());
		coupon1.setStartDate(coupon.getStartDate());
		coupon1.setEndDate(coupon.getEndDate());
		couponRepo.save(coupon1);
		//String output=sendEmail(emailId, coupon1);
		return (coupon1.getCouponCode()+" is generated and sent to all customers successfully");
		} 
			else {
				throw new GeneratingCouponsException("coupon cannot be generated");
			}
		
	}
	
	@Transactional
	@Override
	public List<ProductBean> addProductToCart(String emailId, String productId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(emailId);
		ProductBean product = productRepo.getProduct(productId);

		
		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}else if(product==null){
			throw new CartException("product doesnt exists");
		}else {
			
		
			customer.getCart().add(product);
			customerRepo.save(customer);
		

		return customer.getCart();
		}
	}
	
	@Transactional
	@Override
	public List<ProductBean> removeProductFromProduct(String email, String productId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(email);
		ProductBean product = productRepo.getProduct(productId);

		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}else if(product==null){
			throw new CartException("product doesnt exists");
		}else
		{
			if (customer.getCart().contains(product)) 
			{
			customer.getCart().remove(product);
			}
			else {
				throw new CartException("product is not in cart");
			}
			customerRepo.save(customer);
			return customer.getCart();
		}
	}

	@Override
	public List<ProductBean> displayCart(String emailId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(emailId);
		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}
		else {
			return customer.getCart();
		}
		
	}
	@Transactional
	@Override
	public String addImage(String productId,ImageBean image) throws IOException {
		ProductBean product=productRepo.getOne(productId);
		File file=new File(image.getImagePath());
		
		byte[] bfile=new byte[(int) file.length()];
		
		FileInputStream fileInputStream =new FileInputStream(file);

	     fileInputStream.read(bfile);
	     fileInputStream.close();
	     for(byte b : bfile) {     //Just to check whether bfile has any content
	         System.out.println(b +" ");
	     }
		
		image.setImageData(bfile);
		ImageBean image1=imageRepo.save(image);
		//imageRepo.save(image);
		
		product.getImageId().add(image1);
	productRepo.save(product);
	    return "One image uploaded into database";
		
		
	}

	@Override
	public ImageBean  get(String productId)throws ServletException, IOException {
		
		ProductBean product=productRepo.getProduct(productId);
		ImageBean image=	product.getImageId().get(0);
	
	
		return image;
	}
	@Override
	public FeedbackProductBean addAnFeedback(String productId, FeedbackProductBean feedbackProductBean)
			throws FeedbackException {
		ProductBean productBean=productRepo.getOne(productId);
		if(productBean==null) {
			throw new FeedbackException("product not found");
		}else {
			
		
		FeedbackProductBean feedback=feedbackRepo.save(feedbackProductBean);
		productBean.getFeedbackProduct().add(feedback);
		
		productRepo.save(productBean);
		return feedback;
	}
		
	}
	/**
	 * Surya Team Modules
	 */
	 
	//**************getTransactionDetails************************
		@Override
		public OrderBean getTransactionalDetails(String orderId) throws TransactionException {

			return orderRepo.getOne(orderId);
		}

		

	//********************Rating***********************************
		@Override
		public Double ratingAvg() throws ProductNotFoundException {

			return feedbackRepo.avgRating();
		}

	//*************Checking, Updating Status of delivery*******************
		@Override
		public OrderBean updateStatus(OrderBean status) throws OrderDetailsNotFoundException{

			OrderBean o = orderRepo.getOne(status.getOrderId());
			o.setOrderStatus(status.getOrderStatus());
			return orderRepo.save(o);
		}

	
		// ********************Sending New Promos & list of new
		// items*********************

		@Override
		public String sendPromo(String name) throws MessagingException {

			return promoRepo.findCustomerEmail(name);
		}

	//**********************sorting High to Low****************
		@Override
		public List<ProductBean> sortByHighToLow(String category) throws SortingException {
			return productRepo.sortHighToLow(category);
		}

		public List<ProductBean> rangeSort(double min, double max, String category) throws SortingException {

			return productRepo.sortByRange(min, max, category);
		}

		// ********************sorting**low to High*********************
		@Transactional
		@Override
		public List<ProductBean> sortByLowToHigh(String category) throws SortingException {
			return productRepo.sortByLowToHigh(category);
		}

		@Override
		public List<ProductBean> sortByMostViewed(String category) throws SortingException {
			// TODO Auto-generated method stub
			return productRepo.sortByMostViewed(category);
		}

		@Override
		public ProductBean count(ProductBean productId) throws ProductNotFoundException {
			ProductBean prod = productRepo.getOne(productId.getProductId());
			int a = prod.getCount();
			if (a == 0) {
				prod.setCount(1);
			} else {

				Integer b = a + 1;
				prod.setCount(b);
			}

			return productRepo.save(prod);
		}
	//***********************refund*****************
		@Override
		public String refund(String orderId) {
			OrderBean order = orderRepo.getOne(orderId);
			 order.setOrderStatus("returned");
			 orderRepo.save(order );
			double a =  (order.getMinBillingAmount())*(order.getQuantity());
			PaymentDetailsBean p = paymentRepo.refund(orderId);
			double d=p.getCapStoreRevenueAmount();
			double c = d-a;
			p.setCapStoreRevenueAmount(c);
			paymentRepo.save(p);
			return "refunded";
		}
		
		/*
		 * Ganesh team*/
		@Override
		public List<PaymentDetailsBean> transactionAnalysis(Date start, Date end){
			
			return productRepo.transactionAnalysis(start, end);
		}
		
		@Transactional
		@Override
		public Double updateRevenue(String payment) {
			
			PaymentDetailsBean payment1= paymentRepo.getOne(payment);
			payment1.setTransactionId(payment);
			
			double revenue=payment1.getCapStoreRevenueAmount();
			double amt = payment1.getOrder().getMinBillingAmount();
			revenue=revenue+amt;
					
			payment1.setCapStoreRevenueAmount(revenue);
			paymentRepo.save(payment1);
			
			return revenue;
			
		}


		@Override
		@Transactional
		public MerchantBean addMerchant(MerchantBean merchant) throws UserNotFoundException{
			return	merchanRepo.save(merchant);
			 	
		}
		@Override
		@Transactional
		public void deleteMerchant(String id) throws UserNotFoundException{
			
			
			merchanRepo.deleteById(id);
			
		}

		@Override
		@Transactional
		public MerchantBean addThirdPartyMerchant(MerchantBean merchant) throws UserNotFoundException{
			return	merchanRepo.save(merchant);
		}
		
		@Override
		public String generateInvoice(String customerId)
		
		{
			
			String res = "";
					
			CustomerBean bean = customerRepo.getOne(customerId);
			List<ProductBean> cartProd = bean.getCart();
			
			System.err.println(cartProd);
			
			for (ProductBean productBean : cartProd) {
				res = "<ul><li>Product Name: "+productBean.getProductName() + "</li>"
						+ "<br/><li> Product Price: "+productBean.getPrice()+"</li></ul>";
			}
			
			return "Hello "+customerId+"<br/> Your Product(s) are<br/>"+res;
			
		}
		
		@Override
		public String regiteredNewUser(String name) throws UserNotFoundException{
			// TODO Auto-generated method stub
			return customerRepo.findCustomerEmail(name);
		}

		@Override
		public CustomerBean sentInvitationToCustomer(String email) throws UserNotFoundException{
			// TODO Auto-generated method stub
			return customerRepo.findCustomer(email);
		}

		@Override
		public MerchantBean sendInvitationToMerchant(String email)throws UserNotFoundException {
			// TODO Auto-generated method stub
			return merchanRepo.findMerchant(email);
		}

		@Override
		public String sendSchemeToMerchant(String name)throws UserNotFoundException {
			// TODO Auto-generated method stub
			return merchanRepo.findMerchantEmail(name);
		}

		@Override
		public String sendSchemeToCustomer(String name)throws UserNotFoundException {
			// TODO Auto-generated method stub
			return customerRepo.findCustomerEmail(name);
		}

		@Override
		public String sendPromoToMerchant(String name) throws UserNotFoundException{
			// TODO Auto-generated method stub
			return merchanRepo.findMerchantEmail(name);
		}

		@Override
		public String sendPromoToCustomer(String name)throws UserNotFoundException {
			// TODO Auto-generated method stub
			return customerRepo.findCustomerEmail(name);
		}
		
		/**
		 * Deepraj Team Modules 
		 * */
		@Override
		public String discount(String pid) throws DiscountException {
			
			double totalPrice=0;
			String result="";
			ProductBean product=productRepo.getProduct(pid);
			Double price=product.getPrice();
			DiscountBean discount=product.getDiscount();
			
			double discountPercent=(double) discount.getDiscountPercent();
			
			if(price>0&&discountPercent>0)
			{
			Date discountDate=discount.getTimePeriod();  //discount date
			if(Date.valueOf(LocalDate.now()).before(discountDate) ||Date.valueOf(LocalDate.now()).equals(discountDate) ){
				System.err.println("true");
				double discountAmount=price*(discountPercent/100);
			    totalPrice=price-discountAmount;
			    result="final Price:"+totalPrice;
			} 
			else {
				System.err.println("false");
				totalPrice=price;
				result="Discount period has been expired\nfinal Price:"+totalPrice;
			}
			
			return result;
		
		}
		else
		{
			throw new DiscountException("Enter valid price and discountPercent");
		}
		}
		@Override
		public CustomerBean shippingDetailsMsg(String email) {
			
			CustomerBean customerBean=customerRepo.findCustomer(email);
			
			return customerBean;
		}
		
		@Override
		public List<ProductBean> displaySimilarProducts(String category) {
			// TODO Auto-generated method stub
			return productRepo.getSimilarProducts(category);
		}
		@Override
		@Transactional
		public ProductBean addProduct(String productId, String email) {
			ProductBean product = productRepo.getOne(productId);
			CustomerBean customer =customerRepo.getOne(email);
			WishlistBean wishlist = customer.getWishlist();
			List<ProductBean> prod = wishlist.getProduct();
			wishlist.setProduct(prod);
			prod.add(product);
			wishlist.setProduct(prod);
			wishlistRepo.save(wishlist);
			return product;
		}

		@Override
		@Transactional
		public WishlistBean deleteProductsFromWishlist(String email,String productId) {
			ProductBean product = productRepo.getOne(productId);
			CustomerBean customer = customerRepo.getOne(email);
			WishlistBean wishlist=wishlistRepo.getOne(customer.getWishlist().getWishlistId());
			List<ProductBean> prod = wishlist.getProduct();
			prod.remove(product);
			wishlist.setProduct(prod);
			wishlistRepo.save(wishlist);
			return wishlist;
			
		}

		@Override
		public WishlistBean displayProductsFromWishlist(String email) {
			System.err.println("email:"+email);
			CustomerBean customer = customerRepo.getOne(email);
			String wishlistId = customer.getWishlist().getWishlistId();
			return wishlistRepo.getOne(wishlistId);
		}

		@Override
		public String inviteFriend(String customerName) {
			// TODO Auto-generated method stub
			return customerRepo.findCustomerEmail(customerName);
		}
}

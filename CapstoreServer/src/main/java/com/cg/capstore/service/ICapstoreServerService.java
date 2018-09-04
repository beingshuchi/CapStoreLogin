package com.cg.capstore.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

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

public interface ICapstoreServerService {
	CustomerBean validateCustomer(String email,String password) throws UserNotFoundException;
	Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address) throws UserNotFoundException;
	Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password, String merchantType)throws UserNotFoundException;
	String forgotPassword(String email) throws UserNotFoundException;
	AdminBean validateAdmin(String email,String password) throws UserNotFoundException;
	MerchantBean validateMerchant(String email,String password) throws UserNotFoundException;
	Boolean changePasswordCustomer(String oldPassword,String newPassword) throws UserNotFoundException;
	Boolean changePasswordMerchant(String oldPassword,String newPassword) throws UserNotFoundException;
	Boolean changePasswordAdmin(String oldPassword,String newPassword) throws UserNotFoundException;
	CustomerBean getCustomer(String email)throws UserNotFoundException;
	 MerchantBean getMechant(String email) throws UserNotFoundException;
	CustomerBean editProfileCustomer(String email,String customerName,String address, String mobileNo) throws UserNotFoundException;
	List<CustomerBean> getAllCustomerDetails();
	List<MerchantBean> getAllMerchants();
	
	/*
	 * SEARCH---Divya
	 * */
	
	List<ProductBean> searchProductByName(String name)throws SearchException;
	List<ProductBean> searchProductByCategory(String category)throws SearchException;
	List<ProductBean> searchProductByPrice(Double price)throws SearchException;
	CustomerBean searchCustomerByName(String name)throws SearchException;
	MerchantBean searchMerchantByName(String name)throws SearchException;
	OrderBean searchOrderById(String id)throws SearchException;
	List<OrderBean> searchOrderByStatus(String Status)throws SearchException;
	PromosBean searchPromosByCode(String Code)throws SearchException;
	DiscountBean searchDiscountById(String Id)throws SearchException;
	
	/*
	 * Discount modules for admin and merchant
	 * */
	DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException;
	DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException;
	List<DiscountBean> findAllDiscounts()throws DiscountDateExceedException;
	
	/*
	 * Merchant managing inventory
	 * */
	
	List<ProductBean> displayAllProducts() throws ProductNotFoundException;
	ProductBean addNewProduct(ProductBean product) throws ProductNotFoundException;
	ProductBean updateProductDetails(ProductBean product) throws ProductNotFoundException;
	String deleteProduct(String productId) throws ProductNotFoundException;	
	ProductBean getProductdetailsById(String productId) throws ProductNotFoundException;
	List<ProductBean> displayAllCategory() throws CategoryNotFoundException;
	String removeExistingCategory(String category) throws CategoryNotFoundException;
	
	/*
	 * PLACING ORDER
	 * */
	OrderBean CheckProductAvailability(String id) throws OrderDetailsNotFoundException;
	OrderBean placingOrderOfProduct(OrderBean orderBean, String couponCode) throws OrderDetailsNotFoundException;
	
	/*
	 * Promo code
	 * */
	
	String addPromo(PromosBean promoss) throws PromoCodeInvalidException ;
	PromosBean viewByPromoCode(String pId) throws PromoCodeInvalidException;
	List<PromosBean> viewAllPromos() throws PromoCodeInvalidException;
	
	/*
	 * Return goods and delivery status
	 * */
	
	OrderBean returnProduct(String id) throws ProductNotFoundException; 
	String getDeliveryStatus (String orderId) throws ProductNotFoundException;
	
	/**
	 * Vineesha team modules
	 * */
	
	String createCoupon(String emailId, CouponsBean coupon) throws GeneratingCouponsException;
	List<ProductBean> addProductToCart(String emailId, String productId) throws CartException;
	List<ProductBean> removeProductFromProduct(String emailId, String productId) throws CartException;
	List<ProductBean> displayCart(String emailId) throws CartException;
	ImageBean get(String productId)throws ServletException, IOException;

	String addImage(String prodcutId,ImageBean image) throws FileNotFoundException, IOException;

	
	FeedbackProductBean addAnFeedback(String productId, FeedbackProductBean feedbackProductBean)throws FeedbackException;

	
	
	/**
	 * Surya team modules
	 * */
	
	
 OrderBean getTransactionalDetails(String orderId) throws TransactionException;
	
	 
	
 Double ratingAvg() throws ProductNotFoundException;
	
	 OrderBean updateStatus(OrderBean status) throws OrderDetailsNotFoundException;
	
	 
	
	 String sendPromo(String name) throws MessagingException;
	 List<ProductBean> sortByLowToHigh(String category) throws SortingException ;
	List<ProductBean> sortByHighToLow(String category) throws SortingException ;
	
	 List<ProductBean> rangeSort(double min,double max, String category) throws SortingException ;
	
	 List<ProductBean> sortByMostViewed(String category) throws SortingException ;
	 ProductBean count(ProductBean productId) throws ProductNotFoundException ; 
	
	 String refund(String orderId) throws OrderDetailsNotFoundException;
	 
	 /**
	  * Ganesh team
	  */
		List<PaymentDetailsBean> transactionAnalysis(Date start, Date end);
		 MerchantBean addMerchant(MerchantBean merchant) throws UserNotFoundException;
		
		 void deleteMerchant(String Id) throws UserNotFoundException;
		
		 MerchantBean addThirdPartyMerchant(MerchantBean merchant) throws UserNotFoundException;

		
		 Double updateRevenue(String payment)throws UserNotFoundException;
		 String generateInvoice(String customerId) throws UserNotFoundException;
		 public String regiteredNewUser(String name) throws UserNotFoundException;

			public MerchantBean sendInvitationToMerchant(String email) throws UserNotFoundException;

			public CustomerBean sentInvitationToCustomer(String email) throws UserNotFoundException;

			public String sendSchemeToMerchant(String name) throws UserNotFoundException;

			public String sendSchemeToCustomer(String name) throws UserNotFoundException;

			public String sendPromoToMerchant(String name) throws UserNotFoundException;

			public String sendPromoToCustomer(String name) throws UserNotFoundException;
			
			/*
			 * Deepraj team modules
			 * */
			
			public String discount(String pid) throws DiscountException;
			public String inviteFriend(String customerName);
			public CustomerBean shippingDetailsMsg(String email);
			public List<ProductBean> displaySimilarProducts(String category);
			public ProductBean addProduct(String productId, String email);
			public WishlistBean deleteProductsFromWishlist(String email,String productId);
			public WishlistBean displayProductsFromWishlist(String email);

		 
}

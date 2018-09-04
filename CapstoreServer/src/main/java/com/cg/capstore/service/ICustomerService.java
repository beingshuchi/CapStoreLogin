package com.cg.capstore.service;

import java.util.List;

import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.beans.WishlistBean;
import com.cg.capstore.exception.CartException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.TransactionException;
import com.cg.capstore.exception.UserNotFoundException;

public interface ICustomerService {
	CustomerBean validateCustomer(String email,String password) throws UserNotFoundException;
	Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address) throws UserNotFoundException;
	Boolean changePasswordCustomer(String oldPassword,String newPassword) throws UserNotFoundException;
	CustomerBean getCustomer(String email)throws UserNotFoundException;
	CustomerBean editProfileCustomer(String email,String customerName,String address, String mobileNo) throws UserNotFoundException;
	OrderBean placingOrderOfProduct(OrderBean orderBean, String couponCode) throws OrderDetailsNotFoundException;
	
	List<ProductBean> addProductToCart(String emailId, String productId) throws CartException;
	List<ProductBean> removeProductFromProduct(String emailId, String productId) throws CartException;
	List<ProductBean> displayCart(String emailId) throws CartException;
	OrderBean getTransactionalDetails(String orderId) throws TransactionException;
	 String generateInvoice(String customerId) throws UserNotFoundException;
	 CustomerBean shippingDetailsMsg(String email);
	 WishlistBean displayProductsFromWishlist(String email);
	 WishlistBean deleteProductsFromWishlist(String email,String productId);
	 String inviteFriend(String customerName);
}

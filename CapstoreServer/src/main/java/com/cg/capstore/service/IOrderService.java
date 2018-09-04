package com.cg.capstore.service;

import java.util.List;

import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;

public interface IOrderService {
	OrderBean searchOrderById(String id)throws SearchException;
	List<OrderBean> searchOrderByStatus(String Status)throws SearchException;
	OrderBean CheckProductAvailability(String id) throws OrderDetailsNotFoundException;
	OrderBean returnProduct(String id) throws ProductNotFoundException; 
	String getDeliveryStatus (String orderId) throws ProductNotFoundException;
}

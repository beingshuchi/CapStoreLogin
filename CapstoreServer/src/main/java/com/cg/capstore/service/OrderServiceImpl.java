package com.cg.capstore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.repo.IOrderRepo;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private IOrderRepo orderRepo;
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
	

}

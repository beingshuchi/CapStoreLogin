package com.cg.capstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.service.IOrderService;

@RestController
public class OrderController {
	@Autowired
	private IOrderService service;
	@RequestMapping(value="/searchorder",method=RequestMethod.GET)
	public OrderBean searchByOrderId(String id)throws SearchException {
		return service.searchOrderById(id);	
		
}
	
	@RequestMapping(value="/searchorderstatus",method=RequestMethod.GET)
	public List<OrderBean> searchOrderByStatus(String Status)throws SearchException {
		return service.searchOrderByStatus(Status);	
}
	@RequestMapping(value="/finalizeorder", method=RequestMethod.POST)
	public OrderBean checkProductAvailability(String id) throws OrderDetailsNotFoundException {
		
		//try {
			return service.CheckProductAvailability(id);
		/*} catch (OrderDetailsNotFoundException e) {
			throw e;
		}*/
	}
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
}

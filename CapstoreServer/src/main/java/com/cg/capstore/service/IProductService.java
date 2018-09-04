package com.cg.capstore.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import com.cg.capstore.beans.FeedbackProductBean;
import com.cg.capstore.beans.ImageBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.CategoryNotFoundException;
import com.cg.capstore.exception.DiscountException;
import com.cg.capstore.exception.FeedbackException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.SortingException;

public interface IProductService {
	List<ProductBean> searchProductByName(String name)throws SearchException;
	List<ProductBean> searchProductByCategory(String category)throws SearchException;
	List<ProductBean> searchProductByPrice(Double price)throws SearchException;
	List<ProductBean> displayAllCategory() throws CategoryNotFoundException;
	FeedbackProductBean addAnFeedback(String productId, FeedbackProductBean feedbackProductBean)throws FeedbackException;
	ImageBean get(String productId)throws ServletException, IOException;
	String addImage(String prodcutId,ImageBean image) throws FileNotFoundException, IOException;
	 
	 List<ProductBean> sortByLowToHigh(String category) throws SortingException ;
	List<ProductBean> sortByHighToLow(String category) throws SortingException ;
	
	 List<ProductBean> rangeSort(double min,double max, String category) throws SortingException ;
	 List<ProductBean> displayAllProducts() throws ProductNotFoundException;
	 List<ProductBean> sortByMostViewed(String category) throws SortingException ;
	 ProductBean count(ProductBean productId) throws ProductNotFoundException ; 
	 Double ratingAvg() throws ProductNotFoundException;
	 public String discount(String pid) throws DiscountException;
		
	 ProductBean addNewProduct(ProductBean product) throws ProductNotFoundException;
		ProductBean updateProductDetails(ProductBean product) throws ProductNotFoundException;
		String deleteProduct(String productId) throws ProductNotFoundException;	
		ProductBean getProductdetailsById(String productId) throws ProductNotFoundException;
		
		String removeExistingCategory(String category) throws CategoryNotFoundException;
		public List<ProductBean> displaySimilarProducts(String category);
}

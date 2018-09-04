package com.cg.capstore.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capstore.beans.FeedbackProductBean;
import com.cg.capstore.beans.ImageBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.CategoryNotFoundException;
import com.cg.capstore.exception.DiscountException;
import com.cg.capstore.exception.FeedbackException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.SortingException;
import com.cg.capstore.service.IProductService;

@RestController
public class ProductController {
	@Autowired
	private IProductService service;

	@RequestMapping(value="/search",method=RequestMethod.GET)
	public List<ProductBean> searchByProductName(String name)throws SearchException {
		return service.searchProductByName(name); 
	}
	
	
		@RequestMapping(value="/searchcategory",method=RequestMethod.GET)
		public List<ProductBean> searchByProductCategory(String category) throws SearchException {
			return service.searchProductByCategory(category);
			
		}
		@RequestMapping(value="/searchprice",method=RequestMethod.GET)
		public List<ProductBean> searchByPrice(Double price) throws SearchException{
			return service.searchProductByPrice(price);
	}
		
		@RequestMapping(value="/displayAllCategory", method=RequestMethod.POST)
		public List<ProductBean> displayAllCategory(String category) throws CategoryNotFoundException {
			//try {
				return service.displayAllCategory();
			/*} catch (CategoryNotFoundException e) {
				throw e;
			}*/
		}
		

		@RequestMapping(value = "/displayallproducts",method=RequestMethod.POST)
		List<ProductBean> displayAllProducts() throws ProductNotFoundException {
			
			//try {
				return service.displayAllProducts();
			//} catch (ProductNotFoundException e) {
				//throw e;
			//}
			
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
			
			@RequestMapping(value = "/Avg", method = RequestMethod.GET)
			public Double ratingAvg() throws ProductNotFoundException {
				return service.ratingAvg();

			}
			@RequestMapping(value = "/count", method = RequestMethod.GET)
			public ProductBean count(ProductBean productId) throws ProductNotFoundException {
				return service.count(productId);
				
			}
			@RequestMapping(value="/discount")
			public String  Discount(String pid) throws DiscountException
			{
				
					return service.discount(pid);
				
			}
			  @RequestMapping(value="/displayAllSimilarProducts", method=RequestMethod.GET)
			 	public List<ProductBean> SimilarProductsInterface(String category) {
			 		return service.displaySimilarProducts(category);
			 	}
			  
			  @RequestMapping(value = "/addimage", method = RequestMethod.POST)
				public String addImage(String productId, ImageBean image) throws FileNotFoundException, IOException {

					
					return service.addImage(productId,image);
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
}

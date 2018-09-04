package com.cg.capstore.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.DiscountBean;
import com.cg.capstore.beans.FeedbackProductBean;
import com.cg.capstore.beans.ImageBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.CategoryNotFoundException;
import com.cg.capstore.exception.DiscountException;
import com.cg.capstore.exception.FeedbackException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.SortingException;
import com.cg.capstore.repo.ICouponRepo;
import com.cg.capstore.repo.IDiscountRepo;
import com.cg.capstore.repo.IFeedbackProductRepo;
import com.cg.capstore.repo.IImageRepo;
import com.cg.capstore.repo.IMerchantRepo;
import com.cg.capstore.repo.IPaymentRepo;
import com.cg.capstore.repo.IProductRepo;
import com.cg.capstore.repo.IPromoRepo;
import com.cg.capstore.repo.IWishlistRepo;
@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductRepo productRepo;
	
	@Autowired
	private IImageRepo imageRepo;
	@Autowired
	private IFeedbackProductRepo feedbackRepo;
	
	
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
	
	@Override
	public List<ProductBean> displayAllCategory() throws CategoryNotFoundException {
	/*	if(ProductBean.class==null) {
			throw new CategoryNotFoundException("Inventory with category is empty");
		}*/
		return productRepo.getSimilarCategory();
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
	public List<ProductBean> displayAllProducts() throws ProductNotFoundException {
		/*if(ProductBean.class == null) {
			throw new ProductNotFoundException("Inventory is empty");
		}*/
		return productRepo.findAll();
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
	
		

	//********************Rating***********************************
		@Override
		public Double ratingAvg() throws ProductNotFoundException {

			return feedbackRepo.avgRating();
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
		public List<ProductBean> displaySimilarProducts(String category) {
			// TODO Auto-generated method stub
			return productRepo.getSimilarProducts(category);
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
}

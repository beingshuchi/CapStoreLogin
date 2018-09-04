package com.cg.capstore.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.PaymentDetailsBean;
import com.cg.capstore.beans.ProductBean;
@Repository
public interface IProductRepo extends JpaRepository<ProductBean, String> {
	@Query("select distinct p.category from ProductBean p")
	List<ProductBean> getSimilarCategory();
	
	@Query("select p from ProductBean p where productName=(:name)")
	public List<ProductBean> searchProductByName(@Param(value = "name") String name);

	@Query("select p from ProductBean p where category=(:category)")
	public List<ProductBean> searchProductByCategory(@Param(value = "category") String category);

	@Query("select p from ProductBean p where price=(:price)")
	public List<ProductBean> searchProductByPrice(@Param(value = "price") Double price);
	
	@Query(value="select p from ProductBean p where p.productId=(:productId)")
	ProductBean getProduct(@Param(value="productId")String productId);
	@Query("select p from ProductBean p where p.category =(:category) order by p.price desc ")
	List<ProductBean> sortHighToLow(@Param(value = "category") String category);

	@Query("select p from ProductBean p  where p.category =(:category) order by p.price  ")
	List<ProductBean> sortByLowToHigh(@Param(value = "category") String category);

	@Query("select p from ProductBean p Where p.price BETWEEN (:min) AND (:max) and  p.category =(:category) order by p.price ")
	List<ProductBean> sortByRange(@Param(value = "min") double min, @Param(value = "max") double max,
			@Param(value = "category") String category);
	

	@Query("SELECT p FROM ProductBean p where p.category =(:category) order by p.count desc")
	List<ProductBean> sortByMostViewed(@Param(value = "category") String category);
	
	@Query("select a from PaymentDetailsBean a where a.transactionDate BETWEEN (:start) AND (:end)")
	List<PaymentDetailsBean> transactionAnalysis(@Param(value="start") Date start,@Param(value="end") Date end);
	@Query("select p.productName from ProductBean p  where p.category=(:category) order by p.category")
	public List<ProductBean> getSimilarProducts(@Param(value="category") String category);
}

package com.cg.capstore.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;

@Repository
public interface ICustomerRepo extends JpaRepository<CustomerBean, String> {
	@Query("SELECT customer FROM CustomerBean customer WHERE customer.email= :email")
	CustomerBean findCustomer(@Param(value="email") String email);
}

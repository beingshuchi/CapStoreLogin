package com.cg.capstore.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.capstore.beans.CustomerBean;

@Repository
public interface ICustomerRepo extends JpaRepository<CustomerBean, String> {
	@Query("SELECT customer FROM CustomerBean customer WHERE customer.email= :email")
	CustomerBean findCustomer(@Param(value="email") String email);
	@Query("SELECT customer FROM CustomerBean customer WHERE customer.password= :pass")
	CustomerBean findPassword(@Param(value="pass")String password);
	@Query("select p from CustomerBean p where p.customerName=(:customerName)")
	public CustomerBean searchCustomerByName(@Param(value = "customerName") String name);
	@Query(value = "SELECT c.email FROM CustomerBean c WHERE c.customerName=(:name)")
	public String findCustomerEmail(@Param(value = "name") String name);
	
}

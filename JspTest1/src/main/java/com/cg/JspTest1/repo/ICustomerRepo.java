package com.cg.JspTest1.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cg.JspTest1.bean.CustomerBean;
@Repository
public interface ICustomerRepo extends JpaRepository<CustomerBean, String> {
	@Transactional
	@Query("SELECT customer FROM CustomerBean customer WHERE customer.email=?1")
	CustomerBean FinByEmail(String email);
}

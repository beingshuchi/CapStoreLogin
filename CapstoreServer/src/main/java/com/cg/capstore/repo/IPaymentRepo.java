package com.cg.capstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.PaymentDetailsBean;
@Repository
public interface IPaymentRepo extends JpaRepository<PaymentDetailsBean, String> {
	@Query("select p from PaymentDetailsBean p where p.order.orderId = (:orderId)")
	PaymentDetailsBean refund(@Param(value="orderId") String orderId);
	@Query("select e.minBillingAmount from OrderBean e where e.orderId=(:id)")
	Double getAmount(@Param(value="id") String id);
	
	@Query("select e.transactionDate,e.paymentAmount,e.transactionId from PaymentDetailsBean e where e.transactionId=(:id)")
	PaymentDetailsBean findtransactionBytransactionId(@Param(value="id") String id);
}

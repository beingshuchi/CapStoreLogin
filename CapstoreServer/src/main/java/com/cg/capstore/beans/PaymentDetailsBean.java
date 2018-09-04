package com.cg.capstore.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Transaction")
public class PaymentDetailsBean {

	@Id
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name="payment_amount")
	private Double paymentAmount;
	
	@Column(name="revenue")
	private Double capStoreRevenueAmount;

	@OneToOne
	@JoinColumn(name="order_Id")
	private OrderBean order;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getCapStoreRevenueAmount() {
		return capStoreRevenueAmount;
	}

	public void setCapStoreRevenueAmount(Double capStoreRevenueAmount) {
		this.capStoreRevenueAmount = capStoreRevenueAmount;
	}

	
	
	public PaymentDetailsBean(String transactionId, Date transactionDate, Double paymentAmount,
			Double capStoreRevenueAmount, OrderBean order) {
		super();
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.paymentAmount = paymentAmount;
		this.capStoreRevenueAmount = capStoreRevenueAmount;
		this.order = order;
	}

	public OrderBean getOrder() {
		return order;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}

	public PaymentDetailsBean() {
		super();
	}
	
}

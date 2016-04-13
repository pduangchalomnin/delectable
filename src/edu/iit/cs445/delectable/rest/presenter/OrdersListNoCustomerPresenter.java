package edu.iit.cs445.delectable.rest.presenter;

import java.math.BigDecimal;

import edu.iit.cs445.delectable.entity.Order;

public class OrdersListNoCustomerPresenter {
	private int id;
	private String order_date;
	private String delivery_date;
	private BigDecimal amount;
	private double surcharge;
	private String status;
	
	public OrdersListNoCustomerPresenter(Order order){
		this.id = order.getId();
		this.order_date = order.getOrderDate();
		this.delivery_date = order.getDeliveryDate();
		this.amount = order.getTotalAmount();
		this.surcharge = order.getSurcharge();
		this.status = order.getStatus();
	}
}

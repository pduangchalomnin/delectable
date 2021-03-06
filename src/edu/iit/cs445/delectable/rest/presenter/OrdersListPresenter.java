package edu.iit.cs445.delectable.rest.presenter;

import java.math.BigDecimal;

import edu.iit.cs445.delectable.entity.Order;

public class OrdersListPresenter {
	private int id;
	private String order_date;
	private String delivery_date;
	private BigDecimal amount;
	private double surcharge;
	private String status;
	private String ordered_by;
	
	public OrdersListPresenter(Order order){
		this.id = order.getId();
		this.order_date = order.getOrderDate();
		this.delivery_date = order.getDeliveryDate();
		this.amount = order.getTotalAmount();
		this.surcharge = order.getSurcharge();
		this.status = order.getStatus();
		this.ordered_by = order.getCustomerEmail();
	}
}

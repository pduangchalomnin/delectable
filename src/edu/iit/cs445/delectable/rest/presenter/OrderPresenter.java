package edu.iit.cs445.delectable.rest.presenter;

import java.math.BigDecimal;
import java.util.List;

import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.Order;

public class OrderPresenter {
	private int id;
	private BigDecimal amount;
	private double surcharge;
	private String status;
	private String order_date;
	private String delivery_date;
	private CustomerWithOutIdPresenter ordered_by;
	private String delivery_address;
	private String note;
	private List<OrderDetailPresenter> order_detail;
	
	public OrderPresenter(Order order,CustomerWithOutIdPresenter customer,List<OrderDetailPresenter> order_detail) {
		this.id = order.getId();
		this.amount = order.getTotalAmount();
		this.surcharge = order.getSurcharge();
		this.status = order.getStatus();
		this.order_date = order.getOrderDate();
		this.delivery_date = order.getDeliveryDate();
		this.ordered_by = customer;
		this.delivery_address = order.geteDeliveryAddress().toString();
		this.note = order.getNote();
		this.order_detail = order_detail;
	}
}

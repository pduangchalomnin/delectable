package edu.iit.cs445.delectable.rest.presenter;

import java.math.BigDecimal;
import java.util.List;

import edu.iit.cs445.delectable.entity.Order;

public class ReportOrdersPresenter {
	int id;
	BigDecimal amount;
	double surcharge;
	String status;
	String order_date;
	String delivery_date;
	CustomerWithOutIdPresenter ordered_by;
	String delivery_address;
	String note;
	List<OrderDetailPresenter> order_detail;
	
	public ReportOrdersPresenter(Order order,List<OrderDetailPresenter> order_detail) {
		this.id = order.getId();
		this.amount = order.getTotalAmount();
		this.surcharge = order.getSurcharge();
		this.status = order.getStatus();
		this.order_date = order.getOrderDate();
		this.delivery_date = order.getDeliveryDate();
		this.ordered_by = new CustomerWithOutIdPresenter(order.getCustomer());
		this.delivery_address = order.geteDeliveryAddress().toString();
		this.note = order.getNote();
		this.order_detail = order_detail;
	}
}

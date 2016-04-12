package edu.iit.cs445.delectable.rest.presenter;

import java.math.BigDecimal;
import java.util.List;

import edu.iit.cs445.delectable.entity.Customer;

public class OrderPresenter {
	private int id;
	private BigDecimal amount;
	private double surcharge;
	private String status;
	private String order_date;
	private String delivery_date;
	private Customer ordered_by;
	private String delivery_address;
	private String note;
	private List<OrderDetailPresenter> order_detail;
	
	public OrderPresenter(int id,BigDecimal amount,Double surcharge
			,String status,String order_date,String delivery_date
			,Customer ordered_by,String delivery_address,String note
			,List<OrderDetailPresenter> order_detail) {
		this.id = id;
		this.amount = amount;
		this.surcharge = surcharge;
		this.status = status;
		this.order_date = order_date;
		this.delivery_date = delivery_date;
		this.ordered_by = ordered_by;
		this.delivery_address = delivery_address;
		this.note = note;
		this.order_detail = order_detail;
	}
}

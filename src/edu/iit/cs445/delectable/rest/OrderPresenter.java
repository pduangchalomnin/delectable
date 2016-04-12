package edu.iit.cs445.delectable.rest;

import java.math.BigDecimal;

public class OrderPresenter {
	private int id;
	private String order_date;
	private String delivery_date;
	private BigDecimal amount;
	private double surcharge;
	private String status;
	private String ordered_by;
	
	public OrderPresenter(int id, String order_date, String delivery_date
			,BigDecimal amount,double surcharge,String status,String ordered_by){
		this.id = id;
		this.order_date = order_date;
		this.delivery_date = delivery_date;
		this.amount = amount;
		this.surcharge =surcharge;
		this.status = status;
		this.ordered_by = ordered_by;
	}
}

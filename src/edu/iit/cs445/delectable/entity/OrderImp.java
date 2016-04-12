package edu.iit.cs445.delectable.entity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderImp implements Order {
	
	private static int idCounter = 0;
	private int id;
	private List<Item> items;
	private String deliveryDate;
	private String orderDate;
	private Customer ordered_by;
	private String note;
	private Address delivery_address;
	private Status status;
	private double surcharge;
	
	public OrderImp(Customer customer,String deliveryDate,String note,List<Item> items,Address deliveryAddress,double surcharge)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		this.id = idCounter++;
		this.items = items;
		this.deliveryDate = deliveryDate;
		this.orderDate = dateFormat.format(date);
		this.note = note;
		this.ordered_by = customer;
		this.delivery_address = deliveryAddress;
		this.status = Status.OPEN;
		this.surcharge = surcharge;
	}

	public String getCustomerEmail() {
		return ordered_by.getEmail();
	}

	public String getStatus() {
		return status.toString();
	}

	public BigDecimal getTotalAmount() {
		BigDecimal total = new BigDecimal(0);
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			Item item = it.next();
			total = total.add(item.getAmount());	
		}
		total = total.setScale(2, BigDecimal.ROUND_HALF_UP);   
		return total;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return ordered_by;
	}

	public String getNote() {
		return note;
	}
	
	public List<Item> listItem() {
		return items;
	}

	public double getSurcharge() {
		return surcharge;
	}
	
	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public Address geteDeliveryAddress() {
		return delivery_address;
	}

	public void changeStatus(Status status) {
		this.status = status;
	}

	public boolean isMatch(String keyword) {
		if(this.deliveryDate.matches("(.*)"+keyword+"(.*)")
				|| this.orderDate.matches("(.*)"+keyword+"(.*)")
				|| this.note.matches("(.*)"+keyword+"(.*)")
				|| this.ordered_by.isMatch(keyword)
				|| this.status.toString().equals(keyword)
				|| this.delivery_address.isMatch(keyword))
			return true;
		return false;
	}

	public boolean isNil() {
		return false;
	}

	public int getCustomerId() {
		return ordered_by.getId();
	}

	public boolean isCustomerMatch(String keyword) {
		return this.ordered_by.isMatch(keyword);
	}

}

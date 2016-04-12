package edu.iit.cs445.delectable.entity;

import java.math.BigDecimal;
import java.util.List;

public interface Order {

	public void changeStatus(Status status);
	public double getSurcharge();
	public String getCustomerEmail();
	public int getCustomerId();
	public String getStatus();
	public BigDecimal getTotalAmount();
	public String getDeliveryDate();
	public String getOrderDate();
	public int getId();
	public Customer getCustomer();
	public String getNote();
	public List<Item> listItem();
	public Address geteDeliveryAddress();
	public void setSurcharge(double surcharge);
	public boolean isMatch(String keyword);
	public boolean isCustomerMatch(String keyword);
	public boolean isNil();
	
}

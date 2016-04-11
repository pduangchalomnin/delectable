package edu.iit.cs445.delectable.entity;

import java.util.List;

public interface Customer {
	public String getFirstName();
	public String getLastName();
	public String getEmail();
	public String getPhoneNumber();
	public int getId();
	public boolean isMatch(String keyword);
	public List<Order> getOrderedList();
}

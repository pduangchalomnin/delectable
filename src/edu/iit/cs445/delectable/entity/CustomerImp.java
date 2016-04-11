package edu.iit.cs445.delectable.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerImp implements Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	
	private static int idCounter = 0;
	
	public CustomerImp(String firstName,String lastName,String phoneNumber,String email){
		
		this.firstName = firstName.toLowerCase();
		this.lastName = lastName.toLowerCase();
		this.phoneNumber = phoneNumber;
		this.email = email.toLowerCase();
		
		OrdersList ordersList = OrdersListImp.getInstance();
		int existingId = ordersList.checkExistingCustomer(email);
		if(existingId != -1) {
			this.id = existingId;
		}
		else {
		this.id = idCounter++;
		}
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean isMatch(String keyword) {
		keyword = keyword.toLowerCase();
		if(this.firstName.matches("(.*)"+keyword+"(.*)")
				|| this.lastName.matches("(.*)"+keyword+"(.*)")
				|| this.email.equals(keyword)
				|| this.phoneNumber.equals(keyword))
			return true;
		return false;
	}
	
	public List<Order> getOrderedList() {
		List<Order> output = new ArrayList<Order>();
		OrdersList orders = OrdersListImp.getInstance();
		Iterator<Order> it = orders.getOrders().iterator();
		while(it.hasNext()) {
			Order tmpOrder = it.next();
			if(tmpOrder.getCustomerId() == id) {
				output.add(tmpOrder);
			}
		}
		return output;
	}

}

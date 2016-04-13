package edu.iit.cs445.delectable.rest.presenter;

import java.util.List;

import edu.iit.cs445.delectable.entity.Customer;

public class CustomerWithOrderPresenter {
	private int id;
	private String name;
	private String email;
	private String phone;
	private List<OrdersListNoCustomerPresenter> orders;
	
	public CustomerWithOrderPresenter(Customer customer,List<OrdersListNoCustomerPresenter> orders) {
		this.id = customer.getId();
		this.name = customer.getFirstName()+" "+customer.getLastName();
		this.phone = customer.getPhoneNumber();
		this.email = customer.getEmail();
		this.orders = orders;
	}
}

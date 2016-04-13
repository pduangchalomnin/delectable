package edu.iit.cs445.delectable.rest.presenter;

import edu.iit.cs445.delectable.entity.Customer;

public class CustomerPresenter {
	private int id;
	private String name;
	private String email;
	private String phone;
	
	public CustomerPresenter(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getFirstName()+" "+customer.getLastName();
		this.phone = customer.getPhoneNumber();
		this.email = customer.getEmail();
	}
}

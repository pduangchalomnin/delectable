package edu.iit.cs445.delectable.rest.presenter;

import edu.iit.cs445.delectable.entity.Customer;

public class CustomerWithOutIdPresenter {
	private String name;
	private String email;
	private String phone;
	
	public CustomerWithOutIdPresenter(Customer customer) {
		this.name = customer.getFirstName()+" "+customer.getLastName();
		this.phone = customer.getPhoneNumber();
		this.email = customer.getEmail();
	}
}

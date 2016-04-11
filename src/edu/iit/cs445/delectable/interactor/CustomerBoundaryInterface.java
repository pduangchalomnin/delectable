package edu.iit.cs445.delectable.interactor;

import java.util.List;

import edu.iit.cs445.delectable.entity.Customer;

public interface CustomerBoundaryInterface {
	
	public List<Customer> getCustomerList();
	public List<Customer> getCustomerByKeyword(String keyword);
	public Customer getCustomerById(int id) throws RuntimeException;
}

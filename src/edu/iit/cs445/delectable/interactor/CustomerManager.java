package edu.iit.cs445.delectable.interactor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;

public class CustomerManager implements CustomerBoundaryInterface {
	
	private static CustomerBoundaryInterface instance = null;
	
	private CustomerManager() {
		
	}
	
	public static CustomerBoundaryInterface getInstance() {
		if(instance == null) {
			instance = new CustomerManager();
		}
		return instance;
	}

	public List<Customer> getCustomerList() {
		List<Customer> customerList = new ArrayList<Customer>();
		Iterator<Order> it =  getOrderIterator();
		
		while(it.hasNext()) {
			Order order = it.next();
			eliminateDuplicateCustomer(customerList,order.getCustomerEmail());
			customerList.add(order.getCustomer());
		}
		return customerList;
	}
	
		private Iterator<Order> getOrderIterator() {
			OrdersList ordersList = OrdersListImp.getInstance();
			Iterator<Order> it =  ordersList.getOrders().iterator();
			return it;
		}
		
		private List<Customer> eliminateDuplicateCustomer (List<Customer> customerList,String email) {
			Iterator<Customer> customerListIterator = customerList.iterator();
			while(customerListIterator.hasNext()) {
				Customer tmpCustomer = customerListIterator.next();
				if(tmpCustomer.getEmail().equals(email)) {
					customerListIterator.remove();
					break;
				}
			}
			return customerList;
		}
		
	public List<Customer> getCustomerByKeyword(String keyword) {
		List<Customer> customerList = new ArrayList<Customer>();
		Iterator<Order> it =  getOrderIterator();
		
		while(it.hasNext()) {
			Order order = it.next();
			eliminateDuplicateCustomer(customerList,order.getCustomerEmail());
			if(order.isCustomerMatch(keyword)) {
				customerList.add(order.getCustomer());
			}
		}
		return customerList;
	}

	public Customer getCustomerById(int id) throws RuntimeException {
		OrdersList ordersList = OrdersListImp.getInstance();
		ListIterator<Order> it =  ordersList.getOrders().listIterator(ordersList.getOrders().size());
		
		while(it.hasPrevious()) {
			Order order = it.previous();
			if(order.getCustomerId() == id)
				return order.getCustomer();
		}
		throw new RuntimeException();
	}

}

package edu.iit.cs445.delectable.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.interactor.CustomerBoundaryInterface;
import edu.iit.cs445.delectable.interactor.CustomerManager;
import edu.iit.cs445.delectable.rest.presenter.CustomerPresenter;
import edu.iit.cs445.delectable.rest.presenter.CustomerWithOrderPresenter;
import edu.iit.cs445.delectable.rest.presenter.OrdersListNoCustomerPresenter;

@Path("/customer")
public class Customer_REST_controller {
	private CustomerBoundaryInterface customerManager = CustomerManager.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers(@QueryParam("key") String key) {
		Gson gson = new Gson();
		String s;
		try {
			List<Customer> customers;
			if(key==null) {
				customers = customerManager.getCustomerList();
			}
			else {
				customers = customerManager.getCustomerByKeyword(key);
			}
			List<CustomerPresenter> presenter = createCustomerPresenter(customers);
			s = gson.toJson(presenter);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		return Response.ok(s).build();
		
	}

		private List<CustomerPresenter> createCustomerPresenter(List<Customer> customers) {
			List<CustomerPresenter> presenter = new ArrayList<CustomerPresenter>();
			Iterator<Customer> it = customers.iterator();
			while(it.hasNext()) {
				Customer customer = it.next();
				presenter.add(new CustomerPresenter(customer));
			}
			return presenter;
		}
		
	@Path("/{cid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@PathParam("cid") int cid) {
		Gson gson = new Gson();
		String s;
		try {
			Customer customer = customerManager.getCustomerById(cid);
			List<OrdersListNoCustomerPresenter> orders = createOrders(customer);
			CustomerWithOrderPresenter presenter = new CustomerWithOrderPresenter(customer,orders);
			s = gson.toJson(presenter);
		}
		catch(RuntimeException e) {
			System.out.println(e);
			return Response.status(404).build();
		}
		return Response.ok(s).build();
		
	}

		private List<OrdersListNoCustomerPresenter> createOrders(Customer customer) {
			List<OrdersListNoCustomerPresenter> orders = new ArrayList<OrdersListNoCustomerPresenter>();
			List<Order> originalOrders = customer.getOrderedList();
			Iterator<Order> it = originalOrders.iterator();
			while(it.hasNext()) {
				Order order = it.next();
				orders.add(new OrdersListNoCustomerPresenter(order));
			}
			return orders;
		}
}

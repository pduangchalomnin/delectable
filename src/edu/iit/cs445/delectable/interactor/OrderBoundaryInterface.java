package edu.iit.cs445.delectable.interactor;

import java.util.List;

import edu.iit.cs445.delectable.entity.Address;
import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Order;

public interface OrderBoundaryInterface {

	public List<Order> getOrders();
	public List<Order> getOrdersByDate(String delivery_date) throws RuntimeException;
	public int createOrder(String delivery_date
			,Address delivery_address
			,Customer personal_info
			,String note
			,List<Item> order_details) throws RuntimeException;
	public Order getOrderById(int id) throws RuntimeException;
	public void cancelOrder(int id) throws RuntimeException;
}

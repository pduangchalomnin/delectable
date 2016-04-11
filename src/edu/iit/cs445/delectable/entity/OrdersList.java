package edu.iit.cs445.delectable.entity;

import java.util.List;

public interface OrdersList {
	
	public List<Order> getOrders();
	public Order getOrderById(int id);
	public List<Order> getOrdersByDeliveryDate(String delivery_date);
	public int createOrder(String delivery_date, Address delivery_address, Customer personal_info, String note,
			List<Item> order_details,double surcharge);
	public int checkExistingCustomer(String email);
	//Test Purpose only
	public void resetOrders();	

}

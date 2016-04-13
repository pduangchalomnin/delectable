package edu.iit.cs445.delectable.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrdersListImp implements OrdersList {

	private static OrdersList instance = null;
	private List<Order> orders;
	
	private OrdersListImp() {
		orders = new ArrayList<Order>();
	}
	
	public static OrdersList getInstance() {
		if(instance == null)
			instance = new OrdersListImp();
		return instance;
	}
	
	public Order getOrderById(int id) {
		Iterator<Order> it = orders.iterator();
		while(it.hasNext()) {
			Order order = it.next();
			if(order.getId() == id) {
				return order;
			}
		}
		return NullOrder.getinstance();
	}

	public List<Order> getOrdersByDeliveryDate(String delivery_date) {
		List<Order> output = new ArrayList<Order>();
		Iterator<Order> it = orders.iterator();
		while(it.hasNext()) {
			Order tmpOrder = it.next();
			if(tmpOrder.getDeliveryDate().equals(delivery_date)) {
				output.add(tmpOrder);
			}
		}
		return output;
	}

	public int createOrder(String delivery_date, Address delivery_address, Customer personal_info, String note,
			List<Item> order_details,double surcharge) {
		Order order = new OrderImp(personal_info, delivery_date, note, order_details, delivery_address, surcharge);
		orders.add(order);
		return order.getId();
	}

	public List<Order> getOrders() {
		return orders;
	}

	public int checkExistingCustomer(String email) {
		Iterator<Order> it = orders.iterator();
		while(it.hasNext()) {
			Order tmpOrder = it.next();
			if(tmpOrder.getCustomerEmail().equals(email)) {
				return tmpOrder.getCustomerId();
			}
		}
		return -1;
	}
	
	public void applySurcharge() {
		Iterator<Order> it = orders.iterator();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		while(it.hasNext()) {
			Order tmpOrder = it.next();
			String delivery_date = tmpOrder.getDeliveryDate();
			if(Integer.parseInt(delivery_date) >= Integer.parseInt(dateFormat.format(date))
					&& tmpOrder.getStatus().equals(Status.OPEN.toString())) {
				tmpOrder.setSurcharge(calculateSurcharge(delivery_date));
			}
		}
		
	}
	
		private double calculateSurcharge(String delivery_date) {
			int year = Integer.parseInt(delivery_date.substring(0, 4));
			int month = Integer.parseInt(delivery_date.substring(4,6));
			int day = Integer.parseInt(delivery_date.substring(6));
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month-1, day);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				Menu menu = MenuImp.getInstance();
				return menu.getSurcharge();
			}
			return 0.00;
		}
	
	//Test Purpose only
	public void resetOrders() {
		orders = new ArrayList<Order>();
	}

}

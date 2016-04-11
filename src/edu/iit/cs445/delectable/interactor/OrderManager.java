package edu.iit.cs445.delectable.interactor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.iit.cs445.delectable.entity.Address;
import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;
import edu.iit.cs445.delectable.entity.Status;

public class OrderManager implements OrderBoundaryInterface {

	private static OrderBoundaryInterface instance;
	OrdersList orders;
	
	private OrderManager() {
		orders = OrdersListImp.getInstance();
	}
	
	public static OrderBoundaryInterface getInstance(){
		if(instance==null) {
			instance = new OrderManager();
		}
		return instance;
	}
	
	public List<Order> getOrders() {
		return orders.getOrders();
	}

	public List<Order> getOrdersByDate(String delivery_date) throws RuntimeException {
		validateDateTime(delivery_date);
		List<Order> output = orders.getOrdersByDeliveryDate(delivery_date);
		return output;
	}

		private void validateDateTime(String delivery_date) throws RuntimeException {
			int year = Integer.parseInt(delivery_date.substring(0, 4));
			int month = Integer.parseInt(delivery_date.substring(4,6));
			int day = Integer.parseInt(delivery_date.substring(6));
			Calendar calendar = Calendar.getInstance();
			
			if(delivery_date.length() != 8) {
				throw new RuntimeException();
			}
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month-1);
			int maxDay = calendar.getActualMaximum(Calendar.DATE);
			
			if(month < 1 || month > 12 || day < 1 || day > maxDay || year <= 0){
				throw new RuntimeException();
			}
		}
		

	public int createOrder(String delivery_date, Address delivery_address, Customer personal_info, String note,
			List<Item> order_details) throws RuntimeException {
		validateDateTime(delivery_date);
		validatePersonalInfo(personal_info);
		validateDeliveryAddress(delivery_address);
		validateItems(order_details);
		validateDeliveryDate(delivery_date);
		
		int orderId = orders.createOrder(delivery_date, delivery_address, personal_info, note, order_details, calculateSurcharge(delivery_date));
		
		
		return orderId;
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

		private void validateDeliveryDate(String delivery_date) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			if(Integer.parseInt(delivery_date)<Integer.parseInt(dateFormat.format(date))) {
				throw new RuntimeException();
			}
		}
	
		private void validateDeliveryAddress(Address delivery_address) throws RuntimeException {
			if(delivery_address.getHouseNumber().length() == 0
					|| delivery_address.getStreetName().length() == 0
					|| delivery_address.getCity().length() == 0
					|| delivery_address.getState().length() == 0
					|| delivery_address.getZipcode().length() == 0) {
				throw new RuntimeException();
			}
		}
		
		private void validatePersonalInfo(Customer personal_info) throws RuntimeException {
			String email_regex_patern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			if(personal_info.getFirstName().length() == 0
					|| personal_info.getLastName().length() == 0
					|| !personal_info.getEmail().matches(email_regex_patern)
					|| personal_info.getPhoneNumber().length() == 0) {
				throw new RuntimeException();
			}
		}
		
		private void validateItems(List<Item> order_details) throws RuntimeException{
			if(order_details.size() < 1) {
				throw new RuntimeException();
			}
			Iterator<Item> it = order_details.iterator();
			while(it.hasNext()) {
				Item item = it.next();
				Food food = item.getFood();
				if(item.getCount() < item.getItemMinimumOrder()
						|| food.getName().isEmpty()
						|| food.getPrice_per_person() < 0.00
						|| food.getMinimum_order() < 1) {
					throw new RuntimeException();
				}
			}
		}

	public Order getOrderById(int id) throws RuntimeException{
		Order order = orders.getOrderById(id);
		if(!order.isNil()) {
			return order;
		}
		throw new RuntimeException();
	}

	public void cancelOrder(int id) throws RuntimeException {
		Order order = orders.getOrderById(id);
		if(!order.isNil()) {
			if(checkIsDeliverToday(order)) {
				throw new RuntimeException();
			}
			order.changeStatus(Status.CANCELED);
			return;
		}
		throw new RuntimeException();
	}

		private boolean checkIsDeliverToday(Order order) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			if(order.getDeliveryDate().equals(dateFormat.format(date))) {
				return true;
			}
			return false;
		}

}

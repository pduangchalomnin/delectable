package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Address;
import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.CustomerImp;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;
import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;

public class OrdersListTest {

	static OrdersList orders = OrdersListImp.getInstance();
	static Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
	static Customer customer = new CustomerImp("FistName", "LastName", "312-333-4444", "testemail@email.com");
	static Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
	static Food food = new FoodImp("Food1",1.99,1,cat);
	static List<Item> itemList;
	static int orderId;
	private static final double DELTA = 1e-15;
	
	@BeforeClass
	public static void setup() {
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,1));
		orderId = orders.createOrder("20151228", add, customer, "This is first order", itemList,0.00);
	}
	
	@Test
	public void testCreateOrder() {
		Food food2 = new FoodImp("Soup",3.99,1,cat);
		List<Item> itemList2 = new ArrayList<Item>();
		itemList2.add(new Item(food2,2));

		int orderId = orders.createOrder("20151212", add, customer, "this is test note", itemList2,0.00);
		assertEquals("20151212", orders.getOrderById(orderId).getDeliveryDate());
	}
	
	@Test
	public void testGetOrderById() {
		assertEquals("This is first order", orders.getOrderById(orderId).getNote());
	}
	
	@Test
	public void testGetOrderByDeriveryDateHaveOrderOnDate() {
		assertEquals(1,orders.getOrdersByDeliveryDate("20151228").size());
	}

	@Test
	public void testGetOrderByDeriveryDateHaveNoOrderOnDate() {
		assertEquals(0,orders.getOrdersByDeliveryDate("20111111").size());
	}
	
	@Test
	public void testApplySurcharge() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date saturday = getNextSaturday();
		
		int orderId = orders.createOrder(dateFormat.format(saturday), add, customer, "This is first order", itemList,0.00);
		assertEquals(0.00,orders.getOrderById(orderId).getSurcharge(),DELTA);
		Menu menu = MenuImp.getInstance();
		menu.setSurcharge(5);
		orders.applySurcharge();
		assertEquals(5.00,orders.getOrderById(orderId).getSurcharge(),DELTA);
	}
	
		private Date getNextSaturday() {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
			} 
			return cal.getTime();
		}
}

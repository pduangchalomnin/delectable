package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;
import edu.iit.cs445.delectable.entity.Status;
import edu.iit.cs445.delectable.interactor.OrderBoundaryInterface;
import edu.iit.cs445.delectable.interactor.OrderManager;

public class OrderManagerTest {

	OrderBoundaryInterface orderManager = OrderManager.getInstance();
	static OrdersList orders = OrdersListImp.getInstance();
	static Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
	static Customer customer = new CustomerImp("Manager", "LastName", "312-333-4444", "testManagerl@email.com");
	static Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
	static Food food = new FoodImp("TestManagerFood",1.99,2,cat);
	static List<Item> itemList;
	static int orderId;
	
	@BeforeClass
	public static void setup() {
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,2));
		orderId = orders.createOrder("20161202", add, customer, "This is order manager test", itemList,0.0);
	}
	
	@Test
	public void testGetOrders() {
		OrdersList orderList = OrdersListImp.getInstance();
		assertEquals(orderList.getOrders().size(), orderManager.getOrders().size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOrdersByDateWithInvalidInputLength() {
		orderManager.getOrdersByDate("201101011");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOrdersByDateWithInvalidYear() {
		orderManager.getOrdersByDate("00000101");
	}

	@Test(expected = RuntimeException.class)
	public void testGetOrdersByDateWithInvalidMonth() {
		orderManager.getOrdersByDate("20112101");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOrdersByDateWithExceedMaxDay() {
		orderManager.getOrdersByDate("20160431");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOrdersByDateWithExceedMaxDayNonLeapYear() {
		orderManager.getOrdersByDate("20150229");
	}
	
	@Test
	public void testGetOrderByDateValidInput() {
		assertEquals(1, orderManager.getOrdersByDate("20161202").size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithInvalidDate() {
		orderManager.createOrder("201111111", add, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyHouseNumber() {
		Address address = new Address("", "S State St", "Chicago", "IL", "60616");
		orderManager.createOrder("20110102", address, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyStreetName() {
		Address address = new Address("11", "", "Chicago", "IL", "60616");
		orderManager.createOrder("20110102", address, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyCity() {
		Address address = new Address("11", "S State St", "", "IL", "60616");
		orderManager.createOrder("20110102", address, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyState() {
		Address address = new Address("11", "S State St", "Chicago", "", "60616");
		orderManager.createOrder("20110102", address, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyPostCode() {
		Address address = new Address("11", "S State St", "Chicago", "IL", "");
		orderManager.createOrder("20110102", address, customer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyCustomerName() {
		Customer invalidCustomer = new CustomerImp("", "LastName", "312-333-4444", "testManagerl@email.com");
		orderManager.createOrder("20110102", add, invalidCustomer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyCustomerLastName() {
		Customer invalidCustomer = new CustomerImp("Name", "", "312-333-4444", "testManagerl@email.com");
		orderManager.createOrder("20110102", add, invalidCustomer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithEmptyCustomerPhoneNumber() {
		Customer invalidCustomer = new CustomerImp("Name", "LastName", "", "testManagerl@email.com");
		orderManager.createOrder("20110102", add, invalidCustomer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithInvalidCustomerEmailFormat() {
		Customer invalidCustomer = new CustomerImp("Name", "LastName", "312-333-4444", "testManagerl@email");
		orderManager.createOrder("20110102", add, invalidCustomer, "note", itemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithItemAmountThatLessThanMinimumOrder() {
		List<Item> invalidItemList = new ArrayList<Item>();
		invalidItemList.add(new Item(food,1));
		orderManager.createOrder("20110102", add, customer, "note", invalidItemList);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOrderWithItemAmountThatLessThanOne() {
		List<Item> invalidItemList = new ArrayList<Item>();
		invalidItemList.add(new Item(food,0));
		orderManager.createOrder("20110102", add, customer, "note", invalidItemList);
	}
	
	@Test
	public void testCreatOrderWithValidData() {
		int orderId2 = orderManager.createOrder("20170102", add, customer, "note", itemList);
		
		assertEquals("20170102", orderManager.getOrderById(orderId2).getDeliveryDate());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetItemByIdWithNonExistingOrder() {
		orderManager.getOrderById(999999);
	}
	
	@Test
	public void testGetItemByIdWithExistingOrder() {
		assertEquals("20161202", orderManager.getOrderById(orderId).getDeliveryDate());
	}
	 
	@Test(expected = RuntimeException.class)
	public void testCancelOrderWithNonExistingOrder() {
		orderManager.cancelOrder(999999);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCancelOrderOnDeliveryDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		int orderId2 = orderManager.createOrder(dateFormat.format(date), add, customer, "note", itemList);
		
		orderManager.cancelOrder(orderId2);
	}
	
	@Test
	public void testCancelOrderWithExistingOrder() {
		orderManager.cancelOrder(orderId);
		assertEquals(Status.CANCELED.toString(), orderManager.getOrderById(orderId).getStatus());
	}
}

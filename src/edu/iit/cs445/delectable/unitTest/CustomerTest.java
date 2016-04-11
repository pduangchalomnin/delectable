package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Address;
import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.CustomerImp;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;
import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;

public class CustomerTest {

	Customer customer;
	int customerId;
	Customer customer2 = new CustomerImp("FistName", "LastName", "312-333-4444", "testMember@ff.com");
	
	@Before
	public void setup() {
		customer = new CustomerImp("Name", "LastName", "312-111-1111", "EEEE@ee.CoM");
		customerId = customer.getId();
	}
	
	@Test
	public void testCustomerConstructorWithExistingCustomer() {
		int orderId1,orderId2;
		OrdersList orders = OrdersListImp.getInstance();
		Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
		
		Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
		Food food = new FoodImp("Food1",1.99,1,cat);
		List<Item> itemList;
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,1));
		orderId1 = orders.createOrder("20151212", add, customer2, "This is first order", itemList,0.00);
		Customer customer3 = new CustomerImp("FistName2", "LastName", "312-333-4444", "testMember@ff.com");
		orderId2 = orders.createOrder("20151212", add, customer3, "This is second order", itemList,0.00);
		Order order1 = orders.getOrderById(orderId1);
		Order order2 = orders.getOrderById(orderId2);
		
		assertEquals(order1.getCustomerId(), order2.getCustomerId());
	}
	
	@Test
	public void testCustomerConstructorShouldLowercaseTheNameLastNameAndEmail() {
		assertEquals("name", customer.getFirstName());
		assertEquals("lastname", customer.getLastName());
		assertEquals("eeee@ee.com", customer.getEmail());
	}
	
	@Test
	public void testIsMatchNoting() {
		assertFalse(customer.isMatch("This is not match"));
	}
	
	@Test
	public void testIsMatchLastname() {
		assertTrue(customer.isMatch("last"));
	}
	
	@Test
	public void testIsMatchEmail() {
		assertTrue(customer.isMatch("eeee@ee.com"));
	}
	
	@Test
	public void testIsMatchPhoneNumber() {
		assertTrue(customer.isMatch("312-111-1111"));
	}

	@Test
	public void testGetOrderedList() {
		assertEquals(2,customer2.getOrderedList().size());
	}
}

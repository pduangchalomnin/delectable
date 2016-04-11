package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
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
import edu.iit.cs445.delectable.interactor.CustomerBoundaryInterface;
import edu.iit.cs445.delectable.interactor.CustomerManager;

public class CustomerManagerTest {

	static Customer customer1 = new CustomerImp("John", "Doe", "312-333-4444", "johndoe@email.com");
	static Address add1 = new Address("111", "S State St", "Chicago", "IL", "60616");
	
	static Customer customer1_updated = new CustomerImp("John", "Doe", "312-000-4444", "johndoe@email.com");
	static Address add1_updated = new Address("222", "S State St", "Chicago", "IL", "60616");
	
	static Customer customer2 = new CustomerImp("Bob", "Martin", "312-333-4444", "bobby@email.com");
	static Address add2 = new Address("111", "S State St", "Chicago", "IL", "60616");
	
	static int orderId1,orderId2,orderId3;
	static CustomerBoundaryInterface customerManager;
	
	@BeforeClass
	public static void setup() {
		OrdersList orders = OrdersListImp.getInstance();
		Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
		Food food = new FoodImp("TestManagerFood",1.99,2,cat);
		List<Item> itemList;
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,2));
		orderId1 = orders.createOrder("20161212", add1, customer1, "This is 1st order of John Doe", itemList,0.00);
		orderId2 = orders.createOrder("20161212", add1_updated, customer1_updated, "This is 2nd order of John Doe", itemList,0.00);
		orderId3 = orders.createOrder("20161212", add2, customer2, "This is 1st order of Bob Martin", itemList,0.00);
		customerManager = CustomerManager.getInstance();
	}
	
	@Test
	public void testGetCustomerList() {
		List<Customer> customerList = customerManager.getCustomerList();
		Customer[] customerArray = customerList.toArray(new Customer[customerList.size()]);
		
		assertEquals(2, customerList.size());
		assertSame(customer1_updated,customerArray[0]);
		assertSame(customer2,customerArray[1]);
	}

	@Test
	public void testGetCustomerByKeywordWithNonExistingKeyword() {
		assertEquals(0, customerManager.getCustomerByKeyword("zxczxc").size());
	}
	
	@Test
	public void testGetCustomerByKeywordWithPartialWord() {
		assertEquals(2,customerManager.getCustomerByKeyword("o").size());
	}
	
	@Test
	public void testGetCustomerByKeywordWithEntireWord(){
		assertEquals(1, customerManager.getCustomerByKeyword("Bob").size());
	}
	
	@AfterClass
	public static void cleanUp() {
		OrdersListImp.getInstance().resetOrders();
	}
}

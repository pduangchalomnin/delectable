package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

public class OrdersListTest {

	static OrdersList orders = OrdersListImp.getInstance();
	static Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
	static Customer customer = new CustomerImp("FistName", "LastName", "312-333-4444", "testemail@email.com");
	static Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
	static Food food = new FoodImp("Food1",1.99,1,cat);
	static List<Item> itemList;
	static int orderId;
	
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
}

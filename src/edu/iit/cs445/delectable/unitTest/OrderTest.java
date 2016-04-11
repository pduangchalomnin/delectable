package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import edu.iit.cs445.delectable.entity.OrderImp;
import edu.iit.cs445.delectable.entity.Status;

public class OrderTest {

	
	Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
	Customer customer = new CustomerImp("FirstName", "LastName", "312-333-4444", "testemail@email.com");
	Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
	Food food = new FoodImp("Food1",1.99,1,cat);
	List<Item> itemList;
	Order order;
	private static final double DELTA = 1e-15;

	@Before
	public void createOrder() {	
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,1));

		order = new OrderImp(customer, "20161212", "this is test note", itemList, add, 0.00);
	}

	@Test
	public void testGetSurcharge() {
		assertEquals(0.00, order.getSurcharge(),DELTA);
	}

	@Test
	public void testGetEmail() {
		assertEquals("testemail@email.com",order.getCustomerEmail());
	}
	
	@Test
	public void testGetId() {
		assertEquals(customer.getId(),order.getCustomerId());
	}
	
	@Test
	public void testGetStatusAfterCreateOrder() {
		assertEquals("open",order.getStatus());
	}
	
	@Test
	public void testUpdateStatus(){
		order.changeStatus(Status.CANCELED);
		assertEquals("canceled",order.getStatus());
	}
	
	@Test
	public void testGetTotalAmountWithoutSurcharge(){
		assertEquals(1.99, order.getTotalAmount(),DELTA);
	}
	
	
	@Test
	public void testGetDeriveryDate(){
		assertEquals("20161212",order.getDeliveryDate());
	}
	
	@Test
	public void testGetOrderDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		assertEquals(dateFormat.format(date), order.getOrderDate());
	}
	
	@Test
	public void testIsMatchDeliveryDatePartial(){
		assertTrue(order.isMatch("2016121"));
	}
	
	@Test
	public void testIsMatchDeliveryDateEntire(){
		assertTrue(order.isMatch("20161212"));
	}
	
	@Test
	public void testIsMatchOrderDatePartial(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		assertTrue(order.isMatch(dateFormat.format(date).substring(1,3)));
	}
	
	@Test
	public void testIsMatchOrderDateEntire(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		assertTrue(order.isMatch(dateFormat.format(date)));
	}
	
	@Test
	public void testIsMatchNotePartial(){
		assertTrue(order.isMatch("test note"));
	}
	
	@Test
	public void testIsMatchNoteEntire(){
		assertTrue(order.isMatch("this is test note"));
	}
	
	@Test
	public void testIsMatchNoting(){
		assertFalse(order.isMatch("this is not a test note"));
	}
	
	@Test
	public void testIsMatchStatus(){
		assertTrue(order.isMatch(Status.OPEN.toString()));
	}
	
	@Test
	public void testIsMatchDeliveryAddress(){
		assertTrue(order.isMatch("111"));
	}
	
	@Test
	public void testIsCustomerMatchNotMatch() {
		assertFalse(order.isCustomerMatch("STRING THAT DOESN'T MATCH"));
	}
	
	@Test
	public void testIsCustomerMatchWithPartialFirstName() {
		assertTrue(order.isCustomerMatch("first"));
	}
	
	@Test
	public void testIsCustomerMatchWithFullFirstName() {
		assertTrue(order.isCustomerMatch("firstname"));
	}
	
	@Test
	public void testIsCustomerMatchWithPartialLastName() {
		assertTrue(order.isCustomerMatch("last"));
	}
	
	@Test
	public void testIsCustomerMatchWithFullLastName() {
		assertTrue(order.isCustomerMatch("lastname"));
	}
	
	@Test
	public void testIsCustomerMatchWithPhoneNumber() {
		assertTrue(order.isCustomerMatch("312-333-4444"));
	}
	
	@Test
	public void testIsCustomerMatchWithEmail() {
		assertTrue(order.isCustomerMatch("testemail@email.com"));
	}
}

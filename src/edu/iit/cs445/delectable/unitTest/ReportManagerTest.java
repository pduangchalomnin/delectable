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
import edu.iit.cs445.delectable.entity.ReportCode;
import edu.iit.cs445.delectable.entity.RevenueReport;
import edu.iit.cs445.delectable.interactor.AdminManager;
import edu.iit.cs445.delectable.interactor.OrderBoundaryInterface;
import edu.iit.cs445.delectable.interactor.OrderManager;
import edu.iit.cs445.delectable.interactor.ReportBoundaryInterface;
import edu.iit.cs445.delectable.interactor.ReportManager;

public class ReportManagerTest {
	ReportBoundaryInterface reportManager = ReportManager.getInstance();
	static OrderBoundaryInterface orderManager = OrderManager.getInstance();
	static Catagory cat[] = new Catagory[]{ new Catagory("Cat1") };
	static Address add = new Address("111", "S State St", "Chicago", "IL", "60616");
	static Food food = new FoodImp("TestManagerFood",1.99,2,cat);
	static List<Item> itemList;
	static int orderId1,orderId2,orderId3;
	static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	static Date date = new Date();
	static Date tomorrow,saturday;
	private static final double DELTA = 1e-15;
	
	@BeforeClass
	public static void setup() {
		AdminManager.getInstance().changeSurcharge(2);
		itemList = new ArrayList<Item>();
		itemList.add(new Item(food,2));
		Customer customer1 = new CustomerImp("John", "Doe", "312-333-4444", "johndoe@email.com");
		orderId1 = orderManager.createOrder(dateFormat.format(date), add, customer1, "This is 1st order", itemList);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 1); 
		tomorrow = cal.getTime();
		Customer customer2 = new CustomerImp("Jane", "Roe", "312-333-4444", "jane@email.com");
		orderId2 = orderManager.createOrder(dateFormat.format(tomorrow), add, customer2, "This is 2nd order", itemList);
		
		date = new Date();
		cal.setTime(date);
		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		} 
		saturday = cal.getTime();
		Customer customer3 = new CustomerImp("Bob", "Smith", "312-333-4444", "bobby@email.com");
		orderId3 = orderManager.createOrder(dateFormat.format(saturday), add, customer3, "This is 3nd order", itemList);
	}

	@Test
	public void testGetReportType() {
		ReportCode[] reportType = reportManager.getReportType();
		assertEquals(801,reportType[0].getCode());
		assertEquals(802,reportType[1].getCode());
		assertEquals(803,reportType[2].getCode());
		assertEquals(804,reportType[3].getCode());
		
		assertEquals("Orders to deliver today",reportType[0].getName());
		assertEquals("Orders to deliver tomorrow",reportType[1].getName());
		assertEquals("Revenue report",reportType[2].getName());
		assertEquals("Orders delivery report",reportType[3].getName());
	}
	
	@Test
	public void testGetDeliveryToday() {
		assertEquals(1, reportManager.getDeliveryToday().size());
	}
	
	@Test
	public void testGetDeliveryTomorrow() {
		assertEquals(1, reportManager.getDeliveryTomorrow().size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDeliveryListInvalidDate() {
		reportManager.getDeliveryList("201212122", "");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDeliveryListInvalidDay() {
		reportManager.getDeliveryList("20121232", "");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDeliveryListInvalidMonth() {
		reportManager.getDeliveryList("20121332", "");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDeliveryListInvalidYear() {
		reportManager.getDeliveryList("00001332", "");
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDeliveryListInvalidDateRange() {
		reportManager.getDeliveryList("20191010", "20171010");
	}
	
	@Test
	public void testGetDeliveryListTomorrowAndAfter() {		
		assertEquals(2, reportManager.getDeliveryList(dateFormat.format(tomorrow), "").size());
	}

	@Test
	public void testGetDeliveryListBeforeToday() {
		assertEquals(1, reportManager.getDeliveryList("", dateFormat.format(date)).size());
	}
	
	@Test
	public void testGetDeliveryListBetweenTodayAndTomorrow() {		
		assertEquals(2, reportManager.getDeliveryList(dateFormat.format(date), dateFormat.format(tomorrow)).size());
	}
	
	@Test
	public void testgetRevenueReportOfTodayAndTomorrow() {
		orderManager.cancelOrder(orderId2);
		double surcharge=0;
		RevenueReport revenue = reportManager.getRevenueReport(dateFormat.format(date), dateFormat.format(tomorrow));
		surcharge = applySurcharge(dateFormat.format(date)) + applySurcharge(dateFormat.format(tomorrow));
		
		assertEquals(3.98*2, revenue.getFood_revenue(),DELTA);
		assertEquals(surcharge, revenue.getSurcharge_revenue(),DELTA);
		assertEquals(2, revenue.getOrders_placed());
		assertEquals(1, revenue.getOrders_open());
		assertEquals(1, revenue.getOrders_cancelled());
	}

		private double applySurcharge(String stringDate) {
			int year = Integer.parseInt(stringDate.substring(0, 4));
			int month = Integer.parseInt(stringDate.substring(4,6));
			int day = Integer.parseInt(stringDate.substring(6));
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month-1, day);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				return AdminManager.getInstance().getSurcharge();
			}
			return 0;
		}
		
	@Test
	public void testGetRevenueReportOfNextSaturday() {
		RevenueReport revenue = reportManager.getRevenueReport(dateFormat.format(saturday), dateFormat.format(saturday));	
		
		assertEquals(3.98, revenue.getFood_revenue(),DELTA);
		assertEquals(2, revenue.getSurcharge_revenue(),DELTA);
		assertEquals(1, revenue.getOrders_placed());
		assertEquals(1, revenue.getOrders_open());
		assertEquals(0, revenue.getOrders_cancelled());
	}
	
	@Test
	public void testGetRevenueReportOfTodayAndAfter() {
		double surcharge=0;
		RevenueReport revenue = reportManager.getRevenueReport(dateFormat.format(date), "");
		surcharge = applySurcharge(dateFormat.format(date)) + applySurcharge(dateFormat.format(tomorrow))+2;
		
		assertEquals(3.98*3, revenue.getFood_revenue(),DELTA);
		assertEquals(surcharge, revenue.getSurcharge_revenue(),DELTA);
		assertEquals(3, revenue.getOrders_placed());
		assertEquals(2, revenue.getOrders_open());
		assertEquals(1, revenue.getOrders_cancelled());
	}
	
	@Test
	public void testGetRevenueReportOfBeforeToday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1); 
		Date yesterday = cal.getTime();
		
		RevenueReport revenue = reportManager.getRevenueReport("", dateFormat.format(yesterday));
		
		assertEquals(0, revenue.getFood_revenue(),DELTA);
		assertEquals(0, revenue.getSurcharge_revenue(),DELTA);
		assertEquals(0, revenue.getOrders_placed());
		assertEquals(0, revenue.getOrders_open());
		assertEquals(0, revenue.getOrders_cancelled());
	}
}

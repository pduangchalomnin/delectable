package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;

public class FoodTest {

	Food testFood;
	Catagory[] catagory;
	private static final double DELTA = 1e-15;


	@Before
	public void createFood(){
		catagory = new Catagory[]{new Catagory("cat1"),new Catagory("cat2")};
		testFood = new FoodImp("testName", 2.99, 1, catagory);
	}

	@Test
	public void testGetName(){
		assertEquals("testName", testFood.getName());
	}

	@Test
	public void testGetPricePerPerson(){
		assertEquals(2.99, testFood.getPrice_per_person(),DELTA);
	}

	@Test
	public void testGetMinimumOrder(){
		assertEquals(1, testFood.getMinimum_order());
	}

	@Test
	public void testGetCatagories(){
		assertArrayEquals(catagory, testFood.getCatagories());
	}

	@Test
	public void testGetCreateDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getCreate_date());
	}

	@Test
	public void testGetLastModifiedDateWithOutEdit(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}

	@Test
	public void testUpdateDetail(){
		testFood.updateDeatail("changedName", 3.99, 2, new Catagory[]{new Catagory("newCat")});
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		assertEquals("changedName", testFood.getName());
		assertEquals(3.99, testFood.getPrice_per_person(),DELTA);
		assertEquals(2, testFood.getMinimum_order());
		assertEquals("newCat", testFood.getCatagories()[0].toString());
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}

	@Test
	public void testUpdateName(){
		testFood.updateName("newName");
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		assertEquals("newName", testFood.getName());
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}
	
	@Test
	public void testUpdatePrice(){
		testFood.updatePrice_per_person(1.00);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertEquals(1.00, testFood.getPrice_per_person(),DELTA);
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}
	
	@Test
	public void testUpdateMinimumOrder(){
		testFood.updateMinimumOrder(5);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertEquals(5, testFood.getMinimum_order());
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}
	
	@Test
	public void testUpdateCatagories(){
		testFood.updateCatagories(new Catagory[]{new Catagory("Cat3")});
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertEquals("Cat3", testFood.getCatagories()[0].toString());
		assertEquals(Integer.parseInt(dateFormat.format(date)), testFood.getLast_modified_date());
	}

	@Test
	public void testIsMatchNamePartial(){
		assertTrue(testFood.isMatch("e"));
	}
	
	@Test
	public void testIsMatchNameWholeWord(){
		assertTrue(testFood.isMatch("testName"));
	}
	
	@Test
	public void testIsMatchPricePerPerson(){
		assertTrue(testFood.isMatch("2.99"));
	}
	
	@Test
	public void testIsMatchMinimumOrder(){
		assertTrue(testFood.isMatch("1"));
	}
	
	@Test
	public void testIsMatchCreateDatePartial(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertTrue(testFood.isMatch(dateFormat.format(date).substring(0, 6)));
	}
	
	@Test
	public void testIsMatchCreateDateWholeWord(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertTrue(testFood.isMatch(dateFormat.format(date)));
	}
	
	@Test
	public void testIsMatchLastModifiedDatePartial(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertTrue(testFood.isMatch(dateFormat.format(date).substring(0, 6)));
	}
	
	@Test
	public void testIsMatchLastModifiedDateWholeWord(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		assertTrue(testFood.isMatch(dateFormat.format(date)));
	}
	
	@Test
	public void testIsMatchCatagoriesPartial(){
		assertTrue(testFood.isMatch("cat"));
	}
	
	@Test
	public void testIsMatchCatagoriesWholeWord(){
		assertTrue(testFood.isMatch("cat1"));
	}
	
	@Test
	public void testIsMatchNothing(){
		assertFalse(testFood.isMatch("NOTMATCHWITHANY"));
	}
}

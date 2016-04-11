package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;
import edu.iit.cs445.delectable.interactor.AdminBoundaryInterface;
import edu.iit.cs445.delectable.interactor.AdminManager;

public class AdminManagerTest {
	
	AdminBoundaryInterface admin = AdminManager.getInstance();
	Menu menu = MenuImp.getInstance();
	boolean hasToDelete;
	private static final double DELTA = 1e-15;
	int foodId;
	
	@Before
	public void setup() {
		hasToDelete = false;
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddFoodNameIsEmpty() {
		admin.addFoodToMenu("",1.99,2,new Catagory[]{});
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddFoodPriceIsLessThanZero() {
		admin.addFoodToMenu("Name",-0.1,2,new Catagory[]{});
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddFoodMinimumIsLessThanOne() {
		admin.addFoodToMenu("Name",1.99,0,new Catagory[]{});
	}
	
	@Test
	public void testAddFoodValidData(){
		hasToDelete = true;
		int returnValue = admin.addFoodToMenu("Name",1.99,1,new Catagory[]{});
		foodId = returnValue;
		Menu menu = MenuImp.getInstance();
		Food food = menu.searchItemById(returnValue);
		assertEquals(food.getId(), returnValue);
	}
	
	@Test(expected = RuntimeException.class)
	public void testEditFoodNotFoundID(){
		admin.editFoodInMenu(99999, 9.99);
	}
	
	@Test(expected = RuntimeException.class)
	public void testEditFoodInvalidPrice(){
		hasToDelete = true;
		foodId = admin.addFoodToMenu("Name",1.99,1,new Catagory[]{});
		admin.editFoodInMenu(foodId, -0.01);
	}
	
	@Test
	public void testEditFoodValidData(){
		hasToDelete = true;
		int id = foodId = admin.addFoodToMenu("Name",1.99,1,new Catagory[]{});
		admin.editFoodInMenu(id, 1.00);
		Menu menu = MenuImp.getInstance();
		Food food = menu.searchItemById(id);
		assertEquals(1.00, food.getPrice_per_person(),DELTA);
	}
	
	@Test
	public void testGetSurcharge() {
		admin.changeSurcharge(5);
		assertEquals(5, admin.getSurcharge(),DELTA);
	}

	@Test(expected = RuntimeException.class)
	public void testChangeSurchargeInvalidPrice(){
		admin.changeSurcharge(-0.1);
	}
	
	@Test
	public void testChangeSurchargeValidData(){
		admin.changeSurcharge(5.99);
		assertEquals(5.99, menu.getSurcharge(),DELTA);
	}
	
	@After
	public void clearMenu(){
		if(hasToDelete){
			menu.deleteItem(foodId);
		}
	}
}

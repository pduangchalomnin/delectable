package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;
import edu.iit.cs445.delectable.interactor.MenuBoundaryInterface;
import edu.iit.cs445.delectable.interactor.MenuManager;

public class MenuManagerTest {

	MenuBoundaryInterface menuManager = MenuManager.getInstance();
	Menu menu = MenuImp.getInstance();
	Food food1 = new FoodImp("food1", 1.99, 1, new Catagory[]{new Catagory("glueten free")});
	Food food2 = new FoodImp("food2", 3.99, 2, new Catagory[]{new Catagory("Vegan"),new Catagory("Organic")});
	int itemId1,itemId2;
	
	@Before
	public void setup(){
		itemId1 = menu.addItem(food1);
		itemId2 = menu.addItem(food2);
	}
	
	@Test
	public void testListMenuItem() {
		List<Food> list = menuManager.getMenu();
		assertEquals(2,list.size());
	}

	@Test
	public void testGetgMenuItemWithExistingData() {
		assertEquals(food1,menuManager.getMenuItem(itemId1));
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetMenuItemDoesNotExist(){
		menuManager.getMenuItem(0);
	}
	
	@After
	public void reset(){
		menu.deleteItem(itemId1);
		menu.deleteItem(itemId2);
	}
}

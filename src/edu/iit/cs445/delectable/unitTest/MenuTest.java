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
import edu.iit.cs445.delectable.entity.NullFood;

public class MenuTest {

	Menu menu = MenuImp.getInstance();;
	Food food1 = new FoodImp("food1", 1.99, 1, new Catagory[]{new Catagory("glueten free")});;
	Food food2 = new FoodImp("food2", 3.99, 2, new Catagory[]{new Catagory("Vegan"),new Catagory("Organic")});;
	int[] deletedInAfter;
	
	@Before
	public void createMenu() {
		deletedInAfter = new int[2];
	}
	
	@Test
	public void testAddItem() {
		deletedInAfter[0] = menu.addItem(food1);
		assertFalse(menu.listMenu().isEmpty());
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddAlreadyExistItem() {
		deletedInAfter[0] = menu.addItem(food1);
		menu.addItem(food1);
	}
	
	@Test
	public void testDeleteItemHasItem(){
		int itemToBeDelete = menu.addItem(food1);
		deletedInAfter[0] = menu.addItem(food2);
		
		assertTrue(menu.deleteItem(itemToBeDelete));
		assertEquals(1,menu.listMenu().size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testDeleteItemNoItem(){
		menu.deleteItem(0);
	}
	
	@Test
	public void testsearchItemNotFound(){
		assertEquals(0, menu.searchItem("aaaa").size());
	}

	@Test
	public void testsearchItemFound(){
		deletedInAfter[0] = menu.addItem(food1);
		deletedInAfter[1] = menu.addItem(food2);
		List<Food> result = menu.searchItem("food");
		assertEquals(2,result.size());
	}
	
	@Test
	public void testSearchItemByIdNotFound(){
		Food result = menu.searchItemById(99);
		assertSame(NullFood.getinstance(), result);
	}
	
	@Test
	public void testSearchItemByIdFound(){
		int item1 = deletedInAfter[0] = menu.addItem(food1);
		assertEquals("food1",menu.searchItemById(item1).getName());
	}
	
	@After
	public void deleteItemAfterTest(){
		for(int i=0;i<2;i++)
		{
			if(deletedInAfter[i]!=0)
			{
				menu.deleteItem(deletedInAfter[i]);
			}
		}
	}
}

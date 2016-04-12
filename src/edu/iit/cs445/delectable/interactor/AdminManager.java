package edu.iit.cs445.delectable.interactor;

import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.FoodImp;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;
import edu.iit.cs445.delectable.entity.Status;

public class AdminManager implements AdminBoundaryInterface {
	
	private static AdminBoundaryInterface instance = null;
	private Menu menu = null;
	
	private AdminManager(){
		menu = MenuImp.getInstance();
	}
	
	public static AdminBoundaryInterface getInstance(){
		if(instance == null)
		{
			instance = new AdminManager();
		}
		return instance;
	}

	public int addFoodToMenu(String name,Double price_per_person,int minimum_order,Catagory[] catagories) throws RuntimeException {
		if(name.isEmpty()
			|| price_per_person < 0.00
			|| minimum_order < 1) {
			throw new RuntimeException();
		}
		Food food = new FoodImp(name, price_per_person, minimum_order, catagories);
		return menu.addItem(food);
	}

	public void editFoodInMenu(int id,double price_per_person) throws RuntimeException {
		Food foodToBeEdited = menu.searchItemById(id);
		if(foodToBeEdited.isNil() || price_per_person < 0.00)
		{
			throw new RuntimeException();
		}
		else
		{
			foodToBeEdited.updatePrice_per_person(price_per_person);
		}
	}

	public double getSurcharge() throws RuntimeException {
		return menu.getSurcharge();
	}

	public void changeSurcharge(double surcharge) throws RuntimeException {
		if(surcharge < 0.00)
			throw new RuntimeException();
		menu.setSurcharge(surcharge);
	}

	public void deliveredOrder(int id) throws RuntimeException {
		OrdersList orders = OrdersListImp.getInstance();
		Order order = orders.getOrderById(id);
		if(order.isNil()){
			throw new RuntimeException();
		}
		order.changeStatus(Status.DELIVERIED);
	}

}

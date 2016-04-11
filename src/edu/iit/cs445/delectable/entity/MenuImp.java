package edu.iit.cs445.delectable.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuImp implements Menu {

	private static Menu instance = null;
	private List<Food> menuList;
	private double surcharge = 0.00;
	
	private MenuImp(){
		menuList = new ArrayList<Food>();
	}
	
	public static Menu getInstance(){
		if(instance == null){
			instance = new MenuImp();
		}
		return instance;
	}
	
	public List<Food> listMenu() {
		return menuList;
	}

	public int addItem(Food food) throws RuntimeException {
		Iterator<Food> it = menuList.iterator();
		while(it.hasNext()){
			Food tmp = it.next();
			if(tmp.getId() == food.getId()){
				throw new RuntimeException();
			}
		}
		menuList.add(food);
		return food.getId();
	}

	public boolean deleteItem(int id) throws RuntimeException {
		Iterator<Food> it = menuList.iterator();
		while(it.hasNext()){
			Food tmpFood = it.next();
			if(tmpFood.getId() == id)
			{
				menuList.remove(tmpFood);
				return true;
			}
		}
		throw new RuntimeException();
	}

	public List<Food> searchItem(String keyword) {
		List<Food> foodList = new ArrayList<Food>();
		Iterator<Food> it = menuList.iterator();
		while(it.hasNext()){
			Food tmpFood = it.next();
			if(tmpFood.isMatch(keyword))
			{
				foodList.add(tmpFood);
			}
		}
		return foodList;
	}

	public Food searchItemById(int id) {
		Iterator<Food> it= menuList.iterator();
		while(it.hasNext()){
			Food tmp = it.next();
			if(tmp.getId()==id)
				return tmp;
		}
		return NullFood.getinstance();
	}

	public double getSurcharge() {
		return surcharge;
	}

	@Override
	public void setSurcharge(double amount) {
		this.surcharge = amount;
	}

}

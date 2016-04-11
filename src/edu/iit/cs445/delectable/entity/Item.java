package edu.iit.cs445.delectable.entity;

public class Item {
	private Food food;
	private int count;
	
	public Item(Food food,int count){
		this.food = food;
		this.count = count;
	}
	public Food getFood(){
		return food;
	}
	public int getCount(){
		return count;
	}
	public double getAmount(){
		return food.getPrice_per_person()*count;
	}
	
	public int getItemMinimumOrder(){
		return food.getMinimum_order();
	}
}

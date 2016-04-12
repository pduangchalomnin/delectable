package edu.iit.cs445.delectable.entity;

import java.math.BigDecimal;

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
	public BigDecimal getAmount(){
		BigDecimal price_per_person = new BigDecimal(food.getPrice_per_person());
		BigDecimal countBigDecimal = new BigDecimal(count);
		price_per_person = price_per_person.setScale(2, BigDecimal.ROUND_HALF_UP);   
		return price_per_person.multiply(countBigDecimal);
	}
	
	public int getItemMinimumOrder(){
		return food.getMinimum_order();
	}
}

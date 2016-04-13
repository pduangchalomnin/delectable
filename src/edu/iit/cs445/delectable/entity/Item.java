package edu.iit.cs445.delectable.entity;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class Item {
	private Food food;
	private int count;
	@Expose
	private BigDecimal amount;
	
	public Item(Food food,int count){
		this.food = food;
		this.count = count;
		BigDecimal price_per_person = new BigDecimal(food.getPrice_per_person());
		BigDecimal countBigDecimal = new BigDecimal(count);
		price_per_person = price_per_person.setScale(2, BigDecimal.ROUND_HALF_UP);   
		this.amount = price_per_person.multiply(countBigDecimal);
	}
	
	public Food getFood(){
		return food;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getFoodId() {
		return food.getId();
	}
	
	public String getFoodName() {
		return food.getName();
	}
	public BigDecimal getAmount(){
		return amount;
	}
	
	public int getItemMinimumOrder(){
		return food.getMinimum_order();
	}
}

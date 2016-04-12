package edu.iit.cs445.delectable.rest.presenter;

import edu.iit.cs445.delectable.entity.Catagory;

public class FoodPresenter {
	private int id;
	private String name;
	private double price_per_person;
	private int minimum_order;
	private Catagory catagories[];
	
	public FoodPresenter(int id,String name,double price_per_person,int minimum_order,Catagory[] catagory) {
		this.id = id;
		this.name = name;
		this.price_per_person = price_per_person;
		this.minimum_order = minimum_order;
		this.catagories = catagory;
	}
}

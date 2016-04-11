package edu.iit.cs445.delectable.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodImp implements Food {

	static private int idCounter = 0;
	private int id;
	private String name;
	private double price_per_person;
	private int minimum_order;
	private Catagory catagories[];
	private int create_date;
	private int last_modified_date;
	
	public FoodImp(String name,Double price_per_person,int minimum_order,Catagory catagories[]){
		this.id = idCounter++;
		this.name = name;
		this.price_per_person = price_per_person;
		this.minimum_order = minimum_order;
		this.catagories = catagories;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.create_date = this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}
	
	public void updateDeatail(String name,Double price_per_person,int minimum_order,Catagory catagories[]) {
		this.name = name;
		this.price_per_person = price_per_person;
		this.minimum_order = minimum_order;
		this.catagories = catagories;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}

	public void updateName(String name) {
		this.name = name;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}

	public void updatePrice_per_person(double pricePerPerson) {
		this.price_per_person = pricePerPerson;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}

	public void updateMinimumOrder(int minimum) {
		this.minimum_order = minimum;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}

	public void updateCatagories(Catagory[] catagories) {
		this.catagories = catagories;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.last_modified_date = Integer.valueOf(dateFormat.format(date));
	}

	public boolean isMatch(String keyword) {
		if(this.name.matches("(.*)"+keyword+"(.*)") 
				|| Double.toString(price_per_person).equals(keyword)
				|| Integer.toString(minimum_order).equals(keyword)
				|| Integer.toString(create_date).matches("(.*)"+keyword+"(.*)")
				|| Integer.toString(last_modified_date).matches("(.*)"+keyword+"(.*)"))
			return true;
		for(int i=0;i<catagories.length;i++)
		{
			if(catagories[i].toString().matches("(.*)"+keyword+"(.*)"))
				return true;
		}
		return false;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice_per_person() {
		return price_per_person;
	}

	public int getMinimum_order() {
		return minimum_order;
	}

	public Catagory[] getCatagories() {
		return catagories;
	}

	public int getCreate_date() {
		return create_date;
	}

	public int getLast_modified_date() {
		return last_modified_date;
	}

	public boolean isNil() {
		return false;
	}
}

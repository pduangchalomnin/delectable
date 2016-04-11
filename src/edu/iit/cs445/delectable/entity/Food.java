package edu.iit.cs445.delectable.entity;

public interface Food {

	public void updateDeatail(String name,Double price_per_person,int minimum_order,Catagory catagories[]);
	public void updateName(String name);
	public void updatePrice_per_person(double pricePerPerson);
	public void updateMinimumOrder(int minimum);
	public void updateCatagories(Catagory catagories[]);
	public boolean isMatch(String keyword);
	public int getId();
	public String getName();
	public double getPrice_per_person();
	public int getMinimum_order();
	public Catagory[] getCatagories();
	public int getCreate_date();
	public int getLast_modified_date();
	public boolean isNil();
	
}

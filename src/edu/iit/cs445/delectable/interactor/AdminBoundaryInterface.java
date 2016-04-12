package edu.iit.cs445.delectable.interactor;

import edu.iit.cs445.delectable.entity.Catagory;

public interface AdminBoundaryInterface {
	public int addFoodToMenu(String name,Double price_per_person,int minimum_order,Catagory[] catagories) throws RuntimeException;
	public void editFoodInMenu(int id,double price_per_person) throws RuntimeException;
	public double getSurcharge() throws RuntimeException;
	public void changeSurcharge(double surcharge) throws RuntimeException;
	public void deliveredOrder(int id) throws RuntimeException;
}

package edu.iit.cs445.delectable.entity;

import java.util.List;

public interface Menu {
	public List<Food> listMenu();
	public int addItem(Food food) throws RuntimeException;
	public boolean deleteItem(int id) throws RuntimeException;
	public List<Food> searchItem(String keyword);
	public Food searchItemById(int id);
	public double getSurcharge();
	public void setSurcharge(double amount);
}

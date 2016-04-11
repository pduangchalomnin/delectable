package edu.iit.cs445.delectable.interactor;

import java.util.List;

import edu.iit.cs445.delectable.entity.Food;

public interface MenuBoundaryInterface {
	
	public List<Food> getMenu();
	public Food getMenuItem(int id) throws RuntimeException;

}

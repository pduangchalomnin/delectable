package edu.iit.cs445.delectable.interactor;

import java.util.List;

import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.Menu;
import edu.iit.cs445.delectable.entity.MenuImp;

public class MenuManager implements MenuBoundaryInterface {

	private static MenuBoundaryInterface instance = null;
	private Menu menu = MenuImp.getInstance();
	
	private MenuManager(){
		
	}
	
	public static MenuBoundaryInterface getInstance(){
		if(instance == null)
			instance = new MenuManager();
		return instance;
	}
	
	public List<Food> getMenu() {
		return menu.listMenu();
	}

	public Food getMenuItem(int id) throws RuntimeException {
		Food output = menu.searchItemById(id);
		if(output.isNil())
			throw new RuntimeException();
		return menu.searchItemById(id);
	}

}

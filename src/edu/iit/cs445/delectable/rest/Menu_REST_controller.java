package edu.iit.cs445.delectable.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.interactor.MenuBoundaryInterface;
import edu.iit.cs445.delectable.interactor.MenuManager;
import edu.iit.cs445.delectable.rest.presenter.FoodPresenter;

@Path("/menu")
public class Menu_REST_controller {
	private MenuBoundaryInterface menuManager = MenuManager.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSurcharge() {
		Gson gson = new Gson();
		List<Food> foods = menuManager.getMenu();
		List<FoodPresenter> foodPresenter = new ArrayList<FoodPresenter>();
		Iterator<Food> it = foods.iterator();
		while(it.hasNext()) {
			Food food = it.next();
			foodPresenter.add(new FoodPresenter(food.getId(), food.getName()
					, food.getPrice_per_person(), food.getMinimum_order(), food.getCatagories()));
		}
		String s = gson.toJson(foodPresenter);
		
		return Response.ok(s).build();
	}
	
	@Path("/{mid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuItem(@PathParam("mid") int mid) {
		Gson gson = new Gson();
		Food food;
		try {
			food = menuManager.getMenuItem(mid);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		String s = gson.toJson(food);
		return Response.ok(s).build();
	}

}

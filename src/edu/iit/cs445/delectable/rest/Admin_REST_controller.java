package edu.iit.cs445.delectable.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.iit.cs445.delectable.entity.Catagory;
import edu.iit.cs445.delectable.interactor.AdminBoundaryInterface;
import edu.iit.cs445.delectable.interactor.AdminManager;

@Path("/admin")
public class Admin_REST_controller {
	private AdminBoundaryInterface adminManager = AdminManager.getInstance();
	
	@Path("/menu")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(@Context UriInfo uriInfo, String json) {
		int id;
		Gson gson = new Gson();
		try {
			JsonObject jsonObj = getGsonObject(json);
			
			String name = jsonObj.get("name").getAsString();
			Double price_per_person = jsonObj.get("price_per_person").getAsDouble();
			int minimum_order = jsonObj.get("minimum_order").getAsInt();
			Catagory[] catagories = new Gson().fromJson(jsonObj.get("catagories"), Catagory[].class);
			
			//Type collectionType = new TypeToken<List<Catagory>>(){}.getType();
			//List<Catagory> catagories = new Gson().fromJson(jsonObj.get("catagories"), collectionType);
			id = adminManager.addFoodToMenu(name, price_per_person, minimum_order, catagories);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		JsonObject output = new JsonObject();
		output.addProperty("id", id);
		String s = gson.toJson(output);
		return Response.status(201).header("Location",String.format("account/%s",id)).entity(s).build();
	}

		private JsonObject getGsonObject(String json) {
			JsonElement jsonElement = new JsonParser().parse(json);
			JsonObject jsonObj = jsonElement.getAsJsonObject();
			return jsonObj;
		}
	
	@Path("/menu/{mid}")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFoodPrice(@PathParam("mid") int mid, String json) {
		Response response;
		try {
			JsonObject jsonObj = getGsonObject(json);
			
			int id = jsonObj.get("id").getAsInt();
			double price_per_person = jsonObj.get("price_per_person").getAsDouble();
			if(mid != id) {
				response = Response.status(400).build();
			}
			else {
				adminManager.editFoodInMenu(mid, price_per_person);	
				response = Response.status(204).header("Location",String.format("../menu/%s",mid)).build();
			}
		}
		catch(RuntimeException e) {
			response = Response.status(400).build();
		}
        return response;
    }
	
	@Path("/surcharge")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSurcharge() {
		Gson gson = new Gson();
		JsonObject output = new JsonObject();
		
		double surcharge = adminManager.getSurcharge();
		output.addProperty("surcharge", surcharge);
		String s = gson.toJson(output);
		return Response.ok(s).build();
	}
	
	@Path("/surcharge")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSurcharge(String json) {
		JsonObject jsonObj = getGsonObject(json);
		double surcharge;
		
		try {
			surcharge = jsonObj.get("surcharge").getAsDouble();
			adminManager.changeSurcharge(surcharge);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		return Response.status(204).header("Location","admin/surcharge").build();
	}
}
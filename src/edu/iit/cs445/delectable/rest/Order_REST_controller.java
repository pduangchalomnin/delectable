package edu.iit.cs445.delectable.rest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import edu.iit.cs445.delectable.entity.Address;
import edu.iit.cs445.delectable.entity.Customer;
import edu.iit.cs445.delectable.entity.CustomerImp;
import edu.iit.cs445.delectable.entity.Food;
import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.interactor.MenuBoundaryInterface;
import edu.iit.cs445.delectable.interactor.MenuManager;
import edu.iit.cs445.delectable.interactor.OrderBoundaryInterface;
import edu.iit.cs445.delectable.interactor.OrderManager;
import edu.iit.cs445.delectable.rest.presenter.ItemPresenter;
import edu.iit.cs445.delectable.rest.presenter.OrderDetailPresenter;
import edu.iit.cs445.delectable.rest.presenter.OrderPresenter;
import edu.iit.cs445.delectable.rest.presenter.OrdersListPresenter;

@Path("/order")
public class Order_REST_controller {
	private OrderBoundaryInterface orderManager = OrderManager.getInstance();
	private MenuBoundaryInterface menuManager = MenuManager.getInstance();
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(String json) {
		int id;
		Gson gson = new Gson();
		try {
			JsonObject jsonObj = getGsonObject(json);
			
			String delivery_date = jsonObj.get("delivery_date").getAsString();
			String note = jsonObj.get("note").getAsString();
			String delivery_address = jsonObj.get("delivery_address").getAsString();
			Address address = createAddress(delivery_address);
			Customer customer = createCustomer(jsonObj);
			List<Item> item = createItem(jsonObj);
				
			id = orderManager.createOrder(delivery_date, address, customer, note, item);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		JsonObject output = new JsonObject();
		output.addProperty("id", id);
		output.addProperty("cancel_url","/order/cancel/"+id);
		String s = gson.toJson(output);
		return Response.status(201).header("Location",String.format("order/%s",id)).entity(s).build();
	}

		private List<Item> createItem(JsonObject jsonObj) {
			Type listOfTestObject = new TypeToken<List<ItemPresenter>>(){}.getType();
			List<ItemPresenter> orderDetail = new Gson().fromJson(jsonObj.get("order_detail"), listOfTestObject);
			Iterator<ItemPresenter> it = orderDetail.iterator();
			List<Item> item = new ArrayList<Item>();
			while(it.hasNext()) {
				ItemPresenter presenter = it.next();
				Food food = menuManager.getMenuItem(presenter.getId());
				item.add(new Item(food, presenter.getCount()));
			}
			return item;
		}

		private Customer createCustomer(JsonObject jsonObj) {
			JsonObject jsonObjAddress = jsonObj.get("personal_info").getAsJsonObject();
			String cutomer_name = jsonObjAddress.get("name").getAsString();
			String email = jsonObjAddress.get("email").getAsString();
			String phone = jsonObjAddress.get("phone").getAsString();
			Customer customer = new CustomerImp(cutomer_name, email, phone, email);
			return customer;
		}

		private Address createAddress(String delivery_address) {
			String[] split_address = delivery_address.split(",");
			String[] leftSide= split_address[0].split(" ");
			String houseNumber = leftSide[0];
			String[] rightSide= split_address[1].split(" ");
			String postCode = rightSide[rightSide.length-1];
			String state = rightSide[rightSide.length-2];
			String street = "";
			for(int i = 1;i<leftSide.length;i++) {
				street += leftSide[i]+" ";
			}
			street = street.trim();
			String city = "";
			for(int i = 0;i<rightSide.length-2;i++) {
				city += rightSide[i]+" ";
			}
			city = city.trim();
			Address address = new Address(houseNumber, street, city, state, postCode);
			return address;
		}
	
		private JsonObject getGsonObject(String json) {
			JsonElement jsonElement = new JsonParser().parse(json);
			JsonObject jsonObj = jsonElement.getAsJsonObject();
			return jsonObj;
		}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@QueryParam("date") String date) {
		Gson gson = new Gson();
		String s;
		try {
			List<Order> orders;
			if(date==null) {
				orders = orderManager.getOrders();
			}
			else {
				orders = orderManager.getOrdersByDate(date);
			}
			List<OrdersListPresenter> presenter = createOrdersListPresenter(orders);
			s = gson.toJson(presenter);
		}
		catch(RuntimeException e) {
			System.out.println(e.toString());
			return Response.status(400).build();
		}
		return Response.ok(s).build();
		
	}

		private List<OrdersListPresenter> createOrdersListPresenter(List<Order> orders) {
			List<OrdersListPresenter> presenter = new ArrayList<OrdersListPresenter>();
			Iterator<Order> it = orders.iterator();
			while(it.hasNext()){
				Order order = it.next();
				presenter.add(new OrdersListPresenter(order.getId(), order.getOrderDate(), order.getDeliveryDate()
						, order.getTotalAmount(), order.getSurcharge(), order.getStatus(), order.getCustomerEmail()));
			}
			return presenter;
		}
	
	@Path("/{oid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersById(@PathParam("oid") int mid) {
		Gson gson = new Gson();
		String s;
		try {
			Order order = orderManager.getOrderById(mid);
			List<Item> items = order.listItem();
			List<OrderDetailPresenter> order_detail = createOrderDetail(items);
			OrderPresenter presenter = new OrderPresenter(order.getId(), order.getTotalAmount()
					, order.getSurcharge(), order.getStatus(), order.getOrderDate()
					, order.getDeliveryDate(), order.getCustomer()
					, order.geteDeliveryAddress().toString(), order.getNote(), order_detail);
			s = gson.toJson(presenter);
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		return Response.ok(s).build();
		
	}

		private List<OrderDetailPresenter> createOrderDetail(List<Item> items) {
			List<OrderDetailPresenter> order_detail = new ArrayList<OrderDetailPresenter>();
			Iterator<Item> it = items.iterator();
			while(it.hasNext()) {
				Item item = it.next();
				order_detail.add(new OrderDetailPresenter(item.getFoodId(), item.getFoodName(), item.getCount()));
			}
			return order_detail;
		}
	
}

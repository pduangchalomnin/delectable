package edu.iit.cs445.delectable.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import edu.iit.cs445.delectable.entity.Item;
import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.ReportCode;
import edu.iit.cs445.delectable.entity.RevenueReport;
import edu.iit.cs445.delectable.interactor.ReportBoundaryInterface;
import edu.iit.cs445.delectable.interactor.ReportManager;
import edu.iit.cs445.delectable.rest.presenter.OrderDetailPresenter;
import edu.iit.cs445.delectable.rest.presenter.ReportOrdersPresenter;
import edu.iit.cs445.delectable.rest.presenter.ReportPresenter;

@Path("/report")
public class Report_REST_controller {
	
	private ReportBoundaryInterface reportManager = ReportManager.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportList() {
		Gson gson = new Gson();
		String s = "";
		try {
			s = gson.toJson(reportManager.getReportType());
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		return Response.ok(s).build();
		
	}
	
	@Path("/{rid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportById(@PathParam("rid") int rid
			,@QueryParam("start_date") String start_date,@QueryParam("end_date") String end_date) {
		Gson gson = new Gson();
		String s = "";
		Response response;
		try {
			List<Order> orders;
			start_date = checkNull(start_date);
			end_date = checkNull(end_date);
			if(rid == 801) {
				orders = reportManager.getDeliveryToday();
				ReportCode reportCode = reportManager.getReportCode(801);
				List<ReportOrdersPresenter> ordersPresenterList = createReportOrdersPresenter(orders);
				
				s = gson.toJson(new ReportPresenter(reportCode, ordersPresenterList));
			}
			else if(rid==802) {
				orders = reportManager.getDeliveryTomorrow();
				ReportCode reportCode = reportManager.getReportCode(802);
				List<ReportOrdersPresenter> ordersPresenterList = createReportOrdersPresenter(orders);
				
				s = gson.toJson(new ReportPresenter(reportCode, ordersPresenterList));
			}
			else if(rid==803) {
				RevenueReport report = reportManager.getRevenueReport(start_date, end_date);
				s = gson.toJson(report);
			}
			else if(rid==804) {
				orders = reportManager.getDeliveryList(start_date, end_date);
				ReportCode reportCode = reportManager.getReportCode(804);
				List<ReportOrdersPresenter> ordersPresenterList = createReportOrdersPresenter(orders);
				
				s = gson.toJson(new ReportPresenter(reportCode, ordersPresenterList));
			}
			else {
				response = Response.status(404).build();
			}
			response = Response.ok(s).build();
		}
		catch(RuntimeException e) {
			return Response.status(400).build();
		}
		return response;
	}

		private List<ReportOrdersPresenter> createReportOrdersPresenter(List<Order> orders) {
			List<ReportOrdersPresenter> orderPresenterList = new ArrayList<ReportOrdersPresenter>();
			Iterator<Order> it = orders.iterator();
			while(it.hasNext()) {
				Order tmpOrder = it.next();
				
				List<OrderDetailPresenter> order_detail = createOrderDetail(tmpOrder);
				ReportOrdersPresenter ordersPresenter = new ReportOrdersPresenter(tmpOrder, order_detail);
				orderPresenterList.add(ordersPresenter);
			}
			return orderPresenterList;
		}
	
		private List<OrderDetailPresenter> createOrderDetail(Order tmpOrder) {
			List<OrderDetailPresenter> order_detail = new ArrayList<OrderDetailPresenter>();
			Iterator<Item> itItem = tmpOrder.listItem().iterator();
			while(itItem.hasNext()) {
				Item tmpItem = itItem.next();
				order_detail.add(new OrderDetailPresenter(tmpItem));
			}
			return order_detail;
		}

		private String checkNull(String date) {
			if(date == null) {
				date = "";
			}
			return date;
		}
}

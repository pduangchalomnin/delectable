package edu.iit.cs445.delectable.interactor;

import java.util.List;

import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.ReportCode;
import edu.iit.cs445.delectable.entity.RevenueReport;

public interface ReportBoundaryInterface {
	public ReportCode[] getReportType();
	public List<Order> getDeliveryToday();
	public List<Order> getDeliveryTomorrow();
	public RevenueReport getRevenueReport(String start_date,String end_date) throws RuntimeException;
	public List<Order> getDeliveryList(String start_date,String end_date) throws RuntimeException;

}

package edu.iit.cs445.delectable.interactor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.iit.cs445.delectable.entity.Order;
import edu.iit.cs445.delectable.entity.OrdersList;
import edu.iit.cs445.delectable.entity.OrdersListImp;
import edu.iit.cs445.delectable.entity.ReportCode;
import edu.iit.cs445.delectable.entity.RevenueReport;
import edu.iit.cs445.delectable.entity.Status;

public class ReportManager implements ReportBoundaryInterface {

	private static ReportBoundaryInterface instance = null;
	private ReportCode[] reportCodes = new ReportCode[4];
	
	private ReportManager() {
		reportCodes[0] = new ReportCode(801, "Orders to deliver today");
		reportCodes[1] = new ReportCode(802, "Orders to deliver tomorrow");
		reportCodes[2] = new ReportCode(803, "Revenue report");
		reportCodes[3] = new ReportCode(804, "Orders delivery report");
		
	}
	
	public static ReportBoundaryInterface getInstance() {
		if(instance == null)
			instance = new ReportManager();
		return instance;
	}

	public ReportCode[] getReportType() {
		return reportCodes;
	}

	public List<Order> getDeliveryToday() {
		Date date = new Date();
		List<Order> output = getOrderFromDate(date,date);
		return output;
	}

		private List<Order> getOrderFromDate(Date start_date,Date end_date) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			List<Order> output = new ArrayList<Order>();
			OrdersList orders = OrdersListImp.getInstance();
			
			Iterator<Order> it = orders.getOrders().iterator();
			while(it.hasNext()) {
				Order tmpOrder = it.next();
				int deliverDate = Integer.parseInt(tmpOrder.getDeliveryDate());
				int startDate = 0;
				int endDate = 0;
				if(start_date != null) {
					startDate = Integer.parseInt(dateFormat.format(start_date));
				}
				if(end_date != null) {
					endDate = Integer.parseInt(dateFormat.format(end_date));
				}
				if(!tmpOrder.getStatus().equals(Status.CANCELED.toString())
						&& (start_date!=null && end_date!=null && deliverDate>=startDate && deliverDate<=endDate)
						|| (start_date!=null && end_date==null && deliverDate>=startDate)
						|| (start_date==null && end_date!=null && deliverDate<=endDate)
						|| (start_date==null && end_date==null)) {
					output.add(tmpOrder);
				}
			}
			return output;
		}

	public List<Order> getDeliveryTomorrow() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 1); 
		Date tomorrow = cal.getTime();
		
		List<Order> output = getOrderFromDate(tomorrow,tomorrow);
		return output;
	}

	public RevenueReport getRevenueReport(String start_date, String end_date) throws RuntimeException{
		RevenueReport report = new RevenueReport();
		report.setStart_date(start_date);
		report.setEnd_date(end_date);
		
		if(!start_date.isEmpty()) {
			validateDateTime(start_date);
		}
		if(!end_date.isEmpty()) {
			validateDateTime(end_date);
		}
		validateDateRange(start_date, end_date);
		
		OrdersList orders = OrdersListImp.getInstance();
		Iterator<Order> it = orders.getOrders().iterator();
		while(it.hasNext()) {
			Order tmpOrder = it.next();
			int deliverDate = Integer.parseInt(tmpOrder.getDeliveryDate());
			int startDate = 0;
			int endDate = 0;
			if(!start_date.isEmpty()){
				startDate= Integer.parseInt(start_date);
			}
			if(!end_date.isEmpty()) {
				endDate = Integer.parseInt(end_date);
			}
			
			if((!start_date.isEmpty() && !end_date.isEmpty() && deliverDate >= startDate && deliverDate <= endDate)
				|| (!start_date.isEmpty() && end_date.isEmpty()	&& deliverDate >= startDate)
				|| (start_date.isEmpty() && !end_date.isEmpty()	&& deliverDate <= endDate)
				|| (start_date.isEmpty() && end_date.isEmpty()))
			{
				addReportValue(report, tmpOrder);
			}
		}
		return report;
	}

		private void validateDateRange(String start_date, String end_date) throws RuntimeException {
			if((!start_date.isEmpty() && !end_date.isEmpty()) && Integer.parseInt(start_date)>Integer.parseInt(end_date)) {
				throw new RuntimeException();
			}
		}

		private void addReportValue(RevenueReport report, Order tmpOrder) {
			if(!tmpOrder.getStatus().equals(Status.CANCELED.toString())) {
				report.setFood_revenue(report.getFood_revenue()+tmpOrder.getTotalAmount().doubleValue());
				BigDecimal roundUp = new BigDecimal(report.getFood_revenue());
				roundUp = roundUp.setScale(2, BigDecimal.ROUND_HALF_UP);  
				report.setFood_revenue(roundUp.doubleValue());
				report.setSurcharge_revenue(report.getSurcharge_revenue()+tmpOrder.getSurcharge());
			}
			report.setOrders_placed(report.getOrders_placed()+1);
			if(tmpOrder.getStatus().equals(Status.CANCELED.toString())) {
				report.setOrders_cancelled(report.getOrders_cancelled()+1);
			}
			else if(tmpOrder.getStatus().equals(Status.OPEN.toString())) {
				report.setOrders_open(report.getOrders_open()+1);
			}
		}
	
		private void validateDateTime(String date) throws RuntimeException {
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4,6));
			int day = Integer.parseInt(date.substring(6));
			Calendar calendar = Calendar.getInstance();
			
			if(date.length() != 8) {
				throw new RuntimeException();
			}
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month-1);
			int maxDay = calendar.getActualMaximum(Calendar.DATE);
			
			if(month < 1 || month > 12 || day < 1 || day > maxDay || year <= 0){
				throw new RuntimeException();
			}
		}

	public List<Order> getDeliveryList(String start_date, String end_date) throws RuntimeException{
		if(!start_date.isEmpty()) {
			validateDateTime(start_date);
		}
		if(!end_date.isEmpty()) {
			validateDateTime(end_date);
		}
		validateDateRange(start_date, end_date);
		Date startDate = formatStartDate(start_date);
		Date endDate = formatEndDate(end_date);
		List<Order> output = getOrderFromDate(startDate,endDate);
		return output;
	}
	
		private Date formatStartDate(String date) throws RuntimeException {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date output;
			if(!date.isEmpty()) {
				try {
					output = dateFormat.parse(date);
				} catch (ParseException e) {
					throw new RuntimeException();
				}
			}
			else {
				output = null;
			}
			return output;
		}
		
		private Date formatEndDate(String date) throws RuntimeException {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date output;
			if(!date.isEmpty()) {
				try {
					output = dateFormat.parse(date);
				} catch (ParseException e) {
					throw new RuntimeException();
				}
			}
			else {
				output = null;
			}
			return output;
		}

	public ReportCode getReportCode(int rid) throws RuntimeException {
		for(int i=0;i<reportCodes.length;i++) {
			if(reportCodes[i].getCode() == rid) {
				return reportCodes[i];
			}
		}
		throw new RuntimeException();
	}

}

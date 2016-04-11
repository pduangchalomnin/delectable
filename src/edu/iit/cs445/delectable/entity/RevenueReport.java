package edu.iit.cs445.delectable.entity;

public class RevenueReport {
	private int id = 803;
	private String name = "Revenue report";
	private String start_date;
	private String end_date;
	private int orders_placed;
	private int orders_cancelled;
	private int orders_open;
	private double food_revenue;
	private double surcharge_revenue;
	
	public RevenueReport() {
		this.start_date = "";
		this.end_date = "";
		this.orders_placed = 0;
		this.orders_cancelled = 0;
		this.orders_open = 0;
		this.food_revenue = 0;
		this.surcharge_revenue = 0;
	}
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getOrders_placed() {
		return orders_placed;
	}
	public void setOrders_placed(int orders_placed) {
		this.orders_placed = orders_placed;
	}
	public int getOrders_cancelled() {
		return orders_cancelled;
	}
	public void setOrders_cancelled(int orders_cancelled) {
		this.orders_cancelled = orders_cancelled;
	}
	public int getOrders_open() {
		return orders_open;
	}
	public void setOrders_open(int orders_open) {
		this.orders_open = orders_open;
	}
	public double getFood_revenue() {
		return food_revenue;
	}
	public void setFood_revenue(double food_revenue) {
		this.food_revenue = food_revenue;
	}
	public double getSurcharge_revenue() {
		return surcharge_revenue;
	}
	public void setSurcharge_revenue(double surcharge_revenue) {
		this.surcharge_revenue = surcharge_revenue;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}

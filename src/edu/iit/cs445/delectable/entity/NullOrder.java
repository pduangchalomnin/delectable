package edu.iit.cs445.delectable.entity;

import java.util.List;

public class NullOrder implements Order {
	
	private static Order instance = null;
	
	private NullOrder(){
		
	}
	
	public static Order getinstance(){
		if(instance == null)
			instance = new NullOrder();
		return instance;
	}

	@Override
	public void changeStatus(Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getSurcharge() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCustomerEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTotalAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDeliveryDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNote() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> listItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address geteDeliveryAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMatch(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNil() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getCustomerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCustomerMatch(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSurcharge(double surcharge) {
		// TODO Auto-generated method stub
		
	}

}

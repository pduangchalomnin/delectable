package edu.iit.cs445.delectable.entity;

public class NullFood implements Food {
	
	private static Food instance = null;
	
	private NullFood(){
		
	}
	
	public static Food getinstance(){
		if(instance == null)
			instance = new NullFood();
		return instance;
	}

	@Override
	public void updateDeatail(String name, Double price_per_person, int minimum_order, Catagory[] catagories) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePrice_per_person(double pricePerPerson) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMinimumOrder(int minimum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCatagories(Catagory[] catagories) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMatch(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPrice_per_person() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimum_order() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Catagory[] getCatagories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCreate_date() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLast_modified_date() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isNil() {
		// TODO Auto-generated method stub
		return true;
	}

}

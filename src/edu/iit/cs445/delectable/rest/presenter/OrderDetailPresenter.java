package edu.iit.cs445.delectable.rest.presenter;

public class OrderDetailPresenter {
	public int id;
	public String name;
	public int count;

	public OrderDetailPresenter(int id,String name,int count) {
		this.id =id;
		this.name = name;
		this.count = count;
	}
}
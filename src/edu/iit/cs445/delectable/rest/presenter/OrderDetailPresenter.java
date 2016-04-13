package edu.iit.cs445.delectable.rest.presenter;

import edu.iit.cs445.delectable.entity.Item;

public class OrderDetailPresenter {
	public int id;
	public String name;
	public int count;

	public OrderDetailPresenter(Item item) {
		this.id =item.getFoodId();
		this.name = item.getFoodName();
		this.count = item.getCount();
	}
}
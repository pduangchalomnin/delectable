package edu.iit.cs445.delectable.rest.presenter;

import java.util.List;

import edu.iit.cs445.delectable.entity.ReportCode;

public class ReportPresenter {
	int id;
	String name;
	List<ReportOrdersPresenter> orders;
	
	public ReportPresenter(ReportCode reportCode,List<ReportOrdersPresenter> orders) {
		this.id = reportCode.getCode();
		this.name = reportCode.getName();
		this.orders = orders;
	}
}

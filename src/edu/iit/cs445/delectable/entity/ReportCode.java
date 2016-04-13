package edu.iit.cs445.delectable.entity;

public class ReportCode {
	private int id;
	private String name;
	
	public ReportCode(int code, String name){
		this.id = code;
		this.name = name;
	}

	public int getCode() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}

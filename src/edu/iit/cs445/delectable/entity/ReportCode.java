package edu.iit.cs445.delectable.entity;

public class ReportCode {
	private int code;
	private String name;
	
	public ReportCode(int code, String name){
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}

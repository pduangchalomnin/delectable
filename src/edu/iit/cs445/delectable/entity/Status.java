package edu.iit.cs445.delectable.entity;

public enum Status {
	OPEN{
		public String toString(){
			return "open";
		}
	},CANCELED{
		public String toString(){
			return "canceled";
		}
	},DELIVERIED{
		public String toString(){
			return "deliveried";
		}
	}
}

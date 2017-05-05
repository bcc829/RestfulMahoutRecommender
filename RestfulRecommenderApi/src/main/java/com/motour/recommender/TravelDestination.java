package com.motour.recommender;

public class TravelDestination {
	private String Attribute;
	public TravelDestination(int id) throws Exception{
		XmlParserForGov destination = new XmlParserForGov();
		this.Attribute = destination.returnCat2(String.valueOf(id));
	}
	public String getAttribute(){
		return this.Attribute;
	}
}

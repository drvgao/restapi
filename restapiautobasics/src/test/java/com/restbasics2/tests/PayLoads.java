package com.restbasics2.tests;

public class PayLoads {
	
	//add place pay load
	public static String getPlacePayLoad() {
		String payLoad = "{" + "  \"location\": {" + "    \"lat\": -33.222," + "    \"lng\": 151.222" + "  },"
				+ "  \"accuracy\": 50," + "  \"name\": \"Durgam test location1!\","
				+ "  \"phone_number\": \"(02) 9374 4001\","
				+ "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," + "  \"types\": [\"shoe_store\"],"
				+ "  \"website\": \"http://www.google.com.au/\"," + "  \"language\": \"en-AU\"" + "}";
		return payLoad;
	}
}

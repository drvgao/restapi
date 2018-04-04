package com.rest.test;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostTest {

	@Test
	public void postTest() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";

		// 2. parameters
		given().
			queryParam("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").
			body("{" +
					"  \"location\": {" +
					"    \"lat\": -33.222," +
					"    \"lng\": 151.222" +
					"  }," +
					"  \"accuracy\": 50," +
					"  \"name\": \"Durgam test location1!\"," +
					"  \"phone_number\": \"(02) 9374 4001\"," +
					"  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," +
					"  \"types\": [\"shoe_store\"]," +
					"  \"website\": \"http://www.google.com.au/\"," +
					"  \"language\": \"en-AU\"" +
					"}").
			when().
				post("/maps/api/place/add/json").
			then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				body("status", equalTo("OK"));
				
			}
}

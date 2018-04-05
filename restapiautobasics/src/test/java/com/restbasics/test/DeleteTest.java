package com.restbasics.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * create a place. Extract the placeId from response. And delete the place.
 * @author RDURGAM
 *
 */
public class DeleteTest {

	@Test
	public void deleteTest() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";

		// create location using below pay load
		String payLoad = "{" + "  \"location\": {" + "    \"lat\": -33.222," + "    \"lng\": 151.222" + "  },"
				+ "  \"accuracy\": 50," + "  \"name\": \"Durgam test location1!\","
				+ "  \"phone_number\": \"(02) 9374 4001\","
				+ "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," + "  \"types\": [\"shoe_store\"],"
				+ "  \"website\": \"http://www.google.com.au/\"," + "  \"language\": \"en-AU\"" + "}";

		// 2. create and grab the response
		Response response = given().queryParam("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").body(payLoad).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();

		String res = null;
		res = response.asString();

		System.out.println("response: " + res);

		// 3. grab the placeId to delete the place created above.
		JsonPath locationId = new JsonPath(res);
		String placeId = locationId.get("place_id");
		System.out.println("placeid: " + placeId);

		// 4. delete the place now.
		given().queryParam("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo")
				.body("{" + "  \"place_id\": \"" + placeId + "\"" + "}").
					post("/maps/api/place/delete/json").then()
						.assertThat().statusCode(200).and()
						.contentType(ContentType.JSON).and().body("status", equalTo("OK"));
	}
}

package com.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class HeadersTest {
	
	@Test
	public void verifyHeader() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";

		// 2. parameters
		given().param("query", "restaurants+in+Miyapur").
				param("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").
		when()
			.get("/maps/api/place/textsearch/json").
		then().assertThat().statusCode(200).
			body("results[0].name", equalTo("Angaara Restaurant")).and().
			header("Server", "scaffolding on HTTPServer2");
	}
}

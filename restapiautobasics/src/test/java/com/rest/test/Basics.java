package com.rest.test;

import org.junit.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
	@Test
	public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {

		given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().assertThat()
				.body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
	}

	@Test
	public void firstTest() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";

		// parameters
		given().param("query", "restaurants+in+Miyapur").param("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").when()
				.get("/maps/api/place/textsearch/json").then().assertThat().statusCode(200)
				.body("results.geometry.location.lat", contains(17.496931));
	}
}

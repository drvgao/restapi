package com.restbasics.test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import io.restassured.RestAssured;


public class BasicTest {
	private static Logger log = LogManager.getLogger(BasicTest.class.getName());
	@Test
	public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {

		given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().assertThat()
				.body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
	}

//	@Test
	public void firstTest() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";

		// 2. parameters
		given().param("query", "restaurants+in+Miyapur").
				param("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").
		when()
			.get("/maps/api/place/textsearch/json").
		then().assertThat().statusCode(200).
			body("results[0].geometry.location.lat", equalTo(17.496931));
//			.body("results.geometry.location.lat", contains(17.496931));
		
	}
	
	@Test
	public void verifyNearByLocation() {
		// 1. base url
		RestAssured.baseURI = "https://maps.googleapis.com";
		log.info("Google Base URI "+RestAssured.baseURI);

		// 2. parameters
		given().param("query", "restaurants+in+Miyapur").
				param("key", "AIzaSyD05C4l0V-VS-4aM1mY5KQUdUE2xqu-GJo").
		when()
			.get("/maps/api/place/textsearch/json").
		then().assertThat().statusCode(200).
			body("results[0].name", equalTo("Angaara Restaurant"));
	}
	
	
}
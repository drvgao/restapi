package com.restbasics.test;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * query parameter example
 * @author RDURGAM
 *
 */
public class PathParamTest {

	/**
	 * path parameter example
	 */
	@Test
	public void getCountry() {
		// 1. base url
		RestAssured.baseURI = "http://restcountries.eu";

		// 2. parameters
		// Here the key name 'country' must match the url parameter {country}
		Response response = given()
		        .pathParam("country", "Finland")
		        .when()
		            .get("/rest/v1/name/{country}")
		        .then().extract().response();
//		            .body("capital", equalTo("Helsinki"));
		String res = null;
		res = response.asString();

		System.out.println("response: " + res);
			}
	
	/**
	 * path parameter example with variable
	 */
	@Test
	public void getCountry2() {
		String cty = "Finland";
		// 1. base url
		RestAssured.baseURI = "http://restcountries.eu";

		// 2. parameters
		// Here the name of the variable have no relation with the URL parameter {country}
		Response response = given()
		        .when()
		            .get("/rest/v1/name/{country}", cty)
		        .then().extract().response();
//		            .body("capital", equalTo("Helsinki"));
		String res = null;
		res = response.asString();

		System.out.println("response: " + res);
			}
	
	//to try other services
	/*
	 * https://stackoverflow.com/questions/32475850/how-to-pass-parameters-to-rest-assured
	 * Now if you need to call different services, you can also parametrize the "service" like this:

		// Search by name
		String val = "Finland";
		String svc = "name";

		RestAssured.given()
		        .when()
		            .get("http://restcountries.eu/rest/v1/{service}/{value}", svc, val)
		        .then()
		            .body("capital", containsString("Helsinki"));


		// Search by ISO code (alpha)
		val = "CH"
		svc = "alpha"

		RestAssured.given()
		        .when()
		            .get("http://restcountries.eu/rest/v1/{service}/{value}", svc, val)
		        .then()
		            .body("capital", containsString("Bern"));

		// Search by phone intl code (callingcode)
		val = "359"
		svc = "callingcode"

		RestAssured.given()
		        .when()
		            .get("http://restcountries.eu/rest/v1/{service}/{value}", svc, val)
		        .then()
		            .body("capital", containsString("Sofia"));*/
}

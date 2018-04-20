package com.restbasics3.tests;


import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rest.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonArray_Log_Test {
	Properties prop;
	
	@BeforeTest
	public void getEnvData() {
		try {
			prop = new Properties();
			FileInputStream fs = new FileInputStream("./src/main/resources/files/env.properties");
			prop.load(fs);
			System.out.println("host: " + prop.get("HOST"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	@Test
	public void verifyNearByLocation() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("HOST");

		// 2. parameters
		Response res = given().param("query", "theaters+in+Lingampally").
			queryParam("key", prop.getProperty("KEY")).
		given().log().all().when()
			.get("/maps/api/place/textsearch/json").
		then().assertThat().statusCode(200).
				extract().response();
		
		JsonPath js = Utils.rawToJson(res);
		int count = js.get("results.size()");
		System.out.println("count "+count);
		
		for (int i = 0; i < count; i++) {
			String name = js.get("results["+i+"].name");
			System.out.println("name: "+name);
		}
		
	}
	
	
}
package com.restbasics2.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
/**
 * create a place. Extract the placeId from response. And delete the place.
 * 
 * @author RDURGAM
 *
 */
public class DeleteTest2 {
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

//	@Test
	public void test1() {
		System.out.println("sample test, called");
		System.out.println("host is: "+prop.getProperty("HOST"));
	}

	 @Test
	public void deleteTest() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("HOST");

		// 2. create and grab the response
		Response response = given().queryParam("key", prop.getProperty("KEY")).body(PayLoads.getPlacePayLoad()).when()
				.post(resources.addPlacePostResource()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();

		String res = null;
		res = response.asString();

		System.out.println("response: " + res);

		// 3. grab the placeId to delete the place created above.
		JsonPath locationId = new JsonPath(res);
		String placeId = locationId.get("place_id");
		System.out.println("placeid: " + placeId);

		// 4. delete the place now.
		given().queryParam("key", prop.getProperty("KEY"))
				.body("{" + "  \"place_id\": \"" + placeId + "\"" + "}").post("/maps/api/place/delete/json").then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK"));
	}
}

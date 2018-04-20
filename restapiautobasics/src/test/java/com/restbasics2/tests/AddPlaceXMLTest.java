package com.restbasics2.tests;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rest.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

/**
 * create a place. Extract the placeId from response. And delete the place.
 * 
 * @author RDURGAM
 *
 */
public class AddPlaceXMLTest {
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
	public void test() {
		try {
			System.out.println("string: "+Utils.getXMLString("./src/test/java/com/restbasics2/tests/AddPlacePostData.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addPlaceXML() throws IOException {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("HOST");
		String xmlPostData = Utils.getXMLString("./src/test/java/com/restbasics2/tests/AddPlacePostData.xml");
		
		// 2. create and grab the response
		Response response = given().queryParam("key", prop.getProperty("KEY")).body(xmlPostData).when()
				.post(resources.addPlacePostResourceXML()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.XML).and().extract().response();

		String res = null;
		res = response.asString();

		System.out.println("response: " + res);

		// 3. grab the placeId to delete the place created above.
		//TODO extract xml response values
		XmlPath xmlPath = new XmlPath(res);
		String placeId = xmlPath.get("PlaceAddResponse.place_id");
		System.out.println("placeid: " + placeId);
	}
}

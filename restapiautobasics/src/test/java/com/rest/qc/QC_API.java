package com.rest.qc;

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
public class QC_API {
	Properties prop;

	@BeforeTest
	public void getEnvData() {
		try {
			prop = new Properties();
			FileInputStream envProp = new FileInputStream("./src/main/resources/files/env.properties");
			prop.load(envProp);
			System.out.println("host: " + prop.get("QC_HOST"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void qcAuthentication() throws IOException {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("QC_HOST");
		String xmlPostData = Utils.getXMLString("./src/test/java/com/rest/qc/QCAuth.xml");
		
		// 2. create and grab the response
		given().body(xmlPostData).when()
				.post(qcresources.QC_AUTHENTICATE).then().assertThat().statusCode(200);
	}
	
//	@Test
	public void qcIsAuthenticated(){
		try {
			qcAuthentication();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1. base url
		RestAssured.baseURI = prop.getProperty("QC_HOST");
		
		// 2. create and grab the response
		Response response = given()
				.when()
				.get(qcresources.QC_IS_AUTHENTICATE)
				.then()
				.extract().response();

		String res = null;
		res = response.asString();

		System.out.println("response: " + res);

		// 3. grab the placeId to delete the place created above.
		//TODO extract xml response values
		XmlPath xmlPath = new XmlPath(res);
		String userName = xmlPath.get("AuthenticationInfo.Username").toString();
		System.out.println("Username: " + userName);
	}
	
//	@Test
	public void qcSiteSession(){
		try {
			qcAuthentication();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1. base url
		RestAssured.baseURI = prop.getProperty("QC_HOST");
		
		// 2. create and grab the response
		Response response = given()
				.when()
				.post(qcresources.QC_IS_AUTHENTICATE)
				.then()
				.extract().response();

		String res = null;
		res = response.asString();
		System.out.println("response: " + res);
	}
	
	@Test
	public void qcGetDomains(){
		try {
			qcAuthentication();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1. base url
		RestAssured.baseURI = prop.getProperty("QC_HOST");
		
		// 2. create and grab the response
		Response response = given()
				.when()
				.post("/qcbin/rest/domains/")
				.then()
				.extract().response();

		String res = null;
		res = response.asString();
		System.out.println("response: " + res);
	}
}

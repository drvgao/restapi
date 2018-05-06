package com.rest.twitter;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class TwitterAPI {
	Properties prop;
	String arg0 = null;
	String arg1 = null;
	String arg2 = null;
	String arg3 = null;
	
	@BeforeTest
	public void getEnvData() {
		try {
			prop = new Properties();
			FileInputStream fs = new FileInputStream("./src/main/resources/files/env.properties");
			prop.load(fs);
			arg0 = prop.getProperty("CONSUMER_KEY");
			arg1 = prop.getProperty("CONSUMER_SECRET");
			arg2 = prop.getProperty("ACCESS_TOKEN");
			arg3 = prop.getProperty("TOKEN_SECRET");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getTweet() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("TWITTER_BASE");

		// 2. parameters
		//https://github.com/rest-assured/rest-assured/wiki/Usage#oauth
		Response res = given().auth().oauth(arg0, arg1, arg2, arg3).
				queryParam("count", "1").
				when().log().all().
					get("home_timeline.json").
				then().
					extract().response();

		String tweet = res.asString();
		System.out.println(tweet);
	}

}
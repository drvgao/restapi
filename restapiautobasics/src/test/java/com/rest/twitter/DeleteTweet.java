package com.rest.twitter;

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


public class DeleteTweet {
	Properties prop;
	String arg0 = null;
	String arg1 = null;
	String arg2 = null;
	String arg3 = null;
	String tweetid = null;
	
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

	public String getTweet() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("TWITTER_BASE");

		// 2. parameters
		//https://github.com/rest-assured/rest-assured/wiki/Usage#oauth
		Response res = given().auth().oauth(arg0, arg1, arg2, arg3).
				queryParam("status", "This tweet is from API Automation #8").
				when().log().all().
					post("update.json").
				then().
					extract().response();

		String tweet = res.asString();
		System.out.println(tweet);
		JsonPath js = Utils.rawToJson(res);
		tweetid = js.get("id_str");
		System.out.println("tweet id: "+js.get("id_str"));
		return tweetid;
	}
	
//	@Test
	public void deleteTweet() {
		tweetid= getTweet();
		System.out.println("about to delete tweet with id, "+tweetid);
		// 1. base url
				RestAssured.baseURI = prop.getProperty("TWITTER_BASE");

				// 2. parameters
				//https://github.com/rest-assured/rest-assured/wiki/Usage#oauth
				Response res = given().auth().oauth(arg0, arg1, arg2, arg3).
						when().
							post("destroy/"+tweetid+".json").
						then().
							extract().response();
				//assert delete
//				String response = res.asString();
//				System.out.println(response);
				JsonPath js = Utils.rawToJson(res);
				tweetid = js.get("id_str");
				System.out.println("tweet text: "+js.get("text"));
				System.out.println("is truncated fale? "+js.get("truncated"));
	}

}
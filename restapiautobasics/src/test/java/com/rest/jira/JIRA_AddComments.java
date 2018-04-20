package com.rest.jira;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jira.payloads.JiraPayLoads;
import com.rest.utils.Utils;

import io.restassured.RestAssured;

public class JIRA_AddComments {
	Properties prop;

	@BeforeTest
	public void getEnvData() {
		try {
			prop = new Properties();
			FileInputStream fs = new FileInputStream("./src/main/resources/files/env.properties");
			prop.load(fs);
			// System.out.println("host: " + prop.get("HOST"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testAddJiraComment(int issueId) {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("JIRA_HOST");

		
		//add comments
		given().header("Content-Type", "application/json").
		header("Coookie", "JSESSIONID="+Utils.getJiraSessionID()).
		body(JiraPayLoads.getJiraComments()).
			when().
				post("/rest/api/2/issue/"+issueId+"/comment").
			then().assertThat().statusCode(201);
		
		//to update existing comment. use put method, and provide commentId in the resource URI
		//put("/rest/api/2/issue/"+issueId+"/comment/commentId").
	}

}
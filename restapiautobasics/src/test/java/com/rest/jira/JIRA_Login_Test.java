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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JIRA_Login_Test {
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
	public void testCreateJIRAIssue() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("JIRA_HOST");

		// 2. parameters
		Response res = given().header("Content-Type", "application/json").
				header("Coookie", "JSESSIONID="+Utils.getJiraSessionID()).
				body(JiraPayLoads.getCreateIssuePL()).
				when().
					post("rest/api/2/issue").
				then().assertThat().statusCode(201).
					extract().response();

		JsonPath js = Utils.rawToJson(res);
		String issueId = js.get("id");
		System.out.println("New issue id:  " + issueId);

	}

}
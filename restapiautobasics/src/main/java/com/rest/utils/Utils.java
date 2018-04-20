package com.rest.utils;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Utils {
	static Properties prop;
	
	public static void getEnvData() {
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

	/**
	 * Convert REST API raw XML pay load, to Java String format.
	 * 
	 * @param path
	 * @return xmlAsString
	 * @throws IOException
	 */
	public static String getXMLString(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	/**
	 * Provide XmlPath instance for XML API response
	 * 
	 * @param xmlResponse
	 * @return xmlPath
	 */
	public static XmlPath rawToXML(Response xmlResponse) {
		String response = xmlResponse.asString();
		// System.out.println("response is: " + response);
		XmlPath xmlPath = new XmlPath(response);
		return xmlPath;
	}

	/**
	 * Read JSON response. Convert to Java string. Then return JSON path with
	 * converted response.
	 * 
	 * @param jsonResponse
	 * @return jsonPath
	 */
	public static JsonPath rawToJson(Response jsonResponse) {
		String response = jsonResponse.asString();
		// System.out.println("response is: " + response);
		JsonPath jsonPath = new JsonPath(response);
		return jsonPath;
	}
	
	public static String getJiraSessionID() {
		// 1. base url
		RestAssured.baseURI = prop.getProperty("JIRA_HOST");

		String payLoad = "{" + "\"username\":\"rdurgam\"," + "\"password\":\"dur1!gam\"" + "}";

		// 2. parameters
		Response res = given().header("Content-Type", "application/json").body(payLoad).
				when().
					post("rest/auth/1/session").
				then().assertThat().statusCode(200).
					extract().response();

		JsonPath js = Utils.rawToJson(res);
		String jSession = js.get("session.value");
		System.out.println("session id:  " + jSession);

		return jSession;		
	}
}

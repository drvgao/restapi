package com.jira.payloads;

public class JiraPayLoads {
	
	//add place pay load
	public static String getCreateIssuePL() {
		String payLoad = "{" +
				"    \"fields\": {" +
				"       \"project\":" +
				"       { " +
				"          \"key\": \"TEST\"" +
				"       }," +
				"       \"summary\": \"REST issue: unable to login\"," +
				"       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\"," +
				"       \"issuetype\": {" +
				"          \"name\": \"Bug\"" +
				"       }" +
				"   }" +
				"}";

		return payLoad;
	}
	
	
	public static String getJiraComments() {
		String sb = "{" +
				"    \"body\": \"This is a comment that only administrators can see.\"," +
				"    \"visibility\": {" +
				"        \"type\": \"role\"," +
				"        \"value\": \"Administrators\"" +
				"    }" +
				"}";
		return sb;
	}
}

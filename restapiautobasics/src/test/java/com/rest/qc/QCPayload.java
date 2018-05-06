package com.rest.qc;

public class QCPayload {
	//add place pay load
		public static String getQCAuthPayLoad() {
			String payLoad = "<alm-authentication>" +
					"	    <user>username</user>" +
					"	    <password>password</password>" +
					"	</alm-authentication>";
			return payLoad;
		}
}

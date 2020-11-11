package com.api.lib;

import java.util.HashMap;
import java.util.Map;

import com.api.reports.ReportUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Api_Utils extends Http_Methods{
	/**
	 * Keyword: validateReponsePayload
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return responseCode
	 */
	public static void validateReponsePayload (String requestPayLoad, String URI) {
		JsonPath jsonPathEvaluator = get(URI).jsonPath();
		String[] reqPayLoad = requestPayLoad.split(",");
		String key = null, value = null;
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < reqPayLoad.length; i++) {
			key = reqPayLoad[i].split(":")[0].trim();
			value = reqPayLoad[i].split(":")[1].trim();
			if (String.valueOf(jsonPathEvaluator.get(key.toLowerCase())).equals(value)) {
				count ++;
			} else {
				map.put(key, value);
			}
		}
		if (count == reqPayLoad.length) {
			ReportUtil.markPassed("Response payload values is verified successfully with request payload");
		} else {
			ReportUtil.markFailed("Unverified: " + map.toString());
		}
	}
	
	/**
	 * Keyword: validateReponseHeader
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return responseCode
	 */
	public static void validateReponseHeader (String URI, Response response) {
		if (response.header("Content-Type").contains("application/json")) {
			ReportUtil.markPassed("Response header is correctly validated");
		} else {
			ReportUtil.markFailed("Reponse header is not validated");
		}
	}
	
}

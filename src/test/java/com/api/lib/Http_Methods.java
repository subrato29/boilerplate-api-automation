package com.api.lib;

import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import com.api.base.DriverScript;
import com.api.reports.ReportUtil;
import com.api.utilities.Constants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Http_Methods extends DriverScript{
	static String TEST_DATA = Constants.TEST_DATA;
	
	/**
	 * Keyword: get
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return responseCode
	 */
	public static Response get (String URI) {
		RequestSpecification request = RestAssured.given();
		Response response = request.get(URI);
		return response;
	}
	
	/**
	 * Keyword: post
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return responseCode
	 */
	@SuppressWarnings("unchecked")
	public static Response post (String request) {
		try {
			String[] arrPost = request.split(",");
			JSONObject json = new JSONObject();
			String key, value;
			for (int i = 0; i < arrPost.length; i++) {
				key = arrPost[i].split(":")[0].trim();
				value = arrPost[i].split(":")[1].trim();
				if (key.toUpperCase().equals("PRICE") || key.toUpperCase().equals("SHIPPING")) {
					json.put(key.toLowerCase(), Integer.parseInt(value));
				} else {
					json.put(key.toLowerCase(), value);
				}
			}
			Response response = 
					given()
						.contentType("application/json")
						.body(json)
					.when()
						.post(baseURI);
			JsonPath jsonPathEvaluator = response.jsonPath(); 
			baseURI_POST = "http://localhost:3030/products/" + jsonPathEvaluator.get("id");
			return response;
		} catch (Throwable t) {
			ReportUtil.markFailed("POST method is failed");
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Keyword: put
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return responseCode
	 */
	@SuppressWarnings("unchecked")
	public static Response put (String URI, String request) {
		try {
			String[] arrPost = request.split(",");
			JSONObject json = new JSONObject();
			String key, value;
			for (int i = 0; i < arrPost.length; i++) {
				key = arrPost[i].split(":")[0].trim();
				value = arrPost[i].split(":")[1].trim();
				if (key.toUpperCase().equals("PRICE") || key.toUpperCase().equals("SHIPPING")) {
					json.put(key.toLowerCase(), Integer.parseInt(value));
				} else {
					json.put(key.toLowerCase(), value);
				}
			}	
			Response response = 
					given()
						.contentType("application/json")
						.body(json)
					.when()
						.put(URI);
			JsonPath jsonPathEvaluator = response.jsonPath(); 
			baseURI_POST = "http://localhost:3030/products/" + jsonPathEvaluator.get("id");
			return response;
		} catch (Throwable t) {
			ReportUtil.markFailed("PUT command is failed");
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Keyword: delete
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return responseCode
	 */
	public static Response delete (String URI) {
		RequestSpecification request = RestAssured.given();
		Response response = request.get(URI);
		response = 
				given()
				.when()
					.delete(URI);
		return response;
	}
	
	/**
	 * Keyword: patch
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return responseCode
	 */
	@SuppressWarnings("unchecked")
	public static Response patch (String URI, String request) {
		String[] arrPost = request.split(",");
		JSONObject json = new JSONObject();
		String key, value;
		for (int i = 0; i < arrPost.length; i++) {
			key = arrPost[i].split(":")[0].trim();
			value = arrPost[i].split(":")[1].trim();
			if (key.toUpperCase().equals("PRICE") || key.toUpperCase().equals("SHIPPING")) {
				json.put(key.toLowerCase(), Integer.parseInt(value));
			} else {
				json.put(key.toLowerCase(), value);
			}
		}
		Response response = 
				given()
					.contentType("application/json")
					.body(json)
				.when()
					.patch(URI);
		return response;
	}
}

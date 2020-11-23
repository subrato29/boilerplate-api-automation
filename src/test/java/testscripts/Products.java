package testscripts;

import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.api.base.DriverScript;
import com.api.reports.ReportUtil;
import com.api.utilities.Constants;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Products extends DriverScript{
	
	String TEST_DATA = Constants.TEST_DATA;
	
	@Test
	public void TC001 () {
		String tcId = "TC001";
		if(isTestCaseRunnable(tcId)) {
			RequestSpecification request = RestAssured.given();
			Response response = request.get(baseURI);
			
			String expectedStatusCode = xls.getCellData(TEST_DATA, "ResponseCode", rowNum);
			String actualStatusCode = String.valueOf(response.getStatusCode());
			
			if(expectedStatusCode.equals(actualStatusCode)) {
				ReportUtil.markPassed("Status code is correctly verified, which is: " + expectedStatusCode);
			} else {
				ReportUtil.markFailed("Status code is not correctly verified, "
						+ "where actual is: " + actualStatusCode 
						+ " and expected is: " + expectedStatusCode);
			}
		}			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TC002 () {
		String tcId = "TC002";
		if (isTestCaseRunnable(tcId)) {
			String[] arrPost = xls.getCellData(TEST_DATA, "RequestBody", rowNum).split(",");
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
			
			String expectedResponseCode = xls.getCellData(TEST_DATA, "ResponseCode", rowNum);
				
			Response response = 
					given()
						.contentType("application/json")
						.body(json)
					.when()
						.post(baseURI);
			
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == Integer.parseInt(expectedResponseCode)) {
				JsonPath jsonPathEvaluator = response.jsonPath();
				baseURI_POST = "http://localhost:3030/products/" + jsonPathEvaluator.get("id"); 
				ReportUtil.markPassed("Record added successfully which is: "
									+ response.getBody().asString());
			} else {
				ReportUtil.markFailed("POST is not successful where actual response code: " + actualResponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TC003() {
		String tcId = "TC003";
		if (isTestCaseRunnable (tcId)) {
			String[] arrPost = xls.getCellData(TEST_DATA, "RequestBody", rowNum).split(",");
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
			String expectedResponseCode = xls.getCellData(TEST_DATA, "ResponseCode", rowNum);
			
			Response response = 
					given()
						.contentType("application/json")
						.body(json)
					.when()
						.put(baseURI_POST);
			int actualReponseCode = response.getStatusCode();
			
			if (actualReponseCode == Integer.parseInt(expectedResponseCode)) {
				JsonPath jsonPathEvaluator = response.jsonPath();			    
				ReportUtil.markPassed("Record id: "+ jsonPathEvaluator.get("id") +" is updated successfully by put"
										+ " where updated name: " + jsonPathEvaluator.get("name")
										+ " and updated price: " + jsonPathEvaluator.get("price"));
			} else {
				ReportUtil.markFailed("PUT is not successful where cctual response code: " + actualReponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}
			
		}
	}
	
	@Test
	public void TC004() {
		String tcid = "TC004";
		if (isTestCaseRunnable(tcid)) {
			String expectedResponseCode = xls.getCellData(TEST_DATA, "ResponseCode", rowNum);
			
			RequestSpecification request = RestAssured.given();
			Response response = request.get(baseURI_POST);
			JsonPath jsonPathEvaluator = response.jsonPath();
			String recordId = String.valueOf(jsonPathEvaluator.get("id"));
			
			response = 
					given()
					.when()
						.delete(baseURI_POST);
			
			int actualResponseCode = response.getStatusCode();
			if (actualResponseCode == 200) {
				response = 
						given()
						.when()
							.delete(baseURI_POST);
				actualResponseCode = response.getStatusCode();
				if (actualResponseCode == Integer.parseInt(expectedResponseCode)) {
					ReportUtil.markPassed("DELETE of record is successful where record id: " + recordId);
				} else {
					ReportUtil.markFailed("DELETE is not successful where actual response code: " + actualResponseCode
							+ " where expected reponse code: " + expectedResponseCode);
				}
			} else {
				ReportUtil.markFailed("DELETE is not successful becuase the response code: " + actualResponseCode);
			}
		}
	}
	
}

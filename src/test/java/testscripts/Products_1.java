package testscripts;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.api.lib.Api_Utils;
import com.api.lib.Http_Methods;
import com.api.reports.ReportUtil;
import com.api.utilities.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Products_1 extends Http_Methods{
	
	String TEST_DATA = Constants.TEST_DATA;
	
	@Test
	public void TC001 () {
		String tcId = "TC001";
		if(isTestCaseRunnable(tcId)) {
			Response response = get(baseURI);
			int actualStatusCode = response.getStatusCode();
			int expectedStatusCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			
			if(expectedStatusCode == actualStatusCode) {
				ReportUtil.markPassed("Status code is correctly verified, which is: " + expectedStatusCode + " having response time " + Api_Utils.getResponseTime(response));
			} else {
				ReportUtil.markFailed("Status code is not correctly verified, "
						+ "where actual is: " + actualStatusCode 
						+ " and expected is: " + expectedStatusCode);
			}
		}			
	}
	
	
	@Test
	public void TC002 () {
		String tcId = "TC002";
		if (isTestCaseRunnable(tcId)) {
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			String request = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			Response response = post(request);
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == expectedResponseCode) {
				ReportUtil.markPassed("Record added successfully which is: "
									+ response.getBody().asString());
			} else {
				ReportUtil.markFailed("POST is not successful where actual response code: " + actualResponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}
			String responseBody = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			Api_Utils.validateReponsePayload(responseBody, baseURI_POST);
			Api_Utils.validateReponseHeader(baseURI_POST, response);
		}
	}
	
	
	@Test
	public void TC003() {
		String tcId = "TC003";
		if (isTestCaseRunnable (tcId)) {
			String request = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			Response response = put(baseURI_POST, request);
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualReponseCode = response.getStatusCode();
			
			if (actualReponseCode == expectedResponseCode) {
				JsonPath jsonPathEvaluator = response.jsonPath();			    
				ReportUtil.markPassed("Record id: "+ jsonPathEvaluator.get("id") +" is updated successfully by put"
										+ " where updated name: " + jsonPathEvaluator.get("name")
										+ " and updated price: " + jsonPathEvaluator.get("price"));
			} else {
				ReportUtil.markFailed("PUT is not successful where actual response code: " + actualReponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}
			
		}
	}
	
	@Test
	public void TC004 () {
		String tcid = "TC004";
		if (isTestCaseRunnable(tcid)) {
			String baseURI = xls.getCellData(TEST_DATA, "URI", rowNum);
			String request = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			Response response = patch(baseURI, request);
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualReponseCode = response.getStatusCode();
			
			if (actualReponseCode == expectedResponseCode) {
				JsonPath jsonPathEvaluator = response.jsonPath();			    
				ReportUtil.markPassed("Record id: "+ jsonPathEvaluator.get("id") + response.body().asString());
			} else {
				ReportUtil.markFailed("PATCH is not successful where actual response code: " + actualReponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}
		}
	}
	
	@Test
	public void TC005() {
		String tcid = "TC005";
		if (isTestCaseRunnable(tcid)) {
			JsonPath jsonPathEvaluator = get(baseURI_POST).jsonPath();
			String recordId = String.valueOf(jsonPathEvaluator.get("id"));
			
			Response response = delete(baseURI_POST);
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == 200) {
				actualResponseCode = delete(baseURI_POST).getStatusCode();
				if (actualResponseCode == expectedResponseCode) {
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
	
	
	@Test
	public void TC006 () {
		String tcId = "TC006";
		if (isTestCaseRunnable(tcId)) {
			Response response = get(baseURI);
			int actualResponseCode = response.getStatusCode();
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			if (actualResponseCode == expectedResponseCode) {
				JsonPath jsonPathEval = response.jsonPath();
				String ids = jsonPathEval.get("categories.id").toString();
				String names = jsonPathEval.get("categories.name").toString();
				ReportUtil.markPassed("Categories ids are: " + ids + " and names are " + names);
			} else {
				ReportUtil.markFailed("Actual response code: " + actualResponseCode + " while expected response code: " + expectedResponseCode);
			}
		}
	}
	
	@Test
	public void TC007 () {
		String tcId = "TC007";
		if (isTestCaseRunnable(tcId)) {
			Response response = get(baseURI);
			int actualResponseCode = response.getStatusCode();
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			if (actualResponseCode == expectedResponseCode) {
				JsonPath jsonPathEval = response.jsonPath();
				String ids = jsonPathEval.get("data.categories.id").toString();
				String names = jsonPathEval.get("data.categories.name").toString();
				ReportUtil.markPassed("Categories ids are: " + ids + " and names are " + names);
			} else {
				ReportUtil.markFailed("Actual response code: " + actualResponseCode + " while expected response code: " + expectedResponseCode);
			}
		}
	}
	
	@Test
	public void TC008 () {
		String tcId = "TC008";
		if (isTestCaseRunnable(tcId)) {
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			String request = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			Response response = post(request);
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == expectedResponseCode) {
				ReportUtil.markPassed("POST is not successful as expected where"
						+ " response code is " + actualResponseCode);
			} else {
				ReportUtil.markFailed("Record added successfully which is: "
						+ response.getBody().asString());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TC009 () {
		String tcId = "TC009";
		if (isTestCaseRunnable(tcId)) {
			String request = xls.getCellData(TEST_DATA, "RequestBody", rowNum);
			String[] arrPost = request.split(",");
			JSONObject json = new JSONObject();
			String key, value;
			for (int i = 0; i < arrPost.length; i++) {
				key = arrPost[i].split(":")[0].trim();
				value = arrPost[i].split(":")[1].trim();
				if (key.toUpperCase().equals("SHIPPING")) {
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
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == expectedResponseCode) {
				ReportUtil.markPassed("POST is not successful as expected where"
						+ " response code is " + actualResponseCode);
			} else {
				ReportUtil.markFailed("Record added successfully which is: "
						+ response.getBody().asString());
			}
		}
	}
	
}

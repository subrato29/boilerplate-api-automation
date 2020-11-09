package testscripts;

import org.testng.annotations.Test;
import com.api.base.DriverScript;
import com.api.lib.API_Util;
import com.api.reports.ReportUtil;
import com.api.utilities.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Products_1 extends DriverScript{
	
	String TEST_DATA = Constants.TEST_DATA;
	
	@Test
	public void TC001 () {
		String tcId = "TC001";
		if(isTestCaseRunnable(tcId)) {
			int actualStatusCode = API_Util.get(baseURI).getStatusCode();
			int expectedStatusCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			
			if(expectedStatusCode == actualStatusCode) {
				ReportUtil.markPassed("Status code is correctly verified, which is: " + expectedStatusCode);
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
			Response response = API_Util.post();
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == expectedResponseCode) {
				ReportUtil.markPassed("Record added successfully which is: "
									+ response.getBody().asString());
			} else {
				ReportUtil.markFailed("POST is not successful where actual response code: " + actualResponseCode
									+ " where expected reponse code: " + expectedResponseCode);
			}			
		}
	}
	
	
	@Test
	public void TC003() {
		String tcId = "TC003";
		if (isTestCaseRunnable (tcId)) {
			Response response = API_Util.put(baseURI_POST);
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualReponseCode = response.getStatusCode();
			
			if (actualReponseCode == expectedResponseCode) {
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
			JsonPath jsonPathEvaluator = API_Util.get(baseURI_POST).jsonPath();
			String recordId = String.valueOf(jsonPathEvaluator.get("id"));
			
			Response response = API_Util.delete(baseURI_POST);
			
			int expectedResponseCode = Integer.parseInt(xls.getCellData(TEST_DATA, "ResponseCode", rowNum));
			int actualResponseCode = response.getStatusCode();
			
			if (actualResponseCode == 200) {
				actualResponseCode = API_Util.delete(baseURI_POST).getStatusCode();
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
	
}

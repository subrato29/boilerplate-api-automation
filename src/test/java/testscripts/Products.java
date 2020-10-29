package testscripts;

import org.testng.annotations.Test;

import com.api.base.DriverScript;
import com.api.reports.ReportUtil;
import com.api.utilities.Constants;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Products extends DriverScript{
	
	@Test
	public void TC001() {
		if(isTestCaseRunnable("TC001")) {
			String baseURI = xls.getCellData(Constants.TEST_DATA, "URI", rowNum);
			RequestSpecification request = RestAssured.given();
			Response response = request.get(baseURI);
			
			String expectedStatusCode = xls.getCellData(Constants.TEST_DATA, "StatusCode", rowNum);
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
	
}

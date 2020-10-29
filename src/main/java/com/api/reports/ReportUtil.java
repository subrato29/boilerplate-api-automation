package com.api.reports;
import com.api.base.DriverScript;
import com.api.reports.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportUtil extends ExtentManager{
	 static ExtentReports report = ExtentManager.getInstance();
	 public static ExtentTest test;
	 static int countOfCallingEndTest = 0;
	 static int countOfIntializingTest = 0;
	 static int countOfCallingStartTest=0;
	 
	 public static String reportStepFailed(String comment) {
			String reportStep = null;
			try{
				reportStep = "<font color='red' face='Cambria'><b>" + comment +"</b></font>";
			}catch(Throwable t){
				reportStep = null;
			}
			return reportStep;
		}
			
		
	public static String reportStepPassed(String comment){
		String reportStep = null;
		try{	
			reportStep = "<font color='black' face='Cambria'><b>" + comment +"</b></font>";
		}catch(Throwable t){
			reportStep = null;
		}
		return reportStep;
	}
			
		
	public static String reportStepInfo(String comment){
		try{
			String reportStep = "<font color='blue' face='Cambria'><i>" + comment +"</i></font>";								
			return reportStep;
		}catch(Throwable t){
			return null;
		}
	}
		
	
	public static String reportStepWarning(String comment){
		String reportStep = null;
		try{
			reportStep = "<font color='orange' face='Cambria'><b>" + comment +"</b></font>";
		}catch(Throwable t){
			reportStep = null;
		}
		return reportStep;
	}
		
	
	public static String reportStepSkip(String comment){
		try{
			String reportStep = "<font color='sky blue' face='Cambria'><b>" + comment +"</b></font>";								
			return reportStep;
		}catch(Throwable t){
			return null;
		}
	}
				
		
	public static void markPassed(String comment){
		if(DriverScript.continueRun) {
			if(test == null){
				test = report.startTest(DriverScript.testCaseName);
			}
			try{
				test.log(LogStatus.PASS, reportStepPassed(comment));
			}finally{
				if(report != null){
					report.endTest(test);
	        		report.flush();
				}
			}
		}
	}
	
	public static void markFailed(String comment){
		if(DriverScript.continueRun) {
			if(test == null){
				test = report.startTest(DriverScript.testCaseName);
			}
			try{
				test.log(LogStatus.FAIL, reportStepFailed(comment));
			}finally{
				if(report != null){
					report.endTest(test);
	        		report.flush();
				}
			}
		}
	}
	
	
	public static void markInfo(String comment){
		if(test == null){
			test = report.startTest(DriverScript.testCaseName);
		}
		try{
			if(comment.toUpperCase().equals("START")) {
				comment = "Starting the test";
				test.log(LogStatus.INFO, reportStepInfo(comment));
				countOfCallingStartTest++;
			}else if(comment.toUpperCase().equals("END")) {
				comment = "Ending the test";
				test.log(LogStatus.INFO, reportStepInfo(comment));
				test = null;
				countOfCallingEndTest++;
			}else {
				test.log(LogStatus.INFO, reportStepInfo(comment));
			}
		}finally{
			if(report != null){
				report.endTest(test);
        		report.flush();
			}
		}
	}
	
	
	public static void markWarning(String comment){
		if(DriverScript.continueRun) {
			if(test == null){
				test = report.startTest(DriverScript.testCaseName);
			}
			try{
				test.log(LogStatus.WARNING, reportStepWarning(comment));
			}finally{
				if(report != null){
					report.endTest(test);
	        		report.flush();
				}
			}
		}
	}
	
	
	public static void markSkip(String comment){
		if(test == null){
			test = report.startTest(DriverScript.testCaseName);
		}
		try{
			test.log(LogStatus.SKIP, reportStepSkip(comment));
		}finally{
			if(report != null){
				report.endTest(test);
        		report.flush();
			}
		}
	}
	
	
	public static void markStart(){
		if(test == null){
			test = report.startTest(DriverScript.testCaseName);
		}
		try{
			test=null;
		}finally{
			if(report != null){
				report.endTest(test);
        		report.flush();
			}
		}
	}
				
}

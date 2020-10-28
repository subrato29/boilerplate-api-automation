package com.runner.testng;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class DynamicTestNg {
	static String testNgXml = "testng.xml";
	public static void main(String[] args){
		String testName = null;
		try {
			List<XmlClass> classList = new ArrayList<XmlClass>();
	        XmlSuite suite = new XmlSuite();
	        XmlTest test = new XmlTest(suite);
	        
			suite.setName("Suite");
	        List<String> files = new ArrayList<String>();
	        files.add(System.getProperty("user.dir")+File.separator+testNgXml);
	        
	        File file = new File(System.getProperty("user.dir") + File.separator 
	    			+ "src" + File.separator + "test" + File.separator +"java"+ File.separator +"testscripts");
	        String[] fileList = file.list();
	        for(String name:fileList){
	        	testName = name.split(".java")[0];
	    		classList.add(new XmlClass("testscripts."+testName));
		        test.setName("Test");
		        test.setThreadCount(5);
		        test.setXmlClasses(classList); 
		        FileWriter writer = new FileWriter(new File(testNgXml));
		        writer.write(suite.toXml());
		        writer.flush();
		        writer.close();
	        }
		}catch(Throwable t) {
			t.printStackTrace();
		}
	}

}

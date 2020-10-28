package com.runner.testng;

import java.io.File;
import java.util.List;

import org.testng.TestNG;

import com.beust.jcommander.internal.Lists;

public class TestNgRunner {
	public static void main(String[] args) {
		DynamicTestNg.main(args);
		TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add(System.getProperty("user.dir") + File.separator + DynamicTestNg.testNgXml);
        testng.setTestSuites(suites);
        testng.run();
	}
}

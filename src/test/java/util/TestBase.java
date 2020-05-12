package util;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestBase {

	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	private static OptionsManager optionsManager = new OptionsManager();
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public static String myURL = System.getProperty("instance-url","");
	public static String gridUrl = System.getProperty("grid-url", "https://selenium.k8.seamfix.com/wd/hub");
	public static String toAddress;
	String devices;
	static String[] udid;
	public int deviceNo;
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	
}

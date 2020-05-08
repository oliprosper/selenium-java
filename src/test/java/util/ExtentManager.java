package util;

import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
private static ExtentReports extent;
    
	@Parameters
    public static ExtentReports getInstance(String groupReport) {
    	if (extent == null)
    		createInstance("/"+groupReport);
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Report");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }

}

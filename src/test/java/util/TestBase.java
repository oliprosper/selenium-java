package util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.testinium.deviceinformation.DeviceInfo;
import com.testinium.deviceinformation.DeviceInfoImpl;
import com.testinium.deviceinformation.device.DeviceType;
import com.testinium.deviceinformation.model.Device;

public class TestBase {

	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	private static OptionsManager optionsManager = new OptionsManager();
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public static String myURL = System.getProperty("instance-url","http://");
	public static String gridUrl = System.getProperty("grid-url", "https://IP/wd/hub");
	public static String toAddress;
	String devices;
	static String[] udid;
	public int deviceNo;
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	
	@BeforeSuite
	@Parameters({ "groupReport", "environment", "device" })
	public void setUp(String groupReport, String environment, String device) throws Exception {

		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
		reports = new ExtentReports();
		reports.setSystemInfo("STAGING", myURL);
		if (environment.equalsIgnoreCase("desktop")) {
			reports.setSystemInfo("Test Device", device);
		} else if (environment.equalsIgnoreCase("mobile")) {
			reports.setSystemInfo("Test Device", device);
		} else {
			reports.setSystemInfo("Test Device", device);
			try {
				devices = TestUtils.executeAdbCommand("adb devices");
				devices = devices.replaceAll("List of devices attached", " ");
				devices = devices.replaceAll("device", " ").trim();
				udid = devices.split(" ");
			} catch (IOException e) {
				System.out.println("No devices found: " + e.toString());
			}
			DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.ANDROID);
			Device deviceName = deviceInfo.getFirstDevice();
			reports.setSystemInfo("Test Device", deviceName.getDeviceProductName());
		}
		reports.attachReporter(htmlReporter);
	}
	
	@Parameters({ "server", "environment", "device", "myBrowser", "deviceNo" })
	@BeforeClass
	public void beforeClass(ITestContext context, String server, String environment, String device, String myBrowser, int deviceNo)
			throws Exception {
		this.deviceNo = deviceNo;
		ExtentTest parent = reports.createTest(context.getName());
		parentTest.set(parent);

		if (server.equalsIgnoreCase("remoteJenkins")) {

			if (environment.equalsIgnoreCase("desktop")) {
				if (myBrowser.equalsIgnoreCase("chrome")) {
					DesiredCapabilities capability = DesiredCapabilities.chrome();
					capability.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", context.getName());
					capability.setPlatform(Platform.ANY);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
				} else {
					DesiredCapabilities capability = DesiredCapabilities.firefox();
					capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
					capability.setCapability(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE,
							optionsManager.getFirefoxOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", context.getName());
					capability.setPlatform(Platform.ANY);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
				}
				getDriver().manage().window().maximize();
			} else {
				if (myBrowser.equalsIgnoreCase("chrome")) {

					DesiredCapabilities capability = DesiredCapabilities.chrome();
					capability.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeEmulatorOptions(device));
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", context.getName());
					capability.setPlatform(Platform.ANY);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
				} else {
					DesiredCapabilities capability = DesiredCapabilities.firefox();
					capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
					capability.setCapability(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE,
							optionsManager.getFirefoxOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", context.getName());
					capability.setPlatform(Platform.ANY);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
					getDriver().manage().window().setSize(TestUtils.setDimension(device));
				}

			}
			getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			getDriver().get(myURL);

		} else if (server.equalsIgnoreCase("local")) {
			if (environment.equalsIgnoreCase("desktop")) {
				if (myBrowser.equalsIgnoreCase("chrome")) {
					File classpathRoot = new File(System.getProperty("user.dir"));
					File chromeDriver = new File(classpathRoot, "chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
					driver.set(new ChromeDriver(optionsManager.getChromeOptions()));
					getDriver().manage().window().maximize();
					getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					getDriver().get(myURL);

				} else if (myBrowser.equalsIgnoreCase("firefox")) {

					File classpathRoot = new File(System.getProperty("user.dir"));
					File firefoxDriver = new File(classpathRoot, "geckodriver.exe");
					System.setProperty("webdriver.gecko.driver", firefoxDriver.getAbsolutePath());
					driver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
					getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					getDriver().get(myURL);
				}
			} else if (environment.equalsIgnoreCase("mobile")) {
				if (myBrowser.equalsIgnoreCase("chrome")) {
					File classpathRoot = new File(System.getProperty("user.dir"));
					File chromeDriver = new File(classpathRoot, "chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
					driver.set(new ChromeDriver(optionsManager.getChromeEmulatorOptions(device)));
					getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					getDriver().get(myURL);

				} else if (myBrowser.equalsIgnoreCase("firefox")) {

					File classpathRoot = new File(System.getProperty("user.dir"));
					File firefoxDriver = new File(classpathRoot, "geckodriver.exe");
					System.setProperty("webdriver.gecko.driver", firefoxDriver.getAbsolutePath());
					driver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
					getDriver().manage().window().setSize(TestUtils.setDimension(device));
					getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					getDriver().get(myURL);
				}
			} else if (environment.equalsIgnoreCase("Physical device")) {
				try {
					deviceNo = deviceNo - 1;
					while (deviceNo >= udid.length) {
						deviceNo = deviceNo - 1;
					}
					File classpathRoot = new File(System.getProperty("user.dir"));
					File chromeDriver = new File(classpathRoot, "chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
					driver.set(new ChromeDriver(OptionsManager.getChromeOptionsAndroid()));
					getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					getDriver().get(myURL);
				} catch (Exception e) {
					System.out.println("android does not maximize windows");
				}
			}

		}
	}
	
	@BeforeMethod(description = "fetch test cases name and browser")
	public void register(Method method) {
		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().getModel().setDescription(TestUtils.CheckBrowser());
	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		for (String group : result.getMethod().getGroups())
			testInfo.get().assignCategory(group);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = TestUtils.addScreenshot();
			testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
			testInfo.get().fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP)
			testInfo.get().skip(result.getThrowable());
		else
			testInfo.get().pass(result.getName() + " Test passed");
		reports.flush();
	}
	
	@AfterClass
	public void afterClass() {
		getDriver().quit();
	}
	
	@Parameters({ "toMails", "groupReport" })
	@AfterSuite(description = "clean up report after test suite and send report to recipients")
	public void cleanup(String toMails, String groupReport, ITestContext context) {
//		String suiteFileName = context.getCurrentXmlTest().getSuite().getFileName();
		toAddress = System.getProperty("email_list", toMails);
		SendMail.ComposeGmail("Report <test.report@gmail.com>", toAddress, groupReport);
	}
	
}

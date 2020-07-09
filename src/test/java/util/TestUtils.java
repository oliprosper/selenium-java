package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.testinium.deviceinformation.helper.ProcessHelper;

/**
 * @author soli
 *
 */
public class TestUtils extends TestBase{
	
	public static void clickPseudoElement(String locator) {
		WebElement element = getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]"));
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);

	}

	public static String getPseudoBeforeElementStyle(String locator, String syle_property) {
		WebElement switchLabel = getDriver().findElement(
				By.xpath("//*[contains(@value,'" + locator + "') or contains(@name,'" + locator + "') or contains(@id,'"
						+ locator + "') or contains(text(),'" + locator + "') or contains(@for,'" + locator + "') ]"));
		return ((JavascriptExecutor) getDriver()).executeScript(
				"return window.getComputedStyle(arguments[0], ':before').getPropertyValue('" + syle_property + "');",
				switchLabel).toString();
	}

	public static void dragAndDrop(String sourceLocator, String destinationLocator) throws Exception {
		WebElement sourceElement = getDriver().findElement(By.xpath("//*[starts-with(@name,'" + sourceLocator
				+ "') or starts-with(@id,'" + sourceLocator + "') or starts-with(@id,'" + sourceLocator
				+ "') or starts-with(text(),'" + sourceLocator + "') or starts-with(@for,'" + sourceLocator + "') ]"));
		WebElement destinationElement = getDriver().findElement(
				By.xpath("//*[starts-with(@name,'" + destinationLocator + "') or starts-with(@id,'" + destinationLocator
						+ "') or starts-with(@id,'" + destinationLocator + "') or starts-with(text(),'"
						+ destinationLocator + "') or starts-with(@for,'" + destinationLocator + "') ]"));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
				+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
				+ "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
				+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
				+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
				+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
				+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
				+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
				+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
				+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
				+ "var dropEvent = createEvent('drop');\n"
				+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
				+ "var dragEndEvent = createEvent('dragend');\n"
				+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
				+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
				+ "simulateHTML5DragAndDrop(source,destination);", sourceElement, destinationElement);
		Thread.sleep(1500);

	}

	public static String getPseudoAfterElementStyle(String locator, String syle_property) {
		WebElement switchLabel = getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]"));
		return ((JavascriptExecutor) getDriver()).executeScript(
				"return window.getComputedStyle(arguments[0], ':after').getPropertyValue('" + syle_property + "');",
				switchLabel).toString();
	}

	public static void scrollToPageEnd() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollToPageTop() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void waitForElementPresent(String locator) {
		WebElement element;
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]")));
	}

	public static void scrollToElement(String locator) {
		WebElement element = getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]"));
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void inteligentAssertElementPresent(String locator) {
		Assert.assertTrue(
				getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator + "') or starts-with(@id,'"
						+ locator + "') or starts-with(@id,'" + locator + "') or starts-with(text(),'" + locator
						+ "') or starts-with(@for,'" + locator + "') ]")).isDisplayed());
	}

	public static void inteligentAssertElementText(String locator, String elementText) {
		Assert.assertEquals(getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]")).getText(),
				elementText);
	}

	public static void assertElementPresent(String locator) {
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name")) {
			Assert.assertTrue(getDriver().findElement(By.name(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}

		selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id")) {
			Assert.assertTrue(getDriver().findElement(By.id(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}

		if (selector.contains("xpath")) {
			Assert.assertTrue(
					getDriver().findElement(By.xpath(StringUtils.substringAfter(locator, "="))).isDisplayed());
		}

		if (selector.contains("text")) {
			Assert.assertTrue(getDriver()
					.findElement(By.xpath("//*[contains(text(),'" + StringUtils.substringAfter(locator, "=") + "') ]"))
					.isDisplayed());
		}
	}

	public static void assertElementText(String locator, String elementText) {
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name")) {
			Assert.assertEquals(getDriver().findElement(By.name(StringUtils.substringAfter(locator, "="))).getText(),
					elementText);
		}

		selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id")) {
			Assert.assertEquals(getDriver().findElement(By.id(StringUtils.substringAfter(locator, "="))).getText(),
					elementText);
		}

		if (selector.contains("xpath")) {
			Assert.assertEquals(getDriver().findElement(By.xpath(StringUtils.substringAfter(locator, "="))).getText(),
					elementText);
		}

		if (selector.contains("text")) {
			Assert.assertEquals(getDriver()
					.findElement(By.xpath("//*[contains(text(),'" + StringUtils.substringAfter(locator, "=") + "') ]"))
					.getText(), elementText);
		}
	}
	
	public static void softAssertElementText(String locator, String expectedText) {
		StringBuffer verificationErrors = new StringBuffer();
		String selector = StringUtils.substringBefore(locator, "=");
		String actualText = null;
		switch (selector) {
		case "name":
		case "id":
		case "xpath":
		case "text":
		default:
			actualText = getDriver().findElement(By.name(StringUtils.substringAfter(locator, "="))).getText();
			break;
		}
		try {
			Assert.assertEquals(actualText, expectedText);
			testInfo.get().log(Status.INFO, expectedText + " <b>Text Match</b>");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(expectedText + " <b>Text Mismatch");
			testInfo.get().error(verificationErrorString);
		}

	}

	public static void InteligentClick(String locator) {
		getDriver()
				.findElement(By.xpath("//*[starts-with(@name,'" + locator + "') or starts-with(@id,'" + locator
						+ "') or starts-with(@value,'" + locator + "') or starts-with(text(),'" + locator
						+ "') or starts-with(@href,'" + locator + "')or starts-with(@for,'" + locator + "') ]"))
				.click();

	}

	public static void InteligentType(String locator, String textToType) {

		getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(@value,'" + locator + "') or starts-with(text(),'" + locator
				+ "') or starts-with(@for,'" + locator + "') ]")).sendKeys(textToType);
	}

	public static void PesudoEelementClick(String locator) {
		Actions build = new Actions(getDriver());
		build.moveToElement((getDriver().findElement(
				By.xpath("//*[contains(@value,'" + locator + "') or contains(@name,'" + locator + "') or contains(@id,'"
						+ locator + "') or contains(text(),'" + locator + "') or contains(@for,'" + locator + "') ]"))))
				.click().build().perform();
	}

	public static void PesudoEelementType(String locator, String text) {
		Actions build = new Actions(getDriver());
		build.moveToElement((getDriver().findElement(
				By.xpath("//*[contains(@value,'" + locator + "') or contains(@name,'" + locator + "') or contains(@id,'"
						+ locator + "') or contains(text(),'" + locator + "') or contains(@for,'" + locator + "') ]"))))
				.sendKeys(text).build().perform();
	}

	public static void Type(String locator, String text) {
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name")) {
			getDriver().findElement(By.name(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}

		selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id")) {
			getDriver().findElement(By.id(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}

		if (selector.contains("xpath")) {
			getDriver().findElement(By.xpath(StringUtils.substringAfter(locator, "="))).sendKeys(text);
		}

		if (selector.contains("text")) {
			getDriver()
					.findElement(By.xpath("//*[contains(text(),'" + StringUtils.substringAfter(locator, "=") + "') ]"))
					.sendKeys(text);
			;
		}
	}

	public static void Type(By locator, String text) {
		getDriver().findElement(locator).clear();
		getDriver().findElement(locator).sendKeys(text);
	}

	public static void Click(By locator) {
		getDriver().findElement(locator).click();
	}

	public static String GetText(By locator) {
		return getDriver().findElement(locator).getText();
	}

	public static void SelectDropDown(String locator, String value) {
		WebElement element = getDriver().findElement(By.xpath("//*[starts-with(@name,'" + locator
				+ "') or starts-with(@id,'" + locator + "') or starts-with(@id,'" + locator
				+ "') or starts-with(text(),'" + locator + "') or starts-with(@for,'" + locator + "') ]"));
		Select selectMenu = new Select(element);
		selectMenu.selectByValue(value);
	}

	public static void Click(String locator) {
		String selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("name")) {
			getDriver().findElement(By.name(StringUtils.substringAfter(locator, "="))).click();
		}

		selector = StringUtils.substringBefore(locator, "=");
		if (selector.contains("id")) {
			getDriver().findElement(By.id(StringUtils.substringAfter(locator, "="))).click();
		}

		if (selector.contains("xpath")) {
			getDriver().findElement(By.xpath(StringUtils.substringAfter(locator, "="))).click();
		}

		if (selector.contains("text")) {
			getDriver()
					.findElement(By.xpath("//*[contains(text(),'" + StringUtils.substringAfter(locator, "=") + "') ]"))
					.click();
		}
	}
	
	/**
	 * @return number
	 * @description to generate a 11 digit number.
	 */
	public static String generatePhoneNumber() {

		long y = (long) (Math.random() * 100000 + 0333330000L);
		String Surfix = "080";
		String num = Long.toString(y);
		String number = Surfix + num;
		return number;

	}
	
	public static Integer convertToInt(String value) throws InterruptedException {
		Integer result = null;
		String convertedString = value.replaceAll("[^0-9]", "");
		if (validateParams(convertedString)) {
			try {
				return result = Integer.parseInt(convertedString);
			} catch (NumberFormatException e) {
				testInfo.get().error("convertToInt  Error converting to integer ");
				testInfo.get().error(e);
			}
		}
		return result;

	}

	public static Long convertToLong(String value) {
		Long result = null;
		String convertedString = value.replaceAll("[^0-9]", "");
		if (validateParams(convertedString)) {
			try {
				return result = Long.parseLong(convertedString);
			} catch (NumberFormatException e) {
				testInfo.get().error("ConvertToLong  Error converting to long");
				testInfo.get().error(e);
			}
		}
		return result;
	}

	public static boolean validateParams(Object... params) {

		for (Object param : params) {
			if (param == null) {
				return false;
			}

			if (param instanceof String && ((String) param).isEmpty()) {
				return false;
			}

			if (param instanceof Collection<?> && ((Collection<?>) param).isEmpty()) {
				return false;
			}

			if (param instanceof Long && ((Long) param).compareTo(0L) == 0) {
				return false;
			}
			if (param instanceof Double && ((Double) param).compareTo(0D) == 0) {
				return false;
			}

			if (param instanceof Integer && ((Integer) param).compareTo(0) == 0) {
				return false;
			}

		}

		return true;
	}

	/**
	 * @param value
	 * @return string value.
	 * @throws InterruptedException
	 * @description to convert string value to int value for calculations
	 */
	public static String executeAdbCommand(String command) throws IOException {
		Process process = null;
		StringBuilder builder = new StringBuilder();
		String commandString;
		commandString = String.format("%s", command);
		try {
			process = ProcessHelper.runTimeExec(commandString);
		} catch (IOException e) {
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
			builder.append(line);
		}
		return builder.toString();
	}

	/**
	 * @param String
	 *            value.
	 * @return Dimension.
	 * @throws InterruptedException
	 * @description to set browser view dimension to specified mobile screen
	 *              resolution.
	 */
	public static Dimension setDimension(String device) {
		Dimension dimension = null;
		if (device.equalsIgnoreCase("iPhone 8")) {
			dimension = new Dimension(375, 667);

		} else if (device.equalsIgnoreCase("iPhone X")) {
			dimension = new Dimension(375, 812);

		} else if (device.equalsIgnoreCase("iPhone 8 Plus")) {
			dimension = new Dimension(414, 736);

		} else if (device.equalsIgnoreCase("iPad Mini")) {
			dimension = new Dimension(768, 1024);

		} else if (device.equalsIgnoreCase("Galaxy S5")) {
			dimension = new Dimension(360, 640);

		} else if (device.equalsIgnoreCase("Galaxy S9")) {
			dimension = new Dimension(360, 740);

		} else if (device.equalsIgnoreCase("Nexus 5X")) {
			dimension = new Dimension(412, 732);

		} else if (device.equalsIgnoreCase("Galaxy Tab 10")) {
			dimension = new Dimension(800, 1280);

		} else {
			System.out.println(device + " not found");
		}
		return dimension;
	}

	/**
	 * @return Browser name and version.
	 * @description Get Browser name and version.
	 */
	public static String CheckBrowser() {
		Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
		String os = caps.getBrowserName() + " " + caps.getVersion();
		return os;
	}

	/**
	 * @return Base64string image.
	 * @description To take screenshot of a current screen view.
	 */
	public static String addScreenshot() {

		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File scrFile = ts.getScreenshotAs(OutputType.FILE);

		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}
	
	public static void title(String phrase) {
		String word = phrase;
        Markup w = MarkupHelper.createLabel(word, ExtentColor.BLUE);
        testInfo.get().info(w);
	}
	
	/*
	* param by - locator of the element where you enter the path of the file
	* param fileName – name of the file to be uploaded
	*/
	public static void uploadFile(By by, String fileName) {
		try {
			WebElement element = getDriver().findElement(by);
			LocalFileDetector detector = new LocalFileDetector();
			String path = new File(System.getProperty("user.dir") + "/files").getAbsolutePath() + "/" + fileName;
			File file = detector.getLocalFile(path);
			((RemoteWebElement) element).setFileDetector(detector);
			element.sendKeys(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("file upload not successful");
		}
	}

}

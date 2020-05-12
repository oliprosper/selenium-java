package util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

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
	

}

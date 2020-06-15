package core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class CoreFunctions {

	public static Logger log = Logger.getLogger(CoreFunctions.class);
	private AppiumDriver<WebElement> appiumDriver;

	public CoreFunctions(AppiumDriver<WebElement> appiumDriver) {
		this.appiumDriver = appiumDriver;
	}

	public static void click(WebElement element, String elementName) {

		log.info("Clicking on " + elementName);
		ExtentCucumberAdapter.addTestStepLog("Clicking on " + elementName);
		try {
			element.click();
			log.info("Successfully clicked on " + elementName);
			ExtentCucumberAdapter.addTestStepLog("  Successfully clicked on " + elementName);
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.info("Could not click on " + elementName);
			ExtentCucumberAdapter
					.addTestStepLog("Could not click on " + elementName + " exception occured " + e.getMessage());
			Assert.fail("Could not click on " + elementName + " due to " + e.getMessage());
		}
	}

	/**
	 * returns element text
	 * 
	 * @param element
	 * @param elementName
	 * @return {@link String}
	 */
	public static String getText(WebElement element, String elementName) {

		log.info("Getting Text for " + elementName);
		ExtentCucumberAdapter.addTestStepLog("Getting Text for " + elementName);

		String text = "";
		try {
			text = element.getText().trim();
			log.info("  Text is:" + text);
			ExtentCucumberAdapter.addTestStepLog("Text is:" + text);

		} catch (Exception e) {
			log.info("Could not get text || exception occured " + e.getMessage());
			e.printStackTrace();
		}
		return text;
	}

	public static boolean isElementSelected(WebElement element, String name) {

		String status;
		try {
			log.info("Verifying if " + name + " is selected");
			ExtentCucumberAdapter.addTestStepLog("Verifying if " + name + " is selected");
			status = ((MobileElement) element).getAttribute("selected");

			if (status.equalsIgnoreCase("true")) {
				log.info(name + " is selected");
				ExtentCucumberAdapter.addTestStepLog(name + " is selected");
				return true;
			} else {
				log.info("" + name + " is not selected");
				ExtentCucumberAdapter.addTestStepLog(name + " is not selected");
				return false;
			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void sleep(int i) {

		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author rupeshkumar02
	 * @param androidLocator
	 */

	/**
	 * 
	 * @param scenarioName
	 * @return outputFileLocation
	 */
	public static synchronized String takeFailedScreenshot(String scenarioName) {

		scenarioName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		String outputFileLocation = System.getProperty("user.dir") + "\\screenshots\\FailedScreenshots\\" + scenarioName
				+ "_" + dateFormat.format(new Date()) + ".png";
		try {
			log.info("Taking Failed Screenshot ");
			File srcFile = ((TakesScreenshot) DriverInstance.getAppiumDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(outputFileLocation));
			log.info("Failed Screenshot saved | output file : " + outputFileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFileLocation;
	}

}

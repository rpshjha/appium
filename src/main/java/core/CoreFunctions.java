package core;

import io.appium.java_client.AppiumDriver;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log
public class CoreFunctions {

	/**
	 * 
	 * @param scenarioName
	 * @return outputFileLocation
	 */
	public synchronized String takeFailedScreenshot(String scenarioName, AppiumDriver<WebElement> driver) {

		scenarioName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		String outputFileLocation = System.getProperty("user.dir") + "\\screenshots\\FailedScreenshots\\" + scenarioName
				+ "_" + dateFormat.format(new Date()) + ".png";
		try {
			log.info("Taking Failed Screenshot ");
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(outputFileLocation));
			log.info("Failed Screenshot saved | output file : " + outputFileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFileLocation;
	}

}

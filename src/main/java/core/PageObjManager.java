package core;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.ConfigReader;

public class PageObjManager {

	private long timeout = Long.valueOf(ConfigReader.getProperty("timeout"));

	public PageObjManager(AppiumDriver<WebElement> driver) {
		this.appiumdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(timeout)), this);
	}

	protected AppiumDriver<WebElement> appiumdriver;

	// Pages

}

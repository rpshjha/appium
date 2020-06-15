package cucumber;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import core.CoreFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import pageObjects.HomePage;
import pageObjects.ProfilePage;
import utilities.ConfigReader;

public class PageObjManager extends CoreFunctions {

	private long timeout = Long.valueOf(ConfigReader.getProperty("timeout"));

	public PageObjManager(AppiumDriver<WebElement> driver) {
		super(driver);
		this.appiumdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(timeout)), this);
	}

	protected AppiumDriver<WebElement> appiumdriver;

	// Pages

	private HomePage HomePage;
	private ProfilePage ProfilePage;

	public HomePage HomePage() {
		return (HomePage == null) ? HomePage = new HomePage(appiumdriver) : HomePage;
	}

	public ProfilePage ProfilePage() {
		return (ProfilePage == null) ? ProfilePage = new ProfilePage(appiumdriver) : ProfilePage;
	}

}

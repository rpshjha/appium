package pageObjects;

import org.openqa.selenium.WebElement;

import cucumber.PageObjManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends PageObjManager {

	public HomePage(AppiumDriver<WebElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@AndroidFindBy(accessibility = "Profile")
	private WebElement profile;

	@AndroidFindBy(accessibility = "Home")
	private AndroidElement home;

	public void clickOnProfileTab() {
		click(profile, "profile tab");
	}

	public boolean isHomeTabSelected() {
		return isElementSelected(home, "home tab");
	}

	public boolean isProfileTabSelected() {
		return isElementSelected(profile, "profile tab");
	}

}

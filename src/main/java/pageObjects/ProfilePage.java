package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.PageObjManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProfilePage extends PageObjManager {

	public ProfilePage(AppiumDriver<WebElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@AndroidFindBy(xpath = "//android.widget.LinearLayout[contains(@resource-id,'sdOvContainerSections')]//following-sibling::android.widget.LinearLayout")
	private List<WebElement> section;

	public int getCountOfAvlMenu() {
		return section.size();
	}

	public List<String> getNameOfAvlMenu() {

		List<String> list = new ArrayList<String>();
		for (WebElement temp : section) {
			list.add(temp.findElement(By.className("android.widget.TextView")).getText());
		}
		return list;
	}

	public List<String> clickOnAvlMenuOneByOneAndVerifyHeader() {

		List<String> list = new ArrayList<String>();
		for (WebElement temp : section) {
			String name = temp.findElement(By.className("android.widget.TextView")).getText();
			temp.click();
			list.add(temp.findElement(By.xpath("//android.widget.TextView[2]")).getText());
			temp.findElement(By.xpath("//android.view.ViewGroup[1]")).click();
		}
		return list;
	}

}

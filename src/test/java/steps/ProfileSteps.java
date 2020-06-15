package steps;

import cucumber.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;

public class ProfileSteps {

	private TestContext testContext;

	public ProfileSteps(TestContext testContext) {
		this.testContext = testContext;
	}

	@Given("I am on home page")
	public void i_am_on_home_page() {
		assertEquals(testContext.getPage().HomePage().isHomeTabSelected(), true, "checking if home tab is selected");
	}

	@When("I click on profile tab")
	public void i_click_on_profile_tab() {
		testContext.getPage().HomePage().clickOnProfileTab();
	}

	@Then("profile page should get open")
	public void profile_page_should_get_open() {
		assertEquals(testContext.getPage().HomePage().isProfileTabSelected(), true,
				"checking if profile tab is selected");
	}

	@Then("I should see different menus available on profile page")
	public void i_should_see_different_menus_available_on_profile_page() {
		testContext.log("count of avbl menu " + testContext.getPage().ProfilePage().getCountOfAvlMenu());
		System.out.println(testContext.getPage().ProfilePage().getNameOfAvlMenu());
		assertEquals(testContext.getPage().ProfilePage().getNameOfAvlMenu().size() > 0, true);
	}

	@When("I click on each menu and verify content")
	public void i_click_on_each_menu() {

		assertEquals(testContext.getPage().ProfilePage().clickOnAvlMenuOneByOneAndVerifyHeader().size() > 0, true);
	}

}

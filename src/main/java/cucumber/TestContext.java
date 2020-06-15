package cucumber;

import org.apache.log4j.Logger;

import core.CoreFunctions;
import core.DriverInstance;
import io.appium.java_client.remote.MobilePlatform;
import utilities.ConfigReader;

public class TestContext {

	private static Logger Log = Logger.getLogger(TestContext.class);

	private ScenarioContext scenarioContext;
	private PageObjManager pages;
	private CoreFunctions coreFunctions;

	public TestContext() {

	}

	public TestContext log(String message) {
		Log.info(message);
		return this;
	}

	public ScenarioContext getScenarioContext() {
		if (scenarioContext == null)
			scenarioContext = new ScenarioContext();
		return scenarioContext;
	}

	public PageObjManager getPage() {
		switch (ConfigReader.getProperty("platformName")) {
		case MobilePlatform.ANDROID:
			pages = new PageObjManager(DriverInstance.getAppiumDriver());
			break;

		default:
			System.err.println("platformName should be either Android or iOS");
			break;
		}
		return pages;
	}

	public CoreFunctions getCoreFunctions() {
		switch (ConfigReader.getProperty("platformName")) {
		case MobilePlatform.ANDROID:
			coreFunctions = new CoreFunctions(DriverInstance.getAppiumDriver());
			break;

		default:
			System.err.println("platformName should be Android or iOS");
			break;
		}
		return coreFunctions;
	}

}

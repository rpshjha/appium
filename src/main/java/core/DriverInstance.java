package core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import lombok.extern.java.Log;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.testvagrant.commons.entities.DeviceDetails;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import utilities.ConfigReader;

@Log
public class DriverInstance {

	private static DriverInstance instance;

	private static final ThreadLocal<AppiumDriver<WebElement>> appiumDriver = new ThreadLocal<AppiumDriver<WebElement>>();
	private static final ThreadLocal<DesiredCapabilities> cap = new ThreadLocal<DesiredCapabilities>();

	private DriverInstance() {
	}

	public static DriverInstance getInstance() {
		if (instance == null) {
			synchronized (DriverInstance.class) {
				if (instance == null)
					instance = new DriverInstance();
			}
		}
		return instance;
	}

	/**
	 * set up appium driver based on platform provided in config file
	 * 
	 * @param deviceDetails
	 * @param systemPort
	 * @param systemPort
	 * @param appiumPort
	 * 
	 * @return appiumDriver {@link AppiumDriver}
	 */

	public synchronized AppiumDriver<WebElement> setUpAppiumDriver(DeviceDetails deviceDetails, String systemPort,
			String appiumPort) {

		switch (ConfigReader.getProperty("platformName")) {

		case MobilePlatform.ANDROID:
			// EmulatorControls.launchEmulator(deviceDetails.getUdid());
			return createAndroidDriver(deviceDetails, systemPort, appiumPort);

		case MobilePlatform.IOS:
			return null;

		default:
			System.err.println("enter platformName as Android or iOS");
			return null;
		}

	}

	/**
	 * 
	 * @return {@link AppiumDriver}
	 */
	public static AppiumDriver<WebElement> getAppiumDriver() {
		return appiumDriver.get();
	}

	/**
	 * quit appium session
	 */
	public synchronized void quitAppiumDriver() {

		if (appiumDriver.get() != null) {
			log.info("quitting appium session");

			try {
				List<LogEntry> logEntries = appiumDriver.get().manage().logs().get("driver").getAll();
				File file = new File(ConfigReader.getProperty("appium_log_file_location"));
				FileUtils.write(file, "", StandardCharsets.UTF_8);
				FileUtils.writeLines(file, logEntries);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WebDriverException e) {

			}

			appiumDriver.get().quit();
			appiumDriver.remove();
			cap.remove();
			AppiumServer.stopServer();
		}
	}

	/**
	 *
	 * @param systemPort
	 * @return {@link DesiredCapabilities}
	 */
	private synchronized DesiredCapabilities setAndroidCapability(DeviceDetails deviceDetails, String systemPort) {

		DesiredCapabilities cap = new DesiredCapabilities();
		int newCommandTimeout = Integer.parseInt(ConfigReader.getProperty("newCommandTimeout"));
		String app = "";
		String appPackage = "";

		if (systemPort == null)
			System.err.println("system port NOT received, ignoring system port capability");
		else if (!systemPort.isEmpty())
			cap.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.valueOf(systemPort));

		/*
		 * specify path for apk and specify appPackage based on env
		 */

		app = new File(ConfigReader.getProperty("apk")).getAbsolutePath();
		appPackage = ConfigReader.getProperty("appPackage");

		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigReader.getProperty("platformName"));
		cap.setCapability(MobileCapabilityType.UDID, deviceDetails.getUdid());
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceDetails.getDeviceName());
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceDetails.getOsVersion());
		cap.setCapability(MobileCapabilityType.APP, app);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ConfigReader.getProperty("appActivity"));
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, newCommandTimeout);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		cap.setCapability(MobileCapabilityType.NO_RESET, false);

		return cap;
	}

	/**
	 * creates android driver
	 * 
	 * @param deviceDetails
	 * @param systemPort
	 * @param appiumPort
	 * @return {@link AndroidDriver}
	 */
	private synchronized AndroidDriver<WebElement> createAndroidDriver(DeviceDetails deviceDetails, String systemPort,
			String appiumPort) {

		try {
			cap.set(setAndroidCapability(deviceDetails, systemPort));
			URL appiumServiceUrl;

			if (appiumPort == null || appiumPort.isEmpty()) {
				System.err.println("appium port NOT received, using default port provided in config property");
				appiumServiceUrl = AppiumServer.startServer(Integer.valueOf(ConfigReader.getProperty("appiumPort")))
						.getUrl();
			} else {
				appiumServiceUrl = AppiumServer.startServer(Integer.valueOf(appiumPort)).getUrl();
			}

			log.info("initializing android driver");
			appiumDriver.set(new AndroidDriver<WebElement>(appiumServiceUrl, cap.get()));
			log.info("android driver initialized");

		} catch (AppiumServerHasNotBeenStartedLocallyException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (SessionNotCreatedException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (WebDriverException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (AndroidDriver<WebElement>) appiumDriver.get();
	}
}

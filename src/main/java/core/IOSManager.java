package core;


import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import lombok.extern.java.Log;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import utilities.AppiumServer;
import utilities.ConfigReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Log
public class IOSManager {

    /**
     * creates iosDriver
     * @return IOSDriver
     */
    public static IOSDriver<WebElement> createIOSDriver() {

        IOSDriver<WebElement> iosDriver = null;

        try {

            DesiredCapabilities capabilities = setIOSCapability();
            URL appiumServiceUrl = AppiumServer.startServerOnAnyPort().getUrl();
            iosDriver = new IOSDriver<WebElement>(appiumServiceUrl, capabilities);

        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (SessionNotCreatedException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (WebDriverException e) {
            e.printStackTrace();
            log.severe(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iosDriver;
    }

    private static DesiredCapabilities setIOSCapability() {

        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getProperty("deviceName"));
        cap.setCapability(MobileCapabilityType.UDID, ConfigReader.getProperty("udid"));
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, ConfigReader.getProperty("bundleId"));
        cap.setCapability(MobileCapabilityType.APP, new File(ConfigReader.getProperty("apk")).getPath());

        return cap;
    }
}

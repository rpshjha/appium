package core;

import com.testvagrant.commons.entities.DeviceDetails;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
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

/**
 * @author rupeshkumar
 */
@Log
public class AndroidManager {

    /**
     * creates android driver
     *
     * @param deviceDetails
     * @param systemPort
     * @param appiumPort
     * @return AndroidDriver
     */
    public static AndroidDriver<WebElement> createAndroidDriver(DeviceDetails deviceDetails, String systemPort,
                                                                String appiumPort) {
        AndroidDriver<WebElement> androidDriver = null;
        try {
            DesiredCapabilities cap = setAndroidCapability(deviceDetails, systemPort);
            URL appiumServiceUrl;

            if (appiumPort == null || appiumPort.isEmpty()) {
                System.err.println("appium port NOT received, using default port provided in config property");
                appiumServiceUrl = AppiumServer.startServer(Integer.valueOf(ConfigReader.getProperty("appiumPort")))
                        .getUrl();
            } else {
                appiumServiceUrl = AppiumServer.startServer(Integer.valueOf(appiumPort)).getUrl();
            }

            log.info("initializing android driver");
            androidDriver = new AndroidDriver<WebElement>(appiumServiceUrl, cap);
            log.info("android driver initialized");

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

        return androidDriver;
    }

    /**
     * set desired capability
     *
     * @param deviceDetails
     * @param systemPort
     * @return DesiredCapabilities
     */
    private static DesiredCapabilities setAndroidCapability(DeviceDetails deviceDetails, String systemPort) {

        DesiredCapabilities cap = new DesiredCapabilities();
        int newCommandTimeout = Integer.parseInt(ConfigReader.getProperty("newCommandTimeout"));

        if (systemPort == null)
            log.info("system port NOT received, ignoring system port capability");
        else if (!systemPort.isEmpty())
            cap.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.valueOf(systemPort));


        if (deviceDetails == null) {
            cap.setCapability(AndroidMobileCapabilityType.AVD, ConfigReader.getProperty("nameOfAVD"));
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getProperty("nameOfAVD"));
        } else {
            cap.setCapability(MobileCapabilityType.UDID, deviceDetails.getUdid());
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceDetails.getDeviceName());
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceDetails.getOsVersion());
        }

        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.APP, new File(ConfigReader.getProperty("apk")).getPath());
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, ConfigReader.getProperty("appPackage"));
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ConfigReader.getProperty("appActivity"));
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, newCommandTimeout);
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        cap.setCapability(MobileCapabilityType.NO_RESET, false);

        return cap;
    }
}

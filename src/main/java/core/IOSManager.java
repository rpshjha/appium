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
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12 Pro Max");
        cap.setCapability(MobileCapabilityType.UDID, "50438D4D-DAD5-46F4-928E-7D973075EAF6");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.example.apple-samplecode.UICatalog");

        String appurl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "apk" + File.separator + "UIKitCatalog-iphonesimulator.app";
        cap.setCapability(MobileCapabilityType.APP, appurl);


        return cap;
    }
}

package tests;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import core.DriverInstance;
import io.appium.java_client.remote.MobilePlatform;
import lombok.extern.java.Log;
import org.openqa.selenium.InvalidArgumentException;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Log
public class BaseTest {

    @BeforeTest
    public void beforeTest() {
        log.info("INSIDE BEFORE TEST");
//        System.setProperty("platformName", MobilePlatform.IOS);
        System.setProperty("platformName", MobilePlatform.ANDROID);
    }

    @Parameters({"systemPort", "appiumPort"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional String systemPort, @Optional String appiumPort) {

        log.info("INSIDE BEFORE METHOD " + this.getClass().getCanonicalName());

        if (System.getProperty("platformName").equals(MobilePlatform.ANDROID)) {

            List<DeviceDetails> androidDevices = new ArrayList<>();
            try {
                androidDevices = new Android().getDevices();
                int random = new Random().nextInt(androidDevices.size());

                System.out.println("deviceName: " + androidDevices.get(0).getDeviceName() + "\ndeviceUDID: "
                        + androidDevices.get(0).getUdid() + "\nplatformVersion: " + androidDevices.get(0).getOsVersion()
                        + " \nsystemPort: " + systemPort);

            } catch (com.testvagrant.mdb.Exceptions.ConnectedDevicesException e) {
                log.severe("Could not find any devices, are any devices available?");
            } catch (IllegalArgumentException e){
                log.severe("no devices found");
            }

            if (!androidDevices.isEmpty())
                DriverInstance.setUpAppiumDriver(androidDevices.get(0), systemPort, appiumPort);
            else
                DriverInstance.setUpAppiumDriver(null, systemPort, appiumPort);

        } else if (System.getProperty("platformName").equals(MobilePlatform.IOS)) {

            DriverInstance.setUpAppiumDriver(null, null, null);
        }


    }

    @AfterMethod
    public void teardown() {

        log.info("INSIDE AFTER METHOD " + this.getClass().getCanonicalName());

        /* removing uiautomator app */
        try {
            DriverInstance.getAppiumDriver().removeApp("io.appium.uiautomator2.server");
            DriverInstance.getAppiumDriver().removeApp("io.appium.uiautomator2.server.test");
        } catch (InvalidArgumentException e1) {
        } catch (NullPointerException e2) {
        }

        /* quitting appium driver */
        DriverInstance.quitAppiumDriver();

        try {
            Process p = Runtime.getRuntime().exec("adb kill-server");
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package tests;

import com.testvagrant.commons.entities.DeviceDetails;
import com.testvagrant.mdb.android.Android;
import core.DriverInstance;
import io.appium.java_client.remote.MobilePlatform;
import lombok.extern.java.Log;
import org.openqa.selenium.InvalidArgumentException;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Log
public class BaseTest {

    @BeforeTest
    public void beforeTest() {
        log.info("INSIDE BEFORE TEST");
//        System.setProperty("platformName", MobilePlatform.IOS);
    }

    @Parameters({"systemPort", "appiumPort"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional String systemPort, @Optional String appiumPort) {

        log.info("INSIDE BEFORE METHOD " + this.getClass().getCanonicalName());

        if (System.getProperty("platformName").equals(MobilePlatform.ANDROID)) {
            List<DeviceDetails> androidDevices = new Android().getDevices();

            int random = new Random().nextInt(androidDevices.size());

            System.out.println("deviceName: " + androidDevices.get(0).getDeviceName() + "\ndeviceUDID: "
                    + androidDevices.get(0).getUdid() + "\nplatformVersion: " + androidDevices.get(0).getOsVersion()
                    + " \nsystemPort: " + systemPort);

            DriverInstance.setUpAppiumDriver(androidDevices.get(0), systemPort, appiumPort);
        }

        else if(System.getProperty("platformName").equals(MobilePlatform.IOS)){

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

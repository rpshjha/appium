package utilities;

import io.appium.java_client.remote.MobilePlatform;
import lombok.extern.java.Log;

import java.io.*;
import java.util.Properties;

@Log
public class ConfigReader {

    private static Properties properties = null;

    static {

        String filepath = null;

        if (System.getProperty("platformName").equals(MobilePlatform.ANDROID)) {
            filepath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "android_config.properties";
        } else if (System.getProperty("platformName").equals(MobilePlatform.IOS)) {
            filepath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "ios_config.properties";
        } else {
            log.severe("config file not found");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            properties = new Properties();
            properties.load(reader);
        } catch (FileNotFoundException e) {
            log.info("file not found " + filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * gets the property
     *
     * @param configname
     * @return
     */
    public static String getProperty(String configname) {
        return properties.getProperty(configname);
    }

}
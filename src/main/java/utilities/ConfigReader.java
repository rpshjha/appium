package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties = null;
	private static String filepath = "src/test/resources/configuration.properties";

	static {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("file not found " + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param configname
	 * @return
	 */
	public static String getProperty(String configname) {

		return properties.getProperty(configname);

	}

}
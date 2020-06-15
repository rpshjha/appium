package core;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import utilities.ConfigReader;

public class AppiumServer {

	private static final Logger log = Logger.getLogger(AppiumServer.class);

	private AppiumServer() {
	}

	private static final ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<AppiumDriverLocalService>();
	private static String NodeModulesAppiumMainJs = System.getProperty("user.home") + File.separator
			+ "AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
	private static String NodePath = "C:\\Program Files\\nodejs\\node.exe";

	public static AppiumDriverLocalService startServer(int port) throws Exception {

		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		appiumServiceBuilder.withAppiumJS(new File(NodeModulesAppiumMainJs));
		appiumServiceBuilder.usingDriverExecutable(new File(NodePath));
		appiumServiceBuilder.withIPAddress(ConfigReader.getProperty("ip"));
		appiumServiceBuilder.usingPort(port);
		appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		if (ConfigReader.getProperty("platformName").equalsIgnoreCase(MobilePlatform.ANDROID))
			appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
					String.valueOf(aRandomOpenPortOnAllLocalInterfaces()));

		appiumService.set(AppiumDriverLocalService.buildService(appiumServiceBuilder));

		log.info("starting appium server");
		appiumService.get().start();

		if (appiumService.get() == null || !appiumService.get().isRunning())
			throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");

		log.info("appium server started at : " + appiumService.get().getUrl().toString());
		return appiumService.get();
	}

	public static AppiumDriverLocalService startServerOnAnyPort() throws Exception {

		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		appiumServiceBuilder.withAppiumJS(new File(NodeModulesAppiumMainJs));
		appiumServiceBuilder.usingDriverExecutable(new File(NodePath));
		appiumServiceBuilder.withIPAddress(ConfigReader.getProperty("ip"));
		appiumServiceBuilder.usingAnyFreePort();
		appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		if (ConfigReader.getProperty("platformName").equalsIgnoreCase(MobilePlatform.ANDROID))
			appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
					String.valueOf(aRandomOpenPortOnAllLocalInterfaces()));

		appiumService.set(AppiumDriverLocalService.buildService(appiumServiceBuilder));

		log.info("starting appium server");
		appiumService.get().start();

		if (appiumService.get() == null || !appiumService.get().isRunning())
			throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");

		log.info("appium server started at : " + appiumService.get().getUrl().toString());
		return appiumService.get();
	}

	private boolean isAppiumServiceRunning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static void stopServer() {
		log.info("stopping appium server");
		if (appiumService.get() != null) {
			appiumService.get().stop();
			appiumService.remove();
		}
	}

	private static Integer aRandomOpenPortOnAllLocalInterfaces() {
		try (ServerSocket socket = new ServerSocket(0);) {
			return socket.getLocalPort();
		} catch (IOException e) {
			throw new RuntimeException("no open ports found for bootstrap");
		}
	}

}

package utilities;


import io.appium.java_client.remote.MobilePlatform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class EmulatorControls {

    private static String emulatorPath;
    private static String adbPath;
    private static String HomeDirectory;
    private static String sdkPath;


    static {

        if (System.getProperty("platformName").equals(MobilePlatform.ANDROID)) {

            HomeDirectory = System.getProperty("user.home");
            sdkPath = "AppData" + File.separator + "Local" + File.separator + "Android" + File.separator + "Sdk";
            emulatorPath = HomeDirectory + File.separator + sdkPath + File.separator + "emulator" + File.separator + "emulator.exe";
            adbPath = HomeDirectory + File.separator + sdkPath + File.separator + "platform-tools" + File.separator + "adb.exe";

        } else if (System.getProperty("platformName").equals(MobilePlatform.IOS)) {
            //TODO
        }
    }

    private EmulatorControls() throws IOException {
    }

    /**
     * @param nameOfAVD
     */
    public static void launchEmulator(String nameOfAVD) {

        Process process;

        if (isEmulatorOrDeviceRunning()) {
            System.out.println("Emulator/Device is already running ..!!");
            return;

        } else {
            System.out.println("Starting emulator ->> " + nameOfAVD);
            String[] aCommand = new String[]{emulatorPath, "-avd", nameOfAVD};
            try {
                process = new ProcessBuilder(aCommand).start();
                process.waitFor(40, TimeUnit.SECONDS);
                if (isEmulatorOrDeviceRunning())
                    System.out.println("Emulator ->> " + nameOfAVD + " launched successfully!");
                else
                    throw new Exception("Emulator ->> " + nameOfAVD + " could not be launched");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void closeEmulator() {
        Process process;
        if (isEmulatorOrDeviceRunning()) {
            System.out.println("Killing emulator...");
            String[] aCommand = new String[]{adbPath, "emu", "kill"};
            try {
                process = new ProcessBuilder(aCommand).start();
                process.waitFor(1, TimeUnit.SECONDS);
                if (!isEmulatorOrDeviceRunning())
                    System.out.println("Emulator closed successfully .. !!");
                else if (isEmulatorOrDeviceRunning())
                    System.err.println("Emulator could not be closed .. !!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Currently there are no Emulators running");
    }

    private static boolean isEmulatorOrDeviceRunning() {
        Process process;
        String[] commandDevices;
        boolean isRunning = false;
        String output = "";
        String line = null;
        try {
            commandDevices = new String[]{adbPath, "devices"};
            process = new ProcessBuilder(commandDevices).start();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = inputStream.readLine()) != null) {
                output = output + line;
            }
            if (!output.replace("List of devices attached", "").trim().equals("")) {
                isRunning = true;
                System.out.println("List of devices attached" + "\n" + output.replace("List of devices attached", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }

    public static void main(String[] args) throws IOException {

//        EmulatorControls.launchEmulator("Pixel");

//        String nameOfAvd = "Pixel";
//        String commandName = "/Users/rupeshkumar/Library/Android/sdk/emulator/emulator -avd " + nameOfAvd + " -netdelay none -netspeed full";
//
//        System.out.println(commandName);
//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec(commandName);
    }
}

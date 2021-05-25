# appium


1. Install homebrew (package manager for macOS and is used to install software packages)
============================================================
Link: https://brew.sh/
Command: /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"


2. Install node and npm (Appium dependencies)
===========================================
Commands to check if node and npm are installed:
node -v
npm -v
Command to install node: brew install node (This will install npm as well)
Command to check node installation path: where node


3. Install Appium server using NPM (Appium CLI)
==============================================
Command to install Appium: npm install -g Appium
Command to check Appium version: appium -v
Command to check Appium installation path: where appium

OR

3. Install Appium server using Appium Desktop client
=================================================
Download link: https://appium.io


4. Install JAVA JDK (not the JRE!)
===========================================================
- Command to check if JAVA is already installed: java -version
- JAVA JDK download link: https://www.oracle.com/technetwork/java/javase/downloads/index.html


5. Install Android Studio
=================================================================
- Android Sutdio download link: https://developer.android.com/studio


6. Set JAVA_HOME and ANDROID_HOME environment variables
=======================================================
Option1 (zprofile - MacOS Catalina default shell is zsh): 
--------------------------------------------------------
-> Navigate to home directory: cd ~/
-> Open zprofile file: open -e .zprofile
-> Create zprofile file: touch .zprofile
-> Add below entries:
export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=${HOME}/Library/Android/sdk
export PATH="${JAVA_HOME}/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${PATH}"
-> source .zprofile

Option2 (zprofile and bashprofile):
----------------------------------
-> Navigate to home directory: cd ~/
-> Open bash profile file: open -e .bash_profile
-> Create bash profile: touch .bash_profile
-> Add below entries:
export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=${HOME}/Library/Android/sdk
export PATH="${JAVA_HOME}/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${PATH}"
-> Open zprofile file: open -e .zprofile
-> Create zprofile file: touch .zprofile
-> add this line: source .bash_profile
echo $JAVA_HOME
echo $ANDROID_HOME
echo $PATH

7. Verify installation using appium-doctor
==========================================
- Command to install appium-doctor: npm install -g appium-doctor
- Command to get appium-doctor help: appium-doctor --help
- Command to check Android setup: appium-doctor --android 


8. Emulator Setup: Create AVD and start it
==========================================
Open Android Studio
Click Configure option
Click "AVD Manager" option
Click "Create Virtual Device" button
Select the phone model
Download the Image for desired OS version if not already downloaded
Start AVD

9. Emulator Setup: Create driver session with the AVD using Appium Inspector
============================================================================
Download link for dummy app:
https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk

Note: If using Appium desktop, might get error with adb tool because Appium Desktop 
cannot read ANDROID_HOME and JAVA_HOME path from the zsh/bash profile. 
To resolve, set ANDROID_HOME to SDK path and JAVA_HOME to Java home path using "Edit Configurations" option 
while launching the Appium Desktop.

10. Real Device Setup: Enable USB debugging on Android mobile
============================================================
On your phone,
Go to Settings
Click System option
Click "About Phone" option
Click on "Build Number" 7 to 8 times
Go back to Settings
Open Developer Options
Enable "USB Debugging"

11. Real Device Setup: Create driver session using Appium Inspector
===================================================================
Download link for dummy app:
https://github.com/appium/appium/blob/master/sample-code/apps/ApiDemos-debug.apk

Note: If using Appium desktop, might get error with adb tool because Appium Desktop 
cannot read ANDROID_HOME path from the zsh/bash profile. 
To resolve, set ANDROID_HOME to SDK path using "Edit Configurations" option 
while launching the Appium Desktop.

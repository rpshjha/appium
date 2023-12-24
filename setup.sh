#!/bin/bash

# Exit on any error
set -e

# Check JAVA_HOME
echo "JAVA_HOME set to: $JAVA_HOME"

# Set up the SDK directory
SDK_DIR="$HOME/android-sdk"

# Check if the SDK directory already exists
if [ -d "$SDK_DIR" ]; then
  echo "Removing existing SDK directory: $SDK_DIR"
  rm -rf "$SDK_DIR"
fi

# Create the SDK directory
mkdir -p "$SDK_DIR"

# Download and extract the SDK tools
SDK_TOOLS_URL="https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip"
curl -o sdk-tools.zip "$SDK_TOOLS_URL"
unzip sdk-tools.zip -d "$SDK_DIR"

# Create a temporary directory within SDK_DIR to facilitate the move operation
TEMP_DIR="$SDK_DIR/temp/latest/"
mkdir -p "$TEMP_DIR"

# Move the contents of cmdline-tools into the temp directory
mv "$SDK_DIR/cmdline-tools/"* "$TEMP_DIR/"

# Rename the temporary directory to 'latest'
mv "$TEMP_DIR" "$SDK_DIR/cmdline-tools/latest"

# Remove the temporary directory
rm -rf "$SDK_DIR/temp"

# Set up environment variables
export ANDROID_HOME="$SDK_DIR"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export PATH=$JAVA_HOME/bin:$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/emulator:$ANDROID_SDK_ROOT/platform-tools


# Accept Android SDK licenses
yes | sdkmanager --licenses

# Ensure the emulator package is accepted
yes | sdkmanager "emulator"

# List available packages
sdkmanager --list --verbose

# Install necessary components
yes | sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0" "system-images;android-34;google_apis_playstore;x86_64" "emulator"

# Create AVD
echo "no" | avdmanager create avd -n test -k "system-images;android-34;google_apis_playstore;x86_64" --device "Nexus 5X" --abi "x86_64" --force

# Start the emulator
emulator -avd test -no-snapshot


# Clean up
rm -f sdk-tools.zip

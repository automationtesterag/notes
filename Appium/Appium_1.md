# Appium (2.0)

**What is Appium?**

- Appium is an open-source test automation framework for mobile apps. It allows you to write tests for native, hybrid, and mobile web apps on various platforms like iOS, Android, and Windows.developed and supported by Sauce Labs.

**Advantages**

1. Cross platform: Android &iOS: we can test native, hybrid, web app
2. Allows you to communicate with other apps, Ex: WhatsApp (Majority of tools doesn't support this)
3. Don't have to recompile or modify the app for automation
4. support for built-in app: alarm, phone, calendar etc
5. Any WebDriver compatible language is supported: Java, objective C, Ruby, php, C# : we just need language specific libraries to work on
6. Good community support

**Limitations**

1. For Android, No Support for Android API level < 17(for android v>4.1) if you mobile having less than 17 then we need to go for selendroid.
2. Script execution is very slow on iOS platform & android virtual devices
3. No support for Toast messages: we can handle this
4. No paralel execution directly : we can handle using sauce labs
5. iOS automation with local Appium server requires a Mac
6. Maintaining local Appium server lab requires additional resources
7. Documentation is limited or some times too technical
8. Changes to XCTest framework often breaks Appium

### Comparison with Other tools

|                        | **Appium**  | **XCTest**           | **Robotium** | **UI Automator** | **Expresso**   |
| ---------------------- | ----------- | -------------------- | ------------ | ---------------- | -------------- |
| **Platform**           | iOS/Android | iOS                  | Android      | Android          | Android        |
| **Unit Testing**       | No          | Yes                  | No           | No               | Yes            |
| **Functional Testing** | Yes         | Yes                  | Yes          | Yes              | No             |
| **Language**           | Multiple    | Objective C or Swift | Java         | Java or Kotlin   | Java or Kotlin |

## Appium Architecture

![architecture](images/Architecture.png)

Appium 2.0 introduces a new architecture that brings significant changes and improvements to the framework. The key aspects of the Appium 2.0 architecture are:

1. **Driver Providers**: Appium 2.0 replaces the concept of "drivers" with "driver providers." Driver providers are responsible for managing the lifecycle of the automation session, including launching and terminating the app under test. This separation of concerns allows for better modularity and extensibility.

2. **Plugin-based Architecture**: Appium 2.0 adopts a plugin-based architecture, where different components of the framework can be implemented as separate plugins. This modular approach makes it easier to extend Appium's functionality and maintain individual components.

3. **Execution Contexts**: Appium 2.0 introduces the concept of "execution contexts," which represent different automation contexts within an app, such as native UI, web views, or Chrome Dev Tools (for web apps). This allows for seamless switching between different contexts during test execution.

4. **Pluggable Driver Providers**: Driver providers are now pluggable, allowing for easy integration of new or custom driver providers. This enables support for automating different types of applications or platforms without modifying the core Appium codebase.

5. **Improved Session Management**: Appium 2.0 enhances session management by separating the notion of a session from the automation context. This allows for multiple execution contexts within a single session, enabling more efficient test execution.

6. **Better Compatibility**: Appium 2.0 aims to improve compatibility with the latest versions of iOS, Android, and other platforms, ensuring that the framework stays up-to-date with the latest platform changes and features.

7. **Performance Optimizations**: The new architecture focuses on performance optimizations, including improved parallelization and more efficient resource utilization, leading to faster test execution times.

8. **Improved Testability and Maintainability**: The modular design and separation of concerns in Appium 2.0 make the codebase more testable and maintainable, facilitating easier contributions and bug fixes from the community.

Overall, the Appium 2.0 architecture introduces a more modular, extensible, and scalable approach to mobile app automation. It aims to address some of the limitations and challenges faced in previous versions, while also providing a foundation for future enhancements and improvements to the framework.

**`Note`**:

- Appium 2.x no longer uses the JSON Wire protocol. This is completely replaced by the W3C WebDriver protocol.

- The W3C WebDriver protocol is also based on the same client server architecture as explained in this lecture.

- W3C stands for World Wide Web Consortium, which is an international community that develops the standard for the Web. All major browsers, like Chrome, Firefox, etc. follow this standard, and now Selenium WebDriver 4.x has also switched to it completely.

- In the past, Selenium (3.x) supported both the JSON Wire protocol and the W3C, but with Selenium 4.x, the JSON Wire protocol has been completely removed. Since Appium is based on the WebDriver, it also supported both protocols in Appium 1.x, but from Appium 2.x on, it has also switched to W3C completely.

## Types of Mobile Apps

1. **Mobile Web App:**
   Web apps are not real applications; they are actually websites that open in your smartphone with the help of a web browser. Mobile websites have the broadest audience of all the primary types of applications.

2. **Native App:**
   A native app is developed specifically for one platform. It can be installed through an application store (such as Google Play Store or Apple's App Store).
   Example - Whatsapp, Facebook. This app can use all the system application's(the apps which comes by default with your mobile)

3. **Hybrid App:**
   Hybrid Apps are a way to expose content from existing websites in App format. They can be well described as a mixture of Web App and Native App.

## Installation

**`Windows`**

```
1. Install Node

2. Install Appium Command Line Interface (CLI) server
=====================================================
-> Command to install Appium using npm: npm install -g appium@next
Note: @next will not be required once Appium 2.0 stable release is out to market.
-> Command to install specific version: npm install -g appium@<verion_number>
-> Command to start Appium: appium
-> Command to get installation location: where appium
-> Command to uninstall Appium: npm uninstall -g appium

3. Install UiAutomator2 driver (using Appium CLI)
=================================================
Get help: appium driver --help (or -h)
Get list of officially supported drivers: appium driver list
Install driver: appium driver install uiautomator2
Install driver with specific version: appium driver install uiautomator2@<version_number>

4. Install Appium Inspector
===========================
-> Download and install from https://github.com/appium/appium-inspector/releases

5. Install JAVA JDK and configure environment variables
====================================================
-> Command to check if JAVA is already installed: java -version
-> JAVA JDK download link: https://www.oracle.com/technetwork/java/javase/downloads/index.html
Important note: Please use Java 8/11/15 for now. Don't use Java 16 or higher. The current Appium Java Client 8.x.x is not compatible with Java 16+. You may use Java 16+ once Appium Java client becomes compatible.
-> Create JAVA_HOME system environment variable and set it to JDK path (without bin folder).
Edit PATH system environment variable and add %JAVA_HOME%\bin
Note: Usually JDK path is "C:\Program Files\Java\<your_jdk_version>"

6. Install Android Studio and configure environment variables
==========================================================
-> Android Studio download link: https://developer.android.com/studio
-> Create ANDROID_HOME system environment variable and set it to SDK path.
Edit PATH system environment variable and add below,
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\cmdline-tools


7. Verify installation using appium-doctor
==========================================
Command to install appium-doctor: npm install -g appium-doctor
Command to get help: appium-doctor --help
Command to check Android setup: appium-doctor --android


8. Emulator Setup: Accelerate Performance
======================================
Launch Android Studio -> SDK Manager -> SDK Tools
Intel processor: Check "Intel x86 Emulator Accelerator (HAXM Installer)" and Apply
AMD processor: Check "Android Emulator Hypervisor Driver for AMD Processors (installer)" and Apply


9. Emulator Setup: Create AVD and start it
===========================================
Important note: AVDs are resource hungry! Please use a laptop with powerful processor (that supports Intel HAXM/AMD hypervisor) and sufficient RAM.
Open Android Studio -> Configure -> Virtual Device Manager -> Create Virtual Device ->
Select Model -> Download Image for desired OS version if not already downloaded
-> Start AVD


10. Emulator Setup: Create Driver Session using Appium CLI
======================================================
Download link for dummy app:
https://github.com/appium/appium/tree/master/packages/appium/sample-code/apps


11. Real Device Setup: Enable USB debugging on Android mobile
=============================================================
Note: Steps can differ based on the phone manufacturer!
-> Settings -> System -> About Phone -> Click Build Number 7-8 times
-> Settings -> Developer Options -> Enable USB Debugging
-> Permission pop-up: Check the box and press Allow to recognise the computer
-> run "adb devices" in CMD prompt to check if device is recognised
-> USB drivers:
Google: https://developer.android.com/studio/run/win-usb
OEMs: https://developer.android.com/studio/run/oem-usb


12. Real Device Setup: Create Driver Session using Appium CLI
=========================================================
Download link for dummy app:
https://github.com/appium/appium/tree/master/packages/appium/sample-code/apps


```

**`Mac`**

```

1. Install homebrew [package manager for macOS and is used to install software packages]
======================================================================================
Link: https://brew.sh/
Command: /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"


2. Install node and npm [Appium dependencies]
===============================================
Commands to check if node and npm are installed:
node -v
npm -v
Command to install node: brew install node [This will install npm as well]
Command to check node installation path: where node or which node


3. Install Appium CLI server
=========================
-> Command to install Appium using npm: npm install -g appium@next
Note: @next will not be required once Appium 2.0 stable release is out to market.
-> Command to install specific version: npm install -g appium@<verion_number>
-> Command to start Appium: appium
-> Command to get installation location: where appium
Note: This command only gives location of the shortcut, not the actual installation location.
Actual installation location is this: /usr/local/lib/node_modules/appium
-> Command to uninstall Appium: npm uninstall -g appium


4. Install Appium Inspector
========================
-> Download and install from https://github.com/appium/appium-inspector/releases


5. Install JAVA JDK (not the JRE!)
================================
-> Command to check if JAVA is already installed: java -version
-> JAVA JDK download link: https://www.oracle.com/technetwork/java/javase/downloads/index.html
Important note: Please use Java 8/11/15 for now. Don't use Java 16 or higher. The current Appium Java Client 8.x.x is not compatible with Java 16+. You may use Java 16+ once Appium Java client becomes compatible.


6. Set JAVA_HOME environment variable
==================================
On macOS 10.15 Catalina and later, default shell is zsh:
--------------------------------------------------------
-> Navigate to home directory: cd ~/
-> Open zshrc file: open -e .zshrc
-> Create zshrc file: touch .zshrc
-> Add below entries:
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH="${JAVA_HOME}/bin:${PATH}"
-> source .zshrc
-> echo $JAVA_HOME
-> echo $PATH
Follow this article: https://mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/

For Android in Mac
__________________________________

1. Install UiAutomator2 driver (using Appium CLI)
===========================================
Command to get help: appium driver --help (or -h)
Command to list drivers: appium driver list
Command to install driver: appium driver install UiAutomator2


2. Install Android Studio
====================================================================
- Android Studio download link: https://developer.android.com/studio


3. Set ANDROID_HOME environment variables
======================================
On macOS 10.15 Catalina and later, default shell is zsh:
--------------------------------------------------------
-> Navigate to home directory: cd ~/
-> Open zshrc file: open -e .zshrc
-> Add below entries:
export ANDROID_HOME=${HOME}/Library/Android/sdk
export PATH="${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/cmdline-tools:${PATH}"
-> source .zshrc
-> echo $ANDROID_HOME
-> echo $PATH


4. Verify installation using appium-doctor
==========================================
Command to install appium-doctor: npm install -g appium-doctor
Command to get appium-doctor help: appium-doctor --help
Command to check Android setup: appium-doctor --android


5. Emulator Setup: Create AVD and start it
==========================================
Open Android Studio
Click Configure option
Click "Virtual Device Manager" option
Click "Create Virtual Device" button
Select the phone model
Download the Image for desired OS version if not already downloaded
Start AVD

6. Emulator Setup: Create driver session using Appium CLI
=======================================================
Download link for dummy app:
https://github.com/appium/appium/tree/master/packages/appium/sample-code/apps/ApiDemos-debug.apk


7. Real Device Setup: Enable USB debugging on Android mobile
============================================================
On your phone,
Go to Settings
Click System option
Click "About Phone" option
Click on "Build Number" 7 to 8 times
Go back to Settings
Open Developer Options
Enable "USB Debugging"

8. Real Device Setup: Create driver session using Appium CLI
==========================================================
https://github.com/appium/appium/tree/master/packages/appium/sample-code/apps/ApiDemos-debug.apk


Setup for iOS
____________________


Install Xcode
====================
Configure Apple ID in Account preferences
Install from App Store


Install Xcode command line tools
======================================
Command: xcode-select --install


Install xcpretty [to make Xcode output reasonable]
========================================================
Command to install xcpretty: gem install xcpretty


Install Carthage [dependency manager, required for WebDriverAgent]
==================================================================
Note: This may be optional. In case of any installation issue, just ignore and proceed further.
Command to install Carthage: brew install Carthage


Install Appium-doctor and check Appium setup
===================================================
Command to install Appium doctor: npm install -g appium-doctor
Command to get help: appium-doctor --h
Command to check setup for iOS: appium-doctor --ios


Install XCUITest driver (using Appium CLI)
===========================================
Command to get help: appium driver --help (or -h)
Command to list drivers: appium driver list
Command to install driver: appium driver install XCUITest


Simulator setup: Build UIKitCatalog app for simulator
=====================================================
Download link: https://github.com/appium/ios-uicatalog
Command to get simulator name: xcodebuild -showsdks
Command to build app for the simulator: xcodebuild -sdk <simulator_name>
Command to build UIKitCatalog app for simulator: npm install


Simulator setup: Start session with UIKitCatalog app using Appium CLI
=======================================================================
Command to get UDID: xcrun simctl list
Command to get UDID: xcrun xctrace list devices
Xcode option to get UDID: Xcode -> Window -> Devices and Simulators -> Simulators -> Select the simulator in the left pane. In the right pane, "Identifier" will be the UDID.


=================================================================
                       Real device setup
=================================================================

Getting UDID
=============
Command to install ios-deploy: npm install -g ios-deploy
Command to get UDID: ios-deploy -c
OR
Command to get UDID: xcrun simctl list [May show real devices as well]
Command to get UDID: xcrun xctrace list devices [May show real devices as well]
Xcode option to get UDID: Xcode -> Window -> Devices and Simulators -> Devices -> Select the device in the left pane. In the right pane, "Identifier" will be the UDID.


Option 1. Code signing WebDriverAgent: Basic (automatic/manual) configuration.
=============================================================================

Step 1. Enrol for Developer program
-> Create Apple account: https://developer.apple.com
-> Enable two factor authentication: https://appleid.apple.com/account/manage
-> Click Join the Apple Developer program
-> Click Enrol
-> Click Start Your Enrolment

Step 2. Register device UDID on the developer portal (this can be done from Xcode as well)

Step 3. Add your Apple ID (paid developer account) to Xcode and download the certificate (if required, create new certificate on the developer portal)

Step 4. Generate UIKitCatalog IPA:
-> Download UIKitCatalog app from https://github.com/appium/ios-uicatalog
-> Launch project and code sign using Xcode managed provisioning profile
-> Confirm wild card provisioning profile is created in the developer account
-> (select generic iOS device) Archive app to generate IPA

Step 5. Create session with app using Appium CLI server
-> Open inspector session and set below desired capabilities
"xcodeOrgId": "your team id"
"xcodeSigningId": "iPhone Developer"


Option 2. Code signing WebDriverAgent: Full manual configuration path
====================================================================
Step 1. Add your Apple ID to Xcode and download the certificate

Step 2. Find WebDriverAgent project
In terminal, execute command:
echo "$(dirname "$(find "$HOME/.appium" -name WebDriverAgent.xcodeproj)")"

Step 3. Navigate to WebDriverAgent project path in terminal and run below command to setup the project:
mkdir -p Resources/WebDriverAgent.bundle

Step 4. Open WebDriverAgent.xcodeproj in Xcode. For both the WebDriverAgentLib and WebDriverAgentRunner targets, select "Automatically manage signing" in the "General" tab, and then select your Development Team. This should also auto select Signing Certificate.

Step 5. Manually change the bundle id for the target by going into the "Build Settings" tab, and changing the "Product Bundle Identifier" from com.facebook.WebDriverAgentRunner to
something that Xcode will accept (something really unique!)

Step 6. Command to build the project:
xcodebuild -project WebDriverAgent.xcodeproj -scheme WebDriverAgentRunner -destination 'id=<udid>' test -allowProvisioningUpdates

Note: If mirroring device using Quick Time Player at the same, there could be port conflict. In that case, start Appium server with a different port of WebDriverAgent.
appium server --driver-xcuitest-webdriveragent-port 8101

Step 7. Create session with app using Appium Inspector pointing to CLI server

```

**`Appium Driver Management`**

```
Appium driver management (using Appium CLI)
==========================================
List driver:
-> appium driver list
-> appium driver list --installed
-> appium driver list --updates

Install driver:
-> appium driver install <official_driver_name>
-> appium driver install <official_driver_name>@<specific_version_number>
-> appium driver install --source <source> --package <name>
source: npm (default), github, git, local
package: customDriver@1.0.0
Examples:
-> appium driver install --source npm appium-uiautomator2-driver
-> appium driver install --source git https://github.com/appium/appium-uiautomator2-driver.git --package appium-uiautomator2-driver
-> appium driver install --source github appium/appium-uiautomator2-driver --package appium-uiautomator2-driver

Uninstall driver:
-> appium driver uninstall <official_driver_name>

Update driver:
-> appium driver update uiautomator2
-> appium driver update --unsafe
-> appium driver update installed
```

## Appium Driver

| Driver       | Platform    | Description                                                                                             |
| ------------ | ----------- | ------------------------------------------------------------------------------------------------------- |
| UiAutomator2 | Android     | The default and recommended driver for Android. It uses Google's UiAutomator2 framework for automation. |
| XCUITest     | iOS         | The default and recommended driver for iOS. It uses Apple's XCUITest framework for automation.          |
| Espresso     | Android     | An alternative Android driver that provides faster and more reliable automation for hybrid apps.        |
| UIAutomation | iOS         | An older iOS driver, deprecated since iOS 10. It's recommended to use XCUITest instead.                 |
| Selendroid   | Android     | A legacy Android driver, used for devices running Android 2.3 to 4.1. It's rarely used now.             |
| Windows      | Windows     | Used for automating Windows desktop applications.                                                       |
| Mac          | macOS       | Used for automating macOS desktop applications.                                                         |
| Flutter      | Android/iOS | A driver specifically for automating Flutter applications on both Android and iOS.                      |
| Youi         | Android/iOS | A driver for automating applications built with the You.i Engine.                                       |
| Gecko        | Firefox OS  | Used for automating Firefox OS applications. It's not commonly used now.                                |
| Safari       | iOS         | A driver specifically for automating the Safari browser on iOS devices.                                 |
| Chromium     | Android     | A driver for automating Chromium-based browsers on Android.                                             |

## Desired Capabilities

Desired Capabilities are a set of key-value pairs that define the configuration and settings for an Appium test session. They provide information about the device, application, and other parameters needed to set up and run automated tests on mobile devices or emulators.

Desired Capabilities serve several important purposes in Appium:

- Device configuration: They specify which device or emulator to use for testing.
- Application details: They provide information about the app under test, such as its location or package name.
- Automation settings: They configure various automation-related options, like timeout values or logging preferences.
- Platform-specific settings: They allow you to set capabilities specific to iOS or Android platforms.

## Commonly used desired capabilities for both Android and iOS

| Capability            | Android | iOS | Description                                                                          |
| --------------------- | ------- | --- | ------------------------------------------------------------------------------------ |
| platformName          | ✓       | ✓   | Name of the mobile platform ("Android" or "iOS")                                     |
| deviceName            | ✓       | ✓   | Name of the device to automate                                                       |
| platformVersion       | ✓       | ✓   | Version of the mobile OS                                                             |
| app                   | ✓       | ✓   | Path to the mobile app (.apk or .ipa file)                                           |
| automationName        | ✓       | ✓   | Name of the automation engine (e.g., "UiAutomator2" for Android, "XCUITest" for iOS) |
| udid                  | ✓       | ✓   | Unique device identifier (more commonly used for iOS)                                |
| appPackage            | ✓       |     | Java package of the Android app                                                      |
| appActivity           | ✓       |     | Name of the Android activity to launch                                               |
| bundleId              |         | ✓   | Bundle identifier of the iOS app                                                     |
| noReset               | ✓       | ✓   | Don't reset app state before session (true/false)                                    |
| fullReset             | ✓       | ✓   | Perform a complete reset (true/false)                                                |
| newCommandTimeout     | ✓       | ✓   | Time to wait for a new command (in seconds)                                          |
| language              | ✓       | ✓   | Language to set for the device                                                       |
| locale                | ✓       | ✓   | Locale to set for the device                                                         |
| orientation           | ✓       | ✓   | Initial orientation of the device                                                    |
| autoGrantPermissions  | ✓       |     | Automatically grant app permissions (true/false)                                     |
| xcodeOrgId            |         | ✓   | Apple developer team identifier                                                      |
| xcodeSigningId        |         | ✓   | Signing certificate to use for iOS                                                   |
| useNewWDA             |         | ✓   | Use a new WebDriverAgent instance for each session (true/false)                      |
| androidInstallTimeout | ✓       |     | Timeout for installing Android app (in milliseconds)                                 |
| avd                   | ✓       |     | Name of the Android Virtual Device to use                                            |

## Android Desired Capabilities Example

```
{
  "platformName": "Android",
  "deviceName": "Android Emulator",
  "platformVersion": "11.0",
  "automationName": "UiAutomator2",
  "app": "/path/to/your/app.apk",
  "appPackage": "com.example.myapp",
  "appActivity": "com.example.myapp.MainActivity",
  "noReset": true,
  "newCommandTimeout": 6000,
  "autoGrantPermissions": true,
  "language": "en",
  "locale": "US",
  "orientation": "PORTRAIT",
  "androidInstallTimeout": 90000,
  "avd": "Pixel_3a_API_30_x86"
}
```

## iOS Desired Capabilities Example

```
{
  "platformName": "iOS",
  "deviceName": "iPhone 12",
  "platformVersion": "14.5",
  "automationName": "XCUITest",
  "app": "/path/to/your/app.ipa",
  "bundleId": "com.example.myapp",
  "udid": "1234567890abcdef1234567890abcdef12345678",
  "noReset": true,
  "newCommandTimeout": 6000,
  "language": "en",
  "locale": "US",
  "orientation": "PORTRAIT",
  "xcodeOrgId": "ABCDE12345",
  "xcodeSigningId": "iPhone Developer",
  "useNewWDA": true
}
```

**Note:**

For More Information visit following pages

- UiAutomator2 (Android) capabilities:
  https://github.com/appium/appium-uiautomator2-driver?#capabilities

- XCUITest (iOS) capabilities:
  https://github.com/appium/appium-xcuitest-driver#capabilities

## Vendor Prefix

A **vendor prefix** is a way for browser vendors (like Google for Chrome or Apple for Safari) or platform-specific tools to introduce experimental or non-standardized features that may eventually become standard but aren't fully supported across all platforms yet. These prefixes allow developers to use these features and ensure that their applications function correctly on platforms that support them, without breaking functionality on others.

In **Appium**, vendor prefixes help standardize cross-platform support for mobile automation, especially when working with different driver implementations (like iOS and Android). Each platform has unique capabilities and behaviors, and by using vendor-specific prefixes, Appium can maintain compatibility and differentiate between platform-specific options without causing conflicts. Here’s how it works:

- **Example**: For Android and iOS capabilities, Appium uses vendor prefixes (`appium:`) to specify capabilities. For instance, if you have a capability like `appium:appWaitActivity`, the `appium:` prefix indicates it’s a custom or extended capability that’s understood by Appium rather than being a standard W3C capability.

- **Purpose**: The prefix helps keep Appium’s capabilities consistent with the W3C WebDriver standard by distinguishing Appium-specific capabilities from standardized ones, improving both compatibility and future-proofing the code as standards evolve.

This approach allows Appium to evolve with the WebDriver protocol while also catering to platform-specific needs and maintaining backwards compatibility with mobile devices.

# Create Project

1. Create a maven Project.
2. Add following dependencies
   - Java Client
   - testNG

### **Very Important note**:

Please create all packages and class files in src/test/java and not in src/main/java.
Recently, Appium has changed the scope of one of its transitive Selenium dependency (Support Ul package) from "compile" to "runtime". Due to this, the dependency may not resolve under src/main/java. We can certainly try to change the scope to compile, but let's be safe and create everything under src/test/java.
This is where typically test automation resides. If you still want to use src/main/ java, then please add the entire "selenium-java" package as a seperate dependency in pom.xmi. Make sure to match the version with the Selenium version that's shipped with Appium Java

### **Avoid using DesiredCapabilities class**

If you're still using the DesiredCapabilities class to set up your capabilities, it's crucial to stop and update your approach.

As of version 9.2.3, the Appium Java Client has ceased to automatically add the 'appium:' prefix to capabilities (confirmed through this defect: https://github.com/appium/java-client/issues/2184). This means if you continue using the DesiredCapabilities class, you must explicitly prefix non-W3C capabilities with 'appium:'. Failing to do so will result in the following exception:

**java.lang.IllegalArgumentException: Illegal key values seen in w3c capabilities: [app, automationName, deviceName, udid]**

This change hints at a broader trend: the potential future deprecation of the DesiredCapabilities class by the Appium developers. To stay ahead, it's advisable to switch to the recommended Options class. I have covered this in detail in the lecture titled "Create Driver Session using Options Class." You will find this lecture under section titled "First Appium Project". Please ensure you don't skip this critical lecture.

Should you choose to continue using DesiredCapabilities for the time being, make sure to add the 'appium:' prefix to any non-W3C compliant capabilities. The Appium documentation has already updated these capabilities with the 'appium:' prefix, helping you easily distinguish which capabilities are compliant with W3C standards and which are not. You can reference the list of capabilities at the following links:

Common Capabilities: https://appium.io/docs/en/latest/guides/caps/

UiAutomator2 (Android): https://github.com/appium/appium-uiautomator2-driver?tab=readme-ov-file#capabilities

XCUITest (iOS): https://appium.github.io/appium-xcuitest-driver/latest/reference/capabilities/

### Avoid using MobileCapabilityType

MobileCapabilityType class is removed from Java Client version 9.0.0. Please use the key names directly. For example, use "platformName",
"deviceName", "automationName", "udid", "app" and so on. You can get the actual key names from respective driver's GitHub page. For UiAutomator2 driver, the capabilities are listed here: https://github.com/appium/appium-
uiautomator2-driver#capabilities

### Important note:

From Appium 2.0, we don't need to add
`/wd/hub/` to the URL

### Deprecated Code (FYR)

Android (Deprecated Code)

```Java
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

public class CreateDriverSession {
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        // Set capabilities for Appium
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");

        // Build app URL path
        String appUrl = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resource"
            + File.separator + "ApiDemos-debug.apk";

        caps.setCapability(MobileCapabilityType.APP, appUrl);

        // Create Appium server URL
        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        // Initialize Android driver
        AppiumDriver driver = new AndroidDriver(url, caps);
    }
}
```

iOS(Deprecated Code)

```Java
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

public class CreateDriverSession {
public static void main(String[] args) throws MalformedURLException {
    DesiredCapabilities caps = new DesiredCapabilities();

    // Set iOS-specific capabilities
    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
    caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
    caps.setCapability(MobileCapabilityType.UDID, "77F6B8F0-8877-4EDF-8C8C-99DBE64A93FF");

    // Build app URL path
    String appUrl = System.getProperty("user.dir")
        + File.separator + "src"
        + File.separator + "main"
        + File.separator + "resources"
        + File.separator + "UIKitCatalog-iphonesimulator.app";

    caps.setCapability(MobileCapabilityType.APP, appUrl);

    // Create Appium server URL
    URL url = new URL("http://0.0.0.0:4723/wd/hub");

    // Initialize iOS driver
    AppiumDriver driver = new IOSDriver(url, caps);
}
}
```

**Important note:**
These capabilities are sufficient if you are using a free developer account with Apple and have followed the
"Full Manual Configuration" path lecture to setup Appium for real device.
If you are using a paid developer account with Apple and have followed the "Basic Automatic Configuration" path lecture to setup Appium for real device, then you will need to add below two capabilities:

```
caps.setCapability("xcodeOrgld", "enter your team id");
caps.setCapability("codeSigningld", "¡Phone Developer");`
```

## Sample create session code- Android

```Java
package android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CreateDriverSession {
    public static void main(String[] args) throws MalformedURLException {
        // Initialize UiAutomator2Options
        UiAutomator2Options options = new UiAutomator2Options();

        // Build app URL path
        String appUrl = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + "application"
                + File.separator + "ApiDemos-debug.apk";

        System.out.println("App Path: " + appUrl);

        // Set capabilities
        options.setPlatformName("Android")
                .setDeviceName("pixel")
                .setUdid("emulator-5554")
                .setAutomationName("UiAutomator2")
               //  .setApp(appUrl)
               .setA
                .setNewCommandTimeout(Duration.ofSeconds(60));
        URL url = new URL("http://127.0.0.1:4723");

        // Initialize Android driver
        AndroidDriver driver = new AndroidDriver(url, options);
    }
}
```

## Prerequisites for Starting a Driver Session with Simulator

1. **Appium Server**

   - Ensure you have the Appium server installed.

2. **XCUITest Driver**

   - Make sure the XCUITest driver is set up.

3. **Xcode**

   - Install Xcode from the App Store or Apple’s developer website.

4. **Xcode Command Line Tools**

   - Install the command line tools using the following command:
     ```bash
     sudo xcode-select --install
     ```

5. **Install xcpretty**

   - Beautify the logs by installing `xcpretty`:
     ```bash
     sudo gem install xcpretty
     ```

6. **Install Carthage**

   - Install Carthage, a dependency manager required by WebDriverAgent:
     ```bash
     brew install carthage
     ```

7. **Fetch UDID**

   - Get the UDID of your simulator using the command:
     ```bash
     xcrun simctl list
     ```

8. **Build the .app File**

   - Clone or download the iOS UI Catalog project from [GitHub](https://github.com/appium/ios-uicatalog).

   - Navigate to the `UIKitCatalog` folder:

     ```bash
     cd ./ios-uicatalog-master/UIKitCatalog
     ```

   - Install dependencies and build the .app file:

     ```bash
     npm install
     ```

   - The .app file will be created under:
     `    ./UIKitCatalog/build/Release-iphonesimulator/UIKitCatalog-iphonesimulator.app`

## Sample create session code - iOS

```Java
package ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CreateDriverSession {
    public static void main(String[] args) throws MalformedURLException {

        XCUITestOptions options = new XCUITestOptions();

        // Build app URL path
        String appUrl = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + "application"
                + File.separator + "UIKitCatalog-iphonesimulator.app";

        System.out.println("App Path: " + appUrl);

        // Set capabilities
        options.setDeviceName("iPhone 15 Pro").
                setAutomationName("XCUITest").
                setUdid("4E7F8CBE-88A7-41D3-947B-406DD309CA39").
       //       setApp(appUrl); //installation of application
                setBundleId("com.example.apple-samplecode.UICatalog");//launch existing application
        URL url = new URL("http://127.0.0.1:4723");

        // Initialize iOS driver
        IOSDriver driver = new IOSDriver(url, options);
    }
}

```

## How to find appPackage and appActivity?

Launch the app on your device and make sure the activity you want is in focus and enter following commands in respective terminals

Windows:

```
adb shell

dumpsys window displays | grep -E ‘mCurrentFocus’
```

Mac:

```
adb shell

dumpsys window | grep -E mCurrentFocus
```

## To launch existing application - Android

Add following options

```
.setAppActivity(".ApiDemos") // launching existing application
.setAppPackage("io.appium.android.apis")
```

# How to Get iOS Bundle ID Without Xcode/Source Code

## 1. From .app File

1. Right-click the .app file
2. Select "Show Package Contents"
3. Find and open `Info.plist` in Xcode
4. Search for "Bundle identifier"

## 2. From .ipa File

1. Rename the `.ipa` file to `.zip`
2. Right-click the `.zip` file → Open With → Archive Utility
3. Navigate to the created Payload folder
4. Right-click the `.app` file
5. Select "Show Package Contents"
6. Find and open `Info.plist` in Xcode
7. Search for "Bundle identifier"

## 3. From App Store

1. Find the app in Safari's App Store
   ```
   Example URL:
   https://apps.apple.com/us/app/whatsapp-messenger/id310633997
   ```
2. Use the iTunes Lookup API:

   ```
   https://itunes.apple.com/lookup?id=310633997
   ```

   Replace `310633997` with your app's ID number

3. A Text file will be downloaded after navigating the url. Open the file and search for `bundleId`

## To launch existing application - iOS

Add following options

```
  .setBundleId("com.example.apple-samplecode.UICatalog");//launch existing application
```

## Launch Emulator Automatically

Add the following option

```Java
package android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CreateDriverSession {
    public static void main(String[] args) throws MalformedURLException {
        // Initialize UiAutomator2Options
        UiAutomator2Options options = new UiAutomator2Options();

        // Build app URL path
        String appUrl = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + "application"
                + File.separator + "ApiDemos-debug.apk";

        System.out.println("App Path: " + appUrl);

        // Set capabilities
        options.setPlatformName("Android")
                .setDeviceName("pixel")
                .setUdid("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setApp(appUrl)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAvd("Pixel_3a_API_34") //
                .setAvdLaunchTimeout(Duration.ofSeconds(180))
                .setAvdReadyTimeout(Duration.ofSeconds(120));
        URL url = new URL("http://127.0.0.1:4723");

        // Initialize Android driver
        AndroidDriver driver = new AndroidDriver(url, options);
    }
}
```

## Launch Simulator Automatically

```Java
package ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LaunchSimulator {
    public static void main(String[] args) throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        // Set capabilities
        options.setDeviceName("iPhone 15 Pro").
                setAutomationName("XCUITest").
                setUdid("4E7F8CBE-88A7-41D3-947B-406DD309CA39").
        setBundleId("com.example.apple-samplecode.UICatalog").//launch existing application
                setSimulatorStartupTimeout(Duration.ofSeconds(180));// launch timeout for simulator
        URL url = new URL("http://127.0.0.1:4723");

        // Initialize iOS driver
        IOSDriver driver = new IOSDriver(url, options);
    }
}

```

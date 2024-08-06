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



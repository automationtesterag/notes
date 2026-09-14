# Appium – Short Notes (Java)

## Table of Contents

1. [Introduction to Appium](#1-introduction-to-appium)
2. [Advantages and Disadvantages](#2-advantages-and-disadvantages)
3. [Appium vs Other Tools](#3-appium-vs-other-tools)
4. [Prerequisites for Setup](#4-prerequisites-for-setup)
5. [Installation with Java + Maven](#5-installation-with-java--maven)
6. [Appium Architecture](#6-appium-architecture)
7. [Appium Version Changes and Deprecated APIs](#7-appium-version-changes-and-deprecated-apis)
8. [Appium Drivers](#8-appium-drivers)
9. [Sample code of login](#9-sample-code-of-login)
10. [Appium Mobile Locators](#10-appium-mobile-locators)

---

## 1. Introduction to Appium

**Appium** is an open-source, cross-platform automation framework used to automate mobile applications.

### Supported Applications

| Type       | Description                 |
| ---------- | --------------------------- |
| Native     | Android / iOS applications  |
| Hybrid     | Native app + WebView        |
| Mobile Web | Websites on mobile browsers |

### Key Features

* Open source.

* Cross-platform: Android and iOS.

* Supports Java, Python, JavaScript, C#.

* Uses W3C WebDriver protocol.

* Supports real devices and emulators.

* Integrates with Maven, TestNG and CI/CD.

* Supports native, hybrid and mobile web automation.

**Interview Definition:** Appium is a WebDriver-based mobile automation framework used to automate native, hybrid and mobile web applications across Android and iOS.

---

## 2. Advantages and Disadvantages

### Advantages

1. Open source and free.

2. Supports Android and iOS.

3. Reuses Selenium/WebDriver knowledge.

4. Supports multiple programming languages.

5. Works with real devices and emulators.

6. Supports native, hybrid and mobile web.

7. Integrates with Maven, TestNG and CI/CD.

8. Supports extensible drivers and plugins.

### Disadvantages

1. Setup is complex compared with web automation.

2. Execution may be slower than native tools.

3. Android and iOS may require different locators.

4. Requires compatible server, driver and client versions.

5. Device permissions and pop-ups can cause issues.

6. iOS local automation requires macOS and Xcode.

7. WebView automation needs context switching.

8. UI changes can break locators.

---

## 3. Appium vs Other Tools

| Feature        | Appium                | Selenium         | Espresso       | XCUITest    | Detox            |
| -------------- | --------------------- | ---------------- | -------------- | ----------- | ---------------- |
| Main purpose   | Mobile automation     | Web automation   | Android UI     | iOS UI      | React Native E2E |
| Android        | Yes                   | No native apps   | Yes            | No          | Yes              |
| iOS            | Yes                   | No native apps   | No             | Yes         | Yes              |
| Mobile Web     | Yes                   | Web browsers     | No             | No          | No               |
| Language       | Java, Python, JS      | Java, Python, JS | Java/Kotlin    | Swift/Obj-C | JS/TS            |
| Cross-platform | Android + iOS         | Web browsers     | Android only   | iOS only    | RN focused       |
| Best use       | Cross-platform mobile | Web testing      | Android native | iOS native  | React Native E2E |

**Remember:** Appium is not Selenium. Appium uses WebDriver concepts but automates mobile applications.

---

## 4. Prerequisites for Setup

| Software                | Purpose                  |
| ----------------------- | ------------------------ |
| Java JDK                | Run Java automation      |
| Maven                   | Dependency management    |
| IntelliJ IDEA / Eclipse | Write code               |
| Node.js + npm           | Install Appium           |
| Appium Server           | Receives commands        |
| Android Studio          | Android SDK and emulator |
| Android SDK             | Android automation tools |
| UiAutomator2 driver     | Android automation       |
| Appium Inspector        | Inspect elements         |
| Xcode                   | iOS automation on macOS  |

### Environment Variables

**Java:**

```
java -version
javac -version
```

Set `JAVA_HOME` to the JDK installation.

**Android (macOS):**

```
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
```

**Android device:**

```
adb devices
```

Enable Developer Options and USB debugging.

**iOS:** macOS + Xcode + iOS Simulator or real device.

---

## 5. Installation with Java + Maven

### 5.1 Install Java and Maven

```
java -version
mvn -version
```

### 5.2 Install Node.js and Appium

Install Node.js from [https://nodejs.org/](https://nodejs.org/)

```
node -v
npm -v

npm install -g appium
appium -v
```

### 5.3 Install Android Driver

```
appium driver install uiautomator2
appium driver list --installed
```

Start server:

```
appium
```

Default URL:

```
http://127.0.0.1:4723
```

### 5.4 Create Maven Project

IntelliJ IDEA → New Project → Maven → Select JDK → Create.

**Structure:**

```
AppiumJavaAutomation/
├── pom.xml
└── src/test/java/tests/AndroidTest.java
```

### 5.5 Add Maven Dependency

```
<dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>10.1.1</version>
</dependency>

<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.11.0</version>
    <scope>test</scope>
</dependency>
```

Download dependencies:

```
mvn clean install
```

**Note:** Use compatible Java, Appium Java Client, Selenium, server and driver versions.

---

## 6. Appium Architecture

Appium follows a **client-server architecture**. The Java client sends WebDriver commands to the Appium server, which forwards them to the platform driver and mobile device.
The Mermaid diagram didn't render properly. Here is a simple, visible architecture diagram for your Markdown notes.

## Appium Architecture

### Diagram

```
┌──────────────────────────────┐
│       Java Test Script       │
│        TestNG / JUnit        │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│      Appium Java Client      │
│       Selenium APIs          │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        Appium Server         │
│        Port: 4723            │
└──────────────┬───────────────┘
               │
               ▼
        ┌──────────────┐
        │   Platform   │
        │    Driver    │
        └──────┬───────┘
               │
       ┌───────┴────────┐
       ▼                ▼
┌──────────────┐ ┌──────────────┐
│ UiAutomator2 │ │   XCUITest   │
│   Android    │ │     iOS      │
└──────┬───────┘ └──────┬───────┘
       │                │
       ▼                ▼
┌──────────────┐ ┌──────────────┐
│   Android    │ │     iOS      │
│ Device/      │ │ Device/      │
│ Emulator     │ │ Simulator    │
└──────┬───────┘ └──────┬───────┘
       │                │
       └───────┬────────┘
               ▼
┌──────────────────────────────┐
│      Application Under Test  │
│         Mobile App           │
└──────────────────────────────┘
```

### Command flow

```
Java Test
    ↓
Appium Java Client
    ↓
Appium Server
    ↓
Platform Driver
    ↓
Mobile Device
    ↓
Application
    ↓
Response back to Java Test
```

### Component Explanation

| Component | Responsibility |
|---|---|
| Java Test Script | Contains test cases and assertions. |
| Appium Java Client | Sends automation commands from Java. |
| Appium Server | Receives and routes commands. |
| UiAutomator2 | Android automation driver. |
| XCUITest | iOS automation driver. |
| Android Device | Executes Android actions. |
| iOS Device | Executes iOS actions. |
| Application | Application being tested. |

### Example

Java

```
driver.findElement(
    AppiumBy.accessibilityId("Login")
).click();
```

Execution:

```
Java code
   ↓
Appium Java Client
   ↓
Appium Server
   ↓
UiAutomator2 Driver
   ↓
Android Device
   ↓
Login button clicked
```

Interview answer: Appium follows a client-server architecture. The Java client sends WebDriver commands to the Appium server, which forwards them to the appropriate platform driver, such as UiAutomator2 or XCUITest, to execute on the mobile device.

## 7. Appium Version Changes and Deprecated APIs

## 1. Appium Version Overview

| Version | Major Changes |
| --- | --- |
| Appium 1 | Drivers were bundled with Appium. Older JSONWP and MJSONWP protocols were supported. |
| Appium 2 | Drivers and plugins became separately installable. W3C WebDriver became the standard protocol. Minimum Node.js version was 14.17.0. |
| Appium 3 | Remaining deprecated/legacy endpoints were removed, JSONWP-style parameters were dropped in favor of strict W3C parameters, and minimum Node.js/npm versions were raised significantly (Node ^20.19.0 / npm 10+). |

## 2. Appium 1 to Appium 2

### Architecture Changes

Appium 1:

```
Appium Server
    |
    +-- Android Driver
    |
    +-- iOS Driver
    |
    +-- Other Drivers
```

Appium 2:

```
Appium Server
    |
    +-- UiAutomator2 Driver
    |
    +-- XCUITest Driver
    |
    +-- Other Drivers
    |
    +-- Plugins
```

In Appium 2, drivers and plugins are installed separately.

### Server URL Changes

Appium 1:

```java
driver = new AndroidDriver(
        new URL("http://127.0.0.1:4723/wd/hub"),
        options
);
```

Appium 2 and later:

```java
driver = new AndroidDriver(
        new URL("http://127.0.0.1:4723"),
        options
);
```

Note: `java.net.URL(String)` is deprecated as of Java 20. Prefer building the URL via `URI`, as shown in Section 6 (`URI.create("http://127.0.0.1:4723").toURL()`).

### Driver Installation

Appium 1:

```bash
npm install -g appium
```

Appium 2 and later:

```bash
npm install -g appium
appium driver install uiautomator2
appium driver install xcuitest
```

Check installed drivers:

```bash
appium driver list --installed
```

### Capabilities Changes

Old Appium 1 style:

```java
DesiredCapabilities capabilities =
        new DesiredCapabilities();

capabilities.setCapability("platformName", "Android");
capabilities.setCapability("deviceName", "Android Emulator");
capabilities.setCapability("appPackage", "com.example.app");
capabilities.setCapability("appActivity", "com.example.app.MainActivity");
```

Modern Android style:

```java
UiAutomator2Options options =
        new UiAutomator2Options();

options.setPlatformName("Android");
options.setDeviceName("Android Emulator");
options.setAutomationName("UiAutomator2");
options.setAppPackage("com.example.app");
options.setAppActivity("com.example.app.MainActivity");
```

Modern iOS style:

```java
XCUITestOptions options =
        new XCUITestOptions();

options.setPlatformName("iOS");
options.setDeviceName("iPhone 15");
options.setAutomationName("XCUITest");
options.setBundleId("com.example.app");
```

Also note: as of Appium 2, non-standard (driver-specific) capabilities must use the `appium:` vendor prefix (e.g. `appium:deviceName`) when sent as raw W3C JSON, and `automationName` must be set explicitly — it is no longer inferred.

## 3. Appium 2 to Appium 3

Appium 3 is a smaller change compared with Appium 2 — mostly clean-up of legacy behavior rather than a new architecture.

| Area | Appium 2 | Appium 3 |
| --- | --- | --- |
| Protocol | W3C WebDriver (JSONWP params still accepted on some endpoints) | W3C WebDriver only — JSONWP-style parameters removed |
| Driver installation | Separate drivers | Separate drivers (unchanged) |
| Plugin support | Supported | Supported (unchanged) |
| Default server URL | `/` | `/` (unchanged) |
| Node.js | 14.17.0+ | `^20.19.0 \|\| ^22.12.0 \|\| >=24.0.0` required |
| npm | Older versions supported | 10+ required |
| Session creation | `desiredCapabilities` / `requiredCapabilities` still accepted as a legacy fallback | Legacy capability objects fully removed; only the standard W3C `capabilities` object is accepted |
| Timeouts endpoint | Legacy `type`/`ms` params accepted | Only W3C keys accepted: `script`, `pageLoad`, `implicit` |
| Security features | Flag names like `adb_shell` | Flags now require a driver-scoped prefix, e.g. `uiautomator2:adb_shell` |
| Internal web framework | Express v4 | Express v5 (mostly invisible to end users, relevant if you embed Appium in custom middleware) |
| App archive handling | Unzip/extraction logic lived in Appium core | Moved entirely to the driver layer — drivers (e.g. UiAutomator2, XCUITest) must be updated alongside the server |
| Old endpoints | Some older/deprecated endpoints still present | Most deprecated endpoints removed or migrated to driver-specific `mobile:` execute methods |

<sup>Sources: Appium's official "Migrating to Appium 3" guide and the Appium 3 release announcement.</sup>

### Appium 3 Installation

```bash
node -v
npm -v

npm install -g appium

appium -v
```

Install the required driver:

```bash
appium driver install uiautomator2
```

Start the Appium Server:

```bash
appium
```

Most normal test code remains the same when moving from Appium 2 to Appium 3. The main things to check before upgrading:

- Bump Node.js to `^20.19.0 || ^22.12.0 || >=24.0.0` and npm to `10+` in your dev and CI environments.
- Update any security feature flag names to include the driver prefix (e.g. `uiautomator2:adb_shell`).
- Remove any code or test config that still sends legacy `desiredCapabilities`/`requiredCapabilities` payloads or the old `timeouts` `type`/`ms` params directly (most official clients, including the Java client, already send W3C-correct payloads, so this mainly affects raw HTTP/JSON usage).
- Upgrade platform drivers (UiAutomator2, XCUITest, etc.) alongside the server, since app installation logic moved into the drivers.

## 4. Deprecated and Replaced Java Client APIs

### 4.1 MobileBy to AppiumBy

Old code:

```java
import io.appium.java_client.MobileBy;

driver.findElement(
        MobileBy.AccessibilityId("Login")
).click();
```

New code:

```java
import io.appium.java_client.AppiumBy;

driver.findElement(
        AppiumBy.accessibilityId("Login")
).click();
```

### 4.2 MobileElement to WebElement

Old code:

```java
MobileElement username =
        driver.findElement(
                MobileBy.id("username")
        );
```

New code:

```java
WebElement username =
        driver.findElement(
                AppiumBy.id("username")
        );

username.sendKeys("testuser");
```

Import:

```java
import org.openqa.selenium.WebElement;
```

This also applies to the platform-specific subclasses `AndroidElement` and `IOSElement`, which were removed in favor of the plain Selenium `WebElement`.

### 4.3 DesiredCapabilities to Options

Old code:

```java
DesiredCapabilities capabilities =
        new DesiredCapabilities();

capabilities.setCapability("platformName", "Android");
```

New Android code:

```java
UiAutomator2Options options =
        new UiAutomator2Options();

options.setPlatformName("Android");
```

New iOS code:

```java
XCUITestOptions options =
        new XCUITestOptions();

options.setPlatformName("iOS");
```

| Old API | Replacement |
| --- | --- |
| `DesiredCapabilities` | `UiAutomator2Options` |
| `DesiredCapabilities` | `XCUITestOptions` |
| `MobileCapabilityType` | Platform-specific options |
| `AndroidMobileCapabilityType` | `UiAutomator2Options` |
| `IOSMobileCapabilityType` | `XCUITestOptions` |

### 4.4 launchApp to activateApp

Old code:

```java
driver.launchApp();
```

New code:

```java
driver.activateApp("com.example.app");
```

### 4.5 closeApp to terminateApp

Old code:

```java
driver.closeApp();
```

New code:

```java
driver.terminateApp("com.example.app");
```

### 4.6 setValue to sendKeys

Old code:

```java
element.setValue("testuser");
```

New code:

```java
element.sendKeys("testuser");
```

Example:

```java
driver.findElement(
        AppiumBy.id("username")
).sendKeys("testuser");
```

### 4.7 TouchAction / MultiTouchAction to W3C Actions and Mobile Gesture Commands

`TouchAction` and `MultiTouchAction` (and the `PerformsTouchActions` interface behind them) were removed from the Java client in the Appium 2 era. There are two modern replacements:

**Option A — raw W3C Actions API** (most portable, most verbose):

```java
PointerInput finger =
        new PointerInput(PointerInput.Kind.TOUCH, "finger");

Sequence tap = new Sequence(finger, 1);

tap.addAction(
        finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                300,
                500
        )
);

tap.addAction(
        finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())
);

tap.addAction(
        finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())
);

driver.perform(List.of(tap));
```

Required imports:

```java
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;
```

**Option B — driver-provided mobile gesture commands** (recommended by the Appium team for common gestures like tap, swipe, scroll, long-press, since it's simpler and driver-optimized):

```java
Map<String, Object> params = new HashMap<>();
params.put("elementId", ((RemoteWebElement) element).getId());

driver.executeScript("mobile: tap", params);
```

Both UiAutomator2 and XCUITest expose a family of `mobile:` execute-script commands (e.g. `mobile: swipeGesture`, `mobile: scrollGesture`, `mobile: longClickGesture`) that are generally preferred over hand-rolling W3C Action sequences for standard gestures.

### 4.8 findBy Methods to findElement

Old code:

```java
driver.findByAccessibilityId("Login").click();
```

New code:

```java
driver.findElement(
        AppiumBy.accessibilityId("Login")
).click();
```

Other examples:

```java
driver.findElement(AppiumBy.id("username"));

driver.findElement(AppiumBy.xpath("//button[@text='Login']"));

driver.findElement(AppiumBy.className("android.widget.Button"));
```

### 4.9 TimeUnit to Duration

Old code:

```java
driver.manage()
        .timeouts()
        .implicitlyWait(10, TimeUnit.SECONDS);
```

New code:

```java
driver.manage()
        .timeouts()
        .implicitlyWait(Duration.ofSeconds(10));
```

Import:

```java
import java.time.Duration;
```

## 5. Migration Summary

| Old API or Approach | Modern Replacement |
| --- | --- |
| `MobileBy` | `AppiumBy` |
| `MobileElement` | `WebElement` |
| `IOSElement` | `WebElement` |
| `AndroidElement` | `WebElement` |
| `DesiredCapabilities` | `UiAutomator2Options` / `XCUITestOptions` |
| `MobileCapabilityType` | Driver-specific options |
| `launchApp()` | `activateApp()` |
| `closeApp()` | `terminateApp()` |
| `setValue()` | `sendKeys()` |
| `TouchAction` | W3C Actions or `mobile:` gesture commands |
| `MultiTouchAction` | W3C Actions or `mobile:` gesture commands |
| `findByAccessibilityId()` | `findElement(AppiumBy.accessibilityId())` |
| `TimeUnit` | `Duration` |
| `/wd/hub` | `/` |
| `desiredCapabilities` / `requiredCapabilities` JSON fields (Appium 3) | Standard W3C `capabilities` object only |
| `timeouts` legacy `type`/`ms` params (Appium 3) | W3C keys: `script`, `pageLoad`, `implicit` |
| Unscoped security flags, e.g. `adb_shell` (Appium 3) | Driver-scoped flags, e.g. `uiautomator2:adb_shell` |
| Appium Desktop Inspector | Appium Inspector |
| Older Android driver | UiAutomator2 |
| Older iOS driver | XCUITest |

## 6. Recommended Modern Code

```java
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URI;
import java.time.Duration;

public class AndroidTest {

    public static void main(String[] args) throws Exception {

        UiAutomator2Options options =
                new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.example.app");
        options.setAppActivity("com.example.app.MainActivity");

        AndroidDriver driver =
                new AndroidDriver(
                        URI.create("http://127.0.0.1:4723").toURL(),
                        options
                );

        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.findElement(
                AppiumBy.accessibilityId("Login")
        ).click();

        driver.quit();
    }
}
```

### Recommended Practices

* Use `AppiumBy` instead of `MobileBy`.
* Use `WebElement` instead of `MobileElement` / `AndroidElement` / `IOSElement`.
* Use `UiAutomator2Options` for Android.
* Use `XCUITestOptions` for iOS, and set `automationName` explicitly.
* Use `activateApp()` instead of `launchApp()`.
* Use `terminateApp()` instead of `closeApp()`.
* Use `sendKeys()` instead of `setValue()`.
* Use W3C Actions or driver `mobile:` gesture commands instead of `TouchAction`/`MultiTouchAction`.
* Use `Duration` instead of `TimeUnit`.
* Use the Appium server URL without `/wd/hub`.
* If upgrading to Appium 3: bump Node.js to `^20.19.0 || ^22.12.0 || >=24.0.0`, npm to `10+`, update driver-scoped security flag names, and upgrade platform drivers alongside the server.

## 8. Appium Drivers

Appium drivers fall into two groups: **official drivers**, maintained by the Appium team, and **other/community drivers**, maintained by third parties. Several drivers/names in the original list are outdated (e.g. the standalone "Mac" driver, Selendroid, and Firefox OS) — the table below reflects the current driver ecosystem.

### Official drivers (maintained by the Appium team)

| Driver | Installation Key | Platform(s) | Mode(s) | Description |
| --- | --- | --- | --- | --- |
| UiAutomator2 | `uiautomator2` | Android, Android TV, Android Wear | Native, Hybrid, Web | The default and recommended driver for Android. Uses Google's UiAutomator2 framework. |
| XCUITest | `xcuitest` | iOS, iPadOS, tvOS | Native, Hybrid, Web | The default and recommended driver for iOS. Uses Apple's XCUITest framework. |
| Espresso | `espresso` | Android | Native | An alternative Android driver built on Google's Espresso framework; often faster for native-only apps than UiAutomator2. |
| Mac2 | `mac2` | macOS | Native | Used for automating macOS desktop applications. Replaced the older, now-unsupported "Mac" driver. |
| Windows | `windows` | Windows | Native | Used for automating Windows desktop applications. Note: only the Node.js driver layer is maintained by the Appium team — the underlying WinAppDriver executable (provided by Microsoft) has not been updated since 2022. |
| Safari | `safari` | macOS, iOS | Web | Automates the Safari browser on macOS and iOS. |
| Chromium | `chromium` | macOS, Windows, Linux | Web | Automates desktop and mobile Chromium-based browsers (Chrome, Edge, etc.). |
| Gecko | `gecko` | macOS, Windows, Linux, Android | Web | Automates Gecko-based browsers (Firefox). Not related to the old, discontinued Firefox OS. |

### Other drivers (community-maintained)

| Driver | Platform(s) | Mode | Notes |
| --- | --- | --- | --- |
| Flutter | Android, iOS | Native | For automating apps built with Flutter. |
| Youi | Android, iOS, macOS, Linux, tvOS | Native | For apps built with the You.i Engine. |
| Tizen / TizenTV | Android / Samsung TV | Native / Web | For Tizen and Samsung Smart TV apps. |
| LG WebOS | LG TV | Web | For LG webOS TV apps. |
| Roku | Roku | Native | For Roku apps. |
| Linux | Linux | Native | For Linux desktop apps. |
| NovaWindows | Windows | Native | Alternative Windows automation driver. |

### Deprecated / no longer relevant

| Driver | Status |
| --- | --- |
| UIAutomation (old iOS driver) | Deprecated since iOS 10 and no longer usable on modern iOS versions or maintained. Use XCUITest. |
| Selendroid | Legacy driver for Android 2.3–4.1; effectively unsupported/unmaintained today and not part of the current official or community driver listing. Use UiAutomator2. |

Install any driver with:

```bash
appium driver install <installation key>
```

List installed/available drivers:

```bash
appium driver list --installed
```

## Common Capabilities (set via Options classes)

Appium sessions are configured through **capabilities** — the specific config values (device, app, automation engine, timeouts, etc.) that control the session. Rather than building a raw capabilities object, use the driver-specific Options class (`UiAutomator2Options` for Android, `XCUITestOptions` for iOS) — it's less error-prone and is the current recommended approach.

| Setting | Android setter | iOS setter | Description |
| --- | --- | --- | --- |
| Platform | `setPlatformName("Android")` | `setPlatformName("iOS")` | Name of the mobile platform |
| Device name | `setDeviceName(...)` | `setDeviceName(...)` | Name of the device to automate |
| Platform version | `setPlatformVersion(...)` | `setPlatformVersion(...)` | Version of the mobile OS |
| App path | `setApp(...)` | `setApp(...)` | Path to the app (.apk/.aab for Android, .ipa/.app for iOS), or a URL to a remotely hosted app |
| Automation engine | `setAutomationName("UiAutomator2")` (or `"Espresso"`) | `setAutomationName("XCUITest")` | Required explicitly since Appium 2 — no longer inferred |
| Device UDID | `setUdid(...)` | `setUdid(...)` | Target a specific real device |
| App package/activity | `setAppPackage(...)` / `setAppActivity(...)` | — | Java package and activity to launch |
| Bundle ID | — | `setBundleId(...)` | iOS app's bundle identifier |
| No reset | `setNoReset(true)` | `setNoReset(true)` | Don't reset app state before session |
| Full reset | `setFullReset(true)` | `setFullReset(true)` | Complete reset, including uninstalling the app |
| New command timeout | `setNewCommandTimeout(Duration.ofSeconds(...))` | `setNewCommandTimeout(Duration.ofSeconds(...))` | Time to wait for a new command before ending the session |
| Language / locale | `setLanguage(...)` / `setLocale(...)` | `setLanguage(...)` / `setLocale(...)` | Device language and locale |
| Orientation | `setOrientation(ScreenOrientation.PORTRAIT)` | `setOrientation(ScreenOrientation.PORTRAIT)` | Initial device orientation |
| Auto-grant permissions | `setAutoGrantPermissions(true)` | — | Grant all manifest permissions at install time |
| Xcode signing | — | `setXcodeOrgId(...)` / `setXcodeSigningId(...)` | Apple developer team ID and signing certificate |
| Prebuilt WebDriverAgent | — | `setUsePrebuiltWDA(true)` | Speeds up session start. Replaces the older, removed `useNewWDA` capability. |
| Install timeout | `setAndroidInstallTimeout(Duration.ofMillis(...))` | — | Timeout for installing the app |
| Emulator (AVD) | `setAvd(...)` | — | Name of the Android Virtual Device Appium should boot automatically if not already running |

## Android Options Example

```java
UiAutomator2Options options = new UiAutomator2Options();
options.setPlatformName("Android");
options.setDeviceName("Android Emulator");
options.setPlatformVersion("11.0");
options.setAutomationName("UiAutomator2");
options.setApp("/path/to/your/app.apk");
options.setAppPackage("com.example.myapp");
options.setAppActivity("com.example.myapp.MainActivity");
options.setNoReset(true);
options.setNewCommandTimeout(Duration.ofSeconds(6000));
options.setAutoGrantPermissions(true);
options.setLanguage("en");
options.setLocale("US");
options.setOrientation(ScreenOrientation.PORTRAIT);
options.setAndroidInstallTimeout(Duration.ofMillis(90000));
options.setAvd("Pixel_3a_API_30_x86");
```

## iOS Options Example

```java
XCUITestOptions options = new XCUITestOptions();
options.setPlatformName("iOS");
options.setDeviceName("iPhone 12");
options.setPlatformVersion("14.5");
options.setAutomationName("XCUITest");
options.setApp("/path/to/your/app.ipa");
options.setBundleId("com.example.myapp");
options.setUdid("1234567890abcdef1234567890abcdef12345678");
options.setNoReset(true);
options.setNewCommandTimeout(Duration.ofSeconds(6000));
options.setLanguage("en");
options.setLocale("US");
options.setOrientation(ScreenOrientation.PORTRAIT);
options.setXcodeOrgId("ABCDE12345");
options.setXcodeSigningId("iPhone Developer");
options.setUsePrebuiltWDA(true);
```

## 9. Sample code of login
### Android

Java

```
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

public class AndroidLoginTest {

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.example.app");
        options.setAppActivity("com.example.app.LoginActivity");
        options.setNoReset(true);

        driver = new AndroidDriver(
                URI.create("http://127.0.0.1:4723").toURL(),
                options
        );
    }

    @Test
    public void loginTest() {

        driver.findElement(
                AppiumBy.id("com.example.app:id/username")
        ).sendKeys("testuser");

        driver.findElement(
                AppiumBy.id("com.example.app:id/password")
        ).sendKeys("Password@123");

        driver.findElement(
                AppiumBy.id("com.example.app:id/loginButton")
        ).click();

        Assert.assertTrue(
                driver.findElement(
                        AppiumBy.id("com.example.app:id/homeScreen")
                ).isDisplayed()
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
```

### iOS

Java

```
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

public class IOSLoginTest {

    private IOSDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        XCUITestOptions options = new XCUITestOptions();

        options.setPlatformName("iOS");
        options.setPlatformVersion("17.5");
        options.setDeviceName("iPhone 15");
        options.setAutomationName("XCUITest");
        options.setBundleId("com.example.app");

        driver = new IOSDriver(
                URI.create("http://127.0.0.1:4723").toURL(),
                options
        );
    }

    @Test
    public void loginTest() {

        driver.findElement(
                AppiumBy.accessibilityId("username")
        ).sendKeys("testuser");

        driver.findElement(
                AppiumBy.accessibilityId("password")
        ).sendKeys("Password@123");

        driver.findElement(
                AppiumBy.accessibilityId("Login")
        ).click();

        Assert.assertTrue(
                driver.findElement(
                        AppiumBy.accessibilityId("Home Screen")
                ).isDisplayed()
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
```
## 10. Appium Mobile Locators

### 1. Overview

Appium finds elements the same way Selenium does — via a `By`/locator strategy passed to `findElement()`/`findElements()`. In addition to the standard Selenium locators, Appium adds several **mobile-specific locator strategies** exposed through the `AppiumBy` class (Java client), which map to platform-native automation frameworks (UiAutomator2/Espresso for Android, XCUITest for iOS).

Use `AppiumBy`, not the deprecated `MobileBy` (see the Deprecated APIs notes) — `MobileBy` has been removed in current Java client versions.

```java
import io.appium.java_client.AppiumBy;
```

### 2. Locator Strategies Summary

| Strategy | Platform | Java client method | Backed by |
| --- | --- | --- | --- |
| Resource/element ID | Android, iOS | `AppiumBy.id(...)` | Native element ID (`resource-id` on Android, `id`/name on iOS) |
| Accessibility ID | Android, iOS | `AppiumBy.accessibilityId(...)` | `content-desc` (Android) / accessibility identifier (iOS) |
| Class name | Android, iOS | `AppiumBy.className(...)` | Native UI class, e.g. `android.widget.Button`, `XCUIElementTypeButton` |
| XPath | Android, iOS | `AppiumBy.xpath(...)` | XML representation of the page/view hierarchy |
| Android UiAutomator | Android only | `AppiumBy.androidUIAutomator(...)` | Google's UiAutomator2 `UiSelector`/`UiScrollable` DSL |
| Android DataMatcher | Android only | `AppiumBy.androidDataMatcher(...)` | Espresso `DataMatcher` JSON (Espresso driver) |
| Android ViewMatcher | Android only | `AppiumBy.androidViewMatcher(...)` | Espresso `ViewMatcher` JSON (Espresso driver) |
| Android View Tag | Android only | `AppiumBy.androidViewTag(...)` | Espresso view tag (Espresso driver) |
| iOS Class Chain | iOS only | `AppiumBy.iOSClassChain(...)` | Apple's class-chain query language (XCUITest) |
| iOS NsPredicate | iOS only | `AppiumBy.iOSNsPredicateString(...)` | Apple's `NSPredicate` query language (XCUITest) |
| Image | Android, iOS | `AppiumBy.image(...)` | Base64-encoded template image, matched visually on screen (experimental) |
| Custom | Android, iOS | `AppiumBy.custom(...)` | Delegates to a custom "element finding" plugin |

Note: `name` (plain Selenium `By.name`) is **not** a supported native mobile locator strategy — it only works inside webviews/hybrid contexts. For native elements use `accessibilityId` instead.

### 3. Android Examples

```java
// id — by resource-id
driver.findElement(AppiumBy.id("com.example.android:id/username"));

// accessibilityId — by content-desc
driver.findElement(AppiumBy.accessibilityId("Login"));

// className — by native UI class
driver.findElement(AppiumBy.className("android.widget.Button"));

// xpath — last resort, see best practices below
driver.findElement(AppiumBy.xpath("//android.widget.Button[@text='Submit']"));

// androidUIAutomator — UiAutomator2 UiSelector DSL
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.example.android:id/password\")"
        )
);

// androidUIAutomator + UiScrollable — scroll until a text is found (common pattern for long lists)
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().textContains(\"Settings\"))"
        )
);

// androidDataMatcher — Espresso driver only; JSON describing a Hamcrest DataMatcher
// Espresso equivalent: onData(hasEntry("title", "TextClock"))
driver.findElement(
        AppiumBy.androidDataMatcher(
                "{\"name\": \"hasEntry\", \"args\": [\"title\", \"TextClock\"]}"
        )
);

// androidViewMatcher — Espresso driver only; JSON describing a Hamcrest ViewMatcher for onView()
// Espresso equivalent: onView(withText("Submit"))
driver.findElement(
        AppiumBy.androidViewMatcher(
                "{\"name\": \"withText\", \"args\": [\"Submit\"]}"
        )
);

// androidViewTag — Espresso driver only; matches a view's tag set via view.setTag(...)
driver.findElement(AppiumBy.androidViewTag("submit_button_tag"));

// image — cross-platform, experimental; base64-encoded template image
String base64Template = Base64.getEncoder().encodeToString(imageBytes);
driver.findElement(AppiumBy.image(base64Template));

// custom — delegates to a custom element-finding plugin registered via the
// `customFindModules` capability (e.g. a "my-finder" plugin registered under that name)
driver.findElement(AppiumBy.custom("my-finder:some-selector-string"));
```

### 4. iOS Examples

```java
// id — by native id/name (uncommon on iOS, but supported)
driver.findElement(AppiumBy.id("username"));

// accessibilityId — by accessibility identifier
driver.findElement(AppiumBy.accessibilityId("Login"));

// className — by XCUIElementType
driver.findElement(AppiumBy.className("XCUIElementTypeButton"));

// xpath — last resort, see best practices below
driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Submit']"));

// iOSNsPredicateString — NSPredicate query, very flexible, faster than XPath
driver.findElement(
        AppiumBy.iOSNsPredicateString(
                "label == 'Username' AND type == 'XCUIElementTypeTextField'"
        )
);

// iOSClassChain — structured, positional queries; faster than XPath
driver.findElement(
        AppiumBy.iOSClassChain(
                "**/XCUIElementTypeCell[`name BEGINSWITH \"P\"`]/XCUIElementTypeButton[4]"
        )
);

// image — cross-platform, experimental; base64-encoded template image
String base64Template = Base64.getEncoder().encodeToString(imageBytes);
driver.findElement(AppiumBy.image(base64Template));

// custom — delegates to a custom element-finding plugin registered via the
// `customFindModules` capability (e.g. a "my-finder" plugin registered under that name)
driver.findElement(AppiumBy.custom("my-finder:some-selector-string"));
```

### 5. Image Locator (experimental, cross-platform)

Matches a template image against the current screen — useful for canvas/game UIs or elements with no accessible attributes at all.

```java
String base64Template = Base64.getEncoder().encodeToString(imageBytes);
driver.findElement(AppiumBy.image(base64Template));
```

This is slower and less reliable than attribute-based locators, so it's best reserved for elements that genuinely can't be located any other way (e.g. custom-rendered/canvas UI).

### 6. Best-Practice Ranking (fastest/most stable → slowest/most fragile)

1. **`accessibilityId`** — Preferred wherever possible. Stable across app updates, fast to resolve, works identically on both platforms if the app sets `content-desc`/accessibility identifiers consistently.
2. **`id`** (resource-id / native id) — Nearly as fast and stable, as long as devs don't change IDs often.
3. **Platform DSLs** — `androidUIAutomator` (Android) and `iOSClassChain` / `iOSNsPredicateString` (iOS). More verbose but far faster than XPath, and support powerful queries (scrolling, text-contains, positional selection) natively.
4. **`className`** — Useful combined with an index or scoped `findElements`, but rarely unique on its own.
5. **`xpath`** — Most flexible (works cross-platform, can express complex hierarchy relationships) but also the slowest and most fragile: it walks the entire accessibility/view tree, and locators break easily when layout structure changes. Treat as a last resort.
6. **`image`** — Last resort only, for elements with no other identifying attributes.

### 7. Tips

- Use the **Appium Inspector** (not the retired Appium Desktop Inspector) to explore the live element tree and get suggested locators for a running session.
- Prefer asking developers to add stable `accessibility label`/`content-desc` values to key elements rather than relying on XPath against a UI that may be restyled.
- `findElements` (plural) returns an empty list instead of throwing when nothing matches — useful for existence checks without try/catch.
- Locator strategies that are platform-specific (`androidUIAutomator`, `iOSClassChain`, `iOSNsPredicateString`, `androidDataMatcher`, `androidViewMatcher`, `androidViewTag`) will throw an error if used against the wrong platform/driver — guard platform-specific locator code accordingly in cross-platform test suites.

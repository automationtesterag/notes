# Appium – Short Notes (Java)

## Table of Contents

1. [Introduction to Appium](#1-introduction-to-appium)
2. [Advantages and Disadvantages](#2-advantages-and-disadvantages)
3. [Appium vs Other Tools](#3-appium-vs-other-tools)
4. [Prerequisites for Setup](#4-prerequisites-for-setup)
5. [Installation with Java + Maven](#5-installation-with-java--maven)
6. [Appium Architecture](#6-appium-architecture)
7. [Appium Version Changes and Deprecated APIs](#7-appium-version-changes-and-deprecated-apis)
8. [Sample code of login](#8-sample-code-of-login)

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
  
## 8. Sample code of login
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

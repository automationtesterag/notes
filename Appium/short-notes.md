# Appium – Short Notes (Java)

## Table of Contents

1. [Introduction to Appium](#1-introduction-to-appium)
2. [Advantages and Disadvantages](#2-advantages-and-disadvantages)
3. [Appium vs Other Tools](#3-appium-vs-other-tools)
4. [Prerequisites for Setup](#4-prerequisites-for-setup)
5. [Installation with Java + Maven](#5-installation-with-java--maven)
6. [Appium Architecture](#6-appium-architecture)
7. [Sample code of login](#7-sample-code-of-login)

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


## 7. Sample code of login
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

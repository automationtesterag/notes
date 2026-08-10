# Mobile Automation — Appium + WebdriverIO Quick Notes

## 1. Mobile Automation

**Appium** is an open-source framework used to automate native, hybrid, and mobile web applications.

| Platform | Automation Engine |
| -------- | ----------------- |
| Android  | `UiAutomator2`    |
| iOS      | `XCUITest`        |

---

# 2. Common Capabilities

| Capability                    | Android                           | iOS                                   |
| ----------------------------- | --------------------------------- | ------------------------------------- |
| `platformName`                | `Android`                         | `iOS`                                 |
| `appium:automationName`       | `UiAutomator2`                    | `XCUITest`                            |
| `appium:deviceName`           | Device name                       | Device name                           |
| `appium:platformVersion`      | Android version                   | iOS version                           |
| `appium:udid`                 | Device ID                         | Device ID                             |
| `appium:app`                  | `.apk`                            | `.app` / `.ipa`                       |
| `appium:appPackage`           | App package                       | —                                     |
| `appium:appActivity`          | Launch activity                   | —                                     |
| `appium:bundleId`             | —                                 | App bundle ID                         |
| `appium:noReset`              | Preserve app state                | Preserve app state                    |
| `appium:autoGrantPermissions` | Grant permissions automatically   | —                                     |
| `appium:autoAcceptAlerts`     | —                                 | Accept alerts automatically           |
| `appium:autoDismissAlerts`    | —                                 | Dismiss alerts automatically          |
| `appium:systemPort`           | Unique port for parallel sessions | —                                     |
| `appium:wdaLocalPort`         | —                                 | Unique WDA port for parallel sessions |

---

# 3. Android Capabilities

### Java + Appium

```java
UiAutomator2Options options = new UiAutomator2Options();

options.setPlatformName("Android");              // Mobile platform
options.setAutomationName("UiAutomator2");       // Android automation engine
options.setDeviceName("Pixel_7");                // Device/emulator name
options.setPlatformVersion("15");                // Android OS version
options.setUdid("emulator-5554");                // Device ID

options.setApp("/path/to/app.apk");              // APK path
options.setAppPackage("com.example.app");        // App package
options.setAppActivity(".MainActivity");         // Launch activity

options.setNoReset(true);                        // Preserve app data/state
options.setAutoGrantPermissions(true);           // Automatically grant permissions
```

### WebdriverIO + TypeScript

```ts
capabilities: [{
    platformName: 'Android',                     // Mobile platform
    'appium:automationName': 'UiAutomator2',     // Android automation engine
    'appium:deviceName': 'Pixel_7',              // Device/emulator name
    'appium:platformVersion': '15',              // Android OS version
    'appium:udid': 'emulator-5554',              // Device ID

    'appium:app': '/path/to/app.apk',            // APK path
    'appium:appPackage': 'com.example.app',      // App package
    'appium:appActivity': '.MainActivity',       // Launch activity

    'appium:noReset': true,                      // Preserve app state
    'appium:autoGrantPermissions': true           // Grant permissions automatically
}]
```

---

# 4. iOS Capabilities

### Java + Appium

```java
XCUITestOptions options = new XCUITestOptions();

options.setPlatformName("iOS");                  // Mobile platform
options.setAutomationName("XCUITest");           // iOS automation engine
options.setDeviceName("iPhone 15");              // Device/simulator name
options.setPlatformVersion("18");                // iOS version
options.setUdid("YOUR_DEVICE_UDID");             // Device ID

options.setApp("/path/to/app.ipa");              // APP/IPA path
options.setBundleId("com.example.app");          // App bundle ID

options.setNoReset(true);                        // Preserve app state
options.setAutoAcceptAlerts(true);               // Automatically accept alerts
options.setAutoDismissAlerts(false);             // Automatically dismiss alerts
```

### WebdriverIO + TypeScript

```ts
capabilities: [{
    platformName: 'iOS',                          // Mobile platform
    'appium:automationName': 'XCUITest',          // iOS automation engine
    'appium:deviceName': 'iPhone 15',             // Device/simulator name
    'appium:platformVersion': '18',               // iOS version
    'appium:udid': 'YOUR_DEVICE_UDID',            // Device ID

    'appium:app': '/path/to/app.ipa',             // APP/IPA path
    'appium:bundleId': 'com.example.app',         // App bundle ID

    'appium:noReset': true,                       // Preserve app state
    'appium:autoAcceptAlerts': true,              // Automatically accept alerts
    'appium:autoDismissAlerts': false             // Automatically dismiss alerts
}]
```

---

# 5. Parallel Execution

Each parallel mobile session needs its own communication port.

### Android

```java
options.setSystemPort(8201);       // Unique UiAutomator2 port
```

```ts
'appium:systemPort': 8201         // Unique UiAutomator2 port
```

### iOS

```java
options.setWdaLocalPort(8101);    // Unique WebDriverAgent port
```

```ts
'appium:wdaLocalPort': 8101       // Unique WebDriverAgent port
```

```text
Android Device 1 → systemPort = 8201
Android Device 2 → systemPort = 8202

iPhone 1 → wdaLocalPort = 8101
iPhone 2 → wdaLocalPort = 8102
```

---

# 6. Element Actions

| Action      | Java + Appium                   | WDIO                                  |
| ----------- | ------------------------------- | ------------------------------------- |
| Click / Tap | `element.click();`              | `await element.click();`              |
| Enter text  | `element.sendKeys("test");`     | `await element.setValue("test");`     |
| Clear       | `element.clear();`              | `await element.clearValue();`         |
| Get text    | `element.getText();`            | `await element.getText();`            |
| Displayed   | `element.isDisplayed();`        | `await element.isDisplayed();`        |
| Enabled     | `element.isEnabled();`          | `await element.isEnabled();`          |
| Attribute   | `element.getAttribute("text");` | `await element.getAttribute("text");` |

### Example

```java
driver.findElement(AppiumBy.accessibilityId("Login")).click();

driver.findElement(AppiumBy.accessibilityId("Username"))
      .sendKeys("testuser");
```

```ts
await $('~Login').click();

await $('~Username').setValue('testuser');
```

---

# 7. Gestures

### Swipe

```java
((JavascriptExecutor) driver).executeScript(
    "mobile: swipeGesture",
    Map.of("direction", "up", "percent", 0.75)
);
```

```ts
await driver.swipe({
    direction: 'up',
    percent: 75
});
```

### Scroll

```java
((JavascriptExecutor) driver).executeScript(
    "mobile: scrollGesture",
    Map.of(
        "left", 100,
        "top", 500,
        "width", 800,
        "height", 1000,
        "direction", "down",
        "percent", 0.75
    )
);
```

```ts
await driver.swipe({
    direction: 'up',
    percent: 75
});
```

### Long Press

```java
new TouchAction<>(driver)
    .longPress(ElementOption.element(
        driver.findElement(AppiumBy.accessibilityId("Item"))
    ))
    .release()
    .perform();
```

```ts
await $('~Item').longPress();
```

---

# 8. App Actions

| Action     | Java                                                | WDIO                                            |
| ---------- | --------------------------------------------------- | ----------------------------------------------- |
| Activate   | `driver.activateApp("com.example.app");`            | `await driver.activateApp("com.example.app");`  |
| Terminate  | `driver.terminateApp("com.example.app");`           | `await driver.terminateApp("com.example.app");` |
| Background | `driver.runAppInBackground(Duration.ofSeconds(5));` | `await driver.background(5);`                   |
| Reset      | `driver.resetApp();`                                | `await driver.reset();`                         |
| Keyboard   | `driver.hideKeyboard();`                            | `await driver.hideKeyboard();`                  |

---

# 9. Device Actions

### Java

```java
driver.navigate().back();                         // Back
driver.rotate(ScreenOrientation.LANDSCAPE);       // Landscape
driver.rotate(ScreenOrientation.PORTRAIT);        // Portrait
driver.lockDevice();                              // Lock
driver.unlockDevice();                            // Unlock
```

### WDIO

```ts
await driver.back();                              // Back
await driver.setOrientation('LANDSCAPE');         // Landscape
await driver.setOrientation('PORTRAIT');          // Portrait
await driver.lock();                              // Lock
await driver.unlock();                            // Unlock
```

---

# 10. Alerts

### Java

```java
driver.switchTo().alert().accept();                // Accept
driver.switchTo().alert().dismiss();               // Dismiss

String text = driver.switchTo().alert().getText(); // Get text
```

### WDIO

```ts
await driver.acceptAlert();                        // Accept
await driver.dismissAlert();                       // Dismiss

const text = await driver.getAlertText();          // Get text
```

---

# 11. Hybrid Applications

Hybrid apps contain both **Native** and **WebView** screens.

### Java

```java
driver.getContextHandles();                        // Get available contexts

driver.context("WEBVIEW_com.example.app");        // Switch to WebView

driver.context("NATIVE_APP");                      // Switch to Native
```

### WDIO

```ts
await driver.getContexts();                        // Get available contexts

await driver.switchContext('WEBVIEW_com.example.app'); // WebView

await driver.switchContext('NATIVE_APP');          // Native
```

### Typical Flow

```text
NATIVE_APP
     ↓
WEBVIEW
     ↓
Perform web actions
     ↓
NATIVE_APP
```

---

# 12. Important Locator Strategies

### Java

```java
AppiumBy.accessibilityId("Login");       // Accessibility ID
AppiumBy.id("com.example:id/login");     // ID
AppiumBy.xpath("//android.widget.Button"); // XPath
```

### WDIO

```ts
$('~Login');                             // Accessibility ID
$('id=com.example:id/login');            // ID
$('//android.widget.Button');             // XPath
```

**Preferred order:**

```text
Accessibility ID → ID → Platform-specific locator → XPath
```

Avoid XPath when a stable accessibility ID or ID is available.

---

# 13. Framework Action Categories

For a reusable mobile framework, organize actions into:

```text
MobileActions
│
├── Element Actions
│   ├── click
│   ├── type
│   ├── clear
│   ├── getText
│   └── validations
│
├── Gesture Actions
│   ├── swipe
│   ├── scroll
│   └── longPress
│
├── App Actions
│   ├── activate
│   ├── terminate
│   ├── background
│   └── reset
│
├── Device Actions
│   ├── back
│   ├── orientation
│   ├── lock
│   └── unlock
│
├── Alert Actions
│   ├── accept
│   ├── dismiss
│   └── getText
│
└── Context Actions
    ├── getContexts
    └── switchContext
```

## Quick Revision

```text
CAPABILITIES
Android → UiAutomator2
iOS     → XCUITest

ELEMENT
click | setValue/sendKeys | clear | getText | displayed | enabled

GESTURE
swipe | scroll | longPress

APP
activate | terminate | background | reset | keyboard

DEVICE
back | orientation | lock | unlock

ALERT
accept | dismiss | getText

HYBRID
getContexts | switchContext

LOCATORS
Accessibility ID → ID → Platform locator → XPath
```

This keeps the notes focused on the **capabilities and actions you will actually use most often** in a Java-Appium or WDIO mobile framework.

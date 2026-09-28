````markdown
# Appium Java - Complete Mobile Actions Reference

> Appium Java Client + Selenium WebDriver
>
> Android automation: UiAutomator2

---

## 1. SETUP

### Maven Dependency

```xml
<dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>${appium.version}</version>
    <scope>test</scope>
</dependency>
````

Appium provides an official Java client.

### Install UiAutomator2

```bash
appium driver install uiautomator2
```

### Start Appium Server

```bash
appium
```

### Check connected devices

```bash
adb devices
```

---

# 2. DRIVER / SESSION

```java
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {

    static AndroidDriver driver;

    public static void main(String[] args) throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName("Android");
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");

        options.setApp("/path/to/app.apk");

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        // Test actions...

        driver.quit();
    }
}
```

### Generic Syntax

```java
UiAutomator2Options options = new UiAutomator2Options();

options.setDeviceName("Android");
options.setPlatformName("Android");
options.setAutomationName("UiAutomator2");

AndroidDriver driver = new AndroidDriver(
        new URL("http://127.0.0.1:4723"),
        options
);
```

### Remember

```text
UiAutomator2Options → Android capabilities
AndroidDriver       → Android automation
driver.quit()       → close session
```

---

# 3. LOCATOR STRATEGIES

```java
import org.openqa.selenium.By;
```

### ID

```java
driver.findElement(
        By.id("com.example:id/login")
);
```

### Accessibility ID

```java
driver.findElement(
        AppiumBy.accessibilityId("Login")
);
```

### XPath

```java
driver.findElement(
        By.xpath("//android.widget.Button[@text='Login']")
);
```

### Class Name

```java
driver.findElement(
        By.className("android.widget.Button")
);
```

### Android UIAutomator

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Login\")"
        )
);
```

### Resource ID

```java
driver.findElement(
        AppiumBy.id("com.example:id/login")
);
```

### Text

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Login\")"
        )
);
```

### Contains Text

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Log\")"
        )
);
```

### Common Locators

```java
By.id("id");

By.xpath("//element");

By.className("android.widget.Button");

AppiumBy.accessibilityId("Login");

AppiumBy.androidUIAutomator(
        "new UiSelector().text(\"Login\")"
);
```

---

# 4. ELEMENT CREATION

```java
WebElement loginButton =
        driver.findElement(By.id("login"));
```

### Multiple Elements

```java
List<WebElement> buttons =
        driver.findElements(
                By.className("android.widget.Button")
        );
```

### Count

```java
int count = driver.findElements(
        By.className("android.widget.Button")
).size();

System.out.println(count);
```

---

# 5. CLICK / TAP ACTIONS

### Click

```java
driver.findElement(By.id("login"))
      .click();
```

### Generic Syntax

```java
driver.findElement(LOCATOR).click();
```

### Tap using element

```java
driver.findElement(By.id("login"))
      .click();
```

### Tap using coordinates

```java
PointerInput finger =
        new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger"
        );

Sequence tap = new Sequence(finger, 1);

tap.addAction(
        finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                500,
                800
        )
);

tap.addAction(
        finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

tap.addAction(
        finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

driver.perform(List.of(tap));
```

---

# 6. TEXT INPUT

### Send Text

```java
driver.findElement(By.id("username"))
      .sendKeys("Anudeep");
```

### Generic Syntax

```java
driver.findElement(LOCATOR)
      .sendKeys("TEXT");
```

### Clear Field

```java
driver.findElement(By.id("username"))
      .clear();
```

### Clear + Enter

```java
WebElement username =
        driver.findElement(By.id("username"));

username.clear();
username.sendKeys("Anudeep");
```

### Keyboard Keys

```java
import org.openqa.selenium.Keys;

driver.findElement(By.id("username"))
      .sendKeys(Keys.ENTER);

driver.findElement(By.id("username"))
      .sendKeys(Keys.TAB);

driver.findElement(By.id("username"))
      .sendKeys(Keys.BACK_SPACE);

driver.findElement(By.id("username"))
      .sendKeys(Keys.ESCAPE);
```

---

# 7. HIDE KEYBOARD

```java
driver.hideKeyboard();
```

### Check Keyboard

```java
boolean visible =
        driver.isKeyboardShown();

System.out.println(visible);
```

---

# 8. CHECKBOX

### Select Checkbox

```java
WebElement checkbox =
        driver.findElement(By.id("terms"));

checkbox.click();
```

### Check State

```java
boolean checked =
        checkbox.isSelected();

System.out.println(checked);
```

### Check

```java
if (!checkbox.isSelected()) {
    checkbox.click();
}
```

### Uncheck

```java
if (checkbox.isSelected()) {
    checkbox.click();
}
```

---

# 9. RADIO BUTTON

```java
WebElement male =
        driver.findElement(By.id("male"));

male.click();

System.out.println(
        male.isSelected()
);
```

---

# 10. SWITCH / TOGGLE

```java
WebElement toggle =
        driver.findElement(By.id("notification"));

toggle.click();
```

### Validate

```java
boolean enabled =
        toggle.isSelected();

System.out.println(enabled);
```

---

# 11. DROPDOWN

Native mobile dropdowns are application-dependent.

### Click Dropdown

```java
driver.findElement(
        By.id("country")
).click();
```

### Select Option

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"India\")"
        )
).click();
```

### Generic

```java
driver.findElement(DROPDOWN).click();

driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"India\")"
        )
).click();
```

---

# 12. ELEMENT INFORMATION

### Get Text

```java
String text =
        driver.findElement(By.id("message"))
              .getText();

System.out.println(text);
```

### Get Attribute

```java
String value =
        driver.findElement(By.id("username"))
              .getAttribute("text");
```

### Get Content Description

```java
String description =
        driver.findElement(By.id("login"))
              .getAttribute("content-desc");
```

### Get Class

```java
String className =
        driver.findElement(By.id("login"))
              .getAttribute("className");
```

### Get Resource ID

```java
String resourceId =
        driver.findElement(By.id("login"))
              .getAttribute("resource-id");
```

### Is Displayed

```java
boolean visible =
        driver.findElement(By.id("login"))
              .isDisplayed();
```

### Is Enabled

```java
boolean enabled =
        driver.findElement(By.id("login"))
              .isEnabled();
```

### Is Selected

```java
boolean selected =
        driver.findElement(By.id("checkbox"))
              .isSelected();
```

---

# 13. ASSERTIONS

Using TestNG:

```java
import static org.testng.Assert.*;
```

### Text

```java
assertEquals(
        driver.findElement(By.id("message"))
              .getText(),
        "Login successful"
);
```

### Contains Text

```java
assertTrue(
        driver.findElement(By.id("message"))
              .getText()
              .contains("successful")
);
```

### Visible

```java
assertTrue(
        driver.findElement(By.id("login"))
              .isDisplayed()
);
```

### Enabled

```java
assertTrue(
        driver.findElement(By.id("login"))
              .isEnabled()
);
```

### Selected

```java
assertTrue(
        driver.findElement(By.id("terms"))
              .isSelected()
);
```

### Attribute

```java
assertEquals(
        driver.findElement(By.id("username"))
              .getAttribute("text"),
        "Anudeep"
);
```

---

# 14. EXPLICIT WAIT

```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
```

### Wait for Visibility

```java
WebDriverWait wait =
        new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

wait.until(
        ExpectedConditions.visibilityOfElementLocated(
                By.id("login")
        )
);
```

### Wait for Clickable

```java
wait.until(
        ExpectedConditions.elementToBeClickable(
                By.id("login")
        )
);
```

### Wait for Presence

```java
wait.until(
        ExpectedConditions.presenceOfElementLocated(
                By.id("message")
        )
);
```

### Wait for Text

```java
wait.until(
        ExpectedConditions.textToBePresentInElementLocated(
                By.id("message"),
                "Success"
        )
);
```

---

# 15. IMPLICIT WAIT

```java
driver.manage()
      .timeouts()
      .implicitlyWait(
              Duration.ofSeconds(10)
      );
```

> Prefer explicit waits for important synchronization points instead of relying heavily on implicit waits.

---

# 16. PAGE SOURCE

### Get Page Source

```java
String source =
        driver.getPageSource();

System.out.println(source);
```

### Useful for Debugging

```java
System.out.println(
        driver.getPageSource()
);
```

---

# 17. SCREENSHOT

```java
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
```

### Screenshot

```java
File screenshot =
        ((TakesScreenshot) driver)
                .getScreenshotAs(
                        OutputType.FILE
                );
```

### Save Screenshot

```java
File source =
        ((TakesScreenshot) driver)
                .getScreenshotAs(
                        OutputType.FILE
                );

Files.copy(
        source.toPath(),
        Path.of("screenshots/home.png")
);
```

---

# 18. SCREEN SIZE

```java
Dimension size =
        driver.manage()
              .window()
              .getSize();

System.out.println(
        size.getWidth()
);

System.out.println(
        size.getHeight()
);
```

---

# 19. DEVICE ORIENTATION

### Portrait

```java
driver.rotate(
        new DeviceRotation(
                ScreenOrientation.PORTRAIT
        )
);
```

### Landscape

```java
driver.rotate(
        new DeviceRotation(
                ScreenOrientation.LANDSCAPE
        )
);
```

### Get Orientation

```java
System.out.println(
        driver.getOrientation()
);
```

---

# 20. SCROLL

### Android UIAutomator Scroll

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(" +
                "new UiSelector().scrollable(true)" +
                ").scrollIntoView(" +
                "new UiSelector().text(\"Settings\"));"
        )
);
```

### Scroll to Text

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(" +
                "new UiSelector().scrollable(true)" +
                ").scrollIntoView(" +
                "new UiSelector().textContains(\"Settings\"));"
        )
);
```

### Scroll Forward

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(" +
                "new UiSelector().scrollable(true)" +
                ").scrollForward()"
        )
);
```

### Scroll Backward

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(" +
                "new UiSelector().scrollable(true)" +
                ").scrollBackward()"
        )
);
```

---

# 21. SWIPE / GESTURES

Modern Appium Java Client supports W3C actions for gestures.

### Swipe Up

```java
PointerInput finger =
        new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger"
        );

Sequence swipe = new Sequence(finger, 1);

swipe.addAction(
        finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                500,
                1200
        )
);

swipe.addAction(
        finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

swipe.addAction(
        finger.createPointerMove(
                Duration.ofMillis(800),
                PointerInput.Origin.viewport(),
                500,
                300
        )
);

swipe.addAction(
        finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

driver.perform(List.of(swipe));
```

### Swipe Down

```java
// Start near top → move toward bottom
```

### Swipe Left

```java
// Start near right → move toward left
```

### Swipe Right

```java
// Start near left → move toward right
```

---

# 22. LONG PRESS

```java
PointerInput finger =
        new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger"
        );

Sequence longPress =
        new Sequence(finger, 1);

longPress.addAction(
        finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                500,
                800
        )
);

longPress.addAction(
        finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

longPress.addAction(
        new Pause(
                finger,
                Duration.ofSeconds(2)
        )
);

longPress.addAction(
        finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

driver.perform(List.of(longPress));
```

---

# 23. DRAG AND DROP

```java
WebElement source =
        driver.findElement(By.id("source"));

WebElement target =
        driver.findElement(By.id("target"));
```

Using W3C pointer actions:

```java
// Drag source to target using pointer actions.
```

For application-specific drag/drop, coordinate-based W3C actions may be required.

---

# 24. ALERTS

### Switch to Alert

```java
driver.switchTo().alert();
```

### Get Alert Text

```java
String text =
        driver.switchTo()
              .alert()
              .getText();
```

### Accept

```java
driver.switchTo()
      .alert()
      .accept();
```

### Dismiss

```java
driver.switchTo()
      .alert()
      .dismiss();
```

### Enter Text

```java
driver.switchTo()
      .alert()
      .sendKeys("Anudeep");
```

---

# 25. APP LIFECYCLE

### Activate App

```java
driver.activateApp(
        "com.example.app"
);
```

### Terminate App

```java
driver.terminateApp(
        "com.example.app"
);
```

### Check App Running

```java
boolean running =
        driver.isAppInstalled(
                "com.example.app"
        );
```

### Install App

```java
driver.installApp(
        "/path/to/app.apk"
);
```

### Remove App

```java
driver.removeApp(
        "com.example.app"
);
```

### Reset App

```java
driver.resetApp();
```

---

# 26. APP PACKAGE / ACTIVITY

### Start Activity

```java
driver.startActivity(
        new Activity(
                "com.example.app",
                "com.example.app.MainActivity"
        )
);
```

### Get Current Package

```java
String packageName =
        driver.getCurrentPackage();

System.out.println(packageName);
```

### Get Current Activity

```java
String activity =
        driver.currentActivity();

System.out.println(activity);
```

---

# 27. APP STATE

```java
ApplicationState state =
        driver.queryAppState(
                "com.example.app"
        );

System.out.println(state);
```

---

# 28. BACK BUTTON

```java
driver.navigate().back();
```

### Android Back

```java
driver.pressKey(
        new KeyEvent(AndroidKey.BACK)
);
```

---

# 29. HOME BUTTON

```java
driver.pressKey(
        new KeyEvent(AndroidKey.HOME)
);
```

---

# 30. RECENT APPS

```java
driver.pressKey(
        new KeyEvent(AndroidKey.APP_SWITCH)
);
```

---

# 31. ANDROID KEYBOARD

```java
driver.pressKey(
        new KeyEvent(AndroidKey.ENTER)
);
```

### Backspace

```java
driver.pressKey(
        new KeyEvent(AndroidKey.DEL)
);
```

### Home

```java
driver.pressKey(
        new KeyEvent(AndroidKey.HOME)
);
```

### Volume Up

```java
driver.pressKey(
        new KeyEvent(AndroidKey.VOLUME_UP)
);
```

### Volume Down

```java
driver.pressKey(
        new KeyEvent(AndroidKey.VOLUME_DOWN)
);
```

---

# 32. DEVICE ACTIONS

### Lock Device

```java
driver.lockDevice();
```

### Unlock Device

```java
driver.unlockDevice();
```

### Check Locked

```java
boolean locked =
        driver.isDeviceLocked();

System.out.println(locked);
```

---

# 33. CONTEXTS

### Get Contexts

```java
Set<String> contexts =
        driver.getContextHandles();

System.out.println(contexts);
```

### Current Context

```java
String context =
        driver.getContext();

System.out.println(context);
```

### Switch Context

```java
driver.context("WEBVIEW_com.example");
```

### Switch to Native

```java
driver.context("NATIVE_APP");
```

### Generic Context Switch

```java
for (String context :
        driver.getContextHandles()) {

    System.out.println(context);
}
```

---

# 34. WEBVIEW

### Get WebView Context

```java
for (String context :
        driver.getContextHandles()) {

    if (context.contains("WEBVIEW")) {
        driver.context(context);
        break;
    }
}
```

### Use Selenium Locators

```java
driver.findElement(
        By.id("username")
).sendKeys("Anudeep");
```

### Switch Back

```java
driver.context("NATIVE_APP");
```

---

# 35. DEEP LINK

For Android, deep-link handling can be performed through the driver/appium Android commands depending on the app and driver setup.

Example:

```java
driver.executeScript(
        "mobile: deepLink",
        Map.of(
                "url", "myapp://login",
                "package", "com.example.app"
        )
);
```

---

# 36. PERMISSIONS

### Grant Permission

```java
driver.executeScript(
        "mobile: changePermissions",
        Map.of(
                "permissions",
                "android.permission.CAMERA:allow"
        )
);
```

Application permissions can also be controlled through Android/Appium-specific commands depending on the driver version.

---

# 37. DEVICE INFORMATION

### Device Time

```java
String time =
        driver.getDeviceTime();

System.out.println(time);
```

### Current Package

```java
System.out.println(
        driver.getCurrentPackage()
);
```

### Current Activity

```java
System.out.println(
        driver.currentActivity()
);
```

### Page Source

```java
System.out.println(
        driver.getPageSource()
);
```

---

# 38. NETWORK CONNECTION

Android driver-specific network commands can be used when supported by the driver/device.

```java
ConnectionState state =
        driver.getConnection();

System.out.println(state);
```

### Set Connection

```java
driver.setConnection(
        new ConnectionState(
                ConnectionState.WIFI
        )
);
```

> Exact network-control support depends on the platform and Appium driver.

---

# 39. CLIPBOARD

### Set Clipboard

```java
driver.setClipboardText(
        "Hello Appium"
);
```

### Get Clipboard

```java
String clipboard =
        driver.getClipboardText();

System.out.println(clipboard);
```

### Validate

```java
assertEquals(
        driver.getClipboardText(),
        "Hello Appium"
);
```

---

# 40. FIND ELEMENT WITH UIAUTOMATOR

### Text

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Login\")"
        )
);
```

### Text Contains

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Log\")"
        )
);
```

### Resource ID

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(" +
                "\"com.example:id/login\")"
        )
);
```

### Class Name

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector()" +
                ".className(\"android.widget.Button\")"
        )
);
```

### Description

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiSelector()" +
                ".description(\"Login\")"
        )
);
```

---

# 41. ELEMENT HIERARCHY

### Child Element

```java
driver.findElement(
        By.xpath(
                "//android.widget.LinearLayout" +
                "/android.widget.Button"
        )
);
```

### Parent

```java
driver.findElement(
        By.xpath(
                "//android.widget.TextView" +
                "/.."
        )
);
```

### Following Element

```java
driver.findElement(
        By.xpath(
                "//android.widget.TextView" +
                "/following-sibling::*"
        )
);
```

---

# 42. ELEMENT ATTRIBUTES

```java
WebElement element =
        driver.findElement(By.id("login"));

System.out.println(
        element.getAttribute("text")
);

System.out.println(
        element.getAttribute("content-desc")
);

System.out.println(
        element.getAttribute("resource-id")
);

System.out.println(
        element.getAttribute("className")
);

System.out.println(
        element.getAttribute("enabled")
);

System.out.println(
        element.getAttribute("clickable")
);
```

---

# 43. ELEMENT BOUNDS

```java
Rectangle rect =
        driver.findElement(By.id("login"))
              .getRect();

System.out.println(
        rect.getX()
);

System.out.println(
        rect.getY()
);

System.out.println(
        rect.getWidth()
);

System.out.println(
        rect.getHeight()
);
```

---

# 44. DEVICE ROTATION

```java
driver.rotate(
        new DeviceRotation(
                ScreenOrientation.LANDSCAPE
        )
);

driver.rotate(
        new DeviceRotation(
                ScreenOrientation.PORTRAIT
        )
);
```

---

# 45. MULTI-TOUCH

Multi-touch gestures can be created with W3C pointer actions.

```java
PointerInput finger1 =
        new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger1"
        );

PointerInput finger2 =
        new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger2"
        );
```

Create multiple `Sequence` objects and execute them together:

```java
driver.perform(
        List.of(sequence1, sequence2)
);
```

---

# 46. JAVASCRIPT / MOBILE COMMANDS

### Execute Mobile Command

```java
driver.executeScript(
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

### Swipe Gesture

```java
driver.executeScript(
        "mobile: swipeGesture",
        Map.of(
                "left", 100,
                "top", 500,
                "width", 800,
                "height", 1000,
                "direction", "up",
                "percent", 0.75
        )
);
```

---

# 47. MOBILE SCROLL GESTURE

```java
driver.executeScript(
        "mobile: scrollGesture",
        Map.of(
                "left", 100,
                "top", 500,
                "width", 800,
                "height", 1000,
                "direction", "down",
                "percent", 0.8
        )
);
```

---

# 48. MOBILE CLICK GESTURE

```java
driver.executeScript(
        "mobile: clickGesture",
        Map.of(
                "x", 500,
                "y", 800
        )
);
```

---

# 49. MOBILE LONG CLICK

```java
driver.executeScript(
        "mobile: longClickGesture",
        Map.of(
                "x", 500,
                "y", 800,
                "duration", 2000
        )
);
```

---

# 50. MOBILE DRAG GESTURE

```java
driver.executeScript(
        "mobile: dragGesture",
        Map.of(
                "startX", 300,
                "startY", 800,
                "endX", 700,
                "endY", 800,
                "duration", 1000
        )
);
```

---

# 51. WAIT FOR APP

```java
WebDriverWait wait =
        new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

wait.until(
        ExpectedConditions.visibilityOfElementLocated(
                By.id("login")
        )
);
```

---

# 52. CUSTOM WAIT METHOD

```java
public static WebElement waitForElement(
        AndroidDriver driver,
        By locator
) {

    WebDriverWait wait =
            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(10)
            );

    return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    locator
            )
    );
}
```

### Usage

```java
WebElement login =
        waitForElement(
                driver,
                By.id("login")
        );

login.click();
```

---

# 53. REUSABLE CLICK METHOD

```java
public static void click(
        AndroidDriver driver,
        By locator
) {

    new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
    ).until(
            ExpectedConditions.elementToBeClickable(
                    locator
            )
    ).click();
}
```

### Usage

```java
click(
        driver,
        By.id("login")
);
```

---

# 54. REUSABLE SEND KEYS METHOD

```java
public static void type(
        AndroidDriver driver,
        By locator,
        String text
) {

    WebElement element =
            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(10)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            locator
                    )
            );

    element.clear();
    element.sendKeys(text);
}
```

### Usage

```java
type(
        driver,
        By.id("username"),
        "Anudeep"
);
```

---

# 55. REUSABLE GET TEXT

```java
public static String getText(
        AndroidDriver driver,
        By locator
) {

    return driver.findElement(locator)
                 .getText();
}
```

### Usage

```java
String message =
        getText(
                driver,
                By.id("message")
        );
```

---

# 56. REUSABLE VALIDATIONS

```java
public static void verifyText(
        AndroidDriver driver,
        By locator,
        String expected
) {

    assertEquals(
            driver.findElement(locator)
                  .getText(),
            expected
    );
}
```

### Usage

```java
verifyText(
        driver,
        By.id("message"),
        "Login successful"
);
```

---

# 57. LOGIN EXAMPLE

```java
By username =
        By.id("com.example:id/username");

By password =
        By.id("com.example:id/password");

By login =
        By.id("com.example:id/login");

driver.findElement(username)
      .sendKeys("admin");

driver.findElement(password)
      .sendKeys("password");

driver.findElement(login)
      .click();

assertEquals(
        driver.findElement(
                By.id("com.example:id/message")
        ).getText(),
        "Login successful"
);
```

---

# 58. COMPLETE ANDROID TEST

```java
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest {

    public static void main(String[] args)
            throws Exception {

        UiAutomator2Options options =
                new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android");
        options.setApp("/path/to/app.apk");

        AndroidDriver driver =
                new AndroidDriver(
                        new URL(
                                "http://127.0.0.1:4723"
                        ),
                        options
                );

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(10)
                );

        WebElement username =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        By.id(
                                            "com.example:id/username"
                                        )
                                )
                );

        username.sendKeys("admin");

        driver.findElement(
                By.id("com.example:id/password")
        ).sendKeys("password");

        driver.findElement(
                By.id("com.example:id/login")
        ).click();

        WebElement message =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        By.id(
                                            "com.example:id/message"
                                        )
                                )
                );

        assertEquals(
                message.getText(),
                "Login successful"
        );

        assertTrue(
                message.isDisplayed()
        );

        driver.quit();
    }
}
```

---

# 59. SESSION CAPABILITIES

### Basic Android

```java
UiAutomator2Options options =
        new UiAutomator2Options();

options.setPlatformName("Android");
options.setAutomationName("UiAutomator2");
options.setDeviceName("Android");
```

### APK

```java
options.setApp(
        "/path/to/app.apk"
);
```

### Existing Installed App

```java
options.setAppPackage(
        "com.example.app"
);

options.setAppActivity(
        "com.example.app.MainActivity"
);
```

### No Reset

```java
options.setNoReset(true);
```

### Full Reset

```java
options.setFullReset(true);
```

### Auto Grant Permissions

```java
options.setAutoGrantPermissions(true);
```

### New Command Timeout

```java
options.setNewCommandTimeout(
        Duration.ofSeconds(60)
);
```

---

# 60. PARALLEL EXECUTION

For parallel execution, each test/device should have its own driver session and device configuration.

Example concept:

```java
ThreadLocal<AndroidDriver> driver =
        new ThreadLocal<>();
```

### Get Driver

```java
public AndroidDriver getDriver() {
    return driver.get();
}
```

### Set Driver

```java
driver.set(
        new AndroidDriver(
                serverUrl,
                options
        )
);
```

### Remove Driver

```java
driver.get().quit();
driver.remove();
```

---

# 61. ANDROID WEB / CHROME

```java
UiAutomator2Options options =
        new UiAutomator2Options();

options.setPlatformName("Android");
options.setAutomationName("UiAutomator2");
options.setDeviceName("Android");

options.setBrowserName("Chrome");

AndroidDriver driver =
        new AndroidDriver(
                serverUrl,
                options
        );
```

### Navigate

```java
driver.get(
        "https://example.com"
);
```

### Find Web Element

```java
driver.findElement(
        By.id("username")
).sendKeys("admin");
```

---

# 62. NATIVE → WEBVIEW → NATIVE

### Native

```java
driver.context("NATIVE_APP");
```

### WebView

```java
for (String context :
        driver.getContextHandles()) {

    if (context.contains("WEBVIEW")) {
        driver.context(context);
        break;
    }
}
```

### Back to Native

```java
driver.context("NATIVE_APP");
```

---

# 63. DEBUGGING

### Page Source

```java
System.out.println(
        driver.getPageSource()
);
```

### Current Activity

```java
System.out.println(
        driver.currentActivity()
);
```

### Current Package

```java
System.out.println(
        driver.getCurrentPackage()
);
```

### Screenshot

```java
File screenshot =
        ((TakesScreenshot) driver)
                .getScreenshotAs(
                        OutputType.FILE
                );
```

### Print Contexts

```java
System.out.println(
        driver.getContextHandles()
);
```

---

# 64. DRIVER CLEANUP

```java
driver.quit();
```

### Remember

```text
driver.close() → close current window/context
driver.quit()  → terminate Appium session
```

For mobile tests, `quit()` is normally the important cleanup operation.

---

# 65. QUICK REMEMBER

## Driver

```text
UiAutomator2Options → Android capabilities
AndroidDriver       → Android driver
driver.quit()       → close session
```

## Navigation

```text
driver.get()              → open URL
driver.navigate().back()  → back
driver.getPageSource()    → page source
```

## Locators

```text
By.id()                   → resource ID
By.xpath()                → XPath
By.className()            → class
AppiumBy.accessibilityId()→ accessibility ID
AppiumBy.androidUIAutomator() → Android UIAutomator
```

## Actions

```text
click()                   → click
sendKeys()                → type
clear()                   → clear
hideKeyboard()            → hide keyboard
```

## Element Information

```text
getText()                 → text
getAttribute()            → attribute
isDisplayed()             → visible
isEnabled()               → enabled
isSelected()              → selected
getRect()                 → position/size
```

## Assertions

```text
assertEquals()            → exact value
assertTrue()              → true condition
assertFalse()             → false condition
```

## Waits

```text
WebDriverWait             → explicit wait
visibilityOfElement...    → visible
elementToBeClickable()    → clickable
presenceOfElement...      → present
```

## Gestures

```text
W3C Pointer Actions       → touch gestures
mobile:scrollGesture      → scroll
mobile:swipeGesture       → swipe
mobile:clickGesture       → tap
mobile:longClickGesture   → long press
mobile:dragGesture        → drag
```

## App

```text
activateApp()             → launch/activate
terminateApp()            → terminate
installApp()              → install
removeApp()               → uninstall
resetApp()                → reset
```

## Android

```text
getCurrentPackage()       → package
currentActivity()         → activity
pressKey()                → Android key
startActivity()           → launch activity
```

## Context

```text
getContextHandles()       → all contexts
getContext()              → current context
context("WEBVIEW...")     → switch WebView
context("NATIVE_APP")     → switch native
```

## Device

```text
lockDevice()              → lock
unlockDevice()            → unlock
isDeviceLocked()          → locked?
rotate()                  → orientation
getDeviceTime()           → device time
```

## Clipboard

```text
setClipboardText()        → write clipboard
getClipboardText()        → read clipboard
```

## Screenshot

```text
getScreenshotAs()         → screenshot
```

---

# 66. MOST-USED APPIUM JAVA SYNTAX

```java
// Driver
AndroidDriver driver;

// Locator
driver.findElement(By.id("id"));

// Accessibility ID
driver.findElement(
        AppiumBy.accessibilityId("Login")
);

// XPath
driver.findElement(
        By.xpath("//android.widget.Button[@text='Login']")
);

// Click
driver.findElement(By.id("login")).click();

// Type
driver.findElement(By.id("username"))
      .sendKeys("Anudeep");

// Clear
driver.findElement(By.id("username"))
      .clear();

// Text
String text =
        driver.findElement(By.id("message"))
              .getText();

// Attribute
String value =
        driver.findElement(By.id("element"))
              .getAttribute("text");

// Visible
element.isDisplayed();

// Enabled
element.isEnabled();

// Selected
element.isSelected();

// Wait
wait.until(
        ExpectedConditions.visibilityOfElementLocated(
                By.id("login")
        )
);

// Scroll
driver.executeScript(
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

// Screenshot
((TakesScreenshot) driver)
        .getScreenshotAs(OutputType.FILE);

// Keyboard
driver.hideKeyboard();

// Back
driver.navigate().back();

// Context
driver.context("NATIVE_APP");

// App
driver.activateApp("com.example.app");

// Quit
driver.quit();
```

---

# 67. IMPORTANT APPIUM NOTES

```text
Appium
  ↓
Appium Server
  ↓
Driver
  ↓
Android / iOS Device
  ↓
Application
```

```text
Android
   ↓
UiAutomator2

iOS
   ↓
XCUITest
```

Appium's ecosystem currently lists UiAutomator2 for Android native/hybrid/web automation and XCUITest for iOS native/hybrid/web automation.

### Key principle

```text
Locator
   ↓
Find Element
   ↓
Wait
   ↓
Action
   ↓
Validation
```

Example:

```java
WebElement login =
        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                By.id("login")
                        )
        );

login.click();

assertEquals(
        driver.findElement(
                By.id("message")
        ).getText(),
        "Login successful"
);
```

```

I used the current Appium Java/UiAutomator2 approach rather than older JSON Wire Protocol or deprecated TouchAction-style examples. The official Appium documentation confirms the Java client and UiAutomator2 driver setup used here.
```

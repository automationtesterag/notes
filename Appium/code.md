```java
package other;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileElementInteractions {

    
//============================================================
// ANDROID DRIVER — UIAUTOMATOR2 CAPABILITIES
//============================================================

    public static AndroidDriver getAndroidDriver() throws MalformedURLException {

        String appPath = System.getProperty("user.dir")
                + File.separator
                + "src/test/resources/application/ApiDemos-debug.apk";

        UiAutomator2Options options = new UiAutomator2Options()

                // -------------------- DEVICE --------------------
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel")
                .setUdid("emulator-5554")
                .setPlatformVersion("15")

                // -------------------- APPLICATION --------------------
                .setApp(appPath)
                .setAppPackage("io.appium.android.apis")
                .setAppActivity(".ApiDemos")
                .setAppWaitActivity("*")
                .setAppWaitDuration(java.time.Duration.ofSeconds(30))

                // -------------------- RESET OPTIONS --------------------
                .setNoReset(false)
                .setFullReset(false)
                .setAutoGrantPermissions(true)

                // -------------------- TIMEOUTS --------------------
                .setNewCommandTimeout(java.time.Duration.ofSeconds(120))
                .setUiautomator2ServerLaunchTimeout(java.time.Duration.ofSeconds(60))
                .setUiautomator2ServerInstallTimeout(java.time.Duration.ofSeconds(60))
                .setAdbExecTimeout(java.time.Duration.ofSeconds(60));

        // -------------------- CREATE SESSION --------------------
        return new AndroidDriver(
                URI.create("http://127.0.0.1:4723").toURL(),
                options
        );
    }


//============================================================
// iOS DRIVER — XCUITEST CAPABILITIES
//============================================================

    public static IOSDriver getIOSDriver() throws MalformedURLException {

        String appPath = System.getProperty("user.dir")
                + File.separator
                + "src/test/resources/application/UIKitCatalog-iphonesimulator.app";

        XCUITestOptions options = new XCUITestOptions()

                // -------------------- DEVICE --------------------
                .setPlatformName("iOS")
                .setAutomationName("XCUITest")
                .setDeviceName("iPhone 15 Pro")
                .setUdid("4E7F8CBE-88A7-41D3-947B-406DD309CA39")
                .setPlatformVersion("17.5")

                // -------------------- APPLICATION --------------------
                .setApp(appPath)

                // -------------------- RESET OPTIONS --------------------
                .setNoReset(false)
                .setFullReset(false)

                // -------------------- TIMEOUTS --------------------
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setWdaLaunchTimeout(Duration.ofSeconds(60))
                .setWdaConnectionTimeout(Duration.ofSeconds(60))
                .setCommandTimeouts(Duration.ofSeconds(60))

                // -------------------- AUTOMATION BEHAVIOR --------------------
                .setAutoAcceptAlerts(false)
                .setUseNewWDA(false)
                .setShouldUseSingletonTestManager(true)
                .setReduceMotion(true)
                .setPrintPageSourceOnFindFailure(true);

        // -------------------- CREATE SESSION --------------------
        return new IOSDriver(
                URI.create("http://127.0.0.1:4723").toURL(),
                options
        );
    }


    public static void main(String[] args) throws Exception {

        WebDriver driver;

        AndroidDriver androidDriver = getAndroidDriver();
        IOSDriver iosDriver = getIOSDriver();

        // ============================================================
        // 2. LOCATORS
        // ============================================================

        // ACCESSIBILITY ID (MOST RECOMMENDED)
        By androidAccessibilityId = AppiumBy.accessibilityId("Accessibility");
        By iosAccessibilityId = AppiumBy.accessibilityId("Activity Indicators");

        // ID
        By androidId = AppiumBy.id("android:id/text1");
        By iosId = AppiumBy.id("Activity Indicators");

        // xpath
        By androidXPath = AppiumBy.xpath(
                "//android.widget.TextView[@content-desc='Accessibility']");

        By iosXPath = AppiumBy.xpath(
                "//XCUIElementTypeStaticText[@name='Activity Indicators']");

        // class name
        By androidClassName = AppiumBy.className("android.widget.TextView");
        By iosClassName = AppiumBy.className("XCUIElementTypeStaticText");

        // ANDROID UI AUTOMATOR (ANDROID ONLY)
        By androidUIAutomator = AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Accessibility\")");

        // IOS PREDICATE STRING (IOS ONLY)
        By iosPredicateString = AppiumBy.iOSNsPredicateString(
                "name == 'Activity Indicators'");

        // IOS CLASS CHAIN (IOS ONLY)
        By iosClassChain = AppiumBy.iOSClassChain(
                "**/XCUIElementTypeStaticText[`name == 'Activity Indicators'`]");

        // CSS SELECTOR (WEBVIEW ONLY)
        By androidCssSelector = AppiumBy.cssSelector(
                "android.widget.TextView[content-desc='Accessibility']");

        By iosCssSelector = AppiumBy.cssSelector(
                "XCUIElementTypeStaticText[name='Activity Indicators']");


        // LINK TEXT (WEBVIEW ONLY)
        By androidLinkText = AppiumBy.linkText("Accessibility");
        By iosLinkText = AppiumBy.linkText("Activity Indicators");

        // PARTIAL LINK TEXT (WEBVIEW ONLY)
        By androidPartialLinkText = AppiumBy.partialLinkText("Access");
        By iosPartialLinkText = AppiumBy.partialLinkText("Activity");

        // ============================================================
        // 3. FIND ELEMENT AND FIND ELEMENTS
        // ============================================================

        // -------------------- ANDROID --------------------
        // Find a single element
        WebElement androidElement = androidDriver.findElement(androidAccessibilityId);

        // Find multiple elements
        List<WebElement> androidElements = androidDriver.findElements(androidClassName);

        System.out.println("Android element found successfully");
        System.out.println("Android matching elements: " + androidElements.size());


        // -------------------- iOS --------------------
        // Find a single element
        WebElement iosElement = iosDriver.findElement(iosAccessibilityId);

        // Find multiple elements
        List<WebElement> iosElements = iosDriver.findElements(iosClassName);

        System.out.println("iOS element found successfully");
        System.out.println("iOS matching elements: " + iosElements.size());

        // ============================================================
        // 4. CLICK / TAP
        // ============================================================

        // -------------------- ANDROID --------------------
        androidElement.click();
        System.out.println("Android: Accessibility clicked");

        // -------------------- iOS --------------------
        iosElement.click();
        System.out.println("iOS: Activity Indicators tapped");

        //============================================================
        // 5. SEND KEYS / TYPE
        //============================================================
        // -------------------- ANDROID --------------------
        androidElement.sendKeys("Hello Android");
        System.out.println("Android: Text sent to element");

        // -------------------- iOS --------------------
        iosElement.sendKeys("Hello iOS");
        System.out.println("iOS: Text sent to element");

        //============================================================
        // 6. GET TEXT
        //============================================================
        // -------------------- ANDROID --------------------
        String androidText = androidElement.getText();
        System.out.println("Android: Text retrieved from element: " + androidText);

        // -------------------- iOS --------------------
        String iosText = iosElement.getText();
        System.out.println("iOS: Text retrieved from element: " + iosText);

        //============================================================
        // 7. GET ATTRIBUTE
        //============================================================
        // -------------------- ANDROID --------------------
        String androidAttribute = androidElement.getAttribute("content-desc");
        System.out.println("Android: Attribute retrieved from element: " + androidAttribute);

        // -------------------- iOS --------------------
        String iosAttribute = iosElement.getAttribute("name");
        System.out.println("iOS: Attribute retrieved from element: " + iosAttribute);

        //============================================================
        // 8. IS DISPLAYED / IS ENABLED / IS SELECTED
        //============================================================
        // -------------------- ANDROID --------------------
        boolean androidIsDisplayed = androidElement.isDisplayed();
        boolean androidIsEnabled = androidElement.isEnabled();
        boolean androidIsSelected = androidElement.isSelected();
        System.out.println("Android: Element is displayed: " + androidIsDisplayed);
        System.out.println("Android: Element is enabled: " + androidIsEnabled);
        System.out.println("Android: Element is selected: " + androidIsSelected);

        // -------------------- iOS --------------------
        boolean iosIsDisplayed = iosElement.isDisplayed();
        boolean iosIsEnabled = iosElement.isEnabled();
        boolean iosIsSelected = iosElement.isSelected();
        System.out.println("iOS: Element is displayed: " + iosIsDisplayed);
        System.out.println("iOS: Element is enabled: " + iosIsEnabled);
        System.out.println("iOS: Element is selected: " + iosIsSelected);

        //============================================================
        // 9. GET LOCATION / SIZE / RECT
        //============================================================
        // -------------------- ANDROID --------------------
        System.out.println("Android: Element location: " + androidElement.getLocation());
        System.out.println("Android: Element size: " + androidElement.getSize());
        System.out.println("Android: Element rect: " + androidElement.getRect());

        // -------------------- iOS --------------------
        System.out.println("iOS: Element location: " + iosElement.getLocation());
        System.out.println("iOS: Element size: " + iosElement.getSize());
        System.out.println("iOS: Element rect: " + iosElement.getRect());

        //============================================================
        // 10. CLEAR
        //============================================================
        // -------------------- ANDROID --------------------
        androidElement.clear();
        System.out.println("Android: Element cleared");

        // -------------------- iOS --------------------
        iosElement.clear();
        System.out.println("iOS: Element cleared");

        //============================================================
        // 11. SCROLL GESTURE
        //============================================================
        // -------------------- ANDROID --------------------
        // ANDROID — SCROLL WITHIN A SPECIFIC AREA
        Map<String, Object> androidScrollParams = new HashMap<>();
        androidScrollParams.put("left", 100);
        androidScrollParams.put("top", 500);
        androidScrollParams.put("width", 200);
        androidScrollParams.put("height", 800);
        androidScrollParams.put("direction", "down");
        androidScrollParams.put("percent", 1.0);

        androidDriver.executeScript("mobile: scrollGesture", androidScrollParams);

        // ANDROID — SCROLL TO ELEMENT
        WebElement androidTarget = androidDriver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))"
                                + ".scrollIntoView(new UiSelector().textContains(\"Settings\"))"
                )
        );

        System.out.println("Android: Settings element found");

        // -------------------- iOS --------------------
        // SCROLL WITHIN A SPECIFIC AREA
        WebElement scrollView = iosDriver.findElement(
                AppiumBy.className("XCUIElementTypeScrollView")
        );
        Map<String, Object> iosScrollParams = new HashMap<>();
        iosScrollParams.put("elementId", ((RemoteWebElement) scrollView).getId());
        iosScrollParams.put("direction", "down");
        iosDriver.executeScript("mobile: scroll", iosScrollParams);
        System.out.println("iOS: Scrolled within the scroll view");

        // SCROLL TO A SPECIFIC ELEMENT
        String targetName = "Steppers";
        WebElement iosTarget = null;

        for (int i = 0; i < 10; i++) {

            List<WebElement> elements = iosDriver.findElements(AppiumBy.iOSNsPredicateString("name == '" + targetName + "'"));
            if (!elements.isEmpty()) {
                iosTarget = elements.get(0);
                break;
            }
            iosDriver.executeScript("mobile: scroll", Map.of("direction", "down"));
        }

        if (iosTarget != null) {
            System.out.println("iOS: Target element found: " + targetName);
        } else {
            System.out.println("iOS: Target element not found after scrolling");
        }

        //============================================================
        // 12. SWIPE GESTURE
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidSwipeParams = new HashMap<>();
        androidSwipeParams.put("startX", 500);
        androidSwipeParams.put("startY", 1000);
        androidSwipeParams.put("endX", 500);
        androidSwipeParams.put("endY", 500);
        androidSwipeParams.put("duration", 1000);
        androidDriver.executeScript("mobile: swipeGesture", androidSwipeParams);
        System.out.println("Android: Swipe gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosSwipeParams = new HashMap<>();
        iosSwipeParams.put("startX", 200);
        iosSwipeParams.put("startY", 600);
        iosSwipeParams.put("endX", 200);
        iosSwipeParams.put("endY", 100);
        iosSwipeParams.put("duration", 1.0);
        iosDriver.executeScript("mobile: swipe", iosSwipeParams);
        System.out.println("iOS: Swipe gesture performed");

        //============================================================
        // 13. PINCH / ZOOM GESTURE
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidPinchParams = new HashMap<>();
        androidPinchParams.put("elementId", ((RemoteWebElement) androidElement).getId());
        androidPinchParams.put("scale", 0.5); // Pinch
        androidPinchParams.put("velocity", 1.0);
        androidDriver.executeScript("mobile: pinchGesture", androidPinchParams);
        System.out.println("Android: Pinch gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosPinchParams = new HashMap<>();
        iosPinchParams.put("elementId", ((RemoteWebElement) iosElement).getId());
        iosPinchParams.put("scale", 0.5); // Pinch
        iosPinchParams.put("velocity", 1.0);
        iosDriver.executeScript("mobile: pinch", iosPinchParams);
        System.out.println("iOS: Pinch gesture performed");

        //============================================================
        // 14. LONG PRESS GESTURE
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidLongPressParams = new HashMap<>();
        androidLongPressParams.put("elementId", ((RemoteWebElement) androidElement).getId());
        androidLongPressParams.put("duration", 2000); // 2 seconds
        androidDriver.executeScript("mobile: longClickGesture", androidLongPressParams);
        System.out.println("Android: Long press gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosLongPressParams = new HashMap<>();
        iosLongPressParams.put("elementId", ((RemoteWebElement) iosElement).getId());
        iosLongPressParams.put("duration", 2.0); // 2 seconds
        iosDriver.executeScript("mobile: touchAndHold", iosLongPressParams);
        System.out.println("iOS: Long press gesture performed");

        //============================================================
        // 15. DRAG AND DROP GESTURE
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidDragDropParams = new HashMap<>();
        androidDragDropParams.put("elementId", ((RemoteWebElement) androidElement).getId());
        androidDragDropParams.put("endX", 500);
        androidDragDropParams.put("endY", 500);
        androidDriver.executeScript("mobile: dragGesture", androidDragDropParams);
        System.out.println("Android: Drag and drop gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosDragDropParams = new HashMap<>();
        iosDragDropParams.put("elementId", ((RemoteWebElement) iosElement).getId());
        iosDragDropParams.put("endX", 200);
        iosDragDropParams.put("endY", 100);
        iosDriver.executeScript("mobile: drag", iosDragDropParams);
        System.out.println("iOS: Drag and drop gesture performed");

        //============================================================
        // 16. Double Tap Gesture
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidDoubleTapParams = new HashMap<>();
        androidDoubleTapParams.put("elementId", ((RemoteWebElement) androidElement).getId());
        androidDriver.executeScript("mobile: doubleClickGesture", androidDoubleTapParams);
        System.out.println("Android: Double tap gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosDoubleTapParams = new HashMap<>();
        iosDoubleTapParams.put("elementId", ((RemoteWebElement) iosElement).getId());
        iosDriver.executeScript("mobile: doubleTap", iosDoubleTapParams);
        System.out.println("iOS: Double tap gesture performed");

        //============================================================
        // 17. Tap Gesture
        //============================================================
        // -------------------- ANDROID --------------------
        Map<String, Object> androidTapParams = new HashMap<>();
        androidTapParams.put("elementId", ((RemoteWebElement) androidElement).getId());
        androidDriver.executeScript("mobile: clickGesture", androidTapParams);
        System.out.println("Android: Tap gesture performed");

        // -------------------- iOS --------------------
        Map<String, Object> iosTapParams = new HashMap<>();
        iosTapParams.put("elementId", ((RemoteWebElement) iosElement).getId());
        iosDriver.executeScript("mobile: tap", iosTapParams);
        System.out.println("iOS: Tap gesture performed");

        //============================================================
        // 18. KEYBOARD INTERACTIONS
        //============================================================
        // -------------------- ANDROID --------------------
        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
        System.out.println("Android: Enter pressed");
        androidDriver.pressKey(new KeyEvent(AndroidKey.SPACE));
        System.out.println("Android: Space pressed");
        androidDriver.pressKey(new KeyEvent(AndroidKey.TAB));
        System.out.println("Android: Tab pressed");
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        System.out.println("Android: Back pressed");

        // Hide keyboard
        androidDriver.hideKeyboard();
        System.out.println("Android: Keyboard hidden");

        // -------------------- iOS --------------------
        // Hide keyboard
        iosDriver.hideKeyboard();
        System.out.println("iOS: Keyboard hidden");

        // Tap Return key
        iosDriver.findElement(AppiumBy.accessibilityId("Return")).click();
        System.out.println("iOS: Return pressed");

        // Tap Done key (if available)
        List<WebElement> doneButtons = iosDriver.findElements(AppiumBy.accessibilityId("Done"));

        if (!doneButtons.isEmpty()) {
            doneButtons.get(0).click();
            System.out.println("iOS: Done pressed");
        }

        //============================================================
        // 19. WAITS
        //============================================================
        // -------------------- ANDROID --------------------
        // Implicit wait — applies globally to every findElement call
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait — wait for a specific condition (preferred for flaky/async UI)
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(15));
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("welcome_label")));

        // -------------------- iOS --------------------
        // Implicit wait — applies globally to every findElement call
        iosDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait — wait for a specific condition (preferred for flaky/async UI)
        WebDriverWait iosWait = new WebDriverWait(iosDriver, Duration.ofSeconds(15));
        WebElement iosEl = iosWait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("welcome_label")));


        //============================================================
        // 20. Context Switching (Native <-> WebView)
        //============================================================
        // -------------------- ANDROID --------------------
        // Get current context
        String currentContext = androidDriver.getContext();
        System.out.println("Android: Current context: " + currentContext);

        // Get all available contexts
        Set<String> contexts = androidDriver.getContextHandles();
        System.out.println("Android: Available contexts: " + contexts);

        // Switch to WebView context (if available)
        for (String context : contexts) {
            if (context.toLowerCase().contains("webview")) {
                androidDriver.context(context);
                System.out.println("Android: Switched to context: " + context);
                break;
            }
        }
        // Switch back to native context
        androidDriver.context("NATIVE_APP");
        System.out.println("Android: Switched back to native context");

        // -------------------- iOS --------------------
        // Get current context
        String iosCurrentContext = iosDriver.getContext();
        System.out.println("iOS: Current context: " + iosCurrentContext);

        // Get all available contexts
        Set<String> iosContexts = iosDriver.getContextHandles();
        System.out.println("iOS: Available contexts: " + iosContexts);

        // Switch to WebView context (if available)
        for (String context : iosContexts) {
            if (context.toLowerCase().contains("webview")) {
                iosDriver.context(context);
                System.out.println("iOS: Switched to context: " + context);
                break;
            }
        }

        // Switch back to native context
        iosDriver.context("NATIVE_APP");
        System.out.println("iOS: Switched back to native context");

        //============================================================
        // 21. ALERT HANDLING
        //============================================================
        // -------------------- ANDROID --------------------
        // Get alert text
        String androidAlertText = androidDriver.switchTo().alert().getText();
        System.out.println("Android: Alert text: " + androidAlertText);
        // Accept alert
        androidDriver.switchTo().alert().accept();
        System.out.println("Android: Alert accepted");
        // Dismiss alert
        androidDriver.switchTo().alert().dismiss();
        System.out.println("Android: Alert dismissed");
        // Send text to prompt alert (if supported)
        androidDriver.switchTo().alert().sendKeys("Hello Android Alert");
        System.out.println("Android: Text sent to alert");

        // -------------------- iOS — WEB DRIVER ALERT API --------------------
        // Get alert text
        String iosAlertText = iosDriver.switchTo().alert().getText();
        System.out.println("iOS: Alert text: " + iosAlertText);
        // Accept alert
        iosDriver.switchTo().alert().accept();
        System.out.println("iOS: Alert accepted");
        // Dismiss alert
        iosDriver.switchTo().alert().dismiss();
        System.out.println("iOS: Alert dismissed");
        // Send text to prompt alert (if supported)
        iosDriver.switchTo().alert().sendKeys("Hello iOS Alert");
        System.out.println("iOS: Text sent to alert");

        // -------------------- iOS — NATIVE ALERT ACTIONS --------------------
        // Get available alert buttons
        Map<String, Object> iosButtonsParams = new HashMap<>();
        iosButtonsParams.put("action", "getButtons");
        Object buttons = iosDriver.executeScript("mobile: alert", iosButtonsParams);
        System.out.println("iOS: Available alert buttons: " + buttons);

        // Accept alert using a specific button label
        Map<String, Object> iosAcceptParams = new HashMap<>();
        iosAcceptParams.put("action", "accept");
        iosAcceptParams.put("buttonLabel", "Allow");
        iosDriver.executeScript("mobile: alert", iosAcceptParams);
        System.out.println("iOS: Alert accepted using Allow button");

        // Dismiss alert
        Map<String, Object> iosDismissParams = new HashMap<>();
        iosDismissParams.put("action", "dismiss");
        iosDriver.executeScript("mobile: alert", iosDismissParams);
        System.out.println("iOS: Alert dismissed");

        //============================================================
        // 22 .App Lifecycle Management
        //============================================================
        // -------------------- ANDROID --------------------
        // Close app
        androidDriver.terminateApp("com.example.android.apis");
        System.out.println("Android: App closed");
        // Launch app
        androidDriver.activateApp("com.example.android.apis");
        System.out.println("Android: App launched");
        // put app in background for 5 seconds
        androidDriver.runAppInBackground(Duration.ofSeconds(5));
        System.out.println("Android: App sent to background for 5 seconds");
        // Remove app from device
        androidDriver.removeApp("com.example.android.apis");
        System.out.println("Android: App removed from device");

        // -------------------- iOS --------------------
        // Close app
        iosDriver.terminateApp("com.example.apple-samplecode.UICatalog");
        System.out.println("iOS: App closed");
        // Launch app
        iosDriver.activateApp("com.example.apple-samplecode.UICatalog");
        System.out.println("iOS: App launched");
        // put app in background for 5 seconds
        iosDriver.runAppInBackground(Duration.ofSeconds(5));
        System.out.println("iOS: App sent to background for 5 seconds");
        // Remove app from device
        iosDriver.removeApp("com.example.apple-samplecode.UICatalog");
        System.out.println("iOS: App removed from device");

        //============================================================
        // 23 Device Actions
        //============================================================
        // -------------------- ANDROID --------------------
        // Lock device
        androidDriver.lockDevice();
        System.out.println("Android: Device locked");
        // Unlock device
        androidDriver.unlockDevice();
        System.out.println("Android: Device unlocked");
        // Get device time
        String androidDeviceTime = androidDriver.getDeviceTime();
        System.out.println("Android: Device time: " + androidDeviceTime);
        // Get device orientation
        String androidDeviceOrientation = androidDriver.getOrientation().toString();
        System.out.println("Android: Device orientation: " + androidDeviceOrientation);
        // isDeviceLocked
        boolean isAndroidDeviceLocked = androidDriver.isDeviceLocked();
        System.out.println("Android: Is device locked: " + isAndroidDeviceLocked);

        // -------------------- iOS --------------------
        // Lock device
        iosDriver.lockDevice();
        System.out.println("iOS: Device locked");
        // Unlock device
        iosDriver.unlockDevice();
        System.out.println("iOS: Device unlocked");
        // Get device time
        String iosDeviceTime = iosDriver.getDeviceTime();
        System.out.println("iOS: Device time: " + iosDeviceTime);
        // Get device orientation
        String iosDeviceOrientation = iosDriver.getOrientation().toString();
        System.out.println("iOS: Device orientation: " + iosDeviceOrientation);
        // isDeviceLocked
        boolean isIOSDeviceLocked = iosDriver.isDeviceLocked();
        System.out.println("iOS: Is device locked: " + isIOSDeviceLocked);

        //============================================================
        // 24 Drop down and select an option
        //============================================================
        // -------------------- ANDROID --------------------
        androidElement.click(); // Click to open the dropdown
        WebElement androidOption = androidDriver.findElement(AppiumBy.xpath("//android.widget.CheckedTextView[@text='Option 1']"));
        androidOption.click(); // Select the option
        System.out.println("Android: Option 1 selected from dropdown");
        // get all options
        List<WebElement> androidOptions = androidDriver.findElements(AppiumBy.className("android.widget.CheckedTextView"));
        System.out.println("Android: Available options in dropdown:");
        for (WebElement option : androidOptions) {
            System.out.println(option.getText());
        }

        // -------------------- iOS --------------------
        iosElement.click(); // Click to open the dropdown
        WebElement iosOption = iosDriver.findElement(AppiumBy.xpath("//XCUIElementTypePickerWheel[@value='Option 1']"));
        iosOption.click(); // Select the option
        System.out.println("iOS: Option 1 selected from dropdown");

        //============================================================
        // 25. screenshot
        //============================================================
        // -------------------- ANDROID --------------------
        File androidScreenshot = androidDriver.getScreenshotAs(OutputType.FILE);
        System.out.println("Android: Screenshot taken: " + androidScreenshot.getAbsolutePath());

        // -------------------- iOS --------------------
        File iosScreenshot = iosDriver.getScreenshotAs(OutputType.FILE);
        System.out.println("iOS: Screenshot taken: " + iosScreenshot.getAbsolutePath());

        //============================================================
        // 26. Assertions
        //============================================================
        // -------------------- ANDROID --------------------
        assert androidElement.isDisplayed() : "Android: Element is not displayed";
        assert androidElement.isEnabled() : "Android: Element is not enabled";
        System.out.println("Android: Assertions passed");

        // -------------------- iOS --------------------
        assert iosElement.isDisplayed() : "iOS: Element is not displayed";
        assert iosElement.isEnabled() : "iOS: Element is not enabled";
        System.out.println("iOS: Assertions passed");

        //============================================================
        // 27. Screen size and orientation
        //============================================================
        // -------------------- ANDROID --------------------
        Dimension androidScreenSize = androidDriver.manage().window().getSize();
        System.out.println("Android: Screen size: " + androidScreenSize);
        //width and height
        System.out.println("Android: Screen width: " + androidScreenSize.getWidth());
        System.out.println("Android: Screen height: " + androidScreenSize.getHeight());

        // -------------------- iOS --------------------
        Dimension iosScreenSize = iosDriver.manage().window().getSize();
        System.out.println("iOS: Screen size: " + iosScreenSize);
        //width and height
        System.out.println("iOS: Screen width: " + iosScreenSize.getWidth());
        System.out.println("iOS: Screen height: " + iosScreenSize.getHeight());

        //============================================================
        // 28. Close drivers
        //============================================================
        androidDriver.quit();
        iosDriver.quit();
        System.out.println("Drivers closed successfully");
    }
}
```



##  Selenium vs Cypress vs Playwright vs WDIO vs Appium

| Feature            | Selenium         | Cypress          | Playwright     | **WebdriverIO**                | **Appium**            |
| ------------------ | ---------------- | ---------------- | -------------- | ------------------------------ | --------------------- |
| Primary use        | Web automation   | Web testing      | Web E2E/API    | **Web + Mobile**               | **Mobile automation** |
| Web automation     | ✅                | ✅                | ✅              | ✅                              | ⚠️                    |
| API automation     | ⚠️ External      | ✅                | ✅              | ✅                              | ⚠️ External           |
| Android native     | Via Appium       | ❌                | ❌              | **✅ Appium**                   | **✅**                 |
| iOS native         | Via Appium       | ❌                | ❌              | **✅ Appium**                   | **✅**                 |
| Mobile web         | Via Appium       | ⚠️               | ⚠️             | **✅**                          | **✅**                 |
| Hybrid app         | Via Appium       | ❌                | ❌              | **✅**                          | **✅**                 |
| Browser support    | Excellent        | Good             | Excellent      | **Excellent**                  | N/A                   |
| Auto-waiting       | ❌                | ✅                | ✅              | **✅**                          | Depends on client     |
| Multiple tabs      | ✅                | Limited          | ✅              | **✅**                          | N/A                   |
| iFrames            | Manual           | Easy             | Easy           | **Easy**                       | WebView context       |
| Network mocking    | External         | ✅                | ✅              | ✅                              | ❌                     |
| Parallel execution | ✅                | ✅                | ✅              | **✅**                          | ✅                     |
| Codegen            | Limited          | Limited          | **Excellent**  | ✅                              | Limited               |
| Trace/debugging    | External         | Excellent        | **Excellent**  | Good                           | External              |
| Cucumber           | ✅                | ✅                | ✅              | **✅ Excellent**                | ✅                     |
| BrowserStack       | ✅                | ✅                | ✅              | **✅**                          | **✅**                 |
| TypeScript         | ✅                | ✅                | ✅              | **✅ Excellent**                | ✅                     |
| Native gestures    | Via Appium       | ❌                | ❌              | **✅**                          | **✅**                 |
| Enterprise web     | ⭐⭐⭐⭐⭐            | ⭐⭐⭐⭐             | ⭐⭐⭐⭐⭐          | **⭐⭐⭐⭐⭐**                      | ⭐⭐⭐⭐⭐ mobile          |
| Best use           | Large/legacy web | Frontend testing | Modern web/API | **Web + mobile TS automation** | Native/hybrid mobile  |

15–20 commonly used actions, all inside one `test` block**, separately for **Selenium Java, Cypress, Playwright, WebdriverIO, and Appium Java**.

## 1. Selenium Java — Common Actions

```java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;

@Test
public void commonSeleniumActions() {

    // ============================================================
    // 1. LAUNCH BROWSER
    // ============================================================
    WebDriver driver = new ChromeDriver();

    // Maximize browser
    driver.manage().window().maximize();

    // ============================================================
    // 2. NAVIGATE
    // ============================================================
    driver.get("https://example.com");

    // ============================================================
    // 3. GET URL & TITLE
    // ============================================================
    System.out.println(driver.getCurrentUrl());
    System.out.println(driver.getTitle());

    // ============================================================
    // 4. FIND ELEMENT
    // ============================================================
    WebElement username =
            driver.findElement(By.id("username"));

    // ============================================================
    // 5. ENTER TEXT
    // ============================================================
    username.sendKeys("Anudeep");

    // ============================================================
    // 6. CLEAR TEXT
    // ============================================================
    username.clear();

    // ============================================================
    // 7. CLICK
    // ============================================================
    driver.findElement(By.id("login")).click();

    // ============================================================
    // 8. GET TEXT
    // ============================================================
    String text =
            driver.findElement(By.id("message")).getText();

    System.out.println(text);

    // ============================================================
    // 9. GET ATTRIBUTE
    // ============================================================
    String value =
            driver.findElement(By.id("username"))
                  .getAttribute("value");

    System.out.println(value);

    // ============================================================
    // 10. CHECK DISPLAYED
    // ============================================================
    boolean displayed =
            driver.findElement(By.id("message"))
                  .isDisplayed();

    // ============================================================
    // 11. CHECK ENABLED
    // ============================================================
    boolean enabled =
            driver.findElement(By.id("login"))
                  .isEnabled();

    // ============================================================
    // 12. CHECK SELECTED
    // ============================================================
    boolean selected =
            driver.findElement(By.id("remember"))
                  .isSelected();

    // ============================================================
    // 13. DROPDOWN
    // ============================================================
    Select country =
            new Select(driver.findElement(By.id("country")));

    country.selectByVisibleText("India");

    // ============================================================
    // 14. CHECKBOX
    // ============================================================
    WebElement checkbox =
            driver.findElement(By.id("terms"));

    if (!checkbox.isSelected()) {
        checkbox.click();
    }

    // ============================================================
    // 15. JAVASCRIPT
    // ============================================================
    JavascriptExecutor js =
            (JavascriptExecutor) driver;

    js.executeScript(
            "window.scrollTo(0, document.body.scrollHeight)"
    );

    // ============================================================
    // 16. SCREENSHOT
    // ============================================================
    File screenshot =
            ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

    // ============================================================
    // 17. BACK
    // ============================================================
    driver.navigate().back();

    // ============================================================
    // 18. REFRESH
    // ============================================================
    driver.navigate().refresh();

    // ============================================================
    // 19. FORWARD
    // ============================================================
    driver.navigate().forward();

    // ============================================================
    // 20. CLOSE BROWSER
    // ============================================================
    driver.quit();
}
```

---

## 2. Appium Java — Common Mobile Actions

```java
@Test
public void commonAppiumActions() {

    // Find element using Accessibility ID
    WebElement username =
            driver.findElement(AppiumBy.accessibilityId("username"));

    // Enter text
    username.sendKeys("Anudeep");

    // Clear text
    username.clear();

    // Find password
    WebElement password =
            driver.findElement(AppiumBy.accessibilityId("password"));

    // Enter password
    password.sendKeys("Password123");

    // Click
    driver.findElement(AppiumBy.accessibilityId("login")).click();

    // Get text
    String message =
            driver.findElement(AppiumBy.accessibilityId("message"))
                  .getText();

    // Check displayed
    boolean displayed =
            driver.findElement(AppiumBy.accessibilityId("home"))
                  .isDisplayed();

    // Check enabled
    boolean enabled =
            driver.findElement(AppiumBy.accessibilityId("login"))
                  .isEnabled();

    // Check selected
    boolean selected =
            driver.findElement(AppiumBy.accessibilityId("remember"))
                  .isSelected();

    // Find by Android UIAutomator
    WebElement settings = driver.findElement(
            AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Settings\")"
            )
    );

    // Click element
    settings.click();

    // Swipe / scroll using W3C Actions
    new PointerInput(PointerInput.Kind.TOUCH, "finger");

    // Press Android back
    driver.navigate().back();

    // Get current activity
    System.out.println(driver.currentActivity());

    // Get current package
    System.out.println(driver.getCurrentPackage());

    // Hide keyboard
    driver.hideKeyboard();

    // Screenshot
    File screenshot = driver.getScreenshotAs(OutputType.FILE);

    // Lock device
    driver.lockDevice();

    // Unlock device
    driver.unlockDevice();

    // Close application
    driver.terminateApp("com.example.app");
}
```

> For modern Appium Java clients, prefer `AppiumBy` locators such as `accessibilityId`, `androidUIAutomator`, `iOSClassChain`, etc., rather than relying heavily on XPath.

---

## 3. Cypress — Common Actions

```javascript
it("Common Cypress Actions", () => {

  // Navigate
  cy.visit("https://example.com");

  // Get element
  cy.get("#username");

  // Enter text
  cy.get("#username").type("Anudeep");

  // Clear
  cy.get("#username").clear();

  // Click
  cy.get("#login").click();

  // Get text
  cy.get("#message").should("have.text", "Login successful");

  // Get value
  cy.get("#username").should("have.value", "Anudeep");

  // Check displayed
  cy.get("#message").should("be.visible");

  // Check enabled
  cy.get("#login").should("be.enabled");

  // Check disabled
  cy.get("#login").should("not.be.disabled");

  // Checkbox
  cy.get("#terms").check();

  // Uncheck
  cy.get("#terms").uncheck();

  // Dropdown
  cy.get("#country").select("India");

  // Radio button
  cy.get("#male").check();

  // Select option
  cy.get("#country").select("IN");

  // URL validation
  cy.url().should("include", "/dashboard");

  // Title validation
  cy.title().should("include", "Dashboard");

  // Screenshot
  cy.screenshot("login-page");

  // Browser back
  cy.go("back");

  // Browser forward
  cy.go("forward");

  // Reload
  cy.reload();
});
```

---

## 4. Playwright TypeScript — Common Actions

```typescript
test("Common Playwright Actions", async ({ page }) => {

  // Navigate
  await page.goto("https://example.com");

  // Locator
  const username = page.locator("#username");

  // Enter text
  await username.fill("Anudeep");

  // Clear
  await username.clear();

  // Click
  await page.locator("#login").click();

  // Get text
  const text = await page.locator("#message").textContent();

  // Get value
  const value = await username.inputValue();

  // Check displayed
  await expect(page.locator("#message")).toBeVisible();

  // Check enabled
  await expect(page.locator("#login")).toBeEnabled();

  // Checkbox
  await page.locator("#terms").check();

  // Uncheck
  await page.locator("#terms").uncheck();

  // Dropdown
  await page.locator("#country").selectOption("IN");

  // Radio button
  await page.locator("#male").check();

  // Hover
  await page.locator("#menu").hover();

  // Press keyboard
  await page.locator("#username").press("Control+A");

  // Get attribute
  const href = await page.locator("#link").getAttribute("href");

  // URL validation
  await expect(page).toHaveURL(/dashboard/);

  // Title validation
  await expect(page).toHaveTitle(/Dashboard/);

  // Screenshot
  await page.screenshot({
    path: "screenshots/login.png"
  });

  // Reload
  await page.reload();

  // Go back
  await page.goBack();
});
```

---

## 5. WebdriverIO TypeScript — Common Web + Mobile Actions

```typescript
it("Common WDIO Actions", async () => {

  // =========================
  // WEB AUTOMATION
  // =========================

  // Navigate
  await browser.url("https://example.com");

  // Get URL
  console.log(await browser.getUrl());

  // Get title
  console.log(await browser.getTitle());

  // Find element
  const username = await $("#username");

  // Enter text
  await username.setValue("Anudeep");

  // Clear
  await username.clearValue();

  // Click
  await $("#login").click();

  // Get text
  const text = await $("#message").getText();

  // Get value
  const value = await username.getValue();

  // Check displayed
  await expect($("#message")).toBeDisplayed();

  // Check enabled
  await expect($("#login")).toBeEnabled();

  // Wait for element
  await $("#message").waitForDisplayed();

  // Checkbox
  await $("#terms").click();

  // Keyboard
  await browser.keys("Enter");

  // Scroll
  await $("#footer").scrollIntoView();

  // Screenshot
  await browser.saveScreenshot("./screenshots/page.png");

  // Refresh
  await browser.refresh();

  // Browser back
  await browser.back();


  // =========================
  // MOBILE / APPIUM
  // =========================

  // Accessibility ID
  const mobileLogin = await $("~login");

  // Mobile click
  await mobileLogin.click();

  // Mobile text input
  await $("~username").setValue("Anudeep");

  // Mobile clear
  await $("~username").clearValue();

  // Mobile validation
  await expect($("~home")).toBeDisplayed();

  // Mobile scroll
  await $("~Settings").scrollIntoView();

  // Mobile long press
  await $("~element").longPress();

  // Mobile back
  await browser.back();

  // Device orientation
  await browser.setOrientation("LANDSCAPE");

  // Lock device
  await browser.lock();

  // Unlock device
  await browser.unlock();

  // Screenshot
  await browser.saveScreenshot("./screenshots/mobile.png");
});
```

### Quick memory sheet

| Action     | Selenium Java          | Cypress                 | Playwright                  | WDIO                | Appium Java                    |
| ---------- | ---------------------- | ----------------------- | --------------------------- | ------------------- | ------------------------------ |
| Navigate   | `driver.get()`         | `cy.visit()`            | `page.goto()`               | `browser.url()`     | `driver.get()`                 |
| Find       | `findElement()`        | `cy.get()`              | `locator()`                 | `$()`               | `findElement()`                |
| Click      | `.click()`             | `.click()`              | `.click()`                  | `.click()`          | `.click()`                     |
| Input      | `.sendKeys()`          | `.type()`               | `.fill()`                   | `.setValue()`       | `.sendKeys()`                  |
| Clear      | `.clear()`             | `.clear()`              | `.clear()`                  | `.clearValue()`     | `.clear()`                     |
| Text       | `.getText()`           | `.invoke()`             | `.textContent()`            | `.getText()`        | `.getText()`                   |
| Displayed  | `.isDisplayed()`       | `.should("be.visible")` | `.toBeVisible()`            | `.isDisplayed()`    | `.isDisplayed()`               |
| Enabled    | `.isEnabled()`         | `.should("be.enabled")` | `.toBeEnabled()`            | `.isEnabled()`      | `.isEnabled()`                 |
| Checkbox   | `.click()`             | `.check()`              | `.check()`                  | `.click()`          | `.click()`                     |
| Dropdown   | `Select`               | `.select()`             | `.selectOption()`           | `.selectBy...()`    | `.click()` / platform-specific |
| Screenshot | `getScreenshotAs()`    | `.screenshot()`         | `.screenshot()`             | `.saveScreenshot()` | `getScreenshotAs()`            |
| Back       | `navigate().back()`    | `cy.go("back")`         | `goBack()`                  | `back()`            | `navigate().back()`            |
| Refresh    | `navigate().refresh()` | `reload()`              | `reload()`                  | `refresh()`         | App-specific                   |
| Scroll     | JS/Actions             | `.scrollIntoView()`     | `.scrollIntoViewIfNeeded()` | `.scrollIntoView()` | Mobile gestures                |
| Wait       | `WebDriverWait`        | Auto-wait               | Auto-wait                   | `waitFor...()`      | Explicit/Appium waits          |


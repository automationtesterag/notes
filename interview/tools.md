

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
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Test
public void commonAppiumActions() throws MalformedURLException {

    // ============================================================
    // ANDROID SETUP
    // ============================================================

    UiAutomator2Options androidOptions = new UiAutomator2Options()
            .setDeviceName("Android Emulator")
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setAppPackage("com.example.app")
            .setAppActivity(".MainActivity");

    AndroidDriver androidDriver = new AndroidDriver(
            new URL("http://127.0.0.1:4723"),
            androidOptions
    );


    // ============================================================
    // ANDROID ACTIONS
    // ============================================================

    // Find element - Accessibility ID
    WebElement username =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("username")
            );

    // Enter text
    username.sendKeys("Anudeep");

    // Clear text
    username.clear();

    // Find password
    WebElement password =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("password")
            );

    // Enter password
    password.sendKeys("Password123");

    // Click
    androidDriver.findElement(
            AppiumBy.accessibilityId("login")
    ).click();

    // Get text
    String message =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("message")
            ).getText();

    System.out.println(message);

    // Check displayed
    boolean displayed =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("home")
            ).isDisplayed();

    // Check enabled
    boolean enabled =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("login")
            ).isEnabled();

    // Check selected
    boolean selected =
            androidDriver.findElement(
                    AppiumBy.accessibilityId("remember")
            ).isSelected();

    // Android UIAutomator locator
    WebElement settings =
            androidDriver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().text(\"Settings\")"
                    )
            );

    // Click
    settings.click();

    // Android back
    androidDriver.navigate().back();

    // Current activity
    System.out.println(
            androidDriver.currentActivity()
    );

    // Current package
    System.out.println(
            androidDriver.getCurrentPackage()
    );

    // Hide keyboard
    androidDriver.hideKeyboard();

    // Screenshot
    File androidScreenshot =
            androidDriver.getScreenshotAs(
                    OutputType.FILE
            );

    // Lock device
    androidDriver.lockDevice();

    // Unlock device
    androidDriver.unlockDevice();

    // Scroll using W3C touch action
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
                    700
            )
    );

    swipe.addAction(
            finger.createPointerDown(
                    PointerInput.MouseButton.LEFT.asArg()
            )
    );

    swipe.addAction(
            finger.createPointerMove(
                    Duration.ofMillis(700),
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

    androidDriver.perform(
            java.util.List.of(swipe)
    );

    // Terminate app
    androidDriver.terminateApp(
            "com.example.app"
    );


    // ============================================================
    // IOS SETUP
    // ============================================================

    XCUITestOptions iosOptions = new XCUITestOptions()
            .setDeviceName("iPhone 15")
            .setPlatformName("iOS")
            .setAutomationName("XCUITest")
            .setBundleId("com.example.app");

    IOSDriver iosDriver = new IOSDriver(
            new URL("http://127.0.0.1:4723"),
            iosOptions
    );


    // ============================================================
    // IOS ACTIONS
    // ============================================================

    // Find element - Accessibility ID
    WebElement iosUsername =
            iosDriver.findElement(
                    AppiumBy.accessibilityId("username")
            );

    // Enter text
    iosUsername.sendKeys("Anudeep");

    // Clear
    iosUsername.clear();

    // Password
    iosDriver.findElement(
            AppiumBy.accessibilityId("password")
    ).sendKeys("Password123");

    // Click
    iosDriver.findElement(
            AppiumBy.accessibilityId("login")
    ).click();

    // Get text
    String iosMessage =
            iosDriver.findElement(
                    AppiumBy.accessibilityId("message")
            ).getText();

    System.out.println(iosMessage);

    // Check displayed
    boolean iosDisplayed =
            iosDriver.findElement(
                    AppiumBy.accessibilityId("home")
            ).isDisplayed();

    // Check enabled
    boolean iosEnabled =
            iosDriver.findElement(
                    AppiumBy.accessibilityId("login")
            ).isEnabled();

    // Check selected
    boolean iosSelected =
            iosDriver.findElement(
                    AppiumBy.accessibilityId("remember")
            ).isSelected();

    // iOS Predicate String
    WebElement settingsIOS =
            iosDriver.findElement(
                    AppiumBy.iOSNsPredicateString(
                            "label == 'Settings'"
                    )
            );

    // Click
    settingsIOS.click();

    // Screenshot
    File iosScreenshot =
            iosDriver.getScreenshotAs(
                    OutputType.FILE
            );

    // Lock device
    iosDriver.lockDevice();

    // Unlock device
    iosDriver.unlockDevice();

    // Quit iOS session
    iosDriver.quit();

    // Quit Android session
    androidDriver.quit();
}
```

> For modern Appium Java clients, prefer `AppiumBy` locators such as `accessibilityId`, `androidUIAutomator`, `iOSClassChain`, etc., rather than relying heavily on XPath.

---

## 3. Cypress — Common Actions

```javascript
// ============================================================
// CYPRESS - SETUP + CONFIGURATION + RUN COMMANDS
// ============================================================

// ============================================================
// 1. INSTALLATION
// ============================================================

// Create project
// mkdir cypress-demo
// cd cypress-demo

// Initialize npm
// npm init -y

// Install Cypress
// npm install --save-dev cypress

// Open Cypress for first-time setup
// npx cypress open


// ============================================================
// 2. PROJECT STRUCTURE
// ============================================================

/*
cypress-demo/
│
├── cypress/
│   ├── e2e/
│   │   └── login.cy.js
│   │
│   ├── fixtures/
│   │   └── users.json
│   │
│   └── support/
│       ├── commands.js
│       └── e2e.js
│
├── cypress.config.js
├── package.json
└── node_modules/
*/


// ============================================================
// 3. cypress.config.js
// ============================================================

const { defineConfig } = require("cypress");

module.exports = defineConfig({

  e2e: {

    // Application URL
    baseUrl: "https://example.com",

    // Default command timeout
    defaultCommandTimeout: 10000,

    // Page load timeout
    pageLoadTimeout: 30000,

    // API request timeout
    requestTimeout: 10000,

    // API response timeout
    responseTimeout: 30000,

    // Screenshot when test fails
    screenshotOnRunFailure: true,

    // Video recording
    video: true,

    // Browser security
    chromeWebSecurity: false,

    // Test isolation
    testIsolation: true,

    // Environment variables
    env: {
      username: "Anudeep",
      password: "Password123",
      apiUrl: "https://api.example.com"
    },

    // Node events / plugins
    setupNodeEvents(on, config) {

      // Add tasks, reporters, plugins, etc.

      return config;
    }
  }
});


// ============================================================
// 4. BASIC TEST STRUCTURE
// ============================================================

describe("Login Tests", () => {

  it("Common Cypress Actions", () => {

    // Test actions go here

  });

});


// ============================================================
// 5. COMMON CYPRESS ACTIONS
// ============================================================

describe("Common Cypress Actions", () => {

  it("should perform common actions", () => {

    // --------------------------------------------------------
    // Navigate
    // --------------------------------------------------------

    cy.visit("/login");

    // Full URL can also be used
    // cy.visit("https://example.com/login");


    // --------------------------------------------------------
    // Get element
    // --------------------------------------------------------

    cy.get("#username");


    // --------------------------------------------------------
    // Find by text
    // --------------------------------------------------------

    cy.contains("Login");


    // --------------------------------------------------------
    // Enter text
    // --------------------------------------------------------

    cy.get("#username")
      .type("Anudeep");


    // --------------------------------------------------------
    // Clear text
    // --------------------------------------------------------

    cy.get("#username")
      .clear();


    // --------------------------------------------------------
    // Enter text again
    // --------------------------------------------------------

    cy.get("#username")
      .type("Anudeep");


    // --------------------------------------------------------
    // Click
    // --------------------------------------------------------

    cy.get("#login")
      .click();


    // --------------------------------------------------------
    // Get / validate text
    // --------------------------------------------------------

    cy.get("#message")
      .should("have.text", "Login successful");


    // --------------------------------------------------------
    // Get / validate value
    // --------------------------------------------------------

    cy.get("#username")
      .should("have.value", "Anudeep");


    // --------------------------------------------------------
    // Check displayed
    // --------------------------------------------------------

    cy.get("#message")
      .should("be.visible");


    // --------------------------------------------------------
    // Check enabled
    // --------------------------------------------------------

    cy.get("#login")
      .should("be.enabled");


    // --------------------------------------------------------
    // Check disabled
    // --------------------------------------------------------

    cy.get("#login")
      .should("not.be.disabled");


    // --------------------------------------------------------
    // Checkbox
    // --------------------------------------------------------

    cy.get("#terms")
      .check();


    // --------------------------------------------------------
    // Uncheck
    // --------------------------------------------------------

    cy.get("#terms")
      .uncheck();


    // --------------------------------------------------------
    // Radio button
    // --------------------------------------------------------

    cy.get("#male")
      .check();


    // --------------------------------------------------------
    // Dropdown by visible text
    // --------------------------------------------------------

    cy.get("#country")
      .select("India");


    // --------------------------------------------------------
    // Dropdown by value
    // --------------------------------------------------------

    cy.get("#country")
      .select("IN");


    // --------------------------------------------------------
    // URL validation
    // --------------------------------------------------------

    cy.url()
      .should("include", "/dashboard");


    // --------------------------------------------------------
    // Title validation
    // --------------------------------------------------------

    cy.title()
      .should("include", "Dashboard");


    // --------------------------------------------------------
    // Attribute validation
    // --------------------------------------------------------

    cy.get("#username")
      .should("have.attr", "name", "username");


    // --------------------------------------------------------
    // Class validation
    // --------------------------------------------------------

    cy.get("#login")
      .should("have.class", "login-button");


    // --------------------------------------------------------
    // Count elements
    // --------------------------------------------------------

    cy.get(".product")
      .should("have.length", 5);


    // --------------------------------------------------------
    // Hover
    // --------------------------------------------------------

    cy.get("#menu")
      .trigger("mouseover");


    // --------------------------------------------------------
    // Keyboard
    // --------------------------------------------------------

    cy.get("#username")
      .type("{enter}");


    // --------------------------------------------------------
    // Focus
    // --------------------------------------------------------

    cy.get("#username")
      .focus();


    // --------------------------------------------------------
    // Blur
    // --------------------------------------------------------

    cy.get("#username")
      .blur();


    // --------------------------------------------------------
    // Scroll
    // --------------------------------------------------------

    cy.get("#footer")
      .scrollIntoView();


    // --------------------------------------------------------
    // Screenshot
    // --------------------------------------------------------

    cy.screenshot("login-page");


    // --------------------------------------------------------
    // Browser back
    // --------------------------------------------------------

    cy.go("back");


    // --------------------------------------------------------
    // Browser forward
    // --------------------------------------------------------

    cy.go("forward");


    // --------------------------------------------------------
    // Reload
    // --------------------------------------------------------

    cy.reload();

  });

});


// ============================================================
// 6. COMMON ASSERTIONS
// ============================================================

describe("Assertions", () => {

  it("Common assertions", () => {

    cy.get("#message")
      .should("exist");

    cy.get("#message")
      .should("be.visible");

    cy.get("#button")
      .should("be.enabled");

    cy.get("#button")
      .should("not.be.disabled");

    cy.get("#username")
      .should("have.value", "Anudeep");

    cy.get("#message")
      .should("have.text", "Success");

    cy.get("#message")
      .should("contain.text", "Success");

    cy.get("#username")
      .should("have.attr", "placeholder");

    cy.get("#button")
      .should("have.class", "active");

    cy.url()
      .should("include", "/dashboard");

    cy.title()
      .should("include", "Dashboard");

  });

});


// ============================================================
// 7. CUSTOM COMMAND
// File: cypress/support/commands.js
// ============================================================

Cypress.Commands.add(
  "login",
  (username, password) => {

    cy.get("#username")
      .type(username);

    cy.get("#password")
      .type(password);

    cy.get("#login")
      .click();
  }
);


// ============================================================
// 8. USING CUSTOM COMMAND
// ============================================================

describe("Custom Command", () => {

  it("Login using custom command", () => {

    cy.visit("/login");

    cy.login(
      "Anudeep",
      "Password123"
    );

    cy.get("#dashboard")
      .should("be.visible");

  });

});


// ============================================================
// 9. FIXTURE DATA
// File: cypress/fixtures/users.json
// ============================================================

/*
{
  "validUser": {
    "username": "Anudeep",
    "password": "Password123"
  }
}
*/


// ============================================================
// 10. USING FIXTURE
// ============================================================

describe("Fixture Data", () => {

  it("Login using fixture", () => {

    cy.fixture("users")
      .then((users) => {

        cy.visit("/login");

        cy.get("#username")
          .type(users.validUser.username);

        cy.get("#password")
          .type(users.validUser.password);

        cy.get("#login")
          .click();

      });

  });

});


// ============================================================
// 11. ENVIRONMENT VARIABLES
// ============================================================

describe("Environment Variables", () => {

  it("Use Cypress environment variables", () => {

    const username =
      Cypress.env("username");

    const password =
      Cypress.env("password");

    cy.visit("/login");

    cy.get("#username")
      .type(username);

    cy.get("#password")
      .type(password);

  });

});


// ============================================================
// 12. API REQUEST
// ============================================================

describe("API Actions", () => {

  it("GET request", () => {

    cy.request("GET", "/users")
      .then((response) => {

        expect(response.status)
          .to.eq(200);

        expect(response.body)
          .to.exist;

      });

  });


  it("POST request", () => {

    cy.request({
      method: "POST",
      url: "/users",
      body: {
        name: "Anudeep",
        email: "anudeep@example.com"
      }
    }).then((response) => {

      expect(response.status)
        .to.eq(201);

    });

  });

});


// ============================================================
// 13. NETWORK INTERCEPTION
// ============================================================

describe("Network Interception", () => {

  it("Intercept API", () => {

    cy.intercept(
      "GET",
      "/api/users"
    ).as("getUsers");

    cy.visit("/users");

    cy.wait("@getUsers")
      .its("response.statusCode")
      .should("eq", 200);

  });

});


// ============================================================
// 14. WAIT
// ============================================================

// Prefer Cypress automatic waiting and assertions.

cy.get("#message")
  .should("be.visible");


// Explicit wait when genuinely required

cy.wait(2000);


// Wait for API

cy.intercept("GET", "/api/users")
  .as("users");

cy.wait("@users");


// ============================================================
// 15. COMMON RUN COMMANDS
// ============================================================

/*

# Open Cypress UI
npx cypress open

# Run all tests headless
npx cypress run

# Run Chrome
npx cypress run --browser chrome

# Run Firefox
npx cypress run --browser firefox

# Run headed
npx cypress run --headed

# Run specific browser + headed
npx cypress run --browser chrome --headed

# Run specific test
npx cypress run \
  --spec "cypress/e2e/login.cy.js"

*/


// ============================================================
// 16. PACKAGE.JSON SCRIPTS
// ============================================================

/*

{
  "scripts": {
    "cy:open": "cypress open",
    "cy:run": "cypress run",
    "cy:chrome": "cypress run --browser chrome",
    "cy:headed": "cypress run --headed",
    "cy:login": "cypress run --spec cypress/e2e/login.cy.js"
  }
}

*/


// ============================================================
// 17. RUN USING NPM SCRIPTS
// ============================================================

/*

npm run cy:open

npm run cy:run

npm run cy:chrome

npm run cy:headed

npm run cy:login

*/


// ============================================================
// 18. CYPRESS EXECUTION FLOW
// ============================================================

/*

Install
   ↓
npm install --save-dev cypress
   ↓
npx cypress open
   ↓
Configure cypress.config.js
   ↓
Create cypress/e2e/*.cy.js
   ↓
describe()
   ↓
it()
   ↓
cy.visit()
   ↓
cy.get()
   ↓
Action
   ↓
Assertion
   ↓
npx cypress run
*/


// ============================================================
// 19. MOST USED CYPRESS COMMANDS
// ============================================================

/*

cy.visit()
cy.get()
cy.contains()
cy.find()
cy.click()
cy.type()
cy.clear()
cy.check()
cy.uncheck()
cy.select()
cy.should()
cy.then()
cy.url()
cy.title()
cy.intercept()
cy.request()
cy.fixture()
cy.wait()
cy.screenshot()
cy.go()
cy.reload()
cy.scrollIntoView()
cy.focus()
cy.blur()
cy.invoke()
cy.each()

*/


// ============================================================
// 20. QUICK MEMORY FORMAT
// ============================================================

/*

Navigate       -> cy.visit()
Find           -> cy.get()
Text           -> cy.contains()
Click          -> cy.click()
Type           -> cy.type()
Clear          -> cy.clear()
Checkbox       -> cy.check()
Uncheck        -> cy.uncheck()
Dropdown       -> cy.select()
Assertion      -> cy.should()
URL            -> cy.url()
Title          -> cy.title()
API            -> cy.request()
Mock API       -> cy.intercept()
Test data      -> cy.fixture()
Wait           -> cy.wait()
Screenshot     -> cy.screenshot()
Back           -> cy.go("back")
Forward        -> cy.go("forward")
Reload         -> cy.reload()

*/
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


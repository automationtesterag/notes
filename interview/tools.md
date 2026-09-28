

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
// ============================================================
// PLAYWRIGHT - SETUP + CONFIGURATION + RUN COMMANDS
// ============================================================

// ============================================================
// 1. INSTALLATION
// ============================================================

// Create project
// npm init playwright@latest

// OR install Playwright Test in existing project
// npm install --save-dev @playwright/test

// Install browsers
// npx playwright install

// Run tests
// npx playwright test

// Open HTML report
// npx playwright show-report


// ============================================================
// 2. PROJECT STRUCTURE
// ============================================================

/*
playwright-project/
│
├── tests/
│   └── login.spec.ts
│
├── playwright.config.ts
├── package.json
├── test-results/
└── playwright-report/
*/


// ============================================================
// 3. playwright.config.ts
// ============================================================

import { defineConfig, devices } from "@playwright/test";

export default defineConfig({

  // Test directory
  testDir: "./tests",

  // Run tests in parallel
  fullyParallel: true,

  // Prevent accidental test.only in CI
  forbidOnly: !!process.env.CI,

  // Retry failed tests in CI
  retries: process.env.CI ? 2 : 0,

  // Number of workers
  workers: process.env.CI ? 1 : undefined,

  // Reporter
  reporter: [
    ["list"],
    ["html", { open: "never" }]
  ],

  // Common test settings
  use: {

    // Base URL
    baseURL: "https://example.com",

    // Screenshot
    screenshot: "only-on-failure",

    // Video
    video: "retain-on-failure",

    // Trace
    trace: "on-first-retry",

    // Browser settings
    headless: true,

    // Action timeout
    actionTimeout: 10000,

    // Navigation timeout
    navigationTimeout: 30000,

    // Ignore HTTPS errors if required
    ignoreHTTPSErrors: true
  },

  // Browser projects
  projects: [

    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"]
      }
    },

    {
      name: "firefox",
      use: {
        ...devices["Desktop Firefox"]
      }
    },

    {
      name: "webkit",
      use: {
        ...devices["Desktop Safari"]
      }
    }
  ]
});


// ============================================================
// 4. BASIC TEST STRUCTURE
// ============================================================

import { test, expect } from "@playwright/test";

test(
  "Basic Playwright Test",
  async ({ page }) => {

    // Test actions

  }
);


// ============================================================
// 5. COMMON PLAYWRIGHT ACTIONS
// ============================================================

test(
  "Common Playwright Actions",
  async ({ page }) => {

    // --------------------------------------------------------
    // Navigate
    // --------------------------------------------------------

    await page.goto("/login");

    // Full URL can also be used
    // await page.goto("https://example.com/login");


    // --------------------------------------------------------
    // Locator
    // --------------------------------------------------------

    const username =
      page.locator("#username");


    // --------------------------------------------------------
    // Enter text
    // --------------------------------------------------------

    await username.fill("Anudeep");


    // --------------------------------------------------------
    // Clear
    // --------------------------------------------------------

    await username.clear();


    // --------------------------------------------------------
    // Enter again
    // --------------------------------------------------------

    await username.fill("Anudeep");


    // --------------------------------------------------------
    // Click
    // --------------------------------------------------------

    await page.locator("#login")
      .click();


    // --------------------------------------------------------
    // Get text
    // --------------------------------------------------------

    const text =
      await page.locator("#message")
        .textContent();

    console.log(text);


    // --------------------------------------------------------
    // Get value
    // --------------------------------------------------------

    const value =
      await username.inputValue();

    console.log(value);


    // --------------------------------------------------------
    // Check displayed
    // --------------------------------------------------------

    await expect(
      page.locator("#message")
    ).toBeVisible();


    // --------------------------------------------------------
    // Check enabled
    // --------------------------------------------------------

    await expect(
      page.locator("#login")
    ).toBeEnabled();


    // --------------------------------------------------------
    // Check disabled
    // --------------------------------------------------------

    await expect(
      page.locator("#login")
    ).toBeDisabled();


    // --------------------------------------------------------
    // Checkbox
    // --------------------------------------------------------

    await page.locator("#terms")
      .check();


    // --------------------------------------------------------
    // Uncheck
    // --------------------------------------------------------

    await page.locator("#terms")
      .uncheck();


    // --------------------------------------------------------
    // Radio button
    // --------------------------------------------------------

    await page.locator("#male")
      .check();


    // --------------------------------------------------------
    // Dropdown
    // --------------------------------------------------------

    await page.locator("#country")
      .selectOption("IN");


    // --------------------------------------------------------
    // Hover
    // --------------------------------------------------------

    await page.locator("#menu")
      .hover();


    // --------------------------------------------------------
    // Keyboard
    // --------------------------------------------------------

    await page.locator("#username")
      .press("Control+A");


    // --------------------------------------------------------
    // Get attribute
    // --------------------------------------------------------

    const href =
      await page.locator("#link")
        .getAttribute("href");

    console.log(href);


    // --------------------------------------------------------
    // URL validation
    // --------------------------------------------------------

    await expect(page)
      .toHaveURL(/dashboard/);


    // --------------------------------------------------------
    // Title validation
    // --------------------------------------------------------

    await expect(page)
      .toHaveTitle(/Dashboard/);


    // --------------------------------------------------------
    // Text validation
    // --------------------------------------------------------

    await expect(
      page.locator("#message")
    ).toHaveText("Login successful");


    // --------------------------------------------------------
    // Contains text
    // --------------------------------------------------------

    await expect(
      page.locator("#message")
    ).toContainText("Success");


    // --------------------------------------------------------
    // Screenshot
    // --------------------------------------------------------

    await page.screenshot({
      path: "screenshots/login.png"
    });


    // --------------------------------------------------------
    // Scroll
    // --------------------------------------------------------

    await page.locator("#footer")
      .scrollIntoViewIfNeeded();


    // --------------------------------------------------------
    // Reload
    // --------------------------------------------------------

    await page.reload();


    // --------------------------------------------------------
    // Browser back
    // --------------------------------------------------------

    await page.goBack();


    // --------------------------------------------------------
    // Browser forward
    // --------------------------------------------------------

    await page.goForward();

  }
);


// ============================================================
// 6. COMMON LOCATORS
// ============================================================

test(
  "Common Locators",
  async ({ page }) => {

    // CSS
    page.locator("#username");

    // Class
    page.locator(".login-button");

    // Attribute
    page.locator("[name='username']");

    // Text
    page.getByText("Login");

    // Role
    page.getByRole("button", {
      name: "Login"
    });

    // Label
    page.getByLabel("Username");

    // Placeholder
    page.getByPlaceholder("Enter username");

    // Test ID
    page.getByTestId("login-button");

    // XPath
    page.locator(
      "//button[text()='Login']"
    );

  }
);


// ============================================================
// 7. COMMON ASSERTIONS
// ============================================================

test(
  "Common Assertions",
  async ({ page }) => {

    await expect(
      page.locator("#message")
    ).toBeVisible();

    await expect(
      page.locator("#login")
    ).toBeEnabled();

    await expect(
      page.locator("#login")
    ).toBeDisabled();

    await expect(
      page.locator("#username")
    ).toHaveValue("Anudeep");

    await expect(
      page.locator("#message")
    ).toHaveText("Success");

    await expect(
      page.locator("#message")
    ).toContainText("Success");

    await expect(
      page.locator("#link")
    ).toHaveAttribute(
      "href",
      "/home"
    );

    await expect(page)
      .toHaveURL(/dashboard/);

    await expect(page)
      .toHaveTitle(/Dashboard/);

  }
);


// ============================================================
// 8. WAITING
// ============================================================

test(
  "Wait Examples",
  async ({ page }) => {

    // Playwright automatically waits for
    // most locator actions and assertions.

    await page.locator("#login")
      .waitFor();

    await page.locator("#message")
      .waitFor({
        state: "visible"
      });

    // Explicit timeout when required
    await page.waitForTimeout(1000);

    // Wait for URL
    await page.waitForURL(/dashboard/);

    // Wait for load state
    await page.waitForLoadState("networkidle");

  }
);


// ============================================================
// 9. MOUSE ACTIONS
// ============================================================

test(
  "Mouse Actions",
  async ({ page }) => {

    // Click
    await page.locator("#button")
      .click();

    // Double click
    await page.locator("#button")
      .dblclick();

    // Right click
    await page.locator("#button")
      .click({
        button: "right"
      });

    // Hover
    await page.locator("#menu")
      .hover();

    // Click at coordinates
    await page.mouse.click(500, 300);

  }
);


// ============================================================
// 10. KEYBOARD ACTIONS
// ============================================================

test(
  "Keyboard Actions",
  async ({ page }) => {

    await page.keyboard.press("Enter");

    await page.keyboard.press("Escape");

    await page.keyboard.press("Control+A");

    await page.keyboard.press("Backspace");

    await page.locator("#username")
      .press("Enter");

  }
);


// ============================================================
// 11. MULTIPLE TABS / WINDOWS
// ============================================================

test(
  "Multiple Tabs",
  async ({ page, context }) => {

    const newPagePromise =
      context.waitForEvent("page");

    await page.locator("#new-tab")
      .click();

    const newPage =
      await newPagePromise;

    await newPage.waitForLoadState();

    console.log(
      await newPage.title()
    );

    await newPage.close();

  }
);


// ============================================================
// 12. IFRAME
// ============================================================

test(
  "iFrame",
  async ({ page }) => {

    const frame =
      page.frameLocator("#payment-frame");

    await frame
      .locator("#cardNumber")
      .fill("4111111111111111");

    await frame
      .getByRole("button", {
        name: "Pay"
      })
      .click();

  }
);


// ============================================================
// 13. FILE UPLOAD
// ============================================================

test(
  "File Upload",
  async ({ page }) => {

    await page.locator(
      "input[type='file']"
    ).setInputFiles(
      "test-data/sample.pdf"
    );

  }
);


// ============================================================
// 14. DOWNLOAD
// ============================================================

test(
  "File Download",
  async ({ page }) => {

    const downloadPromise =
      page.waitForEvent("download");

    await page.locator("#download")
      .click();

    const download =
      await downloadPromise;

    await download.saveAs(
      "downloads/file.pdf"
    );

  }
);


// ============================================================
// 15. API REQUEST
// ============================================================

test(
  "API Request",
  async ({ request }) => {

    // GET
    const getResponse =
      await request.get("/users");

    console.log(
      getResponse.status()
    );

    console.log(
      await getResponse.json()
    );


    // POST
    const postResponse =
      await request.post("/users", {
        data: {
          name: "Anudeep",
          email: "anudeep@example.com"
        }
      });

    expect(postResponse.status())
      .toBe(201);

  }
);


// ============================================================
// 16. API + UI HYBRID
// ============================================================

test(
  "API + UI",
  async ({ page, request }) => {

    // Create data using API
    const response =
      await request.post("/users", {
        data: {
          name: "Anudeep"
        }
      });

    expect(response.ok())
      .toBeTruthy();

    const user =
      await response.json();

    console.log(user);

    // Open UI
    await page.goto("/users");

    // Validate created user
    await expect(
      page.getByText("Anudeep")
    ).toBeVisible();

  }
);


// ============================================================
// 17. STORAGE STATE / AUTHENTICATION
// ============================================================

test(
  "Authenticated Page",
  async ({ page }) => {

    // If storageState is configured
    // in playwright.config.ts,
    // the test starts authenticated.

    await page.goto("/dashboard");

    await expect(
      page.getByText("Dashboard")
    ).toBeVisible();

  }
);


// ============================================================
// 18. ENVIRONMENT VARIABLES
// ============================================================

test(
  "Environment Variables",
  async ({ page }) => {

    const baseUrl =
      process.env.BASE_URL ||
      "https://example.com";

    await page.goto(
      `${baseUrl}/login`
    );

  }
);


// ============================================================
// 19. NPM / PLAYWRIGHT RUN COMMANDS
// ============================================================

/*

# Run all tests
npx playwright test

# Run specific test
npx playwright test tests/login.spec.ts

# Run specific test by title
npx playwright test -g "Common Playwright Actions"

# Run Chromium
npx playwright test --project=chromium

# Run Firefox
npx playwright test --project=firefox

# Run WebKit
npx playwright test --project=webkit

# Run headed
npx playwright test --headed

# Run in debug mode
npx playwright test --debug

# Run specific browser + headed
npx playwright test --project=chromium --headed

# Open HTML report
npx playwright show-report

# Run with trace
npx playwright test --trace on

# Install browsers
npx playwright install

# Install specific browser
npx playwright install chromium

*/


// ============================================================
// 20. PACKAGE.JSON SCRIPTS
// ============================================================

/*

{
  "scripts": {
    "test": "playwright test",
    "test:headed": "playwright test --headed",
    "test:debug": "playwright test --debug",
    "test:chromium": "playwright test --project=chromium",
    "test:firefox": "playwright test --project=firefox",
    "test:webkit": "playwright test --project=webkit",
    "report": "playwright show-report"
  }
}

*/


// ============================================================
// 21. RUN USING NPM SCRIPTS
// ============================================================

/*

npm test

npm run test:headed

npm run test:debug

npm run test:chromium

npm run test:firefox

npm run test:webkit

npm run report

*/


// ============================================================
// 22. PLAYWRIGHT EXECUTION FLOW
// ============================================================

/*

Install
   ↓
npm init playwright@latest
   ↓
playwright.config.ts
   ↓
tests/*.spec.ts
   ↓
test()
   ↓
page / request fixture
   ↓
locator
   ↓
action
   ↓
expect()
   ↓
npx playwright test
   ↓
HTML Report


*/


// ============================================================
// 23. MOST USED PLAYWRIGHT COMMANDS
// ============================================================

/*

NAVIGATION
----------
page.goto()
page.reload()
page.goBack()
page.goForward()


LOCATORS
--------
page.locator()
page.getByRole()
page.getByText()
page.getByLabel()
page.getByPlaceholder()
page.getByTestId()


ACTIONS
-------
click()
dblclick()
fill()
clear()
type()
press()
check()
uncheck()
selectOption()
hover()
focus()
blur()
scrollIntoViewIfNeeded()


GET DATA
--------
textContent()
innerText()
inputValue()
getAttribute()
isVisible()
isEnabled()
isChecked()


ASSERTIONS
----------
expect().toBeVisible()
expect().toBeEnabled()
expect().toBeDisabled()
expect().toHaveText()
expect().toContainText()
expect().toHaveValue()
expect().toHaveAttribute()
expect(page).toHaveURL()
expect(page).toHaveTitle()


BROWSER
-------
page.screenshot()
context.waitForEvent("page")
page.waitForEvent()
page.waitForLoadState()
page.waitForURL()


API
---
request.get()
request.post()
request.put()
request.patch()
request.delete()


FILES
-----
setInputFiles()
waitForEvent("download")
download.saveAs()


*/


// ============================================================
// 24. QUICK MEMORY FORMAT
// ============================================================

/*

Navigate       -> page.goto()
Find           -> page.locator()
Role           -> page.getByRole()
Text           -> page.getByText()
Click          -> locator.click()
Type           -> locator.fill()
Clear          -> locator.clear()
Checkbox       -> locator.check()
Uncheck        -> locator.uncheck()
Dropdown       -> locator.selectOption()
Hover          -> locator.hover()
Keyboard       -> locator.press()
Text           -> locator.textContent()
Value          -> locator.inputValue()
Attribute      -> locator.getAttribute()
Assertion      -> expect()
URL            -> expect(page).toHaveURL()
Title          -> expect(page).toHaveTitle()
Screenshot     -> page.screenshot()
iFrame         -> page.frameLocator()
API GET        -> request.get()
API POST       -> request.post()
Upload         -> locator.setInputFiles()
Download       -> page.waitForEvent("download")
Reload         -> page.reload()
Back           -> page.goBack()
Forward        -> page.goForward()

*/


// ============================================================
// 25. SIMPLE INTERVIEW-READY TEST
// ============================================================

test(
  "Login - Common Actions",
  async ({ page }) => {

    await page.goto("/login");

    const username =
      page.getByLabel("Username");

    const password =
      page.getByLabel("Password");

    await username.fill("Anudeep");

    await password.fill("Password123");

    await page.getByRole("button", {
      name: "Login"
    }).click();

    await expect(
      page.getByText("Login successful")
    ).toBeVisible();

    await expect(page)
      .toHaveURL(/dashboard/);

  }
);
```

---

## 5. WebdriverIO TypeScript — Common Web + Mobile Actions

```typescript
// ============================================================
// WEBDRIVERIO (WDIO) - SETUP + CONFIGURATION + RUN COMMANDS
// WEB + MOBILE / APPIUM
// ============================================================


// ============================================================
// 1. INSTALLATION
// ============================================================

// Create project
// mkdir wdio-project
// cd wdio-project

// Initialize WDIO project
// npm init wdio@latest .

// OR install WebdriverIO manually
// npm install --save-dev webdriverio @wdio/cli @wdio/local-runner
// npm install --save-dev @wdio/mocha-framework
// npm install --save-dev @wdio/spec-reporter
// npm install --save-dev expect-webdriverio


// ============================================================
// 2. PROJECT STRUCTURE
// ============================================================

/*
wdio-project/
│
├── test/
│   ├── specs/
│   │   ├── web/
│   │   │   └── login.spec.ts
│   │   │
│   │   └── mobile/
│   │       └── login.spec.ts
│   │
│   ├── pageobjects/
│   │   └── LoginPage.ts
│   │
│   └── data/
│       └── testData.json
│
├── wdio.conf.ts
├── package.json
└── node_modules/
*/


// ============================================================
// 3. wdio.conf.ts - WEB CONFIGURATION
// ============================================================

import { browser } from "@wdio/globals";

export const config = {

  // Test runner
  runner: "local",

  // Test framework
  framework: "mocha",

  // Test files
  specs: [
    "./test/specs/**/*.spec.ts"
  ],

  // Browser capabilities
  capabilities: [
    {
      browserName: "chrome",

      "goog:chromeOptions": {
        args: [
          "--start-maximized"
        ]
      }
    }
  ],

  // Base URL
  baseUrl: "https://example.com",

  // Timeout
  waitforTimeout: 10000,

  // Connection timeout
  connectionRetryTimeout: 120000,

  // Retry commands
  connectionRetryCount: 3,

  // Reporters
  reporters: [
    "spec"
  ],

  // Mocha options
  mochaOpts: {
    ui: "bdd",
    timeout: 60000
  }

};


// ============================================================
// 4. wdio.conf.ts - MOBILE / APPIUM CONFIGURATION
// ============================================================

/*
For Android:

capabilities: [
  {
    platformName: "Android",
    "appium:automationName": "UiAutomator2",
    "appium:deviceName": "Android Emulator",
    "appium:appPackage": "com.example.app",
    "appium:appActivity": ".MainActivity"
  }
]

For iOS:

capabilities: [
  {
    platformName: "iOS",
    "appium:automationName": "XCUITest",
    "appium:deviceName": "iPhone 15",
    "appium:bundleId": "com.example.app"
  }
]
*/


// ============================================================
// 5. BASIC WEBDRIVERIO TEST
// ============================================================

describe("Basic WDIO Test", () => {

  it("should open application", async () => {

    await browser.url("/");

    console.log(
      await browser.getTitle()
    );

  });

});


// ============================================================
// 6. COMMON WDIO WEB ACTIONS
// ============================================================

describe("Common WDIO Web Actions", () => {

  it("should perform common web actions", async () => {

    // --------------------------------------------------------
    // Navigate
    // --------------------------------------------------------

    await browser.url("/login");


    // --------------------------------------------------------
    // Get URL
    // --------------------------------------------------------

    console.log(
      await browser.getUrl()
    );


    // --------------------------------------------------------
    // Get title
    // --------------------------------------------------------

    console.log(
      await browser.getTitle()
    );


    // --------------------------------------------------------
    // Find element
    // --------------------------------------------------------

    const username =
      await $("#username");


    // --------------------------------------------------------
    // Enter text
    // --------------------------------------------------------

    await username.setValue("Anudeep");


    // --------------------------------------------------------
    // Clear text
    // --------------------------------------------------------

    await username.clearValue();


    // --------------------------------------------------------
    // Enter text again
    // --------------------------------------------------------

    await username.setValue("Anudeep");


    // --------------------------------------------------------
    // Click
    // --------------------------------------------------------

    await $("#login").click();


    // --------------------------------------------------------
    // Get text
    // --------------------------------------------------------

    const text =
      await $("#message").getText();

    console.log(text);


    // --------------------------------------------------------
    // Get value
    // --------------------------------------------------------

    const value =
      await username.getValue();

    console.log(value);


    // --------------------------------------------------------
    // Check displayed
    // --------------------------------------------------------

    await expect(
      $("#message")
    ).toBeDisplayed();


    // --------------------------------------------------------
    // Check enabled
    // --------------------------------------------------------

    await expect(
      $("#login")
    ).toBeEnabled();


    // --------------------------------------------------------
    // Check disabled
    // --------------------------------------------------------

    await expect(
      $("#login")
    ).toBeDisabled();


    // --------------------------------------------------------
    // Check selected
    // --------------------------------------------------------

    await expect(
      $("#terms")
    ).toBeSelected();


    // --------------------------------------------------------
    // Wait for displayed
    // --------------------------------------------------------

    await $("#message")
      .waitForDisplayed();


    // --------------------------------------------------------
    // Wait for clickable
    // --------------------------------------------------------

    await $("#login")
      .waitForClickable();


    // --------------------------------------------------------
    // Checkbox
    // --------------------------------------------------------

    await $("#terms").click();


    // --------------------------------------------------------
    // Keyboard
    // --------------------------------------------------------

    await browser.keys("Enter");


    // --------------------------------------------------------
    // Hover
    // --------------------------------------------------------

    await $("#menu").moveTo();


    // --------------------------------------------------------
    // Scroll
    // --------------------------------------------------------

    await $("#footer")
      .scrollIntoView();


    // --------------------------------------------------------
    // Get attribute
    // --------------------------------------------------------

    const href =
      await $("#link")
        .getAttribute("href");

    console.log(href);


    // --------------------------------------------------------
    // Screenshot
    // --------------------------------------------------------

    await browser.saveScreenshot(
      "./screenshots/web-login.png"
    );


    // --------------------------------------------------------
    // Refresh
    // --------------------------------------------------------

    await browser.refresh();


    // --------------------------------------------------------
    // Back
    // --------------------------------------------------------

    await browser.back();


    // --------------------------------------------------------
    // Forward
    // --------------------------------------------------------

    await browser.forward();

  });

});


// ============================================================
// 7. COMMON WDIO WEB LOCATORS
// ============================================================

describe("WDIO Locators", () => {

  it("Common locator examples", async () => {

    // ID
    await $("#username");


    // CSS
    await $(".login-button");


    // Attribute
    await $("[name='username']");


    // XPath
    await $("//button[text()='Login']");


    // Text
    await $("//*[text()='Login']");


    // Accessibility ID
    // Commonly used for mobile
    await $("~login");


    // Tag
    await $("button");

  });

});


// ============================================================
// 8. DROPDOWN
// ============================================================

describe("Dropdown", () => {

  it("should select dropdown option", async () => {

    const country =
      await $("#country");

    // Select by visible text
    await country.selectByVisibleText(
      "India"
    );

    // Select by value
    await country.selectByAttribute(
      "value",
      "IN"
    );

    // Select by index
    await country.selectByIndex(1);

  });

});


// ============================================================
// 9. WDIO ASSERTIONS
// ============================================================

describe("WDIO Assertions", () => {

  it("should validate elements", async () => {

    await expect(
      $("#message")
    ).toBeDisplayed();

    await expect(
      $("#login")
    ).toBeEnabled();

    await expect(
      $("#login")
    ).toBeDisabled();

    await expect(
      $("#message")
    ).toHaveText(
      "Login successful"
    );

    await expect(
      $("#message")
    ).toHaveTextContaining(
      "Success"
    );

    await expect(
      $("#username")
    ).toHaveValue(
      "Anudeep"
    );

    await expect(
      $("#link")
    ).toHaveAttribute(
      "href",
      "/home"
    );

    await expect(browser)
      .toHaveUrl(
        expect.stringContaining(
          "/dashboard"
        )
      );

  });

});


// ============================================================
// 10. WAITING
// ============================================================

describe("WDIO Waits", () => {

  it("should wait for elements", async () => {

    // Wait for display
    await $("#message")
      .waitForDisplayed();


    // Wait for clickable
    await $("#login")
      .waitForClickable();


    // Wait for enabled
    await $("#login")
      .waitForEnabled();


    // Wait until custom condition
    await $("#message")
      .waitUntil(
        async () =>
          (await $("#message").getText())
            === "Success",
        {
          timeout: 10000,
          timeoutMsg:
            "Message was not displayed"
        }
      );

  });

});


// ============================================================
// 11. MOUSE ACTIONS
// ============================================================

describe("WDIO Mouse Actions", () => {

  it("should perform mouse actions", async () => {

    // Click
    await $("#button").click();


    // Double click
    await $("#button").doubleClick();


    // Hover
    await $("#menu").moveTo();


    // Drag and drop
    await $("#source")
      .dragAndDrop(
        $("#target")
      );

  });

});


// ============================================================
// 12. KEYBOARD ACTIONS
// ============================================================

describe("WDIO Keyboard Actions", () => {

  it("should perform keyboard actions", async () => {

    await browser.keys("Enter");

    await browser.keys("Escape");

    await browser.keys("Tab");

    await browser.keys(
      ["Control", "a"]
    );

    await browser.keys("Backspace");

  });

});


// ============================================================
// 13. MULTIPLE WINDOWS / TABS
// ============================================================

describe("Multiple Windows", () => {

  it("should handle multiple windows", async () => {

    // Open new window
    await browser.newWindow(
      "https://example.com"
    );


    // Get window handles
    const handles =
      await browser.getWindowHandles();

    console.log(handles);


    // Switch window
    await browser.switchToWindow(
      handles[0]
    );


    // Close current window
    await browser.closeWindow();

  });

});


// ============================================================
// 14. IFRAME
// ============================================================

describe("iFrame", () => {

  it("should handle iframe", async () => {

    const frame =
      await $("#payment-frame");

    await frame.switchToFrame();

    await $("#cardNumber")
      .setValue(
        "4111111111111111"
      );

    await browser
      .switchToParentFrame();

  });

});


// ============================================================
// 15. JAVASCRIPT EXECUTION
// ============================================================

describe("JavaScript", () => {

  it("should execute JavaScript", async () => {

    const title =
      await browser.execute(
        () => document.title
      );

    console.log(title);


    await browser.execute(() => {

      window.scrollTo(
        0,
        document.body.scrollHeight
      );

    });

  });

});


// ============================================================
// 16. COOKIES
// ============================================================

describe("Cookies", () => {

  it("should handle cookies", async () => {

    // Add cookie
    await browser.setCookies({
      name: "token",
      value: "abc123"
    });


    // Get cookies
    const cookies =
      await browser.getCookies();

    console.log(cookies);


    // Delete cookies
    await browser.deleteCookies();

  });

});


// ============================================================
// 17. MOBILE / APPIUM - ANDROID
// ============================================================

/*

ANDROID CAPABILITIES:

capabilities: [
  {
    platformName: "Android",

    "appium:automationName":
      "UiAutomator2",

    "appium:deviceName":
      "Android Emulator",

    "appium:appPackage":
      "com.example.app",

    "appium:appActivity":
      ".MainActivity"
  }
]

*/


describe("Android Mobile", () => {

  it("Common Android Actions", async () => {

    // --------------------------------------------------------
    // Accessibility ID
    // --------------------------------------------------------

    const username =
      await $("~username");


    // --------------------------------------------------------
    // Enter text
    // --------------------------------------------------------

    await username.setValue(
      "Anudeep"
    );


    // --------------------------------------------------------
    // Clear text
    // --------------------------------------------------------

    await username.clearValue();


    // --------------------------------------------------------
    // Enter password
    // --------------------------------------------------------

    await $("~password")
      .setValue(
        "Password123"
      );


    // --------------------------------------------------------
    // Click
    // --------------------------------------------------------

    await $("~login")
      .click();


    // --------------------------------------------------------
    // Get text
    // --------------------------------------------------------

    const message =
      await $("~message")
        .getText();

    console.log(message);


    // --------------------------------------------------------
    // Check displayed
    // --------------------------------------------------------

    await expect(
      $("~home")
    ).toBeDisplayed();


    // --------------------------------------------------------
    // Check enabled
    // --------------------------------------------------------

    await expect(
      $("~login")
    ).toBeEnabled();


    // --------------------------------------------------------
    // Check selected
    // --------------------------------------------------------

    await expect(
      $("~remember")
    ).toBeSelected();


    // --------------------------------------------------------
    // Android UIAutomator
    // --------------------------------------------------------

    const settings =
      await $(
        'android=new UiSelector().text("Settings")'
      );


    // Click
    await settings.click();


    // --------------------------------------------------------
    // Mobile scroll
    // --------------------------------------------------------

    await $("~Settings")
      .scrollIntoView();


    // --------------------------------------------------------
    // Long press
    // --------------------------------------------------------

    await $("~element")
      .longPress();


    // --------------------------------------------------------
    // Android back
    // --------------------------------------------------------

    await browser.back();


    // --------------------------------------------------------
    // Device orientation
    // --------------------------------------------------------

    await browser.setOrientation(
      "LANDSCAPE"
    );


    // --------------------------------------------------------
    // Lock device
    // --------------------------------------------------------

    await browser.lock();


    // --------------------------------------------------------
    // Unlock device
    // --------------------------------------------------------

    await browser.unlock();


    // --------------------------------------------------------
    // Hide keyboard
    // --------------------------------------------------------

    await browser.hideKeyboard();


    // --------------------------------------------------------
    // Open notifications
    // --------------------------------------------------------

    await browser.openNotifications();


    // --------------------------------------------------------
    // Screenshot
    // --------------------------------------------------------

    await browser.saveScreenshot(
      "./screenshots/android.png"
    );


    // --------------------------------------------------------
    // Current package
    // --------------------------------------------------------

    console.log(
      await browser.getCurrentPackage()
    );


    // --------------------------------------------------------
    // Current activity
    // --------------------------------------------------------

    console.log(
      await browser.getCurrentActivity()
    );

  });

});


// ============================================================
// 18. MOBILE / APPIUM - iOS
// ============================================================

/*

iOS CAPABILITIES:

capabilities: [
  {
    platformName: "iOS",

    "appium:automationName":
      "XCUITest",

    "appium:deviceName":
      "iPhone 15",

    "appium:bundleId":
      "com.example.app"
  }
]

*/


describe("iOS Mobile", () => {

  it("Common iOS Actions", async () => {

    // --------------------------------------------------------
    // Accessibility ID
    // --------------------------------------------------------

    const username =
      await $("~username");


    // --------------------------------------------------------
    // Enter text
    // --------------------------------------------------------

    await username.setValue(
      "Anudeep"
    );


    // --------------------------------------------------------
    // Clear
    // --------------------------------------------------------

    await username.clearValue();


    // --------------------------------------------------------
    // Password
    // --------------------------------------------------------

    await $("~password")
      .setValue(
        "Password123"
      );


    // --------------------------------------------------------
    // Click
    // --------------------------------------------------------

    await $("~login")
      .click();


    // --------------------------------------------------------
    // Get text
    // --------------------------------------------------------

    const message =
      await $("~message")
        .getText();

    console.log(message);


    // --------------------------------------------------------
    // Check displayed
    // --------------------------------------------------------

    await expect(
      $("~home")
    ).toBeDisplayed();


    // --------------------------------------------------------
    // Check enabled
    // --------------------------------------------------------

    await expect(
      $("~login")
    ).toBeEnabled();


    // --------------------------------------------------------
    // iOS Predicate String
    // --------------------------------------------------------

    const settings =
      await $(
        "-ios predicate string:label == 'Settings'"
      );


    // Click
    await settings.click();


    // --------------------------------------------------------
    // Scroll
    // --------------------------------------------------------

    await $("~Settings")
      .scrollIntoView();


    // --------------------------------------------------------
    // Long press
    // --------------------------------------------------------

    await $("~element")
      .longPress();


    // --------------------------------------------------------
    // Device orientation
    // --------------------------------------------------------

    await browser.setOrientation(
      "LANDSCAPE"
    );


    // --------------------------------------------------------
    // Lock device
    // --------------------------------------------------------

    await browser.lock();


    // --------------------------------------------------------
    // Unlock device
    // --------------------------------------------------------

    await browser.unlock();


    // --------------------------------------------------------
    // Screenshot
    // --------------------------------------------------------

    await browser.saveScreenshot(
      "./screenshots/ios.png"
    );

  });

});


// ============================================================
// 19. HYBRID APP - NATIVE ↔ WEBVIEW
// ============================================================

describe("Hybrid Mobile App", () => {

  it("should switch between Native and WebView", async () => {

    // Get available contexts
    const contexts =
      await browser.getContexts();

    console.log(contexts);


    // Switch to WebView
    await browser.switchContext(
      "WEBVIEW"
    );


    // WebView DOM
    await $("#username")
      .setValue("Anudeep");


    await $("#login")
      .click();


    // Switch back to native
    await browser.switchContext(
      "NATIVE_APP"
    );


    // Native element
    await $("~home")
      .click();

  });

});


// ============================================================
// 20. APP LIFECYCLE
// ============================================================

describe("Mobile App Lifecycle", () => {

  it("should control application", async () => {

    // Launch app
    await browser.activateApp(
      "com.example.app"
    );


    // Background app
    await browser.background(
      5
    );


    // Terminate app
    await browser.terminateApp(
      "com.example.app"
    );

  });

});


// ============================================================
// 21. MOBILE GESTURE
// ============================================================

describe("Mobile Gesture", () => {

  it("Swipe using W3C Actions", async () => {

    await browser.action(
      "pointer",
      {
        parameters: {
          pointerType: "touch"
        }
      }
    )
      .move({
        x: 500,
        y: 700
      })
      .down()
      .move({
        x: 500,
        y: 300,
        duration: 700
      })
      .up()
      .perform();

  });

});


// ============================================================
// 22. API REQUEST
// ============================================================

describe("API", () => {

  it("API request", async () => {

    const response =
      await fetch(
        "https://api.example.com/users"
      );

    console.log(
      response.status
    );

    console.log(
      await response.json()
    );

  });

});


// ============================================================
// 23. RUN COMMANDS
// ============================================================

/*

# Run all tests
npx wdio run wdio.conf.ts


# Run specific test
npx wdio run wdio.conf.ts \
  --spec test/specs/web/login.spec.ts


# Run mobile test
npx wdio run wdio.conf.ts \
  --spec test/specs/mobile/login.spec.ts


# Run with a specific suite
npx wdio run wdio.conf.ts \
  --suite web


# Debug
NODE_OPTIONS='--inspect-brk' \
npx wdio run wdio.conf.ts


*/


// ============================================================
// 24. PACKAGE.JSON SCRIPTS
// ============================================================

/*

{
  "scripts": {

    "test":
      "wdio run wdio.conf.ts",

    "test:web":
      "wdio run wdio.conf.ts --suite web",

    "test:mobile":
      "wdio run wdio.conf.ts --suite mobile",

    "test:login":
      "wdio run wdio.conf.ts --spec test/specs/web/login.spec.ts"

  }
}

*/


// ============================================================
// 25. SUITES
// ============================================================

/*

// wdio.conf.ts

suites: {

  web: [
    "./test/specs/web/**/*.spec.ts"
  ],

  mobile: [
    "./test/specs/mobile/**/*.spec.ts"
  ]

}


Run:

npm run test:web

npm run test:mobile

*/


// ============================================================
// 26. MOST USED WDIO COMMANDS
// ============================================================

/*

BROWSER
-------
browser.url()
browser.getUrl()
browser.getTitle()
browser.refresh()
browser.back()
browser.forward()
browser.newWindow()
browser.switchToWindow()
browser.closeWindow()
browser.saveScreenshot()


LOCATORS
--------
$()
$$()
~accessibilityId
CSS
XPath
Android UIAutomator
iOS Predicate


ELEMENT ACTIONS
---------------
click()
doubleClick()
setValue()
clearValue()
getText()
getValue()
getAttribute()
isDisplayed()
isEnabled()
isSelected()
moveTo()
scrollIntoView()
dragAndDrop()
longPress()


WAIT
----
waitForDisplayed()
waitForClickable()
waitForEnabled()
waitUntil()


KEYBOARD
--------
browser.keys()


ASSERTIONS
----------
expect().toBeDisplayed()
expect().toBeEnabled()
expect().toBeDisabled()
expect().toBeSelected()
expect().toHaveText()
expect().toHaveTextContaining()
expect().toHaveValue()
expect().toHaveAttribute()
expect(browser).toHaveUrl()


MOBILE
------
browser.back()
browser.lock()
browser.unlock()
browser.setOrientation()
browser.hideKeyboard()
browser.openNotifications()
browser.activateApp()
browser.terminateApp()
browser.background()
browser.getContexts()
browser.switchContext()


ANDROID
-------
android=new UiSelector()
getCurrentPackage()
getCurrentActivity()


iOS
---
-ios predicate string
-ios class chain


API
---
fetch()
*/


// ============================================================
// 27. QUICK MEMORY FORMAT
// ============================================================

/*

Navigate       -> browser.url()
Find           -> $()
Multiple       -> $$()
Click          -> click()
Type           -> setValue()
Clear          -> clearValue()
Text           -> getText()
Value          -> getValue()
Attribute      -> getAttribute()
Displayed      -> isDisplayed()
Enabled        -> isEnabled()
Selected       -> isSelected()
Wait           -> waitForDisplayed()
Hover          -> moveTo()
Scroll         -> scrollIntoView()
Keyboard       -> browser.keys()
Screenshot     -> browser.saveScreenshot()
Refresh        -> browser.refresh()
Back           -> browser.back()
Forward        -> browser.forward()

WEB
---
CSS            -> $("#login")
XPath          -> $("//button")
Attribute      -> $("[name='username']")

MOBILE
------
Accessibility  -> $("~login")
Android        -> $('android=new UiSelector()...')
iOS            -> $('-ios predicate string:...')

APP
---
Launch         -> browser.activateApp()
Background     -> browser.background()
Terminate      -> browser.terminateApp()
Context        -> browser.switchContext()
Lock           -> browser.lock()
Unlock         -> browser.unlock()
Orientation    -> browser.setOrientation()

*/


// ============================================================
// 28. SIMPLE INTERVIEW-READY WEB TEST
// ============================================================

describe("Login - Web", () => {

  it("should login successfully", async () => {

    await browser.url("/login");

    const username =
      await $("#username");

    const password =
      await $("#password");

    await username.setValue(
      "Anudeep"
    );

    await password.setValue(
      "Password123"
    );

    await $("#login")
      .click();

    await expect(
      $("#message")
    ).toBeDisplayed();

    await expect(
      $("#message")
    ).toHaveText(
      "Login successful"
    );

  });

});


// ============================================================
// 29. SIMPLE INTERVIEW-READY MOBILE TEST
// ============================================================

describe("Login - Android", () => {

  it("should login successfully", async () => {

    await $("~username")
      .setValue("Anudeep");

    await $("~password")
      .setValue("Password123");

    await $("~login")
      .click();

    await expect(
      $("~home")
    ).toBeDisplayed();

  });

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


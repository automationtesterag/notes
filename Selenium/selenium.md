# Selenium 4.49 — Java Study Notes

A complete, modern rewrite of the original notes. Legacy Selenium 3 patterns have been replaced throughout; old code only appears where seeing it side-by-side with the Selenium 4 replacement makes the migration clearer.

**Version:** 4.49.0 · **Protocol:** W3C WebDriver · **Language:** Java · **Driver management:** Selenium Manager

---

## Contents

**Foundations**
1. [Selenium overview](#1-selenium-overview)
2. [Selenium 3 → 4](#2-selenium-3--selenium-4)
3. [Key code changes](#3-key-code-changes-at-a-glance)
4. [Selenium 4 architecture](#4-selenium-4-architecture)
5. [WebDriver class hierarchy](#5-webdriver-class-hierarchy)
6. [Maven dependency](#6-maven-dependency)

**Browser & session**

7. [Starting a browser](#7-starting-a-browser)
8. [close() vs quit()](#8-close-vs-quit)
9. [Navigation](#9-browser-navigation)
10. [Title, URL, source](#10-reading-browser-state)
11. [Window management](#11-window-management)
12. [Browser options](#12-browser-options)
13. [Page load strategy](#13-page-load-strategy)

**Locators**

14. [Web elements](#14-web-elements)
15. [Finding elements](#15-finding-elements)
16. [Locator strategies](#16-locator-strategies)
17. [Locator best practices](#17-locator-best-practices)
18. [CSS selectors](#18-css-selectors)
19. [XPath (incl. axes)](#19-xpath)
20. [Relative locators](#20-relative-locators)

**Interacting with elements**

21. [WebElement methods](#21-webelement-methods)
22. [Dropdowns — Select](#22-dropdowns--select)
23. [Checkboxes & radio buttons](#23-checkboxes--radio-buttons)
24. [Actions API](#24-keyboard--mouse--the-actions-api)
25. [JavaScript execution](#25-javascript-execution--scrolling)

**Browser contexts**

26. [Alerts](#26-alerts)
27. [Frames & iframes](#27-frames--iframes)
28. [Windows & tabs](#28-windows--tabs)
29. [Cookies](#29-cookies)
30. [File upload](#30-file-upload)
31. [Screenshots](#31-screenshots)
32. [Shadow DOM](#32-shadow-dom)

**Synchronization**

33. [Why waits matter](#33-why-synchronization-matters)
34. [Implicit, explicit & fluent waits](#34-implicit-explicit--fluent-waits)
35. [Page load & script timeout](#35-page-load--script-timeout)

**Distributed & advanced**

36. [WebDriver BiDi](#36-webdriver-bidi)
37. [RemoteWebDriver & Grid 4](#37-remotewebdriver--selenium-grid-4)

**Framework design**

38. [Page Object Model](#38-page-object-model)
39. [Framework structure](#39-recommended-framework-structure)

**Reference**

40. [Interview cheat sheet](#40-interview-cheat-sheet)
41. [3 → 4 changes to memorize](#41-selenium-3--4-what-to-memorize)

---

## 1. Selenium overview

Selenium is an open-source umbrella project for browser automation, made up of three parts:

```text
Selenium
│
├── WebDriver        → drives real browsers, natively, local or remote
├── Selenium IDE      → record / playback for quick scripts
└── Selenium Grid      → distributes sessions across machines, for scale
```

For a professional automation framework, **Selenium WebDriver** is the component you build on. It talks to the browser directly using the W3C WebDriver protocol — no IDE, no recording, just code.

**[⬆ back to top](#contents)**

---

## 2. Selenium 3 → Selenium 4

The single most important table in this guide — almost every change below traces back to one of these rows.

| Selenium 3 | Selenium 4 |
|---|---|
| JSON Wire Protocol, with partial W3C support | **Pure W3C WebDriver standard** |
| Manual driver binary setup (`webdriver.chrome.driver`) | **Selenium Manager** — drivers resolved automatically |
| `TimeUnit.SECONDS`-based timeout APIs | **`Duration`**-based timeout APIs |
| `DesiredCapabilities` | Typed browser **`Options`** classes |
| Eight traditional locator strategies only | Same locators, plus **Relative Locators** |
| Selenium Grid 3 (Hub-only architecture) | **Grid 4** — Router / Distributor / Node architecture |
| No standard event or network API | **WebDriver BiDi** over WebSocket |
| Limited window/tab handling | Native tab & window creation (`switchTo().newWindow()`) |

The one line to remember for interviews: Selenium 4 dropped the legacy JSON Wire Protocol and speaks the W3C WebDriver standard end to end, which is what made features like BiDi and Relative Locators possible in the first place.

**[⬆ back to top](#contents)**

---

## 3. Key code changes at a glance

If you only skim one section before an interview, make it this one.

**Driver setup**

```java
// Selenium 3
System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
WebDriver driver = new ChromeDriver();
```

```java
// Selenium 4 — Selenium Manager resolves the driver binary automatically
WebDriver driver = new ChromeDriver();
```

**Timeouts and waits**

```java
// Selenium 3
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
new WebDriverWait(driver, 20);
```

```java
// Selenium 4
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
new WebDriverWait(driver, Duration.ofSeconds(20));
```

> **Why it matters:** interviewers frequently ask "what breaks if I upgrade a Selenium 3 project to 4?" — the answer is almost always these two API surfaces: driver setup and timeout/wait constructors.

**[⬆ back to top](#contents)**

---

## 4. Selenium 4 architecture

What actually happens between your test method and the browser window.

```text
Test Script (Java)
        │
        ▼
Selenium WebDriver (Java API)
        │
        │  W3C WebDriver protocol (HTTP + JSON)
        ▼
Selenium Manager  →  Browser Driver (chromedriver, geckodriver…)
        │
        ▼
   Browser (Chrome / Firefox / Edge / Safari)
```

Selenium Manager resolves the correct driver — and in supported cases the browser itself — before the driver process even starts, so most projects no longer ship driver binaries at all.

**[⬆ back to top](#contents)**

---

## 5. WebDriver class hierarchy

Why you always code against the `WebDriver` interface, never a concrete driver class.

```text
             SearchContext
                   ▲
                   │ extends
                WebDriver
                   ▲
                   │ implements
             RemoteWebDriver
             ▲       ▲       ▲
             │       │       │
     ChromeDriver FirefoxDriver EdgeDriver
```

```java
WebDriver driver = new ChromeDriver();
```

Reasons to reference the interface rather than the implementation:

- **Abstraction** — test code doesn't know or care which browser is underneath.
- **Browser independence** — swap `ChromeDriver` for `FirefoxDriver` with a one-line change.
- **Polymorphism** — a `DriverFactory` can return any driver type through one return type.
- **Common API** — every driver exposes the same navigation, element and window methods.

**[⬆ back to top](#contents)**

---

## 6. Maven dependency

Pin the exact 4.49.0 artifact so the whole team resolves the same driver behaviour.

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.49.0</version>
</dependency>
```

> **Tip:** if you also use TestNG or JUnit 5, keep those versions current too — very old test-runner versions occasionally conflict with Selenium 4.49's transitive dependencies (Jetty, Guava).

**[⬆ back to top](#contents)**

---

## 7. Starting a browser

No paths, no binaries — just instantiate the driver you need.

```java
WebDriver driver = new ChromeDriver();
WebDriver driver = new FirefoxDriver();
WebDriver driver = new EdgeDriver();
```

```java
driver.get("https://www.google.com");
// ...test steps...
driver.quit();
```

**[⬆ back to top](#contents)**

---

## 8. close() vs quit()

One of the most common interview trick questions — the difference is scope.

| `close()` | `quit()` |
|---|---|
| Closes only the current window/tab | Ends the entire WebDriver session |
| Other open windows stay alive | All associated windows are closed |
| The driver process may still be running | The driver process is terminated |

```java
driver.close();  // current window only
driver.quit();   // whole session, always call this at teardown
```

> **Common mistake:** calling `close()` in an `@AfterMethod`/`@AfterEach` and never calling `quit()` leaks driver processes over a long test run. Always end a session with `quit()`.

**[⬆ back to top](#contents)**

---

## 9. Browser navigation

```java
driver.get("https://example.com");
driver.navigate().to("https://example.com");

driver.navigate().back();
driver.navigate().forward();
driver.navigate().refresh();
```

`get()` and `navigate().to()` behave identically for a first load; `navigate()` additionally exposes browser history controls.

**[⬆ back to top](#contents)**

---

## 10. Reading browser state

```java
String title  = driver.getTitle();
String url    = driver.getCurrentUrl();
String source = driver.getPageSource();
```

**[⬆ back to top](#contents)**

---

## 11. Window management

```java
driver.manage().window().maximize();
driver.manage().window().minimize();
driver.manage().window().fullscreen();

driver.manage().window().setSize(new Dimension(1200, 800));
driver.manage().window().setPosition(new Point(100, 100));
```

**[⬆ back to top](#contents)**

---

## 12. Browser options

`Options` classes replace `DesiredCapabilities` for configuring how the browser launches.

```java
ChromeOptions options = new ChromeOptions();
options.addArguments(
    "--headless=new",
    "--disable-notifications",
    "--start-maximized"
);

WebDriver driver = new ChromeDriver(options);
```

Equivalent classes exist for every browser: `FirefoxOptions`, `EdgeOptions`, `SafariOptions`. For remote sessions, an `Options` object is mandatory — it's how the Grid Node knows which browser to launch.

**[⬆ back to top](#contents)**

---

## 13. Page load strategy

| Strategy | Behavior |
|---|---|
| `normal` | Waits for the full `load` event (default) |
| `eager` | Waits for DOMContentLoaded, skips images/CSS finishing |
| `none` | Doesn't block at all — you own all waiting |

```java
ChromeOptions options = new ChromeOptions();
options.setPageLoadStrategy(PageLoadStrategy.EAGER);
```

**[⬆ back to top](#contents)**

---

## 14. Web elements

A web element is any node Selenium can locate and interact with in the DOM — inputs, buttons, links, selects, text areas, and generic containers.

```html
<input id="username" name="username" type="text">
```

**[⬆ back to top](#contents)**

---

## 15. Finding elements

**`findElement()`**

```java
WebElement el = driver.findElement(By.id("username"));
```

Returns the first match, or throws `NoSuchElementException`.

**`findElements()`**

```java
List<WebElement> links = driver.findElements(By.tagName("a"));
```

Returns a `List<WebElement>`, or an empty list if nothing matches — never an exception.

**[⬆ back to top](#contents)**

---

## 16. Locator strategies

Eight traditional strategies, all still exposed through the `By` class.

| Locator | Syntax |
|---|---|
| ID | `By.id("username")` |
| Name | `By.name("username")` |
| Class | `By.className("login")` |
| Tag | `By.tagName("input")` |
| Link text | `By.linkText("Login")` |
| Partial link text | `By.partialLinkText("Log")` |
| CSS | `By.cssSelector("#username")` |
| XPath | `By.xpath("//input[@id='username']")` |

**[⬆ back to top](#contents)**

---

## 17. Locator best practices

Don't memorize a fixed speed ranking — the current guidance is a priority order, not a stopwatch.

```text
Stable, unique ID
       ↓
CSS Selector
       ↓
XPath (when relationship/condition logic is required)
```

```java
// Preferred when a stable ID exists
By.id("username");

// Preferred when ID is unavailable
By.cssSelector("input[name='username']");

// Use when you need parent/sibling/text logic
By.xpath("//label[text()='Username']/following-sibling::input");
```

**[⬆ back to top](#contents)**

---

## 18. CSS selectors

```java
By.cssSelector("#username");                              // ID
By.cssSelector(".login-button");                          // Class
By.cssSelector("input[name='username']");                 // Attribute
By.cssSelector("input[name='username'][type='text']");    // Multiple attributes
By.cssSelector("form input");                              // Descendant
By.cssSelector("form > input");                            // Direct child
```

**[⬆ back to top](#contents)**

---

## 19. XPath

```java
By.xpath("//input[@id='username']");
By.xpath("//input[@id='username' and @type='text']");
By.xpath("//input[@id='username' or @name='username']");
By.xpath("//button[text()='Login']");
By.xpath("//button[contains(text(),'Login')]");
By.xpath("//input[contains(@id,'user')]");
```

**XPath axes**

```text
ancestor    parent      child
descendant  following   following-sibling
preceding   preceding-sibling
```

```java
By.xpath("//label[text()='Username']/following-sibling::input");
By.xpath("//input[@id='username']/parent::*");
By.xpath("//input[@id='username']/ancestor::form");
```

**Indexing**

```java
By.xpath("(//input)[1]");          // first
By.xpath("(//input)[2]");          // second
By.xpath("(//input)[last()]");     // last
By.xpath("(//input)[last()-1]");   // second-to-last
```

> **Avoid indexes** when any stable locator is available — index-based XPath breaks the moment markup order changes.

**[⬆ back to top](#contents)**

---

## 20. Relative locators

A genuine Selenium 4 feature — locate elements by their position relative to another element.

Supported: `above()`, `below()`, `toLeftOf()`, `toRightOf()`, `near()`

```java
WebElement password = driver.findElement(By.id("password"));

WebElement username = driver.findElement(
    RelativeLocator.with(By.tagName("input")).above(password)
);
```

Useful when a form has no reliable IDs but a predictable visual layout — for example locating the input directly above a labeled "Password" field.

**[⬆ back to top](#contents)**

---

## 21. WebElement methods

Four core interaction commands, plus the read-only state methods you'll use in assertions.

**Interaction commands**

```java
element.click();
element.sendKeys("admin");
element.clear();
element.submit();   // rarely needed — prefer clicking the actual submit button
```

**Reading state**

```java
String text  = element.getText();
String value = element.getAttribute("value");     // HTML attribute
String prop  = element.getDomProperty("value");     // live DOM property

boolean displayed = element.isDisplayed();
boolean enabled   = element.isEnabled();
boolean selected  = element.isSelected();
```

> **`getAttribute()` vs `getDomProperty()`:** `getAttribute` reads the HTML source attribute; `getDomProperty` reads the current live DOM property — the two can diverge once JavaScript has mutated the element, e.g. an input's typed value.

**[⬆ back to top](#contents)**

---

## 22. Dropdowns — Select

Only for native `<select>` elements — custom JS dropdowns need ordinary click/type interactions instead.

```java
WebElement dropdown = driver.findElement(By.id("country"));
Select select = new Select(dropdown);

select.selectByVisibleText("India");
select.selectByValue("IN");
select.selectByIndex(2);

WebElement current = select.getFirstSelectedOption();
List<WebElement> all = select.getOptions();

if (select.isMultiple()) {
    select.deselectAll();
}
```

**[⬆ back to top](#contents)**

---

## 23. Checkboxes & radio buttons

```java
WebElement checkbox = driver.findElement(By.id("terms"));
if (!checkbox.isSelected()) {
    checkbox.click();
}

WebElement radio = driver.findElement(By.id("male"));
if (!radio.isSelected()) {
    radio.click();
}
```

Always check `isSelected()` before clicking — clicking an already-selected checkbox toggles it off.

**[⬆ back to top](#contents)**

---

## 24. Keyboard & mouse — the Actions API

```java
Actions actions = new Actions(driver);

actions.moveToElement(element).perform();                 // hover
actions.contextClick(element).perform();                  // right click
actions.doubleClick(element).perform();                    // double click
actions.clickAndHold(element).perform();                   // click & hold
actions.dragAndDrop(source, target).perform();              // drag & drop

actions.keyDown(Keys.CONTROL)
       .sendKeys("a")
       .keyUp(Keys.CONTROL)
       .perform();
```

**Bonus: drag by offset**

```java
actions.dragAndDropBy(source, 120, 0).perform(); // move 120px right
```

**[⬆ back to top](#contents)**

---

## 25. JavaScript execution & scrolling

```java
JavascriptExecutor js = (JavascriptExecutor) driver;

js.executeScript("return document.title;");
js.executeScript("arguments[0].click();", element);
js.executeScript("arguments[0].value='Anudeep';", element);

js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
js.executeScript("arguments[0].scrollIntoView(true);", element);
```

> **Best practice:** use native WebDriver interactions first (`click()`, `sendKeys()`). Reach for JavaScript only when a normal interaction genuinely can't reach the element — e.g. it's covered by a sticky header, or the app blocks synthetic events.

**[⬆ back to top](#contents)**

---

## 26. Alerts

Native browser dialogs — alert, confirm, and prompt.

```java
Alert alert = driver.switchTo().alert();

String text = alert.getText();
alert.accept();
alert.dismiss();

// Prompt dialogs accept typed input before confirming
alert.sendKeys("Hello");
alert.accept();
```

**[⬆ back to top](#contents)**

---

## 27. Frames & iframes

```java
driver.switchTo().frame(driver.findElement(By.id("paymentFrame")));
driver.switchTo().frame("paymentFrame");   // by name or id
driver.switchTo().frame(0);                 // by index

driver.switchTo().defaultContent();          // back to the main document
driver.switchTo().parentFrame();             // up one level only
```

**[⬆ back to top](#contents)**

---

## 28. Windows & tabs

Selenium tracks every window/tab by an opaque **window handle**.

```java
String parent = driver.getWindowHandle();
Set<String> handles = driver.getWindowHandles();

for (String handle : handles) {
    if (!handle.equals(parent)) {
        driver.switchTo().window(handle);
        break;
    }
}
```

**Opening a new tab or window (Selenium 4)**

```java
driver.switchTo().newWindow(WindowType.TAB);
driver.switchTo().newWindow(WindowType.WINDOW);
driver.get("https://example.com");
```

This replaces the old Selenium 3 workaround of executing JavaScript's `window.open()` and hoping the handle order was predictable.

**[⬆ back to top](#contents)**

---

## 29. Cookies

```java
driver.manage().addCookie(new Cookie("username", "anudeep"));

Cookie cookie = driver.manage().getCookieNamed("username");
Set<Cookie> all = driver.manage().getCookies();

driver.manage().deleteCookieNamed("username");
driver.manage().deleteAllCookies();
```

> The browser must already be on the target domain before you add a cookie for it — navigate first, *then* add the cookie.

**[⬆ back to top](#contents)**

---

## 30. File upload

```java
WebElement upload = driver.findElement(By.id("fileUpload"));
upload.sendKeys("/Users/test/Documents/file.pdf");
```

This works for standard `<input type="file">` elements — `sendKeys()` writes the path directly, bypassing the OS file picker entirely. Do not attempt to automate the native OS dialog with WebDriver clicks; it isn't part of the DOM and Selenium can't see it.

**[⬆ back to top](#contents)**

---

## 31. Screenshots

**Full page**

```java
TakesScreenshot screenshot = (TakesScreenshot) driver;
File source = screenshot.getScreenshotAs(OutputType.FILE);

Files.copy(
    source.toPath(),
    Path.of("screenshot.png"),
    StandardCopyOption.REPLACE_EXISTING
);
```

**Single element**

```java
WebElement element = driver.findElement(By.id("login"));
File file = element.getScreenshotAs(OutputType.FILE);
```

**[⬆ back to top](#contents)**

---

## 32. Shadow DOM

Selenium 4 exposes open shadow roots natively via `getShadowRoot()`.

```text
DOM
 └── Shadow Host
       └── Shadow Root
             └── Shadow Element
```

```java
WebElement host = driver.findElement(By.cssSelector("#shadow_host"));
SearchContext shadowRoot = host.getShadowRoot();

WebElement content = shadowRoot.findElement(By.cssSelector("#shadow_content"));
```

> This only works for **open** shadow roots. A `closed` shadow root is intentionally inaccessible to any outside script, Selenium included.

**[⬆ back to top](#contents)**

---

## 33. Why synchronization matters

The single biggest source of flaky Selenium tests.

```text
Test executes
      ↓
Application still loading
      ↓
Element not ready yet
      ↓
Test fails — even though the app is fine
```

This race condition — the test running faster than the app renders — is the root cause of most flaky browser tests, and the reason waits exist at all.

**[⬆ back to top](#contents)**

---

## 34. Implicit, explicit & fluent waits

> **Avoid `Thread.sleep()`** for synchronization — it's a fixed delay that either wastes time (app was ready sooner) or still fails (app took longer). Use a condition-based wait instead.

**Implicit wait — applies globally to every findElement call**

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
// default is 0 seconds
```

**Explicit wait — waits for one specific condition**

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
wait.until(ExpectedConditions.titleContains("Dashboard"));
wait.until(ExpectedConditions.urlContains("/dashboard"));
```

**Explicit wait with a lambda**

```java
wait.until(d -> d.findElement(By.id("username")).isDisplayed());
```

**Fluent wait — custom polling interval and ignored exceptions**

```java
Wait<WebDriver> wait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(30))
        .pollingEvery(Duration.ofSeconds(2))
        .ignoring(NoSuchElementException.class);

WebElement element = wait.until(d -> d.findElement(By.id("username")));
```

| Wait | Purpose |
|---|---|
| `Thread.sleep()` | Fixed delay — avoid using this for synchronization |
| Implicit | Global timeout applied to every element lookup |
| Explicit | Waits for one specific, named condition |
| Fluent | Explicit wait with custom polling & exception handling |

> **Never mix implicit and explicit waits** in the same test. Selenium's own documentation warns this produces unpredictable, hard-to-debug timeout behaviour — pick one strategy (usually explicit) per project.

**[⬆ back to top](#contents)**

---

## 35. Page load & script timeout

```java
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
```

`pageLoadTimeout` bounds how long a navigation can take before Selenium throws; `scriptTimeout` bounds asynchronous JavaScript executed via `executeAsyncScript()`.

**[⬆ back to top](#contents)**

---

## 36. WebDriver BiDi

Bidirectional WebDriver — the protocol upgrade that unlocks real-time browser events.

```text
Classic WebDriver                  WebDriver BiDi
Client                             Test Client ⇄ WebSocket ⇄ Browser
  ↓ request                                       ↕
Browser                                       live events
  ↓ response
Client
```

BiDi runs over a WebSocket connection and streams browser-side events — network activity, console messages, JavaScript errors — back to your test in real time, instead of requiring you to poll for them.

```java
ChromeOptions options = new ChromeOptions();
options.enableBiDi();

WebDriver driver = new ChromeDriver(options);

// Equivalent low-level form
options.setCapability("webSocketUrl", true);
```

**[⬆ back to top](#contents)**

---

## 37. RemoteWebDriver & Selenium Grid 4

Running a browser on a different machine — one test at a time, or hundreds in parallel.

**RemoteWebDriver**

```java
ChromeOptions options = new ChromeOptions();

WebDriver driver = new RemoteWebDriver(
    new URL("http://localhost:4444"),
    options
);
```

Selenium Grid exists to route WebDriver sessions to remote browser instances and run them in parallel, across browsers and platforms.

**Grid 4 architecture**

```text
                      Router
                        │
              new session request
                        ▼
                New Session Queue
                        │
                        ▼
                   Distributor
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
     Node 1           Node 2          Node 3
     Chrome            Firefox          Edge

        Session Map  (session id → node)
        Event Bus    (async messaging between components)
```

| Component | Role |
|---|---|
| Router | Entry point for every Grid request |
| New Session Queue | Holds requests waiting for a free slot |
| Distributor | Assigns a request to a suitable Node |
| Node | Actually runs the browser session |
| Session Map | Maps session ID → Node |
| Event Bus | Asynchronous messaging between all components |

**Start a standalone Grid**

```bash
java -jar selenium-server-4.49.0.jar standalone
# Default URL: http://localhost:4444
```

```text
                 Hub
                  │
       ┌──────────┼──────────┐
       ▼          ▼          ▼
    Node 1      Node 2     Node 3
    Chrome      Firefox      Edge
```

In a Hub/Node deployment, the Hub hosts the Grid coordination components (Router, Queue, Distributor) while each Node contributes browser execution capacity.

> **Grid vs. cloud browser farms** (BrowserStack, Sauce Labs, LambdaTest): with self-hosted Grid you own the machines, browsers, network and scaling; a cloud platform manages that infrastructure for you in exchange for a subscription — the WebDriver code you write is nearly identical either way, just pointed at a different remote URL.

**[⬆ back to top](#contents)**

---

## 38. Page Object Model

Keep locators and page behaviour inside a class per page — never scattered across test methods.

```java
public class LoginPage {

    private final WebDriver driver;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.id("login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String value) {
        driver.findElement(username).sendKeys(value);
    }

    public void enterPassword(String value) {
        driver.findElement(password).sendKeys(value);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
```

**Using it from a test**

```java
LoginPage loginPage = new LoginPage(driver);

loginPage.enterUsername("admin");
loginPage.enterPassword("password");
loginPage.clickLogin();
```

**[⬆ back to top](#contents)**

---

## 39. Recommended framework structure

```text
src/test/java
│
├── base        BaseTest.java
├── pages       LoginPage.java, HomePage.java
├── tests       LoginTest.java, HomeTest.java
├── utils       WaitUtils.java, ScreenshotUtils.java, DriverUtils.java
├── factory     DriverFactory.java
├── config      ConfigReader.java
└── listeners   TestListener.java
```

**The flow inside a single test**

```text
Create Driver → Navigate → Locate Element → Wait
      → Interact → Validate → Screenshot/Report → Quit Driver
```

**[⬆ back to top](#contents)**

---

## 40. Interview cheat sheet

A fast pre-interview scan — every term you should recognize on sight.

**Core:** WebDriver · WebElement · SearchContext · RemoteWebDriver

**Locators:** id · name · className · tagName · linkText · partialLinkText · cssSelector · xpath · Relative Locator

**Browser setup:** ChromeDriver · FirefoxDriver · EdgeDriver · ChromeOptions · FirefoxOptions · EdgeOptions · Selenium Manager

**Navigation:** get() · navigate().to() · back() · forward() · refresh()

**Window:** maximize() · minimize() · fullscreen() · setSize() · setPosition()

**Element:** click() · sendKeys() · clear() · submit() · getText() · getAttribute() · getDomProperty() · isDisplayed() · isEnabled() · isSelected()

**Synchronization:** Implicit Wait · Explicit Wait · Fluent Wait · ExpectedConditions · Duration

**User interactions:** Actions · Keyboard · Mouse · Drag & Drop · Hover · Right Click · Double Click

**Browser contexts:** Alerts · Frames · Windows · Tabs · Cookies · Shadow DOM

**Advanced:** JavascriptExecutor · Screenshots · File Upload · Page Load Strategy

**Distributed:** RemoteWebDriver · WebDriver BiDi · Selenium Grid 4

**Framework:** Page Object Model · DriverFactory · ConfigReader

**[⬆ back to top](#contents)**

---

## 41. Selenium 3 → 4: what to memorize

If an interviewer asks "what changed in Selenium 4," this list is the answer, in order of importance.

1. JSON Wire Protocol → **W3C WebDriver standard**
2. Manual driver management → **Selenium Manager**
3. `TimeUnit` → **`Duration`**
4. `DesiredCapabilities` → **Browser Options classes**
5. Selenium Grid 3 (Hub-only) → **Selenium Grid 4** (Router/Distributor/Node)
6. Traditional locators only → **Traditional + Relative Locators**
7. Request/response only → **+ WebDriver BiDi**
8. Limited window/tab handling → **Native `switchTo().newWindow()`**

**[⬆ back to top](#contents)**

---

*Rebuilt for Selenium 4.49.0 (released September 9, 2026). Legacy Selenium 3 code is shown only where it clarifies a migration — the framework guidance, locator strategy and wait discipline throughout reflect current Selenium documentation.*

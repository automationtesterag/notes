## What is Playwright?

Playwright is an open-source browser automation framework developed by Microsoft for end-to-end testing of web applications.

It supports:

* ✅ Chromium (Chrome, Edge)
* ✅ Firefox
* ✅ WebKit (Safari)
* ✅ Windows, macOS, and Linux
* ✅ JavaScript, TypeScript, Python, Java, and C#

---

## Why use Playwright?

* Fast and reliable
* Auto-waits for elements (less flaky tests)
* Built-in parallel execution
* Network interception and API testing
* Multiple browser support
* Mobile device emulation
* Screenshots and video recording
* Trace Viewer for debugging


## Playwright vs Selenium

| Feature            | Playwright | Selenium                    |
| ------------------ | ---------- | --------------------------- |
| Speed              | ⭐⭐⭐⭐⭐      | ⭐⭐⭐                         |
| Auto Waiting       | ✅ Built-in | ❌ Manual waits often needed |
| Multiple Browsers  | ✅          | ✅                           |
| Parallel Execution | ✅ Built-in | Requires setup              |
| Network Mocking    | ✅          | Limited                     |
| Mobile Emulation   | ✅          | Limited                     |
| Trace Viewer       | ✅          | No built-in                 |

---


## Install Playwright

command to install `npm init playwright`

```
anudeepgubba@Anudeeps-MacBook-Air playwright % npm init playwright
Getting started with writing end-to-end tests with Playwright:
Initializing project in '.'
✔ Do you want to use TypeScript or JavaScript? · JavaScript
✔ Where to put your end-to-end tests? · tests
✔ Add a GitHub Actions workflow? (y/N) · true
✔ Install Playwright browsers (can be done manually via 'npx playwright install')? (Y/n) · true
```

## 🎭 Playwright Test Fixtures: `page` and `browserContext`

Playwright’s **test runner** comes with powerful **built-in fixtures** that handle a lot of browser setup for you. Two of the most important ones are:

### 🔹 `page` Fixture

### 🔹 `browserContext` Fixture

Understanding how and when to use each can help you write more **reliable**, **scalable**, and **clean** automated tests.

---

### 🔹 What is the `page` Fixture?

The `page` fixture provides a **single browser tab (or window)** that is automatically created for you in each test.

Think of it as a **ready-to-use page instance** inside an isolated browser environment.

#### ✨ Key Points:

- Automatically managed by Playwright Test.
- No need to manually create or close it.
- Best for **simple, single-user tests**.
- Internally, each test gets its own browser context and a page, ensuring isolation.

---

### ✅ Example: Using `page` Fixture in JavaScript

```js
// page-example.spec.js
const { test, expect } = require("@playwright/test");

test("login with default page fixture", async ({ page }) => {
  // Navigates to the login page
  await page.goto("https://example.com/login");

  // Simulates user login
  await page.fill("#username", "user1");
  await page.fill("#password", "password1");
  await page.click('button[type="submit"]');

  // Verifies successful login
  await expect(page).toHaveURL("https://example.com/dashboard");
  await expect(page.locator("h1")).toHaveText("Welcome, user1!");
});
```

> This is the **recommended** approach for most basic functional UI tests.

---

### 🔹 What is `browserContext`?

A **`browserContext`** is a powerful feature that acts like an **incognito browser profile**. Each context:

- Has a completely isolated environment.
- Does **not share** cookies, localStorage, sessionStorage, or cache with others.
- Lets you simulate **multiple users** in a single test file or run tests in **parallel** without interference.

#### ✨ Key Points:

- Use when you want **manual control** over session isolation.
- Allows testing multiple users or complex scenarios (e.g., admin vs regular user).
- You must manually create and close the context and its pages.

---

### ✅ Example: Using `browserContext` in JavaScript

```js
// context-example.spec.js
const { test, expect } = require("@playwright/test");

test("login using manual browser context", async ({ browser }) => {
  // Create a new isolated session (like a new private browser window)
  const context = await browser.newContext();

  // Create a new page (tab) in that context
  const page = await context.newPage();

  // Navigate and perform login
  await page.goto("https://example.com/login");
  await page.fill("#username", "user2");
  await page.fill("#password", "password2");
  await page.click('button[type="submit"]');

  // Assert login success
  await expect(page).toHaveURL("https://example.com/dashboard");
  await expect(page.locator("h1")).toHaveText("Welcome, user2!");

  // Clean up
  await context.close();
});
```

> Ideal for scenarios requiring multiple isolated sessions or when testing authentication flows.

---

## 🧠 When to Use What?

| Use Case                                   | Use `page` Fixture | Use `browserContext` |
| ------------------------------------------ | ------------------ | -------------------- |
| Simple UI test                             | ✅ Yes             | ❌ Not needed        |
| Needs session or cookie isolation          | ❌ No              | ✅ Yes               |
| Testing multiple user accounts             | ❌ No              | ✅ Yes               |
| Simulating concurrent sessions in one test | ❌ No              | ✅ Yes               |
| Fast setup with automatic context/page     | ✅ Yes             | ❌ No (manual setup) |

---

## 🧪 Real-world Analogy

| Concept          | Description                               |
| ---------------- | ----------------------------------------- |
| `browser`        | The entire browser (e.g., Chrome/Firefox) |
| `browserContext` | A new incognito/private session           |
| `page`           | A new tab or window inside a context      |

Imagine:

- The **browser** is your Chrome.
- Each **browserContext** is like opening a **new incognito window**.
- Each **page** is a **tab** inside that incognito window.

---

## 🚀 Summary

- Use **`page`** for fast, simple test cases where session management isn't a concern.
- Use **`browserContext`** when you need multiple isolated sessions, simulate different users, or test things like authentication and role-based access.

Both are **built-in fixtures**, so you don’t need to configure anything special to use them — just import from `@playwright/test`.

---

## PlayWright Config guide

[Guide](../playwright/playwright-config-guide.md)

## command to run tests

```
  npx playwright test
    Runs the end-to-end tests.

  npx playwright test --ui
    Starts the interactive UI mode.

  npx playwright test --project=chromium
    Runs the tests only on Desktop Chrome.

  npx playwright test example
    Runs the tests in a specific file.

  npx playwright test --debug
    Runs the tests in debug mode.

  npx playwright codegen
    Auto generate tests with Codegen.

We suggest that you begin by typing:

    npx playwright test

Visit https://playwright.dev/docs/intro for more information. ✨
```

## 1. Locators Supported by Playwright

Playwright supports a variety of locators to identify elements on a web page. These include:

- **CSS Selectors**: Target elements using CSS syntax (e.g., `page.locator('button.submit')`)
- **XPath**: Use XPath expressions (e.g., `page.locator('//button[@type="submit"]')`)
- **Text-based**: Locate elements by their visible text (e.g., `page.locator('text=Submit')` or `page.locator('button:has-text("Submit")')`)
- **Role-based**: Use ARIA roles (e.g., `page.locator('role=button[name="Submit"]')`)
- **Data-testid**: Common for test automation (e.g., `page.locator('[data-testid="submit-button"]')`)
- **ID, Name, Class**: Standard HTML attributes (e.g., `page.locator('#submit')`, `page.locator('[name="username"]')`)
- **Custom Locators**: Combine locators using chaining (e.g., `page.locator('div.container').locator('button')`)
- **Nth Match**: Select specific elements in a list (e.g., `page.locator('button >> nth=1')`)

### Example:

```javascript
const button = page.locator("button.submit"); // CSS
const input = page.locator('//input[@id="username"]'); // XPath
const textButton = page.locator("text=Click Me"); // Text-based
```

## 2. How to Type into Elements on a Page

Here is a complete and **unified guide** to **all possible ways of entering text and using key combinations in Playwright**, including both input methods and keyboard interactions. This covers every combination you may need for form filling, testing keyboard shortcuts, or simulating user input behavior.

---

# 🎯 Complete Playwright Text Input & Keyboard Interaction Cheat Sheet

---

## 🖋️ 1. **Text Entry Methods**

### ✅ `page.fill(selector, text)`

- Clears input and fills text instantly.

```ts
await page.fill("#username", "myUser");
```

### ✅ `locator.fill(text)`

- Same as above, using Locator API (recommended for reliability).

```ts
await page.locator("#username").fill("myUser");
```

---

### ✅ `page.type(selector, text[, options])`

- Types one character at a time.

```ts
await page.type("#username", "myUser", { delay: 100 });
```

### ✅ `locator.type(text[, options])`

- Locator API version of `type`.

```ts
await page.locator("#username").type("myUser", { delay: 50 });
```

---

### ✅ `page.keyboard.type(text[, options])`

- Types via the keyboard API (focused element required).

```ts
await page.click("#username");
await page.keyboard.type("myUser", { delay: 50 });
```

### ✅ `page.keyboard.insertText(text)`

- Directly inserts text like paste, not character-by-character.

```ts
await page.click("#username");
await page.keyboard.insertText("myUser");
```

---

### ✅ Manual Value Assignment via JS

- Use when framework (React, Vue) blocks `fill()` or `type()`.

```ts
await page.evaluate(() => {
  const input = document.querySelector("#username");
  input.value = "myUser";
  input.dispatchEvent(new Event("input", { bubbles: true }));
});
```

---

### ✅ Simulate Paste via Clipboard

```ts
await page.evaluate(() => navigator.clipboard.writeText("myUser"));
await page.click("#username");
await page.keyboard.press("Control+V"); // or 'Meta+V' on macOS
```

---

## 🎹 2. **Keyboard Key Combinations**

### 🔑 **Modifier Keys**

```ts
await page.keyboard.down("Shift");
await page.keyboard.type("a"); // Types 'A'
await page.keyboard.up("Shift");
```

---

### 🔁 **Shortcut Key Combos**

| Action     | Shortcut                |
| ---------- | ----------------------- |
| Select All | `Control+A` or `Meta+A` |
| Copy       | `Control+C` or `Meta+C` |
| Paste      | `Control+V` or `Meta+V` |
| Cut        | `Control+X` or `Meta+X` |
| Undo       | `Control+Z` or `Meta+Z` |
| Redo       | `Control+Shift+Z`       |

```ts
await page.keyboard.press("Control+A");
await page.keyboard.press("Backspace");
await page.keyboard.type("new text");
```

---

### 🧭 **Navigation Keys**

```ts
await page.keyboard.press("ArrowLeft");
await page.keyboard.press("ArrowRight");
await page.keyboard.press("Home");
await page.keyboard.press("End");
```

---

### 🔧 **Function Keys**

```ts
await page.keyboard.press("F5"); // Refresh
await page.keyboard.press("F12"); // DevTools (browser-dependent)
```

---

## 📦 3. **Real-World Example: Simulated Login Flow**

```ts
await page.goto("https://example.com/login");

// Fill using fill()
await page.fill("#username", "testUser");

// Tab to password field and use keyboard typing
await page.keyboard.press("Tab");
await page.keyboard.type("Pa$$w0rd");

// Simulate pressing Enter to submit
await page.keyboard.press("Enter");
```

---

### Short Notes: `pressSequentially()` in Playwright

* **Purpose:** Types text one character at a time, like a real user.

* **Syntax:**

  ```typescript
  await page.locator(selector).pressSequentially("text");
  ```

* **With Delay:**

  ```typescript
  await page.locator(selector).pressSequentially("text", { delay: 150 });
  ```

* **Example Flow:**

  ```
  "ind" → i → (150 ms) → n → (150 ms) → d
  ```

* **Why use `delay`?**

  * Gives the application time to process each keystroke.
  * Helps with slow servers or network latency.
  * Improves reliability for autocomplete and search fields.

* **Common Use Cases:**

  * Autocomplete/typeahead inputs
  * Search boxes
  * Inputs with debouncing
  * Applications that load suggestions dynamically

* **Best Practice:**

  * Prefer waiting for the expected UI or API response (`expect()`, `waitForResponse()`) over relying on delays.
  * Use `delay` only when necessary to simulate realistic typing or improve stability.

> **Interview Tip:** `pressSequentially()` is useful when an application reacts to each keystroke, unlike `fill()`, which sets the entire value instantly.


## 💡 Tips

- Always `click()` or `focus()` before using `keyboard` if the input isn't already active.
- Use `insertText` for large text pastes or avoiding typing delays.
- Prefer `locator.fill()` over `page.fill()` for better stability in modern frameworks.

---

## 3. Extracting Text from Browser and Inserting Valid Expect Assertions

To extract text and use it in assertions, Playwright provides methods like `textContent()`, `innerText()`, or `allTextContents()` for multiple elements. Combine with `expect` for assertions.

### Extracting Text:

```javascript
// Single element
const text = await page.locator("#header").textContent(); // Returns string or null
const innerText = await page.locator("#header").innerText(); // Visible text only

// Multiple elements
const texts = await page.locator("li.item").allTextContents(); // Returns array of strings
```

### Using Expect Assertions:

Playwright's `expect` API supports various matchers like `toBe`, `toContain`, `toMatch`, etc.

```javascript
const headerText = await page.locator("#header").textContent();
await expect(headerText).toBe("Welcome"); // Exact match
await expect(headerText).toContain("Welcome"); // Partial match
await expect(headerText).toMatch(/Welcome\sUser/); // Regex match

// Multiple elements
const items = await page.locator("li.item").allTextContents();
await expect(items).toEqual(["Item 1", "Item 2", "Item 3"]);
```

### Short Notes: `inputValue()` in Playwright

* **Purpose:** Gets the current value of an `<input>`, `<textarea>`, or `<select>` element.
* **Syntax:**

  ```typescript
  const value = await page.locator(selector).inputValue();
  ```
* **Returns:** A `string` containing the current value.

### Example

```typescript
await page.locator('#username').fill('Anudeep');

const value = await page.locator('#username').inputValue();

console.log(value); // Anudeep
```

### Common Use Cases

* Verify entered text in an input field.
* Read values before updating them.
* Validate form data.

### Example Assertion

```typescript
await expect(page.locator('#username')).toHaveValue('Anudeep');
```

or

```typescript
const value = await page.locator('#username').inputValue();
expect(value).toBe('Anudeep');
```

### `inputValue()` vs `textContent()`

| Method          | Used For                            | Returns                         |
| --------------- | ----------------------------------- | ------------------------------- |
| `inputValue()`  | `<input>`, `<textarea>`, `<select>` | Value entered in the field      |
| `textContent()` | `<div>`, `<span>`, `<p>`, etc.      | Visible text inside the element |

### Interview Tip

* Use **`inputValue()`** to read the value of form fields.
* Use **`toHaveValue()`** for assertions, as it automatically waits for the expected value, making tests more reliable.


### Tips:

- Use `textContent()` for raw text, `innerText()` for visible text
- Handle null values with `await expect(locator).toHaveText('value')` for safer assertions
- Use `toHaveText()` for locators directly: `await expect(page.locator('#header')).toHaveText('Welcome')`

## 4. Working with Locators that Extract Multiple Web Elements

When a locator matches multiple elements, Playwright returns a Locator object that can handle all matches. Use methods like `all()`, `nth()`, `first()`, `last()`, or `allTextContents()`.

### Handling Multiple Elements:

```javascript
// Get all matching elements
const items = page.locator("li.item");
const count = await items.count(); // Number of matches
console.log(`Found ${count} items`);

// Iterate over elements
for (let i = 0; i < count; i++) {
  const text = await items.nth(i).textContent();
  console.log(`Item ${i + 1}: ${text}`);
}

// Get all texts at once
const texts = await items.allTextContents();
console.log(texts); // ['Item 1', 'Item 2', ...]

// Click first or last element
await items.first().click();
await items.last().click();

// Filter specific elements
const activeItems = items.locator(".active"); // Chain locators
```

### Tips:

- Use `count()` to verify the number of elements: `await expect(items).toHaveCount(3)`
- Use `filter()` for conditional matching: `items.filter({ hasText: 'Active' })`
- Avoid hardcoding indices; prefer `first()`, `last()`, or unique attributes

## 5. Understanding Wait Mechanism for Lists of Elements

Playwright's auto-waiting ensures elements are actionable (visible, enabled) before interacting. For lists, waiting applies to the locator's resolution, but you may need additional waits for dynamic content.

### How It Works:

- Locators wait for at least one element to match the selector
- Methods like `click()`, `fill()`, etc., wait for the element to be actionable
- For lists, `all()` or `allTextContents()` waits for the DOM to stabilize but doesn't guarantee all elements are fully loaded

### Waiting for Lists:

```javascript
// Wait for at least one element
await page.locator("li.item").waitFor({ state: "visible" });

// Wait for specific number of elements
await expect(page.locator("li.item")).toHaveCount(5, { timeout: 5000 });

// Wait for dynamic list to load
await page.waitForFunction(
  () => document.querySelectorAll("li.item").length >= 5,
  { timeout: 10000 }
);
```

### Tips:

- Use `waitFor()` with `{ state: 'attached' }` for DOM presence or `{ state: 'visible' }` for visibility
- Use `waitForFunction` for custom conditions (e.g., checking list length)
- Set explicit timeouts to avoid infinite waits

## 6. Techniques to Wait Dynamically for a New Page in Service-Based Applications

Service-based applications often involve dynamic page loads (e.g., redirects, async navigation). Playwright provides several techniques to wait for new pages.

### Techniques:

#### Wait for Navigation:

Use `waitForNavigation` or `waitForURL` after triggering navigation.

```javascript
await Promise.all([
  page.waitForURL("**/dashboard", { timeout: 10000 }),
  page.locator('a[href="/dashboard"]').click(),
]);
```

#### Wait for New Page:

Use `context.waitForEvent('page')` for new tabs or popups.

```javascript
const [newPage] = await Promise.all([
  context.waitForEvent("page"),
  page.locator("button#open-tab").click(),
]);
await newPage.waitForLoadState("domcontentloaded");
```

#### Wait for Specific Element:

Wait for an element on the new page to confirm it's loaded.

```javascript
await page.locator('a[href="/login"]').click();
await page.locator("#dashboard").waitFor({ state: "visible" });
```

#### Wait for Network Idle:

Ensure all network requests are complete.

```javascript
await page.waitForLoadState("networkidle");
```

#### Polling for Dynamic Content:

Use `waitForFunction` for custom conditions in dynamic apps.

```javascript
await page.waitForFunction(
  () => document.querySelector("#dashboard") !== null,
  { polling: 500, timeout: 10000 }
);
```

### Example for Service-Based App:

```javascript
// Click a button that triggers an async redirect
const [newPage] = await Promise.all([
  context.waitForEvent("page", { timeout: 10000 }),
  page.locator("button#submit").click(),
]);
await newPage.waitForURL("**/success");
await expect(newPage.locator("#confirmation")).toBeVisible();
```

### Tips:

- Use `Promise.all` to combine navigation triggers and waits
- Prefer `waitForURL` over `waitForNavigation` for modern apps (more reliable)
- Set reasonable timeouts (e.g., 10-30 seconds) to handle network delays
- For SPAs, rely on `waitForFunction` or element-based waits, as `networkidle` may not work due to continuous requests

## Additional Notes

### Error Handling:

Always wrap interactions in try-catch blocks for robust tests.

```javascript
try {
  await page.locator("#submit").click({ timeout: 5000 });
} catch (error) {
  console.error("Click failed:", error);
}
```

### Debugging:

Use `page.pause()` or `trace: 'on'` in Playwright config to debug locator issues.

### Service-Based Apps:

These often involve APIs, so consider waiting for specific API responses using `page.waitForResponse`.

```javascript
await Promise.all([
  page.waitForResponse(
    (resp) => resp.url().includes("/api/data") && resp.status() === 200
  ),
  page.locator("button#fetch").click(),
]);
```

In **Playwright**, the `waitFor()` method is used on a **locator** to pause the test execution until the **desired condition on the element is met**. It's commonly used when you want to **wait for elements to appear, disappear, become visible, or hidden** before proceeding.

---

### ✅ Syntax:

```javascript
await locator.waitFor([options]);
```

---

### ✅ Parameters (Options):

| Option    | Type   | Description                                                                                              |
| --------- | ------ | -------------------------------------------------------------------------------------------------------- |
| `state`   | string | Describes the condition to wait for. Can be one of: `"attached"`, `"detached"`, `"visible"`, `"hidden"`. |
| `timeout` | number | Maximum time to wait in **milliseconds**. Default is 30,000 ms (30 seconds).                             |

---

### ✅ Available States:

| State      | Meaning                                                                                                      |
| ---------- | ------------------------------------------------------------------------------------------------------------ |
| `attached` | Waits until the element is **present in the DOM**.                                                           |
| `detached` | Waits until the element is **removed from the DOM**.                                                         |
| `visible`  | Waits until the element is **in the DOM** and **visible** (not `display: none`, `visibility: hidden`, etc.). |
| `hidden`   | Waits until the element is **either not in the DOM or hidden**.                                              |

---

### ✅ Examples:

#### 1. Wait for element to appear (default `attached`):

```javascript
await page.locator(".my-element").waitFor();
```

#### 2. Wait for element to be visible:

```javascript
await page.locator(".my-element").waitFor({ state: "visible" });
```

#### 3. Wait for element to be hidden:

```javascript
await page.locator(".loading-spinner").waitFor({ state: "hidden" });
```

#### 4. Wait for element to be detached:

```javascript
await page.locator(".temp-popup").waitFor({ state: "detached" });
```

#### 5. Wait with custom timeout:

```javascript
await page.locator("#status").waitFor({ state: "visible", timeout: 10000 });
```

---

### ⚠️ Important Notes:

- If the condition is **not met within the timeout**, Playwright will throw an error.
- You generally don’t need to use `waitFor()` manually if you’re already using **auto-waiting** methods like `click()`, `fill()`, `expect().toBeVisible()`, etc., because Playwright handles that automatically.

---

Thanks for the clarification! Here's a **fully consolidated, detailed Playwright guide** that **covers all dropdown `selectOption()` options**, plus **radio buttons, checkboxes, child windows/tabs, attribute validations, and assertions** — **all in one place** with **every scenario properly explained**.

---

# ✅ Full Guide: Handling UI Components in Playwright

---

## 📌 **1. Handling Static `<select>` Dropdown Options**

### ✅ HTML:

```html
<select id="country">
  <option value="IN">India</option>
  <option value="US">United States</option>
  <option value="UK">United Kingdom</option>
</select>

<select id="colors" multiple>
  <option value="red">Red</option>
  <option value="green">Green</option>
  <option value="blue">Blue</option>
</select>
```

---

### 🔹 Option A: `page.selectOption(selector, value)`

```js
// By value
await page.selectOption("#country", "US");

// By label
await page.selectOption("#country", { label: "India" });

// By index
await page.selectOption("#country", { index: 2 });

// Multiple selection by values
await page.selectOption("#colors", ["red", "green"]);

// Multiple selection by label and value
await page.selectOption("#colors", [{ label: "Blue" }, { value: "green" }]);

// Assertion
await expect(page.locator("#country")).toHaveValue("US");
```

---

### 🔹 Option B: Using `locator.selectOption()`

```js
const dropdown = page.locator("#country");

// By value
await dropdown.selectOption("IN");

// By label
await dropdown.selectOption({ label: "United States" });

// By index
await dropdown.selectOption({ index: 1 });

// Multiple select
await page.locator("#colors").selectOption([
  "red", // by value
  { label: "Blue" }, // by label
  { index: 1 }, // by index (green)
]);

await expect(dropdown).toHaveValue("IN");
```

---

## 📌 **2. Selecting Radio Buttons & Checkboxes with Assertions**

### ✅ HTML:

```html
<input type="radio" name="gender" value="male" id="male" />
<input type="radio" name="gender" value="female" id="female" />
<input type="checkbox" id="subscribe" />
```

### ✅ Playwright Code:

```js
// Radio Buttons
await page.check("#female");
await expect(page.locator("#female")).toBeChecked();
await expect(page.locator("#male")).not.toBeChecked();

// Checkboxes
await page.check("#subscribe");
await expect(page.locator("#subscribe")).toBeChecked();

await page.uncheck("#subscribe");
await expect(page.locator("#subscribe")).not.toBeChecked();
```

---

## 📌 **3. Async/Await with Assertions & Attribute Validations**

### ✅ HTML:

```html
<input id="email" placeholder="Enter your email" />
<button id="submit" disabled>Submit</button>
```

### ✅ Playwright Code:

```js
const emailInput = page.locator("#email");
const submitBtn = page.locator("#submit");

// Attribute validation
await expect(emailInput).toHaveAttribute("placeholder", "Enter your email");

// Disabled button
await expect(submitBtn).toBeDisabled();
await expect(submitBtn).toHaveAttribute("disabled", "");
```

---

## 📌 **4. Handling Child Windows & Tabs**

### ✅ HTML:

```html
<a id="newTab" href="https://example.com" target="_blank">Open New Tab</a>
```

### ✅ Playwright Code:

```js
// Wait for new tab
const [newPage] = await Promise.all([
  context.waitForEvent("page"),
  page.click("#newTab"),
]);

// Wait for the new page to load
await newPage.waitForLoadState("domcontentloaded");

// Assertions on new page
await expect(newPage).toHaveURL("https://example.com");
await expect(newPage).toHaveTitle(/Example/);

// Interact if needed
// await newPage.locator('#someElement').click();

// Close the tab
await newPage.close();
```

---

## 📌 **Bonus: Summary Table**

| Action                 | Method / Example                                      |
| ---------------------- | ----------------------------------------------------- |
| Select by value        | `selectOption('IN')`                                  |
| Select by label        | `selectOption({ label: 'India' })`                    |
| Select by index        | `selectOption({ index: 1 })`                          |
| Multi-select (mixed)   | `selectOption([{ value: 'red' }, { label: 'Blue' }])` |
| Check radio            | `check('#male')` + `toBeChecked()`                    |
| Check/uncheck checkbox | `check()` / `uncheck()` + `toBeChecked()`             |
| Validate attribute     | `toHaveAttribute('placeholder', 'text')`              |
| Check disabled element | `toBeDisabled()`                                      |
| Handle new tab/window  | `context.waitForEvent('page')`                        |

---

Here’s a more **detailed explanation** of the Playwright tools and features you mentioned, excluding the "Stay Updated in the Testing World" section:

---

### **22. What is Playwright Inspector? And How to Debug a Playwright Script**

**🔍 Playwright Inspector** is a built-in debugging tool that allows you to pause and step through your Playwright test code interactively. It's incredibly useful for identifying and fixing test issues in real-time.

#### 💡 Features:

- Visual highlighting of elements your script interacts with.
- Controls for stepping over, stepping into, and resuming execution.
- Ability to inspect the page structure.
- Live console to run custom JavaScript commands.

#### 🧪 How to Use Playwright Inspector:

1. **Enable Inspector via Environment Variable**
   On Linux/macOS:

   ```bash
   PWDEBUG=1 npx playwright test
   ```

   On Windows (CMD):

   ```cmd
   set PWDEBUG=1 && npx playwright test
   ```

2. **Inspector UI Opens:**

   - Test execution pauses automatically before the first step.
   - You can **step through each command**, and **see what’s happening in the browser**.
   - Elements are **highlighted** in the browser so you know exactly what’s being selected.

3. **Common Debug Actions:**

   - **Step**: Run the next step in the script.
   - **Resume**: Continue running until the next pause or end.
   - **Pick Selector**: Interactively select a new element on the page.
   - **Evaluate Expression**: Run code directly in the browser's context.

#### 🧰 Tips:

- Combine with `.pause()` to pause manually at any point:

  ```js
  await page.pause();
  ```

- Use `debug()` from Playwright Test:

  ```js
  import { test, expect } from "@playwright/test";

  test("example test", async ({ page }) => {
    await page.goto("https://example.com");
    await page.pause(); // Opens the inspector here
    await page.click("text=More info");
  });
  ```

---

### **23. Codegen Tool to Record & Playback with Generated Automation Script**

**🎥 Playwright Codegen** is a tool that records your actions on a website and instantly generates equivalent automation code. This is especially helpful for beginners or rapidly building scripts.

#### ⚙️ Command to Start:

```bash
npx playwright codegen https://example.com
```

This opens:

- A **browser window** where you interact with the page.
- A **side panel** showing Playwright code generated in real-time.

#### ✅ Example Output:

After interacting with the page (click, input, navigate), you might get:

```js
await page.goto("https://example.com");
await page.click("text=Sign in");
await page.fill("#username", "your_username");
await page.fill("#password", "your_password");
await page.click("text=Submit");
```

#### 🧩 Additional Options:

- Specify browser:

  ```bash
  npx playwright codegen --browser=chromium
  ```

- Output to file:

  ```bash
  npx playwright codegen https://example.com --output=myTest.spec.ts
  ```

#### 🎯 Use Cases:

- Create test templates quickly
- Learn correct selectors
- Avoid selector guesswork

---

### **25. Detailed View of Test Traces, HTML Reports, Logs & Screenshots for Test Results**

Playwright provides a **rich debugging ecosystem** with test trace collection, screenshots, logs, and HTML reports — all automatically integrated into test runs.

---

#### 📦 1. **Traces**

A **trace** is a complete recording of your test, including:

- Every action and event
- DOM snapshots before and after each step
- Screenshots
- Console logs
- Network requests

##### 🛠 How to Enable Tracing:

In `playwright.config.ts`:

```ts
use: {
  trace: 'on-first-retry', // options: on, off, retain-on-failure
}
```

Or via CLI:

```bash
npx playwright test --trace on
```

##### 📂 View the Trace:

```bash
npx playwright show-trace trace.zip
```

---

#### 🧾 2. **HTML Reports**

Playwright generates clean, interactive **HTML test reports**.

##### How to Enable:

Reports are generated automatically if configured in `playwright.config.ts`:

```ts
reporter: [["html", { open: "never" }]];
```

Generate and open the report:

```bash
npx playwright show-report
```

##### Features:

- Summary of passed/failed/skipped tests
- Error messages and stack traces
- Links to traces/screenshots/videos (if enabled)

---

#### 📸 3. **Screenshots**

Playwright can automatically take screenshots:

- On failure
- Manually within tests

##### 📌 Automatically on failure:

```ts
use: {
  screenshot: 'only-on-failure',
}
```

##### 📸 Manually capture:

```js
await page.screenshot({ path: "login-page.png" });
```

---

#### 📝 4. **Logs**

Playwright captures various logs:

- **Terminal logs** during test execution

- **Console logs** from the browser using:

  ```js
  page.on("console", (msg) => console.log(msg.text()));
  ```

- **Network request/response logging**:

  ```js
  page.on("request", (req) => console.log(">>", req.method(), req.url()));
  page.on("response", (res) => console.log("<<", res.status(), res.url()));
  ```

---

### 🔧 Run Playwright Tests with the UI Runner

If you already have a Playwright test project set up, you can launch the **Playwright UI Runner** to run and debug tests visually.

---

### 1️⃣ Run with UI Runner

Use the following command in your terminal:

```bash
npx playwright test --ui
```

This opens a graphical interface where you can:

- Browse test files
- Run and re-run tests
- Step through tests in the browser
- View logs, screenshots, and trace data (if enabled)

---

### 2️⃣ Enable Tracing (Optional)

For richer debugging information, enable tracing in your `playwright.config.ts`:

```ts
use: {
  trace: 'on',
},
```

This will allow you to view execution traces in the UI for each test.

---

### Login and place order example

```js
const { test, expect } = require("@playwright/test");

test("@Webst Client App login", async ({ page }) => {
  //js file- Login js, DashboardPage
  const email = "anshika@gmail.com";
  const productName = "ZARA COAT 3";
  const products = page.locator(".card-body");
  await page.goto("https://rahulshettyacademy.com/client");
  await page.locator("#userEmail").fill(email);
  await page.locator("#userPassword").fill("Iamking@000");
  await page.locator("[value='Login']").click();
  await page.waitForLoadState("networkidle");
  await page.locator(".card-body b").first().waitFor();
  const titles = await page.locator(".card-body b").allTextContents();
  console.log(titles);
  const count = await products.count();
  for (let i = 0; i < count; ++i) {
    if ((await products.nth(i).locator("b").textContent()) === productName) {
      //add to cart
      await products.nth(i).locator("text= Add To Cart").click();
      break;
    }
  }

  await page.locator("[routerlink*='cart']").click();
  //await page.pause();

  await page.locator("div li").first().waitFor();
  const bool = await page.locator("h3:has-text('zara coat 3')").isVisible();
  expect(bool).toBeTruthy();
  await page.locator("text=Checkout").click();

  await page.locator("[placeholder*='Country']").pressSequentially("ind");
  const dropdown = page.locator(".ta-results");
  await dropdown.waitFor();
  const optionsCount = await dropdown.locator("button").count();
  for (let i = 0; i < optionsCount; ++i) {
    const text = await dropdown.locator("button").nth(i).textContent();
    if (text === " India") {
      await dropdown.locator("button").nth(i).click();
      break;
    }
  }

  expect(page.locator(".user__name [type='text']").first()).toHaveText(email);
  await page.locator(".action__submit").click();
  await expect(page.locator(".hero-primary")).toHaveText(
    " Thankyou for the order. "
  );
  const orderId = await page
    .locator(".em-spacer-1 .ng-star-inserted")
    .textContent();
  console.log(orderId);

  await page.locator("button[routerlink*='myorders']").click();
  await page.locator("tbody").waitFor();
  const rows = await page.locator("tbody tr");

  for (let i = 0; i < (await rows.count()); ++i) {
    const rowOrderId = await rows.nth(i).locator("th").textContent();
    if (orderId.includes(rowOrderId)) {
      await rows.nth(i).locator("button").first().click();
      break;
    }
  }
  const orderIdDetails = await page.locator(".col-text").textContent();
  expect(orderId.includes(orderIdDetails)).toBeTruthy();
});
```

## Playwright - GetByLabel, GetByRole, GetByText, Filters, chaining example

```js
import { test, expect } from "@playwright/test";

test("Playwright Special locators", async ({ page }) => {
  await page.goto("https://rahulshettyacademy.com/angularpractice/");
  await page.getByLabel("Check me out if you Love IceCreams!").click();
  await page.getByLabel("Employed").check();
  await page.getByLabel("Gender").selectOption("Female");
  await page.getByPlaceholder("Password").fill("abc123");
  await page.getByRole("button", { name: "Submit" }).click();
  await page
    .getByText("Success! The Form has been submitted successfully!.")
    .isVisible();
  await page.getByRole("link", { name: "Shop" }).click();
  await page
    .locator("app-card")
    .filter({ hasText: "Nokia Edge" })
    .getByRole("button")
    .click();

  //locator(css)
});
```

### Rewrite place order test with getByRole, getByText conjuction with Filter logic

```js
const { test, expect } = require("@playwright/test");

test("@Webst Client App login", async ({ page }) => {
  //js file- Login js, DashboardPage
  const email = "anshika@gmail.com";
  const productName = "ZARA COAT 3";
  const products = page.locator(".card-body");
  await page.goto("https://rahulshettyacademy.com/client");
  await page.getByPlaceholder("email@example.com").fill(email);
  await page.getByPlaceholder("enter your passsword").fill("Iamking@000");
  await page.getByRole("button", { name: "Login" }).click();
  await page.waitForLoadState("networkidle");
  await page.locator(".card-body b").first().waitFor();

  await page
    .locator(".card-body")
    .filter({ hasText: "ZARA COAT 3" })
    .getByRole("button", { name: "Add to Cart" })
    .click();

  await page
    .getByRole("listitem")
    .getByRole("button", { name: "Cart" })
    .click();

  //await page.pause();
  await page.locator("div li").first().waitFor();
  await expect(page.getByText("ZARA COAT 3")).toBeVisible();

  await page.getByRole("button", { name: "Checkout" }).click();

  await page.getByPlaceholder("Select Country").pressSequentially("ind");

  await page.getByRole("button", { name: "India" }).nth(1).click();
  await page.getByText("PLACE ORDER").click();

  await expect(page.getByText("Thankyou for the order.")).toBeVisible();
});
```

### Calender Example

```js
const { test, expect } = require("@playwright/test");

test("Calendar validations", async ({ page }) => {
  const monthNumber = "6";
  const date = "15";
  const year = "2027";
  const expectedList = [monthNumber, date, year];
  await page.goto("https://rahulshettyacademy.com/seleniumPractise/#/offers");
  await page.locator(".react-date-picker__inputGroup").click();
  await page.locator(".react-calendar__navigation__label").click();
  await page.locator(".react-calendar__navigation__label").click();
  await page.getByText(year).click();
  await page
    .locator(".react-calendar__year-view__months__month")
    .nth(Number(monthNumber) - 1)
    .click();
  await page.locator("//abbr[text()='" + date + "']").click();
  const inputs = await page.locator(".react-date-picker__inputGroup input");
  for (let index = 0; index < inputs.length; index++) {
    const value = inputs[index].getAttribute("value");
    expect(value).toEqual(expectedList[index]);
  }
});
```

---

### How to Validate if Element is Hidden or Displayed using `expect` Assertions (Playwright)

In Playwright, the `expect` API from `@playwright/test` allows you to assert an element's visibility. Here's how to validate if an element is **visible** or **hidden**:

```ts
import { test, expect } from "@playwright/test";

test("check visibility of element", async ({ page }) => {
  await page.goto("https://example.com");

  // Check if the element is visible
  await expect(page.locator("#my-element")).toBeVisible();

  // Check if the element is hidden
  await expect(page.locator("#my-element")).toBeHidden();
});
```

> ✅ Use `.toBeVisible()` or `.toBeHidden()` for checking visibility states.

---

## ✅ How to Automate Java/JavaScript Alert Popups with Playwright (Full Guide)

Web apps often use **JavaScript popups** like `alert()`, `confirm()`, or `prompt()` to get user input or show warnings. These popups **block user interaction** until dismissed.

In **Playwright**, we use `page.on('dialog', callback)` to listen for and handle these popups.

---

## ⚙️ How Playwright Handles Popups

### 🔹 Using `page.on('dialog', callback)`

This tells Playwright:

> “Whenever a dialog (popup) appears on the page, run this function to handle it.”

### 💡 What's `on`?

`on` is a method used to **listen for events** on the `page` object. When a specific event (like a popup) happens, Playwright **fires your function**.

---

## ✍️ Step-by-Step Examples

---

### **1. Handling a Simple Alert**

#### 💻 JavaScript Code:

```ts
import { test, expect } from "@playwright/test";

test("handle alert popup", async ({ page }) => {
  // Set up event listener before the popup shows
  page.on("dialog", async (dialog) => {
    console.log("Alert says:", dialog.message()); // Read the alert text
    await dialog.accept(); // Clicks "OK"
  });

  await page.goto("https://example.com");
  await page.click("#alert-button"); // This triggers alert('Hello!')
});
```

---

### **2. Handling a Confirm Dialog**

```ts
test("handle confirm popup", async ({ page }) => {
  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("confirm"); // Confirm dialog
    console.log("Confirm says:", dialog.message());
    await dialog.accept(); // Accept = "OK"
    // or await dialog.dismiss(); to click "Cancel"
  });

  await page.goto("https://example.com");
  await page.click("#confirm-button");
});
```

---

### **3. Handling a Prompt Dialog**

```ts
test("handle prompt popup", async ({ page }) => {
  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("prompt"); // Prompt dialog
    await dialog.accept("John Doe"); // Fill input and press OK
  });

  await page.goto("https://example.com");
  await page.click("#prompt-button");
});
```

---

## 📌 Key Notes

- ✅ Always set up `page.on('dialog', ...)` **before** the popup appears.
- ✅ You can access:

  - `dialog.message()` – the text shown in the popup
  - `dialog.type()` – one of `'alert'`, `'confirm'`, `'prompt'`
  - `dialog.accept([value])` – clicks "OK"
  - `dialog.dismiss()` – clicks "Cancel" (confirm/prompt)

- ❌ Playwright does **not auto-close popups**. You **must handle them**.

---

## How to handle & Automate frames with Playwright - Example

## 🎯 What You'll Learn

- Navigate to a page with frames
- Switch to a single frame
- Interact with nested (child) frames
- Switch back to the default content
- Use `frame()` and `frameLocator()` methods

---

## 🧪 Sample HTML Structure

```html
<!-- main.html -->
<html>
  <body>
    <iframe
      id="parent-frame"
      srcdoc='
      <html>
        <body>
          <iframe id="child-frame" srcdoc="
            <html>
              <body>
                <button>Click me</button>
              </body>
            </html>
          "></iframe>
        </body>
      </html>'
    >
    </iframe>

    <button id="main-btn">Main Page Button</button>
  </body>
</html>
```

---

## ✅ Playwright Script: Handle & Automate All Frames

```ts
import { chromium } from "playwright";

(async () => {
  const browser = await chromium.launch({ headless: false });
  const page = await browser.newPage();

  // Navigate to your page
  await page.goto("https://your-site.com/main.html");

  // === 1. Default Frame Interaction (main page) ===
  await page.click("#main-btn"); // interacts with button in main page

  // === 2. Switch to Parent Frame ===
  const parentFrame = await page.frameLocator("#parent-frame");

  // === 3. Switch to Nested Child Frame and Click Button ===
  await parentFrame
    .frameLocator("#child-frame")
    .locator("text=Click me")
    .click();

  // === 4. Switch Back to Main Page (default frame) ===
  // No need to "switch back" — just use `page` again
  await page.click("#main-btn");

  // === 5. Optional: Using Traditional Frame API (less common) ===
  const parent =
    page.frame({ name: "parent-frame" }) ||
    page.frames().find((f) => f.url().includes("parent"));
  if (parent) {
    const child = parent.childFrames()[0];
    await child.click("text=Click me");
  }

  await browser.close();
})();
```

---

## 🧠 Key Concepts

| Task                            | Playwright Method                                |
| ------------------------------- | ------------------------------------------------ |
| Main page (default frame)       | `page.locator()` / `page.click()`                |
| Switch to iframe                | `page.frame()` or `page.frameLocator()`          |
| Nested/child frames             | `frame.childFrames()` or nested `frameLocator()` |
| Back to main page               | Just use `page` again                            |
| Wait for elements inside frames | `locator.waitFor()`                              |

---

## ✅ Best Practice: Use `frameLocator()` When Possible

- It's cleaner, chainable, and easier to read.
- Works well with nested frames and scoped locators.

---

## 💬 Example Summary

```ts
await page
  .frameLocator("#parent-frame")
  .frameLocator("#child-frame")
  .locator("text=Click me")
  .click();

await page.click("#main-btn"); // Back to main content
```

---

## REST API's

```ts
import { test, expect } from "@playwright/test";

// GET Request Testing
test.describe("REST API - GET Operations", () => {
  test("should fetch all users", async ({ request }) => {
    const response = await request.get("/users");

    expect(response.status()).toBe(200);
    expect(response.headers()["content-type"]).toContain("application/json");

    const users = await response.json();
    expect(Array.isArray(users)).toBeTruthy();
    expect(users.length).toBeGreaterThan(0);

    // Validate user schema
    users.forEach((user) => {
      expect(user).toHaveProperty("id");
      expect(user).toHaveProperty("name");
      expect(user).toHaveProperty("email");
    });
  });

  test("should fetch user by ID", async ({ request }) => {
    const userId = 1;
    const response = await request.get(`/users/${userId}`);

    expect(response.status()).toBe(200);

    const user = await response.json();
    expect(user.id).toBe(userId);
    expect(typeof user.name).toBe("string");
    expect(user.email).toMatch(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
  });

  test("should return 404 for non-existent user", async ({ request }) => {
    const response = await request.get("/users/99999");
    expect(response.status()).toBe(404);
  });

  test("should handle query parameters", async ({ request }) => {
    const response = await request.get("/users", {
      params: {
        page: "1",
        limit: "10",
        sort: "name",
      },
    });

    expect(response.status()).toBe(200);
    const data = await response.json();
    expect(data.users.length).toBeLessThanOrEqual(10);
  });
});

// POST Request Testing
test.describe("REST API - POST Operations", () => {
  test("should create a new user", async ({ request }) => {
    const newUser = {
      name: "John Doe",
      email: "john.doe@example.com",
      age: 30,
    };

    const response = await request.post("/users", {
      data: newUser,
    });

    expect(response.status()).toBe(201);

    const createdUser = await response.json();
    expect(createdUser.name).toBe(newUser.name);
    expect(createdUser.email).toBe(newUser.email);
    expect(createdUser).toHaveProperty("id");
    expect(createdUser).toHaveProperty("createdAt");
  });

  test("should validate required fields", async ({ request }) => {
    const invalidUser = {
      name: "John Doe",
      // missing email
    };

    const response = await request.post("/users", {
      data: invalidUser,
    });

    expect(response.status()).toBe(400);

    const error = await response.json();
    expect(error.message).toContain("email is required");
  });

  test("should handle duplicate email", async ({ request }) => {
    const user = {
      name: "Jane Doe",
      email: "existing@example.com",
    };

    const response = await request.post("/users", {
      data: user,
    });

    expect(response.status()).toBe(409);

    const error = await response.json();
    expect(error.message).toContain("email already exists");
  });
});

// PUT Request Testing
test.describe("REST API - PUT Operations", () => {
  test("should update existing user", async ({ request }) => {
    const userId = 1;
    const updatedUser = {
      name: "Updated Name",
      email: "updated@example.com",
      age: 35,
    };

    const response = await request.put(`/users/${userId}`, {
      data: updatedUser,
    });

    expect(response.status()).toBe(200);

    const user = await response.json();
    expect(user.name).toBe(updatedUser.name);
    expect(user.email).toBe(updatedUser.email);
    expect(user.age).toBe(updatedUser.age);
    expect(user).toHaveProperty("updatedAt");
  });

  test("should return 404 for non-existent user update", async ({
    request,
  }) => {
    const response = await request.put("/users/99999", {
      data: { name: "Test" },
    });

    expect(response.status()).toBe(404);
  });
});

// PATCH Request Testing
test.describe("REST API - PATCH Operations", () => {
  test("should partially update user", async ({ request }) => {
    const userId = 1;
    const partialUpdate = {
      age: 40,
    };

    const response = await request.patch(`/users/${userId}`, {
      data: partialUpdate,
    });

    expect(response.status()).toBe(200);

    const user = await response.json();
    expect(user.age).toBe(40);
    // Other fields should remain unchanged
    expect(user).toHaveProperty("name");
    expect(user).toHaveProperty("email");
  });
});

// DELETE Request Testing
test.describe("REST API - DELETE Operations", () => {
  test("should delete existing user", async ({ request }) => {
    const userId = 1;
    const response = await request.delete(`/users/${userId}`);

    expect(response.status()).toBe(204);

    // Verify user is deleted
    const getResponse = await request.get(`/users/${userId}`);
    expect(getResponse.status()).toBe(404);
  });

  test("should return 404 for non-existent user deletion", async ({
    request,
  }) => {
    const response = await request.delete("/users/99999");
    expect(response.status()).toBe(404);
  });
});

// Authentication Testing
test.describe("REST API - Authentication", () => {
  let authToken: string;

  test.beforeAll(async ({ request }) => {
    // Login to get auth token
    const loginResponse = await request.post("/auth/login", {
      data: {
        email: "admin@example.com",
        password: "password123",
      },
    });

    const loginData = await loginResponse.json();
    authToken = loginData.token;
  });

  test("should access protected route with valid token", async ({
    request,
  }) => {
    const response = await request.get("/admin/users", {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    expect(response.status()).toBe(200);
  });

  test("should reject access without token", async ({ request }) => {
    const response = await request.get("/admin/users");
    expect(response.status()).toBe(401);
  });

  test("should reject access with invalid token", async ({ request }) => {
    const response = await request.get("/admin/users", {
      headers: {
        Authorization: "Bearer invalid-token",
      },
    });

    expect(response.status()).toBe(401);
  });
});

// File Upload Testing
test.describe("REST API - File Upload", () => {
  test("should upload file successfully", async ({ request }) => {
    const response = await request.post("/upload", {
      multipart: {
        file: {
          name: "test.txt",
          mimeType: "text/plain",
          buffer: Buffer.from("Test file content"),
        },
        description: "Test file upload",
      },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result).toHaveProperty("fileId");
    expect(result).toHaveProperty("url");
  });

  test("should validate file type", async ({ request }) => {
    const response = await request.post("/upload", {
      multipart: {
        file: {
          name: "test.exe",
          mimeType: "application/x-msdownload",
          buffer: Buffer.from("Executable content"),
        },
      },
    });

    expect(response.status()).toBe(400);

    const error = await response.json();
    expect(error.message).toContain("file type not allowed");
  });
});

// Response Time Testing
test.describe("REST API - Performance", () => {
  test("should respond within acceptable time", async ({ request }) => {
    const startTime = Date.now();

    const response = await request.get("/users");

    const responseTime = Date.now() - startTime;

    expect(response.status()).toBe(200);
    expect(responseTime).toBeLessThan(2000); // Should respond within 2 seconds
  });
});

// Error Handling Testing
test.describe("REST API - Error Handling", () => {
  test("should handle malformed JSON", async ({ request }) => {
    const response = await request.post("/users", {
      data: "invalid json string",
      headers: {
        "Content-Type": "application/json",
      },
    });

    expect(response.status()).toBe(400);
  });

  test("should handle missing content-type", async ({ request }) => {
    const response = await request.post("/users", {
      data: JSON.stringify({ name: "Test" }),
    });

    expect(response.status()).toBe(415);
  });
});
```

## Graphql Api's

```ts
import { test, expect } from "@playwright/test";

// GraphQL Query Testing
test.describe("GraphQL API - Queries", () => {
  test("should fetch all users with basic fields", async ({ request }) => {
    const query = `
      query GetUsers {
        users {
          id
          name
          email
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: { query },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data).toBeTruthy();
    expect(result.data.users).toBeTruthy();
    expect(Array.isArray(result.data.users)).toBeTruthy();

    // Validate schema
    result.data.users.forEach((user) => {
      expect(user).toHaveProperty("id");
      expect(user).toHaveProperty("name");
      expect(user).toHaveProperty("email");
    });
  });

  test("should fetch user by ID with nested data", async ({ request }) => {
    const query = `
      query GetUser($id: ID!) {
        user(id: $id) {
          id
          name
          email
          profile {
            bio
            avatar
          }
          posts {
            id
            title
            content
            createdAt
          }
        }
      }
    `;

    const variables = { id: "1" };

    const response = await request.post("/graphql", {
      data: { query, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.user).toBeTruthy();
    expect(result.data.user.id).toBe("1");
    expect(result.data.user.profile).toBeTruthy();
    expect(Array.isArray(result.data.user.posts)).toBeTruthy();
  });

  test("should handle query with arguments and filters", async ({
    request,
  }) => {
    const query = `
      query GetPosts($limit: Int, $offset: Int, $authorId: ID) {
        posts(limit: $limit, offset: $offset, authorId: $authorId) {
          id
          title
          content
          author {
            id
            name
          }
          createdAt
        }
      }
    `;

    const variables = {
      limit: 10,
      offset: 0,
      authorId: "1",
    };

    const response = await request.post("/graphql", {
      data: { query, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.posts).toBeTruthy();
    expect(result.data.posts.length).toBeLessThanOrEqual(10);

    // Verify all posts are from the specified author
    result.data.posts.forEach((post) => {
      expect(post.author.id).toBe("1");
    });
  });

  test("should handle query with fragments", async ({ request }) => {
    const query = `
      fragment UserInfo on User {
        id
        name
        email
        createdAt
      }
      
      fragment PostInfo on Post {
        id
        title
        content
        createdAt
      }
      
      query GetUserWithPosts($userId: ID!) {
        user(id: $userId) {
          ...UserInfo
          posts {
            ...PostInfo
          }
        }
      }
    `;

    const variables = { userId: "1" };

    const response = await request.post("/graphql", {
      data: { query, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.user).toBeTruthy();
  });
});

// GraphQL Mutation Testing
test.describe("GraphQL API - Mutations", () => {
  test("should create a new user", async ({ request }) => {
    const mutation = `
      mutation CreateUser($input: CreateUserInput!) {
        createUser(input: $input) {
          id
          name
          email
          createdAt
        }
      }
    `;

    const variables = {
      input: {
        name: "John Doe",
        email: "john.doe@example.com",
        password: "securePassword123",
      },
    };

    const response = await request.post("/graphql", {
      data: { mutation, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.createUser).toBeTruthy();
    expect(result.data.createUser.name).toBe("John Doe");
    expect(result.data.createUser.email).toBe("john.doe@example.com");
    expect(result.data.createUser).toHaveProperty("id");
    expect(result.data.createUser).toHaveProperty("createdAt");
  });

  test("should update existing user", async ({ request }) => {
    const mutation = `
      mutation UpdateUser($id: ID!, $input: UpdateUserInput!) {
        updateUser(id: $id, input: $input) {
          id
          name
          email
          updatedAt
        }
      }
    `;

    const variables = {
      id: "1",
      input: {
        name: "Updated Name",
        email: "updated@example.com",
      },
    };

    const response = await request.post("/graphql", {
      data: { mutation, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.updateUser.name).toBe("Updated Name");
    expect(result.data.updateUser.email).toBe("updated@example.com");
  });

  test("should delete user", async ({ request }) => {
    const mutation = `
      mutation DeleteUser($id: ID!) {
        deleteUser(id: $id) {
          success
          message
        }
      }
    `;

    const variables = { id: "1" };

    const response = await request.post("/graphql", {
      data: { mutation, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.deleteUser.success).toBe(true);
  });

  test("should create post with file upload", async ({ request }) => {
    const mutation = `
      mutation CreatePost($input: CreatePostInput!) {
        createPost(input: $input) {
          id
          title
          content
          image {
            url
            alt
          }
        }
      }
    `;

    // GraphQL file upload using multipart/form-data
    const operations = JSON.stringify({
      query: mutation,
      variables: {
        input: {
          title: "Post with Image",
          content: "This post has an image attached",
          image: null,
        },
      },
    });

    const map = JSON.stringify({
      "0": ["variables.input.image"],
    });

    const response = await request.post("/graphql", {
      multipart: {
        operations,
        map,
        "0": {
          name: "image.jpg",
          mimeType: "image/jpeg",
          buffer: Buffer.from("fake image data"),
        },
      },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.createPost.image).toBeTruthy();
  });
});

// GraphQL Subscription Testing (for WebSocket-based subscriptions)
test.describe("GraphQL API - Subscriptions", () => {
  test("should establish subscription connection", async ({ request }) => {
    // Note: This is a simplified example. Real subscription testing
    // would require WebSocket connection handling
    const subscription = `
      subscription OnPostCreated {
        postCreated {
          id
          title
          author {
            name
          }
        }
      }
    `;

    // For HTTP-based subscription polling
    const response = await request.post("/graphql", {
      data: { query: subscription },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    // This would depend on your GraphQL subscription implementation
    expect(result.errors).toBeUndefined();
  });
});

// GraphQL Error Handling
test.describe("GraphQL API - Error Handling", () => {
  test("should handle syntax errors in query", async ({ request }) => {
    const invalidQuery = `
      query {
        users {
          id
          name
          // Invalid syntax - missing closing brace
    `;

    const response = await request.post("/graphql", {
      data: { query: invalidQuery },
    });

    expect(response.status()).toBe(400);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].message).toContain("Syntax Error");
  });

  test("should handle field validation errors", async ({ request }) => {
    const query = `
      query GetUser($id: ID!) {
        user(id: $id) {
          id
          nonExistentField
        }
      }
    `;

    const variables = { id: "1" };

    const response = await request.post("/graphql", {
      data: { query, variables },
    });

    expect(response.status()).toBe(400);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].message).toContain("Cannot query field");
  });

  test("should handle missing required variables", async ({ request }) => {
    const query = `
      query GetUser($id: ID!) {
        user(id: $id) {
          id
          name
        }
      }
    `;

    // Missing required variable
    const response = await request.post("/graphql", {
      data: { query },
    });

    expect(response.status()).toBe(400);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].message).toContain(
      'Variable "$id" of required type "ID!" was not provided'
    );
  });

  test("should handle business logic errors", async ({ request }) => {
    const mutation = `
      mutation CreateUser($input: CreateUserInput!) {
        createUser(input: $input) {
          id
          email
        }
      }
    `;

    const variables = {
      input: {
        name: "Test User",
        email: "existing@example.com", // Email that already exists
      },
    };

    const response = await request.post("/graphql", {
      data: { mutation, variables },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].message).toContain("Email already exists");
    expect(result.errors[0].extensions.code).toBe("DUPLICATE_EMAIL");
  });
});

// GraphQL Authentication Testing
test.describe("GraphQL API - Authentication", () => {
  let authToken: string;

  test.beforeAll(async ({ request }) => {
    const loginMutation = `
      mutation Login($email: String!, $password: String!) {
        login(email: $email, password: $password) {
          token
          user {
            id
            name
          }
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: {
        query: loginMutation,
        variables: {
          email: "admin@example.com",
          password: "password123",
        },
      },
    });

    const result = await response.json();
    authToken = result.data.login.token;
  });

  test("should access protected query with valid token", async ({
    request,
  }) => {
    const query = `
      query GetMyProfile {
        me {
          id
          name
          email
          role
        }
      }
    `;

    const response = await request.post("/graphql", {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
      data: { query },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.me).toBeTruthy();
  });

  test("should reject protected query without token", async ({ request }) => {
    const query = `
      query GetMyProfile {
        me {
          id
          name
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: { query },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].extensions.code).toBe("UNAUTHENTICATED");
  });
});

// GraphQL Schema Introspection Testing
test.describe("GraphQL API - Schema Introspection", () => {
  test("should return schema types", async ({ request }) => {
    const introspectionQuery = `
      query IntrospectionQuery {
        __schema {
          types {
            name
            kind
          }
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: { query: introspectionQuery },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.__schema.types).toBeTruthy();
    expect(Array.isArray(result.data.__schema.types)).toBeTruthy();
  });

  test("should return type details", async ({ request }) => {
    const typeQuery = `
      query GetTypeDetails {
        __type(name: "User") {
          name
          kind
          fields {
            name
            type {
              name
              kind
            }
          }
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: { query: typeQuery },
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.errors).toBeUndefined();
    expect(result.data.__type.name).toBe("User");
    expect(result.data.__type.fields).toBeTruthy();
  });
});

// GraphQL Performance Testing
test.describe("GraphQL API - Performance", () => {
  test("should handle complex nested queries efficiently", async ({
    request,
  }) => {
    const complexQuery = `
      query ComplexQuery {
        users(limit: 5) {
          id
          name
          posts {
            id
            title
            comments {
              id
              content
              author {
                id
                name
              }
            }
          }
        }
      }
    `;

    const startTime = Date.now();

    const response = await request.post("/graphql", {
      data: { query: complexQuery },
    });

    const responseTime = Date.now() - startTime;

    expect(response.status()).toBe(200);
    expect(responseTime).toBeLessThan(5000); // Should complete within 5 seconds

    const result = await response.json();
    expect(result.errors).toBeUndefined();
  });

  test("should handle query depth limiting", async ({ request }) => {
    // Very deeply nested query that should be rejected
    const deepQuery = `
      query DeepQuery {
        users {
          posts {
            comments {
              author {
                posts {
                  comments {
                    author {
                      posts {
                        comments {
                          author {
                            name
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    `;

    const response = await request.post("/graphql", {
      data: { query: deepQuery },
    });

    expect(response.status()).toBe(400);

    const result = await response.json();
    expect(result.errors).toBeTruthy();
    expect(result.errors[0].message).toContain("Query depth limit exceeded");
  });
});
```

## Parsing API response & passing token to browser local storage with Playwright

---

## ✅ What Are We Doing?

We want to:

1. **Send a login API request manually** (bypassing the UI).
2. **Extract the auth token** from the response.
3. **Launch the browser using Playwright**.
4. **Inject the token into `localStorage`**.
5. **Navigate to a protected (authenticated) page**.

This is especially useful for automated testing when you want to skip the login flow and go straight to testing authenticated pages.

---

## 🧱 Step-by-Step Explanation

### 🔹 Step 1: Install Playwright (if you haven't)

```bash
npm install -D playwright
npx playwright install
```

---

### 🔹 Step 2: Send the API Login Request

Playwright has a built-in `request.newContext()` method that allows you to send HTTP requests **outside the browser**.

```js
const { chromium, request } = require('playwright');

(async () => {
  const requestContext = await request.newContext();  // Create a request context
  const response = await requestContext.post('https://your-api.com/login', {
    data: {
      username: 'myuser',
      password: 'mypassword'
    }
  });

  const body = await response.json();  // Parse JSON response
  const token = body.token;            // Extract token (adjust key as needed)
```

This is like using Postman or `fetch()` to hit the login API and extract the token.

---

### 🔹 Step 3: Launch the Browser

```js
const browser = await chromium.launch({ headless: false }); // Headed browser for visibility
const context = await browser.newContext(); // New browser context
const page = await context.newPage(); // Open new tab
```

---

### 🔹 Step 4: Inject the Token into `localStorage`

You **must do this _before_ the page loads**, otherwise it might redirect to login before the token is present.

```js
await page.addInitScript(
  ([key, value]) => {
    localStorage.setItem(key, value);
  },
  ["auth_token", token]
); // Replace 'auth_token' with your actual localStorage key
```

This uses `addInitScript()` to run a script **before any page code** executes — perfect for setting tokens.

---

### 🔹 Step 5: Navigate to the Authenticated Page

```js
  await page.goto('https://your-app.com/dashboard');  // Now you are "logged in"
})();
```

At this point, the page loads with the token already in local storage, simulating a logged-in user.

---

## 📂 Full Code (Copy/Paste Ready)

```js
const { chromium, request } = require("playwright");

(async () => {
  // 1. Send login API request
  const requestContext = await request.newContext();
  const response = await requestContext.post("https://your-api.com/login", {
    data: {
      username: "myuser",
      password: "mypassword",
    },
  });

  const body = await response.json();
  const token = body.token; // Adjust to match your API response

  // 2. Launch browser and set token in localStorage
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  const page = await context.newPage();

  await page.addInitScript(
    ([key, value]) => {
      localStorage.setItem(key, value);
    },
    ["auth_token", token]
  );

  // 3. Navigate to an authenticated page
  await page.goto("https://your-app.com/dashboard");
})();
```

---

## 🧠 Why This Works

- **addInitScript**: Runs before any scripts on the page — great for manipulating localStorage or cookies.
- **Token Injection**: Mimics what a real user would have in their browser after logging in.
- **API-first Login**: Skips the slow UI login process for faster, more reliable tests.

---

## 🧪 Bonus: Saving the Login State

To persist the login for future tests:

```js
await context.storageState({ path: "auth.json" });
```

Then reuse it later:

```js
const context = await browser.newContext({ storageState: "auth.json" });
```

---

## 🧩 What Is `addInitScript()`?

`addInitScript()` is a **Playwright API method** that lets you inject JavaScript into every page or frame **before** any script from the page itself runs — even before `DOMContentLoaded`.

It's available on:

- `browserContext.addInitScript(...)` — affects **all pages** in the context
- `page.addInitScript(...)` — affects **only that specific page**

---

## 📌 Use Cases

Common uses of `addInitScript()` include:

1. **Setting `localStorage` or `sessionStorage`** before the app loads
2. **Injecting mock data or functions**
3. **Disabling analytics or certain JavaScript behaviors**
4. **Overriding browser APIs** (e.g., `navigator.geolocation`)

---

## 🧪 How It Works (Detailed Example)

Here’s what we used earlier:

```js
await page.addInitScript(
  ([key, value]) => {
    localStorage.setItem(key, value);
  },
  ["auth_token", token]
);
```

### 🧠 How to Think About This:

This code runs **inside the browser** — just like if you typed it in DevTools. The outer `addInitScript()` wraps code that is serialized and executed in the page context.

### Breakdown:

| Part                                                 | Explanation                                                                           |
| ---------------------------------------------------- | ------------------------------------------------------------------------------------- |
| `addInitScript(fn, args)`                            | Accepts a **function** and **arguments** that will be run in the browser page context |
| `([key, value]) => localStorage.setItem(key, value)` | Arrow function that runs **in the browser**, setting `localStorage`                   |
| `['auth_token', token]`                              | These are the arguments passed to the arrow function — `key` and `value`              |

Effectively this becomes:

```js
localStorage.setItem("auth_token", "eyJhbGciOi...");
```

And it runs **before** any scripts on `https://your-app.com/dashboard`.

---

## 🧪 Full Minimal Example

```js
const { chromium } = require("playwright");

(async () => {
  const browser = await chromium.launch();
  const context = await browser.newContext();
  const page = await context.newPage();

  // Set localStorage value before any page scripts run
  await page.addInitScript(
    ([key, value]) => {
      localStorage.setItem(key, value);
    },
    ["auth_token", "abc123"]
  );

  await page.goto("https://example.com"); // Auth token is already there!

  await browser.close();
})();
```

---

## 🔒 Important Notes

- `addInitScript()` only works if **called before `goto()`** — otherwise, the page’s scripts already run.
- It **doesn’t persist** across contexts unless you call it for each context or use `browserContext.addInitScript()` globally.
- It runs on **every frame** loaded in that page if added before navigation.

---

## 🛠 When to Use It vs Other Methods

| Method                   | Use it when...                                                                       |
| ------------------------ | ------------------------------------------------------------------------------------ |
| `addInitScript()`        | You want to **inject code early** into page runtime (localStorage, global overrides) |
| `evaluate()`             | You want to **run code after page load**                                             |
| `context.storageState()` | You want to **save and reuse login state** without scripting                         |

---

# Playwright SessionStorage Test - Detailed Explanation

```js
const { test, expect } = require("@playwright/test");

test("save and restore sessionStorage after login on rahulshettyacademy", async ({
  browser,
}) => {
  // First context and page
  const context1 = await browser.newContext();
  const page1 = await context1.newPage();

  // Go to login page
  await page1.goto("https://rahulshettyacademy.com/client");

  // Perform login
  await page1.locator("#userEmail").fill("testaccountag@gmail.com");
  await page1.locator("#userPassword").fill("Test@1234");
  await page1.locator("[value='Login']").click();

  // Wait for login success - use a more specific selector
  await page1.waitForLoadState("networkidle");
  await expect(page1.locator("text=Automation Practice")).toBeVisible({
    timeout: 10000,
  });

  // Additional wait to ensure all session data is set
  await page1.waitForTimeout(2000);

  // Save both sessionStorage and localStorage (in case the site uses localStorage)
  const storageData = await page1.evaluate(() => {
    const sessionData = {};
    const localData = {};

    // Get sessionStorage
    for (let i = 0; i < sessionStorage.length; i++) {
      const key = sessionStorage.key(i);
      sessionData[key] = sessionStorage.getItem(key);
    }

    // Get localStorage (in case it's used for auth)
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      localData[key] = localStorage.getItem(key);
    }

    return { sessionData, localData };
  });

  console.log("Saved storage data:", storageData);
  await context1.close();

  // Create a new context and inject saved storage before navigation
  const context2 = await browser.newContext();

  // Inject storage data using addInitScript
  await context2.addInitScript((data) => {
    // Restore sessionStorage
    for (const [key, value] of Object.entries(data.sessionData)) {
      if (value !== null) {
        sessionStorage.setItem(key, value);
      }
    }

    // Restore localStorage
    for (const [key, value] of Object.entries(data.localData)) {
      if (value !== null) {
        localStorage.setItem(key, value);
      }
    }
  }, storageData);

  const page2 = await context2.newPage();

  // Go to the same client page
  await page2.goto("https://rahulshettyacademy.com/client");

  // Wait for page load
  await page2.waitForLoadState("networkidle");

  // Give a moment for session to be recognized
  await page2.waitForTimeout(1000);

  // Verify sessionStorage restored by checking for logged-in UI element
  try {
    await expect(page2.locator("text=Automation Practice")).toBeVisible({
      timeout: 5000,
    });
    console.log("Session successfully restored - user is logged in");
  } catch (error) {
    // If the main check fails, let's see what's on the page
    const pageContent = await page2.content();
    console.log("Page URL:", page2.url());
    console.log("Page title:", await page2.title());

    // Check if we're on login page
    const isLoginPage = await page2.locator("#userEmail").isVisible();
    if (isLoginPage) {
      console.log("Still on login page - session restoration failed");

      // Debug: Check what storage data we have
      const currentStorage = await page2.evaluate(() => {
        const sessionData = {};
        for (let i = 0; i < sessionStorage.length; i++) {
          const key = sessionStorage.key(i);
          sessionData[key] = sessionStorage.getItem(key);
        }
        return sessionData;
      });
      console.log("Current sessionStorage:", currentStorage);
    }

    throw error;
  }

  // Optional: Verify specific sessionStorage key if you know what to look for
  const currentStorageData = await page2.evaluate(() => {
    const data = {};
    for (let i = 0; i < sessionStorage.length; i++) {
      const key = sessionStorage.key(i);
      data[key] = sessionStorage.getItem(key);
    }
    return data;
  });

  console.log("Final sessionStorage verification:", currentStorageData);

  await context2.close();
});
```

## Overview

This test demonstrates how to **save and restore browser session data** between different browser contexts in Playwright. This is useful for maintaining login states without having to re-authenticate in every test.

## Core Concept: Browser Contexts

- **Browser Context** = Isolated browser session (like incognito mode)
- Each context has its own cookies, localStorage, sessionStorage
- When you close a context, all session data is lost
- This test shows how to transfer session data between contexts

---

## Step-by-Step Breakdown

### 1. Initial Setup & Login

```javascript
const context1 = await browser.newContext();
const page1 = await context1.newPage();
await page1.goto("https://rahulshettyacademy.com/client");
```

**What happens:**

- Creates first browser context (fresh session)
- Opens new page in that context
- Navigates to the login page

### 2. Perform Authentication

```javascript
await page1.locator("#userEmail").fill("testaccountag@gmail.com");
await page1.locator("#userPassword").fill("Test@1234");
await page1.locator("[value='Login']").click();
```

**What happens:**

- Fills login form with credentials
- Clicks login button
- Server processes login and sets session data

### 3. Wait for Login Success

```javascript
await page1.waitForLoadState("networkidle");
await expect(page1.locator("text=Automation Practice")).toBeVisible({
  timeout: 10000,
});
await page1.waitForTimeout(2000);
```

**Why this is crucial:**

- `networkidle`: Ensures all network requests are complete
- Waits for specific UI element that confirms successful login
- Extra 2-second wait ensures all session data is fully set
- **Without proper waiting, session data might be incomplete**

### 4. Extract Session Data

```javascript
const storageData = await page1.evaluate(() => {
  const sessionData = {};
  const localData = {};

  // Get sessionStorage
  for (let i = 0; i < sessionStorage.length; i++) {
    const key = sessionStorage.key(i);
    sessionData[key] = sessionStorage.getItem(key);
  }

  // Get localStorage
  for (let i = 0; i < localStorage.length; i++) {
    const key = localStorage.key(i);
    localData[key] = localStorage.getItem(key);
  }

  return { sessionData, localData };
});
```

**Key points:**

- `page.evaluate()` runs JavaScript in the browser context
- Loops through **all** sessionStorage and localStorage keys
- Creates a complete snapshot of browser storage
- **Why both storages?** Different sites store auth data differently

### 5. Close First Context

```javascript
await context1.close();
```

**What happens:**

- Destroys the original browser context
- All cookies, storage, and session data are gone
- This simulates closing the browser completely

---

## The Magic: Session Restoration

### 6. Create New Context with Storage Injection

```javascript
const context2 = await browser.newContext();

await context2.addInitScript((data) => {
  // Restore sessionStorage
  for (const [key, value] of Object.entries(data.sessionData)) {
    if (value !== null) {
      sessionStorage.setItem(key, value);
    }
  }

  // Restore localStorage
  for (const [key, value] of Object.entries(data.localData)) {
    if (value !== null) {
      localStorage.setItem(key, value);
    }
  }
}, storageData);
```

**This is the core technique:**

- `addInitScript()` injects JavaScript that runs **before** any page loads
- The script restores all the saved storage data
- This happens **before** the website's JavaScript runs
- **Result:** The new context thinks it's the same logged-in session

### 7. Test the Restoration

```javascript
const page2 = await context2.newPage();
await page2.goto("https://rahulshettyacademy.com/client");
await page2.waitForLoadState("networkidle");
await page2.waitForTimeout(1000);

await expect(page2.locator("text=Automation Practice")).toBeVisible({
  timeout: 5000,
});
```

**What happens:**

- New page loads with pre-injected session data
- Website reads the session data and recognizes the user as logged in
- No login form appears - directly shows logged-in content
- Test verifies by checking for logged-in UI elements

---

## Why This Approach Works

### 1. **Timing is Everything**

- Waits ensure session data is completely set before extraction
- NetworkIdle ensures all auth-related requests are finished

### 2. **Complete Storage Capture**

- Gets both sessionStorage AND localStorage
- Iterates through ALL keys (no guessing which ones matter)
- Handles null values properly

### 3. **Pre-page Injection**

- `addInitScript` runs BEFORE page JavaScript
- Storage is available when the website checks for authentication
- No race conditions between page load and storage restoration

### 4. **Robust Error Handling**

- Extensive logging for debugging
- Proper timeouts and waits
- Graceful handling of edge cases

---

## Real-World Use Cases

### 1. **Test Suite Optimization**

```javascript
// Instead of logging in for every test:
test("Check user profile", async ({ page }) => {
  await loginSteps(); // Slow!
  // ... test profile functionality
});

test("Check orders", async ({ page }) => {
  await loginSteps(); // Slow again!
  // ... test orders functionality
});

// Use session restoration:
test.beforeAll(async () => {
  // Login once, save session
});

test("Check user profile", async ({ page }) => {
  // Restore session - Fast!
  // ... test profile functionality
});
```

### 2. **Multi-tab Testing**

```javascript
// Test features across multiple tabs with same session
const [page1, page2] = await Promise.all([
  context.newPage(),
  context.newPage(),
]);
// Both pages share the same restored session
```

### 3. **Cross-browser Session Testing**

```javascript
// Save session from Chrome, restore in Firefox
// Test session compatibility across browsers
```

---

## Common Pitfalls & Solutions

### ❌ **Problem: Session not restored**

```javascript
// Wrong - too fast
await page.goto(url);
const storage = await page.evaluate(() => sessionStorage);
```

### ✅ **Solution: Proper waiting**

```javascript
// Right - wait for complete load
await page.goto(url);
await page.waitForLoadState("networkidle");
await page.waitForTimeout(2000); // Extra safety
const storage = await page.evaluate(() => sessionStorage);
```

### ❌ **Problem: Missing storage data**

```javascript
// Wrong - only gets known keys
const token = await page.evaluate(() => sessionStorage.getItem("token"));
```

### ✅ **Solution: Get all storage**

```javascript
// Right - gets everything
const storage = await page.evaluate(() => {
  const data = {};
  for (let i = 0; i < sessionStorage.length; i++) {
    const key = sessionStorage.key(i);
    data[key] = sessionStorage.getItem(key);
  }
  return data;
});
```

---

## Advanced Patterns

### 1. **Reusable Session Manager**

```javascript
class SessionManager {
  static async saveSession(page) {
    await page.waitForLoadState("networkidle");
    return await page.evaluate(() => {
      // ... storage extraction logic
    });
  }

  static async restoreSession(context, sessionData) {
    await context.addInitScript((data) => {
      // ... storage restoration logic
    }, sessionData);
  }
}
```

### 2. **Conditional Session Restoration**

```javascript
// Only restore if session is still valid
if (sessionData.expiresAt > Date.now()) {
  await restoreSession(context, sessionData);
} else {
  await performFreshLogin();
}
```

Let's dive deeper into **Playwright's `page.route()`** method with **detailed explanation and examples**. This guide will show how to **intercept requests**, **mock responses**, and **inspect responses** using Playwright (JavaScript/Node.js).

---

## 📘 1. What is `page.route()`?

`page.route()` is used to **intercept network requests** before they are sent to the server. It gives you the ability to:

- Block requests (abort)
- Modify requests (headers, post data, etc.)
- Mock responses (fulfill)
- Continue as-is (pass through)

---

## 🧩 Method Signature

```js
page.route(url: string | RegExp | function, handler: function)
```

### Parameters:

- `url`: A string, RegExp, or a function to match the request URLs.
- `handler`: A function that gets a `route` object and a `request` object. You use this to decide what to do with the intercepted request.

---

## 🔧 2. Basic Example – Log Intercepted Requests

```js
const { chromium } = require("playwright");

(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();

  // Intercept all requests and log the URL
  await page.route("**/*", (route, request) => {
    console.log("Request:", request.method(), request.url());
    route.continue(); // Continue without changes
  });

  await page.goto("https://example.com");
  await browser.close();
})();
```

---

## 📦 3. Mocking a Network Response

You can use `route.fulfill()` to respond with your own data.

```js
await page.route("**/api/data", async (route, request) => {
  console.log("Intercepted API call:", request.url());

  await route.fulfill({
    status: 200,
    contentType: "application/json",
    body: JSON.stringify({ message: "This is mocked data!" }),
  });
});
```

✅ **Use case**: This is useful for testing UI behavior without needing a live backend.

---

## 🚫 4. Blocking or Aborting Requests

Abort images or analytics scripts to speed up tests:

```js
await page.route("**/*.{png,jpg,jpeg,svg}", (route) => route.abort());
await page.route("**/analytics/**", (route) => route.abort());
```

---

## ✏️ 5. Modify Request Data with `route.continue()`

You can change headers, POST data, or even the method.

```js
await page.route("**/submit", async (route, request) => {
  const overrides = {
    method: "POST",
    headers: {
      ...request.headers(),
      "X-Custom-Header": "MyValue",
    },
    postData: JSON.stringify({ forced: true }),
  };

  await route.continue(overrides);
});
```

---

## 👀 6. Inspecting Responses with `page.on('response')`

Although `route` handles **requests**, you can use `page.on('response')` to log or assert on **responses**:

```js
page.on("response", async (response) => {
  if (response.url().includes("/api/data")) {
    const json = await response.json();
    console.log("Received response from /api/data:", json);
  }
});
```

---

## 🎯 Full Example: Intercept, Mock, and Observe Response

```js
const { chromium } = require("playwright");

(async () => {
  const browser = await chromium.launch({ headless: false });
  const page = await browser.newPage();

  // Intercept API and return mock data
  await page.route("**/api/data", async (route, request) => {
    console.log(`Intercepted: ${request.method()} ${request.url()}`);

    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify({ data: "Mocked response here!" }),
    });
  });

  // Observe actual response from page
  page.on("response", async (response) => {
    if (response.url().includes("/api/data")) {
      const json = await response.json();
      console.log("Observed response:", json);
    }
  });

  await page.goto("https://your-app.example.com"); // Trigger request here
  await page.waitForTimeout(5000);

  await browser.close();
})();
```

---

## 🧠 Summary Table

| Action                     | Method                | Description                                 |
| -------------------------- | --------------------- | ------------------------------------------- |
| Intercept all requests     | `page.route('**/*')`  | Catch all requests                          |
| Block a request            | `route.abort()`       | Prevent request from reaching the server    |
| Continue with modification | `route.continue()`    | Modify method, headers, or body             |
| Mock a response            | `route.fulfill()`     | Fake a server response                      |
| Observe a response         | `page.on('response')` | Check the response content from the browser |

---

## Capture Screenshots

```js
test("Screenshot & Visual comparision", async ({ page }) => {
  await page.goto("https://rahulshettyacademy.com/AutomationPractice/");
  await expect(page.locator("#displayed-text")).toBeVisible();
  await page
    .locator("#displayed-text")
    .screenshot({ path: "partialScreenshot.png" });
  await page.locator("#hide-textbox").click();
  await page.screenshot({ path: "screenshot.png" });
  await expect(page.locator("#displayed-text")).toBeHidden();
});
// What is visual testing & How to perform it using Playwright
//screenshot -store -> screenshot ->
test("visual", async ({ page }) => {
  //make payment -when you 0 balance
  await page.goto("https://rahulshettyacademy.com/loginpagePractise/");
  expect(await page.screenshot()).toMatchSnapshot("landing.png");
});
```

## Strategy to handle download & uploading files using Playwright

```js
const ExcelJs = require("exceljs");
const { test, expect } = require("@playwright/test");

async function writeExcelTest(searchText, replaceText, change, filePath) {
  const workbook = new ExcelJs.Workbook();
  await workbook.xlsx.readFile(filePath);
  const worksheet = workbook.getWorksheet("Sheet1");
  const output = await readExcel(worksheet, searchText);

  const cell = worksheet.getCell(output.row, output.column + change.colChange);
  cell.value = replaceText;
  await workbook.xlsx.writeFile(filePath);
}

async function readExcel(worksheet, searchText) {
  let output = { row: -1, column: -1 };
  worksheet.eachRow((row, rowNumber) => {
    row.eachCell((cell, colNumber) => {
      if (cell.value === searchText) {
        output.row = rowNumber;
        output.column = colNumber;
      }
    });
  });
  return output;
}
//update Mango Price to 350.
//writeExcelTest("Mango",350,{rowChange:0,colChange:2},"/Users/rahulshetty/downloads/excelTest.xlsx");
test("Upload download excel validation", async ({ page }) => {
  const textSearch = "Mango";
  const updateValue = "350";
  await page.goto(
    "https://rahulshettyacademy.com/upload-download-test/index.html"
  );
  const downloadPromise = page.waitForEvent("download");
  await page.getByRole("button", { name: "Download" }).click();
  await downloadPromise;
  writeExcelTest(
    textSearch,
    updateValue,
    { rowChange: 0, colChange: 2 },
    "/Users/rahulshetty/downloads/download.xlsx"
  );
  await page.locator("#fileinput").click();
  await page
    .locator("#fileinput")
    .setInputFiles("/Users/rahulshetty/downloads/download.xlsx");
  const textlocator = page.getByText(textSearch);
  const desiredRow = await page.getByRole("row").filter({ has: textlocator });
  await expect(desiredRow.locator("#cell-4-undefined")).toContainText(
    updateValue
  );
});
```

## Drive data from external JSON files

To drive data from external JSON files into your Playwright tests using JavaScript, you can follow these steps:

---

### ✅ Step-by-Step Guide

#### 1. **Create a JSON File**

Let’s say you have a file named `testData.json`:

```json
{
  "loginTest": {
    "username": "testuser",
    "password": "securePass123"
  },
  "searchTest": {
    "query": "Playwright"
  }
}
```

---

#### 2. **Import JSON Data in Your Test**

You can import JSON data using Node.js `require()` or `fs` module:

##### Option A: Using `require()`

```js
const testData = require("./testData.json");
```

##### Option B: Using `fs` module (more flexible for dynamic paths)

```js
const fs = require("fs");

const rawData = fs.readFileSync("./testData.json");
const testData = JSON.parse(rawData);
```

---

#### 3. **Use the Data in a Playwright Test**

Here’s how you use it in a typical Playwright test file (`test.spec.js`):

```js
const { test, expect } = require("@playwright/test");
const testData = require("./testData.json");

test("Login Test using JSON data", async ({ page }) => {
  await page.goto("https://example.com/login");

  await page.fill("#username", testData.loginTest.username);
  await page.fill("#password", testData.loginTest.password);
  await page.click("#submit");

  await expect(page.locator("h1")).toContainText("Welcome");
});
```

---

### 🔁 Optional: Data-Driven Tests (Loop through JSON)

If you want to run multiple tests using different sets of data from the JSON:

```json
[
  { "username": "user1", "password": "pass1" },
  { "username": "user2", "password": "pass2" }
]
```

Then in your test:

```js
const { test, expect } = require("@playwright/test");
const users = require("./users.json");

for (const user of users) {
  test(`Login Test for ${user.username}`, async ({ page }) => {
    await page.goto("https://example.com/login");
    await page.fill("#username", user.username);
    await page.fill("#password", user.password);
    await page.click("#submit");

    await expect(page.locator("h1")).toContainText("Welcome");
  });
}
```

---

### ✅ Summary

- Store data in a `.json` file.
- Use `require()` or `fs` to load it in your test.
- Use that data directly or in a loop for data-driven tests.

## pass test data as a fixture by extending the `test`

---

## 🧪 Goal

**Pass test data (like user credentials or config) as a fixture** so it can be injected automatically into any Playwright test. This makes your tests more **modular**, **clean**, and **dynamic**.

---

## ✅ Step-by-Step: How to Pass Test Data as a Fixture

### 1. **Extend the default `test` to add a `testData` fixture**

Create a custom fixture file like this:

```js
// tests/fixtures/testWithData.js
const { test as base } = require('@playwright/test');

// Extend the base test with custom test data fixture
exports.test = base.extend({
  testData: async ({}, use) => {
    const data = {
      username: 'user1',
      password: 'pass123',
      role: 'admin'
    };
    await use(data);
  }
});
```

---

### 2. **Use the custom `test` in your test file**

Now, import the custom `test` from your fixture:

```js
// tests/example.spec.js
const { test } = require("./fixtures/testWithData");

test("Login with test data", async ({ page, testData }) => {
  await page.goto("https://example.com/login");
  await page.fill("#username", testData.username);
  await page.fill("#password", testData.password);
  await page.click("#submit");

  // Example assertion
  await page.waitForSelector("#dashboard");
});
```

---

### 3. **Override fixture for specific tests using `test.use()`**

You can customize the data for specific test cases:

```js
test.use({
  testData: { username: "admin", password: "admin123", role: "superadmin" },
});

test("Admin login", async ({ page, testData }) => {
  await page.goto("https://example.com/login");
  await page.fill("#username", testData.username);
  await page.fill("#password", testData.password);
  await page.click("#submit");
});
```

---

## 🎯 Why Use Fixtures Like This?

| 🔧 Feature            | ✅ Benefit                            |
| --------------------- | ------------------------------------- |
| `test.extend()`       | Add custom fixtures like test data    |
| `test.use()`          | Override fixture per test dynamically |
| Shared test setup     | Avoid duplication, keep tests clean   |
| Async fixture support | Fetch from DB/API before test runs    |
| Automatic teardown    | Clean up (e.g., close DB connections) |

---

## 🧠 Real-World Use Cases

1. **Login Tests with Different Roles**

   - Inject credentials based on role (`admin`, `user`, `guest`).

2. **Environment Config**

   - Load base URL or API keys based on environment (`dev`, `qa`, `prod`).

3. **Mock Data Setup**

   - Generate or fetch dynamic data for isolated test scenarios.

4. **Data-Driven Testing**

   - Iterate over different test data using `.each()` or custom logic.

---

## 🧪 Bonus: Using Test Metadata (Optional)

You can access test metadata inside your test via `testInfo`:

```js
test("Check metadata", async ({ testData }, testInfo) => {
  console.log(testInfo.title); // Test title
  console.log(testData.role); // Fixture data
});
```

---

## ✅ Summary

- Create a **fixture file** using `test.extend()`.
- Reuse `testData` across tests without duplicating code.
- Customize test data with `test.use()` for specific cases.
- Makes your tests **cleaner**, **modular**, and **scalable**.

---

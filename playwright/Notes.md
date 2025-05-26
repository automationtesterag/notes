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

To type into an element, use the `fill()` method (preferred for inputs) or `type()` (simulates key-by-key typing). Ensure the element is visible and enabled.

### Steps:

1. Identify the element using a locator
2. Use `fill()` for quick input or `type()` for slower, user-like typing
3. Optionally, clear the field first with `clear()`

### Example:

```javascript
await page.locator("#username").fill("john_doe"); // Fast input
await page.locator("#password").type("secret123", { delay: 100 }); // Slow typing with 100ms delay
await page.locator("#username").clear(); // Clear field before typing
```

### Tips:

- Use `fill()` for most input fields (e.g., `<input>`, `<textarea>`)
- Use `type()` when simulating real user behavior is needed
- Ensure the element is interactable using `waitFor()` if necessary

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

If you need specific code examples for any of these scenarios or have a particular use case (e.g., a specific framework or page structure), let me know, and I can tailor the response further!

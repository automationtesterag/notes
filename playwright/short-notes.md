# Playwright short notes

# Table of Contents
1. [What is Playwright?](#what-is-playwright)
2. [Why use Playwright?](#why-use-playwright)
3. [Playwright vs Selenium](#playwright-vs-selenium)
4. [Install Playwright](#install-playwright)
5. [Playwright Test Fixtures: page and browserContext](#playwright-test-fixtures-page-and-browsercontext)
6. [What is the page Fixture?](#what-is-the-page-fixture)
7. [What is browserContext?](#what-is-browsercontext)
8. [PlayWright Config guide](#playwright-config-guide)
9. [Locator types in Playwright](#locator-types-in-playwright)
10. [Text Entry Methods](#text-entry-methods)
11. [Extracting Text from Browser and Inserting Valid Expect Assertions](#extracting-text-from-browser-and-inserting-valid-expect-assertions)
12. [Working with Locators that Extract Multiple Web Elements](#working-with-locators-that-extract-multiple-web-elements)


# What is Playwright?

Playwright is an open-source browser automation framework developed by Microsoft for end-to-end testing of web applications.

It supports:
* ✅ Chromium (Chrome, Edge)
* ✅ Firefox
* ✅ WebKit (Safari)
* ✅ Windows, macOS, and Linux
* ✅ JavaScript, TypeScript, Python, Java, and C#

---

# Why use Playwright?

* Fast and reliable
* Auto-waits for elements (less flaky tests)
* Built-in parallel execution
* Network interception and API testing
* Multiple browser support
* Mobile device emulation
* Screenshots and video recording
* Trace Viewer for debugging

# Playwright vs Selenium

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

# Install Playwright

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
```


# Playwright Test Fixtures: `page` and `browserContext`

Playwright’s **test runner** comes with powerful **built-in fixtures** that handle a lot of browser setup for you. Two of the most important ones are:
1. `page` Fixture
2. `browserContext` Fixture


# What is the `page` Fixture?

The `page` fixture provides a **single browser tab (or window)** that is automatically created for you in each test.

Think of it as a **ready-to-use page instance** inside an isolated browser environment.

#### ✨ Key Points:

- Automatically managed by Playwright Test.
- No need to manually create or close it.
- Best for **simple, single-user tests**.
- Internally, each test gets its own browser context and a page, ensuring isolation.

---

## Example: Using `page` Fixture in JavaScript

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
---

# What is `browserContext`?

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

---

## 🧠 When to Use What?

| Use Case                                   | Use `page` Fixture | Use `browserContext` |
| ------------------------------------------ | ------------------ | -------------------- |
| Simple UI test                             | ✅ Yes             | ❌ Not needed        |
| Needs session or cookie isolation          | ❌ No              | ✅ Yes               |
| Testing multiple user accounts             | ❌ No              | ✅ Yes               |
| Simulating concurrent sessions in one test | ❌ No              | ✅ Yes               |
| Fast setup with automatic context/page     | ✅ Yes             | ❌ No (manual setup) |

# PlayWright Config guide

[Guide](../playwright/playwright-config-guide.md)

# Locator types in Playwright

- **page.getByRole()** – Locates elements by their ARIA role (e.g., `button`, `link`, `checkbox`, `heading`), optionally with name, level, and other accessible attributes.
- **page.getByText()** – Locates elements by their visible text content (supports exact match, substring, or regex).
- **page.getByLabel()** – Locates form elements (input, textarea, select) by their associated `<label>` text.
- **page.getByPlaceholder()** – Locates input elements by their `placeholder` attribute.
- **page.getByAltText()** – Locates elements (usually images) by their `alt` attribute.
- **page.getByTitle()** – Locates elements by their `title` attribute.
- **page.getByTestId()** – Locates elements by a test-specific attribute, typically `data-testid` (configurable).
- **page.locator()** – Generic locator using CSS or XPath selectors.
  - CSS selector: `page.locator('button.submit')`
  - XPath selector: `page.locator('//button[@type="submit"]')`
  - Text pseudo-class: `page.locator('text=Submit')`
- **page.frameLocator()** – Locates elements inside an iframe.
- **Chained locators** – Combining locators for nested/scoped searches (e.g., `page.locator('.card').getByRole('button')`).
- **Filtering locators**:
  - `.filter({ hasText: 'text' })` – Filter by text content.
  - `.filter({ has: locator })` – Filter by presence of a sub-locator.
  - `.filter({ hasNot: locator })` – Filter by absence of a sub-locator.
  - `.filter({ hasNotText: 'text' })` – Filter by absence of text.
- **Locator methods for multiple elements**:
  - `.first()` – First matching element.
  - `.last()` – Last matching element.
  - `.nth(index)` – Element at a specific index.
- **page.locator('css=...')** – Explicit CSS engine selector.
- **page.locator('xpath=...')** – Explicit XPath engine selector.
- **and()/or() locator combinators**:
  - `.and(locator)` – Matches elements satisfying both locators.
  - `.or(locator)` – Matches elements satisfying either locator.

### Recommended priority (per Playwright docs)
1. `getByRole()`
2. `getByLabel()`
3. `getByPlaceholder()`
4. `getByText()`
5. `getByAltText()`
6. `getByTitle()`
7. `getByTestId()` (as a fallback when semantic locators aren't feasible)

## Text Entry Methods

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

### ✅ `page.type(selector, text[, options])` (deprecated)

- Types one character at a time.

```ts
await page.type("#username", "myUser", { delay: 100 });
```

### ✅ `locator.type(text[, options])` (deprecated)

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
await page.keyboard.press("ArrowLeft");
await page.keyboard.press("ArrowRight");
await page.keyboard.press("Home");
await page.keyboard.press("End");
```


### `pressSequentially()` in Playwright: Types text one character at a time, like a real user.

* **Syntax:**

  ```typescript
  await page.locator(selector).pressSequentially("text");
  ```

* **With Delay:**

  ```typescript
  await page.locator(selector).pressSequentially("text", { delay: 150 });
  ```

## 💡 Tips

- Always `click()` or `focus()` before using `keyboard` if the input isn't already active.
- Use `insertText` for large text pastes or avoiding typing delays.
- Prefer `locator.fill()` over `page.fill()` for better stability in modern frameworks.

---

# Extracting Text from Browser and Inserting Valid Expect Assertions

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

# `inputValue()` in Playwright

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
---
# Working with Locators that Extract Multiple Web Elements
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

## Understanding Wait Mechanism for Lists of Elements

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

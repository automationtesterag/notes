# Playwright short notes

# Table of Contents
1. [What is Playwright?](#what-is-playwright)
2. [Why use Playwright?](#why-use-playwright)
3. [Playwright vs Selenium](#playwright-vs-selenium)
4. [Install Playwright](#install-playwright)
5. [Playwright Test Fixtures: page and browserContext](#playwright-test-fixtures-page-and-browsercontext)
6. [What is the page Fixture?](#what-is-the-page-fixture)
7. [What is browserContext?](#what-is-browsercontext)


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



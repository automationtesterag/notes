```javascript
import { test, expect, request } from "@playwright/test";

test("Playwright Web Actions - Complete Reference", async ({
  page,
  context,
}) => {
  // ============================================================
  // 1. PAGE NAVIGATION ACTIONS
  // ============================================================
  /**
   * Navigation methods move the page between URLs/history entries and
   * let you wait for the browser to reach a known-good load state before
   * continuing, instead of guessing with a fixed timeout.
   */

  // Navigate to a URL. Resolves once Playwright's default load state
  // ("load") is reached, or the `waitUntil` option below overrides it.
  await page.goto("https://example.com");

  // Navigate and resolve as soon as the DOM is parsed (don't wait for
  // images/stylesheets) — faster when you only need DOM-ready state.
  await page.goto("https://example.com", { waitUntil: "domcontentloaded" });

  // Navigate and resolve on the full `load` event (all resources fetched).
  await page.goto("https://example.com", { waitUntil: "load" });

  // Navigate back one entry in the browser's session history.
  await page.goBack();

  // Navigate forward one entry in the browser's session history.
  await page.goForward();

  // Reload the current document (equivalent to hitting the refresh button).
  await page.reload();

  // Block until the page's URL matches the given glob/regex — useful after
  // an action that triggers a client-side or server-side redirect.
  await page.waitForURL("**/dashboard");

  // Wait for a specific lifecycle event ("load" | "domcontentloaded" | "networkidle").
  await page.waitForLoadState("load");
  await page.waitForLoadState("domcontentloaded");
  await page.waitForLoadState("networkidle"); // no network activity for 500ms

  // Read-only getters for the current page state.
  console.log(page.url()); // current URL as a string
  console.log(await page.title()); // <title> contents
  console.log(await page.content()); // full serialized HTML of the page

  // ============================================================
  // 2. LOCATOR CREATION AND SELECTION
  // ============================================================
  /**
   * Locators are lazy, auto-retrying references to DOM elements. They are
   * NOT resolved until an action/assertion is performed on them, and
   * every action automatically waits for the element to be actionable.
   * Prefer role/label/text-based locators (accessibility-first) over raw
   * CSS/XPath, since they're less brittle to markup changes.
   */

  const button = page.locator("#login"); // raw CSS selector
  const username = page.locator('//input[@name="username"]'); // raw XPath

  const products = page.getByText("Products"); // match visible text content
  const loginButton = page.getByRole("button", { name: "Login" }); // ARIA role + accessible name
  const email = page.getByLabel("Email"); // matches an associated <label>
  const password = page.getByPlaceholder("Enter password"); // matches placeholder attribute
  const submit = page.getByTestId("submit-button"); // matches data-testid (configurable)
  const help = page.getByTitle("Help"); // matches title attribute

  // Locate by alt text (typically images) — was missing from the original list.
  const logo = page.getByAltText("Company logo");

  // Positional selection when a locator matches multiple elements.
  await page.locator(".item").first().click(); // first match
  await page.locator(".item").last().click(); // last match
  await page.locator(".item").nth(2).click(); // zero-based index

  // Narrow a locator to only elements containing given text.
  await page.locator(".card").filter({ hasText: "Laptop" }).click();

  // Narrow a locator to only elements that contain a matching child locator.
  await page
    .locator(".card")
    .filter({ has: page.getByRole("button", { name: "Buy" }) })
    .click();

  // Traverse to a parent element via XPath ".." (use sparingly — brittle).
  await page.getByText("Laptop").locator("..").click();

  // Chain locators to scope a search to descendants of a parent locator.
  await page.locator(".card").locator("button").click();

  // Combine locators: `.and()` requires BOTH conditions to match the same element.
  await page.getByRole("listitem").and(page.getByText("Laptop")).click();

  // Combine locators: `.or()` matches whichever locator is found first.
  const loginOrSignup = page
    .getByRole("button", { name: "Login" })
    .or(page.getByRole("button", { name: "Sign up" }));

  // Bulk retrieval helpers — resolve immediately against current DOM state
  // (no auto-waiting/retrying), so use after confirming elements exist.
  const items = await page.locator(".item").all(); // array of Locators, one per match
  const allTexts = await page.locator(".item").allTextContents(); // textContent of every match
  const innerTexts = await page.locator(".item").allInnerTexts(); // innerText of every match
  const count = await page.locator(".item").count(); // number of matches

  // ============================================================
  // 3. MOUSE ACTIONS
  // ============================================================
  /**
   * High-level locator actions (click/hover/etc.) auto-scroll the element
   * into view and wait for it to be visible, stable, and not obscured.
   * `page.mouse` gives raw, low-level control by viewport coordinates.
   */

  await page.getByRole("button", { name: "Login" }).click();
  await page.getByText("Open File").dblclick();
  await page.locator("#menu").click({ button: "right" }); // right-click / context menu
  await page.locator("#link").click({ button: "middle" }); // middle-click
  await page.locator("#link").click({ modifiers: ["Control"] }); // Ctrl+click (e.g. open in new tab)
  await page.getByText("Products").hover(); // trigger CSS :hover / dropdown menus
  await page.locator("#username").focus(); // programmatically focus without clicking
  await page.locator("#username").blur(); // remove focus (fires blur/change events)
  await page.locator("#source").dragTo(page.locator("#target")); // drag-and-drop between elements

  await page.mouse.click(100, 200); // click at absolute viewport coordinates
  await page.mouse.move(100, 200); // move pointer without clicking (for hover effects)
  await page.mouse.down(); // press and hold the mouse button
  await page.mouse.up(); // release the mouse button (pair with .down() for custom drags)
  await page.mouse.wheel(0, 500); // scroll wheel delta (deltaX, deltaY)

  // ============================================================
  // 4. TEXT INPUT ACTIONS
  // ============================================================
  /**
   * `fill()` sets a value directly (fast, fires input/change events) and
   * is preferred for most cases. `pressSequentially()` types character by
   * character (slower, triggers per-keystroke handlers) — use it for
   * masked inputs, autocomplete, or keydown-driven validation.
   */

  await page.locator("#username").fill("admin"); // set value directly
  await page.locator("#username").clear(); // clear the field's value
  await page.locator("#username").pressSequentially("admin"); // type key-by-key
  await page.locator("#username").press("Enter"); // press a single key while focused
  await page.locator("#username").press("Tab");
  await page.locator("#username").press("Backspace");
  await page.locator("#username").press("Escape");

  await page.keyboard.type("Hello World"); // type into whatever is currently focused
  await page.keyboard.insertText("Hello World"); // insert text without per-key events (IME-safe)
  await page.keyboard.press("Enter"); // press a key globally (not locator-scoped)
  await page.keyboard.down("Shift"); // hold a modifier key down
  await page.keyboard.up("Shift"); // release a held modifier key

  // Keyboard shortcuts use "+" to combine modifiers with a key.
  await page.keyboard.press("Control+A");
  await page.keyboard.press("Control+C");
  await page.keyboard.press("Control+V");
  await page.keyboard.press("Control+X");

  // Select all text within an input/textarea — was missing.
  await page.locator("#username").selectText();

  // ============================================================
  // 5. CHECKBOX AND RADIO BUTTON ACTIONS
  // ============================================================
  /**
   * `check()`/`uncheck()` are idempotent no-ops if the element is already
   * in the target state, and throw if used on a non-checkbox/radio input.
   */

  await page.getByLabel("Accept terms").check(); // ensure checked
  await page.getByLabel("Accept terms").uncheck(); // ensure unchecked
  await page.getByLabel("Accept terms").setChecked(true); // same as check()
  await page.getByLabel("Accept terms").setChecked(false); // same as uncheck()
  await page.getByLabel("Male").check(); // select a radio button

  // ============================================================
  // 6. DROPDOWN ACTIONS
  // ============================================================
  /** For native <select> elements. For custom JS dropdowns, use click() + getByRole("option"). */

  await page.locator("#country").selectOption("IN"); // by option value
  await page.locator("#country").selectOption({ label: "India" }); // by visible label text
  await page.locator("#country").selectOption({ index: 1 }); // by zero-based index
  await page.locator("#country").selectOption(["IN", "US"]); // multiple selection (multi-select only)
  await page.locator("#country").selectOption({ value: "IN" }); // explicit value object form

  // ============================================================
  // 7. FILE UPLOAD ACTIONS
  // ============================================================
  /** Works even if the <input type="file"> is visually hidden — no OS file dialog needed. */

  await page
    .locator('input[type="file"]')
    .setInputFiles("tests/data/sample.pdf");
  await page
    .locator('input[type="file"]')
    .setInputFiles(["tests/data/file1.txt", "tests/data/file2.txt"]); // multiple files
  await page.locator('input[type="file"]').setInputFiles([]); // clear selection

  // ============================================================
  // 8. SCROLL ACTIONS
  // ============================================================

  await page.getByText("Footer").scrollIntoViewIfNeeded(); // scroll only if not already visible
  await page.mouse.wheel(0, 1000); // scroll down
  await page.mouse.wheel(0, -1000); // scroll up
  await page.evaluate(() => {
    // Direct DOM scrolling via injected JS, when wheel emulation isn't enough.
    window.scrollTo(0, document.body.scrollHeight);
  });

  // ============================================================
  // 9. FRAME / IFRAME ACTIONS
  // ============================================================

  // Single iframe
  const frame = page.frameLocator("#payment-frame");

  await frame.getByLabel("Card number").fill("4111111111111111");
  await frame.getByRole("button", { name: "Pay" }).click();

  // Nested iframes
  const nestedFrame = page.frameLocator("#frame1").frameLocator("#frame2");

  await nestedFrame.getByRole("button", { name: "Submit" }).click();

  // Frame object
  const namedFrame = page.frame({ name: "payment-frame" });
  const urlFrame = page.frame({ url: /payment/ });

  if (namedFrame) {
    console.log(namedFrame.url());
  }

  // Remember:
  // frameLocator() → interact with elements
  // page.frame()   → get Frame object
  // Nested frame  → chain frameLocator()

  // ============================================================
  // 10. DIALOG ACTIONS
  // ============================================================

  // Handle alert / confirm / prompt
  page.on("dialog", async (dialog) => {
    console.log(dialog.type()); // alert | confirm | prompt
    console.log(dialog.message()); // dialog text

    await dialog.accept(); // OK
    // await dialog.dismiss();    // Cancel
    // await dialog.accept("Hello"); // Prompt input
  });

  // Remember:
  // dialog.accept()  → OK
  // dialog.dismiss() → Cancel
  // dialog.message() → Get text
  // dialog.type()    → Get dialog type

  // ============================================================
  // 11. PAGE / BROWSER ACTIONS
  // ============================================================

  // New tab
  const newPage = await context.newPage();
  await newPage.goto("https://example.com");
  await newPage.bringToFront();

  // Screenshots
  await newPage.screenshot({ path: "screenshot.png" });
  await newPage.screenshot({ path: "fullpage.png", fullPage: true });

  await newPage.close();

  // Viewport
  await page.setViewportSize({ width: 1280, height: 720 });
  console.log(page.viewportSize());

  // Execute JavaScript
  const title = await page.evaluate(() => document.title);
  console.log(title);

  // Get element text using JavaScript
  const textt = await page.locator("h1").evaluate((el) => el.textContent);
  console.log(textt);

  // Pass data to JavaScript
  await page.evaluate((name) => {
    document.title = `Welcome ${name}`;
  }, "Anudeep");

  // Remember:
  // context.newPage()     → new tab
  // bringToFront()        → focus tab
  // screenshot()          → screenshot
  // fullPage: true        → full page
  // setViewportSize()     → resize
  // viewportSize()        → get size
  // page.evaluate()       → run JS on page
  // locator.evaluate()    → run JS on element

  // ============================================================
  // 12. ELEMENT INFORMATION / GETTERS
  // ============================================================

  const element = page.locator("#message");

  // Text
  console.log(await element.innerText()); // visible text
  console.log(await element.textContent()); // raw text
  console.log(await page.locator(".item").allInnerTexts());

  // Input / Attribute
  console.log(await page.locator("#username").inputValue());
  console.log(await page.locator("a").getAttribute("href"));

  // HTML
  console.log(await element.innerHTML());
  console.log(await element.evaluate((el) => el.outerHTML));

  // Element state
  console.log(await element.isVisible());
  console.log(await element.isHidden());
  console.log(await element.isEnabled());
  console.log(await element.isDisabled());
  console.log(await page.locator("#checkbox").isChecked());
  console.log(await page.locator("#username").isEditable());

  // Position / size
  console.log(await page.locator("#button").boundingBox());

  // Attached to DOM
  const isAttached = await page
    .locator("#button")
    .evaluate((el) => el.isConnected);

  console.log(isAttached);

  // In viewport
  await expect(page.locator("#button")).toBeInViewport();

  // Remember:
  // innerText()       → visible text
  // textContent()     → raw text
  // inputValue()      → input value
  // getAttribute()    → attribute
  // innerHTML()       → inside HTML
  // evaluate()        → custom JS / outerHTML
  // boundingBox()     → position + size
  // isVisible()       → visible?
  // isEnabled()       → enabled?
  // isChecked()       → checkbox/radio checked?
  // isEditable()      → editable?
  // isConnected       → attached to DOM
  // toBeInViewport()  → inside viewport

  // ============================================================
  // 13. ASSERTIONS
  // ============================================================

  // Element state
  await expect(page.getByText("Welcome")).toBeVisible();
  await expect(page.getByText("Loading")).toBeHidden();
  await expect(page.getByRole("button")).toBeEnabled();
  await expect(page.getByRole("button")).toBeDisabled();
  await expect(page.getByLabel("Accept terms")).toBeChecked();

  // Text / Value
  await expect(page.locator("#message")).toHaveText("Login successful");
  await expect(page.locator("#message")).toContainText("successful");
  await expect(page.locator("#username")).toHaveValue("admin");

  // Attribute / Class
  await expect(page.locator("#link")).toHaveAttribute("href", "/dashboard");

  await expect(page.locator("#button")).toHaveClass(/btn-primary/);

  // Other element checks
  await expect(page.locator("#username")).toBeEditable();
  await expect(page.locator("#username")).toBeEmpty();
  await expect(page.locator("#username")).toBeFocused();
  await expect(page.locator("#button")).toBeInViewport();
  await expect(page.locator(".item")).toHaveCount(5);

  // Page checks
  await expect(page).toHaveURL("**/dashboard");
  await expect(page).toHaveTitle("Dashboard");

  // CSS
  await expect(page.locator("#button")).toHaveCSS(
    "color",
    "rgb(255, 255, 255)",
  );

  // Screenshot
  await expect(page).toHaveScreenshot("homepage.png");

  // Soft assertion → test continues after failure
  await expect.soft(page.getByText("Optional banner")).toBeVisible();

  // Retry custom code until it passes
  await expect(async () => {
    const response = await page.request.get("/api/status");
    expect(response.status()).toBe(200);
  }).toPass();

  // Remember:
  // toBeVisible()       → visible
  // toBeHidden()        → hidden
  // toBeEnabled()       → enabled
  // toBeChecked()       → checked
  // toHaveText()        → exact text
  // toContainText()     → partial text
  // toHaveValue()       → input value
  // toHaveAttribute()   → attribute
  // toHaveClass()       → class
  // toHaveCount()       → count
  // toHaveURL()         → URL
  // toHaveTitle()       → title
  // expect.soft()       → continue after failure
  // toPass()            → retry custom code

  // ============================================================
  // 14. WAIT / DELAY ACTIONS
  // ============================================================

  // Element waits
  await page.locator("#message").waitFor({ state: "visible" });
  await page.locator("#loader").waitFor({ state: "hidden" });
  await page.locator("#message").waitFor({ state: "attached" });
  await page.locator("#loader").waitFor({ state: "detached" });

  // Page waits
  await page.waitForURL("**/dashboard");
  await page.waitForLoadState("domcontentloaded");
  await page.waitForLoadState("networkidle");

  // Custom condition
  await page.waitForFunction(() => document.title === "Dashboard");

  // Network waits
  const response = await page.waitForResponse((response) =>
    response.url().includes("/api/login"),
  );

  const request = await page.waitForRequest((request) =>
    request.url().includes("/api/login"),
  );

  // Poll until condition is met
  await expect
    .poll(async () => await page.locator(".item").count())
    .toBeGreaterThan(0);

  // Fixed delay — use only when necessary
  await page.waitForTimeout(1000);

  // ============================================================
  // DELAY OPTIONS
  // ============================================================

  // Delay between actions
  await page.waitForTimeout(500);
  await page.click("#button");
  await page.waitForTimeout(1000);

  // Delay before an action using Promise
  await new Promise((resolve) => setTimeout(resolve, 1000));

  // Remember:
  // waitFor()             → element state
  // waitForURL()          → URL
  // waitForLoadState()    → page loading
  // waitForFunction()     → custom condition
  // waitForResponse()     → API response
  // waitForRequest()      → API request
  // expect.poll()         → poll/retry
  // waitForTimeout()      → fixed delay
  // setTimeout()          → JavaScript delay
  //
  // Prefer condition-based waits over fixed delays.

  // ============================================================
  // 15. POPUP / NEW TAB ACTIONS
  // ============================================================

  const [popup] = await Promise.all([
    page.waitForEvent("popup"),
    page.getByText("Open New Tab").click(),
  ]);

  await popup.waitForLoadState();
  console.log(popup.url());
  await popup.close();

  // ============================================================
  // 16. TOUCH ACTIONS
  // ============================================================

  await page.getByRole("button", { name: "Menu" }).tap();

  // 17 removed

  // ============================================================
  // 18. SCREENSHOT / PDF
  // ============================================================

  // Element screenshot
  await page.locator("#header").screenshot({
    path: "header.png",
  });

  // Full page screenshot
  await page.screenshot({
    path: "page.png",
    fullPage: true,
  });

  // PDF — Chromium + headless only
  await page.pdf({
    path: "page.pdf",
    format: "A4",
  });

  // ============================================================
  // 19. DOWNLOAD ACTIONS
  // ============================================================

  // Wait for download + trigger download
  const [download] = await Promise.all([
    page.waitForEvent("download"),
    page.getByText("Download").click(),
  ]);

  // Get filename
  console.log(download.suggestedFilename());

  // Save to custom location
  await download.saveAs("downloads/file.pdf");

  // Check download failure
  console.log(await download.failure());

  // Get temporary download path
  console.log(await download.path());

  // Cancel download
  // await download.cancel();

  // Delete downloaded file
  // await download.delete();

  // Remember:
  // popup → waitForEvent("popup")
  // tap() → touch action
  // screenshot() → screenshot
  // pdf() → PDF
  //
  // download:
  // waitForEvent("download") → wait for download
  // suggestedFilename()      → filename
  // saveAs()                 → save file
  // path()                   → temporary path
  // failure()                → check failure
  // cancel()                 → cancel download
  // delete()                 → delete download

  // ============================================================
  // 20. BROWSER CONTEXT ACTIONS
  // ============================================================

  const context = await browser.newContext();
  const page = await context.newPage();

  // Cookies
  await context.addCookies([
    {
      name: "session",
      value: "abc123",
      domain: "example.com",
      path: "/",
    },
  ]);

  console.log(await context.cookies());
  await context.clearCookies();

  // Permissions
  await context.grantPermissions(["geolocation"]);
  await context.clearPermissions();

  // Headers
  await context.setExtraHTTPHeaders({
    Authorization: "Bearer token",
  });

  // Save authentication state
  await context.storageState({
    path: "auth.json",
  });

  // Close context
  await context.close();

  // Remember:
  // browser.newContext()     → isolated session
  // context.newPage()        → new tab
  // addCookies()             → add cookies
  // cookies()                → get cookies
  // clearCookies()           → remove cookies
  // grantPermissions()       → allow permissions
  // clearPermissions()       → remove permissions
  // setExtraHTTPHeaders()    → add headers
  // storageState()           → save auth state
  // close()                  → close context

  // ============================================================
  // 21. NETWORK ACTIONS
  // ============================================================

  // Continue request
  await page.route("**/api/users", (route) => route.continue());

  // Block request
  await page.route("**/*.png", (route) => route.abort());

  // Mock API response
  await page.route("**/api/users", (route) =>
    route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify({ users: [] }),
    }),
  );

  // Remove route
  await page.unroute("**/api/users");

  // Log requests / responses
  page.on("request", (request) => console.log("Request:", request.url()));
  page.on("response", (response) => console.log("Response:", response.url()));

  // Replay network traffic
  await context.routeFromHAR("tests/data/traffic.har", {
    url: "**/api/**",
  });

  // Offline / Online
  await context.setOffline(true); // Offline
  await context.setOffline(false); // Online

  // Remember:
  // route()          → intercept request
  // continue()       → allow request
  // abort()          → block request
  // fulfill()        → mock response
  // unroute()        → remove route
  // request          → monitor requests
  // response         → monitor responses
  // routeFromHAR()   → replay API traffic
  // setOffline()     → simulate offline

  // ============================================================
  // 22. CLIPBOARD ACTIONS
  // ============================================================

  // Grant clipboard permission
  await context.grantPermissions(["clipboard-read", "clipboard-write"]);

  // Read clipboard
  const text = await page.evaluate(() => navigator.clipboard.readText());
  console.log(text);

  // Write to clipboard
  await page.evaluate(() => navigator.clipboard.writeText("Hello"));

  // ============================================================
  // 23. TRACING ACTIONS
  // ============================================================

  // Start tracing
  await context.tracing.start({
    screenshots: true,
    snapshots: true,
    sources: true,
  });

  // Test actions...

  // Stop and save trace
  await context.tracing.stop({
    path: "trace.zip",
  });

  // Remember:
  // Clipboard:
  // grantPermissions() → allow clipboard
  // readText()        → read
  // writeText()       → write
  //
  // Tracing:
  // tracing.start() → start recording
  // tracing.stop()  → save trace
  // trace.zip       → debug with Trace Viewer
  // ============================================================
  // ============================================================
// 24. API REQUEST ACTIONS
// ============================================================

// Generic syntax:
// const api = await request.newContext({ baseURL: "BASE_URL" });
// const response = await api.get("/endpoint");
// console.log(response.status());
// console.log(await response.json());
// await api.dispose();


// Real practice API: JSONPlaceholder
const api = await request.newContext({
  baseURL: "https://jsonplaceholder.typicode.com",
});


// ============================================================
// GET
// ============================================================

const getResponse = await api.get("/users/1");

expect(getResponse.status()).toBe(200);

const user = await getResponse.json();

expect(user.id).toBe(1);
expect(user.name).toBeTruthy();
expect(user.email).toContain("@");


// ============================================================
// POST
// ============================================================

const postResponse = await api.post("/users", {
  data: {
    name: "Anudeep",
    email: "anudeep@example.com",
  },
});

expect(postResponse.status()).toBe(201);

const createdUser = await postResponse.json();

expect(createdUser.name).toBe("Anudeep");
expect(createdUser.email).toBe("anudeep@example.com");


// ============================================================
// PUT
// ============================================================

const putResponse = await api.put("/users/1", {
  data: {
    name: "Updated User",
    email: "updated@example.com",
  },
});

expect(putResponse.status()).toBe(200);

const updatedUser = await putResponse.json();

expect(updatedUser.name).toBe("Updated User");


// ============================================================
// PATCH
// ============================================================

const patchResponse = await api.patch("/users/1", {
  data: {
    name: "Updated Name",
  },
});

expect(patchResponse.status()).toBe(200);

const patchedUser = await patchResponse.json();

expect(patchedUser.name).toBe("Updated Name");


// ============================================================
// DELETE
// ============================================================

const deleteResponse = await api.delete("/users/1");

expect(deleteResponse.status()).toBe(200);


// Close API context
await api.dispose();


// ============================================================
// COMMON VALIDATIONS
// ============================================================

// Status
expect(response.status()).toBe(200);

// Response body
expect(body.id).toBe(1);
expect(body.name).toBeTruthy();

// String
expect(body.email).toContain("@");
expect(body.name).toBe("Anudeep");

// Response headers
expect(response.headers()["content-type"])
  .toContain("application/json");


// Remember:
// status()     → status code
// json()       → response body
// headers()    → response headers
// toBe()       → exact value
// toBeTruthy() → value exists/is true
// toContain()  → partial value

  // ============================================================
  // 25. JAVASCRIPT EVALUATION
  // ============================================================

  // Run JavaScript on page
  await page.evaluate(() => {
    document.body.style.backgroundColor = "yellow";
  });

  // Run JavaScript on element
  await page.locator("#button").evaluate((el) => {
    (el as HTMLElement).click();
  });

  // Get value using JavaScript
  const id = await page
    .locator("#button")
    .evaluate((el) => el.getAttribute("id"));

  const titles = await page.evaluate(() => document.title);

  // Run script before page loads
  await page.addInitScript(() => {
    localStorage.setItem("feature-flag", "true");
  });

  // Expose Node.js function to browser
  await page.exposeFunction("logMessage", (msg: string) => {
    console.log(msg);
  });

  // Remember:
  // page.evaluate()      → page JS
  // locator.evaluate()   → element JS
  // addInitScript()      → JS before page loads
  // exposeFunction()     → Node function in browser

  // ============================================================
  // 29. DEBUGGING
  // ============================================================

  // Pause and open Playwright Inspector
  // await page.pause();

  // Highlight element
  await page.locator("#button").highlight();

  // Remember:
  // pause()     → open Inspector
  // highlight() → highlight element
});

``

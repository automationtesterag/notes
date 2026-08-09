# WDIO Hybrid Framework — Action Reference

## Table of Contents

- [Element Selection (Web & Mobile)](#element-selection-web--mobile)
- [Web Actions (Browser)](#web-actions-browser)
  - [Navigation](#navigation)
  - [Browser Window](#browser-window)
  - [Element Actions](#element-actions)
  - [Element State](#element-state)
  - [Text & Attributes](#text--attributes)
  - [Keyboard Actions](#keyboard-actions)
  - [Mouse Actions](#mouse-actions)
  - [Cookies](#cookies)
  - [Alerts](#alerts)
  - [Frames](#frames)
  - [JavaScript](#javascript)
  - [Screenshots](#screenshots)
  - [Waits](#waits)
  - [Network (Chromium)](#network-chromium)
- [Mobile Actions (Appium + WDIO)](#mobile-actions-appium--wdio)
  - [App Management](#app-management)
  - [Device Actions](#device-actions)
  - [Touch Gestures](#touch-gestures)
  - [Picker/Wheel Actions](#pickerwheel-actions)
  - [Clipboard](#clipboard)
  - [Notifications](#notifications)
  - [Biometrics](#biometrics)
  - [Device Information](#device-information)
  - [Context Switching (Hybrid Apps)](#context-switching-hybrid-apps)
  - [Screenshots & Recording](#screenshots--recording)
  - [File Operations](#file-operations)
  - [Permissions](#permissions)
  - [Geolocation](#geolocation)
- [Common Actions (Web & Mobile)](#common-actions-web--mobile)
- [Session Management (Web & Mobile)](#session-management-web--mobile)

---

## Element Selection (Web & Mobile)

| Action | Description |
| --- | --- |
| [`$`](#dollar) | Finds the first element matching a selector. |
| [`$$`](#dollar-dollar) | Finds all elements matching a selector. |

### <a id="dollar"></a>`$`

**Syntax:** `await $(selector)`

**Description:** Finds the first element on the page/screen matching the given selector and returns a `WebdriverIO.Element`.

**Example:**
```ts
const element: WebdriverIO.Element = await $('.my-class');
await element.click();
```

**Tips:**
- Use for interacting with a single, first-matching element.
- Chainable: `await $('.parent').$('.child')` selects a child within a parent.
- Always `await` the selection before calling actions on it.
- Selector can be CSS, XPath, accessibility id, or mobile selector strategies (`~name`, `android=`, `-ios predicate string:`).

---

### <a id="dollar-dollar"></a>`$$`

**Syntax:** `await $$(selector)`

**Description:** Finds all elements on the page/screen matching the given selector and returns an array-like `WebdriverIO.ElementArray`.

**Example:**
```ts
const elements: WebdriverIO.ElementArray = await $$('.my-class');
console.log(elements.length);
```

**Tips:**
- Use for interacting with multiple elements matching a selector.
- Returns an array of `Element` objects — supports `.map()`, `.filter()`, `for...of` once resolved.
- Always `await` this — it is an asynchronous operation.
- Combine with an index, e.g. `(await $$('.item'))[2]`, to grab a specific match.

---

## Web Actions (Browser)

### Navigation

| Action | Description |
| --- | --- |
| [`browser.url()`](#browser-url) | Opens a specified URL in the browser. |
| [`browser.back()`](#browser-back) | Navigates to the previous page in browser history. |
| [`browser.forward()`](#browser-forward) | Navigates to the next page in browser history. |
| [`browser.refresh()`](#browser-refresh) | Reloads the current web page. |
| [`browser.getUrl()`](#browser-geturl) | Returns the URL of the current page. |
| [`browser.getTitle()`](#browser-gettitle) | Returns the title of the current page. |

#### <a id="browser-url"></a>`browser.url()`

**Syntax:** `await browser.url(url)`

**Description:** Opens the specified URL in the browser.

**Example:**
```ts
await browser.url('https://example.com');
```

**Tips:**
- Accepts relative paths if `baseUrl` is set in `wdio.conf.js`.
- Always `await` — navigation is asynchronous and can race with subsequent assertions if not awaited.

---

#### <a id="browser-back"></a>`browser.back()`

**Syntax:** `await browser.back()`

**Description:** Navigates to the previous page in the browser's history.

**Example:**
```ts
await browser.back();
```

**Tips:**
- Equivalent to clicking the browser's Back button.
- Follow with `waitForDisplayed()` on a target element rather than a fixed `pause()`.

---

#### <a id="browser-forward"></a>`browser.forward()`

**Syntax:** `await browser.forward()`

**Description:** Navigates to the next page in the browser's history (opposite of `back()`).

**Example:**
```ts
await browser.forward();
```

**Tips:**
- Only works if `back()` was called previously in the session.

---

#### <a id="browser-refresh"></a>`browser.refresh()`

**Syntax:** `await browser.refresh()`

**Description:** Reloads the current web page.

**Example:**
```ts
await browser.refresh();
```

**Tips:**
- Resets any client-side state (form inputs, JS variables) on the page.
- Useful for verifying persisted state (cookies, localStorage) survives a reload.

---

#### <a id="browser-geturl"></a>`browser.getUrl()`

**Syntax:** `const url = await browser.getUrl()`

**Description:** Returns the URL of the current page.

**Example:**
```ts
const url: string = await browser.getUrl();
expect(url).toContain('/dashboard');
```

**Tips:**
- Handy for asserting redirects after navigation or form submission.

---

#### <a id="browser-gettitle"></a>`browser.getTitle()`

**Syntax:** `const title = await browser.getTitle()`

**Description:** Returns the `<title>` of the current page.

**Example:**
```ts
const title: string = await browser.getTitle();
expect(title).toBe('My App — Home');
```

**Tips:**
- Good lightweight assertion for confirming the correct page loaded without checking DOM content.

---

### Browser Window

| Action | Description |
| --- | --- |
| [`browser.maximizeWindow()`](#browser-maximizewindow) | Maximizes the browser window. |
| [`browser.minimizeWindow()`](#browser-minimizewindow) | Minimizes the browser window. |
| [`browser.fullscreenWindow()`](#browser-fullscreenwindow) | Opens the browser in fullscreen mode. |
| [`browser.setWindowSize()`](#browser-setwindowsize) | Sets the browser window dimensions. |
| [`browser.getWindowSize()`](#browser-getwindowsize) | Retrieves the current browser window size. |
| [`browser.newWindow()`](#browser-newwindow) | Opens a new browser window or tab. |
| [`browser.switchWindow()`](#browser-switchwindow) | Switches to another browser window or tab. |
| [`browser.closeWindow()`](#browser-closewindow) | Closes the current browser window. |
| [`browser.getWindowHandles()`](#browser-getwindowhandles) | Returns all open browser window handles. |

#### <a id="browser-maximizewindow"></a>`browser.maximizeWindow()`

**Syntax:** `await browser.maximizeWindow()`

**Description:** Maximizes the current browser window.

**Example:**
```ts
await browser.maximizeWindow();
```

**Tips:**
- Useful at the start of a test suite to ensure a consistent, full-size viewport.

---

#### <a id="browser-minimizewindow"></a>`browser.minimizeWindow()`

**Syntax:** `await browser.minimizeWindow()`

**Description:** Minimizes the current browser window.

**Example:**
```ts
await browser.minimizeWindow();
```

**Tips:**
- Rarely needed in headless CI runs; mostly relevant for local/manual debugging.

---

#### <a id="browser-fullscreenwindow"></a>`browser.fullscreenWindow()`

**Syntax:** `await browser.fullscreenWindow()`

**Description:** Opens the browser window in fullscreen mode.

**Example:**
```ts
await browser.fullscreenWindow();
```

**Tips:**
- Useful for testing fullscreen-specific layouts or video players.

---

#### <a id="browser-setwindowsize"></a>`browser.setWindowSize()`

**Syntax:** `await browser.setWindowSize(width, height)`

**Description:** Sets the browser window's width and height in pixels.

**Example:**
```ts
await browser.setWindowSize(1280, 800);
```

**Tips:**
- Essential for responsive/viewport-specific testing (e.g., simulating tablet or mobile-web breakpoints).

---

#### <a id="browser-getwindowsize"></a>`browser.getWindowSize()`

**Syntax:** `const size = await browser.getWindowSize()`

**Description:** Retrieves the current browser window's width and height.

**Example:**
```ts
const { width, height }: { width: number; height: number } = await browser.getWindowSize();
```

**Tips:**
- Use to assert responsive behavior kicked in at the expected breakpoint.

---

#### <a id="browser-newwindow"></a>`browser.newWindow()`

**Syntax:** `await browser.newWindow(url, options)`

**Description:** Opens a new browser window or tab and navigates to the given URL.

**Example:**
```ts
await browser.newWindow('https://example.com/help');
```

**Tips:**
- Automatically switches focus to the new window — use `switchWindow()` to go back.
- Track handles with `getWindowHandles()` if managing multiple tabs.

---

#### <a id="browser-switchwindow"></a>`browser.switchWindow()`

**Syntax:** `await browser.switchWindow(matcher)`

**Description:** Switches WebDriver focus to another open browser window or tab, matched by URL or title (string or regex).

**Example:**
```ts
await browser.switchWindow('help');
```

**Tips:**
- Needed after actions that open a new tab (e.g., clicking a link with `target="_blank"`).

---

#### <a id="browser-closewindow"></a>`browser.closeWindow()`

**Syntax:** `await browser.closeWindow()`

**Description:** Closes the currently focused browser window/tab.

**Example:**
```ts
await browser.closeWindow();
await browser.switchWindow('main');
```

**Tips:**
- After closing, you must switch to a remaining window before further interaction, or the session becomes invalid.

---

#### <a id="browser-getwindowhandles"></a>`browser.getWindowHandles()`

**Syntax:** `const handles = await browser.getWindowHandles()`

**Description:** Returns an array of all open browser window/tab handles.

**Example:**
```ts
const handles: string[] = await browser.getWindowHandles();
await browser.switchToWindow(handles[1]);
```

**Tips:**
- Useful when `switchWindow()`'s URL/title matcher isn't precise enough and you need positional access.

---

### Element Actions

| Action | Description |
| --- | --- |
| [`click()`](#click) | Clicks on an element. |
| [`doubleClick()`](#doubleclick) | Performs a double-click on an element. |
| [`rightClick()`](#rightclick) | Performs a right-click (context click). |
| [`setValue()`](#setvalue) | Replaces the existing value with new text. |
| [`addValue()`](#addvalue) | Appends text to the existing value. |
| [`clearValue()`](#clearvalue) | Clears the text from an input field. |
| [`getValue()`](#getvalue) | Retrieves the value of an input field. |
| [`selectByVisibleText()`](#selectbyvisibletext) | Selects a dropdown/picker option by visible text. |
| [`selectByIndex()`](#selectbyindex) | Selects a dropdown option by index. |
| [`selectByAttribute()`](#selectbyattribute) | Selects a dropdown option using an attribute. |
| [`scrollIntoView()`](#scrollintoview) | Scrolls the element into the visible area. |
| [`dragAndDrop()`](#draganddrop) | Drags an element and drops it onto another. |
| [`moveTo()`](#moveto) | Moves the mouse pointer over an element. |
| [`hover()`](#hover) | Hovers the mouse over an element. |
| [`uploadFile()`](#uploadfile) | Uploads a local file to the browser. |

> `click()`, `setValue()`, `addValue()`, `clearValue()`, `selectByVisibleText()`, `scrollIntoView()`, and `dragAndDrop()` behave identically on mobile and are documented once in [Common Actions](#common-actions-web--mobile).

#### <a id="doubleclick"></a>`doubleClick()`

**Syntax:** `await element.doubleClick()`

**Description:** Performs a double-click on an element.

**Example:**
```ts
await (await $('#item')).doubleClick();
```

**Tips:**
- Useful for triggering edit-in-place UI or selecting a word/text block.

---

#### <a id="rightclick"></a>`rightClick()`

**Syntax:** `await element.click({ button: 'right' })`

**Description:** Performs a right-click (context click) on an element, typically to open a context menu.

**Example:**
```ts
await (await $('#item')).click({ button: 'right' });
```

**Tips:**
- Follow up with assertions on the context menu that appears, since right-click alone doesn't select a menu item.

---

#### <a id="getvalue"></a>`getValue()`

**Syntax:** `const value = await element.getValue()`

**Description:** Retrieves the current value of an input field.

**Example:**
```ts
const value: string = await (await $('#email')).getValue();
expect(value).toBe('test@example.com');
```

**Tips:**
- Only applies to form elements exposing a `value` property (input, textarea, select).

---

#### <a id="selectbyindex"></a>`selectByIndex()`

**Syntax:** `await element.selectByIndex(index)`

**Description:** Selects a dropdown option by its zero-based index.

**Example:**
```ts
await (await $('#country')).selectByIndex(2);
```

**Tips:**
- Brittle if option order changes; prefer `selectByVisibleText()` or `selectByAttribute()` when possible.

---

#### <a id="selectbyattribute"></a>`selectByAttribute()`

**Syntax:** `await element.selectByAttribute(attribute, value)`

**Description:** Selects a dropdown option whose given attribute (e.g., `value`) matches the provided value.

**Example:**
```ts
await (await $('#country')).selectByAttribute('value', 'IN');
```

**Tips:**
- Best choice when option `value` attributes are stable identifiers (e.g., country codes).

---

#### <a id="moveto"></a>`moveTo()`

**Syntax:** `await element.moveTo(options)`

**Description:** Moves the mouse pointer over the given element without clicking.

**Example:**
```ts
await (await $('#menu-item')).moveTo();
```

**Tips:**
- Useful for triggering `:hover` CSS states or JS mouseover handlers before asserting a tooltip/submenu appears.

---

#### <a id="hover"></a>`hover()`

**Syntax:** `await element.moveTo()` *(WDIO exposes hover via `moveTo()`; some custom commands alias it as `hover()`)*

**Description:** Hovers the mouse over an element to trigger hover-based UI.

**Example:**
```ts
await (await $('.dropdown-trigger')).moveTo();
```

**Tips:**
- Not a distinct WebDriver primitive — it's `moveTo()` under the hood. Pair with `waitForDisplayed()` on whatever should appear on hover.

---

#### <a id="uploadfile"></a>`uploadFile()`

**Syntax:** `const remotePath = await browser.uploadFile(localFilePath)`

**Description:** Uploads a local file to the remote Selenium/browser session so it can be used with a file input.

**Example:**
```ts
const remotePath: string = await browser.uploadFile('/path/to/file.png');
await (await $('input[type="file"]')).setValue(remotePath);
```

**Tips:**
- Needed mainly for remote/cloud grid sessions; for local runs, `setValue()` with a local path on the file input often works directly.

---

### Element State

| Action | Description |
| --- | --- |
| [`isDisplayed()`](#isdisplayed) | Checks if an element is visible. |
| [`isEnabled()`](#isenabled) | Checks if an element is enabled. |
| [`isClickable()`](#isclickable) | Checks if an element can be clicked. |
| [`isSelected()`](#isselected) | Checks if an element is selected. |
| [`isExisting()`](#isexisting) | Checks if an element exists in the DOM. |
| [`waitForDisplayed()`](#waitfordisplayed) | Waits until an element becomes visible. |
| [`waitForClickable()`](#waitforclickable) | Waits until an element becomes clickable. |
| [`waitForEnabled()`](#waitforenabled) | Waits until an element becomes enabled. |
| [`waitForExist()`](#waitforexist) | Waits until an element exists in the DOM. |

> `isDisplayed()`, `isEnabled()`, `isSelected()`, `waitForDisplayed()`, and `waitForClickable()` are shared with mobile — see [Common Actions](#common-actions-web--mobile).

#### <a id="isclickable"></a>`isClickable()`

**Syntax:** `const clickable = await element.isClickable()`

**Description:** Checks whether an element can currently be clicked (visible, enabled, and not obstructed).

**Example:**
```ts
const clickable: boolean = await (await $('#submit')).isClickable();
```

**Tips:**
- More comprehensive than `isDisplayed()` + `isEnabled()` combined, since it also checks overlap/obstruction.

---

#### <a id="isexisting"></a>`isExisting()`

**Syntax:** `const exists = await element.isExisting()`

**Description:** Checks whether an element exists in the DOM (regardless of visibility).

**Example:**
```ts
const exists: boolean = await (await $('#hidden-modal')).isExisting();
```

**Tips:**
- Use for elements that exist in the DOM but are hidden via CSS (`display: none`), which `isDisplayed()` would report as not visible.

---

#### <a id="waitforenabled"></a>`waitForEnabled()`

**Syntax:** `await element.waitForEnabled(options)`

**Description:** Waits until an element becomes enabled.

**Example:**
```ts
await (await $('#submit')).waitForEnabled();
```

**Tips:**
- Useful when a button is disabled until an async validation (e.g., debounce) completes.

---

#### <a id="waitforexist"></a>`waitForExist()`

**Syntax:** `await element.waitForExist(options)`

**Description:** Waits until an element exists in the DOM.

**Example:**
```ts
await (await $('#dynamic-row')).waitForExist();
```

**Tips:**
- Use for elements rendered dynamically (e.g., after an API response), before checking visibility.

---

### Text & Attributes

| Action | Description |
| --- | --- |
| [`getText()`](#gettext) | Retrieves the visible text of an element. |
| [`getHTML()`](#gethtml) | Retrieves the HTML source of an element. |
| [`getAttribute()`](#getattribute) | Retrieves the value of an attribute. |
| [`getCSSProperty()`](#getcssproperty) | Retrieves the value of a CSS property. |
| [`getTagName()`](#gettagname) | Returns the HTML tag name of an element. |
| [`getLocation()`](#getlocation) | Returns the element's X and Y coordinates. |
| [`getSize()`](#getsize) | Returns the width and height of an element. |
| [`getRect()`](#getrect) | Returns the element's position and size. |

> `getText()` and `getAttribute()` are shared with mobile — see [Common Actions](#common-actions-web--mobile).

#### <a id="gethtml"></a>`getHTML()`

**Syntax:** `const html = await element.getHTML(includeSelectorTag)`

**Description:** Retrieves the HTML source of an element.

**Example:**
```ts
const html: string = await (await $('#card')).getHTML();
```

**Tips:**
- Pass `false` to exclude the element's own outer tag and get only the inner HTML.
- Useful for debugging or asserting complex nested markup rather than plain text.

---

#### <a id="getcssproperty"></a>`getCSSProperty()`

**Syntax:** `const prop = await element.getCSSProperty(propertyName)`

**Description:** Retrieves the computed value of a CSS property on an element.

**Example:**
```ts
const color: WebdriverIO.CSSProperty = await (await $('#alert')).getCSSProperty('color');
console.log(color.parsed.hex); // e.g. #ff0000
```

**Tips:**
- Returns a parsed object (`.value`, `.parsed`) rather than a plain string, useful for color/unit comparisons.

---

#### <a id="gettagname"></a>`getTagName()`

**Syntax:** `const tag = await element.getTagName()`

**Description:** Returns the HTML tag name of an element (lowercase).

**Example:**
```ts
const tag: string = await (await $('#el')).getTagName();
expect(tag).toBe('button');
```

**Tips:**
- Handy in generic/dynamic test helpers where the underlying element type varies.

---

#### <a id="getlocation"></a>`getLocation()`

**Syntax:** `const location = await element.getLocation()`

**Description:** Returns the element's X and Y coordinates on the page.

**Example:**
```ts
const { x, y }: { x: number; y: number } = await (await $('#el')).getLocation();
```

**Tips:**
- Useful for visual/positional assertions (e.g., verifying an element is above another).

---

#### <a id="getsize"></a>`getSize()`

**Syntax:** `const size = await element.getSize()`

**Description:** Returns the width and height of an element.

**Example:**
```ts
const { width, height }: { width: number; height: number } = await (await $('#el')).getSize();
```

**Tips:**
- Combine with `getLocation()` (or just use `getRect()`) for full bounding-box assertions.

---

#### <a id="getrect"></a>`getRect()`

**Syntax:** `const rect = await element.getRect()`

**Description:** Returns the element's position and size (x, y, width, height) in one call.

**Example:**
```ts
const rect: { x: number; y: number; width: number; height: number } = await (await $('#el')).getRect();
```

**Tips:**
- More efficient than calling `getLocation()` and `getSize()` separately; also used heavily in mobile native contexts.

---

### Keyboard Actions

| Action | Description |
| --- | --- |
| [`browser.keys()`](#browser-keys) | Sends one or more keyboard keys. |
| [`browser.action('key')`](#browser-action-key) | Performs advanced keyboard actions. |

#### <a id="browser-keys"></a>`browser.keys()`

**Syntax:** `await browser.keys(keys)`

**Description:** Sends one or more keyboard keys to the currently focused element.

**Example:**
```ts
await browser.keys(['Control', 'a']);
await browser.keys('Enter');
```

**Tips:**
- Requires an element to already have focus (e.g., after clicking an input).
- Accepts a single key string or an array for key combinations.

---

#### <a id="browser-action-key"></a>`browser.action('key')`

**Syntax:** `await browser.action('key').down('a').up('a').perform()`

**Description:** Performs advanced, low-level keyboard actions (key down/up sequences, holding modifier keys) using the WebDriver Actions API.

**Example:**
```ts
await browser.action('key')
  .down('Shift')
  .down('a')
  .up('a')
  .up('Shift')
  .perform();
```

**Tips:**
- Use over `browser.keys()` when you need precise control of key press/release timing, e.g., for shortcuts held during another action.

---

### Mouse Actions

| Action | Description |
| --- | --- |
| [`browser.action('pointer')`](#browser-action-pointer) | Performs advanced mouse interactions. |
| [Click / Double Click / Right Click / Hover / Drag & Drop / Move Mouse](#mouse-action-primitives) | Pointer-action primitives chained off `action('pointer')`. |

#### <a id="browser-action-pointer"></a>`browser.action('pointer')`

**Syntax:** `await browser.action('pointer').move({ origin: element }).down().up().perform()`

**Description:** Performs advanced, low-level mouse/pointer interactions using the WebDriver Actions API (click, drag, hover, multi-step gestures).

**Example:**
```ts
await browser.action('pointer')
  .move({ origin: await $('#drag') })
  .down()
  .move({ origin: await $('#drop') })
  .up()
  .perform();
```

**Tips:**
- Use for complex sequences that simple commands (`click()`, `dragAndDrop()`) can't express — e.g., multi-step drags, custom hold durations.

---

#### <a id="mouse-action-primitives"></a>Click / Double Click / Right Click / Hover / Drag & Drop / Move Mouse

**Description:** These are all pointer-action primitives (`down()`, `up()`, `move()`, `doubleClick()`) chained via `browser.action('pointer')` to simulate precise mouse behavior beyond what element-level shorthand methods (`click()`, `moveTo()`, `dragAndDrop()`) offer.

**Example:**
```ts
await browser.action('pointer')
  .move({ origin: await $('#target') })
  .down()
  .up()
  .perform(); // simulates a click via low-level pointer events
```

**Tips:**
- Prefer the simple element methods (`click()`, `doubleClick()`, `dragAndDrop()`) first; drop to the Actions API only when the simple methods don't reliably trigger the app's JS event handlers.

---

### Cookies

| Action | Description |
| --- | --- |
| [`setCookies()`](#setcookies) | Adds cookies to the browser. |
| [`getCookies()`](#getcookies) | Retrieves browser cookies. |
| [`deleteCookies()`](#deletecookies) | Deletes browser cookies. |

#### <a id="setcookies"></a>`setCookies()`

**Syntax:** `await browser.setCookies(cookieObjOrArray)`

**Description:** Adds one or more cookies to the browser session.

**Example:**
```ts
await browser.setCookies({ name: 'session', value: 'abc123' });
```

**Tips:**
- Useful for bypassing UI login by injecting an auth/session cookie directly.
- The target domain must already be loaded (navigate first) before setting cookies for it.

---

#### <a id="getcookies"></a>`getCookies()`

**Syntax:** `const cookies = await browser.getCookies(names)`

**Description:** Retrieves cookies from the browser; optionally filter by name(s).

**Example:**
```ts
const cookies: WebdriverIO.Cookie[] = await browser.getCookies(['session']);
```

**Tips:**
- Omit the `names` argument to retrieve all cookies for the current domain.

---

#### <a id="deletecookies"></a>`deleteCookies()`

**Syntax:** `await browser.deleteCookies(names)`

**Description:** Deletes specified cookies, or all cookies if no names are given.

**Example:**
```ts
await browser.deleteCookies(); // clear all
```

**Tips:**
- Good for resetting session state between tests without a full browser restart.

---

### Alerts

| Action | Description |
| --- | --- |
| [`acceptAlert()`](#acceptalert) | Accepts a browser alert. |
| [`dismissAlert()`](#dismissalert) | Dismisses a browser alert. |
| [`getAlertText()`](#getalerttext) | Retrieves the alert message. |
| [`sendAlertText()`](#sendalerttext) | Enters text into a prompt alert. |

#### <a id="acceptalert"></a>`acceptAlert()`

**Syntax:** `await browser.acceptAlert()`

**Description:** Accepts (clicks "OK" on) a native browser alert/confirm dialog.

**Example:**
```ts
await browser.acceptAlert();
```

**Tips:**
- Must be called while the alert is actually open, or the command errors out.

---

#### <a id="dismissalert"></a>`dismissAlert()`

**Syntax:** `await browser.dismissAlert()`

**Description:** Dismisses (clicks "Cancel" on) a native browser alert/confirm dialog.

**Example:**
```ts
await browser.dismissAlert();
```

**Tips:**
- On plain `alert()` dialogs (no Cancel button), this behaves the same as `acceptAlert()`.

---

#### <a id="getalerttext"></a>`getAlertText()`

**Syntax:** `const text = await browser.getAlertText()`

**Description:** Retrieves the message text of an open alert.

**Example:**
```ts
const message: string = await browser.getAlertText();
expect(message).toBe('Are you sure?');
```

**Tips:**
- Call before accepting/dismissing, since the alert closes (and text becomes unavailable) afterward.

---

#### <a id="sendalerttext"></a>`sendAlertText()`

**Syntax:** `await browser.sendAlertText(text)`

**Description:** Enters text into a native `prompt()` alert's input field.

**Example:**
```ts
await browser.sendAlertText('my answer');
await browser.acceptAlert();
```

**Tips:**
- Only works on `prompt()`-style alerts that have a text field, not plain `alert()`/`confirm()`.

---

### Frames

| Action | Description |
| --- | --- |
| [`switchFrame()`](#switchframe) | Switches to a specified iframe. |
| [`switchToParentFrame()`](#switchtoparentframe) | Switches back to the parent frame. |

#### <a id="switchframe"></a>`switchFrame()`

**Syntax:** `await browser.switchFrame(elementOrIndexOrName)`

**Description:** Switches WebDriver context to a specified `<iframe>`/`<frame>`.

**Example:**
```ts
const frame: WebdriverIO.Element = await $('#payment-iframe');
await browser.switchFrame(frame);
```

**Tips:**
- All subsequent `$`/`$$` element lookups target inside the frame until you switch back.

---

#### <a id="switchtoparentframe"></a>`switchToParentFrame()`

**Syntax:** `await browser.switchToParentFrame()`

**Description:** Switches focus back to the parent frame (out of the current iframe context).

**Example:**
```ts
await browser.switchToParentFrame();
```

**Tips:**
- Always switch back after finishing iframe interactions, or subsequent element lookups on the main page will fail to find elements.

---

### JavaScript

| Action | Description |
| --- | --- |
| [`execute()`](#execute) | Executes JavaScript in the browser. |
| [`executeAsync()`](#executeasync) | Executes asynchronous JavaScript. |

> `execute()` is shared with mobile (used to run `mobile:` gesture commands) — see [Common Actions](#common-actions-web--mobile).

#### <a id="executeasync"></a>`executeAsync()`

**Syntax:** `const result = await browser.executeAsync((...args, done) => { ... done(result); })`

**Description:** Executes asynchronous JavaScript in the browser, resolving when the injected `done` callback is invoked.

**Example:**
```ts
const result: string = await browser.executeAsync((done: (result: string) => void) => {
  setTimeout(() => done('finished'), 1000);
});
```

**Tips:**
- Needed when the script must wait on a callback/promise (e.g., a fetch call) before returning a value to the test.

---

### Screenshots

| Action | Description |
| --- | --- |
| [`saveScreenshot()`](#savescreenshot) | Captures a screenshot of the current page. |

> Documented once, in [Common Actions](#common-actions-web--mobile) — identical on web and mobile.

---

### Waits

| Action | Description |
| --- | --- |
| [`waitUntil()`](#waituntil) | Waits until a custom condition is met. |
| [`pause()`](#pause) | Pauses execution for a specified time. |

> `pause()` is shared with mobile — see [Common Actions](#common-actions-web--mobile).

#### <a id="waituntil"></a>`waitUntil()`

**Syntax:** `await browser.waitUntil(condition, options)`

**Description:** Waits until a custom condition (a function returning a truthy value or resolved promise) is met, up to a timeout.

**Example:**
```ts
await browser.waitUntil(
  async () => (await browser.getTitle()) === 'Dashboard',
  { timeout: 5000, timeoutMsg: 'Title never changed' }
);
```

**Tips:**
- Use for custom synchronization logic not covered by built-in `waitFor*()` methods.
- Always provide a `timeoutMsg` for clearer failure diagnostics.

---

### Network (Chromium)

| Action | Description |
| --- | --- |
| [Request Interception](#request-interception) | Intercepts outgoing network requests. |
| [Mock API Responses](#mock-api-responses) | Mocks backend API responses. |
| [Capture Network Calls](#capture-network-calls) | Monitors network requests and responses. |

#### <a id="request-interception"></a>Request Interception

**Syntax:** `const mock = await browser.mock(urlPattern, filterOptions); mock.abort(errorReason)`

**Description:** Intercepts outgoing network requests before they reach the server, allowing inspection or modification.

**Example:**
```ts
const mock: WebdriverIO.Mock = await browser.mock('**/api/users');
mock.abort('Failed'); // block the request
```

**Tips:**
- Only supported in Chromium-based browsers via the DevTools protocol / WebDriver Bidi.

---

#### <a id="mock-api-responses"></a>Mock API Responses

**Syntax:** `const mock = await browser.mock(urlPattern, filterOptions); mock.respond(mockData)`

**Description:** Mocks backend API responses so tests can run against controlled, predictable data.

**Example:**
```ts
const mock: WebdriverIO.Mock = await browser.mock('**/api/orders');
mock.respond([{ id: 1, status: 'shipped' }]);
```

**Tips:**
- Great for testing UI states that are hard to reproduce with a real backend (errors, empty states, edge-case data).

---

#### <a id="capture-network-calls"></a>Capture Network Calls

**Syntax:** `const mock = await browser.mock(urlPattern); const calls = mock.calls`

**Description:** Monitors and records network requests/responses matching a pattern for inspection.

**Example:**
```ts
const mock: WebdriverIO.Mock = await browser.mock('**/api/**');
await (await $('#load')).click();
console.log(mock.calls.length);
```

**Tips:**
- Useful for asserting the app made (or didn't make) a specific API call, without needing a full mock.

---

## Mobile Actions (Appium + WDIO)

### App Management

| Action | Description |
| --- | --- |
| [`activateApp()`](#activateapp) | Brings an installed app to the foreground. |
| [`terminateApp()`](#terminateapp) | Closes a running application. |
| [`installApp()`](#installapp) | Installs an application on the device. |
| [`removeApp()`](#removeapp) | Uninstalls an application from the device. |
| [`isAppInstalled()`](#isappinstalled) | Checks whether an app is installed. |
| [`queryAppState()`](#queryappstate) | Returns the current state of an app. |

#### <a id="activateapp"></a>`activateApp()`

**Syntax:** `await driver.activateApp(appId)`

**Description:** Brings an installed app to the foreground (e.g., after backgrounding it).

**Example:**
```ts
await driver.activateApp('com.example.app');
```

**Tips:**
- Useful for testing app-resume behavior without a full relaunch.

---

#### <a id="terminateapp"></a>`terminateApp()`

**Syntax:** `await driver.terminateApp(appId)`

**Description:** Closes (kills) a running application.

**Example:**
```ts
await driver.terminateApp('com.example.app');
```

**Tips:**
- Use to simulate the app being force-closed, then `activateApp()` to test cold-start/resume state restoration.

---

#### <a id="installapp"></a>`installApp()`

**Syntax:** `await driver.installApp(appPath)`

**Description:** Installs an application (APK/IPA) on the device or simulator.

**Example:**
```ts
await driver.installApp('/path/to/app.apk');
```

**Tips:**
- Useful for testing app-update flows by installing an older then a newer build.

---

#### <a id="removeapp"></a>`removeApp()`

**Syntax:** `await driver.removeApp(appId)`

**Description:** Uninstalls an application from the device.

**Example:**
```ts
await driver.removeApp('com.example.app');
```

**Tips:**
- Combine with `installApp()` to test a true first-launch/onboarding experience.

---

#### <a id="isappinstalled"></a>`isAppInstalled()`

**Syntax:** `const installed = await driver.isAppInstalled(appId)`

**Description:** Checks whether a given app is installed on the device.

**Example:**
```ts
const installed: boolean = await driver.isAppInstalled('com.example.app');
```

**Tips:**
- Useful as a setup guard before install/uninstall test steps.

---

#### <a id="queryappstate"></a>`queryAppState()`

**Syntax:** `const state = await driver.queryAppState(appId)`

**Description:** Returns the current state of an app (not running, running in background/foreground, etc.), as a numeric code. Also referenced from [Device Information](#device-information) as "App State".

**Example:**
```ts
const state: number = await driver.queryAppState('com.example.app');
```

**Tips:**
- State codes: `0` not installed, `1` not running, `3` running in background, `4` running in foreground (Appium convention).

---

### Device Actions

| Action | Description |
| --- | --- |
| [`lock()`](#lock) | Locks the device screen. |
| [`unlock()`](#unlock) | Unlocks the device screen. |
| [`isLocked()`](#islocked) | Checks whether the device is locked. |
| [`getOrientation()`](#getorientation) | Returns the current screen orientation. |
| [`setOrientation()`](#setorientation) | Changes the device orientation. |
| [`pressKeyCode()`](#presskeycode) _(Android)_ | Simulates an Android hardware key press. |
| [`back()`](#android-back) _(Android)_ | Simulates the Android Back button. |
| [`hideKeyboard()`](#hidekeyboard) | Hides the on-screen keyboard. |
| [`shake()`](#shake) _(iOS)_ | Simulates shaking the device. |

#### <a id="lock"></a>`lock()`

**Syntax:** `await driver.lock(seconds)`

**Description:** Locks the device screen, optionally for a given duration before auto-unlocking.

**Example:**
```ts
await driver.lock(5);
```

**Tips:**
- Useful for testing how the app behaves when backgrounded via screen lock (e.g., session timeout).

---

#### <a id="unlock"></a>`unlock()`

**Syntax:** `await driver.unlock()`

**Description:** Unlocks the device screen.

**Example:**
```ts
await driver.unlock();
```

**Tips:**
- Pair with `isLocked()` to confirm the unlock succeeded before proceeding.

---

#### <a id="islocked"></a>`isLocked()`

**Syntax:** `const locked = await driver.isLocked()`

**Description:** Checks whether the device screen is currently locked.

**Example:**
```ts
const locked: boolean = await driver.isLocked();
```

**Tips:**
- Good guard condition before interacting with the app, since interactions fail while the screen is locked.

---

#### <a id="getorientation"></a>`getOrientation()`

**Syntax:** `const orientation = await driver.getOrientation()`

**Description:** Returns the current screen orientation (`PORTRAIT` or `LANDSCAPE`). Also referenced from [Device Information](#device-information) as "Screen Orientation".

**Example:**
```ts
const orientation: 'LANDSCAPE' | 'PORTRAIT' = await driver.getOrientation();
```

**Tips:**
- Use before/after `setOrientation()` to assert the app correctly re-renders on rotation.

---

#### <a id="setorientation"></a>`setOrientation()`

**Syntax:** `await driver.setOrientation('LANDSCAPE')`

**Description:** Changes the device orientation.

**Example:**
```ts
await driver.setOrientation('LANDSCAPE');
```

**Tips:**
- Essential for testing responsive layouts and orientation-lock behavior in native apps.

---

#### <a id="presskeycode"></a>`pressKeyCode()` _(Android)_

**Syntax:** `await driver.pressKeyCode(keyCode)`

**Description:** Simulates an Android hardware/software key press using its numeric key code.

**Example:**
```ts
await driver.pressKeyCode(4); // Android Back key
```

**Tips:**
- Common codes: `4` Back, `3` Home, `82` Menu, `66` Enter. Android-only.

---

#### <a id="android-back"></a>`back()` _(Android)_

**Syntax:** `await driver.back()`

**Description:** Simulates pressing the Android hardware/software Back button.

**Example:**
```ts
await driver.back();
```

**Tips:**
- Equivalent shorthand for `pressKeyCode(4)`; use to test back-navigation stacks.

---

#### <a id="hidekeyboard"></a>`hideKeyboard()`

**Syntax:** `await driver.hideKeyboard()`

**Description:** Hides the on-screen (soft) keyboard.

**Example:**
```ts
await driver.hideKeyboard();
```

**Tips:**
- Often necessary before interacting with elements the keyboard is covering.

---

#### <a id="shake"></a>`shake()` _(iOS)_

**Syntax:** `await driver.shake()`

**Description:** Simulates shaking the device — commonly wired up as an "Undo" or "Report a problem" gesture in iOS apps.

**Example:**
```ts
await driver.shake();
```

**Tips:**
- iOS simulator only; not available on Android or real iOS devices.

---

### Touch Gestures

| Action | Description |
| --- | --- |
| [Tap](#tap) | Performs a single tap on an element. |
| [Long Press](#longpress) | Presses and holds an element. |
| [Swipe (Up/Down/Left/Right)](#swipe) | Swipes across the screen in a given direction. |
| [Scroll](#mobile-scroll) | Scrolls the screen until content is visible. |
| [Drag & Drop](#gesture-draganddrop) | Drags an element to a target location. |
| [Pinch](#pinch) | Performs a pinch-in or pinch-out gesture. |
| [Zoom](#zoom) | Zooms into an element using gestures. |
| [Fling](#fling) | Performs a fast swipe with momentum. |
| [Double Tap](#doubletap) | Performs two quick taps on an element. |

#### <a id="tap"></a>Tap

**Syntax:** `await element.click()`

**Description:** Performs a single tap on an element.

**Example:**
```ts
await (await $('~loginButton')).click();
```

**Tips:**
- WDIO maps [`click()`](#click) to a native tap automatically on mobile sessions — use the raw touch Actions API below only for custom gestures.

---

#### <a id="longpress"></a>Long Press

**Syntax:** `await driver.action('pointer', { parameters: { pointerType: 'touch' } }).move({ origin: element }).down().pause(1000).up().perform()`

**Description:** Presses and holds an element for an extended duration.

**Example:**
```ts
await driver.action('pointer', { parameters: { pointerType: 'touch' } })
  .move({ origin: await $('~item') })
  .down()
  .pause(1000)
  .up()
  .perform();
```

**Tips:**
- Common trigger for context menus (e.g., "copy/paste", "delete") in native apps.

---

#### <a id="swipe"></a>Swipe (Up/Down/Left/Right)

**Syntax:** `await driver.action('pointer', { parameters: { pointerType: 'touch' } }).move({ x, y }).down().move({ x: x2, y: y2 }).up().perform()`

**Description:** Swipes across the screen in a given direction by moving a touch pointer from one coordinate to another. Direction is simply a matter of which way the coordinates move — up = decreasing Y, down = increasing Y, left = decreasing X, right = increasing X.

**Example:**
```ts
// Swipe up
await driver.action('pointer', { parameters: { pointerType: 'touch' } })
  .move({ x: 200, y: 800 })
  .down()
  .move({ duration: 300, x: 200, y: 200 })
  .up()
  .perform();
```

**Tips:**
- Many teams wrap this in a reusable `swipe(direction)` helper rather than repeating raw Action chains for each direction.

---

#### <a id="mobile-scroll"></a>Scroll

**Syntax:** `await element.scrollIntoView()` or `await driver.execute('mobile: scroll', { direction: 'down' })`

**Description:** Scrolls the screen until the desired content becomes visible.

**Example:**
```ts
await driver.execute('mobile: scroll', { direction: 'down' });
```

**Tips:**
- `mobile: scroll` is Appium's native mobile command — more reliable than manual swipe loops for long lists.

---

#### <a id="gesture-draganddrop"></a>Drag & Drop (gesture)

**Syntax:** `await driver.action('pointer', { parameters: { pointerType: 'touch' } }).move({ origin: source }).down().move({ origin: target }).up().perform()`

**Description:** Drags an element to a target location using a touch pointer sequence.

**Example:**
```ts
await driver.action('pointer', { parameters: { pointerType: 'touch' } })
  .move({ origin: await $('~sourceItem') })
  .down()
  .move({ duration: 500, origin: await $('~targetSlot') })
  .up()
  .perform();
```

**Tips:**
- Add small pauses between move steps for apps whose drag handlers need time to register the gesture start.
- For simple element-to-element drags, the [`dragAndDrop()`](#draganddrop) element command may already work — reach for the raw touch Action chain only if it doesn't.

---

#### <a id="pinch"></a>Pinch

**Syntax:** `await driver.execute('mobile: pinchCloseGesture', options)` *(pinch-in)* / `mobile: pinchOpenGesture` *(pinch-out)*

**Description:** Performs a pinch-in (zoom out) or pinch-out (zoom in) gesture using two simulated touch points.

**Example:**
```ts
await driver.execute('mobile: pinchCloseGesture', { elementId: (await $('~map')).elementId });
```

**Tips:**
- Appium's `mobile:` gesture commands are the modern, more reliable replacement for manual multi-touch Action chains.

---

#### <a id="zoom"></a>Zoom

**Syntax:** `await driver.execute('mobile: pinchOpenGesture', options)`

**Description:** Zooms into an element using a pinch-out touch gesture.

**Example:**
```ts
await driver.execute('mobile: pinchOpenGesture', { elementId: (await $('~photo')).elementId });
```

**Tips:**
- Commonly tested on image galleries, maps, and PDF viewers.

---

#### <a id="fling"></a>Fling

**Syntax:** `await driver.execute('mobile: flingGesture', { elementId, direction: 'up', speed: 2500 })`

**Description:** Performs a fast swipe with momentum, causing content to continue scrolling after the gesture ends.

**Example:**
```ts
await driver.execute('mobile: flingGesture', { elementId: (await $('~feed')).elementId, direction: 'up', speed: 2500 });
```

**Tips:**
- Use to test momentum-scrolling UI (e.g., long feeds) distinct from a plain, controlled [scroll](#mobile-scroll).

---

#### <a id="doubletap"></a>Double Tap

**Syntax:** `await driver.execute('mobile: doubleTap', { elementId })`

**Description:** Performs two quick taps on an element.

**Example:**
```ts
await driver.execute('mobile: doubleTap', { elementId: (await $('~photo')).elementId });
```

**Tips:**
- Common for "like" gestures or zoom-to-fit in image/map views.

---

### Picker/Wheel Actions

| Action | Description |
| --- | --- |
| [`setValue()`](#setvalue) | Sets a value in a picker or text field. |
| [`selectByVisibleText()`](#selectbyvisibletext) | Selects a visible option from a picker. |
| [`mobile: selectPickerWheelValue`](#mobile-selectpickerwheelvalue) _(iOS)_ | Changes the value of an iOS picker wheel. |

> `setValue()` and `selectByVisibleText()` behave the same as on web — see [Common Actions](#common-actions-web--mobile).

#### <a id="mobile-selectpickerwheelvalue"></a>`mobile: selectPickerWheelValue` _(iOS)_

**Syntax:** `await driver.execute('mobile: selectPickerWheelValue', { elementId, order: 'next', offset: 0.15 })`

**Description:** Changes the value of an iOS `UIPickerView` wheel by simulating a scroll/spin gesture.

**Example:**
```ts
await driver.execute('mobile: selectPickerWheelValue', {
  elementId: (await $('~pickerWheel')).elementId,
  order: 'next',
});
```

**Tips:**
- iOS-only; Android equivalents typically use `mobile: scroll` or direct element interaction.

---

### Clipboard

| Action | Description |
| --- | --- |
| [`setClipboard()`](#setclipboard) | Copies data to the device clipboard. |
| [`getClipboard()`](#getclipboard) | Retrieves data from the device clipboard. |

#### <a id="setclipboard"></a>`setClipboard()`

**Syntax:** `await driver.setClipboard(base64Text, contentType)`

**Description:** Copies data to the device clipboard (text is base64-encoded).

**Example:**
```ts
await driver.setClipboard(Buffer.from('hello').toString('base64'), 'plaintext');
```

**Tips:**
- Useful for testing paste functionality without simulating a full copy gesture first.

---

#### <a id="getclipboard"></a>`getClipboard()`

**Syntax:** `const data = await driver.getClipboard()`

**Description:** Retrieves data currently on the device clipboard (base64-encoded).

**Example:**
```ts
const clip = Buffer.from(await driver.getClipboard(), 'base64').toString();
```

**Tips:**
- Useful for verifying a "Copy to clipboard" feature actually copied the expected text.

---

### Notifications

| Action | Description |
| --- | --- |
| [`openNotifications()`](#opennotifications) _(Android)_ | Opens the Android notification panel. |

#### <a id="opennotifications"></a>`openNotifications()` _(Android)_

**Syntax:** `await driver.openNotifications()`

**Description:** Opens the Android notification shade/panel.

**Example:**
```ts
await driver.openNotifications();
```

**Tips:**
- Android-only; useful for testing push-notification content and tap-through behavior.

---

### Biometrics

| Action | Description |
| --- | --- |
| [Fingerprint Authentication](#fingerprint-auth) | Simulates fingerprint authentication. |
| [Face ID Authentication](#faceid-auth) | Simulates Face ID authentication on supported simulators. |

#### <a id="fingerprint-auth"></a>Fingerprint Authentication

**Syntax:** `await driver.fingerPrint(fingerId)` *(Android emulator only)*

**Description:** Simulates fingerprint authentication for apps using biometric login.

**Example:**
```ts
await driver.fingerPrint(1);
```

**Tips:**
- Only works on Android emulators with an enrolled virtual fingerprint; not supported on real devices.

---

#### <a id="faceid-auth"></a>Face ID Authentication

**Syntax:** `await driver.execute('mobile: sendBiometricMatch', { type: 'faceId', match: true })` *(iOS simulator only)*

**Description:** Simulates Face ID authentication (match or non-match) on supported iOS simulators.

**Example:**
```ts
await driver.execute('mobile: sendBiometricMatch', { type: 'faceId', match: true });
```

**Tips:**
- iOS simulator only; requires biometrics to be "enrolled" in the simulator first via `mobile: enrollBiometric`.

---

### Device Information

| Action | Description |
| --- | --- |
| [Battery Info](#batteryinfo) | Retrieves the device battery status. |
| [Device Time](#devicetime) | Retrieves the current device time. |
| [Screen Orientation](#getorientation) | Retrieves the current screen orientation. |
| [App State](#queryappstate) | Retrieves the current state of an application. |

> "Screen Orientation" and "App State" are the same commands as [`getOrientation()`](#getorientation) and [`queryAppState()`](#queryappstate) above — linked here, not repeated.

#### <a id="batteryinfo"></a>Battery Info

**Syntax:** `const battery = await driver.execute('mobile: batteryInfo')`

**Description:** Retrieves the device's current battery level and charging state.

**Example:**
```ts
const battery = await driver.execute('mobile: batteryInfo');
```

**Tips:**
- Useful for apps that adapt behavior based on low-battery state.

---

#### <a id="devicetime"></a>Device Time

**Syntax:** `const time = await driver.getDeviceTime()`

**Description:** Retrieves the current time reported by the device.

**Example:**
```ts
const time = await driver.getDeviceTime();
```

**Tips:**
- Useful for verifying timestamps shown in the app match device time (e.g., timezone handling).

---

### Context Switching (Hybrid Apps)

| Action | Description |
| --- | --- |
| [`getContexts()`](#getcontexts) | Retrieves available app contexts (Native/WebView). |
| [`switchContext()`](#switchcontext) | Switches between Native App and WebView contexts. |

#### <a id="getcontexts"></a>`getContexts()`

**Syntax:** `const contexts = await driver.getContexts()`

**Description:** Retrieves the list of available app contexts (e.g., `NATIVE_APP`, `WEBVIEW_com.example.app`).

**Example:**
```ts
const contexts: string[] = await driver.getContexts();
console.log(contexts); // ['NATIVE_APP', 'WEBVIEW_com.example.app']
```

**Tips:**
- Essential first step before switching into a WebView to interact with embedded web content in a hybrid app — this is the core of what this framework is built around.

---

#### <a id="switchcontext"></a>`switchContext()`

**Syntax:** `await driver.switchContext(contextName)`

**Description:** Switches the driver session between Native App and WebView contexts.

**Example:**
```ts
await driver.switchContext('WEBVIEW_com.example.app');
// ... interact with web elements ...
await driver.switchContext('NATIVE_APP');
```

**Tips:**
- Element selectors behave differently per context — native selectors (accessibility id, `android=`/`-ios predicate string:`) only work in `NATIVE_APP`; CSS/XPath web selectors only work inside a `WEBVIEW_*` context.

---

### Screenshots & Recording

| Action | Description |
| --- | --- |
| [`saveScreenshot()`](#savescreenshot) | Captures a screenshot of the app screen. |
| [`startRecordingScreen()`](#startrecordingscreen) | Starts recording the device screen. |
| [`stopRecordingScreen()`](#stoprecordingscreen) | Stops recording and returns the video. |

> `saveScreenshot()` is identical to the web version — see [Common Actions](#common-actions-web--mobile).

#### <a id="startrecordingscreen"></a>`startRecordingScreen()`

**Syntax:** `await driver.startRecordingScreen(options)`

**Description:** Starts recording the device screen as a video.

**Example:**
```ts
await driver.startRecordingScreen({ videoType: 'mp4' });
```

**Tips:**
- Useful for capturing full test-run evidence, especially for flaky/gesture-heavy tests that are hard to debug from screenshots alone.

---

#### <a id="stoprecordingscreen"></a>`stopRecordingScreen()`

**Syntax:** `const video = await driver.stopRecordingScreen()`

**Description:** Stops the screen recording and returns the video (typically base64-encoded).

**Example:**
```ts
import fs from 'node:fs';

const video: string = await driver.stopRecordingScreen();
fs.writeFileSync('./recording.mp4', Buffer.from(video, 'base64'));
```

**Tips:**
- Decode and save immediately after stopping, since the encoded video is only returned once.

---

### File Operations

| Action | Description |
| --- | --- |
| [Push File](#pushfile) | Uploads a file to the device. |
| [Pull File](#pullfile) | Downloads a file from the device. |

#### <a id="pushfile"></a>Push File

**Syntax:** `await driver.pushFile(devicePath, base64Data)`

**Description:** Uploads a file to the device's filesystem at the specified path.

**Example:**
```ts
await driver.pushFile('/sdcard/test.jpg', Buffer.from(imageData).toString('base64'));
```

**Tips:**
- Useful for pre-seeding device state (e.g., a photo for the app's gallery picker) before a test runs.

---

#### <a id="pullfile"></a>Pull File

**Syntax:** `const data = await driver.pullFile(devicePath)`

**Description:** Downloads a file from the device's filesystem (returned base64-encoded).

**Example:**
```ts
const data: string = await driver.pullFile('/sdcard/exported.pdf');
```

**Tips:**
- Useful for verifying an app correctly wrote/exported a file to device storage.

---

### Permissions

| Action | Description |
| --- | --- |
| [Grant Permissions](#grantpermissions) | Grants app permissions programmatically. |
| [Revoke Permissions](#revokepermissions) | Revokes previously granted app permissions. |

#### <a id="grantpermissions"></a>Grant Permissions

**Syntax:** `await driver.execute('mobile: changePermissions', { permissions, action: 'grant' })`

**Description:** Grants app permissions (camera, location, contacts, etc.) programmatically.

**Example:**
```ts
await driver.execute('mobile: changePermissions', {
  permissions: ['android.permission.CAMERA'],
  action: 'grant',
});
```

**Tips:**
- Skips the native OS permission dialog entirely — useful for testing post-permission-granted flows deterministically.

---

#### <a id="revokepermissions"></a>Revoke Permissions

**Syntax:** `await driver.execute('mobile: changePermissions', { permissions, action: 'revoke' })`

**Description:** Revokes previously granted app permissions.

**Example:**
```ts
await driver.execute('mobile: changePermissions', {
  permissions: ['android.permission.CAMERA'],
  action: 'revoke',
});
```

**Tips:**
- Use to test how the app handles a permission being denied or later revoked mid-use.

---

### Geolocation

| Action | Description |
| --- | --- |
| [`getGeoLocation()`](#getgeolocation) | Retrieves the device's simulated GPS location. |
| [`setGeoLocation()`](#setgeolocation) | Sets the device's simulated GPS location. |

#### <a id="getgeolocation"></a>`getGeoLocation()`

**Syntax:** `const location = await driver.getGeoLocation()`

**Description:** Retrieves the device's current (simulated) GPS coordinates.

**Example:**
```ts
const { latitude, longitude }: { latitude: number; longitude: number } = await driver.getGeoLocation();
```

**Tips:**
- Requires the emulator/simulator to support location simulation; real devices generally don't allow overriding GPS this way.

---

#### <a id="setgeolocation"></a>`setGeoLocation()`

**Syntax:** `await driver.setGeoLocation({ latitude, longitude, altitude })`

**Description:** Sets the device's simulated GPS location, for testing location-aware app behavior.

**Example:**
```ts
await driver.setGeoLocation({ latitude: 12.9716, longitude: 77.5946, altitude: 0 });
```

**Tips:**
- Great for deterministically testing geofencing, "nearby stores", or region-locked features without physically moving.

---

## Common Actions (Web & Mobile)

These commands share the exact same API on both `browser` (web) and `driver` (mobile) sessions in WebdriverIO — because a page object/helper written against this set behaves identically on both, it's the backbone of what makes this framework "hybrid."

| Action | Description |
| --- | --- |
| [`click()`](#click) | Clicks/taps on an element. |
| [`setValue()`](#setvalue) | Replaces the existing value with new text. |
| [`addValue()`](#addvalue) | Appends text to the existing value. |
| [`clearValue()`](#clearvalue) | Clears text from an input field. |
| [`selectByVisibleText()`](#selectbyvisibletext) | Selects a dropdown/picker option by visible text. |
| [`getText()`](#gettext) | Retrieves the visible text of an element. |
| [`getAttribute()`](#getattribute) | Retrieves the value of an element attribute. |
| [`isDisplayed()`](#isdisplayed) | Checks if an element is visible. |
| [`isEnabled()`](#isenabled) | Checks if an element is enabled. |
| [`isSelected()`](#isselected) | Checks if an element is selected. |
| [`waitForDisplayed()`](#waitfordisplayed) | Waits until an element becomes visible. |
| [`waitForClickable()`](#waitforclickable) | Waits until an element is clickable. |
| [`scrollIntoView()`](#scrollintoview) | Scrolls the element into view. |
| [`dragAndDrop()`](#draganddrop) | Drags an element to another location. |
| [`saveScreenshot()`](#savescreenshot) | Captures a screenshot. |
| [`execute()`](#execute) | Executes JavaScript or mobile scripts. |
| [`pause()`](#pause) | Pauses test execution for a specified duration. |

#### <a id="click"></a>`click()`

**Syntax:** `await element.click()`

**Description:** Clicks on an element (web) or taps it (mobile) — the same command resolves to the correct native action on both.

**Example:**
```ts
const button: WebdriverIO.Element = await $('#submit');
await button.click();
```

**Tips:**
- Automatically waits for the element to be clickable (visible, enabled, not obscured) before clicking.
- For elements outside the viewport, WDIO auto-scrolls into view first.

---

#### <a id="setvalue"></a>`setValue()`

**Syntax:** `await element.setValue(value)`

**Description:** Clears the existing value (if any) and sets new text into an input/textarea (web) or text field/picker (mobile).

**Example:**
```ts
const input: WebdriverIO.Element = await $('#username');
await input.setValue('anudeep');
```

**Tips:**
- Prefer this over `addValue()` when you want a clean, deterministic input state.
- Works on `<input>`, `<textarea>`, `contenteditable` elements (web), and native text fields/pickers (mobile).

---

#### <a id="addvalue"></a>`addValue()`

**Syntax:** `await element.addValue(value)`

**Description:** Adds/appends a value to an input element without clearing the existing content.

**Example:**
```ts
const input: WebdriverIO.Element = await $('#my-input');
await input.addValue('Hello World');
```

**Tips:**
- Appends text to existing input values.
- Can be used with both string and array inputs.
- Does not clear the existing value before adding — use `clearValue()` first if a clean slate is needed.

---

#### <a id="clearvalue"></a>`clearValue()`

**Syntax:** `await element.clearValue()`

**Description:** Clears the text/content from an input field.

**Example:**
```ts
await (await $('#search')).clearValue();
```

**Tips:**
- Good practice to call before `addValue()` when the field might already contain text (e.g., autofill).

---

#### <a id="selectbyvisibletext"></a>`selectByVisibleText()`

**Syntax:** `await element.selectByVisibleText(text)`

**Description:** Selects an option by its visible text — from a `<select>` dropdown on web, or a native picker widget on mobile.

**Example:**
```ts
const dropdown: WebdriverIO.Element = await $('#country');
await dropdown.selectByVisibleText('India');
```

**Tips:**
- Most readable/robust option-selection method since it matches what a user actually sees.

---

#### <a id="gettext"></a>`getText()`

**Syntax:** `const text = await element.getText()`

**Description:** Retrieves the visible (rendered) text content of an element.

**Example:**
```ts
const heading: string = await (await $('h1')).getText();
expect(heading).toBe('Welcome');
```

**Tips:**
- Only returns text that's actually rendered/visible — hidden text nodes are excluded.

---

#### <a id="getattribute"></a>`getAttribute()`

**Syntax:** `const value = await element.getAttribute(attributeName)`

**Description:** Retrieves the value of a specified attribute on an element.

**Example:**
```ts
const href: string = await (await $('a.link')).getAttribute('href');
```

**Tips:**
- Use for attributes like `href`, `src`, `data-*`, `aria-*`, `class`, etc. (web) or `content-desc`, `resource-id`, etc. (mobile).

---

#### <a id="isdisplayed"></a>`isDisplayed()`

**Syntax:** `const displayed = await element.isDisplayed()`

**Description:** Checks whether an element is currently visible.

**Example:**
```ts
const isVisible: boolean = await (await $('#banner')).isDisplayed();
expect(isVisible).toBe(true);
```

**Tips:**
- Returns `false` (not an error) if the element isn't visible — useful for conditional logic.

---

#### <a id="isenabled"></a>`isEnabled()`

**Syntax:** `const enabled = await element.isEnabled()`

**Description:** Checks whether an element is enabled (not disabled).

**Example:**
```ts
const enabled: boolean = await (await $('#submit')).isEnabled();
```

**Tips:**
- Useful for verifying form validation disables a submit button until required fields are filled.

---

#### <a id="isselected"></a>`isSelected()`

**Syntax:** `const selected = await element.isSelected()`

**Description:** Checks whether a checkbox, radio button, toggle, or option element is selected.

**Example:**
```ts
const checked: boolean = await (await $('#agree-checkbox')).isSelected();
```

**Tips:**
- Applies to checkboxes, radio buttons, and `<option>` elements within a `<select>` (web), and native switches/checkboxes (mobile).

---

#### <a id="waitfordisplayed"></a>`waitForDisplayed()`

**Syntax:** `await element.waitForDisplayed(options)`

**Description:** Waits until an element becomes visible, up to a timeout.

**Example:**
```ts
await (await $('#toast')).waitForDisplayed({ timeout: 5000 });
```

**Tips:**
- Preferred over `pause()` for synchronizing with async UI updates — avoids flaky, fixed-delay waits.
- Pass `{ reverse: true }` to wait until an element disappears instead.

---

#### <a id="waitforclickable"></a>`waitForClickable()`

**Syntax:** `await element.waitForClickable(options)`

**Description:** Waits until an element becomes clickable.

**Example:**
```ts
await (await $('#submit')).waitForClickable({ timeout: 5000 });
```

**Tips:**
- Combine before `click()` on elements that become clickable only after an animation or async render completes.

---

#### <a id="scrollintoview"></a>`scrollIntoView()`

**Syntax:** `await element.scrollIntoView(options)`

**Description:** Scrolls the page/screen so the element is within the visible viewport.

**Example:**
```ts
await (await $('#footer-link')).scrollIntoView();
```

**Tips:**
- Most actions (`click`, `setValue`) auto-scroll already; call explicitly when you need to scroll without interacting (e.g., for a screenshot).
- Accepts standard `scrollIntoView` options like `{ block: 'center' }` on web.

---

#### <a id="draganddrop"></a>`dragAndDrop()`

**Syntax:** `await element.dragAndDrop(targetElementOrOptions)`

**Description:** Drags an element and drops it onto another element or coordinates.

**Example:**
```ts
const source: WebdriverIO.Element = await $('#draggable');
const target: WebdriverIO.Element = await $('#droppable');
await source.dragAndDrop(target);
```

**Tips:**
- Some custom drag-and-drop widgets (built with complex JS libraries) need `browser.action('pointer')` instead for reliable simulation on web.
- On mobile, prefer this for simple element-to-element drags; fall back to the raw touch [Drag & Drop gesture](#gesture-draganddrop) for anything more custom.

---

#### <a id="savescreenshot"></a>`saveScreenshot()`

**Syntax:** `await browser.saveScreenshot(filepath)`

**Description:** Captures a screenshot of the current page/app screen and saves it to the given file path.

**Example:**
```ts
await browser.saveScreenshot('./screenshots/homepage.png');
```

**Tips:**
- Can also be called on an element (`element.saveScreenshot()`) to screenshot just that element.
- Same API works uniformly across web, native, and WebView contexts.

---

#### <a id="execute"></a>`execute()`

**Syntax:** `const result = await browser.execute(script, ...args)`

**Description:** Executes synchronous JavaScript in the browser context (web), or a `mobile:` native automation command (mobile).

**Example:**
```ts
// Web
const title: string = await browser.execute(() => document.title);

// Mobile
await driver.execute('mobile: scroll', { direction: 'down' });
```

**Tips:**
- On web, useful for accessing browser APIs not exposed by WDIO directly (e.g., `localStorage`).
- On mobile, this is the entry point for every `mobile: *` gesture and device command (scroll, pinch, permissions, battery info, etc.).
- Element arguments passed in are automatically converted to native elements inside the script.

---

#### <a id="pause"></a>`pause()`

**Syntax:** `await browser.pause(milliseconds)`

**Description:** Pauses test execution for a specified duration.

**Example:**
```ts
await browser.pause(1000);
```

**Tips:**
- Avoid using as a substitute for proper waits (`waitForDisplayed`, `waitUntil`) — it slows down suites and is still flaky under load.
- Acceptable for quick local debugging, not for committed test code.

---

## Session Management (Web & Mobile)

Additional cross-platform commands for controlling the WebDriver session itself — useful in hooks/setup-teardown code rather than individual test steps.

| Action | Description |
| --- | --- |
| [`browser.getPageSource()`](#getpagesource) | Returns the full page/screen source (HTML or XML). |
| [`browser.deleteSession()`](#deletesession) | Ends the current WebDriver session. |
| [`browser.reloadSession()`](#reloadsession) | Starts a fresh session, replacing the current one. |
| [`browser.setTimeout()`](#settimeout) | Configures implicit/page-load/script timeouts. |

#### <a id="getpagesource"></a>`browser.getPageSource()`

**Syntax:** `const source = await browser.getPageSource()`

**Description:** Returns the full source of the current page (HTML on web) or screen (XML on mobile).

**Example:**
```ts
const source: string = await browser.getPageSource();
expect(source).toContain('data-testid="checkout"');
```

**Tips:**
- Useful for debugging selector issues — dump the source when an element can't be found.
- On mobile, this is the fastest way to inspect the native accessibility tree without Appium Inspector.

---

#### <a id="deletesession"></a>`browser.deleteSession()`

**Syntax:** `await browser.deleteSession()`

**Description:** Ends the current WebDriver session, closing the browser/app and releasing the driver.

**Example:**
```ts
after(async (): Promise<void> => {
  await browser.deleteSession();
});
```

**Tips:**
- WDIO's test runner normally manages this automatically between test files — call manually only for custom session lifecycle needs.

---

#### <a id="reloadsession"></a>`browser.reloadSession()`

**Syntax:** `await browser.reloadSession()`

**Description:** Ends the current session and starts a brand-new one with the same capabilities.

**Example:**
```ts
await browser.reloadSession();
```

**Tips:**
- Useful for getting a completely clean browser/app state mid-suite without restarting the whole test run.

---

#### <a id="settimeout"></a>`browser.setTimeout()`

**Syntax:** `await browser.setTimeout({ implicit: 5000, pageLoad: 30000, script: 10000 })`

**Description:** Configures the session's implicit wait, page-load, and script timeouts.

**Example:**
```ts
await browser.setTimeout({ implicit: 5000 });
```

**Tips:**
- Prefer explicit waits (`waitForDisplayed`, `waitUntil`) over relying on a large implicit timeout — implicit waits apply globally and can mask real synchronization issues.

# Comprehensive WebdriverIO Keywords Reference

## Element Commands

### 1. `$$`

**Syntax:** `await $$(selector)`

**Description:** Finds multiple elements on the page using the given selector.

**Example:**

```javascript
const elements = await $$(".my-class");
console.log(elements.length);
```

**Tips:**

- Use for interacting with multiple elements matching a selector.
- Returns an array of ElementArrayFinder objects.
- Always use `await` as this is an asynchronous operation.

### 2. `$`

**Syntax:** `await $(selector)`

**Description:** Finds the first element on the page using the given selector.

**Example:**

```javascript
const element = await $(".my-class");
await element.click();
```

**Tips:**

- Use for interacting with the first matching element.
- Returns a single ElementFinder object.
- Remember to `await` both the element selection and any actions on the element.

### 3. `addValue`

**Syntax:** `await element.addValue(value)`

**Description:** Adds a value to an input element.

**Example:**

```javascript
const input = await $("#my-input");
await input.addValue("Hello World");
```

**Tips:**

- Appends text to existing input values.
- Can be used with both string and array inputs.
- Does not clear the existing value before adding.

### 4. `clearValue`

**Syntax:** `await element.clearValue()`

**Description:** Clears the value of an input or textarea element.

**Example:**

```javascript
const input = await $("#my-input");
await input.clearValue();
```

**Tips:**

- Use before setting a new value to ensure the field is empty.
- Consider `await element.setValue('')` as an alternative if `clearValue()` doesn't work.

### 5. `click`

**Syntax:** `await element.click()`

**Description:** Clicks on an element.

**Example:**

```javascript
const button = await $("#submit-button");
await button.click();
```

**Tips:**

- Ensures the element is in view before clicking.
- Can be used on any clickable element (buttons, links, checkboxes, etc.).

### 6. `custom$$`

**Syntax:** `await custom$$(selector, custom)`

**Description:** Allows the use of custom selector strategies for finding multiple elements.

**Example:**

```javascript
const elements = await custom$$("shadow", (selector) => {
  return document.querySelector(selector).shadowRoot.querySelectorAll("button");
});
```

**Tips:**

- Useful for complex DOM structures or custom frameworks.
- The custom function should return an array of elements.

### 7. `custom$`

**Syntax:** `await custom$(selector, custom)`

**Description:** Allows the use of custom selector strategies for finding a single element.

**Example:**

```javascript
const element = await custom$("mySelector", (selector) => {
  return document.querySelector(`[data-custom="${selector}"]`);
});
```

**Tips:**

- Returns only the first matching element.
- Useful for implementing company-specific selectors or complex logic.

### 8. `doubleClick`

**Syntax:** `await element.doubleClick()`

**Description:** Performs a double-click action on an element.

**Example:**

```javascript
const element = await $("#dblclick-area");
await element.doubleClick();
```

**Tips:**

- Useful for elements with specific double-click behavior.
- Ensure the element is clickable and in view before performing the action.

### 9. `dragAndDrop`

**Syntax:** `await element.dragAndDrop(target)`

**Description:** Drags an element to another element or by an offset.

**Example:**

```javascript
const draggable = await $("#draggable");
const droppable = await $("#droppable");
await draggable.dragAndDrop(droppable);
```

**Tips:**

- Can be used with either a target element or x/y coordinates.
- Make sure both source and target elements are visible and interactable.

### 10. `execute`

**Syntax:** `await browser.execute(script[, ...args])`

**Description:** Executes JavaScript in the context of the current page.

**Example:**

```javascript
const result = await browser.execute((a, b) => a + b, 1, 2);
console.log(result); // Outputs: 3
```

**Tips:**

- Useful for accessing page internals or performing complex operations.
- Can return values from the executed script.

### 11. `executeAsync`

**Syntax:** `await browser.executeAsync(script[, ...args])`

**Description:** Executes asynchronous JavaScript in the context of the current page.

**Example:**

```javascript
const result = await browser.executeAsync(
  (a, b, done) => {
    setTimeout(() => done(a + b), 1000);
  },
  1,
  2
);
console.log(result); // Outputs: 3 after 1 second
```

**Tips:**

- Useful for operations that involve callbacks or Promises.
- The last argument of the script function should be a callback to signal completion.

### 12. `getAttribute`

**Syntax:** `await element.getAttribute(attributeName)`

**Description:** Gets the value of a specified attribute of an element.

**Example:**

```javascript
const link = await $("a");
const href = await link.getAttribute("href");
console.log(href);
```

**Tips:**

- Returns `null` if the attribute doesn't exist.
- Useful for validating element properties set by JavaScript.

### 13. `getCSSProperty`

**Syntax:** `await element.getCSSProperty(propertyName)`

**Description:** Gets the computed style of an element.

**Example:**

```javascript
const element = await $("#my-element");
const color = await element.getCSSProperty("color");
console.log(color.value);
```

**Tips:**

- Returns an object with value, parsed, and type properties.
- Useful for validating styles applied dynamically.

### 14. `getComputedLabel`

**Syntax:** `await element.getComputedLabel()`

**Description:** Gets the computed label of an element.

**Example:**

```javascript
const element = await $("button");
const label = await element.getComputedLabel();
console.log(label);
```

**Tips:**

- Useful for accessibility testing.
- Works with ARIA labels and native labels.

### 15. `getComputedRole`

**Syntax:** `await element.getComputedRole()`

**Description:** Gets the computed ARIA role of an element.

**Example:**

```javascript
const element = await $("button");
const role = await element.getComputedRole();
console.log(role); // Outputs: 'button'
```

**Tips:**

- Useful for accessibility testing and validation.
- Returns the implicit role if no explicit role is set.

### 16. `getElement`

**Syntax:** `await $(selector).getElement()`

**Description:** Returns the underlying element object.

**Example:**

```javascript
const element = await $("button").getElement();
console.log(element);
```

**Tips:**

- Useful when you need to work with the raw element object.
- Rarely needed in most WebdriverIO scripts.

### 17. `getElements`

**Syntax:** `await $$(selector).getElements()`

**Description:** Returns an array of underlying element objects.

**Example:**

```javascript
const elements = await $$("button").getElements();
console.log(elements.length);
```

**Tips:**

- Useful when you need to work with raw element objects.
- Prefer WebdriverIO's chainable commands when possible.

### 18. `getHTML`

**Syntax:** `await element.getHTML()`

**Description:** Gets the HTML of an element.

**Example:**

```javascript
const element = await $("#my-element");
const html = await element.getHTML();
console.log(html);
```

**Tips:**

- Returns the outer HTML by default.
- Pass `false` as an argument to get inner HTML.

### 19. `getLocation`

**Syntax:** `await element.getLocation()`

**Description:** Gets the location of an element on the page.

**Example:**

```javascript
const element = await $("#my-element");
const location = await element.getLocation();
console.log(location.x, location.y);
```

**Tips:**

- Returns an object with x and y coordinates.
- Useful for layout testing and element positioning verification.

### 20. `getProperty`

**Syntax:** `await element.getProperty(propertyName)`

**Description:** Gets the JavaScript property of an element.

**Example:**

```javascript
const checkbox = await $('input[type="checkbox"]');
const isChecked = await checkbox.getProperty("checked");
console.log(isChecked);
```

**Tips:**

- Different from `getAttribute` - gets JavaScript properties, not HTML attributes.
- Useful for getting dynamic properties set by JavaScript.

### 21. `getSize`

**Syntax:** `await element.getSize()`

**Description:** Gets the size of an element.

**Example:**

```javascript
const element = await $("#my-element");
const size = await element.getSize();
console.log(size.width, size.height);
```

**Tips:**

- Returns an object with width and height properties.
- Useful for responsive design testing.

### 22. `getTagName`

**Syntax:** `await element.getTagName()`

**Description:** Gets the tag name of an element.

**Example:**

```javascript
const element = await $("#my-element");
const tagName = await element.getTagName();
console.log(tagName);
```

**Tips:**

- Returns the lowercase tag name.
- Useful for verifying correct element types.

### 23. `getText`

**Syntax:** `await element.getText()`

**Description:** Gets the visible text of an element.

**Example:**

```javascript
const element = await $("#my-element");
const text = await element.getText();
console.log(text);
```

**Tips:**

- Returns only visible text, excluding hidden elements.
- Trims whitespace from the beginning and end.

### 24. `getValue`

**Syntax:** `await element.getValue()`

**Description:** Gets the value of a form control.

**Example:**

```javascript
const input = await $("input");
const value = await input.getValue();
console.log(value);
```

**Tips:**

- Works with input, select, and textarea elements.
- For checkboxes and radio buttons, returns the value attribute if checked, otherwise null.

### 25. `isClickable`

**Syntax:** `await element.isClickable()`

**Description:** Checks if an element is clickable.

**Example:**

```javascript
const button = await $("#my-button");
const isClickable = await button.isClickable();
console.log(isClickable);
```

**Tips:**

- Considers visibility, enabled state, and overlapping elements.
- Useful for ensuring interactive elements are ready for user interaction.

### 26. `isDisplayed`

**Syntax:** `await element.isDisplayed()`

**Description:** Checks if an element is visible.

**Example:**

```javascript
const element = await $("#my-element");
const isVisible = await element.isDisplayed();
console.log(isVisible);
```

**Tips:**

- Considers CSS visibility, display, and opacity.
- Does not consider whether the element is in the viewport.

### 27. `isEnabled`

**Syntax:** `await element.isEnabled()`

**Description:** Checks if an element is enabled.

**Example:**

```javascript
const input = await $("input");
const isEnabled = await input.isEnabled();
console.log(isEnabled);
```

**Tips:**

- Useful for form controls and buttons.
- Returns `true` for elements that don't support the enabled/disabled state.

### 28. `isEqual`

**Syntax:** `await element.isEqual(otherElement)`

**Description:** Checks if two elements are equal.

**Example:**

```javascript
const elem1 = await $("#elem1");
const elem2 = await $("#elem2");
const isEqual = await elem1.isEqual(elem2);
console.log(isEqual);
```

**Tips:**

- Compares element references, not content or attributes.
- Useful when you need to confirm two selectors refer to the same element.

### 29. `isExisting`

**Syntax:** `await element.isExisting()`

**Description:** Checks if an element exists in the DOM.

**Example:**

```javascript
const element = await $("#my-element");
const exists = await element.isExisting();
console.log(exists);
```

**Tips:**

- Returns `true` even if the element is not visible.
- Useful for checking if elements have been removed from the DOM.

### 30. `isFocused`

**Syntax:** `await element.isFocused()`

**Description:** Checks if an element has focus.

**Example:**

```javascript
const input = await $("input");
const isFocused = await input.isFocused();
console.log(isFocused);
```

**Tips:**

- Useful for form validation and user input testing.
- Can be used to verify tabbing behavior.

### 31. `isSelected`

**Syntax:** `await element.isSelected()`

**Description:** Checks if a checkbox or radio button is selected.

**Example:**

```javascript
const checkbox = await $('input[type="checkbox"]');
const isSelected = await checkbox.isSelected();
console.log(isSelected);
```

**Tips:**

- Works with `<option>` elements in `<select>` as well.
- Always returns `false` for elements that don't support selection.

### 32. `isStable`

**Syntax:** `await element.isStable()`

**Description:** Checks if an element's position is stable (not moving).

**Example:**

```javascript
const element = await $("#my-element");
const isStable = await element.isStable();
console.log(isStable);
```

**Tips:**

- Useful for waiting for animations to complete.
- Takes into account small pixel differences due to browser rendering.

### 33. `moveTo`

**Syntax:** `await element.moveTo([{x, y}])`

**Description:** Moves the mouse to the element or a specific offset.

**Example:**

```javascript
const element = await $("#my-element");
await element.moveTo();
// or with offset
await element.moveTo({ x: 10, y: 10 });
```

**Tips:**

- Useful for triggering hover effects.
- Can be used in combination with click for more complex interactions.

### 34. `nextElement`

**Syntax:** `await element.nextElement()`

**Description:** Gets the immediately following sibling element.

**Example:**

```javascript
const element = await $("li");
const nextLi = await element.nextElement();
console.log(await nextLi.getText());
```

**Tips:**

- Useful for navigating list items or table rows.
- Returns `null` if there's no next sibling.

### 35. `parentElement`

**Syntax:** `await element.parentElement()`

**Description:** Gets the parent element.

**Example:**

```javascript
const child = await $("#child");
const parent = await child.parentElement();
console.log(await parent.getTagName());
```

**Tips:**

- Useful for traversing the DOM upwards.
- Can be chained to get grandparent, great-grandparent, etc.

### 36. `previousElement`

**Syntax:** `await element.previousElement()`

**Description:** Gets the immediately preceding sibling element.

**Example:**

```javascript
const element = await $("li:nth-child(2)");
const prevLi = await element.previousElement();
console.log(await prevLi.getText());
```

**Tips:**

- Useful for navigating list items or table rows backwards.
- Returns `null` if there's no previous sibling.

### 37. `react$$`

**Syntax:** `await react$$(selector)`

**Description:** Finds multiple React components.

**Example:**

```javascript
const buttons = await react$$("Button");
console.log(await buttons.length);
```

**Tips:**

- Useful for selecting React components by their name.
- Works with both functional and class components.
- Requires React Selector plugin to be configured.

### 38. `react$`

**Syntax:** `await react$(selector)`

**Description:** Finds a single React component.

**Example:**

```javascript
const button = await react$("Button");
await button.click();
```

**Tips:**

- Returns the first matching React component.
- Can select by component name, props, or state.
- Requires React Selector plugin to be configured.

### 39. `saveScreenshot`

**Syntax:** `await element.saveScreenshot(filename)`

**Description:** Saves a screenshot of a specific element.

**Example:**

```javascript
const element = await $("#my-element");
await element.saveScreenshot("./element-screenshot.png");
```

**Tips:**

- Useful for debugging or visual regression testing.
- Saves only the visible part of the element.
- Ensure the directory exists before saving.

### 40. `scrollIntoView`

**Syntax:** `await element.scrollIntoView()`

**Description:** Scrolls the element into the visible area of the browser window.

**Example:**

```javascript
const element = await $("#my-element");
await element.scrollIntoView();
```

**Tips:**

- Useful before interacting with elements that might be out of view.
- Can take options to control the scrolling behavior.

### 41. `selectByAttribute`

**Syntax:** `await element.selectByAttribute(attribute, value)`

**Description:** Selects an option in a `<select>` element based on its attribute.

**Example:**

```javascript
const select = await $("select");
await select.selectByAttribute("value", "option1");
```

**Tips:**

- Commonly used with 'value' attribute, but can work with any attribute.
- Throws an error if no matching option is found.

### 42. `selectByIndex`

**Syntax:** `await element.selectByIndex(index)`

**Description:** Selects an option in a `<select>` element by its index.

**Example:**

```javascript
const select = await $("select");
await select.selectByIndex(2);
```

**Tips:**

- Index is zero-based.
- Useful when the order of options is important.

### 43. `selectByVisibleText`

**Syntax:** `await element.selectByVisibleText(text)`

**Description:** Selects an option in a `<select>` element by its visible text.

**Example:**

```javascript
const select = await $("select");
await select.selectByVisibleText("Option 1");
```

**Tips:**

- Most intuitive way to select options.
- Case-sensitive and must match the text exactly.

### 44. `setValue`

**Syntax:** `await element.setValue(value)`

**Description:** Sets the value of an input field.

**Example:**

```javascript
const input = await $("input");
await input.setValue("Hello World");
```

**Tips:**

- Clears the field before setting the new value.
- Can be used with text inputs, textareas, and other editable fields.

### 45. `shadow$$`

**Syntax:** `await shadow$$(selector)`

**Description:** Finds multiple elements inside a shadow DOM.

**Example:**

```javascript
const shadowElements = await shadow$$("#shadow-host", "button");
console.log(await shadowElements.length);
```

**Tips:**

- Useful for working with Web Components.
- First argument is the shadow host selector, second is the element selector within the shadow DOM.

### 46. `shadow$`

**Syntax:** `await shadow$(selector)`

**Description:** Finds a single element inside a shadow DOM.

**Example:**

```javascript
const shadowElement = await shadow$("#shadow-host", "input");
await shadowElement.setValue("Hello Shadow DOM");
```

**Tips:**

- Returns the first matching element in the shadow DOM.
- Allows interaction with encapsulated shadow DOM elements.

### 47. `touchAction`

**Syntax:** `await browser.touchAction(actions)`

**Description:** Performs a touch action on the screen.

**Example:**

```javascript
await browser.touchAction([
  { action: "press", x: 100, y: 200 },
  { action: "moveTo", x: 300, y: 400 },
  "release",
]);
```

**Tips:**

- Useful for mobile testing scenarios.
- Can perform complex touch gestures like swipe, pinch, etc.

### 48. `waitForClickable`

**Syntax:** `await element.waitForClickable([options])`

**Description:** Waits for an element to be clickable.

**Example:**

```javascript
const button = await $("#my-button");
await button.waitForClickable({ timeout: 5000 });
```

**Tips:**

- Considers both visibility and enabled state.
- Useful before performing click actions.

### 49. `waitForDisplayed`

**Syntax:** `await element.waitForDisplayed([options])`

**Description:** Waits for an element to be displayed.

**Example:**

```javascript
const element = await $("#my-element");
await element.waitForDisplayed({ timeout: 5000 });
```

**Tips:**

- Checks if the element is visible in the viewport.
- Useful for waiting for elements to appear after AJAX calls.

### 50. `waitForEnabled`

**Syntax:** `await element.waitForEnabled([options])`

**Description:** Waits for an element to be enabled.

**Example:**

```javascript
const button = await $("#my-button");
await button.waitForEnabled({ timeout: 5000 });
```

**Tips:**

- Useful for form controls that may be disabled initially.
- Does not consider visibility, only the enabled state.

### 51. `waitForExist`

**Syntax:** `await element.waitForExist([options])`

**Description:** Waits for an element to exist in the DOM.

**Example:**

```javascript
const element = await $("#my-element");
await element.waitForExist({ timeout: 5000 });
```

**Tips:**

- Element doesn't need to be visible, just present in the DOM.
- Useful for waiting for elements to be added dynamically.

### 52. `waitForStable`

**Syntax:** `await element.waitForStable([options])`

**Description:** Waits for an element's position to become stable.

**Example:**

```javascript
const element = await $("#my-element");
await element.waitForStable({ timeout: 5000 });
```

**Tips:**

- Useful for waiting for animations to complete.
- Considers the element stable if its position doesn't change for a short period.

### 53. `waitUntil`

**Syntax:** `await browser.waitUntil(condition[, options])`

**Description:** Waits until a condition evaluates to true.

**Example:**

```javascript
await browser.waitUntil(
  async () => {
    const text = await $("#elem").getText();
    return text === "Expected Text";
  },
  { timeout: 5000, timeoutMsg: "Text never matched" }
);
```

**Tips:**

- Highly flexible, can be used for various custom wait conditions.
- Useful when built-in wait commands are not sufficient.

### 54. `action`

**Syntax:** `await browser.action('name')`

**Description:** Starts a new action chain.

**Example:**

```javascript
const elem = await $("#draggable");
await browser
  .action("drag")
  .move({ origin: elem })
  .press()
  .move({ x: 100, y: 100 })
  .release()
  .perform();
```

**Tips:**

- Useful for complex mouse or keyboard interactions.
- Must end with `.perform()` to execute the action chain.

### 55. `actions`

**Syntax:** `await browser.actions([options])`

**Description:** Performs multiple actions in parallel.

**Example:**

```javascript
await browser.actions([
  {
    type: "key",
    id: "keyboard",
    actions: [{ type: "keyDown", value: "Shift" }],
  },
  {
    type: "pointer",
    id: "mouse",
    actions: [{ type: "pointerMove", duration: 0, x: 100, y: 100 }],
  },
]);
```

**Tips:**

- Allows simultaneous keyboard and mouse actions.
- Useful for complex user interactions like drag-and-drop with modifier keys.

### 56. `addCommand`

**Syntax:** `browser.addCommand(commandName, function)`

**Description:** Adds a custom command to the browser or element object.

**Example:**

```javascript
browser.addCommand("getUrlAndTitle", async function () {
  return {
    url: await this.getUrl(),
    title: await this.getTitle(),
  };
});

const result = await browser.getUrlAndTitle();
console.log(result);
```

**Tips:**

- Useful for creating reusable functions.
- Can be chained with other WebdriverIO commands.

### 57. `addInitScript`

**Syntax:** `await browser.addInitScript(script)`

**Description:** Adds a script to be injected when a new page is loaded.

**Example:**

```javascript
await browser.addInitScript(() => {
  window.onbeforeunload = function () {
    return "Are you sure you want to leave?";
  };
});
```

**Tips:**

- Useful for setting up page behavior before tests run.
- Script runs before any other scripts on the page.

### 58. `call`

**Syntax:** `await browser.call(fn)`

**Description:** Executes a synchronous or asynchronous function.

**Example:**

```javascript
await browser.call(async () => {
  await someAsyncOperation();
  return "result";
});
```

**Tips:**

- Useful for executing custom logic within the test flow.
- Can be used to integrate external libraries or APIs.

### 59. `debug`

**Syntax:** `await browser.debug()`

**Description:** Pauses the test execution and allows debugging in the browser.

**Example:**

```javascript
await $("#myButton").click();
await browser.debug();
```

**Tips:**

- Allows interaction with the browser during test execution.
- Useful for troubleshooting failing tests.

### 60. `deleteCookies`

**Syntax:** `await browser.deleteCookies([names])`

**Description:** Deletes browser cookies.

**Example:**

```javascript
// Delete all cookies
await browser.deleteCookies();
// Delete specific cookies
await browser.deleteCookies(["cookie1", "cookie2"]);
```

**Tips:**

- Useful for cleaning up state between tests.
- Can target specific cookies or clear all cookies.

### 61. `downloadFile`

**Syntax:** `await browser.downloadFile(url)`

**Description:** Downloads a file from a given URL.

**Example:**

```javascript
const filePath = await browser.downloadFile("https://example.com/file.pdf");
console.log(filePath);
```

**Tips:**

- Returns the path to the downloaded file.
- Useful for testing download functionality.

### 62. `emulate`

**Syntax:** `await browser.emulate(device)`

**Description:** Emulates a specific device.

**Example:**

```javascript
await browser.emulate("iPhone X");
```

**Tips:**

- Useful for testing responsive designs.
- Available devices depend on the browser being used.

### 63. `getCookies`

**Syntax:** `await browser.getCookies([names])`

**Description:** Retrieves browser cookies.

**Example:**

```javascript
const cookies = await browser.getCookies();
console.log(cookies);
```

**Tips:**

- Can retrieve all cookies or specific ones by name.
- Useful for verifying cookie-based functionality.

### 64. `getPuppeteer`

**Syntax:** `await browser.getPuppeteer()`

**Description:** Gets the Puppeteer browser instance.

**Example:**

```javascript
const puppeteer = await browser.getPuppeteer();
const pages = await puppeteer.pages();
console.log(pages.length);
```

**Tips:**

- Allows direct access to Puppeteer API.
- Useful for advanced browser control scenarios.

### 65. `getWindowSize`

**Syntax:** `await browser.getWindowSize()`

**Description:** Gets the size of the browser window.

**Example:**

```javascript
const size = await browser.getWindowSize();
console.log(size.width, size.height);
```

**Tips:**

- Returns an object with width and height properties.
- Useful for responsive design testing.

### 66. `keys`

**Syntax:** `await browser.keys(keys)`

**Description:** Sends a sequence of key strokes to the active element.

**Example:**

```javascript
await browser.keys(["Enter"]);
```

**Tips:**

- Can send special keys like Enter, Tab, etc.
- Useful for keyboard navigation testing.

### 67. `mock`

**Syntax:** `await browser.mock(url)`

**Description:** Mocks a network request.

**Example:**

```javascript
await browser
  .mock("https://api.example.com/data")
  .respond({ data: "mocked data" }, { statusCode: 200 });
```

**Tips:**

- Useful for testing different API response scenarios.
- Can mock both successful and error responses.

### 68. `mockClearAll`

**Syntax:** `await browser.mockClearAll()`

**Description:** Clears all active mocks.

**Example:**

```javascript
await browser.mockClearAll();
```

**Tips:**

- Use to reset mock state between tests.
- Ensures clean slate for subsequent network requests.

### 69. `mockRestoreAll`

**Syntax:** `await browser.mockRestoreAll()`

**Description:** Restores all mocked requests to their original behavior.

**Example:**

```javascript
await browser.mockRestoreAll();
```

**Tips:**

- Use when you want to revert to actual network requests.
- Helpful for cleanup after mock-dependent tests.

### 70. `newWindow`

**Syntax:** `await browser.newWindow(url[, options])`

**Description:** Opens a new browser window or tab.

**Example:**

```javascript
await browser.newWindow("https://webdriver.io");
```

**Tips:**

- Can specify windowFeatures as options.
- Useful for testing multi-window scenarios.

### 71. `overwriteCommand`

**Syntax:** `browser.overwriteCommand(commandName, function[, isElement])`

**Description:** Overwrites an existing browser or element command.

**Example:**

```javascript
browser.overwriteCommand(
  "click",
  async function (origClickFunction, ...args) {
    console.log("Clicking element");
    await origClickFunction(...args);
  },
  true
);
```

**Tips:**

- Useful for adding logging or additional behavior to existing commands.
- Set `isElement` to true for element commands.

### 72. `pause`

**Syntax:** `await browser.pause(ms)`

**Description:** Pauses the execution for a specified amount of time.

**Example:**

```javascript
await browser.pause(2000); // Pause for 2 seconds
```

**Tips:**

- Use sparingly, prefer wait commands when possible.
- Useful for debugging or working around timing issues.

### 73. `reloadSession`

**Syntax:** `await browser.reloadSession()`

**Description:** Ends the current session and starts a new one with the same capabilities.

**Example:**

```javascript
await browser.reloadSession();
```

**Tips:**

- Useful for resetting the browser state completely.
- Faster than closing and reopening the browser.

### 74. `savePDF`

**Syntax:** `await browser.savePDF(filename[, options])`

**Description:** Saves the current page as a PDF file.

**Example:**

```javascript
await browser.savePDF("./output.pdf", { orientation: "landscape" });
```

**Tips:**

- Useful for generating reports or documentation.
- Options can include page size, margins, etc.
- Only works with headless Chrome or Firefox.

### 75. `saveRecordingScreen`

**Syntax:** `await browser.saveRecordingScreen(filepath)`

**Description:** Saves the recorded video of the test execution.

**Example:**

```javascript
await browser.saveRecordingScreen("./test-video.mp4");
```

**Tips:**

- Requires screen recording to be enabled in capabilities.
- Useful for debugging or documenting test runs.
- File format depends on the WebDriver implementation.

### 76. `saveScreenshot`

**Syntax:** `await browser.saveScreenshot(filename)`

**Description:** Takes a screenshot of the current browser window.

**Example:**

```javascript
await browser.saveScreenshot("./screenshot.png");
```

**Tips:**

- Useful for visual regression testing or debugging.
- Captures the entire viewport.
- Ensure the directory exists before saving.

### 77. `scroll`

**Syntax:** `await browser.scroll(x, y)` or `await element.scroll()`

**Description:** Scrolls to a particular set of coordinates or scrolls an element into view.

**Example:**

```javascript
// Scroll the window
await browser.scroll(0, 100);

// Scroll an element into view
const elem = await $("#myElement");
await elem.scroll();
```

**Tips:**

- Can be used on both the browser and element level.
- Useful for interacting with elements not in the viewport.

### 78. `setCookies`

**Syntax:** `await browser.setCookies(cookie)`

**Description:** Sets one or more cookies for the current page.

**Example:**

```javascript
await browser.setCookies([
  { name: "session_id", value: "12345" },
  { name: "user_pref", value: "dark_mode" },
]);
```

**Tips:**

- Can set multiple cookies at once.
- Useful for setting up test scenarios requiring specific cookie states.

### 79. `setTimeout`

**Syntax:** `await browser.setTimeout(type, milliseconds)`

**Description:** Sets the timeouts for various WebDriver operations.

**Example:**

```javascript
await browser.setTimeout({ pageLoad: 10000 });
```

**Tips:**

- Can set timeouts for 'script', 'pageLoad', 'implicit', etc.
- Useful for adjusting wait times for slow-loading pages or long-running scripts.

### 80. `setViewport`

**Syntax:** `await browser.setViewport(width, height)`

**Description:** Sets the viewport size of the browser.

**Example:**

```javascript
await browser.setViewport(1280, 720);
```

**Tips:**

- Useful for testing responsive designs.
- Does not change the window size, only the viewport.

### 81. `setWindowSize`

**Syntax:** `await browser.setWindowSize(width, height)`

**Description:** Sets the window size of the browser.

**Example:**

```javascript
await browser.setWindowSize(1024, 768);
```

**Tips:**

- Changes the actual window size, not just the viewport.
- Useful for testing different screen sizes.

### 82. `switchWindow`

**Syntax:** `await browser.switchWindow(urlOrTitleToMatch)`

**Description:** Switches to another window or tab.

**Example:**

```javascript
await browser.switchWindow("https://webdriver.io");
```

**Tips:**

- Can switch using partial URL or title.
- Useful when working with multiple tabs or windows.

### 83. `throttle`

**Syntax:** `await browser.throttle(config)`

**Description:** Throttles the network capabilities of the browser.

**Example:**

```javascript
await browser.throttle({
  latency: 1000,
  downloadThroughput: 500 * 1024,
  uploadThroughput: 500 * 1024,
});
```

**Tips:**

- Useful for testing app behavior under poor network conditions.
- Can simulate various network speeds and latencies.

### 84. `throttleCPU`

**Syntax:** `await browser.throttleCPU(rate)`

**Description:** Throttles the CPU capabilities of the browser.

**Example:**

```javascript
await browser.throttleCPU(4); // Throttle by a factor of 4
```

**Tips:**

- Simulates slower devices or high CPU load.
- Useful for testing performance under constrained conditions.

### 85. `throttleNetwork`

**Syntax:** `await browser.throttleNetwork(preset)`

**Description:** Applies predefined network throttling presets.

**Example:**

```javascript
await browser.throttleNetwork("Regular 3G");
```

**Tips:**

- Provides easy-to-use presets for common network conditions.
- Useful for quickly testing different network scenarios.

### 86. `touchAction`

**Syntax:** `await browser.touchAction(actions)`

**Description:** Performs a sequence of touch actions.

**Example:**

```javascript
await browser.touchAction([
  { action: "press", x: 100, y: 200 },
  { action: "moveTo", x: 300, y: 400 },
  "release",
]);
```

**Tips:**

- Useful for mobile testing scenarios.
- Can perform complex gestures like swipe, pinch, etc.

### 87. `uploadFile`

**Syntax:** `await browser.uploadFile(filepath)`

**Description:** Uploads a file to the browser.

**Example:**

```javascript
const filePath = "/path/to/file.jpg";
const remoteFilePath = await browser.uploadFile(filePath);
await $("#fileInput").setValue(remoteFilePath);
```

**Tips:**

- Returns a remote path that can be used with file input elements.
- Useful for testing file upload functionality.

### 88. `url`

**Syntax:** `await browser.url([url])`

**Description:** Navigate to a new URL or get the current URL.

**Example:**

```javascript
// Navigate to a URL
await browser.url("https://webdriver.io");

// Get current URL
const currentUrl = await browser.url();
console.log(currentUrl);
```

**Tips:**

- Can be used both to navigate and to retrieve the current URL.
- When used without arguments, it returns the current URL.

### 89. `waitUntil`

**Syntax:** `await browser.waitUntil(condition[, options])`

**Description:** Waits until a condition evaluates to true.

**Example:**

```javascript
await browser.waitUntil(
  async () => {
    const text = await $("#elem").getText();
    return text === "Expected Text";
  },
  {
    timeout: 5000,
    timeoutMsg: "Expected text to be different after 5s",
  }
);
```

**Tips:**

- Highly flexible, can be used for various custom wait conditions.
- Useful when built-in wait commands are not sufficient.
- Can specify custom timeout and error message.

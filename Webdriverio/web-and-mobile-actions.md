## Web Actions (Browser)

### Navigation

| Action               | Description                                        |
| -------------------- | -------------------------------------------------- |
| `browser.url()`      | Opens a specified URL in the browser.              |
| `browser.back()`     | Navigates to the previous page in browser history. |
| `browser.forward()`  | Navigates to the next page in browser history.     |
| `browser.refresh()`  | Reloads the current web page.                      |
| `browser.getUrl()`   | Returns the URL of the current page.               |
| `browser.getTitle()` | Returns the title of the current page.             |

---

### Browser Window

| Action                       | Description                                |
| ---------------------------- | ------------------------------------------ |
| `browser.maximizeWindow()`   | Maximizes the browser window.              |
| `browser.minimizeWindow()`   | Minimizes the browser window.              |
| `browser.fullscreenWindow()` | Opens the browser in fullscreen mode.      |
| `browser.setWindowSize()`    | Sets the browser window dimensions.        |
| `browser.getWindowSize()`    | Retrieves the current browser window size. |
| `browser.newWindow()`        | Opens a new browser window or tab.         |
| `browser.switchWindow()`     | Switches to another browser window or tab. |
| `browser.closeWindow()`      | Closes the current browser window.         |
| `browser.getWindowHandles()` | Returns all open browser window handles.   |

---

### Element Actions

| Action                  | Description                                   |
| ----------------------- | --------------------------------------------- |
| `click()`               | Clicks on an element.                         |
| `doubleClick()`         | Performs a double-click on an element.        |
| `rightClick()`          | Performs a right-click (context click).       |
| `setValue()`            | Replaces the existing value with new text.    |
| `addValue()`            | Appends text to the existing value.           |
| `clearValue()`          | Clears the text from an input field.          |
| `getValue()`            | Retrieves the value of an input field.        |
| `selectByVisibleText()` | Selects a dropdown option by visible text.    |
| `selectByIndex()`       | Selects a dropdown option by index.           |
| `selectByAttribute()`   | Selects a dropdown option using an attribute. |
| `scrollIntoView()`      | Scrolls the element into the visible area.    |
| `dragAndDrop()`         | Drags an element and drops it onto another.   |
| `moveTo()`              | Moves the mouse pointer over an element.      |
| `hover()`               | Hovers the mouse over an element.             |
| `uploadFile()`          | Uploads a local file to the browser.          |

---

### Element State

| Action               | Description                               |
| -------------------- | ----------------------------------------- |
| `isDisplayed()`      | Checks if an element is visible.          |
| `isEnabled()`        | Checks if an element is enabled.          |
| `isClickable()`      | Checks if an element can be clicked.      |
| `isSelected()`       | Checks if an element is selected.         |
| `isExisting()`       | Checks if an element exists in the DOM.   |
| `waitForDisplayed()` | Waits until an element becomes visible.   |
| `waitForClickable()` | Waits until an element becomes clickable. |
| `waitForEnabled()`   | Waits until an element becomes enabled.   |
| `waitForExist()`     | Waits until an element exists in the DOM. |

---

### Text & Attributes

| Action             | Description                                 |
| ------------------ | ------------------------------------------- |
| `getText()`        | Retrieves the visible text of an element.   |
| `getHTML()`        | Retrieves the HTML source of an element.    |
| `getAttribute()`   | Retrieves the value of an attribute.        |
| `getCSSProperty()` | Retrieves the value of a CSS property.      |
| `getTagName()`     | Returns the HTML tag name of an element.    |
| `getLocation()`    | Returns the element's X and Y coordinates.  |
| `getSize()`        | Returns the width and height of an element. |
| `getRect()`        | Returns the element's position and size.    |

---

### Keyboard Actions

| Action                  | Description                         |
| ----------------------- | ----------------------------------- |
| `browser.keys()`        | Sends one or more keyboard keys.    |
| `browser.action('key')` | Performs advanced keyboard actions. |

---

### Mouse Actions

| Action                      | Description                             |
| --------------------------- | --------------------------------------- |
| `browser.action('pointer')` | Performs advanced mouse interactions.   |
| Click                       | Simulates a mouse click.                |
| Double Click                | Simulates a double-click.               |
| Right Click                 | Opens the context menu.                 |
| Hover                       | Moves the mouse over an element.        |
| Drag & Drop                 | Drags an item to another location.      |
| Move Mouse                  | Moves the mouse to a specific position. |

---

### Cookies

| Action            | Description                  |
| ----------------- | ---------------------------- |
| `setCookies()`    | Adds cookies to the browser. |
| `getCookies()`    | Retrieves browser cookies.   |
| `deleteCookies()` | Deletes browser cookies.     |

---

### Alerts

| Action            | Description                      |
| ----------------- | -------------------------------- |
| `acceptAlert()`   | Accepts a browser alert.         |
| `dismissAlert()`  | Dismisses a browser alert.       |
| `getAlertText()`  | Retrieves the alert message.     |
| `sendAlertText()` | Enters text into a prompt alert. |

---

### Frames

| Action                  | Description                        |
| ----------------------- | ---------------------------------- |
| `switchFrame()`         | Switches to a specified iframe.    |
| `switchToParentFrame()` | Switches back to the parent frame. |

---

### JavaScript

| Action           | Description                         |
| ---------------- | ----------------------------------- |
| `execute()`      | Executes JavaScript in the browser. |
| `executeAsync()` | Executes asynchronous JavaScript.   |

---

### Screenshots

| Action             | Description                                |
| ------------------ | ------------------------------------------ |
| `saveScreenshot()` | Captures a screenshot of the current page. |

---

### Waits

| Action        | Description                            |
| ------------- | -------------------------------------- |
| `waitUntil()` | Waits until a custom condition is met. |
| `pause()`     | Pauses execution for a specified time. |

---

### Network (Chromium)

| Action                | Description                              |
| --------------------- | ---------------------------------------- |
| Request Interception  | Intercepts outgoing network requests.    |
| Mock API Responses    | Mocks backend API responses.             |
| Capture Network Calls | Monitors network requests and responses. |

---

# Mobile Actions (Appium + WDIO)

### App Management

| Action             | Description                                |
| ------------------ | ------------------------------------------ |
| `activateApp()`    | Brings an installed app to the foreground. |
| `terminateApp()`   | Closes a running application.              |
| `installApp()`     | Installs an application on the device.     |
| `removeApp()`      | Uninstalls an application from the device. |
| `isAppInstalled()` | Checks whether an app is installed.        |
| `queryAppState()`  | Returns the current state of an app.       |

---

### Device Actions

| Action                       | Description                              |
| ---------------------------- | ---------------------------------------- |
| `lock()`                     | Locks the device screen.                 |
| `unlock()`                   | Unlocks the device screen.               |
| `isLocked()`                 | Checks whether the device is locked.     |
| `getOrientation()`           | Returns the current screen orientation.  |
| `setOrientation()`           | Changes the device orientation.          |
| `pressKeyCode()` *(Android)* | Simulates an Android hardware key press. |
| `back()` *(Android)*         | Simulates the Android Back button.       |
| `hideKeyboard()`             | Hides the on-screen keyboard.            |

---

### Touch Gestures

| Action      | Description                                  |
| ----------- | -------------------------------------------- |
| Tap         | Performs a single tap on an element.         |
| Long Press  | Presses and holds an element.                |
| Swipe Up    | Swipes upward on the screen.                 |
| Swipe Down  | Swipes downward on the screen.               |
| Swipe Left  | Swipes left across the screen.               |
| Swipe Right | Swipes right across the screen.              |
| Scroll      | Scrolls the screen until content is visible. |
| Drag & Drop | Drags an element to a target location.       |
| Pinch       | Performs a pinch-in or pinch-out gesture.    |
| Zoom        | Zooms into an element using gestures.        |
| Fling       | Performs a fast swipe with momentum.         |
| Double Tap  | Performs two quick taps on an element.       |

---

### Picker/Wheel Actions

| Action                                   | Description                               |
| ---------------------------------------- | ----------------------------------------- |
| `setValue()`                             | Sets a value in a picker or text field.   |
| `selectByVisibleText()`                  | Selects a visible option from a picker.   |
| `mobile: selectPickerWheelValue` *(iOS)* | Changes the value of an iOS picker wheel. |

---

### Clipboard

| Action           | Description                               |
| ---------------- | ----------------------------------------- |
| `setClipboard()` | Copies data to the device clipboard.      |
| `getClipboard()` | Retrieves data from the device clipboard. |

---

### Notifications

| Action                            | Description                           |
| --------------------------------- | ------------------------------------- |
| `openNotifications()` *(Android)* | Opens the Android notification panel. |

---

### Biometrics

| Action                     | Description                                               |
| -------------------------- | --------------------------------------------------------- |
| Fingerprint Authentication | Simulates fingerprint authentication.                     |
| Face ID Authentication     | Simulates Face ID authentication on supported simulators. |

---

### Device Information

| Action             | Description                                    |
| ------------------ | ---------------------------------------------- |
| Battery Info       | Retrieves the device battery status.           |
| Device Time        | Retrieves the current device time.             |
| Screen Orientation | Retrieves the current screen orientation.      |
| App State          | Retrieves the current state of an application. |

---

### Context Switching (Hybrid Apps)

| Action            | Description                                        |
| ----------------- | -------------------------------------------------- |
| `getContexts()`   | Retrieves available app contexts (Native/WebView). |
| `switchContext()` | Switches between Native App and WebView contexts.  |

---

### Screenshots & Recording

| Action                   | Description                              |
| ------------------------ | ---------------------------------------- |
| `saveScreenshot()`       | Captures a screenshot of the app screen. |
| `startRecordingScreen()` | Starts recording the device screen.      |
| `stopRecordingScreen()`  | Stops recording and returns the video.   |

---

### File Operations

| Action    | Description                       |
| --------- | --------------------------------- |
| Push File | Uploads a file to the device.     |
| Pull File | Downloads a file from the device. |

---

### Permissions

| Action             | Description                                 |
| ------------------ | ------------------------------------------- |
| Grant Permissions  | Grants app permissions programmatically.    |
| Revoke Permissions | Revokes previously granted app permissions. |

---

# Common Actions (Web & Mobile)

| Action               | Description                                     |
| -------------------- | ----------------------------------------------- |
| `click()`            | Clicks on an element.                           |
| `setValue()`         | Enters text into an input field.                |
| `addValue()`         | Appends text to an existing value.              |
| `clearValue()`       | Clears text from an input field.                |
| `getText()`          | Retrieves the visible text of an element.       |
| `getAttribute()`     | Retrieves the value of an element attribute.    |
| `isDisplayed()`      | Checks if an element is visible.                |
| `isEnabled()`        | Checks if an element is enabled.                |
| `isSelected()`       | Checks if an element is selected.               |
| `waitForDisplayed()` | Waits until an element becomes visible.         |
| `waitForClickable()` | Waits until an element is clickable.            |
| `scrollIntoView()`   | Scrolls the element into view.                  |
| `dragAndDrop()`      | Drags an element to another location.           |
| `saveScreenshot()`   | Captures a screenshot.                          |
| `execute()`          | Executes JavaScript or mobile scripts.          |
| `pause()`            | Pauses test execution for a specified duration. |

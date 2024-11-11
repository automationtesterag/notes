In Appium, you can automate actions for **native apps** (iOS/Android), **web apps** (mobile browser), and **hybrid apps** (apps with both native and webview components). Here’s a comprehensive list, organized by app type, with actions that apply specifically to native, web, or hybrid apps included.

---

### 1. **Element Interactions (All App Types)**

- **Tap/Click**: Tap or click on an element.
- **Double Tap/Double Click**: Perform a double-tap gesture.
- **Long Press**: Press and hold on an element.
- **Clear**: Clear the text content of an input field.
- **Set Value/Send Keys**: Enter text into an input field.
- **Get Text**: Retrieve the text from an element.
- **Get Attribute**: Retrieve an attribute of an element (e.g., value, name).
- **Get Location**: Get the x, y coordinates of an element.
- **Get Size**: Get the height and width of an element.
- **Check Displayed/Enabled/Selected**: Verify if an element is displayed, enabled, or selected.

### 2. **Gestures (Native & Hybrid Apps)**

- **Swipe**: Swipe left, right, up, or down.
- **Scroll**: Scroll within the app or in specific elements.
- **Drag and Drop**: Drag an element to another location or onto another element.
- **Pinch and Zoom**: Pinch to zoom in and out on an element.
- **Multi-touch Actions**: Perform simultaneous multi-touch gestures, like two-finger actions.

### 3. **Device Interactions (Native & Hybrid Apps)**

- **Lock/Unlock Device**: Lock or unlock the screen.
- **Press/Release Button**: Simulate pressing device buttons (Home, Back, Volume).
- **Rotate Device**: Change screen orientation (portrait or landscape).
- **Shake Device**: Simulate shaking the device.
- **Open Notifications (Android)**: Open the notification center.
- **Background App**: Send the app to the background.
- **Activate App**: Bring the app to the foreground.
- **Close App**: Close the app.
- **Launch App**: Start the app.

### 4. **Clipboard Interactions (Native & Hybrid Apps)**

- **Set Clipboard Text**: Set text to the device clipboard.
- **Get Clipboard Text**: Retrieve text from the clipboard.

### 5. **App Management (Native & Hybrid Apps)**

- **Install App**: Install an app on the device.
- **Uninstall App**: Remove an app from the device.
- **Launch App by Package/Bundle ID**: Launch an app by its identifier.
- **Terminate App**: Stop an app by its package or bundle ID.
- **Activate App**: Bring a specified app to the foreground.

### 6. **File Management (Native & Hybrid Apps)**

- **Push File**: Upload a file to the device.
- **Pull File**: Download a file from the device.
- **Pull Folder**: Download a folder from the device.

### 7. **Session Management (All App Types)**

- **Start Session**: Start a new Appium session.
- **End Session**: Terminate an active Appium session.

### 8. **Performance and Logs (All App Types)**

- **Get Device Time**: Retrieve the current device time.
- **Get Logs**: Retrieve logs (e.g., crash, performance).
- **Start/Stop Screen Recording**: Record a video of the screen.
- **Capture Screenshot**: Take a screenshot.
- **Get Performance Data**: Access performance metrics (e.g., memory, CPU).

### 9. **Touch Actions (for Advanced Gestures in Native & Hybrid Apps)**

- **Touch Down/Touch Up**: Press down or release at coordinates.
- **Move To**: Move to specific coordinates.
- **Wait**: Pause action sequences.
- **Tap/Double Tap/Long Press on Coordinates**: Perform gestures at specific coordinates.

### 10. **Network Interactions (Native & Hybrid Apps)**

- **Toggle Airplane Mode**: Enable or disable airplane mode.
- **Toggle Wi-Fi**: Turn Wi-Fi on or off.
- **Toggle Data**: Enable or disable cellular data.
- **Toggle Location Services**: Toggle location services.
- **Set Geo-Location**: Set the latitude and longitude.

### 11. **Biometric Authentication (iOS & Android Native Apps)**

- **Fingerprint (Android)**: Simulate fingerprint scanning.
- **Face ID/Touch ID (iOS)**: Simulate Face ID or Touch ID verification.

### 12. **Browser Interactions (Mobile Web & Hybrid Apps in Web Context)**

- **Execute JavaScript**: Run JavaScript code on the page.
- **Navigate to URL**: Open a URL in the browser.
- **Refresh Page**: Reload the page.
- **Get/Set Cookies**: Access or modify cookies.
- **Switch Context**: Switch between native and web contexts.
- **Get/Set Window Size**: Adjust window size in web contexts.
- **Close/Maximize/Minimize Window**: Manage browser window state.

### 13. **Other Actions (All App Types)**

- **Wait for Element**: Wait until an element is present or visible.
- **Switch To Frame**: Focus on an iframe within the app.
- **Alert Handling**: Accept, dismiss, or interact with alerts or pop-ups.

---

These actions cover the full range of available interactions across native, web, and hybrid applications in Appium, allowing versatile testing capabilities across all platforms.

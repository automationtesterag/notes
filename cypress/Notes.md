# Config

Here’s a comprehensive list of parameters that can be used in `cypress.config.js` with detailed explanations and comments for each.

### **Complete List of Parameters in `cypress.config.js`:**

```js
module.exports = {
  e2e: {
    // =========================
    // Basic Test Configurations
    // =========================

    baseUrl: "http://localhost:3000", // Base URL for all relative URLs used in tests.

    specPattern: "cypress/e2e/**/*.cy.js", // Glob pattern to specify location of test files.

    excludeSpecPattern: "*.skip.cy.js", // Glob pattern to exclude test files from being run.

    supportFile: "cypress/support/e2e.js", // Path to the support file with reusable test code.

    indexHtmlFile: "cypress/support/index.html", // Custom index.html to use when visiting non-app URLs.

    fixturesFolder: "cypress/fixtures", // Path to the fixtures folder for static test data.

    videosFolder: "cypress/videos", // Folder to store recorded videos.

    screenshotsFolder: "cypress/screenshots", // Folder to store screenshots taken during test runs.

    downloadsFolder: "cypress/downloads", // Folder to store downloaded files during tests.

    // =========================
    // Timeouts and Retries
    // =========================

    defaultCommandTimeout: 10000, // Time (ms) to wait for commands to complete before failing.

    pageLoadTimeout: 60000, // Time (ms) to wait for page to fully load before timing out.

    execTimeout: 60000, // Time (ms) to wait for a system command (cy.exec()) to complete.

    requestTimeout: 5000, // Time (ms) to wait for a cy.request() to complete.

    responseTimeout: 30000, // Time (ms) to wait for a server response during cy.wait().

    retries: {
      runMode: 2, // Number of retries during `cypress run`.
      openMode: 1, // Number of retries during `cypress open`.
    },

    // =========================
    // Browser and Viewport
    // =========================

    viewportWidth: 1280, // Default browser window width in pixels.

    viewportHeight: 720, // Default browser window height in pixels.

    chromeWebSecurity: false, // Disable security checks for accessing cross-origin resources.

    // =========================
    // Screenshots and Videos
    // =========================

    screenshotOnRunFailure: true, // Capture screenshots when a test fails.

    video: true, // Enable or disable video recording for tests.

    videoCompression: 32, // Compression quality (0-100) for video.

    videoUploadOnPasses: false, // Upload videos even if tests pass.

    // =========================
    // Environment Variables
    // =========================

    env: {
      apiUrl: "https://api.example.com", // Custom environment variable for API base URL.
      loginUsername: "testUser", // Example variable for login credentials.
    },

    // =========================
    // Plugins and Node Events
    // =========================

    setupNodeEvents(on, config) {
      // Register custom tasks, plugins, and event listeners here.
      on("task", {
        log(message) {
          console.log(message); // Log messages to the terminal.
          return null;
        },
      });
    },

    // =========================
    // Network and File Handling
    // =========================

    modifyObstructiveCode: false, // Disable modifications to third-party JS code for compatibility.

    experimentalFetchPolyfill: true, // Enable polyfill for Fetch API.

    experimentalSessionAndOrigin: true, // Enable experimental session and origin handling.

    experimentalWebKitSupport: true, // Enable WebKit (Safari) support.

    fileServerFolder: "cypress/static", // Serve static files from this folder.

    watchForFileChanges: false, // Disable automatic re-runs on file changes.

    // =========================
    // Test Isolation and Scoping
    // =========================

    experimentalStudio: true, // Enable Cypress Studio for creating tests in the GUI.

    scrollBehavior: "center", // Scroll to elements before actions ('top', 'center', 'bottom', false).

    experimentalRunAllSpecs: true, // Run all specs in a single browser session.

    testIsolation: true, // Reset application state between tests.

    trashAssetsBeforeRuns: true, // Clear screenshots and videos before each run.
  },
};
```

---

### **Key Notes:**

- **`e2e` Section:** Contains all the configurations specifically for E2E (end-to-end) tests.
- **Timeouts:** Adjust timeouts based on the speed of your application and network conditions.
- **Retries:** Helps make tests more robust by retrying failed tests automatically.
- **Environment Variables:** Use `env` to manage secrets, credentials, or different configurations for different environments.
- **Experimental Features:** Be cautious when enabling experimental features; they may change in future releases.

Cypress provides several command-line interface (CLI) options to control how tests are run. You can customize test execution by specifying options such as the browser, configuration files, environment variables, and more.

Here’s a detailed guide to the various options you can use when running Cypress tests from the CLI.

---

### **Basic Command to Run Cypress Tests:**

```bash
npx cypress run
```

This command launches Cypress in **headless** mode, running tests in the terminal.

---

### **Common CLI Options:**

1. **Run Specific Browser:**

   ```bash
   npx cypress run --browser chrome
   ```

   - Options: `chrome`, `electron`, `edge`, `firefox`, `brave` (if supported).
   - Default: Electron (Cypress’s built-in browser).

2. **Run Specific Spec File:**

   ```bash
   npx cypress run --spec "cypress/e2e/example.cy.js"
   ```

   - Supports glob patterns to match multiple spec files:

   ```bash
   npx cypress run --spec "cypress/e2e/**/*.cy.js"
   ```

3. **Run in Headed Mode (With Browser UI):**

   ```bash
   npx cypress run --headed
   ```

4. **Specify Config File:**

   ```bash
   npx cypress run --config-file cypress.config.js
   ```

5. **Pass Configuration Values Directly:**

   ```bash
   npx cypress run --config baseUrl=http://localhost:3000,viewportWidth=1280,viewportHeight=720
   ```

6. **Set Environment Variables:**

   ```bash
   npx cypress run --env apiUrl=https://api.example.com,username=testUser
   ```

   Access in tests using:

   ```js
   Cypress.env("apiUrl");
   ```

7. **Run Tests in Parallel:**

   ```bash
   npx cypress run --parallel --ci-build-id <build-id>
   ```

   - Requires a Cypress Dashboard account.

8. **Record Test Results:**

   ```bash
   npx cypress run --record --key <dashboard-key>
   ```

9. **Run Tests with Tags:**
   Use `cypress-grep` plugin for tags:

   ```bash
   npx cypress run --env grep="smoke"
   ```

10. **Change Reporter:**

    ```bash
    npx cypress run --reporter mochawesome
    ```

11. **Output Folder for Videos:**

    ```bash
    npx cypress run --output-path=cypress/videos
    ```

12. **Disable Video Recording:**

    ```bash
    npx cypress run --video=false
    ```

13. **Disable Screenshot on Failure:**

    ```bash
    npx cypress run --screenshot-on-run-failure=false
    ```

14. **Filter by Test Name:**

    ```bash
    npx cypress run --grep "Login Test"
    ```

15. **Run in a Different Configuration (E2E vs Component Testing):**
    ```bash
    npx cypress run --component
    ```

---

### **Full Example Command:**

```bash
npx cypress run --spec "cypress/e2e/**/*.cy.js" --browser firefox --headed --env apiUrl=https://api.example.com --config baseUrl=http://localhost:3000,viewportWidth=1440 --record --key <your-dashboard-key>
```

---

### **Interactive Mode (GUI):**

If you prefer an interactive mode, use:

```bash
npx cypress open
```

This launches Cypress's Test Runner with a GUI, allowing you to pick and run tests interactively.

---

### **List of Useful Flags:**

| **Flag**             | **Description**                                           |
| -------------------- | --------------------------------------------------------- |
| `--headless`         | Runs tests in headless mode.                              |
| `--headed`           | Runs tests with the browser UI visible.                   |
| `--browser`          | Specifies the browser to use.                             |
| `--spec`             | Runs a specific test file or files matching a pattern.    |
| `--config`           | Overrides configuration values.                           |
| `--env`              | Sets environment variables.                               |
| `--parallel`         | Runs tests in parallel.                                   |
| `--ci-build-id`      | Specifies a unique build ID for parallel runs.            |
| `--record`           | Records the run on Cypress Dashboard.                     |
| `--key`              | Cypress Dashboard project key.                            |
| `--reporter`         | Specifies a reporter (e.g., `mochawesome`).               |
| `--reporter-options` | Options for the specified reporter.                       |
| `--tag`              | Tags the run (useful for grouping runs on the Dashboard). |
| `--output-path`      | Specifies the folder for saving videos or reports.        |

---

To perform **parallel testing** and **cross-browser testing** in Cypress **without using the Cypress Dashboard**, you can rely on other tools or custom configurations that allow you to achieve parallelism and browser diversity. Below, I'll explain how you can do this, covering both parallel and cross-browser testing without the need for the Cypress Dashboard.

### **1. Parallel Testing Without Cypress Dashboard:**

Cypress doesn’t support parallel test execution natively without using the Cypress Dashboard service, but you can still run tests in parallel across multiple machines or processes using a **custom script** or **third-party tools** like **Cypress Parallel**, **CI pipelines**, or **`cypress-mochawesome-reporter`**.

#### **Using Node.js and `cypress-parallel` Plugin (Alternative to Dashboard):**

There are third-party tools like `cypress-parallel` which help you run tests in parallel without the Dashboard.

##### **Steps:**

1. Install the `cypress-parallel` package:

   ```bash
   npm install --save-dev cypress-parallel
   ```

2. Modify the `package.json` to run the parallel tests.
   You can add a script in `package.json` to execute tests across multiple processes:

   ```json
   {
     "scripts": {
       "test:parallel": "cypress-parallel --spec 'cypress/e2e/**/*.cy.js' --parallel 4"
     }
   }
   ```

   This command will run your tests in 4 parallel processes.

3. Run the script:
   ```bash
   npm run test:parallel
   ```

#### **Using CI/CD Pipelines (e.g., GitHub Actions or GitLab CI):**

In a CI environment, you can split your test specs into multiple jobs and run them in parallel.

Example with **GitHub Actions**:

```yaml
name: Run Cypress Tests

on:
  push:
    branches:
      - main

jobs:
  job1:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2
      - name: Install dependencies
        run: npm install
      - name: Run tests for spec 1
        run: npx cypress run --spec 'cypress/e2e/spec1.cy.js'

  job2:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2
      - name: Install dependencies
        run: npm install
      - name: Run tests for spec 2
        run: npx cypress run --spec 'cypress/e2e/spec2.cy.js'

  job3:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2
      - name: Install dependencies
        run: npm install
      - name: Run tests for spec 3
        run: npx cypress run --spec 'cypress/e2e/spec3.cy.js'
```

Each job runs a different spec file, achieving parallelism.

### **2. Cross-Browser Testing Without Dashboard:**

Cypress allows you to run tests in **multiple browsers**, but this requires you to run the tests across multiple processes, as Cypress doesn’t have a built-in way to handle cross-browser testing in parallel on the same machine without the Dashboard.

Here’s how you can set it up to run in different browsers:

#### **Run in Chrome, Firefox, and Edge (or Any Other Browser):**

1. **Run Cypress Tests in Chrome:**

   ```bash
   npx cypress run --browser chrome --spec 'cypress/e2e/**/*.cy.js'
   ```

2. **Run Cypress Tests in Firefox:**

   ```bash
   npx cypress run --browser firefox --spec 'cypress/e2e/**/*.cy.js'
   ```

3. **Run Cypress Tests in Edge:**

   ```bash
   npx cypress run --browser edge --spec 'cypress/e2e/**/*.cy.js'
   ```

4. **Run All Browsers in Parallel Using a Custom Script:**
   You can use Node.js to execute tests in multiple browsers in parallel by using the `Promise.all` approach or `child_process` to run multiple commands concurrently.

   **Example using Node.js:**

   Create a script `runTests.js`:

   ```js
   const { exec } = require("child_process");

   const browsers = ["chrome", "firefox", "edge"];
   const specs = "cypress/e2e/**/*.cy.js";

   const runTestsInBrowser = (browser) => {
     return new Promise((resolve, reject) => {
       exec(
         `npx cypress run --browser ${browser} --spec ${specs}`,
         (error, stdout, stderr) => {
           if (error) {
             reject(`Error running tests in ${browser}: ${stderr}`);
           }
           resolve(stdout);
         }
       );
     });
   };

   const runTests = async () => {
     try {
       const results = await Promise.all(browsers.map(runTestsInBrowser));
       console.log("Tests ran successfully in all browsers:", results);
     } catch (error) {
       console.error("Error:", error);
     }
   };

   runTests();
   ```

   Then, run the script with:

   ```bash
   node runTests.js
   ```

This will run the same spec files in **parallel** across **multiple browsers**.

### **Summary:**

1. **Parallel Testing without Dashboard:**

   - Use third-party tools like `cypress-parallel` or set up your own parallel execution in CI pipelines.
   - In CI, you can split your tests into multiple jobs and run them in parallel.

2. **Cross-Browser Testing:**
   - Cypress supports running tests in multiple browsers like Chrome, Firefox, Edge, and others.
   - For running across browsers simultaneously, use a custom script to trigger tests in different browsers in parallel.

Would you like to explore how to implement cross-browser testing in more detail or further automation options?

Cypress provides a comprehensive set of commands to interact with your application, assert conditions, manipulate data, and handle various aspects of testing. Below is a list of the most commonly used Cypress commands along with examples:

---

### **1. Navigation & Interactions**

- **`cy.visit()`**  
  Opens a URL in the browser.

  ```js
  cy.visit("https://example.com");
  ```

- **`cy.get()`**  
  Selects one or more DOM elements.

  ```js
  cy.get("button").click(); // Click on a button element
  ```

- **`cy.contains()`**  
  Finds an element containing specific text.

  ```js
  cy.contains("Submit").click(); // Click a button with text 'Submit'
  ```

- **`cy.click()`**  
  Simulates a click on an element.

  ```js
  cy.get("button").click(); // Click on a button
  ```

- **`cy.type()`**  
  Types text into an input element.

  ```js
  cy.get('input[name="username"]').type("testuser"); // Type in the username input field
  ```

- **`cy.select()`**  
  Selects an option from a dropdown.

  ```js
  cy.get("select").select("Option 1"); // Select 'Option 1' from a dropdown
  ```

- **`cy.clear()`**  
  Clears the value of an input field.

  ```js
  cy.get('input[name="password"]').clear(); // Clear the password field
  ```

- **`cy.submit()`**  
  Submits a form.
  ```js
  cy.get("form").submit(); // Submit a form
  ```

---

### **2. Assertions**

- **`cy.should()`**  
  Asserts that an element should meet certain conditions.

  ```js
  cy.get("button").should("be.visible"); // Assert button is visible
  cy.get('input[name="username"]').should("have.value", "testuser"); // Assert value in input
  ```

- **`cy.expect()`**  
  Performs assertions on values (less common than `cy.should()`).

  ```js
  cy.wrap(10).expect(10).to.equal(10); // Expect the value to be 10
  ```

- **`cy.and()`**  
  Allows chaining multiple assertions on the same element.
  ```js
  cy.get("button").should("be.visible").and("have.text", "Submit"); // Assert visibility and text of a button
  ```

---

### **3. Handling Timeouts**

- **`cy.wait()`**  
  Pauses the test execution for a specific amount of time.

  ```js
  cy.wait(500); // Wait for 500 milliseconds
  ```

- **`cy.pause()`**  
  Pauses test execution and allows manual inspection of the application.

  ```js
  cy.get("button").click();
  cy.pause(); // Pause test execution
  ```

- **`cy.tick()`**  
  Used to simulate the passage of time in tests involving timers (requires `cy.clock()` to be set up).
  ```js
  cy.clock(); // Control the clock
  cy.tick(5000); // Move time forward by 5 seconds
  ```

---

### **4. File and Network Handling**

- **`cy.request()`**  
  Sends an HTTP request (GET, POST, PUT, DELETE) and receives a response.

  ```js
  cy.request("GET", "https://jsonplaceholder.typicode.com/posts")
    .its("status")
    .should("equal", 200); // Assert HTTP status is 200
  ```

- **`cy.intercept()`**  
  Intercepts network requests and modifies the request or response.

  ```js
  cy.intercept("GET", "/api/users", { fixture: "users.json" }); // Mock response for a GET request
  ```

- **`cy.fixture()`**  
  Loads a fixture file for use in the test.
  ```js
  cy.fixture("users.json").then((data) => {
    cy.get("ul").should("contain", data[0].name); // Use fixture data in assertions
  });
  ```

---

### **5. Debugging**

- **`cy.debug()`**  
  Logs debugging information to the browser's console.

  ```js
  cy.get("button").debug(); // Logs the element's state to the console
  ```

- **`cy.log()`**  
  Logs messages to the Cypress Command Log.

  ```js
  cy.log("This is a debug message"); // Logs a custom message
  ```

- **`cy.screenshot()`**  
  Takes a screenshot of the current state of the application.
  ```js
  cy.screenshot(); // Takes a screenshot
  ```

---

### **6. Aliases and Cypress Commands**

- **`cy.get().as()`**  
  Creates an alias for a previously selected element to reuse it later.

  ```js
  cy.get('input[name="username"]').as("usernameInput");
  cy.get("@usernameInput").type("testuser"); // Reuse alias to interact with element
  ```

- **`cy.wrap()`**  
  Wraps a JavaScript object or value for chaining Cypress commands.
  ```js
  cy.wrap({ name: "John", age: 30 }).should("have.property", "name", "John");
  ```

---

### **7. Custom Commands**

- **`Cypress.Commands.add()`**  
  Allows adding custom commands to the Cypress command chain.

  ```js
  Cypress.Commands.add("login", (username, password) => {
    cy.get('input[name="username"]').type(username);
    cy.get('input[name="password"]').type(password);
    cy.get('button[type="submit"]').click();
  });

  cy.login("testuser", "password"); // Call custom command
  ```

---

### **8. Window and Document Interactions**

- **`cy.document()`**  
  Gets the document object of the page.

  ```js
  cy.document().should("have.property", "title", "My Web Page");
  ```

- **`cy.window()`**  
  Gets the window object of the page.
  ```js
  cy.window().should("have.property", "localStorage");
  ```

---

### **9. Managing Browser Local Storage**

- **`cy.clearLocalStorage()`**  
  Clears the local storage of the browser.

  ```js
  cy.clearLocalStorage(); // Clear all localStorage data
  ```

- **`cy.getLocalStorage()`**  
  Gets the value from localStorage.

  ```js
  cy.getLocalStorage("token").should("exist");
  ```

- **`cy.setLocalStorage()`**  
  Sets a key-value pair in the localStorage.
  ```js
  cy.setLocalStorage("token", "12345"); // Set a value in localStorage
  ```

---

### **10. Browser Window & Viewport**

- **`cy.viewport()`**  
  Sets the size of the browser window (viewport).

  ```js
  cy.viewport(1280, 720); // Set viewport to 1280x720
  cy.viewport("iphone-6"); // Set viewport to a preset device size
  ```

- **`cy.resize()`**  
  Resizes the browser window.
  ```js
  cy.resize(1200, 800); // Resize browser window
  ```

---

### **11. Running Tests in Parallel (CLI)**

- **`cy.run()`**  
  Executes tests with specified options (including `--parallel` for running tests across multiple instances in parallel).
  ```bash
  npx cypress run --parallel --record
  ```

---

### **12. Miscellaneous**

- **`cy.clearCookies()`**  
  Clears all browser cookies.

  ```js
  cy.clearCookies(); // Clears cookies
  ```

- **`cy.clearSessionStorage()`**  
  Clears session storage.

  ```js
  cy.clearSessionStorage(); // Clears session storage
  ```

- **`cy.exec()`**  
  Executes a system command.
  ```js
  cy.exec("npm run build"); // Run a shell command
  ```

---

### **Summary:**

Cypress provides a rich set of commands that allow you to interact with the DOM, make assertions, handle network requests, and much more. These commands can be chained together to create powerful and concise test scripts. If you’re looking to extend Cypress, you can also create custom commands using `Cypress.Commands.add()`.

Let me know if you'd like more examples or clarification on any specific command!

Here's a detailed guide on handling windows, iframes, reading/writing files, fixtures, and file uploads/downloads in Cypress:

---

### **1. Handling Windows & Alerts**

Cypress has built-in methods to handle browser windows, popups, and alerts.

#### **Handling Alerts & Confirmations:**

- **`cy.on()`**: To handle browser popups such as `alert()`, `confirm()`, or `prompt()`, you can use the `cy.on()` method to intercept them and prevent the default behavior.

  Example for handling alerts:

  ```js
  cy.on("window:alert", (alertText) => {
    expect(alertText).to.contains("Alert Message"); // Verify the alert message
  });

  cy.get("button").click(); // Triggers the alert
  ```

- **Handling Confirm Dialogs:**

  ```js
  cy.on("window:confirm", (message) => {
    expect(message).to.contains("Are you sure?");
    return false; // Prevent the confirm dialog
  });

  cy.get("button").click(); // Triggers the confirm dialog
  ```

#### **Opening a New Window or Tab:**

Cypress doesn't directly support multi-tab testing in the traditional way because it focuses on a single browser tab. However, you can handle redirects in the same window using `cy.visit()`.

If the app opens a new window via `window.open()`, you can intercept that:

```js
cy.window().then((win) => {
  cy.stub(win, "open").as("windowOpen"); // Stub window.open
  cy.get("button").click(); // Action that opens a new window
  cy.get("@windowOpen").should("be.called"); // Assert that window.open was called
});
```

---

### **2. Handling Iframes**

Since Cypress doesn’t directly support interacting with elements inside an iframe, you need to manually access the iframe’s content window.

#### **Example for interacting with an iframe:**

```js
cy.get("iframe")
  .its("0.contentDocument.body") // Access the iframe body
  .should("not.be.empty")
  .find("button") // Find a button inside the iframe
  .click();
```

Alternatively, you can create a custom command to work with iframes:

```js
Cypress.Commands.add("getIframe", (iframeSelector) => {
  return cy
    .get(iframeSelector)
    .its("0.contentDocument.body")
    .should("not.be.empty")
    .then(cy.wrap); // Wrap the iframe content for further actions
});

// Usage:
cy.getIframe("iframe").find("button").click();
```

---

### **3. Reading and Writing Files**

Cypress allows you to interact with the file system, such as reading or writing files, but it’s restricted to the test environment for security.

#### **Reading a File:**

You can read files using `cy.readFile()` which allows reading local files (typically from the `fixtures` folder).

```js
cy.readFile("cypress/fixtures/sample.json").then((data) => {
  expect(data.name).to.equal("John");
});
```

#### **Writing to a File:**

You can use `cy.writeFile()` to create or overwrite files.

```js
cy.writeFile("cypress/fixtures/output.txt", "Hello, Cypress!"); // Write text to a file
cy.writeFile("cypress/fixtures/output.json", { name: "John" }); // Write JSON to a file
```

---

### **4. Using Fixtures**

Fixtures in Cypress are used to store data that can be loaded into tests (like JSON, CSV, or text files).

#### **Loading Fixtures:**

You can load a fixture using `cy.fixture()` and use it in your tests.

```js
cy.fixture("users.json").then((users) => {
  cy.get(".user-list").should("have.length", users.length); // Use loaded data in assertions
});
```

Make sure the fixture file (e.g., `users.json`) is stored in the `cypress/fixtures/` directory.

#### **Example of `users.json`:**

```json
[
  { "id": 1, "name": "John" },
  { "id": 2, "name": "Jane" }
]
```

---

### **5. File Uploads**

Cypress doesn’t directly support file uploads via the native browser file input, but you can use the `cy.upload()` command or simulate the upload by using the `attachFile` plugin.

#### **Using the `cypress-file-upload` Plugin:**

1. **Install the plugin**:

   ```bash
   npm install --save-dev cypress-file-upload
   ```

2. **Import the plugin** in `commands.js`:

   ```js
   import "cypress-file-upload";
   ```

3. **Uploading a File:**
   ```js
   cy.get('input[type="file"]').attachFile("path/to/file.pdf"); // Upload a file
   ```

You can store the file in the `cypress/fixtures/` folder and upload it by specifying the relative path.

#### **Example for uploading a file in an input:**

```js
cy.get('input[type="file"]').attachFile("example.txt"); // Uploads example.txt from fixtures
```

---

### **6. File Downloads**

Cypress doesn’t directly support downloading files in the traditional sense, as it doesn’t open or download files to your system during the test run. However, you can handle download scenarios by checking network requests or using a custom approach.

#### **Using `cy.intercept()` to Handle Downloads:**

You can intercept the request triggered by a file download and assert the response.

```js
cy.intercept("GET", "/downloads/myfile.pdf").as("fileDownload");

cy.get("button.download").click(); // Trigger the download

cy.wait("@fileDownload").its("response.statusCode").should("equal", 200); // Verify file download
```

#### **Verifying Downloaded Files (in the App):**

If the file download occurs via an API (e.g., when you get a link to the file), you can verify the download by checking the response body.

```js
cy.intercept("GET", "/api/download").as("downloadRequest");
cy.wait("@downloadRequest").then((interception) => {
  const fileContent = interception.response.body;
  expect(fileContent).to.contain("expected content");
});
```

If you want to test the physical download, you’d typically need to set up a custom server or verify the downloaded file manually.

---

### **7. Managing Cookies and LocalStorage for Tests**

Cypress also allows you to handle cookies and localStorage, which can be useful when testing authentication scenarios.

#### **Clear Cookies:**

```js
cy.clearCookies(); // Clear all cookies
```

#### **Set Cookies:**

```js
cy.setCookie("session_id", "12345"); // Set a cookie
```

#### **Get Cookies:**

```js
cy.getCookie("session_id").should("have.property", "value", "12345"); // Verify cookie value
```

#### **Clear LocalStorage:**

```js
cy.clearLocalStorage(); // Clear local storage
```

#### **Set LocalStorage:**

```js
cy.window().then((window) => {
  window.localStorage.setItem("token", "12345"); // Set item in localStorage
});
```

#### **Get LocalStorage:**

```js
cy.window().then((window) => {
  const token = window.localStorage.getItem("token");
  expect(token).to.equal("12345"); // Verify token in localStorage
});
```

---

### **Summary:**

- **Windows and Alerts**: Use `cy.on()` to handle popups like alerts and confirms.
- **Iframes**: Access iframe content with `cy.get()` and interact with elements inside the iframe.
- **Reading/Writing Files**: Use `cy.readFile()` to read and `cy.writeFile()` to write to files.
- **Fixtures**: Load static data for tests with `cy.fixture()`.
- **File Uploads**: Use the `cypress-file-upload` plugin to simulate file uploads.
- **File Downloads**: Handle download events with `cy.intercept()` and validate responses.

Would you like more examples on any of these or help setting up a specific scenario?

API testing in Cypress is a powerful way to interact with your backend services without needing to use the frontend. Cypress provides commands to make HTTP requests, validate responses, and ensure that your API behaves as expected. Here's a detailed guide on how to perform API testing in Cypress:

### **1. Setting Up API Testing in Cypress**

Before you start, ensure you have Cypress installed in your project. You can set it up using:

```bash
npm install cypress --save-dev
```

You can define your API test cases in the `cypress/integration` folder or a separate folder for API tests.

---

### **2. Making API Requests with `cy.request()`**

The main command used for API testing in Cypress is `cy.request()`. It allows you to send HTTP requests (GET, POST, PUT, DELETE, etc.) and make assertions on the response.

#### **Basic `cy.request()` Usage**

```js
cy.request("GET", "https://jsonplaceholder.typicode.com/posts").then(
  (response) => {
    // Assertions
    expect(response.status).to.eq(200); // Assert the status code
    expect(response.body).to.have.length.greaterThan(0); // Assert the response body
  }
);
```

This sends a GET request to `https://jsonplaceholder.typicode.com/posts`, then performs assertions on the response.

#### **Sending POST Requests**

For sending POST requests with data, you can use `cy.request()` with the `method` and `body` properties:

```js
cy.request({
  method: "POST", // Send a POST request
  url: "https://jsonplaceholder.typicode.com/posts",
  body: {
    title: "foo",
    body: "bar",
    userId: 1,
  },
}).then((response) => {
  expect(response.status).to.eq(201); // Assert the status code
  expect(response.body).to.have.property("title", "foo"); // Assert the response body
});
```

#### **Sending PUT Requests**

To update an existing resource, use the PUT method:

```js
cy.request({
  method: "PUT",
  url: "https://jsonplaceholder.typicode.com/posts/1",
  body: {
    id: 1,
    title: "Updated Title",
    body: "Updated Body",
    userId: 1,
  },
}).then((response) => {
  expect(response.status).to.eq(200); // Assert the status code
  expect(response.body).to.have.property("title", "Updated Title"); // Assert the updated title
});
```

#### **Sending DELETE Requests**

To delete a resource, use the DELETE method:

```js
cy.request({
  method: "DELETE",
  url: "https://jsonplaceholder.typicode.com/posts/1",
}).then((response) => {
  expect(response.status).to.eq(200); // Assert the status code
});
```

---

### **3. Assertions on API Responses**

Cypress allows you to make various assertions on the API response such as:

- **Status Code**:

  ```js
  expect(response.status).to.eq(200); // Assert status code is 200 (OK)
  ```

- **Response Body**:

  ```js
  expect(response.body).to.have.property("title"); // Assert response body contains a 'title' property
  expect(response.body.title).to.eq("foo"); // Assert the 'title' property is 'foo'
  ```

- **Response Time**:

  ```js
  expect(response.duration).to.be.lessThan(1000); // Assert response time is less than 1 second
  ```

- **Content Type**:
  ```js
  expect(response.headers["content-type"]).to.include("application/json"); // Assert content-type is JSON
  ```

---

### **4. Working with Response Data**

You can chain commands and manipulate response data just like with regular Cypress commands:

```js
cy.request("GET", "https://jsonplaceholder.typicode.com/posts").then(
  (response) => {
    const posts = response.body;
    posts.forEach((post) => {
      expect(post).to.have.property("id");
      expect(post).to.have.property("title");
    });
  }
);
```

---

### **5. Using `cy.intercept()` to Mock and Stubbing API Requests**

With Cypress 6.0+, you can intercept and mock API requests using `cy.intercept()`. This is helpful for controlling the behavior of your API during testing, especially if you need to simulate different scenarios.

#### **Intercepting a GET Request:**

```js
cy.intercept("GET", "/api/posts", { fixture: "posts.json" }).as("getPosts"); // Mock API call
cy.visit("/"); // Trigger the API call on page load

cy.wait("@getPosts").then((interception) => {
  expect(interception.response.body).to.have.length(3); // Assert the mock response body length
});
```

In this example, whenever a GET request is made to `/api/posts`, it is intercepted and replaced by the mock data in `posts.json` (located in `cypress/fixtures/`).

#### **Mocking a POST Request:**

```js
cy.intercept("POST", "/api/posts", {
  statusCode: 201,
  body: { id: 1, title: "foo", body: "bar", userId: 1 },
}).as("createPost");

cy.request("POST", "/api/posts", { title: "foo", body: "bar", userId: 1 }).then(
  (response) => {
    expect(response.status).to.eq(201); // Assert successful creation
    expect(response.body).to.have.property("id"); // Assert that the response contains an ID
  }
);
```

This intercepts the `POST` request and returns a mocked response instead of actually sending the request to the server.

---

### **6. Using Fixtures for API Data**

Fixtures in Cypress are commonly used to provide mock data for your API tests. You can use them with `cy.fixture()` to load predefined data for requests.

#### **Example with Fixtures:**

- **Step 1**: Create a `posts.json` file in the `cypress/fixtures/` folder:

  ```json
  [
    {
      "id": 1,
      "title": "Post 1",
      "body": "This is the first post."
    },
    {
      "id": 2,
      "title": "Post 2",
      "body": "This is the second post."
    }
  ]
  ```

- **Step 2**: Use the fixture in your test:

  ```js
  cy.fixture("posts.json").then((posts) => {
    cy.intercept("GET", "/api/posts", { statusCode: 200, body: posts }).as(
      "getPosts"
    );
  });

  cy.visit("/"); // Trigger the API call on page load
  cy.wait("@getPosts").its("response.body").should("have.length", 2);
  ```

---

### **7. Handling Authentication in API Tests**

For API testing, you may need to test endpoints that require authentication. You can use `cy.request()` to authenticate without using the UI.

#### **Example:**

- **Step 1**: Get an authentication token via a POST request:

  ```js
  cy.request("POST", "https://example.com/api/login", {
    username: "testuser",
    password: "password",
  }).then((response) => {
    expect(response.status).to.eq(200);
    cy.wrap(response.body.token).as("authToken");
  });
  ```

- **Step 2**: Use the token in subsequent requests:

  ```js
  cy.get("@authToken").then((token) => {
    cy.request({
      method: "GET",
      url: "https://example.com/api/protected",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {
      expect(response.status).to.eq(200); // Assert successful response with token
    });
  });
  ```

---

### **8. Chaining Requests and Validating Responses**

You can chain multiple API requests and use data from one request to validate another.

```js
cy.request("GET", "https://jsonplaceholder.typicode.com/posts/1").then(
  (response) => {
    const postId = response.body.id;
    cy.request(
      `GET`,
      `https://jsonplaceholder.typicode.com/posts/${postId}`
    ).then((secondResponse) => {
      expect(secondResponse.body.id).to.eq(postId); // Validate second request uses the same post ID
    });
  }
);
```

---

### **9. Error Handling in API Requests**

Cypress provides useful methods to handle and assert different types of API errors. You can check if an API returns the correct error response.

#### **Example of handling 404 error:**

```js
cy.request({
  method: "GET",
  url: "https://jsonplaceholder.typicode.com/nonexistent",
  failOnStatusCode: false, // Prevent Cypress from failing the test on 4xx/5xx status
}).then((response) => {
  expect(response.status).to.eq(404); // Assert 404 error
});
```

---

### **Summary of Key Commands for API Testing:**

- **`cy.request()`**: Send HTTP requests (GET, POST, PUT, DELETE).
- **`cy.intercept()`**: Mock or intercept API requests.
- **`cy.fixture()`**: Load fixture data for API testing.
- **Assertions**: Verify status code, response body, response time, etc.
- **Authentication**: Use `cy.request()` for authenticating API endpoints.

By leveraging these commands, you can effectively

test APIs in Cypress, ensuring that they behave as expected without having to rely on the UI.

Would you like further examples or assistance with any specific API testing scenarios?

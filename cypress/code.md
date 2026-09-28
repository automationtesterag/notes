````markdown
# Cypress Web Actions - Complete Reference

```javascript
describe("Cypress Web Actions - Complete Reference", () => {

  // ============================================================
  // 1. PAGE NAVIGATION ACTIONS
  // ============================================================

  /**
   * Navigation methods move the browser between URLs and allow
   * validation of the current URL and page state.
   */

  // Generic syntax
  cy.visit("https://example.com");

  // Navigate to URL
  cy.visit("https://example.com");

  // Navigate with options
  cy.visit("https://example.com", {
    timeout: 30000,
  });

  // Navigate back
  cy.go("back");

  // Navigate forward
  cy.go("forward");

  // Reload page
  cy.reload();

  // Current URL
  cy.url().then((url) => {
    console.log(url);
  });

  // Validate URL
  cy.url().should("include", "/dashboard");

  // Validate exact URL
  cy.url().should("eq", "https://example.com/dashboard");

  // Page title
  cy.title().then((title) => {
    console.log(title);
  });

  cy.title().should("eq", "Dashboard");


  // ============================================================
  // 2. LOCATOR / ELEMENT SELECTION
  // ============================================================

  /**
   * Cypress uses cy.get(), cy.contains(), and CSS selectors
   * to locate elements.
   */

  // CSS selector
  cy.get("#login");

  // Class
  cy.get(".login-button");

  // Attribute
  cy.get('[name="username"]');

  // Attribute value
  cy.get('[data-testid="submit-button"]');

  // Text
  cy.contains("Products");

  // Text inside specific element
  cy.get(".card").contains("Laptop");

  // Find child element
  cy.get(".card").find("button");

  // Parent element
  cy.get("#username").parent();

  // Closest parent
  cy.get("#username").closest(".form-group");

  // First element
  cy.get(".item").first();

  // Last element
  cy.get(".item").last();

  // Element by index
  cy.get(".item").eq(2);

  // Multiple elements
  cy.get(".item");

  // Number of elements
  cy.get(".item").should("have.length", 5);

  // Filter elements
  cy.get(".card").filter(":contains('Laptop')");

  // Filter using child element
  cy.get(".card").filter(":has(button)");

  // Get element by role
  cy.get('[role="button"]');

  // Get element by test id
  cy.get('[data-testid="submit-button"]');


  // ============================================================
  // 3. MOUSE ACTIONS
  // ============================================================

  // Click
  cy.get("#login").click();

  // Double click
  cy.get("#button").dblclick();

  // Right click
  cy.get("#menu").rightclick();

  // Hover
  cy.get("#products").trigger("mouseover");

  // Focus
  cy.get("#username").focus();

  // Blur
  cy.get("#username").blur();

  // Click with position
  cy.get("#button").click("topLeft");

  // Force click
  cy.get("#button").click({ force: true });

  // Click multiple elements
  cy.get(".item").click({ multiple: true });

  // Trigger mouse event
  cy.get("#element").trigger("mousedown");
  cy.get("#element").trigger("mouseup");


  // ============================================================
  // 4. TEXT INPUT ACTIONS
  // ============================================================

  // Type text
  cy.get("#username").type("admin");

  // Clear input
  cy.get("#username").clear();

  // Clear and type
  cy.get("#username")
    .clear()
    .type("admin");

  // Type slowly
  cy.get("#username").type("admin", {
    delay: 100,
  });

  // Press Enter
  cy.get("#username").type("{enter}");

  // Press Tab
  cy.get("#username").type("{tab}");

  // Backspace
  cy.get("#username").type("{backspace}");

  // Escape
  cy.get("#username").type("{esc}");

  // Keyboard shortcuts
  cy.get("#username").type("{ctrl}a");

  cy.get("#username").type("{ctrl}c");

  cy.get("#username").type("{ctrl}v");

  cy.get("#username").type("{ctrl}x");


  // ============================================================
  // 5. CHECKBOX AND RADIO BUTTON ACTIONS
  // ============================================================

  // Check checkbox
  cy.get("#terms").check();

  // Uncheck checkbox
  cy.get("#terms").uncheck();

  // Check multiple checkboxes
  cy.get('input[type="checkbox"]').check();

  // Select radio button
  cy.get("#male").check();

  // Force check
  cy.get("#terms").check({ force: true });

  // Validate checked
  cy.get("#terms").should("be.checked");

  // Validate unchecked
  cy.get("#terms").should("not.be.checked");


  // ============================================================
  // 6. DROPDOWN ACTIONS
  // ============================================================

  // Select by value
  cy.get("#country").select("IN");

  // Select by visible text
  cy.get("#country").select("India");

  // Select by index
  cy.get("#country").select(1);

  // Multiple selection
  cy.get("#country").select(["IN", "US"]);

  // Validate selected value
  cy.get("#country").should("have.value", "IN");

  // Validate selected text
  cy.get("#country").find(":selected")
    .should("have.text", "India");

  // Custom dropdown
  cy.get("#country").click();
  cy.contains("India").click();


  // ============================================================
  // 7. FILE UPLOAD ACTIONS
  // ============================================================

  // Upload one file
  cy.get('input[type="file"]')
    .selectFile("cypress/fixtures/sample.pdf");

  // Upload multiple files
  cy.get('input[type="file"]')
    .selectFile([
      "cypress/fixtures/file1.txt",
      "cypress/fixtures/file2.txt",
    ]);

  // Upload using contents
  cy.get('input[type="file"]').selectFile({
    contents: "cypress/fixtures/sample.txt",
    fileName: "sample.txt",
  });

  // Force upload
  cy.get('input[type="file"]')
    .selectFile("cypress/fixtures/sample.pdf", {
      force: true,
    });


  // ============================================================
  // 8. SCROLL ACTIONS
  // ============================================================

  // Scroll element into view
  cy.get("#footer").scrollIntoView();

  // Scroll to top
  cy.scrollTo("top");

  // Scroll to bottom
  cy.scrollTo("bottom");

  // Scroll to position
  cy.scrollTo(0, 500);

  // Scroll element
  cy.get("#element").scrollIntoView();

  // Validate visibility after scroll
  cy.get("#footer").scrollIntoView()
    .should("be.visible");


  // ============================================================
  // 9. FRAME / IFRAME ACTIONS
  // ============================================================

  /**
   * Cypress does not provide Playwright-style frameLocator().
   * For iframe interaction, commonly use the iframe plugin or
   * access the iframe document/body.
   */

  // Using iframe plugin
  cy.frameLoaded("#payment-frame");

  cy.iframe("#payment-frame")
    .find("#cardNumber")
    .type("4111111111111111");

  cy.iframe("#payment-frame")
    .find("#pay")
    .click();

  // Without plugin
  cy.get("#payment-frame")
    .its("0.contentDocument.body")
    .should("not.be.empty")
    .then(cy.wrap)
    .find("#cardNumber")
    .type("4111111111111111");


  // ============================================================
  // 10. DIALOG ACTIONS
  // ============================================================

  /**
   * Cypress automatically accepts JavaScript alerts.
   */

  // Capture alert text
  cy.on("window:alert", (message) => {
    expect(message).to.contain("Success");
  });

  // Confirm dialog
  cy.on("window:confirm", () => {
    return true;       // Click OK
  });

  // Cancel confirm dialog
  cy.on("window:confirm", () => {
    return false;      // Click Cancel
  });

  // Prompt
  cy.window().then((win) => {
    cy.stub(win, "prompt").returns("Anudeep");
  });


  // ============================================================
  // 11. PAGE / BROWSER ACTIONS
  // ============================================================

  // Open page
  cy.visit("https://example.com");

  // Current URL
  cy.url().then(console.log);

  // Current title
  cy.title().then(console.log);

  // Window object
  cy.window().then((win) => {
    console.log(win);
  });

  // Document object
  cy.document().then((document) => {
    console.log(document);
  });

  // Set viewport
  cy.viewport(1280, 720);

  // Mobile viewport
  cy.viewport("iphone-13");

  // Screenshot
  cy.screenshot("homepage");

  // Full page screenshot
  cy.screenshot("homepage", {
    capture: "fullPage",
  });


  // ============================================================
  // 12. ELEMENT INFORMATION / GETTERS
  // ============================================================

  const element = cy.get("#message");

  // Text
  cy.get("#message").invoke("text");

  // Input value
  cy.get("#username").invoke("val");

  // Attribute
  cy.get("#link").invoke("attr", "href");

  // HTML
  cy.get("#message").invoke("html");

  // Property
  cy.get("#checkbox").invoke("prop", "checked");

  // CSS property
  cy.get("#button").invoke("css", "color");

  // Element state
  cy.get("#button").should("be.visible");
  cy.get("#button").should("be.enabled");

  // Checked state
  cy.get("#checkbox").should("be.checked");

  // Editable
  cy.get("#username").should("not.be.disabled");

  // Element count
  cy.get(".item").should("have.length", 5);


  // ============================================================
  // 13. ASSERTIONS
  // ============================================================

  // Visible
  cy.get("#message").should("be.visible");

  // Hidden
  cy.get("#loader").should("not.be.visible");

  // Enabled
  cy.get("#button").should("be.enabled");

  // Disabled
  cy.get("#button").should("be.disabled");

  // Checked
  cy.get("#terms").should("be.checked");

  // Text
  cy.get("#message")
    .should("have.text", "Login successful");

  // Partial text
  cy.get("#message")
    .should("contain.text", "successful");

  // Value
  cy.get("#username")
    .should("have.value", "admin");

  // Attribute
  cy.get("#link")
    .should("have.attr", "href", "/dashboard");

  // Class
  cy.get("#button")
    .should("have.class", "btn-primary");

  // Count
  cy.get(".item")
    .should("have.length", 5);

  // URL
  cy.url()
    .should("include", "/dashboard");

  // Exact URL
  cy.url()
    .should("eq", "https://example.com/dashboard");

  // Title
  cy.title()
    .should("eq", "Dashboard");

  // CSS
  cy.get("#button")
    .should("have.css", "color", "rgb(255, 255, 255)");

  // Exist
  cy.get("#message")
    .should("exist");

  // Not exist
  cy.get("#loader")
    .should("not.exist");

  // Value contains
  cy.get("#username")
    .should("contain.value", "admin");

  // Multiple assertions
  cy.get("#username")
    .should("be.visible")
    .and("be.enabled")
    .and("have.value", "admin");


  // ============================================================
  // 14. WAIT / DELAY ACTIONS
  // ============================================================

  // Cypress automatically waits for commands/assertions.

  // Wait for element
  cy.get("#message")
    .should("be.visible");

  // Wait for element to exist
  cy.get("#message")
    .should("exist");

  // Wait for URL
  cy.url()
    .should("include", "/dashboard");

  // Wait for API request
  cy.intercept("GET", "/api/users").as("getUsers");

  cy.visit("/users");

  cy.wait("@getUsers");

  // Wait and validate API response
  cy.intercept("GET", "/api/users").as("getUsers");

  cy.visit("/users");

  cy.wait("@getUsers")
    .its("response.statusCode")
    .should("eq", 200);

  // Fixed delay
  cy.wait(1000);

  // IMPORTANT:
  // Prefer Cypress automatic retry / assertions
  // over fixed cy.wait(milliseconds).


  // ============================================================
  // 15. POPUP / NEW TAB ACTIONS
  // ============================================================

  /**
   * Cypress does not support multiple browser tabs in the same
   * way as Playwright.
   */

  // Remove target="_blank"
  cy.get("a[target='_blank']")
    .invoke("removeAttr", "target")
    .click();

  // Validate URL
  cy.url()
    .should("include", "/new-page");


  // ============================================================
  // 16. TOUCH ACTIONS
  // ============================================================

  // Touch event
  cy.get("#menu")
    .trigger("touchstart");

  cy.get("#menu")
    .trigger("touchend");

  // Tap equivalent
  cy.get("#menu")
    .click();


  // ============================================================
  // 17. DRAG AND DROP
  // ============================================================

  // Native drag/drop may require plugin or custom events.

  cy.get("#source")
    .trigger("dragstart");

  cy.get("#target")
    .trigger("drop");

  // With Cypress drag-drop plugin:
  // cy.get("#source").drag("#target");


  // ============================================================
  // 18. SCREENSHOT
  // ============================================================

  // Screenshot page
  cy.screenshot("homepage");

  // Screenshot specific element
  cy.get("#header")
    .screenshot("header");

  // Full page screenshot
  cy.screenshot("full-page", {
    capture: "fullPage",
  });


  // ============================================================
  // 19. DOWNLOAD ACTIONS
  // ============================================================

  // Click download
  cy.get("#download").click();

  // Verify downloaded file
  cy.readFile("cypress/downloads/file.pdf")
    .should("exist");

  // Read downloaded text file
  cy.readFile("cypress/downloads/file.txt")
    .should("contain", "Hello");

  // Validate JSON download
  cy.readFile("cypress/downloads/data.json")
    .its("name")
    .should("eq", "Anudeep");


  // ============================================================
  // 20. COOKIES / SESSION
  // ============================================================

  // Set cookie
  cy.setCookie("session", "abc123");

  // Get cookie
  cy.getCookie("session")
    .then((cookie) => {
      console.log(cookie);
    });

  // Get all cookies
  cy.getCookies()
    .then((cookies) => {
      console.log(cookies);
    });

  // Clear specific cookie
  cy.clearCookie("session");

  // Clear all cookies
  cy.clearCookies();

  // Clear local storage
  cy.clearLocalStorage();

  // Session
  cy.session("login", () => {
    cy.visit("/login");

    cy.get("#username")
      .type("admin");

    cy.get("#password")
      .type("password");

    cy.get("#login")
      .click();
  });


  // ============================================================
  // 21. NETWORK / API INTERCEPT
  // ============================================================

  // Intercept request
  cy.intercept("GET", "/api/users").as("getUsers");

  // Wait for request
  cy.wait("@getUsers");

  // Continue request
  cy.intercept("GET", "/api/users", (req) => {
    req.continue();
  });

  // Mock response
  cy.intercept("GET", "/api/users", {
    statusCode: 200,
    body: {
      users: [],
    },
  });

  // Block / fail request
  cy.intercept("GET", "/api/users", {
    forceNetworkError: true,
  });

  // Validate intercepted response
  cy.intercept("GET", "/api/users").as("users");

  cy.visit("/users");

  cy.wait("@users")
    .its("response.statusCode")
    .should("eq", 200);

  // Inspect request / response
  cy.wait("@users").then((interception) => {
    console.log(interception.request);
    console.log(interception.response);
  });


  // ============================================================
  // 22. CLIPBOARD ACTIONS
  // ============================================================

  // Read clipboard
  cy.window()
    .then((win) => {
      return win.navigator.clipboard.readText();
    })
    .then((text) => {
      console.log(text);
    });

  // Write clipboard
  cy.window()
    .then((win) => {
      return win.navigator.clipboard
        .writeText("Hello");
    });

  // Validate clipboard
  cy.window()
    .then((win) => {
      return win.navigator.clipboard.readText();
    })
    .should("eq", "Hello");


  // ============================================================
  // 23. DEBUGGING
  // ============================================================

  // Debug element
  cy.get("#button").debug();

  // Pause execution
  cy.pause();

  // Log message
  cy.log("Login completed");

  // Console log
  cy.get("#message")
    .invoke("text")
    .then((text) => {
      console.log(text);
    });

  // Screenshot during debugging
  cy.screenshot("debug");


  // ============================================================
  // 24. API REQUEST ACTIONS
  // ============================================================

  // Generic syntax
  cy.request({
    method: "GET",
    url: "https://api.example.com/users",
  }).then((response) => {
    console.log(response.status);
    console.log(response.body);
  });


  // ============================================================
  // GET
  // ============================================================

  cy.request(
    "GET",
    "https://jsonplaceholder.typicode.com/users/1"
  ).then((response) => {

    expect(response.status).to.eq(200);

    expect(response.body.id)
      .to.eq(1);

    expect(response.body.name)
      .to.exist;

    expect(response.body.email)
      .to.contain("@");
  });


  // ============================================================
  // POST
  // ============================================================

  cy.request({
    method: "POST",
    url: "https://jsonplaceholder.typicode.com/users",

    body: {
      name: "Anudeep",
      email: "anudeep@example.com",
    },
  }).then((response) => {

    expect(response.status)
      .to.eq(201);

    expect(response.body.name)
      .to.eq("Anudeep");

    expect(response.body.email)
      .to.eq("anudeep@example.com");
  });


  // ============================================================
  // PUT
  // ============================================================

  cy.request({
    method: "PUT",
    url: "https://jsonplaceholder.typicode.com/users/1",

    body: {
      name: "Updated User",
      email: "updated@example.com",
    },
  }).then((response) => {

    expect(response.status)
      .to.eq(200);

    expect(response.body.name)
      .to.eq("Updated User");
  });


  // ============================================================
  // PATCH
  // ============================================================

  cy.request({
    method: "PATCH",
    url: "https://jsonplaceholder.typicode.com/users/1",

    body: {
      name: "Updated Name",
    },
  }).then((response) => {

    expect(response.status)
      .to.eq(200);

    expect(response.body.name)
      .to.eq("Updated Name");
  });


  // ============================================================
  // DELETE
  // ============================================================

  cy.request(
    "DELETE",
    "https://jsonplaceholder.typicode.com/users/1"
  ).then((response) => {

    expect(response.status)
      .to.eq(200);
  });


  // ============================================================
  // COMMON API VALIDATIONS
  // ============================================================

  cy.request(
    "GET",
    "https://jsonplaceholder.typicode.com/users/1"
  ).then((response) => {

    // Status
    expect(response.status)
      .to.eq(200);

    // Body
    expect(response.body.id)
      .to.eq(1);

    expect(response.body.name)
      .to.exist;

    // String
    expect(response.body.email)
      .to.contain("@");

    expect(response.body.name)
      .to.eq("Leanne Graham");

    // Headers
    expect(response.headers["content-type"])
      .to.contain("application/json");
  });


  // ============================================================
  // API REQUEST WITH HEADERS
  // ============================================================

  cy.request({
    method: "GET",
    url: "/users",
    headers: {
      Authorization: "Bearer token",
      Accept: "application/json",
    },
  });


  // ============================================================
  // API REQUEST WITH QUERY PARAMETERS
  // ============================================================

  cy.request({
    method: "GET",
    url: "/users",
    qs: {
      page: 1,
      limit: 10,
    },
  });


  // ============================================================
  // API REQUEST + SAVE RESPONSE DATA
  // ============================================================

  cy.request("POST", "/users", {
    name: "Anudeep",
    email: "anudeep@example.com",
  }).then((response) => {

    const userId = response.body.id;

    cy.log(`Created User ID: ${userId}`);

    // Use response data in another API
    cy.request("GET", `/users/${userId}`)
      .then((getResponse) => {

        expect(getResponse.status)
          .to.eq(200);

        expect(getResponse.body.id)
          .to.eq(userId);
      });
  });


  // ============================================================
  // 25. JAVASCRIPT EVALUATION
  // ============================================================

  // Window JavaScript
  cy.window().then((win) => {
    win.document.body.style.backgroundColor = "yellow";
  });

  // Document JavaScript
  cy.document().then((document) => {
    console.log(document.title);
  });

  // Element JavaScript
  cy.get("#button")
    .then(($element) => {
      $element[0].click();
    });

  // Get attribute using JS
  cy.get("#button")
    .invoke("attr", "id")
    .then((id) => {
      console.log(id);
    });

  // Get document title
  cy.document()
    .its("title")
    .then((title) => {
      console.log(title);
    });


  // ============================================================
  // 26. COMMON JAVASCRIPT / ELEMENT VALIDATIONS
  // ============================================================

  // Element exists
  cy.get("#button")
    .should("exist");

  // Element visible
  cy.get("#button")
    .should("be.visible");

  // Element enabled
  cy.get("#button")
    .should("be.enabled");

  // Element has class
  cy.get("#button")
    .should("have.class", "primary");

  // Element has attribute
  cy.get("#link")
    .should("have.attr", "href");

  // Element has value
  cy.get("#username")
    .should("have.value", "admin");

  // Element contains text
  cy.get("#message")
    .should("contain.text", "Success");


  // ============================================================
  // 27. CUSTOM COMMANDS
  // ============================================================

  // Define custom command in:
  // cypress/support/commands.js

  /*
  Cypress.Commands.add("login", (username, password) => {
    cy.get("#username").type(username);
    cy.get("#password").type(password);
    cy.get("#login").click();
  });
  */

  // Use custom command
  cy.login("admin", "password");


  // ============================================================
  // 28. ALIASES
  // ============================================================

  // Create alias
  cy.get("#username")
    .as("username");

  // Use alias
  cy.get("@username")
    .type("admin");

  // API alias
  cy.request("/users")
    .as("users");

  cy.get("@users")
    .then((response) => {
      console.log(response);
    });


  // ============================================================
  // 29. DEBUGGING / COMMAND LOG
  // ============================================================

  // Log
  cy.log("Starting test");

  // Debug
  cy.get("#username")
    .debug();

  // Pause test
  cy.pause();

  // Screenshot
  cy.screenshot("debug");

});


// ============================================================
// QUICK REMEMBER
// ============================================================

// Navigation
// cy.visit()          → open URL
// cy.go()             → back / forward
// cy.reload()         → reload
// cy.url()            → current URL
// cy.title()          → page title

// Locators
// cy.get()            → CSS selector
// cy.contains()       → text
// .find()             → child
// .parent()           → parent
// .closest()          → nearest parent
// .first()            → first element
// .last()             → last element
// .eq()               → index

// Actions
// .click()            → click
// .dblclick()         → double click
// .rightclick()       → right click
// .type()             → type text
// .clear()            → clear input
// .check()            → check
// .uncheck()          → uncheck
// .select()           → dropdown
// .selectFile()       → upload
// .scrollIntoView()   → scroll element

// Assertions
// .should()           → assertion
// .and()              → additional assertion
// have.text            → exact text
// contain.text         → partial text
// have.value           → input value
// have.attr            → attribute
// have.class            → class
// be.visible            → visible
// be.enabled            → enabled
// be.disabled           → disabled
// be.checked            → checked
// exist                 → exists

// Waits
// cy.get()             → automatic retry
// .should()            → automatic retry
// cy.wait("@alias")    → wait for request
// cy.wait(ms)          → fixed delay

// Network
// cy.intercept()       → intercept / mock API
// cy.wait()            → wait for intercepted request

// API
// cy.request()         → API request
// response.status      → status code
// response.body        → response body
// response.headers     → headers

// Browser
// cy.window()          → browser window
// cy.document()        → document
// cy.viewport()        → viewport
// cy.screenshot()      → screenshot

// Cookies
// cy.setCookie()       → set cookie
// cy.getCookie()       → get cookie
// cy.getCookies()      → get all cookies
// cy.clearCookie()     → clear cookie
// cy.clearCookies()    → clear all cookies
// cy.session()         → preserve session

// Debugging
// cy.log()             → log message
// .debug()             → debug element
// cy.pause()           → pause test
// cy.screenshot()      → capture screenshot

````

```

This is ready to save as a `.md` reference file.
```

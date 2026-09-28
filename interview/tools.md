## Selenium vs Cypress vs Playwright — Comparison

| Feature                        | **Selenium**                               | **Cypress**                                   | **Playwright**                              |
| ------------------------------ | ------------------------------------------ | --------------------------------------------- | ------------------------------------------- |
| **Type**                       | Browser automation framework               | Web testing framework                         | Browser automation & testing framework      |
| **Primary language**           | Java, Python, C#, JS/TS, Ruby              | JavaScript / TypeScript                       | JavaScript / TypeScript, Python, Java, .NET |
| **Architecture**               | WebDriver-based                            | Browser runs closely with Cypress test runner | Direct browser automation via Playwright    |
| **Browser support**            | Chrome, Firefox, Edge, Safari              | Chrome-family, Firefox, Electron              | Chromium, Firefox, WebKit                   |
| **Safari**                     | ✅ Yes                                      | ⚠️ Limited                                    | ⚠️ WebKit, not Safari itself                |
| **Mobile web**                 | ✅ Yes                                      | ⚠️ Limited                                    | ✅ Yes                                       |
| **Native mobile app**          | ❌ Not directly                             | ❌ No                                          | ❌ Not directly; use Appium                  |
| **iFrame handling**            | ⚠️ More manual                             | ✅ Easy                                        | ✅ Easy                                      |
| **Multiple tabs**              | ✅ Yes                                      | ⚠️ Limited historically                       | ✅ Excellent                                 |
| **Multiple windows**           | ✅ Yes                                      | ⚠️ Limited                                    | ✅ Yes                                       |
| **Auto-waiting**               | ❌ Mostly manual                            | ✅ Yes                                         | ✅ Yes                                       |
| **Web-first assertions**       | ⚠️ External/assertion libraries often used | ✅ Built-in                                    | ✅ Built-in                                  |
| **Network interception**       | ⚠️ Requires additional approach            | ✅ Excellent                                   | ✅ Excellent                                 |
| **API testing**                | ⚠️ Requires libraries/tools                | ✅ Supported                                   | ✅ Excellent                                 |
| **Parallel execution**         | ✅ Grid/cloud solutions                     | ✅ Yes                                         | ✅ Built-in                                  |
| **Cross-browser testing**      | ✅ Excellent                                | ✅ Good                                        | ✅ Excellent                                 |
| **Headless execution**         | ✅ Yes                                      | ✅ Yes                                         | ✅ Yes                                       |
| **Codegen / recorder**         | ⚠️ Limited ecosystem                       | ✅ Cypress Studio/features                     | ✅ Excellent                                 |
| **Debugging**                  | ⚠️ Depends on setup                        | ✅ Excellent                                   | ✅ Excellent                                 |
| **Trace viewer**               | ❌ No native equivalent                     | ⚠️ Different tooling                          | ✅ Excellent                                 |
| **Screenshots/videos**         | ✅                                          | ✅                                             | ✅                                           |
| **CI/CD**                      | ✅ Excellent                                | ✅ Excellent                                   | ✅ Excellent                                 |
| **Test isolation**             | Depends on framework                       | ✅ Strong                                      | ✅ Strong                                    |
| **Component testing**          | ⚠️ Framework-dependent                     | ✅ Yes                                         | ✅ Yes                                       |
| **Learning curve**             | Medium                                     | Easy–Medium                                   | Easy–Medium                                 |
| **Setup complexity**           | Medium–High                                | Low                                           | Low                                         |
| **Legacy application support** | ✅ Excellent                                | ⚠️ Can be challenging                         | ✅ Good                                      |
| **Enterprise adoption**        | ⭐⭐⭐⭐⭐                                      | ⭐⭐⭐⭐                                          | ⭐⭐⭐⭐⭐                                       |
| **Best suited for**            | Broad browser automation                   | Modern web applications                       | Modern E2E + API automation                 |

### Key syntax difference

| Action     | Selenium                   | Cypress                      | Playwright                             |
| ---------- | -------------------------- | ---------------------------- | -------------------------------------- |
| Open page  | `driver.get(url)`          | `cy.visit(url)`              | `await page.goto(url)`                 |
| Click      | `element.click()`          | `cy.get(...).click()`        | `await page.locator(...).click()`      |
| Type       | `element.sendKeys("text")` | `cy.get(...).type("text")`   | `await page.locator(...).fill("text")` |
| Get text   | `element.getText()`        | `cy.get(...).invoke("text")` | `await locator.textContent()`          |
| Assertion  | TestNG/JUnit/etc.          | `cy.should(...)`             | `expect(locator).toHaveText(...)`      |
| Wait       | Explicit WebDriverWait     | Cypress auto-wait            | Playwright auto-wait                   |
| API GET    | RestAssured/HTTP client    | `cy.request()`               | `request.get()`                        |
| Screenshot | `takeScreenshot()`         | `cy.screenshot()`            | `page.screenshot()`                    |

### Interview-level summary

|                     | **Selenium**                               | **Cypress**                      | **Playwright**                         |
| ------------------- | ------------------------------------------ | -------------------------------- | -------------------------------------- |
| **Main strength**   | Mature ecosystem & flexibility             | Developer-friendly web testing   | Fast, reliable modern automation       |
| **Main limitation** | More boilerplate/wait handling             | Browser architecture limitations | Smaller legacy ecosystem than Selenium |
| **Best for**        | Enterprise/legacy + broad language support | Frontend-focused teams           | Modern E2E/API/parallel automation     |

**Simple way to remember:**

* **Selenium →** Mature + flexible + huge ecosystem
* **Cypress →** Simple + developer-friendly + frontend focused
* **Playwright →** Modern + fast + cross-browser + E2E/API

For an **enterprise QA automation framework**, the practical choice often comes down to whether you need Selenium's mature ecosystem/legacy compatibility or Playwright's modern browser, API, parallelization, and debugging capabilities.

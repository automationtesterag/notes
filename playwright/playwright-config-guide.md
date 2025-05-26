# Playwright Configuration Guide

## Introduction

The Playwright configuration file is a critical component when working with Playwright, a powerful browser automation and testing tool. It centralizes the configuration settings for running tests, allowing you to define how tests should behave, which browsers to use, test execution parameters, and more. By using a configuration file, you ensure consistency across test runs, simplify command-line usage, and make it easier to manage complex test setups.

The configuration file is typically named `playwright.config.js` (or `.ts` for TypeScript) and is placed in the root directory of your project. It is written in JavaScript (or TypeScript) and exports a configuration object using the `defineConfig` helper from `@playwright/test`.

## Importance of the Playwright Configuration File

- **Centralized Configuration**:
  - Consolidates all test-related settings in one place
  - Ensures that all tests run with the same settings, reducing inconsistencies and errors

- **Customizable Test Execution**:
  - Specify which browsers to test
  - Set timeouts and configure retries
  - Define parallelism and more

- **Environment Setup**:
  - Define global setup and teardown scripts
  - Set base URLs
  - Configure environment-specific settings

- **Simplified Command-Line Usage**:
  - Define options in the config file instead of passing multiple command-line arguments
  - Makes test execution commands shorter and more maintainable

- **Integration with CI/CD**:
  - Settings optimized for continuous integration environments
  - Headless mode, reporters for test results, and artifact collection

- **Reusability and Scalability**:
  - Define reusable test projects (e.g., different browser configurations)
  - Share settings across teams or projects

- **Extensibility**:
  - Add custom reporters
  - Set up authentication
  - Define custom test hooks

## Structure of the Playwright Configuration File

The configuration file uses the `defineConfig` helper to export a configuration object. Here's a basic example:

```javascript
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: './tests',
  timeout: 30000,
  retries: 2,
  use: {
    headless: true,
    viewport: { width: 1280, height: 720 },
    browserName: 'chromium',
  },
});
```

The `defineConfig` function provides IntelliSense and type-checking in TypeScript projects and ensures the configuration adheres to Playwright's schema.

## Complete Playwright Configuration Options

### 1. Top-Level Options

These are the root-level properties in the configuration object.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| **testDir** | string | `'./tests'` | Directory containing your test files |
| **testIgnore** | string \| RegExp \| (string \| RegExp)[] | - | Patterns to ignore when searching for test files |
| **testMatch** | string \| RegExp \| (string \| RegExp)[] | `['**/*.@(spec\|test).@(js\|ts\|mjs)']` | Patterns to match test files |
| **outputDir** | string | `'./test-results'` | Directory for test artifacts (screenshots, videos, traces) |
| **fullyParallel** | boolean | `false` | Enables running all tests in parallel |
| **forbidOnly** | boolean | `false` | Disallows the use of `test.only` in tests |
| **retries** | number | `0` | Number of times to retry a failed test |
| **workers** | number \| string | Half of CPU cores | Number of parallel test workers |
| **timeout** | number | `30000` | Maximum time (ms) for each test to complete |
| **globalTimeout** | number | No limit | Maximum time (ms) for the entire test suite |
| **maxFailures** | number | No limit | Stops test execution after a specified number of failures |
| **reporter** | string \| ReporterDescription[] | `['list']` | Specifies reporters for test results |
| **projects** | Project[] | - | Defines multiple test projects with different configurations |
| **globalSetup** | string | - | Path to a global setup script |
| **globalTeardown** | string | - | Path to a global teardown script |
| **grep** | RegExp \| RegExp[] | - | Filters tests by title matching regex |
| **grepInvert** | RegExp \| RegExp[] | - | Excludes tests by title matching regex |
| **preserveOutput** | 'always' \| 'never' \| 'failures-only' | `'failures-only'` | Controls when test artifacts are preserved |
| **quiet** | boolean | `false` | Suppresses console output during test execution |
| **updateSnapshots** | 'all' \| 'none' \| 'missing' | `'none'` | Controls when snapshots are updated |

#### Examples and Notes:

**testDir**:
```javascript
testDir: './e2e-tests',
```
*Note: Playwright recursively searches for files matching the testMatch pattern in this directory.*

**testIgnore**:
```javascript
testIgnore: ['**/*.spec.ts', '**/ignored-folder/**'],
```
*Note: Useful for excluding specific files or directories from test runs.*

**testMatch**:
```javascript
testMatch: ['**/*.e2e.ts'],
```
*Note: Use this to customize which files are considered tests.*

**outputDir**:
```javascript
outputDir: './artifacts',
```
*Note: Artifacts are organized by test name and run.*

**fullyParallel**:
```javascript
fullyParallel: true,
```
*Note: Set to true for faster execution if tests are independent.*

**forbidOnly**:
```javascript
forbidOnly: !!process.env.CI,
```
*Note: Useful for ensuring all tests run in production environments.*

**retries**:
```javascript
retries: 2,
```
*Note: Retries are useful for flaky tests, but overusing them can mask issues.*

**workers**:
```javascript
workers: 4,
```
*Note: Use a number (e.g., 4) or a percentage of CPU cores (e.g., '50%'). Set to 1 for sequential execution.*

**timeout**:
```javascript
timeout: 60000,
```
*Note: Can be overridden in individual tests using test.setTimeout().*

**globalTimeout**:
```javascript
globalTimeout: 3600000, // 1 hour
```
*Note: Useful for CI pipelines to prevent runaway test runs.*

**maxFailures**:
```javascript
maxFailures: 10,
```
*Note: Useful in CI to fail fast if too many tests fail.*

**reporter**:
```javascript
reporter: [
  ['list'],
  ['json', { outputFile: 'results.json' }],
  ['html', { open: 'never' }],
],
```
*Note: Built-in reporters include list, line, dot, json, junit, and html. Custom reporters can be added.*

**projects**:
```javascript
projects: [
  {
    name: 'chromium',
    use: { browserName: 'chromium' },
  },
  {
    name: 'firefox',
    use: { browserName: 'firefox' },
  },
  {
    name: 'mobile',
    use: { ...devices['iPhone 12'] },
  },
],
```
*Note: Each project runs independently, allowing multi-browser or multi-device testing.*

**globalSetup**:
```javascript
globalSetup: './global-setup.ts',
```
*Note: Used for tasks like authentication or setting up test data.*

**globalTeardown**:
```javascript
globalTeardown: './global-teardown.ts',
```
*Note: Used for cleanup tasks, like closing connections or resetting environments.*

**grep**:
```javascript
grep: /@smoke/,
```
*Note: Useful for running specific test suites (e.g., smoke tests).*

**grepInvert**:
```javascript
grepInvert: /@slow/,
```
*Note: Useful for skipping specific tests (e.g., slow tests in CI).*

**preserveOutput**:
```javascript
preserveOutput: 'always',
```
*Note: Set to 'always' for debugging or 'never' to save disk space.*

**quiet**:
```javascript
quiet: true,
```
*Note: Useful for cleaner CI logs.*

**updateSnapshots**:
```javascript
updateSnapshots: 'missing',
```
*Note: Use 'all' to update all snapshots or 'missing' to update only new ones.*

### 2. Use Options

The `use` object defines default settings for all tests (or specific projects). These settings are applied to every test unless overridden.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| **actionTimeout** | number | `0` (no timeout) | Timeout (ms) for Playwright actions |
| **navigationTimeout** | number | `0` (no timeout) | Timeout (ms) for navigation actions |
| **baseURL** | string | - | Base URL for all navigation actions |
| **browserName** | 'chromium' \| 'firefox' \| 'webkit' | - | Specifies the browser to use |
| **headless** | boolean | `true` | Runs browsers in headless mode |
| **viewport** | { width: number, height: number } \| null | `{ width: 1280, height: 720 }` | Sets browser viewport size |
| **deviceScaleFactor** | number | - | Specifies device pixel ratio |
| **isMobile** | boolean | - | Emulates mobile device viewport |
| **hasTouch** | boolean | - | Enables touch events |
| **userAgent** | string | - | Sets the user agent string |
| **locale** | string | - | Sets the browser's locale |
| **timezoneId** | string | - | Sets the browser's timezone |
| **geolocation** | { latitude: number, longitude: number, accuracy?: number } | - | Emulates geolocation |
| **permissions** | string[] | - | Grants specific permissions |
| **colorScheme** | 'light' \| 'dark' \| 'no-preference' | - | Sets color scheme preference |
| **contextOptions** | BrowserContextOptions | - | Additional browser context options |
| **screenshot** | 'on' \| 'off' \| 'only-on-failure' | `'off'` | Controls when screenshots are taken |
| **video** | 'on' \| 'off' \| 'retain-on-failure' \| 'on-first-retry' | `'off'` | Controls video recording |
| **trace** | 'on' \| 'off' \| 'retain-on-failure' \| 'on-first-retry' | `'off'` | Controls trace collection |
| **storageState** | string \| StorageState | - | Path to stored cookies/storage |
| **launchOptions** | LaunchOptions | - | Options for browser launch |
| **ignoreHTTPSErrors** | boolean | - | Ignores HTTPS errors |
| **javaScriptEnabled** | boolean | `true` | Enables/disables JavaScript |
| **bypassCSP** | boolean | - | Bypasses Content Security Policy |
| **offline** | boolean | - | Simulates offline mode |
| **httpCredentials** | { username: string, password: string } | - | HTTP authentication credentials |

#### Examples and Notes:

**actionTimeout**:
```javascript
use: {
  actionTimeout: 10000,
}
```

**navigationTimeout**:
```javascript
use: {
  navigationTimeout: 30000,
}
```

**baseURL**:
```javascript
use: {
  baseURL: 'https://example.com',
}
```
*Note: Simplifies test code by avoiding hard-coded URLs.*

**browserName**:
```javascript
use: {
  browserName: 'chromium',
}
```

**headless**:
```javascript
use: {
  headless: false,
}
```
*Note: Set to false for debugging with visible browser windows.*

**viewport**:
```javascript
use: {
  viewport: { width: 1920, height: 1080 },
}
```
*Note: Set to null to use the default browser window size.*

**deviceScaleFactor**:
```javascript
use: {
  deviceScaleFactor: 2,
}
```

**isMobile**:
```javascript
use: {
  isMobile: true,
}
```

**hasTouch**:
```javascript
use: {
  hasTouch: true,
}
```

**userAgent**:
```javascript
use: {
  userAgent: 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)',
}
```

**locale**:
```javascript
use: {
  locale: 'en-US',
}
```

**timezoneId**:
```javascript
use: {
  timezoneId: 'America/New_York',
}
```

**geolocation**:
```javascript
use: {
  geolocation: { latitude: 40.7128, longitude: -74.0060 },
}
```

**permissions**:
```javascript
use: {
  permissions: ['geolocation', 'notifications'],
}
```

**colorScheme**:
```javascript
use: {
  colorScheme: 'dark',
}
```

**contextOptions**:
```javascript
use: {
  contextOptions: {
    extraHTTPHeaders: { 'X-Custom-Header': 'value' },
  },
}
```

**screenshot**:
```javascript
use: {
  screenshot: 'only-on-failure',
}
```

**video**:
```javascript
use: {
  video: 'retain-on-failure',
}
```

**trace**:
```javascript
use: {
  trace: 'on-first-retry',
}
```
*Note: Traces include network requests, screenshots, and DOM snapshots for debugging.*

**storageState**:
```javascript
use: {
  storageState: 'auth.json',
}
```
*Note: Useful for skipping login steps in tests.*

**launchOptions**:
```javascript
use: {
  launchOptions: {
    slowMo: 50, // 50ms delay between actions
    devtools: true,
  },
}
```

**ignoreHTTPSErrors**:
```javascript
use: {
  ignoreHTTPSErrors: true,
}
```

**javaScriptEnabled**:
```javascript
use: {
  javaScriptEnabled: false,
}
```

**bypassCSP**:
```javascript
use: {
  bypassCSP: true,
}
```

**offline**:
```javascript
use: {
  offline: true,
}
```

**httpCredentials**:
```javascript
use: {
  httpCredentials: { username: 'user', password: 'pass' },
}
```

### 3. Expect Options

These options configure Playwright's expect assertions.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| **timeout** | number | `5000` (5 seconds) | Timeout (ms) for expect assertions |
| **toMatchSnapshot** | Object | - | Options for snapshot assertions |
| **toHaveScreenshot** | Object | - | Options for screenshot assertions |

#### Examples and Notes:

**timeout**:
```javascript
expect: {
  timeout: 10000,
}
```

**toMatchSnapshot**:
```javascript
expect: {
  toMatchSnapshot: {
    threshold: 0.1,
  },
}
```

**toHaveScreenshot**:
```javascript
expect: {
  toHaveScreenshot: {
    maxDiffPixels: 100,
    maxDiffPixelRatio: 0.02,
    threshold: 0.1,
  },
}
```

### 4. Web Server Options

Configures a web server to start before running tests.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| **command** | string | - | Command to start the web server |
| **url** | string | - | URL to wait for the server to respond |
| **timeout** | number | `60000` (60 seconds) | Timeout (ms) for server startup |
| **reuseExistingServer** | boolean | `true` | Reuses existing server if running |
| **cwd** | string | - | Working directory for server command |
| **env** | Record<string, string> | - | Environment variables for server |

#### Examples and Notes:

**command**:
```javascript
webServer: {
  command: 'npm run start',
}
```

**url**:
```javascript
webServer: {
  url: 'http://localhost:3000',
}
```

**timeout**:
```javascript
webServer: {
  timeout: 120000,
}
```

**reuseExistingServer**:
```javascript
webServer: {
  reuseExistingServer: false,
}
```

**cwd**:
```javascript
webServer: {
  cwd: './frontend',
}
```

**env**:
```javascript
webServer: {
  env: { PORT: '8080' },
}
```

### 5. Project Dependencies

| Option | Type | Description |
|--------|------|-------------|
| **dependencies** | string[] | Projects that must complete before current project runs |

#### Example:

```javascript
projects: [
  { name: 'setup', use: { browserName: 'chromium' } },
  {
    name: 'tests',
    dependencies: ['setup'],
    use: { browserName: 'chromium' },
  },
]
```
*Note: Useful for running setup projects (e.g., authentication) before tests.*

## Complete Configuration Example

```javascript
const { defineConfig, devices } = require('@playwright/test');

module.exports = defineConfig({
  // Top-level options
  testDir: './tests/e2e',
  testMatch: ['**/*.spec.ts'],
  testIgnore: ['**/*.unit.ts'],
  outputDir: './test-results',
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: 2,
  workers: '50%',
  timeout: 60000,
  globalTimeout: 3600000,
  maxFailures: 10,
  reporter: [
    ['list'],
    ['json', { outputFile: 'results.json' }],
    ['html', { open: 'never' }],
  ],
  globalSetup: './global-setup.ts',
  globalTeardown: './global-teardown.ts',
  grep: /@smoke/,
  grepInvert: /@slow/,
  preserveOutput: 'failures-only',
  quiet: false,
  updateSnapshots: 'missing',

  // Default test settings
  use: {
    actionTimeout: 10000,
    navigationTimeout: 30000,
    baseURL: 'https://example.com',
    browserName: 'chromium',
    headless: !!process.env.CI,
    viewport: { width: 1280, height: 720 },
    deviceScaleFactor: 1,
    isMobile: false,
    hasTouch: false,
    userAgent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)',
    locale: 'en-US',
    timezoneId: 'America/New_York',
    geolocation: { latitude: 40.7128, longitude: -74.0060 },
    permissions: ['geolocation'],
    colorScheme: 'light',
    contextOptions: {
      extraHTTPHeaders: { 'X-Test': 'true' },
    },
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'on-first-retry',
    storageState: 'auth.json',
    launchOptions: {
      slowMo: 50,
      devtools: false,
    },
    ignoreHTTPSErrors: true,
    javaScriptEnabled: true,
    bypassCSP: false,
    offline: false,
    httpCredentials: {
      username: 'testuser',
      password: 'testpass',
    },
  },

  // Expect assertion settings
  expect: {
    timeout: 10000,
    toMatchSnapshot: {
      threshold: 0.1,
    },
    toHaveScreenshot: {
      maxDiffPixels: 100,
      maxDiffPixelRatio: 0.02,
      threshold: 0.1,
    },
  },

  // Web server configuration
  webServer: {
    command: 'npm run start',
    url: 'http://localhost:3000',
    timeout: 120000,
    reuseExistingServer: !process.env.CI,
    cwd: './frontend',
    env: {
      PORT: '8080',
      NODE_ENV: 'test',
    },
  },

  // Project configurations
  projects: [
    {
      name: 'setup',
      use: { browserName: 'chromium' },
      testMatch: 'global.setup.ts',
    },
    {
      name: 'chromium',
      use: {
        browserName: 'chromium',
        viewport: { width: 1280, height: 720 },
      },
      dependencies: ['setup'],
    },
    {
      name: 'firefox',
      use: {
        browserName: 'firefox',
        viewport: { width: 1280, height: 720 },
      },
      dependencies: ['setup'],
    },
    {
      name: 'webkit',
      use: {
        browserName: 'webkit',
        viewport: { width: 1280, height: 720 },
      },
      dependencies: ['setup'],
    },
    {
      name: 'mobile-chrome',
      use: {
        ...devices['Pixel 5'],
        browserName: 'chromium',
      },
      dependencies: ['setup'],
    },
    {
      name: 'mobile-safari',
      use: {
        ...devices['iPhone 12'],
        browserName: 'webkit',
      },
      dependencies: ['setup'],
    },
  ],
});
```

## How to Use the Configuration File

### Create the File
Save the configuration as `playwright.config.js` or `playwright.config.ts` in your project root.

### Run Tests
- Use the `npx playwright test` command to run tests with the configuration
- Filter projects with `--project` (e.g., `npx playwright test --project=chromium`)
- Run specific tests with `--grep` or `--grep-invert` (e.g., `npx playwright test --grep @smoke`)

### Debugging
- Use `headless: false` and `slowMo: 50` to watch tests in real-time
- Enable `trace: 'on'` or `video: 'on'` to capture detailed debugging information
- Run `npx playwright show-report` to view the HTML report

### CI Integration
- Set `headless: true`, `forbidOnly: true`, and `reuseExistingServer: false` for CI environments
- Use reporters like `json` or `junit` for CI pipeline integration

### Snapshot Testing
- Run `npx playwright test --update-snapshots` to update snapshots when changes are expected

## Additional Notes

### Environment Variables
- Use `process.env` to make the configuration dynamic:
  ```javascript
  headless: !!process.env.CI  // enables headless mode only in CI
  baseURL: process.env.BASE_URL || 'https://example.com'
  ```

### Custom Reporters
- Create custom reporters by implementing the Reporter interface:
  ```javascript
  reporter: [['./custom-reporter.js']]
  ```

### TypeScript Support
- For TypeScript projects, use `playwright.config.ts`
- The `defineConfig` helper provides type safety and IntelliSense

### Extending Configurations
- Import and extend configurations for modularity:
  ```javascript
  const baseConfig = require('./base.config');
  module.exports = defineConfig({
    ...baseConfig,
    testDir: './custom-tests',
  });
  ```

### Common Use Cases
- **Multi-browser testing**: Use projects to run tests across Chromium, Firefox, and WebKit
- **Mobile testing**: Use devices for mobile device emulation
- **Authenticated tests**: Use globalSetup to log in and save state, then reuse with storageState
- **Visual regression testing**: Use toHaveScreenshot and updateSnapshots for UI consistency

### Performance Optimization
- Set `fullyParallel: true` and adjust workers to maximize test speed
- Use `maxFailures` to fail fast in CI
- Minimize artifacts with `preserveOutput: 'failures-only'`

## Conclusion

The Playwright configuration file is essential for defining how tests are executed, ensuring consistency, and optimizing test runs for different environments. By leveraging the full range of configuration options, you can tailor Playwright to your project's needs, whether it's multi-browser testing, mobile emulation, or CI/CD integration.

The example above covers all available options, providing a starting point for most use cases. Adjust the settings based on your project requirements, and use environment variables or project-specific configurations for flexibility.

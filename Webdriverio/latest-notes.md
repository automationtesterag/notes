# WebdriverIO — Complete Notes

## Table of Contents

1. [What is WebdriverIO?](#1-what-is-webdriverio)
2. [Protocols Used by WebdriverIO](#2-protocols-used-by-webdriverio)
3. [Selenium vs. WebdriverIO](#3-selenium-vs-webdriverio)
4. [Architecture](#4-architecture)
5. [Pros](#5-pros)
6. [Cons](#6-cons)
7. [Prerequisites](#7-prerequisites)
8. [WebdriverIO + TypeScript Setup](#8-webdriverio--typescript-setup)
   - [8.1 Setup Using WDIO Wizard](#81-setup-using-wdio-wizard)
9. [Manual Web Setup](#9-manual-web-setup)
10. [Manual Mobile Setup — Android + iOS (Combined)](#10-manual-mobile-setup--android--ios-combined)
11. [Manual Setup — Web + Mobile Combined (Single Project)](#11-manual-setup--web--mobile-combined-single-project)
12. [Package Notes — What Each Package Does](#12-package-notes--what-each-package-does)

---

## 1. What is WebdriverIO?

**WebdriverIO** is an open-source automation test framework built around **Node.js**. It allows you to write tests in JavaScript or TypeScript and is primarily used for testing web and mobile applications. WebdriverIO integrates with the WebDriver protocol for controlling web browsers and Appium for mobile testing. It also supports various testing frameworks like Mocha, Jasmine, and Cucumber.

---

## 2. Protocols Used by WebdriverIO

1. **WebDriver Protocol**
   - A protocol designed for automating browsers by driving browser behavior programmatically.
   - Follows the W3C WebDriver specification, the standard protocol for browser automation.
   - Selenium WebDriver is the original/reference implementation of this protocol — WebDriver isn't "formerly" Selenium, Selenium is one of the tools built on the WebDriver spec.

2. **DevTools Protocol (CDP)**
   - WebdriverIO can use the Chrome DevTools Protocol to interact with Chrome or Chromium-based browsers.
   - Provides additional features like network throttling, capturing JavaScript console logs, and accessing browser events directly.

---

## 3. Selenium vs. WebdriverIO

**Selenium (WebDriver)** is a low-level browser automation protocol/library. It gives you raw APIs to find elements, click, type, etc. It's the foundation many other tools build on top of.

**WebdriverIO (WDIO)** is a full test automation framework that uses the WebDriver protocol (and other protocols) under the hood. WDIO isn't a competitor to Selenium — it's built on the same underlying spec, but wraps it with a lot more tooling.

### Key Differences

| # | Aspect | Selenium | WebdriverIO |
|---|--------|----------|-------------|
| 1 | **Level of abstraction** | Raw WebDriver calls (`driver.findElement(By.id("x")).click()`); you assemble your own test runner, assertion library, reporter, etc. | Complete framework — built-in test runner, assertion library (`expect-webdriverio`), parallel execution, plugin/service ecosystem. Much less boilerplate. |
| 2 | **Language/ecosystem** | Language-agnostic — bindings for Java, Python, C#, Ruby, JavaScript, etc. | JavaScript/TypeScript (Node.js) only. |
| 3 | **Protocol support** | Strictly W3C WebDriver protocol. | WebDriver protocol **and** Chrome DevTools Protocol — enables network interception, mocking, etc. without extra glue code. |
| 4 | **Mobile/native app testing** | No native mobile support — must pair with Appium separately. | First-class Appium integration built into config — unified web + mobile automation. |
| 5 | **Setup & config** | Manual driver management, browser capabilities, and wiring in a test framework (JUnit, TestNG, pytest, Mocha, etc.). | CLI wizard (`npm init wdio`) scaffolds the whole project — driver management, reporters, services included. |
| 6 | **Community services/plugins** | Integrations (Sauce Labs, BrowserStack, reporting, etc.) usually done manually. | Rich service ecosystem plugs in with minimal config (Sauce Labs, BrowserStack, visual regression, Allure reporting, etc.). |

**In short:** Selenium is the underlying browser-automation engine/spec; WDIO is a batteries-included framework built on top of WebDriver (and CDP) that reduces boilerplate, adds mobile support, and ships with a runner and ecosystem out of the box. WDIO generally means less setup work and a more modern JS-first developer experience — at the cost of being locked into the Node.js ecosystem.

---

## 4. Architecture

### Web

```text
Test (JS/TS)
     ↓
WebdriverIO
     ↓
WebDriver
     ↓
Browser Driver
     ↓
Browser
```

### Mobile

```text
Test (JS/TS)
     ↓
WebdriverIO
     ↓
Appium
     ↓
UiAutomator2 / XCUITest
     ↓
Android / iOS
```

---

## 5. Pros

* ✅ JavaScript & TypeScript support
* ✅ Web + Mobile automation
* ✅ Strong Appium integration
* ✅ Selenium/WebDriver support
* ✅ Parallel execution
* ✅ Mocha/Cucumber/Jasmine support
* ✅ Allure and other reporting options
* ✅ CI/CD friendly
* ✅ BrowserStack/Sauce Labs support
* ✅ Good plugin ecosystem

---

## 6. Cons

* ❌ Setup can be complex for beginners
* ❌ Configuration can become complicated
* ❌ WebDriver adds extra communication layers
* ❌ Debugging can involve multiple components
* ❌ Mobile setup requires Appium + SDKs
* ❌ Dependency/version management needs attention

---

## 7. Prerequisites

### Web

* Node.js
* npm
* Browser
* VS Code — recommended
* Git — recommended

Check:

```bash
node -v
npm -v
```

### Mobile — Android

Additionally:

* Java/JDK
* Android SDK
* ADB
* Appium
* UiAutomator2
* Android Emulator/device

### Mobile — iOS

Additionally (macOS only):

* Xcode + Command Line Tools
* Appium
* XCUITest driver
* `ios-deploy` (real devices, optional)
* iOS Simulator or real device

---

## 8. WebdriverIO + TypeScript Setup

There are **two ways** to set up WebdriverIO:

1. **WDIO Wizard** — easiest
2. **Manual Setup** — useful for understanding and custom frameworks

### 8.1 Setup Using WDIO Wizard

**Step 1 — Create project**

```bash
mkdir webdriverio-project
cd webdriverio-project
npm init -y
```

**Step 2 — Initialize WDIO**

```bash
npm init wdio@latest .
```

Select:

```text
Language  → TypeScript
Framework → Mocha
Browser   → Chrome
Reporter  → Spec
```

**Step 3 — Run tests**

```bash
npx wdio run wdio.conf.ts
```

---

## 9. Manual Web Setup

**Step 1 — Create project**

```bash
mkdir webdriverio-web
cd webdriverio-web
npm init -y
```

**Step 2 — Install dependencies**

```bash
npm install -D \
@wdio/local-runner@9 \
@wdio/mocha-framework@9 \
@wdio/spec-reporter@9 \
@wdio/globals@9 \
@types/node \
typescript \
expect-webdriverio
```

> Keep all `@wdio/*` packages on the same major version. See [Section 12](#12-package-notes--what-each-package-does) for what each package does.

**Step 3 — Create structure**

```text
webdriverio-web/
│
├── test/
│   └── specs/
│       └── login.test.ts
│
├── wdio.conf.ts
├── tsconfig.json
└── package.json
```

**Step 4 — `tsconfig.json`**

> `tsconfig.json` is parsed by TypeScript's own tooling, which allows `//` comments even though the file has a `.json` extension (this is sometimes called JSONC). Comments below explain each option.

```jsonc
{
  "compilerOptions": {
    "target": "ES2022",              // compile output to modern JS (Node 18+ supports this)
    "module": "NodeNext",            // use Node's native ESM/CJS module resolution
    "moduleResolution": "NodeNext",  // must match "module" when using NodeNext
    "strict": true,                  // enable all strict type-checking options
    "esModuleInterop": true,         // allows default imports from CommonJS modules
    "skipLibCheck": true,            // skip type-checking of .d.ts files, faster builds
    "types": [
      "node",                       // Node.js global types (process, __dirname, etc.)
      "@wdio/globals/types",        // types for global $, $$, browser, driver
      "expect-webdriverio"          // types for expect(...).toBeDisplayed() etc.
    ]
  },
  "include": [
    "./test/**/*.ts",   // include all test spec files
    "./wdio.conf.ts"    // include the config file itself so it's type-checked too
  ]
}
```

**Step 5 — `wdio.conf.ts`**

```ts
import type { Options } from '@wdio/types';

// Main WebdriverIO configuration for running tests against a desktop browser.
export const config: Options.Testrunner = {

    // Runs the tests on the local machine (as opposed to a remote grid).
    runner: 'local',

    // Glob pattern pointing to the test spec files to execute.
    specs: ['./test/specs/**/*.ts'],

    // Max number of browser instances to run in parallel.
    maxInstances: 2,

    // Browser(s)/environment(s) the tests should run against.
    capabilities: [
        {
            browserName: 'chrome'
        }
    ],

    // Test framework used to structure and run the specs.
    framework: 'mocha',

    // Reporter that prints results to the terminal.
    reporters: ['spec'],

    // Mocha-specific options.
    mochaOpts: {
        timeout: 60000 // fail a test if it takes longer than 60s
    }
};
```

**Step 6 — Run**

```bash
npx wdio run wdio.conf.ts
```

---

## 10. Manual Mobile Setup — Android + iOS (Combined)

One project, **one Appium server, one shared config** — only `capabilities` differ per platform.

### Architecture

```text
WebdriverIO
     ↓
Appium
     ↓
UiAutomator2 (Android) / XCUITest (iOS)
     ↓
Android / iOS
```

**Step 1 — Install WDIO + Appium (once, covers both platforms)**

```bash
npm install -D \
@wdio/local-runner@9 \
@wdio/mocha-framework@9 \
@wdio/spec-reporter@9 \
@wdio/globals@9 \
@wdio/appium-service@9 \
@types/node \
typescript \
expect-webdriverio

npm install -g appium
```

> See [Section 12](#12-package-notes--what-each-package-does) for what each package does.

**Step 2 — Install both drivers**

```bash
appium driver install uiautomator2
appium driver install xcuitest
appium driver list
```

**Step 3 — Verify devices**

```bash
adb devices                 # Android emulator/device
xcrun simctl list devices   # iOS simulator (macOS only)
```

**Step 4 — Project structure**

```text
webdriverio-mobile/
│
├── test/
│   └── specs/
│       └── login.test.ts
│
├── config/
│   ├── wdio.shared.conf.ts
│   ├── wdio.android.conf.ts
│   └── wdio.ios.conf.ts
│
├── tsconfig.json
└── package.json
```

**Step 5 — `config/wdio.shared.conf.ts` (common to both)**

```ts
import type { Options } from '@wdio/types';

// Base config shared by both Android and iOS. Platform-specific files
// (wdio.android.conf.ts / wdio.ios.conf.ts) spread this and override capabilities.
export const config: Options.Testrunner = {
    runner: 'local',                       // run locally, not on a remote grid
    specs: ['../test/specs/**/*.ts'],       // shared spec files for mobile tests
    maxInstances: 1,                        // one device/session at a time
    framework: 'mocha',
    reporters: ['spec'],
    services: ['appium'],                   // auto-starts/stops the Appium server
    mochaOpts: { timeout: 60000 }
};
```

**Step 6 — `config/wdio.android.conf.ts`**

```ts
import { config as shared } from './wdio.shared.conf.js';

// Android-specific config: reuses the shared settings, adds Android capabilities.
export const config: typeof shared = {
    ...shared,
    capabilities: [
        {
            platformName: 'Android',
            'appium:automationName': 'UiAutomator2', // Appium driver for Android
            'appium:deviceName': 'Android',            // emulator/device name
            'appium:app': '/path/to/app.apk'           // path to the .apk under test
        }
    ]
};
```

**Step 7 — `config/wdio.ios.conf.ts`**

```ts
import { config as shared } from './wdio.shared.conf.js';

// iOS-specific config: reuses the shared settings, adds iOS capabilities.
export const config: typeof shared = {
    ...shared,
    capabilities: [
        {
            platformName: 'iOS',
            'appium:automationName': 'XCUITest',   // Appium driver for iOS
            'appium:deviceName': 'iPhone 15',        // simulator/device name
            'appium:platformVersion': '17.0',        // iOS version of the simulator/device
            'appium:app': '/path/to/app.app'         // path to the .app under test
        }
    ]
};
```

For already-installed apps, replace `appium:app` with `appium:appPackage` + `appium:appActivity` (Android) or `appium:bundleId` (iOS).

**Step 8 — `package.json` scripts**

```json
{
  "scripts": {
    "test:android": "wdio run ./config/wdio.android.conf.ts",
    "test:ios": "wdio run ./config/wdio.ios.conf.ts"
  }
}
```

**Step 9 — Run**

```bash
npm run test:android
npm run test:ios
```

> `@wdio/appium-service` auto-starts/stops Appium for each run — no need to run `appium` manually.

---

## 11. Manual Setup — Web + Mobile Combined (Single Project)

This merges Sections 9 and 10 into **one project** that can run web (Chrome) and mobile (Android + iOS) tests side by side, each with its own config file but sharing common settings, specs folder structure, and `package.json`.

### Architecture

```text
                Test (JS/TS)
                     ↓
                WebdriverIO
               ↙            ↘
        WebDriver           Appium
             ↓              ↙     ↘
       Browser Driver  UiAutomator2  XCUITest
             ↓              ↓          ↓
          Browser        Android      iOS
```

**Step 1 — Create project**

```bash
mkdir webdriverio-combined
cd webdriverio-combined
npm init -y
```

**Step 2 — Install dependencies (covers web + mobile)**

```bash
npm install -D \
@wdio/local-runner@9 \
@wdio/mocha-framework@9 \
@wdio/spec-reporter@9 \
@wdio/globals@9 \
@wdio/appium-service@9 \
@types/node \
typescript \
expect-webdriverio

npm install -g appium
appium driver install uiautomator2
appium driver install xcuitest
```

> `@wdio/appium-service` and the Appium drivers are only used by the mobile configs — installing them doesn't affect the web config. See [Section 12](#12-package-notes--what-each-package-does) for what each package does.

**Step 3 — Project structure**

```text
webdriverio-combined/
│
├── test/
│   └── specs/
│       ├── web/
│       │   └── login.web.test.ts       # tests that run in Chrome
│       └── mobile/
│           └── login.mobile.test.ts    # tests that run via Appium
│
├── config/
│   ├── wdio.shared.conf.ts    # settings common to ALL runs (web + mobile)
│   ├── wdio.web.conf.ts       # web-only: browser capabilities, no Appium
│   ├── wdio.android.conf.ts   # mobile: Android capabilities + Appium service
│   └── wdio.ios.conf.ts       # mobile: iOS capabilities + Appium service
│
├── tsconfig.json
└── package.json
```

**Step 4 — `tsconfig.json`**

```jsonc
{
  "compilerOptions": {
    "target": "ES2022",              // modern JS output
    "module": "NodeNext",            // native Node module resolution
    "moduleResolution": "NodeNext",  // must match "module"
    "strict": true,                  // strict type-checking
    "esModuleInterop": true,         // default imports from CommonJS modules
    "skipLibCheck": true,            // skip type-checking .d.ts files
    "types": [
      "node",                       // Node.js globals
      "@wdio/globals/types",        // global $, $$, browser, driver
      "expect-webdriverio"          // expect(...).toBeDisplayed() etc.
    ]
  },
  "include": [
    "./test/**/*.ts",     // web + mobile specs (both live under test/)
    "./config/**/*.ts"    // type-check all config files too
  ]
}
```

**Step 5 — `config/wdio.shared.conf.ts`**

```ts
import type { Options } from '@wdio/types';

// Settings common to every run — web AND mobile.
// Platform-specific configs spread this object and override/extend what they need.
export const config: Options.Testrunner = {
    runner: 'local',        // run on the local machine
    maxInstances: 1,        // one session at a time (raise for parallel runs)
    framework: 'mocha',     // test framework
    reporters: ['spec'],    // console reporter
    mochaOpts: {
        timeout: 60000      // 60s per test before it's marked as failed
    }
};
```

**Step 6 — `config/wdio.web.conf.ts`**

```ts
import { config as shared } from './wdio.shared.conf.js';

// Web-only config: points at the web specs, runs in Chrome, no Appium service.
export const config: typeof shared = {
    ...shared,
    specs: ['../test/specs/web/**/*.ts'],  // only web specs
    capabilities: [
        {
            browserName: 'chrome'   // desktop browser to automate
        }
    ]
    // no `services` here — Appium isn't needed for browser tests
};
```

**Step 7 — `config/wdio.android.conf.ts`**

```ts
import { config as shared } from './wdio.shared.conf.js';

// Android config: points at the mobile specs, adds Appium service + Android capabilities.
export const config: typeof shared = {
    ...shared,
    specs: ['../test/specs/mobile/**/*.ts'],  // only mobile specs
    services: ['appium'],                      // auto-starts/stops Appium server
    capabilities: [
        {
            platformName: 'Android',
            'appium:automationName': 'UiAutomator2', // Appium driver for Android
            'appium:deviceName': 'Android',            // emulator/device name
            'appium:app': '/path/to/app.apk'           // path to the .apk under test
        }
    ]
};
```

**Step 8 — `config/wdio.ios.conf.ts`**

```ts
import { config as shared } from './wdio.shared.conf.js';

// iOS config: points at the mobile specs, adds Appium service + iOS capabilities.
export const config: typeof shared = {
    ...shared,
    specs: ['../test/specs/mobile/**/*.ts'],  // only mobile specs
    services: ['appium'],                      // auto-starts/stops Appium server
    capabilities: [
        {
            platformName: 'iOS',
            'appium:automationName': 'XCUITest',   // Appium driver for iOS
            'appium:deviceName': 'iPhone 15',        // simulator/device name
            'appium:platformVersion': '17.0',        // iOS version of simulator/device
            'appium:app': '/path/to/app.app'         // path to the .app under test
        }
    ]
};
```

**Step 9 — `package.json` scripts**

```json
{
  "scripts": {
    "test:web": "wdio run ./config/wdio.web.conf.ts",
    "test:android": "wdio run ./config/wdio.android.conf.ts",
    "test:ios": "wdio run ./config/wdio.ios.conf.ts",
    "test:all": "npm run test:web && npm run test:android && npm run test:ios"
  }
}
```

**Step 10 — Run**

```bash
npm run test:web        # runs only web/Chrome specs
npm run test:android    # runs only Android specs via Appium
npm run test:ios        # runs only iOS specs via Appium
npm run test:all        # runs all three, one after another
```

> Why this works: each platform config imports the same `wdio.shared.conf.ts` and only overrides `specs`, `capabilities`, and `services`. That keeps timeouts, framework, and reporter settings consistent everywhere, while letting each platform run independently or all together.

---

## 12. Package Notes — What Each Package Does

| Package | Type | What it does |
|---|---|---|
| `@wdio/local-runner` | dev dep | The runner that executes tests on your local machine (as opposed to a cloud grid). Required by every WDIO project. |
| `@wdio/mocha-framework` | dev dep | Adapter that lets WDIO use **Mocha** as the test framework (`describe`/`it` syntax). |
| `@wdio/spec-reporter` | dev dep | Prints readable, spec-style test results to the terminal as tests run. |
| `@wdio/globals` | dev dep | Provides WDIO's global helpers (`$`, `$$`, `browser`, `driver`) without manual imports, plus their TypeScript types. |
| `@wdio/appium-service` | dev dep | WDIO service that automatically starts and stops the Appium server around your test run — only needed for mobile testing. |
| `@types/node` | dev dep | TypeScript type definitions for Node.js built-ins (`process`, `__dirname`, `fs`, etc.). |
| `typescript` | dev dep | The TypeScript compiler itself — needed to type-check and run `.ts` config/spec files. |
| `expect-webdriverio` | dev dep | WDIO's built-in assertion library (`expect(element).toBeDisplayed()`, etc.) and its types. |
| `appium` | global install | The Appium server — the bridge between WebdriverIO and mobile automation drivers. Installed globally (or as a dev dep) once per machine/project. |
| `uiautomator2` driver | Appium driver | Installed via `appium driver install uiautomator2`; lets Appium automate **Android** apps. |
| `xcuitest` driver | Appium driver | Installed via `appium driver install xcuitest`; lets Appium automate **iOS** apps (macOS only). |

> Tip: keep all `@wdio/*` packages on the **same major version** (e.g. all `@9`) to avoid compatibility issues between the runner, framework, reporter, and services.

---

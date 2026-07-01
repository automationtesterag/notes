# Playwright

## topics
1. JavaScript/Typescript Fundamentals
2. Web Browser Automation
3. API Testing with Playwright
4. Framework Design from scratch
5. AI Integration
6. CI-CD/cloud Integration
7. Interview Questions


## What is Playwright?

Playwright is an open-source browser automation framework developed by Microsoft for end-to-end testing of web applications.

It supports:
* ✅ Chromium (Chrome, Edge)
* ✅ Firefox
* ✅ WebKit (Safari)
* ✅ Windows, macOS, and Linux
* ✅ JavaScript, TypeScript, Python, Java, and C#

---

## Why use Playwright?

* Fast and reliable
* Auto-waits for elements (less flaky tests)
* Built-in parallel execution
* Network interception and API testing
* Multiple browser support
* Mobile device emulation
* Screenshots and video recording
* Trace Viewer for debugging

## Playwright vs Selenium

| Feature            | Playwright | Selenium                    |
| ------------------ | ---------- | --------------------------- |
| Speed              | ⭐⭐⭐⭐⭐      | ⭐⭐⭐                         |
| Auto Waiting       | ✅ Built-in | ❌ Manual waits often needed |
| Multiple Browsers  | ✅          | ✅                           |
| Parallel Execution | ✅ Built-in | Requires setup              |
| Network Mocking    | ✅          | Limited                     |
| Mobile Emulation   | ✅          | Limited                     |
| Trace Viewer       | ✅          | No built-in                 |

---

## Install Playwright

command to install `npm init playwright`

```
anudeepgubba@Anudeeps-MacBook-Air playwright % npm init playwright
Getting started with writing end-to-end tests with Playwright:
Initializing project in '.'
✔ Do you want to use TypeScript or JavaScript? · JavaScript
✔ Where to put your end-to-end tests? · tests
✔ Add a GitHub Actions workflow? (y/N) · true
✔ Install Playwright browsers (can be done manually via 'npx playwright install')? (Y/n) · true

```

## Command to run Tests
1. `npx playwright test`

## Report
`npx playwright show-report`

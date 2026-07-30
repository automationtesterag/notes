# Jenkins Notes

## Table of Contents

1. [Introduction to Jenkins](#1-introduction-to-jenkins)
2. [Why Jenkins?](#2-why-jenkins)
3. [Continuous Integration (CI)](#3-continuous-integration-ci)
4. [Continuous Deployment (CD)](#4-continuous-deployment-cd)
5. [Why QA Engineers Should Learn CI/CD](#5-why-qa-engineers-should-learn-cicd)
6. [CI vs CD (Quick Comparison)](#6-ci-vs-cd-quick-comparison)
7. [Jenkins Installation & Setup Notes](#jenkins-installation--setup-notes)

---

# 1. Introduction to Jenkins

## What is Jenkins?

Jenkins is an **open-source automation server** used to automate the software development process. It automatically **builds, tests, and deploys** applications whenever developers make changes to the code.

**In simple words:**

> Jenkins is a tool that automates repetitive tasks like build, testing, and deployment.

### Example

Without Jenkins:

* Developer writes code.
* QA manually runs automation tests.
* DevOps manually deploys the application.

With Jenkins:

* Developer pushes code to Git.
* Jenkins automatically builds the project.
* Runs Playwright/Selenium tests.
* Generates reports.
* Deploys the application (if configured).

### Key Features

* Open Source
* Free to use
* Supports 2000+ plugins
* Pipeline as Code (Jenkinsfile)
* Parallel execution
* Easy integration with Git, Docker, Playwright, Selenium, etc.

---

# 2. Why Jenkins?

Jenkins automates repetitive tasks, making software delivery **faster, reliable, and consistent**.

### Without Jenkins

* Manual builds
* Manual testing
* Manual deployment
* Slow releases
* Human errors

### With Jenkins

* Automatic builds
* Automatic test execution
* Automatic deployment
* Faster feedback
* Better software quality

### Example

A developer pushes code to GitHub.

Instead of manually:

* Downloading code
* Building the application
* Running tests

Jenkins automatically performs all these tasks in minutes.

---

# 3. Continuous Integration (CI)

## What is CI?

**Continuous Integration (CI)** is the practice of **frequently merging code changes into a shared repository**, where every commit automatically triggers a **build and automated tests**.

The goal is to detect bugs as early as possible.

### CI Workflow

```text
Developer
    ↓
Push Code
    ↓
Jenkins
    ↓
Build Application
    ↓
Run Automated Tests
    ↓
Generate Report
```

### Example

Developer fixes a login bug and pushes the code.

Jenkins automatically:

* Downloads the latest code
* Builds the application
* Runs Playwright tests
* Generates the test report
* Notifies the team if something fails

### Benefits

* Early bug detection
* Automatic builds
* Faster feedback
* Better code quality
* Stable codebase

---

# 4. Continuous Deployment (CD)

## What is Continuous Deployment?

**Continuous Deployment (CD)** is the practice of **automatically deploying** the application to the production environment after all builds and automated tests pass successfully.

No manual approval is required.

### CD Workflow

```text
Developer
    ↓
Push Code
    ↓
Jenkins
    ↓
Build
    ↓
Run Tests
    ↓
Deploy to Production
```

### Example

Developer fixes a payment issue.

After all automation tests pass, Jenkins automatically deploys the latest version to production, allowing users to access the fix immediately.

### Benefits

* Faster releases
* Fully automated deployment
* Reduced manual effort
* Consistent deployments
* Quick customer feedback

---

# 5. Why QA Engineers Should Learn CI/CD

Modern QA engineers are expected to integrate automation tests into CI/CD pipelines.

### Benefits for QA

* Run automation tests automatically
* Execute regression tests after every code change
* Receive instant feedback on failures
* Generate Allure/HTML reports automatically
* Reduce manual testing effort
* Work closely with Developers and DevOps teams

### Example

Instead of manually running 500 Playwright test cases before every release, Jenkins automatically executes the test suite whenever developers push new code.

---

# 6. CI vs CD (Quick Comparison)

| Feature         | Continuous Integration (CI) | Continuous Deployment (CD)       |
| --------------- | --------------------------- | -------------------------------- |
| Purpose         | Integrate and test code     | Deploy application automatically |
| Trigger         | Every code commit           | After successful CI pipeline     |
| Main Activity   | Build + Test                | Deployment                       |
| Manual Approval | Not Required                | Not Required                     |
| End Result      | Verified application        | Live application in production   |

---

# Quick Summary

| Topic                           | Summary                                                                                                |
| ------------------------------- | ------------------------------------------------------------------------------------------------------ |
| **Jenkins**                     | Automation server used for Build, Test, and Deployment.                                                |
| **Why Jenkins?**                | Saves time by automating repetitive software delivery tasks.                                           |
| **Continuous Integration (CI)** | Automatically builds and tests every code change.                                                      |
| **Continuous Deployment (CD)**  | Automatically deploys the application after all tests pass.                                            |
| **Why QA Should Learn CI/CD?**  | To automate testing, generate reports, reduce manual work, and integrate with modern DevOps workflows. |


# Jenkins Installation & Setup Notes

---

## 1. Prerequisites

Before installing Jenkins, ensure the following are installed:

* Java (JDK 21+)
* Git
* Web Browser
* Internet Connection
* Administrator Access

**Optional (Project Based):**

* Node.js (Playwright)
* Maven / Gradle
* Docker
* Allure Report

---

## 2. Jenkins Installation (Local Machine)

### Step 1: Install Java

Verify installation:

```bash
java -version
```

---

### Step 2: Download & Install Jenkins

* Download Jenkins installer.
* Install using default settings.
* Default Port: **8080**

---

### Step 3: Start Jenkins

Open:

```text
http://localhost:8080
```

---

### Step 4: Unlock Jenkins

* Copy the **initialAdminPassword** from the Jenkins installation folder.
* Paste it into the browser.

---

### Step 5: Install Suggested Plugins

Install commonly used plugins such as:

* Git
* Pipeline
* GitHub
* Maven
* Credentials

---

### Step 6: Create Admin User

Create an admin account and log in.

✅ Jenkins is now ready to use.

---

## 3. Initial Jenkins Setup

Configure the required tools:

* Git
* JDK
* Node.js (for Playwright)
* Credentials (GitHub Token, SSH Keys, AWS Keys)

**Navigation:**

```text
Manage Jenkins
      ↓
Tools / Credentials
```

---

## 4. Real-Time Project Setup

In real projects, Jenkins is installed on a **central server**, not on individual laptops.

### Typical Workflow

```text
Developer
     ↓
GitHub/GitLab
     ↓
Jenkins
     ↓
Build
     ↓
Run Automation Tests
     ↓
Generate Reports
     ↓
Deploy
     ↓
Email/Slack Notification
```

### Common Integrations

* GitHub / GitLab
* Playwright / Selenium
* Maven / npm
* Docker
* Allure Reports
* Slack / Email

**Best Practice:** Store pipelines in a **Jenkinsfile** and credentials in **Jenkins Credentials**, never in code.

---

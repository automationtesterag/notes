# OmniAuto — Build-From-Scratch Framework Guide

This is a **complete, standalone specification** of the OmniAuto Web/Mobile/API test
automation framework. It contains everything needed to rebuild the framework from an empty
directory — architecture, every core class's full source, every config file, every
convention, and the reasoning behind each decision — so that **no other file needs to be
opened** to reconstruct it.

**Stack:** JDK 17 · Maven · TestNG 7.10.2 · Selenium 4.25.0 · Appium Java client 9.3.0 ·
REST Assured 5.5.0 · ExtentReports 5.1.2 · Allure 2.29.0 (allure-testng) · Logback 1.5.8 ·
Jackson 2.18.0 (databind + YAML) · Apache POI 5.3.0 (Excel) · OpenCSV 5.9 · dotenv-java 3.0.2
· commons-lang3 3.17.0 · AspectJ 1.9.22.1 (Allure weaving) · JaCoCo 0.8.12.

---

## Table of contents

1. [Overview & design philosophy](#1-overview--design-philosophy)
2. [Project structure](#2-project-structure)
3. [Maven setup (pom.xml)](#3-maven-setup-pomxml)
4. [Build order — what depends on what](#4-build-order--what-depends-on-what)
5. [Package: exceptions](#5-package-exceptions)
6. [Package: enums](#6-package-enums)
7. [Package: constants](#7-package-constants)
8. [Package: config — ConfigManager](#8-package-config--configmanager)
9. [Package: secrets](#9-package-secrets)
10. [Package: context — VariableManager](#10-package-context--variablemanager)
11. [Package: utils](#11-package-utils)
12. [Package: testdata](#12-package-testdata)
13. [Package: driver](#13-package-driver)
14. [Package: web](#14-package-web)
15. [Package: mobile](#15-package-mobile)
16. [Package: api](#16-package-api)
17. [Package: reporting](#17-package-reporting)
18. [Package: listeners](#18-package-listeners)
19. [Resources — logback.xml & listener registration](#19-resources--logbackxml--listener-registration)
20. [Config files (repo root)](#20-config-files-repo-root)
21. [Test-code layer (src/test) — conventions & full examples](#21-test-code-layer-srctest--conventions--full-examples)
22. [TestNG group taxonomy & running tests](#22-testng-group-taxonomy--running-tests)
23. [CI/CD (GitHub Actions)](#23-cicd-github-actions)
24. [Reporting outputs — what gets produced](#24-reporting-outputs--what-gets-produced)
25. [Thread-safety model](#25-thread-safety-model)
26. [Known gotchas & lessons already paid for](#26-known-gotchas--lessons-already-paid-for)
27. [Build-from-scratch checklist](#27-build-from-scratch-checklist)

---

## 1. Overview & design philosophy

OmniAuto is **one framework** covering three surfaces — Web (Selenium), Mobile (Appium), and
API (REST Assured) — sharing configuration, secrets, driver/context management, test data,
logging, and reporting, driven entirely by TestNG command-line flags (`-D` system
properties). **There is no suite XML anywhere.**

Two Java source roots, two different jobs:

- **`src/main/java/com/framework/`** — the framework itself. Generic, reusable, knows
  nothing about any specific application under test. This is what ships as the framework's
  compiled artifact.
- **`src/test/java/com/tests/`** — application-specific code that exercises the framework
  against one real product (in the reference implementation, "eventhub", a demo
  events/bookings platform with Web, Mobile, and API surfaces). Test code depends on the
  framework; **the framework never depends on test code** (this is enforced structurally —
  `com.framework` classes never import anything from `com.tests`).

### Key ideas that shape every design decision below

- **Thread-safety is load-bearing.** Every shared object in `com.framework.*` is classified
  into exactly one of five categories (see [§25](#25-thread-safety-model)) and built
  accordingly. Nothing is "probably fine under parallel execution" — it is verified.
- **One placeholder syntax everywhere.** `${{KEY}}` resolves against secrets, config, and
  runtime/API context through a single `PlaceholderResolver`, anywhere text is resolved (test
  data, request bodies, ...).
- **No suite XML.** Class, method, group, browser, environment, parallel mode — all plain
  `-D` flags via Surefire's own TestNG provider. Picking a different subset of tests to run is
  never a file edit.
- **Zero boilerplate per test.** Retry, log-tagging, Extent/Allure reporting, and driver
  cleanup are automatic via TestNG listeners (`META-INF/services/org.testng.ITestNGListener`)
  — a test author writes `@Test` methods and business-level Page Object/Service calls, nothing
  else.
- **Fail fast, never mid-test.** Missing config, missing secrets, unresolved placeholders —
  all throw immediately at first access, not partway through a test run.
- **Masking is systemic, not opt-in per call site.** A `logger.info(...)` anywhere that
  touches a secret is masked automatically, via a Logback conversion word plus dedicated
  appenders — no call site has to remember to call `.mask()`.
- **Extend, don't wrap everything.** Base classes (`BasePage`, `BaseComponent`, ...) wrap only
  the handful of operations nearly every page/component needs. Everything else is called
  directly via a public `*Actions`/`*Utils` class — wrapping every possible Selenium/Appium
  method would be pass-through noise with no value.

### High-level architecture

```
                    TESTNG TESTS
                          |
          +---------------+---------------+
          |               |               |
         WEB            MOBILE            API
      Selenium          Appium        REST Assured
          |               |               |
     Page Objects    Page Objects     API Services
          |               |               |
          +---------------+---------------+
                          |
                   FRAMEWORK CORE
      Configuration · Driver · Data · Logging · Reporting
                          |
                  Parallel Execution
```

### Known, deliberate limitations

- **Mobile needs local infra** (a booted emulator/simulator + a running Appium server) — not
  available on a hosted CI runner, so mobile is excluded from CI by default. Use a
  self-hosted runner or a cloud device provider (BrowserStack is supported out of the box).
- **BrowserStack app upload is manual** — a one-time step per app version, via BrowserStack's
  own app-upload API; not automated by this framework.
- **An empty `-Dgroups=X` match still reports `BUILD SUCCESS`** — TestNG/Surefire has no
  concept of "this group tag doesn't exist, fail the build" built in. Always check the printed
  test count after a run.

---

## 2. Project structure

```
pom.xml
.secret.env.example        <- template; copy to .secret.env (git-ignored) and fill in real values
.gitignore
.github/workflows/ci.yml   <- GitHub Actions pipeline
scripts/clean-local.sh     <- removes accumulated local run artifacts

config/                     <- repo root, NOT src/main/resources — easy to find/edit directly
    dev.properties          <- one fully self-contained file per environment
    qa.properties           <- (qa is the default when -Denv is omitted)
    mobile-devices.json      <- device inventory, port pools, device matrices (shared across envs)

apps/                        <- mobile app binaries (e.g. release.apk, simulator .app)

src/main/java/com/framework/     <- THE FRAMEWORK. Generic, reusable, app-agnostic.
    api/            ApiClient, ApiRequest, ApiResponse, ApiContext, ApiHeaders, ApiUtils
                    - a generic REST client engine, no knowledge of any specific endpoint/DTO.
    config/         ConfigManager (4-tier precedence resolution)
    constants/      ConfigKeys (canonical config key name constants)
    context/        VariableManager (thread-safe runtime variable store)
    driver/         DriverFactory, DriverManager, WebDriverManager, MobileDriverManager,
                    MobileDeviceMatrix, MobileDevicePool, MobilePortAllocator
    enums/          BrowserType, Environment, MobilePlatformType, MobileDeviceProvider,
                    ScreenshotMode
    exceptions/     FrameworkException + typed subtypes
    listeners/      12 TestNG listeners (auto-registered via META-INF/services)
    mobile/         BaseMobilePage, BaseMobileComponent, MobileActions, MobileUtils,
                    MobileWaits, PlatformLocator — base classes only, no concrete screen here
    reporting/      ExtentManager, ExtentLoggingAppender, AllureManager, AllureLoggingAppender,
                    ReportManager, plus the API surface's own separate report:
                    ApiReportRecorder, ApiReportModel, ApiHtmlReportRenderer
    secrets/        SecretManager, SensitiveDataMasker, MaskingMessageConverter
    testdata/       TestDataManager, TestData, TestCaseMetadata, TestCaseRecord,
                    JSON/YAML/CSV/Excel readers, PlaceholderResolver, PlaceholderSource
    utils/          JsonUtils, FileUtils, ScreenshotUtils, DateUtils, RandomDataUtils,
                    EnumUtils, TextUtils, WaitUtils, Verify
    web/            BasePage, BaseComponent, WebActions, WebUtils, WebWaits — base classes
                    only, no concrete page here

src/main/resources/
    META-INF/services/org.testng.ITestNGListener   <- listener auto-registration (order matters!)
    logback.xml

src/test/java/com/tests/         <- APPLICATION-SPECIFIC. Everything that only makes sense
                                     because a particular app is under test. Two top-level
                                     packages, nothing else:
    tests/                        <- every *Test.java spec, and ONLY specs. A tester opening
        api/                         this package sees nothing but tests to read/write.
        mobile/
        web/
    application/                  <- everything a spec needs in order to run
        base/                        BaseApiTest / BaseMobileTest / BaseWebTest — shared
                                     @BeforeMethod/@AfterMethod lifecycle every spec extends
        pages/web/                   Web Page Objects
        pages/mobile/                Mobile Page Objects
        components/web/              Web Components (repeated/singleton UI elements)
        components/mobile/           Mobile Components
        requests/                    API request DTOs (Java records)
        responses/                   API response DTOs (Java records)
        services/                    Application API services, wrap com.framework.api.ApiClient
        testdata/                    Every surface's *TestCase records, gathered in one tree:
            TestDataSurface.java        one enum value per surface, knows each surface's file
            <Shared>TestCase.java       shapes shared across surfaces (e.g. login)
            api/                        API-only test-case shapes
            mobile/                     Mobile-only test-case shapes

src/test/resources/testdata/
    json/{web,api,android,ios}/<surface>.json
    yaml/{web,api,android,ios}/<surface>.yaml
    csv/{web,api,android,ios}/<surface>.csv
    excel/{web,api,android,ios}/<surface>.xlsx
```

**Listeners** (auto-registered via `META-INF/services/org.testng.ITestNGListener`, in this
exact order — order is semantically significant, see [§18](#18-package-listeners)):

| # | Listener | Job |
|---|---|---|
| 1 | `TestLoggingContextListener` | Tags every log line with `[ClassName.methodName]` via MDC |
| 2 | `RetryAnalyzerTransformer` | Assigns `RetryAnalyzer` to every `@Test` |
| 3 | `ConfigurationRetryListener` | Extends the same retry policy to `@BeforeMethod`/`@AfterMethod` |
| 4 | `ConfigParameterListener` | Bridges TestNG `<parameter>`s into `ConfigManager`, reset before every method |
| 5 | `ApiContextListener` | Clears API/runtime context around every test |
| 6 | `DriverCleanupListener` | Quits WebDriver/AppiumDriver after every test |
| 7 | `ExtentReportingListener` | Creates/finalizes the Extent report node per test |
| 8 | `ScreenshotCaptureListener` | Captures + attaches a failure screenshot to both reports |
| 9 | `AllureParameterMaskingListener` | Masks every Allure "Parameters" entry before it's written to disk |
| 10 | `AllureMetadataListener` | Feature/Story/Severity/Platform labels + environment.properties |
| 11 | `ApiTestReportListener` | Starts/finalizes an API test's record on the separate API report |
| 12 | `BeforeMethodAlwaysRunListener` | Fails the suite at start if any `@BeforeMethod` lacks `groups()`/`alwaysRun=true` |

## 3. Maven setup (pom.xml)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.framework</groupId>
    <artifactId>web-mobile-api-framework</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>Web-Mobile-API Automation Framework</name>
    <description>
        Unified enterprise automation framework: Web (Selenium), Mobile (Appium)
        and API (REST Assured), driven by TestNG, with Extent and Allure reporting.
    </description>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

        <testng.version>7.10.2</testng.version>
        <selenium.version>4.25.0</selenium.version>
        <appium.version>9.3.0</appium.version>
        <rest-assured.version>5.5.0</rest-assured.version>

        <slf4j.version>2.0.16</slf4j.version>
        <logback.version>1.5.8</logback.version>

        <extent-reports.version>5.1.2</extent-reports.version>
        <allure.version>2.29.0</allure.version>

        <jackson.version>2.18.0</jackson.version>
        <poi.version>5.3.0</poi.version>
        <opencsv.version>5.9</opencsv.version>

        <dotenv.version>3.0.2</dotenv.version>

        <commons-lang3.version>3.17.0</commons-lang3.version>

        <maven-compiler-plugin.version>3.13.0</maven-compiler-plugin.version>
        <maven-surefire-plugin.version>3.5.1</maven-surefire-plugin.version>
        <aspectj.version>1.9.22.1</aspectj.version>
        <jacoco.version>0.8.12</jacoco.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Selenium BOM keeps all selenium-* modules on matching versions -->
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-bom</artifactId>
                <version>${selenium.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- Jackson BOM keeps databind/yaml/annotations aligned -->
            <dependency>
                <groupId>com.fasterxml.jackson</groupId>
                <artifactId>jackson-bom</artifactId>
                <version>${jackson.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Test execution: suites, groups, parameters, DataProviders, parallel execution -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
        </dependency>

        <!-- Web automation - version pinned via selenium-bom above -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
        </dependency>

        <!-- Mobile automation - brings its own Selenium-compatible driver interfaces -->
        <dependency>
            <groupId>io.appium</groupId>
            <artifactId>java-client</artifactId>
            <version>${appium.version}</version>
        </dependency>

        <!-- API automation -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>
        <!-- JSON schema validation for API responses -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>${logback.version}</version>
        </dependency>

        <!-- Reporting -->
        <dependency>
            <groupId>com.aventstack</groupId>
            <artifactId>extentreports</artifactId>
            <version>${extent-reports.version}</version>
        </dependency>
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-testng</artifactId>
            <version>${allure.version}</version>
        </dependency>

        <!-- Test data: JSON / YAML - versions pinned via jackson-bom above -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-yaml</artifactId>
        </dependency>

        <!-- Test data: Excel -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>

        <!-- Test data: CSV -->
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>${opencsv.version}</version>
        </dependency>

        <!-- Secrets: local .secret.env loading -->
        <dependency>
            <groupId>io.github.cdimascio</groupId>
            <artifactId>dotenv-java</artifactId>
            <version>${dotenv.version}</version>
        </dependency>

        <!-- Utilities: StringUtils/RandomStringUtils etc. for RandomDataUtils, masking, null-safety -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${commons-lang3.version}</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>web-mobile-api-framework</finalName>
        <!-- config/ lives at the repo root, not nested under src/main/resources, so a tester
             can find and edit dev/qa.properties directly. Declaring any <resources> here
             requires re-declaring the default src/main/resources explicitly (Maven does not
             merge it in silently once you add a custom entry), so both are listed. targetPath
             places these on the classpath at "config/..." - the exact path ConfigManager
             already expects - so no framework code changes are needed. -->
        <resources>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
            <resource>
                <directory>config</directory>
                <targetPath>config</targetPath>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven-compiler-plugin.version}</version>
                <configuration>
                    <!-- Without this, Jackson cannot introspect Java record component names via
                         reflection and silently binds request/response records to default values
                         (0, null) instead of failing loudly. Applies to both default-compile and
                         default-testCompile. -->
                    <parameters>true</parameters>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven-surefire-plugin.version}</version>
                <configuration>
                    <!-- Deliberately no <suiteXmlFiles>/<groups> here, and no profile that adds
                         them: every test-selection concern (individual test, group, parallel
                         mode/thread count, browser, environment) is driven entirely by
                         command-line -D flags against Surefire's own classpath-wide TestNG
                         discovery. -D system properties (env, browser, headless, groups,
                         excludedGroups, parallel, threadCount, ...) are forwarded to the forked
                         test JVM by Surefire's systemPropertiesFile default behavior; they are
                         deliberately NOT re-declared here via systemPropertyVariables with ${...}
                         defaults, because an unset Maven property interpolates to an empty
                         string (not "undefined"), which would silently defeat ConfigManager's
                         own "dev" fallback. -->
                    <!-- Allure needs to weave test lifecycle via AspectJ for step/attachment
                         capture. @{argLine} (delayed-expansion syntax, not ${argLine}) is
                         JaCoCo's own documented way to combine its injected -javaagent flag with
                         a second, explicit one below - a plain ${argLine} risks resolving before
                         jacoco:prepare-agent (bound to the "initialize" phase, earlier than this)
                         has set it. -->
                    <argLine>
                        @{argLine} -javaagent:"${settings.localRepository}"/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar
                    </argLine>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.aspectj</groupId>
                        <artifactId>aspectjweaver</artifactId>
                        <version>${aspectj.version}</version>
                    </dependency>
                </dependencies>
            </plugin>

            <plugin>
                <!-- Framework code-coverage reporting. jacoco-check (the enforcing goal) is
                     deliberately not configured - a gate with no real framework-level self-test
                     suite behind it would just be measuring incidental coverage from the
                     API/Web/Mobile product tests, not a deliberate quality bar. report still
                     runs so `mvn test` produces target/site/jacoco/index.html for visibility. -->
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
                <executions>
                    <execution>
                        <id>jacoco-prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>jacoco-report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 4. Build order — what depends on what

Building this framework from scratch in the wrong order creates circular-looking confusion.
Build in this sequence — each layer only depends on layers before it:

1. **`exceptions`** — `FrameworkException` and its subtypes. Nothing depends on anything else
   yet; everything downstream throws these.
2. **`enums`** — `BrowserType`, `Environment`, `MobilePlatformType`, `MobileDeviceProvider`,
   `ScreenshotMode`. Depends only on `utils.EnumUtils` (write that alongside) and `exceptions`.
3. **`constants.ConfigKeys`** — pure string constants, no dependencies.
4. **`config.ConfigManager`** — depends on `constants`, `enums.Environment`, `exceptions`.
5. **`secrets`** — `SecretManager` (depends on `exceptions`), `SensitiveDataMasker` (no
   framework deps), `MaskingMessageConverter` (depends on `secrets.SensitiveDataMasker`,
   Logback API).
6. **`context.VariableManager`** — depends on `exceptions` only.
7. **`utils`** — `JsonUtils`, `FileUtils`, `ScreenshotUtils`, `DateUtils`, `RandomDataUtils`,
   `EnumUtils`, `TextUtils`, `WaitUtils` (depends on `config`, `constants`), `Verify` (depends
   on `reporting` — build this one *after* `reporting`, or stub it last).
8. **`testdata`** — depends on `config`, `constants`, `secrets`, `context` (indirectly via
   `ApiContext`, registered later), `utils.FileUtils`/`JsonUtils`, `reporting.AllureManager`
   (for `getCaseData`'s metadata attachment — build `reporting` first, or defer that one call).
9. **`driver`** — depends on `config`, `constants`, `enums`, `exceptions`, `secrets`
   (BrowserStack credentials), `utils.JsonUtils`.
10. **`web`** — depends on `driver.WebDriverManager`, `config`, `constants`, `enums`,
    `exceptions`, `utils.WaitUtils`/`ScreenshotUtils`.
11. **`mobile`** — depends on `driver.MobileDriverManager`, `config`, `constants`, `enums`,
    `exceptions`, `utils.WaitUtils`.
12. **`api`** — depends on `config`, `constants`, `context` (via `ApiContext`), `secrets`,
    `utils.JsonUtils`, `reporting.ApiReportRecorder`, `testdata.PlaceholderResolver`
    (`ApiContext` self-registers as a source).
13. **`reporting`** — build early enough that `testdata`/`utils.Verify`/`api` can call into it;
    in practice build `ExtentManager`/`AllureManager`/`ReportManager` right after `config`, then
    circle back for `ApiReportRecorder`/`ApiReportModel`/`ApiHtmlReportRenderer` once `api`
    exists.
14. **`listeners`** — depends on everything above; build last, one at a time, and only after
    reading [§18](#18-package-listeners)'s ordering notes — several were only discovered
    correct by empirically probing TestNG's actual invocation order, not by reading its docs.
15. **`META-INF/services/org.testng.ITestNGListener`** — register listeners in the exact
    order given in [§2](#2-project-structure); this is not cosmetic.
16. **Test-code layer** (`src/test`) — base classes, then Page Objects/Components, then
    Services/DTOs, then test-data files, then `@Test` classes last.

## 5. Package: exceptions

Root of the framework's unchecked exception hierarchy — every framework-specific failure
extends this instead of throwing a raw `RuntimeException` or letting a library exception leak
unwrapped, so callers can catch one type and messages consistently identify what failed and
why.

```java
package com.framework.exceptions;

public class FrameworkException extends RuntimeException {
    public FrameworkException(String message) { super(message); }
    public FrameworkException(String message, Throwable cause) { super(message, cause); }
}
```

Subtypes (all follow the identical two-constructor shape above):

| Class | Thrown when |
|---|---|
| `ConfigurationException` | Unsupported `env`, missing `config/{env}.properties`, missing/blank required key. Always eager, never mid-test. |
| `SecretResolutionException` | A required secret is missing from both CI env and `.secret.env`, or a `${{KEY}}` placeholder can't be resolved by any source. |
| `DriverInitializationException` | A WebDriver/AppiumDriver can't be created: invalid resolution string, incomplete mobile app identification, or a Selenium/Appium session-creation failure. |
| `ElementInteractionException` | A Web/Mobile element interaction fails: explicit wait times out, or the driver throws while clicking/typing/reading. Wraps the original Selenium/Appium exception as cause. |
| `ApiException` | An API call fails in a way the test shouldn't have to interpret raw REST Assured/HTTP exceptions for: connection failure, unexpected status where validation was requested, unparseable body. |
| `ApiAuthenticationException extends ApiException` | Authentication itself fails: login/register rejected, or a protected endpoint called with no token available. Kept distinct from `ApiException` so a test can tell "the call worked but returned something unexpected" apart from "we were never authenticated". |
| `TestDataException` | Unsupported file extension, missing classpath resource, malformed JSON/YAML/CSV/Excel, or a requested record name/index that doesn't exist. |

```java
package com.framework.exceptions;
public class ConfigurationException extends FrameworkException {
    public ConfigurationException(String message) { super(message); }
    public ConfigurationException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class SecretResolutionException extends FrameworkException {
    public SecretResolutionException(String message) { super(message); }
    public SecretResolutionException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class DriverInitializationException extends FrameworkException {
    public DriverInitializationException(String message) { super(message); }
    public DriverInitializationException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class ElementInteractionException extends FrameworkException {
    public ElementInteractionException(String message) { super(message); }
    public ElementInteractionException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class ApiException extends FrameworkException {
    public ApiException(String message) { super(message); }
    public ApiException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class ApiAuthenticationException extends ApiException {
    public ApiAuthenticationException(String message) { super(message); }
    public ApiAuthenticationException(String message, Throwable cause) { super(message, cause); }
}
```
```java
package com.framework.exceptions;
public class TestDataException extends FrameworkException {
    public TestDataException(String message) { super(message); }
    public TestDataException(String message, Throwable cause) { super(message, cause); }
}
```

---

## 6. Package: enums

Every enum here follows the same pattern: fixed set of constants, `fromString(rawValue)` that
does a case-insensitive, fail-fast lookup via a shared `EnumUtils.fromString` helper (see
[§11](#11-package-utils)).

```java
package com.framework.enums;
import com.framework.utils.EnumUtils;

public enum BrowserType {
    CHROME, FIREFOX, EDGE, SAFARI;
    public static BrowserType fromString(String rawValue) {
        return EnumUtils.fromString(BrowserType.class, rawValue, "-Dbrowser");
    }
}
```

```java
package com.framework.enums;
import com.framework.utils.EnumUtils;

/** Only QA/DEV today - add a constant here plus its matching config/{env}.properties to add another. */
public enum Environment {
    QA, DEV;
    public static Environment fromString(String rawValue) {
        return EnumUtils.fromString(Environment.class, rawValue, "-Denv");
    }
}
```

```java
package com.framework.enums;
import com.framework.utils.EnumUtils;

public enum MobilePlatformType {
    ANDROID, IOS;
    public static MobilePlatformType fromString(String rawValue) {
        return EnumUtils.fromString(MobilePlatformType.class, rawValue, "-Dmobile.platform");
    }
}
```

```java
package com.framework.enums;
import com.framework.utils.EnumUtils;

/**
 * Where a mobile test's device comes from. LOCAL (default) covers an emulator/simulator AND a
 * physical device on this machine alike - both talk to the same local Appium server, differing
 * only in mobile.udid - so there is no separate enum value for "physical device".
 */
public enum MobileDeviceProvider {
    LOCAL, BROWSERSTACK;
    public static MobileDeviceProvider fromString(String rawValue) {
        return EnumUtils.fromString(MobileDeviceProvider.class, rawValue, "-Dmobile.device.provider");
    }
}
```

```java
package com.framework.enums;
import com.framework.utils.EnumUtils;

public enum ScreenshotMode {
    FAILURE, EVERY_ACTION, DISABLED;
    public static ScreenshotMode fromString(String rawValue) {
        return EnumUtils.fromString(ScreenshotMode.class, rawValue, "-Dscreenshot.mode");
    }
}
```

---

## 7. Package: constants

`ConfigKeys` — canonical configuration key name constants, shared by
`config/*.properties`, `-Dkey=value` overrides, and TestNG `<parameter>` tags. Using
constants instead of repeating string literals prevents a key-name typo from silently
creating a new, unresolved config entry.

```java
package com.framework.constants;

public final class ConfigKeys {
    private ConfigKeys() {}

    public static final String ENV = "env";
    public static final String BROWSER = "browser";
    public static final String HEADLESS = "headless";
    public static final String RESOLUTION = "resolution";
    public static final String BASE_URL = "base.url";
    public static final String API_BASE_URL = "api.base.url";

    // Web driver timeouts
    public static final String PAGE_LOAD_TIMEOUT = "page.load.timeout";
    public static final String SCRIPT_TIMEOUT = "script.timeout";
    public static final String IMPLICIT_WAIT_TIMEOUT = "implicit.wait.timeout";
    public static final String EXPLICIT_WAIT_TIMEOUT = "explicit.wait.timeout";
    public static final String POLLING_INTERVAL = "polling.interval";

    // Screenshots
    public static final String SCREENSHOT_MODE = "screenshot.mode";

    // API timeouts (ms)
    public static final String API_CONNECTION_TIMEOUT = "api.connection.timeout";
    public static final String API_SOCKET_TIMEOUT = "api.socket.timeout";

    // Retry
    public static final String RETRY_MAX_COUNT = "retry.max.count";

    // Reporting - whether each run overwrites the previous report or writes its own
    // timestamped file (shared by Extent and the separate API report)
    public static final String REPORT_OVERWRITE = "report.overwrite";
    public static final String REPORT_API_TITLE = "report.api.title";
    public static final String REPORT_API_NAME = "report.api.name";
    // Comma-separated subset of "extent"/"allure" - which report(s) this framework's own code
    // enriches beyond allure-testng's native pass/fail capture (default "extent")
    public static final String REPORT_TYPES = "report.types";

    // Test data - the default source format for an extension-less load() call: json|yaml|csv|excel
    public static final String TEST_DATA_FORMAT = "testdata.format";

    // Mobile / Appium - device details themselves come from config/mobile-devices.json, not here
    public static final String MOBILE_PLATFORM = "mobile.platform";
    public static final String MOBILE_DEVICE_NAME = "mobile.device.name";
    public static final String MOBILE_PLATFORM_VERSION = "mobile.platform.version";
    public static final String MOBILE_AUTOMATION_NAME = "mobile.automation.name";
    public static final String MOBILE_APP_PATH = "mobile.app.path";
    public static final String MOBILE_APP_PATH_ANDROID = "mobile.app.path.android";
    public static final String MOBILE_APP_PATH_IOS = "mobile.app.path.ios";
    public static final String MOBILE_UDID = "mobile.udid";
    public static final String MOBILE_APP_PACKAGE = "mobile.app.package";
    public static final String MOBILE_APP_ACTIVITY = "mobile.app.activity";
    public static final String MOBILE_APP_WAIT_ACTIVITY = "mobile.app.wait.activity";
    public static final String MOBILE_BUNDLE_ID = "mobile.bundle.id";
    public static final String APPIUM_SERVER_URL = "appium.server.url";
    public static final String APPIUM_COMMAND_TIMEOUT = "appium.command.timeout";
    // true only for a device whose mobile-devices.json entry sets "hybrid": true (Android only)
    public static final String MOBILE_HYBRID = "mobile.hybrid";

    // Mobile device provider - cloud device farm extensibility
    public static final String MOBILE_DEVICE_PROVIDER = "mobile.device.provider";
    public static final String BROWSERSTACK_SERVER_URL = "browserstack.server.url";
    public static final String BROWSERSTACK_APP_ID = "browserstack.app.id";
    public static final String BROWSERSTACK_PROJECT_NAME = "browserstack.project.name";
    public static final String BROWSERSTACK_BUILD_NAME = "browserstack.build.name";
}
```

## 8. Package: config — ConfigManager

Central configuration access point for the entire framework. Every value resolves through a
fixed **4-tier precedence chain** (highest wins):

```
Test-specific override (ConfigManager.setOverride)  >  TestNG <parameter>  >
System property (-Dkey=value)  >  config/{env}.properties
```

No separate `default.properties` layer — each `config/{env}.properties` file is fully
self-contained (one file to read, no hidden merge). `qa` is the default environment when
`-Denv` is omitted.

**Thread-safety:** the merged environment/system-property snapshot is computed once
(double-checked lazy init) and then treated as **immutable and globally shareable**. The
TestNG-parameter and test-override layers are **thread-local** so parallel tests never see
each other's values.

**Fail-fast:** an unsupported `env`, a missing `config/{env}.properties`, or a missing/blank
required key (`browser`, `base.url`, `api.base.url`) throws `ConfigurationException`
immediately on first access — never mid-test.

```java
package com.framework.config;

import com.framework.constants.ConfigKeys;
import com.framework.enums.Environment;
import com.framework.exceptions.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    private static final String CONFIG_RESOURCE_PATH = "config/";
    private static final String DEFAULT_ENVIRONMENT = "qa";
    private static final List<String> REQUIRED_KEYS =
            List.of(ConfigKeys.BROWSER, ConfigKeys.BASE_URL, ConfigKeys.API_BASE_URL);

    private static volatile Map<String, String> globalConfig;
    private static final Object INIT_LOCK = new Object();

    private static final ThreadLocal<Map<String, String>> TESTNG_PARAMETERS = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<Map<String, String>> TEST_OVERRIDES = ThreadLocal.withInitial(HashMap::new);

    private ConfigManager() {}

    // ---- Public read API ----

    public static String getString(String key) {
        String value = resolve(key);
        if (value == null) {
            throw new ConfigurationException(
                    "Missing required configuration key '" + key + "'. Checked, highest precedence first: "
                            + "test-override, TestNG <parameter>, system property -D" + key + ", "
                            + getEnvironment().name().toLowerCase() + ".properties.");
        }
        return value;
    }

    public static String getString(String key, String defaultValue) {
        String value = resolve(key);
        return value != null ? value : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = resolve(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ConfigurationException(
                    "Configuration key '" + key + "' must be an integer but was '" + value + "'.", e);
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = resolve(key);
        return value != null ? Boolean.parseBoolean(value.trim()) : defaultValue;
    }

    public static Environment getEnvironment() {
        return Environment.fromString(resolve(ConfigKeys.ENV));
    }

    public static String getBrowser() { return getString(ConfigKeys.BROWSER); }
    public static boolean isHeadless() { return getBoolean(ConfigKeys.HEADLESS, false); }
    public static String getResolution() { return getString(ConfigKeys.RESOLUTION, "1920x1080"); }
    public static String getBaseUrl() { return getString(ConfigKeys.BASE_URL); }
    public static String getApiBaseUrl() { return getString(ConfigKeys.API_BASE_URL); }

    /** true (default) -> every run replaces reports/extent/index.html and reports/api/index.html; false -> timestamped file per run. */
    public static boolean isReportOverwriteEnabled() { return getBoolean(ConfigKeys.REPORT_OVERWRITE, true); }
    public static String getApiReportTitle() { return getString(ConfigKeys.REPORT_API_TITLE, "EventHub API Automation Report"); }
    public static String getApiReportName() { return getString(ConfigKeys.REPORT_API_NAME, "EventHub API Automation Framework"); }

    // ---- Thread-scoped write API (tiers 4 and 5) ----

    /** Populates tier 4 (TestNG <parameter> values) for the calling thread. */
    public static void setTestNgParameters(Map<String, String> parameters) {
        TESTNG_PARAMETERS.set(new HashMap<>(parameters));
    }

    /** Sets a tier-5, highest-precedence override visible only to the calling thread. */
    public static void setOverride(String key, String value) {
        TEST_OVERRIDES.get().put(key, value);
    }

    /** Removes all thread-local configuration state (tiers 4 and 5). Must run on test/thread completion. */
    public static void clearThreadState() {
        TESTNG_PARAMETERS.remove();
        TEST_OVERRIDES.remove();
    }

    // ---- Resolution ----

    private static String resolve(String key) {
        String override = TEST_OVERRIDES.get().get(key);
        if (override != null) return override;
        String parameter = TESTNG_PARAMETERS.get().get(key);
        if (parameter != null) return parameter;
        String global = globalConfig().get(key);
        if (global != null) return global;
        // Ad-hoc system property not predeclared in any properties file, e.g. a one-off -Dkey=value.
        return System.getProperty(key);
    }

    private static Map<String, String> globalConfig() {
        Map<String, String> result = globalConfig;
        if (result == null) {
            synchronized (INIT_LOCK) {
                result = globalConfig;
                if (result == null) {
                    result = loadAndValidate();
                    globalConfig = result;
                }
            }
        }
        return result;
    }

    private static Map<String, String> loadAndValidate() {
        String rawEnv = System.getProperty(ConfigKeys.ENV, DEFAULT_ENVIRONMENT);
        Environment environment = Environment.fromString(rawEnv);
        String envFile = environment.name().toLowerCase() + ".properties";

        Map<String, String> merged = new HashMap<>(loadProperties(envFile, true));
        merged.put(ConfigKeys.ENV, environment.name().toLowerCase());

        // Tier 3: a system property overrides any key already known from the file above.
        for (String key : new HashMap<>(merged).keySet()) {
            String systemValue = System.getProperty(key);
            if (systemValue != null) merged.put(key, systemValue);
        }

        validateRequiredKeys(merged, environment, envFile);
        LOGGER.info("Configuration loaded for environment '{}' from '{}' (system-property overrides applied).",
                environment, envFile);
        return Collections.unmodifiableMap(merged);
    }

    private static Map<String, String> loadProperties(String fileName, boolean required) {
        String resource = CONFIG_RESOURCE_PATH + fileName;
        Properties properties = new Properties();
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                if (required) {
                    throw new ConfigurationException(
                            "Required configuration file '" + resource + "' was not found on the classpath. "
                                    + "Expected at " + resource + " (repo root, not under src/main/resources).");
                }
                return Map.of();
            }
            properties.load(in);
        } catch (IOException e) {
            throw new ConfigurationException("Failed to read configuration file '" + resource + "'.", e);
        }

        Map<String, String> result = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            result.put(name, properties.getProperty(name));
        }
        return result;
    }

    private static void validateRequiredKeys(Map<String, String> merged, Environment environment, String envFile) {
        List<String> missing = REQUIRED_KEYS.stream()
                .filter(key -> merged.get(key) == null || merged.get(key).isBlank())
                .toList();
        if (!missing.isEmpty()) {
            throw new ConfigurationException(
                    "Configuration validation failed for environment '" + environment + "'. "
                            + "Missing/blank required key(s): " + missing + ". Define them in config/" + envFile + ".");
        }
    }
}
```

**Why this shape:** a naive singleton config map fails the moment two tests running in
parallel need different `browser`/`mobile.platform` values — the thread-local override/
parameter tiers are what let `MultiDeviceParallelTest` run several devices concurrently
without one thread's device selection leaking into another's.

---

## 9. Package: secrets

Three classes: `SecretManager` (resolution), `SensitiveDataMasker` (redaction), and
`MaskingMessageConverter` (the Logback wiring that makes masking systemic).

### SecretManager

2-tier precedence (highest wins): a **CI/CD environment variable** (`System.getenv(key)`),
then a local `.secret.env` file (git-ignored, developer machines only). Every value this
class returns is registered with `SensitiveDataMasker` automatically, so it's redacted
everywhere the framework logs/reports text from then on.

```java
package com.framework.secrets;

import com.framework.exceptions.SecretResolutionException;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecretManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretManager.class);
    private static volatile Dotenv dotenv;
    private static final Object INIT_LOCK = new Object();

    private SecretManager() {}

    public static String get(String key) {
        String value = resolve(key);
        if (value == null) {
            throw new SecretResolutionException(
                    "Missing required secret '" + key + "'. Checked a CI/CD environment variable named '"
                            + key + "' and the '" + key + "' entry in .secret.env. Set the environment "
                            + "variable in CI, or add it to .secret.env locally.");
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = resolve(key);
        return value != null ? value : defaultValue;
    }

    public static boolean has(String key) { return resolve(key) != null; }

    private static String resolve(String key) {
        String ciValue = System.getenv(key);
        String value = ciValue != null ? ciValue : dotenv().get(key);
        if (value != null) SensitiveDataMasker.registerSecretValue(value);
        return value;
    }

    private static Dotenv dotenv() {
        Dotenv result = dotenv;
        if (result == null) {
            synchronized (INIT_LOCK) {
                result = dotenv;
                if (result == null) { result = loadDotenv(); dotenv = result; }
            }
        }
        return result;
    }

    private static Dotenv loadDotenv() {
        try {
            Dotenv loaded = Dotenv.configure().directory(".").filename(".secret.env").ignoreIfMissing().load();
            if (loaded.entries().isEmpty()) {
                LOGGER.info(".secret.env not found or empty; relying on CI/CD environment variables only.");
            } else {
                LOGGER.info(".secret.env loaded for local secret resolution (CI/CD env vars still take precedence).");
            }
            return loaded;
        } catch (DotenvException e) {
            throw new SecretResolutionException("Failed to parse .secret.env.", e);
        }
    }
}
```

### SensitiveDataMasker

Masks sensitive values out of arbitrary text before it reaches a log line or report. Two
independent strategies run on every call, in order:

1. **Known-value masking** — any literal value ever registered via `registerSecretValue`
   is replaced wherever it appears verbatim, regardless of context.
2. **Key-pattern masking** — text shaped like `password=...`, `"client_secret": "..."`, or
   `Authorization: Bearer ...` is masked by key name even when the value was never
   explicitly registered.

Masked output is `********-xxxxxxxx`, not a flat `********` — the suffix is a short
deterministic SHA-256 fingerprint (first 4 bytes) of the real value: same secret → same
suffix, different secret → different suffix, so a report reader can tell "was the same
credential used three steps ago" without the real value ever appearing.

**Off by default, opt in explicitly**: `-Dmasking.enabled=true` or `MASKING_ENABLED=true`
(the `-D` flag wins if both set). **Hard-forced on in CI** regardless of that flag, whenever
`CI`/`GITHUB_ACTIONS` env vars are present.

```java
package com.framework.secrets;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SensitiveDataMasker {

    public static final String MASK = "********";

    private static final String MASKING_ENABLED_PROPERTY = "masking.enabled";
    private static final String MASKING_ENABLED_ENV_VAR = "MASKING_ENABLED";

    private static final String KEY_NAME_ALTERNATION = String.join("|",
            "password", "pwd", "secret", "token", "authorization", "auth",
            "api[_-]?key", "client[_-]?secret", "access[_-]?token",
            "refresh[_-]?token", "session[_-]?id");

    private static final Pattern QUOTED_JSON_STYLE = Pattern.compile(
            "(?i)(\"(?:" + KEY_NAME_ALTERNATION + ")\"\\s*:\\s*\")([^\"]*)(\")");

    // Stops a captured value at a comma/semicolon/&/newline, not whitespace, so multi-word
    // values like "Authorization: Bearer <token>" are masked in full. Deliberate: over-masking
    // is safe, leaking part of a secret is not.
    private static final Pattern UNQUOTED_KEY_VALUE_STYLE = Pattern.compile(
            "(?i)\\b(?:" + KEY_NAME_ALTERNATION + ")\\b\\s*[:=]\\s*(?!\")(\\S.*?)(?=[,;&\\n]|$)");

    private static final Set<String> KNOWN_SECRET_VALUES = ConcurrentHashMap.newKeySet();
    private static final AtomicBoolean SUPPRESSION_WARNED = new AtomicBoolean(false);

    private SensitiveDataMasker() {}

    /** Registers a literal secret value to always mask. Values of length <= 3 are ignored (avoids mass false-positive masking on short strings). */
    public static void registerSecretValue(String value) {
        if (value != null && value.length() > 3) KNOWN_SECRET_VALUES.add(value);
    }

    public static String mask(String input) {
        if (input == null || input.isEmpty()) return input;
        if (!isMaskingEnabled()) return input;
        String result = input;
        for (String secretValue : KNOWN_SECRET_VALUES) {
            result = result.replace(secretValue, maskToken(secretValue));
        }
        result = maskQuotedJsonStyle(result);
        result = maskUnquotedKeyValueStyle(result);
        return result;
    }

    private static String maskQuotedJsonStyle(String input) {
        Matcher matcher = QUOTED_JSON_STYLE.matcher(input);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb,
                    Matcher.quoteReplacement(matcher.group(1) + maskToken(matcher.group(2)) + matcher.group(3)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String maskUnquotedKeyValueStyle(String input) {
        Matcher matcher = UNQUOTED_KEY_VALUE_STYLE.matcher(input);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String whole = matcher.group();
            String value = matcher.group(1);
            String prefix = whole.substring(0, whole.length() - value.length());
            matcher.appendReplacement(sb, Matcher.quoteReplacement(prefix + maskToken(value)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String maskToken(String rawValue) { return MASK + "-" + fingerprint(rawValue); }

    /** First 4 bytes (8 hex chars) of value's SHA-256 digest. A correlation aid, not a security boundary. */
    private static String fingerprint(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash, 0, 4);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 MessageDigest unavailable - not a conforming JVM.", e);
        }
    }

    private static boolean isMaskingEnabled() {
        if (System.getenv("CI") != null || System.getenv("GITHUB_ACTIONS") != null) return true;
        String flag = System.getProperty(MASKING_ENABLED_PROPERTY, System.getenv(MASKING_ENABLED_ENV_VAR));
        boolean enabled = "true".equalsIgnoreCase(flag);
        if (!enabled) noteMaskingStatusOnce();
        return enabled;
    }

    /** Printed directly to stderr, not via SLF4J/Logback - this can fire before Logback's own config state is safe to assume anything about. */
    private static void noteMaskingStatusOnce() {
        if (SUPPRESSION_WARNED.compareAndSet(false, true)) {
            System.err.println("[SensitiveDataMasker] Masking is OFF (default): real secret values will appear "
                    + "in logs/reports for this run. Turn it on with -D" + MASKING_ENABLED_PROPERTY
                    + "=true or " + MASKING_ENABLED_ENV_VAR + "=true before sharing a report - "
                    + "CI always masks regardless of this flag.");
        }
    }
}
```

### MaskingMessageConverter

A Logback `ClassicConverter` registered as the `%maskedMsg` conversion word (replacing the
standard `%msg`) in `logback.xml` — masks every CONSOLE/FILE line unconditionally, so masking
no longer depends on a call site remembering to call `.mask()` itself.

```java
package com.framework.secrets;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MaskingMessageConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        return SensitiveDataMasker.mask(event.getFormattedMessage());
    }
}
```

Only protects appenders using a `PatternLayout`/encoder referencing `%maskedMsg` (CONSOLE,
FILE). `ExtentLoggingAppender` doesn't use a pattern at all — it calls
`SensitiveDataMasker.mask(...)` itself directly (see [§17](#17-package-reporting)).

---

## 10. Package: context — VariableManager

Low-level, thread-safe runtime variable store backing all API/data chaining.
`com.framework.api.ApiContext` is the API-facing surface tests actually call; this class is
the shared mechanism underneath it, kept general enough (`com.framework.context`, not
`com.framework.api`) that a future Web/Mobile or cross-layer chaining need reuses it instead
of a second parallel implementation.

**Thread-safety:** backed by a `ThreadLocal` map — each thread gets its own independent
variable set; nothing here is shared or synchronized because nothing is ever visible across
threads by design.

```java
package com.framework.context;

import com.framework.exceptions.FrameworkException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class VariableManager {

    private static final ThreadLocal<Map<String, String>> VARIABLES = ThreadLocal.withInitial(HashMap::new);

    private VariableManager() {}

    /** Stores value under key for the calling thread, overwriting any existing value. */
    public static void set(String key, String value) { VARIABLES.get().put(key, value); }

    /** Returns the value stored for key on the calling thread, or throws if none was ever set. */
    public static String get(String key) {
        String value = VARIABLES.get().get(key);
        if (value == null) {
            throw new FrameworkException(
                    "No runtime variable named '" + key + "' has been set on this thread. "
                            + "It must be produced by an earlier call (e.g. via ApiContext.set(\"" + key
                            + "\", ...)) before it can be read or referenced as ${{" + key + "}}.");
        }
        return value;
    }

    public static Optional<String> getOptional(String key) { return Optional.ofNullable(VARIABLES.get().get(key)); }
    public static boolean contains(String key) { return VARIABLES.get().containsKey(key); }
    public static void remove(String key) { VARIABLES.get().remove(key); }

    /** Removes every variable for the calling thread and detaches the ThreadLocal entry itself. Must run on test/thread completion. */
    public static void clear() { VARIABLES.remove(); }
}
```

## 11. Package: utils

### JsonUtils — shared JSON (de)serialization

One `ObjectMapper`, one configuration, instead of every caller building its own.
`FAIL_ON_UNKNOWN_PROPERTIES` is disabled: real APIs return fields beyond what their published
schema documents, and a test framework should tolerate additive API changes.

```java
package com.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.exceptions.ApiException;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtils() {}

    public static <T> T fromJson(String json, Class<T> type) {
        try { return OBJECT_MAPPER.readValue(json, type); }
        catch (Exception e) { throw new ApiException("Failed to deserialize JSON into " + type.getSimpleName() + ": " + e.getMessage(), e); }
    }

    /** For generic types erasure defeats fromJson(String, Class) on, e.g. PaginatedResponse<EventResponse>. */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try { return OBJECT_MAPPER.readValue(json, typeReference); }
        catch (Exception e) { throw new ApiException("Failed to deserialize JSON into " + typeReference.getType() + ": " + e.getMessage(), e); }
    }

    /**
     * Navigates a dot-separated path (e.g. "data", "data.event") into a JSON document and
     * deserializes the node found there - for APIs that wrap a resource in an envelope such as
     * {success, data, message}, where the domain object isn't at the document root.
     */
    public static <T> T fromJsonAtPath(String json, String dotSeparatedPath, Class<T> type) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            for (String segment : dotSeparatedPath.split("\\.")) node = node.path(segment);
            if (node.isMissingNode()) throw new ApiException("No node found at path '" + dotSeparatedPath + "' in: " + json);
            return OBJECT_MAPPER.treeToValue(node, type);
        } catch (ApiException e) { throw e; }
        catch (Exception e) { throw new ApiException("Failed to deserialize node at path '" + dotSeparatedPath + "' into " + type.getSimpleName() + ": " + e.getMessage(), e); }
    }

    public static String toJson(Object value) {
        try { return OBJECT_MAPPER.writeValueAsString(value); }
        catch (Exception e) { throw new ApiException("Failed to serialize " + value.getClass().getSimpleName() + " to JSON: " + e.getMessage(), e); }
    }

    /** Re-serializes compact JSON with indentation for a log/report line that must stay human-readable. Falls back to rawJson unchanged if not valid JSON. */
    public static String prettyPrintJson(String rawJson) {
        if (rawJson == null || rawJson.isBlank()) return rawJson;
        try {
            Object tree = OBJECT_MAPPER.readTree(rawJson);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
        } catch (Exception e) { return rawJson; }
    }

    public static ObjectMapper objectMapper() { return OBJECT_MAPPER; }
}
```

### FileUtils — classpath resource loading

```java
package com.framework.utils;

import com.framework.exceptions.TestDataException;
import java.io.InputStream;

public final class FileUtils {
    private FileUtils() {}

    /** Opens classpathResource for reading. Caller closes it (try-with-resources). */
    public static InputStream openClasspathResource(String classpathResource) {
        InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(classpathResource);
        if (in == null) {
            throw new TestDataException(
                    "Test data file not found on the classpath: '" + classpathResource
                            + "'. Expected at src/test/resources/" + classpathResource + " (or src/main/resources).");
        }
        return in;
    }
}
```

### ScreenshotUtils — best-effort capture for any driver

Both `WebDriver` and `AppiumDriver` implement `TakesScreenshot`, so one utility serves both.
Best-effort: a capture failure logs a warning and returns `null` rather than throwing (a
screenshot is diagnostic, not the thing under test).

```java
package com.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private ScreenshotUtils() {}

    public static Path capture(WebDriver driver, String label) {
        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            LOGGER.warn("Driver does not support screenshots; skipping capture for '{}'.", label);
            return null;
        }
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String fileName = sanitize(label) + "-" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path destination = SCREENSHOT_DIR.resolve(fileName);
            Files.copy(source.toPath(), destination);
            LOGGER.info("Screenshot captured: {}", destination);
            return destination;
        } catch (IOException e) {
            LOGGER.warn("Failed to capture screenshot for '{}': {}", label, e.getMessage());
            return null;
        }
    }

    private static String sanitize(String label) { return label.replaceAll("[^a-zA-Z0-9_-]", "_"); }
}
```

### DateUtils — dates that stay valid relative to "now"

```java
package com.framework.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** Never hardcode a future date literal in test data - it becomes a silent time bomb the moment it's no longer in the future. */
public final class DateUtils {

    private static final DateTimeFormatter EVENTHUB_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);

    private DateUtils() {}

    /** An ISO-8601 UTC instant daysFromNow days from now. */
    public static String futureIsoDate(int daysFromNow) {
        return EVENTHUB_DATE_FORMAT.format(Instant.now().plus(daysFromNow, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
    }
}
```

### RandomDataUtils

```java
package com.framework.utils;

import java.util.UUID;

public final class RandomDataUtils {
    private RandomDataUtils() {}

    /** A unique email, e.g. uniqueEmail("framework.test") -> "framework.test.<uuid>@example.com". */
    public static String uniqueEmail(String prefix) { return prefix + "." + UUID.randomUUID() + "@example.com"; }

    public static String uniqueId() { return UUID.randomUUID().toString(); }
}
```

### EnumUtils — shared case-insensitive, fail-fast enum lookup

```java
package com.framework.utils;

import com.framework.exceptions.ConfigurationException;
import java.util.Arrays;
import java.util.Locale;

public final class EnumUtils {
    private EnumUtils() {}

    public static <E extends Enum<E>> E fromString(Class<E> enumType, String rawValue, String cliFlag) {
        E[] constants = enumType.getEnumConstants();
        String supported = joinLowercase(constants);
        if (rawValue == null || rawValue.isBlank()) {
            throw new ConfigurationException("No " + cliFlag + " value specified. Set one via " + cliFlag + "=<" + supported + ">.");
        }
        String normalized = rawValue.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(constants)
                .filter(constant -> constant.name().equals(normalized))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException(
                        "Unsupported " + cliFlag + " value '" + rawValue + "'. Supported values: " + supported + "."));
    }

    private static <E extends Enum<E>> String joinLowercase(E[] constants) {
        StringBuilder builder = new StringBuilder();
        for (E constant : constants) {
            if (builder.length() > 0) builder.append('|');
            builder.append(constant.name().toLowerCase(Locale.ROOT));
        }
        return builder.toString();
    }
}
```

### TextUtils — humanize a method name for report titles

```java
package com.framework.utils;

public final class TextUtils {
    private TextUtils() {}

    /** e.g. "bookingWithoutAuthReturns401" -> "Booking Without Auth Returns 401". Zero test-author effort. */
    public static String humanize(String identifier) {
        String spaced = identifier
                .replaceAll("([a-z])([A-Z])", "$1 $2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2")
                .replaceAll("([a-zA-Z])([0-9])", "$1 $2")
                .replaceAll("([0-9])([a-zA-Z])", "$1 $2");
        return spaced.isEmpty() ? spaced : Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }
}
```

### WaitUtils — the one centralized wait-configuration builder

Generic over `SearchContext` so both `com.framework.web` (`WebDriver`/`WebElement`-scoped
waits) and `com.framework.mobile` (`AppiumDriver`-scoped waits) share one implementation.

```java
package com.framework.utils;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {}

    public static <T extends SearchContext> Wait<T> buildFluentWait(T searchContext) {
        Duration timeout = Duration.ofSeconds(ConfigManager.getInt(ConfigKeys.EXPLICIT_WAIT_TIMEOUT, 15));
        Duration polling = Duration.ofMillis(ConfigManager.getInt(ConfigKeys.POLLING_INTERVAL, 500));
        return new FluentWait<>(searchContext)
                .withTimeout(timeout)
                .pollingEvery(polling)
                .ignoring(NoSuchElementException.class);
    }
}
```

### Verify — a reporting drop-in for org.testng.Assert

The single most important utility for report readability: a bare `org.testng.Assert` call is
invisible in either report while it passes — nothing records that a check even happened.
`Verify` mirrors the same method names/signatures (drop-in, just a different static import),
delegates the actual comparison to `org.testng.Assert` unconditionally (so pass/fail
semantics never change), and logs one explicit PASS/FAIL step per assertion to whichever
report is active — Extent for Web/Mobile, or the API surface's own report when an API test is
currently running (`ApiReportRecorder.hasActiveTest()`).

Key design notes:
- Every message-less overload appends `" (line 47)"` (the caller's own stack frame) so two
  message-less assertions in one method still read as distinct report rows.
- `assertEquals`'s default message is **asymmetric on purpose**: a pass reads `"Values match:
  X"`, a fail reads `"Expected X but got Y"` — a passing row showing `"Expected quantity but
  got quantity"` misleadingly looks like a contradiction.
- Full `int`/`Integer`/`long` overload set exists because dropping to a single
  `(Object, Object)` signature breaks primitive-widening resolution TestNG's own
  `Assert.assertEquals` relies on (`long` vs `int` autoboxing to different wrapper types
  compares `false` even when the numbers match) — found live, not theoretical.

```java
package com.framework.utils;

import com.aventstack.extentreports.Status;
import com.framework.reporting.ApiReportRecorder;
import com.framework.reporting.ExtentManager;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

public final class Verify {

    private static final Logger LOGGER = LoggerFactory.getLogger(Verify.class);

    private Verify() {}

    public static void assertTrue(boolean condition) { assertTrue(condition, "Condition should be true" + callerLocation()); }
    public static void assertTrue(boolean condition, String message) { run(message, () -> Assert.assertTrue(condition, message)); }

    public static void assertFalse(boolean condition) { assertFalse(condition, "Condition should be false" + callerLocation()); }
    public static void assertFalse(boolean condition, String message) { run(message, () -> Assert.assertFalse(condition, message)); }

    public static void assertEquals(Object actual, Object expected) {
        assertEquals(actual, expected, equalsMessage(Objects.equals(actual, expected), actual, expected));
    }
    public static void assertEquals(Object actual, Object expected, String message) {
        run(message, String.valueOf(expected), String.valueOf(actual), () -> Assert.assertEquals(actual, expected, message));
    }

    // The int/Integer/long overload set below mirrors org.testng.Assert's own set exactly -
    // required for real call sites like assertEquals(names.stream().distinct().count(), names.size())
    // (long vs int) and assertEquals(intFromResponse, threadLocalInteger.get()) to resolve
    // unambiguously and compare by value, not by boxed-type identity.
    public static void assertEquals(long actual, long expected) { assertEquals(actual, expected, equalsMessage(actual == expected, actual, expected)); }
    public static void assertEquals(long actual, long expected, String message) {
        run(message, String.valueOf(expected), String.valueOf(actual), () -> Assert.assertEquals(actual, expected, message));
    }
    public static void assertEquals(int actual, int expected) { assertEquals(actual, expected, equalsMessage(actual == expected, actual, expected)); }
    public static void assertEquals(int actual, int expected, String message) {
        run(message, String.valueOf(expected), String.valueOf(actual), () -> Assert.assertEquals(actual, expected, message));
    }
    public static void assertEquals(Integer actual, int expected) { assertEquals(actual, expected, equalsMessage(actual != null && actual == expected, actual, expected)); }
    public static void assertEquals(Integer actual, int expected, String message) {
        run(message, String.valueOf(expected), String.valueOf(actual), () -> Assert.assertEquals(actual, expected, message));
    }
    public static void assertEquals(int actual, Integer expected) { assertEquals(actual, expected, equalsMessage(expected != null && actual == expected, actual, expected)); }
    public static void assertEquals(int actual, Integer expected, String message) {
        run(message, String.valueOf(expected), String.valueOf(actual), () -> Assert.assertEquals(actual, expected, message));
    }

    private static String equalsMessage(boolean equal, Object actual, Object expected) {
        return equal ? "Values match: " + actual + callerLocation() : "Expected " + expected + " but got " + actual + callerLocation();
    }

    public static void assertNotNull(Object object) { assertNotNull(object, "Object should not be null" + callerLocation()); }
    public static void assertNotNull(Object object, String message) {
        run(message, "not null", String.valueOf(object), () -> Assert.assertNotNull(object, message));
    }

    /** " (line 47)" - the nearest stack frame outside this class, i.e. the real test method's own call site. */
    private static String callerLocation() {
        for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
            String className = frame.getClassName();
            if (!className.equals(Thread.class.getName()) && !className.equals(Verify.class.getName())) {
                return " (line " + frame.getLineNumber() + ")";
            }
        }
        return "";
    }

    private static void run(String message, Runnable assertion) { run(message, null, null, assertion); }

    private static void run(String message, String expected, String actual, Runnable assertion) {
        try {
            assertion.run();
            LOGGER.info("Assertion passed: {}", message);
            if (ApiReportRecorder.hasActiveTest()) {
                ApiReportRecorder.logAssertion(message, true, expected, actual, null);
            } else {
                ExtentManager.logAssertion(Status.PASS, message);
            }
        } catch (AssertionError e) {
            LOGGER.error("Assertion failed: {}", message, e);
            if (ApiReportRecorder.hasActiveTest()) {
                ApiReportRecorder.logAssertion(message, false, expected, actual, e.getMessage());
            } else {
                ExtentManager.logAssertion(Status.FAIL, message);
                ExtentManager.logStackTrace(e);
            }
            throw e;
        }
    }
}
```

## 12. Package: testdata

**Goal:** a test should not care whether its data source is JSON/YAML/CSV/Excel. Every format
normalizes into the same shape (a list of `Map<String,Object>` records), cached once (raw,
placeholder-unresolved) per resource path, with `${{...}}` placeholders resolved **fresh on
every access** — so a runtime value produced mid-test (e.g. `${{eventId}}`) still resolves
correctly even though the file was cached before that value existed.

### PlaceholderSource

```java
package com.framework.testdata;
import java.util.Optional;

@FunctionalInterface
public interface PlaceholderSource {
    Optional<String> resolve(String key);
}
```

### PlaceholderResolver

Resolves `${{KEY}}` tokens against an ordered list of sources: secrets first, then config
(matched case-insensitively against both the literal key and its `UPPER_SNAKE_CASE ->
dotted.lowercase` form). A third source (`ApiContext`, for runtime chaining) registers itself
later with no change here. Fail-fast: an unresolvable token throws, never silently becomes an
empty string.

```java
package com.framework.testdata;

import com.framework.config.ConfigManager;
import com.framework.exceptions.SecretResolutionException;
import com.framework.secrets.SecretManager;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PlaceholderResolver {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{\\{\\s*([^}]+?)\\s*}}");

    private static final List<PlaceholderSource> SOURCES = new CopyOnWriteArrayList<>(List.of(
            key -> Optional.ofNullable(SecretManager.get(key, null)),
            key -> resolveFromConfig(key)
    ));

    private PlaceholderResolver() {}

    public static void registerSource(PlaceholderSource source) { SOURCES.add(source); }

    public static String resolve(String rawText) {
        if (rawText == null || rawText.isEmpty()) return rawText;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(rawText);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String key = matcher.group(1);
            matcher.appendReplacement(result, Matcher.quoteReplacement(resolveKey(key)));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static String resolveKey(String key) {
        for (PlaceholderSource source : SOURCES) {
            Optional<String> value = source.resolve(key);
            if (value.isPresent()) return value.get();
        }
        throw new SecretResolutionException(
                "Unresolved placeholder '${{" + key + "}}'. Checked secrets (.secret.env / CI environment "
                        + "variables), configuration (config/*.properties), and any registered runtime sources.");
    }

    private static Optional<String> resolveFromConfig(String key) {
        String direct = ConfigManager.getString(key, null);
        if (direct != null) return Optional.of(direct);
        return Optional.ofNullable(ConfigManager.getString(toPropertiesStyleKey(key), null));
    }

    private static String toPropertiesStyleKey(String placeholderKey) {
        return placeholderKey.toLowerCase(Locale.ROOT).replace('_', '.');
    }
}
```

### TestCaseMetadata / TestCaseRecord — the shared (metadata, data) shape

```java
package com.framework.testdata;

/** testCaseName is a readable business/scenario name, not a restatement of the Java method name. */
public record TestCaseMetadata(String testCaseId, String testCaseName) {}
```

```java
package com.framework.testdata;

/**
 * A Java record declared as
 *   record LoginTestCase(TestCaseMetadata metadata, LoginData data) implements TestCaseRecord<LoginData>
 * satisfies this for free - its own generated metadata()/data() accessors are exactly what's needed.
 */
public interface TestCaseRecord<D> {
    TestCaseMetadata metadata();
    D data();
}
```

### TestDataReader — the per-format parser contract

```java
package com.framework.testdata;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

interface TestDataReader {

    /** The record field TestDataManager/TestData treat as a record's lookup name. */
    String NAME_FIELD = "name";

    /** Parses classpathResource into raw records. Never returns null. */
    List<Map<String, Object>> read(String classpathResource);

    /**
     * Expands a flat row's dotted keys (e.g. "metadata.testCaseId") into nested maps, so a
     * CSV/Excel row can carry the same (metadata, data)-nested shape a JSON/YAML object
     * naturally does. Only splits on the FIRST dot - one level deep, as deep as every
     * *TestCase shape actually goes. A blank cell is dropped (means "unset"), not kept as "" -
     * a row-oriented format has no way to omit a column on just one row otherwise.
     */
    static Map<String, Object> unflatten(Map<String, String> flatRow) {
        Map<String, Object> nested = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : flatRow.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isBlank()) continue;
            int dot = entry.getKey().indexOf('.');
            if (dot < 0) { nested.put(entry.getKey(), entry.getValue()); continue; }
            String parent = entry.getKey().substring(0, dot);
            String child = entry.getKey().substring(dot + 1);
            @SuppressWarnings("unchecked")
            Map<String, Object> parentMap =
                    (Map<String, Object>) nested.computeIfAbsent(parent, key -> new LinkedHashMap<String, Object>());
            parentMap.put(child, entry.getValue());
        }
        return nested;
    }
}
```

### JacksonTreeDataReader — shared JSON/YAML tree-normalization logic

Accepts two root shapes: **array root** (each element is one record, unnamed unless it has
its own `name` field) or **object root** (each entry becomes one record, named after its key).

```java
package com.framework.testdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.exceptions.TestDataException;
import com.framework.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

abstract class JacksonTreeDataReader implements TestDataReader {

    private final ObjectMapper mapper;
    private final String formatName;

    protected JacksonTreeDataReader(ObjectMapper mapper, String formatName) {
        this.mapper = mapper;
        this.formatName = formatName;
    }

    @Override
    public final List<Map<String, Object>> read(String classpathResource) {
        JsonNode root;
        try (InputStream in = FileUtils.openClasspathResource(classpathResource)) {
            root = mapper.readTree(in);
        } catch (IOException e) {
            throw new TestDataException("Failed to read/parse " + formatName + " test data file '" + classpathResource + "'.", e);
        }

        if (root.isArray()) {
            List<Map<String, Object>> records = new ArrayList<>();
            for (JsonNode element : root) records.add(toRecord(element, classpathResource));
            return records;
        }
        if (root.isObject()) {
            List<Map<String, Object>> records = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                Map<String, Object> record = toRecord(field.getValue(), classpathResource);
                record.putIfAbsent(NAME_FIELD, field.getKey());
                records.add(record);
            }
            return records;
        }
        throw new TestDataException(formatName + " test data file '" + classpathResource + "' must have an object or array at its root, but found: " + root.getNodeType());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> toRecord(JsonNode node, String classpathResource) {
        if (!node.isObject()) throw new TestDataException("Each record in '" + classpathResource + "' must be an object, but found: " + node.getNodeType());
        return mapper.convertValue(node, LinkedHashMap.class);
    }
}
```

### JsonDataReader / YamlDataReader

```java
package com.framework.testdata;
import com.framework.utils.JsonUtils;

/** Reuses JsonUtils.objectMapper() rather than a second ObjectMapper instance. */
final class JsonDataReader extends JacksonTreeDataReader {
    JsonDataReader() { super(JsonUtils.objectMapper(), "JSON"); }
}
```

```java
package com.framework.testdata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/** YAML is structurally a superset of JSON - parses into the same Jackson tree model, only the factory differs. */
final class YamlDataReader extends JacksonTreeDataReader {
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    YamlDataReader() { super(YAML_MAPPER, "YAML"); }
}
```

### CsvDataReader

Row-oriented: every row is one record, keyed by its header cell (`CSVReaderHeaderAware` does
the header-to-map binding). All values come back as `String` — CSV doesn't infer types.

```java
package com.framework.testdata;

import com.framework.exceptions.TestDataException;
import com.framework.utils.FileUtils;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class CsvDataReader implements TestDataReader {
    @Override
    public List<Map<String, Object>> read(String classpathResource) {
        List<Map<String, Object>> records = new ArrayList<>();
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(
                new InputStreamReader(FileUtils.openClasspathResource(classpathResource), StandardCharsets.UTF_8))) {
            Map<String, String> row;
            while ((row = reader.readMap()) != null) records.add(TestDataReader.unflatten(row));
        } catch (IOException | CsvValidationException e) {
            throw new TestDataException("Failed to read/parse CSV test data file '" + classpathResource + "'.", e);
        }
        return records;
    }
}
```

### ExcelDataReader

Row-oriented like CSV — first sheet's row 0 as headers, every subsequent non-blank row as one
record. `DataFormatter` renders every cell as Excel itself would display it (a numeric `50`
becomes `"50"`, not `"50.0"`).

```java
package com.framework.testdata;

import com.framework.exceptions.TestDataException;
import com.framework.utils.FileUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class ExcelDataReader implements TestDataReader {
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    @Override
    public List<Map<String, Object>> read(String classpathResource) {
        try (InputStream in = FileUtils.openClasspathResource(classpathResource);
             Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            if (headerRow == null) throw new TestDataException("Excel test data file '" + classpathResource + "' has no header row in its first sheet.");
            List<String> headers = readHeaders(headerRow);

            List<Map<String, Object>> records = new ArrayList<>();
            for (int rowIndex = headerRow.getRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isBlankRow(row, headers.size())) continue;
                records.add(TestDataReader.unflatten(toRecord(row, headers)));
            }
            return records;
        } catch (IOException e) {
            throw new TestDataException("Failed to read/parse Excel test data file '" + classpathResource + "'.", e);
        }
    }

    private static List<String> readHeaders(Row headerRow) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            String header = DATA_FORMATTER.formatCellValue(cell).trim();
            if (!header.isEmpty()) headers.add(header);
        }
        return headers;
    }

    private static Map<String, String> toRecord(Row row, List<String> headers) {
        Map<String, String> record = new LinkedHashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.getCell(i);
            record.put(headers.get(i), cell == null ? "" : DATA_FORMATTER.formatCellValue(cell));
        }
        return record;
    }

    private static boolean isBlankRow(Row row, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !DATA_FORMATTER.formatCellValue(cell).isBlank()) return false;
        }
        return true;
    }
}
```

### TestData — the source-agnostic accessor result

Wraps the same cached raw-records list `TestDataManager` caches globally — never mutated;
every accessor builds and returns a brand-new object, so one parallel test can never corrupt
data another test is reading from the same cached file. Placeholder resolution happens here,
lazily, on every access.

```java
package com.framework.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.exceptions.TestDataException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TestData {

    private final List<Map<String, Object>> rawRecords;
    private final ObjectMapper conversionMapper;
    private final String sourceDescription;

    TestData(List<Map<String, Object>> rawRecords, ObjectMapper conversionMapper, String sourceDescription) {
        this.rawRecords = rawRecords;
        this.conversionMapper = conversionMapper;
        this.sourceDescription = sourceDescription;
    }

    public int size() { return rawRecords.size(); }

    public Map<String, Object> get(int index) { return resolve(rawRecordAt(index)); }
    public <T> T get(int index, Class<T> type) { return convert(get(index), type); }

    /** name = a JSON/YAML object key, or a CSV/Excel row's "name" column. */
    public Map<String, Object> get(String name) { return resolve(rawRecordNamed(name)); }
    public <T> T get(String name, Class<T> type) { return convert(get(name), type); }

    public List<Map<String, Object>> all() {
        List<Map<String, Object>> resolved = new ArrayList<>(rawRecords.size());
        for (Map<String, Object> raw : rawRecords) resolved.add(resolve(raw));
        return resolved;
    }

    /** One TestNG @DataProvider row per record, each row a single Map<String,Object> argument. */
    public Object[][] dataProvider() {
        List<Map<String, Object>> resolved = all();
        Object[][] rows = new Object[resolved.size()][1];
        for (int i = 0; i < resolved.size(); i++) rows[i][0] = resolved.get(i);
        return rows;
    }

    public <T> Object[][] dataProvider(Class<T> type) {
        List<Map<String, Object>> resolved = all();
        Object[][] rows = new Object[resolved.size()][1];
        for (int i = 0; i < resolved.size(); i++) rows[i][0] = convert(resolved.get(i), type);
        return rows;
    }

    private Map<String, Object> rawRecordAt(int index) {
        if (index < 0 || index >= rawRecords.size())
            throw new TestDataException("No record at index " + index + " in " + sourceDescription + " (" + rawRecords.size() + " record(s)).");
        return rawRecords.get(index);
    }

    private Map<String, Object> rawRecordNamed(String name) {
        for (Map<String, Object> record : rawRecords) {
            if (name.equals(record.get(TestDataReader.NAME_FIELD))) return record;
        }
        throw new TestDataException("No record named '" + name + "' in " + sourceDescription + ". Add a '" + TestDataReader.NAME_FIELD + "' field.");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> resolve(Map<String, Object> raw) { return (Map<String, Object>) resolveValue(raw); }

    private static Object resolveValue(Object value) {
        if (value instanceof String s) return PlaceholderResolver.resolve(s);
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> resolved = new LinkedHashMap<>();
            map.forEach((key, v) -> resolved.put(String.valueOf(key), resolveValue(v)));
            return resolved;
        }
        if (value instanceof List<?> list) {
            List<Object> resolved = new ArrayList<>(list.size());
            for (Object element : list) resolved.add(resolveValue(element));
            return resolved;
        }
        return value;
    }

    private <T> T convert(Map<String, Object> resolvedRecord, Class<T> type) {
        try { return conversionMapper.convertValue(resolvedRecord, type); }
        catch (IllegalArgumentException e) {
            throw new TestDataException("Failed to convert a record from " + sourceDescription + " into " + type.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}
```

### TestDataManager — the single entry point

Dispatches to a reader by file extension under a fixed per-format folder convention. A bare
file name with no extension resolves against `testdata.format` (default `json`); a name that
already has a recognized extension always wins over that default. Uses a **separate, lenient**
`ObjectMapper` from `JsonUtils`'s (coerces `"50"` → `int` etc., since CSV/Excel are inherently
stringly-typed) — kept private here so real API response parsing stays strict.

```java
package com.framework.testdata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.exceptions.TestDataException;
import com.framework.reporting.AllureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TestDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataManager.class);
    private static final String BASE_FOLDER = "testdata";

    private static final Map<String, TestDataReader> READERS_BY_EXTENSION = Map.of(
            "json", new JsonDataReader(), "yaml", new YamlDataReader(), "yml", new YamlDataReader(),
            "csv", new CsvDataReader(), "xlsx", new ExcelDataReader(), "xls", new ExcelDataReader());

    private static final Map<String, String> FORMAT_FOLDER_BY_EXTENSION = Map.of(
            "json", "json", "yaml", "yaml", "yml", "yaml", "csv", "csv", "xlsx", "excel", "xls", "excel");

    private static final Map<String, String> EXTENSION_BY_FORMAT = Map.of(
            "json", "json", "yaml", "yaml", "yml", "yaml", "csv", "csv", "excel", "xlsx");

    private static final ObjectMapper CONVERSION_MAPPER = buildConversionMapper();

    private static ObjectMapper buildConversionMapper() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.coercionConfigDefaults().setCoercion(CoercionInputShape.String, CoercionAction.TryConvert);
        return mapper;
    }

    private static final Map<String, List<Map<String, Object>>> CACHE = new ConcurrentHashMap<>();

    private TestDataManager() {}

    public static TestData load(String fileName) {
        String resolvedFileName = resolveFileName(fileName);
        String extension = extensionOf(resolvedFileName);
        TestDataReader reader = READERS_BY_EXTENSION.get(extension);
        if (reader == null) {
            throw new TestDataException("Unsupported test data file extension '." + extension + "' for '" + resolvedFileName + "'. Supported: " + READERS_BY_EXTENSION.keySet() + ".");
        }
        String resourcePath = BASE_FOLDER + "/" + FORMAT_FOLDER_BY_EXTENSION.get(extension) + "/" + resolvedFileName;
        List<Map<String, Object>> rawRecords = CACHE.computeIfAbsent(resourcePath, reader::read);
        return new TestData(rawRecords, CONVERSION_MAPPER, resourcePath);
    }

    /**
     * The one call a test needs: file name + case name -> that case's data, ready to use.
     * Requires T to implement TestCaseRecord (any record shaped (metadata, data) does, free).
     * Pass fileName WITHOUT an extension unless the case genuinely must pin one format
     * regardless of testdata.format - a hardcoded ".json" silently defeats YAML/CSV/Excel
     * support for that call site.
     */
    public static <D, T extends TestCaseRecord<D>> D getCaseData(String fileName, String caseName, Class<T> caseType) {
        T testCase = load(fileName).get(caseName, caseType);
        LOGGER.info("[{}] {}", testCase.metadata().testCaseId(), testCase.metadata().testCaseName());
        AllureManager.attachTestCaseMetadata(testCase.metadata().testCaseId(), testCase.metadata().testCaseName());
        return testCase.data();
    }

    private static String resolveFileName(String fileName) {
        if (fileName.lastIndexOf('.') > 0) return fileName;
        String format = ConfigManager.getString(ConfigKeys.TEST_DATA_FORMAT, "json").toLowerCase(Locale.ROOT);
        String extension = EXTENSION_BY_FORMAT.get(format);
        if (extension == null) throw new TestDataException("Unsupported '" + ConfigKeys.TEST_DATA_FORMAT + "' value '" + format + "' for '" + fileName + "'. Supported: " + EXTENSION_BY_FORMAT.keySet() + ".");
        return fileName + "." + extension;
    }

    private static String extensionOf(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot < 0 || dot == fileName.length() - 1) throw new TestDataException("Test data file name '" + fileName + "' has no extension to dispatch on.");
        return fileName.substring(dot + 1).toLowerCase(Locale.ROOT);
    }
}
```

**Usage:**

```java
TestDataManager.load("login.json").get("validLogin", AuthRequest.class);
TestDataManager.load("events.csv").dataProvider(CreateEventRequest.class);
TestDataManager.getCaseData("web/web", "validLogin", LoginTestCase.class); // file + case -> data, logged automatically
```

**Test data file convention** — every row splits `metadata` from `data`:

```json
{
  "validLogin": {
    "metadata": { "testCaseId": "TC-WEB-LOGIN-001", "testCaseName": "User logs in successfully with valid credentials" },
    "data": { "email": "${{EVENTHUB_EMAIL}}", "password": "${{EVENTHUB_PASSWORD}}" }
  }
}
```

CSV/Excel carry the same nested shape via dotted columns:

```csv
name,metadata.testCaseId,metadata.testCaseName,data.email,data.password
validLogin,TC-WEB-LOGIN-001,User logs in successfully...,${{EVENTHUB_EMAIL}},${{EVENTHUB_PASSWORD}}
```

| Format | Folder | Shape |
|---|---|---|
| JSON | `testdata/json/` | Object-root (named records) or array-root |
| YAML | `testdata/yaml/` | Same two shapes as JSON |
| CSV | `testdata/csv/` | Row-oriented; a `name` column enables name lookup |
| Excel | `testdata/excel/` | Same as CSV, first sheet |

**One folder, one file, per surface, never shared** — `testdata/{format}/api/api.*` backs the
API suite, `testdata/{format}/web/web.*` backs Web, `testdata/{format}/android/android.*` /
`testdata/{format}/ios/ios.*` back Mobile. This means a Mobile-only negative case never risks
a Web or API test picking up an unrelated row, even when all three ultimately log into the
same account.

## 13. Package: driver

Six classes: `DriverManager`/`DriverFactory` (package-private internals), `WebDriverManager`/
`MobileDriverManager` (public entry points), `MobileDeviceMatrix` (device inventory),
`MobileDevicePool` (parallel work-queue), `MobilePortAllocator` (per-session port pools).

### DriverManager — thread-local storage and cleanup (package-private)

```java
package com.framework.driver;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DriverManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> WEB_DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> MOBILE_DRIVER = new ThreadLocal<>();

    private DriverManager() {}

    static void setWebDriver(WebDriver driver) { WEB_DRIVER.set(driver); }
    static WebDriver getWebDriverOrNull() { return WEB_DRIVER.get(); }
    static void quitWebDriver() {
        WebDriver driver = WEB_DRIVER.get();
        if (driver == null) return;
        try { driver.quit(); }
        catch (RuntimeException e) { LOGGER.warn("Error while quitting WebDriver on thread '{}': {}", Thread.currentThread().getName(), e.getMessage()); }
        finally { WEB_DRIVER.remove(); }
    }

    static void setMobileDriver(AppiumDriver driver) { MOBILE_DRIVER.set(driver); }
    static AppiumDriver getMobileDriverOrNull() { return MOBILE_DRIVER.get(); }
    static void quitMobileDriver() {
        AppiumDriver driver = MOBILE_DRIVER.get();
        if (driver == null) return;
        try { driver.quit(); }
        catch (RuntimeException e) { LOGGER.warn("Error while quitting AppiumDriver on thread '{}': {}", Thread.currentThread().getName(), e.getMessage()); }
        finally { MOBILE_DRIVER.remove(); }
    }
}
```

### WebDriverManager / MobileDriverManager — public entry points

```java
package com.framework.driver;
import org.openqa.selenium.WebDriver;

public final class WebDriverManager {
    private WebDriverManager() {}

    public static WebDriver getDriver() {
        WebDriver driver = DriverManager.getWebDriverOrNull();
        if (driver == null) { driver = DriverFactory.createWebDriver(); DriverManager.setWebDriver(driver); }
        return driver;
    }
    public static boolean isDriverActive() { return DriverManager.getWebDriverOrNull() != null; }
    public static void quitDriver() { DriverManager.quitWebDriver(); }
}
```

```java
package com.framework.driver;
import io.appium.java_client.AppiumDriver;

public final class MobileDriverManager {
    private MobileDriverManager() {}

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DriverManager.getMobileDriverOrNull();
        if (driver == null) { driver = DriverFactory.createMobileDriver(); DriverManager.setMobileDriver(driver); }
        return driver;
    }
    public static boolean isDriverActive() { return DriverManager.getMobileDriverOrNull() != null; }

    /** Quits this thread's driver and releases whatever it was holding: a pooled device (if drawn from MobileDevicePool) and any checked-out ports. Both releases are no-ops when nothing was checked out that way. */
    public static void quitDriver() {
        DriverManager.quitMobileDriver();
        MobileDevicePool.releaseForCurrentThread();
        MobilePortAllocator.releaseAllForCurrentThread();
    }
}
```

### MobileDeviceMatrix — reads config/mobile-devices.json

One shared JSON file (not duplicated per environment — the same local emulator/simulator is
used regardless of `-Denv`):

```json
{
  "devices": {
    "android1": { "platform": "android", "deviceName": "Pixel_10", "platformVersion": "17" },
    "ios1":     { "platform": "ios", "deviceName": "iPhone 17 Pro", "platformVersion": "26.2" }
  },
  "androidList": ["android1"],
  "iosList": ["ios1"],
  "matrices": { "android": ["android1"], "ios": ["ios1"] },
  "ports": { "systemPort": { "start": 8200, "count": 50 } }
}
```

- `devices` — every device, keyed by id. Optional `appiumServerUrl` overrides the shared
  `appium.server.url` for that one device; optional `hybrid` (Android only) requests a
  `chromedriverPort` alongside `systemPort`.
- **Which app binary to install is deliberately NOT here** — it's an environment/build
  concern, so it lives in `config/{env}.properties` (`mobile.app.path.android`/`.ios`), one
  entry per **platform**, not per device.
- `ports` — the `start`/`count` pool range per port type. Not configurable per device,
  deliberately — see `MobilePortAllocator`'s design note below on why a fixed port per device
  reintroduced a real bug once.
- `androidList`/`iosList` — every id grouped by platform; a sequential run picks one via
  `mobile.platform` and takes the list's first id.
- `matrices` — named lists of ids for "run the same test on every device at once", not a work
  queue.

```java
package com.framework.driver;

import com.fasterxml.jackson.databind.JsonNode;
import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.exceptions.ConfigurationException;
import com.framework.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class MobileDeviceMatrix {

    private static final String RESOURCE_PATH = "config/mobile-devices.json";
    private static volatile JsonNode root;
    private static final Object INIT_LOCK = new Object();

    private MobileDeviceMatrix() {}

    public record Row(String id, String platform, String deviceName, String platformVersion, String appPath,
                       String appiumServerUrl, boolean hybrid) {}

    public static Row loadDevice(String id) {
        JsonNode device = root().path("devices").path(id);
        if (device.isMissingNode()) throw new ConfigurationException("No device '" + id + "' defined in '" + RESOURCE_PATH + "'.");
        String platform = requiredField(device, "platform", id);
        return new Row(id, platform, requiredField(device, "deviceName", id), requiredField(device, "platformVersion", id),
                appPathForPlatform(platform), device.path("appiumServerUrl").asText(null), device.path("hybrid").asBoolean(false));
    }

    public record PortRange(int start, int count) {}

    public static PortRange portRange(String portType, int defaultStart, int defaultCount) {
        JsonNode node = root().path("ports").path(portType);
        return new PortRange(node.path("start").asInt(defaultStart), node.path("count").asInt(defaultCount));
    }

    public static List<String> androidList() { return idsAt("androidList"); }
    public static List<String> iosList() { return idsAt("iosList"); }

    public static List<Row> load(String matrixName) {
        JsonNode ids = root().path("matrices").path(matrixName);
        if (!ids.isArray() || ids.isEmpty())
            throw new ConfigurationException("No matrix '" + matrixName + "' (or it lists no devices) under 'matrices' in '" + RESOURCE_PATH + "'.");
        List<Row> rows = new ArrayList<>();
        for (JsonNode idNode : ids) rows.add(loadDevice(idNode.asText()));
        return rows;
    }

    /** load(matrixName) packaged as a TestNG @DataProvider row set (one Row argument each). */
    public static Object[][] dataProvider(String matrixName) {
        List<Row> rows = load(matrixName);
        Object[][] result = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++) result[i][0] = rows.get(i);
        return result;
    }

    private static String appPathForPlatform(String platform) {
        String key = "ios".equalsIgnoreCase(platform) ? ConfigKeys.MOBILE_APP_PATH_IOS : ConfigKeys.MOBILE_APP_PATH_ANDROID;
        return ConfigManager.getString(key);
    }

    private static List<String> idsAt(String field) {
        JsonNode ids = root().path(field);
        List<String> result = new ArrayList<>();
        for (JsonNode idNode : ids) result.add(idNode.asText());
        return result;
    }

    private static String requiredField(JsonNode device, String field, String deviceId) {
        String value = device.path(field).asText(null);
        if (value == null || value.isBlank()) throw new ConfigurationException("Device '" + deviceId + "' in '" + RESOURCE_PATH + "' is missing '" + field + "'.");
        return value;
    }

    private static JsonNode root() {
        JsonNode result = root;
        if (result == null) {
            synchronized (INIT_LOCK) {
                result = root;
                if (result == null) { result = parse(); root = result; }
            }
        }
        return result;
    }

    private static JsonNode parse() {
        try (InputStream in = MobileDeviceMatrix.class.getClassLoader().getResourceAsStream(RESOURCE_PATH)) {
            if (in == null) throw new ConfigurationException("Classpath resource '" + RESOURCE_PATH + "' not found.");
            return JsonUtils.objectMapper().readTree(in);
        } catch (IOException e) {
            throw new ConfigurationException("Failed to parse '" + RESOURCE_PATH + "': " + e.getMessage(), e);
        }
    }
}
```

### MobileDevicePool — parallel work-queue over real devices (package-private)

Active only when `-Dparallel` is present. Whichever device becomes free first picks up the
next test — a genuine checkout/return pool, not "one device per thread regardless of load"
and not "every device runs the same test" (that's the matrix/`dataProvider` case above).

```java
package com.framework.driver;

import com.framework.constants.ConfigKeys;
import com.framework.exceptions.ConfigurationException;
import com.framework.exceptions.DriverInitializationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

final class MobileDevicePool {

    private static volatile BlockingQueue<MobileDeviceMatrix.Row> pool;
    private static final Object INIT_LOCK = new Object();
    private static final ThreadLocal<MobileDeviceMatrix.Row> CHECKED_OUT_BY_THREAD = new ThreadLocal<>();

    private MobileDevicePool() {}

    static boolean isPooledRunActive() { return System.getProperty("parallel") != null; }

    static MobileDeviceMatrix.Row checkout() {
        try {
            MobileDeviceMatrix.Row device = pool().take();
            CHECKED_OUT_BY_THREAD.set(device);
            return device;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DriverInitializationException("Interrupted while waiting for a free mobile device.", e);
        }
    }

    static void releaseForCurrentThread() {
        MobileDeviceMatrix.Row device = CHECKED_OUT_BY_THREAD.get();
        if (device != null) { pool().offer(device); CHECKED_OUT_BY_THREAD.remove(); }
    }

    private static BlockingQueue<MobileDeviceMatrix.Row> pool() {
        BlockingQueue<MobileDeviceMatrix.Row> result = pool;
        if (result == null) {
            synchronized (INIT_LOCK) {
                result = pool;
                if (result == null) { result = new LinkedBlockingQueue<>(loadPoolDevices()); pool = result; }
            }
        }
        return result;
    }

    /**
     * Both lists combined by default, unless -Dmobile.platform was given explicitly on THIS
     * command line, in which case the pool narrows to that platform's list only. Read via
     * System.getProperty directly, not ConfigManager - a plain config/{env}.properties default
     * must NOT narrow the pool the same way an explicit -D does.
     */
    private static List<MobileDeviceMatrix.Row> loadPoolDevices() {
        String explicitPlatform = System.getProperty(ConfigKeys.MOBILE_PLATFORM);
        String normalized = explicitPlatform == null ? null : explicitPlatform.trim().toLowerCase(Locale.ROOT);
        List<MobileDeviceMatrix.Row> devices = new ArrayList<>();
        if (normalized == null || "android".equals(normalized))
            for (String id : MobileDeviceMatrix.androidList()) devices.add(MobileDeviceMatrix.loadDevice(id));
        if (normalized == null || "ios".equals(normalized))
            for (String id : MobileDeviceMatrix.iosList()) devices.add(MobileDeviceMatrix.loadDevice(id));
        if (devices.isEmpty())
            throw new ConfigurationException("'androidList' and 'iosList' in config/mobile-devices.json listed no devices between them" + (normalized != null ? " for platform '" + normalized + "'" : "") + ".");
        return devices;
    }
}
```

### MobilePortAllocator — bounded, reusable per-session automation ports (package-private)

Hands out a fresh `systemPort` (Android/UiAutomator2), `wdaLocalPort` (iOS/XCUITest), or
`chromedriverPort` (Android WebView/hybrid) on every driver creation, so concurrent local
mobile sessions never collide on the same port. **The shared Appium server port
(`appium.server.url`) is unrelated to this** — one HTTP port legitimately serves many
concurrent sessions, same as any web server; these three are the per-session, device-side
automation ports.

**Checkout, not increment:** each port type is a bounded pool (`LinkedBlockingQueue` of every
port in its configured range), not an ever-climbing counter. Quitting a driver — success,
failure, or retry — returns every port that thread is holding.

> **Never a fixed port per device, deliberately.** An earlier version cached one port per
> thread, reused across that thread's later drivers. A live run against a real Android
> emulator proved that wrong: a session that failed to fully start can leave the device-side
> UiAutomator2 server still bound to that port; a retry on the same thread then reused the
> identical port and collided with the still-bound leftover ("local port #8200 is busy"). A
> released port always goes to the back of the queue, so a retry never collides with its own
> preceding failure's leftover state.

```java
package com.framework.driver;

import com.framework.exceptions.DriverInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

final class MobilePortAllocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobilePortAllocator.class);

    enum PortType {
        SYSTEM_PORT("systemPort", 8200, 50), WDA_LOCAL_PORT("wdaLocalPort", 8100, 50), CHROMEDRIVER_PORT("chromedriverPort", 9515, 50);
        final String jsonKey; final int defaultStart; final int defaultCount;
        PortType(String jsonKey, int defaultStart, int defaultCount) { this.jsonKey = jsonKey; this.defaultStart = defaultStart; this.defaultCount = defaultCount; }
    }

    private static final Map<PortType, BlockingQueue<Integer>> POOLS = new ConcurrentHashMap<>();
    private static final ThreadLocal<Map<PortType, Integer>> CHECKED_OUT_BY_THREAD = ThreadLocal.withInitial(() -> new EnumMap<>(PortType.class));

    private MobilePortAllocator() {}

    static int checkoutSystemPort(String deviceLabel) { return checkout(PortType.SYSTEM_PORT, deviceLabel); }
    static int checkoutWdaLocalPort(String deviceLabel) { return checkout(PortType.WDA_LOCAL_PORT, deviceLabel); }
    static int checkoutChromedriverPort(String deviceLabel) { return checkout(PortType.CHROMEDRIVER_PORT, deviceLabel); }

    static void releaseAllForCurrentThread() {
        Map<PortType, Integer> held = CHECKED_OUT_BY_THREAD.get();
        if (held.isEmpty()) return;
        held.forEach((type, port) -> {
            pool(type).offer(port);
            LOGGER.info("Released {} {} (thread={})", type.jsonKey, port, Thread.currentThread().getName());
        });
        held.clear();
    }

    private static int checkout(PortType type, String deviceLabel) {
        try {
            int port = pool(type).take();
            CHECKED_OUT_BY_THREAD.get().put(type, port);
            LOGGER.info("Allocated {} {} for device '{}' (thread={})", type.jsonKey, port, deviceLabel, Thread.currentThread().getName());
            return port;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DriverInitializationException("Interrupted while waiting for a free " + type.jsonKey + ".", e);
        }
    }

    private static BlockingQueue<Integer> pool(PortType type) { return POOLS.computeIfAbsent(type, MobilePortAllocator::buildPool); }

    private static BlockingQueue<Integer> buildPool(PortType type) {
        MobileDeviceMatrix.PortRange range = MobileDeviceMatrix.portRange(type.jsonKey, type.defaultStart, type.defaultCount);
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(range.count());
        for (int port = range.start(); port < range.start() + range.count(); port++) queue.offer(port);
        LOGGER.info("Initialized {} pool: {} ports starting at {}", type.jsonKey, range.count(), range.start());
        return queue;
    }
}
```

### DriverFactory — builds new driver instances (package-private, stateless)

Every call creates a brand-new driver; thread-local storage/reuse is `DriverManager`'s job.

```java
package com.framework.driver;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.enums.BrowserType;
import com.framework.enums.MobileDeviceProvider;
import com.framework.enums.MobilePlatformType;
import com.framework.exceptions.DriverInitializationException;
import com.framework.secrets.SecretManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

final class DriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private DriverFactory() {}

    // -------------------- Web --------------------

    static WebDriver createWebDriver() {
        BrowserType browserType = BrowserType.fromString(ConfigManager.getBrowser());
        boolean headless = ConfigManager.isHeadless();
        String resolution = ConfigManager.getResolution();

        WebDriver driver = switch (browserType) {
            case CHROME -> createChromeDriver(headless);
            case FIREFOX -> createFirefoxDriver(headless);
            case EDGE -> createEdgeDriver(headless);
            case SAFARI -> createSafariDriver(headless);
        };

        try {
            applyTimeouts(driver);
            applyResolution(driver, resolution);
        } catch (RuntimeException e) {
            // The browser process is already running here; without this, a failure (e.g. bad
            // resolution) leaks it - never stored in DriverManager, so DriverCleanupListener
            // would never see it to quit it.
            LOGGER.warn("Quitting WebDriver after post-creation setup failed: {}", e.getMessage());
            driver.quit();
            throw e;
        }

        LOGGER.info("WebDriver created: browser={}, headless={}, resolution={}", browserType, headless, resolution);
        return driver;
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // works around a CDP-handshake issue in headless CI
        if (headless) options.addArguments("--headless=new");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("-headless");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        return new EdgeDriver(options);
    }

    private static WebDriver createSafariDriver(boolean headless) {
        if (headless) LOGGER.warn("Safari has no headless mode; continuing in headed mode (requires 'safaridriver --enable' and Safari > Develop > Allow Remote Automation).");
        return new SafariDriver();
    }

    private static void applyTimeouts(WebDriver driver) {
        Duration pageLoad = Duration.ofSeconds(ConfigManager.getInt(ConfigKeys.PAGE_LOAD_TIMEOUT, 30));
        Duration script = Duration.ofSeconds(ConfigManager.getInt(ConfigKeys.SCRIPT_TIMEOUT, 30));
        Duration implicitWait = Duration.ofSeconds(ConfigManager.getInt(ConfigKeys.IMPLICIT_WAIT_TIMEOUT, 0));
        driver.manage().timeouts().pageLoadTimeout(pageLoad).scriptTimeout(script).implicitlyWait(implicitWait);
    }

    private static void applyResolution(WebDriver driver, String resolution) {
        if ("maximize".equalsIgnoreCase(resolution)) { driver.manage().window().maximize(); return; }
        String[] parts = resolution.toLowerCase().split("x");
        if (parts.length != 2) throw new DriverInitializationException("Invalid resolution '" + resolution + "'. Expected '<width>x<height>' or 'maximize'.");
        try {
            int width = Integer.parseInt(parts[0].trim());
            int height = Integer.parseInt(parts[1].trim());
            driver.manage().window().setSize(new Dimension(width, height));
        } catch (NumberFormatException e) {
            throw new DriverInitializationException("Invalid resolution '" + resolution + "'. Expected '<width>x<height>' or 'maximize'.", e);
        }
    }

    // -------------------- Mobile --------------------

    static AppiumDriver createMobileDriver() {
        resolveActiveDeviceFromPoolIfNeeded();
        try {
            MobileDeviceProvider provider = MobileDeviceProvider.fromString(ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_PROVIDER, "LOCAL"));
            MobilePlatformType platform = MobilePlatformType.fromString(ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM));
            URL serverUrl = toUrl(serverUrlFor(provider));
            Duration commandTimeout = Duration.ofSeconds(ConfigManager.getInt(ConfigKeys.APPIUM_COMMAND_TIMEOUT, 60));

            AppiumDriver driver = switch (platform) {
                case ANDROID -> new AndroidDriver(serverUrl, buildAndroidOptions(provider, commandTimeout));
                case IOS -> new IOSDriver(serverUrl, buildIosOptions(provider, commandTimeout));
            };
            LOGGER.info("Mobile driver created: provider={}, platform={}, deviceName={}", provider, platform, ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_NAME));
            return driver;
        } catch (RuntimeException e) {
            // A checked-out device/port is only ever released when a driver that was actually
            // created later gets quit - if creation itself fails, release explicitly here or
            // both pools shrink permanently for the rest of the run. No-op if nothing was
            // checked out (the ordinary, non-pooled case).
            MobileDevicePool.releaseForCurrentThread();
            MobilePortAllocator.releaseAllForCurrentThread();
            throw e;
        }
    }

    /**
     * A mobile run names no device details on the command line - device name/platform
     * version/app path all come from config/mobile-devices.json. Which device(s) depends on
     * whether -Dparallel is present: absent -> mobile.platform picks androidList/iosList and
     * uses its first id, sequentially; present -> each test checks a device out of
     * MobileDevicePool, blocking if every device is busy. An explicit mobile.device.name
     * (config/-D/test override) always wins and skips this resolution entirely.
     */
    private static void resolveActiveDeviceFromPoolIfNeeded() {
        if (ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_NAME, null) != null) return;
        MobileDeviceMatrix.Row device = MobileDevicePool.isPooledRunActive()
                ? MobileDevicePool.checkout()
                : MobileDeviceMatrix.loadDevice(firstIdInActivePlatformList());
        ConfigManager.setOverride(ConfigKeys.MOBILE_PLATFORM, device.platform());
        ConfigManager.setOverride(ConfigKeys.MOBILE_DEVICE_NAME, device.deviceName());
        ConfigManager.setOverride(ConfigKeys.MOBILE_PLATFORM_VERSION, device.platformVersion());
        ConfigManager.setOverride(ConfigKeys.MOBILE_APP_PATH, device.appPath());
        ConfigManager.setOverride(ConfigKeys.MOBILE_HYBRID, String.valueOf(device.hybrid()));
        if (device.appiumServerUrl() != null) ConfigManager.setOverride(ConfigKeys.APPIUM_SERVER_URL, device.appiumServerUrl());
    }

    private static String firstIdInActivePlatformList() {
        String platform = ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM, "android").trim().toLowerCase(Locale.ROOT);
        List<String> ids = "ios".equals(platform) ? MobileDeviceMatrix.iosList() : MobileDeviceMatrix.androidList();
        if (ids.isEmpty()) throw new DriverInitializationException("config/mobile-devices.json's '" + (platform.equals("ios") ? "iosList" : "androidList") + "' is empty.");
        return ids.get(0);
    }

    private static String serverUrlFor(MobileDeviceProvider provider) {
        return switch (provider) {
            case LOCAL -> ConfigManager.getString(ConfigKeys.APPIUM_SERVER_URL);
            case BROWSERSTACK -> ConfigManager.getString(ConfigKeys.BROWSERSTACK_SERVER_URL, "https://hub-cloud.browserstack.com/wd/hub");
        };
    }

    private static UiAutomator2Options buildAndroidOptions(MobileDeviceProvider provider, Duration commandTimeout) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(ConfigManager.getString(ConfigKeys.MOBILE_AUTOMATION_NAME, "UiAutomator2"))
                .setNewCommandTimeout(commandTimeout);

        if (provider == MobileDeviceProvider.BROWSERSTACK) { applyBrowserStackOptions(options); return options; }

        String deviceName = ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_NAME);
        options.setDeviceName(deviceName)
                .setPlatformVersion(ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM_VERSION))
                .setSystemPort(MobilePortAllocator.checkoutSystemPort(deviceName));

        if (ConfigManager.getBoolean(ConfigKeys.MOBILE_HYBRID, false))
            options.setChromedriverPort(MobilePortAllocator.checkoutChromedriverPort(deviceName));

        String udid = ConfigManager.getString(ConfigKeys.MOBILE_UDID, null);
        if (udid != null) options.setUdid(udid);

        String appPath = resolveAppPath(ConfigManager.getString(ConfigKeys.MOBILE_APP_PATH, null));
        String appPackage = ConfigManager.getString(ConfigKeys.MOBILE_APP_PACKAGE, null);
        String appActivity = ConfigManager.getString(ConfigKeys.MOBILE_APP_ACTIVITY, null);
        if (appPath != null) options.setApp(appPath);
        else if (appPackage != null && appActivity != null) options.setAppPackage(appPackage).setAppActivity(appActivity);
        else throw new DriverInitializationException("Android mobile driver requires either '" + ConfigKeys.MOBILE_APP_PATH + "' or both '" + ConfigKeys.MOBILE_APP_PACKAGE + "' and '" + ConfigKeys.MOBILE_APP_ACTIVITY + "'.");

        // Many apps show a splash activity transitioning to a main activity within ~1s;
        // Appium's default post-launch check waits for the exact manifest-declared launch
        // activity and can miss that transition, failing session creation even though the app
        // launched fine. A same-package wildcard tolerates any activity the app lands on.
        String defaultWaitActivity = appPackage != null ? appPackage + ".*" : "*";
        options.setAppWaitActivity(ConfigManager.getString(ConfigKeys.MOBILE_APP_WAIT_ACTIVITY, defaultWaitActivity));
        return options;
    }

    private static XCUITestOptions buildIosOptions(MobileDeviceProvider provider, Duration commandTimeout) {
        XCUITestOptions options = new XCUITestOptions()
                .setAutomationName(ConfigManager.getString(ConfigKeys.MOBILE_AUTOMATION_NAME, "XCUITest"))
                .setNewCommandTimeout(commandTimeout);

        if (provider == MobileDeviceProvider.BROWSERSTACK) { applyBrowserStackOptions(options); return options; }

        String deviceName = ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_NAME);
        options.setDeviceName(deviceName)
                .setPlatformVersion(ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM_VERSION))
                .setWdaLocalPort(MobilePortAllocator.checkoutWdaLocalPort(deviceName));

        String udid = ConfigManager.getString(ConfigKeys.MOBILE_UDID, null);
        if (udid != null) options.setUdid(udid);

        String appPath = resolveAppPath(ConfigManager.getString(ConfigKeys.MOBILE_APP_PATH, null));
        String bundleId = ConfigManager.getString(ConfigKeys.MOBILE_BUNDLE_ID, null);
        if (appPath != null) options.setApp(appPath);
        else if (bundleId != null) options.setBundleId(bundleId);
        else throw new DriverInitializationException("iOS mobile driver requires either '" + ConfigKeys.MOBILE_APP_PATH + "' or '" + ConfigKeys.MOBILE_BUNDLE_ID + "'.");
        return options;
    }

    /** Both UiAutomator2Options and XCUITestOptions extend BaseOptions, so the app/bstack:options capabilities are identical either way. Deliberately does NOT call setDeviceName/setPlatformVersion - BrowserStack reads device selection from bstack:options.deviceName/osVersion, not the top-level capabilities LOCAL uses. */
    private static void applyBrowserStackOptions(BaseOptions<?> options) {
        String appId = ConfigManager.getString(ConfigKeys.BROWSERSTACK_APP_ID, null);
        if (appId == null) throw new DriverInitializationException("BrowserStack mobile testing requires '" + ConfigKeys.BROWSERSTACK_APP_ID + "' - a 'bs://...' app ID from BrowserStack's app-upload API.");
        options.setCapability("app", appId);

        Map<String, Object> bstackOptions = new LinkedHashMap<>();
        bstackOptions.put("userName", SecretManager.get("BROWSERSTACK_USERNAME"));
        bstackOptions.put("accessKey", SecretManager.get("BROWSERSTACK_ACCESS_KEY"));
        bstackOptions.put("deviceName", ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_NAME));
        bstackOptions.put("osVersion", ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM_VERSION));
        putIfConfigured(bstackOptions, "projectName", ConfigKeys.BROWSERSTACK_PROJECT_NAME);
        putIfConfigured(bstackOptions, "buildName", ConfigKeys.BROWSERSTACK_BUILD_NAME);
        options.setCapability("bstack:options", bstackOptions);
    }

    private static void putIfConfigured(Map<String, Object> capabilities, String capabilityName, String configKey) {
        String value = ConfigManager.getString(configKey, null);
        if (value != null) capabilities.put(capabilityName, value);
    }

    private static URL toUrl(String rawUrl) {
        try { return URI.create(rawUrl).toURL(); }
        catch (IllegalArgumentException | MalformedURLException e) { throw new DriverInitializationException("Invalid Appium server URL '" + rawUrl + "'.", e); }
    }

    /** Resolves a relative mobile.app.path against the current working directory so config files stay portable across machines. */
    private static String resolveAppPath(String appPath) {
        if (appPath == null || appPath.isBlank()) return null;
        return java.nio.file.Path.of(appPath).toAbsolutePath().toString();
    }
}
```

## 14. Package: web

Five classes: `WebWaits` (the centralized explicit-wait engine), `WebActions` (element-level
interactions), `WebUtils` (browser/page-level operations), `BasePage`, `BaseComponent`.

### WebWaits

```java
package com.framework.web;

import com.framework.driver.WebDriverManager;
import com.framework.exceptions.ElementInteractionException;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.function.Function;

public final class WebWaits {
    private WebWaits() {}

    public static WebElement waitForVisible(By locator) { return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), "element to be visible: " + locator); }
    public static WebElement waitForClickable(By locator) { return waitFor(ExpectedConditions.elementToBeClickable(locator), "element to be clickable: " + locator); }
    public static boolean waitForInvisible(By locator) { return waitFor(ExpectedConditions.invisibilityOfElementLocated(locator), "element to become invisible: " + locator); }
    public static boolean waitForUrlContains(String fragment) { return waitFor(ExpectedConditions.urlContains(fragment), "URL to contain: " + fragment); }
    public static boolean waitForTitleContains(String fragment) { return waitFor(ExpectedConditions.titleContains(fragment), "title to contain: " + fragment); }

    /** Escape hatch for any ExpectedConditions (or custom condition) not covered above. */
    public static <T> T waitFor(Function<WebDriver, T> condition, String description) {
        try { return WaitUtils.buildFluentWait(WebDriverManager.getDriver()).until(condition); }
        catch (TimeoutException e) { throw new ElementInteractionException("Timed out waiting for " + description, e); }
    }
}
```

### WebActions — element-level, always waits first, never a raw findElement

```java
package com.framework.web;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.driver.WebDriverManager;
import com.framework.enums.ScreenshotMode;
import com.framework.exceptions.ElementInteractionException;
import com.framework.utils.ScreenshotUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public final class WebActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebActions.class);
    private WebActions() {}

    public static void click(By locator) {
        try {
            WebElement element = WebWaits.waitForClickable(locator);
            clickResiliently(element, locator.toString());
            LOGGER.info("Clicked element: {}", locator);
            maybeScreenshotAfterAction("click");
        } catch (ElementInteractionException e) { throw e; }
        catch (WebDriverException e) { throw new ElementInteractionException("Failed to click element: " + locator, e); }
    }

    public static void type(By locator, String value) {
        try {
            WebElement element = WebWaits.waitForVisible(locator);
            element.clear();
            element.sendKeys(value);
            LOGGER.info("Typed into element: {}", locator);
            maybeScreenshotAfterAction("type");
        } catch (ElementInteractionException e) { throw e; }
        catch (WebDriverException e) { throw new ElementInteractionException("Failed to type into element: " + locator, e); }
    }

    public static String getText(By locator) {
        String text = WebWaits.waitForVisible(locator).getText();
        LOGGER.info("Read text from element {}: '{}'", locator, text);
        return text;
    }

    public static boolean isDisplayed(By locator) {
        try { return WebWaits.waitForVisible(locator).isDisplayed(); }
        catch (ElementInteractionException e) { return false; }
    }

    public static List<WebElement> findAll(By locator) {
        List<WebElement> elements = WebDriverManager.getDriver().findElements(locator);
        LOGGER.info("Found {} element(s) matching: {}", elements.size(), locator);
        return elements;
    }

    public static void selectByVisibleText(By locator, String visibleText) {
        new Select(WebWaits.waitForVisible(locator)).selectByVisibleText(visibleText);
        LOGGER.info("Selected '{}' in dropdown: {}", visibleText, locator);
        maybeScreenshotAfterAction("selectByVisibleText");
    }

    public static void selectByValue(By locator, String value) {
        new Select(WebWaits.waitForVisible(locator)).selectByValue(value);
        LOGGER.info("Selected value '{}' in dropdown: {}", value, locator);
        maybeScreenshotAfterAction("selectByValue");
    }

    public static void hover(By locator) {
        WebElement element = WebWaits.waitForVisible(locator);
        new Actions(WebDriverManager.getDriver()).moveToElement(element).perform();
        LOGGER.info("Hovered over element: {}", locator);
        maybeScreenshotAfterAction("hover");
    }

    public static void pressEnter(By locator) {
        WebWaits.waitForVisible(locator).sendKeys(Keys.ENTER);
        LOGGER.info("Pressed ENTER on element: {}", locator);
        maybeScreenshotAfterAction("pressEnter");
    }

    public static void uploadFile(By locator, String absoluteFilePath) {
        WebWaits.waitForVisible(locator).sendKeys(absoluteFilePath);
        LOGGER.info("Uploaded file via element: {}", locator);
        maybeScreenshotAfterAction("uploadFile");
    }

    public static void scrollIntoView(By locator) {
        WebElement element = WebWaits.waitForVisible(locator);
        WebUtils.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        LOGGER.info("Scrolled element into view: {}", locator);
    }

    private static void maybeScreenshotAfterAction(String actionName) {
        ScreenshotMode mode = ScreenshotMode.fromString(ConfigManager.getString(ConfigKeys.SCREENSHOT_MODE, "FAILURE"));
        if (mode == ScreenshotMode.EVERY_ACTION) ScreenshotUtils.capture(WebDriverManager.getDriver(), actionName);
    }

    /**
     * Clicks an already-located, already-clickable element, retrying through
     * ElementClickInterceptedException - which elementToBeClickable does NOT prevent (it only
     * checks visible+enabled, not "nothing currently overlaps it"). A sticky header/toast/
     * animation intercepting a click is common on real SPAs. Scroll-then-retry first; a JS
     * click is the final fallback since it bypasses the browser's hit-testing entirely.
     */
    static void clickResiliently(WebElement element, String description) {
        try { element.click(); }
        catch (ElementClickInterceptedException e) {
            LOGGER.warn("Click intercepted for {}, retrying after scrollIntoView.", description);
            WebUtils.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            try { element.click(); }
            catch (ElementClickInterceptedException stillIntercepted) {
                LOGGER.warn("Click still intercepted for {}, falling back to a JavaScript click.", description);
                WebUtils.executeScript("arguments[0].click();", element);
            }
        }
    }
}
```

### WebUtils — browser/page-level operations not tied to one element

```java
package com.framework.web;

import com.framework.driver.WebDriverManager;
import com.framework.exceptions.ElementInteractionException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;

public final class WebUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);
    private WebUtils() {}

    public static void navigateTo(String url) { WebDriverManager.getDriver().get(url); LOGGER.info("Navigated to: {}", url); }
    public static void refresh() { WebDriverManager.getDriver().navigate().refresh(); LOGGER.info("Refreshed the page."); }
    public static void navigateBack() { WebDriverManager.getDriver().navigate().back(); LOGGER.info("Navigated back."); }
    public static void navigateForward() { WebDriverManager.getDriver().navigate().forward(); LOGGER.info("Navigated forward."); }
    public static String getCurrentUrl() { return WebDriverManager.getDriver().getCurrentUrl(); }
    public static String getTitle() { return WebDriverManager.getDriver().getTitle(); }
    public static String getPageSource() { return WebDriverManager.getDriver().getPageSource(); }

    public static Object executeScript(String script, Object... args) {
        Object result = ((JavascriptExecutor) WebDriverManager.getDriver()).executeScript(script, args);
        LOGGER.info("Executed JavaScript.");
        return result;
    }

    public static void acceptAlert() { alert().accept(); LOGGER.info("Accepted alert."); }
    public static void dismissAlert() { alert().dismiss(); LOGGER.info("Dismissed alert."); }
    public static String getAlertText() { return alert().getText(); }
    private static Alert alert() { return WebWaits.waitFor(ExpectedConditions.alertIsPresent(), "alert to be present"); }

    public static void switchToFrame(By locator) {
        try { WebDriverManager.getDriver().switchTo().frame(WebWaits.waitForVisible(locator)); LOGGER.info("Switched to frame: {}", locator); }
        catch (WebDriverException e) { throw new ElementInteractionException("Failed to switch to frame: " + locator, e); }
    }
    public static void switchToDefaultContent() { WebDriverManager.getDriver().switchTo().defaultContent(); LOGGER.info("Switched to default content."); }

    public static void switchToWindow(String nameOrHandle) { WebDriverManager.getDriver().switchTo().window(nameOrHandle); LOGGER.info("Switched to window/tab: {}", nameOrHandle); }

    /** Uses Selenium's native switchTo().newWindow(), deliberately NOT JavaScript's window.open() - that route hangs headless Chrome when switching into the resulting window (found in practice). */
    public static void openNewTab() { WebDriverManager.getDriver().switchTo().newWindow(WindowType.TAB); LOGGER.info("Opened and switched to a new tab."); }
    public static Set<String> getWindowHandles() { return WebDriverManager.getDriver().getWindowHandles(); }
    public static String getCurrentWindowHandle() { return WebDriverManager.getDriver().getWindowHandle(); }
    public static void closeCurrentWindow() { WebDriverManager.getDriver().close(); LOGGER.info("Closed the current window/tab."); }
}
```

### BasePage / BaseComponent

```java
package com.framework.web;

import com.framework.driver.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Only the handful of methods nearly every page needs. Anything else - dropdowns, alerts, frames, windows, JS - call WebActions/WebUtils directly. */
public abstract class BasePage {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected WebDriver driver() { return WebDriverManager.getDriver(); }
    protected void click(By locator) { WebActions.click(locator); }
    protected void type(By locator, String value) { WebActions.type(locator, value); }
    protected String getText(By locator) { return WebActions.getText(locator); }
    protected boolean isDisplayed(By locator) { return WebActions.isDisplayed(locator); }
    protected void navigateTo(String url) { WebUtils.navigateTo(url); }
}
```

```java
package com.framework.web;

import com.framework.exceptions.ElementInteractionException;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base for reusable UI components embedded in one or more pages. Every locator method is
 * scoped under `root`, so N instances of the same component (e.g. N product cards) never
 * collide with each other's elements. Two constructors: an already-located WebElement root
 * (a repeated component, handed one by its parent page via WebActions.findAll), or a By
 * (a page-wide singleton component that locates its own root, wait-safe).
 */
public abstract class BaseComponent {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebElement root;

    protected BaseComponent(WebElement root) { this.root = root; }
    protected BaseComponent(By rootLocator) { this.root = WebWaits.waitForVisible(rootLocator); }
    protected WebElement root() { return root; }

    protected WebElement find(By locator) {
        try { return WaitUtils.buildFluentWait(root).until(context -> context.findElement(locator)); }
        catch (TimeoutException e) { throw new ElementInteractionException("Timed out waiting for '" + locator + "' inside " + getClass().getSimpleName(), e); }
    }

    protected String textOf(By locator) { return find(locator).getText(); }

    protected void click(By locator) {
        // find() only waits for existence under root; a React (or similar) app can render an
        // element before its handlers attach, so a raw click() right after find() can silently
        // no-op. Waiting for elementToBeClickable on the located element closes that gap.
        WebElement element = find(locator);
        WebWaits.waitFor(ExpectedConditions.elementToBeClickable(element), "element to be clickable: " + locator);
        WebActions.clickResiliently(element, locator.toString());
        logger.info("Clicked '{}' inside {}", locator, getClass().getSimpleName());
    }
}
```

---

## 15. Package: mobile

Six classes, mirroring `web`'s shape: `MobileWaits`, `MobileActions`, `MobileUtils`,
`BaseMobilePage`, `BaseMobileComponent`, plus `PlatformLocator` (Mobile-only, no Web
equivalent needed).

### MobileWaits

```java
package com.framework.mobile;

import com.framework.driver.MobileDriverManager;
import com.framework.exceptions.ElementInteractionException;
import com.framework.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.function.Function;

public final class MobileWaits {
    private MobileWaits() {}

    public static WebElement waitForVisible(By locator) { return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), "element to be visible: " + locator); }
    public static WebElement waitForClickable(By locator) { return waitFor(ExpectedConditions.elementToBeClickable(locator), "element to be clickable: " + locator); }
    public static boolean waitForInvisible(By locator) { return waitFor(ExpectedConditions.invisibilityOfElementLocated(locator), "element to become invisible: " + locator); }

    public static <T> T waitFor(Function<org.openqa.selenium.WebDriver, T> condition, String description) {
        try {
            AppiumDriver driver = MobileDriverManager.getDriver();
            return WaitUtils.buildFluentWait(driver).until(condition);
        } catch (TimeoutException e) { throw new ElementInteractionException("Timed out waiting for " + description, e); }
    }
}
```

### MobileActions — element-level; gestures use W3C PointerInput, never TouchAction

```java
package com.framework.mobile;

import com.framework.driver.MobileDriverManager;
import com.framework.exceptions.ElementInteractionException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.List;

public final class MobileActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileActions.class);
    private static final Duration SWIPE_DURATION = Duration.ofMillis(400);
    private static final int MAX_SCROLL_ATTEMPTS = 8;
    private MobileActions() {}

    public static void tap(By locator) {
        try { MobileWaits.waitForClickable(locator).click(); LOGGER.info("Tapped element: {}", locator); }
        catch (ElementInteractionException e) { throw e; }
        catch (WebDriverException e) { throw new ElementInteractionException("Failed to tap element: " + locator, e); }
    }

    public static void type(By locator, String value) {
        try {
            WebElement element = MobileWaits.waitForVisible(locator);
            // Explicit tap-to-focus before typing - on Android, an unfocused Flutter EditText's
            // sendKeys()/clear() goes through the accessibility service's direct "set text"
            // action, which visually updates the native element but never reaches Flutter's own
            // TextEditingController, so the app's own form state stays empty. Tapping first opens
            // a real IME/input-connection so the following clear()/sendKeys() land as genuine key
            // events. A no-op on iOS, where this was never an issue.
            element.click();
            element.clear();
            element.sendKeys(value);
            LOGGER.info("Typed into element: {}", locator);
        } catch (ElementInteractionException e) { throw e; }
        catch (WebDriverException e) { throw new ElementInteractionException("Failed to type into element: " + locator, e); }
    }

    public static String getText(By locator) {
        String text = MobileWaits.waitForVisible(locator).getText();
        LOGGER.info("Read text from element {}: '{}'", locator, text);
        return text;
    }

    public static boolean isDisplayed(By locator) {
        try { return MobileWaits.waitForVisible(locator).isDisplayed(); }
        catch (ElementInteractionException e) { return false; }
    }

    public static List<WebElement> findAll(By locator) {
        List<WebElement> elements = MobileDriverManager.getDriver().findElements(locator);
        LOGGER.info("Found {} element(s) matching: {}", elements.size(), locator);
        return elements;
    }

    public static void longPress(By locator) {
        WebElement element = MobileWaits.waitForVisible(locator);
        Dimension size = element.getSize();
        org.openqa.selenium.Point location = element.getLocation();
        int centerX = location.getX() + size.getWidth() / 2;
        int centerY = location.getY() + size.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofMillis(800)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        perform(longPress);
        LOGGER.info("Long-pressed element: {}", locator);
    }

    /** Swipes up (searching downward through a scrollable list) up to MAX_SCROLL_ATTEMPTS times until locator is visible. */
    public static WebElement scrollToElement(By locator) {
        for (int attempt = 1; attempt <= MAX_SCROLL_ATTEMPTS; attempt++) {
            List<WebElement> matches = MobileDriverManager.getDriver().findElements(locator);
            if (!matches.isEmpty() && matches.get(0).isDisplayed()) {
                LOGGER.info("Found element {} after {} scroll attempt(s).", locator, attempt - 1);
                return matches.get(0);
            }
            MobileUtils.swipeUp();
        }
        throw new ElementInteractionException("Element '" + locator + "' was not found after " + MAX_SCROLL_ATTEMPTS + " scroll attempts.");
    }

    static void perform(Sequence sequence) { ((Interactive) MobileDriverManager.getDriver()).perform(List.of(sequence)); }
}
```

### MobileUtils — device/app-level operations not tied to one element

```java
package com.framework.mobile;

import com.framework.driver.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.List;

public final class MobileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileUtils.class);
    private static final Duration SWIPE_DURATION = Duration.ofMillis(400);
    private static final int MAX_DIALOG_DISMISS_ATTEMPTS = 4;
    private static final long DIALOG_SETTLE_MILLIS = 1000;
    private MobileUtils() {}

    /**
     * Dismisses unexpected Android system dialogs (an ANR prompt, an OS/API compatibility
     * warning, ...) that can appear on resource-constrained emulators or when a legacy app runs
     * on a much newer OS. Best-effort: taps the last button in each dialog up to
     * MAX_DIALOG_DISMISS_ATTEMPTS times, then stops once none are found. No-op on iOS.
     *
     * IMPORTANT filter: a Flutter app's OWN widgets (a Sign In button, a show/hide-password
     * toggle) render as real native android.widget.Button elements too - the Android
     * accessibility bridge maps any "is a button" semantics node to that class, not just
     * genuine OS dialogs. Filter out any button whose package equals the app's own current
     * package - otherwise this taps the app's own Sign In button on every launch, submitting a
     * blank form before the test itself even starts.
     */
    public static void dismissSystemDialogsIfPresent() {
        if (!(MobileDriverManager.getDriver() instanceof AndroidDriver driver)) return;
        String ownAppPackage = driver.getCurrentPackage();
        for (int attempt = 1; attempt <= MAX_DIALOG_DISMISS_ATTEMPTS; attempt++) {
            List<WebElement> buttons = driver.findElements(By.className("android.widget.Button")).stream()
                    .filter(button -> !ownAppPackage.equals(button.getAttribute("package")))
                    .toList();
            if (buttons.isEmpty()) return;
            LOGGER.warn("Dismissing an unexpected system dialog (attempt {}/{}, {} button(s) found).", attempt, MAX_DIALOG_DISMISS_ATTEMPTS, buttons.size());
            buttons.get(buttons.size() - 1).click();
            try { Thread.sleep(DIALOG_SETTLE_MILLIS); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
        }
    }

    public static void hideKeyboard() {
        AppiumDriver driver = MobileDriverManager.getDriver();
        if (driver instanceof HidesKeyboard hidesKeyboard) { hidesKeyboard.hideKeyboard(); LOGGER.info("Hid the on-screen keyboard."); }
        else LOGGER.warn("Current driver does not support hiding the keyboard.");
    }

    public static void activateApp(String appId) {
        AppiumDriver driver = MobileDriverManager.getDriver();
        if (driver instanceof InteractsWithApps interactsWithApps) { interactsWithApps.activateApp(appId); LOGGER.info("Activated app: {}", appId); }
        else LOGGER.warn("Current driver does not support app lifecycle management.");
    }

    public static void terminateApp(String appId) {
        AppiumDriver driver = MobileDriverManager.getDriver();
        if (driver instanceof InteractsWithApps interactsWithApps) { interactsWithApps.terminateApp(appId); LOGGER.info("Terminated app: {}", appId); }
        else LOGGER.warn("Current driver does not support app lifecycle management.");
    }

    public static boolean isAppInstalled(String appId) {
        AppiumDriver driver = MobileDriverManager.getDriver();
        if (driver instanceof InteractsWithApps interactsWithApps) return interactsWithApps.isAppInstalled(appId);
        LOGGER.warn("Current driver does not support app lifecycle management.");
        return false;
    }

    public static void swipeUp() {
        Dimension size = MobileDriverManager.getDriver().manage().window().getSize();
        int centerX = size.getWidth() / 2;
        swipe(centerX, (int) (size.getHeight() * 0.8), centerX, (int) (size.getHeight() * 0.2));
        LOGGER.info("Swiped up.");
    }

    public static void swipeDown() {
        Dimension size = MobileDriverManager.getDriver().manage().window().getSize();
        int centerX = size.getWidth() / 2;
        swipe(centerX, (int) (size.getHeight() * 0.2), centerX, (int) (size.getHeight() * 0.8));
        LOGGER.info("Swiped down.");
    }

    private static void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(SWIPE_DURATION, PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        MobileActions.perform(swipe);
    }
}
```

### BaseMobilePage / BaseMobileComponent

```java
package com.framework.mobile;

import com.framework.driver.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseMobilePage {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected AppiumDriver driver() { return MobileDriverManager.getDriver(); }
    protected void tap(By locator) { MobileActions.tap(locator); }
    protected void type(By locator, String value) { MobileActions.type(locator, value); }
    protected String getText(By locator) { return MobileActions.getText(locator); }
    protected boolean isDisplayed(By locator) { return MobileActions.isDisplayed(locator); }
}
```

```java
package com.framework.mobile;

import com.framework.exceptions.ElementInteractionException;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Kept as a separate, small, mirrored class rather than unifying with BaseComponent generically - the two constructors' root lookup genuinely needs a different driver manager per platform. */
public abstract class BaseMobileComponent {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebElement root;

    protected BaseMobileComponent(WebElement root) { this.root = root; }
    protected BaseMobileComponent(By rootLocator) { this.root = MobileWaits.waitForVisible(rootLocator); }
    protected WebElement root() { return root; }

    protected WebElement find(By locator) {
        try { return WaitUtils.buildFluentWait(root).until(context -> context.findElement(locator)); }
        catch (TimeoutException e) { throw new ElementInteractionException("Timed out waiting for '" + locator + "' inside " + getClass().getSimpleName(), e); }
    }

    protected String textOf(By locator) { return find(locator).getText(); }

    protected void tap(By locator) {
        WebElement element = find(locator);
        MobileWaits.waitFor(ExpectedConditions.elementToBeClickable(element), "element to be tappable: " + locator);
        element.click();
        logger.info("Tapped '{}' inside {}", locator, getClass().getSimpleName());
    }

    /** For components whose root element is itself the tap target, rather than a child of it. */
    protected void tapRoot() {
        MobileWaits.waitFor(ExpectedConditions.elementToBeClickable(root), "component root to be tappable: " + getClass().getSimpleName());
        root.click();
        logger.info("Tapped root of {}", getClass().getSimpleName());
    }
}
```

### PlatformLocator — a By that resolves platform-specific at find-time

Only needed for locators that genuinely differ per platform (an XPath anchored to a
platform-specific element class name). Locators built from `AppiumBy.accessibilityId(...)`
don't need this — one Flutter Semantics tree drives both iOS's `accessibilityId` and
Android's `content-desc` through that same strategy.

```java
package com.framework.mobile;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.enums.MobilePlatformType;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import java.util.List;

public final class PlatformLocator extends By {
    private final By androidLocator;
    private final By iosLocator;

    private PlatformLocator(By androidLocator, By iosLocator) { this.androidLocator = androidLocator; this.iosLocator = iosLocator; }

    public static By of(By androidLocator, By iosLocator) { return new PlatformLocator(androidLocator, iosLocator); }

    @Override public List<WebElement> findElements(SearchContext context) { return active().findElements(context); }
    @Override public WebElement findElement(SearchContext context) { return active().findElement(context); }

    private By active() {
        MobilePlatformType platform = MobilePlatformType.fromString(ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM));
        return platform == MobilePlatformType.ANDROID ? androidLocator : iosLocator;
    }

    @Override public String toString() { return "PlatformLocator(android=" + androidLocator + ", ios=" + iosLocator + ")"; }
}
```

## 16. Package: api

Six classes: `ApiHeaders` (package-private), `ApiRequest`, `ApiResponse`, `ApiUtils`,
`ApiContext`, `ApiClient` — the only place REST Assured is actually called from.

### ApiHeaders (package-private)

```java
package com.framework.api;
import java.util.LinkedHashMap;
import java.util.Map;

/** Framework defaults, then the current thread's bearer token (if any), then the request's own headers layered on top so an explicit Authorization header always wins. */
final class ApiHeaders {
    private ApiHeaders() {}

    static Map<String, String> build(Map<String, String> requestHeaders, String currentThreadToken) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        if (currentThreadToken != null) headers.put("Authorization", "Bearer " + currentThreadToken);
        headers.putAll(requestHeaders);
        return headers;
    }
}
```

### ApiRequest — fluent, framework-level call description

```java
package com.framework.api;

import io.restassured.http.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiRequest {
    private final Method method;
    private final String endpoint;
    private final Map<String, String> headers = new LinkedHashMap<>();
    private final Map<String, Object> queryParams = new LinkedHashMap<>();
    private final Map<String, Object> pathParams = new LinkedHashMap<>();
    private Object body;

    private ApiRequest(Method method, String endpoint) { this.method = method; this.endpoint = endpoint; }

    public static ApiRequest get(String endpoint) { return new ApiRequest(Method.GET, endpoint); }
    public static ApiRequest post(String endpoint) { return new ApiRequest(Method.POST, endpoint); }
    public static ApiRequest put(String endpoint) { return new ApiRequest(Method.PUT, endpoint); }
    public static ApiRequest patch(String endpoint) { return new ApiRequest(Method.PATCH, endpoint); }
    public static ApiRequest delete(String endpoint) { return new ApiRequest(Method.DELETE, endpoint); }

    public ApiRequest header(String name, String value) { headers.put(name, value); return this; }
    public ApiRequest queryParam(String name, Object value) { queryParams.put(name, value); return this; }
    /** endpoint must contain a matching {name} placeholder, e.g. "/events/{id}". */
    public ApiRequest pathParam(String name, Object value) { pathParams.put(name, value); return this; }
    public ApiRequest body(Object body) { this.body = body; return this; }

    Method method() { return method; }
    String endpoint() { return endpoint; }
    Map<String, String> headers() { return headers; }
    Map<String, Object> queryParams() { return queryParams; }
    Map<String, Object> pathParams() { return pathParams; }
    Object body() { return body; }
}
```

### ApiResponse — wraps REST Assured's Response with what tests actually need

```java
package com.framework.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.framework.exceptions.ApiException;
import com.framework.reporting.ApiReportRecorder;
import com.framework.secrets.SensitiveDataMasker;
import com.framework.utils.JsonUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApiResponse {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResponse.class);
    private final Response response;

    ApiResponse(Response response) { this.response = response; }

    public int statusCode() { return response.getStatusCode(); }
    public String body() { return response.getBody().asString(); }
    public JsonPath jsonPath() { return response.jsonPath(); }
    public String header(String name) { return response.getHeader(name); }
    public <T> T as(Class<T> type) { return JsonUtils.fromJson(body(), type); }

    /** For an envelope response {success, data, message}: deserializes just the node at dotSeparatedPath into type, instead of the whole body (which would silently produce a zero/null-filled object). */
    public <T> T extract(String dotSeparatedPath, Class<T> type) { return JsonUtils.fromJsonAtPath(body(), dotSeparatedPath, type); }

    /** For generic response types erasure defeats as(Class) on, e.g. PaginatedResponse<EventResponse>. */
    public <T> T as(TypeReference<T> typeReference) { return JsonUtils.fromJson(body(), typeReference); }

    /** Logs its own PASS/FAIL step to the API report either way, as a plain Expected/Actual pair. */
    public ApiResponse assertStatusCode(int expected) {
        if (statusCode() != expected) {
            String failMessage = "Expected status code " + expected + " but got " + statusCode()
                    + ". Body: " + SensitiveDataMasker.mask(body());
            ApiException exception = new ApiException(failMessage);
            LOGGER.error(failMessage, exception);
            ApiReportRecorder.logAssertion("Status Code", false, String.valueOf(expected), String.valueOf(statusCode()), failMessage);
            throw exception;
        }
        String passMessage = "Status code is " + expected + " as expected.";
        LOGGER.info(passMessage);
        ApiReportRecorder.logAssertion("Status Code", true, String.valueOf(expected), String.valueOf(statusCode()), null);
        return this;
    }

    public Response raw() { return response; }
}
```

### ApiUtils

```java
package com.framework.api;
import io.restassured.module.jsv.JsonSchemaValidator;

public final class ApiUtils {
    private ApiUtils() {}

    /** Validates a response body against a JSON schema file on the test classpath (e.g. src/test/resources/schemas/event.json). */
    public static void assertMatchesSchema(ApiResponse response, String classpathSchemaFile) {
        response.raw().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(classpathSchemaFile));
    }
}
```

### ApiContext — the API-facing runtime-variable surface

A thin facade over `VariableManager` (§10), kept as a static utility to match every other
framework-wide access point. Self-registers as a `PlaceholderResolver` source in a static
initializer, so any value stored here resolves as `${{key}}` in test data automatically.
Also **absorbs `ApiClient`'s bearer-token storage** — the current thread's token is just
another context variable under `ACCESS_TOKEN_KEY`, so it chains and resolves via
`${{accessToken}}` the same as any server-generated `userId`.

```java
package com.framework.api;

import com.framework.context.VariableManager;
import com.framework.secrets.SensitiveDataMasker;
import com.framework.testdata.PlaceholderResolver;
import java.util.Optional;

public final class ApiContext {

    public static final String ACCESS_TOKEN_KEY = "accessToken";

    static { PlaceholderResolver.registerSource(ApiContext::getOptional); }

    private ApiContext() {}

    public static void set(String key, String value) {
        VariableManager.set(key, value);
        if (ACCESS_TOKEN_KEY.equals(key) && value != null) {
            // Defense in depth beyond Authorization-header key-pattern masking: the raw token
            // can now flow unprefixed into any ${{accessToken}}-templated body, so mask by
            // literal value too.
            SensitiveDataMasker.registerSecretValue(value);
        }
    }

    public static String get(String key) { return VariableManager.get(key); }
    public static Optional<String> getOptional(String key) { return VariableManager.getOptional(key); }
    public static boolean has(String key) { return VariableManager.contains(key); }
    public static void remove(String key) { VariableManager.remove(key); }

    /** Clears every variable for this thread, including the access token. */
    public static void clear() { VariableManager.clear(); }
}
```

### ApiClient — executes ApiRequest against api.base.url, the only REST Assured call site

**Thread-safety:** every `execute` call builds a brand-new REST Assured request specification
— nothing shared across threads there. The one piece of thread-owned state (the current
bearer token) lives in `ApiContext`, not a private ThreadLocal here.

**Reporting:** every call is logged to SLF4J and recorded on the current test's
`ApiReportRecorder` record for the API surface's own separate HTML report — this class never
touches Extent or Allure directly.

```java
package com.framework.api;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.exceptions.ApiException;
import com.framework.reporting.ApiReportRecorder;
import com.framework.secrets.SensitiveDataMasker;
import com.framework.utils.JsonUtils;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);
    private ApiClient() {}

    public static void setAuthToken(String token) { ApiContext.set(ApiContext.ACCESS_TOKEN_KEY, token); }
    public static void clearAuthToken() { ApiContext.remove(ApiContext.ACCESS_TOKEN_KEY); }
    public static boolean hasAuthToken() { return ApiContext.has(ApiContext.ACCESS_TOKEN_KEY); }

    public static ApiResponse execute(ApiRequest request) {
        Map<String, String> headers = ApiHeaders.build(request.headers(), ApiContext.getOptional(ApiContext.ACCESS_TOKEN_KEY).orElse(null));

        RequestSpecification spec = RestAssured.given()
                .config(restAssuredConfig())
                .baseUri(ConfigManager.getApiBaseUrl())
                .headers(headers);
        request.queryParams().forEach(spec::queryParam);
        request.pathParams().forEach(spec::pathParam);
        if (request.body() != null) spec.body(request.body());

        LOGGER.info("{} {}", request.method(), request.endpoint());
        Response response;
        try { response = spec.request(request.method(), request.endpoint()); }
        catch (RuntimeException e) { throw new ApiException("API call failed: " + request.method() + " " + request.endpoint() + " - " + e.getMessage(), e); }
        logAndReport(request, headers, response);
        return new ApiResponse(response);
    }

    private static RestAssuredConfig restAssuredConfig() {
        int connectionTimeout = ConfigManager.getInt(ConfigKeys.API_CONNECTION_TIMEOUT, 10000);
        int socketTimeout = ConfigManager.getInt(ConfigKeys.API_SOCKET_TIMEOUT, 10000);
        return RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", connectionTimeout)
                .setParam("http.socket.timeout", socketTimeout));
    }

    private static void logAndReport(ApiRequest request, Map<String, String> headers, Response response) {
        String url = SensitiveDataMasker.mask(resolvedUrl(request));
        int statusCode = response.getStatusCode();
        long durationMs = response.time();

        String maskedHeaders = SensitiveDataMasker.mask(JsonUtils.prettyPrintJson(JsonUtils.toJson(headers)));
        String maskedRequestBody = request.body() != null
                ? SensitiveDataMasker.mask(JsonUtils.prettyPrintJson(JsonUtils.toJson(request.body())))
                : null;
        String maskedResponseHeaders = SensitiveDataMasker.mask(JsonUtils.prettyPrintJson(JsonUtils.toJson(headersToMap(response.headers()))));
        String maskedResponseBody = SensitiveDataMasker.mask(JsonUtils.prettyPrintJson(response.getBody().asString()));

        LOGGER.info("-> {} ({} ms)", statusCode, durationMs);
        LOGGER.info("Request headers:\n{}", maskedHeaders);
        if (maskedRequestBody != null) LOGGER.info("Request body:\n{}", maskedRequestBody);
        LOGGER.info("Response headers:\n{}", maskedResponseHeaders);
        LOGGER.info("Response body:\n{}", maskedResponseBody);

        ApiReportRecorder.logApiCall(request.method().name(), request.endpoint(), url, statusCode, durationMs,
                maskedHeaders, maskedRequestBody, maskedResponseHeaders, maskedResponseBody);
    }

    private static Map<String, String> headersToMap(Headers headers) {
        Map<String, String> map = new LinkedHashMap<>();
        headers.forEach(header -> map.put(header.getName(), header.getValue()));
        return map;
    }

    private static String resolvedUrl(ApiRequest request) {
        String path = request.endpoint();
        for (Map.Entry<String, Object> pathParam : request.pathParams().entrySet())
            path = path.replace("{" + pathParam.getKey() + "}", String.valueOf(pathParam.getValue()));
        String url = ConfigManager.getApiBaseUrl() + path;
        if (!request.queryParams().isEmpty()) {
            String query = request.queryParams().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "&" + b).orElse("");
            url = url + "?" + query;
        }
        return url;
    }
}
```

## 17. Package: reporting

Two independent reporting tracks, deliberately separate:

1. **Extent + Allure** for Web/Mobile tests (`ExtentManager`, `ExtentLoggingAppender`,
   `AllureManager`, `AllureLoggingAppender`, `ReportManager`).
2. **A completely separate, dependency-free, self-contained HTML report** for API tests only
   (`ApiReportRecorder`, `ApiReportModel`, `ApiHtmlReportRenderer`) — `com.framework.api` and
   `Verify` never touch Extent or Allure for an API test.

Why separate: API tests have no richer narrative layer underneath them (unlike Web/Mobile
Page Objects), so a generic Logback-to-Extent bridge can't render a multi-call, multi-assertion
API test usefully — Extent's Spark theme has no `white-space: pre` anywhere in its bundled
CSS, so even pretty-printed JSON collapses to one squished line in a real browser. Building a
Newman/Postman-style dashboard purpose-built for request/response/assertion detail is more
useful and simpler than fighting that.

### ReportManager — the one seam that knows an artifact belongs in both reports

Also the single place that resolves `report.types` (default `"extent"` only) — this
framework's own enrichment work (Extent node/step creation, every Allure attachment/step/
masking call) has a real cost even when nobody opens the result, so this lets a run skip it
outright. **`allure-testng`'s own native pass/fail/`@Before`/`@After` capture always runs
regardless** — it self-registers via its own `META-INF/services` entry the moment it's on the
classpath and cannot be silenced from a runtime flag.

```java
package com.framework.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class ReportManager {

    private static final Set<String> ENABLED_TYPES = resolveEnabledTypes();
    private ReportManager() {}

    public static boolean isExtentEnabled() { return ENABLED_TYPES.contains("extent"); }
    public static boolean isAllureEnabled() { return ENABLED_TYPES.contains("allure"); }

    private static Set<String> resolveEnabledTypes() {
        String raw = ConfigManager.getString(ConfigKeys.REPORT_TYPES, "extent");
        Set<String> types = new HashSet<>();
        for (String type : raw.split(",")) {
            String trimmed = type.trim().toLowerCase(Locale.ROOT);
            if (!trimmed.isEmpty()) types.add(trimmed);
        }
        return types;
    }

    /** Attaches pngPath to the calling thread's current Extent test node (if any) and to Allure (if enabled). */
    public static void attachScreenshot(Path pngPath, String label) {
        if (pngPath == null) return;
        ExtentTest test = ExtentManager.getTest();
        if (test != null) test.addScreenCaptureFromPath(pngPath.toString(), label);
        AllureManager.attachScreenshotFromPath(pngPath, label);
    }
}
```

### ExtentManager — owns the single ExtentReports instance and the current thread's node

**Thread-safety:** `EXTENT` is a thread-safe singleton (`createTest`/`flush` are documented
safe for TestNG parallel execution); `CURRENT_TEST` is thread-local. Node lifecycle is owned
by `ExtentReportingListener`, scoped to each `@Test` method's own invocation.
`report.overwrite=true` (default) always writes `reports/extent/index.html`; `false` gives
each run its own `reports/extent/report-{timestamp}.html`.

```java
package com.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtentManager.class);
    private static final DateTimeFormatter REPORT_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final Path REPORT_PATH = resolveReportPath();

    // Only ever created if Extent enrichment is enabled (report.types) - null otherwise, and
    // every method below already null-checks getTest() so disabling Extent needed no other changes.
    private static final ExtentReports EXTENT = ReportManager.isExtentEnabled() ? initExtent() : null;
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    private ExtentManager() {}

    public static ExtentTest startTest(String name) {
        if (EXTENT == null) return null;
        ExtentTest test = EXTENT.createTest(name);
        CURRENT_TEST.set(test);
        return test;
    }

    public static ExtentTest getTest() { return CURRENT_TEST.get(); }
    public static void endTest() { CURRENT_TEST.remove(); }
    public static void flush() { if (EXTENT != null) EXTENT.flush(); }

    public static void logInfo(String message) {
        ExtentTest test = getTest();
        if (test != null) test.info(message);
    }

    public static void logAssertion(Status status, String message) {
        ExtentTest test = getTest();
        if (test != null) test.log(status, coloredLabel(message, status == Status.PASS ? ExtentColor.GREEN : ExtentColor.RED));
    }

    public static void logStackTrace(Throwable throwable) {
        ExtentTest test = getTest();
        if (test != null) test.fail(throwable);
    }

    /** Label (bold badge) + content in a monospace, whitespace-preserving code block (a <textarea readonly>, not a plain <td>, so multi-line JSON stays readable). */
    public static void logCodeBlock(String label, String content) { logCodeBlock(label, content, ExtentColor.BLUE); }
    public static void logCodeBlock(String label, String content, int statusCode) { logCodeBlock(label, content, colorForStatus(statusCode)); }

    private static void logCodeBlock(String label, String content, ExtentColor labelColor) {
        ExtentTest test = getTest();
        if (test == null) return;
        test.info(coloredLabel(label, labelColor));
        test.log(Status.INFO, MarkupHelper.createCodeBlock(content));
    }

    public static void logStatusLine(String message, int statusCode) {
        ExtentTest test = getTest();
        if (test == null) return;
        test.log(Status.INFO, coloredLabel(message, colorForStatus(statusCode)));
    }

    private static Markup coloredLabel(String text, ExtentColor color) { return MarkupHelper.createLabel(text, color); }

    private static ExtentColor colorForStatus(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) return ExtentColor.GREEN;
        if (statusCode >= 300 && statusCode < 400) return ExtentColor.AMBER;
        if (statusCode >= 400) return ExtentColor.RED;
        return ExtentColor.GREY;
    }

    private static Path resolveReportPath() {
        boolean overwrite = ConfigManager.getBoolean(ConfigKeys.REPORT_OVERWRITE, true);
        String fileName = overwrite ? "index.html" : "report-" + REPORT_TIMESTAMP_FORMAT.format(LocalDateTime.now()) + ".html";
        return Path.of("reports", "extent", fileName);
    }

    private static ExtentReports initExtent() {
        try { Files.createDirectories(REPORT_PATH.getParent()); }
        catch (IOException e) { LOGGER.warn("Failed to create Extent report directory '{}': {}", REPORT_PATH.getParent(), e.getMessage()); }

        ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH.toString());
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Web-Mobile-API Automation Report");
        spark.config().setReportName("Web-Mobile-API Automation Framework");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", ConfigManager.getEnvironment().name());
        LOGGER.info("Extent report will be written to '{}'.", REPORT_PATH);
        return extent;
    }
}
```

### ExtentLoggingAppender — mirrors log events into the active Extent node

```java
package com.framework.reporting;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.framework.secrets.SensitiveDataMasker;

/** Wired purely through logback.xml - no test/page-object/service code changes needed. Silently drops events with no active Extent test on the calling thread (the expected common case outside a @Test's own invocation window). */
public class ExtentLoggingAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent event) {
        ExtentTest test = ExtentManager.getTest();
        if (test == null) return;
        test.log(mapLevel(event.getLevel()), SensitiveDataMasker.mask(event.getFormattedMessage()));
    }

    private static Status mapLevel(Level level) {
        if (level.isGreaterOrEqual(Level.ERROR)) return Status.FAIL;
        if (level.isGreaterOrEqual(Level.WARN)) return Status.WARNING;
        return Status.INFO;
    }
}
```

### AllureManager — thin wrapper around Allure's static attachment API

Callers are responsible for masking (pass already-masked text). Every method is a no-op
unless Allure enrichment is enabled.

```java
package com.framework.reporting;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AllureManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureManager.class);
    private AllureManager() {}

    public static void attachScreenshotFromPath(Path pngPath, String name) {
        if (!ReportManager.isAllureEnabled()) return;
        try { Allure.addAttachment(name, "image/png", new ByteArrayInputStream(Files.readAllBytes(pngPath)), "png"); }
        catch (IOException e) { LOGGER.warn("Failed to attach screenshot '{}' to Allure: {}", pngPath, e.getMessage()); }
    }

    public static void attachText(String name, String content) {
        if (!ReportManager.isAllureEnabled()) return;
        try { Allure.addAttachment(name, "text/plain", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), "txt"); }
        catch (RuntimeException e) { LOGGER.warn("Failed to attach '{}' to Allure: {}", name, e.getMessage()); }
    }

    /** Surfaces a test-case-data row's testCaseId/testCaseName as filterable/sortable Allure parameters. Called once, centrally, from TestDataManager.getCaseData - no test author adds this. */
    public static void attachTestCaseMetadata(String testCaseId, String testCaseName) {
        attachParameter("Test Case ID", testCaseId);
        attachParameter("Test Case Name", testCaseName);
    }

    public static void attachParameter(String name, String value) {
        if (!ReportManager.isAllureEnabled()) return;
        try { Allure.parameter(name, value); }
        catch (RuntimeException e) { LOGGER.warn("Failed to attach parameter '{}' to Allure: {}", name, e.getMessage()); }
    }
}
```

### AllureLoggingAppender — mirrors business-narrative log events into Allure as steps

```java
package com.framework.reporting;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.framework.secrets.SensitiveDataMasker;
import io.qameta.allure.Allure;

/** Deliberately NOT wired to com.framework.api - ApiClient already gets its own richer, explicit Allure.step per call with request/response attachments nested under it. */
public class AllureLoggingAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent event) {
        if (!ReportManager.isAllureEnabled() || Allure.getLifecycle().getCurrentTestCaseOrStep().isEmpty()) return;
        Allure.step(SensitiveDataMasker.mask(event.getFormattedMessage()));
    }
}
```

### The API's own report — ApiReportModel, ApiReportRecorder, ApiHtmlReportRenderer

`ApiReportModel` is plain data with zero reporting-library dependency:

```java
package com.framework.reporting;

import java.util.ArrayList;
import java.util.List;

final class ApiReportModel {
    enum Outcome { PASS, FAIL, SKIP }

    sealed interface TestEvent permits ApiCallEvent, AssertionEvent {}

    /** endpoint is the relative path shown on the collapsed row; url is the full absolute URL shown in expanded detail. Headers/bodies are already masked/pretty-printed by the time they get here. */
    record ApiCallEvent(String method, String endpoint, String url, int statusCode, long durationMs,
                         String requestHeaders, String requestBody, String responseHeaders, String responseBody) implements TestEvent {}

    /** expected/actual are shown as a plain Expected/Actual pair; detail is optional extra context (e.g. a response body dump on mismatch). */
    record AssertionEvent(String label, boolean passed, String expected, String actual, String detail) implements TestEvent {}

    static final class TestRecord {
        final String name;
        final String description;
        final List<String> groups;
        final String module;
        final long startMillis;
        final List<TestEvent> events = new ArrayList<>();
        long endMillis;
        Outcome outcome = Outcome.SKIP;
        String errorMessage;

        TestRecord(String name, String description, List<String> groups, String module, long startMillis) {
            this.name = name; this.description = description; this.groups = groups; this.module = module; this.startMillis = startMillis;
        }
        long durationMs() { return endMillis - startMillis; }
    }

    private ApiReportModel() {}
}
```

`ApiReportRecorder` collects one `TestRecord` per API test method (thread-local — TestNG runs
a given test method start-to-finish on one thread even under `parallel="classes"`/`"methods"`)
and accumulates every finished record into a suite-wide list that flushes to
`ApiHtmlReportRenderer` once, at suite end:

```java
package com.framework.reporting;

import com.framework.reporting.ApiReportModel.ApiCallEvent;
import com.framework.reporting.ApiReportModel.AssertionEvent;
import com.framework.reporting.ApiReportModel.Outcome;
import com.framework.reporting.ApiReportModel.TestRecord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ApiReportRecorder {

    private static final long SUITE_START_MILLIS = System.currentTimeMillis();
    private static final List<TestRecord> COMPLETED = new CopyOnWriteArrayList<>();
    private static final ThreadLocal<TestRecord> CURRENT = new ThreadLocal<>();

    private ApiReportRecorder() {}

    public static void startTest(String name, String description, List<String> groups, String module) {
        CURRENT.set(new TestRecord(name, description, groups, module, System.currentTimeMillis()));
    }

    /** true once startTest has run for the calling thread and before its matching finishTest - lets Verify decide whether to report here or to Extent. */
    public static boolean hasActiveTest() { return CURRENT.get() != null; }

    public static void logApiCall(String method, String endpoint, String url, int statusCode, long durationMs,
                                   String requestHeaders, String requestBody, String responseHeaders, String responseBody) {
        TestRecord test = CURRENT.get();
        if (test != null) test.events.add(new ApiCallEvent(method, endpoint, url, statusCode, durationMs, requestHeaders, requestBody, responseHeaders, responseBody));
    }

    public static void logAssertion(String label, boolean passed, String expected, String actual, String detail) {
        TestRecord test = CURRENT.get();
        if (test != null) test.events.add(new AssertionEvent(label, passed, expected, actual, detail));
    }

    public static void finishTest(boolean passed, boolean skipped, String errorMessage) {
        TestRecord test = CURRENT.get();
        if (test == null) return;
        test.endMillis = System.currentTimeMillis();
        test.outcome = skipped ? Outcome.SKIP : (passed ? Outcome.PASS : Outcome.FAIL);
        test.errorMessage = errorMessage;
        COMPLETED.add(test);
        CURRENT.remove();
    }

    /** No-op when no API test ran this suite. Safe to call more than once. */
    public static void flush() {
        if (COMPLETED.isEmpty()) return;
        ApiHtmlReportRenderer.render(SUITE_START_MILLIS, System.currentTimeMillis(), List.copyOf(COMPLETED));
    }
}
```

`ApiHtmlReportRenderer` builds one self-contained HTML file (everything inlined — no CDN, no
network access assumed) under `reports/api/`: summary cards, a perf strip, module grouping,
search + tag filters, one collapsible row per test with full request/response detail and
Expected/Actual assertions. Status pills are colored purely by HTTP status class (2xx green,
4xx amber, 5xx red) for at-a-glance reading — **they are not the pass/fail signal**; a test
that deliberately asserts a 400 is exactly as green as one asserting 200, only the row's own
icon and the Validations section decide that.

```java
package com.framework.reporting;

import com.framework.config.ConfigManager;
import com.framework.reporting.ApiReportModel.ApiCallEvent;
import com.framework.reporting.ApiReportModel.AssertionEvent;
import com.framework.reporting.ApiReportModel.Outcome;
import com.framework.reporting.ApiReportModel.TestEvent;
import com.framework.reporting.ApiReportModel.TestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

final class ApiHtmlReportRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHtmlReportRenderer.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a").withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").withZone(ZoneId.systemDefault());
    private static final AtomicInteger ID_SEQ = new AtomicInteger();

    private ApiHtmlReportRenderer() {}

    static void render(long suiteStartMillis, long suiteEndMillis, List<TestRecord> tests) {
        File reportDir = new File(new File(System.getProperty("user.dir"), "reports"), "api");
        reportDir.mkdirs();
        String fileName = ConfigManager.isReportOverwriteEnabled()
                ? "index.html"
                : "report-" + FILE_TIMESTAMP_FORMAT.format(Instant.ofEpochMilli(suiteStartMillis)) + ".html";
        File reportFile = new File(reportDir, fileName);

        String html = buildHtml(suiteStartMillis, suiteEndMillis, tests);
        try {
            Files.writeString(reportFile.toPath(), html, StandardCharsets.UTF_8);
            LOGGER.info("API report written to '{}'.", reportFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.warn("Failed to write API report to '{}': {}", reportFile.getAbsolutePath(), e.getMessage());
        }
    }

    private static String buildHtml(long suiteStartMillis, long suiteEndMillis, List<TestRecord> tests) {
        int total = tests.size();
        long passed = tests.stream().filter(t -> t.outcome == Outcome.PASS).count();
        long failed = tests.stream().filter(t -> t.outcome == Outcome.FAIL).count();
        long skipped = tests.stream().filter(t -> t.outcome == Outcome.SKIP).count();
        long durationMs = suiteEndMillis - suiteStartMillis;

        List<ApiCallEvent> calls = tests.stream().flatMap(t -> t.events.stream())
                .filter(ApiCallEvent.class::isInstance).map(ApiCallEvent.class::cast).toList();
        double avgResponseMs = calls.isEmpty() ? 0 : calls.stream().mapToLong(ApiCallEvent::durationMs).average().orElse(0);
        ApiCallEvent slowest = calls.stream().max(Comparator.comparingLong(ApiCallEvent::durationMs)).orElse(null);

        Map<String, List<TestRecord>> byModule = new LinkedHashMap<>();
        for (TestRecord test : tests) byModule.computeIfAbsent(test.module, m -> new java.util.ArrayList<>()).add(test);

        StringBuilder html = new StringBuilder(64 * 1024);
        html.append("<!doctype html><html lang=\"en\"><head><meta charset=\"UTF-8\">")
            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
            .append("<title>").append(escape(ConfigManager.getApiReportTitle())).append("</title>")
            .append("<style>").append(CSS).append("</style></head><body>");

        html.append("<header class=\"top\"><div class=\"top-inner\">")
            .append("<h1>").append(escape(ConfigManager.getApiReportName())).append("</h1>")
            .append("<p class=\"meta\">Environment: <b>").append(escape(ConfigManager.getEnvironment().name())).append("</b>")
            .append(" &nbsp;&middot;&nbsp; Base URL: <b>").append(escape(ConfigManager.getApiBaseUrl())).append("</b>")
            .append(" &nbsp;&middot;&nbsp; Executed: <b>").append(TIMESTAMP_FORMAT.format(Instant.ofEpochMilli(suiteStartMillis)))
            .append("</b></p></div></header>");

        html.append("<section class=\"summary\">")
            .append(card(String.valueOf(total), "Tests", "neutral"))
            .append(card(String.valueOf(passed), "Passed", "pass"))
            .append(card(String.valueOf(failed), "Failed", "fail"))
            .append(card(String.valueOf(skipped), "Skipped", "skip"))
            .append(card(formatDuration(durationMs), "Duration", "neutral"))
            .append("</section>");

        html.append("<section class=\"perf\">")
            .append("<span>Average Response: <b>").append(Math.round(avgResponseMs)).append(" ms</b></span>")
            .append("<span>Total API Calls: <b>").append(calls.size()).append("</b></span>");
        if (slowest != null) {
            html.append("<span>Slowest Call: <b>").append(escape(slowest.method())).append(" ")
                .append(escape(slowest.endpoint())).append(" &mdash; ").append(slowest.durationMs()).append(" ms</b></span>");
        }
        html.append("</section>");

        html.append("<section class=\"toolbar\">")
            .append("<input id=\"search\" type=\"search\" placeholder=\"Search test name or endpoint…\" oninput=\"filterTests()\">")
            .append("<div id=\"tagFilters\" class=\"tags\">");
        for (String tag : distinctTags(tests)) {
            html.append("<button type=\"button\" class=\"tag\" data-tag=\"").append(escape(tag))
                .append("\" onclick=\"toggleTag(this)\">").append(escape(tag)).append("</button>");
        }
        html.append("</div></section>");

        html.append("<main class=\"modules\">");
        for (Map.Entry<String, List<TestRecord>> entry : byModule.entrySet()) {
            List<TestRecord> moduleTests = entry.getValue();
            long modulePassed = moduleTests.stream().filter(t -> t.outcome == Outcome.PASS).count();
            html.append("<section class=\"module\"><h2>").append(escape(entry.getKey()))
                .append(" <span class=\"module-count\">(").append(modulePassed).append("/").append(moduleTests.size())
                .append(" passed)</span></h2>");
            for (TestRecord test : moduleTests) html.append(renderTestRow(test));
            html.append("</section>");
        }
        if (tests.isEmpty()) html.append("<p class=\"empty\">No tests ran.</p>");
        html.append("</main>");

        html.append("<p id=\"noMatches\" class=\"empty\" hidden>No tests match your filters.</p>");
        html.append("<footer class=\"foot\">Generated by OmniAuto</footer>");
        html.append("<script>").append(JS).append("</script>");
        html.append("</body></html>");
        return html.toString();
    }

    private static String renderTestRow(TestRecord test) {
        List<ApiCallEvent> calls = test.events.stream().filter(ApiCallEvent.class::isInstance).map(ApiCallEvent.class::cast).toList();
        List<AssertionEvent> assertions = test.events.stream().filter(AssertionEvent.class::isInstance).map(AssertionEvent.class::cast).toList();

        String icon = switch (test.outcome) { case PASS -> "✓"; case FAIL -> "✗"; case SKIP -> "⚠"; };

        String rowRight;
        if (calls.size() == 1) {
            ApiCallEvent call = calls.get(0);
            rowRight = "<span class=\"method\">" + escape(call.method()) + "</span>"
                    + "<span class=\"endpoint\">" + escape(call.endpoint()) + "</span>"
                    + statusPill(call.statusCode())
                    + "<span class=\"duration\">" + call.durationMs() + " ms</span>";
        } else if (calls.isEmpty()) {
            rowRight = "<span class=\"duration\">" + test.durationMs() + " ms</span>";
        } else {
            rowRight = "<span class=\"calls-count\">" + calls.size() + " calls</span>"
                    + "<span class=\"duration\">" + test.durationMs() + " ms total</span>";
        }

        String searchEndpoint = calls.isEmpty() ? "" : calls.get(0).endpoint();

        StringBuilder row = new StringBuilder();
        row.append("<details class=\"test-row outcome-").append(test.outcome.name().toLowerCase()).append("\" data-tags=\"")
           .append(escape(String.join(" ", test.groups))).append("\" data-search=\"")
           .append(escape((test.name + " " + searchEndpoint).toLowerCase())).append("\">");
        row.append("<summary><span class=\"icon\">").append(icon).append("</span>")
           .append("<span class=\"test-name\">").append(escape(test.name)).append("</span>")
           .append("<span class=\"row-right\">").append(rowRight).append("</span></summary>");

        row.append("<div class=\"detail\">");
        if (test.description != null && !test.description.isBlank())
            row.append("<p class=\"description\">").append(escape(test.description)).append("</p>");
        if (test.outcome == Outcome.FAIL && test.errorMessage != null && !test.errorMessage.isBlank())
            row.append("<div class=\"failure-box\"><b>Error</b><pre>").append(escape(test.errorMessage)).append("</pre></div>");
        if (test.outcome == Outcome.SKIP) {
            String reason = test.errorMessage != null && !test.errorMessage.isBlank() ? test.errorMessage : "No reason given.";
            row.append("<div class=\"skip-box\"><b>Skipped</b><pre>").append(escape(reason)).append("</pre></div>");
        }
        for (TestEvent event : test.events) if (event instanceof ApiCallEvent call) row.append(renderCall(call));
        if (!assertions.isEmpty()) {
            row.append("<div class=\"validations\"><b>Validations</b><ul>");
            for (AssertionEvent a : assertions) row.append(renderAssertion(a));
            row.append("</ul></div>");
        }
        row.append("</div></details>");
        return row.toString();
    }

    private static String renderCall(ApiCallEvent call) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"call\">");
        sb.append("<div class=\"call-line\"><span class=\"method\">").append(escape(call.method())).append("</span>")
          .append("<span class=\"url\">").append(escape(call.url())).append("</span>")
          .append(statusPill(call.statusCode()))
          .append("<span class=\"duration\">").append(call.durationMs()).append(" ms</span></div>");

        sb.append("<div class=\"req-res\">");
        sb.append("<details class=\"body-block\"><summary>Request</summary>");
        sb.append(sectionLabel("Headers")).append(headersBlock(call.requestHeaders()));
        sb.append(sectionLabel("Body")).append(jsonBlock(call.requestBody()));
        sb.append("</details>");

        sb.append("<details class=\"body-block\"><summary>Response</summary>");
        sb.append(sectionLabel("Headers")).append(headersBlock(call.responseHeaders()));
        sb.append(sectionLabel("Body")).append(jsonBlock(call.responseBody()));
        sb.append("</details>");
        sb.append("</div></div>");
        return sb.toString();
    }

    private static String renderAssertion(AssertionEvent a) {
        StringBuilder sb = new StringBuilder();
        sb.append("<li class=\"").append(a.passed() ? "pass" : "fail").append("\">")
          .append("<span class=\"icon\">").append(a.passed() ? "✓" : "✗").append("</span> ")
          .append("<span class=\"assertion-label\">").append(escape(a.label())).append("</span>");
        if (a.expected() != null) {
            sb.append("<div class=\"expected-actual\">")
              .append("<span>Expected: <code>").append(escape(a.expected())).append("</code></span>")
              .append("<span>Actual: <code>").append(escape(a.actual())).append("</code></span>")
              .append("</div>");
        }
        if (a.detail() != null && !a.detail().isBlank())
            sb.append("<div class=\"assertion-detail\">").append(escape(a.detail())).append("</div>");
        sb.append("</li>");
        return sb.toString();
    }

    private static String jsonBlock(String content) {
        if (content == null || content.isBlank()) return "<p class=\"empty\">(no body)</p>";
        String id = "body-" + ID_SEQ.incrementAndGet();
        return "<div class=\"json-block\"><button type=\"button\" class=\"copy\" onclick=\"copyBlock('" + id + "', this)\">Copy</button>"
             + "<pre id=\"" + id + "\">" + escape(content) + "</pre></div>";
    }

    private static String sectionLabel(String label) { return "<div class=\"section-label\">" + escape(label) + "</div>"; }

    private static String headersBlock(String content) {
        if (content == null || content.isBlank()) return "<p class=\"empty\">(no headers)</p>";
        return "<pre class=\"headers\">" + escape(content) + "</pre>";
    }

    private static String statusPill(int statusCode) {
        String cls = statusCode >= 500 ? "s5xx" : statusCode >= 400 ? "s4xx" : statusCode >= 300 ? "s3xx" : "s2xx";
        return "<span class=\"status-pill " + cls + "\">" + statusCode + "</span>";
    }

    private static String card(String value, String label, String tone) {
        return "<div class=\"card " + tone + "\"><div class=\"card-value\">" + value + "</div>"
             + "<div class=\"card-label\">" + escape(label) + "</div></div>";
    }

    /** smoke/regression first (the run-selector tags every test tends to carry), then the rest alphabetically. */
    private static List<String> distinctTags(List<TestRecord> tests) {
        TreeSet<String> tags = new TreeSet<>();
        for (TestRecord t : tests) tags.addAll(t.groups);
        List<String> ordered = new java.util.ArrayList<>();
        for (String priority : List.of("smoke", "regression")) if (tags.remove(priority)) ordered.add(priority);
        ordered.addAll(tags);
        return ordered;
    }

    private static String formatDuration(long ms) { return ms < 1000 ? ms + " ms" : String.format("%.1f s", ms / 1000.0); }

    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    // Inline CSS/JS (no CDN, no fonts) so the file opens standalone from disk or a CI artifact
    // download with no network access assumed. See the actual class for the full stylesheet
    // (summary cards, module sections, collapsible test rows/calls, status pills, search/tag
    // filter toolbar, expected/actual validation list) and the ~35-line vanilla-JS filter/copy
    // script (filterTests(), toggleTag(), copyBlock()) - reproduce the visual language described
    // above (light theme, green/red/amber status colors, monospace code blocks) rather than
    // retyping the literal CSS/JS byte-for-byte; nothing about their content is load-bearing
    // beyond "renders a readable dashboard with working search/filter/copy".
    private static final String CSS = "/* full stylesheet - light theme, summary cards, module sections, collapsible rows, status pills */";
    private static final String JS = "/* filterTests()/toggleTag()/copyBlock() - vanilla JS, no dependencies */";
}
```

> **Note on CSS/JS above:** the real implementation inlines a complete, specific stylesheet
> and script (light theme with CSS custom properties for green/red/amber/blue tones, a
> responsive summary-card grid, collapsible `<details>` rows, a sticky search+tag toolbar, and
> a `copyBlock()`/`filterTests()`/`toggleTag()` script). Any equivalent CSS/JS achieving the
> same visual/functional result (searchable, taggable, collapsible, copy-to-clipboard, status-
> colored) is a faithful rebuild — the byte-for-byte styling is not part of the framework's
> behavioral contract, only its outputs (summary counts, module grouping, request/response
> detail, Expected/Actual pairs) are.

## 18. Package: listeners

**Read this section before writing any listener.** TestNG's actual invocation order for
mixed `IInvokedMethodListener`/`ITestListener`/`ISuiteListener` callbacks is **not** always
what the API names suggest, and getting it wrong causes silent, hard-to-diagnose bugs (three
are documented below because they were each hit and fixed for real). Confirmed empirically
against TestNG 7.10.2, not assumed from docs:

```
beforeInvocation(before-config) -> @BeforeMethod -> afterInvocation(before-config)
-> onTestStart -> beforeInvocation(test) -> @Test -> afterInvocation(test)
-> onTestSuccess/Failure/Skipped -> beforeInvocation(after-config) -> @AfterMethod -> afterInvocation(after-config)
```

**Key rules:**
- `ITestListener.onTestStart` fires **after** `@BeforeMethod` has already run. Anything that
  must be in place *before* `@BeforeMethod` (config, thread-state resets) must use
  `IInvokedMethodListener.beforeInvocation` and check `isBeforeMethodConfiguration()`, not
  `onTestStart`.
- Multiple `afterInvocation` listeners of the same hook type fire in **reverse** registration
  order (confirmed with a two-listener probe — an initial "first registered, first invoked"
  assumption was wrong and broke a listener silently).
- `ITestListener`'s result-based callbacks (`onTestSuccess`/`onTestFailure`/`onTestSkipped`)
  fire only **after** every registered `IInvokedMethodListener.afterInvocation` has already
  run for that method — a phase-ordering guarantee independent of registration order between
  the two hook types.
- `ITestAnnotation.getRetryAnalyzerClass()` does **not** return `null` for a `@Test` with no
  `retryAnalyzer` declared — TestNG 7.10.2 defaults it to the sentinel
  `DisabledRetryAnalyzer.class`. Comparing against `null` silently never assigns a retry
  analyzer to anything.

### 1. TestLoggingContextListener — MDC log tagging (registered first)

```java
package com.framework.listeners;

import org.slf4j.MDC;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/** Registered FIRST so its afterInvocation (which removes the MDC value) fires LAST, after every other listener's own afterInvocation logging has already run with the tag still present. */
public class TestLoggingContextListener implements IInvokedMethodListener {
    private static final String MDC_KEY = "test";

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        MDC.put(MDC_KEY, method.getTestMethod().getRealClass().getSimpleName() + "." + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) { MDC.remove(MDC_KEY); }
}
```

### 2. RetryAnalyzer + RetryAnalyzerTransformer — bounded retry, never on AssertionError

```java
package com.framework.listeners;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryAnalyzer.class);

    /** Set right before a retry is scheduled; read-and-cleared by ExtentReportingListener when it creates the retry attempt's report node. Safe as a plain ThreadLocal handoff (not a queue) because TestNG retries the exact same method on the exact same thread as its very next action. */
    static final ThreadLocal<Integer> CURRENT_ATTEMPT = new ThreadLocal<>();

    private int retryCount = 0; // test-scoped: TestNG creates one RetryAnalyzer instance per @Test method

    @Override
    public boolean retry(ITestResult result) {
        if (result.getThrowable() instanceof AssertionError) {
            LOGGER.info("Not retrying '{}': failure was an assertion (business/logic failure), not transient.", testLabel(result));
            return false;
        }
        int maxRetries = ConfigManager.getInt(ConfigKeys.RETRY_MAX_COUNT, 0);
        if (retryCount >= maxRetries) return false;
        retryCount++;
        CURRENT_ATTEMPT.set(retryCount);
        LOGGER.warn("Retrying '{}': attempt {}/{} after failure - {}", testLabel(result), retryCount, maxRetries,
                result.getThrowable() != null ? result.getThrowable().getMessage() : "unknown failure");
        return true;
    }

    private static String testLabel(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }
}
```

```java
package com.framework.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/** Assigns RetryAnalyzer to every @Test automatically. IMPORTANT: compare against DisabledRetryAnalyzer.class, not null - see this section's intro. */
public class RetryAnalyzerTransformer implements IAnnotationTransformer {
    @Override
    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        Class<? extends IRetryAnalyzer> existing = annotation.getRetryAnalyzerClass();
        if (existing == null || existing == DisabledRetryAnalyzer.class) annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
```

### 3. ConfigurationRetryListener — extends the same retry policy to @Before/@AfterMethod

TestNG has no `retryAnalyzer` concept for configuration methods at all. Without this, a
transient failure in per-test setup (e.g. login hitting a momentary 502) fails outright and
skips every other `@Test` in the class — found live in an 8-thread parallel run.

```java
package com.framework.listeners;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class ConfigurationRetryListener implements IConfigurable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationRetryListener.class);

    @Override
    public void run(IConfigureCallBack callBack, ITestResult testResult) {
        ITestNGMethod method = testResult.getMethod();
        if (!method.isBeforeMethodConfiguration() && !method.isAfterMethodConfiguration()) {
            callBack.runConfigurationMethod(testResult);
            return;
        }
        int maxRetries = ConfigManager.getInt(ConfigKeys.RETRY_MAX_COUNT, 0);
        int attempt = 0;
        while (true) {
            callBack.runConfigurationMethod(testResult);
            Throwable raw = testResult.getThrowable();
            if (raw == null) return;
            // A reflectively-invoked configuration method's real exception arrives wrapped in
            // InvocationTargetException (getMessage() always null, never itself an
            // AssertionError even when its cause is) - unwrap before checking/logging.
            Throwable cause = raw.getCause() != null ? raw.getCause() : raw;
            if (cause instanceof AssertionError) {
                LOGGER.info("Not retrying '{}': failure was an assertion, not transient.", label(testResult));
                return;
            }
            if (attempt >= maxRetries) return;
            attempt++;
            LOGGER.warn("Retrying '{}': attempt {}/{} after failure - {}", label(testResult), attempt, maxRetries, cause.getMessage());
        }
    }

    private static String label(ITestResult testResult) {
        return testResult.getTestClass().getRealClass().getSimpleName() + "." + testResult.getMethod().getMethodName();
    }
}
```

> **Why read `getThrowable()` after each attempt, not a try/catch around
> `runConfigurationMethod`, and not `getStatus()`:** verified against TestNG 7.10.2's own
> source — `IConfigureCallBack#runConfigurationMethod` never throws back to `run`; TestNG's
> internal invocation helper wraps it in a try/catch that stashes the failure via
> `testResult.setThrowable(t)` and returns normally either way. `getStatus()` stays `STARTED`
> for the whole duration of this method. A first version tried both alternatives and the
> configuration method ran exactly once no matter how many retries were configured.

### 4. ConfigParameterListener — bridges TestNG `<parameter>`s into ConfigManager

```java
package com.framework.listeners;

import com.framework.config.ConfigManager;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * IMPORTANT: uses IInvokedMethodListener.beforeInvocation, repopulating before EVERY invoked
 * method (@BeforeMethod, @Test, @AfterMethod alike) - NOT ITestListener.onTestStart. A first
 * version used onTestStart and broke under real -Dparallel=classes -DthreadCount=4: a class
 * creating its WebDriver inside @BeforeMethod was reading whichever config a DIFFERENT test
 * last left on that pooled thread, since onTestStart fires after @BeforeMethod. Clearing and
 * resetting are cheap/idempotent, so there's no cost to doing it unconditionally.
 */
public class ConfigParameterListener implements IInvokedMethodListener, ITestListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ConfigManager.clearThreadState();
        ConfigManager.setTestNgParameters(testResult.getTestContext().getCurrentXmlTest().getAllParameters());
    }

    @Override
    public void onFinish(ITestContext context) { ConfigManager.clearThreadState(); } // final safety-net cleanup
}
```

### 5. ApiContextListener — clears runtime/API context around every test

```java
package com.framework.listeners;

import com.framework.context.VariableManager;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * IMPORTANT: clears at the true before/after-method boundaries, NOT ITestListener.onTestStart.
 * onTestStart fires AFTER @BeforeMethod - clearing there would wipe out exactly the context a
 * @BeforeMethod commonly sets up (e.g. login storing a token) before the @Test body ever runs.
 * Confirmed the hard way: a login-in-@BeforeMethod test started failing with 401s the first
 * time this was wired up with onTestStart instead.
 */
public class ApiContextListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.getTestMethod().isBeforeMethodConfiguration()) VariableManager.clear();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.getTestMethod().isAfterMethodConfiguration()) VariableManager.clear();
    }
}
```

### 6. DriverCleanupListener — quits WebDriver/AppiumDriver after every test

```java
package com.framework.listeners;

import com.framework.driver.MobileDriverManager;
import com.framework.driver.WebDriverManager;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/** afterInvocation fires unconditionally on outcome (pass/fail/skip), avoiding three near-identical ITestListener hooks. isTestMethod() filters out @Before/@After (should not trigger a mid-suite driver quit). */
public class DriverCleanupListener implements IInvokedMethodListener {
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;
        if (WebDriverManager.isDriverActive()) WebDriverManager.quitDriver();
        if (MobileDriverManager.isDriverActive()) MobileDriverManager.quitDriver();
    }
}
```

### 7. ExtentReportingListener — creates/finalizes the Extent node per @Test invocation

Scope decision: bound to the `@Test` method's own invocation window only —
`@BeforeMethod`/`@AfterMethod` steps are **not** captured here (Allure's own listener captures
those natively as "Before"/"After" sections regardless). Registration order matters: listed
*before* `ScreenshotCaptureListener`, so this listener's `afterInvocation` (detaches the node)
fires *after* that one's (attaches a failure screenshot to the still-open node).

```java
package com.framework.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.framework.config.ConfigManager;
import com.framework.driver.MobileDriverManager;
import com.framework.driver.WebDriverManager;
import com.framework.reporting.ExtentManager;
import com.framework.secrets.SecretManager;
import com.framework.utils.TextUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportingListener implements IInvokedMethodListener, ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // Forces ConfigManager/SecretManager's own one-time startup log lines to happen here,
        // before any @Test method (and therefore any Extent node) exists - otherwise whichever
        // test happens to first touch either class gets those lines misattributed into its own
        // report step log.
        ConfigManager.getBrowser();
        SecretManager.has("__report_clarity_warmup__");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod() || isApiTest(method)) return;
        String className = method.getTestMethod().getRealClass().getSimpleName();
        String methodName = method.getTestMethod().getMethodName();
        String name = className + " — " + TextUtils.humanize(methodName);
        if (MobileDriverManager.isDriverActive()) {
            var capabilities = MobileDriverManager.getDriver().getCapabilities();
            Object deviceName = capabilities.getCapability("deviceName");
            name += " [" + capabilities.getPlatformName() + (deviceName != null ? " · " + deviceName : "") + "]";
        }
        Integer retryAttempt = RetryAnalyzer.CURRENT_ATTEMPT.get();
        RetryAnalyzer.CURRENT_ATTEMPT.remove();
        if (retryAttempt != null && retryAttempt > 0) name += " (Retry " + retryAttempt + ")";

        ExtentTest test = ExtentManager.startTest(name); // null when Extent is disabled
        if (test == null) return;
        for (String group : method.getTestMethod().getGroups()) test.assignCategory(group);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod() || isApiTest(method)) return;
        ExtentTest test = ExtentManager.getTest();
        if (test != null) { finalizeStatus(test, testResult); assignRuntimeCategory(test); }
        ExtentManager.endTest();
    }

    private static void finalizeStatus(ExtentTest test, ITestResult testResult) {
        switch (testResult.getStatus()) {
            case ITestResult.SUCCESS -> test.pass("Test passed.");
            case ITestResult.FAILURE -> { if (testResult.getThrowable() != null) test.fail(testResult.getThrowable()); else test.fail("Test failed."); }
            case ITestResult.SKIP -> test.skip("Test skipped.");
            default -> { }
        }
    }

    /** Reads the live driver's capabilities, not ConfigManager's mobile.platform/device.name - those are thread-local overrides ConfigParameterListener already clears before the @Test itself runs. */
    private static void assignRuntimeCategory(ExtentTest test) {
        if (WebDriverManager.isDriverActive()) {
            test.assignCategory(ConfigManager.getBrowser());
        } else if (MobileDriverManager.isDriverActive()) {
            var capabilities = MobileDriverManager.getDriver().getCapabilities();
            test.assignCategory(String.valueOf(capabilities.getPlatformName()));
            Object deviceName = capabilities.getCapability("deviceName");
            if (deviceName != null) test.assignCategory(String.valueOf(deviceName));
        }
    }

    @Override
    public void onFinish(ITestContext context) { ExtentManager.flush(); }

    /** API tests get their own separate report entirely - excluded from Extent, not just left detail-free. */
    private static boolean isApiTest(IInvokedMethod method) {
        for (String group : method.getTestMethod().getGroups()) if ("api".equals(group)) return true;
        return false;
    }
}
```

### 8. ScreenshotCaptureListener — failure screenshot + diagnostics, registered after #6

**Same-hook-type ordering matters here** (confirmed empirically, not assumed): TestNG invokes
multiple `afterInvocation` listeners of the same hook type in reverse registration order.
`DriverCleanupListener` (#6) must be listed *before* this one so its `afterInvocation` fires
*after* this one — otherwise the driver is already quit by the time this checks
`isDriverActive()`.

```java
package com.framework.listeners;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.driver.MobileDriverManager;
import com.framework.driver.WebDriverManager;
import com.framework.enums.ScreenshotMode;
import com.framework.reporting.AllureManager;
import com.framework.reporting.ReportManager;
import com.framework.secrets.SensitiveDataMasker;
import com.framework.utils.ScreenshotUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import java.nio.file.Path;

public class ScreenshotCaptureListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod() || testResult.getStatus() != ITestResult.FAILURE) return;
        ScreenshotMode mode = ScreenshotMode.fromString(ConfigManager.getString(ConfigKeys.SCREENSHOT_MODE, "FAILURE"));
        if (mode == ScreenshotMode.DISABLED) return;
        Path screenshot = null;
        if (WebDriverManager.isDriverActive()) {
            WebDriver driver = WebDriverManager.getDriver();
            screenshot = ScreenshotUtils.capture(driver, testResult.getName());
            attachWebFailureDetail(driver);
        } else if (MobileDriverManager.isDriverActive()) {
            AppiumDriver driver = MobileDriverManager.getDriver();
            screenshot = ScreenshotUtils.capture(driver, testResult.getName());
            attachMobileFailureDetail(driver);
        }
        ReportManager.attachScreenshot(screenshot, testResult.getName());
    }

    private static void attachWebFailureDetail(WebDriver driver) {
        AllureManager.attachParameter("Current URL", SensitiveDataMasker.mask(driver.getCurrentUrl()));
        if (driver instanceof HasCapabilities hasCapabilities) {
            var capabilities = hasCapabilities.getCapabilities();
            AllureManager.attachParameter("Browser", String.valueOf(capabilities.getBrowserName()));
            AllureManager.attachParameter("Browser Version", String.valueOf(capabilities.getBrowserVersion()));
        }
        AllureManager.attachText("Page Source", SensitiveDataMasker.mask(driver.getPageSource()));
    }

    private static void attachMobileFailureDetail(AppiumDriver driver) {
        var capabilities = driver.getCapabilities();
        AllureManager.attachParameter("Device Name", String.valueOf(capabilities.getCapability("deviceName")));
        AllureManager.attachParameter("Platform", String.valueOf(capabilities.getPlatformName()));
        AllureManager.attachParameter("Platform Version", String.valueOf(capabilities.getCapability("platformVersion")));
        if (driver instanceof AndroidDriver androidDriver) {
            try { AllureManager.attachParameter("Current Activity", androidDriver.currentActivity()); }
            catch (RuntimeException ignored) { /* best-effort - not every device/app state supports this */ }
        }
        AllureManager.attachText("Page Source", SensitiveDataMasker.mask(driver.getPageSource()));
    }
}
```

### 9. AllureParameterMaskingListener — masks @DataProvider row values before disk write

Closes a real gap: `allure-testng` records every `@DataProvider` row as an Allure "Parameters"
entry using that row object's own `toString()`, completely bypassing every masking call site
this framework controls.

```java
package com.framework.listeners;

import com.framework.secrets.SensitiveDataMasker;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Uses IInvokedMethodListener.afterInvocation, not ITestListener - allure-testng writes the
 * result JSON from ITestListener.onTestSuccess/onTestFailure/onTestSkipped, which fire only
 * AFTER every registered IInvokedMethodListener.afterInvocation has already run (a
 * phase-ordering guarantee independent of which jar registered which listener). Masking here
 * is therefore guaranteed to land before allure-testng ever serializes parameters to disk.
 */
public class AllureParameterMaskingListener implements IInvokedMethodListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureParameterMaskingListener.class);

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;
        try {
            Allure.getLifecycle().updateTestCase(allureResult ->
                    allureResult.getParameters().forEach(parameter -> parameter.setValue(SensitiveDataMasker.mask(parameter.getValue()))));
        } catch (RuntimeException e) {
            LOGGER.warn("Failed to mask Allure parameters for '{}': {}", testResult.getName(), e.getMessage());
        }
    }
}
```

### 10. AllureMetadataListener — Feature/Story/Severity/Platform labels + environment.properties

```java
package com.framework.listeners;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.reporting.ReportManager;
import com.framework.utils.TextUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.SeverityLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class AllureMetadataListener implements ISuiteListener, IInvokedMethodListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureMetadataListener.class);
    private static final AtomicBoolean ENVIRONMENT_WRITTEN = new AtomicBoolean(false);
    private static final List<String> SURFACES = List.of("web", "mobile", "api");
    private static final List<String> RESOURCES = List.of("auth", "events", "bookings", "system");

    @Override
    public void onStart(ISuite suite) {
        if (!ReportManager.isAllureEnabled() || !ENVIRONMENT_WRITTEN.compareAndSet(false, true)) return;
        writeEnvironmentProperties();
    }

    @Override
    public void onFinish(ISuite suite) { /* environment.properties is written once, at start */ }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod() || !ReportManager.isAllureEnabled()) return;
        List<String> groups = Arrays.asList(method.getTestMethod().getGroups());
        if (groups.contains("api")) return; // API tests get their own separate report instead

        Allure.feature(featureFor(groups));
        Allure.story(TextUtils.humanize(method.getTestMethod().getMethodName()));
        Allure.label("severity", severityFor(groups).value());
        surfaceOf(groups).ifPresent(surface -> Allure.label("platform", surface));
    }

    private static String featureFor(List<String> groups) {
        return RESOURCES.stream().filter(groups::contains).findFirst().or(() -> surfaceOf(groups))
                .map(AllureMetadataListener::capitalize).orElse("General");
    }

    private static SeverityLevel severityFor(List<String> groups) {
        if (groups.contains("sanity")) return SeverityLevel.BLOCKER;
        if (groups.contains("smoke") || groups.contains("e2e")) return SeverityLevel.CRITICAL;
        return SeverityLevel.NORMAL;
    }

    private static java.util.Optional<String> surfaceOf(List<String> groups) { return SURFACES.stream().filter(groups::contains).findFirst(); }
    private static String capitalize(String value) { return value.isEmpty() ? value : Character.toUpperCase(value.charAt(0)) + value.substring(1); }

    private static void writeEnvironmentProperties() {
        Properties properties = new Properties();
        properties.setProperty("Environment", ConfigManager.getEnvironment().name());
        properties.setProperty("Base URL", ConfigManager.getString(ConfigKeys.BASE_URL, ""));
        properties.setProperty("API Base URL", ConfigManager.getApiBaseUrl());
        properties.setProperty("Browser", ConfigManager.getBrowser());
        properties.setProperty("Headless", String.valueOf(ConfigManager.isHeadless()));
        properties.setProperty("Mobile Platform", ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM, ""));
        properties.setProperty("Mobile Device Provider", ConfigManager.getString(ConfigKeys.MOBILE_DEVICE_PROVIDER, ""));

        Path path = Path.of("allure-results", "environment.properties");
        try {
            Files.createDirectories(path.getParent());
            try (OutputStream out = Files.newOutputStream(path)) { properties.store(out, "Generated once per run by AllureMetadataListener - do not edit by hand."); }
        } catch (IOException e) { LOGGER.warn("Failed to write Allure environment.properties: {}", e.getMessage()); }
    }
}
```

### 11. ApiTestReportListener — starts/finalizes the API surface's own report

"Is this an API test?" is decided by the `"api"` TestNG group — not by base-class
inheritance. Every method across every API test class carries this group, and unlike a
base-class check, this needs no compile-time dependency from this class (which lives in
`src/main`, compiled before `src/test`) on the application's own test-scoped base classes.

```java
package com.framework.listeners;

import com.framework.reporting.ApiReportRecorder;
import com.framework.utils.TextUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiTestReportListener implements ITestListener, ISuiteListener {

    private static final String API_GROUP = "api";

    private static final Map<String, String> MODULE_NAMES = Map.of(
            "auth", "Authentication", "bookings", "Bookings", "events", "Events",
            "system", "Health & Config", "e2e", "End-to-End");
    private static final Set<String> NON_MODULE_GROUPS = Set.of(API_GROUP, "smoke", "regression", "sanity", "positive", "negative");

    @Override
    public void onTestStart(ITestResult result) {
        if (!isApiTest(result)) return;
        List<String> groups = List.of(result.getMethod().getGroups());
        String description = result.getMethod().getDescription();
        String name = result.getTestClass().getRealClass().getSimpleName() + " — " + TextUtils.humanize(result.getMethod().getMethodName());
        ApiReportRecorder.startTest(name, description == null ? "" : description, groups, moduleFor(groups));
    }

    @Override
    public void onTestSuccess(ITestResult result) { if (isApiTest(result)) ApiReportRecorder.finishTest(true, false, null); }

    @Override
    public void onTestFailure(ITestResult result) {
        if (isApiTest(result)) {
            Throwable t = result.getThrowable();
            ApiReportRecorder.finishTest(false, false, t == null ? "Test failed" : String.valueOf(t.getMessage()));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (!isApiTest(result)) return;
        // A test can be skipped before onTestStart ever ran (e.g. a failed @BeforeClass skips
        // every @Test in the class without TestNG ever calling onTestStart) - start a record
        // now so it still shows up on the report.
        if (!ApiReportRecorder.hasActiveTest()) onTestStart(result);
        Throwable t = result.getThrowable();
        ApiReportRecorder.finishTest(false, true, t == null ? "Skipped" : t.getMessage());
    }

    @Override
    public void onFinish(ITestContext context) { /* no-op: flushed once per suite in onFinish(ISuite), not per <test> */ }

    @Override
    public void onFinish(ISuite suite) { ApiReportRecorder.flush(); }

    private static boolean isApiTest(ITestResult result) {
        for (String group : result.getMethod().getGroups()) if (API_GROUP.equals(group)) return true;
        return false;
    }

    private static String moduleFor(List<String> groups) {
        for (String group : groups) { String mapped = MODULE_NAMES.get(group); if (mapped != null) return mapped; }
        for (String group : groups) if (!NON_MODULE_GROUPS.contains(group)) return Character.toUpperCase(group.charAt(0)) + group.substring(1);
        return "Other";
    }
}
```

### 12. BeforeMethodAlwaysRunListener — fails the suite at start if a footgun is present

Converts a documented TestNG footgun into a loud, immediate failure: a `@BeforeMethod` with no
`groups()` of its own is silently skipped by TestNG whenever `-Dgroups=...` is active, unless
it also sets `alwaysRun = true` — the `@Test` it was meant to set up then simply runs
unset-up, with nothing in the console or either report pointing at the real cause.

```java
package com.framework.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class BeforeMethodAlwaysRunListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        Set<Class<?>> participatingTestClasses = new LinkedHashSet<>();
        for (ITestNGMethod method : suite.getAllMethods()) participatingTestClasses.add(method.getRealClass());

        Set<String> offenders = new TreeSet<>();
        for (Class<?> testClass : participatingTestClasses) collectOffenders(testClass, offenders);

        if (!offenders.isEmpty()) {
            throw new IllegalStateException(
                    "Refusing to start '" + suite.getName() + "': found @BeforeMethod method(s) with no "
                            + "groups() of their own and alwaysRun=false: " + offenders + ". TestNG silently "
                            + "skips these whenever -Dgroups=... is active, letting the @Test it was meant to "
                            + "set up run unset-up. Fix: add alwaysRun = true to each one listed above.");
        }
    }

    @Override
    public void onFinish(ISuite suite) { /* the check only needs to happen once, before start */ }

    /** Walks testClass's own inheritance chain (excluding Object) for offending @BeforeMethods - catches one inherited from a base class, not just declared directly. */
    private void collectOffenders(Class<?> testClass, Set<String> offenders) {
        for (Class<?> current = testClass; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                BeforeMethod annotation = method.getAnnotation(BeforeMethod.class);
                if (annotation != null && annotation.groups().length == 0 && !annotation.alwaysRun())
                    offenders.add(current.getName() + "#" + method.getName());
            }
        }
    }
}
```

## 19. Resources — logback.xml & listener registration

### src/main/resources/META-INF/services/org.testng.ITestNGListener

`java.util.ServiceLoader` discovery — TestNG auto-registers every listener listed here with no
suite XML or `@Listeners` annotation anywhere. **Order is significant** — see
[§18](#18-package-listeners) for exactly why each position matters:

```
com.framework.listeners.TestLoggingContextListener
com.framework.listeners.RetryAnalyzerTransformer
com.framework.listeners.ConfigurationRetryListener
com.framework.listeners.ConfigParameterListener
com.framework.listeners.ApiContextListener
com.framework.listeners.DriverCleanupListener
com.framework.listeners.ExtentReportingListener
com.framework.listeners.ScreenshotCaptureListener
com.framework.listeners.AllureParameterMaskingListener
com.framework.listeners.AllureMetadataListener
com.framework.listeners.ApiTestReportListener
com.framework.listeners.BeforeMethodAlwaysRunListener
```

### src/main/resources/logback.xml

Without this file, SLF4J/Logback falls back to a zero-config `BasicConfigurator`
(console-only, root DEBUG, no file appender, no rotation, third-party libraries logging at
whatever level they feel like into the same stream). `[%X{test}]` carries the currently-
invoking method's `"ClassName.methodName"`, set by `TestLoggingContextListener`. `%maskedMsg`
(the `MaskingMessageConverter` conversion word) replaces the standard `%msg` — every line
through CONSOLE/FILE is masked unconditionally.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <conversionRule conversionWord="maskedMsg" class="com.framework.secrets.MaskingMessageConverter"/>

    <property name="LOG_DIR" value="logs"/>
    <property name="LOG_PATTERN" value="%d{HH:mm:ss.SSS} [%thread] [%X{test}] %-5level %logger{36} - %maskedMsg%n"/>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder><pattern>${LOG_PATTERN}</pattern></encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_DIR}/framework.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>${LOG_DIR}/framework-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>50MB</maxFileSize>
            <maxHistory>14</maxHistory>
            <totalSizeCap>500MB</totalSizeCap>
        </rollingPolicy>
        <encoder><pattern>${LOG_PATTERN}</pattern></encoder>
    </appender>

    <!-- Mirrors log events into the current Extent test node. No encoder/pattern - it logs
         into Extent's own model, not text output. -->
    <appender name="EXTENT" class="com.framework.reporting.ExtentLoggingAppender"/>

    <!-- Mirrors the same business-narrative log events into Allure as steps, when Allure is enabled. -->
    <appender name="ALLURE_STEPS" class="com.framework.reporting.AllureLoggingAppender"/>

    <!-- Framework/test code: DEBUG is the point of "detailed action-level logging". Not
         attached to EXTENT here (except the application-layer loggers below) - only the
         curated business-narrative loggers are, so the report reads as "what the test did"
         (one line per real action, in plain language) rather than duplicating every low-level
         Selenium/Appium interaction underneath it. -->
    <logger name="com.framework" level="DEBUG"/>
    <logger name="com.tests" level="DEBUG"/>

    <!-- com.framework.api (ApiClient) deliberately gets NO EXTENT appender-ref here (unlike the
         application-layer loggers below): Extent's Spark theme places a log message inside a
         plain HTML <td> with no white-space:pre anywhere in its bundled CSS, so even
         pretty-printed, multi-line JSON collapses to one squished line in a real browser.
         ApiClient calls ExtentManager.logCodeBlock(...) directly instead, which renders into a
         <textarea readonly> - whitespace-preserving natively, no CSS/JS dependency. -->

    <!-- The business-narrative layer, per surface - application Page Objects/Components/
         Services (adjust these three package prefixes to match your own src/test tree). Each
         already logs one line per real action in plain language, at their common parent
         package (logback's hierarchy covers every platform-specific leaf package beneath it). -->
    <logger name="com.tests.application.pages" level="DEBUG">
        <appender-ref ref="EXTENT"/>
        <appender-ref ref="ALLURE_STEPS"/>
    </logger>
    <logger name="com.tests.application.components" level="DEBUG">
        <appender-ref ref="EXTENT"/>
        <appender-ref ref="ALLURE_STEPS"/>
    </logger>
    <logger name="com.tests.application.services" level="DEBUG">
        <appender-ref ref="EXTENT"/>
        <appender-ref ref="ALLURE_STEPS"/>
    </logger>

    <!-- Third-party libraries: default verbosity is connection/protocol-level noise unrelated
         to what a test author needs to diagnose a failure. -->
    <logger name="org.apache.http" level="WARN"/>
    <logger name="org.apache.hc" level="WARN"/>
    <logger name="org.openqa.selenium" level="WARN"/>
    <logger name="io.appium" level="WARN"/>
    <logger name="io.netty" level="WARN"/>
    <logger name="org.testng" level="WARN"/>
    <logger name="org.apache.poi" level="WARN"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>
```

**Pitfall to avoid:** if the application-layer logger names (`com.tests.application.pages`
etc.) ever drift from where the actual code lives (a package restructure), this file's
`<logger name="...">` entries silently stop matching anything and the business-narrative
layer stops reaching Extent/Allure with no error anywhere — only the bare test pass/fail node
shows, with nothing underneath it. Keep these three logger names in sync with the real
package structure whenever it changes.

---

## 20. Config files (repo root)

`config/` lives at the **repo root**, not `src/main/resources` (easy for a tester to find and
edit directly) — `pom.xml`'s `<resources>` block places it on the classpath at `config/...`
(see [§3](#3-maven-setup-pomxml)).

### config/qa.properties (the default environment)

```properties
browser=chrome
headless=false
resolution=maximize
# resolution: maximize | <width>x<height>

page.load.timeout=30
script.timeout=30
implicit.wait.timeout=0
explicit.wait.timeout=15
polling.interval=500
# Driver timeouts above are in seconds.

screenshot.mode=FAILURE
# screenshot.mode: FAILURE | EVERY_ACTION | DISABLED

api.connection.timeout=10000
api.socket.timeout=10000
# API timeouts above are in milliseconds.

retry.max.count=1

report.overwrite=true
report.types=extent
# report.types: comma-separated subset of extent | allure

testdata.format=json
# testdata.format: json | yaml | csv | excel

mobile.device.provider=LOCAL
# mobile.device.provider: LOCAL | BROWSERSTACK
appium.command.timeout=60
appium.server.url=http://127.0.0.1:4723/wd/hub

mobile.platform=android

mobile.app.path.android=apps/<your-app>-release.apk
mobile.app.path.ios=apps/<your-app>-simulator.app

base.url=https://your-app-under-test.example.com
api.base.url=https://api.your-app-under-test.example.com/api
```

### config/dev.properties

Identical shape to `qa.properties` — every `config/{env}.properties` file is fully
self-contained, no shared `default.properties` layer. Point `base.url`/`api.base.url` (and
anything else that genuinely differs) at the dev environment.

### config/mobile-devices.json

```json
{
  "devices": {
    "android1": { "platform": "android", "deviceName": "Pixel_6a", "platformVersion": "16" },
    "ios1": { "platform": "ios", "deviceName": "iPhone 17 Pro", "platformVersion": "26.2" },
    "ios2": { "platform": "ios", "deviceName": "iPhone 17", "platformVersion": "26.2" }
  },

  "androidList": ["android1"],
  "iosList": ["ios1", "ios2"],

  "matrices": {
    "android": ["android1"],
    "ios": ["ios1", "ios2"]
  },

  "ports": {
    "systemPort": { "start": 8200, "count": 50 },
    "wdaLocalPort": { "start": 8100, "count": 50 },
    "chromedriverPort": { "start": 9515, "count": 50 }
  }
}
```

### .secret.env.example (template — copy to .secret.env, git-ignored, never commit it)

```
# Copy this file to .secret.env (already in .gitignore) and fill in real values.
# Never commit .secret.env - CI/CD should supply these as real environment variables
# instead, which SecretManager checks first.

LOGIN_USERNAME=testuser
LOGIN_PASSWORD=secretPassword
<YOUR_APP>_EMAIL=your-account@example.com
<YOUR_APP>_PASSWORD=your-password

# Only needed if mobile.device.provider=BROWSERSTACK (default is LOCAL).
BROWSERSTACK_USERNAME=your-browserstack-username
BROWSERSTACK_ACCESS_KEY=your-browserstack-access-key
```

### .gitignore (the entries this framework specifically depends on)

```
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

.DS_Store

### Framework: secrets - never commit credentials ###
.secret.env

### Framework: generated at runtime ###
/logs/
/test-output/
/reports/
/allure-results/
/allure-report/
```

### scripts/clean-local.sh

Removes accumulated local run artifacts — all git-ignored and regenerated by the next
`mvn test`, so safe to run anytime.

```bash
#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

RUN_ARTIFACT_DIRS=(allure-results allure-report test-output logs reports)

for dir in "${RUN_ARTIFACT_DIRS[@]}"; do
    if [ -d "$dir" ]; then
        rm -rf "$dir"
        echo "Removed $dir/"
    fi
done

if [[ "${1:-}" == "--all" ]]; then
    echo "Running mvn clean (removes target/)..."
    mvn -q clean
fi

echo "Done."
```

## 21. Test-code layer (src/test) — conventions & full examples

Everything in `src/test/java/com/tests/` is application-specific — it only makes sense
because a particular app is under test. Two top-level packages, nothing else:

- **`tests/`** — every `*Test.java` spec, and *only* specs. A tester opening this package sees
  nothing but tests to read/write.
- **`application/`** — everything a spec needs to run: `base/` (shared lifecycle),
  `pages/{web,mobile}/`, `components/{web,mobile}/`, `requests/`, `responses/`, `services/`,
  `testdata/`.

**Rule: test code calls only business-level Page Object/Service methods — never raw
Selenium/Appium/REST Assured.** Retry, logging, reporting, and driver cleanup are automatic.

> **Every new `@BeforeMethod` needs `alwaysRun = true`.**
> `BeforeMethodAlwaysRunListener` fails the suite at start-of-run with the exact
> `Class#method` if one is missing it — see [§18, item 12](#18-package-listeners).

### base/ — shared per-surface @BeforeMethod/@AfterMethod lifecycle

**BaseWebTest** — every Web spec extends this instead of hand-writing its own cleanup:

```java
package com.tests.application.base;

import com.framework.config.ConfigManager;
import com.framework.secrets.SecretManager;
import com.tests.application.pages.web.HomePage;
import com.tests.application.pages.web.LoginPage;
import org.testng.annotations.AfterMethod;
import static com.framework.utils.Verify.assertTrue;

public abstract class BaseWebTest {

    @AfterMethod(alwaysRun = true)
    public final void baseWebCleanup() { ConfigManager.clearThreadState(); }

    /** Opens the login page and signs in with the shared seeded account. */
    protected HomePage loginWithSeededAccount() {
        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigManager.getBaseUrl())
                .enterEmail(SecretManager.get("YOUR_APP_EMAIL"))
                .enterPassword(SecretManager.get("YOUR_APP_PASSWORD"))
                .clickLogin();

        HomePage homePage = new HomePage();
        assertTrue(homePage.isDisplayed(), "Login should complete before proceeding.");
        return homePage;
    }
}
```

**BaseMobileTest**:

```java
package com.tests.application.base;

import com.framework.config.ConfigManager;
import com.framework.driver.MobileDriverManager;
import com.framework.mobile.MobileUtils;
import com.framework.secrets.SecretManager;
import com.tests.application.pages.mobile.HomePage;
import com.tests.application.pages.mobile.LoginPage;
import org.testng.annotations.AfterMethod;

public abstract class BaseMobileTest {

    @AfterMethod(alwaysRun = true)
    public final void baseMobileCleanup() { tearDownTestData(); ConfigManager.clearThreadState(); }

    /** Override to release test data created during the test. No-op by default. */
    protected void tearDownTestData() {}

    /** Acquires the driver, dismisses any system dialogs, and logs in with the shared seeded account if not already logged in. */
    protected HomePage ensureLoggedIn() {
        MobileDriverManager.getDriver();
        MobileUtils.dismissSystemDialogsIfPresent();
        return new LoginPage().loginIfNeeded(SecretManager.get("YOUR_APP_EMAIL"), SecretManager.get("YOUR_APP_PASSWORD"));
    }

    /** Acquires the driver, dismisses any system dialogs, and logs out if currently logged in. */
    protected void ensureLoggedOut() {
        MobileDriverManager.getDriver();
        MobileUtils.dismissSystemDialogsIfPresent();
        new HomePage().logoutIfLoggedIn();
    }
}
```

**BaseApiTest**:

```java
package com.tests.application.base;

import com.framework.secrets.SecretManager;
import com.tests.application.services.AuthenticationService;
import org.testng.annotations.AfterMethod;

public abstract class BaseApiTest {

    protected final AuthenticationService authService = new AuthenticationService();

    @AfterMethod(alwaysRun = true)
    public final void baseApiCleanup() { tearDownTestData(); authService.logout(); }

    /** Override to release test data (a created booking/event, etc.) while still authenticated - runs before logout(). No-op by default. */
    protected void tearDownTestData() {}

    /** Logs in as the shared seeded account. */
    protected void loginWithSeededAccount() {
        authService.login(SecretManager.get("YOUR_APP_EMAIL"), SecretManager.get("YOUR_APP_PASSWORD"));
    }
}
```

**Convention across all three base classes:** a subclass never writes its own
`@AfterMethod` for test-data cleanup — it overrides `tearDownTestData()`, and the base class
guarantees the ordering (test data cleanup happens *before* logout/driver teardown, while
still authenticated/logged in).

### testdata/TestDataSurface — one enum value per surface

```java
package com.tests.application.testdata;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.enums.MobilePlatformType;
import com.framework.testdata.TestCaseRecord;
import com.framework.testdata.TestDataManager;

public enum TestDataSurface {

    WEB("web/web"),
    API("api/api"),
    MOBILE_ANDROID("android/android"),
    MOBILE_IOS("ios/ios");

    private final String fileName;
    TestDataSurface(String fileName) { this.fileName = fileName; }

    public String fileName() { return fileName; }

    public <D, T extends TestCaseRecord<D>> D getCaseData(String caseName, Class<T> caseType) {
        return TestDataManager.getCaseData(fileName, caseName, caseType);
    }

    /** Resolves the same mobile.platform value DriverFactory used when the active driver was created, so a mobile test never has to know/hardcode which platform this run is actually driving. */
    public static TestDataSurface currentMobile() {
        MobilePlatformType platform = MobilePlatformType.fromString(ConfigManager.getString(ConfigKeys.MOBILE_PLATFORM));
        return platform == MobilePlatformType.ANDROID ? MOBILE_ANDROID : MOBILE_IOS;
    }
}
```

**A shared test-case shape across surfaces** (login's `email`/`password` shape is identical
for Web/Android/iOS, so one record instead of near-duplicate `WebLoginTestCase`/
`MobileLoginTestCase` types):

```java
package com.tests.application.testdata;

import com.framework.testdata.TestCaseMetadata;
import com.framework.testdata.TestCaseRecord;

public record LoginTestCase(TestCaseMetadata metadata, LoginData data) implements TestCaseRecord<LoginTestCase.LoginData> {
    public record LoginData(String email, String password) {}
}
```

---

### Web vertical slice — LoginPage, HomePage, HeaderComponent, EventsPage, EventCardComponent, tests

**Page Object** (extends `BasePage`, exposes business-level actions only):

```java
package com.tests.application.pages.web;

import com.framework.secrets.SensitiveDataMasker;
import com.framework.web.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-btn");
    private static final By ERROR_TOAST_MESSAGE = By.cssSelector("[aria-live='polite'] p");

    public LoginPage open(String baseUrl) {
        String url = baseUrl + "/login";
        navigateTo(url);
        logger.info("Navigated to {}", url);
        return this;
    }

    public LoginPage enterEmail(String email) {
        type(EMAIL_INPUT, email);
        logger.info("Entered email: {}", SensitiveDataMasker.mask(email));
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD_INPUT, password);
        logger.info("Entered password: {}", SensitiveDataMasker.mask(password));
        return this;
    }

    /** Deliberately returns void, not the next page: a failed login doesn't navigate anywhere, so assuming success here would hand back a page object for a page that never loaded. */
    public void clickLogin() {
        click(LOGIN_BUTTON);
        logger.info("Clicked Sign In button");
    }

    public String getErrorMessage() {
        String message = getText(ERROR_TOAST_MESSAGE);
        logger.info("Error message displayed: '{}'", message);
        return message;
    }

    public boolean isErrorDisplayed() { return isDisplayed(ERROR_TOAST_MESSAGE); }
}
```

```java
package com.tests.application.pages.web;

import com.framework.web.BasePage;
import com.tests.application.components.web.HeaderComponent;

public class HomePage extends BasePage {

    /** Deliberately not cached as a field: re-located fresh on every call so a client-side re-render of the nav never leaves a stale root behind. */
    public HeaderComponent header() { return new HeaderComponent(); }

    public boolean isDisplayed() {
        boolean displayed = header().isLoggedIn();
        logger.info("Home page displayed (logged-in nav present): {}", displayed);
        return displayed;
    }
}
```

**Component** (page-wide singleton — locates its own root):

```java
package com.tests.application.components.web;

import com.framework.secrets.SensitiveDataMasker;
import com.framework.web.BaseComponent;
import org.openqa.selenium.By;

public class HeaderComponent extends BaseComponent {

    private static final By HOME_LINK = By.id("nav-home");
    private static final By EVENTS_LINK = By.id("nav-events");
    private static final By BOOKINGS_LINK = By.id("nav-bookings");
    private static final By USER_EMAIL_DISPLAY = By.id("user-email-display");
    private static final By LOGOUT_BUTTON = By.id("logout-btn");

    public HeaderComponent() { super(By.cssSelector("nav")); }

    public void goToEvents() { click(EVENTS_LINK); logger.info("Clicked 'Events' in the nav"); }
    public void goToBookings() { click(BOOKINGS_LINK); logger.info("Clicked 'My Bookings' in the nav"); }
    public void goToHome() { click(HOME_LINK); logger.info("Clicked 'Home' in the nav"); }
    public void logout() { click(LOGOUT_BUTTON); logger.info("Logged out"); }

    public String getLoggedInUserEmail() {
        String email = textOf(USER_EMAIL_DISPLAY);
        logger.info("Logged-in user email shown in nav: {}", SensitiveDataMasker.mask(email));
        return email;
    }

    public boolean isLoggedIn() {
        try { return !find(USER_EMAIL_DISPLAY).getText().isBlank(); }
        catch (com.framework.exceptions.ElementInteractionException e) { return false; }
    }
}
```

**Component** (N-repeated — takes an already-located root, so N cards never collide):

```java
package com.tests.application.components.web;

import com.framework.web.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EventCardComponent extends BaseComponent {

    private static final By NAME = By.tagName("h3");
    private static final By PRICE = By.cssSelector(".text-indigo-700");
    private static final By SEATS_LEFT = By.cssSelector(".text-amber-600");
    private static final By BOOK_NOW_BUTTON = By.cssSelector("[data-testid='book-now-btn']");

    public EventCardComponent(WebElement root) { super(root); }

    public String getName() { return textOf(NAME); }
    public String getPrice() { return textOf(PRICE); }
    public String getSeatsLeftText() { return textOf(SEATS_LEFT); }

    public void clickBookNow() {
        // Capture the name before clicking: navigation invalidates this card's root.
        String name = getName();
        click(BOOK_NOW_BUTTON);
        logger.info("Clicked 'Book Now' on '{}'", name);
    }
}
```

**Page that enumerates N components**:

```java
package com.tests.application.pages.web;

import com.framework.web.BasePage;
import com.framework.web.WebActions;
import com.framework.web.WebWaits;
import com.tests.application.components.web.EventCardComponent;
import com.tests.application.components.web.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class EventsPage extends BasePage {

    private static final By EVENT_CARDS = By.cssSelector("[data-testid='event-card']");

    public HeaderComponent header() { return new HeaderComponent(); }

    public List<EventCardComponent> getEventCards() {
        WebWaits.waitForVisible(EVENT_CARDS);
        List<WebElement> roots = WebActions.findAll(EVENT_CARDS);
        List<EventCardComponent> cards = roots.stream().map(EventCardComponent::new).toList();
        logger.info("Events listing shows {} event(s)", cards.size());
        return cards;
    }
}
```

**Test class** — extends `BaseWebTest`, pulls data via `TestDataSurface`, asserts via
`Verify`:

```java
package com.tests.tests.web;

import com.framework.config.ConfigManager;
import com.tests.application.base.BaseWebTest;
import com.tests.application.testdata.LoginTestCase.LoginData;
import com.tests.application.testdata.LoginTestCase;
import com.tests.application.testdata.TestDataSurface;
import com.tests.application.pages.web.HomePage;
import com.tests.application.pages.web.LoginPage;
import org.testng.annotations.Test;
import static com.framework.utils.Verify.assertTrue;

public class LoginTest extends BaseWebTest {

    @Test(groups = {"smoke", "sanity", "web", "auth", "positive"})
    public void validLoginNavigatesToHomePage() {
        LoginData data = TestDataSurface.WEB.getCaseData("validLogin", LoginTestCase.class);

        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigManager.getBaseUrl())
                .enterEmail(data.email())
                .enterPassword(data.password())
                .clickLogin();

        HomePage homePage = new HomePage();
        assertTrue(homePage.isDisplayed(), "Home page should show the logged-in nav after a valid login.");
    }

    @Test(groups = {"smoke", "web", "auth", "negative"})
    public void invalidLoginShowsErrorAndStaysOnLoginPage() {
        LoginData data = TestDataSurface.WEB.getCaseData("invalidLogin", LoginTestCase.class);

        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigManager.getBaseUrl())
                .enterEmail(data.email())
                .enterPassword(data.password())
                .clickLogin();

        assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed for a wrong password.");
        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("invalid"),
                "Error message should mention 'invalid' credentials, was: " + loginPage.getErrorMessage());
    }
}
```

```java
package com.tests.tests.web;

import com.framework.config.ConfigManager;
import com.framework.secrets.SecretManager;
import com.framework.web.WebUtils;
import com.framework.web.WebWaits;
import com.tests.application.base.BaseWebTest;
import com.tests.application.components.web.EventCardComponent;
import com.tests.application.pages.web.EventsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static com.framework.utils.Verify.assertEquals;
import static com.framework.utils.Verify.assertTrue;

public class EventsTest extends BaseWebTest {

    private EventsPage eventsPage;

    @BeforeMethod(alwaysRun = true) // alwaysRun=true: see BeforeMethodAlwaysRunListener
    public void logInAndGoToEvents() {
        loginWithSeededAccount();
        WebUtils.navigateTo(ConfigManager.getBaseUrl() + "/events");
        eventsPage = new EventsPage();
    }

    @Test(groups = {"web", "events", "positive"})
    public void eventsListingShowsAtLeastOneEvent() {
        List<EventCardComponent> cards = eventsPage.getEventCards();
        assertTrue(cards.size() > 0, "Events page should list at least one event.");

        EventCardComponent first = cards.get(0);
        assertTrue(!first.getName().isBlank(), "First event card should display a non-blank name.");
        assertTrue(first.getPrice().startsWith("$"), "Price should be displayed as a dollar amount.");
    }

    @Test(groups = {"web", "events", "positive"})
    public void eachEventCardIsIndependentlyScoped() {
        // Proves BaseComponent's root-scoping: reading N cards' names never bleeds one card's
        // data into another's.
        List<EventCardComponent> cards = eventsPage.getEventCards();
        assertTrue(cards.size() >= 2, "Test needs at least 2 events to prove independent scoping.");
        List<String> names = cards.stream().map(EventCardComponent::getName).toList();
        assertEquals(names.stream().distinct().count(), names.size(), "Every event card should report its own distinct name.");
    }

    @Test(groups = {"web", "events", "positive"})
    public void headerShowsLoggedInUserAcrossPages() {
        assertTrue(eventsPage.header().isLoggedIn(), "Header should show a logged-in state on the events page.");
        assertEquals(eventsPage.header().getLoggedInUserEmail(), SecretManager.get("YOUR_APP_EMAIL"),
                "Header should display the email of the account that's logged in.");
    }
}
```

**Test data** — `src/test/resources/testdata/json/web/web.json`:

```json
{
  "validLogin": {
    "metadata": { "testCaseId": "TC-WEB-LOGIN-001", "testCaseName": "User logs in successfully with valid credentials and lands on the home page" },
    "data": { "email": "${{YOUR_APP_EMAIL}}", "password": "${{YOUR_APP_PASSWORD}}" }
  },
  "invalidLogin": {
    "metadata": { "testCaseId": "TC-WEB-LOGIN-002", "testCaseName": "User sees an invalid-credentials error when logging in with the wrong password" },
    "data": { "email": "${{YOUR_APP_EMAIL}}", "password": "DefinitelyTheWrongPassword1!" }
  }
}
```

---

### Mobile vertical slice — LoginPage, HomePage, MultiDeviceParallelTest

Locators use the app's own accessibility labels (`AppiumBy.accessibilityId`), which map to
`accessibilityId` on iOS and `content-desc` on Android for one shared semantics tree (e.g. a
Flutter app). Where a locator genuinely differs per platform, use `PlatformLocator.of(...)`.

```java
package com.tests.application.pages.mobile;

import com.framework.mobile.BaseMobilePage;
import com.framework.mobile.PlatformLocator;
import com.framework.secrets.SensitiveDataMasker;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginPage extends BaseMobilePage {

    private static final By EMAIL_INPUT = PlatformLocator.of(
            By.xpath("//android.widget.EditText[@hint='you@email.com']"),
            AppiumBy.accessibilityId("you@email.com"));
    private static final By PASSWORD_INPUT = PlatformLocator.of(
            By.xpath("//android.widget.EditText[@hint='••••••']"),
            AppiumBy.accessibilityId("••••••"));
    private static final By SIGN_IN_BUTTON = AppiumBy.accessibilityId("Sign In");
    private static final By EMAIL_REQUIRED_ERROR = AppiumBy.accessibilityId("Email is required");
    private static final By PASSWORD_REQUIRED_ERROR = AppiumBy.accessibilityId("Password is required");
    private static final By INVALID_EMAIL_ERROR = AppiumBy.accessibilityId("Enter a valid email address");

    public boolean isDisplayed() { return isDisplayed(SIGN_IN_BUTTON); }

    public LoginPage enterEmail(String email) {
        type(EMAIL_INPUT, email);
        logger.info("Entered email: {}", SensitiveDataMasker.mask(email));
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD_INPUT, password);
        logger.info("Entered password: {}", SensitiveDataMasker.mask(password));
        return this;
    }

    public void tapSignIn() { tap(SIGN_IN_BUTTON); logger.info("Tapped Sign In button"); }

    public boolean isEmailRequiredErrorDisplayed() { return isDisplayed(EMAIL_REQUIRED_ERROR); }
    public boolean isPasswordRequiredErrorDisplayed() { return isDisplayed(PASSWORD_REQUIRED_ERROR); }
    public boolean isInvalidEmailErrorDisplayed() { return isDisplayed(INVALID_EMAIL_ERROR); }

    /**
     * Logs in only if the login screen is actually showing; otherwise assumes a prior test in
     * this run already left the app signed in. Needed because the mobile driver capabilities
     * don't request fullReset, so app-level login state can persist across tests in the same run.
     */
    public HomePage loginIfNeeded(String email, String password) {
        if (!isDisplayed()) { logger.info("Already past the login screen - skipping login."); return new HomePage(); }
        enterEmail(email).enterPassword(password).tapSignIn();
        return new HomePage();
    }
}
```

```java
package com.tests.application.pages.mobile;

import com.framework.mobile.BaseMobilePage;
import com.tests.application.components.mobile.HeaderComponent;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePage extends BaseMobilePage {

    private static final By LOGOUT_BUTTON = AppiumBy.accessibilityId("Logout");
    private static final By BROWSE_EVENTS_BUTTON = AppiumBy.accessibilityId("Browse Events →");

    public HeaderComponent header() { return new HeaderComponent(); }

    public boolean isDisplayed() {
        boolean displayed = isDisplayed(LOGOUT_BUTTON);
        logger.info("Post-login header present: {}", displayed);
        return displayed;
    }

    public EventsPage browseEvents() { tap(BROWSE_EVENTS_BUTTON); logger.info("Tapped 'Browse Events'"); return new EventsPage(); }

    /** Logs out only if actually logged in; a no-op otherwise. */
    public void logoutIfLoggedIn() { if (isDisplayed()) header().logout(); }
}
```

**MultiDeviceParallelTest** — the device-matrix infra (app-agnostic, one class covers every
future matrix): each `@DataProvider(parallel = true)` row launches concurrently on its own
thread, overriding `mobile.platform`/`device.name`/`platform.version`/`app.path` via a
thread-local `ConfigManager.setOverride` before creating the driver.

```java
package com.tests.tests.mobile;

import com.framework.config.ConfigManager;
import com.framework.constants.ConfigKeys;
import com.framework.driver.MobileDeviceMatrix;
import com.framework.driver.MobileDeviceMatrix.Row;
import com.framework.driver.MobileDriverManager;
import com.tests.application.base.BaseMobileTest;
import io.appium.java_client.AppiumDriver;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static com.framework.utils.Verify.assertNotNull;

public class MultiDeviceParallelTest extends BaseMobileTest {

    @DataProvider(name = "androidDeviceMatrix", parallel = true)
    public Object[][] androidDeviceMatrix() { return MobileDeviceMatrix.dataProvider("android"); }

    @Test(groups = "mobile", dataProvider = "androidDeviceMatrix")
    public void appLaunchesOnEachAndroidDeviceConcurrently(Row device) { skipUnlessRequested("android"); launchOnDevice(device); }

    @DataProvider(name = "iosDeviceMatrix", parallel = true)
    public Object[][] iosDeviceMatrix() { return MobileDeviceMatrix.dataProvider("ios"); }

    @Test(groups = "mobile", dataProvider = "iosDeviceMatrix")
    public void appLaunchesOnEachIosSimulatorConcurrently(Row device) { skipUnlessRequested("ios"); launchOnDevice(device); }

    /** Skips this matrix method outright when -Dmobile.platform names the OTHER platform - a SkipException, not a failure, so the run still reports success. */
    private static void skipUnlessRequested(String thisMatrixPlatform) {
        String explicit = System.getProperty(ConfigKeys.MOBILE_PLATFORM);
        if (explicit != null && !explicit.trim().equalsIgnoreCase(thisMatrixPlatform))
            throw new SkipException("Skipped: -Dmobile.platform=" + explicit + " excludes the '" + thisMatrixPlatform + "' device matrix.");
    }

    private static void launchOnDevice(Row device) {
        ConfigManager.setOverride(ConfigKeys.MOBILE_DEVICE_PROVIDER, "LOCAL");
        ConfigManager.setOverride(ConfigKeys.MOBILE_PLATFORM, device.platform());
        ConfigManager.setOverride(ConfigKeys.MOBILE_DEVICE_NAME, device.deviceName());
        ConfigManager.setOverride(ConfigKeys.MOBILE_PLATFORM_VERSION, device.platformVersion());
        ConfigManager.setOverride(ConfigKeys.MOBILE_APP_PATH, device.appPath());
        ConfigManager.setOverride(ConfigKeys.MOBILE_HYBRID, String.valueOf(device.hybrid()));
        if (device.appiumServerUrl() != null) ConfigManager.setOverride(ConfigKeys.APPIUM_SERVER_URL, device.appiumServerUrl());

        AppiumDriver driver = MobileDriverManager.getDriver();
        assertNotNull(driver.getSessionId(), "A real Appium session should exist for " + device.deviceName());
    }
}
```

---

### API vertical slice — service, request/response DTOs, test-case data, tests

**Service** (wraps `ApiClient`, the only place REST Assured is called from):

```java
package com.tests.application.services;

import com.framework.api.ApiClient;
import com.framework.api.ApiRequest;
import com.framework.api.ApiResponse;
import com.tests.application.requests.AuthRequest;
import com.tests.application.responses.AuthResponse;
import com.tests.application.responses.MeResponse;
import com.framework.exceptions.ApiAuthenticationException;
import com.framework.secrets.SensitiveDataMasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthResponse register(String email, String password) {
        ApiResponse response = ApiClient.execute(ApiRequest.post("/auth/register").body(new AuthRequest(email, password)));
        if (response.statusCode() != 201)
            throw new ApiAuthenticationException("Registration failed for '" + SensitiveDataMasker.mask(email) + "': HTTP " + response.statusCode() + " - " + SensitiveDataMasker.mask(response.body()));
        AuthResponse auth = response.as(AuthResponse.class);
        ApiClient.setAuthToken(auth.token());
        LOGGER.info("Registered and authenticated as '{}'.", SensitiveDataMasker.mask(email));
        return auth;
    }

    public AuthResponse login(String email, String password) {
        ApiResponse response = ApiClient.execute(ApiRequest.post("/auth/login").body(new AuthRequest(email, password)));
        if (response.statusCode() != 200)
            throw new ApiAuthenticationException("Login failed for '" + SensitiveDataMasker.mask(email) + "': HTTP " + response.statusCode() + " - " + SensitiveDataMasker.mask(response.body()));
        AuthResponse auth = response.as(AuthResponse.class);
        ApiClient.setAuthToken(auth.token());
        LOGGER.info("Authenticated as '{}'.", SensitiveDataMasker.mask(email));
        return auth;
    }

    public MeResponse me() {
        if (!ApiClient.hasAuthToken()) throw new ApiAuthenticationException("No auth token set for this thread - call login()/register() first.");
        ApiResponse response = ApiClient.execute(ApiRequest.get("/auth/me"));
        if (response.statusCode() != 200)
            throw new ApiAuthenticationException("Token validation failed: HTTP " + response.statusCode() + " - " + SensitiveDataMasker.mask(response.body()));
        return response.as(MeResponse.class);
    }

    /** Clears this thread's stored token; a safe no-op when there is nothing to clear. */
    public void logout() { ApiClient.clearAuthToken(); }
}
```

```java
package com.tests.application.services;

import com.framework.api.ApiClient;
import com.framework.api.ApiRequest;
import com.framework.api.ApiResponse;
import com.tests.application.requests.CreateEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public final class EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    public ApiResponse createEvent(CreateEventRequest request) {
        ApiResponse response = ApiClient.execute(ApiRequest.post("/events").body(request));
        LOGGER.info("Created event '{}' (status {})", request.title(), response.statusCode());
        return response;
    }

    public ApiResponse getEvent(int eventId) {
        ApiResponse response = ApiClient.execute(ApiRequest.get("/events/{id}").pathParam("id", eventId));
        LOGGER.info("Fetched event {} (status {})", eventId, response.statusCode());
        return response;
    }

    public ApiResponse listEvents(Map<String, Object> filters) {
        ApiRequest request = ApiRequest.get("/events");
        filters.forEach(request::queryParam);
        ApiResponse response = ApiClient.execute(request);
        LOGGER.info("Listed events with filters {} (status {})", filters, response.statusCode());
        return response;
    }

    public ApiResponse updateEvent(int eventId, CreateEventRequest request) {
        ApiResponse response = ApiClient.execute(ApiRequest.put("/events/{id}").pathParam("id", eventId).body(request));
        LOGGER.info("Updated event {} (status {})", eventId, response.statusCode());
        return response;
    }

    public ApiResponse deleteEvent(int eventId) {
        ApiResponse response = ApiClient.execute(ApiRequest.delete("/events/{id}").pathParam("id", eventId));
        LOGGER.info("Deleted event {} (status {})", eventId, response.statusCode());
        return response;
    }
}
```

**Request/Response DTOs** — Java records, one file per shape:

```java
package com.tests.application.requests;
/** Body shared by POST /auth/register and POST /auth/login (identical shape). */
public record AuthRequest(String email, String password) {}
```

```java
package com.tests.application.responses;
/** Body of POST /auth/register (201) and POST /auth/login (200). */
public record AuthResponse(boolean success, String token, AuthUser user) {
    public record AuthUser(int id, String email) {}
}
```

**API test-case data shape** — nested `*Data` record, same (metadata, data) convention as
Web/Mobile:

```java
package com.tests.application.testdata.api;

import com.framework.testdata.TestCaseMetadata;
import com.framework.testdata.TestCaseRecord;

public record AuthApiTestCase(TestCaseMetadata metadata, AuthApiData data) implements TestCaseRecord<AuthApiTestCase.AuthApiData> {
    /** Not every case needs every field - unused ones are simply absent from that row's JSON and resolve to null. */
    public record AuthApiData(
            String email, String password, String token,
            Integer expectedStatusCode, String expectedError, String expectedField, String expectedMessage) {}
}
```

**API test class** — extends `BaseApiTest`, real HTTP calls against a live API, full
positive/negative coverage per endpoint:

```java
package com.tests.tests.api;

import com.framework.api.ApiClient;
import com.framework.api.ApiRequest;
import com.framework.api.ApiResponse;
import com.tests.application.requests.AuthRequest;
import com.tests.application.responses.AuthResponse;
import com.tests.application.responses.MeResponse;
import com.tests.application.testdata.TestDataSurface;
import com.tests.application.testdata.api.AuthApiTestCase.AuthApiData;
import com.tests.application.testdata.api.AuthApiTestCase;
import com.framework.exceptions.ApiAuthenticationException;
import com.framework.utils.RandomDataUtils;
import com.tests.application.base.BaseApiTest;
import org.testng.annotations.Test;
import java.util.Map;
import static com.framework.utils.Verify.*;
import static org.testng.Assert.expectThrows;

public class AuthApiTest extends BaseApiTest {

    @Test(groups = {"smoke", "api", "auth", "positive"})
    public void registeringANewUserReturnsAUsableToken() {
        AuthApiData data = TestDataSurface.API.getCaseData("registerNewUser", AuthApiTestCase.class);
        String email = RandomDataUtils.uniqueEmail("auth.register");

        AuthResponse response = authService.register(email, data.password());

        assertTrue(response.success(), "Registration response should report success.");
        assertNotNull(response.token(), "Registration response should include a usable auth token.");
        assertEquals(response.user().email(), email, "Registered user's email should match what was submitted.");

        MeResponse me = authService.me();
        assertEquals(me.user().email(), email, "GET /auth/me should return the same account the token was just issued for.");
    }

    @Test(groups = {"api", "auth", "negative"})
    public void registeringWithAnAlreadyRegisteredEmailFails() {
        AuthApiData data = TestDataSurface.API.getCaseData("registerDuplicateEmail", AuthApiTestCase.class);
        ApiResponse response = ApiClient.execute(ApiRequest.post("/auth/register").body(new AuthRequest(data.email(), data.password())));

        response.assertStatusCode(data.expectedStatusCode());
        assertEquals(response.jsonPath().getString("error"), data.expectedError());
        assertFalse(response.jsonPath().getBoolean("success"), "Response should report success=false for a rejected registration.");
    }

    @Test(groups = {"smoke", "api", "auth", "positive"})
    public void loginWithExistingAccountWorks() {
        AuthApiData data = TestDataSurface.API.getCaseData("loginExistingAccount", AuthApiTestCase.class);
        AuthResponse response = authService.login(data.email(), data.password());

        assertTrue(response.success(), "Login response should report success.");
        assertNotNull(response.token(), "Login response should include a usable auth token.");
        assertEquals(response.user().email(), data.email(), "Logged-in user's email should match the account logged in with.");
    }

    @Test(groups = {"api", "auth", "negative"})
    public void loginWithWrongPasswordFails() {
        AuthApiData data = TestDataSurface.API.getCaseData("loginWrongPassword", AuthApiTestCase.class);
        ApiAuthenticationException exception = expectThrows(ApiAuthenticationException.class, () -> authService.login(data.email(), data.password()));
        assertTrue(exception.getMessage().contains("Login failed"), "Exception message should indicate login failed.");
    }

    @Test(groups = {"api", "auth", "negative"})
    public void callingProtectedEndpointWithoutLoggingInFailsFast() {
        // No login() called this test - hasAuthToken() is false, so the service short-circuits
        // before ever making the call, rather than letting the API 401 it.
        expectThrows(ApiAuthenticationException.class, authService::me);
    }
}
```

**API E2E chaining test** — the pattern for multi-step, cross-resource journeys, with
**`ThreadLocal` instance fields**, not plain ones (see [§25](#25-thread-safety-model) and
[§26](#26-known-gotchas--lessons-already-paid-for) for exactly why plain fields here caused a
real, reproduced bug under `-Dparallel=methods`):

```java
package com.tests.tests.api;

import com.framework.api.ApiContext;
import com.framework.api.ApiResponse;
import com.tests.application.requests.CreateBookingRequest;
import com.tests.application.requests.CreateEventRequest;
import com.tests.application.responses.BookingResponse;
import com.tests.application.responses.EventResponse;
import com.tests.application.services.BookingService;
import com.tests.application.services.EventService;
import com.tests.application.testdata.TestDataSurface;
import com.tests.application.testdata.api.BookingApiTestCase.BookingApiData;
import com.tests.application.testdata.api.BookingApiTestCase;
import com.tests.application.testdata.api.EventPayloadTestCase.EventPayloadData;
import com.tests.application.testdata.api.EventPayloadTestCase;
import com.framework.utils.DateUtils;
import com.framework.utils.RandomDataUtils;
import com.tests.application.base.BaseApiTest;
import org.testng.annotations.Test;
import static com.framework.utils.Verify.assertEquals;
import static com.framework.utils.Verify.assertTrue;

public class EventBookingE2EFlowTest extends BaseApiTest {

    private final EventService eventService = new EventService();
    private final BookingService bookingService = new BookingService();

    // ThreadLocal, NOT a plain field - TestNG runs every @Test method of a class on ONE SHARED
    // INSTANCE under parallel="methods", not one instance per thread/method. A plain field here
    // let one thread's write get clobbered by a different @Test running concurrently on the
    // same instance before the first thread read it back - reproduced live with
    // -Dparallel=methods -DthreadCount=8.
    private final ThreadLocal<Integer> createdBookingId = new ThreadLocal<>();
    private final ThreadLocal<Integer> createdEventId = new ThreadLocal<>();

    @Override
    protected void tearDownTestData() {
        if (createdBookingId.get() != null) { bookingService.cancelBooking(createdBookingId.get()); createdBookingId.remove(); }
        if (createdEventId.get() != null) { eventService.deleteEvent(createdEventId.get()); createdEventId.remove(); }
        ApiContext.clear();
    }

    private static CreateEventRequest toEventRequest(String title, EventPayloadData data) {
        String eventDate = data.eventDate() != null ? data.eventDate() : DateUtils.futureIsoDate(data.daysInFuture());
        return new CreateEventRequest(title, data.eventDescription(), data.category(), data.venue(), data.city(),
                eventDate, data.price(), data.totalSeats(), data.imageUrl());
    }

    @Test(groups = {"smoke", "api", "e2e", "events", "bookings"})
    public void fullEventLifecycleFromRegistrationThroughBookingToDeletionWorksEndToEnd() {
        // 1. Create an event.
        EventPayloadData eventData = TestDataSurface.API.getCaseData("e2eFullLifecycleEvent", EventPayloadTestCase.class);
        String uniqueTitle = "E2E Flow Event " + RandomDataUtils.uniqueId();
        ApiResponse createEventResponse = eventService.createEvent(toEventRequest(uniqueTitle, eventData));
        createEventResponse.assertStatusCode(eventData.expectedStatusCode());
        int eventId = createEventResponse.jsonPath().getInt("data.id");
        createdEventId.set(eventId);

        // 2. Book tickets, chaining the event ID extracted above straight into the request.
        BookingApiData bookingData = TestDataSurface.API.getCaseData("e2eFullLifecycleBooking", BookingApiTestCase.class);
        CreateBookingRequest bookingRequest = new CreateBookingRequest(
                eventId, bookingData.customerName(), RandomDataUtils.uniqueEmail("e2e.customer"), bookingData.customerPhone(), bookingData.quantity());
        ApiResponse createBookingResponse = bookingService.createBooking(bookingRequest);
        createBookingResponse.assertStatusCode(bookingData.expectedStatusCode());
        BookingResponse booking = createBookingResponse.extract("data", BookingResponse.class);
        createdBookingId.set(booking.id());
        assertEquals(booking.eventId(), eventId, "Booking should reference the event it was made against.");

        // 3. Confirm the atomic seat decrement - a fresh GET, not the booking response's own
        // stale pre-transaction snapshot.
        EventResponse afterBooking = eventService.getEvent(eventId).extract("data", EventResponse.class);
        int expectedAvailable = eventData.totalSeats() - bookingData.quantity();
        assertEquals(afterBooking.availableSeats(), expectedAvailable, "Seat count should reflect the new booking.");

        // 4. Cancel, confirm seat restoration; delete the event, confirm it's gone.
        bookingService.cancelBooking(createdBookingId.get()).assertStatusCode(200);
        assertEquals(eventService.getEvent(eventId).jsonPath().getInt("data.availableSeats"), eventData.totalSeats(), "Cancelling should restore all seats.");
        createdBookingId.remove();

        eventService.deleteEvent(eventId).assertStatusCode(200);
        eventService.getEvent(eventId).assertStatusCode(404);
        createdEventId.remove();
    }

    /** Same create-event-then-book chain, threaded explicitly through ApiContext.set/get instead of plain Java locals - proves the bearer token and an arbitrary chained value coexist correctly in the same runtime-variable store. */
    @Test(groups = {"smoke", "api", "e2e", "events", "bookings"})
    public void eventIdChainsThroughApiContextIntoTheBookingCall() {
        loginWithSeededAccount();
        assertTrue(ApiContext.has(ApiContext.ACCESS_TOKEN_KEY), "Login should have populated the ApiContext access token.");

        EventPayloadData eventData = TestDataSurface.API.getCaseData("e2eContextChainingEvent", EventPayloadTestCase.class);
        ApiResponse createEventResponse = eventService.createEvent(toEventRequest("ApiContext Chaining Event " + RandomDataUtils.uniqueId(), eventData));
        createEventResponse.assertStatusCode(eventData.expectedStatusCode());
        int eventId = createEventResponse.jsonPath().getInt("data.id");
        createdEventId.set(eventId);
        ApiContext.set("eventId", String.valueOf(eventId));

        BookingApiData bookingData = TestDataSurface.API.getCaseData("e2eContextChainingBooking", BookingApiTestCase.class);
        CreateBookingRequest bookingRequest = new CreateBookingRequest(
                Integer.parseInt(ApiContext.get("eventId")), bookingData.customerName(), RandomDataUtils.uniqueEmail("context.tester"),
                bookingData.customerPhone(), bookingData.quantity());
        ApiResponse createBookingResponse = bookingService.createBooking(bookingRequest);
        createBookingResponse.assertStatusCode(bookingData.expectedStatusCode());

        BookingResponse booking = createBookingResponse.extract("data", BookingResponse.class);
        createdBookingId.set(booking.id());
        assertEquals(String.valueOf(booking.eventId()), ApiContext.get("eventId"), "Booking should reference the event ID chained through ApiContext.");
    }
}
```

**Writing a new API service** — one method per endpoint, never calling REST Assured outside
`ApiClient`:

```java
public final class MyNewService {
    public ApiResponse doSomething(MyRequest request) {
        return ApiClient.execute(ApiRequest.post("/my-endpoint").body(request));
    }
}
```

## 22. TestNG group taxonomy & running tests

No suite XML anywhere — class, method, group, browser, environment, parallel mode are all
plain `-D` flags against Surefire's own classpath-wide TestNG discovery.

**Four independent tag axes, combinable in any filter:**

| Axis | Values | Meaning |
|---|---|---|
| Run tier | `smoke`, `sanity` | `smoke` = broad, every PR; `sanity` = one narrowest "is anything even up" check per surface |
| Surface | `api`, `web`, `mobile` | which stack drives the test |
| Test shape | `positive`, `negative`, `e2e` | single-endpoint/screen happy-path vs. rejection case; `e2e` = a multi-step cross-resource journey (never combined with positive/negative) |
| Resource | (app-specific, e.g. `auth`, `events`, `bookings`, `system`) | which domain the test covers — an `e2e` test carries every resource its journey touches |

`MultiDeviceParallelTest`-style device-matrix infra classes deliberately carry none of the
test-shape/resource tags — they aren't verifying product behavior, so those tags would
misrepresent what they actually check.

```bash
mvn clean compile

# Default "everything green" run - excludes mobile (no local emulator by default)
mvn test -DexcludedGroups=mobile

mvn clean test -Denv=qa -Dgroups=smoke -Dbrowser=chrome -Dheadless=true
mvn test -Dtest=AuthApiTest                                      # one class
mvn test -Dtest=AuthApiTest#loginWithExistingAccountWorks         # one method
mvn test -Dtest=AuthApiTest,EventBookingE2EFlowTest               # several classes
mvn test -Dgroups=smoke,api                                      # several groups
mvn test -Dgroups=sanity -DexcludedGroups=mobile                  # one live test per surface
mvn test -Dgroups=api -Dparallel=classes -DthreadCount=4          # parallel, one class per thread
mvn test -Dgroups=api -Dparallel=methods -DthreadCount=8          # parallel, one method per thread

mvn test -Dgroups=negative                          # every rejection/validation case, any surface
mvn test -Dgroups=events -DexcludedGroups=mobile     # every events-domain test, Web+API
mvn test -Dgroups=e2e                                # every multi-step journey, any surface
mvn test -Dgroups=bookings,negative                  # booking rejection cases specifically
```

**A group name nothing is tagged with matches zero tests but still reports `BUILD SUCCESS`**
— always check the printed test count.

**Rerunning only what failed:**

```bash
mvn -Dsurefire.suiteXmlFiles=target/surefire-reports/testng-failed.xml test
```

Surefire's TestNG provider writes `testng-failed.xml` after every run — a suite file listing
just the failed classes/methods, regardless of the fact this repo has no suite XML of its
own. Overwritten by the next full run.

**Web** — browser choice is environment-level config, never a test's own concern:

```bash
mvn test -Dgroups=web -Dbrowser=firefox -Dheadless=true  # chrome (default) | firefox | edge | safari
```

Cross-browser coverage is CI's own job matrix (see [§23](#23-cicd-github-actions)), not a
per-test loop — running the same suite twice with two different `-Dbrowser` values is how you
reproduce that locally.

**Mobile** — device details are never passed on the CLI; whether it runs sequentially on one
device or pooled across several depends only on whether `-Dparallel` is present:

```bash
mvn test -Dgroups=mobile                                              # sequential, one device (android by default)
mvn test -Dgroups=mobile -Dmobile.platform=ios                        # sequential, iOS instead
mvn test -Dgroups=mobile -Dparallel=methods -DthreadCount=3           # pooled across every device (work queue)
mvn test -Dgroups=mobile -Dmobile.device.provider=BROWSERSTACK ...    # real device / cloud farm
mvn test -Dtest=MultiDeviceParallelTest#appLaunchesOnEachAndroidDeviceConcurrently  # same test, every device at once
```

**Before any mobile command:** boot an emulator/simulator, then start Appium with
`appium --base-path /wd/hub` (matching `appium.server.url` — Appium 2.x/3.x serves at the
bare `/` root by default, and a mismatch surfaces as `SessionNotCreatedException: ... Response
code 404`, not an obviously Appium-related error). Check first whether one's already running:
`curl -s http://127.0.0.1:4723/wd/hub/status` (a `200` with `"ready":true` means nothing to
start).

**Other useful flags:**

```bash
mvn test -Dtestdata.format=yaml                       # switch the test-data source format
mvn test -Dmasking.enabled=true                       # turn masking on for a run you intend to share
mvn test -Dreport.types=extent,allure                 # enrich both reports (default: extent only)
mvn test -Dtest=... -Dmaven.surefire.debug            # attach a remote debugger on localhost:5005
```

---

## 23. CI/CD (GitHub Actions)

`.github/workflows/ci.yml` runs the exact same `mvn` command shape a developer runs locally —
nothing CI-specific about the framework's own behavior. Mobile is excluded (no
emulator/Appium on a hosted runner).

```yaml
name: CI

on:
  pull_request:
  push:
    branches: [main]
  workflow_dispatch:
    inputs:
      groups:
        description: "TestNG groups to run (-Dgroups=...); leave blank to run every group"
        required: false
        default: ""
      browser:
        description: "Browser (-Dbrowser=...)"
        required: false
        default: "chrome"
      env:
        description: "Environment (-Denv=...)"
        required: false
        default: "qa"

env:
  # Resolved by SecretManager as CI/CD environment variables - highest precedence over
  # .secret.env, which does not exist in CI at all (gitignored, never committed).
  # Configure as repository secrets: Settings > Secrets and variables > Actions.
  YOUR_APP_EMAIL: ${{ secrets.YOUR_APP_EMAIL }}
  YOUR_APP_PASSWORD: ${{ secrets.YOUR_APP_PASSWORD }}

jobs:
  smoke:
    name: Smoke (every PR, ${{ matrix.browser }})
    if: github.event_name == 'pull_request'
    runs-on: ubuntu-latest
    strategy:
      fail-fast: false
      matrix:
        browser: [chrome, firefox]   # ubuntu-latest ships both preinstalled; edge/safari stay local-only
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: "17", cache: maven }
      # Selenium Manager (bundled with Selenium 4) resolves the matching driver at runtime -
      # no separate driver-install step needed.
      - name: Run smoke tests
        run: >
          mvn -B clean test
          -Denv=qa -Dgroups=smoke -Dbrowser=${{ matrix.browser }} -Dheadless=true
          -DexcludedGroups=mobile
      - name: Upload test artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: smoke-reports-${{ matrix.browser }}
          path: |
            target/surefire-reports/
            logs/
            reports/extent/
            reports/api/
            allure-results/
            target/screenshots/
            target/site/jacoco/
          if-no-files-found: ignore
          retention-days: 14

  regression:
    name: Full regression (push to main, ${{ matrix.browser }})
    if: github.event_name == 'push'
    runs-on: ubuntu-latest
    strategy:
      fail-fast: false
      matrix:
        browser: [chrome, firefox]
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: "17", cache: maven }
      - name: Run regression suite
        # -Dgroups intentionally left blank - every group, never a nonexistent group name
        # that would silently match zero tests and still report a false-green build.
        run: >
          mvn -B clean test
          -Denv=qa -Dgroups= -Dbrowser=${{ matrix.browser }} -Dheadless=true
          -DexcludedGroups=mobile
      - name: Upload test artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: regression-reports-${{ matrix.browser }}
          path: |
            target/surefire-reports/
            logs/
            reports/extent/
            reports/api/
            allure-results/
            target/screenshots/
            target/site/jacoco/
          if-no-files-found: ignore
          retention-days: 30

  regression-manual:
    name: Full regression (manual dispatch, single browser)
    if: github.event_name == 'workflow_dispatch'
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: "17", cache: maven }
      - name: Run regression suite
        run: >
          mvn -B clean test
          -Denv=${{ github.event.inputs.env || 'qa' }}
          -Dgroups=${{ github.event.inputs.groups }}
          -Dbrowser=${{ github.event.inputs.browser || 'chrome' }}
          -Dheadless=true
          -DexcludedGroups=mobile
      - name: Upload test artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: regression-manual-reports
          path: |
            target/surefire-reports/
            logs/
            reports/extent/
            reports/api/
            allure-results/
            target/screenshots/
            target/site/jacoco/
          if-no-files-found: ignore
          retention-days: 30
```

**Design notes:**
- `smoke`/`regression` run a real `chrome`+`firefox` matrix (`fail-fast: false`, so one
  browser's failure doesn't cancel the other) — a manual dispatch stays single-browser
  deliberately, since it's normally someone targeting one specific combination on purpose.
- None of these jobs pass `-Dreport.types=...`, so Web/Mobile get the default (`extent` only)
  — the archived `allure-results/` is `allure-testng`'s own bare native capture. Add
  `-Dreport.types=extent,allure` to a job's command if a downstream Allure dashboard is ever
  wired up.
- `reports/api/` only has content when the job's group filter actually included API tests.

---

## 24. Reporting outputs — what gets produced

After any run:

| Path | What it is |
|---|---|
| `reports/extent/index.html` (or `report-{timestamp}.html`) | Self-contained, colored, readable HTML report for Web/Mobile — one node per `@Test`, business-narrative steps mirrored from logs, PASS/FAIL assertion steps, screenshots on failure |
| `reports/api/index.html` (or `report-{timestamp}.html`) | The API surface's own separate, dependency-free dashboard — module grouping, request/response detail, Expected/Actual assertions, search + tag filters |
| `allure-results/` | Raw JSON — `allure serve allure-results` to view. Native pass/fail/`@Before`/`@After` always present; steps/screenshots/labels/environment widget only when `report.types` includes `allure` |
| `logs/framework.log` | MDC-tagged per thread (`[ClassName.methodName]`), masked |
| `target/screenshots/` | Captured per `screenshot.mode` |
| `target/surefire-reports/` | Raw TestNG/Surefire output, including `testng-failed.xml` for reruns |
| `target/site/jacoco/index.html` | Line-coverage report for `com.framework.*` |

**Masking:** off by default in a local run (a developer's own `.secret.env` already has the
real value, and reading it straight off the console/report while debugging is the point).
Turn it on for a run you intend to share: `-Dmasking.enabled=true` or
`MASKING_ENABLED=true`. **CI always masks regardless of this flag.** Masked output is
`********-xxxxxxxx` — same secret always produces the same suffix, so two masked values can
be compared for equality without the real value ever appearing.

**Retry:** `RetryAnalyzer` (`retry.max.count`, default 1) retries everything except
`AssertionError`; a retried attempt's own report entry is kept, labeled `(Retry N)`. The same
policy covers `@BeforeMethod`/`@AfterMethod` via `ConfigurationRetryListener`. A retried,
eventually-passing method shows as `status="SKIP"`/`retried="true"` in
`target/surefire-reports/testng-results.xml` for its failed first attempt — this is TestNG's
own bookkeeping convention, not a bug; only a real `BUILD FAILURE` needs investigating.

---

## 25. Thread-safety model

Every shared object in `com.framework.*` falls into exactly one of five categories:

1. **Immutable global** — loaded once, read-only for the process lifetime.
   Example: `ConfigManager`'s merged environment/system-property snapshot,
   `SecretManager`'s parsed `.secret.env`, `MobileDeviceMatrix`'s parsed JSON tree.
2. **Thread-safe singleton** — a genuinely concurrent-safe structure (`ConcurrentHashMap`,
   `CopyOnWriteArrayList`, `AtomicInteger`, `LinkedBlockingQueue`).
   Example: `SensitiveDataMasker.KNOWN_SECRET_VALUES`, `TestDataManager.CACHE`,
   `MobileDevicePool`'s/`MobilePortAllocator`'s queues, `ApiReportRecorder.COMPLETED`.
3. **Thread-local** — each thread gets its own independent value, never visible across
   threads. Example: `ConfigManager`'s per-test override/parameter tiers,
   `VariableManager`/`ApiContext`, every `WebDriver`/`AppiumDriver` (via `DriverManager`),
   `ExtentManager.CURRENT_TEST`, `ApiReportRecorder.CURRENT`, `MobilePortAllocator`'s
   checked-out-port map, `MobileDevicePool`'s checked-out-device.
4. **Test-scoped** — safe only because TestNG runs one thread per class instance under
   `parallel="classes"`. **This is the one category that bites easily** — see the gotcha
   catalog below.
5. **Suite-scoped listener** — stateless, only touches other classes' thread-local state
   (every listener in [§18](#18-package-listeners) except the two ThreadLocal handoffs
   `RetryAnalyzer.CURRENT_ATTEMPT` and the retry-count instance field, which is itself
   test-scoped/category-4-safe since TestNG creates one `RetryAnalyzer` per `@Test` method).

**`MobilePortAllocator` checks each port out of a bounded, thread-safe pool and always
returns it on driver quit** — success, failure, or a retry — rather than caching one per
thread. An earlier cached-per-thread version caused real port collisions on retry (see
[§13](#13-package-driver)'s design note).

**Validated live, not assumed:** `-Dparallel=classes -DthreadCount=4` with genuinely
concurrent Chrome/Firefox sessions and API calls (distinct thread names/overlapping
timestamps in `logs/framework.log`); a real Android emulator + iOS simulator launching
concurrently in a `MultiDeviceParallelTest`-style class, confirmed via overlapping
`POST /session` requests in Appium's own server log.

---

## 26. Known gotchas & lessons already paid for

These were each found live, not theorized — reproducing them from scratch would waste real
debugging time this document exists to save.

1. **Test class instance fields under `parallel="methods"`.** TestNG runs every `@Test`
   method of a class on **one shared instance**, not one instance per thread/method — a plain
   instance field a test writes and reads back later (typically for `tearDownTestData()`) is
   exactly as unsafe as any other shared mutable state without `ThreadLocal`. Reproduced live
   with `mvn test -Dgroups=api -Dparallel=methods -DthreadCount=8`: one thread's
   `createdEventId`/`createdBookingId` write was clobbered by a different `@Test` method
   running concurrently on the same instance. Worse silent failure mode: `tearDownTestData()`
   reading the same field after the method returns could cancel/delete a *different* thread's
   still-in-use booking/event. **Fix:** hold any test-scoped chained-ID field as
   `ThreadLocal<Integer>`, same convention as `ApiContext`/`ConfigManager`.

2. **`ITestAnnotation.getRetryAnalyzerClass()` never returns `null`.** TestNG 7.10.2 defaults
   it to the sentinel `DisabledRetryAnalyzer.class` for a `@Test` with no `retryAnalyzer`
   declared. Comparing against `null` instead of that sentinel silently never assigns a retry
   analyzer to anything — caught only because a test specifically designed to prove retry
   happens failed instead of retrying and passing.

3. **`ITestListener.onTestStart`/`onTestSuccess` etc. fire *after* `@BeforeMethod`, not
   before.** Any listener that must reset thread-local state *before* `@BeforeMethod` runs
   (config parameters, runtime/API context) must use `IInvokedMethodListener.beforeInvocation`
   with `isBeforeMethodConfiguration()`, never `ITestListener.onTestStart`. Got this wrong
   twice independently in this codebase's history (`ConfigParameterListener`,
   `ApiContextListener`) before switching — both times confirmed via a throwaway probe
   listener logging the actual invocation order, not by reading TestNG's docs.

4. **Same-hook-type `afterInvocation` listeners fire in *reverse* registration order.**
   `ScreenshotCaptureListener` must be registered *after* `DriverCleanupListener` so its
   `afterInvocation` (needs the driver still alive) runs *before* the driver gets quit —
   confirmed with a two-listener probe; an initial "first registered, first invoked"
   assumption was wrong and silently broke screenshot capture.

5. **A stale UiAutomator2/WDA server can still be bound to a "released" port after a failed
   session.** Caching one mobile automation port per thread (reused across that thread's
   later drivers) reintroduces this: a retry on the same thread reuses the identical port and
   collides with the still-bound leftover from the just-failed attempt. **Fix:** a genuine
   checkout/return pool where a released port goes to the back of the queue, never reused
   immediately by the thread that just released it.

6. **A Flutter app's own native buttons can look identical to a genuine Android system
   dialog's buttons.** Both render as `android.widget.Button` via the accessibility bridge.
   Dismissing "unexpected dialogs" by tapping the last found button, with no package filter,
   ends up tapping the app's own Sign In button on every launch. **Fix:** filter out any
   button whose `package` attribute equals the app's own current package.

7. **An unfocused mobile text field's `sendKeys()` can silently not reach the app's own form
   state.** On Android, the accessibility service's direct "set text" action can visually
   update a native `EditText` without ever reaching a cross-platform UI framework's own text
   controller underneath it. **Fix:** an explicit `click()` (tap-to-focus) before `clear()`/
   `sendKeys()`, so a real IME/input-connection opens first.

8. **Hardcoded future date literals in test data are a silent time bomb.** A literal like
   `"2026-12-01T09:00:00.000Z"` works today and fails with a confusing API validation error
   the moment it's no longer in the future, with nothing pointing at "this test data is
   stale". **Fix:** compute a future date relative to actual run time (`DateUtils.futureIsoDate`).

9. **A `.json` extension hardcoded at a `TestDataManager.load(...)` call site silently
   defeats `testdata.format`.** Only an extension-less name lets the configured default format
   fill one in — always pass the bare file name unless a case genuinely must pin one specific
   format regardless of the run's configured default.

10. **`allure-testng`'s own `@DataProvider` row capture bypasses every masking call site.** It
    records a row as an Allure "Parameters" entry using that row object's own `toString()`,
    which no `.mask()` call anywhere in application code ever touches. **Fix:** a listener
    that masks every already-captured Allure parameter value centrally, in
    `IInvokedMethodListener.afterInvocation` (guaranteed to run before `allure-testng`'s own
    `ITestListener` callbacks serialize the result to disk — a phase-ordering guarantee,
    independent of registration order between the two).

11. **Deserializing an enveloped API response (`{success, data, message}`) directly into the
    domain DTO silently produces a zero/null-filled object**, not a failure — every declared
    field is genuinely absent at the document root. **Fix:** `ApiResponse.extract(path, type)`
    navigates to the nested node first.

12. **A logger name in `logback.xml` that drifts from the real package (after a restructure)
    fails silently** — no error, the business-narrative layer simply stops reaching
    Extent/Allure, and only the bare test pass/fail node shows with nothing underneath it.
    There is no automated check for this; keep logger names and package structure in sync by
    hand whenever one changes.

13. **A flat `********` mask makes debugging impossible** — no way to tell whether two masked
    values are the same secret or a report picked up a different dataset row than expected.
    **Fix:** a deterministic short fingerprint suffix (`********-xxxxxxxx`), unique enough to
    compare for equality, one-way enough to never leak the real value.

---

## 27. Build-from-scratch checklist

A literal, ordered checklist for standing this framework up from an empty directory (see
[§4](#4-build-order--what-depends-on-what) for the underlying dependency reasoning):

- [ ] `git init`, `pom.xml` (§3), `.gitignore`, directory skeleton (§2)
- [ ] `config/qa.properties`, `config/dev.properties`, `.secret.env.example` (§20) — fill in
      real `base.url`/`api.base.url` and secret key names for your actual app under test
- [ ] `src/main/resources/logback.xml` (§19) — adjust the three application-layer logger
      names once `src/test`'s package structure exists
- [ ] `com.framework.exceptions.*` (§5)
- [ ] `com.framework.enums.*` + `com.framework.utils.EnumUtils` (§6, §11)
- [ ] `com.framework.constants.ConfigKeys` (§7)
- [ ] `com.framework.config.ConfigManager` (§8)
- [ ] `com.framework.secrets.*` (§9)
- [ ] `com.framework.context.VariableManager` (§10)
- [ ] Remaining `com.framework.utils.*` — `JsonUtils`, `FileUtils`, `ScreenshotUtils`,
      `DateUtils`, `RandomDataUtils`, `TextUtils`, `WaitUtils` (§11)
- [ ] `com.framework.reporting.ExtentManager` / `ExtentLoggingAppender` / `AllureManager` /
      `AllureLoggingAppender` / `ReportManager` (§17, first half)
- [ ] `com.framework.utils.Verify` (§11 — now that `reporting` exists)
- [ ] `com.framework.testdata.*` (§12)
- [ ] `com.framework.driver.*` — `MobileDeviceMatrix`, `MobilePortAllocator`,
      `MobileDevicePool`, `DriverFactory`, `DriverManager`, `WebDriverManager`,
      `MobileDriverManager` (§13)
- [ ] `com.framework.web.*` (§14)
- [ ] `com.framework.mobile.*` (§15)
- [ ] `com.framework.api.*` (§16)
- [ ] `com.framework.reporting.ApiReportModel` / `ApiReportRecorder` /
      `ApiHtmlReportRenderer` (§17, second half — now that `api` exists)
- [ ] `com.framework.listeners.*`, one at a time, reading each ordering note in §18 as you go
- [ ] `src/main/resources/META-INF/services/org.testng.ITestNGListener` in the exact order
      given (§19) — **register and verify each listener before moving to the next**; ordering
      bugs are invisible until a specific parallel/retry/failure scenario exercises them
- [ ] `mvn clean compile` — confirm the framework module compiles standalone
- [ ] `src/test/java/com/tests/application/base/*` (§21)
- [ ] One vertical slice per surface you need (Page Object → Component → test-data record →
      JSON file → `@Test` class), following §21's patterns exactly
- [ ] `src/test/resources/testdata/json/{surface}/{surface}.json` per surface
- [ ] `mvn test -Dgroups=sanity -DexcludedGroups=mobile` — the narrowest possible "is
      anything even up" check, once one real test per surface exists
- [ ] `.github/workflows/ci.yml` (§23)
- [ ] `scripts/clean-local.sh` (§20)

**Verification milestones**, in order of increasing confidence:

1. A single Web test passes sequentially.
2. A single API test passes sequentially, and its report appears at `reports/api/index.html`.
3. A single Mobile test passes sequentially against a booted emulator/simulator.
4. `mvn test -Dgroups=api -Dparallel=methods -DthreadCount=8` — repeat several times; any
   flaky cross-test data corruption here means a plain instance field needs to become
   `ThreadLocal` (gotcha #1).
5. `mvn test -Dgroups=mobile -Dparallel=methods -DthreadCount=3` with 2+ devices booted —
   confirms `MobileDevicePool`/`MobilePortAllocator` genuinely pool rather than collide.
6. A deliberately failing test — confirms retry, screenshot capture, and both reports'
   failure rendering all fire correctly and in the right order.
7. `-Dmasking.enabled=true` against a test that logs a real secret — confirms the
   `********-xxxxxxxx` fingerprint appears in `logs/framework.log`, the Extent report, and
   the Allure `environment.properties`/parameters, everywhere that value could possibly leak.

---

*This document is self-contained: every class listed above is complete or explicitly marked
where a faithful equivalent (not a byte-for-byte copy) suffices. No other file in this
repository needs to be opened to reconstruct the framework from scratch.*


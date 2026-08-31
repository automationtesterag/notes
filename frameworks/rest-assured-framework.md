# Building a Rest Assured + TestNG API Framework From Scratch

This document is a **build guide**, not a usage guide. [`Readme.md`](Readme.md) explains
how to use the finished framework to write tests. This document explains how the
framework itself is put together, in the order you'd build it starting from an
empty Maven project, why each piece exists, and — in the appendix — the **complete,
unabridged source** of every file in the repository, so this document is
self-sufficient even without the checked-out code.

Read this if you want to:

- Understand the framework's internals well enough to extend or debug them
- Rebuild the same architecture for a different tech stack
- Onboard someone into maintaining `apiframework` itself (not just writing tests against it)

---

## Table of Contents

- [1. Design Goals](#1-design-goals)
- [2. Architecture at a Glance](#2-architecture-at-a-glance)
- [3. Project Skeleton](#3-project-skeleton)
- [4. Build Order](#4-build-order)
  - [Step 1 — pom.xml](#step-1--pomxml)
  - [Step 2 — Exceptions](#step-2--exceptions)
  - [Step 3 — Configuration Layer](#step-3--configuration-layer)
  - [Step 4 — Environment Properties Files](#step-4--environment-properties-files)
  - [Step 5 — Secrets Layer](#step-5--secrets-layer)
  - [Step 6 — Request/Response Core](#step-6--requestresponse-core)
  - [Step 7 — Scenario Context](#step-7--scenario-context)
  - [Step 8 — Test Data Layer](#step-8--test-data-layer)
  - [Step 9 — Reporting Engine](#step-9--reporting-engine)
  - [Step 10 — Logging](#step-10--logging)
  - [Step 11 — Wiring an Application on Top](#step-11--wiring-an-application-on-top)
  - [Step 12 — Run and Verify](#step-12--run-and-verify)
- [5. Key Design Decisions and Why](#5-key-design-decisions-and-why)
- [6. Extending the Framework](#6-extending-the-framework)
- [7. File Map](#7-file-map)
- [8. Complete Source Listing](#8-complete-source-listing)
  - [8.1 pom.xml](#81-pomxml)
  - [8.2 apiframework — config](#82-apiframework--config)
  - [8.3 apiframework — exceptions](#83-apiframework--exceptions)
  - [8.4 apiframework — secrets](#84-apiframework--secrets)
  - [8.5 apiframework — core](#85-apiframework--core)
  - [8.6 apiframework — context](#86-apiframework--context)
  - [8.7 apiframework — data](#87-apiframework--data)
  - [8.8 apiframework — reporting](#88-apiframework--reporting)
  - [8.9 src/main/resources](#89-srcmainresources)
  - [8.10 config/*.properties](#810-configproperties)
  - [8.11 secrets/secrets.properties.example](#811-secretssecretspropertiesexample)
  - [8.12 eventhub — the bundled example application](#812-eventhub--the-bundled-example-application)
  - [8.13 eventhub test resources](#813-eventhub-test-resources)
  - [8.14 .gitignore (relevant sections)](#814-gitignore-relevant-sections)

---

## 1. Design Goals

Every decision in `apiframework` traces back to one of these:

1. **Tests never touch Rest Assured directly.** `ApiClient` is the only class that
   calls `given()`. Everything else — services, tests — works with plain
   `ApiRequest`/`ApiResponse` objects.
2. **Config and secrets are never hardcoded.** Base URLs live in
   `config/<env>.properties`; credentials come from env vars, JVM properties, or a
   gitignored secrets file — in that priority order.
3. **Parallel execution is safe by default.** Anything that holds per-test state
   (`ScenarioContext`, auth tokens, the in-flight report record) is `ThreadLocal`,
   never a shared static field.
4. **Reporting is free.** A tester writes a `@Test` method and gets a full HTML
   report — no `ExtentReports`/Allure dependency, no manual "attach this to the
   report" calls sprinkled through test code.
5. **The framework knows nothing about any specific API.** Everything under
   `apiframework` is generic. Everything that knows about a specific application
   (endpoints, auth contract, domain models) lives in `src/test/java/<application>`.

---

## 2. Architecture at a Glance

```text
TestNG Test               "what should happen"
    ↓
Service Layer               "how to call this API"        (application-specific)
    ↓
ApiClient.execute()          the only Rest Assured caller   (apiframework/core)
    ↓
Rest Assured  →  API
    ↓
ApiResponse                  fluent verify*() assertions    (apiframework/core)
    ↓
ReportRecorder / HtmlReportRenderer   (apiframework/reporting)
```

Supporting, cross-cutting pieces feed into this pipeline:

- `apiframework.config` — which environment, which base URL, which timeouts
- `apiframework.secrets` — credentials, and redacting them out of logs/reports
- `apiframework.data` — externalized JSON test data with dynamic placeholders
- `apiframework.context` — passing values between chained API calls in one test

---

## 3. Project Skeleton

Start from an empty directory and lay out the two source roots the framework
depends on:

```text
your-project/
├── pom.xml
├── config/                          # env properties, kept outside src/ on purpose
│   ├── qa.properties
│   ├── staging.properties
│   └── prod.properties
├── secrets/
│   └── secrets.properties.example   # template only — real file is gitignored
├── src/
│   ├── main/
│   │   ├── java/apiframework/       # the reusable framework — this guide builds this
│   │   └── resources/
│   │       ├── META-INF/services/org.testng.ITestNGListener
│   │       └── logback.xml
│   └── test/
│       ├── java/<application>/      # one package per application under test
│       └── resources/<application>/
│           ├── testdata/
│           └── schemas/
└── reports/                          # generated at runtime, gitignored
```

`config/` and `secrets/` sit at the project root — not under `src/`— purely so a
non-Java teammate can find and edit an environment URL or a credential without
digging into source folders. Maven maps them onto the test classpath explicitly
(see Step 1).

---

## 4. Build Order

Build bottom-up: exceptions first (everything else throws them), then config and
secrets (the request layer needs both), then the request/response core, then the
cross-cutting helpers, then reporting and logging, and only then an actual
application on top. Every code block below is the **complete** file — nothing
abridged — so you can copy each one as-is.

### Step 1 — pom.xml

Dependencies, minimum:

| Purpose | Artifact |
|---|---|
| HTTP calls | `io.rest-assured:rest-assured` |
| JSON schema assertions | `io.rest-assured:json-schema-validator` |
| Test execution | `org.testng:testng` |
| JSON (de)serialization | `com.fasterxml.jackson.core:jackson-databind` |
| Logging | `org.slf4j:slf4j-api` + `ch.qos.logback:logback-classic` |

Two Maven wiring decisions matter more than the dependency list itself:

**a) Map `config/` and `secrets/` onto the test classpath**, so `ConfigManager`
and `SecretStore` (both classloader-resource readers, not filesystem readers) can
find them without either class knowing the project's directory layout.

**b) Let Surefire auto-discover tests and forward CLI switches as plain Maven
properties**, so nothing about environment, parallelism, or config location is
hardcoded in `pom.xml`. Declaring `<env>`/`<config.dir>`/etc. as Maven
`<properties>` — rather than literals inside `<systemPropertyVariables>` — is
what lets `-Denv=staging` on the command line actually win; a literal there
would always beat a same-named `-D`.

No `testng.xml` is needed anywhere: Surefire's default include pattern
(`**/*Tests.java`) picks up every test class, and the report listener
self-registers (Step 9).

The full, working `pom.xml` is in [§8.1](#81-pomxml).

---

### Step 2 — Exceptions

One unchecked exception type for every framework-level failure (bad config,
missing test data, a request that couldn't even be built) that is *not* itself a
test assertion failure. Keeping this separate from `AssertionError` (which
`ApiResponse.verify*` throws, Step 6) matters for TestNG: an
`ApiFrameworkException` means *the test couldn't even run*; an `AssertionError`
means *the test ran and the API didn't do what was expected*. Both fail the
test, but they read differently in a report.

Full source: [§8.3](#83-apiframework--exceptions).

---

### Step 3 — Configuration Layer

Two classes: an `Environment` enum, and a singleton `ConfigManager` that loads
`<env>.properties` once per JVM.

`Environment` resolves the `-Denv=` system property (case-insensitively, falling
back to `QA` if unset or unrecognized) and knows its own properties filename.

`ConfigManager` is a classic eager singleton — read once at class-load, exposed
read-only from then on. Two things worth calling out:

- `config.dir` is itself a system property (default `"config"`), so an
  application module can point at its own config folder
  (`-Dconfig.dir=eventhub/config`) without `ConfigManager` ever hardcoding a
  project name.
- `get(key)` (no default) throws `ApiFrameworkException` naming the missing key
  and the active environment — a missing config value fails loudly, at first use,
  with an actionable message, instead of surfacing as a mysterious NPE three
  layers down.

Full source: [§8.2](#82-apiframework--config).

---

### Step 4 — Environment Properties Files

One `.properties` file per environment under `config/` — `base.url`,
`request.timeout.ms`, `retry.count`, `retry.delay.ms`, and the `report.*` keys
consumed later (Steps 9–10). Full contents (all three shipped environments) are
in [§8.10](#810-configproperties).

---

### Step 5 — Secrets Layer

`SecretStore` resolves a credential through a fixed priority order — **env var →
JVM system property → classpath properties file** — and never lets a resolved
value escape into a log or report unmasked. Env var beats system property beats
file, so CI can override a locally-committed `secrets.properties.example`-derived
file without editing it.

The **static** `redact(String)` method is the piece `ApiClient` (Step 6) calls on
every request and response body before either reaches a log line or the report —
a plain regex substitution, safe to call on arbitrary text, matching well-known
sensitive JSON field names plus any bearer token found in free text (e.g. a
header dump).

Ship only a `secrets/secrets.properties.example` template in git, gitignore the
real `secrets/*.properties`, and document the lookup order in the template's
comments so a new contributor doesn't need this doc to get started.

Full source: [§8.4](#84-apiframework--secrets); template in [§8.11](#811-secretssecretspropertiesexample).

---

### Step 6 — Request/Response Core

This is the heart of the framework: four classes that together mean a test never
imports `io.restassured.*`.

- **`ApiRequest`** — an immutable-ish, fluent description of one call (method,
  endpoint, headers, query/path params, body). No Rest Assured knowledge
  required to build one.
- **`RetryPolicy`** — a tiny generic retry loop (`Supplier<T>` + `Predicate<T>`),
  applied only to idempotent calls.
- **`ApiClient`** — the only class that calls `given()`. It builds the
  `RequestSpecification`, applies timeouts from `ConfigManager`, retries `GET`
  calls on a 5xx (never `POST`/`PUT`/`DELETE` — retrying those could duplicate a
  side effect), executes, times the call, and logs it to both slf4j and the
  report — redacted, in that order, before either destination sees it.
  `logAndReport` serializes the body to real JSON (so `SecretStore.redact` —
  which matches quoted JSON fields — can find password/token fields in it),
  truncates a response body over 4000 characters, and masks the `Authorization`
  header outright (a raw header line isn't JSON, so the JSON-field regex
  wouldn't catch it).
- **`ApiResponse`** — the only response type a test ever sees. Every `verify*`
  method returns `this` so checks chain, and every failure both logs an
  Expected/Actual pair to the report *and* throws a plain `AssertionError` so
  TestNG's own result still reads correctly. It ships `verifyStatusCode`,
  `verifyField`, `verifyFieldExists`, `verifyFieldNotExists`, `verifyListSize`,
  `verifyEachElement`, `verifyHeader`, `verifyResponseTimeUnder`,
  `verifyJsonSchema` (via `JsonSchemaValidator.matchesJsonSchemaInClasspath`),
  plus escape hatches: `statusCode()`, `extract(jsonPath)`, `as(Class)`,
  `asPrettyString()`, and `raw()` for the rare case a test genuinely needs the
  underlying Rest Assured `Response`.

Full source for all four classes: [§8.5](#85-apiframework--core).

---

### Step 7 — Scenario Context

A `ThreadLocal`-backed key/value scratch space for passing a value from one API
call to the next inside a single test (e.g. an ID created in step 1, used in
step 3). `ThreadLocal`, never a shared static map, is what makes parallel test
classes/methods safe by construction rather than by convention. The report
listener (Step 9) calls `remove()` after every test so a thread reused for the
next test starts clean.

Full source: [§8.6](#86-apiframework--context).

---

### Step 8 — Test Data Layer

Two classes: `DataGenerator` (small, stateless random-value helpers — random
email/string/phone/title, a future ISO-8601 date, a bounded random int) and
`TestDataManager` (loads JSON from the classpath and resolves `${PLACEHOLDER}`
tokens in it).

`TestDataManager` treats a dotted path's first segment as a filename:
`getMap("events.validEvent")` reads `<baseDir>/events.json` → key `validEvent`.
Files are parsed once and cached (`ConcurrentHashMap`); placeholders inside
string leaves are re-resolved on **every** call, so `${RANDOM_EMAIL}` produces a
fresh value each time the same dataset is loaded. `getMap(dotPath)` converts the
resolved node to a `Map<String, Object>` (ready to hand straight to
`ApiRequest.body(...)`); `getString(dotPath)` returns a resolved scalar. Add new
placeholder tokens by adding a `case` in `resolveToken` plus a method on
`DataGenerator` — never by touching test code.

Full source: [§8.7](#87-apiframework--data).

---

### Step 9 — Reporting Engine

Four classes, each with exactly one job, so nothing outside `apiframework.reporting`
needs to know an HTML report exists at all:

- **`ReportModel`** (package-private) — plain data only: `TestRecord` (one per
  test method), and two `TestEvent` variants (`ApiCallEvent`, `AssertionEvent`).
  No HTML, no rendering logic.
- **`ReportRecorder`** — the only class `ApiClient`, `ApiResponse`, and the TestNG
  listener talk to. Holds the *current* test's record in a `ThreadLocal` (TestNG
  runs one test method start-to-finish on one thread, even under
  `parallel="classes"`) and appends every finished record to a
  `CopyOnWriteArrayList` for the whole suite. `flush()` hands the whole
  collected list to `HtmlReportRenderer` once, at suite end.
- **`TestReportListener implements ITestListener, ISuiteListener`** — wires
  TestNG's lifecycle to `ReportRecorder`: starts a record `onTestStart`, finishes
  it `onTestSuccess`/`onTestFailure`/`onTestSkipped` (handling the edge case where
  a failed `@BeforeClass` skips every `@Test` without TestNG ever calling
  `onTestStart`), derives a "module" name from the test's TestNG `groups` for
  report grouping, calls `ScenarioContext.remove()` after every test, and flushes
  the whole report once, `onFinish(ISuite)`.

  Self-registers with zero config via Java's `ServiceLoader` mechanism — the
  file `src/main/resources/META-INF/services/org.testng.ITestNGListener`
  contains one line naming this class (full contents in [§8.9](#89-srcmainresources)).
  No `testng.xml`, no `@Listeners` annotation on any test class, ever.

- **`HtmlReportRenderer`** — the only class that turns a `List<TestRecord>` into
  actual HTML: a Newman/Postman-style dashboard with summary cards, a
  performance summary, tests grouped by module, one collapsed row per test that
  expands to show request/response and every `verify*` as a plain Expected/Actual
  pair, search, and tag filters. It writes to `reports/index.html`
  (`report.overwrite=true`) or a timestamped `reports/report-<ts>.html`
  (`report.overwrite=false`), both controlled by `ConfigManager`. All CSS and JS
  are inlined into the generated file — no external assets — because the report
  has to open standalone from disk (`file://`, a CI artifact download) with no
  network access assumed. Status pills are colored by HTTP status *class* only,
  never by pass/fail — a test that deliberately asserts a 400/404 is exactly as
  green as one asserting 200; only the Validations section and the row's own
  check/cross icon reflect actual outcomes.

Full source for all four classes: [§8.8](#88-apiframework--reporting).

---

### Step 10 — Logging

A `logback.xml` on `src/main/resources` gives every run both a console appender
and a rotated-per-run file appender (`logs/framework.log`), keeps noisy
third-party loggers (`org.testng`, `io.restassured`, `org.apache.http`) at
`WARN`, and leaves `INFO` as root so framework and application code log without
extra configuration. Full contents in [§8.9](#89-srcmainresources).

---

### Step 11 — Wiring an Application on Top

Everything above is generic. An application module (e.g. `eventhub`, this repo's
bundled example) adds, under `src/test/java/<application>`:

```text
<application>/
├── auth/     # how THIS API authenticates (e.g. register-a-sandbox-user-per-thread)
├── base/     # BaseTest — wires services + test data, extended by every test class
├── models/   # POJOs, only where response typing genuinely helps
├── services/ # one class per resource, each method = one ApiRequest -> ApiResponse
└── tests/    # @Test classes — extend BaseTest, nothing else
```

**`BaseTest`** is the composition root: it `new`s up one `ApiClient`, one service
per resource, one `TestDataManager` pointed at this application's `testdata/`
folder, and (if the API needs auth) an app-specific auth manager — all as
`protected final` fields so every test class gets them for free just by
extending it, plus a `@BeforeClass` that authenticates once per class/thread.

A **service** class turns one resource into methods, each a one-line call
through `ApiClient`.

A **test** reads as Arrange → Act → Assert, with zero framework plumbing visible.
The `groups` array does double duty: TestNG group filtering (`-Dgroups=smoke`)
*and* the report's module grouping (`TestReportListener.moduleFor` maps a known
tag like `"events"` to a display name, e.g. "Events").

This is the only layer that should ever change day-to-day. Adding a new test
should essentially never require touching `apiframework`.

The complete `eventhub` example — `AuthManager`, every service, every model,
every test class, test data, and the JSON schema — is reproduced in full in
[§8.12](#812-eventhub--the-bundled-example-application) and
[§8.13](#813-eventhub-test-resources), and doubles as a worked reference for
building a second application.

---

### Step 12 — Run and Verify

```bash
mvn clean test-compile   # compiles everything, including the framework
mvn test                 # runs the suite for -Denv=qa (default)
mvn test -Denv=staging   # against another environment
open reports/index.html  # the report this build produces
```

If it compiles, self-registers, authenticates, executes, redacts, retries, and
renders — the framework is built. Everything from here is application code.

---

## 5. Key Design Decisions and Why

| Decision | Why |
|---|---|
| `ConfigManager` is a singleton, loaded once | Config is read-only for the life of the JVM; a singleton avoids re-parsing the properties file on every call without needing a DI container. |
| Everything stateful is `ThreadLocal` (`ScenarioContext`, `ReportRecorder.CURRENT`, `AuthManager`'s cached token) | TestNG's `parallel="classes"`/`"methods"` run different tests on different threads concurrently; a shared static field would leak one test's state into another's. |
| Retry is applied only to `GET`, only on 5xx | `POST`/`PUT`/`DELETE` are not guaranteed idempotent — blindly retrying one could duplicate a real side effect (e.g. create the same resource twice). |
| Redaction (`SecretStore.redact`) runs before *either* destination — log or report | A credential that reaches a log file or an HTML report file is a leak regardless of whether a human ever looks at it; redact once, at the single choke point (`ApiClient.logAndReport`), not per-caller. |
| `TestReportListener` self-registers via `META-INF/services` | Removes an entire class of setup mistakes — a developer adding a new test class can't forget a `@Listeners` annotation, because there's nothing to forget. |
| Status-code pills in the report are colored by class only, not pass/fail | A test that deliberately asserts `404` is exactly as valid as one asserting `200`; conflating "what the API returned" with "did the test pass" would make negative-test reports misleading. |
| `apiframework` vs `<application>` package split | Keeps the reusable 80% (request execution, config, secrets, reporting) portable to a completely different API by deleting one test package, not by disentangling framework code from business logic after the fact. |
| No `testng.xml` | Fewer moving parts to keep in sync: Surefire's default `**/*Tests.java` pattern, the self-registering listener, and CLI-only `-Dgroups=`/`-Dparallel=` together cover everything a `testng.xml` would otherwise configure. |
| Report HTML/CSS/JS is fully inlined, no third-party reporting library | The report file must open standalone (`file://`, a CI artifact download) with no build step and no network access assumed; a library dependency (ExtentReports, Allure) would add a runtime dependency and a generation step for something a ~450-line class can do directly. |
| EventHub's `AuthManager` registers one throwaway sandbox user per thread | EventHub gives every registered account a fully isolated data sandbox, so leaning on that gives parallel test classes complete data isolation for free — no shared fixtures, no manual token plumbing, no cross-test contamination. |

---

## 6. Extending the Framework

Adding a new *test* almost never touches `apiframework` — see `Readme.md` §5 for
that flow. You only touch `apiframework` itself when you need new **framework**
capability, e.g.:

- A new auth scheme generic enough for every application (OAuth client-credentials,
  API-key-in-header) — add it to `apiframework.core`/`apiframework.secrets`, not
  to one application's `auth` package, if more than one application will need it.
- A new `verify*` assertion shape — add it to `ApiResponse`, following the
  existing `pass(label, expected, actual)` / `fail(label, expected, actual, detail)`
  pattern so it shows up in the report the same way every other check does.
- A new test-data placeholder — add a `case` in
  `TestDataManager.resolveToken` plus a method on `DataGenerator`.
- A change to what the HTML report shows — `HtmlReportRenderer` only, since it's
  the sole class that knows HTML exists.

To stand up a **second application** against a different API in the same repo,
copy the `eventhub` shape (`auth/`, `base/`, `models/`, `services/`, `tests/`)
under a new top-level test package, point its `BaseTest`'s `TestDataManager` and
`AuthManager`/`SecretStore` at its own resource folders, and add a matching
`config/<app>/<env>.properties` set if it needs separate environment values.

---

## 7. File Map

```text
pom.xml
config/
├── qa.properties
├── staging.properties
└── prod.properties
secrets/
└── secrets.properties.example

src/main/java/apiframework/
├── config/
│   ├── Environment.java        # QA/STAGING/PROD + -Denv= resolution
│   └── ConfigManager.java      # singleton, loads <env>.properties once
├── context/
│   └── ScenarioContext.java    # ThreadLocal key/value store for API chaining
├── core/
│   ├── ApiRequest.java         # fluent request builder (no Rest Assured leakage)
│   ├── ApiClient.java          # the only class that calls given()
│   ├── ApiResponse.java        # fluent verify*() assertions + report logging
│   └── RetryPolicy.java        # generic retry, used for idempotent GETs only
├── data/
│   ├── DataGenerator.java      # random email/string/phone/date/int helpers
│   └── TestDataManager.java    # loads JSON test data, resolves ${PLACEHOLDER}s
├── exceptions/
│   └── ApiFrameworkException.java
├── reporting/
│   ├── ReportModel.java        # package-private plain data (TestRecord, events)
│   ├── ReportRecorder.java     # ThreadLocal current test + suite-wide collector
│   ├── TestReportListener.java # wires TestNG lifecycle -> ReportRecorder
│   └── HtmlReportRenderer.java # renders the collected data to reports/index.html
└── secrets/
    └── SecretStore.java        # env var / system property / file lookup + redact()

src/main/resources/
├── META-INF/services/org.testng.ITestNGListener   # -> apiframework.reporting.TestReportListener
└── logback.xml

src/test/java/eventhub/                # the bundled example application
├── auth/AuthManager.java
├── base/BaseTest.java
├── models/{Booking,Event}.java
├── services/{AuthService,BookingService,EventService,HealthService}.java
└── tests/{AuthTests,BookingsTests,E2EFlowTests,EventsTests,HealthAndConfigTests}.java

src/test/resources/eventhub/
├── testdata/{events,bookings,users}.json
└── schemas/event-schema.json
```

Build in the order this document walks through — `exceptions` → `config` →
`secrets` → `core` → `context` → `data` → `reporting` → `logging` — and each
class only ever depends on ones already built.

---

## 8. Complete Source Listing

Every file below is reproduced in full, exactly as it exists in this repository.

### 8.1 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>RestAssuredTestNG</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <!-- Default execution environment. Override with -Denv=staging|prod -->
        <env>qa</env>
        <!-- Classpath folder ConfigManager reads <env>.properties from. The root-level
             config/ folder (mapped below) is kept out of src/ for easy, non-Java access. -->
        <config.dir>eventhub/config</config.dir>
        <!-- Classpath folder SecretStore reads secrets.properties (and <env>.secrets.properties)
             from. The root-level secrets/ folder (mapped below) holds only *.properties.example
             templates in git — real *.properties files are gitignored. -->
        <secrets.dir>eventhub/secrets</secrets.dir>

        <!-- Parallel execution defaults. Override on the CLI, e.g.
             -Dparallel=methods -DthreadCount=8 — these are plain Maven properties
             interpolated into the surefire config below, not hardcoded there,
             so a CLI -D actually wins (a literal value in <configuration> would
             otherwise always beat a same-named -D system property). -->
        <parallel>classes</parallel>
        <threadCount>4</threadCount>

        <rest-assured.version>5.5.6</rest-assured.version>
        <testng.version>7.11.0</testng.version>
        <jackson.version>2.18.2</jackson.version>
        <slf4j.version>2.0.16</slf4j.version>
        <logback.version>1.5.16</logback.version>
    </properties>

    <dependencies>
        <!-- HTTP / API testing -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>

        <!-- Test execution -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
        </dependency>

        <!-- JSON -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
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
    </dependencies>

    <build>
        <!-- Explicit testResources: keep the default src/test/resources, and additionally
             map the root-level config/ and secrets/ folders onto the test classpath under
             eventhub/config and eventhub/secrets, so ConfigManager (-Dconfig.dir) and
             SecretStore (-Dsecrets.dir) find them. Both live at the project root purely so
             they're easy to find and edit without digging into src/. -->
        <testResources>
            <testResource>
                <directory>src/test/resources</directory>
            </testResource>
            <testResource>
                <directory>config</directory>
                <targetPath>eventhub/config</targetPath>
            </testResource>
            <testResource>
                <directory>secrets</directory>
                <targetPath>eventhub/secrets</targetPath>
                <!-- Only *.properties.example templates are ever present in git; if a
                     developer has created a real local secrets.properties, still include
                     it here (it's excluded from git, not from the build). -->
            </testResource>
        </testResources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.2</version>
                <configuration>
                    <!-- No testng.xml anywhere: test classes are auto-discovered by Surefire's
                         default include patterns (**/*Tests.java matches every class here), the
                         report listener self-registers via META-INF/services (see apiframework's
                         src/main/resources), and parallelism/groups are both plain CLI overrides
                         -Dparallel/-DthreadCount/-Dgroups/-DexcludedGroups — no pom.xml edit needed
                         to change any of them. -->
                    <parallel>${parallel}</parallel>
                    <threadCount>${threadCount}</threadCount>
                    <systemPropertyVariables>
                        <env>${env}</env>
                        <config.dir>${config.dir}</config.dir>
                        <secrets.dir>${secrets.dir}</secrets.dir>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

---

### 8.2 apiframework — config

**`src/main/java/apiframework/config/Environment.java`**

```java
package apiframework.config;

/**
 * Supported execution environments. The active environment is selected via the
 * {@code env} system property (e.g. {@code -Denv=qa}) and defaults to {@link #QA}.
 */
public enum Environment {
    QA,
    STAGING,
    PROD;

    /**
     * Resolves an environment name (case-insensitive) to an {@link Environment}.
     * Falls back to {@link #QA} when the value is null/blank/unrecognized.
     */
    public static Environment fromString(String value) {
        if (value == null || value.isBlank()) {
            return QA;
        }
        try {
            return Environment.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return QA;
        }
    }

    public String propertiesFileName() {
        return name().toLowerCase() + ".properties";
    }
}
```

**`src/main/java/apiframework/config/ConfigManager.java`**

```java
package apiframework.config;

import apiframework.exceptions.ApiFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized configuration. Loads {@code &lt;config.dir&gt;/&lt;env&gt;.properties} from the
 * classpath once per JVM and exposes it as immutable, thread-safe lookups.
 *
 * <p>Environment is never hardcoded in test code — select it with
 * {@code -Denv=qa|staging|prod} (defaults to {@code qa}).</p>
 *
 * <p>This class knows nothing about any particular project: {@code config.dir}
 * (default {@code config}) points at whichever classpath folder holds the
 * properties files, so an application module can keep its own environment
 * config alongside its test code (e.g. {@code -Dconfig.dir=applicationeventhub/config})
 * without touching this framework.</p>
 */
public final class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Environment environment;
    private final String configDir;
    private final Properties properties;

    private ConfigManager() {
        this.environment = Environment.fromString(System.getProperty("env"));
        this.configDir = System.getProperty("config.dir", "config");
        this.properties = load(environment);
        log.info("Loaded configuration for environment [{}] from [{}] -> baseUrl={}", environment, configDir, getBaseUrl());
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private Properties load(Environment env) {
        String resource = configDir + "/" + env.propertiesFileName();
        Properties props = new Properties();
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new ApiFrameworkException("Configuration file not found on classpath: " + resource
                        + " (set -Dconfig.dir=<folder> if your project keeps config elsewhere)");
            }
            props.load(in);
        } catch (IOException e) {
            throw new ApiFrameworkException("Failed to read configuration file: " + resource, e);
        }
        return props;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public int getRequestTimeoutMs() {
        return getInt("request.timeout.ms", 15000);
    }

    public int getRetryCount() {
        return getInt("retry.count", 0);
    }

    public long getRetryDelayMs() {
        return getInt("retry.delay.ms", 500);
    }

    public String getReportTitle() {
        return get("report.title", "API Automation Report");
    }

    public String getReportName() {
        return get("report.name", "API Automation Framework");
    }

    /**
     * {@code true} (default, matching historical behavior) always writes
     * {@code reports/index.html}, so every run replaces the last one — simplest when
     * each run's report is already archived elsewhere (e.g. uploaded as a CI artifact).
     * {@code false} gives each run its own timestamped file instead, so a developer
     * iterating locally can keep several runs' reports side by side to compare rather than
     * the next run silently erasing the last one.
     */
    public boolean overwriteReport() {
        return getBoolean("report.overwrite", true);
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new ApiFrameworkException("Missing required config key [" + key + "] for environment [" + environment + "]");
        }
        return value;
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : Integer.parseInt(value.trim());
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value.trim());
    }
}
```

---

### 8.3 apiframework — exceptions

**`src/main/java/apiframework/exceptions/ApiFrameworkException.java`**

```java
package apiframework.exceptions;

/**
 * Unchecked exception for framework-level failures (configuration, test data,
 * request execution) that are not themselves test assertion failures.
 */
public class ApiFrameworkException extends RuntimeException {

    public ApiFrameworkException(String message) {
        super(message);
    }

    public ApiFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

---

### 8.4 apiframework — secrets

**`src/main/java/apiframework/secrets/SecretStore.java`**

```java
package apiframework.secrets;

import apiframework.config.ConfigManager;
import apiframework.exceptions.ApiFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolves sensitive values (credentials, API keys, tokens) without ever hardcoding
 * or committing them, and redacts them out of anything that might end up in a
 * report or log file.
 *
 * <p>Lookup order for a key, e.g. {@code "test.user.password"}:</p>
 * <ol>
 *   <li>Environment variable — {@code TEST_USER_PASSWORD}</li>
 *   <li>JVM system property — {@code test.user.password}</li>
 *   <li>A classpath properties file at {@code <secretsDir>/secrets.properties},
 *       optionally overridden per environment by {@code <secretsDir>/<env>.secrets.properties}</li>
 * </ol>
 *
 * <p>None of those files are meant to be committed — only the {@code *.properties.example}
 * templates that document the expected keys are. See the project's {@code .gitignore}.</p>
 */
public final class SecretStore {

    private static final Logger log = LoggerFactory.getLogger(SecretStore.class);

    /** JSON field names whose values get redacted by {@link #redact(String)}. */
    private static final Pattern SENSITIVE_JSON_FIELD = Pattern.compile(
            "(?i)(\"(?:password|token|secret|apikey|api_key|accesstoken|access_token|"
                    + "refreshtoken|refresh_token|clientsecret|client_secret)\"\\s*:\\s*)\"[^\"]*\"");

    /** Matches a bearer token wherever it appears outside JSON, e.g. in a header dump. */
    private static final Pattern BEARER_TOKEN = Pattern.compile("(?i)(Bearer\\s+)[A-Za-z0-9\\-_.]+");

    private final Properties fileSecrets;

    /** Defaults to the {@code secrets} folder on the classpath. */
    public SecretStore() {
        this("secrets");
    }

    public SecretStore(String secretsDir) {
        this.fileSecrets = loadFileSecrets(secretsDir);
    }

    /** Returns the secret, or throws if it isn't defined anywhere. The exception never includes the value. */
    public String getRequired(String key) {
        String value = getOptional(key, null);
        if (value == null) {
            throw new ApiFrameworkException("Missing required secret [" + key + "]. Set env var ["
                    + toEnvVarName(key) + "], system property [-D" + key + "=...], or add it to a secrets properties file.");
        }
        return value;
    }

    /** Returns the secret, or {@code defaultValue} if it isn't defined anywhere. */
    public String getOptional(String key, String defaultValue) {
        String fromEnv = System.getenv(toEnvVarName(key));
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromSystemProperty = System.getProperty(key);
        if (fromSystemProperty != null && !fromSystemProperty.isBlank()) {
            return fromSystemProperty;
        }
        String fromFile = fileSecrets.getProperty(key);
        if (fromFile != null && !fromFile.isBlank()) {
            return fromFile;
        }
        return defaultValue;
    }

    /**
     * Redacts well-known sensitive fields (password, token, secret, API keys, ...) out of a
     * JSON string, plus any bearer token found in free text. Safe to call on any text —
     * returns it unchanged if nothing matches. Used by {@code ApiClient} so request/response
     * bodies attached to the HTML report and log files never carry live credentials.
     */
    public static String redact(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String result = replaceAll(SENSITIVE_JSON_FIELD, text, group1 -> group1 + "\"***REDACTED***\"");
        result = replaceAll(BEARER_TOKEN, result, group1 -> group1 + "***REDACTED***");
        return result;
    }

    private static String replaceAll(Pattern pattern, String input, java.util.function.UnaryOperator<String> replacement) {
        Matcher matcher = pattern.matcher(input);
        StringBuilder out = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(out, Matcher.quoteReplacement(replacement.apply(matcher.group(1))));
        }
        matcher.appendTail(out);
        return out.toString();
    }

    private Properties loadFileSecrets(String secretsDir) {
        Properties merged = new Properties();
        loadIfPresent(secretsDir + "/secrets.properties", merged);
        String env = ConfigManager.getInstance().getEnvironment().name().toLowerCase();
        loadIfPresent(secretsDir + "/" + env + ".secrets.properties", merged);
        return merged;
    }

    private void loadIfPresent(String resource, Properties target) {
        try (InputStream in = SecretStore.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                return;
            }
            Properties loaded = new Properties();
            loaded.load(in);
            target.putAll(loaded);
            log.debug("Loaded secrets file [{}] ({} key(s) — values are never logged)", resource, loaded.size());
        } catch (IOException e) {
            throw new ApiFrameworkException("Failed to read secrets file: " + resource, e);
        }
    }

    private static String toEnvVarName(String key) {
        return key.toUpperCase().replaceAll("[^A-Z0-9]", "_");
    }
}
```

---

### 8.5 apiframework — core

**`src/main/java/apiframework/core/ApiRequest.java`**

```java
package apiframework.core;

import io.restassured.http.Method;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A plain, fluent description of a single HTTP call — endpoint, method, headers,
 * params and body. Building one of these involves no Rest Assured knowledge;
 * {@link ApiClient} is the only place that translates it into an actual request.
 */
public final class ApiRequest {

    private final Method method;
    private final String endpoint;
    private final Map<String, Object> headers = new LinkedHashMap<>();
    private final Map<String, Object> queryParams = new LinkedHashMap<>();
    private final Map<String, Object> pathParams = new LinkedHashMap<>();
    private Object body;

    private ApiRequest(Method method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    public static ApiRequest get(String endpoint) {
        return new ApiRequest(Method.GET, endpoint);
    }

    public static ApiRequest post(String endpoint) {
        return new ApiRequest(Method.POST, endpoint);
    }

    public static ApiRequest put(String endpoint) {
        return new ApiRequest(Method.PUT, endpoint);
    }

    public static ApiRequest patch(String endpoint) {
        return new ApiRequest(Method.PATCH, endpoint);
    }

    public static ApiRequest delete(String endpoint) {
        return new ApiRequest(Method.DELETE, endpoint);
    }

    public ApiRequest header(String name, Object value) {
        if (value != null) {
            headers.put(name, value);
        }
        return this;
    }

    public ApiRequest bearerToken(String token) {
        return header("Authorization", "Bearer " + token);
    }

    public ApiRequest queryParam(String name, Object value) {
        if (value != null) {
            queryParams.put(name, value);
        }
        return this;
    }

    public ApiRequest queryParams(Map<String, ?> params) {
        if (params != null) {
            params.forEach(this::queryParam);
        }
        return this;
    }

    public ApiRequest pathParam(String name, Object value) {
        pathParams.put(name, value);
        return this;
    }

    public ApiRequest body(Object body) {
        this.body = body;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public Map<String, Object> getPathParams() {
        return pathParams;
    }

    public Object getBody() {
        return body;
    }
}
```

**`src/main/java/apiframework/core/RetryPolicy.java`**

```java
package apiframework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Minimal retry helper. Only ever applied to idempotent calls (GET) inside
 * {@link ApiClient} — retrying a POST/PUT/DELETE could duplicate side effects,
 * so those always run exactly once.
 */
public final class RetryPolicy {

    private static final Logger log = LoggerFactory.getLogger(RetryPolicy.class);

    private RetryPolicy() {
    }

    /**
     * Executes {@code action}, retrying up to {@code maxRetries} additional times
     * when {@code shouldRetry} matches the returned response, or when the call throws.
     */
    public static <T> T execute(Supplier<T> action, java.util.function.Predicate<T> shouldRetry,
                                 int maxRetries, long delayMs) {
        RuntimeException lastError = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                T result = action.get();
                if (attempt == maxRetries || !shouldRetry.test(result)) {
                    return result;
                }
                log.warn("Retryable response on attempt {}/{}, retrying in {}ms", attempt + 1, maxRetries + 1, delayMs);
            } catch (RuntimeException e) {
                lastError = e;
                if (attempt == maxRetries) {
                    throw e;
                }
                log.warn("Call threw {} on attempt {}/{}, retrying in {}ms", e.getClass().getSimpleName(),
                        attempt + 1, maxRetries + 1, delayMs);
            }
            sleep(delayMs);
        }
        // Unreachable in practice: loop always returns or throws above.
        throw lastError != null ? lastError : new IllegalStateException("Retry loop exited unexpectedly");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

**`src/main/java/apiframework/core/ApiClient.java`**

```java
package apiframework.core;

import apiframework.config.ConfigManager;
import apiframework.reporting.ReportRecorder;
import apiframework.secrets.SecretStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * The single place that talks to Rest Assured. Every service goes through
 * {@link #execute(ApiRequest)} — nobody else builds a request specification,
 * touches timeouts/retries, or logs a request/response by hand.
 */
public final class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ConfigManager config;

    public ApiClient() {
        this.config = ConfigManager.getInstance();
    }

    public ApiResponse execute(ApiRequest request) {
        if (request.getMethod() == Method.GET && config.getRetryCount() > 0) {
            Response response = RetryPolicy.execute(
                    () -> doExecute(request),
                    r -> r.statusCode() >= 500,
                    config.getRetryCount(),
                    config.getRetryDelayMs());
            return new ApiResponse(response);
        }
        return new ApiResponse(doExecute(request));
    }

    private Response doExecute(ApiRequest request) {
        RequestSpecification spec = given()
                .baseUri(config.getBaseUrl())
                .config(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", config.getRequestTimeoutMs())
                                .setParam("http.socket.timeout", config.getRequestTimeoutMs())))
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .pathParams(request.getPathParams());

        if (request.getBody() != null) {
            spec = spec.contentType("application/json").body(request.getBody());
        }

        long start = System.currentTimeMillis();
        Response response = spec.request(request.getMethod(), request.getEndpoint());
        long durationMs = System.currentTimeMillis() - start;

        logAndReport(request, response, durationMs);
        return response;
    }

    /**
     * Logs the call to both slf4j and the report, immediately — not buffered for
     * later — so the report reads in the same order the test actually ran: this call's
     * details appear right here, before whatever {@code verify*} calls the caller makes
     * against the returned {@link ApiResponse} log their own pass/fail lines straight
     * after.
     *
     * <p>Request/response bodies are always logged, to both destinations — this is not
     * an opt-in a config file can turn off. {@link SecretStore#redact} still masks
     * sensitive fields (and {@link #formatHeaders} masks the Authorization header
     * outright), and a very large response body is still truncated, before either
     * reaches either destination.</p>
     */
    private void logAndReport(ApiRequest request, Response response, long durationMs) {
        String url = config.getBaseUrl() + request.getEndpoint();
        int statusCode = response.statusCode();
        log.info("{} {} -> {} ({} ms)", request.getMethod(), url, statusCode, durationMs);

        String requestBody = request.getBody() != null ? SecretStore.redact(toJson(request.getBody())) : null;
        if (requestBody != null) {
            log.info("Request: {}", requestBody);
        }

        String body = response.getBody().asPrettyString();
        if (body != null && body.length() > 4000) {
            body = body.substring(0, 4000) + "... (truncated)";
        }
        String responseBody = SecretStore.redact(body);
        log.info("Response: {}", responseBody);

        ReportRecorder.logApiCall(request.getMethod().name(), request.getEndpoint(), url, statusCode, durationMs,
                formatHeaders(request.getHeaders()), requestBody, responseBody);
    }

    /** Formats headers as {@code "Name: value"} lines for the report, masking the Authorization value outright rather than relying on {@link SecretStore#redact}, which only matches quoted JSON fields, not a raw header line. */
    private String formatHeaders(Map<String, Object> headers) {
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        headers.forEach((name, value) -> {
            String shown = "Authorization".equalsIgnoreCase(name) ? "***REDACTED***" : String.valueOf(value);
            sb.append(name).append(": ").append(shown).append('\n');
        });
        return sb.toString().stripTrailing();
    }

    /**
     * Serializes a request body to real JSON for display (rather than Java's {@code Map.toString()}
     * shape) so {@link SecretStore#redact(String)} — which matches quoted JSON fields — can find
     * and mask password/token/secret values in it before it reaches the report.
     */
    private String toJson(Object body) {
        try {
            return MAPPER.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            return String.valueOf(body);
        }
    }
}
```

**`src/main/java/apiframework/core/ApiResponse.java`**

```java
package apiframework.core;

import apiframework.reporting.ReportRecorder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.List;
import java.util.Objects;

/**
 * Fluent wrapper around a Rest Assured {@link Response}. This is the only response
 * type a tester ever sees — no Rest Assured API leaks past this class.
 *
 * <p>Every {@code verify*} method returns {@code this}, so checks chain, and every
 * failure throws a plain {@link AssertionError} with a descriptive message so it
 * reads correctly in TestNG's own results.</p>
 *
 * <p>Each {@code verify*} also logs its own outcome to the report via {@link #pass}/
 * {@link #fail} as a plain Expected/Actual pair, labeled by check type ("Status Code",
 * "Field Value", ...) — not a technical assertion sentence — so a test that makes
 * several assertions shows exactly which check ran and which one failed.</p>
 */
public final class ApiResponse {

    private final Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public ApiResponse verifyStatusCode(int expected) {
        int actual = response.statusCode();
        if (actual != expected) {
            fail("Status Code", String.valueOf(expected), String.valueOf(actual),
                    "Response body: " + response.getBody().asPrettyString());
        }
        pass("Status Code", String.valueOf(expected), String.valueOf(actual));
        return this;
    }

    public ApiResponse verifyField(String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        String expectedStr = String.valueOf(expected);
        String actualStr = String.valueOf(actual);
        if (!Objects.equals(actualStr, expectedStr)) {
            fail("Field: " + jsonPath, expectedStr, actualStr, null);
        }
        pass("Field: " + jsonPath, expectedStr, actualStr);
        return this;
    }

    public ApiResponse verifyFieldExists(String jsonPath) {
        Object actual = response.jsonPath().get(jsonPath);
        if (actual == null) {
            fail("Field Exists: " + jsonPath, "exists", "absent", null);
        }
        pass("Field Exists: " + jsonPath, "exists", "exists (" + actual + ")");
        return this;
    }

    public ApiResponse verifyFieldNotExists(String jsonPath) {
        Object actual = response.jsonPath().get(jsonPath);
        if (actual != null) {
            fail("Field Absent: " + jsonPath, "absent", String.valueOf(actual), null);
        }
        pass("Field Absent: " + jsonPath, "absent", "absent");
        return this;
    }

    public ApiResponse verifyListSize(String jsonPath, int expected) {
        List<?> list = response.jsonPath().getList(jsonPath);
        int actual = list == null ? 0 : list.size();
        if (actual != expected) {
            fail("List Size: " + jsonPath, String.valueOf(expected), String.valueOf(actual), null);
        }
        pass("List Size: " + jsonPath, String.valueOf(expected), String.valueOf(actual));
        return this;
    }

    public ApiResponse verifyEachElement(String jsonPath, String fieldName, Object expected) {
        List<?> values = response.jsonPath().getList(jsonPath + "." + fieldName);
        String label = "List Field: " + jsonPath + "." + fieldName;
        String expectedStr = "every element = " + expected;
        if (values == null || values.isEmpty()) {
            fail(label, expectedStr, "(empty list)", null);
        }
        for (Object actual : values) {
            if (!Objects.equals(String.valueOf(actual), String.valueOf(expected))) {
                fail(label, expectedStr, "found " + actual, null);
            }
        }
        pass(label, expectedStr, values.size() + " elements matched");
        return this;
    }

    public ApiResponse verifyHeader(String name, String expected) {
        String actual = response.getHeader(name);
        if (!Objects.equals(actual, expected)) {
            fail("Header: " + name, expected, actual, null);
        }
        pass("Header: " + name, expected, actual);
        return this;
    }

    public ApiResponse verifyResponseTimeUnder(long millis) {
        long actual = response.getTime();
        String expectedStr = "< " + millis + " ms";
        if (actual > millis) {
            fail("Response Time", expectedStr, actual + " ms", null);
        }
        pass("Response Time", expectedStr, actual + " ms");
        return this;
    }

    public ApiResponse verifyJsonSchema(String classpathSchemaPath) {
        String label = "JSON Schema: " + classpathSchemaPath;
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(classpathSchemaPath));
        } catch (AssertionError e) {
            fail(label, "matches schema", "does not match", e.getMessage());
        }
        pass(label, "matches schema", "matches");
        return this;
    }

    public int statusCode() {
        return response.statusCode();
    }

    public <T> T extract(String jsonPath) {
        return response.jsonPath().get(jsonPath);
    }

    public <T> T as(Class<T> type) {
        return response.as(type);
    }

    public String asPrettyString() {
        return response.getBody().asPrettyString();
    }

    /** Escape hatch for the rare case a test genuinely needs the raw Rest Assured response. */
    public Response raw() {
        return response;
    }

    /** Logs a passing check, labeled {@code label}, as an Expected/Actual pair on the current test's report record. */
    private void pass(String label, String expected, String actual) {
        ReportRecorder.logAssertion(label, true, expected, actual, null);
    }

    /**
     * Logs a failing check, labeled {@code label}, as an Expected/Actual pair (plus
     * optional {@code detail}, e.g. a response body dump) on the current test's report
     * record, then throws it as an {@link AssertionError} so TestNG still sees a normal
     * assertion failure.
     */
    private void fail(String label, String expected, String actual, String detail) {
        ReportRecorder.logAssertion(label, false, expected, actual, detail);
        String message = label + " — expected <" + expected + "> but was <" + actual + ">"
                + (detail != null ? ". " + detail : "");
        throw new AssertionError(message);
    }
}
```

---

### 8.6 apiframework — context

**`src/main/java/apiframework/context/ScenarioContext.java`**

```java
package apiframework.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe scratch space for passing values between chained API calls within
 * a test (e.g. an ID created in step 1, used in step 2). Backed by a
 * {@link ThreadLocal} map — never a shared static — so parallel test
 * methods/classes never see each other's data.
 */
public final class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> STORE = ThreadLocal.withInitial(HashMap::new);

    private ScenarioContext() {
    }

    public static void set(String key, Object value) {
        STORE.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) STORE.get().get(key);
    }

    public static boolean contains(String key) {
        return STORE.get().containsKey(key);
    }

    /** Clears all values stored for the current thread — call between independent tests/scenarios. */
    public static void clear() {
        STORE.get().clear();
    }

    public static void remove() {
        STORE.remove();
    }
}
```

---

### 8.7 apiframework — data

**`src/main/java/apiframework/data/DataGenerator.java`**

```java
package apiframework.data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates random/dynamic values for test data placeholders. Kept deliberately
 * small — just what {@link TestDataManager} needs to resolve {@code ${...}} tokens.
 */
public final class DataGenerator {

    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final AtomicLong SEQUENCE = new AtomicLong(System.currentTimeMillis());

    private DataGenerator() {
    }

    public static String randomEmail() {
        return "fw.qa." + uniqueSuffix() + "@example.com";
    }

    public static String randomAlphanumeric(int length) {
        Random random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    public static String randomPhone() {
        long number = 6000000000L + ThreadLocalRandom.current().nextLong(999999999L);
        return "+91-" + number;
    }

    public static String randomTitle() {
        return "FW Automated Item " + uniqueSuffix();
    }

    /** ISO-8601 timestamp N days in the future — always valid for date fields. */
    public static String futureDate(int daysAhead) {
        return Instant.now().plus(daysAhead, ChronoUnit.DAYS).toString();
    }

    public static int randomInt(int minInclusive, int maxInclusive) {
        return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
    }

    private static String uniqueSuffix() {
        return SEQUENCE.incrementAndGet() + "." + Thread.currentThread().threadId();
    }
}
```

**`src/main/java/apiframework/data/TestDataManager.java`**

```java
package apiframework.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import apiframework.exceptions.ApiFrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads centralized JSON test data from a classpath folder and hands it to
 * tests as ready-to-use request bodies. Knows nothing about any particular
 * project — the folder is passed in by the caller, e.g.
 * {@code new TestDataManager("applicationeventhub/testdata")}.
 *
 * <p>Lookup uses a dotted path where the first segment is the file name, e.g.
 * {@code get("events.validEvent")} reads {@code <baseDir>/events.json} -> {@code validEvent}.
 * String leaves may contain {@code ${PLACEHOLDER}} tokens (random email, phone,
 * future date, etc.) which are resolved fresh on every call — a tester adds or
 * edits a dataset by editing JSON only, never framework code.</p>
 */
public final class TestDataManager {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([A-Z_]+)(?::([^}]*))?}");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String baseDir;
    private final Map<String, JsonNode> fileCache = new ConcurrentHashMap<>();

    /** Defaults to the {@code testdata} folder on the classpath. */
    public TestDataManager() {
        this("testdata");
    }

    public TestDataManager(String baseDir) {
        this.baseDir = baseDir;
    }

    /** Returns the value at {@code dotPath} as a request-body-ready Map, with placeholders resolved. */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String dotPath) {
        JsonNode node = resolveNode(dotPath);
        Object converted = MAPPER.convertValue(node, Object.class);
        Object resolved = resolvePlaceholders(converted);
        if (!(resolved instanceof Map)) {
            throw new ApiFrameworkException("Test data at [" + dotPath + "] is not a JSON object");
        }
        return (Map<String, Object>) resolved;
    }

    /** Returns the value at {@code dotPath} as a resolved string. */
    public String getString(String dotPath) {
        JsonNode node = resolveNode(dotPath);
        return resolveString(node.asText());
    }

    private JsonNode resolveNode(String dotPath) {
        String[] parts = dotPath.split("\\.", 2);
        if (parts.length == 0) {
            throw new ApiFrameworkException("Invalid test data path: " + dotPath);
        }
        JsonNode root = loadFile(parts[0]);
        if (parts.length == 1) {
            return root;
        }
        JsonNode current = root;
        for (String segment : parts[1].split("\\.")) {
            current = current.path(segment);
        }
        if (current.isMissingNode()) {
            throw new ApiFrameworkException("Test data path not found: " + dotPath);
        }
        return current;
    }

    private JsonNode loadFile(String fileName) {
        return fileCache.computeIfAbsent(fileName, name -> {
            String resource = baseDir + "/" + name + ".json";
            try (InputStream in = TestDataManager.class.getClassLoader().getResourceAsStream(resource)) {
                if (in == null) {
                    throw new ApiFrameworkException("Test data file not found on classpath: " + resource);
                }
                return MAPPER.readTree(in);
            } catch (IOException e) {
                throw new ApiFrameworkException("Failed to read test data file: " + resource, e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Object resolvePlaceholders(Object value) {
        if (value instanceof String s) {
            return resolveString(s);
        }
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> resolved = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                resolved.put((String) entry.getKey(), resolvePlaceholders(entry.getValue()));
            }
            return resolved;
        }
        if (value instanceof java.util.List<?> list) {
            return list.stream().map(this::resolvePlaceholders).toList();
        }
        return value;
    }

    private String resolveString(String input) {
        Matcher matcher = PLACEHOLDER.matcher(input);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String token = matcher.group(1);
            String arg = matcher.group(2);
            matcher.appendReplacement(result, Matcher.quoteReplacement(resolveToken(token, arg)));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private String resolveToken(String token, String arg) {
        return switch (token) {
            case "RANDOM_EMAIL" -> DataGenerator.randomEmail();
            case "RANDOM_STRING" -> DataGenerator.randomAlphanumeric(arg == null ? 8 : Integer.parseInt(arg));
            case "RANDOM_PHONE" -> DataGenerator.randomPhone();
            case "RANDOM_TITLE" -> DataGenerator.randomTitle();
            case "FUTURE_DATE" -> DataGenerator.futureDate(arg == null ? 30 : Integer.parseInt(arg));
            case "RANDOM_INT" -> resolveRandomInt(arg);
            default -> throw new ApiFrameworkException("Unknown test data placeholder: ${" + token + "}");
        };
    }

    private String resolveRandomInt(String arg) {
        if (arg == null || !arg.contains("-")) {
            throw new ApiFrameworkException("${RANDOM_INT:min-max} requires a min-max argument");
        }
        String[] bounds = arg.split("-", 2);
        return String.valueOf(DataGenerator.randomInt(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1])));
    }
}
```

---

### 8.8 apiframework — reporting

**`src/main/java/apiframework/reporting/ReportModel.java`**

```java
package apiframework.reporting;

import java.util.ArrayList;
import java.util.List;

/**
 * Plain data collected during a test run — no HTML, no third-party reporting library,
 * no rendering concerns. {@link ReportRecorder} fills this in as {@code ApiClient}/{@code
 * ApiResponse}/{@code TestReportListener} report what happened; {@link
 * HtmlReportRenderer} is the only class that turns it into a page, once, at suite end.
 */
final class ReportModel {

    enum Outcome { PASS, FAIL, SKIP }

    sealed interface TestEvent permits ApiCallEvent, AssertionEvent {
    }

    /**
     * One HTTP call made during a test. {@code endpoint} is the relative path (e.g.
     * {@code /events/{id}}) shown on the collapsed row; {@code url} is the full
     * absolute URL shown inside the expanded Request detail. Headers/bodies are
     * already redacted and truncated by the time they get here — this class doesn't
     * re-check either.
     */
    record ApiCallEvent(String method, String endpoint, String url, int statusCode, long durationMs,
                         String requestHeaders, String requestBody, String responseBody) implements TestEvent {
    }

    /**
     * One {@code verify*} check. {@code expected}/{@code actual} are what the report
     * shows as a plain "Expected / Actual" pair instead of a technical assertion
     * sentence; {@code detail} is optional extra context (e.g. a response body dump
     * on a status-code mismatch) shown only when present.
     */
    record AssertionEvent(String label, boolean passed, String expected, String actual, String detail) implements TestEvent {
    }

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
            this.name = name;
            this.description = description;
            this.groups = groups;
            this.module = module;
            this.startMillis = startMillis;
        }

        long durationMs() {
            return endMillis - startMillis;
        }
    }

    private ReportModel() {
    }
}
```

**`src/main/java/apiframework/reporting/ReportRecorder.java`**

```java
package apiframework.reporting;

import apiframework.config.ConfigManager;
import apiframework.reporting.ReportModel.ApiCallEvent;
import apiframework.reporting.ReportModel.AssertionEvent;
import apiframework.reporting.ReportModel.Outcome;
import apiframework.reporting.ReportModel.TestRecord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Collects one {@link TestRecord} per test method (thread-local — TestNG runs a given
 * test method start-to-finish on a single thread, even under {@code parallel="classes"})
 * and accumulates every finished record into one suite-wide list that {@link #flush()}
 * hands to {@link HtmlReportRenderer} to write once, at suite end.
 *
 * <p>This is the only class {@code ApiClient}, {@code ApiResponse} and {@code
 * TestReportListener} talk to for reporting — none of them (or anything outside this
 * package) knows {@link ReportModel} or HTML rendering exist.</p>
 */
public final class ReportRecorder {

    private static final long SUITE_START_MILLIS = System.currentTimeMillis();
    private static final List<TestRecord> COMPLETED = new CopyOnWriteArrayList<>();
    private static final ThreadLocal<TestRecord> CURRENT = new ThreadLocal<>();

    private ReportRecorder() {
    }

    public static void startTest(String name, String description, List<String> groups, String module) {
        CURRENT.set(new TestRecord(name, description, groups, module, System.currentTimeMillis()));
    }

    /** {@code true} once {@link #startTest} has run for the calling thread and before its matching {@link #finishTest}. */
    public static boolean hasActiveTest() {
        return CURRENT.get() != null;
    }

    public static void logApiCall(String method, String endpoint, String url, int statusCode, long durationMs,
                                   String requestHeaders, String requestBody, String responseBody) {
        TestRecord test = CURRENT.get();
        if (test != null) {
            test.events.add(new ApiCallEvent(method, endpoint, url, statusCode, durationMs,
                    requestHeaders, requestBody, responseBody));
        }
    }

    public static void logAssertion(String label, boolean passed, String expected, String actual, String detail) {
        TestRecord test = CURRENT.get();
        if (test != null) {
            test.events.add(new AssertionEvent(label, passed, expected, actual, detail));
        }
    }

    public static void finishTest(boolean passed, boolean skipped, String errorMessage) {
        TestRecord test = CURRENT.get();
        if (test == null) {
            return;
        }
        test.endMillis = System.currentTimeMillis();
        test.outcome = skipped ? Outcome.SKIP : (passed ? Outcome.PASS : Outcome.FAIL);
        test.errorMessage = errorMessage;
        COMPLETED.add(test);
        CURRENT.remove();
    }

    /** Writes the whole suite's collected results to disk as one HTML report. Safe to call more than once — each call re-renders the current snapshot. */
    public static void flush() {
        HtmlReportRenderer.render(ConfigManager.getInstance(), SUITE_START_MILLIS, System.currentTimeMillis(), List.copyOf(COMPLETED));
    }
}
```

**`src/main/java/apiframework/reporting/TestReportListener.java`**

```java
package apiframework.reporting;

import apiframework.context.ScenarioContext;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;
import java.util.Map;

/**
 * Wires TestNG's test lifecycle to {@link ReportRecorder}. This is the only place
 * that starts/finishes a test record or flushes the report — testers write plain
 * {@code @Test} methods and get a full report (module grouping, every request/
 * response made, every assertion, failure details) for free.
 *
 * <p>{@code ApiClient} and {@code ApiResponse} log each request/response and each
 * assertion straight to the current test record as they happen, so the report reads
 * in the same order the test actually ran: call, then the checks made against it.
 * This listener only owns the record's start/end and the module a test belongs to.</p>
 *
 * <p>Self-registers via {@code META-INF/services/org.testng.ITestNGListener} — no
 * {@code testng.xml} or {@code @Listeners} annotation needed anywhere.</p>
 */
public class TestReportListener implements ITestListener, ISuiteListener {

    /** Domain tag -&gt; display name for module grouping. A tag not listed here falls back to a title-cased version of itself (see {@link #moduleFor}), so a new domain tag doesn't need a code change to show up correctly grouped — just less prettily named until added here. */
    private static final Map<String, String> MODULE_NAMES = Map.of(
            "auth", "Authentication",
            "bookings", "Bookings",
            "events", "Events",
            "health", "Health & Config",
            "e2e", "End-to-End"
    );

    @Override
    public void onTestStart(ITestResult result) {
        List<String> groups = List.of(result.getMethod().getGroups());
        String description = result.getMethod().getDescription();
        ReportRecorder.startTest(result.getMethod().getMethodName(),
                description == null ? "" : description, groups, moduleFor(groups));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportRecorder.finishTest(true, false, null);
        finishTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable t = result.getThrowable();
        ReportRecorder.finishTest(false, false, t == null ? "Test failed" : String.valueOf(t.getMessage()));
        finishTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // A test can be skipped before onTestStart ever ran for it (e.g. a failed
        // @BeforeClass skips every @Test in the class without TestNG ever calling
        // onTestStart) — start a record now so it still shows up on the report.
        if (!ReportRecorder.hasActiveTest()) {
            List<String> groups = List.of(result.getMethod().getGroups());
            ReportRecorder.startTest(result.getMethod().getMethodName(), "", groups, moduleFor(groups));
        }
        Throwable t = result.getThrowable();
        ReportRecorder.finishTest(false, true, t == null ? "Skipped" : t.getMessage());
        finishTest();
    }

    private void finishTest() {
        ScenarioContext.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        // no-op: report is flushed once per suite in onFinish(ISuite), not per <test>
    }

    @Override
    public void onFinish(org.testng.ISuite suite) {
        ReportRecorder.flush();
    }

    private static String moduleFor(List<String> groups) {
        for (String group : groups) {
            String mapped = MODULE_NAMES.get(group);
            if (mapped != null) {
                return mapped;
            }
        }
        for (String group : groups) {
            if (!group.equals("smoke") && !group.equals("regression")) {
                return Character.toUpperCase(group.charAt(0)) + group.substring(1);
            }
        }
        return "Other";
    }
}
```

**`src/main/java/apiframework/reporting/HtmlReportRenderer.java`**

```java
package apiframework.reporting;

import apiframework.config.ConfigManager;
import apiframework.reporting.ReportModel.ApiCallEvent;
import apiframework.reporting.ReportModel.AssertionEvent;
import apiframework.reporting.ReportModel.Outcome;
import apiframework.reporting.ReportModel.TestEvent;
import apiframework.reporting.ReportModel.TestRecord;
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

/**
 * Renders the suite's collected {@link TestRecord}s as one self-contained HTML file —
 * a Newman/Postman-style dashboard (summary cards, tests grouped by module, one
 * collapsed row per test, Expected/Actual instead of raw assertion sentences). Built
 * from scratch with no reporting library at all.
 *
 * <p>No external CSS/JS/fonts — everything is inlined, because this file has to open
 * standalone from disk (double-click, {@code file://}, a CI artifact download) with no
 * network access assumed.</p>
 *
 * <p>API-call status pills (200/400/500, ...) are colored purely by HTTP status class
 * for at-a-glance reading — they are <b>not</b> the test's pass/fail signal. A test
 * that deliberately asserts a 400/404 is exactly as green as one that asserts a 200;
 * only the Validations section (driven by actual {@code verify*} outcomes) and the
 * row's own check/cross icon decide red vs. green.</p>
 */
final class HtmlReportRenderer {

    private static final Logger log = LoggerFactory.getLogger(HtmlReportRenderer.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a").withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").withZone(ZoneId.systemDefault());
    private static final AtomicInteger ID_SEQ = new AtomicInteger();

    private HtmlReportRenderer() {
    }

    static void render(ConfigManager config, long suiteStartMillis, long suiteEndMillis, List<TestRecord> tests) {
        File reportDir = new File(System.getProperty("user.dir"), "reports");
        reportDir.mkdirs();
        String fileName = config.overwriteReport()
                ? "index.html"
                : "report-" + FILE_TIMESTAMP_FORMAT.format(Instant.ofEpochMilli(suiteStartMillis)) + ".html";
        File reportFile = new File(reportDir, fileName);

        String html = buildHtml(config, suiteStartMillis, suiteEndMillis, tests);
        try {
            Files.writeString(reportFile.toPath(), html, StandardCharsets.UTF_8);
            log.info("Report written to '{}'.", reportFile.getAbsolutePath());
        } catch (IOException e) {
            log.warn("Failed to write report to '{}': {}", reportFile.getAbsolutePath(), e.getMessage());
        }
    }

    private static String buildHtml(ConfigManager config, long suiteStartMillis, long suiteEndMillis, List<TestRecord> tests) {
        int total = tests.size();
        long passed = tests.stream().filter(t -> t.outcome == Outcome.PASS).count();
        long failed = tests.stream().filter(t -> t.outcome == Outcome.FAIL).count();
        long skipped = tests.stream().filter(t -> t.outcome == Outcome.SKIP).count();
        long durationMs = suiteEndMillis - suiteStartMillis;

        List<ApiCallEvent> calls = tests.stream()
                .flatMap(t -> t.events.stream())
                .filter(ApiCallEvent.class::isInstance)
                .map(ApiCallEvent.class::cast)
                .toList();
        double avgResponseMs = calls.isEmpty() ? 0 : calls.stream().mapToLong(ApiCallEvent::durationMs).average().orElse(0);
        ApiCallEvent slowest = calls.stream().max(Comparator.comparingLong(ApiCallEvent::durationMs)).orElse(null);

        Map<String, List<TestRecord>> byModule = new LinkedHashMap<>();
        for (TestRecord test : tests) {
            byModule.computeIfAbsent(test.module, m -> new java.util.ArrayList<>()).add(test);
        }

        StringBuilder html = new StringBuilder(64 * 1024);
        html.append("<!doctype html><html lang=\"en\"><head><meta charset=\"UTF-8\">")
            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
            .append("<title>").append(escape(config.getReportTitle())).append("</title>")
            .append("<style>").append(CSS).append("</style></head><body>");

        html.append("<header class=\"top\"><div class=\"top-inner\">")
            .append("<h1>").append(escape(config.getReportName())).append("</h1>")
            .append("<p class=\"meta\">Environment: <b>").append(escape(config.getEnvironment().name())).append("</b>")
            .append(" &nbsp;&middot;&nbsp; Base URL: <b>").append(escape(config.getBaseUrl())).append("</b>")
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
            for (TestRecord test : moduleTests) {
                html.append(renderTestRow(test));
            }
            html.append("</section>");
        }
        if (tests.isEmpty()) {
            html.append("<p class=\"empty\">No tests ran.</p>");
        }
        html.append("</main>");

        html.append("<p id=\"noMatches\" class=\"empty\" hidden>No tests match your filters.</p>");
        html.append("<footer class=\"foot\">Generated by RestAssuredTestNG</footer>");
        html.append("<script>").append(JS).append("</script>");
        html.append("</body></html>");
        return html.toString();
    }

    private static String renderTestRow(TestRecord test) {
        List<ApiCallEvent> calls = test.events.stream()
                .filter(ApiCallEvent.class::isInstance).map(ApiCallEvent.class::cast).toList();
        List<AssertionEvent> assertions = test.events.stream()
                .filter(AssertionEvent.class::isInstance).map(AssertionEvent.class::cast).toList();

        String icon = switch (test.outcome) {
            case PASS -> "✓";
            case FAIL -> "✗";
            case SKIP -> "⚠";
        };

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
        if (test.description != null && !test.description.isBlank()) {
            row.append("<p class=\"description\">").append(escape(test.description)).append("</p>");
        }
        if (test.outcome == Outcome.FAIL && test.errorMessage != null && !test.errorMessage.isBlank()) {
            row.append("<div class=\"failure-box\"><b>Error</b><pre>").append(escape(test.errorMessage)).append("</pre></div>");
        }
        if (test.outcome == Outcome.SKIP) {
            String reason = test.errorMessage != null && !test.errorMessage.isBlank() ? test.errorMessage : "No reason given.";
            row.append("<div class=\"skip-box\"><b>Skipped</b><pre>").append(escape(reason)).append("</pre></div>");
        }
        for (TestEvent event : test.events) {
            if (event instanceof ApiCallEvent call) {
                row.append(renderCall(call));
            }
        }
        if (!assertions.isEmpty()) {
            row.append("<div class=\"validations\"><b>Validations</b><ul>");
            for (AssertionEvent a : assertions) {
                row.append(renderAssertion(a));
            }
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
        if (call.requestHeaders() != null && !call.requestHeaders().isBlank()) {
            sb.append("<div class=\"headers\">").append(escape(call.requestHeaders()).replace("\n", "<br>")).append("</div>");
        }
        sb.append(jsonBlock(call.requestBody()));
        sb.append("</details>");

        sb.append("<details class=\"body-block\"><summary>Response</summary>");
        sb.append(jsonBlock(call.responseBody()));
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
        if (a.detail() != null && !a.detail().isBlank()) {
            sb.append("<div class=\"assertion-detail\">").append(escape(a.detail())).append("</div>");
        }
        sb.append("</li>");
        return sb.toString();
    }

    private static String jsonBlock(String content) {
        if (content == null || content.isBlank()) {
            return "<p class=\"empty\">(no body)</p>";
        }
        String id = "body-" + ID_SEQ.incrementAndGet();
        return "<div class=\"json-block\"><button type=\"button\" class=\"copy\" onclick=\"copyBlock('" + id + "', this)\">Copy</button>"
             + "<pre id=\"" + id + "\">" + escape(content) + "</pre></div>";
    }

    private static String statusPill(int statusCode) {
        String cls = statusCode >= 500 ? "s5xx" : statusCode >= 400 ? "s4xx" : statusCode >= 300 ? "s3xx" : "s2xx";
        return "<span class=\"status-pill " + cls + "\">" + statusCode + "</span>";
    }

    private static String card(String value, String label, String tone) {
        return "<div class=\"card " + tone + "\"><div class=\"card-value\">" + value + "</div>"
             + "<div class=\"card-label\">" + escape(label) + "</div></div>";
    }

    /** {@code smoke}/{@code regression} first (they're the run-selector tags every test tends to carry), then the rest alphabetically. */
    private static List<String> distinctTags(List<TestRecord> tests) {
        TreeSet<String> tags = new TreeSet<>();
        for (TestRecord t : tests) {
            tags.addAll(t.groups);
        }
        List<String> ordered = new java.util.ArrayList<>();
        for (String priority : List.of("smoke", "regression")) {
            if (tags.remove(priority)) {
                ordered.add(priority);
            }
        }
        ordered.addAll(tags);
        return ordered;
    }

    private static String formatDuration(long ms) {
        if (ms < 1000) {
            return ms + " ms";
        }
        return String.format("%.1f s", ms / 1000.0);
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private static final String CSS = """
            :root{--bg:#f4f5f7;--panel:#fff;--border:#e2e5ea;--text:#1f2430;--muted:#6b7280;
                --green:#16a34a;--green-bg:#ecfdf3;--red:#dc2626;--red-bg:#fef2f2;
                --amber:#d97706;--amber-bg:#fffbeb;--blue:#2563eb;--blue-bg:#eff6ff;--grey-bg:#f1f2f4;}
            *{box-sizing:border-box;}
            body{margin:0;font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Helvetica,Arial,sans-serif;
                background:var(--bg);color:var(--text);font-size:14px;}
            .top{background:#111827;color:#fff;}
            .top-inner{max-width:1100px;margin:0 auto;padding:20px 24px;}
            .top h1{margin:0 0 6px;font-size:20px;}
            .top .meta{margin:0;color:#cbd5e1;font-size:13px;}
            .summary{max-width:1100px;margin:16px auto 0;padding:0 24px;display:grid;
                grid-template-columns:repeat(5,1fr);gap:12px;}
            .card{background:var(--panel);border:1px solid var(--border);border-radius:8px;
                padding:14px;text-align:center;}
            .card-value{font-size:24px;font-weight:700;}
            .card-label{color:var(--muted);font-size:12px;text-transform:uppercase;letter-spacing:.04em;}
            .card.pass .card-value{color:var(--green);}
            .card.fail .card-value{color:var(--red);}
            .card.skip .card-value{color:var(--amber);}
            .perf{max-width:1100px;margin:12px auto 0;padding:10px 24px;display:flex;gap:24px;
                background:var(--panel);border:1px solid var(--border);border-radius:8px;
                margin-left:auto;margin-right:auto;font-size:13px;color:var(--muted);}
            .perf b{color:var(--text);}
            .toolbar{max-width:1100px;margin:16px auto 0;padding:0 24px;display:flex;gap:12px;
                align-items:center;flex-wrap:wrap;}
            #search{flex:1;min-width:220px;padding:8px 12px;border:1px solid var(--border);
                border-radius:6px;font-size:13px;}
            .tags{display:flex;gap:6px;flex-wrap:wrap;}
            .tag{border:1px solid var(--border);background:var(--panel);color:var(--muted);
                border-radius:999px;padding:5px 12px;font-size:12px;cursor:pointer;}
            .tag.active{background:var(--blue);border-color:var(--blue);color:#fff;}
            .modules{max-width:1100px;margin:16px auto 40px;padding:0 24px;}
            .module{margin-bottom:20px;}
            .module h2{font-size:14px;margin:0 0 8px;color:var(--text);}
            .module-count{color:var(--muted);font-weight:400;font-size:12px;}
            .test-row{background:var(--panel);border:1px solid var(--border);border-left:4px solid var(--border);
                border-radius:6px;margin-bottom:8px;}
            .test-row.outcome-pass{border-left-color:var(--green);}
            .test-row.outcome-fail{border-left-color:var(--red);}
            .test-row.outcome-skip{border-left-color:var(--amber);}
            .test-row summary{list-style:none;cursor:pointer;padding:10px 14px;display:flex;
                align-items:center;gap:10px;}
            .test-row summary::-webkit-details-marker{display:none;}
            .test-row .icon{font-weight:700;width:16px;text-align:center;}
            .outcome-pass .icon{color:var(--green);}
            .outcome-fail .icon{color:var(--red);}
            .outcome-skip .icon{color:var(--amber);}
            .test-name{flex:1;font-weight:500;}
            .row-right{display:flex;align-items:center;gap:10px;color:var(--muted);font-size:12px;}
            .method{font-family:ui-monospace,SFMono-Regular,Menlo,monospace;font-weight:700;color:var(--blue);}
            .endpoint,.url{font-family:ui-monospace,SFMono-Regular,Menlo,monospace;}
            .status-pill{border-radius:4px;padding:2px 7px;font-size:11px;font-weight:700;}
            .status-pill.s2xx{background:var(--green-bg);color:var(--green);}
            .status-pill.s3xx{background:var(--blue-bg);color:var(--blue);}
            .status-pill.s4xx{background:var(--amber-bg);color:var(--amber);}
            .status-pill.s5xx{background:var(--red-bg);color:var(--red);}
            .detail{padding:0 14px 14px 40px;border-top:1px solid var(--border);}
            .description{color:var(--muted);font-style:italic;margin:10px 0;}
            .failure-box{background:var(--red-bg);border:1px solid #fecaca;border-radius:6px;
                padding:10px 12px;margin:10px 0;}
            .failure-box pre{white-space:pre-wrap;margin:6px 0 0;font-size:12px;}
            .skip-box{background:var(--amber-bg);border:1px solid #fde68a;border-radius:6px;
                padding:10px 12px;margin:10px 0;}
            .skip-box pre{white-space:pre-wrap;margin:6px 0 0;font-size:12px;}
            .call{margin:10px 0;padding:10px;background:var(--grey-bg);border-radius:6px;}
            .call-line{display:flex;align-items:center;gap:10px;font-size:12px;margin-bottom:6px;}
            .req-res{display:flex;gap:10px;flex-wrap:wrap;}
            .body-block{flex:1;min-width:240px;background:var(--panel);border:1px solid var(--border);
                border-radius:6px;}
            .body-block summary{cursor:pointer;padding:6px 10px;font-size:12px;font-weight:600;
                color:var(--muted);list-style:none;}
            .body-block summary::-webkit-details-marker{display:none;}
            .headers{font-family:ui-monospace,SFMono-Regular,Menlo,monospace;font-size:11px;
                padding:0 10px 8px;color:var(--muted);}
            .json-block{position:relative;}
            .json-block pre{margin:0;padding:10px;font-size:12px;white-space:pre-wrap;word-break:break-word;
                max-height:320px;overflow:auto;border-top:1px solid var(--border);}
            .json-block .copy{position:absolute;top:6px;right:8px;font-size:11px;border:1px solid var(--border);
                background:var(--panel);border-radius:4px;padding:2px 8px;cursor:pointer;}
            .validations{margin-top:12px;}
            .validations ul{list-style:none;margin:8px 0 0;padding:0;}
            .validations li{padding:8px 10px;border-radius:6px;margin-bottom:4px;font-size:13px;}
            .validations li.pass{background:var(--green-bg);}
            .validations li.fail{background:var(--red-bg);}
            .validations .icon{font-weight:700;}
            .validations li.pass .icon{color:var(--green);}
            .validations li.fail .icon{color:var(--red);}
            .expected-actual{display:flex;gap:20px;margin-top:4px;padding-left:22px;font-size:12px;color:var(--muted);}
            .expected-actual code{background:rgba(0,0,0,.06);border-radius:3px;padding:1px 5px;color:var(--text);}
            .assertion-detail{padding-left:22px;margin-top:4px;font-size:12px;color:var(--muted);white-space:pre-wrap;}
            .empty{text-align:center;color:var(--muted);padding:24px;}
            .foot{text-align:center;color:var(--muted);font-size:12px;padding:20px;}
            @media(max-width:720px){.summary{grid-template-columns:repeat(2,1fr);}.perf{flex-direction:column;gap:6px;}}
            """;

    private static final String JS = """
            function activeTags(){
                return Array.from(document.querySelectorAll('.tag.active')).map(function(b){return b.dataset.tag;});
            }
            function toggleTag(btn){
                btn.classList.toggle('active');
                filterTests();
            }
            function filterTests(){
                var query = document.getElementById('search').value.trim().toLowerCase();
                var tags = activeTags();
                var rows = document.querySelectorAll('.test-row');
                var anyVisible = false;
                rows.forEach(function(row){
                    var matchesSearch = !query || row.dataset.search.indexOf(query) !== -1;
                    var rowTags = row.dataset.tags.split(' ');
                    var matchesTags = tags.length === 0 || tags.some(function(t){return rowTags.indexOf(t) !== -1;});
                    var visible = matchesSearch && matchesTags;
                    row.hidden = !visible;
                    if (visible) { anyVisible = true; }
                });
                document.querySelectorAll('.module').forEach(function(section){
                    var hasVisible = Array.from(section.querySelectorAll('.test-row')).some(function(r){return !r.hidden;});
                    section.hidden = !hasVisible;
                });
                document.getElementById('noMatches').hidden = anyVisible;
            }
            function copyBlock(id, btn){
                var text = document.getElementById(id).textContent;
                var done = function(){
                    var original = btn.textContent;
                    btn.textContent = 'Copied!';
                    setTimeout(function(){ btn.textContent = original; }, 1200);
                };
                if (navigator.clipboard && navigator.clipboard.writeText) {
                    navigator.clipboard.writeText(text).then(done, function(){});
                }
            }
            """;
}
```

---

### 8.9 src/main/resources

**`src/main/resources/META-INF/services/org.testng.ITestNGListener`**

```text
apiframework.reporting.TestReportListener
```

**`src/main/resources/logback.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/framework.log</file>
        <append>false</append>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Keep third-party libraries quiet; everything else (framework + any
         application code under test/) logs at INFO by default via root. -->
    <logger name="org.testng" level="WARN"/>
    <logger name="io.restassured" level="WARN"/>
    <logger name="org.apache.http" level="WARN"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>
```

---

### 8.10 config/*.properties

**`config/qa.properties`**

```properties
# QA environment configuration
# This is the real, publicly available EventHub sandbox API used for automated testing.
base.url=https://api.eventhub.rahulshettyacademy.com/api
request.timeout.ms=15000
retry.count=2
retry.delay.ms=500

# Report branding (read by apiframework.reporting.HtmlReportRenderer)
report.title=EventHub API Automation Report
report.name=EventHub API Automation Framework
# true = every run overwrites reports/index.html; false = each run gets its own
# reports/report-<timestamp>.html instead.
report.overwrite=true
```

**`config/staging.properties`**

```properties
# STAGING environment configuration
# Example environment showing how a separate deployment would be targeted.
# The EventHub training API only exposes one public deployment, so this file
# points at the same host as qa.properties — swap base.url when a real
# staging deployment exists. No code changes are needed elsewhere.
base.url=https://api.eventhub.rahulshettyacademy.com/api
request.timeout.ms=15000
retry.count=2
retry.delay.ms=500

report.title=EventHub API Automation Report (Staging)
report.name=EventHub API Automation Framework
# true = every run overwrites reports/index.html; false = each run gets its own
# reports/report-<timestamp>.html instead.
report.overwrite=true
```

**`config/prod.properties`**

```properties
# PROD environment configuration
# Example environment. Points at the same public EventHub host as qa/staging —
# swap base.url when a real production deployment exists. No code changes
# are needed elsewhere; select it with -Denv=prod.
base.url=https://api.eventhub.rahulshettyacademy.com/api
request.timeout.ms=20000
retry.count=1
retry.delay.ms=500

report.title=EventHub API Automation Report (Prod)
report.name=EventHub API Automation Framework
# true = every run overwrites reports/index.html; false = each run gets its own
# reports/report-<timestamp>.html instead.
report.overwrite=true
```

---

### 8.11 secrets/secrets.properties.example

```properties
# Template only — copy this file to secrets.properties (same folder) and fill in
# real values there. secrets.properties is gitignored and must never be committed;
# only this .example file is tracked.
#
# SecretStore (apiframework.secrets.SecretStore) reads a key from, in order:
#   1. Environment variable (key upper-cased, non-alphanumerics -> "_")
#   2. JVM system property (-Dkey=value)
#   3. This file, or its per-environment override secrets/<env>.secrets.properties
#      (e.g. secrets/prod.secrets.properties)
#
# Prefer env vars / system properties in CI; use these files for local runs only.

# Optional fixed password for the sandbox user AuthManager registers per test class.
# Leave unset to let the framework generate a fresh random password per thread (the default).
sandboxUser.password=
```

---

### 8.12 eventhub — the bundled example application

**`src/test/java/eventhub/auth/AuthManager.java`**

```java
package eventhub.auth;

import apiframework.data.DataGenerator;
import apiframework.secrets.SecretStore;
import eventhub.services.AuthService;

/**
 * Centralized authentication for the EventHub application. EventHub gives every
 * registered user a fully isolated data sandbox, so this leans on that: each
 * thread gets its own throwaway account, registered once and cached for reuse.
 * That gives parallel test classes complete data isolation for free — no manual
 * token plumbing, no shared mutable state, no cross-test contamination.
 *
 * <p>This class is application-specific (it knows EventHub's register contract)
 * and lives under {@code eventhub}, not the generic framework.</p>
 */
public final class AuthManager {

    private static final String SECRETS_DIR = "eventhub/secrets";
    /** Optional override: env var SANDBOX_USER_PASSWORD, system property, or secrets file key. */
    private static final String PASSWORD_SECRET_KEY = "sandboxUser.password";

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> EMAIL = new ThreadLocal<>();

    private final AuthService authService;
    private final SecretStore secretStore;

    public AuthManager(AuthService authService) {
        this(authService, new SecretStore(SECRETS_DIR));
    }

    public AuthManager(AuthService authService, SecretStore secretStore) {
        this.authService = authService;
        this.secretStore = secretStore;
    }

    /** Returns the cached token for this thread, registering a fresh sandbox user if needed. */
    public String getToken() {
        if (TOKEN.get() == null) {
            registerNewSandboxUser();
        }
        return TOKEN.get();
    }

    public int getUserId() {
        getToken();
        return USER_ID.get();
    }

    public String getEmail() {
        getToken();
        return EMAIL.get();
    }

    /** Discards the cached identity for this thread; the next {@link #getToken()} registers a new one. */
    public void reset() {
        TOKEN.remove();
        USER_ID.remove();
        EMAIL.remove();
    }

    private void registerNewSandboxUser() {
        String email = DataGenerator.randomEmail();
        // A fixed password can be supplied (env var/system property/secrets file, see SecretStore)
        // for environments that need deterministic/audited sandbox credentials; otherwise a fresh
        // random one is generated per thread, same as before. Either way it's never logged in
        // plaintext — ApiClient redacts "password" fields before anything reaches the report.
        String password = secretStore.getOptional(PASSWORD_SECRET_KEY, "Fw@" + DataGenerator.randomAlphanumeric(8));

        var body = java.util.Map.<String, Object>of("email", email, "password", password);
        var response = authService.register(body).verifyStatusCode(201);

        TOKEN.set(response.extract("token"));
        USER_ID.set(response.extract("user.id"));
        EMAIL.set(email);
    }
}
```

**`src/test/java/eventhub/base/BaseTest.java`**

```java
package eventhub.base;

import apiframework.core.ApiClient;
import apiframework.data.TestDataManager;
import eventhub.auth.AuthManager;
import eventhub.services.AuthService;
import eventhub.services.BookingService;
import eventhub.services.EventService;
import eventhub.services.HealthService;
import org.testng.annotations.BeforeClass;

/**
 * Everything a test class needs, already wired: services to call and test data
 * to read. Extending this is the only setup a tester has to do — no request
 * specs, no token handling, no reporting boilerplate.
 *
 * <p>One {@link AuthManager} sandbox user is registered per test class (per
 * thread, under TestNG's {@code parallel="classes"} execution) so classes never
 * see each other's events/bookings.</p>
 *
 * <p>This class, and everything under {@code eventhub}, is the only
 * EventHub-specific part of the project. To reuse the framework (under
 * {@code apiframework} in src/main) for a different API, delete this package
 * and the {@code eventhub} test resources and write a new one.</p>
 */
public abstract class BaseTest {

    /** Classpath folder (relative to the test resources root) holding this application's JSON test data. */
    private static final String TEST_DATA_DIR = "eventhub/testdata";

    protected final ApiClient apiClient = new ApiClient();
    protected final AuthService authService = new AuthService(apiClient);
    protected final EventService eventService = new EventService(apiClient);
    protected final BookingService bookingService = new BookingService(apiClient);
    protected final HealthService healthService = new HealthService(apiClient);
    protected final TestDataManager testData = new TestDataManager(TEST_DATA_DIR);
    protected final AuthManager authManager = new AuthManager(authService);

    protected String token;

    @BeforeClass(alwaysRun = true)
    public void authenticateSandboxUser() {
        token = authManager.getToken();
    }
}
```

**`src/test/java/eventhub/models/Event.java`**

```java
package eventhub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Typed view of an EventHub event, for tests that prefer {@code response.as(Event.class)}
 * over raw JSONPath. Field set mirrors the {@code Event} schema in the API docs.
 * Note: {@code price} comes back from the API as a numeric string (e.g. "1500"),
 * not a JSON number — modeled as String here to match actual behavior.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private Integer id;
    private String title;
    private String description;
    private String category;
    private String venue;
    private String city;
    private String eventDate;
    private String price;
    private Integer totalSeats;
    private Integer availableSeats;
    private String imageUrl;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
```

**`src/test/java/eventhub/models/Booking.java`**

```java
package eventhub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Typed view of an EventHub booking. Note: {@code totalPrice} is returned by the
 * API as a numeric string, modeled as String here to match actual behavior.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

    private Integer id;
    private Integer eventId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Integer quantity;
    private String totalPrice;
    private String status;
    private String bookingRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }
}
```

**`src/test/java/eventhub/services/AuthService.java`**

```java
package eventhub.services;

import apiframework.core.ApiClient;
import apiframework.core.ApiRequest;
import apiframework.core.ApiResponse;

import java.util.Map;

/**
 * Business-facing wrapper around {@code /auth/*}. Tests call these methods and
 * never build a request specification or manage headers themselves.
 */
public class AuthService {

    private final ApiClient apiClient;

    public AuthService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse register(Map<String, Object> body) {
        return apiClient.execute(ApiRequest.post("/auth/register").body(body));
    }

    public ApiResponse login(Map<String, Object> body) {
        return apiClient.execute(ApiRequest.post("/auth/login").body(body));
    }

    public ApiResponse me(String token) {
        return apiClient.execute(ApiRequest.get("/auth/me").bearerToken(token));
    }

    public ApiResponse meWithoutToken() {
        return apiClient.execute(ApiRequest.get("/auth/me"));
    }
}
```

**`src/test/java/eventhub/services/EventService.java`**

```java
package eventhub.services;

import apiframework.core.ApiClient;
import apiframework.core.ApiRequest;
import apiframework.core.ApiResponse;

import java.util.Map;

/**
 * Business-facing wrapper around {@code /events*}. All event endpoints require
 * a bearer token in practice (each account only sees its own created events,
 * plus the platform's shared seed events).
 */
public class EventService {

    private final ApiClient apiClient;

    public EventService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse list(String token, Map<String, ?> queryParams) {
        return apiClient.execute(ApiRequest.get("/events").bearerToken(token).queryParams(queryParams));
    }

    public ApiResponse listWithoutToken() {
        return apiClient.execute(ApiRequest.get("/events"));
    }

    public ApiResponse create(String token, Map<String, Object> body) {
        return apiClient.execute(ApiRequest.post("/events").bearerToken(token).body(body));
    }

    public ApiResponse getById(String token, int id) {
        return apiClient.execute(ApiRequest.get("/events/{id}").bearerToken(token).pathParam("id", id));
    }

    public ApiResponse update(String token, int id, Map<String, Object> body) {
        return apiClient.execute(ApiRequest.put("/events/{id}").bearerToken(token).pathParam("id", id).body(body));
    }

    public ApiResponse delete(String token, int id) {
        return apiClient.execute(ApiRequest.delete("/events/{id}").bearerToken(token).pathParam("id", id));
    }
}
```

**`src/test/java/eventhub/services/BookingService.java`**

```java
package eventhub.services;

import apiframework.core.ApiClient;
import apiframework.core.ApiRequest;
import apiframework.core.ApiResponse;

import java.util.Map;

/**
 * Business-facing wrapper around {@code /bookings*}.
 */
public class BookingService {

    private final ApiClient apiClient;

    public BookingService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse list(String token, Map<String, ?> queryParams) {
        return apiClient.execute(ApiRequest.get("/bookings").bearerToken(token).queryParams(queryParams));
    }

    public ApiResponse listWithoutToken() {
        return apiClient.execute(ApiRequest.get("/bookings"));
    }

    public ApiResponse create(String token, Map<String, Object> body) {
        return apiClient.execute(ApiRequest.post("/bookings").bearerToken(token).body(body));
    }

    public ApiResponse getById(String token, int id) {
        return apiClient.execute(ApiRequest.get("/bookings/{id}").bearerToken(token).pathParam("id", id));
    }

    public ApiResponse getByRef(String token, String ref) {
        return apiClient.execute(ApiRequest.get("/bookings/ref/{ref}").bearerToken(token).pathParam("ref", ref));
    }

    public ApiResponse cancel(String token, int id) {
        return apiClient.execute(ApiRequest.delete("/bookings/{id}").bearerToken(token).pathParam("id", id));
    }
}
```

**`src/test/java/eventhub/services/HealthService.java`**

```java
package eventhub.services;

import apiframework.core.ApiClient;
import apiframework.core.ApiRequest;
import apiframework.core.ApiResponse;

/**
 * Business-facing wrapper around the public, unauthenticated {@code /health}
 * and {@code /config} endpoints.
 */
public class HealthService {

    private final ApiClient apiClient;

    public HealthService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse health() {
        return apiClient.execute(ApiRequest.get("/health"));
    }

    public ApiResponse config() {
        return apiClient.execute(ApiRequest.get("/config"));
    }
}
```

**`src/test/java/eventhub/tests/AuthTests.java`**

```java
package eventhub.tests;

import eventhub.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Coverage for {@code /auth/register}, {@code /auth/login} and {@code /auth/me}:
 * positive registration/login flows plus the validation and authorization
 * failures around them.
 */
public class AuthTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "auth"}, description = "A new user can register and receives a usable JWT")
    public void registerNewUser_returns201AndToken() {
        Map<String, Object> body = testData.getMap("users.validUser");

        authService.register(body)
                .verifyStatusCode(201)
                .verifyField("success", true)
                .verifyFieldExists("token")
                .verifyFieldExists("user.id")
                .verifyField("user.email", body.get("email"));
    }

    @Test(groups = {"regression", "auth"}, description = "Registering the same email twice is rejected")
    public void registerDuplicateEmail_returns400() {
        Map<String, Object> body = testData.getMap("users.validUser");

        authService.register(body).verifyStatusCode(201);
        authService.register(body)
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"regression", "auth"}, description = "Registering with an invalid email format fails validation")
    public void registerInvalidEmail_returns400Validation() {
        authService.register(testData.getMap("users.invalidEmailUser"))
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"regression", "auth"}, description = "Registering with a too-short password fails validation")
    public void registerShortPassword_returns400Validation() {
        authService.register(testData.getMap("users.shortPasswordUser"))
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "auth"}, description = "A registered user can log back in and receives a token")
    public void login_validCredentials_returns200AndToken() {
        Map<String, Object> user = testData.getMap("users.validUser");
        authService.register(user).verifyStatusCode(201);

        authService.login(user)
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyFieldExists("token")
                .verifyField("user.email", user.get("email"));
    }

    @Test(groups = {"regression", "auth"}, description = "Logging in with the wrong password is rejected")
    public void login_wrongPassword_returns400() {
        Map<String, Object> user = testData.getMap("users.validUser");
        authService.register(user).verifyStatusCode(201);

        authService.login(Map.of("email", user.get("email"), "password", "TotallyWrongPassword"))
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"regression", "auth"}, description = "Logging in with an email that was never registered is rejected")
    public void login_nonExistentUser_returns400() {
        authService.login(Map.of("email", "nobody.here@example.com", "password", "Secret123"))
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "auth"}, description = "GET /auth/me with a valid token returns the token's identity")
    public void authMe_withValidToken_returnsUser() {
        authService.me(token)
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyField("user.userId", authManager.getUserId())
                .verifyField("user.email", authManager.getEmail());
    }

    @Test(groups = {"regression", "auth"}, description = "GET /auth/me without a token is rejected")
    public void authMe_withoutToken_returns401() {
        authService.meWithoutToken()
                .verifyStatusCode(401)
                .verifyField("success", false);
    }
}
```

**`src/test/java/eventhub/tests/EventsTests.java`**

```java
package eventhub.tests;

import eventhub.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Coverage for {@code /events} CRUD: positive create/read/update/delete flows,
 * validation failures, the undocumented-but-real 401 when no token is sent,
 * and one JSON schema validation example.
 */
public class EventsTests extends BaseTest {

    @Test(groups = {"regression", "events"}, description = "Listing events without a bearer token is rejected")
    public void getEvents_withoutToken_returns401() {
        eventService.listWithoutToken()
                .verifyStatusCode(401)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "events"}, description = "Creating an event with all required fields succeeds and echoes them back")
    public void createEvent_returns201WithMatchingFields() {
        Map<String, Object> body = testData.getMap("events.validEvent");

        eventService.create(token, body)
                .verifyStatusCode(201)
                .verifyField("success", true)
                .verifyField("data.title", body.get("title"))
                .verifyField("data.city", body.get("city"))
                .verifyField("data.availableSeats", body.get("totalSeats"))
                .verifyFieldExists("data.id");
    }

    @Test(groups = {"regression", "events"}, description = "Creating an event missing every required field fails validation")
    public void createEvent_missingRequiredFields_returns400Validation() {
        eventService.create(token, testData.getMap("events.missingRequiredFieldsEvent"))
                .verifyStatusCode(400)
                .verifyField("success", false)
                .verifyFieldExists("details");
    }

    @Test(groups = {"smoke", "regression", "events"}, description = "A created event can be fetched by its ID and matches the JSON schema")
    public void getEventById_returnsEvent() {
        int id = createEvent(testData.getMap("events.validEvent"));

        eventService.getById(token, id)
                .verifyStatusCode(200)
                .verifyField("data.id", id)
                .verifyJsonSchema("eventhub/schemas/event-schema.json");
    }

    @Test(groups = {"regression", "events"}, description = "Fetching a non-existent event returns 404")
    public void getEventById_notFound_returns404() {
        eventService.getById(token, 999_999_999)
                .verifyStatusCode(404)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "events"}, description = "Updating an event persists the new field values")
    public void updateEvent_returns200WithUpdatedFields() {
        int id = createEvent(testData.getMap("events.validEvent"));
        Map<String, Object> updated = testData.getMap("events.updatedEvent");

        eventService.update(token, id, updated)
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyField("data.title", updated.get("title"))
                .verifyField("data.venue", updated.get("venue"))
                .verifyField("data.city", updated.get("city"));

        eventService.getById(token, id)
                .verifyStatusCode(200)
                .verifyField("data.title", updated.get("title"));
    }

    @Test(groups = {"smoke", "regression", "events"}, description = "A deleted event is permanently gone")
    public void deleteEvent_thenGetReturns404() {
        int id = createEvent(testData.getMap("events.validEvent"));

        eventService.delete(token, id)
                .verifyStatusCode(200)
                .verifyField("success", true);

        eventService.getById(token, id)
                .verifyStatusCode(404);
    }

    @Test(groups = {"regression", "events"}, description = "Listing events with a category filter returns only matching events")
    public void listEvents_withCategoryFilter_returnsOnlyMatchingCategory() {
        createEvent(testData.getMap("events.validEvent")); // ensure at least one Workshop event exists

        eventService.list(token, Map.of("category", "Workshop", "limit", 50))
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyEachElement("data", "category", "Workshop");
    }

    @Test(groups = {"regression", "events"}, description = "Listing events with a city filter returns only matching events")
    public void listEvents_withCityFilter_returnsOnlyMatchingCity() {
        createEvent(testData.getMap("events.validEvent")); // ensure at least one Bangalore event exists

        eventService.list(token, Map.of("city", "Bangalore", "limit", 50))
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyEachElement("data", "city", "Bangalore");
    }

    private int createEvent(Map<String, Object> body) {
        return eventService.create(token, body)
                .verifyStatusCode(201)
                .extract("data.id");
    }
}
```

**`src/test/java/eventhub/tests/BookingsTests.java`**

```java
package eventhub.tests;

import eventhub.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Coverage for {@code /bookings}: positive booking/cancellation flows, seat
 * accounting (creating a booking decrements {@code availableSeats}, cancelling
 * restores it), and the validation/not-found failures around them.
 */
public class BookingsTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "bookings"}, description = "Booking tickets succeeds and decrements the event's available seats")
    public void createBooking_returns201AndDecrementsSeats() {
        int eventId = createEvent();
        int seatsBefore = eventService.getById(token, eventId).extract("data.availableSeats");

        Map<String, Object> body = new java.util.HashMap<>(testData.getMap("bookings.validBooking"));
        body.put("eventId", eventId);
        int quantity = (int) body.get("quantity");

        bookingService.create(token, body)
                .verifyStatusCode(201)
                .verifyField("success", true)
                .verifyField("data.eventId", eventId)
                .verifyField("data.status", "confirmed")
                .verifyField("data.quantity", quantity)
                .verifyFieldExists("data.bookingRef");

        eventService.getById(token, eventId)
                .verifyField("data.availableSeats", seatsBefore - quantity);
    }

    @Test(groups = {"regression", "bookings"}, description = "Booking more seats than available fails validation")
    public void createBooking_excessiveQuantity_returns400Validation() {
        int eventId = createEvent();

        Map<String, Object> body = new java.util.HashMap<>(testData.getMap("bookings.excessiveQuantityBooking"));
        body.put("eventId", eventId);

        bookingService.create(token, body)
                .verifyStatusCode(400)
                .verifyField("success", false);
    }

    @Test(groups = {"regression", "bookings"}, description = "Booking a non-existent event returns 404")
    public void createBooking_nonExistentEvent_returns404() {
        Map<String, Object> body = new java.util.HashMap<>(testData.getMap("bookings.validBooking"));
        body.put("eventId", 999_999_999);

        bookingService.create(token, body)
                .verifyStatusCode(404)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "bookings"}, description = "A booking can be fetched by its numeric ID")
    public void getBookingById_returnsBooking() {
        int bookingId = createBooking(createEvent());

        bookingService.getById(token, bookingId)
                .verifyStatusCode(200)
                .verifyField("data.id", bookingId);
    }

    @Test(groups = {"smoke", "regression", "bookings"}, description = "A booking can be fetched by its reference code")
    public void getBookingByRef_returnsBooking() {
        int eventId = createEvent();
        String ref = bookingService.create(token, bookingBody(eventId))
                .verifyStatusCode(201)
                .extract("data.bookingRef");

        bookingService.getByRef(token, ref)
                .verifyStatusCode(200)
                .verifyField("data.bookingRef", ref)
                .verifyField("data.eventId", eventId);
    }

    @Test(groups = {"regression", "bookings"}, description = "Looking up an unknown booking reference returns 404")
    public void getBookingByRef_notFound_returns404() {
        bookingService.getByRef(token, "NOPE-999999")
                .verifyStatusCode(404)
                .verifyField("success", false);
    }

    @Test(groups = {"smoke", "regression", "bookings"}, description = "Cancelling a booking restores the event's available seats")
    public void cancelBooking_returns200AndRestoresSeats() {
        int eventId = createEvent();
        int seatsBefore = eventService.getById(token, eventId).extract("data.availableSeats");

        Map<String, Object> body = bookingBody(eventId);
        int quantity = (int) body.get("quantity");
        int bookingId = bookingService.create(token, body).verifyStatusCode(201).extract("data.id");

        bookingService.cancel(token, bookingId)
                .verifyStatusCode(200)
                .verifyField("success", true);

        eventService.getById(token, eventId)
                .verifyField("data.availableSeats", seatsBefore);

        // sanity: the quantity that was released matches what was booked
        org.testng.Assert.assertTrue(quantity > 0);
    }

    @Test(groups = {"regression", "bookings"}, description = "Listing bookings filtered by eventId returns only bookings for that event")
    public void listBookings_filterByEventId_returnsOnlyThatEvent() {
        int eventId = createEvent();
        createBooking(eventId);

        bookingService.list(token, Map.of("eventId", eventId, "limit", 50))
                .verifyStatusCode(200)
                .verifyField("success", true)
                .verifyEachElement("data", "eventId", eventId);
    }

    private int createEvent() {
        return eventService.create(token, testData.getMap("events.validEvent"))
                .verifyStatusCode(201)
                .extract("data.id");
    }

    private int createBooking(int eventId) {
        return bookingService.create(token, bookingBody(eventId))
                .verifyStatusCode(201)
                .extract("data.id");
    }

    private Map<String, Object> bookingBody(int eventId) {
        Map<String, Object> body = new java.util.HashMap<>(testData.getMap("bookings.validBooking"));
        body.put("eventId", eventId);
        return body;
    }
}
```

**`src/test/java/eventhub/tests/E2EFlowTests.java`**

```java
package eventhub.tests;

import apiframework.context.ScenarioContext;
import eventhub.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * End-to-end flows that chain several API calls together, the way a real
 * EventHub user session would. Values (event ID, booking ref, seat counts)
 * move between steps via {@link ScenarioContext} — nothing is hardcoded and
 * nothing leaks into a shared static field.
 */
public class E2EFlowTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "e2e"},
            description = "Register -> create event -> book tickets -> verify seats -> cancel -> verify seats restored -> booking is gone")
    public void bookingLifecycle_endToEnd() {
        // Step 1: create an event (organizer side)
        Map<String, Object> eventBody = testData.getMap("events.validEvent");
        int eventId = eventService.create(token, eventBody)
                .verifyStatusCode(201)
                .extract("data.id");
        ScenarioContext.set("eventId", eventId);
        int totalSeats = ((Number) eventBody.get("totalSeats")).intValue();

        // Step 2: confirm the freshly created event starts fully available
        eventService.getById(token, eventId)
                .verifyStatusCode(200)
                .verifyField("data.availableSeats", totalSeats);

        // Step 3: book tickets (customer side) and propagate the booking ID/ref
        Map<String, Object> bookingBody = testData.getMap("bookings.validBooking");
        bookingBody.put("eventId", eventId);
        int quantity = (int) bookingBody.get("quantity");

        var bookingResponse = bookingService.create(token, bookingBody)
                .verifyStatusCode(201)
                .verifyField("data.status", "confirmed");
        int bookingId = bookingResponse.extract("data.id");
        String bookingRef = bookingResponse.extract("data.bookingRef");
        ScenarioContext.set("bookingId", bookingId);
        ScenarioContext.set("bookingRef", bookingRef);

        // Step 4: seats were atomically decremented
        eventService.getById(token, eventId)
                .verifyField("data.availableSeats", totalSeats - quantity);

        // Step 5: the booking is independently retrievable by both ID and reference
        bookingService.getById(token, ScenarioContext.<Integer>get("bookingId"))
                .verifyStatusCode(200)
                .verifyField("data.bookingRef", bookingRef);
        bookingService.getByRef(token, ScenarioContext.get("bookingRef"))
                .verifyStatusCode(200)
                .verifyField("data.id", bookingId);

        // Step 6: cancel the booking
        bookingService.cancel(token, bookingId)
                .verifyStatusCode(200)
                .verifyField("success", true);

        // Step 7: seats are atomically restored...
        eventService.getById(token, eventId)
                .verifyField("data.availableSeats", totalSeats);

        // Step 8: ...and the cancelled booking no longer exists
        bookingService.getById(token, bookingId)
                .verifyStatusCode(404);

        // Cleanup: remove the event created for this scenario
        eventService.delete(token, eventId).verifyStatusCode(200);
    }

    @Test(groups = {"regression", "e2e"},
            description = "Create event -> update it -> verify the update stuck -> delete it -> verify it is gone")
    public void eventLifecycle_createUpdateDelete_endToEnd() {
        int eventId = eventService.create(token, testData.getMap("events.validEvent"))
                .verifyStatusCode(201)
                .extract("data.id");
        ScenarioContext.set("eventId", eventId);

        Map<String, Object> updatedFields = testData.getMap("events.updatedEvent");
        eventService.update(token, eventId, updatedFields)
                .verifyStatusCode(200)
                .verifyField("data.title", updatedFields.get("title"));

        eventService.getById(token, eventId)
                .verifyStatusCode(200)
                .verifyField("data.title", updatedFields.get("title"))
                .verifyField("data.venue", updatedFields.get("venue"));

        eventService.delete(token, eventId).verifyStatusCode(200);

        eventService.getById(token, eventId).verifyStatusCode(404);
    }
}
```

**`src/test/java/eventhub/tests/HealthAndConfigTests.java`**

```java
package eventhub.tests;

import eventhub.base.BaseTest;
import org.testng.annotations.Test;

/**
 * Public, unauthenticated endpoints that report platform status.
 */
public class HealthAndConfigTests extends BaseTest {

    @Test(groups = {"smoke", "health"}, description = "GET /health reports the API and DB as up")
    public void healthCheck_returnsOkStatus() {
        healthService.health()
                .verifyStatusCode(200)
                .verifyField("status", "ok")
                .verifyField("dbStatus", "connected")
                .verifyFieldExists("timestamp");
    }

    @Test(groups = {"smoke", "health"}, description = "GET /config exposes public feature flags")
    public void getConfig_returnsFeatureFlags() {
        healthService.config()
                .verifyStatusCode(200)
                .verifyFieldExists("showExploreLinks");
    }
}
```

---

### 8.13 eventhub test resources

**`src/test/resources/eventhub/testdata/events.json`**

```json
{
  "validEvent": {
    "title": "${RANDOM_TITLE}",
    "description": "Created by the automated API test suite.",
    "category": "Workshop",
    "venue": "Automation Test Hall",
    "city": "Bangalore",
    "eventDate": "${FUTURE_DATE:45}",
    "price": 499,
    "totalSeats": 25,
    "imageUrl": "https://example.com/banner.jpg"
  },
  "minimalEvent": {
    "title": "${RANDOM_TITLE}",
    "category": "Conference",
    "venue": "Minimal Venue",
    "city": "Pune",
    "eventDate": "${FUTURE_DATE:60}",
    "price": 0,
    "totalSeats": 1
  },
  "updatedEvent": {
    "title": "${RANDOM_TITLE} (Updated)",
    "description": "Updated by the automated API test suite.",
    "category": "Workshop",
    "venue": "Updated Test Hall",
    "city": "Chennai",
    "eventDate": "${FUTURE_DATE:50}",
    "price": 599,
    "totalSeats": 30
  },
  "missingRequiredFieldsEvent": {
    "description": "Missing every required field."
  }
}
```

**`src/test/resources/eventhub/testdata/bookings.json`**

```json
{
  "validBooking": {
    "customerName": "Priya Sharma",
    "customerEmail": "${RANDOM_EMAIL}",
    "customerPhone": "${RANDOM_PHONE}",
    "quantity": 2
  },
  "singleSeatBooking": {
    "customerName": "Rahul Verma",
    "customerEmail": "${RANDOM_EMAIL}",
    "customerPhone": "${RANDOM_PHONE}",
    "quantity": 1
  },
  "excessiveQuantityBooking": {
    "customerName": "Greedy Customer",
    "customerEmail": "${RANDOM_EMAIL}",
    "customerPhone": "${RANDOM_PHONE}",
    "quantity": 999
  }
}
```

**`src/test/resources/eventhub/testdata/users.json`**

```json
{
  "validUser": {
    "email": "${RANDOM_EMAIL}",
    "password": "Secret123"
  },
  "shortPasswordUser": {
    "email": "${RANDOM_EMAIL}",
    "password": "123"
  },
  "invalidEmailUser": {
    "email": "not-an-email",
    "password": "Secret123"
  }
}
```

**`src/test/resources/eventhub/schemas/event-schema.json`**

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "EventResponse",
  "type": "object",
  "required": ["success", "data"],
  "properties": {
    "success": { "type": "boolean" },
    "data": {
      "type": "object",
      "required": ["id", "title", "category", "venue", "city", "totalSeats", "availableSeats"],
      "properties": {
        "id": { "type": "integer" },
        "title": { "type": "string" },
        "category": { "type": "string" },
        "venue": { "type": "string" },
        "city": { "type": "string" },
        "totalSeats": { "type": "integer" },
        "availableSeats": { "type": "integer" }
      }
    }
  }
}
```

---

### 8.14 .gitignore (relevant sections)

Only the sections relevant to this framework — the full file also has the usual
Maven/IDE/OS boilerplate:

```gitignore
# Test Reports
test-output/
allure-results/
allure-report/
extent-reports/
reports/
screenshots/
logs/

# Environment variables
.env
.env.local
.env.*.local
env.yaml
env.yml

# Security
*.key
*.pem
*.p12
*.jks
credentials.json
secrets.yaml

# Secrets (apiframework.secrets.SecretStore) — real values never committed,
# only the *.properties.example templates are.
secrets/*.properties
!secrets/*.properties.example
```

This is the piece that makes Step 5 (secrets) actually safe in practice: every
real `secrets/*.properties` file is ignored, while the `.example` template stays
tracked so the expected keys are documented for the next person.

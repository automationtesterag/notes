# Building This Framework From Scratch — Complete, Self-Contained Guide

Everything you need to reproduce this Playwright UI+API automation framework is on this
one page: no other file in the repo is assumed. It's ordered as a real build sequence —
each phase ends in something you can run — and every file below is the **complete**
source, not an excerpt, with a short "why" before the code.

**Stack:** TypeScript (`strict`, CommonJS/Node16 resolution) · `@playwright/test` ·
Winston (logging) · `dotenv`/`yaml`/`csv-parse`/`exceljs` (test data) · Allure
(reporting) · one browser project (`chromium`).

**What you end up with:** two independently runnable test layers — UI (Page Object
Model, against a demo e-commerce site) and API (service-based, against a demo events
API) — sharing one test-data system (JSON/YAML/CSV/Excel, interchangeable), one
secrets-resolution path, one logger, and one CI workflow.

## Table of contents

- [Phase 0 — Project scaffold and tooling](#phase-0--project-scaffold-and-tooling)
- [Phase 1 — Environment configuration](#phase-1--environment-configuration)
- [Phase 2 — Shared utilities and constants](#phase-2--shared-utilities-and-constants)
- [Phase 3 — Test data layer](#phase-3--test-data-layer)
- [Phase 3a — Secrets](#phase-3a--secrets)
- [Phase 4 — UI layer (Page Object Model)](#phase-4--ui-layer-page-object-model)
- [Phase 5 — API layer (service-based)](#phase-5--api-layer-service-based)
- [Phase 6 — Reporting](#phase-6--reporting)
- [Phase 7 — npm scripts recap](#phase-7--npm-scripts-recap)
- [Phase 8 — CI](#phase-8--ci)
- [Final directory tree](#final-directory-tree)
- [Build order at a glance](#build-order-at-a-glance)

---

## Phase 0 — Project scaffold and tooling

```bash
npm init -y
npm install --save-dev typescript ts-node @playwright/test cross-env
npm install winston dotenv yaml csv-parse exceljs allure-js-commons
npm install --save-dev allure-commandline allure-playwright eslint @eslint/js \
  eslint-plugin-playwright @babel/core @babel/eslint-parser @babel/preset-typescript
npx playwright install --with-deps chromium
```

**Why these choices up front:** `"type": "commonjs"` paired with `module`/`moduleResolution:
"Node16"` in `tsconfig.json` is what lets `require.resolve(...)` work for
`globalSetup`/`globalTeardown` (Phase 1) while still writing ESM-style `import`/`export`
everywhere else. `strict` mode plus the four extra compiler checks below are far cheaper
to start with than to retrofit later. Only one Playwright project (`chromium`) is
configured — this framework deliberately doesn't run cross-browser.

### `package.json`

```json
{
  "name": "playwright-framework",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "type": "commonjs",
  "scripts": {
    "pretest": "rm -rf allure-results",
    "test": "playwright test",
    "presmoke": "rm -rf allure-results",
    "smoke": "playwright test --grep @smoke",
    "preregression": "rm -rf allure-results",
    "regression": "playwright test --grep @regression",
    "preapi": "rm -rf allure-results",
    "api": "playwright test tests/api",
    "preui": "rm -rf allure-results",
    "ui": "playwright test tests/authentication tests/checkout",
    "typecheck": "tsc --noEmit",
    "lint": "eslint .",
    "lint:fix": "eslint . --fix",
    "allure:generate": "allure generate allure-results --clean",
    "allure:open": "allure open allure-report",
    "report": "playwright show-report",
    "allure-report": "npm run allure:generate && npm run allure:open"
  },
  "dependencies": {
    "allure-js-commons": "^3.10.2",
    "csv-parse": "^7.0.1",
    "dotenv": "^17.4.2",
    "exceljs": "^4.4.0",
    "winston": "^3.19.0",
    "yaml": "^2.9.0"
  },
  "devDependencies": {
    "@babel/core": "^7.28.0",
    "@babel/eslint-parser": "^7.28.0",
    "@babel/preset-typescript": "^7.27.1",
    "@eslint/js": "^9.39.5",
    "@playwright/test": "^1.61.1",
    "allure-commandline": "^2.43.0",
    "allure-playwright": "^3.10.2",
    "cross-env": "^10.1.0",
    "eslint": "^9.39.5",
    "eslint-plugin-playwright": "^2.11.0",
    "ts-node": "^10.9.2",
    "typescript": "^7.0.2"
  },
  "keywords": [],
  "author": "",
  "license": "ISC"
}
```

**What this file does:** it's the single source of truth for how the project is run,
not just what's installed. `"type": "commonjs"` tells Node to interpret every `.js`
Node would touch as CommonJS (irrelevant here since nothing is emitted — `noEmit` in
`tsconfig.json` — but it's what keeps `ts-node`/`require.resolve` semantics consistent
with the TS compiler options below). `main: "index.js"` is a no-op placeholder — this
package is never `require()`'d by anything else, it's an executable test suite, not a
library — `npm init -y` puts it there and nothing removes it.

**Why the scripts are structured as `pre<script>` pairs:** npm auto-runs a script named
`pre<name>` immediately before `npm run <name>` — no explicit wiring needed, it's a
built-in npm lifecycle convention. `rm -rf allure-results` has to run *before* each
Playwright invocation, not after, because `allure-playwright` (registered as a reporter
in `playwright.config.ts`, Phase 1) appends result files into `allure-results/` as
tests run; if a stale directory from a previous run under a different filter (`--grep
@smoke` vs the full suite) is left behind, `npm run allure-report` would generate a
report mixing tests that didn't actually run this time with ones that did. `npm test`
itself doesn't need its own explicit filter because Playwright's default is "run
everything under `testDir`" — the plain `playwright test` in the `test` script.

**Why versions are pinned with `^` and dependencies are split dependencies/devDependencies:**
`dependencies` holds everything the actual test *runtime* imports (`winston`, `dotenv`,
`yaml`, `csv-parse`, `exceljs`, `allure-js-commons`) — code that runs inside a test
process. `devDependencies` holds the toolchain that only runs during development/CI
orchestration (`typescript`, `@playwright/test` itself, ESLint, Babel, the Allure CLI,
`cross-env`, `ts-node`). The split matters if this package were ever installed as a
dependency elsewhere (`npm install --production` skips `devDependencies`) — it isn't
here, but keeping the split honest is what makes `npm ci` in CI (Phase 8) reproducible
and keeps a reviewer able to tell "test framework code" from "used-only-locally tooling"
at a glance. `cross-env` is present specifically so `TEST_ENV=dev npm run ui`-style
commands (Phase 7) work identically on Windows, where bare `KEY=value command` shell
syntax isn't supported.

### `tsconfig.json`

```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "Node16",
    "moduleResolution": "Node16",
    "lib": ["ES2022"],
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true,
    "resolveJsonModule": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noImplicitReturns": true,
    "noFallthroughCasesInSwitch": true,
    "noEmit": true
  },
  "include": ["src/**/*.ts", "tests/**/*.ts", "config/**/*.ts", "playwright.config.ts"],
  "exclude": ["node_modules", "allure-report", "allure-results", "test-results", "playwright-report"]
}
```

**What each option buys you, and why it's set this way:**

- `target: "ES2022"` / `lib: ["ES2022"]` — the framework only ever runs under a modern
  Node LTS (Node 20 in CI, Phase 8), so there's no reason to downlevel syntax to an
  older ECMAScript target; `ES2022` gives access to things like top-level `await`-free
  class fields without a transpilation tax, and since `noEmit` is set nothing is
  actually downleveled anyway — this only affects which built-in types/globals `lib`
  makes visible to the type checker.
- `module: "Node16"` / `moduleResolution: "Node16"` — this pair makes TypeScript's
  module resolution algorithm match Node's own CommonJS `require()` resolution exactly
  (as opposed to the older, looser `"node"` resolution mode). It's what makes
  `require.resolve("./config/global.setup")` in `playwright.config.ts` (Phase 1)
  type-check correctly and behave the same way at runtime as it does in the type
  checker — a mismatch here is a common source of "works when I run it, but `tsc`
  complains" bugs in mixed CJS/ESM-syntax codebases.
- `strict: true` — turns on the full bundle (`strictNullChecks`, `noImplicitAny`,
  `strictFunctionTypes`, etc.) in one flag. Concretely, this is why `TokenManager.getToken()`
  (Phase 5) can declare its return type as `string` instead of `string | undefined` —
  the class explicitly throws instead, and `strictNullChecks` is what forces that
  discipline everywhere a value might be missing rather than silently allowing
  `undefined` to flow into code that assumes a string.
- `esModuleInterop: true` — lets `import winston from "winston"` (a CommonJS package
  with no `default` export) work without `import * as winston from "winston"`
  boilerplate everywhere. Without it, half the `import` statements in this framework
  (`winston`, `YAML`, `dotenv`) would need the verbose form.
- `skipLibCheck: true` — skips type-checking inside `.d.ts` files pulled from
  `node_modules`. Purely a build-speed/pragmatism setting: this project doesn't own
  those files, so there's nothing actionable if a third-party type definition has an
  issue, and re-checking them on every `tsc` run adds up.
- `noUnusedLocals` / `noUnusedParameters` / `noImplicitReturns` /
  `noFallthroughCasesInSwitch` — four extra strictness flags *not* bundled into
  `strict`. Each catches a specific class of bug this framework's code shape is prone
  to: `noUnusedLocals` catches a destructured import left over after a refactor;
  `noImplicitReturns` matters directly for something like
  `ApiEngine.createException`'s `switch` (Phase 5), where every branch must explicitly
  return an exception instance — a forgotten `return` there would silently produce
  `undefined` instead of a thrown error; `noFallthroughCasesInSwitch` guards the same
  `switch` against an accidentally-missing `break`/`return` letting a `400` fall
  through into the `401`/`403` case.
- `noEmit: true` — this project never compiles to JS on disk; `ts-node`/Playwright's
  own runtime transform run `.ts` files directly. `tsc`'s only job here is to *check*,
  which is exactly what `npm run typecheck` calls it for.

`include` is scoped deliberately to `src/`, `tests/`, `config/`, and the config file
itself — not the whole repo — so generated output (`playwright-report/`,
`allure-results/`, `test-results/`, and anything under `node_modules`) never gets
type-checked; those directories don't contain source TypeScript and including them
would slow every `tsc` invocation for no benefit. `npm run typecheck` is added
immediately, before there's any real application code, specifically so CI (Phase 8)
enforces it starting with the very first commit rather than retrofitting a type-check
gate onto a codebase that's already accumulated violations.

### `eslint.config.mjs`

```js
import js from "@eslint/js";
import playwright from "eslint-plugin-playwright";

export default [
  {
    ignores: [
      "node_modules/**",
      "allure-report/**",
      "allure-results/**",
      "playwright-report/**",
      "test-results/**",
      "logs/**",
    ],
  },
  {
    // eslint:recommended is tuned for plain JS ASTs and must never touch .ts files (see below).
    files: ["**/*.{js,mjs,cjs}"],
    ...js.configs.recommended,
  },
  {
    // TypeScript 7 (this project's pinned compiler) isn't supported by typescript-eslint yet
    // (github.com/typescript-eslint/typescript-eslint/issues/10940), so .ts files are parsed via
    // Babel instead — syntax/style linting only, type errors are already caught by `npm run typecheck`.
    files: ["**/*.ts"],
    languageOptions: {
      parser: (await import("@babel/eslint-parser")).default,
      parserOptions: {
        requireConfigFile: false,
        babelOptions: {
          presets: ["@babel/preset-typescript"],
        },
        sourceType: "module",
      },
    },
    // eslint:recommended assumes plain JS function/getter shapes and crashes on
    // TypeScript-only constructs (abstract methods, overload signatures, interface
    // members) that have no body — so TS files get a small, hand-verified rule set
    // instead of the full recommended config. Real type errors are tsc's job
    // (`npm run typecheck`); this is a style/quality pass on top.
    rules: {
      "no-undef": "off",
      // Not "no-unused-vars": the Babel parser has no type-checker, so it can't tell a
      // type-only import/parameter (e.g. `page: Page`) from a genuinely unused one and
      // flags nearly every typed signature in this codebase as a false positive.
      // tsconfig.json already enables noUnusedLocals/noUnusedParameters, so `npm run
      // typecheck` catches real unused-variable bugs correctly.
      "no-var": "error",
      "prefer-const": "warn",
      eqeqeq: ["error", "smart"],
      "no-duplicate-imports": "error",
      "no-console": "warn",
    },
  },
  {
    files: ["tests/**/*.ts"],
    ...playwright.configs["flat/recommended"],
    rules: {
      ...playwright.configs["flat/recommended"].rules,
      // Assertions in this framework are centralized in src/validators/*.ts (see below)
      // and called as e.g. `LoginValidator.expectLoginSuccess(...)` — the rule only sees
      // the bare `expect(...)` call by default, so teach it this project's naming convention.
      "playwright/expect-expect": ["warn", { assertFunctionPatterns: ["^expect"] }],
    },
  },
];
```

**Why this file exists at all, beyond `tsc`:** `npm run typecheck` catches type errors,
but it has no opinion on style or on non-type bugs like `==` vs `===`, duplicate
imports, or accidental `var`. ESLint fills that gap. The three-block structure
(plain JS → all `.ts` → `tests/**/*.ts` on top) is a layered configuration where later
blocks in the flat-config array add to, rather than replace, earlier ones for files
they also match:

1. The first `.{js,mjs,cjs}` block applies `eslint:recommended` — this file itself
   (`eslint.config.mjs`) is plain JS, so it's the only thing this block actually lints.
2. The `**/*.ts` block is where almost everything in `src/`, `config/`, and `tests/`
   gets linted, but deliberately *not* via `eslint:recommended` — that ruleset assumes
   plain-JS shapes (every function has a body, every property has a value) and throws
   parser-level errors on TypeScript-only syntax with no body, like an abstract method
   signature in `BaseDataProvider` (Phase 3: `protected abstract parse<T>(filePath:
   string): Promise<T>;`) or an interface member. So instead of pulling in
   `typescript-eslint`'s type-aware parser (blocked here because this project pins
   TypeScript 7, ahead of what `typescript-eslint` currently supports), `.ts` files are
   parsed with Babel's TypeScript preset — good enough to build an AST for style rules,
   with no actual type information. That's why the ruleset here is small and
   hand-picked rather than a wholesale `recommended` set: every rule had to be manually
   checked for false positives against a parser that can't see types.
3. The `tests/**/*.ts` block layers Playwright's own recommended rules on top (catching
   things like a `test()` with no assertions, or `await` missing on a Playwright API
   call) — scoped to `tests/` only, since page objects and services in `src/` aren't
   Playwright test bodies and the plugin's heuristics don't apply to them.

The `expect-expect` override is the one rule that needed reconfiguring rather than just
accepting the plugin default: this framework never calls bare `expect(...)` inside a
spec (see `src/validators/*.ts`, Phase 4) — assertions are always a call like
`LoginValidator.expectLoginSuccess(...)`. Without `assertFunctionPatterns`, the plugin
would flag every single test in this suite as having no assertions, since it only
recognizes the literal `expect` call by default.

### `.gitignore`

```gitignore
# Dependencies
node_modules/

# Environment Variables
.env
.env.*
!.env.example

# Secrets (real credential values — see config/secrets/*.env.example)
config/secrets/*.env
!config/secrets/*.env.example

# Playwright Reports
playwright-report/
test-results/
blob-report/
*.png
*.webm
*.zip
*.trace

# Allure
allure-results/
allure-report/

# Coverage
coverage/
.nyc_output/

# Build Output
dist/
build/
out/

# Logs
logs/
*.log
npm-debug.log*
yarn-debug.log*
yarn-error.log*
pnpm-debug.log*

# TypeScript
*.tsbuildinfo

# IDE
.idea/
*.iml
.vscode/*
!.vscode/extensions.json
!.vscode/settings.json
!.vscode/launch.json
!.vscode/tasks.json

# Cache / package managers / runtime / DB / OS / misc
.cache/
tmp/
temp/
.pnpm-store/
.npm/
.yarn/
*.pid
*.pid.lock
*.sqlite
*.db
.DS_Store
.AppleDouble
.LSOverride
Thumbs.db
Desktop.ini
*.bak
*.swp
*.swo
```

**Why two separate secrets-ignore rules instead of one:** `.env` / `.env.* !
.env.example` is the generic, conventional rule most Node projects have — it protects
anything matching that naming pattern anywhere in the tree. `config/secrets/*.env !
config/secrets/*.env.example` is a second, narrower rule specific to this framework's
own secrets convention (Phase 3a), which lives one directory deeper and uses a
different naming pattern (`qa.env`, `dev.env` — an environment name, not a bare
`.env`). Git's ignore-pattern matching is purely path/glob based with no semantic
understanding, so the generic rule doesn't automatically cover the nested one; both
have to be declared, each paired with its own `!`-negated exception so the *template*
file (`*.env.example`) stays trackable while the *filled-in* file next to it does not.
Forgetting either exception line is the single easiest way to accidentally either (a)
never let a new teammate see what keys they need to fill in, or (b) commit a real
credential the moment someone runs `git add -A` after filling in their local file.

**Why the rest of the file is organized as blocks instead of scattered as it's needed:**
each block maps to one artifact-producing tool this framework's own scripts invoke —
Playwright itself (`playwright-report/`, `test-results/`, screenshots/videos/traces
from `use.screenshot`/`trace`/`video` in `playwright.config.ts`), Allure
(`allure-results/`, `allure-report/` — the two directories `npm run allure-report`,
Phase 7, generates into and reads from), the TypeScript compiler's incremental-build
cache (`*.tsbuildinfo` — not actually produced here since `noEmit: true`, but harmless
to ignore preemptively), and Winston's own output (`logs/`, from `Logger.ts`, Phase 2).
Grouping by "what tool wrote this" rather than alphabetizing makes it obvious, when a
new tool is added later, exactly where its ignore rule belongs.

**Checkpoint:** `npm run typecheck` passes on an empty `src/`.

---

## Phase 1 — Environment configuration

Everything else in the framework reads settings through one frozen `ENV` object, so
build that path before anything else.

### `config/environments/qa.env`

```env
#########################################
# Application
#########################################

BASE_URL=https://rahulshettyacademy.com/client
API_BASE_URL=https://api.eventhub.rahulshettyacademy.com
TEST_DATA_FORMAT=csv

#########################################
# Browser
#########################################

HEADLESS=false
SLOW_MO=0

#########################################
# Execution
#########################################

DEFAULT_TIMEOUT=30000
EXPECT_TIMEOUT=10000

#########################################
# Viewport
#########################################

VIEWPORT_WIDTH=1440
VIEWPORT_HEIGHT=900

#########################################
# Locale
#########################################

LOCALE=en-US
TIMEZONE=Asia/Kolkata
```

**What every key here is for, and why it lives in a `.env` file rather than being
hardcoded in `playwright.config.ts`:** these are the values that legitimately change
per environment (QA vs dev vs staging vs prod-like) without any code change — that's
the entire point of the file. `BASE_URL`/`API_BASE_URL` point the UI and API layers at
different backend deployments; `TEST_DATA_FORMAT` is what lets the exact same test
suite be exercised against JSON here and YAML in `dev.env` as a live proof that the
four data providers (Phase 3) really are interchangeable, rather than that claim being
untested. The `HEADLESS`/`SLOW_MO`/timeout/viewport/locale/timezone keys all exist
because a *local* debugging run wants different values than a *CI* run — headed and
slowed down to watch it live locally, headless and full-speed in CI — and putting them
in env files means switching between those modes is a `TEST_ENV=<name>` away, with no
code edited and no risk of accidentally committing a debug setting. This is the file a
new environment (say, `staging`) always starts from: copy it, change `BASE_URL`/
`API_BASE_URL`, done — no code changes required anywhere else in the framework, which
is the whole reason `ENV` (built next) reads from files instead of being hardcoded.

### `config/environments/dev.env`

Same shape, different `TEST_DATA_FORMAT` (proves the four data formats are truly
interchangeable):

```env
BASE_URL=https://rahulshettyacademy.com/client
API_BASE_URL=https://api.eventhub.rahulshettyacademy.com
TEST_DATA_FORMAT=yaml

HEADLESS=false
SLOW_MO=0

DEFAULT_TIMEOUT=30000
EXPECT_TIMEOUT=10000

VIEWPORT_WIDTH=1440
VIEWPORT_HEIGHT=900

LOCALE=en-US
TIMEZONE=Asia/Kolkata
```

Add one `.env` file per environment name you need; `TEST_ENV` selects which one loads.
Notice this is a real, live test-data format switch, not just a documentation claim —
running the exact same specs with `TEST_ENV=qa` (CSV) versus `TEST_ENV=dev` (YAML) is
the concrete verification that `TestData.load()` (Phase 3) truly produces identical
objects regardless of source format.

### `config/envLoader.ts`

```ts
import dotenv from "dotenv";
import path from "path";

export type TestDataFormat = "json" | "yaml" | "csv" | "excel";

const environment = process.env.TEST_ENV ?? "qa";

dotenv.config({
  path: path.resolve(process.cwd(), `config/environments/${environment}.env`),
});

function required(key: string): string {
  const value = process.env[key];
  if (!value) {
    throw new Error(`Environment variable '${key}' is missing.`);
  }

  return value;
}

export const ENV = Object.freeze({
  ENVIRONMENT: environment,

  BASE_URL: required("BASE_URL"),

  API_BASE_URL: required("API_BASE_URL"),

  TEST_DATA_FORMAT: required("TEST_DATA_FORMAT") as TestDataFormat,

  HEADLESS: process.env.HEADLESS === "true",

  SLOW_MO: Number(process.env.SLOW_MO ?? 0),

  DEFAULT_TIMEOUT: Number(process.env.DEFAULT_TIMEOUT ?? 30000),

  EXPECT_TIMEOUT: Number(process.env.EXPECT_TIMEOUT ?? 10000),

  VIEWPORT: {
    width: Number(process.env.VIEWPORT_WIDTH ?? 1440),
    height: Number(process.env.VIEWPORT_HEIGHT ?? 900),
  },

  LOCALE: process.env.LOCALE ?? "en-US",

  TIMEZONE: process.env.TIMEZONE ?? "Asia/Kolkata",
});
```

**Line-by-line rationale:**

- `const environment = process.env.TEST_ENV ?? "qa"` — this line runs at *module load
  time*, before `dotenv.config()` below it. That ordering is deliberate: `TEST_ENV`
  itself has to come from the real process environment (however the test runner was
  invoked — `TEST_ENV=dev npm run ui`), not from a file, because it's the thing that
  decides *which* file to load next. `"qa"` as the fallback means the whole framework
  has a sane default and never requires `TEST_ENV` to be set explicitly for the common
  case.
- `dotenv.config({ path: ... })` — `dotenv.config()` with no `path` looks for a bare
  `.env` in the current working directory, which isn't this framework's layout at
  all (environments live under `config/environments/<name>.env`). Passing an explicit
  `path` is what makes the per-environment file structure work; `dotenv` merges what it
  reads into `process.env` as a side effect, which is why every other line in this file
  can immediately read `process.env.BASE_URL` etc. as if it had always been there.
  Critically, `dotenv.config()` **never overwrites a variable that's already set** in
  `process.env` — so if CI (Phase 8) has already exported `BASE_URL` some other way,
  the `.env` file's value is silently ignored in favor of it. That's a real, intentional
  behavior of the `dotenv` library this code relies on, not an edge case to work around.
- `function required(key)` — a tiny helper, but it's what turns "the app breaks with a
  confusing error three tests into the run because `BASE_URL` was `undefined`" into "the
  very first line of test output is `Environment variable 'BASE_URL' is missing.`". This
  fail-fast pattern is repeated deliberately in `secretsLoader.ts` (`Secrets.get`,
  Phase 3a) for the exact same reason: a missing config value should never propagate
  silently into a request as `undefined` or an empty string.
- `export const ENV = Object.freeze({...})` — computing every field once and freezing
  the whole object accomplishes two things: (1) it guarantees every consumer of `ENV`
  across the entire framework (`playwright.config.ts`, `BaseDataProvider`, `ApiEngine`
  via `ApiFixture`, the hooks, `secretsLoader.ts`) sees the exact same values for the
  life of the process — no risk of one file accidentally reassigning `ENV.HEADLESS`
  and another silently picking that up; (2) `Object.freeze` is a runtime guard, not
  just documentation — an attempt to write `ENV.BASE_URL = "..."` elsewhere in the
  codebase fails silently in non-strict mode or throws in strict mode, catching what
  would otherwise be a very hard-to-trace bug (global config mutated from some
  unrelated file).
- The nested `VIEWPORT: { width, height }` object is the one field that isn't a scalar
  — it's shaped that way because `playwright.config.ts`'s `use.viewport` expects exactly
  `{ width, height }`, so `ENV.VIEWPORT` can be passed straight through with no
  reshaping at the call site.
- Every optional key (`HEADLESS`, `SLOW_MO`, the timeouts, `LOCALE`, `TIMEZONE`) uses
  `??` with a concrete default rather than `required()` — these have safe, sensible
  defaults (headed locally, no slow-motion, 30s/10s timeouts, US locale, IST timezone)
  where forcing every environment file to redeclare them would just be noise; only the
  three values that genuinely differ in a way the framework *can't* guess safely
  (`BASE_URL`, `API_BASE_URL`, `TEST_DATA_FORMAT`) are `required()`.

### `playwright.config.ts`

```ts
import { defineConfig } from "@playwright/test";
import { ENV } from "./config/envLoader";

export default defineConfig({
  testDir: "./tests",
  testMatch: "**/*.spec.ts",

  timeout: ENV.DEFAULT_TIMEOUT,

  expect: {
    timeout: ENV.EXPECT_TIMEOUT,
  },

  fullyParallel: true,

  workers: process.env.CI ? 2 : undefined,

  retries: process.env.CI ? 2 : 0,

  reporter: [["list"], ["html", { open: "never" }], ["allure-playwright"]],

  use: {
    baseURL: ENV.BASE_URL,

    headless: ENV.HEADLESS,

    viewport: ENV.VIEWPORT,

    locale: ENV.LOCALE,

    timezoneId: ENV.TIMEZONE,

    ignoreHTTPSErrors: true,

    screenshot: "only-on-failure",

    trace: "retain-on-failure",

    video: "retain-on-failure",

    actionTimeout: 15000,

    navigationTimeout: 30000,

    launchOptions: {
      slowMo: ENV.SLOW_MO,
    },
  },

  projects: [
    {
      name: "chromium",

      use: {
        browserName: "chromium",
      },
    },
  ],

  outputDir: "test-results",

  globalSetup: require.resolve("./config/global.setup"),

  globalTeardown: require.resolve("./config/global.teardown"),
});
```

**Why each setting is what it is, not just what it does:**

- `testDir: "./tests"` / `testMatch: "**/*.spec.ts"` — this pair is what physically
  separates "test specs" from everything else in the repo. Only files under `tests/`
  matching `*.spec.ts` are ever picked up as test files — page objects, services,
  providers, and every other `.ts` file elsewhere in `src/` are plain modules that
  specs *import*, never executed by Playwright's own discovery. This is also why
  `npm run ui` and `npm run api` (Phase 7) can safely pass `tests/authentication
  tests/checkout` / `tests/api` as extra path filters — they're narrowing an
  already-scoped `testDir`, not redefining it.
- `timeout: ENV.DEFAULT_TIMEOUT` / `expect.timeout: ENV.EXPECT_TIMEOUT` — two *different*
  timeouts for two different failure modes. `timeout` bounds an entire test function
  (30s default) — if a test hangs on some unexpected app state, this is the backstop
  that kills it. `expect.timeout` (10s) bounds a single `expect(...)` assertion's
  auto-retrying poll — e.g. `expect(locator).toBeVisible()` retries for up to 10s before
  failing, not 30s, so a genuinely-failing assertion doesn't eat the entire test budget
  waiting on one check. Both are pulled from `ENV` rather than hardcoded so a slower
  environment (e.g. a staging deploy known to be laggy) can raise them via its `.env`
  file alone.
- `fullyParallel: true` — tests within the *same file*, not just across files, run
  concurrently across workers. This is what makes the strict "assertions live in
  validators, page objects hold no shared mutable state" discipline (Phase 4) actually
  matter: if two tests in `login.spec.ts` shared a page object instance or a module-level
  variable, running them in parallel would produce flaky cross-test interference. Each
  test instead gets its own fresh `page` (and therefore fresh page-object instances, via
  the `testFixture`) from Playwright itself.
- `workers: process.env.CI ? 2 : undefined` / `retries: process.env.CI ? 2 : 0` — locally,
  `undefined` lets Playwright auto-detect worker count from available CPU cores (fast
  iteration, and 0 retries means a flaky failure surfaces immediately instead of being
  silently retried away while debugging). In CI, workers are capped at 2 rather than
  left to auto-detect, because CI runners typically report more logical cores than they
  can actually give a browser-heavy workload without resource contention — an
  uncapped worker count in a constrained CI container tends to produce *more* flakiness,
  not less parallelism gained. Retries are set to 2 in CI specifically to absorb
  transient CI-only flakiness (a slow cold start, a momentary network hiccup) without
  masking a genuinely broken test, since `process.env.CI` being falsy locally means a
  developer debugging a failure always sees the true first-attempt result.
- `reporter: [["list"], ["html", {open: "never"}], ["allure-playwright"]]` — three
  reporters serving three different audiences at once: `list` streams pass/fail to the
  terminal as tests run (immediate feedback during a live run), `html` builds
  Playwright's own report for `npm run report` (Phase 7) with `open: "never"` so CI
  doesn't try to pop a browser window on a headless runner, and `allure-playwright`
  is what actually populates `allure-results/` for the richer Allure report (step-level
  detail via `AllureHelper`, Phase 6). All three run from the same test execution — no
  extra runs needed to get all three outputs.
- `use.baseURL` / `headless` / `viewport` / `locale` / `timezoneId` / `slowMo` — every
  one of these is read from `ENV`, not hardcoded, for the same reason the env files
  exist at all (Phase 1 intro): switching environments or debugging modes should never
  require editing this file.
- `ignoreHTTPSErrors: true` — the demo/QA backends this framework targets don't
  necessarily have production-grade TLS certs; without this, a self-signed or
  otherwise-imperfect cert would fail every single navigation before a test even
  starts.
- `screenshot: "only-on-failure"` / `trace: "retain-on-failure"` / `video:
  "retain-on-failure"` — all three are set to capture *only on failure*, not always.
  Capturing unconditionally would work but would balloon `test-results/` with
  artifacts nobody ever looks at for passing tests, slowing CI's artifact-upload step
  (Phase 8) for no diagnostic benefit; `retain-on-failure` keeps exactly the debugging
  material needed exactly when it's needed.
- `actionTimeout: 15000` / `navigationTimeout: 30000` — separate from `expect.timeout`:
  these bound a single Playwright *action* (a click, a fill) and a single *navigation*
  (a `page.goto`), independent of both the test-level timeout and the assertion-level
  timeout. A slow page load shouldn't be attributed to a flaky assertion, and a stuck
  click shouldn't be allowed to silently consume the entire navigation budget — three
  separate knobs for three separate kinds of "this took too long."
- `projects: [{ name: "chromium", use: { browserName: "chromium" } }]` — a single
  project is a deliberate scope decision, not an oversight: this framework doesn't
  claim cross-browser coverage, and adding Firefox/WebKit projects later is exactly
  this — one more entry in this array — with zero other code changes required, since
  every page object/locator was written against standard web APIs rather than
  browser-specific behavior.
- `globalSetup`/`globalTeardown` via `require.resolve(...)` rather than a relative
  string path — `require.resolve` returns the file's *resolved absolute path*, which is
  what Playwright's config actually expects here (a resolvable module path, evaluated
  once at config-load time) — this is also the concrete reason the CommonJS/Node16
  module setup from `tsconfig.json` had to be gotten right first: `require.resolve`
  only works correctly when the project's module resolution genuinely matches Node's.

### `config/global.setup.ts`

```ts
import fs from "fs";
import { ENV } from "./envLoader";

async function globalSetup() {
  console.log("\n=====================================");
  console.log(" PLAYWRIGHT FRAMEWORK");
  console.log("=====================================\n");
  console.log("BASE URL:", ENV.BASE_URL);

  const folders = ["logs", "test-results", "allure-results"];

  folders.forEach((folder) => {
    fs.mkdirSync(folder, { recursive: true });
  });
}

export default globalSetup;
```

### `config/global.teardown.ts`

```ts
async function globalTeardown() {
  console.log("\n=====================================");
  console.log(" EXECUTION FINISHED");
  console.log("=====================================\n");
}

export default globalTeardown;
```

**Why these two files exist as separate, tiny modules rather than being inlined
somewhere:** Playwright runs `globalSetup` exactly once, in a dedicated process, before
any worker spins up — and `globalTeardown` exactly once after every worker has
finished. That "runs exactly once regardless of worker count" guarantee is precisely
why folder creation belongs here and not, say, in `beforeEachHook` (Phase 4): creating
`logs/`, `test-results/`, `allure-results/` in a per-test hook would mean the first
several parallel workers all race to `mkdir` the same directories simultaneously
(harmless with `{ recursive: true }`, but wasteful busywork repeated on every single
test instead of once for the whole run). `fs.mkdirSync(..., { recursive: true })`
specifically means the setup never fails on a fresh checkout where none of these
directories exist yet, and never fails on a second run where they already do — no
existence check needed.

The console banners in both files are intentionally plain `console.log`, not
`Logger.info` — at the point `globalSetup` runs, `logs/` may not exist yet (it's this
very function creating it), so routing through the Winston-backed `Logger` (Phase 2)
here would be building on ground that isn't there yet. This is the one place in the
framework where a raw console statement is the correct choice rather than a shortcut
around the logger.

**Checkpoint:** `npx playwright test` runs (0 tests, no errors) and `logs/` gets created.

---

## Phase 2 — Shared utilities and constants

Build these before UI or API code — both layers depend on them.

### `src/utils/Logger.ts`

```ts
import winston from "winston";

// Playwright runs each worker as a separate OS process, and every worker executes
// its tests one at a time — so a single mutable module-level value is safe here
// (no cross-test race within a process) and lets every log line carry the worker
// index + current test title. Without this, logs/*.log interleaves lines from
// every worker process with no way to tell which test produced which line.
let currentContext = "";

function withContext(message: unknown): string {
  return currentContext ? `${currentContext} ${message}` : String(message);
}

const consoleFormat = winston.format.combine(
  winston.format.colorize(),
  winston.format.timestamp({
    format: "YYYY-MM-DD HH:mm:ss",
  }),
  winston.format.printf(
    ({ timestamp, level, message }) =>
      `${timestamp} ${level}: ${withContext(message)}`,
  ),
);

const fileFormat = winston.format.combine(
  winston.format.timestamp({
    format: "YYYY-MM-DD HH:mm:ss",
  }),
  winston.format.errors({
    stack: true,
  }),
  winston.format.printf(
    ({ timestamp, level, message, stack }) =>
      `${timestamp} [${level.toUpperCase()}] ${withContext(stack ?? message)}`,
  ),
);

export class Logger {
  /**
   * Tags every subsequent log line (until the next call) with the given
   * context, e.g. `[w2] [Valid user should login successfully]`. Call with an
   * empty string to clear. See beforeEach/afterEach hooks (Phase 4).
   */
  static setContext(context: string): void {
    currentContext = context;
  }
  private static logger = winston.createLogger({
    level: process.env.LOG_LEVEL ?? "info",

    defaultMeta: {
      framework: "Playwright",
    },

    transports: [
      new winston.transports.Console({
        format: consoleFormat,
      }),

      new winston.transports.File({
        filename: "logs/execution.log",
        format: fileFormat,
      }),

      new winston.transports.File({
        filename: "logs/error.log",
        level: "error",
        format: fileFormat,
      }),
    ],

    exceptionHandlers: [
      new winston.transports.File({
        filename: "logs/exceptions.log",
      }),
    ],

    rejectionHandlers: [
      new winston.transports.File({
        filename: "logs/rejections.log",
      }),
    ],
  });

  static info(message: string): void {
    this.logger.info(message);
  }

  static warn(message: string): void {
    this.logger.warn(message);
  }

  static error(message: string): void {
    this.logger.error(message);
  }

  static debug(message: string): void {
    this.logger.debug(message);
  }
}
```

**Design decisions worth calling out individually:**

- **Why a static class instead of an instance you'd construct and pass around:** every
  file in this framework that needs to log just wants to call `Logger.info(...)`
  without threading a logger instance through every constructor (page objects,
  services, hooks, providers). A single module-level Winston instance, exposed through
  static methods, is the simplest way to get that — the trade-off (no per-test-suite
  configurability, one global instance for the whole process) is acceptable because
  Playwright already gives one process per worker, so "one logger per process" is
  already the right granularity.
- **Why `currentContext` is a plain module-level `let`, not something worker-aware like
  `AsyncLocalStorage`:** the comment in the code states the actual constraint —
  Playwright workers are separate OS processes, and within one process tests run
  strictly one at a time (never two tests interleaved in the same worker, even with
  `fullyParallel: true` in `playwright.config.ts`, since parallelism there comes from
  *multiple workers*, not concurrent tests inside one). Given that guarantee, a mutable
  module-level string is completely safe: `registerBeforeEachHook` (Phase 4) sets it
  before a test's first log line and `registerAfterEachHook` clears it after the last —
  there's never a window where two different tests' log lines could interleave with
  each other's context inside a single worker. Using `AsyncLocalStorage` here would
  solve a problem this codebase doesn't have, at real complexity cost.
- **Why two separate `format` pipelines (`consoleFormat`/`fileFormat`) instead of one
  shared format:** the console output is for a human watching a live run — `colorize()`
  makes info/warn/error visually distinct in a terminal, which would just be ANSI
  escape-code noise if written to a file that's later opened in an editor or attached
  to a report. `fileFormat` instead runs `winston.format.errors({ stack: true })`,
  which is what makes an `Error` object logged via `Logger.error(err)` actually write
  its full stack trace to `logs/error.log` instead of just its message — critical for
  post-mortem debugging of a CI failure where there's no live terminal to have watched.
- **Why three separate file transports (`execution.log`, `error.log`, plus the
  exception/rejection handlers) instead of one log file:** `execution.log` is the full
  narrative of a run — every `info`/`debug`/`warn`/`error` line, useful for reconstructing
  exactly what a test did step by step. `error.log` uses the *same* `fileFormat` but
  with `level: "error"`, which in Winston means "only pass through records at this
  severity or higher" — so it's a pre-filtered view a reviewer can open directly when
  they only care about what went wrong, without scrolling past hundreds of routine
  info-level request/response lines. `exceptionHandlers`/`rejectionHandlers` are a
  distinct Winston mechanism from calling `Logger.error()` yourself — they catch
  **uncaught exceptions and unhandled promise rejections that would otherwise crash the
  process silently or print only to stderr**, giving a durable, separate record
  (`exceptions.log`/`rejections.log`) of exactly those catastrophic, unexpected failures
  as opposed to the errors this framework explicitly logs itself (like a caught
  `ApiException` in `ApiEngine`, Phase 5).
- **Why `level: process.env.LOG_LEVEL ?? "info"`:** Winston's levels are ordered
  (`error` < `warn` < `info` < `debug`, roughly), and setting the logger's level to
  `"info"` means `debug`-level calls (like `ApiEngine.logResponse`'s header dump, Phase 5)
  are silently dropped unless someone explicitly opts in — exactly what `LOG_LEVEL=debug
  npm run api` (Phase 7) does. This keeps routine local/CI runs readable while making
  full verbose tracing a single env var away when actually debugging a failure.

### `src/utils/Redactor.ts`

Build this *before* the API engine (Phase 5) — request/response logging and Allure
attachments should never see a secret un-redacted.

```ts
const SENSITIVE_KEY_PATTERN =
  /^(password|token|accessToken|refreshToken|authorization|secret|apiKey|clientSecret)$/i;

/**
 * Deep-clones a value, replacing any object key that matches a known-sensitive
 * name (password, token, authorization, ...) with a fixed mask. Used before
 * writing request/response data to logs or report attachments, so credentials
 * and bearer tokens never end up in plaintext in logs/*.log, the Playwright
 * HTML report, or Allure results.
 */
export function redact<T>(value: T): T {
  if (Array.isArray(value)) {
    return value.map((item) => redact(item)) as unknown as T;
  }

  if (value && typeof value === "object") {
    const entries = Object.entries(value as Record<string, unknown>).map(
      ([key, val]) => [
        key,
        SENSITIVE_KEY_PATTERN.test(key) ? "*****" : redact(val),
      ],
    );

    return Object.fromEntries(entries) as T;
  }

  return value;
}
```

**Why this exists as a completely generic, recursive function rather than something
tied to specific request/response shapes:** `redact<T>(value: T): T` doesn't know or
care whether it's given an `ApiHeaders` object, a full `ApiRequest`, or an
`ApiResponse<EventResponse>` — it just walks whatever structure it's handed. That
genericity is exactly why `ApiEngine` (Phase 5) can call `redact(headers)`,
`redact(request)`, and `redact(response)` from the same function without three
different redaction implementations, and why adding a brand-new API service later
needs zero changes here — any new request/response shape is automatically covered as
long as its sensitive fields use one of the recognized key names.

**Why it deep-clones instead of mutating in place:** `Object.fromEntries(entries)`
builds a *new* object rather than reassigning into the one passed in. This matters
concretely: `ApiEngine.attachRequest` calls `redact(request)` purely for the purpose of
building a redacted copy to attach to the test report — the *original* `request` object
still needs its real, un-redacted `body` a few lines later when `this.send(request,
url, headers)` actually makes the HTTP call. If `redact` mutated in place, the real
password would already be replaced with `"*****"` by the time the request tried to
actually authenticate.

**Why the sensitive-key check is a single case-insensitive regex over key *names*,
not a check on value shape or an explicit field-by-field allowlist per request type:**
key-name matching is what makes this maintenance-free — a new request type
(`LoginRequest`, `CreateEventRequest`, anything added later) is automatically
protected as long as its sensitive fields are named conventionally (`password`,
`token`, `accessToken`, etc.). The alternative — an explicit list of "these are the
sensitive fields of `LoginRequest`, these are the sensitive fields of `X`" — would need
updating every time a new request/response shape is added, and silently under-protects
the moment someone forgets. Case-insensitivity (`/i` flag) means `Password`, `PASSWORD`,
and `password` are all caught regardless of the exact casing convention a given
backend or dataset happens to use.

**Where the anchors matter:** the pattern is anchored with `^...$`, so it matches a key
that *equals* one of the listed names exactly — not a key that merely *contains* one of
them. This is a deliberate, narrow match: a field like `passwordConfirmationRequired`
(a boolean flag, not a secret) would *not* get masked, which is correct — masking it
would hide genuinely useful debugging information for no security benefit. The trade-off
is that a differently-named secret field (say, `pwd` or `bearer`) wouldn't be caught
automatically; extending the regex's alternation list is the intended way to add a new
recognized sensitive key name.

### `src/utils/DateUtils.ts`

```ts
export interface FutureDateOptions {
  hours?: number;
  minutes?: number;
  seconds?: number;
  milliseconds?: number;
}

export function getFutureDateIso(
  days = 1,
  options: FutureDateOptions = {},
): string {
  const date = new Date();
  date.setUTCDate(date.getUTCDate() + days);
  date.setUTCHours(
    options.hours ?? 9,
    options.minutes ?? 0,
    options.seconds ?? 0,
    options.milliseconds ?? 0,
  );
  return date.toISOString();
}
```

**Why this needs to exist at all:** `tests/api/event.spec.ts` (Phase 5) creates an event
with `eventDate: getFutureDateIso(2, {...})` — "2 days from whenever this test happens
to run." A hardcoded date string in the dataset (`event.createEvent.eventDate:
"2027-06-15T..."`, as it appears literally in `apiData.json`) works as a fallback
default value inside the JSON, but the spec deliberately *overrides* it at runtime with
a computed near-future date, because a backend validating "event date must be in the
future" would eventually start rejecting a hardcoded date as that date arrives in the
past — a slow-motion test failure that only appears months after the test was written,
with no code change to explain it. Computing the date at runtime instead means the
test is correct on every run, indefinitely.

**Why `days = 1` as the default and an explicit options object rather than separate
positional parameters for hours/minutes/etc.:** `days` is overwhelmingly the parameter
callers actually vary ("2 days out," "30 days out"), so it gets a plain positional
parameter with a sensible default. The time-of-day components are bundled into one
optional `FutureDateOptions` object instead of four more positional parameters,
because passing `getFutureDateIso(2, undefined, undefined, 0, 0)` to set just seconds
and milliseconds while leaving hours/minutes at their defaults would be unreadable and
error-prone (easy to shift an argument by one position); the options object lets a
caller specify only what it cares about (`{ hours: 9, minutes: 0, seconds: 0,
milliseconds: 0 }` in `event.spec.ts` — pinning the event to exactly 9:00:00.000 AM UTC
regardless of what time the test itself runs, which keeps repeated runs deterministic
down to the second, an important property when a value like this ends up compared in
assertions or logged for debugging).

**Why `setUTCDate`/`setUTCHours` and not local-time equivalents:** using UTC methods
throughout means the computed date is identical no matter what timezone the machine
running the test happens to be in — a CI runner in one timezone and a developer's
laptop in another produce the exact same ISO string for the same inputs. Mixing local-time
and UTC date math is a classic source of off-by-one-day bugs right at timezone
boundaries; this function sidesteps that class of bug entirely by never touching local
time.

### `src/utils/CommonActions.ts`

```ts
import { Page } from "@playwright/test";

export class CommonActions {
  static async refresh(page: Page): Promise<void> {
    await page.reload();
  }

  static async goBack(page: Page): Promise<void> {
    await page.goBack();
  }

  static async goForward(page: Page): Promise<void> {
    await page.goForward();
  }
}
```

**Why this is a thin, deliberately narrow class rather than a growing grab-bag:** these
three methods exist because they're the handful of raw Playwright `Page` operations
(`reload`, `goBack`, `goForward`) that are genuinely generic — not tied to any one
page's structure, unlike everything in `src/components/`/`src/pages/`. Note that
`BasePage` (Phase 4) *also* exposes a `reload()` method of its own — that's not
duplication by accident: `BasePage.reload()` is what a page-object method calls
internally (`this.page.reload()`), scoped to one page instance a test is already
holding, while `CommonActions.refresh(page)` is for the rarer case of a raw `page`
being available in a context where there's no page-object instance to call through
(e.g. a low-level browser-context helper, or a future non-page-object test utility).
The static-class-of-static-methods shape mirrors every other helper class in this
framework (`Logger`, `DateUtils`, the validators) — a deliberate, consistent choice
over exporting three loose functions, so every shared helper in the codebase is
imported and read the same way (`ClassName.methodName(...)`).

### `src/utils/index.ts`

```ts
export * from "./Logger";
export * from "./DateUtils";
```

**Why `Redactor` and `CommonActions` are conspicuously absent from this barrel:** this
isn't an oversight — it's a scope boundary. `Logger` and `DateUtils` are genuinely
general-purpose and get imported from all over the codebase (page objects, hooks, the
API engine, specs), so re-exporting them from one `src/utils` barrel is convenient.
`Redactor` is imported by exactly one place (`ApiEngine`, Phase 5) and `CommonActions`
by callers that need the raw `Page`-level helpers — both are imported directly from
their own files (`import { redact } from "../../utils/Redactor"`) rather than through
this barrel. A barrel file is a convenience for widely-shared exports, not an
obligation to funnel every file in a folder through it — and doing so here would add an
indirection with no corresponding benefit for two files with a single, specific
consumer each.

### `src/constants/AppRoutes.ts`

```ts
export const AppRoutes = Object.freeze({
  LOGIN: "/client/#/auth/login",
  REGISTER: "/client/#/auth/register",
  DASHBOARD: "/client/#/dashboard/dash",
  CART: "/client/#/dashboard/cart",
  ORDERS: "/client/#/dashboard/myorders",
});
```

**Why routes are centralized here instead of inlined in each page object's
`navigate()` call:** two independent reasons. First, this demo app is a hash-routed
Angular SPA (`/client/#/...`), and every route shares the `/client/#/dashboard/...`
prefix pattern — spelling that out by hand in six different page objects invites a typo
in one of them that only surfaces as a confusing navigation failure at test time, far
from where the typo actually lives. Second, and more importantly: if the app's routing
ever changes (a URL restructure, a path segment renamed), this is the *one* file that
needs updating — every page object's `navigate()` method (Phase 4, e.g. `LoginPage.navigate()`
calling `super.navigate(AppRoutes.LOGIN)`) keeps working unchanged. `Object.freeze` here
serves the same purpose it does in `ENV` (Phase 1) — these are compile-time-constant
values and freezing makes an accidental reassignment fail loudly rather than silently
corrupting shared state some other test depends on.

### `src/constants/APIEndpoints.ts`

```ts
export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: "/api/auth/login",
  },

  USER: {
    PROFILE: "/api/ecom/user/get-profile",
  },

  EVENT: {
    CREATE: "/api/events",
    UPDATE: (id: number) => `/api/events/${id}`,
    DELETE: (id: number) => `/api/events/${id}`,
  },
} as const;
```

**Why this is a plain `as const` object rather than `Object.freeze`, and why `EVENT.UPDATE`/
`EVENT.DELETE` are functions instead of string constants:** `as const` gets TypeScript to
infer the narrowest possible literal types for every string here (so `API_ENDPOINTS.AUTH.LOGIN`
has the type `"/api/auth/login"`, not the wider `string`) — a compile-time guarantee with
zero runtime cost, unlike `Object.freeze` which is a runtime-only guard. `AppRoutes`
uses `Object.freeze` instead because every one of its values really is just a static
string; `API_ENDPOINTS` mixes static paths (`AUTH.LOGIN`) with *parameterized* ones
(`EVENT.UPDATE(id)`, `EVENT.DELETE(id)`) — a REST resource path that embeds an ID isn't
knowable ahead of time, so it has to be a function that builds the string once the ID
is known. `EventService.updateEvent`/`deleteEvent` (Phase 5) call
`API_ENDPOINTS.EVENT.UPDATE(id)` exactly like they'd reference a plain constant, so from
the service's perspective the distinction is invisible — it's purely how the endpoint
constant has to be *defined* to support a dynamic path segment.

New endpoints always get added here — never a hardcoded path string in a service or
test (Phase 5) — for the same centralization reason as `AppRoutes`: a backend API
version bump or path restructure becomes a one-file change instead of a search-and-replace
across every service that happens to call that endpoint.

### `src/constants/Messages.ts`

```ts
export const Messages = Object.freeze({
  LOGIN_SUCCESS: "Login successful.",
  LOGIN_FAILURE: "Incorrect email or password.",
  ACCOUNT_CREATION_SUCCESS: "Account created successfully.",
  ORDER_CONFIRMATION_SUCCESS: "Thankyou for the order.",
});
```

**Why expected UI copy lives here instead of as a string literal inside each
validator:** `LoginValidator.expectLoginFailed` (Phase 4) asserts
`expect(message).toContain(Messages.LOGIN_FAILURE)` rather than
`expect(message).toContain("Incorrect email or password.")` inline. The value of that
indirection shows up the moment the application's actual copy changes (a product team
tweaks an error message's wording) — every assertion that checks for that message
across the whole suite updates by editing one line here, instead of a reviewer having
to grep the codebase for every place that string might have been typed out by hand
(and possibly typed slightly differently in different specs, which would make some
assertions silently stop matching without anyone noticing why). Note this also
reinforces the framework's assertion-in-validators convention (Phase 4) — `Messages`
constants are consumed *by validators*, not by specs directly, keeping "what the app
should say" and "where that gets asserted" both centralized rather than scattered.

### `src/constants/TestTags.ts`

```ts
export const TestTags = Object.freeze({
  SMOKE: "@smoke",
  SANITY: "@sanity",
  REGRESSION: "@regression",
  API: "@api",
  UI: "@ui",
  E2E: "@e2e",
  NEGATIVE: "@negative",
});
```

**Why these exist as named constants at all, when a test could just write the literal
string `"@smoke"` in its title:** Playwright's `--grep`/`--grep-invert` (and this
framework's `npm run smoke`/`npm run regression` scripts, Phase 7) match against the
literal tag text in a test's title — there's no dedicated Playwright "tag" API these
constants plug into; `TestTags.SMOKE` is purely a typed, autocomplete-friendly, typo-proof
reference to the string `"@smoke"` for anywhere in the codebase that needs to *refer to*
a tag programmatically (for instance, a future helper that filters or groups tests by
tag). Directly in a test title, the tag is still written as the plain literal
(`test("Valid user should login successfully @smoke", ...)` in `login.spec.ts`, Phase 4)
because that's what Playwright's own grep matching actually scans — `TestTags.SMOKE`
inside a template-string title would work identically, but the plain literal is what's
used by convention here since a test title is inherently a one-off string, not shared
code that benefits from a named reference. `Object.freeze` again guards against a
constant like `TestTags.SMOKE` being reassigned somewhere and silently breaking every
existing `--grep @smoke` invocation.

### `src/constants/index.ts`

```ts
export * from "./AppRoutes";
export * from "./APIEndpoints";
export * from "./Messages";
export * from "./TestTags";
```

This barrel is why `LoginValidator` (Phase 4) can write `import { Messages } from
"../constants"` instead of `import { Messages } from "../constants/Messages"` — a
single import path for the whole constants module regardless of which specific file a
given constant actually lives in, and one place to add a re-export the moment a fifth
constants file is introduced.

---

## Phase 3 — Test data layer

Goal: `TestData.load<T>("uiData")` returns an identically-shaped object whether the
backing file is JSON, YAML, CSV, or Excel.

### Domain models first

`src/models/User.ts`:

```ts
export interface User {
  email: string;
  password: string;
}
```

`src/models/PaymentDetails.ts`:

```ts
export interface PaymentDetails {
  cvv: string;
  nameOnCard: string;
  country: string;
}
```

`src/models/index.ts`:

```ts
export * from "./User";
export * from "./PaymentDetails";
```

**Why `User` and `PaymentDetails` live in top-level `src/models/`, separate from
`src/data/models/`:** this is a genuinely important distinction the folder names alone
don't make obvious. `src/models/` holds *domain* types — shapes that describe real-world
concepts (a user's credentials, a card's payment details) independent of any dataset
or file format. `src/data/models/` (built next) holds *dataset* types — the shape of
one specific test-data file (`UiData`, `ApiData`), which *compose* the domain types
(`UiData.login.validUser` is typed as `User`). Because `User` is domain-level rather
than UI- or API-specific, both `UiData` and `ApiData` can import and reuse the exact
same `User` interface for their respective `login.validUser` fields — one definition of
"what a user's credentials look like," never two independently-maintained copies that
could drift out of sync with each other.

### Provider contract and shared base

`src/data/providers/IDataProvider.ts`:

```ts
export interface IDataProvider {
  load<T>(fileName: string): Promise<T>;
}
```

**Why this interface exists separately from `BaseDataProvider`, when every concrete
provider extends the base class anyway:** `IDataProvider` is the *contract* — the
minimum any data source must fulfill to be usable by `TestData`. `DataProviderFactory`
(built below) types its map as `Record<string, ProviderConstructor>` where
`ProviderConstructor = new () => IDataProvider`, deliberately typed against the
interface rather than the concrete `BaseDataProvider` class. That's what makes the
factory genuinely open to a future provider that *doesn't* share the file-based,
key-value-cached implementation at all — say, a provider that pulls test data from a
remote API or a database — as long as it implements `load<T>(fileName): Promise<T>`,
it slots into the factory's map with zero changes to the factory or to `TestData`
itself. Depending on the interface rather than the base class is what keeps that door
open architecturally, even though every provider that exists today happens to walk
through `BaseDataProvider`.

`src/data/providers/BaseDataProvider.ts` — the shared skeleton every format-specific
provider extends:

```ts
import fs from "fs";
import path from "path";

import { ENV } from "../../../config/envLoader";
import { IDataProvider } from "./IDataProvider";
import { resolveSecrets } from "../utils/resolveSecrets";

export abstract class BaseDataProvider implements IDataProvider {
  private readonly cache = new Map<string, unknown>();

  protected abstract readonly extension: string;

  protected abstract parse<T>(filePath: string): Promise<T>;

  public async load<T>(fileName: string): Promise<T> {
    const cacheKey = `${ENV.TEST_DATA_FORMAT}:${fileName}`;

    const cached = this.cache.get(cacheKey);
    if (cached) {
      return cached as T;
    }

    const filePath = this.resolveFilePath(fileName);

    const parsed = await this.parse<T>(filePath);
    const data = resolveSecrets(parsed);

    this.cache.set(cacheKey, data);

    return data;
  }
  private resolveFilePath(fileName: string): string {
    const filePath = path.resolve(
      process.cwd(),
      "src",
      "data",
      "datasets",
      ENV.TEST_DATA_FORMAT,
      `${fileName}.${this.extension}`,
    );
    if (!fs.existsSync(filePath)) {
      throw new Error(`Test data file not found: ${filePath}`);
    }

    return filePath;
  }
}
```

**Why this is an `abstract class` implementing an interface, rather than each provider
independently implementing `IDataProvider` from scratch:** the Template Method pattern
is doing real work here. `load()` is implemented exactly once, in the base class, and
is *never* overridden — every provider inherits the identical caching, path-resolution,
and secrets-resolution behavior automatically, with no chance of one provider
accidentally forgetting to cache or forgetting to resolve secrets. Only the two things
that genuinely differ per format — the file extension (`protected abstract readonly
extension`) and how to turn raw file bytes into a JS object (`protected abstract
parse<T>`) — are left as abstract members each subclass must supply. This is why
`JsonProvider` (below) is only 6 lines: everything except "how to parse JSON
specifically" is already handled here.

**Line-by-line walkthrough of `load()`:**

- `const cacheKey = \`${ENV.TEST_DATA_FORMAT}:${fileName}\`` — the cache key includes
  the *format*, not just the filename, even though in practice one running process
  only ever has one `TEST_DATA_FORMAT` (it's read once into the frozen `ENV`, Phase 1,
  and never changes mid-run). This is defensive precision rather than something the
  current architecture strictly requires: it guarantees correctness even if this
  provider class were ever reused in a context where the format could vary, without
  relying on an invariant ("format never changes at runtime") that lives elsewhere.
- `if (cached) return cached as T` — a plain truthy check, not `this.cache.has(cacheKey)`.
  This is a deliberate, narrow simplification that relies on a real property of this
  codebase's data: every dataset here is a non-empty object (`{ login: {...}, checkout:
  {...} }`), and a non-empty object is always truthy in JavaScript — so there's no
  practical case where a legitimately-cached value would be falsy and get treated as a
  cache miss. It's simpler than a `.has()` check and correct for every dataset this
  framework actually loads.
- `const parsed = await this.parse<T>(filePath)` then `const data =
  resolveSecrets(parsed)` — parsing and secrets-resolution are two clearly separate
  steps, run in that order, on purpose: `parse()` only ever has to know about the raw
  file format (JSON syntax, YAML syntax, CSV rows) and never has to know anything about
  `{{key}}` placeholders; `resolveSecrets()` (Phase 3a) only ever has to know about
  walking an already-parsed plain JS object and never has to know anything about file
  formats. Neither one could do the other's job, and neither needs to — that separation
  of concerns is what let four completely independent parsing implementations
  (`JsonProvider`, `YamlProvider`, `CsvProvider`, `ExcelProvider`) all share the exact
  same one-line secrets-resolution call, for free, by inheriting `load()`.
- `this.cache.set(cacheKey, data)` — caches the *already-secret-resolved* data, not the
  raw parse. This means `Secrets.get()` (Phase 3a) — and the environment-variable/file
  lookup it performs — only actually runs once per file per test run, no matter how
  many times different specs call `TestData.load("uiData")`, which matters because
  `beforeAll` hooks in *each* spec file independently call `TestData.load(...)` (Phase 4)
  and there's no reason to repeat file I/O and placeholder resolution on every one of
  those calls.
- `private resolveFilePath` throwing on a missing file rather than letting `parse()`
  fail with whatever cryptic error `fs.readFileSync`/`JSON.parse`/etc. would produce on
  a nonexistent path — this is the same fail-fast philosophy as `envLoader.ts`'s
  `required()` (Phase 1) and `secretsLoader.ts`'s `Secrets.get()` (Phase 3a): a clear,
  actionable error message (`Test data file not found: <exact path>`) at the earliest
  possible point, rather than an opaque low-level error several stack frames deeper.

Every load runs `resolveSecrets()` (built in [Phase 3a](#phase-3a--secrets)) before
caching, and results are cached per `(format, fileName)` — call `TestData.load()` from
`test.beforeAll`, not module scope, so the cache actually helps.

### The four format-specific providers

`JsonProvider`/`YamlProvider` parse the file's native nested structure directly.
`CsvProvider`/`ExcelProvider` read flat `key,value,type` rows instead, since those
formats can't express nesting — a shared helper (`unflattenRows`, below) rebuilds the
same nested shape.

`src/data/providers/JsonProvider.ts`:

```ts
import fs from "fs";

import { BaseDataProvider } from "./BaseDataProvider";

export class JsonProvider extends BaseDataProvider {
  protected readonly extension = "json";

  protected async parse<T>(filePath: string): Promise<T> {
    return JSON.parse(fs.readFileSync(filePath, "utf8")) as T;
  }
}
```

`src/data/providers/YamlProvider.ts`:

```ts
import fs from "fs";
import YAML from "yaml";

import { BaseDataProvider } from "./BaseDataProvider";

export class YamlProvider extends BaseDataProvider {
  protected readonly extension = "yaml";

  protected async parse<T>(filePath: string): Promise<T> {
    return YAML.parse(fs.readFileSync(filePath, "utf8")) as T;
  }
}
```

**Why both of these are `async` even though `fs.readFileSync`/`JSON.parse`/`YAML.parse`
are all synchronous operations:** `parse<T>(filePath: string): Promise<T>` is declared
`abstract` on `BaseDataProvider` with an async signature specifically because
`ExcelProvider` (below) genuinely *needs* to be async — `exceljs`'s `Workbook.xlsx.readFile`
has no synchronous equivalent. Since all four providers must share one interface
signature (that's the entire point of the Template Method pattern above), `JsonProvider`
and `YamlProvider` are forced to wrap synchronous work in an `async` function too, even
though they don't strictly need to await anything themselves. This is a deliberate,
worthwhile trade — a slightly awkward `async` on two providers that don't need it, in
exchange for `TestData.load()` and every spec's `beforeAll` having exactly one call
shape (`await TestData.load(...)`) that works identically no matter which format is
configured.

**Why `fs.readFileSync` and not the async `fs.promises.readFile`, given the function is
already `async`:** at the scale of a single test-data file (a few KB), the difference
is immaterial, and `readFileSync` keeps these two providers simpler to read (no extra
`await` needed for the read itself, only for the overall function signature). This
isn't reading a large file under load — it's a handful of KB of test fixtures, read
once per run per file thanks to `BaseDataProvider`'s cache.

`src/data/utils/tabularData.ts` (needed by both CSV and Excel providers):

```ts
export interface TabularRow {
  key?: unknown;
  value?: unknown;
  type?: unknown;
}

/**
 * CSV/Excel rows are flat "key,value,type" triples (dot-path key, e.g. "login.validUser.email").
 * This rebuilds the nested object shape that JSON/YAML datasets already produce, so the same
 * data models (UiData, ApiData, ...) work unchanged regardless of source format.
 */
export function unflattenRows(rows: TabularRow[]): Record<string, unknown> {
  const result: Record<string, unknown> = {};

  for (const row of rows) {
    const path = String(row.key ?? "").trim();
    if (!path) {
      continue;
    }

    setPath(result, path.split("."), coerce(row.value, row.type));
  }

  return result;
}

function setPath(target: Record<string, unknown>, path: string[], value: unknown): void {
  const [head, ...rest] = path;

  if (rest.length === 0) {
    target[head] = value;
    return;
  }

  target[head] = (target[head] as Record<string, unknown>) ?? {};
  setPath(target[head] as Record<string, unknown>, rest, value);
}

function coerce(value: unknown, type: unknown): unknown {
  const normalizedType = String(type ?? "").trim().toLowerCase() || "string";

  switch (normalizedType) {
    case "number":
      return Number(value);
    case "boolean":
      return String(value).trim().toLowerCase() === "true";
    default:
      return String(value ?? "");
  }
}
```

**Why this file exists as a separate `src/data/utils/` module rather than being
private logic inside `CsvProvider`:** `ExcelProvider` needs *exactly* the same
row-to-nested-object rebuilding logic as `CsvProvider` — both read a flat
`key,value,type` table, just from different physical formats (a text file vs. a
spreadsheet). Extracting `unflattenRows`/`TabularRow` into their own module is what
lets both providers share one implementation instead of two independently-maintained
(and inevitably eventually-diverging) copies of the same dot-path-splitting logic.

**Walking through why each piece works the way it does:**

- `TabularRow` types every field as `unknown`, not `string`, even though CSV parsing
  naturally produces strings — this is because `ExcelProvider` populates the exact same
  interface from spreadsheet *cell values*, which `exceljs` can hand back as numbers,
  booleans, or `Date` objects depending on how the cell was formatted, not just strings.
  Typing the interface as `unknown` is honest about that variability and pushes the
  actual normalization work into `coerce()`, one single place, rather than each
  provider needing its own type-narrowing logic.
- `setPath` is recursive, and deliberately mutates its `target` argument in place
  (`target[head] = ...`) rather than returning a new object at each level — this is
  a case where mutation is the right, standard choice: `unflattenRows` builds one
  fresh `result` object per call and repeatedly folds rows into it, so there's no
  concern about mutating something a caller still holds a reference to elsewhere.
  The `(target[head] as Record<string, unknown>) ?? {}` line is what allows rows to
  arrive in *any* order and still build the correct nested structure — a row for
  `checkout.payment.cvv` can appear before or after `checkout.payment.nameOnCard` in the
  CSV and the result is identical, because each recursive call creates the intermediate
  object (`checkout`, then `checkout.payment`) only the first time it's needed and
  reuses it on every subsequent row that shares that prefix.
- `coerce(value, type)` defaults an absent/blank `type` column to `"string"` — this
  matches the CSV format's documented convention (Phase 3 dataset files) that `type` is
  optional and string is the fallback, so a dataset author only ever has to write
  `,number` or `,boolean` for the values that actually need it, never `,string` for the
  common case.
- The `boolean` branch specifically checks `=== "true"` after lowercasing and trimming,
  rather than doing something like `Boolean(value)` — `Boolean("false")` is `true` in
  JavaScript (any non-empty string is truthy), which would silently make every boolean
  value in a CSV/Excel dataset coerce to `true` regardless of what was actually written.
  The explicit string comparison against `"true"` is what makes `,boolean` columns
  behave the way a human reading the dataset file would expect.

`src/data/providers/CsvProvider.ts`:

```ts
import fs from "fs";
import { parse } from "csv-parse/sync";

import { BaseDataProvider } from "./BaseDataProvider";
import { TabularRow, unflattenRows } from "../utils/tabularData";

export class CsvProvider extends BaseDataProvider {
  protected readonly extension = "csv";

  protected async parse<T>(filePath: string): Promise<T> {
    const rows = parse(fs.readFileSync(filePath, "utf8"), {
      columns: true,
      skip_empty_lines: true,
      trim: true,
    }) as TabularRow[];

    return unflattenRows(rows) as T;
  }
}
```

**Why `columns: true` / `skip_empty_lines: true` / `trim: true` specifically:**
`columns: true` tells `csv-parse` to use the first row as field names and return an
array of objects keyed by header (`{ key: "...", value: "...", type: "..." }`) rather
than an array of plain arrays — that's what lets the result be cast directly to
`TabularRow[]` with no manual header-mapping step. `skip_empty_lines: true` means a
trailing blank line at the end of the CSV file (extremely easy to leave behind when
hand-editing a dataset in a text editor) doesn't produce a bogus empty row that
`unflattenRows` would then have to specially ignore — `trim: true` similarly absorbs
incidental whitespace around values (a space accidentally typed after a comma) so it
never has to be defended against downstream. All three options push data-hygiene
concerns into the parsing library's own options rather than into custom cleanup code
in this provider.

`src/data/providers/ExcelProvider.ts` — reads via `exceljs` (async-only, no sync API,
which is why `load()` is async everywhere in this layer):

```ts
import { Workbook } from "exceljs";

import { BaseDataProvider } from "./BaseDataProvider";
import { TabularRow, unflattenRows } from "../utils/tabularData";

export class ExcelProvider extends BaseDataProvider {
  protected readonly extension = "xlsx";

  protected async parse<T>(filePath: string): Promise<T> {
    const workbook = new Workbook();
    await workbook.xlsx.readFile(filePath);

    const worksheet = workbook.worksheets[0];
    if (!worksheet) {
      throw new Error(`Excel file has no worksheets: ${filePath}`);
    }

    const headers: string[] = [];
    worksheet.getRow(1).eachCell({ includeEmpty: false }, (cell, colNumber) => {
      headers[colNumber] = String(cell.value ?? "").trim();
    });

    const rows: TabularRow[] = [];
    worksheet.eachRow({ includeEmpty: false }, (row, rowNumber) => {
      if (rowNumber === 1) {
        return;
      }

      const record: Record<string, unknown> = {};
      row.eachCell({ includeEmpty: false }, (cell, colNumber) => {
        const header = headers[colNumber];
        if (header) {
          record[header] = cell.value;
        }
      });

      rows.push(record as TabularRow);
    });

    return unflattenRows(rows) as T;
  }
}
```

**Why this provider is meaningfully more code than the other three, and what each part
is doing:** unlike JSON/YAML (parse the whole file's structure in one library call) or
CSV (one `csv-parse` call gives you an array of row objects directly), a spreadsheet
has no single "give me the header row and the data rows as objects" API — `exceljs`
gives raw cell-by-cell access to a worksheet, and mapping that into the same
`TabularRow[]` shape `unflattenRows` expects has to be done by hand here:

- `const worksheet = workbook.worksheets[0]` — this provider always reads the *first*
  worksheet in the file and nothing else, which is why the dataset-building
  instructions (Phase 3) specify "a single worksheet" — a workbook with data on a
  second sheet would be silently ignored, not an error, so the convention has to be
  enforced by how the file is authored, not by code.
- The header-row loop populates `headers[colNumber]`, an array indexed by *column
  number*, not by array position — `eachCell({ includeEmpty: false }, (cell,
  colNumber) => ...)` skips genuinely empty cells but still reports the real column
  number for the cells it does visit, so `headers[3]` reliably means "whatever's in
  column C" even if columns A/B were somehow empty. This positional-by-column-number
  approach is what lets the data-row loop below look up `headers[colNumber]` and get
  the right field name for each cell, rather than assuming columns are always
  contiguous from 1.
- `if (rowNumber === 1) return` inside the data-row loop is a plain, direct skip of the
  header row — the header was already consumed by the separate loop just above,
  so processing it again as a data row would try to build a row like `{ key: "key",
  value: "value", type: "type" }`, which `unflattenRows` would then try to write to a
  nonsensical path.
- `record[header] = cell.value` uses the raw `cell.value` as-is, with no type coercion
  at this stage — that's intentional: coercion is `coerce()`'s job (in
  `tabularData.ts`, above), applied uniformly to both CSV strings and Excel's
  richer cell values in exactly one place, so this provider doesn't need its own,
  Excel-specific type-handling logic.
- The explicit `if (!worksheet) throw ...` guard is the same fail-fast pattern seen
  throughout this framework (`envLoader.ts`, `secretsLoader.ts`,
  `BaseDataProvider.resolveFilePath`) — a `.xlsx` file that somehow has zero worksheets
  (corrupted, or created wrong) fails immediately with a clear message instead of
  crashing later with a confusing "cannot read property of undefined."

`src/data/providers/index.ts`:

```ts
export * from "./IDataProvider";
export * from "./BaseDataProvider";
export * from "./JsonProvider";
export * from "./YamlProvider";
export * from "./CsvProvider";
export * from "./ExcelProvider";
```

Bundles the contract, the shared base, and all four concrete implementations behind
one import path — consumed by `DataProviderFactory` next, which is the only file that
actually needs to know all four providers exist simultaneously.

### Factory and entry point

`src/data/factory/DataProviderFactory.ts`:

```ts
import { ENV } from "../../../config/envLoader";

import { IDataProvider } from "../providers/IDataProvider";
import { JsonProvider } from "../providers/JsonProvider";
import { YamlProvider } from "../providers/YamlProvider";
import { CsvProvider } from "../providers/CsvProvider";
import { ExcelProvider } from "../providers/ExcelProvider";

type ProviderConstructor = new () => IDataProvider;

export class DataProviderFactory {
  private static readonly providers: Record<string, ProviderConstructor> = {
    json: JsonProvider,

    yaml: YamlProvider,

    csv: CsvProvider,

    excel: ExcelProvider,
  };

  static create(): IDataProvider {
    const Provider = this.providers[ENV.TEST_DATA_FORMAT];
    if (!Provider) {
      throw new Error(`Unsupported TEST_DATA_FORMAT: ${ENV.TEST_DATA_FORMAT}`);
    }

    return new Provider();
  }
}
```

**Why this is the Factory pattern rather than, say, a `switch` inline in `TestData`
itself:** the map (`Record<string, ProviderConstructor>`) makes "which formats are
supported" a *data structure* rather than *control flow* — adding a fifth format later
means adding one entry to this object, with no branching logic to extend anywhere.
`ProviderConstructor = new () => IDataProvider` is what lets the map's values be
*classes themselves* (`JsonProvider`, not `new JsonProvider()`) — the factory
instantiates the right one lazily, inside `create()`, only once it knows which format
is actually configured, rather than eagerly constructing all four providers up front
just to discard three of them. The `if (!Provider) throw ...` guard means an
unsupported value in `TEST_DATA_FORMAT` (a typo in a `.env` file, or a genuinely new
format someone forgot to register here) fails immediately with a message naming the
exact bad value, rather than `undefined` silently propagating into `new Provider()` and
crashing with a much less informative "Provider is not a constructor."

`src/data/TestData.ts` — the one entry point everything else calls:

```ts
import { DataProviderFactory } from "./factory/DataProviderFactory";

export class TestData {
  private static readonly provider = DataProviderFactory.create();

  static load<T>(fileName: string): Promise<T> {
    return this.provider.load<T>(fileName);
  }
}
```

**Why this class is so small, and why that's the point:** `TestData` is deliberately
the *only* class in the whole codebase that a spec ever imports to get test data — no
spec ever imports `DataProviderFactory` or a concrete provider directly. That single
choke point is what makes the entire four-format architecture invisible to test
authors: a spec just writes `await TestData.load<UiData>("uiData")` and has no idea,
and no need to know, whether that's reading JSON, YAML, CSV, or Excel underneath.
`private static readonly provider = DataProviderFactory.create()` runs exactly once —
at class-load time, the very first time anything imports `TestData` — which means the
provider (and therefore the format decision baked into `ENV.TEST_DATA_FORMAT`) is fixed
for the entire process lifetime; there's no code path where a test could accidentally
get a different provider than another test in the same run. `load<T>` is a thin
pass-through with no logic of its own — all the actual behavior (path resolution,
parsing, secrets resolution, caching) lives in `BaseDataProvider`, which is exactly
right: `TestData`'s job is *only* to be the single, simple front door.

`src/data/index.ts`:

```ts
export * from "./TestData";
```

A barrel with exactly one re-export might look pointless, but it's what makes `import
{ TestData } from "../../src/data"` (as every spec does, Phase 4/5) work without
callers needing to know or care that `TestData` specifically lives in
`src/data/TestData.ts` — if the internal file layout of this module ever changed, every
spec's import statement would keep working unchanged.

### Typed models per dataset

`src/data/models/UiData.ts`:

```ts
import { User, PaymentDetails } from "../../models";

export interface UiData {
  login: {
    validUser: User;
    invalidPassword: User;
    invalidEmail: User;
  };

  checkout: {
    productName: string;
    payment: PaymentDetails;
  };
}
```

**What this type is actually protecting against:** `UiData` describes the exact
expected shape of `uiData.{json,yaml,csv,xlsx}` — and because `TestData.load<UiData>("uiData")`
is a *generic* call, TypeScript doesn't verify at compile time that the JSON/YAML/CSV/Excel
file on disk actually matches this shape (that's not something a static type system can
check against a runtime file read) — what it *does* guarantee is that every place in
the codebase that consumes the loaded data (`login.spec.ts`, `checkout.spec.ts`, Phase 4)
gets full autocomplete and compile-time checking against this declared shape. If a spec
tries to access `uiData.login.nonExistentUser`, that's a `tsc` error (Phase 0's
`typecheck` script) rather than a runtime `undefined` discovered only when the test
actually runs. The three login variants (`validUser`/`invalidPassword`/`invalidEmail`)
directly mirror the three test scenarios in `login.spec.ts` — this type and that spec
were designed together, one describing the shape the other consumes.

`src/data/models/ApiData.ts` (reuses `CreateEventRequest` from the API layer, built in
Phase 5, so payload shape stays in sync with the service that sends it):

```ts
import { User } from "../../models";
import { CreateEventRequest } from "../../api/requests/EventRequest";

export interface ApiData {
  login: {
    validUser: User;
  };

  event: {
    createEvent: CreateEventRequest;
    updateEvent: CreateEventRequest;
  };
}
```

**Why `event.updateEvent` is typed as `CreateEventRequest`, not a hypothetical separate
`UpdateEventRequest` model, even though `EventService.updateEvent` (Phase 5) accepts an
`UpdateEventRequest`:** this is intentional, not an inconsistency — `UpdateEventRequest`
(defined in `src/api/requests/EventRequest.ts`, Phase 5) is declared as `export type
UpdateEventRequest = CreateEventRequest`, i.e. a type alias with an identical shape,
because this particular API's update endpoint happens to require the same full payload
as create (there's no partial-update/PATCH-style endpoint here). `ApiData.event.updateEvent`
could equally have been typed `UpdateEventRequest` with zero practical difference — the
choice to write `CreateEventRequest` here just reflects that both dataset fields
genuinely hold "a complete event payload," and reusing one name is marginally simpler
than importing and referencing a type alias that resolves to the exact same shape
anyway.

**The bigger point this file demonstrates:** `import { CreateEventRequest } from
"../../api/requests/EventRequest"` is a *cross-layer* import — `src/data/` reaching into
`src/api/`. That's allowed specifically because it's a one-directional dependency on a
*type*, not a runtime dependency on API behavior — `ApiData` never calls anything from
the API layer, it only borrows its request shape so that the moment someone adds a
field to `CreateEventRequest` (say, a new required `organizer` field), `tsc` immediately
flags every dataset file's corresponding data as incomplete, rather than that mismatch
only surfacing as a confusing runtime 400 from the real backend during a test run. This
is the concrete mechanism behind the framework's stated principle (mentioned at the top
of this document) that "payload shape stays in sync with the service that sends it."

`src/data/models/index.ts`:

```ts
export * from "./UiData";
export * from "./ApiData";
```

### The datasets themselves — one logical dataset, four physical files

Every value below that is `{{...}}` is a secrets placeholder (resolved in
[Phase 3a](#phase-3a--secrets)) — never a real credential.

**Why four physical copies of the same data instead of one canonical file plus a
conversion step:** it would be entirely possible to author `uiData.json` as the single
source of truth and generate the YAML/CSV/Excel versions from it with a script. This
framework deliberately does *not* do that — each of the four files is hand-authored and
independently maintained, because the entire point of having four providers is to
prove, on every single test run, that the framework handles all four formats correctly
end to end; a generated-from-JSON YAML file would only ever test "can this framework
read back exactly what it (or a script) wrote," not "does the YAML provider correctly
parse YAML a human would actually write by hand," which is a meaningfully weaker test.
The cost of that choice is real and explicit: whoever adds a new dataset field must
remember to add it in all four files, in the right nested location and with the right
`type` column for CSV/Excel — which is exactly why this doc calls it out at every level
(the intro to Phase 3, and again here) rather than leaving it as an implicit
expectation.

`src/data/datasets/json/uiData.json`:

```json
{
  "login": {
    "validUser": {
      "email": "{{uiValidUserEmail}}",
      "password": "{{uiValidUserPassword}}"
    },
    "invalidPassword": {
      "email": "valid@test.com",
      "password": "WrongPassword"
    },
    "invalidEmail": {
      "email": "invalid@test.com",
      "password": "Password123"
    }
  },
  "checkout": {
    "productName": "ZARA COAT 3",
    "payment": {
      "cvv": "123",
      "nameOnCard": "Test User",
      "country": "India"
    }
  }
}
```

`src/data/datasets/json/apiData.json`:

```json
{
  "login": {
    "validUser": {
      "email": "{{apiValidUserEmail}}",
      "password": "{{apiValidUserPassword}}"
    }
  },
  "event": {
    "createEvent": {
      "title": "Tech Summit 2026",
      "description": "A premier technology conference.",
      "category": "Conference",
      "venue": "Bangalore International Centre",
      "city": "Bangalore",
      "eventDate": "2027-06-15T09:00:00.000Z",
      "price": 1500,
      "totalSeats": 500,
      "imageUrl": "https://example.com/banner.jpg"
    },
    "updateEvent": {
      "title": "Tech Summit 2027",
      "description": "Updated premier technology conference.",
      "category": "Conference",
      "venue": "Bangalore International Centre",
      "city": "Bangalore",
      "eventDate": "2027-06-15T09:00:00.000Z",
      "price": 1600,
      "totalSeats": 500,
      "imageUrl": "https://example.com/updated-banner.jpg"
    }
  }
}
```

`src/data/datasets/yaml/uiData.yaml` (note the placeholders are quoted — `{{` is
flow-mapping syntax in YAML):

```yaml
login:
  validUser:
    email: "{{uiValidUserEmail}}"
    password: "{{uiValidUserPassword}}"

  invalidPassword:
    email: valid@test.com
    password: WrongPassword

  invalidEmail:
    email: invalid@test.com
    password: Password123

checkout:
  productName: ZARA COAT 3
  payment:
    cvv: "123"
    nameOnCard: Test User
    country: India
```

`src/data/datasets/yaml/apiData.yaml`:

```yaml
login:
  validUser:
    email: "{{apiValidUserEmail}}"
    password: "{{apiValidUserPassword}}"

event:
  createEvent:
    title: Tech Summit 2026
    description: A premier technology conference.
    category: Conference
    venue: Bangalore International Centre
    city: Bangalore
    eventDate: "2027-06-15T09:00:00.000Z"
    price: 1500
    totalSeats: 500
    imageUrl: https://example.com/banner.jpg

  updateEvent:
    title: Tech Summit 2027
    description: Updated premier technology conference.
    category: Conference
    venue: Bangalore International Centre
    city: Bangalore
    eventDate: "2027-06-15T09:00:00.000Z"
    price: 1600
    totalSeats: 500
    imageUrl: https://example.com/updated-banner.jpg
```

`src/data/datasets/csv/uiData.csv` (always set an explicit `type` for non-string
values — CSV/Excel values are otherwise treated as strings):

```csv
key,value,type
login.validUser.email,{{uiValidUserEmail}},string
login.validUser.password,{{uiValidUserPassword}},string
login.invalidPassword.email,valid@test.com,string
login.invalidPassword.password,WrongPassword,string
login.invalidEmail.email,invalid@test.com,string
login.invalidEmail.password,Password123,string
checkout.productName,ZARA COAT 3,string
checkout.payment.cvv,123,string
checkout.payment.nameOnCard,Test User,string
checkout.payment.country,India,string
```

`src/data/datasets/csv/apiData.csv`:

```csv
key,value,type
login.validUser.email,{{apiValidUserEmail}},string
login.validUser.password,{{apiValidUserPassword}},string
event.createEvent.title,Tech Summit 2026,string
event.createEvent.description,A premier technology conference.,string
event.createEvent.category,Conference,string
event.createEvent.venue,Bangalore International Centre,string
event.createEvent.city,Bangalore,string
event.createEvent.eventDate,2027-06-15T09:00:00.000Z,string
event.createEvent.price,1500,number
event.createEvent.totalSeats,500,number
event.createEvent.imageUrl,https://example.com/banner.jpg,string
event.updateEvent.title,Tech Summit 2027,string
event.updateEvent.description,Updated premier technology conference.,string
event.updateEvent.category,Conference,string
event.updateEvent.venue,Bangalore International Centre,string
event.updateEvent.city,Bangalore,string
event.updateEvent.eventDate,2027-06-15T09:00:00.000Z,string
event.updateEvent.price,1600,number
event.updateEvent.totalSeats,500,number
event.updateEvent.imageUrl,https://example.com/updated-banner.jpg,string
```

`src/data/datasets/excel/uiData.xlsx` / `apiData.xlsx` — build these as actual `.xlsx`
workbooks (e.g. via Excel/Google Sheets, or a one-off `exceljs` script) with the exact
same three-column `key,value,type` header row and rows as the CSV files above, in a
single worksheet.

**Checkpoint:** a throwaway script (or an early spec) calling
`await TestData.load("uiData")` returns identical, secret-resolved objects regardless
of `TEST_DATA_FORMAT`.

---

## Phase 3a — Secrets

Real credential values must never live in `src/data/datasets/*` directly — they're
referenced there as `{{key}}` placeholders (already visible above) and resolved from
here.

### `config/secrets/qa.env.example` (committed template)

```env
#########################################
# Secrets — QA (template)
# Copy to qa.env (gitignored) and fill in real values.
# In CI, these are instead injected as environment variables of the same
# name from GitHub Actions repository secrets — see .github/workflows/playwright-tests.yml.
#########################################

uiValidUserEmail=
uiValidUserPassword=

apiValidUserEmail=
apiValidUserPassword=
```

### `config/secrets/dev.env.example`

```env
#########################################
# Secrets — DEV (template)
# Copy to dev.env (gitignored) and fill in real values.
# In CI, these are instead injected as environment variables of the same
# name from GitHub Actions repository secrets — see .github/workflows/playwright-tests.yml.
#########################################

uiValidUserEmail=
uiValidUserPassword=

apiValidUserEmail=
apiValidUserPassword=
```

Locally, copy each `*.env.example` to the matching `*.env` (gitignored, per Phase 0)
and fill in real values — never commit the filled-in file.

**Why these are separate files per environment (`qa.env.example`, `dev.env.example`)
rather than one shared `secrets.env.example`:** this mirrors `config/environments/`
exactly (Phase 1) — QA and dev are realistically different backend deployments with
different user accounts, so the QA environment's valid test user almost certainly isn't
a valid login on the dev deployment. Keeping secrets scoped per environment, the same
way base URLs are, means switching `TEST_ENV` switches *both* which app you're testing
and which credentials are used for it, consistently, with no risk of accidentally
running dev tests against a QA-only account.

### `config/secretsLoader.ts`

```ts
import dotenv from "dotenv";
import fs from "fs";
import path from "path";

import { ENV } from "./envLoader";

/**
 * Sensitive test-data values (credentials, tokens, ...) never live in
 * src/data/datasets/* directly — those files reference them as `{{key}}`
 * (see src/data/utils/resolveSecrets.ts). The actual values come from here:
 *
 *  - Locally: config/secrets/<env>.env (gitignored — copy the matching
 *    *.env.example template and fill in real values).
 *  - In CI: environment variables of the same name, injected from GitHub
 *    Actions repository secrets (see .github/workflows/playwright-tests.yml).
 *    Env vars always win over the local file, so CI never needs the file.
 */
const secretsFilePath = path.resolve(
  process.cwd(),
  "config/secrets",
  `${ENV.ENVIRONMENT}.env`,
);

const fileSecrets: Record<string, string> = fs.existsSync(secretsFilePath)
  ? (dotenv.parse(fs.readFileSync(secretsFilePath)) as Record<string, string>)
  : {};

export const Secrets = Object.freeze({
  /**
   * Resolves a secret by key. Checks real environment variables first (how
   * CI supplies secrets), then falls back to the local secrets file. Throws
   * if the key is referenced by test data but not defined anywhere, so a
   * missing secret fails fast with an actionable message instead of a test
   * silently logging in with the literal string "{{key}}".
   */
  get(key: string): string {
    const value = process.env[key] ?? fileSecrets[key];
    if (!value) {
      throw new Error(
        `Secret '${key}' (referenced as {{${key}}} in test data) was not found. ` +
          `Add it to config/secrets/${ENV.ENVIRONMENT}.env (copy ${ENV.ENVIRONMENT}.env.example) ` +
          `or set it as an environment variable of the same name.`,
      );
    }

    return value;
  },
});
```

**Why the file-reading logic runs at module load time (top-level `const
secretsFilePath`/`const fileSecrets`), not inside `get()`:** `Secrets.get(key)` can be
called many times across a single test run — once per placeholder, across every
dataset a spec loads — and re-reading and re-parsing the secrets file on every single
call would be wasted, repeated file I/O for data that never changes mid-run. Reading it
once, when the module first loads, and holding the parsed result in `fileSecrets` means
every subsequent `get()` call is a plain in-memory object lookup.

**Why `fs.existsSync(secretsFilePath) ? dotenv.parse(...) : {}` rather than letting a
missing file throw immediately:** a missing `config/secrets/<env>.env` file is
*expected and correct* in CI, where secrets arrive exclusively via `process.env` (Phase
8) and the file never exists at all. Falling back to an empty object rather than
throwing here means this loader works identically whether it's running locally (file
present) or in CI (file absent) — the only place a *missing secret* becomes an error is
inside `get()`, and only when a key genuinely isn't found in *either* source. This is a
deliberate two-tier design: "file missing" is fine, "key missing from both env and
file" is not.

**Why env vars are checked before the file, not the other way around
(`process.env[key] ?? fileSecrets[key]`):** this ordering is what makes CI need zero
special-casing in this file — CI (Phase 8) injects secrets as real environment
variables with the same names the dataset placeholders reference, so `process.env[key]`
resolves them directly and `fileSecrets` (empty, since the file doesn't exist there)
never even gets consulted. Locally, a developer typically has no matching environment
variables set, so the fallback to the local file kicks in. The single line `value =
process.env[key] ?? fileSecrets[key]` is what lets one code path serve both
environments correctly without an `if (isCI)` branch anywhere.

**Why the thrown error message repeats the key three different ways (as `'${key}'`, as
`{{${key}}}`, and in the suggested file path):** this is written specifically for the
person debugging a red CI run or a fresh local checkout, not for a machine — it names
the exact missing key, shows what it looks like as written in the dataset file (so a
`grep` for that exact placeholder string immediately finds the offending dataset
entry), and names the exact file and template to copy. Every other fail-fast error in
this framework (`envLoader.ts`'s `required()`, `BaseDataProvider`'s missing-file error)
follows the same "name the problem, name the fix" shape deliberately.

### `src/data/utils/resolveSecrets.ts`

```ts
import { Secrets } from "../../../config/secretsLoader";

const PLACEHOLDER_PATTERN = /^\{\{\s*([\w.]+)\s*\}\}$/;

/**
 * Deep-walks a parsed test-data object and replaces any string value that is
 * *entirely* a `{{key}}` placeholder (e.g. "{{uiValidUserPassword}}") with the
 * matching value from `Secrets`. Applied once in BaseDataProvider.load(), so
 * it runs identically for JSON/YAML/CSV/Excel regardless of provider.
 */
export function resolveSecrets<T>(value: T): T {
  if (Array.isArray(value)) {
    return value.map((item) => resolveSecrets(item)) as unknown as T;
  }

  if (typeof value === "string") {
    const match = PLACEHOLDER_PATTERN.exec(value);
    return (match ? Secrets.get(match[1]) : value) as unknown as T;
  }

  if (value && typeof value === "object") {
    const entries = Object.entries(value as Record<string, unknown>).map(
      ([key, val]) => [key, resolveSecrets(val)],
    );

    return Object.fromEntries(entries) as T;
  }

  return value;
}
```

**Why the placeholder regex is anchored with `^\{\{\s*([\w.]+)\s*\}\}$` rather than a
global, unanchored match that could find `{{key}}` *inside* a larger string:** the
anchors (`^`...`$`, with no `g` flag) mean a value only counts as a secret reference if
it is *entirely and only* the placeholder — `"{{uiValidUserPassword}}"` matches, but
`"my password is {{uiValidUserPassword}}"` does not. This is a deliberate scope
limitation, not an oversight: supporting placeholders embedded inside a larger string
would require string interpolation logic (replace just the matched substring, keep the
rest) instead of whole-value replacement, and no dataset in this framework actually
needs that — every secret reference is a field's entire value. Keeping the simpler,
narrower implementation means there's no ambiguity about what counts as "this whole
value is a secret" versus "this value merely contains something that looks like one."
The `\s*` around the key permits incidental whitespace (`{{ uiValidUserPassword }}`)
without requiring exact `{{key}}` formatting, and `[\w.]+` allows dots in the key name
specifically to leave room for a future dotted/namespaced secret key convention, even
though every actual key today is a single camelCase word.

**Why this function recurses through arrays and objects rather than only checking
top-level dataset fields:** dataset shapes are arbitrarily nested (`login.validUser.email`,
`checkout.payment.cvv`) — a shallow, one-level check would only catch a placeholder
sitting at the very top of the parsed object, missing every actual placeholder in this
framework's datasets, all of which live several levels deep. The three-way branch
(array → map each element; string → check for placeholder; object → map each entry;
anything else → return unchanged) is a standard structural-recursion shape that
requires no knowledge of any dataset's specific shape — it's exactly this genericity
that lets one function correctly walk both `UiData` and `ApiData`'s very different
nested structures without dataset-specific code.

**Why `resolveSecrets` is a standalone function in `src/data/utils/`, not a method on
`BaseDataProvider` itself:** it needs zero access to any provider state (no `this`,
no `cache`, no `extension`) — it's a pure function of its input, which is exactly why
it's testable and reasoned-about independently of the provider machinery, and why it
can be imported and called from `BaseDataProvider.load()` (Phase 3) as a plain utility
rather than requiring inheritance or a shared base to access it.

**Checkpoint:** with `config/secrets/qa.env` filled in locally, loading a dataset
containing `{{uiValidUserPassword}}` returns the real password string, and deleting
that line from the local file makes `TestData.load()` throw a clear error instead of
returning the literal placeholder.

---

## Phase 4 — UI layer (Page Object Model)

Build bottom-up: locators → components → base page → page objects → hooks → fixture →
validators → specs.

### Locators — one file per page, plain string constants

**Why selectors are pulled into their own dedicated file per page instead of being
written inline inside each page object's constructor:** this is the single biggest
lever for UI test maintainability in a Page Object Model — when the application's
markup changes (an `id` gets renamed, a class gets refactored), the fix is a one-line
change in exactly one locators file, and every page object, every component built on
top of it, and every spec that exercises that page keeps working completely unchanged.
If selectors were scattered as string literals directly in `LoginPage`'s constructor,
the same markup change would require finding and updating every occurrence
individually, with real risk of missing one. Splitting locators into their own files
(rather than, say, one giant `Locators.ts` file for the whole app) also keeps each file
small and scoped to exactly the page it describes, so a change to the checkout page's
markup can never accidentally touch a locator string that also happens to be used
elsewhere. Every locator is `static readonly` — there's never an instance of
`LoginPageLocators`; the class is purely a namespaced bag of constants, chosen over a
plain exported object for consistency with the rest of the codebase's static-class
convention (`Logger`, `Messages`, the validators).

```ts
// src/locators/LoginPageLocators.ts
export class LoginPageLocators {
  static readonly email = "#userEmail";

  static readonly password = "#userPassword";

  static readonly loginButton = "#login";

  static readonly loginErrorMessage = "[class*='toast-message']";
}
```

```ts
// src/locators/HomePageLocators.ts
export class HomePageLocators {
  static readonly searchInput = "#sidebar input[placeholder='search']";

  static readonly productCard = ".card";

  static readonly productName = "h5 b";

  static readonly cartNavButton = "button[routerlink='/dashboard/cart']";

  static readonly ordersNavButton = "button[routerlink='/dashboard/myorders']";
}
```

```ts
// src/locators/CartPageLocators.ts
export class CartPageLocators {
  static readonly checkoutButton = ".subtotal button";
}
```

```ts
// src/locators/CheckoutPageLocators.ts
export class CheckoutPageLocators {
  static readonly paymentFieldInputs = ".payment__info .field .input.txt";

  static readonly countryInput = "input[placeholder='Select Country']";

  static readonly countryResultsContainer = ".ta-results";

  static readonly countryResultItem = ".ta-item";

  static readonly placeOrderButton = "a.action__submit";
}
```

```ts
// src/locators/OrderConfirmationPageLocators.ts
export class OrderConfirmationPageLocators {
  static readonly confirmationHeading = "h1.hero-primary";

  static readonly ordersHistoryLink = "label[routerlink='/dashboard/myorders']";
}
```

```ts
// src/locators/OrdersPageLocators.ts
export class OrdersPageLocators {
  static readonly orderRows = "table tbody tr";

  static readonly productNameCell = "td:nth-child(3)";

  static readonly priceCell = "td:nth-child(4)";
}
```

`src/locators/index.ts`:

```ts
export * from "./LoginPageLocators";
export * from "./HomePageLocators";
export * from "./CartPageLocators";
export * from "./CheckoutPageLocators";
export * from "./OrderConfirmationPageLocators";
export * from "./OrdersPageLocators";
```

Only exists so a page object *could* import several locator classes from one path
(`import { LoginPageLocators, HomePageLocators } from "../locators"`) if it needed to
— in practice every page object in this codebase imports its own single locators file
directly (e.g. `import { LoginPageLocators } from "../locators/LoginPageLocators"`,
seen below), since a page object almost never needs another page's locators. The
barrel is kept for consistency with every other folder's `index.ts` and for the rare
future case (a component that needs to reference two pages' locators at once).

### Components — thin wrappers around a `Locator`

`src/components/base/BaseComponent.ts`:

```ts
import { expect, Locator } from "@playwright/test";

export abstract class BaseComponent {
  constructor(protected readonly locator: Locator) {}
  async isVisible(): Promise<void> {
    await expect(this.locator).toBeVisible();
  }
  async isHidden(): Promise<void> {
    await expect(this.locator).toBeHidden();
  }
  async isEnabled(): Promise<void> {
    await expect(this.locator).toBeEnabled();
  }
  async isDisabled(): Promise<void> {
    await expect(this.locator).toBeDisabled();
  }
  async scrollIntoView(): Promise<void> {
    await this.locator.scrollIntoViewIfNeeded();
  }
  locatorElement(): Locator {
    return this.locator;
  }
}
```

**Why every component wraps a `Locator` instead of extending or wrapping `Page`
directly:** a Playwright `Locator` is already a lazy, re-evaluated reference to
"whatever element matches this selector, whenever an action is next performed" — it
doesn't need to be re-queried by this framework's own code, so `BaseComponent` doesn't
need to do anything clever to stay "fresh" across page reloads or dynamic re-renders;
it just holds the `Locator` Playwright gave it and defers all the real work to
Playwright's own auto-waiting mechanics.

**Why the visibility/state methods (`isVisible`, `isHidden`, `isEnabled`, `isDisabled`)
wrap `expect(...)` internally instead of just returning a `boolean`:** this looks like
it contradicts the framework's own rule that assertions belong in validators, not page
objects/components — but the distinction is about *what kind of check* this is, not
*where* it lives. `expect(locator).toBeVisible()` is Playwright's auto-retrying
assertion — it polls up to `expect.timeout` (Phase 1) waiting for the condition to
become true, which is exactly the *waiting* behavior you want before interacting with
an element, not a one-shot business-logic check like "does this login attempt succeed."
These four methods are really **wait helpers that happen to be spelled as
assertions** because that's the API Playwright exposes for polling-with-timeout; they
return `Promise<void>` (throwing on timeout) rather than `Promise<boolean>` precisely
so that calling `await someButton.isEnabled()` before clicking it acts as a wait-then-proceed
gate, not a value a test would branch on. The actual business-outcome assertions (Phase
4's `LoginValidator`, `CheckoutValidator`) are a completely separate, deliberate layer
on top of this.

**Why `locatorElement()` exists as an escape hatch:** most component methods
(`Button.click()`, `TextBox.enter()`) cover the common case, but occasionally a page
object needs the raw `Locator` for something the component wrapper doesn't expose —
`CheckoutPage.selectCountry()` (below) needs `.pressSequentially()` directly on the
underlying locator plus a follow-up chained `.locator(...)` call, and
`ApiEngine`-adjacent UI code sometimes needs `.filter()`/`.first()`/`.nth()` chaining
that a thin wrapper class can't practically re-expose for every possible use.
`locatorElement()` is the sanctioned way to drop down to raw Playwright API without
bypassing the component abstraction entirely — a page object still constructs the
component from `page.locator(...)` first, gets the encapsulation benefit everywhere
else, and only reaches for the raw locator when it genuinely needs to.

`src/components/Button.ts`:

```ts
import { BaseComponent } from "./base/BaseComponent";

export class Button extends BaseComponent {
  async click(): Promise<void> {
    await this.locator.click();
  }
  async doubleClick(): Promise<void> {
    await this.locator.dblclick();
  }
  async rightClick(): Promise<void> {
    await this.locator.click({
      button: "right",
    });
  }
}
```

**Why `Button` has exactly these three methods and not more:** `click`/`doubleClick`/
`rightClick` cover every distinct *kind* of pointer interaction a clickable element
actually needs across this whole framework's specs — nothing here has ever needed a
long-press or a drag gesture, so those aren't speculatively added. This class exists
specifically so `LoginPage`/`HomePage`/etc. (below) never write `this.locator.click()`
directly — every click goes through a named, semantically-clear method
(`this.loginButton.click()` reads as intent, not implementation).

`src/components/form/TextBox.ts`:

```ts
import { BaseComponent } from "../base/BaseComponent";

export class TextBox extends BaseComponent {
  async enter(text: string): Promise<void> {
    await this.locator.fill(text);
  }
  async append(text: string): Promise<void> {
    await this.locator.pressSequentially(text);
  }
  async clear(): Promise<void> {
    await this.locator.clear();
  }
  async value(): Promise<string> {
    return await this.locator.inputValue();
  }
}
```

**Why both `enter()` (calling `.fill()`) and `append()` (calling
`.pressSequentially()`) exist, rather than just one "type text" method:** these map to
two genuinely different Playwright input strategies with different real-world
behavior. `.fill()` sets a field's value directly/instantly — the right choice for the
overwhelming majority of form-filling (`LoginPage.login`'s email/password entry, Phase
4), where the test only cares about the final value and speed matters. `.pressSequentially()`
dispatches individual keystroke events one at a time, which matters specifically for
inputs wired to fire per-keystroke JavaScript (an autocomplete/typeahead, like the
country selector in `CheckoutPage.selectCountry`, below) — `.fill()` on such a field
would set the value but never trigger the keystroke-driven suggestion dropdown to
appear, since no individual `keydown`/`input` events actually fire. Having both as
distinct, named methods means a page object author picks the correct one deliberately
based on what the target field actually needs, instead of discovering the difference
the hard way when a `.fill()` silently fails to open a dropdown.

`src/components/form/CheckBox.ts`:

```ts
import { BaseComponent } from "../base/BaseComponent";

export class CheckBox extends BaseComponent {
  async check(): Promise<void> {
    await this.locator.check();
  }
  async uncheck(): Promise<void> {
    await this.locator.uncheck();
  }
  async toggle(): Promise<void> {
    if (await this.locator.isChecked()) {
      await this.uncheck();
    } else {
      await this.check();
    }
  }
  async isChecked(): Promise<boolean> {
    return await this.locator.isChecked();
  }
}
```

**Why `check()`/`uncheck()` are idempotent Playwright calls but `toggle()` is written
as an explicit read-then-branch instead of a single Playwright API call:** Playwright's
`.check()`/`.uncheck()` are already idempotent — calling `.check()` on an
already-checked box is a safe no-op, not a double-toggle — so they're passed through
directly with no extra logic needed. There is no built-in "flip whatever the current
state is" Playwright method, though, so `toggle()` has to explicitly read the current
state first (`await this.locator.isChecked()`) and then call the appropriate one of the
two idempotent operations. This class isn't currently used by any page object in this
framework's own specs (the checkout flow here has no checkbox interactions) — it exists
as a complete, ready-to-use component for the next feature that needs one, following
the same wrapper pattern as `Button`/`TextBox`/`Label`.

`src/components/display/Label.ts`:

```ts
import { BaseComponent } from "../base/BaseComponent";

export class Label extends BaseComponent {
  async text(): Promise<string> {
    return (await this.locator.textContent()) ?? "";
  }
}
```

**Why `?? ""` on the return value:** Playwright's `.textContent()` returns `string |
null` — `null` specifically when the element isn't found at all, as opposed to an
empty string, which is what you'd get from a genuinely-empty-but-present element.
Coercing `null` to `""` here means every caller of `Label.text()` (`LoginPage.getErrorMessage()`,
`OrderConfirmationPage.getConfirmationMessage()`, Phase 4) can treat the return value as
a plain `string` without a null check at every call site — consistent with this
framework's broader preference (seen in `TokenManager.getToken()`, Phase 5, and
`OrdersPage.getLatestOrderPrice()`, below) for absorbing a possibly-nullable value into
a safe default at the lowest possible layer rather than pushing `| null` handling up
into every consumer.

`src/components/index.ts`:

```ts
export * from "./base/BaseComponent";
export * from "./Button";
export * from "./form/TextBox";
export * from "./form/CheckBox";
export * from "./display/Label";
```

Note `BaseComponent` (the abstract base) is re-exported from its own path
(`./base/BaseComponent`) alongside the four concrete components — every page object
imports what it needs (`Button`, `Label`, `TextBox`) from this single barrel
(`import { Button, Label, TextBox } from "../components"`, seen in `LoginPage` below),
never from each component's individual file.

### `src/pages/BasePage.ts`

Deliberately thin — no `expect(...)` assertions here; those belong in validators.

```ts
import { expect, Page } from "@playwright/test";

import { Logger } from "../utils/Logger";

export abstract class BasePage {
  constructor(protected readonly page: Page) {}
  protected async navigate(url: string): Promise<void> {
    Logger.info(`Navigating to: ${url}`);
    await this.page.goto(url);
  }
  async reload(): Promise<void> {
    await this.page.reload();
  }
  async currentUrl(): Promise<string> {
    return this.page.url();
  }
  async verifyUrl(url: string | RegExp): Promise<void> {
    await expect(this.page).toHaveURL(url);
  }
  async verifyTitle(title: string | RegExp): Promise<void> {
    await expect(this.page).toHaveTitle(title);
  }
  async waitForLoad(): Promise<void> {
    await this.page.waitForLoadState("domcontentloaded");
  }
  protected getPage(): Page {
    return this.page;
  }
}
```

**What each method is for, and why the class is `abstract`:** `BasePage` is never
instantiated directly — it's `abstract`, and every real page (`LoginPage`, `HomePage`,
etc.) extends it, inheriting a small set of primitives every page genuinely needs
regardless of what it actually shows:

- `protected async navigate(url)` — `protected`, not `public`, deliberately: a spec
  should never be able to call `basePage.navigate("/some/arbitrary/url")` directly on
  an already-typed page object — navigation is meant to go through each page's *own*
  public `navigate()` override (like `LoginPage.navigate()` calling `super.navigate(AppRoutes.LOGIN)`,
  below), which hardcodes exactly which route that specific page object represents. This
  is what keeps "which URL does the login page live at" defined in exactly one place
  (`AppRoutes.LOGIN`) rather than something a test could accidentally get wrong by
  passing the wrong string.
- `Logger.info(...)` before every navigation — this is what makes `logs/execution.log`
  (Phase 2) a readable narrative of a UI test's flow: every page transition is logged
  automatically, for free, just by extending `BasePage`, with no page object author
  needing to remember to add their own logging call.
- `reload()`/`currentUrl()` — thin, direct pass-throughs to the underlying `Page`.
  These exist purely so a test never needs to reach for the raw `page` fixture at all —
  everything a test does goes through a page-object method, keeping the raw Playwright
  `Page` object entirely an implementation detail page objects hide.
- `verifyUrl`/`verifyTitle` — these *do* wrap `expect(...)`, which might look like it
  contradicts "no assertions in page objects" — but exactly like `BaseComponent`'s
  visibility helpers (above), these are Playwright's auto-retrying wait-and-check
  primitives (`toHaveURL`/`toHaveTitle` poll until true or timeout), not business-logic
  assertions about test outcomes. In practice, this framework's actual specs use the
  dedicated validators (`LoginValidator.expectLoginSuccess`, which checks `currentUrl()`
  directly) rather than calling `verifyUrl` — these two methods exist as available,
  generic building blocks for the common "wait until the URL/title matches" pattern any
  future page object might need, following the same design intent as `BaseComponent`'s
  parallel methods.
- `waitForLoad()` — a generic default (`waitForLoadState("domcontentloaded")`) that
  most pages never even need to call explicitly, since Playwright's own auto-waiting on
  subsequent actions already handles most timing concerns. `LoginPage` (below)
  *overrides* this with something far more specific
  (`page.waitForURL(/\/dashboard\/dash/)`) — this is a deliberate signal of the
  override pattern this base class is designed to support: a subclass can always
  replace the generic default with page-specific waiting logic that better captures
  "this page has actually finished loading" for that particular page's real behavior.
- `protected getPage()` — an escape hatch mirroring `BaseComponent.locatorElement()`
  (above), for the rare page-object method that needs the raw `Page` for something none
  of `BasePage`'s own methods cover (e.g. `HomePage.addProductToCart`, below, needs
  `this.page.locator(...)` directly to build a filtered card locator that doesn't map
  cleanly onto any single named `BasePage` method).

### Page objects

`src/pages/LoginPage.ts`:

```ts
import { Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { Button, Label, TextBox } from "../components";
import { LoginPageLocators } from "../locators/LoginPageLocators";
import { AppRoutes } from "../constants/AppRoutes";
import { User } from "../models/User";
import { AllureHelper } from "../reporting/AllureHelper";

export class LoginPage extends BasePage {
  private readonly email: TextBox;
  private readonly password: TextBox;
  private readonly loginButton: Button;
  private readonly errorMessage: Label;

  constructor(page: Page) {
    super(page);

    this.email = new TextBox(page.locator(LoginPageLocators.email));

    this.password = new TextBox(page.locator(LoginPageLocators.password));

    this.loginButton = new Button(page.locator(LoginPageLocators.loginButton));

    this.errorMessage = new Label(
      page.locator(LoginPageLocators.loginErrorMessage),
    );
  }
  async navigate(): Promise<void> {
    await super.navigate(AppRoutes.LOGIN);
  }
  async login(user: User): Promise<void> {
    await AllureHelper.step("Enter Email", async () => {
      await this.email.enter(user.email);
    });

    await AllureHelper.step("Enter Password", async () => {
      await this.password.enter(user.password);
    });

    await AllureHelper.step("Click Login", async () => {
      await this.loginButton.click();
    });
  }
  async waitForLoad(): Promise<void> {
    await this.page.waitForURL(/\/dashboard\/dash/);
  }
  async getErrorMessage(): Promise<string> {
    return this.errorMessage.text();
  }
}
```

**A close read of every design decision in this one file, since it's the template
every other page object follows:**

- **Constructor-time locator wiring:** every field (`email`, `password`, `loginButton`,
  `errorMessage`) is constructed once, in the constructor, from `page.locator(...)`
  wrapped in the matching component class. This happens exactly once per page-object
  instance — and since `testFixture.ts` (below) creates a fresh `LoginPage` per test,
  each test gets fresh component instances too, with no state leaking between tests.
  Playwright locators are lazy (they don't query the DOM until an action is performed
  on them), so constructing them eagerly here costs nothing even before the page has
  navigated anywhere.
- **`private readonly` fields:** nothing about a page object's internal component
  wiring should ever be reassigned after construction (`readonly`) or accessed from
  outside the class (`private`) — a spec interacts with `LoginPage` purely through its
  public methods (`login`, `getErrorMessage`, `navigate`), never by reaching in to grab
  `loginPage.loginButton` directly. This is the actual mechanism that keeps "how the
  login page's DOM is structured" fully encapsulated.
- **`navigate()` overriding the inherited `protected navigate(url)`:** this public
  method is what a spec actually calls (`await loginPage.navigate()`), taking no
  arguments — the URL itself (`AppRoutes.LOGIN`) is baked in here, not something a
  caller supplies, because *this* page object only ever represents *the* login page.
- **Why `login()` wraps each step in `AllureHelper.step(...)` instead of just three
  plain `await` calls:** this is what makes the Allure report (Phase 6) show "Enter
  Email" / "Enter Password" / "Click Login" as three distinct, individually
  pass/fail-attributable steps nested under the test, rather than one opaque `login()`
  call — when a test fails partway through, the report immediately shows which specific
  sub-step failed, without needing to read the raw log to figure that out.
- **`login(user: User)` taking the shared `User` domain type, not two separate
  `email`/`password` string parameters:** this is what lets a spec write `await
  loginPage.login(user)` where `user` came straight out of `structuredClone(uiData.login.validUser)`
  (Phase 3's `UiData` model) with zero reshaping — the page object's method signature
  and the test-data model's shape are designed to match exactly.
- **Why `waitForLoad()` is overridden here specifically to `page.waitForURL(/\/dashboard\/dash/)`
  instead of using the inherited generic version:** a successful login on this app
  causes an SPA route transition (not necessarily a full page load event) to the
  dashboard — waiting for `domcontentloaded` (the base class's generic default) could
  resolve too early, before the redirect actually completes, since the initial page
  never technically reloads. Waiting for the specific URL pattern is a stronger,
  more accurate signal that login genuinely succeeded and the app has moved on, which
  is exactly why `login.spec.ts`'s positive-scenario test calls `waitForLoad()` right
  after `login()` before checking the final URL.
- **`getErrorMessage()` returning a plain string, not asserting anything itself:** the
  page object's job stops at "here is what the page currently shows" — deciding whether
  that string constitutes success or failure is `LoginValidator`'s job (below), keeping
  the separation of concerns intact even for this one-line method.

`src/pages/HomePage.ts`:

```ts
import { Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { Button, TextBox } from "../components";
import { HomePageLocators } from "../locators/HomePageLocators";
import { AppRoutes } from "../constants/AppRoutes";
import { AllureHelper } from "../reporting/AllureHelper";

export class HomePage extends BasePage {
  private readonly searchBox: TextBox;
  private readonly cartNavButton: Button;
  private readonly ordersNavButton: Button;

  constructor(page: Page) {
    super(page);

    this.searchBox = new TextBox(page.locator(HomePageLocators.searchInput));
    this.cartNavButton = new Button(page.locator(HomePageLocators.cartNavButton));
    this.ordersNavButton = new Button(page.locator(HomePageLocators.ordersNavButton));
  }

  async navigate(): Promise<void> {
    await super.navigate(AppRoutes.DASHBOARD);
  }

  async searchProduct(productName: string): Promise<void> {
    await AllureHelper.step(`Search product: ${productName}`, async () => {
      await this.searchBox.enter(productName);
    });
  }

  async addProductToCart(productName: string): Promise<void> {
    await AllureHelper.step(`Add product to cart: ${productName}`, async () => {
      const card = this.page
        .locator(HomePageLocators.productCard)
        .filter({
          has: this.page.locator(HomePageLocators.productName, { hasText: productName }),
        })
        .first();

      await card.getByText("Add To Cart").click();
    });
  }

  async goToCart(): Promise<void> {
    await AllureHelper.step("Go to cart", async () => {
      await this.cartNavButton.click();
    });
  }

  async goToOrders(): Promise<void> {
    await AllureHelper.step("Go to orders", async () => {
      await this.ordersNavButton.click();
    });
  }
}
```

**What's different here from `LoginPage`, and why:** `addProductToCart` is the one
method in this file that drops to `this.page.locator(...)` directly (via the
`protected getPage()`-style raw access) instead of going through a `TextBox`/`Button`
component wrapper — because what it needs isn't a static, single element but a
*dynamically filtered* one: "whichever `.card` contains a product name matching
`productName`." `.filter({ has: this.page.locator(HomePageLocators.productName, {
hasText: productName }) })` is genuinely Playwright locator-chaining logic (find all
cards, keep only the one containing this specific text), which doesn't map onto any of
`BaseComponent`'s fixed methods — this is exactly the kind of situation the
`locatorElement()`/raw-page escape hatch (discussed under `BasePage`, above) exists for.
`.first()` guards against the search term matching more than one product card. Note
this method takes a `productName: string` parameter rather than having the product
baked in — `HomePage` isn't tied to one specific product, unlike `LoginPage`, which is
inherently tied to one specific page's one login form.

`src/pages/CartPage.ts`:

```ts
import { Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { Button } from "../components";
import { CartPageLocators } from "../locators/CartPageLocators";
import { AllureHelper } from "../reporting/AllureHelper";

export class CartPage extends BasePage {
  private readonly checkoutButton: Button;

  constructor(page: Page) {
    super(page);

    this.checkoutButton = new Button(
      page.locator(CartPageLocators.checkoutButton, { hasText: "Checkout" }),
    );
  }

  async checkout(): Promise<void> {
    await AllureHelper.step("Proceed to checkout", async () => {
      await this.checkoutButton.click();
    });
  }
}
```

**Why this is the smallest page object in the framework, and why that's fine:** the
cart page, in this test flow, only ever needs one interaction — proceed to checkout.
There's no pressure to pre-build methods for things no spec actually exercises yet
(viewing cart contents, removing an item) — a page object grows exactly as far as the
specs that need it, which keeps every file here honestly reflecting what's actually
tested rather than aspirational, unused surface area. Note the locator constructor
combines a CSS selector *and* a Playwright text filter (`{ hasText: "Checkout" }`) in
one call — this disambiguates the checkout button from any other `button` that might
also match `.subtotal button` structurally.

`src/pages/CheckoutPage.ts`:

```ts
import { Locator, Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { Button, TextBox } from "../components";
import { CheckoutPageLocators } from "../locators/CheckoutPageLocators";
import { PaymentDetails } from "../models/PaymentDetails";
import { AllureHelper } from "../reporting/AllureHelper";

export class CheckoutPage extends BasePage {
  private readonly paymentFields: Locator;
  private readonly countryInput: TextBox;
  private readonly placeOrderButton: Button;

  constructor(page: Page) {
    super(page);

    this.paymentFields = page.locator(CheckoutPageLocators.paymentFieldInputs);
    this.countryInput = new TextBox(page.locator(CheckoutPageLocators.countryInput));
    this.placeOrderButton = new Button(page.locator(CheckoutPageLocators.placeOrderButton));
  }

  async enterPaymentDetails(payment: PaymentDetails): Promise<void> {
    await AllureHelper.step("Enter CVV", async () => {
      await this.paymentFields.nth(1).fill(payment.cvv);
    });

    await AllureHelper.step("Enter name on card", async () => {
      await this.paymentFields.nth(2).fill(payment.nameOnCard);
    });
  }

  async selectCountry(country: string): Promise<void> {
    await AllureHelper.step(`Select country: ${country}`, async () => {
      const countryField = this.countryInput.locatorElement();
      const escapedCountry = country.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
      const exactCountryPattern = new RegExp(`^\\s*${escapedCountry}\\s*$`);

      await countryField.click();
      await countryField.pressSequentially(country);

      await this.page
        .locator(CheckoutPageLocators.countryResultsContainer)
        .locator(CheckoutPageLocators.countryResultItem)
        .filter({ hasText: exactCountryPattern })
        .click();
    });
  }

  async placeOrder(): Promise<void> {
    await AllureHelper.step("Place order", async () => {
      await this.placeOrderButton.click();
    });
  }
}
```

**Why `paymentFields` is typed as a raw `Locator`, not wrapped in `TextBox`, and why
CVV/name-on-card are accessed by `.nth(1)`/`.nth(2)`:** the underlying page renders
several payment inputs sharing one CSS class (`.payment__info .field .input.txt`) with
no individually distinguishing selector for "the CVV one" specifically — the only way
to address them is by position within the matched set. A single `TextBox` wrapper is
built around one specific locator at construction time; here, the *same* locator
(matching multiple elements) needs `.nth(n)` applied per-field at the point of use, so
keeping it as a raw `Locator` and indexing into it directly (`this.paymentFields.nth(1).fill(...)`)
is simpler and more honest about what's actually happening than trying to force it
through a wrapper built for a single, unambiguous element.

**`selectCountry` is the most involved method in the whole page-object layer — why:**
the country field is a live-search/autocomplete input, not a plain dropdown, so
selecting a value requires three distinct steps in sequence: (1) type the country name
character-by-character so the app's own JS-driven suggestion list actually populates —
this is exactly why `pressSequentially` (not `.fill()`) is used here, per the
`TextBox.append` rationale above; (2) build a regex that matches the suggestion text
*exactly*, not just as a substring; (3) filter the results list down to the one item
matching that exact pattern and click it. The regex-escaping line
(`country.replace(/[.*+?^${}()|[\]\\]/g, "\\$&")`) exists because `country` is a plain
string from test data that could in principle contain regex-special characters — even
though today's actual dataset values (`"India"`) don't need it, escaping unconditionally
means this method stays correct if a future dataset ever used a country name containing
a character like `.` or `(` that would otherwise be misinterpreted as regex syntax
rather than a literal character to match. The `^\s*...\s*$` wrapping around the escaped
name is what turns "contains this substring" into "is exactly this text, ignoring
incidental whitespace" — necessary because a looser substring match risks selecting a
different, longer country name that happens to contain the target as a prefix (a real
risk with country name lists).

`src/pages/OrderConfirmationPage.ts`:

```ts
import { Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { Button, Label } from "../components";
import { OrderConfirmationPageLocators } from "../locators/OrderConfirmationPageLocators";
import { AllureHelper } from "../reporting/AllureHelper";

export class OrderConfirmationPage extends BasePage {
  private readonly confirmationHeading: Label;
  private readonly ordersHistoryLink: Button;

  constructor(page: Page) {
    super(page);

    this.confirmationHeading = new Label(
      page.locator(OrderConfirmationPageLocators.confirmationHeading),
    );
    this.ordersHistoryLink = new Button(
      page.locator(OrderConfirmationPageLocators.ordersHistoryLink),
    );
  }

  async getConfirmationMessage(): Promise<string> {
    return (await this.confirmationHeading.text()).trim();
  }

  async goToOrders(): Promise<void> {
    await AllureHelper.step("Go to orders history", async () => {
      await this.ordersHistoryLink.click();
    });
  }
}
```

**Why `getConfirmationMessage()` calls `.trim()` on the result:** the heading element's
raw text content commonly carries incidental leading/trailing whitespace from how the
markup happens to be indented in the DOM — trimming it here, once, at the page-object
boundary, means `CheckoutValidator.expectOrderConfirmed` (below) can do a clean
`toContain` comparison against `Messages.ORDER_CONFIRMATION_SUCCESS` without needing to
account for whitespace noise itself. This mirrors the general principle seen in
`Label.text()` (`?? ""`) and `TokenManager.getToken()` (throwing instead of returning
`undefined`) — normalize a messy real-world value once, close to its source, rather
than pushing that cleanup responsibility onto every consumer.

`src/pages/OrdersPage.ts`:

```ts
import { Page } from "@playwright/test";

import { BasePage } from "./BasePage";
import { OrdersPageLocators } from "../locators/OrdersPageLocators";
import { AppRoutes } from "../constants/AppRoutes";

export class OrdersPage extends BasePage {
  constructor(page: Page) {
    super(page);
  }

  async navigate(): Promise<void> {
    await super.navigate(AppRoutes.ORDERS);
  }

  async isProductInOrders(productName: string): Promise<boolean> {
    const matchingRow = this.page
      .locator(OrdersPageLocators.orderRows)
      .filter({ has: this.page.locator(OrdersPageLocators.productNameCell, { hasText: productName }) });

    try {
      await matchingRow.first().waitFor({ state: "visible" });
      return true;
    } catch {
      return false;
    }
  }

  async getLatestOrderPrice(): Promise<string> {
    const latestRow = this.page.locator(OrdersPageLocators.orderRows).first();

    return (await latestRow.locator(OrdersPageLocators.priceCell).textContent())?.trim() ?? "";
  }
}
```

**Why `isProductInOrders` returns a plain `boolean` via try/catch on a `.waitFor()`,
rather than letting a "not found" case simply throw:** this method's entire purpose is
to answer the yes/no question "did the order actually show up in history" — and the
natural way to check for an element's *eventual* presence with Playwright is
`.waitFor({ state: "visible" })`, which throws if the element never appears within the
timeout. Catching that throw and converting it into `return false` is what turns "wait
for this, or throw" into "tell me whether this happened" — exactly the shape
`CheckoutValidator.expectOrderInHistory(isPresent: boolean)` (below) needs to receive a
plain boolean it can assert on, keeping the actual pass/fail *decision* (is `false`
here a failure) in the validator, while the page object only ever reports observed
fact. This is the same page-object-observes/validator-decides split seen throughout
Phase 4, applied to a case where the observation itself requires waiting logic rather
than an instant DOM read.

### Validators — assertions live here, not in page objects or specs

`src/validators/LoginValidator.ts`:

```ts
import { expect } from "@playwright/test";
import { Messages } from "../constants";

export class LoginValidator {
  static expectLoginFailed(message: string): void {
    expect(message).toContain(Messages.LOGIN_FAILURE);
  }

  static expectLoginSuccess(currentUrl: string): void {
    expect(currentUrl).toContain("/dashboard/dash");
  }
}
```

**Why this separation of "page objects observe, validators decide" exists as a hard
rule rather than a loose guideline:** it's what keeps two very different kinds of
change from ever colliding in the same file. A page object changes when the
*application's* structure changes (a selector moves, a page adds a new element) — a
validator changes when the *expected business outcome* changes (what counts as
"login succeeded" is redefined, an error message's wording is updated). If both lived
in the same class, a UI refactor and a requirements change would touch the same file
for unrelated reasons, and a reviewer diffing that file couldn't tell at a glance which
kind of change they were looking at. Concretely, `LoginPage.getErrorMessage()` (Phase
4) never decides whether an error is expected — it just returns whatever string the
page currently shows; `LoginValidator.expectLoginFailed` is the one and only place that
decides "containing `Messages.LOGIN_FAILURE` means the login correctly failed."

**Why these methods take primitive values (`message: string`, `currentUrl: string`)
rather than a `Page` or a page-object instance:** this is a deliberate decoupling — a
validator never needs to *drive* the browser, only to judge a value already extracted
by a page object. That's what makes validators trivially reusable and testable in
isolation from Playwright's `Page` object entirely, and what makes the data flow in
every spec explicit and readable: `LoginValidator.expectLoginSuccess(await
loginPage.currentUrl())` reads left-to-right as "get this value, then check it,"
with no hidden coupling to browser state inside the validator itself.

`src/validators/CheckoutValidator.ts`:

```ts
import { expect } from "@playwright/test";
import { Messages } from "../constants";

export class CheckoutValidator {
  static expectOrderConfirmed(message: string): void {
    expect(message).toContain(Messages.ORDER_CONFIRMATION_SUCCESS);
  }

  static expectOrderInHistory(isPresent: boolean): void {
    expect(isPresent).toBe(true);
  }
}
```

**Why `expectOrderInHistory` exists as a one-line wrapper around `expect(isPresent).toBe(true)`
instead of specs calling `expect()` directly on the boolean:** this might look like
pure ceremony around a trivial check, but it's consistency, not laziness — every
business-outcome assertion in this framework goes through a named validator method, no
exceptions, which is exactly what makes the ESLint `expect-expect` override (Phase 0,
`assertFunctionPatterns: ["^expect"]`) able to reliably recognize every real assertion
in the suite by its naming convention. A spec that occasionally called bare `expect()`
directly for "simple enough" checks would silently erode that convention and eventually
make it unreliable as a signal of "this test actually asserts something."

### Hooks — logging context around every test, UI or API

`src/hooks/beforeEachHook.ts`:

```ts
import { test as base } from "@playwright/test";
import { Logger } from "../utils/Logger";
import { ENV } from "../../config/envLoader";

export function registerBeforeEachHook(test: typeof base): void {
  test.beforeEach(async ({}, testInfo) => {
    Logger.setContext(`[w${testInfo.workerIndex}] [${testInfo.title}]`);

    Logger.info("====================================");
    Logger.info(`Test        : ${testInfo.title}`);
    Logger.info(`Project     : ${testInfo.project.name}`);
    Logger.info(`Environment : ${ENV.ENVIRONMENT}`);
    Logger.info(`Retry       : ${testInfo.retry}`);
    Logger.info("====================================");
  });
}
```

**Why this is a function that *registers* a hook onto a passed-in `test`, rather than
a `test.beforeEach` call written directly at module scope:** the same hook logic has to
attach to *two different* Playwright `test` objects in this framework — the UI
fixture's `test` (`src/fixtures/testFixture.ts`) and the API fixture's `test`
(`src/api/fixtures/apiTest.ts`, Phase 5) — and Playwright's fixture system requires
`beforeEach` to be called on the specific extended `test` instance a spec actually
imports, not on some shared, separately-imported base. Wrapping the registration in a
function parameterized on `test: typeof base` is what lets both fixtures call
`registerBeforeEachHook(test)` on their own respective `test` object and get identical
logging behavior, with the logic written exactly once.

**Why `Logger.setContext(...)` is set as literally the first line of the hook body:**
every subsequent log line in the test — including ones written by page objects, the
API engine, or the test itself — needs to already carry the `[w<index>] [<title>]`
prefix (Phase 2's `Logger.setContext`), so it has to be set before anything else in the
test can possibly log. The banner logged right after (`Test`/`Project`/`Environment`/`Retry`)
is what makes `logs/execution.log` self-documenting for post-run debugging — reading
the log file alone, without cross-referencing the Playwright report, tells you exactly
which environment a failing test ran against and whether it was a retry attempt, which
matters because a test that fails only on retry (`Retry: 1`) often indicates flakiness
rather than a genuine regression, a distinction worth having visible directly in the
logs.

`src/hooks/afterEachHook.ts`:

```ts
import { test as base } from "@playwright/test";
import { Logger } from "../utils/Logger";

export function registerAfterEachHook(test: typeof base): void {
  test.afterEach(async ({}, testInfo) => {
    Logger.info("====================================");
    Logger.info(`Status   : ${testInfo.status}`);
    Logger.info(`Duration : ${testInfo.duration} ms`);
    Logger.info("====================================");
    if (testInfo.status !== testInfo.expectedStatus) {
      Logger.error(`FAILED : ${testInfo.title}`);
    }

    Logger.setContext("");
  });
}
```

**Why `Logger.setContext("")` runs last, at the very end of `afterEach`:** since
`currentContext` is one shared, mutable, module-level value (Phase 2), it *must* be
explicitly cleared after every test — otherwise the *next* test to run in this same
worker process would inherit the previous test's now-stale context prefix on its own
early log lines, right up until its own `beforeEach` overwrites it. Clearing it here
closes that window entirely: there's never a moment between two tests in the same
worker where a log line could be mislabeled.

**Why the failure log (`Logger.error(...)`) checks `testInfo.status !==
testInfo.expectedStatus` instead of just `testInfo.status !== "passed"`:** Playwright
supports `test.fail()`-annotated tests that are *expected* to fail — for such a test,
`testInfo.expectedStatus` is `"failed"`, and a `status` of `"failed"` in that case is
the *correct*, non-alarming outcome. Comparing against `expectedStatus` rather than a
hardcoded `"passed"` is what keeps this hook from spamming `logs/error.log` with
false-alarm "FAILED" entries for tests that are deliberately marked as expected to
fail — it only logs an error when the actual result diverges from what was expected,
which is the only case that genuinely warrants attention.

`src/hooks/testHook.ts`:

```ts
import { test as base } from "@playwright/test";
import { registerBeforeEachHook } from "./beforeEachHook";
import { registerAfterEachHook } from "./afterEachHook";

export function registerTestHooks(test: typeof base): void {
  registerBeforeEachHook(test);
  registerAfterEachHook(test);
}
```

**Why this trivial two-line function is worth having as its own file:** both
`src/fixtures/testFixture.ts` and `src/api/fixtures/apiTest.ts` (Phase 5) need to call
*both* hooks, in the same before/after order, every single time — `registerTestHooks`
is the one function both fixtures actually call, so the "hooks are registered as a
pair, in this order" decision is made exactly once, here, rather than each fixture
file independently remembering to call both `registerBeforeEachHook` and
`registerAfterEachHook` itself (and potentially getting the order wrong, or forgetting
one, in one of the two places).

### `src/fixtures/testFixture.ts`

```ts
import { test as base, expect } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderConfirmationPage } from "../pages/OrderConfirmationPage";
import { OrdersPage } from "../pages/OrdersPage";
import { registerTestHooks } from "../hooks/testHook";

type FrameworkFixtures = {
  loginPage: LoginPage;
  homePage: HomePage;
  cartPage: CartPage;
  checkoutPage: CheckoutPage;
  orderConfirmationPage: OrderConfirmationPage;
  ordersPage: OrdersPage;
};

export const test = base.extend<FrameworkFixtures>({
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },
  homePage: async ({ page }, use) => {
    await use(new HomePage(page));
  },
  cartPage: async ({ page }, use) => {
    await use(new CartPage(page));
  },
  checkoutPage: async ({ page }, use) => {
    await use(new CheckoutPage(page));
  },
  orderConfirmationPage: async ({ page }, use) => {
    await use(new OrderConfirmationPage(page));
  },
  ordersPage: async ({ page }, use) => {
    await use(new OrdersPage(page));
  },
});

registerTestHooks(test);

export { expect };
```

**Why `base.extend<FrameworkFixtures>({...})` is the mechanism, rather than each spec
constructing its own page objects in a `beforeEach`:** Playwright's fixture system
gives each fixture its own lazily-created, automatically-scoped-per-test instance —
`loginPage: async ({ page }, use) => { await use(new LoginPage(page)); }` means a
fresh `LoginPage` is created from that test's own `page` *only if the test actually
destructures `loginPage` as a parameter*, and Playwright handles creating, providing,
and disposing of it with no manual setup/teardown code in any spec. This is what lets
`login.spec.ts` write `async ({ loginPage }) => {...}` and get a fully-wired page object
with zero boilerplate, while `checkout.spec.ts` can destructure *six* fixtures at once
(`loginPage, homePage, cartPage, checkoutPage, orderConfirmationPage, ordersPage`) and
each is independently created from the same underlying `page`, letting one test flow
naturally across the objects representing each page it visits.

**Why every fixture function follows the identical `async ({ page }, use) => { await
use(new X(page)); }` shape:** this uniformity isn't accidental — it's what makes adding
the *next* page object purely mechanical: extend the `FrameworkFixtures` type with one
new field, add one fixture function following the exact same pattern, and it's
immediately available to every spec that imports `test` from this file. There's no
special-casing needed regardless of how many page objects the fixture eventually holds.

**Why `registerTestHooks(test)` is called at module scope, right after `test` is
created, rather than being left for each spec to call itself:** this guarantees that
*every* spec that imports `test` from this file automatically gets the logging hooks
(Phase 4's `beforeEachHook`/`afterEachHook`) with zero chance of a spec author
forgetting to wire them up — the hooks are an inherent property of importing this
fixture, not an opt-in step.

**Why `expect` is re-exported from here instead of specs importing it from
`@playwright/test` directly:** this is a small but important discipline-enforcing
choice — if a spec imports `test` from `../../src/fixtures/testFixture` but `expect`
from `@playwright/test` directly, it still works today (they're the same underlying
`expect`), but it invites a spec to also import `test` from the wrong place later, since
there'd be no established habit of "everything test-related comes from the fixture
file." Re-exporting `expect` here means a spec's import line
(`import { test, expect } from "../../src/fixtures/testFixture"`) is a single,
consistent source for both, with the fixture file as the one deliberate seam between
this framework's specs and raw Playwright.

### Specs — always import `test`/`expect` from the fixture, never `@playwright/test` directly

`tests/authentication/login.spec.ts`:

```ts
import { test } from "../../src/fixtures/testFixture";
import { TestData } from "../../src/data";
import { UiData } from "../../src/data/models/UiData";
import { LoginValidator } from "../../src/validators/LoginValidator";

test.describe("Authentication :: Login", () => {
  let uiData: UiData;

  test.beforeAll(async () => {
    uiData = await TestData.load<UiData>("uiData");
  });

  test.describe("Positive Scenarios", () => {
    test("Valid user should login successfully @smoke", async ({ loginPage }) => {
      const user = structuredClone(uiData.login.validUser);

      await loginPage.navigate();

      await loginPage.login(user);

      await loginPage.waitForLoad();

      LoginValidator.expectLoginSuccess(await loginPage.currentUrl());
    });
  });

  test.describe("Negative Scenarios", () => {
    test("Invalid password should display login error @regression", async ({ loginPage }) => {
      const loginUser = structuredClone(uiData.login.invalidPassword);

      await loginPage.navigate();

      await loginPage.login(loginUser);

      LoginValidator.expectLoginFailed(await loginPage.getErrorMessage());
    });

    test("Invalid email should display login error @regression", async ({ loginPage }) => {
      const loginUser = structuredClone(uiData.login.invalidEmail);

      await loginPage.navigate();

      await loginPage.login(loginUser);

      LoginValidator.expectLoginFailed(await loginPage.getErrorMessage());
    });
  });
});
```

**Why this spec doesn't import `expect` at all, even though it's re-exported from the
fixture:** every assertion here goes through `LoginValidator`, which owns its own
`import { expect } from "@playwright/test"` internally — the spec itself never calls
`expect()` directly, so it has no need to import it. This is the concrete, visible
proof that the "assertions live in validators" rule (Phase 4 intro) is actually being
followed here, not just stated as a guideline.

**Why `test.beforeAll` loads the data once, and why `structuredClone(...)` wraps the
data at each use site inside the tests:** `TestData.load()`'s own caching (Phase 3)
already means calling it repeatedly is cheap, but `beforeAll` runs it exactly once for
the whole `describe` block regardless, which is the more efficient and idiomatic
Playwright pattern — data that doesn't vary per test shouldn't be reloaded per test.
`structuredClone(uiData.login.validUser)` inside each test, though, is a distinct and
important second precaution: `uiData` is a *shared* object reference across every test
in the file (assigned once in `beforeAll`), and because tests run with
`fullyParallel: true` (Phase 1), if a page object or component further downstream ever
mutated the object it was handed, an un-cloned shared reference could let one test's
mutation leak into a concurrently-running test that also reads `uiData.login.validUser`.
`structuredClone` gives each test its own fully independent deep copy, closing off that
entire class of parallel-test interference regardless of what any current or future
page-object code does with the object it receives.

**Why the three tests are grouped into nested `describe` blocks
(`Positive Scenarios`/`Negative Scenarios`) instead of one flat list:** this is purely
organizational, but it matters for report readability — the Allure and HTML reports
(Phase 6) both render `describe` nesting as a hierarchy, so a reviewer scanning the
report immediately sees "2 negative scenarios, both passing" as a grouped unit rather
than three unrelated-looking top-level test names.

`tests/checkout/checkout.spec.ts` — a full multi-page-object flow through several
fixtures at once:

```ts
import { test } from "../../src/fixtures/testFixture";
import { TestData } from "../../src/data";
import { UiData } from "../../src/data/models/UiData";
import { CheckoutValidator } from "../../src/validators/CheckoutValidator";

test.describe("Shopping :: Checkout", () => {
  let uiData: UiData;

  test.beforeAll(async () => {
    uiData = await TestData.load<UiData>("uiData");
  });

  test("Valid user should complete checkout and see the order in history @smoke @e2e", async ({
    loginPage,
    homePage,
    cartPage,
    checkoutPage,
    orderConfirmationPage,
    ordersPage,
  }) => {
    const user = structuredClone(uiData.login.validUser);
    const { productName, payment } = structuredClone(uiData.checkout);

    await loginPage.navigate();
    await loginPage.login(user);
    await loginPage.waitForLoad();

    await homePage.searchProduct(productName);
    await homePage.addProductToCart(productName);
    await homePage.goToCart();

    await cartPage.checkout();

    await checkoutPage.enterPaymentDetails(payment);
    await checkoutPage.selectCountry(payment.country);
    await checkoutPage.placeOrder();

    CheckoutValidator.expectOrderConfirmed(await orderConfirmationPage.getConfirmationMessage());

    await orderConfirmationPage.goToOrders();

    CheckoutValidator.expectOrderInHistory(await ordersPage.isProductInOrders(productName));
  });
});
```

**Why this is written as one long test rather than split into several smaller ones
(one for search, one for add-to-cart, one for checkout, etc.):** this is a genuine,
end-to-end user journey — search, add to cart, check out, confirm, verify in history —
where each step's precondition is the *previous* step's outcome; there's no
independently-meaningful "checkout succeeded" state without first having logged in,
searched, and added an item. Splitting it into isolated tests would require each one to
redundantly re-run every earlier step just to reach its own starting point, multiplying
total execution time for no added coverage — this is precisely why it's tagged
`@e2e` (Phase 2's `TestTags.E2E`) as well as `@smoke`, marking it explicitly as an
end-to-end journey test rather than a narrow unit-style UI check.

**Why the fixture destructuring lists six page objects in this test's parameter, more
than any other spec in the framework:** this is the practical payoff of the fixture
design (`testFixture.ts`, above) — a test that genuinely needs to drive six different
pages just lists all six by name and gets fully-wired, ready-to-use instances of each,
with no manual construction, no passing `page` around by hand between helper functions,
and no risk of accidentally reusing a stale page-object instance across steps.

**Why `const { productName, payment } = structuredClone(uiData.checkout)` destructures
immediately after cloning, rather than cloning `productName` and `payment` separately:**
cloning the parent `checkout` object once and then destructuring the two fields out of
the clone is simpler and marginally cheaper than calling `structuredClone` twice on two
sibling fields — and since `payment` (a `PaymentDetails` object) and `productName` (a
plain string) are always used together in this one test, treating them as one logical
unit to clone matches how they're actually consumed.

**Checkpoint:** `npx playwright test tests/authentication tests/checkout` passes
against a real app.

---

## Phase 5 — API layer (service-based)

Build inside-out: types → exceptions → retry/auth → engine → services → facade →
fixture → spec.

### Types

`src/api/types/HttpMethod.ts`:

```ts
export enum HttpMethod {
  GET = "GET",
  POST = "POST",
  PUT = "PUT",
  PATCH = "PATCH",
  DELETE = "DELETE",
}
```

`src/api/types/ApiHeaders.ts`:

```ts
export type ApiHeaders = Record<string, string>;
```

A type alias, not an interface — `ApiHeaders` genuinely is just "an arbitrary bag of
string keys to string values" with no fixed set of expected fields (unlike, say,
`LoginRequest`), so `Record<string, string>` describes it exactly, with no need for the
extensibility an interface offers.

`src/api/types/ApiQueryParams.ts`:

```ts
export type ApiQueryParams = Record<
  string,
  string | number | boolean | undefined
>;
```

Wider than `ApiHeaders` deliberately — a query param naturally wants to be a number
(`?page=2`) or boolean (`?includeDeleted=true`) at the call site without a caller
having to manually stringify it first; `ApiEngine.buildUrl` (below) is what actually
converts each value to its string form via `String(value)` when building the final URL.
`undefined` is included in the union specifically so a caller can pass an object with
some keys conditionally omitted (`{ page: hasPage ? page : undefined }`) — `buildUrl`
explicitly filters out `undefined` entries rather than serializing them as the literal
string `"undefined"`.

`src/api/types/ApiRequest.ts`:

```ts
import { ApiHeaders } from "./ApiHeaders";
import { ApiQueryParams } from "./ApiQueryParams";
import { HttpMethod } from "./HttpMethod";

export interface ApiRequest<TBody = unknown> {
  method: HttpMethod;

  endpoint: string;

  body?: TBody;

  headers?: ApiHeaders;

  queryParams?: ApiQueryParams;

  timeout?: number;

  retries?: number;

  expectedStatus?: number;
}
```

**Why this interface is generic (`ApiRequest<TBody = unknown>`) and why every field
except `method`/`endpoint` is optional:** the generic `TBody` is what lets
`AuthenticationService.login` (below) call `this.api.execute<LoginResponse, LoginRequest>({...})`
and get a fully-typed `body: LoginRequest` field checked at compile time, while a
`DELETE` request (`EventService.deleteEvent`) that has no body at all can simply omit
`body` entirely rather than being forced to pass `undefined` explicitly — `TBody = unknown`
as the default means the generic doesn't need to be specified at all for such calls.
Every optional field (`headers`, `queryParams`, `timeout`, `retries`, `expectedStatus`)
represents a genuinely optional per-request override of the engine's own defaults
(built in `ApiEngine`, below) — a service method only needs to specify the ones it
actually wants to customize, and `ApiEngine` fills in sensible behavior for the rest
(no extra headers, no retries, success/failure judged by HTTP status alone rather than
one specific expected code).

`src/api/types/ApiResponse.ts`:

```ts
import { ApiHeaders } from "./ApiHeaders";

export interface ApiResponse<TBody = unknown> {
  status: number;

  ok: boolean;

  headers: ApiHeaders;

  duration: number;

  body: TBody;
}
```

**Why this response envelope carries `ok`, `duration`, and `headers` alongside the
actual `body`, instead of every service method just returning the parsed body
directly:** this uniform envelope is what `ApiEngine.execute` (below) can build and
reason about generically for *every* call regardless of what that call's specific
response shape is — `duration` feeds directly into the "how long did this call take"
log line, `ok`/`status` feed the pass/fail validation logic, and `headers` are what get
redacted and attached to the report. Service methods (`AuthenticationService.login`,
`EventService.createEvent`, below) then unwrap this envelope and return just
`response.body` to their own callers, so specs never see the envelope at all — they
work with plain `LoginResponse`/`EventResponse` objects — while `ApiEngine` internally
gets the richer structure it needs to do logging, attachment, and validation
consistently across every single request type.

### Exceptions — one base class, one subclass per HTTP failure family

`src/api/exception/ApiException.ts`:

```ts
export class ApiException extends Error {
  public readonly status: number;
  public readonly response?: unknown;

  constructor(message: string, status: number, response?: unknown) {
    super(message);

    this.name = "ApiException";
    this.status = status;
    this.response = response;

    Object.setPrototypeOf(this, ApiException.prototype);
  }
}
```

**Why every exception class calls `Object.setPrototypeOf(this, X.prototype)` in its own
constructor — this is the single most important, easy-to-miss detail in this whole
file:** when TypeScript compiles a class `extends Error` down to a CommonJS/ES5-ish
target, a long-standing quirk in how native `Error` subclassing interacts with
transpilation means `instanceof` checks against the subclass can silently fail — an
`AuthenticationException` thrown at runtime might not actually report `true` for
`error instanceof AuthenticationException`, only for `error instanceof Error`. Since
`ApiEngine.handleException` (below) explicitly checks `error instanceof ApiException`
to decide whether to re-throw as-is or wrap it, a broken `instanceof` chain here would
silently break that entire dispatch mechanism — the manual `Object.setPrototypeOf` call
is the well-known fix, repeated in every subclass because each one needs its own
prototype explicitly restored, not just the immediate parent's.

**Why `status` and `response` are both `public readonly` on the base class:** every
catch block anywhere in a spec or elsewhere that needs to inspect a failed API call
(say, a test that deliberately expects a `ValidationException` and wants to check its
`.status`) can do so without needing to know which specific subclass it caught — the
common fields live on the shared base, `readonly` because an exception's own recorded
facts about what happened shouldn't be mutable after the fact.

`src/api/exception/AuthenticationException.ts`:

```ts
import { ApiException } from "./ApiException";

export class AuthenticationException extends ApiException {
  constructor(
    message = "Authentication failed. Please verify your credentials or access token.",
    response?: unknown,
  ) {
    super(message, 401, response);

    this.name = "AuthenticationException";

    Object.setPrototypeOf(this, AuthenticationException.prototype);
  }
}
```

`src/api/exception/ValidationException.ts`:

```ts
import { ApiException } from "./ApiException";

export class ValidationException extends ApiException {
  constructor(message = "Request validation failed.", response?: unknown) {
    super(message, 400, response);

    this.name = "ValidationException";

    Object.setPrototypeOf(this, ValidationException.prototype);
  }
}
```

`src/api/exception/ResourceNotFoundException.ts`:

```ts
import { ApiException } from "./ApiException";

export class ResourceNotFoundException extends ApiException {
  constructor(message = "Requested resource was not found.", response?: unknown) {
    super(message, 404, response);

    this.name = "ResourceNotFoundException";

    Object.setPrototypeOf(this, ResourceNotFoundException.prototype);
  }
}
```

`src/api/exception/ServerException.ts`:

```ts
import { ApiException } from "./ApiException";

export class ServerException extends ApiException {
  constructor(
    status: number,
    message = "Server returned an unexpected error.",
    response?: unknown,
  ) {
    super(message, status, response);

    this.name = "ServerException";

    Object.setPrototypeOf(this, ServerException.prototype);
  }
}
```

**Why each of these four subclasses hardcodes its own HTTP status in its constructor
(`401` for auth, `400` for validation, `404` for not-found) rather than accepting
`status` as a parameter like `ServerException` does:** `AuthenticationException`,
`ValidationException`, and `ResourceNotFoundException` each correspond to *exactly one*
specific, well-known status code by definition — there's no ambiguity to parameterize
away, and hardcoding it means `new AuthenticationException()` can never accidentally be
constructed with the wrong status. `ServerException` is the deliberate exception to
that pattern (`constructor(status: number, message = ..., response?)`) because "server
error" genuinely covers a *range* of related status codes (500, 501, 502, 503, 504 —
see `ApiEngine.createException`, below) that all warrant the same *kind* of handling
but aren't interchangeable facts — the caller needs to record which one actually
happened, so `status` has to be a real parameter there rather than a constant.
Each subclass's default `message` describes that failure category in plain language,
used whenever `ApiEngine` constructs one without a more specific message of its own.

`src/api/exception/index.ts`:

```ts
export * from "./ApiException";
export * from "./AuthenticationException";
export * from "./ValidationException";
export * from "./ResourceNotFoundException";
export * from "./ServerException";
```

Lets `ApiEngine` (below) write one combined import
(`import { ApiException, AuthenticationException, ResourceNotFoundException,
ServerException, ValidationException } from "../exception"`) instead of five separate
import statements — trivial, but it's the same barrel convention applied consistently
across every multi-file folder in this codebase.

### Retry and token holder

`src/api/client/RetryPolicy.ts`:

```ts
export interface RetryPolicyOptions {
  maxRetries?: number;
  retryDelay?: number;
}

export class RetryPolicy {
  constructor(private readonly options: RetryPolicyOptions = {}) {}

  public async execute<T>(
    operation: () => Promise<T>,
    shouldRetry?: (error: unknown) => boolean,
  ): Promise<T> {
    const retries = this.options.maxRetries ?? 0;
    const delay = this.options.retryDelay ?? 1000;

    let currentAttempt = 0;
    while (true) {
      try {
        return await operation();
      } catch (error) {
        currentAttempt++;

        const canRetry =
          currentAttempt <= retries &&
          (shouldRetry ? shouldRetry(error) : true);
        if (!canRetry) {
          throw error;
        }

        await this.sleep(delay);
      }
    }
  }
  private async sleep(milliseconds: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, milliseconds));
  }
}
```

**Why this is `while (true)` with a manual `currentAttempt` counter rather than a
`for` loop bounded by `retries`:** the loop has two independent exit conditions — a
successful `operation()` call returns immediately from inside the `try`, or the retry
budget is exhausted and `!canRetry` re-throws — neither of which maps cleanly onto a
simple bounded `for (let i = 0; i <= retries; i++)`, since the loop needs to keep
retrying indefinitely *until* one of those two things happens, not iterate a fixed
number of times unconditionally. `currentAttempt++` happens inside the `catch`, meaning
it only counts actual *failures*, not total attempts — so `maxRetries: 2` genuinely
means "the original attempt, plus up to 2 more if it keeps failing," not "3 attempts
total minus one for some off-by-one reason."

**Why `shouldRetry` is an optional second parameter that defaults to "always retry,"
rather than the policy having a fixed opinion about what's retryable:** `ApiEngine`
(below) calls `retryPolicy.execute(async () => this.send(...))` with no `shouldRetry`
predicate at all — meaning by default, if `request.retries` is set above `0`, *any*
thrown error triggers a retry, including ones that would never succeed no matter how
many times they're retried (like a malformed request causing a `400`). This is a
conscious, narrow design choice: the framework's actual retry usage today is opt-in per
request (`request.retries` defaults to `0`, meaning no retries at all unless a
specific service call explicitly asks for them) rather than a blanket "retry every
failed request" policy, so the unconditional-by-default `shouldRetry` is safe in
practice — but the parameter exists specifically so a future caller *could* pass
something like `(error) => error instanceof ServerException` to retry only on
transient server errors and fail fast on client errors, without any change needed to
`RetryPolicy` itself.

**Why `sleep` is a `private` instance method instead of a shared/exported utility
function:** this delay logic is genuinely only ever needed inside `RetryPolicy.execute`'s
own loop — there's no other consumer in the codebase for "wait N milliseconds," so
keeping it as a small private implementation detail here avoids manufacturing a shared
utility for a single call site.

`src/api/auth/TokenManager.ts`:

```ts
export class TokenManager {
  private accessToken?: string;

  /**
   * Stores the current access token for authenticated API requests.
   */
  public setToken(token: string): void {
    this.accessToken = token;
  }

  /**
   * Returns the current access token.
   * Throws if no token has been set yet.
   */
  public getToken(): string {
    if (!this.accessToken) {
      throw new Error(
        "Access token is not available. Please authenticate before calling secured APIs.",
      );
    }

    return this.accessToken;
  }

  /**
   * Indicates whether a token is available.
   */
  public hasToken(): boolean {
    return !!this.accessToken;
  }

  /**
   * Clears the current token.
   */
  public clear(): void {
    this.accessToken = undefined;
  }
}
```

**Why `getToken()` throws instead of returning `string | undefined`:** this is the same
fail-fast-at-the-source pattern as `envLoader.ts`'s `required()` and
`secretsLoader.ts`'s `Secrets.get()` — if some service tries to call an authenticated
endpoint before `auth.login(...)` has actually run, the error surfaces immediately,
with a clear message naming exactly what went wrong ("Access token is not available.
Please authenticate before calling secured APIs."), right at the point of the mistake.
The alternative — returning `undefined` and letting `ApiEngine.buildHeaders` silently
send `Authorization: Bearer undefined` — would produce a confusing 401 from the *real
backend* instead, several layers removed from the actual bug (a missing login call),
forcing whoever debugs it to work backwards from a symptom instead of seeing the cause
directly. This is also exactly why `getToken()`'s return type can be the clean
`string` rather than `string | undefined` under `strict`'s `strictNullChecks` (Phase 0)
— the throw *is* the null-handling, done once, here, rather than pushed onto every
caller.

**Why `hasToken()` exists as a separate method rather than callers just try/catching
`getToken()`:** `ApiEngine.buildHeaders` (below) needs to make a *branching decision* —
"should this request even attempt to attach an `Authorization` header" — not extract
the token unconditionally. `hasToken()` gives it a clean boolean to branch on
(`if (this.tokenManager.hasToken()) { headers.Authorization = ... }`) without needing a
try/catch purely for control flow, which would be an unusual and noisy way to express a
simple conditional.

**Why this class holds exactly one token, with no concept of multiple simultaneous
users/sessions:** each API test gets its own fresh `TokenManager` instance, created
per-test inside `createApiFixture` (below) — so "only one token at a time" is scoped
correctly to "only one token *per test*," which matches how this framework's specs
actually work: `event.spec.ts` logs in as exactly one user for the duration of that one
test. A scenario genuinely needing two simultaneous authenticated identities in one
test would need two separate `TokenManager`/`ApiEngine`/`ApiFacade` sets, which the
fixture as written doesn't currently provide — that's a real, acknowledged scope limit
of the current design, not something this class tries to solve generically.

### `src/api/client/ApiEngine.ts` — the core request executor

Every service call funnels through this one class: builds headers/URL, logs and
attaches redacted request/response data, retries, parses, validates status, and maps
failures to typed exceptions.

```ts
import {
  APIRequestContext,
  APIResponse as PlaywrightResponse,
  TestInfo,
} from "@playwright/test";

import { Logger } from "../../utils/Logger";
import { redact } from "../../utils/Redactor";
import { RequestResponseAttachment } from "../../reporting/RequestResponseAttachment";

import { TokenManager } from "../auth/TokenManager";
import { RetryPolicy } from "./RetryPolicy";

import { ApiRequest } from "../types/ApiRequest";
import { ApiResponse } from "../types/ApiResponse";
import { ApiHeaders } from "../types/ApiHeaders";
import { HttpMethod } from "../types/HttpMethod";

import {
  ApiException,
  AuthenticationException,
  ResourceNotFoundException,
  ServerException,
  ValidationException,
} from "../exception";

export class ApiEngine {
  constructor(
    private readonly requestContext: APIRequestContext,
    private readonly tokenManager: TokenManager,
    private readonly testInfo: TestInfo,
  ) {}

  public async execute<TResponse, TRequest = unknown>(
    request: ApiRequest<TRequest>,
  ): Promise<ApiResponse<TResponse>> {
    const start = Date.now();

    const headers = this.buildHeaders(request.headers);

    const url = this.buildUrl(request.endpoint, request.queryParams);

    this.logRequest(request, url, headers);

    await this.attachRequest(request, headers);

    try {
      const retryPolicy = new RetryPolicy({ maxRetries: request.retries ?? 0 });

      const response = await retryPolicy.execute(async () =>
        this.send(request, url, headers),
      );

      const duration = Date.now() - start;

      const body = await this.parseResponse<TResponse>(response);

      const apiResponse: ApiResponse<TResponse> = {
        status: response.status(),
        ok: response.ok(),
        headers: response.headers(),
        duration,
        body,
      };

      this.validateResponse(apiResponse, request);

      await this.attachResponse(apiResponse);

      this.logResponse(apiResponse, url, duration);

      return apiResponse;
    } catch (error) {
      Logger.error(
        `[API] ${request.method} ${url} failed: ${
          error instanceof Error ? error.stack : String(error)
        }`,
      );

      throw this.handleException(error);
    }
  }

  // -------------------------------------------------------
  // Request Helpers
  // -------------------------------------------------------
  private buildHeaders(customHeaders?: ApiHeaders): ApiHeaders {
    const headers: ApiHeaders = {
      Accept: "application/json",
      "Content-Type": "application/json",
      ...customHeaders,
    };
    if (this.tokenManager.hasToken()) {
      headers.Authorization = `Bearer ${this.tokenManager.getToken()}`;
    }

    return headers;
  }
  private buildUrl(
    endpoint: string,
    queryParams?: Record<string, string | number | boolean | undefined>,
  ): string {
    if (!queryParams) {
      return endpoint;
    }

    const params = new URLSearchParams();

    Object.entries(queryParams).forEach(([key, value]) => {
      if (value !== undefined) {
        params.append(key, String(value));
      }
    });

    return params.toString() ? `${endpoint}?${params.toString()}` : endpoint;
  }

  private async send<TRequest>(
    request: ApiRequest<TRequest>,
    url: string,
    headers: ApiHeaders,
  ): Promise<PlaywrightResponse> {
    const options = {
      headers,
      data: request.body,
      timeout: request.timeout,
    };
    switch (request.method) {
      case HttpMethod.GET:
        return this.requestContext.get(url, options);

      case HttpMethod.POST:
        return this.requestContext.post(url, options);

      case HttpMethod.PUT:
        return this.requestContext.put(url, options);

      case HttpMethod.PATCH:
        return this.requestContext.patch(url, options);

      case HttpMethod.DELETE:
        return this.requestContext.delete(url, options);

      default:
        throw new Error(`Unsupported HTTP method: ${request.method}`);
    }
  }

  private async parseResponse<T>(response: PlaywrightResponse): Promise<T> {
    const contentType = response.headers()["content-type"] ?? "";
    if (contentType.includes("application/json")) {
      return (await response.json()) as T;
    }
    return (await response.text()) as T;
  }
  private async attachRequest(
    request: ApiRequest,
    headers: ApiHeaders,
  ): Promise<void> {
    await RequestResponseAttachment.attachHeaders(this.testInfo, redact(headers));

    await RequestResponseAttachment.attachRequest(this.testInfo, redact(request));
  }

  private async attachResponse<T>(response: ApiResponse<T>): Promise<void> {
    await RequestResponseAttachment.attachResponse(
      this.testInfo,
      redact(response),
    );
  }

  private logRequest<TRequest>(
    request: ApiRequest<TRequest>,
    url: string,
    headers: ApiHeaders,
  ): void {
    Logger.info(`[API] Request -> ${request.method} ${url}`);
    Logger.debug(`[API] Request headers -> ${JSON.stringify(redact(headers))}`);
    if (request.body !== undefined) {
      Logger.info(
        `[API] Request body -> ${JSON.stringify(redact(request.body), null, 2)}`,
      );
    }
  }

  private logResponse<TResponse>(
    response: ApiResponse<TResponse>,
    url: string,
    duration: number,
  ): void {
    Logger.info(
      `[API] Response <- ${url} [${response.status}] ${duration} ms`,
    );
    Logger.debug(
      `[API] Response headers -> ${JSON.stringify(redact(response.headers))}`,
    );
    Logger.info(
      `[API] Response body -> ${JSON.stringify(redact(response.body), null, 2)}`,
    );
  }
  // -------------------------------------------------------
  // Validation & Exception Handling
  // -------------------------------------------------------

  private validateResponse<TResponse, TRequest>(
    response: ApiResponse<TResponse>,
    request: ApiRequest<TRequest>,
  ): void {
    const expectedStatus = request.expectedStatus;
    if (expectedStatus !== undefined) {
      if (response.status !== expectedStatus) {
        throw this.createException(
          response.status,
          `Expected HTTP ${expectedStatus} but received ${response.status}`,
        );
      }

      return;
    }
    if (response.ok) {
      return;
    }

    throw this.createException(
      response.status,
      `Request failed with HTTP ${response.status}`,
    );
  }
  private createException(status: number, message: string): ApiException {
    switch (status) {
      case 400:
        return new ValidationException(message);

      case 401:
      case 403:
        return new AuthenticationException(message);

      case 404:
        return new ResourceNotFoundException(message);

      case 500:
      case 501:
      case 502:
      case 503:
      case 504:
        return new ServerException(status, message);

      default:
        return new ApiException(message, status);
    }
  }
  private handleException(error: unknown): never {
    if (error instanceof ApiException) {
      throw error;
    }
    if (error instanceof Error) {
      throw new ApiException(error.message, 0);
    }

    throw new ApiException("Unknown API error occurred.", 0);
  }
}
```

**A full walkthrough of `execute()` and why the steps are ordered exactly this way:**

1. `const start = Date.now()` — captured before *anything* else, including header
   building, so the eventually-logged `duration` reflects the true end-to-end time of
   the whole operation (including retries), not just the network call in isolation.
2. `buildHeaders`/`buildUrl` run before the request is sent, and their results
   (`headers`, `url`) are then reused for logging, attaching, *and* sending — computed
   once, not recomputed three times with the risk of subtly different values each time.
3. `this.logRequest(...)` and `await this.attachRequest(...)` both run *before* the
   actual network call — deliberately, so that even if the request fails catastrophically
   (a network error, a timeout), there's already a durable record in both the log file
   and the test report of exactly what was about to be sent. Waiting until after a
   successful response to log the request would mean a failed request leaves no trace
   of what was even attempted.
4. The `RetryPolicy` wraps only `this.send(...)` — not the header-building, not the
   logging/attaching that already happened, and not the response parsing/validation
   that happens after. This is precise scoping: only the actual network I/O is worth
   retrying; retrying the whole `execute()` method would re-log and re-attach the
   request on every retry attempt, cluttering the report with duplicate entries for
   what's conceptually one logical request.
5. `duration` is computed right after the (possibly-retried) `send` resolves, and
   `parseResponse` happens after that — so `duration` reflects "how long until we got a
   usable response," not including the time spent parsing it.
6. `validateResponse(...)` runs *before* `attachResponse`/`logResponse` — meaning a
   response that fails validation never reaches those two calls at all; it throws
   straight into the `catch` block instead, whose single `Logger.error` line records the
   thrown exception's message/stack. This is a real, worth-noting trade-off: a
   validation failure's log entry is a compact one-line error rather than the full
   attached response body — if deeper debugging is needed for *why* validation failed
   (what the actual response body/status looked like), the *request* is still fully
   logged and attached from step 3 above, which always runs regardless of outcome; only
   the successful-path response attachment/logging is conditional on validation passing.
7. The single `try`/`catch` wrapping steps 4–6 means *any* failure anywhere in that
   range — a network error from `send`, a parse error, or a thrown validation exception
   — funnels through the exact same `catch` block: log it, then `handleException(error)`
   decides whether to re-throw as-is (already an `ApiException`) or wrap it in a new one
   (any other kind of thrown value, including a raw network error from Playwright
   itself). This is what guarantees every single call to `api.execute(...)` anywhere in
   the codebase can be caught with one `catch (error) { if (error instanceof
   ApiException) ... }` pattern, regardless of *what kind* of failure actually occurred
   underneath.

**Per-method rationale for the private helpers:**

- **`buildHeaders`** — spreads `customHeaders` *after* the two defaults
  (`Accept`/`Content-Type`), so a service that genuinely needs to override
  `Content-Type` (say, for a file upload) can do so by passing it in `request.headers`
  — object spread means later keys win. The `Authorization` header is appended last,
  after the spread, specifically so a per-request custom header can never accidentally
  clobber the token the engine itself is responsible for attaching.
- **`buildUrl`** — returns the bare `endpoint` unchanged when there are no query
  params at all (`if (!queryParams) return endpoint`), rather than always appending a
  `?` — this keeps a plain `GET /api/events` request's logged/attached URL exactly as
  clean as the endpoint constant itself, with no trailing `?` for a request that never
  had params.
- **`send`** — the `switch` on `request.method` exists because Playwright's
  `APIRequestContext` exposes `.get()`/`.post()`/`.put()`/`.patch()`/`.delete()` as five
  *separate* methods rather than one generic `.request(method, url, options)` call —
  this switch is the necessary translation layer between this framework's own
  `HttpMethod` enum and Playwright's actual API shape. The `default: throw` branch is
  unreachable in practice (every value of the `HttpMethod` enum is handled), but it's
  what satisfies `noImplicitReturns`/exhaustiveness and gives a clear error if the enum
  is ever extended with a new method that this switch forgets to handle.
- **`parseResponse`** — checks the `content-type` header rather than always calling
  `.json()`, because not every response this engine might ever encounter is guaranteed
  to be JSON (an error page, a plain-text response from a misconfigured endpoint) —
  calling `.json()` on a non-JSON body would throw a confusing parse error instead of
  gracefully falling back to `.text()` and letting `validateResponse` report the
  *actual* problem (an unexpected status code) rather than a secondary parsing failure
  masking it.
- **`attachRequest`/`attachResponse`** — every value passed to
  `RequestResponseAttachment` (Phase 6) is wrapped in `redact(...)` first, with zero
  exceptions — there's no code path in this engine where an unredacted header, request
  body, or response body ever reaches the Playwright report.
- **`logRequest`/`logResponse`** — headers are logged at `debug` level, but the request
  URL/method and the response status/duration are logged at `info` level. This is a
  deliberate verbosity split: the "what happened, and how fast" summary is always
  visible in a normal run, while the full header dump (mostly boilerplate, rarely
  useful) only shows up when `LOG_LEVEL=debug` is explicitly set (Phase 7) — keeping
  routine log output readable without losing the detailed trace when it's actually
  needed.
- **`validateResponse`** — when `request.expectedStatus` is set, it's checked as an
  *exact* match — a service explicitly expecting `404` (say, testing "delete an
  already-deleted resource returns not-found") would otherwise be treated as a failure
  by the generic `response.ok` check, since `404` isn't a 2xx status. This is what lets
  a test deliberately exercise and assert on an expected-failure status code as a
  *successful* test outcome, distinct from `EventService`'s normal calls, which omit
  `expectedStatus` entirely and rely on the default "any non-2xx is a failure" behavior.
- **`createException`** — the `switch` here is the single place that maps a raw numeric
  status code to a specific, semantically-named exception class — grouping `401`/`403`
  together under `AuthenticationException` reflects that both represent "you're not
  allowed to do this" from the caller's perspective (unauthenticated vs. unauthorized),
  a distinction most callers don't need to handle differently. Every `50x` status
  collapses into `ServerException` for the same reason — from a *test's* point of view,
  "the server broke" is one category of failure regardless of which specific 500-series
  code came back, while the exact code is still preserved on the exception instance
  (`ServerException`'s `status` field) for anyone who does need it.
- **`handleException`** — this is the funnel every thrown error passes through before
  leaving `execute()`. An already-thrown `ApiException` (including ones this engine's
  own `validateResponse` just threw) is re-thrown completely unchanged — no
  double-wrapping. Anything else — a raw `Error` (like a Playwright network-level
  failure, a DNS error, a connection refused) or even a non-`Error` thrown value — gets
  wrapped into a generic `ApiException` with `status: 0`, a status no real HTTP response
  would ever have, which is a deliberate signal to any catching code that this failure
  never actually reached the server at all.

### Services

`src/api/services/BaseService.ts`:

```ts
import { ApiEngine } from "../client/ApiEngine";

export abstract class BaseService {
  protected constructor(protected readonly api: ApiEngine) {}
}
```

**Why this class exists at all, given it has no methods:** every concrete service
(`AuthenticationService`, `EventService`, and any future one) needs the exact same
single dependency — a reference to the shared `ApiEngine` instance — stored the exact
same way. `BaseService` exists purely to guarantee that consistency: every service
gets `protected readonly api: ApiEngine` for free by extending this class, rather than
each service independently declaring its own `constructor(private readonly api: ApiEngine)`
and risking a typo or an inconsistent access modifier in one of them. The `protected`
constructor means `BaseService` itself can never be instantiated directly — only
subclassed — reinforcing that it's a pure base, never a usable service on its own.

`src/api/requests/LoginRequest.ts`:

```ts
export interface LoginRequest {
  email: string;
  password: string;
}
```

`src/api/responses/LoginResponse.ts`:

```ts
export interface LoginResponse {
  success: boolean;
  token: string;
  user: {
    id: number;
    email: string;
  };
}
```

`src/api/services/AuthenticationService.ts`:

```ts
import { BaseService } from "./BaseService";
import { ApiEngine } from "../client/ApiEngine";
import { TokenManager } from "../auth/TokenManager";

import { LoginRequest } from "../requests/LoginRequest";
import { LoginResponse } from "../responses/LoginResponse";

import { HttpMethod } from "../types/HttpMethod";

import { API_ENDPOINTS } from "../../constants/APIEndpoints";

export class AuthenticationService extends BaseService {
  constructor(
    api: ApiEngine,
    private readonly tokenManager: TokenManager,
  ) {
    super(api);
  }

  /**
   * Login user
   */
  public async login(credentials: LoginRequest): Promise<LoginResponse> {
    const response = await this.api.execute<LoginResponse, LoginRequest>({
      method: HttpMethod.POST,
      endpoint: API_ENDPOINTS.AUTH.LOGIN,
      body: credentials,
    });

    this.tokenManager.setToken(response.body.token);

    return response.body;
  }

  /**
   * Clears authentication.
   */
  public logout(): void {
    this.tokenManager.clear();
  }

  /**
   * Returns current access token.
   */
  public getAccessToken(): string {
    return this.tokenManager.getToken();
  }

  /**
   * Indicates whether the user is authenticated.
   */
  public isLoggedIn(): boolean {
    return this.tokenManager.hasToken();
  }
}
```

**Why this service takes *both* `api: ApiEngine` (via `BaseService`) *and* its own
extra `tokenManager: TokenManager` in the constructor — unlike `EventService` below,
which only needs `api`:** `AuthenticationService` is the one service in this framework
whose whole purpose includes *managing* the authentication lifecycle, not just making
authenticated calls — `login()` needs to be able to call `tokenManager.setToken(...)`
the moment a login succeeds, and `logout()`/`isLoggedIn()` need direct access to clear
or query it. `ApiEngine` itself also holds a reference to the *same* `TokenManager`
instance (passed into its own constructor by `createApiFixture`, below) purely to read
the current token when building request headers — it never writes to it. Both classes
sharing one `TokenManager` instance (not two independent ones) is what makes `login()`'s
`tokenManager.setToken(...)` call immediately visible to every *subsequent*
`ApiEngine.execute(...)` call within the same test, automatically attaching the
`Authorization` header from that point on with no extra wiring needed at each call
site.

**Why `login()` calls `tokenManager.setToken(...)` internally rather than leaving that
to the caller:** `event.spec.ts` (below) never manually manages the token itself
(beyond storing it in the scenario context, purely for its own assertions) — every
*authenticated* call after login just works, because the token was already captured as
a side effect of the login call succeeding. This is a deliberate ergonomic choice: the
alternative (a spec manually calling `tokenManager.setToken(loginResponse.token)` after
every login) would be one more easy-to-forget step repeated in every single API spec
that needs authentication.

`src/api/requests/EventRequest.ts`:

```ts
export interface CreateEventRequest {
  title: string;
  description: string;
  category: string;
  venue: string;
  city: string;
  eventDate: string;
  price: number;
  totalSeats: number;
  imageUrl: string;
}

export type UpdateEventRequest = CreateEventRequest;
```

As discussed under `ApiData.ts` (Phase 3) — `UpdateEventRequest` is a plain type alias
to `CreateEventRequest`, not a separately-defined shape, because this particular
backend's update endpoint genuinely requires the same full payload as create. Declaring
it as a named alias rather than just using `CreateEventRequest` directly in
`EventService.updateEvent`'s signature (below) still gives that method a
self-documenting parameter type — a reader sees `request: UpdateEventRequest` and
immediately understands *intent* ("this is an update payload"), even though the
underlying shape happens to be identical to create's.

`src/api/responses/EventResponse.ts`:

```ts
export interface EventData {
  id: number;
  title: string;
  description: string;
  category: string;
  venue: string;
  city: string;
  eventDate: string;
  price: number;
  totalSeats: number;
  availableSeats: number;
  imageUrl: string;
  createdAt: string;
  updatedAt: string;
}

export interface EventResponse {
  success: boolean;
  data: EventData;
  message: string;
}
```

**Why `EventData` and `EventResponse` are two separate interfaces instead of one flat
one:** `EventResponse` is the *envelope* every event-related endpoint returns
(`success`/`message`/`data`) — the same wrapper shape for create, update, and (implicitly)
delete responses — while `EventData` is specifically the shape of the event resource
itself, nested inside that envelope. Splitting them means `EventData` could be reused
independently later (say, if a future `GET /api/events/:id` endpoint were added that
returns just the event, wrapped differently) without needing to redefine the event
resource's fields from scratch. Note `EventData` includes fields
(`availableSeats`, `createdAt`, `updatedAt`) that never appear in `CreateEventRequest`
at all — this correctly models that the *request* only sends what the client controls,
while the *response* includes additional fields the server computes and returns
(remaining seat count, timestamps) — a request/response asymmetry that's completely
normal for a REST API and exactly why request and response get their own separate type
definitions rather than sharing one interface for both directions.

`src/api/services/EventService.ts`:

```ts
import { BaseService } from "./BaseService";
import { ApiEngine } from "../client/ApiEngine";
import {
  CreateEventRequest,
  UpdateEventRequest,
} from "../requests/EventRequest";
import { EventResponse } from "../responses/EventResponse";
import { HttpMethod } from "../types/HttpMethod";
import { API_ENDPOINTS } from "../../constants/APIEndpoints";

export class EventService extends BaseService {
  constructor(api: ApiEngine) {
    super(api);
  }

  public async createEvent(
    request: CreateEventRequest,
  ): Promise<EventResponse> {
    const response = await this.api.execute<EventResponse, CreateEventRequest>({
      method: HttpMethod.POST,
      endpoint: API_ENDPOINTS.EVENT.CREATE,
      body: request,
    });

    return response.body;
  }

  public async updateEvent(
    id: number,
    request: UpdateEventRequest,
  ): Promise<EventResponse> {
    const response = await this.api.execute<EventResponse, UpdateEventRequest>({
      method: HttpMethod.PUT,
      endpoint: API_ENDPOINTS.EVENT.UPDATE(id),
      body: request,
    });

    return response.body;
  }

  public async deleteEvent(id: number): Promise<EventResponse> {
    const response = await this.api.execute<EventResponse>({
      method: HttpMethod.DELETE,
      endpoint: API_ENDPOINTS.EVENT.DELETE(id),
    });

    return response.body;
  }
}
```

**Why every method here is a thin, near-identical wrapper — build one `ApiRequest`,
call `this.api.execute(...)`, return `response.body` — and why that's the whole point
rather than a missed opportunity to generalize further:** this is the "service" layer's
entire job: translate a business operation ("create an event") into the generic
request/response shape `ApiEngine` understands, and translate the generic response back
into a plain, typed value the caller actually wants. There's deliberately no logic
here beyond that translation — no retries, no header manipulation, no error handling —
because all of that already lives one layer down, in `ApiEngine`, shared identically
across every service. This is what makes adding a sixth event-related method (say,
`getEvent(id)`) a matter of writing four lines matching this exact template, not
reimplementing request-building or response-handling from scratch.

`src/api/services/index.ts` — a new service only needs registering here:

```ts
import { ApiEngine } from "../client/ApiEngine";
import { TokenManager } from "../auth/TokenManager";
import { AuthenticationService } from "./AuthenticationService";
import { EventService } from "./EventService";

export const createApiServices = (
  apiEngine: ApiEngine,
  tokenManager: TokenManager,
) => {
  return {
    auth: new AuthenticationService(apiEngine, tokenManager),
    event: new EventService(apiEngine),
  } as const;
};

export type ApiServiceMap = ReturnType<typeof createApiServices>;
```

**Why `ApiServiceMap` is *derived* from `createApiServices`'s return type
(`ReturnType<typeof createApiServices>`) instead of being hand-written as its own
interface:** this is a small but important type-safety detail — if `ApiServiceMap`
were manually declared (`interface ApiServiceMap { auth: AuthenticationService; event:
EventService }`), it would be entirely possible for the interface and the actual
factory function to silently drift out of sync (someone adds a third service to
`createApiServices` but forgets to update the separately-maintained interface, or vice
versa). Deriving the type *from* the function's actual return value means they can
never disagree — `tsc` guarantees it structurally. This is the same technique that
makes `ApiFacade.service<T extends keyof ApiServiceMap>(name: T): ApiServiceMap[T]`
(below) fully and automatically type-safe: `api.service("auth")` is inferred to return
`AuthenticationService` and `api.service("event")` to return `EventService`, purely
from this one derived type, with zero manual type annotations needed at any call site.

**Why `as const` on the returned object literal:** without it, TypeScript would widen
the object's type to something like `{ auth: AuthenticationService; event: EventService
}` anyway in this case (since the values are already concrete class instances, not
literal primitives) — `as const` here is a defensive, idiomatic habit rather than
strictly required, signaling that this object's shape is meant to be treated as fixed,
matching the same discipline seen in `API_ENDPOINTS`, Phase 2.

### Scenario context and facade

`src/api/context/ApiScenarioContext.ts`:

```ts
export class ApiScenarioContext {
  private readonly values = new Map<string, unknown>();

  /**
   * Stores a value in the scenario context for the current test.
   */
  public set(key: string, value: unknown): void {
    this.values.set(key, value);
  }

  /**
   * Retrieves a stored value from the scenario context.
   * Throws if the value is not found.
   */
  public get<T>(key: string): T {
    if (!this.values.has(key)) {
      throw new Error(`Scenario context value '${key}' was not found.`);
    }

    return this.values.get(key) as T;
  }

  /**
   * Checks if the value exists.
   */
  public has(key: string): boolean {
    return this.values.has(key);
  }

  /**
   * Removes one value.
   */
  public remove(key: string): void {
    this.values.delete(key);
  }

  /**
   * Clears the complete scenario context.
   */
  public clear(): void {
    this.values.clear();
  }
}
```

**Why this class exists at all, when a spec could just use a local `const` variable
for values like an event ID:** `event.spec.ts` (below) actually *could* just write
`const eventId = createEventResponse.data.id` and use that local variable directly —
and for a single flat test like that one, a scenario context is arguably more ceremony
than strictly necessary. Its real value shows up in more complex, multi-step flows
where intermediate state needs to be threaded across separate helper functions,
separate `test.step()` blocks,
or even across `beforeEach`/test-body boundaries — a plain local variable can't cross
those boundaries, while `api.setContextValue(...)`/`api.getContextValue(...)` can,
since the `ApiFacade` instance (and its context) is the one thing threaded through the
whole test via the `api` fixture. It also gives every stored value a debuggable name
(`"authToken"`, `"eventId"`) rather than relying on however local variables happen to
be named in a given test, which matters if this context were ever logged or inspected
for debugging.

**Why `get<T>` throws on a missing key instead of returning `undefined`:** the same
fail-fast philosophy applied here as everywhere else in this framework (`TokenManager.getToken`,
`Secrets.get`, `envLoader`'s `required`) — `api.getContextValue<number>("eventId")`
failing loudly with "Scenario context value 'eventId' was not found" the moment it's
called out of order (say, before `setContextValue("eventId", ...)` ever ran) is far
more debuggable than silently getting back `undefined` and having that propagate into
a subsequent API call as a literal `undefined` in a URL path.

**Why a plain `Map<string, unknown>` rather than a typed, per-test interface listing
every possible context key:** the whole point of this context is to be a generic,
reusable mechanism any test can put arbitrary values into under any string key it
chooses — a fixed interface would need updating for every new key any spec ever wants
to introduce, defeating the purpose of a general-purpose scratch space. The trade-off
(no compile-time guarantee that `"eventId"` is spelled consistently between the `set`
and `get` call, and the caller must supply the type via `get<T>`) is accepted in
exchange for that flexibility — `unknown` as the stored value type, rather than `any`,
still forces the caller to explicitly assert the type it expects back via the generic
parameter, rather than silently letting anything through unchecked.

`src/api/ApiFacade.ts` — what tests actually interact with:

```ts
import { ApiScenarioContext } from "./context/ApiScenarioContext";
import { ApiEngine } from "./client/ApiEngine";
import { TokenManager } from "./auth/TokenManager";
import { createApiServices, ApiServiceMap } from "./services";

export class ApiFacade {
  public readonly services: ApiServiceMap;
  public readonly context: ApiScenarioContext;

  private constructor(services: ApiServiceMap, context: ApiScenarioContext) {
    this.services = services;
    this.context = context;
  }

  public static create(apiEngine: ApiEngine, tokenManager: TokenManager): ApiFacade {
    const services = createApiServices(apiEngine, tokenManager);
    const scenarioContext = new ApiScenarioContext();

    return new ApiFacade(services, scenarioContext);
  }

  public setContextValue(key: string, value: unknown): void {
    this.context.set(key, value);
  }

  public getContextValue<T>(key: string): T {
    return this.context.get<T>(key);
  }

  public hasContextValue(key: string): boolean {
    return this.context.has(key);
  }

  public removeContextValue(key: string): void {
    this.context.remove(key);
  }

  public service<T extends keyof ApiServiceMap>(name: T): ApiServiceMap[T] {
    return this.services[name];
  }
}
```

**Why this is the Facade pattern — one object a test interacts with, hiding the
service map and scenario context behind it — rather than a spec directly importing
`AuthenticationService`/`EventService`/`ApiScenarioContext` itself:** `event.spec.ts`
(below) never imports any service class or `ApiScenarioContext` directly — it only ever
touches the single `api` fixture value, calling `api.service("auth")`,
`api.setContextValue(...)`, etc. This is deliberate encapsulation: if this framework
ever restructured how services are constructed or wired (say, injecting a shared cache
or a different retry policy per service), every spec keeps working unchanged as long as
`ApiFacade`'s own public surface (`service`, `setContextValue`/`getContextValue`/etc.)
stays the same — the internal wiring is entirely `ApiFacade`'s and
`createApiFixture`'s (below) concern, invisible to test authors.

**Why the constructor is `private` and construction only happens through the static
`create(apiEngine, tokenManager)` factory method:** this pattern (a private
constructor plus a static factory) guarantees an `ApiFacade` can never exist in a
half-built state — `create()` is the *only* way to get an instance, and it always
builds the service map and scenario context together, atomically, before the instance
is ever handed back to a caller. It also gives a natural, readable call site
(`ApiFacade.create(apiEngine, tokenManager)` inside `createApiFixture`, below) that
reads as "construct a fully-wired facade from these two dependencies," rather than a
bare `new ApiFacade(...)` that would need to already have the service map and context
computed and passed in from outside.

**Why `service<T extends keyof ApiServiceMap>(name: T): ApiServiceMap[T]` is written as
a generic method instead of simply returning `ApiServiceMap[T]` from a plain property
lookup like `api.services.auth`:** exposing `services` directly as a public field would
work too, but the method form is what gives `api.service("event")` and
`api.service("auth")` fully independent, correctly-inferred return types
(`EventService` vs. `AuthenticationService` respectively) purely from the string
literal passed in — TypeScript narrows `T` to the specific literal at each call site,
and `ApiServiceMap[T]` then resolves to that exact service's type. This is the payoff
of `ApiServiceMap` being derived (rather than hand-written, as discussed above): the
whole chain from `createApiServices`'s actual object shape through to
`api.service("...")`'s return type is inferred end-to-end with no manual type
annotations anywhere in that chain.

### Fixture

`src/api/fixtures/apiFixture.ts`:

```ts
import { APIRequestContext, request, TestInfo } from "@playwright/test";

import { ENV } from "../../../config/envLoader";

import { ApiFacade } from "../ApiFacade";
import { ApiEngine } from "../client/ApiEngine";

import { TokenManager } from "../auth/TokenManager";


export interface ApiFixture {
  api: ApiFacade;
  requestContext: APIRequestContext;
}

export async function createApiFixture(
  testInfo: TestInfo,
): Promise<ApiFixture> {
  const requestContext = await request.newContext({
    baseURL: ENV.API_BASE_URL,
    ignoreHTTPSErrors: true,
  });

  const tokenManager = new TokenManager();

  const apiEngine = new ApiEngine(requestContext, tokenManager, testInfo);

  const api = ApiFacade.create(apiEngine, tokenManager);

  return {
    api,
    requestContext,
  };
}
```

**Why this construction logic is a standalone `createApiFixture(testInfo)` function
rather than being written directly inline inside `apiTest.ts`'s `base.extend({...})`
call:** separating "how to build the fixture's dependencies" from "how to register it
as a Playwright fixture" makes each concern independently readable and, in principle,
independently testable — `createApiFixture` is pure dependency-wiring logic
(`APIRequestContext` → `TokenManager` → `ApiEngine` → `ApiFacade`) with no Playwright
fixture-lifecycle concepts (`use`, automatic disposal) mixed into it; `apiTest.ts`
(below) is purely "how does this plug into Playwright's fixture system."

**Why a *new* `request.newContext({ baseURL: ENV.API_BASE_URL })` is created per test
instead of one shared context reused across every API test:** Playwright's
`APIRequestContext` maintains its own cookie jar and any context-level state (headers
set at the context level, etc.) — a fresh context per test guarantees complete
isolation, so nothing an earlier test's requests did (a cookie set by the backend, for
instance) can leak into a later test's requests. The cost is a small amount of
per-test setup overhead, traded deliberately for correctness and test independence —
the same reasoning behind Playwright's own default of giving every UI test a fresh
`page` and browser context.

**Why a brand-new `TokenManager` is created per test, right alongside the request
context, rather than reusing one across the whole run:** this is what guarantees
`event.spec.ts` never accidentally starts already-authenticated from some *previous*
test's login — every API test starts from a completely clean slate: no token, no
service state, a fresh request context. Combined with `fullyParallel: true` (Phase 1),
this per-test isolation is also what makes it safe for many API tests to run
concurrently across workers without one test's authentication state ever interfering
with another's.

**Why `ignoreHTTPSErrors: true` is repeated here even though it's already set globally
in `playwright.config.ts`'s `use` block (Phase 1):** the global `use.ignoreHTTPSErrors`
setting applies to the browser-based `page`/`context` fixtures Playwright creates for
UI tests — it does **not** automatically apply to a manually-created
`request.newContext(...)` for API testing, since that's a separate, independently
configured request context. Setting it again here is necessary, not redundant, because
these are two genuinely different Playwright constructs that each need the option
specified on their own terms.

`src/api/fixtures/apiTest.ts` — reuses the same `registerTestHooks` from the UI layer,
since logging/context isn't UI-specific:

```ts
import { test as base, expect } from "@playwright/test";
import { createApiFixture, ApiFixture } from "./apiFixture";
import { registerTestHooks } from "../../hooks/testHook";

export const test = base.extend<ApiFixture>({
  api: async ({}, use, testInfo) => {
    const { api, requestContext } = await createApiFixture(testInfo);

    await use(api);

    await requestContext.dispose();
  },
});

registerTestHooks(test);

export { expect };
```

**Why `requestContext.dispose()` is called *after* `use(api)` returns, right there in
the fixture body, rather than relying on some automatic cleanup:** `use(api)` is the
point where control passes to the actual test body — everything *after* that `await
use(api)` line runs only once the test has finished (pass, fail, or otherwise) and
control returns to the fixture. Calling `.dispose()` there guarantees the request
context's underlying resources (open connections, cookie jar) are always released
after every single API test, success or failure, with no separate `afterEach` needed —
Playwright's fixture teardown model handles this automatically as long as the fixture
function is written in this `setup; await use(x); teardown;` shape, which is exactly
the shape used here.

**Why this fixture only defines one field (`api`), not two, even though
`createApiFixture` returns both `api` and `requestContext`:** a spec never needs the
raw `requestContext` directly — it only ever interacts through `api.service(...)`,
which internally already has everything it needs (the `ApiEngine` was built with that
context). Exposing only `api` as a fixture keeps the raw Playwright-level request
context as a genuinely internal implementation detail, invisible to test authors, the
same encapsulation principle as `ApiFacade` hiding its own services and context.

`src/api/index.ts` (barrel export for the whole layer):

```ts
export * from "./client/ApiEngine";
export * from "./client/RetryPolicy";

export * from "./auth/TokenManager";

export * from "./context/ApiScenarioContext";

export * from "./services/BaseService";
export * from "./services/AuthenticationService";
export * from "./services/EventService";

export * from "./ApiFacade";

export * from "./types/ApiHeaders";
export * from "./types/ApiQueryParams";
export * from "./types/ApiRequest";
export * from "./types/ApiResponse";
export * from "./types/HttpMethod";

export * from "./exception";
```

Gives anything outside the `src/api/` folder one import path
(`import { ApiEngine, HttpMethod, ... } from "../../src/api"`) for the whole layer's
public surface — mirroring the same barrel convention used for `src/components`,
`src/constants`, and `src/data`. In practice, most consumers (the specs) import more
specifically from `src/api/fixtures/apiTest` for `test`/`expect`, since that's the one
thing every API spec actually needs; this top-level barrel matters more for anything
that needs the lower-level building blocks (a service, an exception class) directly.

### Spec

`tests/api/event.spec.ts` — never calls `request.newContext()` or a raw HTTP verb
directly; everything goes through `api.service("<name>")`:

```ts
import { getFutureDateIso } from "../../src/utils/DateUtils";
import { test, expect } from "../../src/api/fixtures/apiTest";
import { TestData } from "../../src/data";
import { ApiData } from "../../src/data/models/ApiData";

test.describe("API :: Event CRUD", () => {
  let apiData: ApiData;

  test.beforeAll(async () => {
    apiData = await TestData.load<ApiData>("apiData");
  });

  test("should login, create, update and delete an event via API @regression", async ({
    api,
  }) => {
    const user = apiData.login.validUser;
    const createEventPayload = {
      ...apiData.event.createEvent,
      eventDate: getFutureDateIso(2, {
        hours: 9,
        minutes: 0,
        seconds: 0,
        milliseconds: 0,
      }),
    };

    const updateEventPayload = {
      ...apiData.event.updateEvent,
      eventDate: getFutureDateIso(2, {
        hours: 9,
        minutes: 0,
        seconds: 0,
        milliseconds: 0,
      }),
    };

    const loginResponse = await api.service("auth").login({
      email: user.email,
      password: user.password,
    });

    expect(loginResponse.success).toBe(true);
    expect(loginResponse.token).toBeTruthy();

    api.setContextValue("authToken", loginResponse.token);

    const createEventResponse = await api
      .service("event")
      .createEvent(createEventPayload);

    expect(createEventResponse.success).toBe(true);
    expect(createEventResponse.message).toContain("Event created successfully");
    expect(createEventResponse.data.id).toBeGreaterThan(0);
    expect(createEventResponse.data.title).toBe(createEventPayload.title);
    expect(createEventResponse.data.city).toBe(createEventPayload.city);
    expect(Number(createEventResponse.data.price)).toBe(
      createEventPayload.price,
    );

    api.setContextValue("eventId", createEventResponse.data.id);

    const eventId = api.getContextValue<number>("eventId");

    const updateEventResponse = await api
      .service("event")
      .updateEvent(eventId, updateEventPayload);

    expect(updateEventResponse.success).toBe(true);
    expect(updateEventResponse.message).toContain("Event updated successfully");
    expect(updateEventResponse.data.id).toBe(eventId);
    expect(updateEventResponse.data.title).toBe(updateEventPayload.title);
    expect(updateEventResponse.data.city).toBe(updateEventPayload.city);

    const deleteEventResponse = await api.service("event").deleteEvent(eventId);

    expect(deleteEventResponse.success).toBe(true);
    expect(deleteEventResponse.message).toContain("Event deleted successfully");
  });
});
```

**Why this spec asserts with raw `expect(...)` calls directly, unlike the UI specs
which route every assertion through a validator class:** this is a real, deliberate
asymmetry between the two layers worth calling out rather than glossing over. The UI
layer's validators exist primarily to keep expected *application copy* (exact error
message text, success message wording) out of page objects and centralized in one
place (`Messages`, Phase 2) — UI assertions are fundamentally about matching
human-facing text that's prone to wording changes. API assertions here are checking
*structural* facts about a JSON response (`success === true`, `data.id > 0`,
`data.title === createEventPayload.title`) — there's no equivalent "centralize the
expected copy" concern, since there's no copy being matched, just data shape and value
correctness. A dedicated `EventValidator` class *could* be introduced following the
same pattern if these checks grew more complex or needed reuse across multiple specs,
but for this one spec's needs, inline `expect()` calls are simpler and equally clear.

**Why `eventDate` is recomputed via `getFutureDateIso` for *both* `createEventPayload`
and `updateEventPayload`, rather than reusing one computed date for both:** each spread
(`{...apiData.event.createEvent, eventDate: getFutureDateIso(2, {...})}`) independently
overrides just the `eventDate` field, keeping every other field from the dataset
(`title`, `description`, `category`, etc.) exactly as authored — this is the spread
pattern for "take this test-data object, but override just the one runtime-computed
field," which keeps the vast majority of the payload declaratively defined in the
dataset file while only the one genuinely time-sensitive field is computed in the spec.
Both calls use identical arguments (`2` days out, pinned to `09:00:00.000`) rather than
different offsets, since there's no requirement here that create and update use
different dates — it's simply that each payload needs its *own* independently
future-valid date rather than literally sharing one JS object reference.

**Why the test flows strictly login → create → update → delete in one sequence, with
each step's output feeding the next step's input via the scenario context:** this
directly exercises the full lifecycle of a resource this test itself creates, and
critically, it also **cleans up after itself** — the `deleteEvent` call at the end means
this test doesn't leave orphaned event records behind in the backend after every run,
which matters for a test that might run repeatedly (every CI run, every local run) and
would otherwise accumulate junk data over time. `api.setContextValue("eventId", ...)`
followed immediately by `const eventId = api.getContextValue<number>("eventId")` might
look redundant with just keeping a local `const eventId = createEventResponse.data.id`
— and functionally, in this one flat test, it is — but it's written this way
deliberately to demonstrate the scenario-context pattern this framework's own
conventions (mentioned at the top of this document) prescribe for passing state between
steps, the same mechanism a more complex, helper-function-based test would need to rely
on instead of a plain local variable.

**Checkpoint:** `npx playwright test tests/api/event.spec.ts` passes against a real
backend.

---

## Phase 6 — Reporting

Can be built in parallel with Phases 4–5 — both layers depend on it.

`src/reporting/AllureHelper.ts`:

```ts
import * as allure from "allure-js-commons";

export class AllureHelper {
  static async step<T>(stepName: string, action: () => Promise<T>): Promise<T> {
    return allure.step(stepName, action);
  }

  static description(description: string): void {
    allure.description(description);
  }

  static severity(
    level: "blocker" | "critical" | "normal" | "minor" | "trivial",
  ): void {
    allure.severity(level);
  }

  static owner(owner: string): void {
    allure.owner(owner);
  }

  static tag(...tags: string[]): void {
    allure.tags(...tags);
  }
}
```

**Why this thin wrapper exists instead of page objects calling `allure-js-commons`'
functions directly:** two reasons. First, consistency with this codebase's static-class
convention (`Logger`, `Messages`, the validators) — importing `AllureHelper.step(...)`
reads the same way every other shared helper does, rather than mixing in a
third-party library's own free-function API style directly into page objects. Second,
and more practically, it's an insulation layer: if this framework ever swapped Allure
for a different reporting library, only this one file's internals would need to change
— every `AllureHelper.step(...)` call across every page object (Phase 4) keeps working
unchanged, since they depend on this wrapper's interface, not on `allure-js-commons`
directly.

**Why `step<T>` is generic and returns `Promise<T>` rather than `Promise<void>`:**
`allure.step(stepName, action)` itself returns whatever `action` returns — making this
wrapper generic and pass-through-typed means `AllureHelper.step(...)` can wrap steps
that need to return a value (not just fire-and-forget UI actions) without losing that
value's type. None of the current page objects in this framework happen to need a
step's return value, but the generic makes the wrapper correct and useful for that case
without adding any extra code — `T` is simply inferred from whatever `action` actually
returns.

`src/reporting/AttachmentHelper.ts`:

```ts
import { TestInfo } from "@playwright/test";

export class AttachmentHelper {
  static async attachJson(
    testInfo: TestInfo,
    name: string,
    data: unknown,
  ): Promise<void> {
    await testInfo.attach(name, {
      body: JSON.stringify(data, null, 2),
      contentType: "application/json",
    });
  }

  static async attachText(
    testInfo: TestInfo,
    name: string,
    text: string,
  ): Promise<void> {
    await testInfo.attach(name, {
      body: text,
      contentType: "text/plain",
    });
  }
}
```

**Why this general-purpose helper exists alongside the more specific
`RequestResponseAttachment` (below), rather than the API layer using this one
directly:** `AttachmentHelper` is a generic "attach arbitrary named JSON or text to the
report" utility, usable from anywhere (a UI test wanting to attach a debug payload, a
future non-API feature needing to attach something). `RequestResponseAttachment` is
purpose-built *specifically* for the API request/response/headers attachment pattern —
giving each of its three attachments a fixed, consistent name (`"API Request"`, `"API
Response"`, `"HTTP Headers"`) every single time, which is what makes every API test's
report look uniform regardless of which service or endpoint was called. Using
`AttachmentHelper.attachJson(testInfo, "API Request", redact(request))` directly from
`ApiEngine` instead would work identically in practice, but would mean the "this is
what an API request attachment is called" naming convention lives implicitly at each
call site rather than being guaranteed by one dedicated class — `RequestResponseAttachment`
is that guarantee, expressed as its own narrow-purpose file.

`src/reporting/RequestResponseAttachment.ts` — always given already-`redact()`-ed data
by `ApiEngine` (Phase 5):

```ts
import { TestInfo } from "@playwright/test";

export class RequestResponseAttachment {
  static async attachRequest(testInfo: TestInfo, body: unknown): Promise<void> {
    await testInfo.attach("API Request", {
      body: JSON.stringify(body, null, 2),
      contentType: "application/json",
    });
  }

  static async attachResponse(
    testInfo: TestInfo,
    body: unknown,
  ): Promise<void> {
    await testInfo.attach("API Response", {
      body: JSON.stringify(body, null, 2),
      contentType: "application/json",
    });
  }

  static async attachHeaders(
    testInfo: TestInfo,
    headers: unknown,
  ): Promise<void> {
    await testInfo.attach("HTTP Headers", {
      body: JSON.stringify(headers, null, 2),
      contentType: "application/json",
    });
  }
}
```

**Why this class takes an already-redacted `body`/`headers` parameter rather than
calling `redact()` itself internally:** this is a deliberate separation of
*responsibility* — `RequestResponseAttachment`'s only job is "attach this JSON to the
report under this fixed name," full stop; it has no opinion about whether the data
it's given is safe to attach. `ApiEngine` (Phase 5) is the one place that decides *what*
gets attached and is the one that calls `redact(...)` before ever handing data to this
class — meaning there's exactly one place in the whole codebase responsible for
ensuring nothing sensitive reaches a report, rather than that responsibility being
duplicated (and therefore able to be inconsistently applied) across every consumer of
this attachment helper. If a second caller of `RequestResponseAttachment` were ever
added, it would inherit this same contract: redact before calling, not the other way
around.

`src/reporting/index.ts`:

```ts
export * from "./AllureHelper";
export * from "./AttachmentHelper";
export * from "./RequestResponseAttachment";
```

Reporters are registered in `playwright.config.ts` (Phase 1):
`reporter: [["list"], ["html", { open: "never" }], ["allure-playwright"]]`.

---

## Phase 7 — npm scripts recap

Already defined in full in [Phase 0](#phase-0--project-scaffold-and-tooling); the run
surface once both layers have at least one passing spec:

| Command | What it does |
|---|---|
| `npm test` | full suite (`playwright test`) |
| `npm run ui` | `tests/authentication` + `tests/checkout` only |
| `npm run api` | `tests/api` only |
| `npm run smoke` | everything tagged `@smoke` |
| `npm run regression` | everything tagged `@regression` |
| `npm run typecheck` | `tsc --noEmit` |
| `npm run report` | opens the last Playwright HTML report |
| `npm run allure-report` | generates + opens the Allure report |

Target an environment / data format with env vars:

```bash
TEST_ENV=dev npm run ui
LOG_LEVEL=debug npm run api
```

**Why `ui`/`api`/`smoke`/`regression` are four separate, overlapping ways to slice the
same underlying test suite rather than one script with a flag:** each answers a
genuinely different question a developer or CI pipeline might ask. `ui`/`api` slice by
*layer* (which part of the codebase am I testing), using `testDir` path arguments —
useful when working on one layer in isolation. `smoke`/`regression` slice by
*criticality*, using `--grep` against the tag conventions from `TestTags` (Phase 2) —
useful for a fast pre-merge check (`smoke`) versus a fuller nightly run (`regression`),
completely independent of which layer a given tagged test happens to belong to. A test
can be reached by more than one of these scripts simultaneously (e.g.
`login.spec.ts`'s `@smoke`-tagged test is included by both `npm run ui` and `npm run
smoke`) — that overlap is intentional, not a design flaw, since the two slicing
dimensions (layer vs. criticality) are orthogonal.

---

## Phase 8 — CI

`.github/workflows/playwright-tests.yml`, triggered on push to `main` and on every PR:

```yaml
name: Playwright Tests

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - "**"

jobs:
  playwright-tests:
    runs-on: ubuntu-latest

    env:
      TEST_ENV: qa
      HEADLESS: true
      # Test-data credential placeholders ({{uiValidUserEmail}}, etc. — see
      # src/data/utils/resolveSecrets.ts) resolve against these repository
      # secrets in CI instead of config/secrets/qa.env, which is gitignored
      # and only exists locally. Add matching secrets under Settings ->
      # Secrets and variables -> Actions.
      uiValidUserEmail: ${{ secrets.UI_VALID_USER_EMAIL }}
      uiValidUserPassword: ${{ secrets.UI_VALID_USER_PASSWORD }}
      apiValidUserEmail: ${{ secrets.API_VALID_USER_EMAIL }}
      apiValidUserPassword: ${{ secrets.API_VALID_USER_PASSWORD }}

    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: 20
          cache: npm

      - name: Install dependencies
        run: npm ci

      - name: Type-check
        run: npm run typecheck

      - name: Install Playwright browser
        run: npx playwright install --with-deps chromium

      - name: Run Playwright tests
        run: npm run test

      - name: Upload Playwright HTML report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: playwright-html-report
          path: playwright-report/
          if-no-files-found: warn

      - name: Upload Playwright test results
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: playwright-test-results
          path: test-results/
          if-no-files-found: warn
```

The four `env:` entries are what `Secrets.get()` (Phase 3a) finds via `process.env` in
CI — add matching repository secrets under **Settings → Secrets and variables →
Actions** in GitHub before the first run; no `config/secrets/*.env` file is needed
there at all.

**Why the trigger is `push` to `main` *and* `pull_request` against every branch
(`"**"`), not just one or the other:** the `pull_request` trigger is what actually
gates code review — every PR, regardless of target or source branch, gets a full run
before it can be merged, catching a regression before it ever reaches `main`. The
`push` trigger on `main` specifically is a second, independent safety net — it catches
the case where something merges into `main` in a way that wasn't fully validated by its
own PR run (a fast-forward merge, a direct push by someone with permission, a merge
commit whose combination of changes wasn't individually tested) — so `main` itself is
continuously re-verified, not just each PR in isolation.

**Why `TEST_ENV: qa` and `HEADLESS: true` are hardcoded in the workflow's `env:` block
rather than left to whatever default `envLoader.ts` would otherwise pick:** `qa` is
this framework's designated stable, CI-appropriate environment — pinning it explicitly
in the workflow means CI's behavior is never accidentally affected by an `envLoader.ts`
default changing later (recall `envLoader.ts`'s own fallback is also `"qa"`, so this is
belt-and-suspenders, but making it explicit here means anyone reading just the workflow
file, without also reading `envLoader.ts`, immediately knows which environment CI
targets). `HEADLESS: true` overrides `qa.env`'s own `HEADLESS=false` — CI runners have
no display server for a headed browser to render into, so this is a hard requirement,
not a preference, and setting it at the workflow-env level (rather than editing
`qa.env` itself) keeps the *local* default experience headed while making CI's
override explicit and visible right in the workflow file.

**Why type-checking runs as its own separate step, before installing the Playwright
browser and before running tests, rather than folding it into `npm run test`:** this
is a fast-fail ordering decision — `npm run typecheck` takes seconds and requires no
browser download at all; if there's a type error anywhere in the codebase, failing at
this step means CI never wastes time downloading and installing a Chromium binary
(`npx playwright install --with-deps chromium`, a genuinely slow step) only to fail
later for a reason that had nothing to do with the browser or the actual test run. Each
step failing independently and early is what keeps CI feedback fast for the most common
kinds of mistakes (a type error caught by `tsc`) versus the slower, real end-to-end
test failures.

**Why both report artifacts are uploaded with `if: always()` rather than only on
success:** the HTML report and `test-results/` (screenshots/traces/videos captured
`only-on-failure`/`retain-on-failure`, Phase 1) are almost always *more* valuable when
the run failed, not less — `if: always()` on both upload steps guarantees a failing CI
run still leaves behind exactly the artifacts needed to diagnose it (the trace viewer,
failure screenshots, the interactive HTML report), rather than a failed run silently
discarding the very evidence needed to debug it. `if-no-files-found: warn` (rather than
the default `error`) means a *passing* run — which by design produces no failure
screenshots/traces — doesn't itself fail the upload step just because `test-results/`
happens to be empty or near-empty that time.

**Checkpoint:** open a PR — the workflow runs typecheck, then the full suite, and
uploads both report artifacts regardless of pass/fail.

---

## Final directory tree

```
.
├── .github/workflows/playwright-tests.yml
├── .gitignore
├── eslint.config.mjs
├── package.json
├── tsconfig.json
├── playwright.config.ts
├── config/
│   ├── envLoader.ts
│   ├── secretsLoader.ts
│   ├── global.setup.ts
│   ├── global.teardown.ts
│   ├── environments/{qa,dev}.env
│   └── secrets/{qa,dev}.env.example      (real .env files gitignored)
├── src/
│   ├── models/{User,PaymentDetails,index}.ts
│   ├── constants/{AppRoutes,APIEndpoints,Messages,TestTags,index}.ts
│   ├── utils/{Logger,Redactor,DateUtils,CommonActions,index}.ts
│   ├── reporting/{AllureHelper,AttachmentHelper,RequestResponseAttachment,index}.ts
│   ├── data/
│   │   ├── TestData.ts, index.ts
│   │   ├── factory/DataProviderFactory.ts
│   │   ├── providers/{IDataProvider,BaseDataProvider,Json,Yaml,Csv,Excel}Provider.ts, index.ts
│   │   ├── utils/{tabularData,resolveSecrets}.ts
│   │   ├── models/{UiData,ApiData,index}.ts
│   │   └── datasets/{json,yaml,csv,excel}/{uiData,apiData}.*
│   ├── locators/*.ts (one per page) + index.ts
│   ├── components/{base/BaseComponent,Button,form/TextBox,form/CheckBox,display/Label}.ts, index.ts
│   ├── pages/{BasePage,LoginPage,HomePage,CartPage,CheckoutPage,OrderConfirmationPage,OrdersPage}.ts
│   ├── validators/{LoginValidator,CheckoutValidator}.ts
│   ├── hooks/{beforeEachHook,afterEachHook,testHook}.ts
│   ├── fixtures/testFixture.ts
│   └── api/
│       ├── ApiFacade.ts, index.ts
│       ├── auth/TokenManager.ts
│       ├── client/{ApiEngine,RetryPolicy}.ts
│       ├── context/ApiScenarioContext.ts
│       ├── exception/{ApiException,Authentication,Validation,ResourceNotFound,Server}Exception.ts, index.ts
│       ├── fixtures/{apiFixture,apiTest}.ts
│       ├── requests/{LoginRequest,EventRequest}.ts
│       ├── responses/{LoginResponse,EventResponse}.ts
│       ├── services/{BaseService,AuthenticationService,EventService,index}.ts
│       └── types/{ApiHeaders,ApiQueryParams,ApiRequest,ApiResponse,HttpMethod}.ts
└── tests/
    ├── authentication/login.spec.ts
    ├── checkout/checkout.spec.ts
    └── api/event.spec.ts
```

---

## Build order at a glance

```
Phase 0  Scaffold & tooling (npm, tsconfig, ESLint, .gitignore, Playwright browsers)
Phase 1  ENV config (envLoader, environments/*.env, playwright.config.ts, global setup/teardown)
Phase 2  Shared utils & constants (Logger, Redactor, DateUtils, CommonActions, AppRoutes, APIEndpoints, Messages, TestTags)
Phase 3  Test data (models, providers, factory, TestData, datasets in all 4 formats)
Phase 3a   ↳ Secrets (secretsLoader, resolveSecrets, config/secrets/*.env.example)
Phase 4  UI layer (locators → components → BasePage/pages → validators → hooks → fixture → specs)
Phase 5  API layer (types → exceptions → retry/token → engine → services → context/facade → fixture → spec)
Phase 6  Reporting (AllureHelper, AttachmentHelper, RequestResponseAttachment)
Phase 7  npm scripts
Phase 8  CI workflow
```

Each phase's checkpoint should be green before starting the next — that's what keeps
the framework buildable incrementally instead of as one large, unverifiable change.

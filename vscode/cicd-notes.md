# CI/CD Complete Keyword Glossary — GitHub Actions & GitLab CI

Every keyword explained, organized by category, followed by fully-commented working examples for Maven and Node.js.

---

# PART A — GitHub Actions Keywords

File location: `.github/workflows/<any-name>.yml`

## A1. Top-Level Keywords

| Keyword | Meaning |
|---|---|
| `name` | The display name of the workflow, shown in the GitHub Actions tab. Purely cosmetic — doesn't affect execution. |
| `on` | Defines **what event triggers this workflow** (a push, a pull request, a schedule, a manual click, etc.). This is mandatory — without it, the workflow never runs. |
| `jobs` | A collection of one or more **jobs**. Each job runs in its own fresh virtual machine (runner) by default. |
| `env` | Environment variables available to **all jobs** in the workflow (when placed at the top level). |
| `permissions` | Controls what the auto-generated `GITHUB_TOKEN` is allowed to do (e.g., read/write to repo contents, issues, pull requests). |
| `concurrency` | Prevents multiple runs of the same workflow from executing at once (e.g., cancel old runs when new commits come in). |
| `defaults` | Sets default settings (like working directory or shell) applied to all `run` steps unless overridden. |

## A2. `on` (Triggers)

| Keyword | Meaning |
|---|---|
| `push` | Triggers the workflow when commits are pushed to the repo. |
| `branches` | Restricts the trigger to specific branch names (e.g., only `main`). |
| `pull_request` | Triggers the workflow when a pull request is opened, updated, or reopened. |
| `workflow_dispatch` | Adds a manual **"Run workflow"** button in the GitHub UI — lets a human trigger it on demand. |
| `schedule` | Runs the workflow automatically at fixed times. |
| `cron` | The actual schedule expression (standard 5-field cron syntax: minute, hour, day-of-month, month, day-of-week), used inside `schedule`. |
| `workflow_call` | Marks this workflow as **reusable** — another workflow can call it like a function. |

## A3. `jobs` and Job-Level Keywords

| Keyword | Meaning |
|---|---|
| `<job_id>` | A unique identifier you choose for the job (e.g., `test`, `build`). Used to reference it elsewhere (like in `needs`). |
| `runs-on` | Specifies the **operating system/environment** the job runs on (e.g., `ubuntu-latest`, `windows-latest`, `macos-latest`). |
| `needs` | Makes this job **wait** for another job to finish successfully before starting (creates job ordering/dependencies). |
| `if` | A **conditional expression** — the job (or step) only runs if this evaluates to true. |
| `strategy` | Defines a **matrix** — run the same job multiple times with different variable combinations (e.g., multiple Node versions). |
| `matrix` | The actual list of variable values used inside `strategy` (e.g., `node-version: [18.x, 20.x]` runs the job twice). |
| `timeout-minutes` | Maximum time a job is allowed to run before GitHub force-kills it. |
| `continue-on-error` | If `true`, the workflow keeps going even if this job fails (marks it as a warning instead of a hard failure). |
| `container` | Runs the job's steps inside a specific Docker container image instead of directly on the runner OS. |
| `services` | Starts additional background containers alongside the job (e.g., a database needed for integration tests). |
| `outputs` | Values a job produces that other jobs (via `needs`) can read afterward. |

## A4. `steps` and Step-Level Keywords

| Keyword | Meaning |
|---|---|
| `steps` | An ordered list of **individual tasks** within a job. Each runs top-to-bottom; if one fails, later steps stop (unless `continue-on-error` is set). |
| `name` (inside a step) | A human-readable label for that specific step, shown in logs. Optional but recommended. |
| `uses` | Runs a **pre-built reusable Action** from the GitHub Marketplace or another repo (e.g., `actions/checkout@v4`). This is how you avoid writing boilerplate yourself. |
| `with` | Supplies **input parameters** to the action referenced in `uses` (like arguments to a function). |
| `run` | Executes a **raw shell command** directly (as opposed to a pre-built action). |
| `env` (inside a step) | Environment variables scoped only to this one step. |
| `if` (inside a step) | Conditional — this specific step only runs if the expression is true (e.g., `if: always()` to run even after a failure, commonly used for uploading test reports). |
| `working-directory` | Changes which folder the `run` command executes in. |
| `shell` | Specifies which shell to use to interpret the `run` command (e.g., `bash`, `pwsh`). |

## A5. Common Actions (used with `uses`)

| Action | Meaning |
|---|---|
| `actions/checkout@v4` | Downloads/clones your repository's code onto the runner so subsequent steps can access it. Almost every workflow needs this as its first step. |
| `actions/setup-java@v4` | Installs a specific Java (JDK) version onto the runner. |
| `actions/setup-node@v4` | Installs a specific Node.js version onto the runner. |
| `actions/cache@v4` | Manually caches specific folders (e.g., dependencies) between runs to speed up future builds. |
| `actions/upload-artifact@v4` | Saves files (like test reports or build output) from the runner so they're downloadable from the GitHub UI after the run finishes. |
| `actions/download-artifact@v4` | Retrieves artifacts uploaded by an earlier job (used when passing files between jobs). |
| `dorny/test-reporter@v1` | A third-party action that reads JUnit XML test results and displays them as a readable report in the GitHub UI. |

## A6. Context Variables & Expressions

| Syntax | Meaning |
|---|---|
| `${{ }}` | The **expression syntax** — anything inside these double braces gets evaluated by GitHub Actions before running (used to insert variables, secrets, matrix values, etc.). |
| `secrets.<NAME>` | Refers to an encrypted secret stored in repo Settings (e.g., API keys, passwords). Never appears in plain text in logs. |
| `matrix.<name>` | Refers to the current value from the `matrix` list, used to parameterize a job (e.g., `${{ matrix.node-version }}`). |
| `github.ref` | The branch or tag that triggered the workflow. |
| `always()` | A special function used in `if:` conditions meaning "run this step regardless of whether previous steps failed." |

---

# PART B — GitLab CI Keywords

File location: `.gitlab-ci.yml` at the repo root.

## B1. Top-Level (Pipeline-Wide) Keywords

| Keyword | Meaning |
|---|---|
| `image` | The **default Docker image** used to run jobs (e.g., `node:20`, `maven:3.9-eclipse-temurin-17`). This is the "operating system + tools" your commands run inside. |
| `stages` | Defines the **ordered list of stages** in the pipeline (e.g., `build`, `test`, `deploy`). Jobs assigned to the same stage run in parallel; stages run one after another. |
| `variables` | Pipeline-wide environment variables available to every job. |
| `cache` | Defines files/folders to **save and reuse** between pipeline runs (e.g., `node_modules`) to speed things up. |
| `workflow` | Controls whether the **entire pipeline** should run at all, based on conditions (separate from individual job rules). |
| `include` | Pulls in YAML config from another file or project — used to share/reuse pipeline logic across repos. |
| `default` | Sets default keyword values (like `image`, `before_script`, `retry`) applied to every job unless a job overrides them. |

## B2. Job-Level Keywords

| Keyword | Meaning |
|---|---|
| `<job_name>` | A name you choose for the job (e.g., `test`, `build`). Appears in the pipeline UI. |
| `stage` | Assigns this job to one of the stages defined in the top-level `stages` list. Determines execution order relative to other jobs. |
| `script` | The **required** list of shell commands that actually run for this job — this is the core of every job. |
| `before_script` | Commands that run **before** `script`, often for setup (e.g., installing extra tools). Can be set globally or per-job. |
| `after_script` | Commands that run **after** `script` finishes, even if it failed (e.g., cleanup). |
| `rules` | Conditional logic that decides **whether this job runs**, based on branch, pipeline source, variables, etc. (Modern replacement for `only`/`except`.) |
| `only` / `except` | Older way (pre-`rules`) to restrict which branches/events trigger a job. Still works, but `rules` is now preferred. |
| `when` | Controls **when** a job runs relative to the pipeline: `on_success` (default), `on_failure`, `always`, `manual` (needs a person to click "Run"), `delayed`. |
| `allow_failure` | If `true`, the pipeline continues even if this job fails — it shows as a warning instead of blocking later stages. |
| `needs` | Lets a job start as soon as its specific dependency finishes, **without waiting for the whole previous stage** — speeds up pipelines by running jobs out of strict stage order. |
| `dependencies` | Specifies which earlier jobs' **artifacts** this job should download and use. |
| `extends` | Lets a job **inherit configuration** from a reusable template job (reduces repeated YAML). |
| `tags` | Routes the job to a specific GitLab Runner that has matching tags (useful when you have multiple runners with different capabilities). |
| `timeout` | Maximum time this specific job can run before being killed. |
| `retry` | Automatically re-runs the job a set number of times if it fails. |
| `parallel` | Runs multiple instances of the same job simultaneously (e.g., splitting a test suite into chunks). |
| `environment` | Marks this job as deploying to a named environment (e.g., `production`) — enables environment tracking/rollback features in GitLab. |

## B3. `cache` Sub-Keywords

| Keyword | Meaning |
|---|---|
| `key` | A string identifying the cache — jobs sharing the same key share the same cache. Often based on a lockfile hash so the cache invalidates when dependencies change. |
| `paths` | The actual folders/files to cache (e.g., `node_modules/`, `.m2/repository/`). |
| `policy` | Controls whether a job only reads (`pull`), only writes (`push`), or both (`pull-push`, the default) the cache. |

## B4. `artifacts` Sub-Keywords

| Keyword | Meaning |
|---|---|
| `paths` | Files/folders to save after the job finishes, downloadable from the GitLab UI and passable to later jobs. |
| `reports` | A special artifact type GitLab parses natively to show results in the UI (e.g., in merge requests). |
| `junit` | Under `reports` — points to JUnit XML file(s); GitLab renders these as pass/fail test results directly in the merge request. |
| `when` (inside artifacts) | Controls whether artifacts are kept `on_success` (default), `on_failure`, or `always`. Set to `always` so you get test reports even when tests fail. |

## B5. `rules` Sub-Keywords

| Keyword | Meaning |
|---|---|
| `if` | A condition (using GitLab CI/CD predefined variables) that must be true for the job to run. |
| `changes` | Only runs the job if specific files were modified in the commit/MR (useful to skip tests when unrelated files change). |
| `when` (inside rules) | Same meaning as job-level `when`, but scoped to this specific rule condition. |

## B6. Predefined Variables (built into GitLab, used inside `if:`)

| Variable | Meaning |
|---|---|
| `$CI_PIPELINE_SOURCE` | What triggered the pipeline (e.g., `push`, `merge_request_event`, `schedule`, `web` for manual trigger). |
| `$CI_COMMIT_BRANCH` | The name of the branch the current commit is on. |
| `$CI_MERGE_REQUEST_ID` | The ID of the merge request, if the pipeline was triggered by one. |
| `$CI_JOB_NAME` | The name of the currently running job. |

---

# PART C — Fully Commented Maven Example

### GitHub Actions version

```yaml
name: Run Maven Tests            # Label shown in the GitHub Actions UI for this workflow

on:                               # Defines what triggers this workflow to run
  push:                           # Trigger when code is pushed
    branches: [ main, develop ]   # ...but only if the push is to 'main' or 'develop'
  pull_request:                   # Also trigger when a pull request is opened/updated
    branches: [ main ]            # ...but only if the PR targets 'main'

jobs:                             # Start of the jobs section
  test:                           # Job ID, chosen by us; referenced elsewhere as 'test'
    runs-on: ubuntu-latest        # Run this job on a fresh Ubuntu Linux virtual machine

    steps:                        # Ordered list of tasks this job performs
      - name: Checkout code       # Human-readable label for this step
        uses: actions/checkout@v4 # Pre-built action: downloads your repo code onto the runner

      - name: Set up JDK 17                 # Label for this step
        uses: actions/setup-java@v4         # Pre-built action: installs Java
        with:                              # Parameters passed into the action above
          java-version: '17'               # Tells the action to install Java 17
          distribution: 'temurin'          # Which Java build/vendor to use (Temurin is a common free one)
          cache: 'maven'                   # Auto-caches Maven's local repo (~/.m2) between runs, speeds up builds

      - name: Run tests            # Label for this step
        run: mvn clean test        # Raw shell command: 'clean' wipes old build files, 'test' runs the test suite

      - name: Publish test report        # Label for this step
        if: always()                     # Run this step even if the 'Run tests' step above failed
        uses: dorny/test-reporter@v1      # Third-party action that renders JUnit XML nicely in the UI
        with:                            # Parameters for the action
          name: Maven Tests                          # Display name for the report
          path: target/surefire-reports/*.xml         # Location of Maven's auto-generated JUnit XML results
          reporter: java-junit                        # Tells the action what XML format to expect
```

### GitLab CI version

```yaml
image: maven:3.9-eclipse-temurin-17     # Every job runs inside this Docker image (Maven + Java 17 preinstalled)

stages:                                 # Defines the pipeline's stages, in execution order
  - test                                # We only have one stage here, named 'test'

variables:                              # Environment variables available to all jobs
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"   # Tells Maven to store downloaded dependencies inside the project folder (so it can be cached)

cache:                                  # Defines what to cache between pipeline runs
  paths:                                # The folders to save/restore
    - .m2/repository/                   # Maven's dependency cache — avoids re-downloading libraries every run

test:                                   # Job name, chosen by us
  stage: test                           # Assigns this job to the 'test' stage defined above
  script:                               # The actual commands this job runs
    - mvn clean test                    # 'clean' wipes old build files, 'test' runs the test suite
  artifacts:                            # Files to keep after the job finishes
    when: always                        # Keep artifacts even if the job/tests failed
    reports:                            # Special artifact type GitLab parses natively
      junit:                            # Tells GitLab these are JUnit test result files
        - target/surefire-reports/TEST-*.xml   # Location of Maven's auto-generated test XML results
    paths:                              # Also keep these as plain downloadable files
      - target/surefire-reports/        # The whole reports folder
  rules:                                # Conditions controlling when this job runs
    - if: '$CI_PIPELINE_SOURCE == "merge_request_event"'  # Run if triggered by a merge request
    - if: '$CI_COMMIT_BRANCH == "main"'                    # ...or if pushed directly to 'main'
    - if: '$CI_COMMIT_BRANCH == "develop"'                 # ...or if pushed directly to 'develop'
```

---

# PART D — Fully Commented Node.js Example

### GitHub Actions version

```yaml
name: Run Node Tests             # Label shown in the GitHub Actions UI

on:                               # What triggers this workflow
  push:                           # On a push event
    branches: [ main, develop ]   # ...only for these branches
  pull_request:                   # On a pull request event
    branches: [ main ]            # ...only if targeting 'main'

jobs:                             # Start of jobs section
  test:                           # Job ID
    runs-on: ubuntu-latest        # Run on a fresh Ubuntu VM

    strategy:                     # Defines a build matrix (run this job multiple times with different inputs)
      matrix:                     # The actual variable(s) to vary
        node-version: [18.x, 20.x]   # Run the whole job once with Node 18, once with Node 20

    steps:                        # Ordered list of tasks
      - name: Checkout code       # Label
        uses: actions/checkout@v4 # Downloads repo code onto the runner

      - name: Set up Node.js              # Label
        uses: actions/setup-node@v4       # Pre-built action: installs Node.js
        with:                            # Parameters for the action
          node-version: ${{ matrix.node-version }}  # Uses the current matrix value (18.x or 20.x depending on the run)
          cache: 'npm'                              # Auto-caches npm dependencies between runs

      - name: Install dependencies   # Label
        run: npm ci                 # 'ci' = clean install from package-lock.json; faster & more reliable than 'npm install' for pipelines

      - name: Run tests              # Label
        run: npm test                # Runs whatever script is defined as "test" in package.json
```

### GitLab CI version

```yaml
image: node:20                    # Every job runs inside this Docker image (Node.js 20 preinstalled)

stages:                           # Ordered list of pipeline stages
  - test                          # Just one stage: 'test'

cache:                            # Cache configuration to speed up repeated runs
  key:                            # How GitLab decides if the cache is still valid
    files:                        # Base the cache key on the contents of this file
      - package-lock.json         # If this file's hash changes, the cache is invalidated (dependencies changed)
  paths:                          # Folders to actually cache
    - node_modules/               # Installed npm packages — avoids reinstalling every run

test:                             # Job name
  stage: test                     # Assigns this job to the 'test' stage
  script:                         # Commands this job runs, in order
    - npm ci                      # Clean install of dependencies from package-lock.json
    - npm test                    # Runs the "test" script defined in package.json
  artifacts:                      # Files to keep after this job runs
    when: always                  # Keep them even if tests fail
    reports:                      # Special GitLab-parsed artifact type
      junit: junit.xml            # Path to JUnit XML output (requires a JUnit reporter configured in your test tool, e.g. jest-junit)
    paths:                        # Also save as plain downloadable files
      - coverage/                 # Code coverage report folder, if generated
  rules:                          # Conditions controlling when this job runs
    - if: '$CI_PIPELINE_SOURCE == "merge_request_event"'  # Run on merge requests
    - if: '$CI_COMMIT_BRANCH == "main"'                    # ...or pushes to 'main'
    - if: '$CI_COMMIT_BRANCH == "develop"'                 # ...or pushes to 'develop'
```

---

# PART E — Side-by-Side Cheat Sheet (same concept, different word)

| What it does | GitHub Actions | GitLab CI |
|---|---|---|
| Config file | `.github/workflows/*.yml` | `.gitlab-ci.yml` |
| What environment to run in | `runs-on: ubuntu-latest` | `image: node:20` |
| List of commands | `steps` + `run:` | `script:` |
| Pre-built reusable step | `uses:` | `extends:` (less common, different mechanism) |
| Passing inputs to a reusable step | `with:` | N/A (not a direct equivalent) |
| Run only on certain branches | `on: push: branches:` | `rules: - if: '$CI_COMMIT_BRANCH == ...'` |
| Save files after job runs | `actions/upload-artifact` | `artifacts:` |
| Cache dependencies | `actions/cache` or `cache:` in setup-* actions | `cache:` |
| Show test results in UI | `dorny/test-reporter` (third-party) | `artifacts: reports: junit:` (native) |
| Run step/job even after failure | `if: always()` | `when: always` |
| Environment variables | `env:` | `variables:` |
| Manual trigger button | `workflow_dispatch:` | `when: manual` (per job) |
| Job depends on another job | `needs:` | `needs:` (same keyword, same idea) |

---

# PART F — How to Read Any New Keyword You Encounter

1. Check if it's **top-level** (whole pipeline), **job-level** (one job), or **step-level** (one command) — indentation tells you this.
2. Ask: is this defining **what runs** (`run`, `script`), **when it runs** (`if`, `rules`, `on`, `when`), **where it runs** (`runs-on`, `image`), or **what it needs** (`with`, `variables`, `env`)?
3. GitHub docs: search "github actions [keyword]" → official docs almost always the first result.
4. GitLab docs: search "gitlab ci [keyword] keyword reference" → their keyword reference page covers everything.

# Generative AI, LLMs & AI Agents for Software Testing — Notes

## Table of Contents
- Section 1
- [Chapter 1: LLMs, AI Applications & AI Agents (Basics)](#chapter-1-llms-ai-applications--ai-agents-basics)
- [Chapter 2: AI Agents for Browser Automation & Course Roadmap](#chapter-2-ai-agents-for-browser-automation--course-roadmap)
- [Chapter 3: Data Privacy, Enterprise LLMs & Secure AI Usage](#chapter-3-data-privacy-enterprise-llms--secure-ai-usage)
- [Overall Key Takeaways](#overall-key-takeaways)
- [Prompt Engineering for QA](#prompt-engineering-for-qa)

---

## Chapter 1: LLMs, AI Applications & AI Agents (Basics)

### What is an LLM?
An LLM is the "brain" — trained on huge amounts of text (books, code, docs, websites) so it can understand language and generate answers based on patterns it learned, **not by searching the internet live**.
> **Example:** GPT-4, Gemini, Claude, DeepSeek, Llama are all LLMs.

### AI Application vs LLM — don't confuse them
People often think "ChatGPT" and "GPT-4" are the same thing. They're not.

| | AI Application (front-end) | LLM (brain) |
|---|---|---|
| Role | The interface you type into | The engine that actually "thinks" |
| Example | ChatGPT, Gemini app, GitHub Copilot | GPT-4, GPT-5, Gemini 2.0 Flash |
| Job | Takes your input, sends it to the LLM, shows you the reply | Understands the prompt and generates the answer/code |

> **Example:** ChatGPT (app) stayed the same, but the model behind it upgraded from GPT-4 → GPT-5. Same "car," better "engine."

### How a request flows
```
You type a prompt
   ↓
AI Application (e.g. ChatGPT) receives it
   ↓
LLM (e.g. GPT-4) processes it
   ↓
You get a response
```
> **Example:** You ask "Write Selenium code to log in." The LLM generates it because it has seen thousands of similar Selenium examples during training — it's not looking this up live.

### What is an AI Agent?
An LLM just *answers*. An **AI Agent** goes further: it **observes → decides → acts** on its own, with very little human help, until it reaches a goal.

> **Analogy:** A Tesla on autopilot. It watches the road, decides what to do (turn, brake, accelerate), and drives — you don't control every move.

### LLM vs AI Agent (in testing terms)
| Ask an LLM | Ask an AI Agent |
|---|---|
| "Generate Playwright code to log in" → gives you code, **you** still copy/run/debug it | "Log in and verify the dashboard" → it opens the browser, clicks, types, and checks the result **itself** |

### AI Agents in Testing
The agent can "see" a webpage the way a human does — reading the DOM, spotting buttons, links, and input fields — then follow plain-English steps:
```
Click Login → Enter username → Enter password → Verify Dashboard
```
No need to hand-write Selenium/Playwright scripts for each step.

### What this course will teach (quick list)
- **Prompt Engineering** — writing prompts that avoid "hallucinations" (AI making up wrong info).
- **Test Artifacts** — auto-generate test plans, test cases, test data, edge cases.
- **Automation Code** — Selenium/Playwright/Cypress scripts from plain instructions.
- **API Testing** — request bodies, assertions, TestNG/Cucumber setups.
- **SQL Generation** — turn "show me all orders over $500" into a real SQL query.
- **Code Assistance** — tools like GitHub Copilot to fix bugs and clean up code.
- **Offline LLMs** — run models locally when the internet or data-sharing isn't allowed.

---

## Chapter 2: AI Agents for Browser Automation & Course Roadmap

### Plain English → Browser Actions
Instead of writing test scripts, you literally say:
> "Login to the application using valid credentials and verify the dashboard."

The AI agent understands this and performs it in the browser — no code from you.

### The tools that make this possible: Browser Use & ChatGPT Operator
These act as the **bridge** between the AI's "thinking" and actual browser actions — they let the agent open pages, read the DOM, click buttons, and type text.

### Is testing fully codeless? Almost.
- **Test cases** → codeless (you just write instructions in English).
- **Framework** (the setup around the tests) → still needs code: folder structure, config files, reporting, execution setup.

> **Example:** You won't write `driver.findElement(By.id("login")).click();` anymore for a test — but someone still needs to code the framework that runs and reports all your English-language tests.

### Adding AI to frameworks you already have
If your team already uses Selenium/Playwright/Cypress, AI agents can be *added on top* to give you:
- Smarter element finding (no more brittle locators)
- **Self-healing tests** — if a button's ID changes, the agent still finds it
- Less maintenance overall

### Two ways to build with AI agents
1. **Add AI to an existing framework** → Existing Framework + AI Agent → Smarter Automation
2. **Build a new framework from scratch** → AI Agent → New Framework → Codeless Test Cases → Auto Execution

### AI-Powered Testing Tools (commercial products)
Built on top of Generative AI + AI Agents. Common features:
- Auto-generates tests
- Self-healing locators
- Natural language test writing
- Smart reports

### Testing AI itself — a different topic
Checking whether an LLM/chatbot is *accurate, unbiased, safe, and not hallucinating* is its own specialized field. This course only gives a brief overview — it's not the main focus.

### Roadmap of the course
1. Prompt Engineering
2. Test Plan/Case Generation
3. AI Agents (browser automation, codeless testing)
4. AI-Powered Testing Tools
5. Ongoing industry updates

---

## Chapter 3: Data Privacy, Enterprise LLMs & Secure AI Usage

### The problem: why companies banned tools like ChatGPT
When employees paste company data into public AI tools, that data leaves the company's servers and goes to an external cloud.

```
You → ChatGPT/Gemini (public website) → Cloud-hosted LLM → Response
```

> **Example:** A developer pastes proprietary source code into ChatGPT to "fix a bug" — that code has now left the company's control. This is the core fear behind AI bans.

**What's considered risky to share:** source code, business logic, customer data, internal docs, API keys, product roadmaps.

### How companies solve this (4 main strategies)

**1. Enterprise LLM Hosting**
Instead of the public ChatGPT, the company runs its **own private version** of the model on its own servers.
> **Example:** "ChatGPT Enterprise" or a company's private Gemini instance — same AI power, but your data never leaves the company's infrastructure.

**2. No Data Retention Policy**
The AI provider agrees *not to store or use your prompts* to train future models.
> **Example:** You ask about an internal project — that conversation isn't saved or reused anywhere.

**3. Gateway Controls** (a security checkpoint before the AI)
```
Your Prompt → Security Gateway (checks for sensitive info) → LLM
```
If you accidentally include a secret, internal project name, or credentials, the gateway **blocks or filters it out** before it ever reaches the AI.
> **Example:** You type "Debug this using API key ABC123..." — the gateway strips or blocks the key before sending the prompt onward.

**4. Running LLMs Offline (Locally)**
The whole model runs on the company's own machines — no internet needed, nothing ever leaves the building.
- **Pro:** Maximum privacy, great for regulated industries (banking, healthcare).
- **Con:** Needs serious hardware (RAM, GPUs) — expensive and technically hard to maintain.

### Whose job is AI security? Not yours (as QA)
Security/compliance decisions (which LLM, where it's hosted, access rules) are handled by **Infrastructure, DevOps, Security, and AI Platform teams**.

### What QA engineers should actually focus on
Once a secure AI solution exists (enterprise ChatGPT, private LLM, local LLM, etc.), your job is to **use it well** for:
- Writing test plans and test cases
- Generating test data
- Writing automation scripts and SQL queries
- Speeding up API testing

The course teaches these usage skills — the security setup is someone else's responsibility.

---

## Overall Key Takeaways
- **LLM** = the brain; **AI App** = the interface you use; **AI Agent** = something that acts on your behalf.
- AI agents can turn plain English into real browser actions — reducing (but not eliminating) the need for coded tests.
- Companies protect data via private hosting, no-retention policies, gateway filtering, or fully offline LLMs.
- As a QA engineer, your focus is **using AI effectively for testing** — not securing the AI infrastructure.


# Prompt Engineering for QA

## Table of Contents
1. Why Prompt Engineering Matters
2. The 3C Formula
3. Poor vs Good Prompt
4. Prompting Techniques
   - Zero-Shot Prompting
   - Few-Shot Prompting
   - Chain-of-Thought Prompting
   - Iterative Prompting
5. QA Examples
6. Key Takeaways

---

# 1. Why Prompt Engineering Matters

Prompt engineering is the skill of asking AI the right way to get accurate, relevant, and efficient responses.

### Why it is important
- AI output depends on:
  - **Context**
  - **Constraints**
  - **Clarity**
- Similar to writing:
  - Test Cases
  - Acceptance Criteria
- If requirements are unclear:
  - Developers ask questions.
  - Testers cannot validate correctly.
- AI behaves differently:
  - It **doesn't ask questions.**
  - It **hallucinates** (makes assumptions) and generates answers.

### Problems with Poor Prompts
- Wrong or irrelevant answers
- AI makes assumptions
- Wasted tokens
- Wasted time
- Multiple follow-up prompts required

> **Remember:** Hallucination is often caused by unclear prompts, not just AI limitations.

---

# 2. The 3C Formula

Every good prompt should contain three things.

## 1. Context
Tell AI:
- Role
- Background
- Scenario

Examples:
- Act as a QA Engineer.
- Behave like a Google Maps expert.
- You are reviewing Python code.

---

## 2. Constraints

Specify rules and limits.

Examples:
- By car
- Historical landmarks only
- Less than 1-hour detour
- Focus only on SQL Injection
- Include negative scenarios

---

## 3. Clarity

Specify how you want the output.

Examples:
- Table format
- Bullet points
- Include priority
- Include line numbers
- Include estimated time

---

## 3C Prompt Template

```
Context:
Explain the role and scenario.

Constraints:
Mention rules, scope, limitations.

Clarity:
Specify desired output format.
```

---

# 3. Poor vs Good Prompt

## Poor Prompt

```
How long does it take?
```

AI has to guess:
- Flight?
- Car?
- Walking?
- Train?

Result:
- Too much unnecessary information
- Hallucination
- Wasted tokens

---

## Better Prompt

```
How long does it take to drive by car
from San Francisco to New York?
```

---

## Best Prompt

```
I am planning a road trip from San Francisco
to New York in September.

Suggest historical landmarks
that require less than one hour detour.

Provide results in a table with:
- Name
- Location
- Estimated detour time
```

Contains:
- Context
- Constraints
- Clarity

---

# 4. Prompting Techniques

---

# Zero-Shot Prompting

## Definition

Ask AI directly without providing examples.

AI relies only on your prompt.

### Works well when:
- Prompt is very clear
- Enough context is provided

### Example

```
Act as a QA Engineer.

Review this login code for
security vulnerabilities.

Focus only on:
- SQL Injection
- Password Storage

Return findings as bullet points
with line numbers.
```

---

## Advantages

- Fast
- Simple
- One-shot response

---

## Disadvantages

Needs a very well-written prompt.

---

# Few-Shot Prompting

## Definition

Provide AI with one or more examples before asking the actual question.

AI learns from your examples.

---

## Structure

```
Example 1

↓

Example 2

↓

Now generate similar output.
```

---

## Example

```
Here are two sample test cases.

Generate test cases
for Forgot Password
using the same format.
```

---

## Advantages

- Consistent formatting
- Better structured output
- Less explanation required

---

## When to Use

- Company templates
- Existing documentation
- Fixed report formats
- Test case formats

---

# Chain-of-Thought Prompting

## Definition

Ask AI to explain its reasoning before giving the answer.

Example:

```
Let's reason step by step.

How long will it take to drive
from SFO to New York
at 65 mph
driving 8 hours per day?
```

Instead of only giving:

```
6 Days
```

AI explains:

- Distance
- Speed
- Formula
- Daily travel
- Final calculation

---

## Benefits

- Understand AI reasoning
- Verify calculations
- Reduce hallucinations
- Better for technical problems

---

## Modern AI Models

Many AI models already provide:

- Thinking mode
- Reasoning mode

Examples:
- ChatGPT
- Gemini
- DeepSeek

---

# Iterative Prompting

Instead of asking one vague question:

Ask follow-up questions to refine results.

Example:

```
Prompt 1

↓

Review answer

↓

Refine

↓

Ask follow-up

↓

Final answer
```

Keep iterations minimal to save time and tokens.

---

# 5. QA Prompt Examples

## Example 1 – Security Review

### Poor Prompt

```
Find bugs in my login code.
```

---

### Better Prompt

```
You are a QA Engineer.

Review my Python login code.

Focus on:

- SQL Injection
- Password Storage

Return:

- Issue
- Description
- Line Number
```

---

## Example 2 – Writing Test Cases

### Poor Prompt

```
Write test cases.
```

---

### Better Prompt

```
You are a QA Engineer
working on an e-commerce website.

Write test cases for:

- Valid Login
- Invalid Login
- Forgot Password

Include:

- Positive scenarios
- Negative scenarios
- Chrome testing
- Firefox testing

Output columns:

- Test Case ID
- Steps
- Expected Result
- Priority
```

---

## Benefits

- Exact output
- Less editing
- Better quality
- Saves time

---

# 6. Human + AI = Best Results

AI is not a replacement for technical skills.

Technical knowledge is required to:

- Validate AI responses
- Detect incorrect answers
- Refine prompts
- Improve generated solutions

AI provides answers.

Humans decide whether they are correct.

---

# 7. Tokens

Poor prompts increase:

- Token usage
- Cost
- Response time

Better prompts:

- Reduce token consumption
- Produce accurate responses
- Save time
- Save AI quota

---

# Key Takeaways

- Prompt engineering is a critical skill for QA and all technical roles.
- AI output depends on **Context + Constraints + Clarity (3C Formula)**.
- Poor prompts lead to hallucinations, wasted tokens, and inaccurate results.
- **Zero-Shot Prompting:** Ask directly with a clear prompt.
- **Few-Shot Prompting:** Provide examples so AI follows the same pattern.
- **Chain-of-Thought Prompting:** Ask AI to reason step-by-step for better transparency and accuracy.
- **Iterative Prompting:** Refine answers with minimal follow-up prompts.
- Always specify:
  - Role
  - Scope
  - Constraints
  - Expected output format
- Apply QA thinking when designing prompts.
- Technical knowledge remains essential to validate AI-generated answers.
- Better prompts = **Better, Faster, and Cheaper** AI responses (fewer tokens and less time).

---

# Quick Revision

## 3C Formula

| C | Meaning |
|---|---------|
| Context | Role and scenario |
| Constraints | Rules, scope, limitations |
| Clarity | Desired output format |

---

## Prompting Techniques

| Technique | Purpose |
|-----------|---------|
| Zero-Shot | Direct prompt without examples |
| Few-Shot | Learn from provided examples |
| Chain-of-Thought | Explain reasoning step-by-step |
| Iterative | Improve answers through follow-up prompts |

---

## Golden Rule

> **The quality of AI output depends more on the quality of your prompt than on the AI itself. Think like a QA engineer: provide clear requirements, constraints, and expected output to get reliable results.**


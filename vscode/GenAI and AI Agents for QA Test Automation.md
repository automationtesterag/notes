# Generative AI, LLMs & AI Agents for Software Testing — Notes

## Table of Contents
- Section 1
- [Chapter 1: LLMs, AI Applications & AI Agents (Basics)](#chapter-1-llms-ai-applications--ai-agents-basics)
- [Chapter 2: AI Agents for Browser Automation & Course Roadmap](#chapter-2-ai-agents-for-browser-automation--course-roadmap)
- [Chapter 3: Data Privacy, Enterprise LLMs & Secure AI Usage](#chapter-3-data-privacy-enterprise-llms--secure-ai-usage)
- [Overall Key Takeaways](#overall-key-takeaways)
- section 2
- [Prompt Engineering for QA](#prompt-engineering-for-qa)
- Section 3
- [Tokens & Context Window – Short Notes](#tokens--context-window--short-notes)
- Resources:[Project high level Requirements](#project-high-level-requirements)
- [Generate Test plan for the project business requirements](#generate-test-plan-for-the-project-business-requirements)
- [Generate Test Cases](#generate-test-cases)
- [Generating Test Strategy using AI](#generating-test-strategy-using-ai)
- [Generate Test Data combinations for the given tests using AI](#generate-test-data-combinations-for-the-given-tests-using-ai)
- section 4
- [Copilot - AI Buddy to help coding inside VS code editor for Playwright/Cypress](#copilot---ai-buddy-to-help-coding-inside-vs-code-editor-for-playwrightcypress)
- [GenAI Github copilot plugin for Selenium Java Frameworks within Intellij Editor](#genai-github-copilot-plugin-for-selenium-java-frameworks-within-intellij-editor)
- section 5 (only for understanding mcp doesn't have complete implementation for local machine)
- [Building AI Agents for Test Automation using MCP](#building-ai-agents-for-test-automation-using-mcp)
- [Setting Up Playwright MCP with Claude](#setting-up-playwright-mcp-with-claude)
- [MySQL MCP + Playwright MCP](#mysql-mcp--playwright-mcp)
- [REST API MCP](#rest-api-mcp)
- [Excel MCP](#excel-mcp)
- [VS Code Agent + MCP](#vs-code-agent--mcp)
- [Use Gemini in Your Terminal for Free: Quickstart](#use-gemini-in-your-terminal-for-free-quickstart)
- section 6
- [Custom Chat Modes, Multi-Agents, and Agentic AI](#custom-chat-modes-multi-agents-and-agentic-ai)
- section 7
- [Claude Code Setup & Initialization](#claude-code-setup--initialization)
- [Claude Code Skills](#claude-code-skills)
- [Claude Code Skills System (overview)](#claude-code-skills-system-overview)
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

# Tokens & Context Window – Short Notes

## 1. What is a Token?

* A **token** is the basic unit of text processed by an LLM.
* A token can be:

  * A whole word
  * Part of a word
  * Punctuation
  * Spaces
* In English, **1 token ≈ 4 characters ≈ ¾ of a word** (approximate, varies by model).

### Example

Prompt:

> **"Write test cases for login page"**

* Approximate token count: **8 tokens**
* Models internally split text into tokens before processing.

---

# 2. How Token Usage is Calculated

**Total Tokens = Input Tokens + Output Tokens**

* **Input Tokens:** Your prompt.
* **Output Tokens:** AI's response.

Both are billed and counted against model limits.

---

# 3. Long Prompt vs Short Prompt

## Vague Prompt

```
Write test cases for login page.
```

* Few input tokens
* AI makes assumptions
* Longer response
* More hallucinations
* Requires multiple follow-up questions
* Overall token usage increases

---

## Well-Structured Prompt

```
You are a QA engineer.
Write positive and negative login test cases.
Include boundary values.
Return output in table format.
```

* More input tokens
* Precise instructions
* Focused response
* Fewer follow-ups
* Lower total token consumption

> **A longer prompt can actually consume fewer total tokens than a vague prompt.**

---

# 4. Why Good Prompt Engineering Saves Tokens

A clear prompt:

* Reduces ambiguity
* Produces the correct answer faster
* Minimizes follow-up questions
* Reduces hallucinations
* Saves cost

Example:

* Vague prompt → ~200 total tokens after multiple clarifications
* Refined prompt → ~55 total tokens

Savings:

* **145 tokens per query**

Across thousands of prompts, this becomes significant.

---

# 5. Why Tokens Matter

Most commercial LLMs charge based on token usage.

Examples:

* ChatGPT
* Claude
* Gemini
* OpenAI APIs

More unnecessary conversation =

* More tokens
* More cost
* Slower workflow

Good prompt engineering saves:

* Time
* Money
* API usage
* Context space

---

# 6. Future Interview Perspective

Future AI interviews may evaluate:

* Time limit
* Token limit
* Prompt quality

Candidates will need:

1. Subject Matter Expertise
2. Prompt Engineering Skills

The goal is to get the best answer using the fewest tokens.

---

# 7. Context Window

## Definition

A **context window** is the maximum amount of conversation an AI model can remember within a single chat.

The model remembers:

* Previous prompts
* Previous responses
* Uploaded documents
* Instructions

until the context limit is reached.

---

# 8. What Happens When Context Window is Full?

The model may:

* Forget earlier conversation
* Lose important context
* Produce inconsistent answers
* Hallucinate more
* Reduce response quality
* Ask you to start a new chat (especially in free versions)

---

# 9. Why Context Window Matters

Poor prompting leads to:

* Many follow-up questions
* More tokens
* Faster context exhaustion
* Lost conversation history
* Lower response quality

Good prompting:

* Reaches the solution quickly
* Preserves context
* Improves consistency

---

# 10. Best Practices to Save Tokens

### ✅ Use the 3Cs

* **Context**
* **Clarity**
* **Constraints**

---

### ✅ Be Specific from the First Prompt

Avoid vague questions.

Instead of:

> Write test cases.

Use:

> As a QA engineer, write positive and negative login test cases in table format with expected results.

---

### ✅ Remove Unnecessary Words

Avoid filler phrases like:

* Can you...
* Please...
* Thank you...
* Nice...

They consume tokens (though acceptable in casual conversations).

---

### ✅ Avoid Re-uploading Documents

If you've already uploaded a PDF or file in the same chat:

* Don't upload it again.
* Simply refer to it:

  > "Based on the previously uploaded document..."

This saves tokens and preserves context.

---

### ✅ Structure Large Prompts

Use:

* Role
* Context
* Constraints
* Expected output

Instead of a random paragraph.

---

### ✅ Break Very Large Tasks into Smaller Prompts

If the task is too complex:

1. Get relevant information first.
2. Ask focused follow-up questions.

This is often more effective than one massive prompt.

---

# 11. Prompt Engineering is an Art

Good prompt engineering is about knowing:

* When to ask everything in one prompt
* When to split into multiple prompts
* How much context to provide
* How to minimize tokens while maximizing accuracy

This skill improves with practice.

---

# Key Takeaways

* **Token = Basic text unit processed by AI.**
* **Total Tokens = Input + Output.**
* A detailed prompt can reduce overall token usage.
* Better prompts reduce hallucinations and follow-up questions.
* Commercial LLMs charge based on token usage.
* Context window is the AI's temporary memory within a chat.
* Exceeding the context window causes forgotten context and lower-quality responses.
* Follow the **3Cs (Context, Clarity, Constraints)** for efficient prompting.
* Avoid filler words and repeated document uploads.
* Prompt engineering saves **time, cost, tokens, and improves response quality**.

# Project High Level Requirements

## Project Name
**"Style Haven" – A Fashion E-commerce Platform**

## Project Description
Style Haven is an online fashion marketplace designed to connect independent fashion designers with customers who crave unique and trendy clothing. The platform will provide a user-friendly shopping experience with personalized recommendations, secure transactions, and seamless customer support.

## Target Audience
Fashion-conscious individuals aged 18-35 who value individuality and seek out unique clothing pieces.

## Key Features and Requirements

### 1. User Accounts

#### Registration/Login
Secure user registration and login with email or social media accounts.

#### Profile Management
Allow users to update personal information, shipping addresses, and payment details.

#### Order History
View past orders and track current order status.

### 2. Product Catalog

#### Browse/Search
Intuitive browsing by category, brand, style, or search by keywords.

#### Product Pages
Detailed product descriptions, high-resolution images, size charts, and customer reviews.

#### Recommendations
Personalized product suggestions based on browsing history and preferences.

### 3. Shopping Cart

#### Add/Remove Items
Easy addition and removal of items from the shopping cart.

#### Quantity Adjustments
Ability to change item quantities in the cart.

#### Order Summary
Display a clear summary of items in the cart with total price and shipping costs.

### 4. Checkout

#### Shipping Options
Multiple shipping methods with estimated delivery times and costs.

#### Payment Gateway Integration
Secure payment processing with credit cards, debit cards, and digital wallets.

#### Order Confirmation
Generate an order confirmation email with details of the purchase.

### 5. Seller Dashboard

#### Product Listing
Allow sellers to create product listings with detailed descriptions and images.

#### Inventory Management
Track inventory levels and receive notifications for low stock.

#### Order Fulfillment
Manage orders, generate shipping labels, and update order status.

### 6. Customer Support

#### Live Chat
Offer real-time chat support for immediate assistance.

#### FAQ Section
Provide a comprehensive list of frequently asked questions.

#### Contact Form
Allow customers to submit inquiries via a contact form.

## Non-Functional Requirements

### Performance
Fast page loading times and responsive design for optimal user experience.

### Security
Secure data transmission and protection of user information.

### Scalability
Ability to handle increased traffic and transactions as the platform grows.

## Additional Features (Optional)

### Wishlists
Allow users to save items for future purchase.

### Promotions and Discounts
Implement discount codes and promotional offers.

### Loyalty Program
Reward repeat customers with points or special benefits.

### Social Media Integration
Enable sharing of products on social media platforms.


# Generate Test plan for the project business requirements

## prompt 1: Web application without automation

```
QA Test Plan Generation Prompt
Role

Act as a Senior QA Test Manager with experience in planning testing activities for large-scale web-based e-commerce applications.

Context

I will provide the project requirements.

Based on those requirements, create a professional and industry-standard QA Test Plan.

The application is a web-based e-commerce platform, and the generated test plan should be suitable for real-world software development projects.

Project Requirements

Paste the complete project requirements here.

Project Constraints
Team
3 Manual QA Testers
Timeline
45 Working Days
Supported Platforms
Windows
macOS
Supported Browsers
Google Chrome (Latest)
Safari (Latest on macOS)
Testing Approach

Use a Risk-Based Testing approach to maximize coverage within the available time and resources.

Testing Scope

Include the following testing types:

Smoke Testing
Functional Testing
Integration Testing
End-to-End Testing
Regression Testing
Cross-Browser Testing
Responsive UI Testing
Compatibility Testing
Basic Performance Validation
Basic Security Validation
Exploratory Testing
User Acceptance Testing (UAT) Support

Do not include:

Automation Testing
API Automation
Mobile Application Testing
Load Testing
Stress Testing
Penetration Testing
What the Test Plan Should Include

Generate a complete QA Test Plan with the following sections:

Project Overview
Testing Objectives
Scope
In Scope
Out of Scope
Test Strategy
Test Levels and Test Types
Test Environment
Browser and Platform Coverage
Test Deliverables
Entry Criteria
Exit Criteria
Assumptions
Risks and Mitigation Plan
Defect Severity and Priority Matrix
Requirement Traceability Strategy (RTM)
Resource Allocation for all three testers
Browser Allocation Matrix
Module Ownership for each tester
Week-wise Execution Plan for 45 working days
Test Effort Estimation
Regression Testing Strategy
Cross-Browser Testing Strategy
Test Data Preparation Strategy
Daily Execution and Status Reporting Plan
High-Priority Business Flows
Test Metrics and KPIs
Go / No-Go Release Criteria
Final Test Summary Report Template
Expectations

Follow industry best practices such as IEEE 829 and ISTQB.

The generated test plan should:

Be professional and suitable for stakeholders.
Use clear headings and well-formatted tables.
Include realistic effort estimates.
Assign work evenly across the three testers.
Prioritize critical business flows first.
Reserve sufficient time for regression testing, defect verification, and UAT support.
Cover all functional and non-functional requirements.
Include assumptions, risks, dependencies, and mitigation plans.
Be practical and executable within 45 working days.
Be ready to use without requiring additional formatting.
Output Format

Generate the response as a professional QA Test Plan using Markdown.

Use:

Clear headings
Tables wherever applicable
Bullet points for readability
Professional language suitable for project documentation

The final output should be complete enough to share directly with project managers, developers, clients, or stakeholders.
```

## Prompt 2: Web, mobile and api applications along with automation
```
QA Test Plan Generation Prompt
Role

Act as a Senior QA Test Manager with extensive experience in planning testing activities for enterprise web and mobile applications. Generate a professional, practical, and industry-standard QA Test Plan that can be directly shared with stakeholders.

Context

I will provide the project requirements after this prompt.

Based on those requirements, create a comprehensive QA Test Plan covering Web, Mobile, API, and Automation Testing where applicable.

The application may include web applications, mobile applications, APIs, third-party integrations, and backend services. Analyze the requirements and determine the appropriate testing scope.

Project Requirements

Paste the complete project requirements here.

Project Constraints

Generate the test plan using the following constraints.

Team
Total QA Engineers: 3
The team may consist of Manual QA Engineers, Automation QA Engineers, or Full Stack QA Engineers.
Timeline
Total Project Duration: 45 Working Days
Supported Platforms
Web
Windows
macOS
Mobile
Android
iOS
Supported Browsers
Google Chrome (Latest)
Safari (Latest)
Microsoft Edge (Latest) (if applicable)
Firefox (Latest) (if applicable)
Testing Scope

Include the following testing activities wherever applicable.

Functional Testing
Smoke Testing
Sanity Testing
Functional Testing
System Testing
Integration Testing
End-to-End Testing
Regression Testing
Exploratory Testing
User Acceptance Testing (Support)
Web Testing
Cross-Browser Testing
Responsive UI Testing
Compatibility Testing
Mobile Testing
Android Testing
iOS Testing
Device Compatibility Testing
Orientation Testing
Installation & Upgrade Testing
Mobile Usability Testing
API Testing
Functional API Testing
Request & Response Validation
Status Code Validation
Authentication & Authorization
Error Handling
Data Validation
Third-Party API Integration Testing
Automation Testing

Include an automation strategy covering:

Automation Scope
Automation Feasibility
Candidate Test Cases
Automation Framework Recommendation
Automation Tool Recommendation
CI/CD Integration Approach
Regression Automation Strategy
Automation Maintenance Plan
Non-Functional Testing
Basic Performance Validation
Basic Security Validation
Accessibility Validation
Scalability Considerations
Reliability
Usability
Testing Approach

Use a Risk-Based Testing approach.

Prioritize testing based on:

Business Criticality
Customer Impact
Technical Complexity
Risk
Dependency
Frequently Used Features

Optimize the overall testing effort so that the project can be completed successfully within the available resources and timeline.

Test Plan Deliverables

Generate a complete QA Test Plan containing the following sections.

1. Project Overview
2. Testing Objectives
3. Scope
In Scope
Out of Scope
4. Test Strategy

Include the overall testing approach.

5. Test Levels

Include:

Unit Testing (Development Responsibility)
Integration Testing
System Testing
End-to-End Testing
User Acceptance Testing
6. Test Types

List all applicable testing types.

7. Test Environment

Include:

Hardware
Software
Browsers
Mobile Devices
Operating Systems
Test Data
Third-Party Integrations
8. Browser & Device Coverage Matrix
9. API Testing Strategy
10. Automation Testing Strategy

Include recommendations for:

Framework
Tools
Reporting
CI/CD
Maintenance
11. Test Deliverables
12. Entry Criteria
13. Exit Criteria
14. Assumptions
15. Risks & Mitigation Plan
16. Dependencies
17. Requirement Traceability Matrix (RTM) Strategy
18. Defect Management Process

Include:

Workflow
Severity
Priority
SLAs
19. Defect Severity Matrix
20. Defect Priority Matrix
21. Resource Allocation

Assign responsibilities to all three testers.

22. Module Ownership Matrix

Clearly assign ownership for every module.

23. Browser Allocation Matrix
24. Device Allocation Matrix (if mobile exists)
25. Week-wise Execution Plan

Create a detailed execution schedule for the 45 working days.

Include:

Planning
Test Design
Environment Setup
Test Execution
Regression
Automation
API Testing
Cross-Browser Testing
Mobile Testing
UAT Support
Final Sign-off
26. Test Effort Estimation

Provide realistic effort estimates for every testing activity.

27. Regression Strategy
28. Cross-Browser Strategy
29. Mobile Testing Strategy
30. API Testing Strategy
31. Automation Strategy
32. Test Data Strategy
33. Daily Test Execution & Reporting Plan
34. High-Priority Business Flows

Identify and prioritize critical end-to-end business scenarios.

35. Test Metrics & KPIs

Include metrics such as:

Requirement Coverage
Test Case Coverage
Execution Progress
Pass Percentage
Defect Density
Defect Leakage
Reopen Rate
Automation Coverage
API Coverage
Regression Progress
36. Go / No-Go Release Criteria
37. Final Test Summary Report Template
Output Expectations

The generated QA Test Plan should:

Follow IEEE 829 and ISTQB best practices.
Be suitable for enterprise software projects.
Use professional language.
Include clear headings and well-structured tables.
Be practical and executable.
Include realistic effort estimates.
Balance the workload across all testers.
Prioritize high-risk and business-critical functionality.
Allocate sufficient time for defect fixing, regression testing, and UAT support.
Be comprehensive enough for stakeholder review and project sign-off.
Be formatted entirely in Markdown.
```
# Generate Test Cases
## prompt:( consider as example only)
# Test Case Generation Prompt (TestRail Style)

# Context

Act as a **Senior QA Test Lead** with 10+ years of experience in Manual Testing, Automation Testing, API Testing, Mobile Testing, and Test Management.

I will provide one or more of the following:

* Business Requirements
* User Stories
* Acceptance Criteria
* Functional Specifications
* Wireframes
* API Documentation
* UI Screens
* Process Flows

Analyze the requirements thoroughly before generating test cases.

Think like an experienced QA engineer and identify all possible positive, negative, boundary, validation, security, usability, compatibility, accessibility, and business rule scenarios.

If any requirement is ambiguous, make reasonable assumptions and clearly mention them.

---

# Constraints

Generate comprehensive and review-ready test cases following TestRail best practices.

## Test Design Techniques

Apply appropriate techniques wherever applicable:

* Equivalence Partitioning
* Boundary Value Analysis
* Decision Table Testing
* State Transition Testing
* Pairwise Testing
* Error Guessing
* Use Case Testing
* Risk-Based Testing

---

## Coverage

Generate test cases for every applicable testing type.

### Functional Testing

* Positive Scenarios
* Negative Scenarios
* Boundary Conditions
* Field Validation
* Business Rules
* End-to-End Flow
* Error Handling
* Session Management

### UI Testing

* Labels
* Alignment
* Fonts
* Colors
* Responsive Design
* Navigation
* Images
* Links
* Error Messages

### API Testing (if APIs exist)

* Request Validation
* Response Validation
* Status Codes
* Authentication
* Authorization
* Invalid Requests
* Data Validation
* Schema Validation

### Mobile Testing (if mobile application exists)

* Android
* iOS
* Orientation
* Installation
* Upgrade
* Interruptions
* Device Compatibility

### Cross Browser Testing

Generate browser-specific scenarios where applicable.

### Security Validation

Include basic security scenarios such as:

* Authentication
* Authorization
* Input Validation
* SQL Injection
* XSS
* Session Timeout
* Sensitive Data Exposure

### Performance Validation

Include lightweight validation scenarios such as:

* Page Load Time
* API Response Time
* Large Data Handling

### Accessibility

Include basic WCAG validation scenarios.

---

# Test Case Format

Generate the output in a format similar to **TestRail**.

For every test case include the following fields.

| Field                | Description                                                     |
| -------------------- | --------------------------------------------------------------- |
| Test Case ID         | Sequential IDs (TC-001, TC-002...)                              |
| Title                | Short and meaningful                                            |
| Module               | Feature/Module name                                             |
| Requirement ID       | Map to requirement if available                                 |
| Priority             | Critical / High / Medium / Low                                  |
| Severity             | Critical / High / Medium / Low                                  |
| Type                 | Functional / UI / API / Mobile / Security / Performance         |
| Preconditions        | Required setup                                                  |
| Test Data            | Sample input values                                             |
| Steps                | Numbered execution steps                                        |
| Expected Result      | Expected outcome                                                |
| Postconditions       | State after execution                                           |
| Automation Candidate | Yes / No                                                        |
| Automation Priority  | High / Medium / Low                                             |
| Tags                 | Smoke, Regression, Sanity, E2E, API, UI, Mobile, Security, etc. |

---

# Test Case Writing Guidelines

Each test case should:

* Validate only one logical scenario.
* Have a clear objective.
* Use simple and professional language.
* Be independent of other test cases whenever possible.
* Contain detailed execution steps.
* Include realistic test data.
* Clearly define expected results.
* Be reusable.
* Avoid duplicate scenarios.
* Follow ISTQB and TestRail standards.

---

# Additional Coverage

Include separate test cases for:

* Mandatory Fields
* Optional Fields
* Invalid Inputs
* Boundary Values
* Empty Values
* Maximum Length
* Minimum Length
* Special Characters
* Duplicate Data
* Expired Sessions
* Browser Refresh
* Network Failure
* Concurrent Users (where applicable)
* Error Messages
* Logging
* Audit Trail (if applicable)

---

# Traceability

Map every generated test case to its corresponding requirement or acceptance criterion whenever possible.

---

# Output Structure

Organize the test cases as follows:

## Module 1

### Functional Test Cases

### Negative Test Cases

### Boundary Test Cases

### UI Test Cases

### Security Test Cases

### API Test Cases (if applicable)

### Mobile Test Cases (if applicable)

---

Repeat the same structure for every module.

---

# Summary

At the end of the document provide:

* Total Test Cases
* Functional Test Cases
* UI Test Cases
* API Test Cases
* Mobile Test Cases
* Security Test Cases
* Performance Test Cases
* Smoke Test Cases
* Regression Test Cases
* Automation Candidates
* Requirement Coverage (%)

---

# Output Format

Generate the output in **Markdown** using professional tables similar to TestRail.

* Use one table row per test case.
* Keep execution steps in numbered lists within the table cell.
* Ensure the output can be easily copied into TestRail or imported into Excel with minimal formatting changes.
* Do not omit any critical scenarios, even if they are not explicitly mentioned in the requirements.

# Generating Test Strategy using AI
  (Shift Left Testing & Test Pyramid)

## 🎯 Objective

After generating **Test Cases**, use AI to identify **where each test should be executed** in the **Test Pyramid** instead of automating everything in the UI.

---

# Shift Left Testing

**Definition:**
Shift Left Testing means **starting testing as early as possible** in the Software Development Life Cycle (SDLC) to detect defects sooner.

### Benefits

* Finds bugs earlier
* Lower fixing cost
* Faster feedback to developers
* Better software quality
* Reduces UI automation effort
* Faster CI/CD execution

### Traditional vs Shift Left

| Traditional Testing       | Shift Left Testing         |
| ------------------------- | -------------------------- |
| Testing after development | Testing during development |
| Late bug detection        | Early bug detection        |
| Expensive fixes           | Cheaper fixes              |
| Heavy UI testing          | More Unit & API testing    |

---

# Test Pyramid

The Test Pyramid recommends having **more low-level tests** and **fewer UI tests**.

```
           UI Tests
        (Few - Slow)
      End-to-End Tests
--------------------------
     API / Integration
   (Medium Quantity)
--------------------------
      Unit Tests
(Most - Fastest & Cheapest)
```

---

# Test Layers

## 1. Unit Testing (Base Layer)

**Performed by:** Developers

### Tests

* Input validation
* Business logic
* Calculations
* Utility methods
* Data formatting
* Individual functions/classes

### Characteristics

* Fastest execution
* Cheapest
* Highly stable
* Easy to maintain

---

## 2. Integration / API Testing (Middle Layer)

**Performed by:** Developers & QA

### Tests

* API requests/responses
* Database integration
* Service communication
* Authentication
* CRUD operations
* Error handling

### Characteristics

* Faster than UI
* Validates system interactions
* Excellent automation ROI

---

## 3. End-to-End (UI Testing)

**Performed by:** QA Automation

### Tests

* Complete user journeys
* Login
* Registration
* Checkout
* Payment
* Search + Purchase
* Critical business flows

### Characteristics

* Slowest
* Most expensive
* More flaky
* Highest maintenance

---

# Example Distribution

| Test Case               | Recommended Layer |
| ----------------------- | ----------------- |
| Password validation     | Unit Test         |
| Email format validation | Unit Test         |
| Tax calculation         | Unit Test         |
| Registration API        | Integration/API   |
| Login API               | Integration/API   |
| Database update         | Integration/API   |
| User Registration Flow  | End-to-End        |
| Login Flow              | End-to-End        |
| Checkout Flow           | End-to-End        |
| Complete Purchase       | End-to-End        |

---

# Why NOT Automate Everything in UI?

### Problems

* Slow execution
* High maintenance
* Flaky tests
* Difficult debugging
* Higher infrastructure cost

### Better Strategy

* Maximum tests → Unit
* Moderate tests → API
* Minimum critical flows → UI

---

# Using AI for Test Strategy

After AI generates test cases, ask it:

> "Classify each test case into Unit Testing, Integration/API Testing, or End-to-End Testing based on Shift Left Testing and the Test Pyramid."

Then refine the output:

> "Add a new column called **Test Layer** and map every test case to Unit, Integration/API, or End-to-End."

This creates a spreadsheet like:

| Test ID | Test Case                        | Test Data     | Expected Result         | Test Layer  |
| ------- | -------------------------------- | ------------- | ----------------------- | ----------- |
| UA-01   | Register user                    | Valid data    | Registration successful | Integration |
| UA-02   | Validate password rules          | Weak password | Validation message      | Unit        |
| UA-03   | Validate email format            | Invalid email | Error message           | Unit        |
| UA-07   | Complete registration through UI | Valid details | User created            | End-to-End  |

You can then filter by **Test Layer** to:

* Build **Unit Tests**
* Plan **API Automation**
* Develop **UI Automation**

---

# AI Best Practices

✅ Use AI as a **guide**, not the final decision maker.

Always review:

* Some test cases may belong to multiple layers.
* Apply QA knowledge before assigning layers.
* Discuss Unit/API tests with developers.
* Reserve UI automation for critical business scenarios.

---

# Key Takeaways

* **Shift Left Testing** = Test earlier in the SDLC.
* **Test Pyramid** = More Unit Tests → Fewer API Tests → Minimal UI Tests.
* Avoid automating every test through the UI.
* Use AI to classify test cases into the appropriate testing layer.
* Add a **Test Layer** column for easier planning and filtering.
* Human expertise is essential to validate AI recommendations.
* A balanced strategy improves speed, quality, and automation ROI.

# Generate Test Data combinations for the given tests using AI
## Short Notes – AI for Unit Testing & Cucumber Automation

### 1. Unit Testing

* **Unit testing** verifies an individual method or function written by the developer.
* Focus is on validating business logic before the complete application is ready.
* Example: Validate email/password authentication logic.

### 2. AI for Unit Test Data Generation

Use AI to generate:

* Input combinations
* Expected outputs
* Positive and negative test scenarios
* Edge cases and boundary values

**Example Prompt:**

> "For Test ID UA-05, generate input and output combinations to test the unit method."

### 3. How QA Can Validate Unit Tests

Even without coding knowledge, QA can:

* Provide various input combinations.
* Compare actual vs expected output.
* Identify defects if invalid inputs produce successful results.

### 4. Benefits of Early Testing (Shift Left)

* Test developer code before the full application is available.
* Detect defects earlier.
* Reduce debugging effort and cost.

### 5. AI for Cucumber Gherkin Generation

AI can generate:

* Feature files
* Scenarios
* Given–When–Then steps
* Step definition skeletons

**Example Prompt:**

> "Generate Cucumber Gherkin feature file for Test Case PC-05."

### 6. Adding Background in Cucumber

Use **Background** for common prerequisite steps such as:

* User login
* Landing on Product Catalog page

**Example Prompt:**

> "Add a Background section for logging into the e-commerce application."

### 7. AI for Different Automation Frameworks

AI can convert the same scenario into different frameworks:

* Selenium Java
* Playwright JavaScript
* Cucumber with Playwright
* Other supported automation stacks

**Example Prompt:**

> "Convert this Cucumber Selenium implementation to Playwright JavaScript."

### 8. Generate Automation Skeleton

AI can generate:

* Browser launch
* Context creation
* Page initialization
* Navigation methods
* Empty step definitions
* Placeholder comments for locators

Only locators and project-specific logic need to be added manually.

---

# Key Takeaways

* AI can generate **unit test inputs and expected outputs**.
* QA can validate unit methods without deep programming knowledge.
* Shift-left testing enables testing during development.
* AI quickly generates **Cucumber Feature files**, **Background**, and **Step Definitions**.
* AI can convert automation code between frameworks (Selenium ↔ Playwright).
* AI creates automation skeletons, saving significant development time.

# Copilot - AI Buddy to help coding inside VS code editor for Playwright/Cypress
Signup with Github account is vscode and you are ready to use
<img width="1363" height="830" alt="Screenshot 2026-07-16 at 6 23 53 PM" src="https://github.com/user-attachments/assets/56e700e3-52bc-4648-bd81-e26378411f7e" />

# GenAI Github copilot plugin for Selenium Java Frameworks within Intellij Editor
<img width="435" height="358" alt="Screenshot 2026-07-16 at 6 45 24 PM" src="https://github.com/user-attachments/assets/024228df-306c-41a3-91f0-da8746804317" />

# Building AI Agents for Test Automation using MCP

This lecture introduces **Model Context Protocol (MCP)** and demonstrates how it enables **AI agents** to perform real-world automation tasks by connecting Large Language Models (LLMs) like ChatGPT, Claude, or Gemini with external systems such as browsers, databases, APIs, Excel files, and local file systems. 

---

# Objective

Build an AI agent that can execute an entire business test flow using only plain English instructions.

Example workflow:

1. Open a registration webpage.
2. Fetch test data from a database.
3. Fill the registration form.
4. Verify registration through REST API.
5. Save generated credentials to an Excel sheet.
6. Perform login validation.

No automation code is written manually—the AI agent coordinates everything. 

---

# Problem with Large Language Models

LLMs are excellent at reasoning based on their training data but **cannot directly interact with external systems**.

By default, they cannot:

* Access databases
* Automate browsers
* Execute REST APIs
* Read or modify local files
* Manipulate Excel spreadsheets

Example:

> "How many orders were placed in New York?"

An LLM cannot answer if the data resides in a company's internal database because it lacks access to that data. 

---

# What is MCP?

**MCP (Model Context Protocol)** is an open protocol that standardizes how applications expose their functionality and data to AI models.

It acts as a bridge between:

* LLMs
* External tools/resources

Examples:

* MySQL
* PostgreSQL
* Playwright
* Selenium
* REST APIs
* Local File System
* Excel
* Git

Think of MCP as:

> **USB-C for AI applications**

One standard interface allows AI models to interact with many different systems. 

---

# MCP Architecture

```
User Prompt
      │
      ▼
Large Language Model
(ChatGPT / Claude / Gemini)
      │
      ▼
MCP Client
      │
      ▼
MCP Server
      │
 ┌────┼─────┬────────┬─────────┐
 ▼    ▼     ▼        ▼
DB Browser APIs File System
```
<img width="616" height="404" alt="Screenshot 2026-07-20 at 7 50 27 AM" src="https://github.com/user-attachments/assets/25ca13bb-f5a0-4fe9-a974-7268df9579f5" />

The LLM interprets natural language and delegates execution to the appropriate MCP server. 

---

# How MCP Works

When the user asks:

> "Get all customers who placed at least one order."

The AI:

1. Understands the request.
2. Finds the connected MySQL MCP server.
3. Discovers available tools.
4. Reads the database schema.
5. Generates SQL automatically.
6. Executes the query.
7. Returns the results.

The AI learns the schema dynamically—it does not require prior knowledge of the database. 

---

# MCP Servers

Each application requires its own MCP server.

Examples:

* MySQL MCP
* PostgreSQL MCP
* Playwright MCP
* Selenium MCP
* Git MCP
* Filesystem MCP
<img width="453" height="299" alt="Screenshot 2026-07-20 at 7 52 28 AM" src="https://github.com/user-attachments/assets/9621db06-4554-4b6c-936a-f713d5cabcbb" />

There is no single universal MCP server; developers can create their own implementations for any application. 

---

# MCP Tools

Every MCP server exposes **Tools** (functions) that the AI can invoke.

Example from a MySQL MCP server:

```
execute_sql()
```

The AI:

* Discovers available tools
* Reads tool descriptions
* Determines the correct tool
* Supplies required inputs
* Executes the function

Tool descriptions are crucial because the LLM relies on them to choose the appropriate action. 

---

# Playwright MCP

Microsoft provides a Playwright MCP server exposing browser automation capabilities such as:

* browser_navigate
* browser_click
* browser_type
* browser_press
* browser_drag
* browser_go_back

Example prompt:

> "Open rahulshettyacademy.com and perform smoke testing."

The LLM selects the necessary Playwright tools and the MCP server executes the browser actions automatically. 

---

# LLM vs MCP Responsibilities

## Large Language Model

* Understands natural language
* Plans execution steps
* Chooses appropriate tools
* Generates SQL or workflow logic
* Makes decisions

---

## MCP Server

* Executes browser actions
* Connects to databases
* Performs API calls
* Reads/writes files
* Interacts with Excel
* Returns results

In short:

**LLM = Brain**

**MCP = Hands** 

---

# Configuring MCP

Connecting an MCP server to an AI client requires only a small JSON configuration.

The configuration specifies:

* MCP server
* Connection details
* Credentials (if needed)

Once configured, the AI client automatically discovers available tools and can begin using them. 

---

# Browser Automation Flow

Example prompt:

> "Go to rahulshettyacademy.com and perform smoke testing."

Execution flow:

1. LLM interprets the request.
2. Selects `browser_navigate`.
3. Opens the website.
4. Identifies clickable elements.
5. Clicks links.
6. Types into search fields.
7. Navigates pages.
8. Completes the smoke test.

The user provides only plain English instructions. 

---

# Database Intelligence

The AI can:

* Discover databases
* Identify tables
* Inspect schemas
* Understand primary and foreign keys
* Generate joins and subqueries
* Execute SQL

This enables natural language querying without manually writing SQL. 

---

# End-to-End AI Testing Vision

The instructor demonstrates an AI-driven test flow:

```
Database
     │
     ▼
Fetch Test Data
     │
     ▼
Browser Automation
     │
     ▼
Submit Registration
     │
     ▼
REST API Validation
     │
     ▼
Write Results to Excel
     │
     ▼
Complete Business Flow
```

All orchestrated through a single natural language prompt. 

---

# Key Takeaways

* LLMs alone cannot interact with external systems.
* MCP provides standardized access between AI and external tools.
* Each application exposes functionality through MCP servers.
* MCP servers offer reusable tools/functions.
* The LLM determines **what** should happen; the MCP server performs **how** it happens.
* Complex automation workflows can be executed using natural language alone.
* AI agents can combine browser automation, databases, APIs, file systems, and spreadsheets into a single end-to-end workflow. 

# Setting Up Playwright MCP with Claude

This lecture explains how to configure **Claude Desktop** as an MCP client and connect it with the **Playwright MCP Server** to perform browser automation using plain English commands. 

### Key Points

* **Choose an MCP Client**

  * Claude Desktop is recommended because it has built-in MCP support.
  * Other supported clients include VS Code, Cursor, and GitHub Copilot.
  * The MCP concepts remain the same regardless of the client used. 

* **Use Playwright MCP for Browser Automation**

  * Playwright MCP allows an LLM to control a web browser without writing automation code.
  * Selenium MCP is also an option, but the instructor uses Playwright since it is maintained by Microsoft. 

* **Configure the MCP Server**

  * Add the Playwright MCP configuration (JSON) to Claude's `config.json`.
  * The configuration specifies:

    * MCP server name
    * Command (`npx`)
    * Arguments (`@playwright/mcp`)
  * Node.js must be installed because the server is started using `npx`.
    
```
{
"mcpServers": {
 "playwright": {
  "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ]
    }
  }
}
```

* **How an MCP Request Works**

  1. User enters a plain English instruction.
  2. Claude identifies the appropriate MCP server (Playwright).
  3. Claude starts the Playwright MCP server using the configuration.
  4. Claude selects the required Playwright tool (e.g., `browser_navigate`, `browser_click`).
  5. It converts the instruction into MCP protocol messages.
  6. The MCP server executes the action in the browser. 

* **LLM Responsibilities**

  * Chooses the correct MCP server.
  * Selects the appropriate tool.
  * Determines required parameters.
  * Converts natural language into MCP protocol.
  * Sends requests to the MCP server. 

* **Playwright MCP Responsibilities**

  * Launches the browser.
  * Navigates to URLs.
  * Clicks elements.
  * Types into fields.
  * Performs browser actions using Playwright internally. 

* **After Configuration**

  * Restart Claude Desktop.
  * Claude detects the Playwright MCP server and exposes available browser tools (e.g., navigate, click, upload, go back). 

* **Practical Demo**

  * Prompt:

    * Navigate to `https://rahulshettyacademy.com/client`
    * Click the **Register Here** link.
  * Claude:

    * Requests permission.
    * Starts Playwright MCP.
    * Opens the browser.
    * Navigates to the website.
    * Clicks the registration link automatically. 

* **Next Step**

  * The browser reaches the registration page, but the form requires test data.
  * The next lecture connects a **MySQL MCP Server** to fetch data from a database and automatically populate the registration form, creating an end-to-end AI-driven workflow. 


# MySQL MCP + Playwright MCP

## Goal

Automate user registration by retrieving test data from a MySQL database and using Playwright to fill and submit the registration form.

Instead of manually writing SQL queries and Playwright code, Claude performs both tasks using MySQL MCP and Playwright MCP.

---

# Setup Overview

The complete setup consists of four steps:

* Install MySQL Server locally.
* Create the sample database and import the SQL script.
* Configure MySQL MCP in Claude Desktop.
* Configure Playwright MCP in Claude Desktop.

Once completed, Claude can access both the browser and the database.

---

# Step 1: Install MySQL

Install the following on your local machine:

* MySQL Community Server
* MySQL Workbench

During installation, note the following details:

* Host
* Port (default: `3306`)
* Username
* Password

These values will be required when configuring MySQL MCP.

---

# Step 2: Create the Database

* Open MySQL Workbench.
* Create a new database named `rahulshettyacademy`.
* Execute the provided SQL script.
* Verify that the following tables are created:

  * RegistrationDetails
  * UserNames
  * Customers
  * Orders

The SQL script inserts all sample data automatically.

```
create database rahulshettyacademy;
use rahulshettyacademy;

CREATE TABLE RegistrationDetails (
    id_number VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(20),
    occupation VARCHAR(100),
    gender VARCHAR(10),
    password VARCHAR(255), -- Storing passwords in plain text is insecure in a real application
    is_18_or_older BOOLEAN
);

CREATE TABLE UserNames (
    id_number VARCHAR(50) PRIMARY KEY,
    email VARCHAR(200),
    FOREIGN KEY (id_number) REFERENCES RegistrationDetails(id_number)
);

-- Inserting data into RegistrationDetails table
INSERT INTO RegistrationDetails (id_number, first_name, last_name, phone_number, occupation, gender, password, is_18_or_older) VALUES
('USER001', 'John', 'Doe', '123-456-7890', 'Engineer', 'Male', '12345', TRUE),
('USER002', 'Jane', 'Smith', '987-654-3210', 'Teacher', 'Female', '12345', TRUE),
('USER003', 'Peter', 'Jones', '555-123-4567', 'Student', 'Male', '12345', FALSE),
('USER004', 'Alice', 'Brown', '111-222-3333', 'Doctor', 'Female', '12345', TRUE),
('USER005', 'David', 'Wilson', '444-555-6666', 'Artist', 'Male', '12345', TRUE);

-- Inserting data into UserNames table
INSERT INTO UserNames (id_number, email) VALUES
('USER001', 'JohnDoe434@gmail.com'),
('USER002', 'JaneSmithgdfgf@gmail.com'),
('USER003', 'PeterJones5454@gmail.com'),
('USER004', 'AliceB3232wn@gmail.com'),
('USER005', 'DavidW24lson@gmail.com');

CREATE TABLE Customers (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(100),
    age INT
);

-- Table: Orders
CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT, -- Added auto-increment for easier insertion
    customer_id INT,
    subject VARCHAR(100),
    company VARCHAR(100),
    order_date DATE, -- Added an order date for potential grouping/filtering
    FOREIGN KEY (customer_id) REFERENCES Customers(id)
);


INSERT INTO Customers (id, name, city, age) VALUES
(1, 'Alice Smith', 'New York', 30),
(2, 'Bob Johnson', 'Los Angeles', 25),
(3, 'Charlie Brown', 'Chicago', 35),
(4, 'David Lee', 'New York', 28),
(5, 'Eve Williams', 'Houston', 32),
(6, 'Frank Miller', 'Los Angeles', 40);



INSERT INTO Orders (customer_id, subject, company, order_date) VALUES
(1, 'Laptop', 'Tech Solutions Inc.', '2024-01-15'),
(1, 'Software License', 'Global Software Corp', '2024-02-20'),
(2, 'Marketing Services', 'Ad Agency Pro', '2024-03-10'),
(3, 'Consulting Services', 'Business Growth Ltd.', '2024-04-01'),
(3, 'Training Program', 'EduTech Systems', '2024-05-05'),
(4, 'Web Development', 'Creative Web Design', '2024-06-12'),
(5, 'Cloud Storage', 'Data Secure Cloud', '2024-07-25'),
(2, 'Graphic Design', 'Visual Arts Studio', '2024-08-18'),
(1, 'Technical Support', 'Tech Solutions Inc.', '2024-09-01'),
(6, 'Hardware Upgrade', 'Computer Parts Plus', '2024-10-10');





-- Get the names of customers who have placed at least one order
SELECT name
FROM Customers
WHERE id IN (SELECT DISTINCT customer_id FROM Orders);


-- Find companies that have placed more than one order
SELECT company, COUNT(*) AS total_orders
FROM Orders
GROUP BY company
HAVING COUNT(*) > 1;


select * from RegistrationDetails;
select * from UserNames;

SELECT
    rd.id_number,
    rd.first_name,
    rd.last_name,
    un.email,
    rd.phone_number,
    rd.occupation,
    rd.gender,
    rd.is_18_or_older
FROM
    RegistrationDetails rd
JOIN
    UserNames un ON rd.id_number = un.id_number;
```

---

# Step 3: Configure MySQL MCP

Update the MySQL configuration in Claude Desktop with your local database details.

Replace:

* `MYSQL_HOST`
* `MYSQL_PORT`
* `MYSQL_USER`
* `MYSQL_PASSWORD`
* `MYSQL_DATABASE`

Also update the `uv` executable path if it differs on your system.

---

# Step 4: Configure Playwright MCP

Add the Playwright MCP configuration to Claude Desktop.

Playwright MCP enables Claude to:

* Launch the browser
* Navigate to web pages
* Click buttons
* Fill forms
* Submit data

---

# Claude MCP Configuration

> **Complete `config.json` (Playwright + MySQL only)
```
{
  "mcpServers": {
    "playwright": {
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ]
    },
    
	"mysql": {
      "command": "/Library/Frameworks/Python.framework/Versions/3.12/bin/uv",
      "args": [
        "--directory",
        "/Library/Frameworks/Python.framework/Versions/3.12/lib/python3.12/site-packages",
        "run",
        "mysql_mcp_server"
      ],
      "env": {
        "MYSQL_HOST": "localhost",
        "MYSQL_PORT": "3306",
        "MYSQL_USER": "root",
        "MYSQL_PASSWORD": "root1234",
        "MYSQL_DATABASE": "rahulshettyacademy"
      }
    }
  }
}
```

---

# Verify the Setup

Restart Claude Desktop.

Verify that both tools are available:

* Playwright MCP
* MySQL MCP

If both appear in the MCP tools list, the setup is complete. 

---

# Sample Prompts

### Retrieve Data from Database

```text
Show the first five records from the RegistrationDetails table.
```

---

### Execute a SQL Query

```text
Find the companies that have placed more than one order.
```

---

### Register Users

```text
Open the Rahul Shetty Academy client application.

Navigate to the registration page.

Retrieve the first two users from the RegistrationDetails and UserNames tables.

Register both users.

If any required field is missing, use appropriate values.

Generate a strong password if required.

Provide a summary after execution.
```

---

### Verify Registration

```text
Retrieve the first user from the database.

Register the user.

Verify whether the registration is successful.

If the user already exists, continue with the next available user.
```

---

# End-to-End Flow

```text
User Prompt
        │
        ▼
Claude
        │
        ├── Playwright MCP → Opens Browser
        │
        ├── MySQL MCP → Reads Database
        │
        ├── Claude → Combines Database Results
        │
        └── Playwright MCP → Completes Registration
```

---

This format keeps the notes concise and practical:

* **Goal** – What are we trying to automate?
* **Setup** – What needs to be installed and configured?
* **Code** – Complete SQL script and `config.json` kept intact in one place.
* **Usage** – Example prompts you can immediately run.
* **Flow** – A quick visual of how the MCP servers work together.


# REST API MCP

## Goal

Validate the user registration by making API calls after the Playwright automation completes.

Instead of manually creating HTTP requests or using Postman, Claude uses the REST API MCP server to:

* Read the API contract.
* Construct the HTTP request.
* Execute the API.
* Validate the response.
* Confirm whether the registration was successful.

---

# Setup Overview

The complete setup consists of four steps:

* Export the Postman Collection.
* Configure the Filesystem MCP.
* Configure the REST API MCP.
* Restart Claude Desktop.

Once completed, Claude can access both the API contract and execute API requests.

---

# Step 1: Export the Postman Collection

Export the API collection from Postman in **Collection v2.1 JSON** format.

Store the exported JSON file in a local directory.

Example:

```text
files_claude/
    └── EcomBasic.postman_collection.json
```

The Postman Collection acts as the **API contract** for Claude. It contains:

* Endpoints
* HTTP Methods
* Request Body
* Headers
* Expected Responses
* Collection Variables

In this example, the collection contains two APIs:

* Login
* Create Order

---

# Step 2: Configure Filesystem MCP

Configure the Filesystem MCP to point to the folder containing the Postman Collection.

This enables Claude to:

* Read local files.
* Locate the Postman Collection.
* Understand the API contract before making requests.

Without the Filesystem MCP, Claude cannot access local JSON files. 

---

# Step 3: Configure REST API MCP

Configure the REST API MCP by providing:

* Base URL
* Required Headers
* Authentication (if applicable)

In this course, only the following are required:

* Base URL
* Accept Header

The REST API MCP is responsible for executing HTTP requests after Claude understands the API contract. 

---

# Step 4: Restart Claude Desktop

Restart Claude Desktop after updating the configuration.

Verify that the following MCP servers are available:

* Playwright MCP
* MySQL MCP
* Filesystem MCP
* REST API MCP

Once these tools are visible, the setup is complete. 

---

# Complete MCP Configuration

```
{
  "mcpServers": {
    "playwright": {
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ]
    },

"filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/Users/rahulshetty/files_claude"
      ]
    },
  

"rest-api": {
      "command": "npx",
      "args": [
        "-y",
        "dkmaker-mcp-rest-api"
      ],
      "env": {
        "REST_BASE_URL": "https://rahulshettyacademy.com",
        "HEADER_Accept": "application/json"
      }
},
    


	"mysql": {
      "command": "/Library/Frameworks/Python.framework/Versions/3.12/bin/uv",
      "args": [
        "--directory",
        "/Library/Frameworks/Python.framework/Versions/3.12/lib/python3.12/site-packages",
        "run",
        "mysql_mcp_server"
      ],
      "env": {
        "MYSQL_HOST": "localhost",
        "MYSQL_PORT": "3306",
        "MYSQL_USER": "root",
        "MYSQL_PASSWORD": "root1234",
        "MYSQL_DATABASE": "rahulshettyacademy"
      }
    }
  }
}


``` 

---

# Complete Postman Collection

```
{
	"info": {
		"_postman_id": "6f231aca-8483-4b0e-ae59-8c8ae7d9a66d",
		"name": "EcomBasic",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "15901394",
		"_collection_link": "https://winter-resonance-174723.postman.co/workspace/E2E-Flows~5da70357-7e2c-4c21-bf20-24df743c5317/collection/15901394-6f231aca-8483-4b0e-ae59-8c8ae7d9a66d?action=share&source=collection_link&creator=15901394"
	},
	"item": [
		{
			"name": "Login",
			"event": [
				{
					"listen": "test",
					"script": {
						"exec": [
							"const jsonData = pm.response.json();",
							"pm.expect(jsonData.message).to.eql(\"Login Successfully\")",
							"pm.collectionVariables.set(\"token\",jsonData.token);",
							"pm.collectionVariables.set(\"userId\",jsonData.userId);",
							"",
							""
						],
						"type": "text/javascript",
						"packages": {}
					}
				}
			],
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"userEmail\": \"rahulshetty@gmail.com\",\n    \"userPassword\": \"Iamking@000\"\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "https://rahulshettyacademy.com/api/ecom/auth/login",
					"protocol": "https",
					"host": [
						"rahulshettyacademy",
						"com"
					],
					"path": [
						"api",
						"ecom",
						"auth",
						"login"
					]
				}
			},
			"response": []
		},
		{
			"name": "Create order",
			"event": [
				{
					"listen": "test",
					"script": {
						"exec": [
							"const jsonData = pm.response.json();",
							"pm.collectionVariables.set(\"orderId\",jsonData.orders[0]);",
							"pm.expect(jsonData.message).to.eql(\"Order Placed Successfully\");"
						],
						"type": "text/javascript",
						"packages": {}
					}
				}
			],
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Authorization",
						"value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mjc0MjU0OWUyNmI3ZTFhMTBlOWZjZTAiLCJ1c2VyRW1haWwiOiJyYWh1bHNoZXR0eUBnbWFpbC5jb20iLCJ1c2VyTW9iaWxlIjo5NTQzNjQ1NzU0LCJ1c2VyUm9sZSI6ImN1c3RvbWVyIiwiaWF0IjoxNzQzMTQ1MTYzLCJleHAiOjE3NzQ3MDI3NjN9.2ZqZDDESj7lIEKxEmLytkEkbeui3HVmjsOnPAPGiBfA",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"orders\": [\n        {\n            \"country\": \"India\",\n            \"productOrderedId\": \"67a8dde5c0d3e6622a297cc8\"\n        }\n    ]\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "https://rahulshettyacademy.com/api/ecom/order/create-order",
					"protocol": "https",
					"host": [
						"rahulshettyacademy",
						"com"
					],
					"path": [
						"api",
						"ecom",
						"order",
						"create-order"
					]
				}
			},
			"response": []
		}
	],
	"variable": [
		{
			"key": "token",
			"value": ""
		},
		{
			"key": "userId",
			"value": ""
		},
		{
			"key": "orderId",
			"value": ""
		}
	]
}
```

---

# Sample Prompts

### Read the API Contract

```text
Read the attached Postman Collection and understand all available APIs.
```

---

### Verify User Login

```text
Read the attached Postman Collection.

Use the Login API.

Execute the login request using the registered user's email and password.

Verify that the response contains:
- Status Code 200
- Login Successfully
- Authentication Token
```

---

### End-to-End Registration Validation

```text
Open the Rahul Shetty Academy client application.

Register a random user using data from the MySQL database.

Read the attached Postman Collection.

Understand the Login API contract.

Execute the Login API using the registered user's credentials.

Confirm that the login is successful and provide a summary.
```

---

### Create an Order

```text
Read the attached Postman Collection.

Authenticate using the Login API.

Use the returned token.

Execute the Create Order API.

Verify that the order is created successfully and return the Order ID.
```

---

# End-to-End Flow

```text
User Prompt
      │
      ▼
Claude
      │
      ├── Playwright MCP
      │      ↓
      │   Register User
      │
      ├── MySQL MCP
      │      ↓
      │   Retrieve User Data
      │
      ├── Filesystem MCP
      │      ↓
      │   Read Postman Collection
      │
      ├── REST API MCP
      │      ↓
      │   Execute Login / Create Order API
      │
      ▼
Validate Response
      │
      ▼
Execution Summary
```


# Excel MCP

## Goal

Store the successfully registered user details in an Excel sheet after validating the registration through the Login API.

Instead of manually opening Excel and updating records, Claude automatically writes the required data into the appropriate worksheet using the Excel MCP server.

---

# Setup Overview

The complete setup consists of four steps:

* Install the Excel MCP Server.
* Configure the Excel MCP in Claude Desktop.
* Place the Excel file inside the shared local directory.
* Restart Claude Desktop.

Once completed, Claude can read from and write to Excel files.

---

# Why Excel MCP?

The Filesystem MCP only provides access to local files and directories.

It can:

* Read files
* Write text files
* Create directories
* Move files
* List files and folders

However, it **cannot understand or modify Excel workbooks**.

Excel operations such as:

* Reading worksheets
* Writing rows
* Updating cells
* Listing sheet names

require a dedicated Excel MCP server. 

---

# Step 1: Install Excel MCP

* Open the Smithery MCP Registry.
* Search for an Excel MCP Server.
* Select an Excel MCP server that supports:

  * Read Sheet
  * Write Sheet
  * List Sheets
* Copy the Claude Desktop configuration.
* Generate your personal Smithery API Key.
* Replace the placeholder key with your own API key.

---

# Step 2: Configure Excel MCP

Add the Excel MCP configuration to your Claude Desktop `config.json`.

The configuration contains:

* NPX command
* Smithery CLI
* Excel MCP package
* Smithery API Key

Unlike MySQL MCP, no database credentials are required.

Unlike Filesystem MCP, no folder path is required because the Filesystem MCP already provides access to the local directory. 

---

# Step 3: Place the Excel File

Store the Excel workbook inside the same directory configured in the Filesystem MCP.

Example:

```text
files_claude/
│
├── EcomBasic.postman_collection.json
├── RegistrationData.xlsx
└── Other Project Files
```

Claude uses the Filesystem MCP to locate the workbook and the Excel MCP to modify it.

---

# Step 4: Restart Claude Desktop

Restart Claude Desktop after updating the configuration.

Verify that the following Excel tools are available:

* Read Sheet
* Write Sheet
* List Sheets

Once these tools appear, the Excel MCP setup is complete. 

---

# Complete MCP Configuration

> Paste the complete **Excel MCP configuration** here as one code block. 

---

# Sample Excel File

Create an Excel file similar to the following:

| Email | Password |
| ----- | -------- |
|       |          |

Claude automatically identifies the appropriate columns and writes the registered user's credentials into the sheet. 

---

# Sample Prompts

### Write Registered User to Excel

```text
Register a random user.

Verify the registration using the Login API.

Store the registered user's email and password in the Excel sheet.
```

---

### Update Existing Excel File

```text
Read the Excel workbook.

Append the newly registered user's credentials to the next available row.

Do not overwrite existing records.
```

---

### Complete End-to-End Automation

```text
Open the Rahul Shetty Academy client application.

Retrieve a random user from the MySQL database.

Register the user.

If the user already exists, generate a unique email and register again.

Read the Postman Collection.

Execute the Login API to verify successful registration.

After successful verification, write the user's email and password into the Excel workbook.

Provide a summary of all completed actions.
```

---

# End-to-End Flow

```text
User Prompt
      │
      ▼
Claude
      │
      ├── Playwright MCP
      │      ↓
      │   Register User
      │
      ├── MySQL MCP
      │      ↓
      │   Retrieve User Data
      │
      ├── Filesystem MCP
      │      ↓
      │   Locate Excel & Postman Files
      │
      ├── REST API MCP
      │      ↓
      │   Verify Login
      │
      ├── Excel MCP
      │      ↓
      │   Write Email & Password
      │
      ▼
Execution Summary
```

---

# Key Learning

* **Filesystem MCP** provides access to local files and directories but does **not** understand Excel workbooks.
* **Excel MCP** provides workbook-level operations such as reading sheets, writing data, and listing worksheets.
* The Filesystem MCP and Excel MCP work together:

  * Filesystem MCP locates the Excel file.
  * Excel MCP performs read/write operations.
* After a successful browser automation and API verification, Claude can automatically update the Excel workbook without any custom automation code.



---

# VS Code Agent + MCP

## Goal

Use GitHub Copilot **Agent Mode** inside VS Code to perform development tasks using natural language.

Instead of manually writing commands or code, the AI Agent can:

* Create a Playwright project.
* Generate Playwright test cases.
* Execute tests.
* Interact with the browser using Playwright MCP.
* Commit code.
* Push code to GitHub.

---

# Setup Overview

The complete setup consists of five steps:

* Install VS Code Insiders.
* Install GitHub Copilot extension.
* Configure Playwright MCP.
* Configure GitHub MCP.
* Restart VS Code Insiders.

Once completed, GitHub Copilot Agent can interact with browsers and GitHub repositories using MCP servers. 

---

# Step 1: Install VS Code Insiders

Download and install **VS Code Insiders**.

The instructor uses VS Code Insiders because new AI Agent and MCP features are available there before they are released in the stable version of VS Code. 

---

# Step 2: Install GitHub Copilot

Install the following extensions:

* GitHub Copilot
* GitHub Copilot Chat

Open the Copilot panel and switch the interaction mode from:

```
Ask
```

to

```
Agent
```

Agent Mode allows Copilot to execute tasks using MCP tools instead of only answering questions. 

---

# Step 3: Configure Playwright MCP

There are two ways to configure Playwright MCP:

### Option 1 (Recommended)

Install directly from Microsoft's Playwright MCP page.

### Option 2

Copy the Playwright MCP configuration into the VS Code User Settings JSON.

Open:

```
Preferences
→ Open User Settings (JSON)
```

Add the Playwright MCP configuration.

Restart VS Code Insiders after saving the configuration.

Once configured, Copilot Agent automatically detects the available Playwright MCP tools. 

---

# Step 4: Configure GitHub MCP

Install the official GitHub MCP server.

Before using it:

* Install Docker Desktop.
* Create a GitHub repository.
* Generate a GitHub Personal Access Token.
* Grant the required repository permissions.
* Add the token to the GitHub MCP configuration.

Restart VS Code Insiders after saving the configuration.

Once configured, the Agent can interact directly with GitHub repositories. 

---

# Step 5: Verify MCP Tools

Open GitHub Copilot Agent.

Verify that the required MCP tools are available.

The instructor demonstrates that the number of available tools increases as additional MCP servers are configured, confirming successful integration. 

---

# Complete MCP Configuration

> Paste the complete **Playwright MCP** and **GitHub MCP** configuration here as one code block.

---

# Sample Prompts

### Create a Playwright Project

```text
Create a Playwright project under the AIAgentMCP folder.
```

---

### Generate a Test Case

```text
Create a Playwright test that:

- Opens the Rahul Shetty Academy client application.
- Logs in with valid credentials.
- Selects the iPhone X product.
- Adds the product to the cart.
- Opens the checkout page.
- Verify that the product is present in the cart.
```

---

### Execute the Test

```text
Run the generated Playwright test in headed mode.
```

---

### Commit Code

```text
Initialize Git.

Stage all files.

Commit the project using an appropriate commit message.
```

---

### Push Code to GitHub

```text
Push the committed code to my GitHub repository.

Repository:
<Repository URL>
```

---

# End-to-End Flow

```text
User Prompt
      │
      ▼
GitHub Copilot Agent
      │
      ├── Playwright MCP
      │      ↓
      │   Create Project
      │
      ├── Playwright MCP
      │      ↓
      │   Generate Tests
      │
      ├── Playwright MCP
      │      ↓
      │   Execute Tests
      │
      ├── GitHub MCP
      │      ↓
      │   Initialize Git
      │
      ├── GitHub MCP
      │      ↓
      │   Commit Changes
      │
      ├── GitHub MCP
      │      ↓
      │   Push Repository
      │
      ▼
Execution Summary
```

---

# Key Learning

* **VS Code Agent** executes development tasks using MCP servers instead of just generating code.
* **Playwright MCP** gives the agent real browser interaction, allowing it to inspect the DOM, generate accurate locators, create Playwright projects, and execute tests. Without MCP, an LLM may guess or hallucinate locators; with Playwright MCP it retrieves them from the actual page. 
* **GitHub MCP** enables the agent to perform Git operations such as initializing a repository, staging files, committing changes, and pushing code using natural language prompts.
* Writing **clear and specific prompts** is important. The more precise the instructions, the more accurate the generated project, test cases, and Git operations will be. 

# Use Gemini in Your Terminal for Free: Quickstart

Welcome! This hands-on guide walks you through installing, authenticating, configuring, and using Gemini CLI on your machine—step by step and beginner-friendly.

You'll be up and running in minutes.

> **If you're looking to connect external tools via MCP servers, see Part 2.**

---

# What is Gemini CLI?

Gemini CLI brings the power of Google's Gemini models to your terminal.

You can:

* Ask questions
* Generate and explain code
* Summarize documents
* Run structured tools

—all from the command line.

---

# Prerequisites

* A recent **Node.js + npm** installation
* Internet access to authenticate with your Google account
* *(Optional)* Docker (if you want to try sandboxed runs)

### Check if Node.js and npm are installed

```bash
node -v
npm -v
```

If those commands show versions, you're good to go.

---

# 1) Install Gemini CLI

The simplest way is a global npm install:

```bash
npm install -g @google/gemini-cli
```

Now launch the CLI:

```bash
gemini
```

### Alternative: Run without installing globally

Always pulls the latest published version.

```bash
npx @google/gemini-cli
```

### Optional: Run the latest commit from GitHub

Useful for testing the newest changes.

```bash
npx https://github.com/google-gemini/gemini-cli
```

---

# 2) First Run: Authenticate

On first launch, the CLI will ask how you want to authenticate.

For most users:

1. Start the CLI

```bash
gemini
```

2. Choose **Login with Google**

3. Select your Google account in the browser

4. Complete the sign-in process

### Tip (Windows)

If the browser doesn't open:

* Copy the shown URL into a browser manually.
* If a firewall blocks localhost redirects, temporarily allow the CLI to open a local callback (standard OAuth behavior).

---

# 3) Your First Prompt (Two Easy Ways)

## Option 1 — One-off Prompt

```bash
gemini -p "Explain what this project does in one paragraph."
```

---

## Option 2 — Interactive Session (Recommended)

```bash
gemini
```

Then type your message and press **Enter**.

### Helpful Commands

```
/help
```

List commands.

```
/clear
```

Clear the conversation.

```
/quit
```

Exit.

---

# 4) Optional: Run in a Sandbox (Extra Safety)

Gemini CLI can execute tools in an isolated container for safety.

If Docker is installed:

```bash
gemini --sandbox -y -p "List five fun CLI project ideas, each with a one-sentence pitch."
```

Or run the published sandbox image directly:

```bash
docker run --rm -it us-docker.pkg.dev/gemini-code-dev/gemini-cli/sandbox:0.1.1
```

---

# 5) Configure the CLI (settings.json)

Gemini CLI uses JSON settings files so you can tailor behavior.

### User Settings Location

**Windows**

```
C:\Users\<username>\.gemini\settings.json
```

**macOS/Linux**

```
~/.gemini/settings.json
```

Create the file if it doesn't exist.

### Starter Configuration

```json
{
  "general": {
    "vimMode": false
  },
  "ui": {
    "showCitations": true,
    "useFullWidth": false
  },
  "model": {
    "enableShellOutputEfficiency": true
  },
  "tools": {
    "sandbox": false,
    "enableToolOutputTruncation": true,
    "truncateToolOutputThreshold": 20000,
    "truncateToolOutputLines": 1000
  }
}
```

### Notes

* Switch `"tools.sandbox"` to `true` for default sandboxing.
* Environment variables are supported inside JSON strings.

Example:

```json
"apiKey": "${MY_API_TOKEN}"
```

Project-specific settings can live in:

```
.project/.gemini/settings.json
```

These override user settings for that project.

---

# 6) Verify Things Are Working

### Get Help

```bash
gemini --help
```

---

### Try a Coding Task

```bash
gemini -p "Write a JavaScript function that returns the nth Fibonacci number with O(n) time and O(1) space."
```

---

### Summarize a File

Interactive session recommended.

You can:

* Paste file contents
* Ask the model how to best summarize a large file using available tools

---

# Zero-Cost Automation: Playwright + DevTools MCP with Gemini

In Part 1, you installed and ran Gemini CLI.

In this guide, you'll wire up two high-value MCP servers:

* Playwright MCP
* Chrome DevTools MCP

so Gemini can discover and use them immediately.

---

# MCP in a Nutshell

MCP servers expose tools that Gemini CLI can call.

You declare them under:

```json
mcpServers
```

inside `settings.json`.

On startup, Gemini CLI:

* Discovers tools
* Validates schemas
* Registers them for the model

If two servers export the same tool name:

* The first keeps the original name.
* Later ones are automatically prefixed:

```
serverAlias__toolName
```

---

# Where to Put Configuration

## User Scope (All Projects)

### Windows

```
C:\Users\<username>\.gemini\settings.json
```

### macOS/Linux

```
~/.gemini/settings.json
```

---

## Project Scope

```
<project>/.gemini/settings.json
```

Project settings override user settings.

---

# Quick Add: Minimal Starter

```json
{
  "mcp": {
    "allowed": ["playwright", "chrome-devtools"]
  },
  "mcpServers": {}
}
```

---

# Option A: Playwright MCP (Recommended for Automation)

Playwright MCP lets Gemini automate and inspect web pages using Playwright's accessibility tree.

Add to `settings.json`:

```json
{
  "mcpServers": {
    "playwright": {
      "command": "npx",
      "args": ["@playwright/mcp@latest"],
      "trust": false
    }
  }
}
```

---

## Optional Capabilities + Headless Mode

```json
{
  "mcpServers": {
    "playwright": {
      "command": "npx",
      "args": [
        "@playwright/mcp@latest",
        "--caps=pdf",
        "--caps=testing",
        "--headless"
      ],
      "trust": false
    }
  }
}
```

---

## Run as a Long-lived HTTP/SSE Service

Start server:

```bash
npx @playwright/mcp@latest --port 8931
```

Gemini configuration:

```json
{
  "mcpServers": {
    "playwright": {
      "url": "http://localhost:8931"
    }
  }
}
```

---

### Windows Note

Playwright downloads browsers to your user cache.

If a corporate policy blocks downloads:

Run once outside policy or preinstall browsers.

```bash
npx playwright install
```

---

### Verify in Gemini CLI

```text
/mcp
```

Try:

> Open example.com and take a page snapshot, then list network requests.

---

# Option B: Chrome DevTools MCP (Performance & Deep Debugging)

Chrome DevTools MCP provides:

* Performance tracing
* Screenshots
* Console inspection
* Network inspection

using Chrome DevTools Protocol.

---

## Add to settings.json

```json
{
  "mcpServers": {
    "chrome-devtools": {
      "command": "npx",
      "args": ["chrome-devtools-mcp@latest"],
      "trust": false
    }
  }
}
```

---

## Use Canary + Headless + Isolated Profile

```json
{
  "mcpServers": {
    "chrome-devtools": {
      "command": "npx",
      "args": [
        "chrome-devtools-mcp@latest",
        "--channel=canary",
        "--headless=true",
        "--isolated=true"
      ]
    }
  }
}
```

---

## Connect to an Existing Chrome Instance

```json
{
  "mcpServers": {
    "chrome-devtools": {
      "command": "npx",
      "args": [
        "chrome-devtools-mcp@latest",
        "--browser-url=http://127.0.0.1:9222"
      ]
    }
  }
}
```

---

## Windows: Start Chrome with Remote Debugging

```bash
"C:\Program Files\Google\Chrome\Application\chrome.exe" --remote-debugging-port=9222 --user-data-dir="%TEMP%\chrome-profile-stable"
```

---

## Verify

```text
/mcp
```

Try:

> Check the performance of [https://developers.chrome.com](https://developers.chrome.com) and provide insights.

---

# Trust, Confirmations, and Conflicts

### trust: true

Skips confirmation prompts for that server's tools.

Use only for known-safe servers.

---

### Tool Name Conflicts

If two servers expose the same tool:

* First discovered keeps the plain name.
* Later ones become:

```
serverAlias__toolName
```

---

### Tool Filtering

You can use:

* `includeTools`
* `excludeTools`

inside each server configuration.

---

# Verify Your Setup (Checklist)

### 1. List Servers

```text
/mcp
```

---

### 2. Test Playwright

Prompt:

> Navigate to [https://example.com](https://example.com), then take a screenshot and show me the page title.

---

### 3. Test Chrome DevTools

Prompt:

> Start a performance trace for [https://news.ycombinator.com](https://news.ycombinator.com), navigate, then stop the trace and summarize top opportunities.

---

If prompted:

Choose **Proceed** or configure:

* `trust`
* allow-list

based on your policy.

---

# Troubleshooting

## Server Shows DISCONNECTED

### Playwright

* Ensure Node 18+
* Ensure Playwright browsers are installed
* Try `--headless` in CI

---

### Chrome DevTools

Confirm:

* Chrome is installed
* If using `--browser-url`, Chrome was started with:

```bash
--remote-debugging-port=<port>
```

and

```bash
--user-data-dir
```

Increase timeout if necessary and verify firewall rules for local ports.

---

## Tools Missing

Check:

* `includeTools`
* `excludeTools`

Inspect:

```text
/mcp
```

to see what registered.

---

## Browser Didn't Open or Authentication Blocked

Some enterprise environments block launching browsers.

Use:

* `--headless`

or connect to an existing browser via:

```bash
--browser-url
```

---

## Name Conflicts

Use:

```
serverAlias__toolName
```

or filter tools to avoid conflicts.

# Custom Chat Modes, Multi-Agents, and Agentic AI

### 1. Problem with a Single AI Agent

Initially, one AI agent is configured with multiple MCP servers such as:

* Playwright
* MySQL
* GitHub
* Excel
* APIs
* File System

This works well technically because a single prompt can perform end-to-end tasks.

**Example**

> Get data from the database → Register user in browser → Execute API → Save results in Excel.

### Real-world issue

In enterprise environments:

* QA engineers don't have database permissions.
* Database teams don't have Playwright access.
* Not everyone should have GitHub repository access.
* Giving one agent every tool effectively grants everyone excessive permissions.

This violates the **Principle of Least Privilege** and creates security and governance concerns.

---

# 2. Solution: Custom Chat Modes (Sub Agents)

Instead of one "super agent", create **specialized agents**.

Examples:

| Agent          | MCP Tools  |
| -------------- | ---------- |
| Browser Agent  | Playwright |
| Database Agent | MySQL      |
| GitHub Agent   | GitHub MCP |
| Excel Agent    | Excel MCP  |
| API Agent      | API MCP    |

Each agent only knows how to perform its own task.

---

## Browser Agent

Contains only:

* Playwright MCP

Instructions can be customized:

* Perform manual validation first.
* Then automate.
* Prefer Playwright best practices.
* Use resilient locators.
* Avoid XPath.
* Reuse Page Objects.
* Follow existing framework conventions.

If asked:

> Navigate to website

It can perform the action.

If asked:

> Connect to MySQL database

It refuses because it has no database capability.

---

## Database Agent

Contains only:

* MySQL MCP

Instructions:

* Query database
* Read tables
* Execute SQL
* Return data

If asked:

> Navigate to browser

It refuses because Playwright isn't available.

---

# 3. Benefits of Custom Chat Modes

### Security

Only authorized users receive access to specific agents.

Example:

Database team
→ Database Agent only

Automation team
→ Browser Agent only

---

### Better Permission Management

Instead of:

One agent with 20 tools

Use:

* Browser Agent
* Database Agent
* GitHub Agent
* Excel Agent

Each has only the required permissions.

---

### Better Performance

Smaller agents:

* have fewer instructions
* less context
* reduced confusion
* produce more focused responses

---

### Easier Maintenance

Updating Database Agent won't affect Browser Agent.

Similar to software engineering:

* Single Responsibility Principle (SRP)
* Modular architecture

---

### Reduced Complexity

Instead of:

1 Agent

* 20 MCPs

Use:

4 Agents

* 5 MCPs each

Much easier to debug and maintain.

---

### Organization-level Control

With GitHub Copilot Enterprise:

Administrators can:

* Create custom chat modes
* Assign them to teams
* Restrict available tools
* Control permissions centrally

Example:

QA Team

* Browser Agent
* GitHub Agent

Database Team

* Database Agent

Business Team

* Excel Agent

---

# 4. Multi-Agent Architecture

Instead of:

```
One Agent
├── Browser
├── Database
├── GitHub
├── Excel
├── API
```

Use:

```
Browser Agent
      │
Database Agent
      │
GitHub Agent
      │
Excel Agent
      │
API Agent
```

Each agent specializes in one domain.

---

# 5. Why Multi-Agents?

Single agent problems:

* Too many responsibilities
* Large prompts
* Conflicting instructions
* Hard to maintain
* Security risks

Multi-agent benefits:

* Specialized expertise
* Better accuracy
* Better scalability
* Easier maintenance
* Parallel execution possibilities
* Better enterprise governance

---

# 6. What is Agentic AI?

An **AI Agent** performs tasks using tools.

**Agentic AI** is a system where **multiple specialized AI agents collaborate autonomously** to achieve a goal.

Instead of the user coordinating agents manually, the agents communicate with each other.

---

## Traditional AI

User asks:

> Answer this question.

AI responds.

---

## AI Agent

User asks:

> Open browser and register a user.

Agent performs the task using MCP tools.

---

## Agentic AI

User gives only the goal.

Example:

> Create smoke tests from recent bug fixes and execute them.

The agents determine the workflow themselves.

---

# 7. Example: Jira + Playwright

Agents:

### Jira Agent

Responsibilities:

* Connect to Jira
* Read recent bug fixes
* Analyze bug descriptions
* Generate smoke test scenarios

---

### Playwright Agent

Responsibilities:

* Receive generated test cases
* Automate them
* Execute tests
* Report results

---

## Agent Collaboration

Goal:

```
Create smoke tests from recent bug fixes and execute them.
```

Workflow:

```
User Goal
     │
     ▼
Jira Agent
     │
Reads recent bugs
     │
Creates smoke tests
     │
Passes tests
     ▼
Playwright Agent
     │
Automates tests
     │
Executes them
     ▼
Returns report
```

The user never coordinates the agents manually.

This autonomous coordination is **Agentic AI**.

---

# 8. AI Ecosystem Overview

```
                Large Language Model (LLM)
                        │
                        ▼
                + MCP / External Tools
                        │
                        ▼
                    AI Agent
                        │
        ─────────────────────────────
        │          │         │
        ▼          ▼         ▼
 Browser Agent  DB Agent  Jira Agent
        │          │         │
        └──────────┼─────────┘
                   ▼
         Multi-Agent Collaboration
                   │
                   ▼
             Agentic AI
```

---

# 9. AI Agent vs Agentic AI

| AI Agent                   | Agentic AI                         |
| -------------------------- | ---------------------------------- |
| Single intelligent agent   | Multiple collaborating agents      |
| Uses MCP tools             | Uses multiple specialized agents   |
| Executes user instructions | Plans and coordinates autonomously |
| User controls every step   | Agents decide execution order      |
| One agent solves tasks     | Team of agents solves goals        |

---

# 10. Frameworks for Building Agentic AI

To implement Agentic AI in practice, specialized frameworks are used:

* **Microsoft AutoGen** – Build and orchestrate collaborating AI agents.
* **CrewAI** – Create role-based multi-agent workflows with autonomous collaboration.

These frameworks provide agent communication, task delegation, memory, and orchestration without requiring extensive low-level implementation.

---

## Key Takeaways

* A single agent with many MCP tools is suitable for demos but is difficult to secure and manage in enterprise environments.
* **Custom Chat Modes** in GitHub Copilot act as **specialized sub-agents** with only the tools they need.
* Multi-agent architecture improves **security, maintainability, scalability, and performance**.
* **Agentic AI** extends this idea by allowing specialized agents to **autonomously coordinate** and complete complex goals.
* Frameworks like **Microsoft AutoGen** and **CrewAI** help implement practical Agentic AI systems where agents collaborate similarly to a team of domain experts.

# Claude Code Setup & Initialization

#### **Key Takeaways**

* Install **Claude Code** from the official website.
* You can use it in multiple ways:

  * 💻 Desktop App
  * 🖥️ Terminal (Recommended)
  * 🔌 VS Code / JetBrains Plugin
  * 🌐 Web Version
* **Terminal installation** is preferred because it works with any IDE and project.
* **Windows users** should install **Git for Windows** first, then install Claude Code using Git Bash.
* After installation, open your project folder and run:

  ```bash
  claude
  ```

  This starts Claude in the context of your project.

### **Using Claude Desktop**

* Open **Claude Desktop → Code** tab.
* Select your project folder.
* Claude will understand your project and answer questions based on your codebase.

### **Chrome Extension**

* Install the **Claude Chrome Extension**.
* Sign in with the same Claude account.
* Useful for browser-based workflows.

### **Subscription**

* Claude Code requires a **Pro subscription**.
* The instructor recommends subscribing for a month to practice its capabilities.

### **Important First Command**

Run this inside your project:

```bash
/init
```

### **What `/init` Does**

* Scans the entire project.
* Creates a **CLAUDE.md** file in the project root.
* Stores:

  * Project overview
  * Tech stack
  * Coding style
  * Commands to run the project
  * Basic configuration
* This file is loaded automatically whenever Claude starts, so it remembers the project's basic context.

> **Note:** Keep `CLAUDE.md` concise. Avoid filling it with excessive project details.

### **Overall Workflow**

1. Install Claude Code.
2. Open your project.
3. Run `claude`.
4. Execute `/init`.
5. Claude scans the project and creates `CLAUDE.md`.
6. Start asking questions, generating code, reviewing code, creating tests, and automating tasks based on your project.

# Claude Code Skills

### **Key Concept**

Claude Code uses **Skills** to perform intelligent tasks. There are **two types of skills**:

1. **Knowledge Skills** → Store project knowledge.
2. **Agent Skills** → Perform tasks using that knowledge.

---

## **1. Knowledge Skills (What the App Knows)**

Knowledge Skills are **passive documents** that contain all the information about your project.

They typically include:

* Functional requirements
* User journeys
* Acceptance criteria
* Technology stack
* Database schema
* API documentation
* Development guidelines

> **Purpose:** Provide accurate project context so AI doesn't hallucinate.

---

## **2. Agent Skills (What the AI Does)**

Agent Skills are **task-oriented instructions** that use Knowledge Skills to complete work.

Example:

* Create test scenarios
* Review code
* Generate Playwright tests
* Perform API testing
* Create test strategy

---

## **How It Works**

```
Project Documents
    │
    ├── Functional Requirements
    ├── User Flows
    ├── API Docs
    ├── Database Schema
    └── Development Guidelines
            │
            ▼
     Knowledge Skill
     (Stores Information)
            │
            ▼
       Agent Skill
  (Reads Knowledge + Executes Task)
            │
            ▼
     Generates Test Scenarios
```

---

## **What an Agent Skill Should Define**

### **1. Role**

Tell Claude who it should act as.

Example:

> "Act as a Senior QA Engineer."

---

### **2. Thinking Process**

Guide how Claude should approach the task.

For test scenarios:

* Positive test cases
* Negative test cases
* Edge cases
* API scenarios
* UI validation
* Error handling

---

### **3. Output Format**

Specify how the result should be presented.

Examples:

* Test Case ID
* Scenario
* Preconditions
* Steps
* Expected Result
* Priority

---

## **Why Knowledge Skills Are Important**

Without project documents:

* AI guesses information.
* Responses may be inaccurate.

With Knowledge Skills:

* AI understands the project.
* Generates accurate, context-aware outputs.

---

## **Human QA vs AI QA**

| Human QA                    | Claude AI                    |
| --------------------------- | ---------------------------- |
| Reads requirement documents | Reads Knowledge Skills       |
| Understands business flow   | Understands Knowledge Skills |
| Uses QA experience          | Uses Agent Skills            |
| Writes test scenarios       | Generates test scenarios     |

---

## **Key Takeaways**

* **Knowledge Skills** = Project information ("What the project is").
* **Agent Skills** = Instructions ("How to perform the task").
* Agent Skills rely on Knowledge Skills for accurate context.
* A good Agent Skill should define:

  * **Role**
  * **Thinking process**
  * **Output format**
* Better context leads to better AI-generated results.

# Claude Code Skills System (overview)

These notes combine all the lectures you've covered into one structured reference.    

---

# Overview

Claude Code is designed around **Skills**.

A Skill is simply a Markdown document (`SKILL.md`) containing plain English instructions.

There are two types of skills:

1. **Knowledge Skills**
2. **Agent Skills**

Knowledge Skills provide information.

Agent Skills perform work using that information.

---

# Overall Architecture

```text
Business Documents
(Product Requirements)
        │
        ▼
Knowledge Skills
(Project Knowledge)
        │
        ▼
Agent Skills
(Execution Logic)
        │
        ▼
AI performs QA Tasks
        │
        ├── Test Scenarios
        ├── Test Strategy
        ├── Playwright Tests
        ├── Test Review
        └── Test Execution
```

---

# Project Structure

```text
.claude/
│
└── skills/
    │
    ├── eventhub-domain/
    │      └── SKILL.md
    │
    ├── create-scenarios/
    │      └── SKILL.md
    │
    ├── test-strategy/
    │      └── SKILL.md
    │
    ├── playwright-best-practices/
    │      └── SKILL.md
    │
    ├── generate-tests/
    │      └── SKILL.md
    │
    └── review-tests/
           └── SKILL.md
```

Every Skill has its own folder containing a **`SKILL.md`** file.

---

# Types of Skills

## 1. Knowledge Skill

Purpose

Stores project knowledge.

Examples

* Business Rules
* API Documentation
* Database Schema
* User Journey
* Tech Stack
* Framework Guidelines
* Playwright Standards

Think of it as

> AI's Confluence Documentation

Knowledge Skills never perform work.

They only provide context.

---

## 2. Agent Skill

Purpose

Performs a task.

Examples

* Create Test Scenarios
* Generate Test Strategy
* Write Playwright Tests
* Review Automation
* Execute Tests

Think of it as

> AI Team Member

---

# Standard Skill Structure

Every skill starts with

```md
name:
description:
```

Everything else is simply plain English.

Example

```md
name: create-scenarios

description:
Generate functional test scenarios from EventHub domain.
```

No programming required.

---

# Knowledge Skill 1 — EventHub Domain

Purpose

Teach Claude about the project.

Contents

* Product Overview
* Functional Requirements
* User Journey
* Business Rules
* Database Models
* API Endpoints
* Validation Rules
* Tech Stack
* Test Accounts
* Registration Flow
* Booking Flow
* Refund Flow

Think of it as

> Complete Requirement Document

---

# Knowledge Skill 2 — Playwright Best Practices

Purpose

Teach AI how your automation framework works.

Contents

## Coding Standards

* Naming Convention
* Folder Structure
* Test Organization

---

## Locator Priority

Preferred order

```
data-testid

↓

getByRole()

↓

getByLabel()

↓

getByText()

↓

CSS/XPath
```

---

## Test Structure

Follow

```
Arrange

↓

Act

↓

Assert
```

---

## Framework Practices

* Page Object Model
* API Testing
* Assertions
* Wait Strategy
* Test Data
* CI/CD Practices
* Debugging Tips
* Anti-patterns

Examples

Avoid

* waitForTimeout()
* Hardcoded waits
* Unnecessary CSS selectors

---

# Agent Skill 1 — Create Scenarios

Role

```
Senior Functional QA Engineer
```

Input

Reads

* EventHub Domain
* Frontend Code
* Backend Code

Thinking Process

Claude generates

* Happy Path
* Business Rules
* Negative Tests
* Edge Cases
* Security Tests
* Validation Tests
* Error Scenarios

Output

```
TC ID

Priority

Category

Preconditions

Steps

Expected Result

Business Rule Mapping
```

---

# Agent Skill 2 — Test Strategy

Purpose

Analyze generated test cases.

Role

```
Test Architect
```

Reads

* Generated Test Scenarios
* Domain Knowledge
* Source Code
* Architecture

Claude decides

```
Which layer should test belong to?
```

Possible Layers

```
Unit

↓

API

↓

Component

↓

End-to-End
```

Decision Rules

| Scenario       | Layer      |
| -------------- | ---------- |
| Business Logic | Unit       |
| API Contract   | API        |
| UI Component   | Component  |
| Full User Flow | End-to-End |

---

Output

```
Distribution Table

Unit Tests

API Tests

Component Tests

E2E Tests

Decision Rationale

Recommendations
```

Benefits

Instead of automating everything,

Claude pushes tests to the lowest suitable layer.

Result

* Faster Execution
* Less Maintenance
* Better Test Pyramid

---

# Agent Skill 3 — Generate Playwright Tests

Purpose

Write automation code.

Role

```
Senior Test Automation Engineer
```

Reads

* Playwright Best Practices
* Domain Skill
* Test Strategy
* Source Code

Process

```
Read E2E Test Cases

↓

Find Locators

↓

Write Playwright Tests

↓

Execute Tests

↓

Validate Result

↓

Fix Automation Issues

↓

Report Application Bugs
```

---

# Automatic Validation Workflow

Claude doesn't stop after writing code.

It also

```
Write Test

↓

Run Test

↓

If Failed

↓

Read Error

↓

Open Browser

↓

Reproduce Failure

↓

Check Source Code

↓

Check Domain Requirement

↓

Decide

Automation Bug?

OR

Application Bug?
```

---

# Bug Decision Logic

### Automation Bug

If

* Domain says feature shouldn't exist
* Test expects incorrect behavior

Claude

```
Fixes Automation Test
```

---

### Application Bug

If

* Domain confirms expected behavior
* Application behaves differently

Claude

```
Reports Application Bug

Keeps Test Failing
```

Very important

AI should never modify a test just to make it pass.

---

# Avoid Context Bloat

Large projects may have

```
5000+

10000+

20000+

Lines
```

Problem

Every prompt loads everything.

Issues

* High Token Usage
* High Cost
* Slow Response
* Lower Accuracy
* Context Window Exhausted

---

Solution

Split documentation.

Example

```
eventhub-domain/

│

├── SKILL.md

├── api-reference.md

├── business-rules.md

├── frontend.md

├── backend.md
```

Main Skill

```
If API required

↓

Read api-reference.md

If Business Rules required

↓

Read business-rules.md

If Automation required

↓

Read frontend.md
```

Benefits

* Faster
* Smaller Context
* Better Performance
* Lower Cost

---

# Invoking Skills

## Option 1 (Recommended)

Directly invoke

```bash
/create-scenarios
```

```bash
/test-strategy
```

```bash
/generate-tests
```

---

## Option 2

Natural Language

```
Generate booking scenarios

Create Playwright tests

Analyze test strategy
```

Claude selects the correct Agent automatically.

---

# Knowledge vs Agent Skills

| Knowledge Skill       | Agent Skill           |
| --------------------- | --------------------- |
| Stores information    | Performs work         |
| Passive               | Active                |
| Project Documentation | AI Employee           |
| Loaded for context    | Invoked for execution |

---

# Complete AI QA Workflow

```text
Business Requirements
        │
        ▼
Knowledge Skill
(EventHub Domain)
        │
        ▼
Create Scenario Agent
        │
        ▼
Functional Test Scenarios
        │
        ▼
Test Strategy Agent
        │
        ▼
Categorize Tests
(Unit/API/Component/E2E)
        │
        ▼
Playwright Best Practices
        │
        ▼
Generate Test Agent
        │
        ▼
Generate Playwright Tests
        │
        ▼
Execute Browser Tests
        │
        ▼
Analyze Failures
        │
        ├── Automation Bug → Fix Test
        │
        └── Application Bug → Report Defect
```

---

# Key Takeaways

* **Claude Code is built around Skills**—Markdown documents that either provide knowledge or perform tasks.
* **Knowledge Skills** store project context (requirements, APIs, architecture, coding standards).
* **Agent Skills** act like specialized AI teammates that use Knowledge Skills to execute work.
* Design every Agent Skill with four essentials:

  * **Role** (who the AI should act as)
  * **Knowledge Sources** (which documents to read)
  * **Thinking Process** (how to reason)
  * **Output Format** (how to present results)
* Keep knowledge **modular** to avoid context bloat by splitting large documentation into referenced sub-documents.
* Use AI to build an end-to-end QA pipeline:

  1. Understand the domain.
  2. Generate test scenarios.
  3. Classify them using the Test Pyramid.
  4. Generate Playwright tests following project standards.
  5. Execute, validate, and distinguish **automation defects** from **real application bugs**.

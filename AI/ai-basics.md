# AI Agentic — Quick & Practical Notes

## 1. LLM Fundamentals

### What is an LLM?

An **LLM (Large Language Model)** is an AI model trained on large amounts of text/code that can understand and generate human-like text.

Examples:

* Claude
* GPT
* Gemini
* Llama

### Important concepts

**Token**

* Small unit of text processed by the model.
* A word can be one or multiple tokens.

**Context**

* Information provided to the model while solving a task.
* Includes instructions, conversation history, tool results, documents, etc.

**Context Window**

* Maximum amount of information the model can process at a time.

**Temperature**

* Controls randomness.
* Lower → more predictable.
* Higher → more varied/creative.

**Hallucination**

* Model generates information that sounds correct but is actually incorrect or unsupported.

### LLM vs Agent

```text
LLM
 ↓
Understands + generates response

Agent
 ↓
LLM
 + Tools
 + Context
 + Memory/State
 + Decision making
 + Actions
```

**Example**

LLM:

> "The weather might be 30°C."

Agent:

> Calls weather API → gets current temperature → responds with actual data.

### Remember

> **LLM generates. Agent reasons, uses tools and acts.**

**Reference:** [Anthropic Model Documentation](https://docs.anthropic.com/en/docs/about-claude/models/overview)

---

# 2. Prompt Engineering

### What is Prompt Engineering?

Prompt engineering is the practice of designing instructions so an AI model produces the **desired and consistent output**.

A good prompt should clearly define:

```text
Role
 ↓
Context
 ↓
Task
 ↓
Constraints
 ↓
Expected Output
 ↓
Examples (if required)
```

### Example

```text
You are a QA automation engineer.

Analyze the following API response.

Check:
- HTTP status
- Response schema
- Missing fields
- Incorrect values

Return the result as a table.
```

This is better than:

```text
Check this API response.
```

### Common techniques

**Role prompting**

> You are a senior QA engineer.

**Few-shot prompting**

* Give examples of expected input/output.

**Structured output**

* Ask for JSON, table, list, etc.

**Prompt chaining**

* Break a complex task into multiple steps.

**Constraints**

* Tell the model what it should and shouldn't do.

### Important point

Don't start by trying to create the "perfect prompt."

First define:

1. What does success mean?
2. How will you measure it?
3. What does a good output look like?

Anthropic's current prompting guidance explicitly recommends defining success criteria and evaluation before spending significant effort on prompt engineering. ([Claude Platform][1])

### Remember

> **Clear input + clear expectations = better output.**

**Reference:** [Anthropic Prompt Engineering Guide](https://platform.claude.com/docs/en/build-with-claude/prompt-engineering/overview)

---

# 3. Agentic AI Fundamentals

### What is an AI Agent?

An AI agent is an AI system that can **decide what actions to take to achieve a goal**.

A simplified agent loop:

```text
User Goal
   ↓
Understand
   ↓
Plan
   ↓
Choose Action
   ↓
Use Tool
   ↓
Observe Result
   ↓
Decide Next Step
   ↓
Complete Task
```

### Example — QA Agent

User:

> "Analyze today's failed automation tests."

Agent could:

```text
Read test report
      ↓
Find failed tests
      ↓
Read logs
      ↓
Analyze failure
      ↓
Check application/API
      ↓
Classify failure
      ↓
Generate report
```

### Workflow vs Agent

**Workflow**

```text
Step 1 → Step 2 → Step 3 → Step 4
```

Steps are mostly predetermined.

**Agent**

```text
Goal
 ↓
Agent decides:
  "Should I call API?"
  "Should I inspect logs?"
  "Should I retry?"
  "Should I ask the user?"
```

### When should you use an agent?

Use an agent when:

* The task has multiple possible paths.
* Decisions depend on intermediate results.
* Tools need to be selected dynamically.
* The task cannot easily be represented as fixed steps.

Don't use an agent just because it sounds advanced. Anthropic recommends starting with the simplest architecture that solves the problem and adding complexity only when it provides real value. ([Anthropic Resources][2])

### Remember

> **Agent = Goal + Reasoning + Tools + Actions + Feedback loop**

**Reference:** [Anthropic — Building Effective AI Agents](https://resources.anthropic.com/building-effective-ai-agents)

---

# 4. MCP — Model Context Protocol

### What is MCP?

**MCP (Model Context Protocol)** is a standard way for AI applications to connect with external tools, systems and data.

Think of MCP as a **common interface between AI and external capabilities**.

```text
                 AI Application
                       ↓
                    MCP
                       ↓
       ┌───────────────┼───────────────┐
       ↓               ↓               ↓
     GitHub           Jira          Database
```

### Example

You have an AI QA Agent.

Instead of writing custom integration logic for every system:

```text
AI Agent
   ↓
MCP
   ↓
GitHub → Read PR
Jira   → Create bug
DB     → Get test data
Slack  → Send message
```

### MCP can expose

* **Tools** → actions the AI can perform
* **Resources** → information/data available to the AI
* **Prompts** → reusable instructions

### MCP vs API

**API**

> Application communicates with another application.

**MCP**

> AI application gets a standardized way to discover/use external capabilities.

### Remember

> **MCP is a standard connection layer, not an AI model.**

The MCP specification is actively evolving; the July 2026 specification introduced major protocol changes including a stateless core and authorization improvements. ([Model Context Protocol Blog][3])

**Reference:** [Official Model Context Protocol](https://modelcontextprotocol.io/)

---

# 5. Tool Calling / Function Calling

### What is Tool Calling?

Tool calling allows an AI model to **request an external function** when it needs information or needs to perform an action.

### Example

User:

> "Check whether the payment API is working."

Agent:

```text
User
 ↓
LLM
 ↓
Select healthCheck API tool
 ↓
Application executes API
 ↓
API returns 200
 ↓
LLM interprets result
 ↓
"Payment API is healthy"
```

### Important

The LLM usually **doesn't execute the function itself**.

It produces a structured request such as:

```text
Tool: getPaymentStatus
Input:
{
  "transactionId": "12345"
}
```

The application executes the function and sends the result back to the model.

### Typical tools

* API calls
* Database queries
* File operations
* Web search
* Calculator
* Browser automation
* Email
* Jira/GitHub
* Cloud services

### Why tools matter

Without tools:

> AI can only work with information available in its context.

With tools:

> AI can **retrieve information and perform actions**.

### Remember

> **Tool calling turns an LLM from an answer generator into an action-capable system.**

**Reference:** [Anthropic Tool Use](https://platform.claude.com/docs/en/agents-and-tools/tool-use/overview)

---

# 6. RAG — Retrieval-Augmented Generation

### What is RAG?

RAG allows an LLM to retrieve relevant information from an external knowledge source before generating an answer.

Instead of:

```text
Question → LLM → Answer
```

we have:

```text
Question
   ↓
Search Knowledge
   ↓
Relevant Documents
   ↓
LLM
   ↓
Answer
```

### Typical RAG pipeline

```text
Documents
    ↓
Chunking
    ↓
Embeddings
    ↓
Vector Database
    ↓
User Question
    ↓
Similarity Search
    ↓
Relevant Chunks
    ↓
LLM
    ↓
Answer
```

### Key terms

**Chunking**

* Break large documents into smaller pieces.

**Embedding**

* Convert text into numerical vectors representing semantic meaning.

**Vector Database**

* Stores embeddings and helps find similar content.

**Retrieval**

* Finds relevant information for the user's question.

**Reranking**

* Reorders retrieved results based on relevance.

### Example — Company QA

Documents:

```text
HR Policy
Leave Policy
Insurance Policy
Travel Policy
```

User:

> "How many annual leaves can I take?"

RAG:

```text
Question
 ↓
Search company documents
 ↓
Find Leave Policy
 ↓
Send relevant section to LLM
 ↓
Generate answer
```

### Why use RAG?

Useful when information is:

* Company-specific
* Frequently changing
* Too large to put directly into the prompt
* Not available in the model's training knowledge

### Remember

> **RAG = Retrieve relevant knowledge → Give it to LLM → Generate grounded answer.**

**Reference:** [Anthropic — Contextual Retrieval](https://www.anthropic.com/news/contextual-retrieval)

---

# 7. Memory

### What is Agent Memory?

Memory allows an agent to retain and reuse useful information.

### Short-Term Memory

Information needed for the current interaction.

Example:

```text
User: My API uses OAuth.
User: Create an authentication test.
```

The agent can use the previous message.

### Long-Term Memory

Information retained beyond the current conversation/session.

Example:

```text
User preference:
Java + Rest Assured
```

Later:

> "Create an API automation example."

Agent can use the stored preference.

### Simple architecture

```text
Agent
 ↓
Memory Manager
 ↓
 ┌──────────────┐
 │ Short-term   │
 │ Long-term    │
 └──────────────┘
```

### Memory challenges

You need to decide:

* What should be stored?
* What should not be stored?
* When should memory be retrieved?
* When should it be updated?
* When should it expire/delete?

### Important

More memory isn't automatically better.

Bad memory can result in:

* Outdated information
* Wrong assumptions
* Larger context
* Privacy problems

### Remember

> **Memory gives an agent continuity, but memory must be managed carefully.**

---

# 8. Multi-Agent Systems

### What is a Multi-Agent System?

Instead of one agent performing everything, multiple specialized agents work together.

### Example — QA Platform

```text
                 QA Orchestrator
                       ↓
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
    API Agent       UI Agent      Analysis Agent
        ↓              ↓              ↓
    API Tests       UI Tests      Failure Analysis
```

### Example task

> "Run regression and provide the final report."

**API Agent**

* Runs API tests.

**UI Agent**

* Runs UI tests.

**Analysis Agent**

* Analyzes failures.

**Report Agent**

* Creates final report.

### Benefits

* Specialization
* Parallel work
* Separation of responsibilities
* Easier scaling for complex tasks

### Problems

* More tokens/cost
* More latency
* Communication complexity
* Harder debugging
* More failure points

### Rule

Don't create 5 agents when one agent can solve the problem.

> **Start simple → measure → add agents when necessary.**

Anthropic's current guidance similarly emphasizes choosing between single-agent, workflow, and multi-agent architectures based on actual business value and complexity. ([Anthropic Resources][2])

---

# 9. Evaluation & Observability

This is one of the **most important production topics**.

An agent can produce a response that looks good but still be wrong.

### Evaluation

Evaluation answers:

> **Did the agent actually perform the task correctly?**

Measure things like:

* Accuracy
* Task success
* Tool selection
* Tool arguments
* Final response quality
* Hallucination
* Cost
* Latency
* Regression

### Example

Agent task:

> "Create a Jira bug for failed payment tests."

Don't only check:

> Was the response generated?

Check:

```text
Was the correct failure identified?
        ↓
Was the correct Jira project selected?
        ↓
Was the correct bug created?
        ↓
Were required fields populated?
        ↓
Was duplicate creation avoided?
```

### Observability

Track the complete agent execution:

```text
User Input
   ↓
Prompt
   ↓
LLM Response
   ↓
Tool Selected
   ↓
Tool Input
   ↓
Tool Output
   ↓
Next Decision
   ↓
Final Response
```

This makes debugging much easier.

### Evaluation vs Observability

**Evaluation**

> "Was the result correct?"

**Observability**

> "What happened internally while producing the result?"

### Remember

> **Don't test only the final answer. Test the agent's decisions and actions.**

Modern agent evaluation focuses on the full trajectory because agents can make multiple tool calls, modify state and adapt their behavior before producing the final result. ([Anthropic][4])

**Reference:** [Anthropic — Demystifying Evals for AI Agents](https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents)

---

# 10. AI Safety & Guardrails

Agents are more powerful than normal chatbots because they can **take actions**.

That also makes mistakes more dangerous.

### Common risks

* Prompt injection
* Data leakage
* Unauthorized access
* Incorrect tool usage
* Excessive permissions
* Hallucinated actions
* Sensitive data exposure
* Destructive actions

### Guardrails

```text
User
 ↓
Input Validation
 ↓
Agent
 ↓
Permission Check
 ↓
Tool
 ↓
Output Validation
 ↓
User
```

### Example

Suppose an agent has access to:

```text
Read Database
Create Jira Bug
Delete Database
```

Giving the agent unrestricted access is dangerous.

Instead:

```text
Read Database      → Allowed
Create Jira Bug    → Allowed
Delete Database    → Human Approval
```

### Important principles

**Least privilege**

* Give the agent only the permissions it needs.

**Human-in-the-loop**

* Require approval for high-risk actions.

**Input validation**

* Don't blindly trust user input.

**Output validation**

* Validate important results before using them.

**Audit logging**

* Record what the agent did.

**Data protection**

* Prevent sensitive information from being exposed.

### Remember

> **The more autonomy an agent has, the stronger the guardrails need to be.**

Current agent safety guidance emphasizes human control, security, transparency and privacy because autonomous tool use creates risks beyond those of a simple chatbot. ([Anthropic][5])

**Reference:** [Anthropic — Trustworthy Agents](https://www.anthropic.com/research/trustworthy-agents)

---

# 11. Context Engineering — Important Addition

This is worth learning because modern agent systems are moving beyond just **prompt engineering**.

### Prompt Engineering

Focuses mainly on:

> **How should I write the instructions?**

### Context Engineering

Focuses on:

> **What information should the model receive at this moment?**

Context may include:

```text
System instructions
+
Conversation history
+
User request
+
Tools
+
MCP
+
Retrieved documents
+
Memory
+
Previous tool results
```

The challenge is selecting the **right information**, not simply providing as much information as possible.

### Example

Bad:

```text
Send the entire 500-page company documentation.
```

Better:

```text
Retrieve only the sections relevant to the user's question.
```

### Remember

> **Good agents don't just have more context; they have the right context at the right time.**

Anthropic describes context engineering as the broader problem of curating the information available to an agent during inference, including tools, MCP, external data and message history. ([Anthropic][6])

**Reference:** [Anthropic — Effective Context Engineering](https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents)

---

# Final 80/20 Revision

If you want to quickly revise before an interview or implementation discussion, remember this:

| Topic                   | Remember This                                             |
| ----------------------- | --------------------------------------------------------- |
| **LLM**                 | Generates text/code based on learned patterns and context |
| **Prompt Engineering**  | Give clear instructions, context and expected output      |
| **Agent**               | LLM that can decide, use tools and take actions           |
| **Tool Calling**        | Agent requests external functions/APIs                    |
| **MCP**                 | Standard way for AI to connect with external capabilities |
| **RAG**                 | Retrieve external knowledge before generating an answer   |
| **Memory**              | Retain useful information across interactions             |
| **Context Engineering** | Give the agent the right information at the right time    |
| **Multi-Agent**         | Multiple specialized agents collaborate                   |
| **Evaluation**          | Check whether the agent actually succeeds                 |
| **Observability**       | Understand what happened during execution                 |
| **Guardrails**          | Control what the agent can access and do                  |

## Recommended Learning Flow

```text
LLM Basics
    ↓
Prompt Engineering
    ↓
Agent Fundamentals
    ↓
Tool Calling
    ↓
RAG
    ↓
Memory
    ↓
MCP
    ↓
Context Engineering
    ↓
Multi-Agent Systems
    ↓
Evaluation & Observability
    ↓
Safety & Guardrails
```

**For your QA/automation background**, the most important areas to spend hands-on time on are **Agents → Tool Calling → RAG → MCP → Evaluation/Observability → Guardrails**. Those concepts map particularly well to building AI-powered API/UI test analysis, test generation, failure analysis and automation assistants.

[1]: https://platform.claude.com/docs/en/build-with-claude/prompt-engineering/overview?trk=public_post_comment-text&utm_source=chatgpt.com "Prompt engineering overview - Claude Platform Docs"
[2]: https://resources.anthropic.com/building-effective-ai-agents?utm_source=chatgpt.com "Building Effective AI Agents"
[3]: https://blog.modelcontextprotocol.io/posts/2026-07-28/?utm_source=chatgpt.com "The 2026-07-28 Specification | Model Context Protocol Blog"
[4]: https://www.anthropic.com/engineering/demystifying-evals-for-ai-agents?utm_source=chatgpt.com "Demystifying evals for AI agents \ Anthropic"
[5]: https://www.anthropic.com/research/trustworthy-agents?utm_source=chatgpt.com "Trustworthy agents in practice \ Anthropic"
[6]: https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents?utm_source=chatgpt.com "Effective context engineering for AI agents \ Anthropic"

### **1. Walk me through the testing process you followed**

- Started with **requirement analysis** and clarifying acceptance criteria.
- Prepared test scenarios and **detailed test cases**.
- Set up QA environment and ensured required test data was available.
- Executed **functional testing** for new features.
- Logged defects with steps, severity, screenshots/logs.
- Worked with dev to reproduce issues and verify fixes.
- Ran **regression testing** after every build.
- Performed API validation via **Postman / Swagger**.
- Provided QA sign-off for staging and production deployment.
- Participated in **sprint reviews** and release verification.

### **2. Have you done testing in production? How was it done? What happened when a production defect was found?**

- Yes, we performed **controlled testing in production** after deployments.
- Used **feature toggles/limited rollout** to avoid customer impact.
- Verified critical flows like login, payments, API responses and logs.
- Tested using **non-business-impacting accounts** and dummy data where allowed.
- Monitored logs, dashboards and alerts for errors and latency.
- If a production defect was found, we raised **P0/P1 incident** immediately.
- Dev team created a hotfix patch and deployed to test + production.
- QA validated the fix first in staging, then in production.
- RCA (root cause analysis) was documented to prevent recurrence.
- Learnings were added back into regression or automation suites.

### **3. How did you create test data in functional testing and production testing?**

- For **functional testing**, we manually created the required test accounts in the QA environment.
- Prepared data combinations covering **positive, negative, and boundary** scenarios.
- Reused sample datasets from previous sprints when relevant.
- Cloned or duplicated existing records to simulate edge cases.
- Ensured each test had a **clean starting point** to avoid dependency issues.
- For **production testing**, used only pre-approved **dummy/whitelisted accounts**.
- Never modified or touched real customer data.
- Validated through read-only dashboards to avoid impacting transactions.
- Marked or tagged all production test records as **TEST** for easy tracking.
- Followed company data privacy and compliance standards at all times.

### **4. Did you write a Test Plan? What did it contain?**

- Yes, I contributed to and maintained the test plan for each release.
- It defined the **scope and objectives** of testing.
- Listed features to be tested and **out of scope** areas.
- Documented testing approaches — functional, regression, and integration.
- Specified **test environments**, tools, and required test data.
- Included **entry and exit criteria** for each test phase.
- Defined roles and responsibilities for QA, Dev, and BA.
- Captured **risk areas and mitigation strategies**.
- Included defect reporting rules, severity/priority guidelines.
- Outlined the deliverables and timelines for execution and sign-off.

### **5. How did you handle defects rejected by developers?**

- First, I **revalidated the defect** to ensure it was reproducible.
- Gathered additional evidence like logs, screenshots, and steps.
- Checked the requirement or acceptance criteria for clarity.
- Discussed the issue directly with the developer for alignment.
- If misunderstanding persisted, raised it in **defect triage** with QA + Dev + BA.
- If defect was valid, dev reopened it for fixing.
- If requirement was unclear, we updated the story/AC to avoid repeat confusion.
- If QA mistake, closed the bug with correct status and learning noted.
- Added the scenario to test cases or regression if relevant.
- Maintained healthy communication, avoiding conflict or blame.

### **6. Did you do functional and end-to-end testing? What’s the difference?**

- Yes, I performed both functional testing and end-to-end testing.
- **Functional testing** focuses on verifying a specific feature or module.
- It checks if the feature works as per the acceptance criteria.
- Usually limited to one screen, one API, or one user action.
- Validates input, output, UI behaviour, and error handling.
- **End-to-end testing** validates a complete user workflow from start to finish.
- Covers UI + API + DB + integrations between systems.
- Simulates a real end user journey across multiple modules.
- Example: Login → Search → Add to Cart → Payment → Confirmation.
- Ensures all connecting systems and services work together smoothly.

### **7. Did you continuously run tests in Jenkins? How did you configure it?**

- Yes, we integrated our automation suite with Jenkins for continuous testing.
- Created a Jenkins job linked to our automation repo (Git).
- Jenkins pulled the latest code whenever changes were pushed.
- Configured triggers for nightly builds and on merge to main branch.
- Installed required dependencies on the agent/node before execution.
- Tests ran automatically and generated HTML/Allure reports.
- Stored results and logs as Jenkins artifacts for debugging.
- Sent build status notifications to email/Slack for the team.
- Failed tests triggered investigation in the next standup.
- Helped maintain continuous feedback and quick defect detection.

### **8. How did you configure Jenkins to run your tests?**

- Created a **pipeline job** in Jenkins linked to the automation Git repository.
- Added a **Jenkinsfile** that defined steps like checkout, build, and execution.
- Specified test commands (Maven/Gradle/npm) inside the pipeline stages.
- Configured **environment variables** like URLs, credentials, browser choice.
- Used different agents/nodes depending on test type (API/UI/parallel).
- Set up triggers: manual, scheduled nightly, or automatic on code merge.
- Enabled storing of reports, screenshots, and logs as build artifacts.
- Integrated notifications via Slack/email for build success/failure.
- Allowed parameterized runs to test different environments.
- Ensured pipeline failure immediately alerts QA and dev teams.

### **9. How did you debug test automation failures or Jenkins job failures?**

- First reviewed the **Jenkins console logs** to see where it failed.
- Identified if the issue was test logic, data issue, or environment downtime.
- Re-ran the failed test case **locally** to reproduce the problem.
- Checked configuration files, URLs, and credentials used by tests.
- Looked at screenshots and logs generated by the automation framework.
- Validated whether recent code changes caused locator/API changes.
- Synced with developers if the issue pointed to an application bug.
- Cleared stale caches, workspaces, or dependency conflicts if needed.
- Updated retry logic/waits for flaky tests and re-ran the suite.
- Root-caused and fixed, then committed changes and triggered a fresh Jenkins run.

### **10. How did you perform code deployment in your project?**

- Code deployment was handled using our CI/CD pipeline (Jenkins/Azure DevOps).
- Developers merged changes into the main branch after review.
- The pipeline automatically built the artifact (Jar/Docker image).
- Deployed sequentially to **Dev → QA → Staging → Production** environments.
- QA validated the build in the QA environment before promotion.
- Used infrastructure tools like Kubernetes/EC2/Containers (based on project).
- Staging deployment required business/PO approval.
- Production deployment was done in a scheduled release window.
- QA performed post-deployment **sanity/smoke testing**.
- Any incident triggered rollback or hotfix based on impact.

### **11. How was the code release process in your team?**

- We followed a **sprint-based release process** aligned with Agile.
- Stories were developed, tested, and completed within the sprint.
- A **release candidate build** was created toward sprint end.
- QA fully tested it in QA/Staging environments.
- UAT/Business teams validated critical features.
- A release checklist was reviewed (test pass %, defects, dependencies).
- The PO/BA gave **final approval** to move to production.
- Production deployment was done during approved release windows.
- QA performed **sanity checks** after deployment.
- Post-release monitoring was done and hotfixes applied if required.

### **12. What Agile ceremonies happened in your team?**

- **Daily Standups** – Quick 15-min sync on progress, blockers, and plans.
- **Sprint Planning** – Estimating tasks and committing to sprint stories.
- **Backlog Refinement/Grooming** – Clarifying upcoming stories and refining AC.
- **Sprint Demo/Review** – Showing completed features to stakeholders.
- **Retrospective** – Discussing what went well, what to improve, and taking actions.
- **Release/Deployment discussion** if feature was ready to go live.
- **Defect triage meetings** when high number of bugs.
- Close collaboration with Dev, QA, BA, and PO throughout sprint.
- Used Jira/Confluence for tracking deliverables.
- Ensured team alignment and visibility at every stage of development.

### **13. How were sprint scope changes handled?**

- Any scope change was first raised by PO or stakeholder.
- Team assessed the **impact on testing, development effort, and timelines**.
- If small, the team absorbed it within the sprint capacity.
- If large, we **reprioritized** stories or moved items to next sprint.
- Updated estimates and task breakdown in Jira.
- Ensured acceptance criteria were clear before development started.
- Communicated the change and impact in stand-up.
- Added new test cases and updated regression plan based on change.
- Tracked unexpected work separately for transparency.
- Final decision always aligned with the Product Owner.

### **14. While building a test automation framework what things do you take care of?**

- Use a **clean folder and package structure** for easy maintenance.
- Follow **Page Object Model** or modular design to avoid duplication.
- Keep test data external (files/env variables), not hardcoded.
- Build reusable utilities like logging, wait handlers, API wrappers.
- Ensure tests are **stable and not flaky** with smart waits and retries.
- Make framework **configurable** for different environments and browsers.
- Add meaningful **logs, screenshots, and reporting** for debugging.
- Support **parallel execution** to reduce test run time.
- Integrate with CI tools like Jenkins for continuous runs.
- Keep framework scalable, readable, and easy for new team members to adopt.

---

### 1. You are given a string which might contain words, spaces. Return the length of last word of the string. If no words found return zero

```
public class Sample {
    public static int lengthOfLastWord(String s) {
        if (s == null || s.trim().isEmpty()) return 0;
        String trimmed = s.trim();
        int lastSpace = trimmed.lastIndexOf(" ");
        return trimmed.length() - lastSpace - 1;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));      // 5
        System.out.println(lengthOfLastWord("Java basics  "));    // 6
        System.out.println(lengthOfLastWord("     "));            // 0
    }
}

```

## 2. A Java method which takes array of string which may have multiple words. De duplicate the string and return only unique words.

```
import java.util.*;

public class Main {
    public static Set<String> uniqueWords(String[] sentences) {
        Set<String> result = new HashSet<>();
        for (String s : sentences) {
            if (s == null || s.trim().isEmpty()) continue;
            String[] words = s.trim().split("\\s+");
            result.addAll(Arrays.asList(words));
        }
        return result;
    }

    public static void main(String[] args) {
        String[] arr = {"hello world", "hello java", "world of java"};
        System.out.println(uniqueWords(arr));
    }
}

```

## 3. Rest assured

**GET**

```
public void testGET() {

    Response res = RestAssured
            .given()
            .when()
            .get("https://reqres.in/api/users?page=2");

    System.out.println(res.asString());
    Assert.assertEquals(res.getStatusCode(), 200);

    }
```

**POST**

```
public void testPOST() {

    String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

    Response res = RestAssured
            .given()
                .header("Content-Type", "application/json")
                .body(body)
            .when()
                .post("https://reqres.in/api/users");

    System.out.println(res.asString());
    Assert.assertEquals(res.getStatusCode(), 201);
}

```

**PUT**

```
public void testPUT() {

    String body = "{ \"name\": \"neo\", \"job\": \"chosen one\" }";

    Response res = RestAssured
            .given()
                .header("Content-Type", "application/json")
                .body(body)
            .when()
                .put("https://reqres.in/api/users/2");

    System.out.println(res.asString());
    Assert.assertEquals(res.getStatusCode(), 200);
}

```

**PATCH**

```
public void testPATCH() {

    String body = "{ \"job\": \"zion leader\" }";

    Response res = RestAssured
            .given()
                .header("Content-Type", "application/json")
                .body(body)
            .when()
                .patch("https://reqres.in/api/users/2");

    System.out.println(res.asString());
    Assert.assertEquals(res.getStatusCode(), 200);
}


```

**DELETE**

```
public void testDELETE() {

    Response res = RestAssured
            .given()
            .when()
            .delete("https://reqres.in/api/users/2");

    System.out.println(res.getStatusCode());
    Assert.assertEquals(res.getStatusCode(), 204);
}

```

# Rest Assured — Complete Notes

**Difficulty tags**, next to each topic below: 🟢 Beginner · 🟡 Intermediate · 🔴 Advanced.

---

## Table of Contents

**Part 1 — Fundamentals**
1. 🟢 [REST & API Basics](#1-rest--api-basics)
2. 🟢 [What is Rest Assured?](#2-what-is-rest-assured)
3. 🟢 [Why Use It?](#3-why-use-it)
4. 🟢 [Key Features](#4-key-features)
5. 🟢 [DSL Syntax & Request Flow](#5-dsl-syntax--request-flow)
6. 🟢 [RequestSpecification](#6-requestspecification)
7. 🟢 [HTTP Methods](#7-http-methods)
8. 🟢 [Request Parameters](#8-request-parameters)
9. 🟢 [Request Headers](#9-request-headers)
10. 🟢 [Request Body](#10-request-body)

**Part 2 — Requests & Responses**

11. 🟢 [The Response Object](#11-the-response-object)
12. 🟡 [JSONPath](#12-jsonpath)
13. 🟢 [Advantages](#13-advantages)
14. 🟢 [Disadvantages](#14-disadvantages)
15. 🟢 [Mental Model](#15-mental-model)
16. 🟢 [Quick Revision (Parts 1 & 2)](#16-quick-revision)

**Part 3 — Response Validation & Extraction**

17. 🟢 [Project Setup](#17-project-setup)
18. 🟢 [Project Structure](#18-project-structure)
19. 🟢 [Sample CRUD Test Class — Beginner Version (Plain JSON, No POJO)](#19-sample-crud-test-class--beginner-version-plain-json-no-pojo)
20. 🟡 [Sample CRUD Test Class — Leveling Up with a POJO](#20-sample-crud-test-class--leveling-up-with-a-pojo)
21. 🟢 [Running the Tests](#21-running-the-tests)
22. 🟡 [POJOs Used Below](#22-pojos-used-below)
23. 🟢 [Status Code & Status Line Validation](#23-status-code--status-line-validation)
24. 🟢 [Response Header Validation](#24-response-header-validation)
25. 🟢 [Hamcrest Matchers — Assertion Building Blocks](#25-hamcrest-matchers--assertion-building-blocks)
26. 🟢 [Response Body Validation](#26-response-body-validation)
27. 🟡 [JSONPath — Nested Objects & Arrays](#27-jsonpath--nested-objects--arrays)
28. 🟡 [Extracting Data from Arrays](#28-extracting-data-from-arrays)
29. 🟢 [Combining Multiple Assertions](#29-combining-multiple-assertions)
30. 🟡 [Validation vs Extraction, `extract()`](#30-validation-vs-extraction-extract)
31. 🟡 [Extracting Headers & Cookies](#31-extracting-headers--cookies)
32. 🟢 [Response Time & Content-Type Validation](#32-response-time--content-type-validation)
33. 🟢 [Response as String & Pretty Print](#33-response-as-string--pretty-print)
34. 🟡 [Collection Size / Contents / Negative Validation](#34-collection-size--contents--negative-validation)
35. 🟡 [Complete Validation Example](#35-complete-validation-example)

**Part 4 — Advanced Rest Assured**

36. 🔴 [Authentication (Basic, Preemptive, Bearer/OAuth2)](#36-authentication-basic-preemptive-beareroauth2)
37. 🟡 [Serialization (Object → JSON)](#37-serialization-object--json)
38. 🟡 [Deserialization (JSON → Object)](#38-deserialization-json--object)
39. 🟡 [Deserializing JSON Arrays into `List<T>`](#39-deserializing-json-arrays-into-listt)
40. 🔴 [Advanced JSONPath — Filtering](#40-advanced-jsonpath--filtering)
41. 🟢 [PUT & DELETE Requests](#41-put--delete-requests)
42. 🟡 [POST → Serialize → Send → Deserialize (Full Round Trip)](#42-post--serialize--send--deserialize-full-round-trip)
43. 🟡 [Full Runnable Test Class](#43-full-runnable-test-class)

**Part 5 — EventHub: A Second, Real API**

44. 🟢 [EventHub — Why a Second, Real API](#44-eventhub--why-a-second-real-api)
45. 🟡 [Finding the API: Swagger Docs, and Why You Verify Anyway](#45-finding-the-api-swagger-docs-and-why-you-verify-anyway)
46. 🟢 [EventHub Project Structure & POJOs](#46-eventhub-project-structure--pojos)
47. 🟡 [Authentication Tests](#47-authentication-tests)
48. 🟡 [Events CRUD Tests](#48-events-crud-tests)
49. 🔴 [Booking Tests — Seat Counts as a Side Effect](#49-booking-tests--seat-counts-as-a-side-effect)
50. 🟢 [Health & Config Tests](#50-health--config-tests)
51. 🟢 [Running the EventHub Suite](#51-running-the-eventhub-suite)
52. 🟡 [Real API vs Documented API — What Differed](#52-real-api-vs-documented-api--what-differed)

**Appendix**

- [Glossary](#glossary)
- [Learning Roadmap](#learning-roadmap)

---

## 1. 🟢 REST & API Basics

Before Rest Assured makes sense, a few foundational terms:

- **API** (Application Programming Interface) — a way for one program to ask another program for
  data or actions, instead of a human clicking through a website.
- **Client / Server** — the *client* (your test code) sends a **request**; the *server* (the API)
  sends back a **response**. That's the entire interaction, every time:

```text
Client                          Server
  │  ── request (GET /posts/1) ──▶  │
  │                                 │
  │  ◀── response (200 + JSON) ───  │
```

- **REST** (REpresentational State Transfer) — a common *style* of API built on plain HTTP:
  - every piece of data is a **resource**, identified by a URL (e.g. `/posts/1` = post #1)
  - the **HTTP method** says what to do to it (§7 covers this properly)
  - data is usually sent/received as **JSON**
- **Endpoint** — one specific URL an API exposes, e.g. `https://jsonplaceholder.typicode.com/posts/1`.
- **JSON** (JavaScript Object Notation) — the text format almost all REST APIs use: key–value
  pairs, objects `{ }`, and arrays `[ ]`. A real example (this is what `/posts/1` actually returns):

```json
{
  "userId": 1,
  "id": 1,
  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
  "body": "quia et suscipit suscipit recusandae consequuntur expedita et cum..."
}
```

- **Status code** — a 3-digit number telling you what happened: `2xx` = success, `4xx` = you (the
  client) did something wrong, `5xx` = the server broke. §23 covers this in depth.

**The one API used throughout this entire file:** `https://jsonplaceholder.typicode.com` — a free,
public, fake REST API (posts, users, comments) built exactly for practicing this kind of testing.
Every example from here on hits this same API, so what you learn on one topic transfers directly
to the next.

---

## 2. 🟢 What is Rest Assured?

Rest Assured is an **open-source Java library** for testing and validating REST APIs — the
back-end endpoints that return data (usually JSON) instead of a rendered web page. It's a
**headless client**: it sends real HTTP requests and reads real HTTP responses entirely in code,
with no browser or GUI involved. Think of it as the code equivalent of a tool like Postman, except
it lives inside your Java test suite so API checks run automatically alongside the rest of your
automated tests.

> In one line: **Rest Assured automates REST API testing using Java.**

It supports every common HTTP method — `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `OPTIONS`, `HEAD`
— and can validate every part of a response: status code, headers, cookies, and body content, in
both JSON and XML.

---

## 3. 🟢 Why Use It?

Compared to sending requests with a raw HTTP client and hand-parsing the response yourself, Rest
Assured removes most of the boilerplate:

- **Open source** — free, active community
- **Simplifies testing** — easier than raw HTTP clients
- **Java-native** — fits Java-based test automation
- **Built-in validation** for API responses
- **Multi-method support** (GET/POST/PUT/DELETE/etc.)

---

## 4. 🟢 Key Features

A quick reference of what Rest Assured gives you out of the box:

| Feature | Description |
|---|---|
| REST testing | Core purpose |
| JSON/XML support | Test both formats |
| JSONPath / XMLPath | Locate specific data, e.g. `$['store']['book'][3]['title']` |
| Request validation | Params, headers, cookies, content |
| Response validation | Validate HTTP responses |
| Spec reuse | Reusable request/response specs |
| File upload | Multipart upload support |

---

## 5. 🟢 DSL Syntax & Request Flow

**DSL** stands for **Domain-Specific Language** — a small, focused vocabulary built for one job
(here: describing HTTP requests and responses) instead of general-purpose code. Rest Assured's DSL
reads almost like an English sentence, in the same `given/when/then` style used by BDD
(Behavior-Driven Development) tools like Cucumber:

```java
given()
    .when()
    .then();
```

- **given()** → Define request details (what to send)
- **when()** → Perform the request (action to take)
- **then()** → Validate the response (what to check)

**Overall flow:**

```text
Create Request
      ↓
Configure Request
      ↓
Send HTTP Request
      ↓
Receive Response
      ↓
Validate Response
```

---

## 6. 🟢 RequestSpecification

Every Rest Assured test starts by describing the request you want to send — that description is
called a `RequestSpecification`, and you build it up inside `given()`. It can contain:

- Base URI
- Headers
- Parameters
- Cookies
- Authentication
- Request body
- Content type

Example:

```java
given()
    .header("Accept", "application/json")
    .queryParam("page", 1)
```

---

## 7. 🟢 HTTP Methods

If you're new to REST APIs: an API exposes URLs called **endpoints**, and you interact with the
same endpoint differently depending on which HTTP method you send — the method tells the server
*what you intend to do* to that resource.

| Method  | Purpose                       |
| ------- | ------------------------------ |
| GET     | Retrieve data                 |
| POST    | Create data                   |
| PUT     | Update/replace data           |
| PATCH   | Partially update data         |
| DELETE  | Delete data                   |
| HEAD    | Retrieve headers              |
| OPTIONS | Retrieve supported operations |

Basic examples (just showing the syntax — these don't specify a `baseUri` yet, so they won't run
standalone; fully runnable versions with a real host start in Part 3):

```java
given().when().get("/users");
given().when().post("/users");
given().when().put("/users/1");
given().when().patch("/users/1");
given().when().delete("/users/1");
```

**⚠️ Common mistakes**
- Using `PUT` when you meant `PATCH` (or vice versa) — `PUT` conventionally *replaces* the whole
  resource, `PATCH` updates part of it. Many APIs (including jsonplaceholder) don't enforce the
  difference, so a wrong choice can pass silently.
- Forgetting a request body on `POST`/`PUT`/`PATCH` — the method alone doesn't send any data (§10).

---

## 8. 🟢 Request Parameters

**Query Parameters** — appended to the URL after a `?`, typically used for filtering, sorting, or
pagination:

```text
/users?page=2
```
```java
.queryParam("page", 2)
```

**Path Parameters** — embedded directly in the URL path, typically used to identify *which*
resource you mean (e.g. which user):

```text
/users/123
```
```java
.pathParam("id", 123)
```

**Form Parameters** — used when sending form-encoded data (like an HTML form submission), as
opposed to a JSON body.

**⚠️ Common mistakes**
- Mixing up query params and path params — `.queryParam()` adds `?key=value` to the URL,
  `.pathParam()` fills in a `{placeholder}` inside the URL path itself; using the wrong one either
  hits the wrong URL or silently does nothing.
- Forgetting to actually reference a `.pathParam("id", 123)` with a `{id}` placeholder in the URL
  you call, e.g. `.get("/users/{id}")` — without the placeholder, the parameter is never used.

---

## 9. 🟢 Request Headers

Headers are extra metadata sent alongside a request — they describe the request without being
part of its actual content. Common examples:

```text
Content-Type    — what format the request body is in (e.g. application/json)
Accept          — what format you want the response in
Authorization   — credentials/tokens for authenticating the request
```

Rest Assured lets you add headers individually or several at once:

```java
.header("Accept", "application/json")
```

**⚠️ Common mistakes**
- Confusing `Content-Type` (what *you're sending*) with `Accept` (what *you want back*) — setting
  the wrong one can make a perfectly good request get misread or rejected.
- Setting `.contentType(ContentType.JSON)` but then sending a body that isn't valid JSON — the
  header is a promise about the body's format, not a check on it.

---

## 10. 🟢 Request Body

For methods such as POST, PUT, and PATCH, you usually need to send data along with the request —
that data is the **request body**. Most REST APIs speak **JSON** (JavaScript Object Notation): a
lightweight text format for structured data, written as key–value pairs, optionally nested inside
objects (`{ }`) and arrays (`[ ]`).

Example request body:

```json
{
  "name": "John",
  "email": "john@test.com"
}
```

**⚠️ Common mistakes**
- Sending a body on a `GET` — most servers ignore it; if you need to send data, that's what
  query params or a different method are for.
- Forgetting `.contentType(ContentType.JSON)` alongside `.body(...)` — some servers reject (or
  misinterpret) a JSON body sent without the matching content type.

---

## 11. 🟢 The Response Object

After sending a request, Rest Assured hands you back a `Response` object containing:

- Status code
- Status line
- Headers
- Cookies
- Response body
- Response time

Example:

```java
Response response =
    given()
    .when()
    .get("/users");
```

**Reading the response:**

```text
Response
   ├── Status
   ├── Headers
   ├── Cookies
   └── Body
```

The response body can be read as a plain String, or parsed as structured JSON/XML — which is
exactly what JSONPath (next section) is for.

---

## 12. 🟡 JSONPath

For JSON responses, Rest Assured provides **JSONPath** — a query language for picking a specific
value out of a JSON document by describing *where* it lives, instead of manually digging through
nested maps and lists yourself.

Given this JSON:

```json
{
  "id": 101,
  "name": "John"
}
```

The *formal* JSONPath syntax to reach `id` is `$.id` or `$['id']` (the `$` means "the whole
document"). Rest Assured also accepts a shorter, simplified path when you use it inside `.body()`
or `.path()` — you'll see both styles throughout this file:

| You want | Simplified path (used in `.body("...")`) | Formal JSONPath |
|---|---|---|
| Top-level field | `"id"` | `$.id` |
| Nested field | `"address.city"` | `$.address.city` |
| Array element by index | `"[0].userId"` | `$[0].userId` |
| Every value of a field across an array | `"title"` (with `getList`) | `$..title` |

JSONPath becomes especially useful once JSON gets deeply nested or contains arrays — e.g. reaching
a book's title inside a store's inventory: `$['store']['book'][3]['title']`. §27 and §28 put this
into practice against a real API.

**⚠️ Common mistakes**
- Typing a field name wrong (`"titel"` instead of `"title"`) — the assertion just fails (or
  returns `null`), with no compiler to catch it; double-check the spelling against the real
  response body (§33 shows how to print it).
- Forgetting the `[n]` index when the response body itself is an array, not an object — `"userId"`
  alone works on `/posts/1` (an object) but needs `"[0].userId"` on `/posts` (an array).

---

## 13. 🟢 Advantages

- Open source
- Easy setup
- Rich, readable syntax
- Built-in assertions
- Less code than raw HTTP clients
- Easy JSON/XML parsing
- Headers/cookies support
- Response-time validation
- Logging support
- Authentication support
- JSONPath/XMLPath navigation
- JSON Schema validation
- Multipart data support
- Integrates with other Java tools (JUnit, TestNG, etc.)

---

## 14. 🟢 Disadvantages

- **No SOAP support** — REST only
- **Requires Java knowledge**
- **No built-in reporting**

---

## 15. 🟢 Mental Model

```text
             REST ASSURED
                  │
        ┌─────────┴─────────┐
        ↓                   ↓
     REQUEST              RESPONSE
   GET/POST/etc.        Status Code
   Headers              Headers
   Parameters           Body
   Cookies              JSON/XML
   Body                 Validation
        └─────────┬─────────┘
                  ↓
             TEST RESULT
```

---

## 16. 🟢 Quick Revision

```text
RestAssured
     ↓
given()
     ↓
RequestSpecification
     ↓
HTTP Method
     ↓
Request
     ↓
Response
     ↓
JSONPath / Response Data
```

**Key topics to remember:**
RequestSpecification · HTTP methods · Query parameters · Path parameters · Headers · Request body · Response object · Response data · JSONPath

**Core takeaway:**

> Rest Assured lets you **build a request, send it to the API, receive the response, and access/validate the response data** — all in readable, BDD-style Java code.

If any of this still feels abstract, that's expected — it'll click once you run real code, starting
right now in Part 3.

---

## 17. 🟢 Project Setup

Before we start writing API tests, we need to prepare our Maven project.

For these examples, we need **three dependencies**:

| Dependency | Purpose |
| --- | --- |
| `rest-assured` | Used to send API requests and validate API responses |
| `testng` | Used to create and run test cases |
| `jackson-databind` | Used later (§20) to convert Java objects to JSON and JSON back to Java objects |

### Maven Dependencies

Add the following dependencies to `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.5.6</version>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.11.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.18.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Imports

We will also use these static imports in our test classes:

```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
```

These allow us to write:

```java
given()
when()
then()
```

instead of using the complete Rest Assured class name.

The `Matchers` import provides assertions such as:

```java
equalTo()
containsString()
not()
```

(That's Hamcrest — bundled transitively with `rest-assured`, so no separate dependency for it above.)

**⚠️ Common mistakes**
- Adding `jackson-databind` but forgetting it's only needed once a POJO is involved (§20) — plain
  JSONPath tests (§19) run fine without it.
- Missing `<scope>test</scope>` on `testng`/`jackson-databind` — harmless for a learning project,
  but in a real one it bloats the shipped production jar with test-only dependencies.

---

## 18. 🟢 Project Structure

We only need two folders for these examples:

```text
src/test/java
├── models
│   └── Post.java
│
└── tests
    ├── CrudTestsBeginner.java
    └── CrudTests.java
```

* **`tests`** → Contains our API test classes.
* **`models`** → Contains POJO classes used to represent API data.

```bash
mkdir -p src/test/java/models src/test/java/tests
```

For now, `Post.java` will be introduced in **§20** when we learn POJOs.

---

## 19. 🟢 Sample CRUD Test Class — Beginner Version (Plain JSON, No POJO)

Now we can write our first complete CRUD example.

For this example, we will **not use POJOs**. Instead, we will provide the request body directly as a JSON string.

This helps us first understand the basic API testing flow before introducing Java objects.

We will use the public `JSONPlaceholder` API for practice.

### What is CRUD?

CRUD represents the four basic operations we commonly perform on API resources:

| Operation | HTTP Method | Purpose |
| --- | --- | --- |
| Create | POST | Create new data |
| Read | GET | Retrieve data |
| Update | PUT | Update existing data |
| Delete | DELETE | Delete data |

The flow is:

```text
CREATE  → POST
READ    → GET
UPDATE  → PUT
DELETE  → DELETE
```

`src/test/java/tests/CrudTestsBeginner.java`:

```java
package tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CrudTestsBeginner {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    // ---------- CREATE (POST) ----------
    @Test
    public void createPost() {
        String requestBody =
            "{ \"userId\": 10, \"title\": \"Learning Rest Assured CRUD\", " +
            "\"body\": \"This post is created via a POST request.\" }";

        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo("Learning Rest Assured CRUD"))
            .body("userId", equalTo(10));
    }

    // ---------- READ (GET) ----------
    @Test
    public void readPost() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", equalTo(1))
            .body("title", not(emptyString()));
    }

    // ---------- UPDATE (PUT) ----------
    @Test
    public void updatePost() {
        String requestBody =
            "{ \"userId\": 1, \"title\": \"Updated Title via PUT\", " +
            "\"body\": \"Updated body content.\" }";

        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .put("/posts/1")
        .then()
            .statusCode(200)
            .body("title", equalTo("Updated Title via PUT"));
    }

    // ---------- DELETE ----------
    @Test
    public void deletePost() {
        given()
            .baseUri(BASE_URI)
        .when()
            .delete("/posts/1")
        .then()
            .statusCode(200); // jsonplaceholder returns 200 with an empty {} body
    }
}
```

### Understanding the Flow

Don't try to memorize every line yet.

The important Rest Assured flow is:

```text
given()
   ↓
Prepare the request
   ↓
when()
   ↓
Send the request
   ↓
then()
   ↓
Validate the response
```

For example, the POST test does this:

```text
POST /posts
     ↓
Send JSON request body
     ↓
Receive response
     ↓
Verify status code = 201
     ↓
Verify response data
```

The four tests demonstrate how different HTTP methods are used:

* **POST** → sends data to create a resource.
* **GET** → retrieves a resource.
* **PUT** → sends data to update a resource.
* **DELETE** → removes a resource.

### Limitation of Using JSON Strings

The request body is currently written manually as a Java `String`.

For example:

```java
String requestBody = "{ ... }";
```

This works well for learning, but larger request bodies can become difficult to read and maintain.

It is also easy to make spelling mistakes in JSON field names.

This is where **POJOs** become useful.

**⚠️ Common mistakes**
- Mismatched braces/quotes in the hand-written JSON string — nothing catches this until the test
  fails at runtime with a confusing error, since Java sees it as valid text either way.
- Forgetting `.contentType(ContentType.JSON)` — without it, the server may not parse the string
  body as JSON at all.

---

## 20. 🟡 Sample CRUD Test Class — Leveling Up with a POJO

In §19, we created the request body directly as a JSON string.

Now we'll write the same CRUD example using a **POJO**.

### What is a POJO?

POJO stands for **Plain Old Java Object**.

In simple terms, a POJO is a normal Java class that represents the data used by the API (§22 has the fuller explanation, with more POJO examples used later in this file).

For our example, a post contains:

```text
userId
id
title
body
```

So we create a `Post` Java class containing these fields.

Instead of manually creating JSON:

```text
Java String
    ↓
JSON
    ↓
API
```

we can work with a Java object:

```text
Java Post Object
    ↓
Jackson
    ↓
JSON
    ↓
API
```

### Step 1 — Create the `Post` POJO

Create:

```text
src/test/java/models/Post.java
```

```java
package models;

public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
```

This class represents the `Post` data used by the API.

### Step 2 — Use the POJO in the CRUD Tests

The CRUD operations are still exactly the same:

```text
POST   → Create
GET    → Read
PUT    → Update
DELETE → Delete
```

The main difference is how we handle the request data.

**§19:**

```text
JSON String
    ↓
API
```

**§20:**

```text
Java Object
    ↓
JSON
    ↓
API
```

The complete test class is:

`src/test/java/tests/CrudTests.java`:

```java
package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Post;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class CrudTests {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    // ---------- CREATE (POST) ----------
    @Test
    public void createPost() {
        Post newPost = new Post();
        newPost.setUserId(10);
        newPost.setTitle("Learning Rest Assured CRUD");
        newPost.setBody("This post is created via a POST request.");

        Post created =
            given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(newPost)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .body("title", equalTo(newPost.getTitle()))
                .extract()
                .as(Post.class);

        assertEquals(created.getTitle(), newPost.getTitle());
        System.out.println("Created post id: " + created.getId());
    }

    // ---------- READ (GET) ----------
    @Test
    public void readPost() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", equalTo(1))
            .body("title", not(emptyString()));
    }

    // ---------- UPDATE (PUT) ----------
    @Test
    public void updatePost() {
        Post updatedPost = new Post();
        updatedPost.setUserId(1);
        updatedPost.setTitle("Updated Title via PUT");
        updatedPost.setBody("Updated body content.");

        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body(updatedPost)
        .when()
            .put("/posts/1")
        .then()
            .statusCode(200)
            .body("title", equalTo("Updated Title via PUT"));
    }

    // ---------- DELETE ----------
    @Test
    public void deletePost() {
        given()
            .baseUri(BASE_URI)
        .when()
            .delete("/posts/1")
        .then()
            .statusCode(200); // jsonplaceholder returns 200 with an empty {} body
    }
}
```

### What Changed?

The API and CRUD operations haven't changed.

Only the way we represent the data has changed.

**§19 — JSON String:**

```java
String requestBody = "...";

.body(requestBody)
```

We manually create the JSON.

**§20 — POJO:**

```java
Post newPost = new Post();

newPost.setUserId(10);
newPost.setTitle("Learning Rest Assured");
newPost.setBody("...");
```

Then we pass the object:

```java
.body(newPost)
```

Jackson converts the Java object into JSON automatically.

### POJO Can Also Handle the Response

POJOs are useful not only for **sending** data but also for **receiving** data.

In the POST example, we have:

```java
.extract()
.as(Post.class);
```

The API returns JSON, and Rest Assured converts that JSON into a `Post` object.

```text
API Response
     ↓
    JSON
     ↓
Jackson
     ↓
Post Object
```

We can then access the response using normal Java methods:

```java
created.getTitle()
created.getId()
created.getUserId()
created.getBody()
```

This conversion from **JSON → Java Object** is called **deserialization**.

**Two terms to remember:**

| Term | Direction | Meaning |
| --- | --- | --- |
| Serialization | Java → JSON | Converts a Java object into JSON (§37 has the full walkthrough) |
| Deserialization | JSON → Java | Converts JSON into a Java object (§38 has the full walkthrough) |

**Overall flow:**

```text
             REQUEST
                │
        Java Post Object
                │
          Serialization
                ↓
              JSON
                │
               API
                │
                ↓
              JSON
                │
        Deserialization
                ↓
        Java Post Object
                │
             RESPONSE
```

**Key takeaway:**

**§19** teaches the basic CRUD flow using JSON directly.

**§20** introduces POJOs so request and response data can be represented as reusable Java objects.

Remember just these three concepts for now:

> **POJO** → Java representation of API data

> **Serialization** → Java → JSON

> **Deserialization** → JSON → Java

**⚠️ Common mistakes**
- Missing getters/setters on a POJO field — Jackson relies on them to map JSON keys to Java
  fields; a field with no getter/setter is silently skipped.
- A POJO field name that doesn't match the JSON key (`postTitle` vs `title`) — no error, the field
  just stays at its default value (`null`/`0`).
- Forgetting the Jackson dependency (§17) before using `.body(pojo)` or `.extract().as(Class)`.

---

## 21. 🟢 Running the Tests

**Option A — Maven CLI:**

```bash
mvn test
```

This picks up any class ending in `*Test`/`*Tests` (or annotated with `@Test`) via the Maven
Surefire plugin's TestNG support — no extra config needed for a quick run, but adding a
`testng.xml` (below) gives explicit control over which suites/tests run.

**Option B — `testng.xml` suite file** (this repo has one at the project root, covering every test
class described in this file — not just §19/§20's):

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="CrudSuite">
    <test name="CrudTestsBeginner">
        <classes>
            <class name="tests.CrudTestsBeginner"/>
        </classes>
    </test>
    <test name="CrudTests">
        <classes>
            <class name="tests.CrudTests"/>
        </classes>
    </test>
</suite>
```

Run it with:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

That property name is easy to get wrong — it's `surefire.suiteXmlFiles` (plural, with the
`surefire.` prefix), **not** `suiteXmlFile`. Passing the wrong name isn't an error; Maven silently
ignores it and Surefire falls back to its default class discovery (`**/*Test.java`, `**/*Tests.java`,
etc.), so *every* test class on the classpath runs instead of just the ones in your suite file.

**Where the report goes:** by default, Maven Surefire runs TestNG *embedded* and redirects its
report (`index.html`, `emailable-report.html`, `testng-results.xml`) into
`target/surefire-reports/` — there's no `test-output/` folder, because that's only where TestNG
writes when run standalone (outside Maven), e.g. by some IDEs' native TestNG runners. This
project's `pom.xml` configures Surefire's `reportsDirectory` to point at `test-output/` at the
project root instead, so the report ends up in the same place either way — open
`test-output/index.html` after any `mvn test` run.

**Option C — IDE:** right-click either `CrudTestsBeginner.java` or `CrudTests.java` → **Run** (IntelliJ
auto-detects the TestNG annotations once the `testng` dependency in `pom.xml` is resolved).

**Quick sanity check:** all four tests in each class should pass independently — `jsonplaceholder`
is a mock API, so `createPost` doesn't persist a real row, and `deletePost` doesn't actually remove
`/posts/1` for anyone else. That's what makes it safe for practicing full CRUD flows repeatedly,
in either version.

**Every code example in this file exists as a real, runnable class** under `src/test/java/`, one
file per topic area, plus the four POJOs from §22:

```text
src/test/java
├── models/           Post, User, Address, Geo, Company
└── tests/
    ├── CrudTestsBeginner.java              §19
    ├── CrudTests.java                      §20
    ├── ValidationTests.java                §23–§26, §29, §35
    ├── JsonPathAndExtractionTests.java      §27, §28, §30–§33, §40
    ├── NegativeTests.java                  §34
    ├── SerializationDeserializationTests.java  §37–§39, §42
    ├── PutDeleteTests.java                 §41
    └── AuthenticationSyntaxReference.java  §36 (mostly disabled — see the note in that section)
```

`mvn test -Dsurefire.suiteXmlFiles=testng.xml` runs all of them (38 tests; Basic/Preemptive auth
stay `enabled = false` since jsonplaceholder has no protected endpoint for them, but the
Bearer/OAuth2 login test is live against a real API). `mvn test` with no suite file runs the same
set via Surefire's default discovery.

---

## 22. 🟡 POJOs Used Below

A **POJO (Plain Old Java Object)** is a simple Java class used to represent API data.

We already introduced `Post` in §20. The following POJOs are used in later examples:

| POJO | Represents |
| --- | --- |
| `Post` | `/posts/{id}` |
| `User` | `/users/{id}` |
| `Address` | Nested inside `User` |
| `Geo` | Nested inside `Address` |
| `Company` | Nested inside `User` |

The `User` structure is:

```text
User
├── Basic details
├── Address
│   └── Geo
└── Company
```

### `Post.java`

```java
// Post.java  — maps https://jsonplaceholder.typicode.com/posts/{id}
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    // getters & setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
```

### `Geo.java`

```java
// Geo.java
public class Geo {
    private String lat;
    private String lng;
    public String getLat() { return lat; }
    public void setLat(String lat) { this.lat = lat; }
    public String getLng() { return lng; }
    public void setLng(String lng) { this.lng = lng; }
}
```

### `Address.java`

```java
// Address.java  — nested inside User, mirrors ToolsQA's user.address.city example
public class Address {
    private String street, suite, city, zipcode;
    private Geo geo;
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getSuite() { return suite; }
    public void setSuite(String suite) { this.suite = suite; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    public Geo getGeo() { return geo; }
    public void setGeo(Geo geo) { this.geo = geo; }
}
```

### `Company.java`

```java
// Company.java
public class Company {
    private String name, catchPhrase, bs;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCatchPhrase() { return catchPhrase; }
    public void setCatchPhrase(String catchPhrase) { this.catchPhrase = catchPhrase; }
    public String getBs() { return bs; }
    public void setBs(String bs) { this.bs = bs; }
}
```

### `User.java`

```java
// User.java — maps https://jsonplaceholder.typicode.com/users/{id}
public class User {
    private int id;
    private String name, username, email, phone, website;
    private Address address;
    private Company company;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}
```

**Key takeaway:** These POJOs allow us to represent simple and nested JSON responses as Java objects.

---

## 23. 🟢 Status Code & Status Line Validation

Every HTTP response starts with a **status code** — a 3-digit number the server uses to say what
happened (`200` = OK, `201` = Created, `404` = Not Found, `500` = Server Error, and so on). The
**status line** is the full first line of the raw response, e.g. `HTTP/1.1 200 OK`. Both are
checked inside `.then()`, which is where every validation in this file happens:

```java
@Test
public void statusCodeValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .statusCode(200);
}

@Test
public void statusLineValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .statusLine(containsString("200"));
}
```

`containsString("200")` is a **Hamcrest matcher** — a readable way of expressing "the status line
should contain the text 200" instead of a raw string comparison. §25 covers matchers properly;
for now, read `containsString(x)` as "must contain x somewhere".

**⚠️ Common mistakes**
- Asserting `statusCode(200)` on every request out of habit, even ones that should legitimately
  fail — see §34's negative-test example for what a "wrong" status code should actually be.
- Confusing status *code* (`200`, an `int`) with status *line* (`"HTTP/1.1 200 OK"`, a `String`) —
  `.statusCode(200)` and `.statusLine(containsString("200"))` check different things.

---

## 24. 🟢 Response Header Validation

Just like you can send headers on a request (§9), the server sends headers back on the response —
and you can assert on them the same way you assert on status codes:

```java
@Test
public void headerValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .header("Content-Type", containsString("application/json"));
}

@Test
public void multipleHeaderValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .statusCode(200)
        .header("Content-Type", containsString("application/json"))
        .header("Cache-Control", containsString("max-age"));
}
```

Chain as many `.header(...)` calls as you need — each one checks a different header on the same
response.

**⚠️ Common mistakes**
- Header names are case-*insensitive* over HTTP, but pick one spelling and stick with it in your
  tests for readability.
- Asserting on a header's exact value (`equalTo(...)`) when the real value can vary (timestamps,
  cache ages) — use `containsString(...)` for anything that isn't a fixed constant.

---

## 25. 🟢 Hamcrest Matchers — Assertion Building Blocks

You've already seen `containsString(...)` and `equalTo(...)` used above — both are **Hamcrest
matchers**. Hamcrest is a small assertion library (bundled transitively with `rest-assured`, see
§17) that lets you describe *what a value should look like* in plain, readable English instead of
writing raw comparison logic yourself. Every `.body(path, matcher)` or `.header(name, matcher)` call
in this file takes a matcher as its second argument.

The matchers you'll run into throughout this file:

| Matcher | Meaning |
|---|---|
| `equalTo(x)` | value must equal `x` exactly |
| `not(matcher)` | value must **not** satisfy `matcher` |
| `emptyString()` | value must be `""` (used with `not(...)` to mean "must not be blank") |
| `containsString(x)` | value (a string) must contain `x` somewhere |
| `greaterThan(x)` / `lessThan(x)` | numeric comparison |
| `hasItem(x)` | a collection must contain `x` somewhere |
| `hasItems(x, y, ...)` | a collection must contain all of `x, y, ...` |
| `hasSize(n)` | a collection must have exactly `n` elements |
| `everyItem(matcher)` | every element in a collection must satisfy `matcher` |

```java
@Test
public void hamcrestMatchersDemo() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .body("userId", equalTo(1))                     // equalTo()
        .body("title", not(emptyString()))               // not()
        .body("id", greaterThan(0))                       // greaterThan()
        .body("id", lessThan(101));                        // lessThan()

    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts")
    .then()
        .body("userId", hasItem(1))                        // hasItem()
        .body("userId", hasItems(1, 2, 3));                 // hasItems()
}
```

**⚠️ Common mistakes**
- Using `equalTo("1")` (a String) when the JSON value is a number — type mismatches fail even when
  the value "looks" right.
- Reaching for `hasItem(x)` (collection contains `x`) when you meant `equalTo(x)` (single value
  equals `x`) — using the collection matcher on a single value throws a `ClassCastException`.

---

## 26. 🟢 Response Body Validation

`.body(jsonPath, matcher)` is how you assert on values inside the JSON response body — pick a
field by its (simplified) JSONPath (§12), then check it with a Hamcrest matcher (§25):

```java
@Test
public void bodyValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .body("userId", equalTo(1))
        .body("id", equalTo(1))
        .body("title", not(emptyString()));
}
```

**⚠️ Common mistakes**
- Chaining several `.body(...)` calls and assuming the first failure stops the rest — depending on
  how you run tests, only the first failure may be reported, hiding whether the others passed too.
- Asserting on a field that's genuinely optional/nullable without accounting for `null` — a
  matcher like `equalTo(x)` on a `null` value behaves differently than on a missing field entirely.

---

## 27. 🟡 JSONPath — Nested Objects & Arrays

Reaching a nested field is just dot notation (`parent.child`), and reaching an array element is
just a numeric index in brackets (`[n]`) — both are the simplified JSONPath style introduced in §12:

```java
@Test
public void nestedJsonPathValidation() {
    // GET /users/2 -> address.city = "Wisokyburgh"
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/users/2")
    .then()
        .statusCode(200)
        .body("address.city", equalTo("Wisokyburgh"))
        .body("company.name", not(emptyString()));
}

@Test
public void jsonArrayIndexValidation() {
    // GET /posts -> array of 100 posts
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts")
    .then()
        .statusCode(200)
        .body("[0].userId", equalTo(1))
        .body("[1].id", equalTo(2));
}
```

**⚠️ Common mistakes**
- Hardcoding an array index (`"[1].id"`) when the API's ordering isn't guaranteed — a reordered
  response silently breaks the assertion. Prefer filtering by a stable field when order isn't fixed.
- Using dot notation on an array response, or bracket-index notation on an object response — the
  two aren't interchangeable; check which shape the endpoint actually returns first.

---

## 28. 🟡 Extracting Data from Arrays

Sometimes you don't want to *assert* on a value — you want to pull it out and use it (print it,
loop over it, feed it into another request). That's **extraction**, not validation (§30 covers the
distinction properly). `response.jsonPath().getList(field, Type)` extracts every value of `field`
across a JSON array into a Java `List`:

```java
@Test
public void extractListOfValues() {
    Response response =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts");

    List<String> titles = response.jsonPath().getList("title", String.class);
    System.out.println("Total titles: " + titles.size());
    System.out.println("First title: " + titles.get(0));
}
```

**⚠️ Common mistakes**
- Calling `.get(0)` on a list without checking it's non-empty first — an empty result (no matches,
  wrong endpoint) throws `IndexOutOfBoundsException` instead of a clear test failure.
- Passing the wrong `Type` to `getList(field, Type)` — asking for `Integer.class` when the JSON
  field is actually a string throws a class-cast error at runtime.

---

## 29. 🟢 Combining Multiple Assertions

Because `.then()` returns itself after each check, you can chain as many validations as you want
onto a single request/response — no need to repeat the request to check different things:

```java
@Test
public void combinedAssertions() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("userId", equalTo(1))
        .body("id", equalTo(1))
        .body("title", not(emptyString()));
}
```

---

## 30. 🟡 Validation vs Extraction, `extract()`

Two different questions you can ask about the same response — and this file has used both since
§23, so it's worth naming the distinction explicitly:

| | Validation | Extraction |
|---|---|---|
| **Question** | "Is this true?" | "What is this value?" |
| **Where** | inside `.then()` | `.then().extract()...` |
| **Result** | test passes or fails | a Java value you can use |
| **Example** | `.body("id", equalTo(1))` | `int id = ....extract().path("id");` |
| **Use it when** | you just need pass/fail | you need the value for later (another request, a log line, further logic) |

They aren't mutually exclusive — a real test often validates first, *then* extracts something it
needs for the next step (you'll see this pattern in §19's `createPost`, which does both).

```java
@Test
public void validationVsExtraction() {
    // VALIDATION — "is the id 1?"
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .body("id", equalTo(1));

    // EXTRACTION — "what is the id?"
    int id =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1")
        .then()
            .extract()
            .path("id");

    System.out.println("Extracted id = " + id);

    String title =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1")
        .then()
            .extract()
            .path("title");

    System.out.println("Extracted title = " + title);
}
```

**⚠️ Common mistakes**
- Extracting a value and never asserting anything about the response first — you can end up
  "validating" a broken response just because a field happened to exist.
- Calling `.extract()` when a plain `.body(path, matcher)` assertion would've done the job —
  extraction adds a variable you now have to manage; only reach for it when you actually need the
  value afterward.

---

## 31. 🟡 Extracting Headers & Cookies

Headers and cookies can be extracted the same way as body values. A **cookie** is a small piece of
data the server asks the client to store and send back on future requests (often used for sessions
or auth):

```java
@Test
public void extractHeader() {
    String contentType =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1")
        .then()
            .extract()
            .header("Content-Type");

    System.out.println("Content-Type header = " + contentType);
}
```

`jsonplaceholder` doesn't set any cookies, so there's no live example to run here — but the pattern
for an API that does is identical, just one call on the `Response` object:

```java
// Illustrative only — works against any API that sets a cookie named "session"
Response response = given().baseUri(BASE_URI).when().get("/some-endpoint");
String session = response.getCookie("session");
```

**⚠️ Common mistakes**
- `.extract().header(...)` returns `null` if the header genuinely isn't present — check for that
  before using the value, don't assume it's always there.
- Header/cookie names are exact strings — `"Content-Type"` and `"content-type"` extract the same
  value, but a typo like `"Content_Type"` silently returns `null`.

---

## 32. 🟢 Response Time & Content-Type Validation

Two more things worth checking beyond status/body: how long the request took, and what format the
response body actually is (`Content-Type`):

```java
@Test
public void responseTimeValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .time(lessThan(3000L)); // milliseconds — keep thresholds generous
}

@Test
public void contentTypeValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .contentType(ContentType.JSON);
}
```

**⚠️ Common mistakes**
- Setting a tight `time(lessThan(...))` threshold — network conditions vary run to run; keep it
  generous or this becomes a flaky test that fails for reasons unrelated to a real bug.
- Confusing `contentType(ContentType.JSON)` (checks the header, exact enum match) with
  `header("Content-Type", containsString("json"))` (checks the raw string) — similar but not the
  same assertion.

---

## 33. 🟢 Response as String & Pretty Print

When a test fails and you need to see exactly what the server sent back, these two are your
debugging tools — one gives you the raw body as text, the other prints it nicely formatted:

```java
@Test
public void responseAsStringAndPrettyPrint() {
    Response response =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1");

    String body = response.getBody().asString();
    System.out.println("Raw body: " + body);

    response.prettyPrint(); // formatted JSON to console — great for debugging
}
```

---

## 34. 🟡 Collection Size / Contents / Negative Validation

Beyond checking individual fields, you can assert on an entire array's size or contents:

```java
@Test
public void collectionSizeValidation() {
    // GET /posts?userId=1 -> exactly 10 posts belong to user 1
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts?userId=1")
    .then()
        .statusCode(200)
        .body("$", hasSize(10));
}

@Test
public void collectionContentsValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts?userId=1")
    .then()
        .body("id", hasItems(1, 2, 3));
}
```

`"$"` here means "the whole response body" — useful when the body itself is a JSON array (as
`/posts?userId=1` is), rather than an object with a named array field.

Two different things both get called "negative" — worth telling apart:

- **Negative assertion** — still a happy-path request, just asserting something is *not* true:
  ```java
  @Test
  public void negativeAssertion() {
      given()
          .baseUri("https://jsonplaceholder.typicode.com")
      .when()
          .get("/posts/1")
      .then()
          .body("title", not(equalTo("")))
          .body("userId", not(equalTo(999)));
  }
  ```
- **Negative test** — deliberately triggering a *failure* scenario (missing resource, bad input,
  no auth) and asserting the API fails the way it's supposed to:
  ```java
  @Test
  public void notFoundNegativeTest() {
      // post 99999 doesn't exist -> the API should 404, not silently return 200
      given()
          .baseUri("https://jsonplaceholder.typicode.com")
      .when()
          .get("/posts/99999")
      .then()
          .statusCode(404);
  }
  ```

**⚠️ Common mistakes**
- Only ever testing the happy path — an API that *should* reject bad input but doesn't is a real
  bug, and negative tests are the only thing that catches it.
- Asserting `statusCode(200)` out of habit on a request you expect to fail — check for the error
  code you actually expect (`404`, `400`, `401`, ...), not just "not a crash."

---

## 35. 🟡 Complete Validation Example

Everything from §23–§34 combined into one realistic test — status, content type, a header, and
several body fields, including a nested one:

```java
@Test
public void completeValidationExample() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .header("Content-Type", containsString("application/json"))
        .body("id", equalTo(1))
        .body("name", equalTo("Leanne Graham"))
        .body("email", containsString("@"))
        .body("address.city", not(emptyString()));
}
```

---

## 36. 🔴 Authentication (Basic, Preemptive, Bearer/OAuth2)

Many real APIs require you to prove who you are before they'll respond. Three common styles, all
supported via `.auth()`:

- **Basic auth** — a username/password sent with each request. `.auth().basic(user, pass)`.
- **Preemptive basic auth** — same as above, but the credentials are sent on the *first* request
  instead of waiting for the server to challenge with a `401` first (some servers require this).
  `.auth().preemptive().basic(user, pass)`.
- **Bearer token / OAuth2** — a token (obtained separately, e.g. from a login endpoint) sent in the
  `Authorization: Bearer <token>` header. `.auth().oauth2(token)`.

`jsonplaceholder` has no auth-protected endpoints — nothing to log into, so Basic/Preemptive auth
stay as **syntax reference** below (the exact method calls, ready to point at a real protected
API). Bearer/OAuth2 *does* have a live example further down, against a real login endpoint.

```java
// Basic auth — replace with a real protected endpoint + real credentials
given()
    .baseUri(BASE_URI)
    .auth()
    .basic("theUsername", "thePassword")
.when()
    .get("/some-protected-endpoint")
.then()
    .statusCode(200);

// Preemptive basic auth — sends credentials on the first request instead of
// waiting for the server to challenge with a 401 first (some servers require this)
given()
    .baseUri(BASE_URI)
    .auth()
    .preemptive()
    .basic("theUsername", "thePassword")
.when()
    .get("/some-protected-endpoint")
.then()
    .statusCode(200);

```

This is the one place in this file pointed at a different, real API — auth genuinely needs a login
endpoint to demonstrate against. **This one is live and passing**, using
`https://api.eventhub.rahulshettyacademy.com` (a public practice API) with a shared test account:
log in once, reuse the returned token as a Bearer token on a protected call.

```java
String token =
    given()
        .baseUri("https://api.eventhub.rahulshettyacademy.com")
        .contentType(ContentType.JSON)
        .body("{ \"email\": \"apiTesting@gmail.com\", \"password\": \"secret123\" }")
    .when()
        .post("/api/auth/login")
    .then()
        .statusCode(200)
        .body("success", equalTo(true))
        .extract()
        .path("token");

given()
    .baseUri("https://api.eventhub.rahulshettyacademy.com")
    .auth().oauth2(token)
.when()
    .get("/api/events")
.then()
    .statusCode(200)
    .body("success", equalTo(true));
```

See `src/test/java/tests/AuthenticationSyntaxReference.java` — `loginThenReuseTokenPattern` runs
exactly this and passes.

**⚠️ Common mistakes**
- Using `.auth().basic(...)` when the server actually needs preemptive auth (or vice versa) — if a
  request that should work returns `401`, try `.preemptive()` first.
- Forgetting the token expires — a hardcoded token from earlier in the day silently starts failing
  later; fetch it fresh at the start of the test run (this token is a JWT valid ~7 days).
- Guessing an endpoint path instead of verifying it — the original version of this example pointed
  at `/api/ems/auth/login` and `/api/ems/events`, neither of which exist; the real paths are
  `/api/auth/login` and `/api/events`.

---

## 37. 🟡 Serialization (Object → JSON)

**Serialization** means turning a Java object into JSON text. Instead of hand-building a JSON
string for a request body, build a POJO (§22) and hand it straight to `.body(...)` — Rest Assured
converts it automatically:

```java
@Test
public void serializationDemo() {
    Post newPost = new Post();
    newPost.setUserId(10);
    newPost.setTitle("Learning Rest Assured");
    newPost.setBody("Serialization sends this whole object as the JSON request body.");

    given()
        .baseUri("https://jsonplaceholder.typicode.com")
        .contentType(ContentType.JSON)
        .body(newPost)              // <-- Rest Assured serializes the POJO to JSON internally
    .when()
        .post("/posts")
    .then()
        .statusCode(201)
        .body("title", equalTo("Learning Rest Assured"))
        .body("userId", equalTo(10));
}
```

**⚠️ Common mistakes**
- Passing a POJO to `.body(...)` without the Jackson dependency (§17) — throws
  `IllegalStateException: Cannot serialize object...` at runtime.
- Forgetting `.contentType(ContentType.JSON)` — Rest Assured still serializes the object, but the
  server may not know to parse the body as JSON.

---

## 38. 🟡 Deserialization (JSON → Object)

**Deserialization** is the reverse: turning a JSON response back into a Java object you can call
getters on, instead of pulling individual fields out with JSONPath one at a time:

```java
@Test
public void deserializationDemo() {
    Post post =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .extract()
            .as(Post.class);         // <-- JSON response deserialized into a Post object

    System.out.println("Post title: " + post.getTitle());
    System.out.println("Post userId: " + post.getUserId());
}

@Test
public void deserializeNestedObject() {
    User user =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .extract()
            .as(User.class);

    System.out.println("User city: " + user.getAddress().getCity());
    System.out.println("Company: " + user.getCompany().getName());
}
```

**⚠️ Common mistakes**
- Calling `.as(Post.class)` when the response is actually an array — deserializes into the wrong
  shape entirely; use `List<Post>` (§39) for arrays.
- Chaining into a nested field (`user.getAddress().getCity()`) without checking `getAddress()`
  isn't `null` first — throws `NullPointerException` if that JSON field was ever missing.

---

## 39. 🟡 Deserializing JSON Arrays into `List<T>`

The same idea as §38, but for an entire JSON array of objects at once — each element becomes one
POJO instance in a `List`:

```java
@Test
public void deserializeJsonArrayToList() {
    Response response =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts?userId=1");

    List<Post> posts = response.jsonPath().getList("", Post.class);

    System.out.println("Number of posts: " + posts.size());
    System.out.println("First post title: " + posts.get(0).getTitle());
}

@Test
public void deserializeUsersArray() {
    Response response =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/users");

    List<User> users = response.jsonPath().getList("", User.class);
    users.forEach(u -> System.out.println(u.getName() + " -> " + u.getAddress().getCity()));
}
```

**⚠️ Common mistakes**
- Using `getList("", Class)` (empty path = the whole array) when the array is actually nested
  inside a named field — then the path needs to name that field instead of being empty.
- Assuming every element has the same shape — one post/user missing an optional nested object
  breaks `.forEach(...)` with a `NullPointerException` on that one element.

---

## 40. 🔴 Advanced JSONPath — Filtering

Rest Assured's JsonPath supports Groovy-style `findAll { ... }` closures — a compact way to filter
a collection down to only the elements matching a condition, without writing a manual Java loop:

```java
@Test
public void jsonPathFiltering() {
    Response response =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts");

    // Filter: only posts belonging to userId 1
    List<Map<String, ?>> userOnePosts =
        response.jsonPath().getList("findAll { it.userId == 1 }");

    System.out.println("Posts for user 1: " + userOnePosts.size());

    // Filter: titles longer than 40 characters
    List<String> longTitles =
        response.jsonPath().getList("findAll { it.title.length() > 40 }.title");

    System.out.println("Long titles found: " + longTitles.size());
}
```

`it` refers to "the current element being examined" — same idea as a lambda parameter, just
Groovy's default name for it.

**⚠️ Common mistakes**
- Forgetting the closure syntax is Groovy, not Java — this string is parsed at runtime by
  Rest Assured's JsonPath engine, so a typo inside it fails at runtime with no compiler warning.
- Filtering, then forgetting the result is still a raw `Map`/`List`, not a POJO — you index into
  it by JSON key (`.get("title")`), not by a generated getter.

---

## 41. 🟢 PUT & DELETE Requests

§19 and §20 already bundled PUT and DELETE together with POST/GET as quick-start CRUD classes. Here
they're shown on their own, matching the topic-by-topic structure of the rest of this Part:

```java
@Test
public void putRequest() {
    Post updatedPost = new Post();
    updatedPost.setUserId(1);
    updatedPost.setTitle("Updated Title via PUT");
    updatedPost.setBody("Updated body content.");

    given()
        .baseUri("https://jsonplaceholder.typicode.com")
        .contentType(ContentType.JSON)
        .body(updatedPost)
    .when()
        .put("/posts/1")
    .then()
        .statusCode(200)
        .body("title", equalTo("Updated Title via PUT"));
}

@Test
public void deleteRequest() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .delete("/posts/1")
    .then()
        .statusCode(200); // jsonplaceholder returns 200 with an empty {} body
}
```

**⚠️ Common mistakes**
- Assuming `DELETE` on jsonplaceholder actually removes anything — it's a mock API, so it always
  returns `200` even for an id that never existed. Don't rely on this behavior matching a real API.
- Sending a `PUT` with only some fields set on the POJO — `PUT` conventionally means "replace the
  whole resource," so omitted fields may come back `null` rather than keeping their old value.

---

## 42. 🟡 POST → Serialize → Send → Deserialize (Full Round Trip)

This ties §37 (serialization) and §38 (deserialization) together in one flow: build a POJO, send
it, and turn the server's response straight back into a POJO — the shape of a realistic API test.

```java
@Test
public void fullRoundTripSerializationDeserialization() {
    // 1. Build the object (this is what gets serialized)
    Post requestPost = new Post();
    requestPost.setUserId(99);
    requestPost.setTitle("Full round trip test");
    requestPost.setBody("Object -> JSON -> API -> JSON -> Object");

    // 2. Send it (serialization happens automatically) and deserialize the response
    Post responsePost =
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
            .contentType(ContentType.JSON)
            .body(requestPost)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .extract()
            .as(Post.class);

    // 3. Work with the deserialized object directly
    System.out.println("New post id assigned by server: " + responsePost.getId());
    System.out.println("Echoed title: " + responsePost.getTitle());
}
```

---

## 43. 🟡 Full Runnable Test Class

Everything from §23–§42 assembled into one file you can drop straight into
`src/test/java/tests/RestAssuredPart3And4Tests.java` and run with `mvn test`. This is a separate,
broader class from the CRUD-focused `CrudTests` in §20 — that one is a minimal quick-start; this
one is a fuller tour of every topic in Parts 3 & 4.

```java
package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Post;
import models.User;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredPart3And4Tests {

    private static final String JSONPLACEHOLDER = "https://jsonplaceholder.typicode.com";

    // ---------- Part 3 ----------

    @Test
    public void statusCodeAndStatusLine() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts/1")
            .then().statusCode(200).statusLine(containsString("200"));
    }

    @Test
    public void headerValidation() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts/1")
            .then().header("Content-Type", containsString("application/json"));
    }

    @Test
    public void bodyAndNestedJsonPath() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/users/2")
            .then().statusCode(200)
                   .body("address.city", equalTo("Wisokyburgh"));
    }

    @Test
    public void hamcrestMatchers() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts")
            .then().body("userId", hasItem(1))
                   .body("id", everyItem(greaterThan(0)));
    }

    @Test
    public void extractionVsValidation() {
        int id = given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts/1")
            .then().extract().path("id");
        org.testng.Assert.assertEquals(id, 1);
    }

    @Test
    public void responseTimeAndContentType() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts/1")
            .then().time(lessThan(3000L))
                   .contentType(ContentType.JSON);
    }

    @Test
    public void collectionSizeAndNegativeValidation() {
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts?userId=1")
            .then().body("$", hasSize(10))
                   .body("userId", everyItem(not(equalTo(2))));
    }

    // ---------- Part 4 ----------

    @Test
    public void notFoundNegativeTest() {
        // Negative test: a post that doesn't exist should 404, not 200
        given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts/99999")
            .then().statusCode(404);
    }

    @Test
    public void serializeAndDeserializeRoundTrip() {
        Post request = new Post();
        request.setUserId(99);
        request.setTitle("Round trip");
        request.setBody("Testing serialization + deserialization together");

        Post response = given().baseUri(JSONPLACEHOLDER)
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/posts")
            .then().statusCode(201)
            .extract().as(Post.class);

        org.testng.Assert.assertEquals(response.getTitle(), "Round trip");
    }

    @Test
    public void deserializeArrayAndFilter() {
        Response response = given().baseUri(JSONPLACEHOLDER)
            .when().get("/posts");

        List<Post> posts = response.jsonPath().getList("", Post.class);
        org.testng.Assert.assertEquals(posts.size(), 100);

        List<Map<String, ?>> userOnePosts =
            response.jsonPath().getList("findAll { it.userId == 1 }");
        org.testng.Assert.assertEquals(userOnePosts.size(), 10);
    }

    @Test
    public void putAndDeleteRequests() {
        Post updated = new Post();
        updated.setUserId(1);
        updated.setTitle("Updated via PUT");
        updated.setBody("Updated body");

        given().baseUri(JSONPLACEHOLDER)
            .contentType(ContentType.JSON)
            .body(updated)
            .when().put("/posts/1")
            .then().statusCode(200).body("title", equalTo("Updated via PUT"));

        given().baseUri(JSONPLACEHOLDER)
            .when().delete("/posts/1")
            .then().statusCode(200);
    }
}
```

---

## 44. 🟢 EventHub — Why a Second, Real API

Every example so far hits `jsonplaceholder` — a **mock** API. Nothing you send is really saved,
nothing you delete is really gone, and there's nothing to log into. That's great for learning the
mechanics, but it hides a few things a real API forces on you:

- **Authentication is mandatory**, not optional (§36 could only be a syntax reference against
  jsonplaceholder — here it's live).
- **Data actually persists.** Create an event and it's still there tomorrow, under your account,
  until you delete it. Tests have to clean up after themselves.
- **Side effects are real.** Booking 3 tickets actually decrements a seat count that a *different*
  request can observe.

`https://api.eventhub.rahulshettyacademy.com` is a public practice API (event listings + ticket
bookings) built for exactly this kind of exercise. All of its code lives in its **own package**,
completely separate from the jsonplaceholder code in Parts 3–4:

```text
src/test/java
├── models/, tests/          Parts 3–4 (jsonplaceholder) — unchanged
└── eventhub/
    ├── models/              Event, Booking, AuthCredentials, CreateEventRequest, CreateBookingRequest
    └── tests/
        ├── BaseEventHubTest.java        shared login-once-per-class helper
        ├── HealthAndConfigTests.java    §50 — the only two endpoints with no auth
        ├── AuthTests.java               §47 — register, login, /auth/me
        ├── EventsTests.java             §48 — full CRUD on events
        └── BookingsTests.java           §49 — full CRUD on bookings + seat-count side effects
```

---

## 45. 🟡 Finding the API: Swagger Docs, and Why You Verify Anyway

EventHub publishes its docs at `/api/docs/` — but that URL is just a **Swagger UI** shell (static
HTML/JS that renders documentation from a spec). The actual machine-readable spec is an OpenAPI
JSON document embedded inside one of that page's scripts, `swagger-ui-init.js`. Pulling it out
directly is faster and more reliable than trying to scrape the rendered page:

```bash
curl -s https://api.eventhub.rahulshettyacademy.com/api/docs/swagger-ui-init.js \
  | grep -o '"swaggerDoc":.*' # the JSON starts here — extract and parse it
```

That gave a complete picture of every endpoint, its parameters, and its response shape — 14
endpoints across 4 tags (Auth, Events, Bookings, Health/Config).

**The docs weren't fully trusted, though.** Every endpoint below was hit with `curl` first,
by hand, before a single line of Java was written — the same lesson from §36 and §27 (guessed
values and unverified paths cause real failures) applied at API-discovery scale this time.
That verification pass is *why* §52 exists: several things the spec claims turned out not to
match what the API actually does.

---

## 46. 🟢 EventHub Project Structure & POJOs

Same idea as §22, applied to a different API. All five classes are plain POJOs — fields plus
getters/setters, nothing else:

| POJO | Used for | Notable field quirk |
|---|---|---|
| `AuthCredentials` | request body for register/login | — |
| `Event` | response shape for events | `price` comes back as a JSON **string** (`"150"`), not a number |
| `CreateEventRequest` | request body for create/update event | `price` here is a `double` — the *request* accepts a number; the *response* returns a string |
| `Booking` | response shape for bookings | `totalPrice` is also a string; has a nested `Event` |
| `CreateBookingRequest` | request body for create booking | `quantity` must be 1–10 (enforced server-side, §49) |

```java
// Event.java (trimmed to the fields that matter most)
public class Event {
    private long id;
    private String title;
    private String category;
    private String price;          // <-- String, not double — verified against the live API
    private int totalSeats;
    private int availableSeats;
    // ...getters/setters for all fields; see src/test/java/eventhub/models/Event.java
}
```

**⚠️ Common mistakes**
- Modeling `price`/`totalPrice` as `double` because that's what you *send* — deserialization then
  fails, since the *response* is a string. Different types on the way in vs. the way out.
- Assuming a `CreateEventRequest` and a returned `Event` are interchangeable — they're deliberately
  two different classes here because their `price` field types don't match.

---

## 47. 🟡 Authentication Tests

Covers `POST /auth/register`, `POST /auth/login`, and `GET /auth/me`. Every other EventHub test
class extends `BaseEventHubTest`, which just logs in once per class and stores the token:

```java
public abstract class BaseEventHubTest {
    protected static final String BASE_URI = "https://api.eventhub.rahulshettyacademy.com/api";
    protected static final String TEST_EMAIL = "apiTesting@gmail.com";
    protected static final String TEST_PASSWORD = "secret123";
    protected String token;

    @BeforeClass
    public void loginOnceForThisClass() {
        token = given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body("{ \"email\": \"" + TEST_EMAIL + "\", \"password\": \"" + TEST_PASSWORD + "\" }")
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");
    }
}
```

`AuthTests` itself has 7 methods (`src/test/java/eventhub/tests/AuthTests.java`) — registering a
brand-new user needs a **unique email every run**, generated at test time instead of hardcoded:

```java
@Test
public void registerNewUser_returns201AndToken() {
    String newEmail = "restassured.notes+" + System.currentTimeMillis() + "@example.com";

    given()
        .baseUri(BASE_URI)
        .contentType(ContentType.JSON)
        .body("{ \"email\": \"" + newEmail + "\", \"password\": \"secret123\" }")
    .when()
        .post("/auth/register")
    .then()
        .statusCode(201)
        .body("success", equalTo(true))
        .body("token", not(emptyOrNullString()))
        .body("user.email", equalTo(newEmail));
}

@Test
public void authMe_withoutToken_returns401() {
    given()
        .baseUri(BASE_URI)
    .when()
        .get("/auth/me")
    .then()
        .statusCode(401)
        .body("success", equalTo(false));
}
```

The other 5 methods cover: registering an already-used email (400), logging in with a wrong
password (400), logging in with an email that was never registered (400 — see §52), a successful
login, and `/auth/me` with a valid token.

**⚠️ Common mistakes**
- Hardcoding an email for the register test — the second run fails with "already registered."
  Generate a unique one every time (timestamp, UUID, etc.).
- Assuming a nonexistent-user login is `404` because that's what feels intuitive — this API
  deliberately returns `400` for both "wrong password" and "no such user," so it never reveals
  which emails are registered.

---

## 48. 🟡 Events CRUD Tests

`src/test/java/eventhub/tests/EventsTests.java` — 8 tests covering all 5 Events endpoints. The one
thing every single test needs that jsonplaceholder never did: a Bearer token, even for a plain
`GET` (see §52 — this isn't in the published docs).

```java
@Test
public void createEvent_returns201WithMatchingFields() {
    CreateEventRequest request = sampleEvent("RestAssured Create Test");

    long eventId =
        given()
            .baseUri(BASE_URI)
            .auth().oauth2(token)
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/events")
        .then()
            .statusCode(201)
            .body("data.title", equalTo("RestAssured Create Test"))
            .body("data.availableSeats", equalTo(50)) // starts equal to totalSeats
            .extract()
            .jsonPath().getLong("data.id");

    // cleanup — this is a real backend, created data doesn't disappear on its own
    given().baseUri(BASE_URI).auth().oauth2(token)
        .when().delete("/events/" + eventId)
        .then().statusCode(200);
}

@Test
public void createEvent_missingRequiredFields_returns400Validation() {
    given()
        .baseUri(BASE_URI)
        .auth().oauth2(token)
        .contentType(ContentType.JSON)
        .body("{ \"description\": \"missing everything else\" }")
    .when()
        .post("/events")
    .then()
        .statusCode(400)
        .body("error", equalTo("Validation failed"))
        .body("details.field", hasItem("title"));
}
```

The remaining 6 methods: get by id, update, delete-then-get-404, list filtered by category
(against the platform's permanent seed events — no fixture needed), get a nonexistent id (404),
and a plain `GET /events` with no token at all (401).

**⚠️ Common mistakes**
- Creating an event in a test and forgetting to delete it — every test here does cleanup
  (usually in a `finally` block) specifically because this data is permanent otherwise.
- Comparing `data.price` to a number (`equalTo(250)`) — it comes back as a string, so the
  assertion needs `equalTo("250")` (§46).

---

## 49. 🔴 Booking Tests — Seat Counts as a Side Effect

`src/test/java/eventhub/tests/BookingsTests.java` — 8 tests covering all 5 Bookings endpoints.
Every test needs an event to book against, so a fresh throwaway event is created in
`@BeforeMethod` and deleted in `@AfterMethod` (deleting an event cascades and removes its bookings
too, so that's the only cleanup needed):

```java
private long fixtureEventId;
private static final int FIXTURE_TOTAL_SEATS = 20;

@BeforeMethod
public void createFixtureEvent() {
    // ...creates a 20-seat event, stores its id in fixtureEventId
}

@AfterMethod
public void deleteFixtureEvent() {
    given().baseUri(BASE_URI).auth().oauth2(token)
        .when().delete("/events/" + fixtureEventId)
        .then().statusCode(200);
}

@Test
public void createBooking_returns201AndDecrementsSeats() {
    given()
        .baseUri(BASE_URI)
        .auth().oauth2(token)
        .contentType(ContentType.JSON)
        .body(bookingFor(fixtureEventId, 3))
    .when()
        .post("/bookings")
    .then()
        .statusCode(201)
        .body("data.status", equalTo("confirmed"))
        .body("data.bookingRef", startsWith("R-")); // not "EVT-", despite the docs' example

    // the side effect: booking 3 seats actually decremented the event's availableSeats
    given().baseUri(BASE_URI).auth().oauth2(token)
        .when().get("/events/" + fixtureEventId)
        .then().statusCode(200)
               .body("data.availableSeats", equalTo(FIXTURE_TOTAL_SEATS - 3));
}

@Test
public void cancelBooking_returns200AndRestoresSeats() {
    long bookingId = createBooking(4);

    given().baseUri(BASE_URI).auth().oauth2(token)
        .when().delete("/bookings/" + bookingId)
        .then().statusCode(200).body("message", equalTo("Booking cancelled"));

    given().baseUri(BASE_URI).auth().oauth2(token)
        .when().get("/events/" + fixtureEventId)
        .then().statusCode(200)
               .body("data.availableSeats", equalTo(FIXTURE_TOTAL_SEATS)); // fully restored
}
```

The remaining 6 methods: get by id, get by reference code, list filtered by `eventId`, booking
more seats than allowed (400), booking a nonexistent event (404), and looking up a reference code
that doesn't exist (404).

**⚠️ Common mistakes**
- Testing `createBooking` in isolation without checking the event afterward — the interesting
  behavior here isn't the booking response, it's the seat count changing on a *different*
  endpoint as a result.
- Not cleaning up the fixture event — with a real backend, a failed test that skips its `@AfterMethod`
  leaves permanent junk data in the account. `@AfterMethod` runs even if the test body fails, so
  it still gets cleaned up.

---

## 50. 🟢 Health & Config Tests

The only two endpoints in the whole API that need no token at all — a full file, since it's short:

```java
public class HealthAndConfigTests {

    private static final String BASE_URI = "https://api.eventhub.rahulshettyacademy.com/api";

    @Test
    public void healthCheck_returnsOkStatus() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/health")
        .then()
            .statusCode(200)
            .body("status", equalTo("ok"))
            .body("dbStatus", equalTo("connected"));
    }

    @Test
    public void getConfig_returnsFeatureFlags() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/config")
        .then()
            .statusCode(200)
            .body("showExploreLinks", notNullValue());
    }
}
```

---

## 51. 🟢 Running the EventHub Suite

A separate `testng-eventhub.xml` at the project root, kept independent from the jsonplaceholder
`testng.xml` (§21) on purpose — the two APIs don't need to run together:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="EventHubSuite">
    <test name="HealthAndConfigTests">
        <classes><class name="eventhub.tests.HealthAndConfigTests"/></classes>
    </test>
    <test name="AuthTests">
        <classes><class name="eventhub.tests.AuthTests"/></classes>
    </test>
    <test name="EventsTests">
        <classes><class name="eventhub.tests.EventsTests"/></classes>
    </test>
    <test name="BookingsTests">
        <classes><class name="eventhub.tests.BookingsTests"/></classes>
    </test>
</suite>
```

```bash
mvn test -Dsurefire.suiteXmlFiles=testng-eventhub.xml
```

25 tests, all passing, repeatably (every test that creates data cleans it up). Plain `mvn test`
with no suite file runs *everything* — jsonplaceholder and EventHub together — since Surefire's
default discovery matches any `**/*Tests.java` regardless of package.

---

## 52. 🟡 Real API vs Documented API — What Differed

Every row here was found by actually calling the API (§45), not by reading the docs more
carefully. This is the same lesson as §27's `/users/7` bug and §21's `-DsuiteXmlFile` typo, just
at the scale of an entire API:

| The docs say | What actually happens |
|---|---|
| Only `GET /auth/me` requires auth (per its `security` block) | Every endpoint except register/login/health/config requires a Bearer token |
| Login with an unregistered email → `404` | Returns `400` — the API never reveals whether an email exists |
| Login endpoint at `/api/ems/auth/login` (an earlier, unverified guess in this file, §36) | Real path is `/api/auth/login` |
| Protected events endpoint at `/api/ems/events` (same earlier guess) | Real path is `/api/events` |
| `price` / `totalPrice` are numbers (per the schema's `type: number`) | Both come back as JSON **strings** in every response |
| Example booking reference looks like `EVT-XXXXXX` | Real format is `R-XXXXXX` |
| `GET /events/{id}` with a non-numeric id → presumably `404` | Returns `500` (an unhandled error, not a validation check) |

**Takeaway:** a published spec tells you what an API is *supposed* to do. Whether it actually does
that is a separate question — and answering it is most of what API testing is for.

---

## Glossary

| Term | Meaning |
|---|---|
| **API** | A way for one program to ask another program for data or actions. (§1) |
| **REST** | A style of API built on plain HTTP: resources identified by URLs, HTTP methods say what to do, JSON as the usual data format. (§1) |
| **REST API** | A web service you interact with via HTTP requests to URLs ("endpoints"), usually exchanging JSON. |
| **Endpoint** | A specific URL path an API exposes, e.g. `/posts/1`. |
| **Base URI** | The root of an API's URL, e.g. `https://jsonplaceholder.typicode.com` — set once via `.baseUri(...)` instead of repeating it on every request. |
| **HTTP method** | The verb describing what you want to do to an endpoint: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, etc. (§7) |
| **Status code** | 3-digit number the server returns describing the outcome, e.g. `200 OK`, `404 Not Found`. (§23) |
| **Header** | Metadata sent with a request or response, separate from its body content, e.g. `Content-Type`. (§9, §24) |
| **Query parameter** | Extra data appended to a URL after `?`, e.g. `?page=2`. (§8) |
| **Path parameter** | Part of the URL path itself that identifies a specific resource, e.g. the `1` in `/users/1`. (§8) |
| **Request body** | The data sent along with a POST/PUT/PATCH request, typically JSON. (§10) |
| **JSON** | JavaScript Object Notation — a text format for structured data using key–value pairs, objects `{}`, and arrays `[]`. (§10) |
| **JSONPath** | A query syntax for locating a value inside a JSON document by describing where it lives. (§12) |
| **DSL** | Domain-Specific Language — a small, focused syntax for one job; Rest Assured's `given/when/then` is one. (§5) |
| **POJO** | Plain Old Java Object — a simple class with fields + getters/setters, no framework baggage. (§22) |
| **Serialization** | Converting a Java object into JSON (for a request body). (§37) |
| **Deserialization** | Converting a JSON response back into a Java object. (§38) |
| **Hamcrest matcher** | A readable assertion helper, e.g. `equalTo(1)`, `containsString("x")`, used as the second argument to `.body(...)`. (§25) |
| **Assertion** | A pass/fail check on a value — "this must be true, or the test fails." |
| **Extraction** | Pulling a value out of a response to use it, as opposed to asserting on it. (§30) |
| **Negative testing** | Deliberately triggering a failure scenario (missing resource, bad input) and asserting the API fails the way it's supposed to. (§34) |
| **Cookie** | A small piece of data the server asks the client to store and resend on future requests (often for sessions/auth). (§31) |
| **TestNG** | The Java testing framework used throughout this file (`@Test` annotation, `mvn test` runs it via Surefire). |
| **Static import** | `import static ...` — lets you call `given()` instead of `RestAssured.given()`. (§17) |

---

## Learning Roadmap

A suggested order through this file, and what "done" looks like at each stage.

**🟢 Beginner — get comfortable with the basics**
- [ ] §1–§10 — REST/API concepts, what Rest Assured is, the `given/when/then` DSL, requests
- [ ] §11–§16 — the Response object, JSONPath basics, Part 1 & 2 recap
- [ ] §17–§21 — set up the project, run §19's `CrudTestsBeginner` end to end
- [ ] §22–§26 — POJOs exist (don't need to master them yet), status/header/body validation, Hamcrest
- **Checkpoint:** you can write a GET test that checks a status code and two body fields, from scratch.

**🟡 Intermediate — validate, extract, and move data as objects**
- [ ] §20, §27–§35 — the POJO version of CRUD, nested JSONPath, extraction vs validation, cookies,
      collection/negative validation
- [ ] §37–§39 — serialization, deserialization, deserializing arrays into `List<T>`
- **Checkpoint:** you can write a POST test that builds a POJO, sends it, and deserializes the
  response back into another POJO — §42 is exactly this, end to end.

**🔴 Advanced — auth, filtering, and putting it all together**
- [ ] §36 — authentication syntax (basic/preemptive/bearer), applied to a real protected API
- [ ] §40 — Groovy `findAll {}` filtering
- [ ] §41–§43 — PUT/DELETE on their own, the full round trip, the consolidated runnable class
- **Checkpoint:** you can take a brand-new public API's docs and write a small CRUD + validation
  suite against it without copying this file's code directly — only its patterns.

**🚀 Beyond Advanced — a real, independent API (Part 5)**
- [ ] §44–§46 — why EventHub is a different kind of practice target, and how its spec was found
  and verified rather than trusted outright
- [ ] §47–§50 — auth, events CRUD, bookings + their seat-count side effects, health/config
- [ ] §51–§52 — running the suite, and the full list of docs-vs-reality discrepancies found
- **Checkpoint:** this *is* the previous checkpoint, done for real — the EventHub suite in
  `src/test/java/eventhub/` was built exactly this way: docs read, every endpoint verified by hand,
  then written up.

**Beyond this file:** JSON Schema validation, request/response logging (`.log().all()`),
`RequestSpecBuilder`/`ResponseSpecBuilder` for reusable specs, and integrating these tests into a
CI pipeline (GitHub Actions, Jenkins) so they run on every commit.

---

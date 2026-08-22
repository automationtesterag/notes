# Rest Assured — Complete Notes

## Table of Contents
1. [What is Rest Assured?](#1-what-is-rest-assured)
2. [Why Use It?](#2-why-use-it)
3. [Key Features](#3-key-features)
4. [DSL Syntax & Request Flow](#4-dsl-syntax--request-flow)
5. [RequestSpecification](#5-requestspecification)
6. [HTTP Methods](#6-http-methods)
7. [Request Parameters](#7-request-parameters)
8. [Request Headers](#8-request-headers)
9. [Request Body](#9-request-body)
10. [The Response Object](#10-the-response-object)
11. [JSONPath](#11-jsonpath)
12. [Advantages](#12-advantages)
13. [Disadvantages](#13-disadvantages)
14. [Mental Model](#14-mental-model)


---

## 1. What is Rest Assured?

Open-source **Java library** for testing/validating REST APIs. Acts as a headless client (no GUI needed).

> Automates REST API testing using Java.

Supports HTTP methods: `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `OPTIONS`, `HEAD`

Validates: parameters, headers, cookies, responses, JSON, XML

---

## 2. Why Use It?

- **Open source** — free, active community
- **Simplifies testing** — easier than raw HTTP clients
- **Java-native** — fits Java-based test automation
- **Built-in validation** for API responses
- **Multi-method support** (GET/POST/PUT/DELETE/etc.)

---

## 3. Key Features

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

## 4. DSL Syntax & Request Flow

Rest Assured uses a BDD-style, human-readable DSL:

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

## 5. RequestSpecification

`RequestSpecification` represents the details of an API request. It can contain:

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

## 6. HTTP Methods

| Method  | Purpose                       |
| ------- | ------------------------------ |
| GET     | Retrieve data                 |
| POST    | Create data                   |
| PUT     | Update/replace data           |
| PATCH   | Partially update data         |
| DELETE  | Delete data                   |
| HEAD    | Retrieve headers              |
| OPTIONS | Retrieve supported operations |

Basic examples:

```java
given().when().get("/users");
given().when().post("/users");
given().when().put("/users/1");
given().when().patch("/users/1");
given().when().delete("/users/1");
```

---

## 7. Request Parameters

**Query Parameters** — part of the query string:

```text
/users?page=2
```
```java
.queryParam("page", 2)
```

**Path Parameters** — part of the resource path:

```text
/users/123
```
```java
.pathParam("id", 123)
```

**Form Parameters** — used when sending form-based data.

---

## 8. Request Headers

Headers provide additional information about the request. Common examples:

```text
Content-Type
Accept
Authorization
```

Rest Assured allows headers to be added individually or together:

```java
.header("Accept", "application/json")
```

---

## 9. Request Body

For methods such as POST, PUT, and PATCH, Rest Assured allows a request body to be supplied along with the appropriate content type.

Example:

```json
{
  "name": "John",
  "email": "john@test.com"
}
```

---

## 10. The Response Object

After sending a request, Rest Assured provides a `Response` object containing:

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

The response body can be read as a String or processed as JSON/XML.

---

## 11. JSONPath

For JSON responses, Rest Assured provides **JSONPath** to navigate and extract specific data.

Example:

```json
{
  "id": 101,
  "name": "John"
}
```

You can access `id` and `name` directly. JSONPath becomes especially useful when dealing with nested JSON and arrays (e.g. `$['store']['book'][3]['title']`).

---

## 12. Advantages

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

## 13. Disadvantages

- **No SOAP support** — REST only
- **Requires Java knowledge**
- **No built-in reporting**

---

## 14. Mental Model

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


---

*Source: [ToolsQA — Rest Assured Library](https://www.toolsqa.com/rest-assured/rest-assured-library/)*

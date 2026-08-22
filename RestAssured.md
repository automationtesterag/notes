# Rest Assured — Quick Notes

## Table of Contents
1. [What is Rest Assured?](#1-what-is-rest-assured)
2. [Why Use It?](#2-why-use-it)
3. [Key Features](#3-key-features)
4. [DSL Syntax](#4-dsl-syntax)
5. [Advantages](#5-advantages)
6. [Disadvantages](#6-disadvantages)
7. [Mental Model](#7-mental-model)

---

## 1. What is Rest Assured?

Open-source **Java library** for testing/validating REST APIs. Acts as a headless client (no GUI needed).

> Automates REST API testing using Java.

Supports HTTP methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`, `HEAD`
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

## 4. DSL Syntax

BDD-style, human-readable:

```java
given()
    .when()
    .then();
```

- `given()` → what to send
- `when()` → action to perform
- `then()` → what to validate

---

## 5. Advantages

Open source · easy setup · rich syntax · built-in assertions · less code · easy JSON/XML parsing · headers/cookies support · response-time validation · logging · auth support · JSONPath/XMLPath · JSON Schema validation · multipart data · integrates with other Java tools

---

## 6. Disadvantages

- **No SOAP support** — REST only
- **Requires Java knowledge**
- **No built-in reporting**

---

## 7. Mental Model

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
*Source: [ToolsQA — Rest Assured Library](https://toolsqa.com/rest-assured/rest-assured-library/)*

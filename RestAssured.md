# Rest Assured

## 1. What is Rest Assured?

**Rest Assured** is an **open-source Java-based library** used for testing and validating REST APIs / RESTful web services.

It acts like a **headless client**, meaning it can communicate with REST services without a graphical user interface.

### In simple words

> Rest Assured helps us automate REST API testing using Java.

It supports HTTP methods such as:

* GET
* POST
* PUT
* DELETE
* OPTIONS
* HEAD

It also supports validation of:

* Parameters
* Headers
* Cookies
* HTTP responses
* JSON
* XML

---

## 2. Why Do We Need Rest Assured?

Testing REST services directly using Java can be complicated.

Rest Assured makes REST API testing **simpler and easier** by providing a readable DSL-style syntax.

### Main reasons to use it

**1. Open source**

Rest Assured is free to use and has an active development community.

**2. Simplifies API testing**

It makes testing REST services easier compared with implementing API testing using low-level HTTP clients.

**3. Java support**

It allows testers who work with Java to perform API automation.

**4. Easy validation**

It provides built-in capabilities for validating API responses.

**5. Supports different HTTP operations**

GET, POST, PUT, DELETE, OPTIONS, HEAD, etc.

([Tools QA][1])

---

# 3. Key Features of Rest Assured

Rest Assured provides several useful capabilities.

### REST API Testing

Used for testing REST-based services.

### JSON Support

Can test and validate JSON-based web services.

### XML Support

Can also test XML-based web services.

### JSONPath

Allows us to locate specific data inside a JSON response.

Example:

```text
$['store']['book'][3]['title']
```

### XMLPath

Allows us to locate specific elements inside XML responses.

### Request Validation

Supports validation of:

* Parameters
* Headers
* Cookies
* Content

### Response Validation

Allows validation of HTTP responses.

### Specification Reuse

Provides the ability to create reusable specifications.

### File Upload

Supports file-upload scenarios.

([Tools QA][1])

---

# 4. DSL-Like Syntax

One of the important features of Rest Assured is its **readable DSL-style syntax**.

The commonly used structure is:

```java
given()
    .when()
    .then();
```

Think of it as:

```text
given() → What should I send?
when()  → What action should I perform?
then()  → What should I validate?
```

specifically identifies `given()`, `when()`, and `then()` as part of Rest Assured's readable BDD-style syntax. ([Tools QA][1])

---

# 5. JSONPath

Rest Assured supports **JSONPath** to access specific elements from JSON responses.

Example JSON:

```json
{
    "store": {
        "book": [
            {
                "title": "Book 1"
            }
        ]
    }
}
```

A JSONPath expression can identify a particular value:

```text
$['store']['book'][0]['title']
```

This is similar in concept to using XPath for XML.

([Tools QA][1])

---

# 6. Advantages of Rest Assured

### Important ones for beginners

* Open source and free
* Easy setup
* Rich syntax
* Built-in assertions
* Less coding
* Easy JSON/XML parsing
* Supports headers and cookies
* Supports response-time validation
* Powerful logging
* Supports authentication mechanisms
* Supports JSONPath and XMLPath
* Supports JSON Schema validation
* Supports multipart form data
* Supports integration with other Java tools

---

# 7. Disadvantages of Rest Assured


### 1. No explicit SOAP support

Rest Assured is primarily designed for REST APIs.

### 2. Java knowledge is required

Since Rest Assured is Java-based, some Java knowledge is necessary.

### 3. No built-in reporting

Rest Assured itself does not provide an inbuilt reporting system.

---

# 8. Rest Assured — Simple Mental Model

```text
             REST ASSURED
                  │
        ┌─────────┴─────────┐
        ↓                   ↓
     REQUEST              RESPONSE
        │                   │
   GET/POST/etc.        Status Code
   Headers              Headers
   Parameters           Body
   Cookies              JSON/XML
   Body                 Validation
        │                   │
        └─────────┬─────────┘
                  ↓
             TEST RESULT
```

---

[1]: https://toolsqa.com/rest-assured/rest-assured-library/?utm_source=chatgpt.com "Get started with API testing using Rest Assured"

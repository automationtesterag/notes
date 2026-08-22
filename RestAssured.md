# Rest Assured — Complete Notes


## Table of Contents

**Part 1 — Fundamentals**
1. [What is Rest Assured?](#1-what-is-rest-assured)
2. [Why Use It?](#2-why-use-it)
3. [Key Features](#3-key-features)
4. [DSL Syntax & Request Flow](#4-dsl-syntax--request-flow)
5. [RequestSpecification](#5-requestspecification)
6. [HTTP Methods](#6-http-methods)
7. [Request Parameters](#7-request-parameters)
8. [Request Headers](#8-request-headers)
9. [Request Body](#9-request-body)

**Part 2 — Requests & Responses**

10. [The Response Object](#10-the-response-object)
11. [JSONPath](#11-jsonpath)
12. [Advantages](#12-advantages)
13. [Disadvantages](#13-disadvantages)
14. [Mental Model](#14-mental-model)
15. [Quick Revision (Parts 1 & 2)](#15-quick-revision)

**Part 3 — Response Validation & Extraction**

16. [Project Setup](#16-project-setup)
17. [POJOs Used Below](#17-pojos-used-below)
18. [Status Code & Status Line Validation](#18-status-code--status-line-validation)
19. [Response Header Validation](#19-response-header-validation)
20. [Response Body Validation](#20-response-body-validation)
21. [JSONPath — Nested Objects & Arrays](#21-jsonpath--nested-objects--arrays)
22. [Extracting Data from Arrays](#22-extracting-data-from-arrays)
23. [Hamcrest Matchers](#23-hamcrest-matchers)
24. [Combining Multiple Assertions](#24-combining-multiple-assertions)
25. [Validation vs Extraction, `extract()`](#25-validation-vs-extraction-extract)
26. [Extracting Headers & Cookies](#26-extracting-headers--cookies)
27. [Response Time & Content-Type Validation](#27-response-time--content-type-validation)
28. [Response as String & Pretty Print](#28-response-as-string--pretty-print)
29. [Collection Size / Contents / Negative Validation](#29-collection-size--contents--negative-validation)
30. [Complete Validation Example](#30-complete-validation-example)

**Part 4 — Advanced Rest Assured**

31. [Authentication (Basic, Preemptive, Bearer/OAuth2)](#31-authentication-basic-preemptive-beareroauth2)
32. [Serialization (Object → JSON)](#32-serialization-object--json)
33. [Deserialization (JSON → Object)](#33-deserialization-json--object)
34. [Deserializing JSON Arrays into `List<T>`](#34-deserializing-json-arrays-into-listt)
35. [Advanced JSONPath — Filtering](#35-advanced-jsonpath--filtering)
36. [PUT & DELETE Requests](#36-put--delete-requests)
37. [POST → Serialize → Send → Deserialize (Full Round Trip)](#37-post--serialize--send--deserialize-full-round-trip)
38. [Full Runnable Test Class](#38-full-runnable-test-class)

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

## 15. Quick Revision

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

## 16. Project Setup

`pom.xml` dependencies (Java 11+, Maven):

```xml
<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Static imports used throughout:

```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
```

---

## 17. POJOs Used Below

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

---

## 18. Status Code & Status Line Validation

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

---

## 19. Response Header Validation

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
        .baseUri("https://httpbin.org")
    .when()
        .get("/response-headers?Cache-Control=no-cache&X-Custom-Header=RestAssured")
    .then()
        .statusCode(200)
        .header("Cache-Control", containsString("no-cache"))
        .header("X-Custom-Header", equalTo("RestAssured"));
}
```

---

## 20. Response Body Validation

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

---

## 21. JSONPath — Nested Objects & Arrays

```java
@Test
public void nestedJsonPathValidation() {
    // GET /users/7 -> address.city = "Wisokyburgh"
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/users/7")
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

---

## 22. Extracting Data from Arrays

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

---

## 23. Hamcrest Matchers

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

---

## 24. Combining Multiple Assertions

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

## 25. Validation vs Extraction, `extract()`

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

---

## 26. Extracting Headers & Cookies

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

@Test
public void extractCookie() {
    // httpbin lets us set a cookie then read it back
    Response response =
        given()
            .baseUri("https://httpbin.org")
        .when()
            .get("/cookies/set?session=abc123");

    String session = response.getCookie("session");
    System.out.println("Extracted cookie 'session' = " + session);
}
```

---

## 27. Response Time & Content-Type Validation

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

---

## 28. Response as String & Pretty Print

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

## 29. Collection Size / Contents / Negative Validation

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

@Test
public void negativeValidation() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .body("title", not(equalTo("")))
        .body("userId", not(equalTo(999)));
}
```

---

## 30. Complete Validation Example

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

## 31. Authentication (Basic, Preemptive, Bearer/OAuth2)

`httpbin.org` has dedicated endpoints built exactly for testing auth flows.

```java
@Test
public void basicAuthentication() {
    // httpbin.org/basic-auth/{user}/{passwd} only returns 200
    // if you send EXACTLY that username/password
    given()
        .baseUri("https://httpbin.org")
        .auth()
        .basic("testuser", "testpass")
    .when()
        .get("/basic-auth/testuser/testpass")
    .then()
        .statusCode(200)
        .body("authenticated", equalTo(true))
        .body("user", equalTo("testuser"));
}

@Test
public void preemptiveBasicAuthentication() {
    given()
        .baseUri("https://httpbin.org")
        .auth()
        .preemptive()
        .basic("testuser", "testpass")
    .when()
        .get("/basic-auth/testuser/testpass")
    .then()
        .statusCode(200);
}

@Test
public void bearerTokenOAuth2() {
    // httpbin.org/bearer accepts ANY token and echoes it back — perfect for practicing oauth2()
    given()
        .baseUri("https://httpbin.org")
        .auth()
        .oauth2("sample-access-token-123")
    .when()
        .get("/bearer")
    .then()
        .statusCode(200)
        .body("authenticated", equalTo(true))
        .body("token", equalTo("sample-access-token-123"));
}
```

> For EventHub specifically: once you confirm the sign-in path from the Swagger UI (typically
> something like `POST /api/ems/auth/login`), extract the token like this, then reuse it as a
> Bearer token on subsequent calls:
> ```java
> String token =
>     given()
>         .baseUri("https://api.eventhub.rahulshettyacademy.com")
>         .contentType(ContentType.JSON)
>         .body("{ \"email\": \"user@test.com\", \"password\": \"Test@123\" }")
>     .when()
>         .post("/api/ems/auth/login")
>     .then()
>         .statusCode(200)
>         .extract()
>         .path("token"); // adjust field name to match the real response
>
> given()
>     .baseUri("https://api.eventhub.rahulshettyacademy.com")
>     .auth().oauth2(token)
> .when()
>     .get("/api/ems/events")
> .then()
>     .statusCode(200);
> ```

---

## 32. Serialization (Object → JSON)

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

---

## 33. Deserialization (JSON → Object)

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

---

## 34. Deserializing JSON Arrays into `List<T>`

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

---

## 35. Advanced JSONPath — Filtering

Rest Assured's JsonPath supports Groovy-style `findAll` closures for filtering collections —
e.g. "find posts where the title is longer than 40 characters."

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

---

## 36. PUT & DELETE Requests

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

---

## 37. POST → Serialize → Send → Deserialize (Full Round Trip)

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

## 38. Full Runnable Test Class

Everything above, assembled into one file you can drop straight into
`src/test/java/tests/RestAssuredPart3And4Tests.java` and run with `mvn test`.

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
    private static final String HTTPBIN = "https://httpbin.org";

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
            .when().get("/users/7")
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
    public void basicAndBearerAuth() {
        given().baseUri(HTTPBIN).auth().basic("testuser", "testpass")
            .when().get("/basic-auth/testuser/testpass")
            .then().statusCode(200);

        given().baseUri(HTTPBIN).auth().oauth2("sample-token")
            .when().get("/bearer")
            .then().statusCode(200).body("token", equalTo("sample-token"));
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

## Part 3 & 4 — Quick Revision

```text
RESPONSE VALIDATION                    ADVANCED FEATURES
├── statusCode()                       ├── Authentication
├── statusLine()                       │   ├── basic() / preemptive()
├── header() / headers                 │   └── oauth2() (Bearer)
├── contentType()                      ├── Serialization
├── body() + Hamcrest                  │   └── Object → JSON (POJO in .body())
├── time()                             ├── Deserialization
└── JSONPath (nested + arrays)         │   ├── .as(Class)
                                        │   └── jsonPath().getList("", Class)
EXTRACTION                             ├── Advanced JSONPath
├── extract().path()                   │   └── findAll { } filtering
├── extract().header()                 └── PUT / DELETE
├── extract().cookie()
└── response.jsonPath().getList()
```

**Core takeaway:** Part 3 teaches you to **validate and pull data out of** a response.
Part 4 teaches you to **authenticate, and move data cleanly between Java objects and JSON**
in both directions — everything you need for a real, production-style API test suite.

---

## Final Takeaway — All 4 Parts

```text
Part 1: Fundamentals        Part 2: Requests/Responses     Part 3: Validation           Part 4: Advanced
├── given/when/then          ├── Response object            ├── statusCode/header/body   ├── Auth (basic/bearer)
├── HTTP methods              ├── JSONPath basics             ├── Hamcrest matchers        ├── Serialization
├── RequestSpecification      ├── Advantages/Disadvantages    ├── extract() vs body()      ├── Deserialization
└── Params/Headers/Body       └── Mental model                └── time()/contentType()     └── Advanced JSONPath, PUT/DELETE
```

> Across all four parts: Rest Assured lets you **build a request** (given), **send it** (when),
> **validate the response** (then), **extract data from it**, and — with authentication and
> serialization — do all of this against real, secured, structured APIs in clean, readable
> Java code.

---

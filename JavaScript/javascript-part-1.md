# An Introduction to JavaScript

## What is JavaScript?

JavaScript was initially created to “make web pages alive.” Programs written in this language are called scripts, which can be embedded directly in a web page’s HTML and executed automatically as the page loads. These scripts are provided and executed as plain text, meaning they don’t require special preparation or compilation to run. This distinguishes JavaScript from another language called Java.

## Why is it called JavaScript?

Originally named “LiveScript,” the language was rebranded to JavaScript to leverage the popularity of Java at the time. Over the years, JavaScript evolved into a fully independent language with its own specification called ECMAScript, and it no longer has any relation to Java.

Today, JavaScript can execute not only in the browser but also on the server or any device with a JavaScript engine, which is often embedded in browsers. Different engines have unique codenames, including:

- **V8** – used in Chrome, Opera, and Edge.
- **SpiderMonkey** – used in Firefox.
- **Chakra** – for Internet Explorer.
- **JavaScriptCore** (Nitro, SquirrelFish) – for Safari.

These terms are useful for developers as they indicate feature support across different browsers.

## How do engines work?

JavaScript engines perform several steps:

1. **Parsing**: The engine reads the script.
2. **Compiling**: It converts the script to machine code.
3. **Execution**: The machine code runs quickly.

Engines optimize at each stage and can monitor the compiled script during execution to improve performance further.

## What can in-browser JavaScript do?

Modern JavaScript is designed to be a “safe” programming language, limiting low-level access to memory or the CPU to protect users. In-browser JavaScript can:

- Manipulate HTML, change content, and modify styles.
- Respond to user actions, like mouse clicks and key presses.
- Send network requests and handle file uploads/downloads (e.g., using AJAX and COMET).
- Manage cookies, prompt questions, and display messages.
- Store data on the client-side using local storage.

## What CAN’T in-browser JavaScript do?

JavaScript's capabilities are intentionally limited for user safety. Examples of these restrictions include:

- No arbitrary file read/write or program execution on the user’s device.
- Limited file access, typically requiring user interaction (like file selection).
- Access to devices like cameras or microphones only with explicit user permission.
- Isolation between different tabs/windows unless they share the same origin (domain, protocol, port), enforced by the Same Origin Policy.

While in-browser JavaScript can communicate with the originating server, it has restricted access to data from other domains unless explicit permission is granted through HTTP headers.

## What makes JavaScript unique?

JavaScript stands out for three main reasons:

1. Full integration with HTML/CSS.
2. Simplicity in handling basic tasks.
3. Universal support by all major browsers, enabled by default.

These attributes make JavaScript the most prevalent tool for creating browser interfaces. Moreover, it can be used for server-side programming and mobile application development.

## Languages “over” JavaScript

To cater to various developer needs, several languages have emerged that transpile (convert) to JavaScript. This allows developers to code in languages with different syntax or features while maintaining the ability to run their code in the browser. Some popular examples include:

- **CoffeeScript**: Adds syntactic sugar for clearer code, often favored by Ruby developers.
- **TypeScript**: Developed by Microsoft, it adds strict data typing for better management of complex systems.
- **Flow**: Also adds data typing but with a different approach, developed by Facebook.
- **Dart**: A standalone language with its own engine for non-browser environments, which can also transpile to JavaScript, developed by Google.
- **Brython**: A Python-to-JavaScript transpiler, allowing applications to be written in Python.
- **Kotlin**: A modern language targeting browsers or Node.js.

Understanding JavaScript remains essential, even when using these transpiled languages, as it forms the foundation of their functionality.

# What is JavaScript?

JavaScript is a programming language designed primarily for interacting with web page elements. Within web browsers, it consists of three main components:

1. **ECMAScript**: Provides core functionality.
2. **Document Object Model (DOM)**: Offers interfaces for manipulating web page elements.
3. **Browser Object Model (BOM)**: Provides APIs for interacting with the web browser itself.

JavaScript enhances web pages by adding interactivity, enabling features like form validation, interactive maps, and animated charts. When a web page loads, the JavaScript engine in the browser executes the JavaScript code, modifying the HTML and CSS to update the user interface dynamically.

### JavaScript Engine

The JavaScript engine is responsible for interpreting and executing JavaScript code within web browsers. It typically includes:

- **Parser**: Analyzes the code.
- **Compiler**: Converts the code into machine code.
- **Interpreter**: Executes the compiled code.

Notable JavaScript engines include:

- **V8**: Used in Chrome.
- **SpiderMonkey**: Used in Firefox.
- **JavaScriptCore**: Used in Safari.

Modern JavaScript engines are often implemented as just-in-time (JIT) compilers, which compile JavaScript to bytecode for better performance.

## Client-side vs. Server-side JavaScript

JavaScript operates in two primary environments:

- **Client-side JavaScript**: Executed in web browsers, enhancing user interaction and experience.
- **Server-side JavaScript**: Executed on the server. A popular environment for this is Node.js, which allows access to databases and file systems.

## JavaScript History

JavaScript was created in 1995 by Brendan Eich, a developer at Netscape. Initially named Mocha, it was later rebranded as LiveScript before finally being named JavaScript to capitalize on Java's popularity. The first official version, JavaScript 1.0, was introduced with Netscape Navigator 2.

In 1996, Microsoft introduced Internet Explorer 3, which included its own implementation called JScript, leading to two distinct versions in the market:

1. **JavaScript** in Netscape Navigator
2. **JScript** in Internet Explorer

As the language lacked standardization, the community pushed for a unified standard. In 1997, JavaScript 1.1 was proposed to ECMA, leading to the creation of the ECMA-262 standard and the establishment of ECMAScript.

# Popular JavaScript Code Editors

To edit JavaScript source code, you can use basic text editors like Notepad on Windows. However, for a more efficient coding experience, it’s beneficial to use a dedicated JavaScript code editor. These editors offer features that simplify coding, including:

- **Syntax highlighting**: Makes code easier to read by coloring different elements.
- **Indentation**: Helps maintain consistent formatting.
- **Autocomplete**: Speeds up coding by suggesting completions for code.
- **Brace matching**: Helps identify matching braces and parentheses.
- **Debugging tools**: Some editors include built-in debugging capabilities.

Here are some popular JavaScript code editors, all of which are free:

1. **Visual Studio Code**
2. **Atom**
3. **Notepad++**
4. **Vim**
5. **GNU Emacs**

For this tutorial, we will be using **Visual Studio Code** due to its powerful features and extensive community support.

# Web Development Tools

## What are Web Development Tools?

Web development tools, commonly referred to as **devtools**, are essential for testing and debugging JavaScript code. These tools are integrated into modern web browsers, making them easily accessible.

### Supported Browsers

Most popular browsers come with built-in devtools, including:

- **Google Chrome**
- **Firefox**
- **Edge**
- **Safari**
- **Opera**

### Features of Devtools

Devtools provide a range of functionalities for working with various web technologies, including:

- **HTML**: Inspect and modify the structure of web pages.
- **CSS**: Edit styles and see changes in real time.
- **DOM**: Interact with and manipulate the Document Object Model.
- **JavaScript**: Test scripts, log messages, and debug code.

### Opening the Console Tab

To view messages or debug your JavaScript code, you can open the Console tab in devtools. Here’s how to do it:

1. **Google Chrome**: Right-click on the page, select "Inspect," then navigate to the "Console" tab.
2. **Firefox**: Right-click on the page, select "Inspect Element," then click on the "Console" tab.
3. **Edge**: Right-click on the page, select "Inspect," then choose the "Console" tab.
4. **Safari**: Enable the "Develop" menu in Preferences, then select "Show JavaScript Console."
5. **Opera**: Right-click on the page, select "Inspect," and go to the "Console" tab.

Using the Console, you can log messages, run JavaScript commands, and debug issues effectively.

# JavaScript Hello World Example

## Summary

This tutorial will help you get started with JavaScript by demonstrating how to embed JavaScript code into an HTML page.

## Inserting JavaScript into an HTML Page

You can insert JavaScript into an HTML page using the `<script>` element in two ways:

1. **Embed JavaScript code directly in the HTML page.**
2. **Reference an external JavaScript code file.**

### Embedding JavaScript Code

While it's possible to place JavaScript code directly inside the `<script>` element, this method is mainly for testing or proof of concept. The JavaScript code is interpreted from top to bottom.

#### Example: Embedded JavaScript

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>JavaScript Hello World Example</title>
    <script>
      alert("Hello, World!");
    </script>
  </head>
  <body></body>
</html>
```

In this example, the `alert()` function displays the message "Hello, World!".

### Including an External JavaScript File

To include JavaScript from an external file, follow these steps:

1. Create a JavaScript file with a `.js` extension (e.g., `app.js`) and place it in a directory (commonly named `js`).
2. Use the `src` attribute in the `<script>` element to reference the external JavaScript file.

#### Contents of `app.js`

```javascript
alert("Hello, World!");
```

#### Example: External JavaScript in HTML

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>JavaScript Hello World Example</title>
    <script src="js/app.js"></script>
  </head>
  <body></body>
</html>
```

When you launch the `helloworld.html` file in a web browser, an alert will display "Hello, World!".

### Loading JavaScript from a Remote Server

You can also load a JavaScript file from a remote server, such as a Content Delivery Network (CDN), to improve page loading speed.

### Script Execution Order

If your page includes multiple external JavaScript files, they will be interpreted in the order they appear:

```html
<script src="js/service.js"></script>
<script src="js/app.js"></script>
```

In this case, `service.js` will be interpreted before `app.js`.

### Optimizing Loading with the `<script>` Element

To prevent a blank page during rendering when multiple JavaScript files are included, place the `<script>` tags just before the closing `</body>` tag:

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>JavaScript Hello World Example</title>
  </head>
  <body>
    <!-- end of page content here -->
    <script src="js/service.js"></script>
    <script src="js/app.js"></script>
  </body>
</html>
```

### The `async` and `defer` Attributes

You can modify how the browser loads and executes JavaScript files using the `async` and `defer` attributes, which apply only to external scripts.

#### `async` Attribute

The `async` attribute tells the browser to execute the JavaScript file asynchronously, without guaranteeing the order of execution:

```html
<script async src="service.js"></script>
<script async src="app.js"></script>
```

In this scenario, `app.js` might execute before `service.js`, so ensure there are no dependencies between them.

#### `defer` Attribute

The `defer` attribute requests that the script be executed after the HTML document has been parsed, even if it is placed in the `<head>` section:

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>JavaScript Defer Demonstration</title>
    <script defer src="defer-script.js"></script>
  </head>
  <body></body>
</html>
```

With the `defer` attribute, the script will wait until the browser has received the closing `</html>` tag before executing.

This ensures that the HTML is fully parsed before any script runs, preventing potential issues with accessing elements that haven't been rendered yet.

# Modern Markup for the `<script>` Tag

## Overview

The `<script>` tag has a few attributes that were commonly used in older code but are rarely necessary in modern development. Here’s a look at these attributes and some outdated practices:

### Attributes No Longer Commonly Used

1. **The `type` Attribute**:

   ```html
   <script type="text/javascript">
   ```

   - In the old HTML standard (HTML4), the `type` attribute was required to specify the scripting language, typically set to `text/javascript`.
   - In modern HTML (HTML5), the `type` attribute is optional as JavaScript is the default language. If specified, it can now indicate JavaScript modules, but that's an advanced topic covered later.

2. **The `language` Attribute**:
   ```html
   <script language="javascript">
   ```
   - This attribute was intended to specify the scripting language. It is now obsolete because JavaScript is the default language for the `<script>` tag, making the attribute unnecessary.

### Comments in Older Scripts

In some very old guides, you might encounter comments surrounding the JavaScript code inside the `<script>` tags:

```html
<script type="text/javascript">
  <!--
  // Your JavaScript code here
  //-->
</script>
```

- This practice was a workaround for old browsers that did not recognize the `<script>` tag. The comments hid the JavaScript code from these browsers.
- Today, this method is considered obsolete, as all modern browsers correctly process the `<script>` tag without any issues.

### Conclusion

In modern JavaScript development, you can simply use the `<script>` tag without the `type` or `language` attributes, and there's no need for legacy comments. This helps keep your code clean and compliant with current standards.

### Please note:

As a rule, only the simplest scripts are put into HTML. More complex ones reside in separate files. The benefit of a separate file is that the browser will download it and store it in its cache. Other pages that reference the same script will take it from the cache instead of downloading it, so the file is actually downloaded only once. That reduces traffic and makes pages faster.

Got it! Here's the original text you provided, but formatted as requested:

---

**JavaScript Syntax**  
Summary: In this tutorial, you will learn about JavaScript syntax, including whitespace, statements, identifiers, comments, expressions, and keywords.

### Whitespace

Whitespace refers to characters that provide the space between other characters. JavaScript has the following whitespace:

- Carriage return
- Space
- New Line
- Tab

The JavaScript engine ignores whitespace. However, you can use whitespace to format the code to make it easy to read and maintain.

The following JavaScript code doesn’t use whitespace:

```javascript
let formatted = true;
if (formatted) {
  console.log("The code is easy to read");
}
```

It is equivalent to the following code that uses whitespace, which is much easier to read:

```javascript
let formatted = true;

if (formatted) {
  console.log("The code is easy to read");
}
```

Note that JavaScript bundlers remove all whitespace from JavaScript files and put them into a single file for deployment. By doing this, JavaScript bundlers make the JavaScript code lighter and faster to load in web browsers.

### Statements

A statement is a piece of code that either declares a variable or instructs the JavaScript engine to perform a task. A simple statement is concluded by a semicolon (`;`).

Although the semicolon (`;`) is optional, you should always use it to terminate a statement.

For example, the following declares a variable and shows it to the console:

```javascript
let message = "Welcome to JavaScript";
console.log(message);
```

### Blocks

A block is a sequence of zero or more simple statements. A block is delimited by a pair of curly brackets `{}`. For example:

```javascript
if (window.localStorage) {
  console.log("The local storage is supported");
}
```

### Identifiers

An identifier is a name you choose for variables, parameters, functions, classes, etc.

An identifier name starts with a letter (a-z, or A-Z), an underscore(`_`), or a dollar sign (`$`) and is followed by a sequence of characters including (a-z, A-Z), numbers (0-9), underscores (`_`), and dollar signs (`$`).

Note that the letter is not limited to the ASCII character set and may include extended ASCII or Unicode, though it is not recommended.

Identifiers in JavaScript are case-sensitive. For example, `message` is different from `Message`.

### Comments

Comments allow you to include notes or hints in JavaScript code. When executing the code, the JavaScript engine ignores the comments.

JavaScript supports both single-line and block comments.

**Single-line comments:**  
A single-line comment starts with two forward-slashes (`//`). It turns all the text following the `//` on the same line into a comment. For example:

```javascript
// this is a single-line comment
```

**Block comments:**  
A delimited comment begins with a forward slash and asterisk (`/*`) and ends with the opposite (`*/`) as in the following example:

```javascript
/* This is a block comment
   that can span multiple lines */
```

### Expressions

An expression is a piece of code that evaluates to a value. For example:

```javascript
2 + 1;
```

The above expression returns `3`.

### Keywords & Reserved Words

JavaScript defines a list of reserved keywords that have specific uses. Consequently, you cannot use the reserved keywords as identifiers or property names due to the language rules.

The following table displays the JavaScript reserved words as defined in ECMA-262:

```plaintext
break    case    catch
continue debugger default
else     export  extends
function if      import
new      return  super
throw    try     null
void     while   with
class    delete  finally
in       switch  typeof
yield    const   do
for      instanceof this
var
```

In addition to the reserved keywords, ECMA-252 also defines a list of future reserved words that cannot be used as identifiers or property names:

```plaintext
enum       implements   let
protected  private      public
await      interface    package
implements public
```

### Summary

- Use whitespace, including carriage return, space, newline, and tab to format the code. The JavaScript engine disregards whitespace.
- Use a semicolon (`;`) to terminate a simple statement.
- Use curly braces (`{}`) to create a block that groups one or more simple statements.
- A single-line comment starts with `//` followed by text, while a block comment begins with `/*` and ends with `*/`. The JavaScript engine ignores comments.
- Identifiers are names you choose for variables, functions, classes, and so on.
- Avoid using reserved keywords for identifiers.

---

### The Modern Mode: `"use strict"`

For many years, JavaScript evolved without breaking existing code, preserving backward compatibility. While this avoided disruption, it also meant that early mistakes in the language were locked in place.

That changed in 2009 with ECMAScript 5 (ES5), which introduced new features and improved some existing ones. To keep older code functional, these improvements are off by default and must be activated with the `"use strict"` directive.

### What is `"use strict"`?

The `"use strict"` directive is simply a string: `"use strict"` or `'use strict'`. Placed at the top of a script, it enables the "modern" mode throughout the entire script.

Example:

```javascript
"use strict";

// Modern code here
```

You can also use `"use strict"` inside individual functions to activate strict mode only for that function, but it's more common to apply it to the whole script.

### Placement is Crucial

For `"use strict"` to take effect, it **must** appear at the very top of the script. If there’s any other code before it, strict mode won’t activate.

Incorrect:

```javascript
alert("some code");
// "use strict" below is ignored because it's not at the top

("use strict");
```

Only comments are allowed above the `"use strict"` directive.

### No Way to Disable `"use strict"`

Once you enable strict mode, you can’t disable it—there’s no `"no use strict"` directive. Once entered, strict mode remains in effect for the rest of the script.

### Using `"use strict"` in the Browser Console

The browser console does not apply strict mode by default. If you need strict mode for console experiments, you can add it manually:

1. Press `Shift + Enter` for multiple lines.
2. Start your code with `'use strict';`.

Example:

```javascript
'use strict'; <Shift + Enter for a newline>
//  ...your code
<Enter to run>
```

In case this doesn’t work in some browsers, you can wrap your code like this:

```javascript
(function () {
  "use strict";

  // Your code here
})();
```

### Should We Always Use `"use strict"`?

It’s generally recommended to start scripts with `"use strict"` to enforce modern standards. However, **classes** and **modules** in modern JavaScript automatically enable strict mode. If your code is structured this way, the `"use strict"` directive becomes redundant.

For now, including `"use strict"` at the top of your scripts is a good practice. As your codebase evolves to use classes and modules, you can omit it.

---

### JavaScript Variables

**Summary:** In this tutorial, you’ll learn about JavaScript variables and how to use variables to store values in an application.

A **variable** is a label that references a value like a number or string. Before using a variable, you need to declare it.

---

### Declare a Variable

To declare a variable, you use the `var` keyword followed by the variable name as follows:

```javascript
var message;
```

A variable name can be any valid identifier. By default, the `message` variable has a special value `undefined` if you have not assigned a value to it.

#### Rules for Variable Names:

- Variable names are **case-sensitive**. This means that `message` and `Message` are different variables.
- Variable names can only contain **letters**, **numbers**, **underscores**, or **dollar signs** and cannot contain spaces. Also, variable names must begin with a **letter**, an **underscore** (`_`), or a **dollar sign** (`$`).
- Variable names cannot use **reserved words**.
- By convention, variable names use **camelCase** like `message`, `yourAge`, and `myName`.

JavaScript is a **dynamically typed** language. This means you don’t need to specify the variable’s type in the declaration like in other statically typed languages such as **Java** or **C#**.

Starting in **ES6**, you can use the `let` keyword to declare a variable:

```javascript
let message;
```

It’s a good practice to use the `let` keyword to declare a variable. Later, you’ll learn the differences between `var` and `let`. But you should not worry about that for now.

---

### Initialize a Variable

Once you have declared a variable, you can **initialize** it with a value. To initialize a variable, you specify the variable name, followed by an **equals sign** (`=`) and a value.

For example, the following declares the `message` variable and initializes it with a literal string `"Hello"`:

```javascript
let message;
message = "Hello";
```

To declare and initialize a variable at the same time, you use the following syntax:

```javascript
let variableName = value;
```

For example, the following statement declares the `message` variable and initializes it with the literal string `"Hello"`:

```javascript
let message = "Hello";
```

JavaScript allows you to declare **two or more variables** using a single statement. To separate two variable declarations, use a **comma** (`,`):

```javascript
let message = "Hello",
  counter = 100;
```

Since JavaScript is a **dynamically typed language**, you can assign a value of a different type to a variable. However, this is **not recommended**. For example:

```javascript
let message = "Hello";
message = 100;
```

---

### Change a Variable

Once you initialize a variable, you can change its value by assigning a different value. For example:

```javascript
let message = "Hello";
message = "Bye";
```

---

### Undefined vs. Undeclared Variables

It’s important to distinguish between **undefined** and **undeclared** variables.

- An **undefined variable** is a variable that has been declared but has not been initialized with a value. For example:

```javascript
let message;
console.log(message); // undefined
```

In this example, the `message` variable is declared but not initialized. Therefore, the `message` variable is **undefined**.

- In contrast, an **undeclared variable** is a variable that has **not** been declared. For example:

```javascript
console.log(counter);
```

Output:

```
console.log(counter);
            ^
ReferenceError: counter is not defined
```

In this example, the `counter` variable has not been declared. Hence, accessing it causes a **ReferenceError**.

---

### Constants

A **constant** holds a value that doesn’t change. To declare a constant, you use the `const` keyword. When defining a constant, you need to initialize it with a value. For example:

```javascript
const workday = 5;
```

Once you define a constant, you **cannot change** its value.

The following example attempts to change the value of the `workday` constant to 2 and causes an error:

```javascript
workday = 2;
```

Error:

```
Uncaught TypeError: Assignment to constant variable.
```

Later, you’ll learn that the `const` keyword actually defines a **read-only reference** to a value in the constants tutorial.

---

### Summary

- A **variable** is a label that references a value.
- Use the `let` keyword to declare a variable.
- An **undefined** variable is one that has been declared but not initialized, while an **undeclared** variable is one that has not been declared.
- Use the `const` keyword to define a **read-only reference** to a value.

## JavaScript Data Types

**Summary:** In this tutorial, you will learn about JavaScript data types and their unique characteristics.

---

JavaScript has the **primitive data types**:

- `null`
- `undefined`
- `boolean`
- `number`
- `string`
- `symbol` – available from ES2015
- `bigint` – available from ES2020

and a **complex data type**: `object`.

---

### JavaScript Data Types

JavaScript is a **dynamically typed language**, meaning that a variable isn’t associated with a specific type. In other words, a variable can hold a value of different types. For example:

```javascript
let counter = 120; // counter is a number
counter = false; // counter is now a boolean
counter = "foo"; // counter is now a string
```

To determine the current type of the value stored in a variable, use the `typeof` operator:

```javascript
let counter = 120;
console.log(typeof counter); // "number"

counter = false;
console.log(typeof counter); // "boolean"

counter = "Hi";
console.log(typeof counter); // "string"
```

Output:

```
"number"
"boolean"
"string"
```

---

### The `undefined` Type

The `undefined` type is a primitive type that has only one value: `undefined`. By default, when a variable is declared but not initialized, it defaults to `undefined`.

Consider the following example:

```javascript
let counter;
console.log(counter); // undefined
console.log(typeof counter); // undefined
```

In this example, the `counter` variable hasn’t been initialized, so it is assigned the value `undefined`. The type of `counter` is also `undefined`.

It’s important to note that the `typeof` operator also returns `undefined` when you call it on a variable that hasn’t been declared:

```javascript
console.log(typeof undeclaredVar); // undefined
```

---

### The `null` Type

The `null` type is the second primitive data type that also has only one value: `null`. For example:

```javascript
let obj = null;
console.log(typeof obj); // object
```

The `typeof null` returns `object`, which is a known bug in JavaScript. A proposal to fix this was rejected due to the potential to break many existing sites.

JavaScript defines that `null` is equal to `undefined` as follows:

```javascript
console.log(null == undefined); // true
```

---

### The `number` Type

JavaScript uses the `number` type to represent both integers and floating-point numbers.

The following statement declares a variable and initializes its value with an integer:

```javascript
let num = 100;
```

To represent a floating-point number, include a decimal point followed by at least one number. For example:

```javascript
let price = 12.5;
let discount = 0.05;
```

JavaScript automatically converts a floating-point number into an integer if the number appears to be a whole number:

```javascript
let price = 200.0; // interpreted as an integer 200
```

To get the range of the `number` type, use `Number.MIN_VALUE` and `Number.MAX_VALUE`. For example:

```javascript
console.log(Number.MAX_VALUE); // 1.7976931348623157e+308
console.log(Number.MIN_VALUE); // 5e-324
```

Also, you can use `Infinity` and `-Infinity` to represent infinite numbers:

```javascript
console.log(Number.MAX_VALUE + Number.MAX_VALUE); // Infinity
console.log(-Number.MAX_VALUE - Number.MAX_VALUE); // -Infinity
```

---

### NaN (Not a Number)

`NaN` stands for **Not a Number**. It is a special numeric value that indicates an invalid number. For example, dividing a string by a number returns `NaN`:

```javascript
console.log("a" / 2); // NaN;
```

Two special characteristics of `NaN`:

1. Any operation with `NaN` returns `NaN`.
2. `NaN` does not equal any value, including itself.

Examples:

```javascript
console.log(NaN / 2); // NaN
console.log(NaN == NaN); // false
```

---

### The `string` Type

In JavaScript, a `string` is a sequence of zero or more characters. A string literal begins and ends with either single quotes (`'`) or double quotes (`"`). For example:

```javascript
let greeting = "Hi";
let message = "Bye";
```

If you want to use single or double quotes inside a string, use a backslash (`\`) to escape them:

```javascript
let message = "I'm also a valid string"; // escaping single quote
```

**JavaScript strings are immutable**, meaning they cannot be modified once created. However, you can create a new string from an existing one. For example:

```javascript
let str = "JavaScript";
str = str + " String";
```

In this example, the JavaScript engine creates a new string `'JavaScript String'` and destroys the original strings `'JavaScript'` and `' String'`.

If you attempt to modify a string directly, the original string remains unchanged:

```javascript
let s = "JavaScript";
s[0] = "j";
console.log(s); // 'JavaScript'
```

---

### The `boolean` Type

The `boolean` type has two literal values: `true` and `false`. For example:

```javascript
let inProgress = true;
let completed = false;

console.log(typeof completed); // boolean
```

JavaScript allows values of other types to be converted into boolean values using the `Boolean()` function. Here are the conversion rules:

| Type      | `true`                    | `false`      |
| --------- | ------------------------- | ------------ |
| string    | non-empty string          | empty string |
| number    | non-zero number, Infinity | 0, NaN       |
| object    | non-null object           | null         |
| undefined |                           | `undefined`  |

Examples:

```javascript
console.log(Boolean("Hi")); // true
console.log(Boolean("")); // false

console.log(Boolean(20)); // true
console.log(Boolean(Infinity)); // true
console.log(Boolean(0)); // false

console.log(Boolean({ foo: 100 })); // true (non-empty object)
console.log(Boolean(null)); // false
```

---

### The `symbol` Type

JavaScript introduced the `symbol` type in **ES6**. Unlike other primitive types, the `symbol` type does not have a literal form. To create a symbol, call the `Symbol` function:

```javascript
let s1 = Symbol();
```

The `Symbol` function creates a new unique value every time it is called:

```javascript
console.log(Symbol() == Symbol()); // false
```

---

### The `bigint` Type

The `bigint` type represents whole numbers larger than `2^53 - 1`. To form a `bigint` literal, append the letter `n` to the end of the number:

```javascript
let pageView = 9007199254740991n;
console.log(typeof pageView); // 'bigint'
```

---

### The `object` Type

In JavaScript, an `object` is a collection of properties, where each property is defined as a **key-value pair**.

Example of an empty object:

```javascript
let emptyObject = {};
```

Example of an object with properties:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};
```

A property name can be any string, and quotes are necessary if the name is not a valid identifier:

```javascript
let person = {
  "first-name": "John",
};
```

An object can contain other objects as properties:

```javascript
let contact = {
  firstName: "John",
  lastName: "Doe",
  email: "john.doe@example.com",
  phone: "(408)-555-9999",
  address: {
    building: "4000",
    street: "North 1st street",
    city: "San Jose",
    state: "CA",
    country: "USA",
  },
};
```

To access an object’s properties, use the **dot notation** (`.`) or the **array-like notation** (`[]`):

```javascript
console.log(contact.firstName); // 'John'
console.log(contact["phone"]); // '(408)-555-9999'
```

If a property doesn’t exist, it returns `undefined`:

```javascript
console.log(contact.age); // undefined
```

---

### Summary

JavaScript has the primitive types: `number`, `string`, `boolean`, `null`, `undefined`, `symbol`, `bigint`, and a complex type: `object`.

### JavaScript Numbers

**Summary:** In this tutorial, you’ll learn about JavaScript number types and how to use them effectively.

---

### Introduction to the JavaScript Number

JavaScript uses the `number` type to represent both **integers** and **floating-point values**. Under the hood, JavaScript adheres to the **IEEE-754** standard for representing numbers.

In **ES2020**, JavaScript introduced a new primitive type: `bigint`, which can represent numbers larger than \(2^{53} - 1\).

---

### Integer Numbers

Here’s how you can declare a variable holding a **decimal integer**:

```javascript
let counter = 100;
```

Integers in JavaScript can be expressed in several formats:

- **Decimal (base 10)**
- **Octal (base 8)**
- **Hexadecimal (base 16)**

JavaScript treats octal and hexadecimal numbers as **decimal** numbers during arithmetic operations.

#### Octal Numbers

Octal literals begin with a `0`, followed by digits ranging from 0 to 7:

```javascript
let num = 071; // Octal
console.log(num); // 57
```

If an invalid octal digit (8 or 9) is included, JavaScript treats the number as decimal:

```javascript
let num = 080; // Interpreted as decimal
console.log(num); // 80
```

To avoid ambiguity, **ES6** introduced the `0o` prefix for octal literals:

```javascript
let num = 0o71;
console.log(num); // 57
```

An invalid `0o` literal will throw a **SyntaxError**:

```javascript
let num = 0o80; // Error
```

#### Hexadecimal Numbers

Hexadecimal literals start with `0x` or `0X`, followed by digits 0-9 and letters a-f:

```javascript
let num = 0x1a;
console.log(num); // 26
```

---

### Floating-Point Numbers

A **floating-point** number must include a decimal point:

```javascript
let price = 9.99;
let tax = 0.08;
```

For large or small numbers, **E-notation** can be used, representing powers of 10:

```javascript
let amount = 3.14e7;
console.log(amount); // 31400000

let tinyAmount = 5e-7;
console.log(tinyAmount); // 0.0000005
```

Floating-point numbers are **precise up to 17 decimal places**, but their arithmetic may result in slight inaccuracies due to how they are represented:

```javascript
let amount = 0.2 + 0.1;
console.log(amount); // 0.30000000000000004
```

---

### Big Integers

With **ES2020**, JavaScript introduced `bigint` for numbers larger than \(2^{53} - 1\). To define a `bigint`, append `n` to the integer literal:

```javascript
let bigNumber = 9007199254740991n;
```

---

### Summary

- JavaScript uses the `number` type for integers and floating-point numbers.
- Octal numbers use `0o`, and hexadecimal numbers use `0x` prefixes.
- Floating-point numbers allow for large and small values, often expressed using **E-notation**.
- The `bigint` type represents extremely large integers.

---

**Did you know?** JavaScript numbers are all technically **double-precision floating-point** values—even for integers! This is why you sometimes see strange rounding issues in simple math operations.

### JavaScript Type Conversions

**Summary:** This tutorial covers JavaScript type conversions, focusing on converting values to strings, numbers, and booleans. These conversions happen automatically in many situations, but can also be done explicitly.

---

### String Conversion

String conversion happens when a value needs to be represented as text. Functions like `alert(value)` automatically convert values to strings. You can explicitly convert a value to a string using `String(value)`:

```javascript
let value = true;
console.log(typeof value); // boolean

value = String(value);
console.log(typeof value); // string
```

- Common conversions:
  - `false` becomes `"false"`
  - `null` becomes `"null"`
  - `true` becomes `"true"`

---

### Numeric Conversion

Numeric conversion occurs automatically during mathematical operations. For example:

```javascript
console.log("6" / "2"); // 3
```

To explicitly convert a value to a number, use `Number(value)`:

```javascript
let str = "123";
let num = Number(str);
console.log(typeof num); // number
```

- Conversion results:
  - `undefined` → `NaN`
  - `null` → `0`
  - `true` → `1`, `false` → `0`
  - Strings with valid numbers (ignoring whitespaces) become numbers. Otherwise, they return `NaN`.

Examples:

```javascript
console.log(Number("   123   ")); // 123
console.log(Number("123z")); // NaN
console.log(Number(true)); // 1
console.log(Number(false)); // 0
```

> **Note:** `null` converts to `0`, while `undefined` converts to `NaN`.

---

### Boolean Conversion

Boolean conversion determines whether a value is **truthy** or **falsy**. This happens in logical operations and condition checks, but can also be done explicitly using `Boolean(value)`:

```javascript
console.log(Boolean(1)); // true
console.log(Boolean(0)); // false
console.log(Boolean("hello")); // true
console.log(Boolean("")); // false
```

- **Falsy values** (convert to `false`):
  - `0`, `null`, `undefined`, `NaN`, `""` (empty string)
- **Truthy values** (convert to `true`):
  - Any non-empty string (including `"0"` and `" "`)
  - Non-zero numbers, objects, arrays, etc.

Examples:

```javascript
console.log(Boolean("0")); // true (non-empty string)
console.log(Boolean(" ")); // true (spaces are non-empty)
```

---

### Summary

The three primary type conversions in JavaScript are:

1. **String Conversion** – Automatically happens when outputting values. Use `String(value)` to convert explicitly.
2. **Numeric Conversion** – Occurs in mathematical operations. Use `Number(value)` to convert explicitly. Key rules:
   - `undefined` → `NaN`
   - `null` → `0`
   - `true`/`false` → `1`/`0`
   - Strings are converted based on content, with whitespaces trimmed.
3. **Boolean Conversion** – Happens in logical contexts. Use `Boolean(value)` to convert explicitly. Key rules:
   - Falsy: `0`, `null`, `undefined`, `NaN`, `""`
   - Truthy: All other values

These rules are easy to understand, though common pitfalls include:

- `undefined` becomes `NaN` as a number, not `0`.
- `"0"` and `" "` (space-only strings) are truthy in JavaScript.

---

### JavaScript Numeric Separator

**Summary:** The numeric separator (`_`) in JavaScript helps improve the readability of large numeric literals by visually separating groups of digits.

---

### Introduction to Numeric Separators

When working with large numbers, readability can be an issue. For example:

```javascript
const budget = 1000000000; // Hard to tell at a glance if it's 1 billion or 100 million
```

To make such numbers easier to read, JavaScript allows the use of underscores (`_`) as numeric separators:

```javascript
const budget = 1_000_000_000; // Clearly 1 billion
```

This separator can be used with **both integers and floating-point numbers**:

```javascript
let amount = 120_201_123.05; // Easier to read
let expense = 123_450; // 123,450
let fee = 12345_00; // 1,234,500
```

---

### Usage in Different Numeric Literals

Numeric separators can also be used with **fractional and exponent parts** of numbers:

```javascript
let amount = 0.000_001; // 1 millionth
```

Additionally, numeric separators can be applied to various number literals, including **bigint, binary, octal, and hexadecimal**:

- **BigInt**:

```javascript
const max = 9_223_372_036_854_775_807n; // Clear separation of digits
```

- **Binary**:

```javascript
let nibbles = 0b1011_0101_0101; // Easier to read binary
```

- **Octal**:

```javascript
let val = 0o1234_5670; // Octal value with separators
```

- **Hexadecimal**:

```javascript
let message = 0xd0_e0_f0; // Hex value with separators
```

---

### Key Points

- Numeric separators (`_`) do **not** affect the value of the number, only its readability.
- You can use them in **integer**, **floating-point**, **bigint**, **binary**, **octal**, and **hexadecimal** literals.

---

### Summary

Use underscores (`_`) as numeric separators to improve the readability of large numbers. They can be applied across different numeric formats without affecting the underlying value.

### A Quick Look at Octal and Binary Literals in ES6

**Summary**: ES6 introduced new ways to represent octal and binary literals, improving upon the numeric literal system from ES5.

---

### Octal Literals

In **ES5**, octal literals were represented by a **leading zero (0)** followed by digits from **0 to 7**:

```javascript
let a = 051;
console.log(a); // 41 (octal 51 is decimal 41)
```

- If a digit outside the valid range (0-7) is used, the leading zero is ignored, and the number is treated as decimal:

```javascript
let b = 058; // invalid octal
console.log(b); // 58 (treated as decimal)
```

- **Strict mode** disallows the use of octal literals with a leading zero, throwing an error instead:

```javascript
"use strict";
let b = 058; // SyntaxError: Invalid or unexpected token
```

In **ES6**, octal literals are now specified using the **`0o`** prefix:

```javascript
let c = 0o51;
console.log(c); // 41 (valid octal literal)
```

- Using an invalid digit in an octal literal (e.g., 8 or 9) results in a **SyntaxError**:

```javascript
let d = 0o58; // SyntaxError: Invalid or unexpected token
```

---

### Binary Literals

In **ES5**, JavaScript didn’t provide a way to represent binary literals directly. Instead, developers had to rely on `parseInt()` to parse binary strings:

```javascript
let e = parseInt("111", 2); // binary string to decimal
console.log(e); // 7
```

In **ES6**, binary literals are represented using the **`0b`** prefix followed by a sequence of binary digits (0 or 1):

```javascript
let f = 0b111;
console.log(f); // 7 (binary 111 is decimal 7)
```

---

### Summary

- **Octal literals** in ES6 start with **`0o`** and include digits from **0 to 7**.
- **Binary literals** in ES6 start with **`0b`** and include only **0** and **1**.

### JavaScript Boolean Type

**Summary**: This guide introduces the JavaScript boolean type, which holds two literal values: `true` and `false`.

---

### Introduction to the JavaScript Boolean Type

In JavaScript, the **boolean type** has two literal values: **`true`** and **`false`**.

#### Example:

```javascript
let completed = true;
let running = false;
```

- Boolean literals (`true` and `false`) are **case-sensitive**, meaning that identifiers like `True` or `False` are not valid boolean values.

---

### Casting Non-Boolean Values to Booleans

You can convert values of other types into booleans using the built-in **`Boolean()`** function.

#### Example:

```javascript
let error = "An error occurred";
let hasError = Boolean(error);
console.log(hasError); // true
```

#### How it works:

1. The `error` variable holds a non-empty string.
2. Using `Boolean(error)` converts this string to `true`, as non-empty strings are evaluated as `true` in JavaScript.

---

### Boolean Conversion Table

| Data Type     | Values Converted to `true` | Values Converted to `false` |
| ------------- | -------------------------- | --------------------------- |
| **String**    | Any non-empty string       | Empty string (`""`)         |
| **Number**    | Any non-zero number        | `0`, `NaN`                  |
| **Object**    | Any object                 | `null`                      |
| **undefined** | N/A                        | `undefined`                 |

- This conversion is important because certain statements, like the `if` statement, automatically cast non-boolean values to boolean values.

---

### Implicit Boolean Conversion in Conditional Statements

If a non-boolean value is used in an `if` statement, JavaScript implicitly casts the value to a boolean using the **`Boolean()`** function.

#### Example:

```javascript
let error = "An error occurred";

if (error) {
  console.log(error); // Output: "An error occurred"
}
```

In this case, since `error` holds a non-empty string, the `if` statement evaluates it as `true`.

---

If the `error` variable is set to an empty string (`""`), the condition will evaluate as `false`, and the code inside the `if` block will not execute:

```javascript
let error = "";
if (error) {
  console.log(error); // No output
}
```

---

### Summary

- JavaScript's boolean type has two values: **`true`** and **`false`**.
- Use the **`Boolean()`** function to explicitly convert other types into boolean values.
- Many statements (such as `if`) implicitly cast non-boolean values to booleans using the `Boolean()` function.

### JavaScript String Type

**Summary**: This guide covers the JavaScript string primitive type, explaining how to define strings, manipulate them, and perform operations like concatenation and comparison.

---

### Introduction to JavaScript Strings

- JavaScript **strings** are **primitive** values and are **immutable**. This means any modification results in a new string, without changing the original.
- **Defining strings**:
  You can create strings using **single quotes** (`'`) or **double quotes** (`"`).

  ```javascript
  let str1 = "Hi";
  let str2 = "Hello";
  ```

- **Template literals**: Introduced in ES6, these are defined using **backticks** (`` ` ``), allowing easier string interpolation and multi-line strings.

  ```javascript
  let name = `John`;
  let message = `Hi, I'm ${name}.`;
  console.log(message); // "Hi, I'm John."
  ```

---

### Escaping Special Characters

You can **escape special characters** using the backslash (`\`) character:

- **Line breaks**: `\r\n` (Windows) and `\n` (Unix)
- **Tab**: `\t`
- **Backslash**: `\\`
- **Single quote**: `\'`

Example:

```javascript
let str = "I'm a string!";
```

---

### String Properties and Methods

- **Length**: The `.length` property returns the length of the string.

  ```javascript
  let str = "Good Morning!";
  console.log(str.length); // 13
  ```

- **Accessing characters**: You can access characters in a string using array-like indexing:

  ```javascript
  let str = "Hello";
  console.log(str[0]); // "H"
  console.log(str[str.length - 1]); // "o"
  ```

---

### Concatenating Strings

- **Using the `+` operator**: Concatenate strings using the `+` operator.

  ```javascript
  let name = "John";
  let greeting = "Hello " + name;
  console.log(greeting); // "Hello John"
  ```

- **Using the `+=` operator**: You can append to a string piece by piece with `+=`.

  ```javascript
  let className = "btn";
  className += " btn-primary";
  className += " none";
  console.log(className); // "btn btn-primary none"
  ```

---

### Converting Values to Strings

- **Methods**:

  - `String(n)`
  - `n.toString()`
  - `"" + n` (concatenating an empty string)

  ```javascript
  let num = 123;
  let str = String(num); // "123"
  console.log(str);
  ```

  **Note**: `toString()` does not work on `undefined` and `null`.

---

### Boolean Conversion of Strings

When converting a string back to a boolean, only the empty string (`""`) converts to `false`. Any other non-empty string converts to `true`.

Example:

```javascript
let status = false;
let str = status.toString(); // "false"
let boolValue = Boolean(str); // true
```

Here, `"false"` is a non-empty string, so it's cast to `true`.

---

### Comparing Strings

Strings are compared **lexicographically** (based on character values). Comparison operators like `>`, `<`, `>=`, `<=`, and `==` work on strings.

Examples:

```javascript
let result = "a" < "b"; // true
let result2 = "a" < "B"; // false (because "B" has a lower character code than "a")
```

---

### Summary

- JavaScript strings are **immutable primitive values**.
- Use **single quotes**, **double quotes**, or **backticks** to define strings.
- **Escape special characters** with a backslash (`\`).
- Use the `.length` property to get the string's length.
- Compare strings using comparison operators (`>`, `>=`, `<`, `<=`, `==`).

### JavaScript Objects

**Summary**: In this tutorial, you will learn about JavaScript objects and how to manipulate object properties effectively.

---

### Introduction to JavaScript Objects

In JavaScript, an object is an unordered collection of key-value pairs. Each key-value pair is called a **property**.

- The key of a property is a string.
- The value of a property can be any data type: a string, number, array, or function.

The most common way to create an object is by using **object literal notation**:

```javascript
let empty = {};
```

To create an object with properties:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};
```

Here, the `person` object has two properties: `firstName` and `lastName`, with values `'John'` and `'Doe'`.

When an object has multiple properties, you separate them with commas.

---

### Accessing Properties

You can access object properties in two ways:

#### 1. **Dot Notation** ( `.` )

Syntax:  
`objectName.propertyName`

Example:

```javascript
person.firstName;
```

To display the properties:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};

console.log(person.firstName); // John
console.log(person.lastName); // Doe
```

#### 2. **Array-like Notation** ( `[]` )

Syntax:  
`objectName['propertyName']`

Example:

```javascript
console.log(person["firstName"]); // John
console.log(person["lastName"]); // Doe
```

If a property name contains spaces, you must use array-like notation:

```javascript
let address = {
    'building no': 3960,
    street: 'North 1st street',
    state: 'CA',
    country: 'USA'
};

address['building no'];  // Correct
address.'building no';   // Syntax Error
```

**Note**: Avoid using spaces in property names.

If you try to access a non-existent property, it returns `undefined`:

```javascript
console.log(address.district); // undefined
```

---

### Modifying the Value of a Property

To modify an object property:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};

person.firstName = "Jane";

console.log(person); // { firstName: 'Jane', lastName: 'Doe' }
```

---

### Adding a New Property

You can add new properties to an existing object:

```javascript
person.age = 25;
```

---

### Deleting a Property

To delete a property, use the `delete` operator:

```javascript
delete person.age;
```

Attempting to access the deleted property results in `undefined`.

---

### Checking if a Property Exists

To check if a property exists in an object, use the `in` operator:

```javascript
propertyName in objectName;
```

Example:

```javascript
let employee = {
  firstName: "Peter",
  lastName: "Doe",
  employeeId: 1,
};

console.log("ssn" in employee); // false
console.log("employeeId" in employee); // true
```

---

### Summary

- An object is a collection of key-value pairs.
- Use dot notation (`.`) or array-like notation (`[]`) to access object properties.
- Use the `delete` operator to remove properties.
- Use the `in` operator to check if a property exists in an object.

### JavaScript Primitive vs. Reference Values

**Summary**: In this tutorial, you’ll learn about two different types of values in JavaScript: **primitive** and **reference** values.

---

### JavaScript Value Types

JavaScript has two types of values:

1. **Primitive values**: Atomic pieces of data.
2. **Reference values**: Objects that may consist of multiple values.

---

### Stack and Heap Memory

When you declare variables, the JavaScript engine allocates memory in two locations:

- **Stack**: For static data (fixed size) like primitive values (`null`, `undefined`, `boolean`, `number`, `string`, `symbol`, `BigInt`).
- **Heap**: For reference values (objects) that can dynamically grow.

#### Example:

```javascript
let name = "John";
let age = 25;
```

Since `name` and `age` are primitive values, they are stored in the **stack**.

Objects, however, are stored on the **heap**. The following example allocates an object to the heap and links it with the `person` variable in the stack:

```javascript
let person = {
  name: "John",
  age: 25,
};
```

Here, the `person` variable on the stack refers to an object stored on the heap.

---

### Dynamic Properties

Reference values allow you to modify properties dynamically by adding, changing, or deleting them:

```javascript
let person = {
  name: "John",
  age: 25,
};

// Add property
person.ssn = "123-45-6789";

// Modify property
person.name = "John Doe";

// Delete property
delete person.age;

console.log(person); // { name: 'John Doe', ssn: '123-45-6789' }
```

In contrast, **primitive values** cannot have properties. Any attempt to add a property has no effect:

```javascript
let name = "John";
name.alias = "Knight";

console.log(name.alias); // undefined
```

---

### Copying Values

#### **Primitive Values**:

When you assign a primitive value from one variable to another, JavaScript creates a **copy** of that value. Changing one variable does not affect the other.

```javascript
let age = 25;
let newAge = age;

newAge = newAge + 1;

console.log(age, newAge); // 25, 26
```

#### **Reference Values**:

When you assign a reference value, both variables refer to the same object on the heap. Modifying one variable affects the other.

```javascript
let person = {
  name: "John",
  age: 25,
};

let member = person;
member.age = 26;

console.log(person); // { name: 'John', age: 26 }
console.log(member); // { name: 'John', age: 26 }
```

Since both `person` and `member` reference the same object, any changes to one are reflected in the other.

---

### Summary

- **Primitive values** are stored in the stack and cannot have dynamic properties.
- **Reference values** (objects) are stored in the heap and allow dynamic modification.
- Copying a **primitive value** creates a new, independent value.
- Copying a **reference value** makes two variables point to the same object, so changes in one affect the other.

### JavaScript Arrays

**Summary**: This tutorial introduces JavaScript arrays and covers basic operations you can perform on them.

---

### Introduction to JavaScript Arrays

- A JavaScript array is an **ordered list** of values.
- Each value is called an **element**, and its position in the array is specified by an **index**.

**Key Characteristics**:

1. Arrays can hold values of mixed types (e.g., numbers, strings, booleans).
2. Arrays are dynamic, meaning they can grow or shrink in size automatically.

---

### Creating JavaScript Arrays

There are two main ways to create arrays:

1. **Using the Array Constructor**:

   ```javascript
   let scores = new Array();
   let scores = Array(10); // Array with initial size
   let scores = new Array(9, 10, 8); // Array initialized with elements
   let signs = new Array("Red"); // Array with one element
   ```

   - The constructor creates an array.
   - You can omit the `new` operator, e.g., `let artists = Array();`.

2. **Using Array Literals** (Preferred):
   ```javascript
   let colors = ["red", "green", "blue"]; // Array with elements
   let emptyArray = []; // Empty array
   ```

---

### Accessing JavaScript Array Elements

- Arrays are **zero-indexed**: the first element has an index of `0`.
- You can access or modify elements by their index using square brackets:

```javascript
let mountains = ["Everest", "Fuji", "Nanga Parbat"];
console.log(mountains[0]); // 'Everest'
mountains[2] = "K2";
console.log(mountains); // ['Everest', 'Fuji', 'K2']
```

---

### Getting Array Size

- The `length` property returns the number of elements in an array:
  ```javascript
  let mountains = ["Everest", "Fuji", "Nanga Parbat"];
  console.log(mountains.length); // 3
  ```

---

### Basic Array Operations

1. **Add Element to the End**: Use `push()`.

   ```javascript
   let seas = ["Black Sea", "Caribbean Sea"];
   seas.push("Red Sea");
   console.log(seas); // ['Black Sea', 'Caribbean Sea', 'Red Sea']
   ```

2. **Add Element to the Beginning**: Use `unshift()`.

   ```javascript
   seas.unshift("Baltic Sea");
   console.log(seas); // ['Baltic Sea', 'Black Sea', 'Caribbean Sea', 'Red Sea']
   ```

3. **Remove Element from the End**: Use `pop()`.

   ```javascript
   let lastElement = seas.pop();
   console.log(lastElement); // 'Red Sea'
   ```

4. **Remove Element from the Beginning**: Use `shift()`.

   ```javascript
   let firstElement = seas.shift();
   console.log(firstElement); // 'Baltic Sea'
   ```

5. **Find Index of an Element**: Use `indexOf()`.

   ```javascript
   let index = seas.indexOf("Caribbean Sea");
   console.log(index); // 1
   ```

6. **Check if a Value is an Array**: Use `Array.isArray()`.
   ```javascript
   console.log(Array.isArray(seas)); // true
   ```

---

### Summary

- Arrays in JavaScript are ordered lists of values accessed by an index.
- They can hold mixed types and dynamically grow or shrink.
- Basic operations include adding, removing elements, and checking the array size or type.

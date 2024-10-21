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

A block is a sequence of zero or more simple statements. A block is delimited by a braces(pair of curly brackets) `{}`. For example:

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
- Use braces (`{}`) to create a block that groups one or more simple statements.
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

## JavaScript Variables

A **variable** is a label that references a value like a number or string. Before using a variable, you need to declare it.

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

## JavaScript Arithmetic Operators

**Introduction**  
JavaScript supports several standard arithmetic operators for performing calculations on numerical values, which can be either literals or variables.

#### Supported Operators

| Operator       | Sign |
| -------------- | ---- |
| Addition       | +    |
| Subtraction    | -    |
| Multiplication | \*   |
| Division       | /    |

### 1. Addition Operator (+)

The addition operator sums two values.

**Examples:**

```javascript
let sum = 10 + 20;
console.log(sum); // 30

let netPrice = 9.99,
  shippingFee = 1.99;
let grossPrice = netPrice + shippingFee;
console.log(grossPrice); // 11.98
```

**String Concatenation:**

- If both values are strings, they are concatenated.
- If one value is a string, the numeric value is converted to a string and concatenated.

```javascript
let result = "10" + "20"; // "1020"
let mixedResult = 10 + "20"; // "1020"
```

| First Value | Second Value | Result   | Explanation                    |
| ----------- | ------------ | -------- | ------------------------------ |
| NaN         | NaN          | NaN      | Any NaN results in NaN         |
| Infinity    | Infinity     | Infinity | Infinity + Infinity = Infinity |
| Infinity    | -Infinity    | NaN      | Infinity + -Infinity = NaN     |

### 2. Subtraction Operator (-)

Subtracts one number from another.

**Example:**

```javascript
let result = 30 - 10;
console.log(result); // 20
```

JavaScript converts non-numeric values to numbers before subtraction.

| First Value | Second Value | Result | Explanation               |
| ----------- | ------------ | ------ | ------------------------- |
| NaN         | NaN          | NaN    | Any NaN results in NaN    |
| Infinity    | Infinity     | NaN    | Infinity - Infinity = NaN |

### 3. Multiplication Operator (\*)

Multiplies two numbers.

**Example:**

```javascript
let result = 2 * 3;
console.log(result); // 6
```

Non-numeric values are converted to numbers.

| First Value | Second Value | Result | Explanation            |
| ----------- | ------------ | ------ | ---------------------- |
| NaN         | NaN          | NaN    | Any NaN results in NaN |
| Infinity    | 0            | NaN    | Infinity \* 0 = NaN    |

### 4. Division Operator (/)

Divides the first value by the second.

**Example:**

```javascript
let result = 20 / 10;
console.log(result); // 2
```

Non-numeric values are converted to numbers.

| First Value | Second Value | Result   | Explanation                          |
| ----------- | ------------ | -------- | ------------------------------------ |
| NaN         | NaN          | NaN      | Any NaN results in NaN               |
| Number      | 0            | Infinity | Division by zero results in Infinity |

### Using Operators with Objects

When using arithmetic operators with objects, JavaScript calls the `valueOf()` method if it exists; otherwise, it calls `toString()`.

**Example with `valueOf()`:**

```javascript
let energy = {
  valueOf() {
    return 100;
  },
};

console.log(energy - 10); // 90
console.log(energy + 100); // 200
console.log(energy / 2); // 50
console.log(energy * 1.5); // 150
```

**Example with `toString()`:**

```javascript
let energy = {
  toString() {
    return 50;
  },
};

console.log(energy - 10); // 40
console.log(energy + 100); // 150
console.log(energy / 2); // 25
console.log(energy * 1.5); // 75
```

### Summary

JavaScript arithmetic operators—addition (+), subtraction (-), multiplication (\*), and division (/)—allow for versatile numerical calculations, accommodating various data types through implicit type conversion.

## JavaScript Remainder Operator

### Introduction

The remainder operator (`%`) returns the remainder when one value (dividend) is divided by another value (divisor).

**Syntax:**

```javascript
dividend % divisor;
```

**Equation:**  
\[ \text{dividend} = \text{divisor} \times \text{quotient} + \text{remainder} \]  
Where \(|\text{remainder}| < |\text{divisor}|\)  
The sign of the remainder matches the sign of the dividend.

### Examples of the Remainder Operator

1. **Positive Dividend**

   ```javascript
   let remainder = 5 % -2;
   console.log(remainder); // 1

   remainder = 5 % 2;
   console.log(remainder); // 1
   ```

2. **Negative Dividend**

   ```javascript
   let remainder = -5 % 3;
   console.log(remainder); // -2

   remainder = -5 % -3;
   console.log(remainder); // -2
   ```

3. **Special Values**
   - **Infinity and Finite Number**
     ```javascript
     let remainder = Infinity % 2;
     console.log(remainder); // NaN
     ```
   - **Finite Number and Zero**
     ```javascript
     let remainder = 10 % 0;
     console.log(remainder); // NaN
     ```
   - **Both Dividend and Divisor as Infinity**
     ```javascript
     let remainder = Infinity % Infinity;
     console.log(remainder); // NaN
     ```
   - **Finite Number and Infinity**
     ```javascript
     let remainder = 10 % Infinity;
     console.log(remainder); // 10
     ```
   - **Zero Dividend**
     ```javascript
     let remainder = 0 % 10;
     console.log(remainder); // 0
     ```
   - **Non-Number Values**
     ```javascript
     let remainder = "10" % 3;
     console.log(remainder); // 1
     ```

### Using the Remainder Operator to Check Odd Numbers

To determine if a number is odd, use the remainder operator:

```javascript
let num = 13;
let isOdd = num % 2; // 1 if odd, 0 if even
```

You can define a function to check if a number is odd:

```javascript
function isOdd(num) {
  return num % 2 !== 0;
}

// Or using an arrow function
const isOdd = (num) => num % 2 !== 0;
```

### Remainder vs. Modulo Operator

In JavaScript, the remainder operator (`%`) differs from the modulo operator commonly found in other languages like Python.

To get a modulo in JavaScript:

```javascript
const mod = (dividend, divisor) => ((dividend % divisor) + divisor) % divisor;
```

**Example:**

```javascript
// Same sign
console.log("remainder:", 5 % 3); // 2
console.log("modulo:", mod(5, 3)); // 2

// Different signs
console.log("remainder:", -5 % 3); // -2
console.log("modulo:", mod(-5, 3)); // 1
```

### Summary

Use the JavaScript remainder operator (`%`) to obtain the remainder of a value divided by another value. Remember that its behavior can differ from the modulo operator in other programming languages.

## JavaScript Assignment Operators

### Introduction to JavaScript Assignment Operators

The assignment operator (`=`) assigns a value to a variable. The syntax is as follows:

```javascript
let a = b;
```

In this syntax, JavaScript evaluates the expression `b` first and assigns the result to the variable `a`.

**Example:**

```javascript
let counter = 0; // Initializes counter to zero
```

You can increase the counter variable by one and assign the result back:

```javascript
counter = counter + 1; // counter is now 1
```

To make the code more concise, you can use the `+=` operator:

```javascript
counter += 1; // Equivalent to counter = counter + 1
```

### Shorthand Assignment Operators

The following table illustrates shorthand assignment operators:

| Operator   | Meaning       | Description                                                             |
| ---------- | ------------- | ----------------------------------------------------------------------- | --- | -------------------------------------- |
| `a = b`    | `a = b`       | Assigns the value of `b` to `a`.                                        |
| `a += b`   | `a = a + b`   | Assigns the result of `a + b` to `a`.                                   |
| `a -= b`   | `a = a - b`   | Assigns the result of `a - b` to `a`.                                   |
| `a *= b`   | `a = a * b`   | Assigns the result of `a * b` to `a`.                                   |
| `a /= b`   | `a = a / b`   | Assigns the result of `a / b` to `a`.                                   |
| `a %= b`   | `a = a % b`   | Assigns the result of `a % b` to `a`.                                   |
| `a &= b`   | `a = a & b`   | Assigns the result of `a AND b` to `a`.                                 |
| `a         | = b`          | `a = a                                                                  | b`  | Assigns the result of `a OR b` to `a`. |
| `a ^= b`   | `a = a ^ b`   | Assigns the result of `a XOR b` to `a`.                                 |
| `a <<= b`  | `a = a << b`  | Assigns the result of `a` shifted left by `b` to `a`.                   |
| `a >>= b`  | `a = a >> b`  | Assigns the result of `a` shifted right (sign preserved) by `b` to `a`. |
| `a >>>= b` | `a = a >>> b` | Assigns the result of `a` shifted right by `b` to `a`.                  |

### Chaining Assignment Operators

You can assign a single value to multiple variables by chaining assignment operators. For example:

```javascript
let a = 10,
  b = 20,
  c = 30;
a = b = c; // All variables are now 30
```

In this case, JavaScript evaluates from right to left:

1. `b = c;` // `b` is 30
2. `a = b;` // `a` is also 30

### Summary

Use the assignment operator (`=`) to assign values to variables. You can chain assignment operators to assign a single value to multiple variables.

### JavaScript Unary Operators Explained

### Introduction to JavaScript Unary Operators

Unary operators operate on a single value. The following table summarizes the unary operators and their meanings:

| Unary Operator | Name                         | Meaning                                       |
| -------------- | ---------------------------- | --------------------------------------------- |
| `+x`           | Unary Plus                   | Converts a value into a number                |
| `-x`           | Unary Minus                  | Converts a value into a number and negates it |
| `++x`          | Increment Operator (Prefix)  | Adds one to the value                         |
| `--x`          | Decrement Operator (Prefix)  | Subtracts one from the value                  |
| `x++`          | Increment Operator (Postfix) | Adds one to the value                         |
| `x--`          | Decrement Operator (Postfix) | Subtracts one from the value                  |

### Unary Plus (+)

The unary plus operator converts values into numbers. For example:

```javascript
let x = 10;
let y = +x;
console.log(y); // 10
```

When applied to non-numeric values, it uses the following conversion rules:

| Value   | Result                                               |
| ------- | ---------------------------------------------------- |
| Boolean | `false` to `0`, `true` to `1`                        |
| String  | Converts to a number based on specific rules         |
| Object  | Calls `valueOf()` and/or `toString()` for conversion |

**Examples:**

```javascript
let s = "10";
console.log(+s); // 10

let f = false,
  t = true;
console.log(+f); // 0
console.log(+t); // 1
```

For objects:

```javascript
let person = {
  name: "John",
  toString: function () {
    return "25";
  },
};

console.log(+person); // 25

person = {
  name: "John",
  toString: function () {
    return "25";
  },
  valueOf: function () {
    return "30";
  },
};

console.log(+person); // 30
```

### Unary Minus (-)

The unary minus operator negates a number:

```javascript
let x = 10;
let y = -x;
console.log(y); // -10
```

It also converts non-numeric values into numbers before negation.

### Increment and Decrement Operators

Both the increment (`++`) and decrement (`--`) operators have prefix and postfix forms:

- **Prefix:** Changes the variable's value before the statement is evaluated.
- **Postfix:** Changes the variable's value after the statement is evaluated.

**Examples:**

**Prefix Increment:**

```javascript
let age = 25;
++age; // Now age is 26
```

**Postfix Increment:**

```javascript
let weight = 90;
let newWeight = weight++ + 5;
console.log(newWeight); // 95
console.log(weight); // 91
```

**Prefix Decrement:**

```javascript
let weight = 90;
--weight; // Now weight is 89
```

**Postfix Decrement:**

```javascript
let weight = 90;
let newWeight = weight-- + 5;
console.log(newWeight); // 95
console.log(weight); // 89
```

When using increment/decrement on non-numeric values, JavaScript first converts them into numbers using the same rules as the unary plus operator.

### Summary

- Unary operators work on one value.
- The unary plus (`+`) converts non-numeric values into numbers, while the unary minus (`-`) negates them.
- The prefix increment (`++x`) and decrement (`--x`) operators modify the variable before evaluation.
- The postfix increment (`x++`) and decrement (`x--`) operators modify the variable after evaluation.

### JavaScript Comparison Operators

### Introduction to JavaScript Comparison Operators

Comparison operators are used to compare two values, returning a Boolean value (`true` or `false`). The following table shows the comparison operators in JavaScript:

| Operator | Meaning                  |
| -------- | ------------------------ |
| `<`      | less than                |
| `>`      | greater than             |
| `<=`     | less than or equal to    |
| `>=`     | greater than or equal to |
| `==`     | equal to                 |
| `!=`     | not equal to             |

### Examples of Comparison Operators

```javascript
let r1 = 20 > 10; // true
let r2 = 20 < 10; // false
let r3 = 10 == 10; // true
```

### Comparing Different Types of Values

#### Compare Numbers

Comparison operators perform numeric comparisons directly on numbers:

```javascript
let a = 10,
  b = 20;
console.log(a >= b); // false
console.log(a == 10); // true
```

#### Compare Strings

JavaScript compares strings based on their character codes:

```javascript
let name1 = "alice",
  name2 = "bob";
console.log(name1 < name2); // true
```

Unexpected results can occur due to character codes:

```javascript
let f1 = "apple",
  f2 = "Banana";
console.log(f2 < f1); // true
```

To ensure accurate comparisons, convert strings to a common case:

```javascript
let result = f2.toLowerCase() < f1.toLowerCase();
console.log(result); // false
```

#### Compare Numbers with Other Types

When comparing a number with a non-numeric value, JavaScript converts the non-numeric value to a number:

```javascript
console.log(10 < "20"); // true
console.log(10 == "10"); // true
```

#### Compare Objects

For object comparisons, the `valueOf()` method is called if it exists, otherwise `toString()` is used:

```javascript
let apple = {
  valueOf: function () {
    return 10;
  },
};

let orange = {
  toString: function () {
    return "20";
  },
};
console.log(apple > 10); // false
console.log(orange == 20); // true
```

#### Compare Booleans

Booleans are converted to numbers: `true` to `1` and `false` to `0`:

```javascript
console.log(true > 0); // true
console.log(false < 1); // true
```

### Special Cases

#### Compare `null` and `undefined`

In JavaScript, `null` is equal to `undefined`:

```javascript
console.log(null == undefined); // true
```

#### Compare `NaN`

The comparison with `NaN` always returns `false`:

```javascript
console.log(NaN == 1); // false
console.log(NaN == NaN); // false
console.log(NaN != 1); // true
console.log(NaN != NaN); // true
```

### Strict Comparison Operators

JavaScript also provides strict comparison operators that do not convert types:

| Operator | Meaning          |
| -------- | ---------------- |
| `===`    | strict equal     |
| `!==`    | not strict equal |

**Example:**

```javascript
console.log("10" == 10); // true
console.log("10" === 10); // false
```

## An Introduction to JavaScript Logical Operators

### 1. The Logical NOT Operator (`!`)

The logical NOT operator (`!`) negates a boolean value or converts a non-boolean value to a boolean. Here’s how it works:

- If the value is `false`, `!` returns `true`.
- If the value is `true`, `!` returns `false`.

#### Examples:

```javascript
let eligible = false;
console.log(!eligible); // true

let value = 20;
console.log(!value); // false
console.log(!undefined); // true
console.log(!0); // true
console.log(!NaN); // true
```

#### Double Negation (`!!`)

The double negation (`!!`) converts a value to its true boolean value, similar to `Boolean()`:

```javascript
let counter = 10;
console.log(!!counter); // true
```

### 2. The Logical AND Operator (`&&`)

The logical AND operator (`&&`) evaluates two values and returns:

- The second value if the first value can be converted to `true`.
- The first value if it cannot.

#### Truth Table:

| a     | b     | a && b |
| ----- | ----- | ------ |
| true  | true  | true   |
| true  | false | false  |
| false | true  | false  |
| false | false | false  |

#### Examples:

```javascript
let eligible = true,
  required = false;
console.log(eligible && required); // false

eligible = true;
required = true;
console.log(eligible && required); // true;
```

#### Short-Circuit Evaluation

The `&&` operator stops evaluating as soon as it finds a `false`:

```javascript
let b = false;
let result = b && 1 / 0; // returns false, does not evaluate (1/0)
```

### 3. The Logical OR Operator (`||`)

The logical OR operator (`||`) evaluates two values and returns:

- The first value if it can be converted to `true`.
- The second value if the first cannot.

#### Truth Table:

| a     | b     | a \|\| b |
| ----- | ----- | -------- |
| true  | true  | true     |
| true  | false | true     |
| false | true  | true     |
| false | false | false    |

#### Examples:

```javascript
let eligible = true,
  required = false;
console.log(eligible || required); // true

eligible = false;
required = false;
console.log(eligible || required); // false;
```

#### Short-Circuit Evaluation

The `||` operator stops evaluating as soon as it finds a `true`:

```javascript
let b = true;
let result = b || 1 / 0; // returns true, does not evaluate (1/0)
```

### Chaining Logical Operators

You can chain multiple logical operators:

```javascript
let result = value1 && value2 && value3; // Returns first falsy or last truthy value
let result = value1 || value2 || value3; // Returns first truthy or last value
```

### Operator Precedence

The precedence of logical operators from highest to lowest is:

1. Logical NOT (`!`)
2. Logical AND (`&&`)
3. Logical OR (`||`)

### Summary

- The NOT operator (`!`) negates a boolean value; double negation (`!!`) converts a value to its boolean equivalent.
- The AND operator (`&&`) returns true only if both values are true.
- The OR operator (`||`) returns true if at least one value is true.
- Both `&&` and `||` are short-circuited.
- Logical operator precedence is `!`, `&&`, `||`.

### JavaScript Logical Assignment Operators

**Summary:**  
In this tutorial, you'll learn about JavaScript logical assignment operators introduced in ES2021: the logical OR assignment operator (`||=`), the logical AND assignment operator (`&&=`), and the nullish coalescing assignment operator (`??=`).

#### Overview of Logical Assignment Operators

| Logical Assignment Operator | Equivalent Logical Operation |
| --------------------------- | ---------------------------- |
| `x \|\|= y`                 | `x \|\| (x = y)`             |
| `x &&= y`                   | `x && (x = y)`               |
| `x ??= y`                   | `x ?? (x = y)`               |

### 1. Logical OR Assignment Operator (`||=`)

The logical OR assignment operator (`||=`) assigns the right operand to the left operand if the left operand is falsy:

```javascript
let title;
title ||= "untitled";

console.log(title); // Output: 'untitled'
```

In this example, since `title` is undefined (falsy), `'untitled'` is assigned to it.

#### Example with Truthy Value:

```javascript
let title = "JavaScript Awesome";
title ||= "untitled";

console.log(title); // Output: 'JavaScript Awesome'
```

Here, `title` is truthy, so the assignment does not occur.

You can also use it for default messages:

```javascript
document.querySelector(".search-result").textContent ||=
  "Sorry! No result found";
```

### 2. Logical AND Assignment Operator (`&&=`)

The logical AND assignment operator (`&&=`) assigns the right operand to the left operand only if the left operand is truthy:

```javascript
let person = {
  firstName: "Jane",
  lastName: "Doe",
};

person.lastName &&= "Smith";

console.log(person); // Output: { firstName: 'Jane', lastName: 'Smith' }
```

In this case, since `person.lastName` is truthy, it is updated to `'Smith'`.

### 3. Nullish Coalescing Assignment Operator (`??=`)

The nullish coalescing assignment operator (`??=`) assigns the right operand to the left operand if the left operand is null or undefined:

```javascript
let user = {
  username: "Satoshi",
};
user.nickname ??= "anonymous";

console.log(user); // Output: { username: 'Satoshi', nickname: 'anonymous' }
```

Here, since `user.nickname` is undefined, it gets assigned `'anonymous'`.

### Summary

- The **logical OR assignment** (`x ||= y`) assigns `y` to `x` if `x` is falsy.
- The **logical AND assignment** (`x &&= y`) assigns `y` to `x` if `x` is truthy.
- The **nullish coalescing assignment** (`x ??= y`) assigns `y` to `x` if `x` is null or undefined.

## JavaScript Nullish Coalescing Operator

#### Introduction to the Nullish Coalescing Operator

Introduced in ES2020, the nullish coalescing operator is represented by `??`. It accepts two values in the form:

```javascript
value1 ?? value2;
```

The operator returns `value2` if `value1` is either null or undefined. It behaves similarly to the following code block:

```javascript
const result = value1 !== null && value1 !== undefined ? value1 : value2;
```

A **nullish value** refers to either null or undefined.

#### Examples

1. **Using the Operator:**

   ```javascript
   const name = null ?? "John";
   console.log(name); // Output: 'John'
   ```

2. **Handling Undefined:**
   ```javascript
   const age = undefined ?? 28;
   console.log(age); // Output: 28
   ```

#### Why Use the Nullish Coalescing Operator?

The nullish coalescing operator avoids issues that can arise when using the logical OR operator (`||`), which treats 0 or empty strings as falsy values. For example:

```javascript
let count;
let result = count || 1;
console.log(result); // Output: 1
```

If `count` is 0:

```javascript
let count = 0;
let result = count || 1;
console.log(result); // Output: 1 (unexpected)
```

With `??`, you can correctly handle these cases:

```javascript
let result = count ?? 1; // count is 0, so result is 0
```

#### Short-Circuit Evaluation

Similar to logical operators, the nullish coalescing operator does not evaluate the second value if the first is neither null nor undefined:

```javascript
let result = 1 ?? console.log("Hi"); // No output
```

In contrast, if the first value is undefined:

```javascript
let result = undefined ?? console.log("Hi"); // Output: 'Hi'
```

#### Chaining with Logical Operators

You cannot directly combine `??` with logical AND (`&&`) or OR (`||`) operators due to syntax constraints:

```javascript
const result = null || undefined ?? 'OK'; // SyntaxError
```

To avoid this, use parentheses:

```javascript
const result = (null || undefined) ?? "OK";
console.log(result); // Output: 'OK'
```

### Summary

- The nullish coalescing operator (`??`) returns the second value if the first is null or undefined.
- It short-circuits and cannot be combined directly with logical AND or OR operators without parentheses.

## JavaScript Exponentiation Operator

#### Introduction to the Exponentiation Operator

Before the introduction of the exponentiation operator, you would typically use the `Math.pow()` method:

```javascript
Math.pow(base, exponent);
```

**Examples:**

```javascript
let result = Math.pow(2, 2);
console.log(result); // Output: 4

result = Math.pow(2, 3);
console.log(result); // Output: 8
```

With ECMAScript 2016, the exponentiation operator (`**`) was introduced, allowing a more concise syntax:

```javascript
x ** n;
```

This operator raises `x` to the power of `n`. Note that in JavaScript, the caret symbol (`^`) is used for the bitwise XOR operator, not for exponentiation.

**Examples:**

```javascript
let result = 2 ** 2;
console.log(result); // Output: 4

result = 2 ** 3;
console.log(result); // Output: 8
```

#### Working with Different Types

The exponentiation operator can work with both numbers and bigints:

```javascript
let result = 2n ** 3n;
console.log(result); // Output: 8n
```

You can also use the operator in an assignment expression:

```javascript
let x = 2;
x **= 4; // Equivalent to x = x ** 4
console.log(x); // Output: 16
```

#### Unary Operators and Syntax Errors

JavaScript does not allow a unary operator directly before the base number. For example:

```javascript
let result = -2 ** 3; // This causes a SyntaxError
```

**Error:**

```
Uncaught SyntaxError: Unary operator used immediately before exponentiation expression. Parenthesis must be used to disambiguate operator precedence.
```

To resolve this, use parentheses:

```javascript
let result = (-2) ** 3;
console.log(result); // Output: -8
```

### Summary

- The exponentiation operator (`**`) raises a number to the power of an exponent.
- It accepts values of type number or bigint.

## Introduction to the JavaScript `if` statement

The `if` statement in JavaScript executes a block of code if a condition is true. Here’s the syntax:

```javascript
if (condition) {
  statement;
}
```

The condition can be any expression that evaluates to `true` or `false`. If the condition evaluates to `true`, the code inside the `if` block is executed; otherwise, control moves to the next statement.

#### Example:

```javascript
let age = 18;
if (age >= 18) {
  console.log("You can sign up");
}
```

**Output:**

```
You can sign up
```

#### Important Note:

If there are multiple statements to execute, wrap them in curly braces `{}`:

```javascript
if (condition) {
  // multiple statements
}
```

This ensures code maintainability and avoids potential mistakes.

### Handling Non-Boolean Conditions

JavaScript implicitly converts non-Boolean values to `true` or `false` using the `Boolean()` function when evaluating conditions.

### Example Scenarios

**Example 1 (age check):**

```javascript
let age = 16;
if (age >= 18) {
  console.log("You can sign up");
}
// No output, as condition is false
```

### Nested `if` Statements

You can nest `if` statements, but it’s recommended to avoid it for simplicity. Here’s an example:

```javascript
let age = 16;
let state = "CA";

if (state === "CA") {
  if (age >= 16) {
    console.log("You can drive.");
  }
}
```

**Output:**

```
You can drive.
```

Instead of nesting, combine conditions with logical operators:

```javascript
if (state === "CA" && age >= 16) {
  console.log("You can drive.");
}
```

### Summary:

- Use the `if` statement to execute code if a condition is true.
- Always use curly braces, even for single statements, to avoid errors.
- Avoid nesting `if` statements when possible by using logical operators like `&&`.

## Introduction to the JavaScript `if...else` Statement

The `if...else` statement allows you to execute one block of code if a condition is true and another block if the condition is false. The syntax is:

```javascript
if (condition) {
  // block if true
} else {
  // block if false
}
```

The condition typically evaluates to a boolean value (`true` or `false`). If it's `true`, the code inside the `if` block runs; otherwise, the code inside the `else` block runs.

### Example 1 (age check):

```javascript
let age = 18;

if (age >= 18) {
  console.log("You can sign up.");
} else {
  console.log("You must be at least 18 to sign up.");
}
```

**Output:**

```
You can sign up.
```

In this example, the condition `age >= 18` is `true`, so the code inside the `if` block runs.

### Example 2 (condition is false):

```javascript
let age = 16;

if (age >= 18) {
  console.log("You can sign up.");
} else {
  console.log("You must be at least 18 to sign up.");
}
```

**Output:**

```
You must be at least 18 to sign up.
```

Since the condition `age >= 18` is `false`, the code inside the `else` block runs.

### Example 3 (using a logical operator):

You can also combine multiple conditions using logical operators like `&&` (AND).

```javascript
let age = 16;
let country = "USA";

if (age >= 16 && country === "USA") {
  console.log("You can get a driving license.");
} else {
  console.log("You are not eligible to get a driving license.");
}
```

**Output:**

```
You can get a driving license.
```

In this case, both conditions `age >= 16` and `country === 'USA'` are true, so the `if` block executes.

### Summary:

- The `if...else` statement runs one block of code if the condition is `true`, and another if the condition is `false`.
- You can combine conditions using logical operators like `&&` to create more complex expressions.

## Introduction to the JavaScript `if...else if` Statement

The `if...else if` statement allows you to check multiple conditions and execute the corresponding block of code when a condition is true. The syntax is:

```javascript
if (condition1) {
  // block 1
} else if (condition2) {
  // block 2
} else if (condition3) {
  // block 3
} else {
  // block if all conditions are false
}
```

- The conditions are evaluated from top to bottom.
- As soon as one condition is true, the corresponding block is executed, and the rest are ignored.
- If all conditions are false, the `else` block is executed.

### Example 1: Getting Month Name

```javascript
let month = 6;
let monthName;

if (month == 1) {
  monthName = "Jan";
} else if (month == 2) {
  monthName = "Feb";
} else if (month == 3) {
  monthName = "Mar";
} else if (month == 4) {
  monthName = "Apr";
} else if (month == 5) {
  monthName = "May";
} else if (month == 6) {
  monthName = "Jun";
} else if (month == 7) {
  monthName = "Jul";
} else if (month == 8) {
  monthName = "Aug";
} else if (month == 9) {
  monthName = "Sep";
} else if (month == 10) {
  monthName = "Oct";
} else if (month == 11) {
  monthName = "Nov";
} else if (month == 12) {
  monthName = "Dec";
} else {
  monthName = "Invalid month";
}

console.log(monthName);
```

**Output:**

```
Jun
```

In this example, the `month` variable is checked against numbers from 1 to 12. Since `month == 6` is true, it assigns `'Jun'` to `monthName`.

### Example 2: Calculating BMI

This example calculates the Body Mass Index (BMI) and determines the weight status using an `if...else if` statement.

```javascript
let weight = 70; // kg
let height = 1.72; // meters

// calculate BMI
let bmi = weight / (height * height);
let weightStatus;

if (bmi < 18.5) {
  weightStatus = "Underweight";
} else if (bmi >= 18.5 && bmi <= 24.9) {
  weightStatus = "Healthy Weight";
} else if (bmi >= 25 && bmi <= 29.9) {
  weightStatus = "Overweight";
} else {
  weightStatus = "Obesity";
}

console.log(weightStatus);
```

**Output:**

```
Healthy Weight
```

- The BMI is calculated using the formula `weight / (height * height)`.
- The `if...else if` statement checks the BMI and assigns the corresponding weight status based on predefined ranges.

### Summary:

- Use the `if...else if` statement to check multiple conditions.
- The first condition that evaluates to true will have its corresponding block executed.
- If none of the conditions are true, the `else` block (if provided) will execute.

## Introduction to the JavaScript Ternary Operator

The JavaScript ternary operator is a shorthand for the `if...else` statement, allowing you to write more concise code.

#### Example with `if...else`:

```javascript
let age = 18;
let message;

if (age >= 16) {
  message = "You can drive.";
} else {
  message = "You cannot drive.";
}

console.log(message);
```

#### Equivalent with Ternary Operator:

```javascript
let age = 18;
let message = age >= 16 ? "You can drive." : "You cannot drive.";

console.log(message);
```

### Syntax of the Ternary Operator

```javascript
condition ? expressionIfTrue : expressionIfFalse;
```

- **`condition`**: An expression that evaluates to `true` or `false`.
- **`expressionIfTrue`**: Executes if the condition is `true`.
- **`expressionIfFalse`**: Executes if the condition is `false`.

You can assign the result of the ternary operator directly to a variable:

```javascript
let result = condition ? expressionIfTrue : expressionIfFalse;
```

### JavaScript Ternary Operator Examples

#### Example 1: Using Ternary Operator for Multiple Statements

You can execute multiple operations with the ternary operator, separated by commas:

```javascript
let authenticated = true;
let nextURL = authenticated
  ? (alert("Redirecting to admin area"), "/admin")
  : (alert("Access denied"), "/403");

console.log(nextURL); // '/admin'
```

Here, the ternary operator evaluates both the alert and the URL redirection.

#### Example 2: Simplifying a Boolean Expression

A ternary operator can often be simplified:

```javascript
let locked = 1;
let canChange = locked != 1 ? true : false; // Original

// Simplified:
let canChange = locked != 1;
```

#### Example 3: Using Multiple Ternary Operators

You can nest ternary operators for multiple conditions:

```javascript
let speed = 90;
let message = speed >= 120 ? "Too Fast" : speed >= 80 ? "Fast" : "OK";

console.log(message); // 'Fast'
```

While you can nest ternary operators, be cautious—too many can make code harder to read.

### Summary

- The ternary operator (`?:`) simplifies conditional expressions.
- It’s useful for concise, readable code but should be avoided when dealing with complex logic or multiple `if...else` conditions.

## Introduction to the JavaScript `switch` Statement

The `switch` statement evaluates an expression and executes code based on matching conditions (cases).

#### Syntax:

```javascript
switch (expression) {
  case value1:
    statement1;
    break;
  case value2:
    statement2;
    break;
  default:
    statement;
}
```

- **Expression**: The value or result to compare.
- **Case**: Each case is compared using strict comparison (`===`).
- **Break**: Exits the `switch` after a case is executed. Omitting it results in "fall-through."
- **Default**: Executes if no case matches.

#### Equivalent `if...else`:

```javascript
if (expression === value1) {
  statement1;
} else if (expression === value2) {
  statement2;
} else {
  statement;
}
```

### JavaScript `switch` Examples

#### Example 1: Get Day of the Week

```javascript
let day = 3;
let dayName;

switch (day) {
  case 1:
    dayName = "Sunday";
    break;
  case 2:
    dayName = "Monday";
    break;
  case 3:
    dayName = "Tuesday";
    break;
  // ...other cases...
  default:
    dayName = "Invalid day";
}

console.log(dayName); // Tuesday
```

- **How it works**: Based on the `day` value, the corresponding day name is assigned. If no case matches, `default` runs.

#### Example 2: Get Days in a Month

```javascript
let year = 2016;
let month = 2;
let dayCount;

switch (month) {
  case 1:
  case 3:
  case 5:
  case 7:
  case 8:
  case 10:
  case 12:
    dayCount = 31;
    break;
  case 4:
  case 6:
  case 9:
  case 11:
    dayCount = 30;
    break;
  case 2:
    dayCount =
      year % 4 === 0 && (year % 100 !== 0 || year % 400 === 0) ? 29 : 28;
    break;
  default:
    dayCount = -1; // invalid month
}

console.log(dayCount); // 29
```

- **How it works**: The `switch` assigns the number of days based on the month. For February, it checks if the year is a leap year.

### Summary

- Use the `switch` statement to handle multiple conditions in a cleaner and more readable way than complex `if...else` chains.
- `switch` uses strict comparison (`===`).
- Include `break` to prevent fall-through.

## JavaScript `while` Loop

The `while` loop executes a block of code repeatedly as long as a condition evaluates to `true`.

#### Syntax:

```javascript
while (condition) {
  // statement
}
```

- The **condition** is evaluated before each iteration (pre-test loop).
- If the condition is `true`, the loop runs; otherwise, it exits.
- If the condition is `false` initially, the loop won't execute at all.

#### Example:

```javascript
let count = 1;
while (count < 10) {
  console.log(count);
  count += 2;
}
```

**Output**:

```
1
3
5
7
9
```

- **How it works**: The loop starts with `count = 1` and runs as long as `count` is less than 10. After each iteration, `count` increases by 2. The loop stops when `count` reaches 11.

### Key Points:

- The `while` loop is a pre-test loop: the condition is evaluated before each iteration.
- Use the `while` loop when the number of iterations is not known in advance.
- If you need to guarantee at least one execution, use a `do...while` loop instead.

### Summary

- The `while` loop continues as long as the condition is `true`. It stops once the condition becomes `false`.

## JavaScript `do...while` Loop

The `do...while` loop executes a block of code at least once before checking the condition.

#### Syntax:

```javascript
do {
  statement;
} while (condition);
```

- The loop executes the block **once** before evaluating the condition.
- If the condition is `true`, it repeats; otherwise, the loop exits.
- It’s called a post-test loop since the condition is evaluated after the loop body.

#### Example:

```javascript
let count = 0;
do {
  console.log(count);
  count++;
} while (count < 5);
```

**Output**:

```
0
1
2
3
4
```

- **How it works**: The loop starts with `count = 0`, prints it, and increments it by 1 each time. It stops once `count` reaches 5.

#### Example: Simple Number-Guessing Game

The following example generates a random number between 1 and 10 and allows the user to guess until they are correct:

```javascript
const MIN = 1,
  MAX = 10;
let secretNumber = Math.floor(Math.random() * (MAX - MIN + 1)) + MIN;
let guesses = 0,
  hint = "",
  number;

do {
  let input = prompt(`Enter a number between ${MIN} and ${MAX}` + hint);
  number = parseInt(input);
  guesses++;

  if (number > secretNumber) {
    hint = ", and less than " + number;
  } else if (number < secretNumber) {
    hint = ", and greater than " + number;
  } else {
    alert(`Bravo! You're correct after ${guesses} guess(es).`);
  }
} while (number !== secretNumber);
```

**How it works**:

- A secret number between 1 and 10 is generated.
- The user inputs a guess.
- Hints guide the user to guess the correct number, and the loop continues until they guess correctly.

### Key Points:

- The `do...while` loop guarantees at least one execution before checking the condition.
- Use it when you need the loop to run at least once, regardless of the initial condition.

### Summary:

The `do...while` loop is useful when you need a block to execute once before checking the condition. It repeats as long as the condition is `true`.

## JavaScript `for` Loop

The `for` loop in JavaScript allows you to create loops that execute a block of code based on various conditions.

#### Syntax:

```javascript
for (initializer; condition; iterator) {
  // statements
}
```

- **initializer**: Initializes variables for the loop, executed once at the beginning.
- **condition**: Boolean expression checked before each iteration. If `true`, the loop continues; if `false`, it stops.
- **iterator**: Executes after every iteration, typically updating the loop variable.

#### Key Characteristics:

- All three expressions are optional. You can omit any or all, and still have a valid loop.

#### Example 1: Basic `for` loop

```javascript
for (let i = 1; i < 5; i++) {
  console.log(i);
}
```

**Output**:

```
1
2
3
4
```

- The loop initializes `i = 1`, runs while `i < 5`, and increments `i` after each iteration.

#### Example 2: `for` loop without initializer

```javascript
let j = 1;
for (; j < 10; j += 2) {
  console.log(j);
}
```

**Output**:

```
1
3
5
7
9
```

- Here, the initializer (`let j = 1;`) is defined outside the loop, but the rest works the same.

#### Example 3: `for` loop without condition

```javascript
for (let j = 1; ; j += 2) {
  console.log(j);
  if (j > 10) {
    break;
  }
}
```

**Output**:

```
1
3
5
7
9
11
```

- The loop continues indefinitely unless terminated with a `break` statement.

#### Example 4: `for` loop without any expressions

```javascript
let j = 1;
for (;;) {
  if (j > 10) {
    break;
  }
  console.log(j);
  j += 2;
}
```

**Output**:

```
1
3
5
7
9
```

- All expressions are omitted. The loop runs until a `break` statement is encountered.

#### Example 5: `for` loop with empty body

```javascript
let sum = 0;
for (let i = 0; i <= 9; i++, sum += i);
console.log(sum);
```

**Output**:

```
45
```

- The loop calculates the sum of numbers from 0 to 9 without any body (`;` indicates the loop body is empty).

### Summary:

- The `for` loop is flexible, with optional expressions that can be customized as needed.
- Use it for scenarios where you know the number of iterations in advance.

## JavaScript `break` Statement

The `break` statement in JavaScript is used to exit a loop, `switch`, or label statement prematurely when certain conditions are met.

#### Basic Syntax:

```javascript
break [label];
```

- **label**: Optional. If used, it must correspond to a labeled statement.

### Using `break` in a `for` loop

The `break` statement terminates a `for` loop when a certain condition is met.

**Example:**

```javascript
for (let i = 0; i < 5; i++) {
  console.log(i);
  if (i === 2) {
    break;
  }
}
```

**Output:**

```
0
1
2
```

- The loop stops when `i === 2`.

### Using `break` in Nested Loops

When using a `break` inside a nested loop, it only breaks the innermost loop unless a labeled statement is used.

**Example without Label:**

```javascript
for (let i = 1; i <= 3; i++) {
  for (let j = 1; j <= 3; j++) {
    if (i + j === 4) {
      break;
    }
    console.log(i, j);
  }
}
```

**Output:**

```
1 1
1 2
2 1
```

- The `break` only stops the inner loop when `i + j === 4`.

**Example with Label:**

```javascript
outer: for (let i = 1; i <= 3; i++) {
  for (let j = 1; j <= 3; j++) {
    if (i + j === 4) {
      break outer;
    }
    console.log(i, j);
  }
}
```

**Output:**

```
1 1
1 2
```

- The `break` terminates the outer loop when `i + j === 4`, using the `outer` label.

### Using `break` in a `while` loop

The `break` statement can also exit a `while` loop when a condition is met.

**Example:**

```javascript
let i = 0;

while (i < 5) {
  i++;
  console.log(i);
  if (i === 3) {
    break;
  }
}
```

**Output:**

```
1
2
3
```

- The loop terminates when `i === 3`.

### Using `break` in a `do...while` loop

The `break` statement also works inside a `do...while` loop.

**Example:**

```javascript
let i = 0;

do {
  i++;
  console.log(i);
  if (i === 3) {
    break;
  }
} while (i < 5);
```

**Output:**

```
1
2
3
```

- The loop stops when `i === 3`.

### Summary

- Use `break` to exit loops like `for`, `while`, `do...while`, and `switch` early.
- When using `break` in nested loops, only the innermost loop is terminated unless a label is used to exit an outer loop.

## JavaScript `continue` Statement

The `continue` statement is used to skip the rest of the code in the current iteration of a loop and move to the next iteration.

#### Syntax:

```javascript
continue [label];
```

- **label**: Optional. A valid identifier that refers to a label for a statement, typically used in nested loops.

### Using `continue` in Loops

1. **In a `for` loop:**
   The `continue` statement skips the current iteration and jumps to the next iteration, executing the iterator expression.

   **Example:**

   ```javascript
   for (let i = 0; i < 10; i++) {
     if (i % 2 === 0) continue;
     console.log(i);
   }
   ```

   **Output:**

   ```
   1
   3
   5
   7
   9
   ```

   - The loop prints only odd numbers, skipping even ones.

2. **In a `while` loop:**
   The `continue` statement skips to the next iteration after checking the loop condition again.

   **Example:**

   ```javascript
   let i = 0;
   while (i < 10) {
     i++;
     if (i % 2 === 0) continue;
     console.log(i);
   }
   ```

   **Output:**

   ```
   1
   3
   5
   7
   9
   ```

### Using `continue` with Labels

Labels can be used with `continue` in nested loops to skip specific iterations in outer loops.

**Example:**

```javascript
outer: for (let i = 1; i < 4; i++) {
  for (let j = 1; j < 4; j++) {
    if (i + j === 3) continue outer;
    console.log(i, j);
  }
}
```

**Output:**

```
1 1
3 1
3 2
3 3
```

- When `i + j === 3`, the `continue outer` statement skips the rest of the inner loop and continues the outer loop.

### Summary

- The `continue` statement skips the current iteration in loops (`for`, `while`, `do...while`).
- When used in nested loops with labels, it skips the current iteration of the labeled loop.

## JavaScript Comma Operator

The **comma operator** in JavaScript allows you to evaluate two expressions in sequence and returns the result of the **rightmost** expression.

#### Syntax:

```javascript
leftExpression, rightExpression;
```

### Example:

```javascript
let result = (10, 10 + 20);
console.log(result); // Output: 30
```

- **Explanation**: The comma operator evaluates `10` and `10 + 20`. It returns the value of `10 + 20`, which is `30`.

### Another Example:

```javascript
let x = 10;
let y = (x++, x + 1);

console.log(x, y); // Output: 11 12
```

- **Explanation**:
  - First, `x++` increments `x` from 10 to 11.
  - Then, `x + 1` evaluates to `12` and assigns it to `y`.

### Practical Usage in Loops

The comma operator is most useful inside a `for` loop where multiple variables are updated in each iteration.

**Example: Displaying a matrix using a for loop:**

```javascript
let board = [1, 2, 3, 4, 5, 6, 7, 8, 9];
let s = "";

for (let i = 0, j = 1; i < board.length; i++, j++) {
  s += board[i] + " ";
  if (j % 3 == 0) {
    console.log(s);
    s = "";
  }
}
```

**Output:**

```
1 2 3
4 5 6
7 8 9
```

#### How It Works:

1. **Initialize Variables**:
   - `i` is used to iterate over the array `board`.
   - `j` counts the iterations to determine when to print a new row.
2. **Building the String**:
   - In each iteration, an element from `board` is added to the string `s`.
3. **Print Every 3 Elements**:
   - When `j % 3 == 0` (i.e., every third iteration), it prints the string `s` and resets it for the next row.

### Summary

- The **comma operator** evaluates two expressions and returns the value of the **rightmost** one.
- It is commonly used in `for` loops to update multiple variables simultaneously.
- Outside loops, it's better to avoid using the comma operator to keep the code clearer and more readable by splitting expressions into separate statements.

### JavaScript Functions

#### Introduction

Functions in JavaScript help organize code by encapsulating repetitive actions into reusable units. Built-in functions like `parseInt()` and `parseFloat()` are examples, but you can also create custom functions.

#### Declaring a Function

A function is declared using the `function` keyword followed by:

- **Function name** (camelCase, starting with verbs).
- **Parameters** (optional).
- **Function body**.

```javascript
function functionName(parameters) {
  // function body
}
```

Examples:

- Function without parameters:
  ```javascript
  function say() {}
  ```
- Function with one parameter:
  ```javascript
  function square(a) {}
  ```
- Function with two parameters:
  ```javascript
  function add(a, b) {}
  ```

#### Calling a Function

To invoke a function, use its name followed by parentheses and provide arguments if needed.

```javascript
functionName(arguments);
```

Example:

```javascript
function say(message) {
  console.log(message);
}

say("Hello"); // Output: Hello
```

#### Parameters vs. Arguments

- **Parameters**: Defined when declaring the function.
- **Arguments**: Values passed when calling the function.

Example:

```javascript
function say(message) {
  console.log(message); // 'message' is a parameter
}

say("Hello"); // 'Hello' is an argument
```

#### Returning a Value

Functions return `undefined` by default. To return a value explicitly, use the `return` statement.

```javascript
function add(a, b) {
  return a + b;
}

let sum = add(10, 20);
console.log("Sum:", sum); // Output: Sum: 30
```

#### Multiple Return Statements

A function can return different values based on conditions.

```javascript
function compare(a, b) {
  if (a > b) return -1;
  if (a < b) return 1;
  return 0;
}
```

#### Early Exit with `return`

You can exit a function early without returning a value.

```javascript
function say(message) {
  if (!message) return;
  console.log(message);
}
```

#### Returning Multiple Values

To return multiple values, package them in an array or object.

#### `arguments` Object

The `arguments` object allows access to all arguments passed to a function, even if not explicitly declared.

```javascript
function add() {
  let sum = 0;
  for (let i = 0; i < arguments.length; i++) {
    sum += arguments[i];
  }
  return sum;
}

console.log(add(1, 2)); // Output: 3
console.log(add(1, 2, 3, 4, 5)); // Output: 15
```

#### Function Hoisting

Functions can be called before their declaration due to hoisting.

```javascript
showMe();

function showMe() {
  console.log("Hoisting example");
}
```

#### Summary

- Declare functions with the `function` keyword.
- Call a function using `functionName()`.
- Functions return `undefined` by default unless a value is returned.
- The `arguments` object provides access to all passed arguments.
- JavaScript hoists function declarations, allowing them to be called before they're defined.

## **JavaScript Functions as First-Class Citizens**

#### **Overview**

In JavaScript, functions are considered **first-class citizens**. This means that functions can:

1. Be stored in variables.
2. Be passed as arguments to other functions.
3. Be returned from other functions.

These capabilities allow for highly flexible and dynamic code.

---

#### **1. Storing Functions in Variables**

You can store a function in a variable and call it just like you would with a regular function.

```javascript
function add(a, b) {
  return a + b;
}

let sum = add; // Store the 'add' function in a variable
```

Now, you can call the `add()` function either directly or through the `sum` variable:

```javascript
let result1 = add(10, 20); // Using original function
let result2 = sum(10, 20); // Using the variable that stores the function
console.log(result1, result2); // Output: 30, 30
```

---

#### **2. Passing Functions as Arguments**

Since functions are values, you can pass them as arguments to other functions. This is useful for defining custom behavior in higher-order functions.

Example:

```javascript
function average(a, b, fn) {
  return fn(a, b) / 2;
}

function add(a, b) {
  return a + b;
}

let result = average(10, 20, add); // Passing the 'add' function as an argument
console.log(result); // Output: 15
```

---

#### **3. Returning Functions from Other Functions**

Functions can also be returned from other functions, enabling dynamic generation of new functions based on input.

Example:

```javascript
function compareBy(propertyName) {
  return function (a, b) {
    let x = a[propertyName];
    let y = b[propertyName];
    if (x > y) return 1;
    if (x < y) return -1;
    return 0;
  };
}

let products = [
  { name: "iPhone", price: 900 },
  { name: "Samsung Galaxy", price: 850 },
  { name: "Sony Xperia", price: 700 },
];

// Sorting by 'name'
products.sort(compareBy("name"));
console.table(products);

// Sorting by 'price'
products.sort(compareBy("price"));
console.table(products);
```

Output for sorted products:

```
Products sorted by name:
┌─────────┬──────────────────┬───────┐
│ (index) │       name       │ price │
├─────────┼──────────────────┼───────┤
│    0    │ 'Samsung Galaxy' │  850  │
│    1    │  'Sony Xperia'   │  700  │
│    2    │     'iPhone'     │  900  │
└─────────┴──────────────────┴───────┘

Products sorted by price:
┌─────────┬──────────────────┬───────┐
│ (index) │       name       │ price │
├─────────┼──────────────────┼───────┤
│    0    │  'Sony Xperia'   │  700  │
│    1    │ 'Samsung Galaxy' │  850  │
│    2    │     'iPhone'     │  900  │
└─────────┴──────────────────┴───────┘
```

---

#### **4. Example: Conversion Functions**

You can create functions to handle conversions between different units and pass them to other functions.

```javascript
function cmToIn(length) {
  return length / 2.54;
}

function inToCm(length) {
  return length * 2.54;
}

function convert(fn, length) {
  return fn(length);
}

let inches = convert(cmToIn, 10);
console.log(inches); // Output: 3.937

let cm = convert(inToCm, 10);
console.log(cm); // Output: 25.4
```

In this example, `convert()` is a generic function that takes another function (`cmToIn` or `inToCm`) as an argument and applies it to the provided length.

---

#### **5. Function Hoisting**

In JavaScript, function declarations are "hoisted," meaning you can call a function before its declaration.

Example:

```javascript
showMessage(); // Works even though it's called before its declaration

function showMessage() {
  console.log("Function hoisting example");
}
```

This works because JavaScript moves the function declaration to the top of the scope before the code execution begins.

---

#### **Summary**

- **First-class citizens**: Functions in JavaScript can be assigned to variables, passed as arguments, and returned from other functions.
- **Storing in variables**: Functions can be referenced and called via variables.
- **Passing as arguments**: Functions can be passed to other functions for dynamic behavior.
- **Returning functions**: Functions can return other functions, allowing for flexible and dynamic programming.
- **Hoisting**: Function declarations are hoisted, so they can be called before their actual declaration in the code.

## JavaScript Anonymous Functions

### Introduction to JavaScript Anonymous Functions

An anonymous function is a function without a name. It can be defined like this:

```javascript
(function () {
  //...
});
```

The parentheses `()` around the function make it an expression that returns a function object. Without them, a syntax error would occur.

Since an anonymous function is not accessible after its creation, you often need to assign it to a variable, like this:

```javascript
let show = function () {
  console.log("Anonymous function");
};

show();
```

In this example, the anonymous function is assigned to the `show` variable, making it callable. There’s no need to wrap the function in parentheses in this case.

### Using Anonymous Functions as Arguments

Anonymous functions are often passed as arguments to other functions. For example:

```javascript
setTimeout(function () {
  console.log("Execute later after 1 second");
}, 1000);
```

Here, the anonymous function is passed to the `setTimeout()` function, which executes it after one second.

### Immediately Invoked Function Expression (IIFE)

An Immediately Invoked Function Expression (IIFE) is a function that is executed right after it is created. For example:

```javascript
(function () {
  console.log("IIFE");
})();
```

**Output:**

```
IIFE
```

The function is executed immediately because of the trailing parentheses `()`.

You can also pass arguments to an IIFE, like this:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};

(function (person) {
  console.log(person.firstName + " " + person.lastName);
})(person);
```

**Output:**

```
John Doe
```

### Arrow Functions

ES6 introduced arrow functions, which provide a concise syntax for writing anonymous functions. For example:

```javascript
let show = function () {
  console.log("Anonymous function");
};
```

This can be shortened to:

```javascript
let show = () => console.log("Anonymous function");
```

Similarly, this function:

```javascript
let add = function (a, b) {
  return a + b;
};
```

Can be written as:

```javascript
let add = (a, b) => a + b;
```

### Summary

- Anonymous functions are functions without names.
- They can be used as arguments to other functions or in Immediately Invoked Function Expressions (IIFE).
- Arrow functions provide a shorthand syntax for anonymous functions introduced in ES6.

## Understanding JavaScript Pass-By-Value

### Introduction

Before diving into pass-by-value, it's important to understand the difference between primitive values and reference values in JavaScript.

### JavaScript Pass-By-Value vs. Pass-By-Reference

In JavaScript, all function arguments are always passed by value. This means that JavaScript copies the values of the variables into the function arguments. Any changes made to the arguments inside the function do not affect the original variables outside the function.

If function arguments were passed by reference, changes made to the variables passed into the function would be reflected outside the function. However, this is not the case in JavaScript.

### Pass-By-Value of Primitive Values

Let's consider the following example:

```javascript
function square(x) {
  x = x * x;
  return x;
}

let y = 10;
let result = square(y);

console.log(result); // 100
console.log(y); // 10 -- no change
```

**How the Script Works:**

1. The `square()` function is defined to accept an argument `x`, which is then squared.
2. The variable `y` is declared and initialized to `10`.
3. The variable `y` is passed into the `square()` function. JavaScript copies the value of `y` (which is `10`) to the parameter `x`.
4. Inside the `square()` function, the variable `x` is modified. However, this does not affect the original variable `y` because `x` and `y` are separate variables.
5. After the function completes, the value of `y` remains `10`.

If JavaScript used pass-by-reference, the variable `y` would have changed to `100` after the function call.

### Pass-By-Value of Reference Values

It’s less obvious that reference values are also passed by value. Consider the following example:

```javascript
let person = {
  name: "John",
  age: 25,
};

function increaseAge(obj) {
  obj.age += 1;
}

increaseAge(person);

console.log(person);
```

**How the Script Works:**

1. A variable `person` is defined, referencing an object with properties `name` and `age`.
2. The `increaseAge()` function is defined to accept an object `obj` and increments its `age` property by one.
3. The `person` object is passed to the `increaseAge()` function.
4. Internally, JavaScript creates a reference `obj` that points to the same object that `person` references.
5. The `age` property is modified through the `obj` reference.

It appears that JavaScript passes objects by reference since changes to the object reflect outside the function. However, the reality is that you are passing the reference itself by value, not the actual object. Thus, while you can modify the properties of the object, you cannot change the reference to point to a new object:

```javascript
let person = {
  name: "John",
  age: 25,
};

function increaseAge(obj) {
  obj.age += 1;

  // Reference another object
  obj = { name: "Jane", age: 22 };
}

increaseAge(person);

console.log(person);
```

**Output:**

```
{ name: 'John', age: 26 }
```

**Explanation:**

1. The `increaseAge()` function modifies the `age` property via the `obj` argument.
2. It then attempts to make `obj` reference another object.
3. However, the original `person` reference still points to the original object, which now has an `age` property of `26`.

### Summary

- JavaScript passes all arguments to functions by value.
- Function arguments are treated as local variables within the function. This means any modifications to primitive values do not affect the originals, while properties of objects can be modified, but the object reference itself cannot be changed outside the function.

## JavaScript Recursive Function

### Introduction to JavaScript Recursive Functions

A recursive function is a function that calls itself until it reaches a stopping condition. This technique is called recursion. For example, a function called `recurse()` is considered recursive if it calls itself within its body:

```javascript
function recurse() {
  // ...
  recurse();
  // ...
}
```

### Structure of a Recursive Function

A recursive function must always have a condition to stop calling itself; otherwise, it will call itself indefinitely. The general structure looks like this:

```javascript
function recurse() {
  if (condition) {
    // stop calling itself
    //...
  } else {
    recurse();
  }
}
```

Recursive functions are often used to break down large problems into smaller ones and are commonly found in data structures like binary trees and graphs, as well as algorithms such as binary search and quicksort.

### JavaScript Recursive Function Examples

#### 1. A Simple JavaScript Recursive Function Example

Suppose you need to develop a function that counts down from a specified number to 1. For example, to count down from 3 to 1:

**Initial Function:**

```javascript
function countDown(fromNumber) {
  console.log(fromNumber);
}

countDown(3);
```

This will output only the number 3. To count down, you can show the number and call `countDown()` with the next number.

**Recursive Function:**

```javascript
function countDown(fromNumber) {
  console.log(fromNumber);
  countDown(fromNumber - 1); // Recursive call
}

countDown(3); // This will result in an error due to lack of stopping condition
```

The call stack will exceed, resulting in the error:

```
Uncaught RangeError: Maximum call stack size exceeded.
```

**Adding a Stop Condition:**

To prevent the function from running indefinitely, add a condition to stop when reaching zero:

```javascript
function countDown(fromNumber) {
  console.log(fromNumber);

  let nextNumber = fromNumber - 1;

  if (nextNumber > 0) {
    countDown(nextNumber);
  }
}

countDown(3);
```

**Output:**

```
3
2
1
```

The function works as expected now.

**Potential Error with Function Reference:**

If you set the function name to `null` somewhere in the code, it will stop working:

```javascript
let newYearCountDown = countDown;
countDown = null; // Set countDown to null
newYearCountDown(10); // This will cause an error
```

**Error:**

```
Uncaught TypeError: countDown is not a function
```

**How to Fix:**

You can use a named function expression to prevent this issue:

```javascript
let countDown = function f(fromNumber) {
  console.log(fromNumber);

  let nextNumber = fromNumber - 1;

  if (nextNumber > 0) {
    f(nextNumber); // Use the named function expression
  }
};

let newYearCountDown = countDown;
countDown = null; // Set countDown to null
newYearCountDown(10); // This will work now
```

#### 2. Calculate the Sum of n Natural Numbers Example

You can also use recursion to calculate the sum of natural numbers from 1 to n. The recursive formula is:

- `sum(n)=n+sum(n−1)`

**Recursive Function:**

```javascript
function sum(n) {
  if (n <= 1) {
    return n; // Base case
  }
  return n + sum(n - 1); // Recursive case
}
```

**How it Works:**

- **Base Case:** The function checks if `n` is less than or equal to 1. If true, it returns `n`.
- **Recursive Case:** If not, it calculates `sum(n)=n+sum(n−1)`, effectively reducing the problem.

For example, calling `sum(3)` proceeds as follows:

1. Checks if 3 is less than or equal to 1 (base case not met).
2. Calculates \( 3 + \text{sum}(2) \).
3. Calls `sum(2)`, which calculates \( 2 + \text{sum}(1) \).
4. Finally, `sum(1)` returns 1 (base case reached).
5. Unwinds to give \( 2 + 1 = 3 \), then \( 3 + 3 = 6 \).

**Termination:**

Recursion reduces the problem until it reaches the base case, at which point it unwinds to provide the final result.

### Summary

- A recursive function is a function that calls itself until it reaches a stopping condition.
- Every recursive function should have a base case to prevent infinite recursion.

## JavaScript Default Parameters

## Summary

In this tutorial, you will learn how to handle JavaScript default parameters in ES6.

## TL;DR

```javascript
function say(message = "Hi") {
  console.log(message);
}

say(); // 'Hi'
say("Hello"); // 'Hello'
```

The default value of the `message` parameter in the `say()` function is 'Hi'.

### Understanding Arguments vs. Parameters

- **Parameters**: These are the names specified in the function declaration.
- **Arguments**: These are the actual values passed into the function.

For example, in the `add()` function:

```javascript
function add(x, y) {
  return x + y;
}

add(100, 200);
```

Here, `x` and `y` are parameters, while `100` and `200` are arguments.

## Setting JavaScript Default Parameters for a Function

In JavaScript, parameters have a default value of `undefined`. If you don’t pass arguments to the function, its parameters will default to `undefined`.

### Example

```javascript
function say(message) {
  console.log(message);
}

say(); // undefined
```

In this case, since no argument is passed, `message` is `undefined`.

### Providing Default Values

You can use a ternary operator to set a default value if the parameter is undefined:

```javascript
function say(message) {
  message = typeof message !== "undefined" ? message : "Hi";
  console.log(message);
}
say(); // 'Hi'
```

With ES6, you can easily set default values like this:

```javascript
function say(message = "Hi") {
  console.log(message);
}

say(); // 'Hi'
say(undefined); // 'Hi'
say("Hello"); // 'Hello'
```

## How It Works

1. If no argument is passed to `say()`, `message` takes the default value 'Hi'.
2. If `undefined` is passed, `message` also takes the default value 'Hi'.
3. If a string ('Hello') is passed, `message` takes the value 'Hello'.

## More Examples of JavaScript Default Parameters

### 1. Passing Undefined Arguments

Here's a function that creates a new `<div>` element:

```javascript
function createDiv(
  height = "100px",
  width = "100px",
  border = "solid 1px red"
) {
  let div = document.createElement("div");
  div.style.height = height;
  div.style.width = width;
  div.style.border = border;
  document.body.appendChild(div);
  return div;
}
createDiv(); // Uses default values
createDiv(undefined, undefined, "solid 5px blue"); // Uses default height and width
```

### 2. Evaluating Default Parameters

JavaScript evaluates default arguments at the time of the function call:

```javascript
function put(toy, toyBox = []) {
  toyBox.push(toy);
  return toyBox;
}

console.log(put("Toy Car")); // -> ['Toy Car']
console.log(put("Teddy Bear")); // -> ['Teddy Bear'], not ['Toy Car', 'Teddy Bear']
```

### Using Function Return Values as Default Parameters

You can set a parameter's default value to the result of a function:

```javascript
function today() {
  return new Date().toLocaleDateString("en-US");
}

function date(d = today()) {
  console.log(d);
}
date(); // Outputs today's date
```

### Making Arguments Mandatory

You can throw an error if required arguments are missing:

```javascript
function requiredArg() {
  throw new Error("The argument is required");
}

function add(x = requiredArg(), y = requiredArg()) {
  return x + y;
}

add(10); // Throws error
add(10, 20); // OK
```

### 3. Using Other Parameters in Default Values

You can reference other default parameters:

```javascript
function add(x = 1, y = x, z = x + y) {
  return x + y + z;
}

console.log(add()); // 4
```

### 4. Using Functions as Default Values

You can also use a function's return value as a default:

```javascript
let taxRate = () => 0.1;

let getPrice = function (price, tax = price * taxRate()) {
  return price + tax;
};

let fullPrice = getPrice(100);
console.log(fullPrice); // 110
```

### The Arguments Object

The `arguments` object holds the number of actual arguments passed:

```javascript
function add(x, y = 1, z = 2) {
  console.log(arguments.length);
  return x + y + z;
}

add(10); // 1
add(10, 20); // 2
add(10, 20, 30); // 3
```

## Conclusion

You should now understand JavaScript default function parameters and how to use them effectively.

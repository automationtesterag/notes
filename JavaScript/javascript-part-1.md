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

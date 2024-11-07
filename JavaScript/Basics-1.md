### Variables in JavaScript:

In JavaScript, **variables** are containers used to store data values. JavaScript is a **loosely-typed** or **dynamically-typed** language, meaning variables can hold data of any type, and their type can change during runtime.

### 1. Declaring Variables

JavaScript provides three primary ways to declare variables: `var`, `let`, and `const`. Each of these has different scoping rules and behaviors.

#### a) `var` (Function-Scoped or Globally Scoped)

- **Before ES6**, `var` was the primary way to declare variables.
- A variable declared with `var` is **function-scoped**, meaning its scope is limited to the function in which it is declared. If declared outside of a function, it is globally scoped.
- Variables declared with `var` are **hoisted**, meaning the declaration (but not the initialization) is moved to the top of the scope.

##### Example:

```javascript
function testVar() {
  console.log(x); // undefined (hoisting)
  var x = 10;
  console.log(x); // 10
}

testVar();
```

In the above example, even though `x` is declared after the first `console.log`, JavaScript doesn't throw an error because of hoisting. However, the value of `x` at the time of the first `console.log` is `undefined` because the variable is declared but not yet initialized.

#### b) `let` (Block-Scoped)

- Introduced in ES6 (ECMAScript 2015), `let` is block-scoped, meaning it is confined to the block (enclosed by `{}`) in which it is defined.
- It is not hoisted in the same way `var` is, which makes it safer to use.
- You cannot redeclare a variable within the same block using `let`.

##### Example:

```javascript
if (true) {
  let x = 10;
  console.log(x); // 10
}
console.log(x); // ReferenceError: x is not defined
```

In the above example, `x` is block-scoped. It only exists inside the `if` block and is inaccessible outside of it.

#### c) `const` (Block-Scoped, Constant)

- Also introduced in ES6, `const` is block-scoped, like `let`, but it is used to declare **constants**.
- A variable declared with `const` must be initialized at the time of declaration and cannot be reassigned later. However, if the variable holds an object or array, the contents of the object or array can still be modified.
- Like `let`, `const` is also **hoisted**, but you cannot access the variable before its declaration due to the "temporal dead zone."

##### Example:

```javascript
const x = 10;
console.log(x); // 10

x = 20; // TypeError: Assignment to constant variable.
```

In the above example, trying to reassign `x` will throw a `TypeError` because it is a constant.

### 2. Types of Data that Variables Can Hold

JavaScript variables can store a wide variety of data types, including primitive types (such as numbers, strings, booleans, etc.) and non-primitive types (such as objects, arrays, and functions).

#### a) Primitive Types

- **String**: Represents text.
- **Number**: Represents numeric values (integers or floating-point).
- **Boolean**: Represents either `true` or `false`.
- **Null**: Represents an intentional absence of value.
- **Undefined**: Represents an uninitialized variable.
- **Symbol**: Represents a unique and immutable value.
- **BigInt**: Represents large integers beyond the `Number` type's range.

##### Example:

```javascript
let name = "John"; // String
let age = 30; // Number
let isActive = true; // Boolean
let nothing = null; // Null
let notDefined; // Undefined (variable declared but not initialized)
```

#### b) Non-Primitive Types (Objects)

- **Object**: A collection of key-value pairs (often used to represent real-world entities).
- **Array**: An ordered collection of values (which can be of mixed types).
- **Function**: A block of code that performs a task and can be invoked.

##### Example:

```javascript
let person = {
  // Object
  name: "Alice",
  age: 25,
};

let numbers = [1, 2, 3, 4]; // Array

function greet() {
  // Function
  console.log("Hello, World!");
}
```

### 3. Hoisting in JavaScript

Hoisting is the behavior of JavaScript in which variable and function declarations are moved to the top of their scope during the compile phase. Only the **declarations** are hoisted, not the **initializations**.

- **`var` declarations** are hoisted to the top of their scope, and initialized with `undefined`.
- **`let` and `const`** declarations are hoisted but are not initialized. Accessing them before their declaration leads to a `ReferenceError`.

#### Example with `var`:

```javascript
console.log(x); // undefined (hoisted)
var x = 10;
console.log(x); // 10
```

#### Example with `let` and `const` (Temporal Dead Zone):

```javascript
console.log(x); // ReferenceError: Cannot access 'x' before initialization
let x = 10;
```

### 4. Re-declaration Behavior

- With `var`, you can re-declare a variable in the same scope without any errors.
- With `let` and `const`, you **cannot** re-declare the same variable in the same scope.

##### Example:

```javascript
var x = 10;
var x = 20; // No error with 'var'

let y = 10;
let y = 20; // SyntaxError: Identifier 'y' has already been declared
```

### 5. Scope of Variables

- **Global Scope**: A variable declared outside any function or block is globally scoped.
- **Function Scope**: A variable declared inside a function is accessible only within that function (when using `var`).
- **Block Scope**: Variables declared with `let` or `const` inside a block (`if`, `for`, etc.) are only accessible within that block.

#### Example of Global and Local Scope:

```javascript
var globalVar = "I am global";

function testScope() {
  var localVar = "I am local";
  console.log(globalVar); // Accessible (global scope)
  console.log(localVar); // Accessible (local scope)
}

console.log(globalVar); // Accessible (global scope)
console.log(localVar); // ReferenceError: localVar is not defined (local scope)
```

### 6. Variable Initialization

When you declare a variable, you can choose to initialize it with a value, or you can leave it uninitialized (in which case it defaults to `undefined`).

#### Example of Initialization:

```javascript
let x = 5; // Initialized with 5
let y; // Uninitialized, defaults to undefined
```

### Summary of Differences Between `var`, `let`, and `const`

| Feature            | `var`                           | `let`                    | `const`                         |
| ------------------ | ------------------------------- | ------------------------ | ------------------------------- |
| **Scope**          | Function-scoped, or global      | Block-scoped             | Block-scoped                    |
| **Hoisting**       | Yes, initialized as `undefined` | Yes, not initialized     | Yes, not initialized            |
| **Re-declaration** | Allowed in the same scope       | Not allowed              | Not allowed                     |
| **Re-assignment**  | Allowed                         | Allowed                  | Not allowed (for value)         |
| **Use**            | Mostly outdated, legacy         | Preferred for most cases | Use when value shouldn’t change |

### Conclusion

JavaScript variables are fundamental for storing and managing data. Understanding how to declare variables with `var`, `let`, and `const`, and knowing their differences and scoping rules is key to writing clean and bug-free code. In modern JavaScript, `let` and `const` are preferred over `var`, as they provide better scoping and avoid some of the pitfalls that `var` can introduce.

### A. Primitive Data Types: **Number**

JavaScript's `Number` type is used to represent both integer and floating-point numbers, as well as special numeric values like `Infinity` and `NaN`. This category encompasses a wide range of methods, operations, and considerations when working with numeric data.

#### 1. **Number**

##### **a) Integer**

An integer is a whole number, positive or negative, without any decimal point. JavaScript uses a floating-point number representation for integers, meaning even though they may appear to be whole numbers, they're still represented as IEEE 754 double-precision floating point values.

**Examples:**

```javascript
let positiveInteger = 42;
let negativeInteger = -23;
console.log(Number.isInteger(positiveInteger)); // true
console.log(Number.isInteger(negativeInteger)); // true
```

**Safe Integer Limits (MAX_SAFE_INTEGER, MIN_SAFE_INTEGER)**
JavaScript uses double-precision floating point numbers to represent all numbers, including integers. However, integers beyond a certain range cannot be safely represented due to precision limitations.

- **`Number.MAX_SAFE_INTEGER`** is the largest integer that can be represented accurately in JavaScript.
- **`Number.MIN_SAFE_INTEGER`** is the smallest integer that can be represented accurately.

**Examples:**

```javascript
console.log(Number.MAX_SAFE_INTEGER); // 9007199254740991
console.log(Number.MIN_SAFE_INTEGER); // -9007199254740991
```

Any integer beyond these limits will lose precision and cannot be accurately represented.

**BigInt for Large Numbers**
For numbers that exceed the safe integer limits, JavaScript introduced the `BigInt` type, which allows working with arbitrarily large integers.

**Example:**

```javascript
let largeInteger = 9007199254740992n; // 'n' denotes BigInt
console.log(largeInteger + 1n); // BigInt addition
```

##### **b) Float**

Floating-point numbers are numbers that have a decimal point. These can represent fractional values.

**Examples:**

```javascript
let pi = 3.14159;
let negativeFloat = -12.34;
console.log(Number.isInteger(pi)); // false
console.log(Number.isInteger(negativeFloat)); // false
```

**Precision Limits**
JavaScript uses IEEE 754 double-precision format to store numbers, which gives it a precision of approximately 15-17 decimal digits. This means floating-point numbers beyond this precision may not be accurately represented.

**Example:**

```javascript
let num = 0.1 + 0.2;
console.log(num); // Output: 0.30000000000000004 (not exactly 0.3 due to precision issues)
```

**Floating-point Arithmetic Considerations**
Due to the inherent precision limitations of floating-point arithmetic in JavaScript, operations involving floating-point numbers might result in rounding errors. This is common when working with decimals.

**Example:**

```javascript
let result = 0.1 + 0.2;
console.log(result); // Output: 0.30000000000000004
```

##### **c) Special Values**

JavaScript also has special numeric values that fall outside the normal range of numbers:

- **Infinity**: Represents an overflow result, such as dividing a positive number by zero.
- **-Infinity**: Represents a result of dividing a negative number by zero.
- **NaN**: Represents "Not-a-Number", typically as a result of invalid mathematical operations.

**Examples:**

```javascript
let positiveInfinity = 1 / 0;
let negativeInfinity = -1 / 0;
let notANumber = 0 / 0;

console.log(positiveInfinity); // Infinity
console.log(negativeInfinity); // -Infinity
console.log(notANumber); // NaN
```

##### **d) Number Methods**

JavaScript's `Number` object has a set of useful methods for manipulating and working with numbers. Let's look at some of them in detail:

- **`.toFixed()`**: Formats a number to a specified number of decimal places, returning a string.

  **Example:**

  ```javascript
  let num = 3.14159;
  console.log(num.toFixed(2)); // "3.14"
  console.log(num.toFixed(0)); // "3"
  ```

- **`.toPrecision()`**: Formats a number to a specific precision (total number of significant digits), returning a string.

  **Example:**

  ```javascript
  let num = 3.14159;
  console.log(num.toPrecision(3)); // "3.14"
  console.log(num.toPrecision(5)); // "3.1416"
  ```

- **`.toString()`**: Converts the number to a string.

  **Example:**

  ```javascript
  let num = 42;
  console.log(num.toString()); // "42"
  console.log((42).toString(2)); // "101010" (binary representation)
  ```

- **`.valueOf()`**: Returns the primitive value of a `Number` object. This is typically used to convert a `Number` object to a primitive number.

  **Example:**

  ```javascript
  let numObj = new Number(42);
  console.log(numObj.valueOf()); // 42
  ```

- **`Number.isInteger()`**: Checks if the value is an integer.

  **Example:**

  ```javascript
  console.log(Number.isInteger(10)); // true
  console.log(Number.isInteger(10.5)); // false
  ```

- **`Number.isFinite()`**: Checks if the value is a finite number (not `Infinity` or `NaN`).

  **Example:**

  ```javascript
  console.log(Number.isFinite(42)); // true
  console.log(Number.isFinite(Infinity)); // false
  console.log(Number.isFinite(NaN)); // false
  ```

##### **e) Math Object Methods**

The `Math` object in JavaScript provides a variety of methods and constants for performing mathematical operations.

- **`Math.round()`**: Rounds a number to the nearest integer.

  **Example:**

  ```javascript
  console.log(Math.round(4.7)); // 5
  console.log(Math.round(4.4)); // 4
  ```

- **`Math.floor()`**: Rounds a number down to the nearest integer.

  **Example:**

  ```javascript
  console.log(Math.floor(4.7)); // 4
  console.log(Math.floor(-4.7)); // -5
  ```

- **`Math.ceil()`**: Rounds a number up to the nearest integer.

  **Example:**

  ```javascript
  console.log(Math.ceil(4.1)); // 5
  console.log(Math.ceil(-4.1)); // -4
  ```

- **`Math.max()` and `Math.min()`**: Return the largest or smallest number from a set of numbers.

  **Example:**

  ```javascript
  console.log(Math.max(1, 2, 3, 4)); // 4
  console.log(Math.min(1, 2, 3, 4)); // 1
  ```

- **`Math.random()`**: Returns a random number between 0 (inclusive) and 1 (exclusive).

  **Example:**

  ```javascript
  console.log(Math.random()); // Random number between 0 and 1
  ```

- **`Math.pow()`**: Returns the base raised to the power of exponent (base^exponent).

  **Example:**

  ```javascript
  console.log(Math.pow(2, 3)); // 8 (2^3)
  ```

- **`Math.sqrt()`**: Returns the square root of a number.

  **Example:**

  ```javascript
  console.log(Math.sqrt(16)); // 4
  console.log(Math.sqrt(2)); // 1.4142135623730951
  ```

#### Conclusion

The `Number` primitive type in JavaScript is fundamental to working with numeric values, from simple integers to floating-point numbers, and even large values with `BigInt`. Understanding the behavior of numbers, such as precision limits, special values like `Infinity` and `NaN`, and leveraging various built-in methods will help you avoid common pitfalls in mathematical operations and ensure accuracy in your code.

### 2. **String**

Strings are one of the most commonly used data types in JavaScript. They represent sequences of characters, and JavaScript provides a variety of ways to create, manipulate, and inspect strings. Let's dive into the different aspects of working with strings in detail.

---

#### **a) Creation Methods**

In JavaScript, there are several ways to create strings.

1. **Single/Double Quotes**

   You can create a string using either single quotes (`'`) or double quotes (`"`). Both methods work in the same way, and it's generally a matter of style preference or consistency within a codebase.

   **Examples:**

   ```javascript
   let str1 = "Hello, world!"; // Using single quotes
   let str2 = "Hello, world!"; // Using double quotes

   console.log(str1 === str2); // true, both are the same string
   ```

2. **Template Literals**

   Template literals (denoted by backticks, `` ` ``) allow for string creation with embedded expressions. Template literals are more powerful than regular strings because they support **multiline strings** and **string interpolation** (embedding variables or expressions inside the string).

   **Examples:**

   ```javascript
   let name = "Alice";
   let greeting = `Hello, ${name}!`; // String interpolation
   let multiline = `This is a
   multiline string.`;

   console.log(greeting); // "Hello, Alice!"
   console.log(multiline); // "This is a\nmultiline string."
   ```

   Template literals also support **expressions** inside `${}` placeholders, which can be variables, arithmetic operations, or even function calls.

   ```javascript
   let a = 5;
   let b = 3;
   let sum = `${a} + ${b} = ${a + b}`;
   console.log(sum); // "5 + 3 = 8"
   ```

---

#### **b) Properties & Access**

1. **`length` Property**

   The `length` property returns the number of characters in a string. It counts every character, including spaces and punctuation.

   **Example:**

   ```javascript
   let text = "Hello, world!";
   console.log(text.length); // 13
   ```

2. **Character Access (`[]` and `charAt()` Method)**

   You can access characters in a string using either **array-like indexing (`[]`)** or the `charAt()` method. Both return the character at the specified index (0-based).

   - **`[]` notation**:

     ```javascript
     let str = "Hello";
     console.log(str[0]); // 'H'
     console.log(str[4]); // 'o'
     ```

   - **`charAt()` method**:
     ```javascript
     let str = "Hello";
     console.log(str.charAt(0)); // 'H'
     console.log(str.charAt(4)); // 'o'
     ```

   Both methods return the character at the specified index. However, `charAt()` returns an empty string (`""`) if the index is out of range, whereas `[]` notation returns `undefined`.

---

#### **c) Methods**

JavaScript strings have a wide range of built-in methods that allow you to search, extract, modify, and manipulate them. Here’s a breakdown of some of the most commonly used string methods:

---

##### **Search Methods**

1. **`indexOf()`**

   - Searches for the first occurrence of a substring within the string and returns its index (or `-1` if not found).

   **Example:**

   ```javascript
   let str = "JavaScript is fun!";
   console.log(str.indexOf("is")); // 10
   console.log(str.indexOf("Python")); // -1
   ```

2. **`lastIndexOf()`**

   - Similar to `indexOf()`, but returns the index of the **last** occurrence of a substring.

   **Example:**

   ```javascript
   let str = "I love JavaScript. JavaScript is fun!";
   console.log(str.lastIndexOf("JavaScript")); // 29
   ```

3. **`includes()`**

   - Checks if a substring exists in the string and returns `true` or `false`.

   **Example:**

   ```javascript
   let str = "Hello, world!";
   console.log(str.includes("world")); // true
   console.log(str.includes("earth")); // false
   ```

4. **`startsWith()`**

   - Determines if the string starts with a specified substring.

   **Example:**

   ```javascript
   let str = "Hello, world!";
   console.log(str.startsWith("Hello")); // true
   console.log(str.startsWith("world")); // false
   ```

5. **`endsWith()`**

   - Determines if the string ends with a specified substring.

   **Example:**

   ```javascript
   let str = "Hello, world!";
   console.log(str.endsWith("world!")); // true
   console.log(str.endsWith("Hello")); // false
   ```

---

##### **Extract Methods**

1. **`slice()`**

   - Extracts a section of the string based on start and end indices, and returns a new string.

   **Example:**

   ```javascript
   let str = "JavaScript";
   console.log(str.slice(4, 10)); // "Script"
   console.log(str.slice(4)); // "Script"
   console.log(str.slice(-6, -1)); // "Script"
   ```

2. **`substring()`**

   - Similar to `slice()`, but it does not accept negative indices. The method swaps the arguments if the start index is greater than the end index.

   **Example:**

   ```javascript
   let str = "JavaScript";
   console.log(str.substring(4, 10)); // "Script"
   console.log(str.substring(10, 4)); // "Script" (argument order is swapped)
   ```

3. **`substr()`**

   - Extracts a substring starting from a specified index and optionally a length.

   **Example:**

   ```javascript
   let str = "JavaScript";
   console.log(str.substr(4, 6)); // "Script"
   ```

---

##### **Modify Methods**

1. **`toUpperCase()`**

   - Converts the entire string to uppercase.

   **Example:**

   ```javascript
   let str = "hello";
   console.log(str.toUpperCase()); // "HELLO"
   ```

2. **`toLowerCase()`**

   - Converts the entire string to lowercase.

   **Example:**

   ```javascript
   let str = "HELLO";
   console.log(str.toLowerCase()); // "hello"
   ```

3. **`trim()`**

   - Removes whitespace from both ends of the string (but not from the middle).

   **Example:**

   ```javascript
   let str = "  hello  ";
   console.log(str.trim()); // "hello"
   ```

4. **`trimStart()` and `trimEnd()`**

   - Removes whitespace from the start (`trimStart()`) or end (`trimEnd()`) of the string.

   **Example:**

   ```javascript
   let str = "  hello  ";
   console.log(str.trimStart()); // "hello  "
   console.log(str.trimEnd()); // "  hello"
   ```

---

##### **Split/Join Methods**

1. **`split()`**

   - Splits a string into an array of substrings, based on a specified delimiter.

   **Example:**

   ```javascript
   let str = "apple,banana,cherry";
   let arr = str.split(",");
   console.log(arr); // ["apple", "banana", "cherry"]
   ```

2. **`join()`**

   - Joins all elements of an array into a single string, with a specified separator between them.

   **Example:**

   ```javascript
   let arr = ["apple", "banana", "cherry"];
   console.log(arr.join("-")); // "apple-banana-cherry"
   ```

3. **`concat()`**

   - Concatenates two or more strings into one.

   **Example:**

   ```javascript
   let str1 = "Hello";
   let str2 = "World";
   console.log(str1.concat(" ", str2)); // "Hello World"
   ```

---

##### **Replace Methods**

1. **`replace()`**

   - Replaces the first occurrence of a substring with another substring.

   **Example:**

   ```javascript
   let str = "I love JavaScript";
   console.log(str.replace("JavaScript", "Python")); // "I love Python"
   ```

2. **`replaceAll()`**

   - Replaces all occurrences of a substring with another substring. This method is useful for global replacements.

   **Example:**

   ```javascript
   let str = "I love JavaScript. JavaScript is awesome.";
   console.log(str.replaceAll("JavaScript", "Python")); // "I love Python. Python is awesome."
   ```

---

##### **Padding Methods**

1. **`padStart()`**

   - Pads the string at the start with a specified character (or spaces by default) until it reaches the given length.

   **Example:**

   ```javascript
   let str = '5';
   console.log(str.padStart(3, '0
   ```

')); // "005"

````

2. **`padEnd()`**
- Pads the string at the end with a specified character (or spaces by default) until it reaches the given length.

**Example:**
```javascript
let str = '5';
console.log(str.padEnd(3, '0'));  // "500"
````

---

#### Conclusion

Strings in JavaScript are versatile and come with a wide range of methods for creation, modification, extraction, and search. Mastering these methods will allow you to manipulate text effectively in your JavaScript applications. Whether it's searching for substrings, extracting parts of a string, or modifying case and formatting, the methods provided by JavaScript make working with strings both powerful and flexible.

### 3. **Boolean**

In JavaScript, the `Boolean` type is used to represent a logical value, which can either be `true` or `false`. Booleans are fundamental in controlling the flow of your program, particularly in conditional statements and loops. Let's explore the details of Booleans in JavaScript.

---

#### **a) Values**

1. **`true` and `false`**

   A Boolean can only have two values: `true` or `false`. These values are used to represent logical truth or falsity.

   **Examples:**

   ```javascript
   let isActive = true;
   let isCompleted = false;

   console.log(isActive); // true
   console.log(isCompleted); // false
   ```

   In many scenarios, `true` and `false` are used as flags or conditions in your code to indicate whether something is enabled, completed, or met certain criteria.

2. **Truthy and Falsy Values**

   In JavaScript, many values can be converted to a Boolean using the `Boolean()` constructor or in conditional statements. Values that evaluate to `true` are called **truthy**, and values that evaluate to `false` are called **falsy**.

   **Falsy Values:**

   - `false`
   - `0` (zero)
   - `""` (empty string)
   - `null`
   - `undefined`
   - `NaN`

   **Truthy Values:**

   - Any non-zero number (e.g., `1`, `-1`, `3.14`)
   - Any non-empty string (e.g., `"hello"`)
   - Arrays and objects (even if empty, like `[]` and `{}`)
   - Functions, dates, and other objects

   **Examples:**

   ```javascript
   console.log(Boolean(false)); // false
   console.log(Boolean(0)); // false
   console.log(Boolean("")); // false
   console.log(Boolean(null)); // false
   console.log(Boolean(undefined)); // false
   console.log(Boolean(NaN)); // false

   console.log(Boolean(1)); // true
   console.log(Boolean("hello")); // true
   console.log(Boolean([])); // true
   console.log(Boolean({})); // true
   ```

   Understanding **truthy** and **falsy** values is important because JavaScript will implicitly convert many values to Boolean when evaluating conditions, such as in `if` statements.

---

#### **b) Operations**

1. **Logical Operators: `&&`, `||`, `!`**

   JavaScript provides logical operators to perform logical operations with Booleans.

   - **`&&` (Logical AND)**: Returns `true` if both operands are `true`. If any operand is `false`, the result is `false`.
   - **`||` (Logical OR)**: Returns `true` if at least one operand is `true`. The result is `false` only if both operands are `false`.
   - **`!` (Logical NOT)**: Reverses the Boolean value. It converts `true` to `false` and `false` to `true`.

   **Examples:**

   ```javascript
   let a = true;
   let b = false;

   // Logical AND (&&)
   console.log(a && b); // false (both must be true to return true)

   // Logical OR (||)
   console.log(a || b); // true (only one must be true)

   // Logical NOT (!)
   console.log(!a); // false (reverses true to false)
   console.log(!b); // true (reverses false to true)
   ```

   These operators are often used to evaluate complex conditions in conditional statements.

2. **Short-circuit Evaluation**

   Short-circuit evaluation refers to the way JavaScript evaluates expressions with logical operators.

   - **For `&&` (AND)**: If the first operand is `false`, JavaScript won't evaluate the second operand, because the result will already be `false` regardless.
   - **For `||` (OR)**: If the first operand is `true`, JavaScript won't evaluate the second operand, because the result will already be `true` regardless.

   **Examples:**

   ```javascript
   let a = false;
   let b = true;
   console.log(a && b); // false (evaluation stops at `a`)
   console.log(a || b); // true (evaluation stops at `a`)

   // Example of "short-circuiting" with function calls:
   function logFalse() {
     console.log("This will not be logged.");
     return false;
   }

   console.log(false && logFalse()); // false (logFalse is not called)
   console.log(true || logFalse()); // true (logFalse is not called)
   ```

   Short-circuiting improves performance by preventing unnecessary evaluations, especially in functions or operations that are costly or unnecessary.

---

#### **c) Contexts**

Booleans are crucial in many situations, such as **conditional statements**, **loops**, and **ternary operations**.

1. **Conditional Statements**

   `if`, `else if`, and `else` statements rely on Boolean expressions to decide whether or not to execute a block of code. These conditions evaluate to `true` or `false`, controlling the flow of the program.

   **Example:**

   ```javascript
   let isAuthenticated = true;

   if (isAuthenticated) {
     console.log("Welcome back!");
   } else {
     console.log("Please log in.");
   }
   ```

   In this case, the `if` statement checks if `isAuthenticated` is truthy (evaluates to `true`). Since it's `true`, the first block of code is executed.

2. **Loop Conditions**

   Booleans are often used in loop conditions to determine whether the loop should continue or stop. A loop will continue running as long as the condition evaluates to `true`.

   **Example with `while` loop:**

   ```javascript
   let counter = 0;
   let limit = 5;

   while (counter < limit) {
     console.log(counter);
     counter++; // Increment counter
   }
   // Output: 0 1 2 3 4
   ```

   The loop runs as long as `counter < limit` evaluates to `true`. Once the condition becomes `false`, the loop terminates.

   **Example with `for` loop:**

   ```javascript
   for (let i = 0; i < 3; i++) {
     console.log(i);
   }
   // Output: 0 1 2
   ```

3. **Ternary Operations**

   The ternary operator is a shorthand for an `if-else` statement. It uses a condition followed by two expressions: one for `true` and one for `false`. It evaluates the condition and returns the first expression if the condition is `true`, and the second expression if it is `false`.

   **Example:**

   ```javascript
   let age = 18;
   let canVote = age >= 18 ? "Yes" : "No";
   console.log(canVote); // "Yes"
   ```

   In this example, the condition `age >= 18` is checked. Since it's `true`, the value `'Yes'` is returned and assigned to `canVote`. If `age` had been less than 18, the value `'No'` would have been returned.

---

#### **Conclusion**

Booleans are essential in JavaScript, enabling decision-making logic throughout your program. From the fundamental values `true` and `false` to more complex logical operations with operators like `&&`, `||`, and `!`, understanding how Booleans work is crucial for controlling the flow of your application. Short-circuit evaluation, truthy/falsy values, and their use in conditional statements, loops, and ternary operations make Booleans a powerful tool in JavaScript for making logical decisions and controlling program behavior.

### 4. **Undefined & Null**

In JavaScript, both `undefined` and `null` represent the absence of a value, but they are used in different contexts and have different meanings. Let's explore these two types in detail.

---

#### **a) Undefined**

1. **Occurrence Scenarios**

   In JavaScript, `undefined` occurs in several scenarios:

   - A variable is declared but not assigned a value.
   - A function does not explicitly return a value.
   - An object property that doesn't exist or isn't initialized will return `undefined`.

   **Examples:**

   ```javascript
   let x;
   console.log(x); // undefined (variable declared but not initialized)

   function greet() {
     console.log("Hello!");
   }
   let result = greet();
   console.log(result); // undefined (no return statement in the function)

   let obj = {};
   console.log(obj.name); // undefined (property doesn't exist)
   ```

   **Note:** `undefined` is a primitive value and is automatically assigned by JavaScript in these cases. It is not a value that can be explicitly assigned (though it can be, but that's not a good practice).

2. **Type Checking**

   You can check if a variable is `undefined` using strict equality (`===`) or `typeof` operator.

   **Examples:**

   ```javascript
   let a;
   console.log(a === undefined); // true (strict comparison)
   console.log(typeof a); // "undefined" (typeof operator)

   let b = null;
   console.log(b === undefined); // false
   ```

   - `typeof` will return `"undefined"` for a variable that is `undefined`.
   - `===` can be used for direct comparison.

3. **Optional Chaining (`?.`)**

   Optional chaining (`?.`) is a feature introduced in JavaScript to handle cases where you want to access deeply nested properties or methods, but the intermediate objects might be `undefined` or `null`. Using `?.` prevents runtime errors by short-circuiting the evaluation if a reference is `undefined` or `null`.

   **Examples:**

   ```javascript
   let user = {
     profile: {
       name: "Alice",
     },
   };

   console.log(user.profile?.name); // "Alice"
   console.log(user.address?.city); // undefined (no error, just returns undefined)
   ```

   Without optional chaining, attempting to access `user.address.city` would throw a `TypeError` if `user.address` is `undefined`.

---

#### **b) Null**

1. **Purpose**

   `null` is a special value in JavaScript that represents the **intentional absence of any object value**. It is used when you want to explicitly indicate that a variable should not point to any object, but you still want it to exist as a defined value.

   - **`null`** is often used to initialize a variable that will later be assigned an object, signaling the absence of a value at that moment.
   - It is also used to clear or reset values when removing references to objects.

   **Example:**

   ```javascript
   let user = null; // User doesn't exist, initialized with null
   console.log(user); // null
   ```

2. **Comparison with `undefined`**

   - **`undefined`** is automatically assigned to uninitialized variables, missing function return values, or non-existing object properties.
   - **`null`** is explicitly assigned to indicate "no value" or the absence of an object.

   While both represent the absence of a value, they are not the same:

   - `undefined` is a **type**, while `null` is an **object** (this can be confusing because `typeof null` returns `"object"`, a known bug in JavaScript).

   **Comparison example:**

   ```javascript
   let a;
   let b = null;
   console.log(a === b); // false
   console.log(a == b); // true (loose equality, both are "falsy")

   console.log(typeof a); // "undefined"
   console.log(typeof b); // "object"
   ```

3. **Nullish Coalescing (`??`)**

   The nullish coalescing operator (`??`) allows you to return a **default value** when the left-hand side operand is `null` or `undefined` (but not other falsy values like `0`, `false`, or `""`).

   This operator is useful when you want to provide a fallback value for `null` or `undefined`, but you still want other falsy values like `0` or `false` to be respected.

   **Examples:**

   ```javascript
   let username = null;
   let defaultName = "Guest";

   console.log(username ?? defaultName); // "Guest" (because username is null)

   let age = 0;
   let defaultAge = 18;

   console.log(age ?? defaultAge); // 0 (because age is not null or undefined, even though it is falsy)
   ```

   The nullish coalescing operator is useful in cases where you want to differentiate between "no value" (i.e., `null` or `undefined`) and other falsy values.

---

### 5. **Symbol**

Symbols are a relatively new primitive type introduced in JavaScript (ES6) that are often used for **unique identifiers**. Unlike other primitive types like `string` or `number`, each `Symbol` is unique, even if two symbols have the same description.

---

#### **a) Creation & Usage**

You can create a new `Symbol` using the `Symbol()` function. Each time you call `Symbol()`, a new unique symbol is created.

**Example:**

```javascript
let symbol1 = Symbol();
let symbol2 = Symbol();
console.log(symbol1 === symbol2); // false (each Symbol is unique)

let symbolWithDescription = Symbol("description");
console.log(symbolWithDescription.toString()); // Symbol(description)
```

Symbols are commonly used as **property keys** for objects. Since every symbol is unique, it prevents name collisions when properties with the same name exist in different parts of your program.

**Example of symbol as an object property:**

```javascript
let sym = Symbol("uniqueKey");
let obj = {
  [sym]: "value",
};

console.log(obj[sym]); // "value"
```

Symbols are **not enumerable** by default (i.e., they do not show up in a `for...in` loop or `Object.keys()`), making them a good choice for defining properties that you don’t want to accidentally access or overwrite.

---

#### **b) `Symbol.for()`**

`Symbol.for()` is a method that allows you to create or retrieve symbols from a **global symbol registry**. Unlike regular `Symbol()`, which creates a new unique symbol each time, `Symbol.for()` will return the same symbol if it's already registered with the specified key.

- If the symbol already exists in the global symbol registry, it returns that symbol.
- If it doesn't exist, it creates a new symbol and registers it globally.

This is useful when you want to share symbols across different parts of your program or across different files.

**Example:**

```javascript
let globalSym1 = Symbol.for("app.uniqueKey");
let globalSym2 = Symbol.for("app.uniqueKey");
console.log(globalSym1 === globalSym2); // true (both refer to the same symbol)
```

Symbols created with `Symbol.for()` are shared across the entire program, making them useful for things like global constants.

---

#### **c) Well-known Symbols**

JavaScript also defines a set of built-in symbols that have special meaning within the language. These symbols are used by the language itself to implement internal behaviors or algorithms. Some well-known symbols include:

- `Symbol.iterator`: Defines the default iterator for an object (used by `for...of` loops).
- `Symbol.asyncIterator`: Defines the default asynchronous iterator (used in `for await...of` loops).
- `Symbol.hasInstance`: Used to customize the behavior of `instanceof`.
- `Symbol.toPrimitive`: Allows you to customize how objects are converted to primitive values (like strings or numbers).
- `Symbol.toStringTag`: Used to customize the output of `Object.prototype.toString()`.

**Examples:**

```javascript
// Symbol.iterator example
let iterable = {
  items: ["apple", "banana", "cherry"],
  [Symbol.iterator]() {
    let index = 0;
    let items = this.items;
    return {
      next: function () {
        if (index < items.length) {
          return { value: items[index++], done: false };
        } else {
          return { done: true };
        }
      },
    };
  },
};

for (let item of iterable) {
  console.log(item); // apple, banana, cherry
}
```

---

#### **d) Symbol Properties**

Symbols have some unique properties and behaviors:

- **Symbols are immutable**: Once created, a symbol cannot be changed.
- **Symbols are unique**: Each symbol is unique even if it has the same description.
- **Symbols are not enumerable** by default, meaning they do not appear in `Object.keys()`, `for...in`, or `JSON.stringify()`.

**Example of properties:**

```javascript
let sym = Symbol('key');
let obj = {
    [sym]: 'value'
};

console.log(Object.keys(obj));  // [] (Symbols do not appear in Object.keys)
console.log(Object.getOwnPropertyNames(obj));  // [] (Symbols do not appear here either)
console.log(Object.getOwnPropertySymbols(obj));  // [Symbol(key)] (Symbols are retrieved with Object

.getOwnPropertySymbols)
```

---

### **Conclusion**

- **Undefined** is automatically assigned when variables are declared but not initialized, while **null** is explicitly assigned to indicate the absence of a value.
- **Symbols** provide a way to create unique identifiers that can be used for property keys or to define special behavior within objects. They ensure there are no conflicts with object property names and allow for private, non-enumerable properties.

### B. **Complex Data Types & Collections: Arrays**

Arrays are one of the most commonly used data structures in JavaScript. They allow you to store ordered collections of values, which can be of any type, including other arrays or objects. JavaScript arrays provide numerous built-in methods and properties for efficient data manipulation.

---

### **1. Arrays**

#### **a) Creation**

Arrays in JavaScript can be created in a variety of ways:

1. **Using Array Literals**  
   The most common and preferred way to create an array is by using array literals (`[]`). This is concise, clear, and easy to use.

   **Example:**

   ```javascript
   let fruits = ["apple", "banana", "cherry"];
   console.log(fruits); // ["apple", "banana", "cherry"]
   ```

2. **Using the Array Constructor**  
   You can also create an array using the `Array` constructor. This method is less commonly used but still valid. It can take one or more arguments:

   - If no arguments are passed, it creates an empty array.
   - If one argument is passed, it is interpreted as the array length.
   - If multiple arguments are passed, they become the array elements.

   **Examples:**

   ```javascript
   let arr1 = new Array(); // Creates an empty array
   let arr2 = new Array(3); // Creates an array with 3 empty slots
   let arr3 = new Array(1, 2, 3); // Creates an array [1, 2, 3]
   console.log(arr1, arr2, arr3);
   ```

3. **`Array.from()`**
   The `Array.from()` method creates a new array instance from an array-like or iterable object (such as a string, NodeList, or Set).

   **Example:**

   ```javascript
   let str = "hello";
   let arrFromString = Array.from(str);
   console.log(arrFromString); // ["h", "e", "l", "l", "o"]
   ```

   You can also use a map function as the second argument to modify each element as it is added to the array.

   ```javascript
   let arrFromMap = Array.from([1, 2, 3], (x) => x * 2);
   console.log(arrFromMap); // [2, 4, 6]
   ```

4. **`Array.of()`**
   The `Array.of()` method creates a new array with a variable number of elements passed as arguments. It is similar to `Array()` but does not treat a single numeric argument as the array's length.

   **Example:**

   ```javascript
   let arr1 = Array.of(1, 2, 3);
   let arr2 = Array.of(5); // Creates an array with one element, not the length 5
   console.log(arr1, arr2); // [1, 2, 3], [5]
   ```

---

#### **b) Properties & Methods**

1. **`length` Property**  
   The `length` property of an array returns the number of elements in the array. It’s important to note that the `length` property is always one more than the index of the last element (since arrays are 0-indexed).

   **Example:**

   ```javascript
   let arr = ["a", "b", "c"];
   console.log(arr.length); // 3
   ```

2. **Element Access**  
   You can access array elements using their index (starting from 0). Use square brackets `[]` to access an element.

   **Example:**

   ```javascript
   let arr = [10, 20, 30];
   console.log(arr[1]); // 20
   ```

---

#### **c) Manipulation**

1. **Adding/Removing Elements**

   - **`push()`**: Adds one or more elements to the end of the array.
   - **`pop()`**: Removes the last element from the array.
   - **`shift()`**: Removes the first element from the array.
   - **`unshift()`**: Adds one or more elements to the beginning of the array.
   - **`splice()`**: Adds or removes elements from any position in the array.

   **Examples:**

   ```javascript
   let arr = [1, 2, 3];
   arr.push(4); // Adds 4 to the end
   console.log(arr); // [1, 2, 3, 4]

   arr.pop(); // Removes 4 from the end
   console.log(arr); // [1, 2, 3]

   arr.shift(); // Removes 1 from the beginning
   console.log(arr); // [2, 3]

   arr.unshift(0); // Adds 0 to the beginning
   console.log(arr); // [0, 2, 3]

   arr.splice(1, 1, "a", "b"); // Removes 1 element at index 1, and inserts 'a' and 'b'
   console.log(arr); // [0, 'a', 'b', 3]
   ```

2. **Subarrays**

   - **`slice()`**: Creates a shallow copy of a portion of the array and returns it as a new array.
   - **`concat()`**: Merges two or more arrays and returns a new array.

   **Examples:**

   ```javascript
   let arr = [1, 2, 3, 4, 5];

   // Slice from index 1 to 3 (index 3 is excluded)
   console.log(arr.slice(1, 3)); // [2, 3]

   let arr2 = [6, 7];
   let combined = arr.concat(arr2);
   console.log(combined); // [1, 2, 3, 4, 5, 6, 7]
   ```

3. **Search**

   - **`indexOf()`**: Returns the index of the first occurrence of a specified value, or `-1` if not found.
   - **`includes()`**: Checks if a specified value exists in the array, returns `true` or `false`.
   - **`find()`**: Returns the first element that satisfies the provided condition (callback function).
   - **`findIndex()`**: Returns the index of the first element that satisfies the provided condition.

   **Examples:**

   ```javascript
   let arr = [10, 20, 30, 40];
   console.log(arr.indexOf(20)); // 1
   console.log(arr.includes(50)); // false
   console.log(arr.find((x) => x > 25)); // 30
   console.log(arr.findIndex((x) => x > 25)); // 2
   ```

4. **Iteration**  
   JavaScript provides several powerful methods for iterating over arrays:

   - **`forEach()`**: Executes a provided function once for each array element.
   - **`map()`**: Creates a new array with the results of calling a function on every element.
   - **`filter()`**: Creates a new array with all elements that pass a test.
   - **`reduce()`**: Applies a function against an accumulator and each element in the array (from left to right) to reduce it to a single value.

   **Examples:**

   ```javascript
   let arr = [1, 2, 3, 4];

   // forEach
   arr.forEach((x) => console.log(x)); // 1, 2, 3, 4

   // map
   let doubled = arr.map((x) => x * 2);
   console.log(doubled); // [2, 4, 6, 8]

   // filter
   let even = arr.filter((x) => x % 2 === 0);
   console.log(even); // [2, 4]

   // reduce
   let sum = arr.reduce((acc, x) => acc + x, 0);
   console.log(sum); // 10
   ```

5. **Sort and Reverse**

   - **`sort()`**: Sorts the array in place.
   - **`reverse()`**: Reverses the order of elements in the array.

   **Examples:**

   ```javascript
   let arr = [3, 1, 4, 2];

   // Sort in ascending order
   arr.sort((a, b) => a - b);
   console.log(arr); // [1, 2, 3, 4]

   // Reverse the array
   arr.reverse();
   console.log(arr); // [4, 3, 2, 1]
   ```

6. **Flattening**

   - **`flat()`**: Flattens nested arrays into a single array.
   - **`flatMap()`**: Maps each element using a mapping function and then flattens the result into a new array.

   **Examples:**

   ```javascript
   let arr = [1, [2, 3], [4, [5, 6]]];
   console.log(arr.flat(2)); // [1, 2, 3, 4, 5, 6]

   let arr2 = [1, 2, 3];
   console.log(arr2.flatMap((x) => [x, x * 2])); //
   ```

[1, 2, 2, 4, 3, 6]

````

---

#### **d) Advanced Patterns**

1. **Destructuring**
Destructuring allows you to unpack values from arrays (or objects) into distinct variables.

**Example:**
```javascript
let arr = [1, 2, 3];
let [a, b, c] = arr;
console.log(a, b, c);  // 1 2 3
````

2. **Spread Operator (`...`)**  
   The spread operator can be used to unpack array elements or to create copies of arrays.

   **Example:**

   ```javascript
   let arr = [1, 2, 3];
   let arrCopy = [...arr]; // Copy of arr
   console.log(arrCopy); // [1, 2, 3]
   ```

3. **Copy Methods**

   - **`slice()`**: Creates a shallow copy of an array.
   - **`concat()`**: Also creates a shallow copy and combines arrays.

   **Example:**

   ```javascript
   let arr = [1, 2, 3];
   let arrCopy = arr.slice();
   console.log(arrCopy); // [1, 2, 3]
   ```

---

#### **e) Typed Arrays**

Typed arrays are an array-like view of binary data. They allow you to work with raw binary data buffers directly, and they are used when you need to deal with low-level data manipulation, such as in graphics, audio processing, or networking.

- **`Int8Array`, `Uint8Array`**: Signed and unsigned 8-bit integers.
- **`Float32Array`, `Float64Array`**: 32-bit and 64-bit floating-point numbers.

**Example:**

```javascript
let intArray = new Int8Array(3); // Creates an array of 3 elements with 8-bit integers
intArray[0] = 1;
intArray[1] = -2;
intArray[2] = 3;

console.log(intArray); // Int8Array [1, -2, 3]

let floatArray = new Float32Array(2);
floatArray[0] = 1.23;
floatArray[1] = 4.56;

console.log(floatArray); // Float32Array [1.23, 4.56]
```

Typed arrays are useful when working with binary data, such as reading files, manipulating images, or working with WebGL or WebAudio APIs.

---

### **Conclusion**

Arrays in JavaScript are highly flexible and provide a wide variety of methods for working with collections of data. Whether you're adding, removing, or transforming elements, JavaScript arrays provide a robust set of tools to make your work easier. Advanced patterns such as destructuring, spread operators, and typed arrays further expand the possibilities.

### **2. Objects**

Objects are a fundamental data structure in JavaScript that allow you to store collections of data in the form of key-value pairs. Objects are used to represent and manipulate real-world entities, and they are highly flexible and extensible. Below is a comprehensive breakdown of how to create, work with, and extend objects in JavaScript.

---

#### **a) Creation of Objects**

There are several ways to create an object in JavaScript, each with its advantages and use cases:

1. **Object Literals**

   The simplest and most common way to create an object is by using the object literal syntax `{}`.

   **Example:**

   ```javascript
   const person = {
     name: "Alice",
     age: 25,
     greet() {
       console.log("Hello, " + this.name);
     },
   };
   console.log(person.name); // "Alice"
   person.greet(); // "Hello, Alice"
   ```

   - **Pros**: Short and readable. Ideal for creating objects with known, fixed properties.
   - **Cons**: Not ideal for creating objects dynamically or based on other variables.

2. **Constructor Functions**

   You can also create objects using constructor functions. This is similar to how classes work in other languages, but using functions in JavaScript.

   **Example:**

   ```javascript
   function Person(name, age) {
     this.name = name;
     this.age = age;
     this.greet = function () {
       console.log("Hello, " + this.name);
     };
   }

   const person1 = new Person("Bob", 30);
   person1.greet(); // "Hello, Bob"
   ```

   - **Pros**: Useful for creating multiple instances of objects with the same properties.
   - **Cons**: Can be more verbose and harder to manage when properties are shared across instances.

3. **`Object.create()`**

   `Object.create()` creates a new object and allows you to set a prototype for the object. This is useful when you want to create objects that inherit from a specific prototype.

   **Example:**

   ```javascript
   const personPrototype = {
     greet() {
       console.log("Hello, " + this.name);
     },
   };

   const person2 = Object.create(personPrototype);
   person2.name = "Charlie";
   person2.greet(); // "Hello, Charlie"
   ```

   - **Pros**: Provides a clear mechanism for prototype-based inheritance.
   - **Cons**: Can be less intuitive when you're just starting with JavaScript's object model.

4. **Factory Functions**

   Factory functions are functions that return a new object. This pattern is a more flexible and modern alternative to using constructor functions.

   **Example:**

   ```javascript
   function createPerson(name, age) {
     return {
       name,
       age,
       greet() {
         console.log("Hello, " + this.name);
       },
     };
   }

   const person3 = createPerson("Dave", 40);
   person3.greet(); // "Hello, Dave"
   ```

   - **Pros**: More flexible and allows for multiple objects with different prototypes.
   - **Cons**: Does not support inheritance patterns out of the box (though it can be combined with `Object.create()` for that purpose).

---

#### **b) Properties**

JavaScript objects can have two types of properties: **data properties** and **accessor properties**. Understanding these is essential to working effectively with objects.

1. **Data Properties**

   Data properties are the most common type of property. They store a value directly.

   **Example:**

   ```javascript
   const person = {
     name: "Eve",
     age: 28,
   };
   console.log(person.name); // "Eve"
   ```

   - **Characteristics**:
     - Each data property has a **value** (which can be any valid JavaScript value).
     - **Writable**: The value can be modified.
     - **Enumerable**: The property shows up when iterating over the object.
     - **Configurable**: The property can be deleted or modified.

2. **Accessor Properties**

   Accessor properties are properties that get or set values using getter and setter functions, rather than directly holding a value. These are often used to create computed properties or encapsulate logic.

   **Example:**

   ```javascript
   const person = {
     firstName: "John",
     lastName: "Doe",
     get fullName() {
       return this.firstName + " " + this.lastName;
     },
     set fullName(name) {
       const [first, last] = name.split(" ");
       this.firstName = first;
       this.lastName = last;
     },
   };

   console.log(person.fullName); // "John Doe"
   person.fullName = "Alice Smith";
   console.log(person.firstName); // "Alice"
   ```

   - **Characteristics**:
     - A getter retrieves the property value when accessed.
     - A setter allows you to modify the property value when assigned.

3. **Descriptors**

   Property descriptors are objects that define attributes for properties. You can access and modify descriptors using `Object.getOwnPropertyDescriptor()` and `Object.defineProperty()`.

   **Example:**

   ```javascript
   const person = {
     name: "Zara",
   };

   const descriptor = Object.getOwnPropertyDescriptor(person, "name");
   console.log(descriptor);
   // { value: "Zara", writable: true, enumerable: true, configurable: true }
   ```

   Property descriptors are essential when working with non-standard property behaviors, such as making a property non-writable or non-enumerable.

---

#### **c) Methods**

JavaScript objects come with several built-in methods for working with their properties:

1. **`Object.keys()`**

   This method returns an array of the object's own enumerable property names (keys).

   **Example:**

   ```javascript
   const person = { name: "Ella", age: 22 };
   console.log(Object.keys(person)); // ["name", "age"]
   ```

2. **`Object.values()`**

   This method returns an array of the object's own enumerable property values.

   **Example:**

   ```javascript
   const person = { name: "Ella", age: 22 };
   console.log(Object.values(person)); // ["Ella", 22]
   ```

3. **`Object.entries()`**

   This method returns an array of key-value pairs as arrays, allowing you to iterate through both keys and values.

   **Example:**

   ```javascript
   const person = { name: "Ella", age: 22 };
   console.log(Object.entries(person)); // [["name", "Ella"], ["age", 22]]
   ```

4. **`Object.assign()`**

   This method copies the values of all enumerable properties from one or more source objects to a target object. It can be used for shallow cloning or merging objects.

   **Example:**

   ```javascript
   const obj1 = { name: "Fay", age: 25 };
   const obj2 = { gender: "female" };
   const merged = Object.assign({}, obj1, obj2);
   console.log(merged); // { name: "Fay", age: 25, gender: "female" }
   ```

5. **`Object.freeze()`**

   This method freezes an object, preventing new properties from being added and existing properties from being removed or modified.

   **Example:**

   ```javascript
   const person = { name: "Gina" };
   Object.freeze(person);
   person.name = "Hanna"; // The assignment is ignored
   console.log(person.name); // "Gina"
   ```

6. **`Object.seal()`**

   This method seals an object, preventing new properties from being added but allowing modifications to existing properties.

   **Example:**

   ```javascript
   const person = { name: "Harold" };
   Object.seal(person);
   person.name = "Ivan"; // Allowed
   delete person.name; // Not allowed
   console.log(person.name); // "Ivan"
   ```

7. **`defineProperty()` and `defineProperties()`**

   These methods allow you to define new properties on an object, or modify existing ones, with more control over property descriptors.

   **Example:**

   ```javascript
   const person = {};
   Object.defineProperty(person, "name", {
     value: "Jack",
     writable: true,
     configurable: true,
     enumerable: true,
   });
   console.log(person.name); // "Jack"
   ```

---

#### **d) Prototypes**

Every JavaScript object has a **prototype**, which is another object that it inherits properties and methods from. This prototype chain forms the basis of JavaScript’s inheritance model.

1. **Prototype Chain Mechanism**

   If an object doesn't have a property or method directly, JavaScript will search for it in the object’s prototype. This continues up the prototype chain until the property is found or `null` is reached.

   **Example:**

   ```javascript
   const animal = {
     speak() {
       console.log("Animal speaks");
     },
   };
   const dog = Object.create(animal);
   dog.speak(); // "Animal speaks"
   ```

2. **Inheritance Patterns**

   JavaScript's inheritance works by using the prototype chain. Objects inherit properties and methods from their prototypes. Constructor functions or classes are commonly used to set up inheritance patterns.

   **Example:**

   ```javascript
   function Animal(name) {
     this.name = name;
   }

   Animal.prototype.speak = function() {
     console.log(this.name + " makes a sound
   ```

.");
};

function Dog(name) {
Animal.call(this, name); // Inherit properties from Animal
}

Dog.prototype = Object.create(Animal.prototype); // Inherit methods
Dog.prototype.constructor = Dog;

const dog = new Dog("Rex");
dog.speak(); // "Rex makes a sound."

````

3. **Prototype Methods**

Prototype methods are methods that are added to an object’s prototype and can be inherited by all instances of the object.

**Example:**
```javascript
function Car(brand) {
  this.brand = brand;
}

Car.prototype.drive = function() {
  console.log(this.brand + " is driving!");
};

const myCar = new Car("Toyota");
myCar.drive();  // "Toyota is driving!"
````

---

#### **e) Patterns**

1. **Destructuring**

   Destructuring allows you to unpack values from objects into distinct variables.

   **Example:**

   ```javascript
   const person = { name: "James", age: 30 };
   const { name, age } = person;
   console.log(name, age); // "James", 30
   ```

2. **Property Shorthand**

   When the property key is the same as the variable name, you can use shorthand syntax to define properties in objects.

   **Example:**

   ```javascript
   const name = "Mia";
   const age = 26;
   const person = { name, age };
   console.log(person); // { name: "Mia", age: 26 }
   ```

3. **Computed Properties**

   You can use computed property names to dynamically create property keys from expressions.

   **Example:**

   ```javascript
   const key = "dynamicKey";
   const obj = { [key]: "value" };
   console.log(obj.dynamicKey); // "value"
   ```

---

### **Conclusion**

Objects are central to JavaScript and provide a powerful way to store and manipulate data. By understanding the various methods and patterns associated with objects, including how to define properties, use prototypes, and implement inheritance, you can effectively manage complex data structures in JavaScript. Whether using object literals, constructors, or factory functions, mastering objects is crucial for working efficiently with JavaScript.

### **3. Collections in JavaScript**

In JavaScript, collections refer to data structures that hold groups of values or key-value pairs. The two most common collection types are **Map** and **Set**, both of which have different behaviors and use cases. Additionally, JavaScript provides **WeakMap** and **WeakSet**, which are similar but designed to hold references to objects in a more memory-efficient way.

Let's explore these collection types in detail.

---

### **a) Map**

A **Map** is a collection of key-value pairs where both keys and values can be of any data type (primitive or object). Maps maintain the order of insertion, meaning the keys are ordered in the way they were added to the map.

#### **1. Basic Operations**

Maps support a variety of methods to perform operations like adding, retrieving, checking, and deleting key-value pairs.

- **`new Map()`**: Create a new map.
- **`set(key, value)`**: Add a new key-value pair to the map.
- **`get(key)`**: Retrieve the value for a given key.
- **`has(key)`**: Check if a key exists in the map.
- **`delete(key)`**: Remove a key-value pair from the map.
- **`clear()`**: Remove all key-value pairs from the map.

**Example:**

```javascript
// Creating a new Map
const map = new Map();

// Adding key-value pairs
map.set("name", "Alice");
map.set(1, "One");
map.set(true, "True");

// Accessing values
console.log(map.get("name")); // "Alice"
console.log(map.get(1)); // "One"
console.log(map.get(true)); // "True"

// Checking existence of keys
console.log(map.has("name")); // true
console.log(map.has("age")); // false

// Deleting a key-value pair
map.delete(1);
console.log(map.has(1)); // false

// Clearing the Map
map.clear();
console.log(map.size); // 0
```

#### **2. Iteration Methods**

Maps provide several methods for iterating over the key-value pairs:

- **`forEach(callback)`**: Iterates over the map and executes the callback for each key-value pair.
- **`keys()`**: Returns an iterator over the map’s keys.
- **`values()`**: Returns an iterator over the map’s values.
- **`entries()`**: Returns an iterator over the map’s entries (key-value pairs).

**Example:**

```javascript
const map = new Map([
  ["name", "Alice"],
  ["age", 25],
  ["city", "Wonderland"],
]);

// Using forEach
map.forEach((value, key) => {
  console.log(key, value);
});
// Output:
// name Alice
// age 25
// city Wonderland

// Iterating over keys
for (let key of map.keys()) {
  console.log(key); // "name", "age", "city"
}

// Iterating over values
for (let value of map.values()) {
  console.log(value); // "Alice", 25, "Wonderland"
}

// Iterating over entries (key-value pairs)
for (let [key, value] of map.entries()) {
  console.log(key, value); // "name" "Alice", "age" 25, "city" "Wonderland"
}
```

#### **3. WeakMap**

A **WeakMap** is similar to a regular **Map**, but with one key difference: the keys in a **WeakMap** must be objects, and the values are garbage-collected when there are no more references to the object key. This makes **WeakMap** useful for cases where you want to store private data or metadata associated with objects without preventing those objects from being garbage collected.

- **`set(key, value)`**: Add a key-value pair, where `key` must be an object.
- **`get(key)`**: Retrieve the value associated with the object key.
- **`has(key)`**: Check if a key exists in the **WeakMap**.
- **`delete(key)`**: Remove a key-value pair from the **WeakMap**.

**Example:**

```javascript
const weakMap = new WeakMap();

const obj1 = {};
const obj2 = {};

weakMap.set(obj1, "This is the first object");
weakMap.set(obj2, "This is the second object");

// Retrieve value
console.log(weakMap.get(obj1)); // "This is the first object"

// Check if key exists
console.log(weakMap.has(obj2)); // true

// Delete a key-value pair
weakMap.delete(obj1);
console.log(weakMap.has(obj1)); // false
```

**Note**: **WeakMap** doesn't have methods like `forEach()`, `keys()`, or `values()` because it is designed to not interfere with the garbage collection process.

---

### **b) Set**

A **Set** is a collection of unique values, where the same value cannot appear more than once. Unlike arrays, sets do not store duplicate values. Sets also maintain the order of insertion.

#### **1. Operations**

- **`new Set()`**: Creates a new Set.
- **`add(value)`**: Adds a value to the Set.
- **`has(value)`**: Checks if a value is in the Set.
- **`delete(value)`**: Removes a value from the Set.
- **`clear()`**: Removes all values from the Set.

**Example:**

```javascript
const set = new Set();

// Adding values
set.add(1);
set.add(2);
set.add(3);

// Adding a duplicate (won't be added)
set.add(2);

console.log(set.has(2)); // true
console.log(set.has(4)); // false

// Deleting a value
set.delete(3);
console.log(set.has(3)); // false

// Clearing the Set
set.clear();
console.log(set.size); // 0
```

#### **2. Iteration**

Sets support several iteration methods:

- **`forEach(callback)`**: Iterates over the Set, invoking the callback for each element.
- **`keys()`**: Returns an iterator over the Set’s values (since sets are just collections of unique values).
- **`values()`**: Same as `keys()`, as Set values are not associated with keys.
- **`entries()`**: Returns an iterator of key-value pairs (although keys and values are the same in a Set).

**Example:**

```javascript
const set = new Set([1, 2, 3]);

// Using forEach
set.forEach((value) => {
  console.log(value); // 1, 2, 3
});

// Iterating over Set values
for (let value of set.values()) {
  console.log(value); // 1, 2, 3
}

// Iterating over Set entries
for (let [key, value] of set.entries()) {
  console.log(key, value); // key and value are the same (1 1, 2 2, 3 3)
}
```

#### **3. WeakSet**

A **WeakSet** is similar to a regular **Set**, but it only stores objects as its values, and the objects are held weakly. This means that if there are no other references to an object stored in a **WeakSet**, that object can be garbage-collected.

- **`add(value)`**: Adds an object to the WeakSet.
- **`has(value)`**: Checks if an object exists in the WeakSet.
- **`delete(value)`**: Removes an object from the WeakSet.

**Example:**

```javascript
const weakSet = new WeakSet();

let obj1 = {};
let obj2 = {};

weakSet.add(obj1);
weakSet.add(obj2);

// Checking existence of objects
console.log(weakSet.has(obj1)); // true
console.log(weakSet.has(obj2)); // true

// Deleting an object
weakSet.delete(obj1);
console.log(weakSet.has(obj1)); // false
```

**Note**: Like **WeakMap**, **WeakSet** does not support iteration methods (`forEach()`, `keys()`, `values()`, `entries()`) because the values are weakly held and may be garbage-collected.

---

### **c) Usage Patterns**

Here are some common usage patterns for **Map**, **Set**, **WeakMap**, and **WeakSet**:

#### **1. Using Map for Key-Value Pair Storage**

**Maps** are ideal when you need to associate data with keys, and when the key can be anything (not just strings or symbols, like in regular objects).

- **Example**: Storing configuration settings, user preferences, or any associative data.
  ```javascript
  const config = new Map();
  config.set("theme", "dark");
  config.set("fontSize", 16);
  console.log(config.get("theme")); // "dark"
  ```

#### **2. Using Set for Unique Collections**

**Sets** are perfect for storing collections of unique values, such as when you need to remove duplicates from an array or ensure that a collection contains only distinct elements.

- **Example**: Storing unique tags in a blog system.
  ```javascript
  const tags = new Set();
  tags.add("JavaScript");
  tags.add("Web Development");
  tags.add("JavaScript"); // Duplicate, will not be added
  console.log(tags); // Set { "JavaScript", "Web Development" }
  ```

#### \*\*3. Using WeakMap for Private Data

\*\*

**WeakMap** is useful when you need to associate private or metadata with objects without preventing them from being garbage collected. For example, storing user-specific settings in a weak reference.

- **Example**: Associating user settings with a user object.
  ```javascript
  const userSettings = new WeakMap();
  let user = { id: 1 };
  userSettings.set(user, { theme: "dark", notifications: true });
  console.log(userSettings.get(user)); // { theme: "dark", notifications: true }
  ```

#### **4. Using WeakSet for Tracking Object Existence**

**WeakSet** is useful when you need to track a collection of objects but do not want to prevent those objects from being garbage-collected.

- **Example**: Keeping track of active elements in a UI without preventing them from being garbage collected when no longer needed.
  ```javascript
  const activeElements = new WeakSet();
  let element = document.getElementById("myElement");
  activeElements.add(element);
  ```

---

### **Conclusion**

Maps and Sets are powerful collection types in JavaScript, allowing you to handle key-value pairs and unique collections efficiently. **WeakMap** and **WeakSet** provide memory optimizations when working with objects, automatically cleaning up resources when no longer needed. By understanding when to use each of these collections and their methods, you can build more efficient and maintainable JavaScript applications.

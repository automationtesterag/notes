## A. **Control Structures**

Control structures are essential components in programming that determine the flow of execution in a program. They allow you to make decisions (conditionals) or repeat actions (loops).

### **1. Conditionals**

Conditionals allow you to execute different blocks of code based on whether a condition is true or false.

#### **if/else**

The `if` statement evaluates a condition and runs the corresponding block of code if the condition is true. If it’s false, you can provide an `else` block to execute an alternative code.

```javascript
let age = 18;

if (age >= 18) {
  console.log("You are an adult.");
} else {
  console.log("You are a minor.");
}
```

**Explanation**:

- The condition `age >= 18` is evaluated. If true, `"You are an adult."` is printed.
- If false, the `else` block prints `"You are a minor."`.

#### **switch**

The `switch` statement is used to execute one of many code blocks based on the value of an expression. It's often used when there are multiple possible conditions for a single variable.

```javascript
let fruit = "apple";

switch (fruit) {
  case "apple":
    console.log("You chose an apple.");
    break;
  case "banana":
    console.log("You chose a banana.");
    break;
  case "orange":
    console.log("You chose an orange.");
    break;
  default:
    console.log("Unknown fruit.");
}
```

**Explanation**:

- `switch(fruit)` checks the value of `fruit` and matches it with the cases.
- If `fruit` is `'apple'`, the first case is executed.
- The `break` statement ensures the program exits the `switch` block once the case is executed.
- The `default` is a fallback case if no match is found.

#### **ternary operator**

The ternary operator is a shorthand for the `if/else` statement. It allows you to evaluate a condition and choose between two values based on whether the condition is true or false.

```javascript
let number = 10;
let result = number % 2 === 0 ? "Even" : "Odd";
console.log(result); // Output: Even
```

**Explanation**:

- `(number % 2 === 0)` is the condition that checks if `number` is even.
- If true, it returns `'Even'`; if false, it returns `'Odd'`.

---

### **2. Loops**

Loops are used to repeat a block of code multiple times. Depending on the type of loop, the condition or iteration behavior may differ.

#### **for loop**

The `for` loop is commonly used when you know how many times you want to iterate over a block of code.

```javascript
for (let i = 0; i < 5; i++) {
  console.log(i); // Output: 0, 1, 2, 3, 4
}
```

**Explanation**:

- The loop initializes `i = 0`, then checks the condition `i < 5`.
- After each iteration, `i` is incremented by 1 (`i++`).
- The loop will continue until the condition evaluates to false.

#### **for...in loop**

The `for...in` loop is used to iterate over the **keys** (properties) of an object or the **indices** of an array.

```javascript
let person = { name: "Alice", age: 25, city: "New York" };

for (let key in person) {
  console.log(key, person[key]); // Output: name Alice, age 25, city New York
}
```

**Explanation**:

- The loop iterates over each key in the `person` object.
- For each iteration, it outputs the key and the corresponding value (`person[key]`).

#### **for...of loop**

The `for...of` loop is used to iterate over the **values** of iterable objects (like arrays, strings, etc.).

```javascript
let numbers = [1, 2, 3, 4, 5];

for (let num of numbers) {
  console.log(num); // Output: 1, 2, 3, 4, 5
}
```

**Explanation**:

- The loop iterates over the values in the array `numbers`.
- On each iteration, the value of `num` is printed.

#### **while loop**

The `while` loop continues to run as long as the condition is true. It checks the condition before running the code inside the loop.

```javascript
let counter = 0;

while (counter < 3) {
  console.log(counter); // Output: 0, 1, 2
  counter++;
}
```

**Explanation**:

- The loop runs as long as `counter < 3`.
- `counter++` increments `counter` after each iteration, and the loop stops when the condition is no longer true.

#### **do...while loop**

The `do...while` loop is similar to `while`, but the condition is checked **after** the code block is executed. This ensures that the block of code runs **at least once**.

```javascript
let num = 0;

do {
  console.log(num); // Output: 0, 1, 2
  num++;
} while (num < 3);
```

**Explanation**:

- The loop first runs the block (printing `num`), and then checks the condition (`num < 3`).
- Since the condition is checked after the code block runs, the loop executes even if the condition is false initially.

#### **break**

The `break` statement is used to immediately exit a loop or a `switch` statement. It stops the loop from executing further iterations, even if the condition is still true.

```javascript
for (let i = 0; i < 5; i++) {
  if (i === 3) {
    break; // Exits the loop when i equals 3
  }
  console.log(i); // Output: 0, 1, 2
}
```

**Explanation**:

- The loop runs until `i === 3`, at which point the `break` statement terminates the loop early.
- The loop does not print `3` or any higher numbers.

#### **continue**

The `continue` statement is used to skip the current iteration of a loop and continue with the next iteration.

```javascript
for (let i = 0; i < 5; i++) {
  if (i === 2) {
    continue; // Skips this iteration when i equals 2
  }
  console.log(i); // Output: 0, 1, 3, 4
}
```

**Explanation**:

- When `i === 2`, the `continue` statement skips the `console.log(i)` statement and moves to the next iteration, hence `2` is never printed.

#### **labeled statements**

A labeled statement is a way to give a label (or name) to a loop or block of code. It’s useful when you want to break or continue from a nested loop.

```javascript
outerLoop: for (let i = 0; i < 3; i++) {
  for (let j = 0; j < 3; j++) {
    if (i === 1 && j === 1) {
      break outerLoop; // Breaks the outer loop when i=1 and j=1
    }
    console.log(`i: ${i}, j: ${j}`);
  }
}
```

**Explanation**:

- The outer loop is labeled as `outerLoop`.
- The `break outerLoop` statement exits the outer loop when `i === 1` and `j === 1`, even though the inner loop hasn’t finished iterating.

---

### **Summary of Control Flow Structures**:

- **Conditionals**: Control the flow of execution based on certain conditions (`if/else`, `switch`, and the ternary operator).
- **Loops**: Execute a block of code repeatedly, with different types suited to different use cases (`for`, `while`, `do...while`, etc.).
- **Control Statements**: `break` and `continue` modify the flow inside loops. `break` exits the loop, and `continue` skips the current iteration.
- **Labeled Statements**: Labels are used with loops or blocks to specify which loop or block to break or continue from.

These control structures form the foundation of how we create logic and control the flow of our programs.

---

## **B. Functions**

In JavaScript, functions are a fundamental building block for organizing code and performing specific tasks. Functions allow you to encapsulate code into reusable units, making your program more modular and maintainable.

### **1. Function Declarations**

There are different ways to define functions in JavaScript. The three most common approaches are **function statements**, **function expressions**, and **arrow functions**.

#### **Function Statements (Function Declarations)**

A function declaration is the most common and traditional way to define a function. It starts with the `function` keyword, followed by the function name, parameters, and the block of code to execute.

```javascript
function greet(name) {
  console.log(`Hello, ${name}!`);
}

greet("Alice"); // Output: Hello, Alice!
```

**Explanation**:

- The function `greet` takes a single parameter `name` and logs a greeting message.
- Function declarations are **hoisted**, meaning they can be called before their definition in the code.

#### **Function Expressions**

A function expression defines a function inside an expression, typically assigned to a variable. Unlike function declarations, function expressions are **not hoisted**.

```javascript
const add = function (a, b) {
  return a + b;
};

console.log(add(2, 3)); // Output: 5
```

**Explanation**:

- The anonymous function is assigned to the `add` variable.
- Function expressions are evaluated at runtime, so you can’t call them before they are defined in the code.

#### **Arrow Functions**

Arrow functions provide a more concise syntax for writing functions. They are often used in situations where you need a short, inline function. They also have a unique feature in how they handle the `this` keyword.

```javascript
const multiply = (a, b) => a * b;

console.log(multiply(3, 4)); // Output: 12
```

**Explanation**:

- The `=>` syntax is the defining feature of arrow functions.
- Arrow functions implicitly return the result of the expression, so there’s no need for the `return` keyword when there’s a single expression.
- Arrow functions don’t have their own `this` context, which makes them useful for certain scenarios (we’ll discuss this further under **"this keyword"**).

---

### **2. Parameters**

JavaScript functions can handle parameters in several ways. You can use **default parameters**, **rest parameters**, and the **arguments object** to work with dynamic sets of values passed to functions.

#### **Default Parameters**

Default parameters allow you to specify default values for parameters when they are not provided by the caller.

```javascript
function greet(name = "Guest") {
  console.log(`Hello, ${name}!`);
}

greet("Alice"); // Output: Hello, Alice!
greet(); // Output: Hello, Guest!
```

**Explanation**:

- If the `name` parameter is not provided when calling `greet()`, it defaults to `'Guest'`.

#### **Rest Parameters**

Rest parameters allow you to pass an arbitrary number of arguments to a function. It collects the remaining arguments into an array.

```javascript
function sum(...numbers) {
  return numbers.reduce((acc, num) => acc + num, 0);
}

console.log(sum(1, 2, 3, 4)); // Output: 10
```

**Explanation**:

- The `...numbers` syntax collects all the arguments into an array.
- The `reduce` method is used to sum all the numbers in the array.

#### **Arguments Object**

The `arguments` object is an array-like object available inside all functions (except arrow functions) that contains all the arguments passed to the function, regardless of how they are declared.

```javascript
function printArgs() {
  console.log(arguments);
}

printArgs(1, 2, 3); // Output: { '0': 1, '1': 2, '2': 3 }
```

**Explanation**:

- The `arguments` object contains all the arguments passed to the function, indexed by their position.
- Note that `arguments` is not available in arrow functions. Instead, you should use **rest parameters**.

---

### **3. Scope & Context**

Understanding **scope** and **context** is crucial in JavaScript. These concepts determine where variables and functions are accessible and how `this` behaves in different situations.

#### **Lexical Scope**

Lexical scope refers to the scope in which a function was defined, not where it’s called. Variables are available within the scope where they are declared.

```javascript
function outer() {
  let outerVar = "I am outside!";

  function inner() {
    console.log(outerVar); // Output: I am outside!
  }

  inner();
}

outer();
```

**Explanation**:

- `inner()` has access to `outerVar` because of lexical scoping. Even though `inner()` is called within a different scope, it can still access variables defined in its outer function's scope.

#### **Closure**

A **closure** occurs when a function remembers the scope in which it was created, even after the outer function has finished executing.

```javascript
function outer() {
  let count = 0;

  return function inner() {
    count++;
    console.log(count);
  };
}

const counter = outer();
counter(); // Output: 1
counter(); // Output: 2
```

**Explanation**:

- The `inner` function forms a closure because it "remembers" the `count` variable from the `outer` function, even though `outer` has finished executing.

#### **`this` Keyword**

The `this` keyword refers to the **context** in which a function is called. Its value depends on how the function is called.

1. **Global Context**:

   - In the global execution context (outside of any function), `this` refers to the global object (`window` in browsers).

   ```javascript
   console.log(this); // In browsers, it logs: Window { ... }
   ```

2. **Object Method**:

   - When a function is called as a method of an object, `this` refers to the object.

   ```javascript
   const person = {
     name: "Alice",
     greet() {
       console.log(`Hello, ${this.name}`);
     },
   };

   person.greet(); // Output: Hello, Alice
   ```

3. **Arrow Functions**:

   - Arrow functions do not have their own `this`; they inherit it from the enclosing scope (lexical scoping).

   ```javascript
   const person = {
     name: "Alice",
     greet: () => {
       console.log(this.name); // 'this' refers to the global object
     },
   };

   person.greet(); // Output: undefined (or global object name if in non-strict mode)
   ```

---

### **4. Advanced Patterns**

Advanced function patterns are used to create more flexible, reusable, and optimized functions.

#### **Higher-Order Functions**

A higher-order function is a function that either takes one or more functions as arguments or returns a function as a result.

```javascript
function applyOperation(a, b, operation) {
  return operation(a, b);
}

const add = (x, y) => x + y;
const result = applyOperation(5, 3, add); // Output: 8
```

**Explanation**:

- `applyOperation` is a higher-order function because it accepts another function (`add`) as an argument.
- Higher-order functions are often used in functional programming to create flexible and reusable code.

#### **Currying**

Currying is the process of transforming a function that takes multiple arguments into a series of functions, each taking a single argument.

```javascript
function multiply(a) {
  return function (b) {
    return a * b;
  };
}

const multiplyBy2 = multiply(2);
console.log(multiplyBy2(3)); // Output: 6
```

**Explanation**:

- The function `multiply` returns a new function that takes one argument `b` and multiplies it by `a`.
- Currying is useful when you want to create specialized versions of a function (like `multiplyBy2`).

#### **Composition**

Composition involves combining multiple functions into a single function, where the output of one function is passed as the input to the next.

```javascript
const add5 = (x) => x + 5;
const multiply2 = (x) => x * 2;

const add5ThenMultiply2 = (x) => multiply2(add5(x));

console.log(add5ThenMultiply2(3)); // Output: 16
```

**Explanation**:

- `add5ThenMultiply2` combines `add5` and `multiply2`. It first adds 5 to `x`, then multiplies the result by 2.

#### **Memoization**

Memoization is an optimization technique where the result of a function call is cached so that it doesn’t need to be recomputed when the same inputs are provided again.

```javascript
function memoize(fn) {
  const cache = {};

  return function (...args) {
    const key = JSON.stringify(args);

    if (cache[key]) {
      console.log("Fetching from cache");
      return cache[key];
    }

    const result = fn(...args);
    cache[key] = result;
    return result;
  };
}

const add = (a, b) => a + b;
const memoizedAdd = memoize(add);

console.log(memoizedAdd(2, 3)); // Output: 5
console.log(memoizedAdd(2, 3)); // Output: Fetching from cache, 5
```

**Explanation**:

- `memoize` caches the results of the `add` function, so subsequent calls with the same arguments return the cached result instead of recalculating it.

---

### **Summary of Functions**

- **Function Declarations**: `function` statements, expressions, and arrow functions.
- **Parameters**: Use **default parameters**, **rest parameters**, and **arguments** for flexible function signatures.
- **Scope & Context**: Functions have access to **lexical scope**, and closures allow functions to remember the scope they were created in. The value of `this` depends on how a function is called.
- **Advanced Patterns**: Techniques like **higher-order functions**, **currying**, **composition**, and **memoization** make functions more powerful and efficient.

These features enable you to write more modular, reusable, and optimized code in JavaScript.

Sure! Let's go over the ES6+ features in detail. I'll explain each feature, provide examples, and include any additional relevant topics that might be helpful in understanding modern JavaScript syntax, modules, and classes.

---

## **3. ES6+ Features**

ES6 (ECMAScript 2015) and later versions of JavaScript introduced many new features that make the language more powerful, concise, and easier to work with. These features are commonly referred to as **ES6+** (ES6 and beyond), and they continue to improve JavaScript development practices.

### **A. Modern Syntax**

ES6 introduced many improvements in syntax, making JavaScript code more readable, concise, and expressive. Let's break down the main syntax-related features.

#### **1. Destructuring**

Destructuring allows you to unpack values from arrays or objects and assign them to variables in a more concise way.

**Array Destructuring**:

```javascript
const colors = ["red", "green", "blue"];
const [firstColor, secondColor] = colors;

console.log(firstColor); // Output: red
console.log(secondColor); // Output: green
```

**Object Destructuring**:

```javascript
const person = { name: "Alice", age: 30 };
const { name, age } = person;

console.log(name); // Output: Alice
console.log(age); // Output: 30
```

**Explanation**:

- With array destructuring, the values from the array are assigned to variables in the order they appear.
- With object destructuring, properties of the object are assigned to variables with the same name as the keys.

#### **2. Spread/Rest**

Both spread and rest use the `...` syntax but are applied in different contexts.

**Spread (expanding elements/objects)**:

- In arrays:

```javascript
const numbers = [1, 2, 3];
const newNumbers = [...numbers, 4, 5];

console.log(newNumbers); // Output: [1, 2, 3, 4, 5]
```

- In objects:

```javascript
const user = { name: "Alice", age: 30 };
const updatedUser = { ...user, city: "New York" };

console.log(updatedUser); // Output: { name: 'Alice', age: 30, city: 'New York' }
```

**Rest (collecting elements/arguments)**:

- In function arguments:

```javascript
function sum(...numbers) {
  return numbers.reduce((acc, num) => acc + num, 0);
}

console.log(sum(1, 2, 3, 4)); // Output: 10
```

- In object or array destructuring:

```javascript
const person = { name: "Alice", age: 30, city: "New York" };
const { name, ...rest } = person;

console.log(name); // Output: Alice
console.log(rest); // Output: { age: 30, city: 'New York' }
```

**Explanation**:

- **Spread** is used to expand elements from an array or object.
- **Rest** collects remaining values into an array (for function arguments or in destructuring).

#### **3. Template Literals**

Template literals provide a way to embed expressions into strings, making string concatenation easier and more readable.

```javascript
const name = "Alice";
const age = 30;

console.log(`Hello, my name is ${name} and I am ${age} years old.`);
// Output: Hello, my name is Alice and I am 30 years old.
```

**Explanation**:

- Template literals use backticks (\`\`) and `${}` syntax for embedding expressions inside strings.
- They also support multi-line strings without the need for escape characters.

#### **4. Optional Chaining**

Optional chaining allows you to safely access deeply nested properties of an object without having to check for `null` or `undefined` at each level.

```javascript
const user = {
  profile: {
    name: "Alice",
    address: {
      city: "New York",
    },
  },
};

console.log(user?.profile?.address?.city); // Output: New York
console.log(user?.profile?.phone?.number); // Output: undefined
```

**Explanation**:

- The `?.` operator checks if the property exists before accessing it. If any part of the chain is `null` or `undefined`, it short-circuits and returns `undefined` instead of throwing an error.

#### **5. Nullish Coalescing**

The nullish coalescing operator (`??`) provides a default value when a variable is `null` or `undefined`, unlike the logical OR (`||`) which returns the default value for other falsy values (like `0`, `''`, `false`).

```javascript
const foo = null;
const bar = "Hello";

console.log(foo ?? bar); // Output: Hello
console.log(0 ?? bar); // Output: 0 (because 0 is not null or undefined)
console.log("" ?? bar); // Output: '' (because '' is not null or undefined)
```

**Explanation**:

- `??` ensures the fallback value is only used when the left operand is `null` or `undefined`.

---

### **B. Modules**

ES6 introduced modules, which allow you to split your code into smaller files, improving code organization, maintainability, and reusability. Modules can export and import functions, objects, or variables between files.

#### **1. Import/Export**

Modules allow you to separate concerns by exporting pieces of functionality and then importing them into other files.

**Exporting**:

```javascript
// math.js
export const add = (a, b) => a + b;
export const multiply = (a, b) => a * b;
```

**Importing**:

```javascript
// app.js
import { add, multiply } from "./math";

console.log(add(1, 2)); // Output: 3
console.log(multiply(2, 3)); // Output: 6
```

**Explanation**:

- The `export` keyword makes the functions `add` and `multiply` available to be imported in other files.
- The `import` statement is used to bring the functions into the current file.

#### **2. Default Exports**

You can also export a single value (function, class, object) as the **default export**.

**Exporting a default value**:

```javascript
// math.js
export default function add(a, b) {
  return a + b;
}
```

**Importing a default value**:

```javascript
// app.js
import add from "./math";

console.log(add(3, 4)); // Output: 7
```

**Explanation**:

- A default export allows you to export one thing per module, and you can import it without using curly braces.
- When importing, you can choose any name for the default export.

#### **3. Named Exports**

Named exports allow you to export multiple named values from a single module.

**Exporting named values**:

```javascript
// math.js
export const subtract = (a, b) => a - b;
export const divide = (a, b) => a / b;
```

**Importing named values**:

```javascript
// app.js
import { subtract, divide } from "./math";

console.log(subtract(5, 2)); // Output: 3
console.log(divide(6, 2)); // Output: 3
```

**Explanation**:

- With named exports, you can export multiple values from the same module and import them using their names.

#### **4. Module Patterns**

You can combine `import`/`export` with other patterns to organize your code better. A common pattern is the **Module Pattern**, which uses closures to encapsulate private data and expose only the necessary functionality.

```javascript
// counter.js
const counter = (function () {
  let count = 0;

  return {
    increment() {
      count++;
      return count;
    },
    getCount() {
      return count;
    },
  };
})();

export default counter;
```

```javascript
// app.js
import counter from "./counter";

console.log(counter.increment()); // Output: 1
console.log(counter.getCount()); // Output: 1
```

**Explanation**:

- The counter is a self-invoking function that returns an object with methods. The `count` variable is private and cannot be accessed directly, ensuring data encapsulation.

---

### **C. Classes**

JavaScript ES6 introduced **classes**, which provide a clearer, more structured way to create objects and manage inheritance.

#### **1. Class Syntax**

Classes are a blueprint for creating objects with shared methods and properties.

```javascript
class Person {
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }

  greet() {
    console.log(`Hello, my name is ${this.name}`);
  }
}

const person = new Person("Alice", 30);
person.greet(); // Output: Hello, my name is Alice
```

**Explanation**:

- The `constructor` method is used to initialize the object with properties like `name` and `age`.
- Methods are defined inside the class body and can be called on instances of the class.

#### **2. Constructor**

The `constructor` method is called when an object is instantiated from the class. It initializes the properties of the object.

```javascript
class Car {
  constructor(make, model) {
    this.make = make;
    this.model = model;
  }
}
```

**Explanation**:

- The `constructor` is special in that it runs automatically when the class is instantiated using the `new` keyword.

#### **3. Methods**

Methods are functions defined inside the class and are used by instances of the class.

```javascript
class Animal {
  speak() {
    console.log("Animal sound");
  }
}

const dog = new Animal();
dog.speak(); // Output: Animal sound
```

#### **4. Inheritance**

JavaScript classes support **inheritance**, allowing you to create a new class that inherits methods and properties from another class.

```javascript
class Animal {
  speak() {
    console.log("Animal sound");
  }
}

class Dog extends Animal {
  speak() {
    console.log("Woof!");
  }
}

const dog = new Dog();
dog.speak(); // Output: Woof!
```

**Explanation**:

- The `Dog` class extends the `Animal` class, inheriting the `speak` method but overriding it with its own implementation.

#### **5. Static Members**

Static methods or properties are associated with the class itself, not with instances of the class.

```javascript
class MathUtils {
  static add(a, b) {
    return a + b;
  }
}

console.log(MathUtils.add(3, 4)); // Output: 7
```

**Explanation**:

- Static methods are called on the class itself (`MathUtils.add`) rather than on instances of the class.

#### **6. Private Fields**

ES2022 introduced private fields and methods in classes. These are fields that cannot be accessed outside the class.

```javascript
class Counter {
  #count = 0; // private field

  increment() {
    this.#count++;
    return this.#count;
  }
}

const counter = new Counter();
console.log(counter.increment()); // Output: 1
console.log(counter.#count); // SyntaxError: Private field '#count' must be declared in an enclosing class
```

**Explanation**:

- Private fields are declared with `#`, and they are inaccessible from outside the class, helping to encapsulate state.

---

### **Summary**

- **Modern Syntax**: ES6+ introduces cleaner, more concise ways to handle arrays, objects, and strings (via destructuring, spread/rest, template literals, etc.).
- **Modules**: ES6 modules allow you to import/export code between different files, improving organization and maintainability.
- **Classes**: Classes provide a structured way to create objects and implement inheritance, static members, and private fields.

These ES6+ features help developers write cleaner, more modular, and efficient JavaScript code. They significantly enhance the language's ability to handle large-scale applications with improved maintainability and readability.

Asynchronous programming in JavaScript is critical for handling operations that take time, such as reading files, fetching data from a server, or waiting for a user input, without blocking the main execution thread. In this section, we’ll explore how to work with asynchronous code using **Promises**, **async/await**, and understand the **Event Loop** that powers JavaScript’s concurrency model.

---

## **4. Asynchronous Programming**

JavaScript's single-threaded nature means that it can only execute one task at a time, but we often need to handle operations that can take time, like API requests or animations. Asynchronous programming enables JavaScript to perform other tasks while waiting for these long-running operations to complete.

### **A. Promises**

A **Promise** represents a value that might not be available yet but will be resolved at some point in the future, either successfully or with an error.

#### **1. Creation of Promises**

Promises are created using the `new Promise()` constructor, which takes an executor function with two parameters: `resolve` and `reject`.

```javascript
const myPromise = new Promise((resolve, reject) => {
  const success = true; // Change this to false to test rejection

  if (success) {
    resolve("Promise resolved!");
  } else {
    reject("Promise rejected!");
  }
});

myPromise
  .then((message) => console.log(message)) // Output: Promise resolved!
  .catch((error) => console.error(error)); // Will log 'Promise rejected!' if `reject` is called
```

**Explanation**:

- The `resolve` function is called when the promise is successfully fulfilled.
- The `reject` function is called when the promise encounters an error.

#### **2. States of a Promise**

A promise can be in one of the following states:

- **Pending**: The promise is still being executed (the operation is still in progress).
- **Resolved (Fulfilled)**: The promise has been successfully completed.
- **Rejected**: The promise has failed (an error occurred).

```javascript
const pendingPromise = new Promise((resolve, reject) => {});
console.log(pendingPromise); // Output: Promise { <pending> }
```

#### **3. Chaining Promises**

Once a promise is resolved or rejected, you can chain `.then()` for handling success or `.catch()` for handling errors. The `.then()` method returns a new promise, which allows chaining.

```javascript
const fetchData = new Promise((resolve, reject) => {
  setTimeout(() => resolve("Data fetched!"), 1000);
});

fetchData
  .then((data) => {
    console.log(data); // Output: Data fetched!
    return data + " More data"; // Chaining with a new promise
  })
  .then((data) => {
    console.log(data); // Output: Data fetched! More data
  })
  .catch((error) => {
    console.error("Error:", error);
  });
```

**Explanation**:

- The second `.then()` will receive the result from the first `.then()` (chaining the promises).
- You can chain multiple `.then()` blocks to perform additional tasks.

#### **4. Error Handling**

Promises can fail, and when they do, errors should be handled using `.catch()`. The `.catch()` method captures errors thrown in any of the `.then()` blocks.

```javascript
const faultyPromise = new Promise((resolve, reject) => {
  setTimeout(() => reject("Something went wrong!"), 1000);
});

faultyPromise
  .then(() => console.log("This will not run"))
  .catch((error) => console.error(error)); // Output: Something went wrong!
```

#### **5. Promise Methods**

JavaScript provides several built-in methods to handle multiple promises at once:

- **`Promise.all()`**: Waits for all promises to resolve, and returns an array of results. If any promise is rejected, it immediately returns the rejection reason.

```javascript
const p1 = Promise.resolve(3);
const p2 = new Promise((resolve) => setTimeout(resolve, 1000, "foo"));
const p3 = 42;

Promise.all([p1, p2, p3]).then((values) => {
  console.log(values); // Output: [3, 'foo', 42]
});
```

- **`Promise.race()`**: Returns the result of the first promise that settles (either resolves or rejects).

```javascript
const p1 = new Promise((resolve, reject) => setTimeout(resolve, 1000, "First"));
const p2 = new Promise((resolve, reject) => setTimeout(resolve, 500, "Second"));

Promise.race([p1, p2]).then((value) => {
  console.log(value); // Output: Second
});
```

- **`Promise.allSettled()`**: Waits for all promises to settle (whether resolved or rejected) and returns an array of results, including status (fulfilled or rejected).

```javascript
const p1 = Promise.resolve(3);
const p2 = Promise.reject("Error occurred");
const p3 = new Promise((resolve) => setTimeout(resolve, 1000, "Completed"));

Promise.allSettled([p1, p2, p3]).then((results) => {
  console.log(results);
  // Output:
  // [
  //   { status: 'fulfilled', value: 3 },
  //   { status: 'rejected', reason: 'Error occurred' },
  //   { status: 'fulfilled', value: 'Completed' }
  // ]
});
```

- **`Promise.any()`**: Returns the first promise that resolves. If all promises reject, it returns an `AggregateError`.

```javascript
const p1 = new Promise((_, reject) => setTimeout(reject, 500, "Failed"));
const p2 = new Promise((resolve) => setTimeout(resolve, 1000, "Success"));

Promise.any([p1, p2]).then((value) => {
  console.log(value); // Output: Success
});
```

- **`Promise.resolve()` and `Promise.reject()`**: Used to return a resolved or rejected promise respectively.

```javascript
Promise.resolve("Resolved!").then((value) => console.log(value)); // Output: Resolved!
Promise.reject("Rejected!").catch((error) => console.error(error)); // Output: Rejected!
```

---

### **B. Async/Await**

**Async/Await** provides a more readable and synchronous-like way to work with asynchronous code, making it easier to write and maintain.

#### **1. `async` Functions**

An **`async`** function is a function that always returns a **Promise**. If the function returns a value, that value is wrapped in a resolved promise.

```javascript
async function fetchData() {
  return "Data fetched!";
}

fetchData().then((data) => console.log(data)); // Output: Data fetched!
```

**Explanation**:

- An `async` function will automatically return a resolved promise. If you return a value from an `async` function, JavaScript wraps it in a promise.

#### **2. `await` Operator**

The **`await`** operator can only be used inside `async` functions. It waits for a promise to resolve or reject and returns the result (or throws an error if the promise is rejected).

```javascript
async function fetchData() {
  const data = await new Promise((resolve) =>
    setTimeout(resolve, 1000, "Data fetched!")
  );
  console.log(data); // Output: Data fetched!
}

fetchData();
```

**Explanation**:

- The `await` operator pauses the execution of the `async` function until the promise resolves.

#### **3. Error Handling with Async/Await**

You can use **`try...catch`** blocks to handle errors in `async` functions, just like you would in synchronous code.

```javascript
async function fetchData() {
  try {
    const data = await new Promise((resolve, reject) =>
      setTimeout(reject, 1000, "Error occurred!")
    );
    console.log(data);
  } catch (error) {
    console.error(error); // Output: Error occurred!
  }
}

fetchData();
```

**Explanation**:

- If the promise rejects, the `catch` block handles the error.

#### **4. Sequential vs Parallel Execution**

- **Sequential**: Awaiting each promise one by one:

```javascript
async function fetchSequential() {
  const result1 = await fetchData1();
  const result2 = await fetchData2();
  console.log(result1, result2);
}
```

- **Parallel**: Running promises in parallel and awaiting them together:

```javascript
async function fetchParallel() {
  const [result1, result2] = await Promise.all([fetchData1(), fetchData2()]);
  console.log(result1, result2);
}
```

**Explanation**:

- **Sequential** execution waits for one promise to finish before starting the next one.
- **Parallel** execution runs both promises simultaneously and waits for both to resolve before proceeding.

#### **5. Performance Considerations**

Although `async/await` provides cleaner syntax, excessive use of `await` in a sequential manner can introduce delays. To optimize performance, run independent promises in parallel when possible.

```javascript
async function fetchData() {
  const data1 = fetchData1(); // Initiates the promise
  const data2 = fetchData2(); // Initiates the promise

  // Await both promises in parallel
  const result1 = await data1;
  const result2 = await data2;

  console.log(result1, result2);
}
```

---

### **C. Event Loop**

The **Event Loop** is the mechanism that allows JavaScript to perform non-blocking I/O operations by delegating the execution of code to the browser or Node.js API, while executing synchronous code in a single thread.

#### **1. Call Stack**

The **Call Stack** is where JavaScript keeps track of function execution. Functions are added to the stack when called and removed when they complete.

- If a function calls another function, the new function is added to the call stack.
- Once a function completes, it's popped off the stack.

#### **2. Task Queue**

The **Task Queue** (also called **Callback Queue**) is where asynchronous code like `setTimeout`, DOM events, or I/O operations wait to be executed. Once the call stack is empty, the event loop moves the next task from the task queue to the call stack.

#### **3. Microtask Queue**

The **Microtask Queue** handles promises and other microtasks like `Promise.then()` and `process.nextTick()` in Node.js. Microtasks are given priority over regular tasks in the task queue.

#### **4. Event Handling**

Event handlers are queued in the task queue and executed once the call stack is empty. Asynchronous events like clicks, fetch responses, and timers use the task queue.

---

### **Summary**

- **Promises** are used to handle asynchronous operations, and can be chained for sequential operations, with `.all()`, `.race()`, and other utility methods for handling multiple promises.
- **Async/Await** makes asynchronous code look and behave like synchronous code, providing cleaner and more readable code for handling asynchronous operations.
- **Event Loop** explains how JavaScript handles concurrency, using the call stack, task queue, and microtask queue to manage asynchronous tasks.

These concepts allow JavaScript to perform non-blocking operations and run efficiently even when dealing with complex, time-consuming tasks.

Error handling is an essential part of programming in JavaScript. It ensures that the application behaves predictably even when unexpected situations arise. JavaScript provides mechanisms to detect, handle, and propagate errors, allowing you to manage failures effectively.

Let's explore **Error Handling** in JavaScript in detail, covering the different error types and handling mechanisms.

---

## **5. Error Handling**

Error handling in JavaScript allows you to catch runtime issues and ensure your code can gracefully handle them, rather than crashing or producing unpredictable behavior.

### **A. Error Types**

Errors in JavaScript can be categorized into several types, from generic errors to more specific built-in error classes. You can also create your own custom errors to handle application-specific issues.

#### **1. Error Object**

The `Error` object is the base object for all error types in JavaScript. When you throw an error, an `Error` object is created, which contains details about the error, including a message and a stack trace.

```javascript
try {
  throw new Error("Something went wrong!");
} catch (error) {
  console.log(error.message); // Output: Something went wrong!
  console.log(error.stack); // Outputs the stack trace
}
```

**Explanation**:

- The `Error` constructor creates a new error object.
- `error.message` gives a human-readable description of the error.
- `error.stack` provides a stack trace that shows where the error occurred in the code.

#### **2. Built-in Errors**

JavaScript has several built-in error classes that inherit from the `Error` object. These provide specific information about different types of errors. Common built-in errors include:

- **`SyntaxError`**: Occurs when there’s a mistake in the syntax of your code.

```javascript
try {
  eval("foo bar"); // Throws a SyntaxError
} catch (error) {
  console.log(error instanceof SyntaxError); // Output: true
}
```

- **`ReferenceError`**: Thrown when an invalid reference is made, like accessing a variable that is not declared.

```javascript
try {
  console.log(nonExistentVar); // ReferenceError: nonExistentVar is not defined
} catch (error) {
  console.log(error instanceof ReferenceError); // Output: true
}
```

- **`TypeError`**: Occurs when a value is not of the expected type. For example, calling a non-function as a function.

```javascript
try {
  null.f(); // Throws a TypeError
} catch (error) {
  console.log(error instanceof TypeError); // Output: true
}
```

- **`RangeError`**: Thrown when a number is outside of an allowable range, such as an invalid array length.

```javascript
try {
  new Array(-1); // Throws a RangeError
} catch (error) {
  console.log(error instanceof RangeError); // Output: true
}
```

- **`EvalError`**: Rarely used in modern JavaScript, this error was related to the use of the `eval()` function. It's mostly deprecated.

```javascript
try {
  throw new EvalError("EvalError");
} catch (error) {
  console.log(error instanceof EvalError); // Output: true
}
```

- **`URIError`**: Thrown when there's an error in encoding or decoding a URI.

```javascript
try {
  decodeURIComponent("%"); // Throws a URIError
} catch (error) {
  console.log(error instanceof URIError); // Output: true
}
```

#### **3. Custom Errors**

You can create custom error classes to represent specific application errors. By extending the built-in `Error` class, you can add custom properties and methods to your error objects.

```javascript
class CustomError extends Error {
  constructor(message, code) {
    super(message); // Calls the parent Error constructor
    this.name = this.constructor.name; // Sets the error name to the class name
    this.code = code; // Custom property to store an error code
  }
}

try {
  throw new CustomError("Custom error occurred!", 400);
} catch (error) {
  console.log(error.name); // Output: CustomError
  console.log(error.message); // Output: Custom error occurred!
  console.log(error.code); // Output: 400
}
```

**Explanation**:

- `CustomError` extends the `Error` class and adds a custom `code` property.
- You can create any type of custom error with additional details that are meaningful to your application.

---

### **B. Handling Mechanisms**

JavaScript provides several mechanisms to handle errors. These allow you to catch, rethrow, or log errors as necessary. The most common methods are `try...catch`, `throw`, and `finally`.

#### **1. `try...catch`**

The `try...catch` statement is the most common way to handle errors in JavaScript. Code that may cause an error is placed inside the `try` block, and if an error occurs, the control is transferred to the `catch` block.

```javascript
try {
  const result = riskyFunction(); // This may throw an error
} catch (error) {
  console.error("An error occurred:", error.message);
}
```

**Explanation**:

- The `try` block contains code that may throw an error.
- If an error occurs, it is caught by the `catch` block, where you can handle it.

#### **2. `throw`**

You can use the `throw` statement to explicitly throw errors, either built-in or custom. This allows you to manually trigger errors when specific conditions are met.

```javascript
function validateAge(age) {
  if (age < 18) {
    throw new Error("Age must be at least 18.");
  }
  return "Age is valid";
}

try {
  console.log(validateAge(16)); // This will throw an error
} catch (error) {
  console.error(error.message); // Output: Age must be at least 18.
}
```

**Explanation**:

- The `throw` keyword is used to generate a runtime error when a specific condition is met (e.g., invalid input).
- This can be used to trigger both built-in and custom errors.

#### **3. `finally`**

The `finally` block is executed after the `try` block, regardless of whether an error was thrown or not. It is typically used for cleanup actions, such as closing resources or resetting values.

```javascript
function readFile() {
  try {
    console.log("Reading file...");
    throw new Error("File not found");
  } catch (error) {
    console.error(error.message); // Output: File not found
  } finally {
    console.log("Cleaning up..."); // Always runs
  }
}

readFile();
```

**Explanation**:

- The `finally` block runs whether an error occurred or not. This ensures that certain tasks (like closing files or releasing resources) are always performed, regardless of success or failure.

#### **4. Error Propagation**

Error propagation refers to the process of letting an error "bubble up" through the call stack until it is caught by an appropriate error handler. This allows you to handle errors at a higher level in your application, such as in a centralized error handler or at the top level of an application.

```javascript
function processData() {
  try {
    throw new Error("Data processing failed!");
  } catch (error) {
    console.error("Handled in processData:", error.message);
    throw error; // Re-throw the error to propagate it further
  }
}

function main() {
  try {
    processData();
  } catch (error) {
    console.error("Handled in main:", error.message); // Output: Handled in main: Data processing failed!
  }
}

main();
```

**Explanation**:

- In the `processData` function, we throw an error that is caught locally, but we re-throw it with `throw error` to propagate the error.
- The error is then caught in the `main` function, allowing you to handle it further up the call stack.

#### **5. Asynchronous Error Handling (Promises & Async/Await)**

For asynchronous code, error handling works similarly. With promises, you use `.catch()` to handle errors, and with async/await, you use `try...catch` blocks.

**Promise-based error handling**:

```javascript
fetchData()
  .then((data) => {
    console.log(data);
  })
  .catch((error) => {
    console.error("Error fetching data:", error);
  });
```

**Async/Await-based error handling**:

```javascript
async function getData() {
  try {
    const data = await fetchData();
    console.log(data);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
}
```

**Explanation**:

- For asynchronous functions, you handle errors in the `.catch()` method or `try...catch` blocks for async/await.
- Handling errors asynchronously ensures that promises or async functions fail gracefully, without affecting the rest of the application.

---

### **Summary**

- **Error Types**: JavaScript provides several built-in error types (e.g., `Error`, `SyntaxError`, `TypeError`), and you can also create custom errors to handle specific issues in your application.
- **Handling Mechanisms**: The primary mechanisms for handling errors are `try...catch`, `throw`, and `finally`. These allow you to catch, propagate, or clean up after errors.
- **Error Propagation**: You can allow errors to propagate to higher levels in the call stack, giving you centralized control over error handling.
- **Async Error Handling**: Asynchronous errors are handled with `.catch()` for promises and `try...catch` for async/await.

Effective error handling ensures that your JavaScript applications are more robust, resilient, and maintainable, especially when working with complex or asynchronous tasks.

The **DOM (Document Object Model)** and **Browser APIs** are essential components for interacting with web pages in JavaScript. The DOM allows you to dynamically manipulate HTML and CSS, while Browser APIs provide access to various browser features like window management, navigation, and storage.

In this section, we’ll explore **DOM Manipulation** techniques and some important **Browser APIs**.

---

## **6. DOM & Browser APIs**

### **A. DOM Manipulation**

The DOM represents the structure of an HTML document as a tree of objects, where each element is an object that can be manipulated. JavaScript interacts with the DOM to read, modify, and manipulate HTML content, structure, and style on the fly.

#### **1. Selection Methods**

To manipulate DOM elements, you first need to select them using one of the following methods:

- **`getElementById()`**: Selects an element by its unique ID.

```javascript
const element = document.getElementById("myElement");
```

- **`getElementsByClassName()`**: Selects all elements with a specified class name. Returns a live HTMLCollection.

```javascript
const elements = document.getElementsByClassName("myClass");
```

- **`getElementsByTagName()`**: Selects all elements with a specified tag name (e.g., `div`, `span`). Returns a live HTMLCollection.

```javascript
const divs = document.getElementsByTagName("div");
```

- **`querySelector()`**: Selects the first element that matches a CSS selector.

```javascript
const firstDiv = document.querySelector("div");
```

- **`querySelectorAll()`**: Selects all elements that match a CSS selector. Returns a static NodeList.

```javascript
const allDivs = document.querySelectorAll("div");
```

#### **2. Traversal**

Once you have selected an element, you may want to traverse the DOM tree to access related elements. These are some common traversal methods:

- **`parentNode`**: Gets the parent element.

```javascript
const parent = element.parentNode;
```

- **`childNodes`**: Gets all child nodes of an element, including text nodes and comments.

```javascript
const children = element.childNodes;
```

- **`firstChild` / `lastChild`**: Gets the first or last child node.

```javascript
const firstChild = element.firstChild;
const lastChild = element.lastChild;
```

- **`nextSibling` / `previousSibling`**: Gets the next or previous sibling element.

```javascript
const nextSibling = element.nextSibling;
const prevSibling = element.previousSibling;
```

- **`querySelector()` (relative to current element)**: You can also use `querySelector()` or `querySelectorAll()` to find descendants or siblings of the selected element.

```javascript
const nextDiv = element.querySelector(".next-class");
```

#### **3. Modification**

Once you’ve selected elements, you can modify their content, attributes, and styles:

- **Modifying Text**: Use the `textContent` or `innerText` properties to get or set the text content of an element.

```javascript
element.textContent = "New Text Content";
```

- **Modifying HTML**: Use `innerHTML` to get or set the HTML inside an element. Be careful when setting `innerHTML` to avoid security risks (e.g., XSS attacks).

```javascript
element.innerHTML = "<p>New HTML Content</p>";
```

- **Modifying Attributes**: Use `setAttribute()` and `getAttribute()` to modify or retrieve attributes.

```javascript
element.setAttribute("src", "new-image.jpg");
const src = element.getAttribute("src");
```

- **Modifying Styles**: You can directly change an element's style using the `style` property.

```javascript
element.style.color = "red";
element.style.fontSize = "20px";
```

- **Modifying Classes**: Use `classList` to manipulate classes.

```javascript
element.classList.add("new-class"); // Add a class
element.classList.remove("old-class"); // Remove a class
element.classList.toggle("toggle-class"); // Toggle a class
```

#### **4. Events**

Events are actions that occur in the browser (such as clicks, key presses, etc.). You can add event listeners to elements and respond to those events.

##### **Types of Events**

There are many types of events in JavaScript, such as:

- **Mouse Events**: `click`, `dblclick`, `mousedown`, `mouseup`, `mousemove`
- **Keyboard Events**: `keydown`, `keypress`, `keyup`
- **Form Events**: `submit`, `change`, `input`, `focus`, `blur`
- **Window Events**: `resize`, `scroll`, `load`, `unload`

##### **Adding Event Handlers**

You can add event handlers using either the `addEventListener()` method or the `onclick` (or other event) property.

- **Using `addEventListener()`**:

```javascript
element.addEventListener("click", function () {
  alert("Element clicked!");
});
```

- **Using `onclick` Property**:

```javascript
element.onclick = function () {
  alert("Element clicked!");
};
```

**Note**: `addEventListener()` is preferred as it allows multiple listeners to be attached to the same event on the same element.

##### **Event Propagation**

Events propagate through the DOM in two phases:

1. **Capturing phase**: The event starts from the root and travels down to the target.
2. **Bubbling phase**: The event bubbles up from the target to the root.

You can control the propagation using `stopPropagation()` and `stopImmediatePropagation()`.

```javascript
element.addEventListener("click", function (event) {
  event.stopPropagation(); // Prevents the event from propagating
});
```

##### **Event Delegation**

Event delegation is the practice of attaching a single event listener to a parent element instead of individual child elements. This is especially useful for dynamically added elements.

```javascript
document.getElementById("parent").addEventListener("click", function (event) {
  if (event.target && event.target.matches("button.className")) {
    alert("Button clicked!");
  }
});
```

**Explanation**:

- The event listener is attached to the parent (`#parent`), but it listens for clicks on specific child elements (in this case, buttons with the class `className`).

---

### **B. Browser APIs**

Browser APIs provide a range of functionalities for interacting with the browser and the web environment. These include accessing window information, managing the browser's history, handling local storage, and more.

#### **1. Window Object**

The `window` object is the global object in the browser environment, representing the browser window. It contains properties and methods related to the browser environment.

- **Properties**:

```javascript
console.log(window.innerWidth); // Width of the browser window
console.log(window.innerHeight); // Height of the browser window
```

- **Methods**:

```javascript
window.alert("Hello, world!"); // Display a simple alert
window.confirm("Are you sure?"); // Display a confirmation dialog
window.prompt("What is your name?"); // Display a prompt for user input
```

#### **2. Document Object**

The `document` object represents the HTML document loaded in the browser. It provides methods for selecting and modifying elements, handling events, and accessing metadata.

- **Accessing the title**:

```javascript
console.log(document.title); // Get the title of the document
document.title = "New Title"; // Set the title of the document
```

- **Creating Elements**:

```javascript
const newDiv = document.createElement("div"); // Create a new div element
document.body.appendChild(newDiv); // Append the new div to the body
```

#### **3. Location & History**

The **`location`** and **`history`** objects allow you to interact with the URL of the current page and the browser history.

- **`location`**:

```javascript
console.log(location.href); // Get the current URL
location.href = "https://example.com"; // Redirect to a new URL
```

- **`history`**:

```javascript
history.back(); // Go back to the previous page
history.forward(); // Go forward to the next page
history.pushState({}, "", "/new-path"); // Modify the URL without reloading
```

#### **4. Storage**

The browser provides several ways to store data on the client side. The most common are **localStorage**, **sessionStorage**, and **cookies**.

- **localStorage**: Stores data persistently, even after the browser is closed.

```javascript
localStorage.setItem("username", "JohnDoe");
const username = localStorage.getItem("username");
console.log(username); // Output: JohnDoe
```

- **sessionStorage**: Stores data for the duration of the page session. The data is lost when the page is closed.

```javascript
sessionStorage.setItem("sessionKey", "sessionValue");
const sessionValue = sessionStorage.getItem("sessionKey");
console.log(sessionValue); // Output: sessionValue
```

- **Cookies**: Used for small pieces of data (usually key-value pairs) stored in the browser and sent with HTTP requests.

```javascript
document.cookie = "user=JohnDoe; path=/; expires=Thu, 1 Jan 2025 00:00:00 UTC";
```

**Explanation**:

- **Cookies** are sent to the server with each HTTP request, so they are useful

for authentication and session management.

- **localStorage** and **sessionStorage** are often used for client-side data storage. `localStorage` persists across sessions, while `sessionStorage` is temporary.

---

### **Summary**

- **DOM Manipulation**: JavaScript provides a wide range of methods for selecting, traversing, modifying, and handling events within the DOM. This allows you to create interactive, dynamic web pages.
- **Browser APIs**: The browser exposes various APIs such as the `window`, `document`, `location`, `history`, and storage mechanisms (localStorage, sessionStorage, cookies), which enable you to interact with the browser environment and manage the web page's behavior and data.

These tools are essential for modern web development and create the interactive, dynamic experiences that users expect.

### **7. Additional Concepts**

In this section, we'll cover a few important concepts and advanced topics that are crucial for any modern JavaScript developer: **Regular Expressions**, **Security**, **Performance**, and **Design Patterns**. These topics help with writing cleaner, more efficient, and more secure code.

---

## **A. Regular Expressions (RegEx)**

Regular expressions (RegEx) are patterns used to match character combinations in strings. They allow you to perform complex string manipulations, like searching, replacing, or validating input. In JavaScript, regular expressions are implemented using the `RegExp` object.

### **1. Patterns**

A regular expression pattern is a sequence of characters that define a search criteria. Some key symbols used in patterns include:

- **Literal characters**: These match themselves.
  ```javascript
  const regex = /hello/; // Matches "hello"
  ```
- **Metacharacters**: Special characters that have a particular meaning.
  - **`.`**: Matches any character except newline.
    ```javascript
    const regex = /h.llo/; // Matches "hello", "hxllo", etc.
    ```
  - **`^`**: Matches the beginning of a string.
    ```javascript
    const regex = /^hello/; // Matches "hello" at the start of the string
    ```
  - **`$`**: Matches the end of a string.
    ```javascript
    const regex = /world$/; // Matches "world" at the end of the string
    ```
  - **`[]`**: Matches any one of the characters inside the brackets.
    ```javascript
    const regex = /[aeiou]/; // Matches any vowel
    ```
  - **`\d`**: Matches any digit (equivalent to `[0-9]`).
    ```javascript
    const regex = /\d/; // Matches any single digit
    ```
  - **`\w`**: Matches any word character (letters, digits, and underscores).
    ```javascript
    const regex = /\w/; // Matches any alphanumeric character or underscore
    ```
  - **`\s`**: Matches any whitespace character.
    ```javascript
    const regex = /\s/; // Matches spaces, tabs, and line breaks
    ```

### **2. Modifiers**

Modifiers are flags that control how the regular expression matches the string. Common modifiers include:

- **`g`** (global): Matches all occurrences, not just the first.
  ```javascript
  const regex = /hello/g; // Matches all instances of "hello"
  ```
- **`i`** (ignore case): Makes the regex case-insensitive.
  ```javascript
  const regex = /hello/i; // Matches "Hello", "HELLO", etc.
  ```
- **`m`** (multiline): Treats the start and end of line (`^`, `$`) as working across multiple lines.
  ```javascript
  const regex = /^hello/m; // Matches "hello" at the start of any line
  ```

### **3. Methods**

JavaScript provides several methods for working with regular expressions:

- **`test()`**: Tests whether a string matches the pattern (returns `true` or `false`).

  ```javascript
  const regex = /hello/;
  console.log(regex.test("hello world")); // Output: true
  ```

- **`exec()`**: Executes the regex on a string and returns the result in an array (or `null` if no match).

  ```javascript
  const regex = /hello/;
  console.log(regex.exec("hello world")); // Output: ["hello"]
  ```

- **`match()`**: Returns an array of matches (or `null`).

  ```javascript
  const str = "hello world";
  console.log(str.match(/hello/)); // Output: ["hello"]
  ```

- **`replace()`**: Replaces matched parts of a string with a new substring.

  ```javascript
  const str = "hello world";
  console.log(str.replace(/hello/, "hi")); // Output: "hi world"
  ```

- **`split()`**: Splits a string into an array of substrings based on the pattern.
  ```javascript
  const str = "hello world";
  console.log(str.split(/\s/)); // Output: ["hello", "world"]
  ```

### **4. Common Uses**

Regular expressions are often used in:

- **Validation**: Checking the format of user input, such as email addresses, phone numbers, or passwords.
  ```javascript
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  console.log(emailRegex.test("test@example.com")); // true
  ```
- **Search & Replace**: Modifying strings by finding patterns and replacing them with new content.
  ```javascript
  const phoneRegex = /\d{3}-\d{3}-\d{4}/;
  const newString = "My number is 123-456-7890.".replace(
    phoneRegex,
    "XXX-XXX-XXXX"
  );
  console.log(newString); // Output: My number is XXX-XXX-XXXX.
  ```
- **Parsing Data**: Extracting data from a string, such as dates, log files, etc.

---

## **B. Security**

Security is an essential part of web development. JavaScript, being a client-side language, introduces specific challenges. Here we cover common vulnerabilities, best practices, and secure handling techniques.

### **1. Common Vulnerabilities**

- **Cross-Site Scripting (XSS)**: An attacker injects malicious scripts into webpages viewed by other users.

  - **Prevention**: Escape user input, sanitize inputs (especially in HTML, JS, and URLs), and use Content Security Policy (CSP).

- **Cross-Site Request Forgery (CSRF)**: An attacker tricks a user into making a request to a different website where the user is authenticated, often performing an unwanted action.

  - **Prevention**: Use anti-CSRF tokens and ensure that state-changing requests (e.g., POST, PUT) are protected.

- **SQL Injection**: Malicious SQL queries are inserted into input fields to manipulate or access the database.

  - **Prevention**: Use prepared statements and parameterized queries.

- **Insecure Storage**: Storing sensitive data (like passwords) in an insecure way.
  - **Prevention**: Use secure hashing algorithms (e.g., bcrypt) and avoid storing sensitive information in local storage or cookies.

### **2. Best Practices**

- **HTTPS**: Always use HTTPS to ensure that data sent between the client and server is encrypted.
- **Content Security Policy (CSP)**: Helps mitigate XSS attacks by restricting the sources from which scripts can be loaded.
- **SameSite Cookies**: Prevent CSRF by ensuring cookies are sent only for same-origin requests.
- **Sanitize Input**: Always sanitize user input to prevent malicious code injection.

### **3. Input Validation**

Validate and sanitize all user inputs (form data, URL parameters, etc.) to ensure they meet the expected format and to protect against malicious payloads. Always check on both client and server sides.

```javascript
function isValidEmail(email) {
  const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  return regex.test(email);
}
```

### **4. Authentication**

- **JWT (JSON Web Tokens)**: Used for securely transmitting information between parties, often used in stateless authentication mechanisms.
- **OAuth 2.0**: Commonly used for delegating authentication to third-party services (e.g., Google, Facebook).
- **Password Hashing**: Store passwords in a hashed form (e.g., bcrypt) to avoid exposing plain-text passwords.

---

## **C. Performance**

Web application performance is a crucial aspect of user experience. Optimizing both the **front-end** (JavaScript) and **back-end** can help deliver faster and more efficient applications.

### **1. Optimization Techniques**

- **Minimize DOM Manipulation**: Direct DOM manipulation can be costly. Try to batch updates and minimize reflows/repaints by modifying the DOM as a whole rather than piecemeal.
- **Lazy Loading**: Load only the necessary resources (images, scripts) initially, and defer others until they’re needed (e.g., images below the fold).
- **Code Splitting**: Break large JavaScript files into smaller bundles and only load what’s necessary for the current page.
- **Debouncing/Throttling**: Optimize functions that are triggered frequently (e.g., scroll events, keypress events) to reduce excessive computation.

### **2. Memory Management**

- **Garbage Collection**: JavaScript automatically cleans up unused objects through garbage collection. However, it’s important to avoid memory leaks.
  - **Prevention**: Ensure event listeners are removed, unused variables are dereferenced, and closures are optimized to avoid memory retention.
- **Avoid Global Variables**: Global variables can be overwritten or cause memory leaks. Use closures and local scopes instead.

### **3. Benchmarking**

- Use browser tools (e.g., Chrome DevTools) to monitor performance (e.g., scripting time, rendering time).
- **`console.time()` and `console.timeEnd()`**: Use these methods to measure how long a specific part of your code takes to execute.

```javascript
console.time("fetchData");
fetchData().then(() => {
  console.timeEnd("fetchData");
});
```

### **4. Profiling**

Profiling helps you find performance bottlenecks in your code. Use the **Chrome DevTools** or similar tools to analyze performance:

- **Performance tab**: Records page load performance and identifies heavy tasks.
- **Memory tab**: Helps find memory leaks or large allocations.
- **JS Profiler**: Analyzes the execution time of your functions.

---

## **D. Design Patterns**

Design patterns are reusable solutions to common problems in software design. JavaScript developers often use certain patterns to make code more modular, maintainable, and scalable.

### **1. Creational Patterns**

- **Singleton**: Ensures a class has only one instance and provides a global point of access.
- **Factory Method**: Defines an interface for creating objects, but lets subclasses decide which class to instantiate.

### **2. Structural Patterns**

- **Decorator**: Allows you to add behavior to an object dynamically, without modifying its structure.
- **Facade**: Provides a simplified interface to a complex subsystem.

### **3. Behavioral Patterns**

- **Observer**: Defines a dependency between objects so that when one object changes state, all its dependents are notified.
- **Strategy**: Allows algorithms to be selected at runtime based on a given context.

### **4. Architectural Patterns**

- **MVC (Model-View-Controller)**: Separates an application into three main components: Model (data), View (UI), and Controller (business logic).
- **MVVM (Model-View-ViewModel)**: A variation of MVC, commonly used in frameworks like Angular, where the ViewModel mediates between the View and Model.

---

### **Summary**

- **Regular Expressions**: Powerful tools for pattern matching and text manipulation.
- **Security**: Protect your applications against common vulnerabilities (XSS, CSRF, SQL injection) and follow best practices for input validation and secure authentication.
- **Performance**: Optimize your JavaScript code with techniques like lazy loading, debouncing, code splitting, and using tools for memory management and benchmarking.
- **Design Patterns**: Reusable solutions to common design problems that enhance code maintainability and scalability.

Mastering these concepts is key to building modern, secure, and efficient web applications.

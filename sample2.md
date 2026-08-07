JavaScript & TypeScript — Complete Notes

A single, de-duplicated reference covering JavaScript and TypeScript side-by-side, from fundamentals to advanced typing.

---

## Table of Contents

1. [What is JavaScript](#what-is-javascript)
2. [What is TypeScript](#what-is-typescript)
3. [JavaScript vs TypeScript](#javascript-vs-typescript)
4. [Installation & Setup](#installation--setup)
5. [Comments](#comments)
6. [Variables](#variables)
   - [Identifiers](#identifiers)
   - [var vs let vs const — Overview](#var-vs-let-vs-const--overview)
   - [`let` in Depth](#let-in-depth)
   - [`const` in Depth](#const-in-depth)
   - [Hoisting](#hoisting)
   - [Destructuring](#destructuring)
7. [Data Types](#data-types)
   - [Primitive Data Types](#primitive-data-types)
   - [Non-Primitive (Reference) Data Types](#non-primitive-reference-data-types)
8. [The `typeof` Operator](#the-typeof-operator)
9. [Arrays](#arrays)
   - [Array Methods Reference](#array-methods-reference)
   - [`map()`, `filter()`, `reduce()` Deep Dive](#map-filter-reduce-deep-dive)
     - [`map()`](#map--transforms-every-element-returns-a-new-array-of-the-same-length)
     - [`filter()`](#filter--returns-a-new-array-of-elements-matching-a-condition)
     - [`reduce()`](#reduce--reduces-an-array-to-a-single-value-number-string-object-array)
10. [TypeScript — Explicit Types & Type Inference](#typescript--explicit-types--type-inference)
    - [Explicit Typing (Type Annotations)](#1-explicit-typing-type-annotations)
    - [Type Inference](#2-type-inference)
    - [Type Safety](#type-safety)
    - [When TypeScript Can't Infer](#when-typescript-cant-infer)
11. [TypeScript — Special Types](#typescript--special-types)
    - [`any`](#any)
    - [`unknown`](#unknown)
    - [`never`](#never)
    - [`undefined` and `null`](#undefined-and-null)
    - [`void`](#void)
    - [Optional Chaining & Nullish Coalescing](#optional-chaining--nullish-coalescing)
12. [TypeScript — Tuples](#typescript--tuples)
13. [TypeScript — Object Types](#typescript--object-types)
14. [TypeScript — Enums](#typescript--enums)
15. [TypeScript — Type Aliases & Interfaces](#typescript--type-aliases--interfaces)
    - [Type Aliases (`type`)](#type-aliases-type)
    - [Interfaces](#interfaces)
    - [Type vs Interface](#type-vs-interface)
16. [TypeScript — Union Types](#typescript--union-types)
17. [Operators](#operators)
    - [Assignment Operators](#assignment-operators)
    - [Arithmetic Operators](#arithmetic-operators)
    - [Comparison Operators](#comparison-operators)
    - [Logical Operators](#logical-operators)
18. [Conditional Statements](#conditional-statements)
    - [if / else / else if](#if--else--else-if)
    - [switch Statement](#switch-statement)
    - [Ternary (Conditional) Operator](#ternary-conditional-operator)
19. [Loops & Control Flow](#loops--control-flow)
20. [Type Casting](#type-casting)
21. [TypeScript — Generics](#typescript--generics)
22. [TypeScript — Utility Types](#typescript--utility-types)

---

## What is JavaScript

- JavaScript (JS) is a high-level, interpreted programming language used to build interactive web applications.
- It runs directly in browsers and can also run on servers using Node.js.
- JavaScript is **dynamically typed** — variable types are determined at runtime.

## What is TypeScript

- **TypeScript** is a **syntactic superset of JavaScript** that adds **static typing**.
- It extends JavaScript with **type annotations** and other features, making code easier to write, maintain, and debug.
- TypeScript is **transpiled (compiled) into JavaScript** using the TypeScript compiler (`tsc`), because browsers only understand JavaScript.

## JavaScript vs TypeScript

| JavaScript               | TypeScript                       |
| ------------------------ | -------------------------------- |
| Dynamically typed        | Statically typed                 |
| Runs directly in browser | Compiles to JavaScript first     |
| Errors mostly at runtime | Errors caught during compilation |
| No type checking         | Supports type checking           |
| Easier to start          | Better for large applications    |

---

## Installation & Setup

**Install TypeScript** as a dev dependency:

```bash
npm install typescript --save-dev
```

**Create a `tsconfig.json`:**

```bash
npx tsc --init
```

Example config:

```json
{
  "include": ["typescript/*.ts"],
  "compilerOptions": {
    "rootDir": "./typescript",
    "outDir": "./build"
  }
}
```

- `include` — which files/folders TypeScript should compile.
- `compilerOptions` — compiler settings.
- `outDir` — folder where compiled JS files are generated.

**Compile a single file:**

```bash
npx tsc filename.ts
```

This produces `filename.js`. Run it with:

```bash
node filename.js
```

Once `tsconfig.json` exists, compile everything at once:

```bash
npx tsc
```

**Run TypeScript directly (no manual compile step)** using `tsx`:

```bash
npm install tsx --save-dev
npx tsx filename.ts
```

---

## Comments

**Single-line:**

```js
// single line comment
let x = 6;
```

**Multi-line:**

```js
/*
This is
Multi-line
comments
*/
```

---

## Variables

Variables are containers for storing data values.

**Modern JavaScript / TypeScript**

1. `let`
2. `const`

**Legacy (avoid)**

1. `var`
2. Automatic/undeclared globals

```js
let x = 5;
const y = 6;
```

### Identifiers

Rules for naming variables:

- Names can contain letters, digits, underscores, and `$`.
- Names must begin with a letter, `$`, or `_`.
- Names are case-sensitive (`X` ≠ `x`).
- Reserved keywords cannot be used as names.

### var vs let vs const — Overview

| Feature                        | `var`                               | `let`                          | `const`                        |
| ------------------------------ | ----------------------------------- | ------------------------------ | ------------------------------ |
| Scope                          | Function / Global                   | Block `{}`                     | Block `{}`                     |
| Reassignment                   | ✅ Yes                              | ✅ Yes                         | ❌ No                          |
| Redeclaration                  | ✅ Yes                              | ❌ No                          | ❌ No                          |
| Must initialize on declaration | ❌ No                               | ❌ No                          | ✅ Yes                         |
| Hoisting                       | Hoisted & initialized (`undefined`) | Hoisted, not initialized (TDZ) | Hoisted, not initialized (TDZ) |
| Recommended                    | ❌ No                               | ✅ Yes                         | ⭐ Default choice              |

**Declaration syntax — JavaScript vs TypeScript**

```js
// JavaScript
let carName = "Volvo";
```

```ts
// TypeScript — same syntax, plus an optional type
let carName: string = "Volvo";
```

**Undeclared variables (not recommended):**

```js
// JavaScript — allowed, but creates accidental globals
x = 5;
y = 6;
z = x + y;
```

```ts
// TypeScript — not allowed
x = 5; // ❌ Error: Cannot find name 'x'.
```

**`var` (legacy):**

```js
var x = 5;
var y = 6;
var z = x + y;
```

`var` is function-scoped, not block-scoped, which leads to unexpected behavior — this is why `let`/`const` replaced it.

---

### `let` in Depth

Introduced in ES6. Variables declared with `let`:

- ✅ Have block scope
- ✅ Must be declared before use
- ✅ Cannot be redeclared in the same scope
- ✅ Can be reassigned

**1. Block Scope**

```js
// JavaScript
{
  let x = 2;
  console.log(x); // 2
}
// console.log(x); ❌ ReferenceError
```

```ts
// TypeScript
{
  let x: number = 2;
  console.log(x);
}
// console.log(x); ❌ Error
```

**2. Function Scope** — `var`, `let`, and `const` are all inaccessible outside the function they're declared in.

```ts
function myFunction() {
  var x: number = 1;
  let y: number = 2;
  const z: number = 3;
}
// x, y, z not accessible here
```

**3. Global Scope with `var`** — `var` ignores block scope:

```js
{
  var x = 2;
}
console.log(x); // 2 — still accessible
```

**4. Reassignment vs Redeclaration**

```js
// Reassignment — allowed
let age = 25;
age = 26; // ✅

// Redeclaration — not allowed
let age = 25;
let age = 30; // ❌ Error
```

`var`, by contrast, **allows redeclaration**:

```js
var x = 10;
var x = 20; // ✅ Allowed
console.log(x); // 20
```

**5. Redeclaration inside blocks**

```js
// var ignores block scope — inner declaration overwrites outer
var x = 10;
{
  var x = 2;
}
console.log(x); // 2

// let respects block scope — inner x is a separate variable
let y = 10;
{
  let y = 2;
}
console.log(y); // 10
```

Using `let`, the same name can be reused safely across separate blocks, since each block has its own scope.

---

### `const` in Depth

Introduced in ES6. Variables declared with `const`:

- ✅ Cannot be reassigned
- ✅ Cannot be redeclared in the same scope
- ✅ Have block scope
- ✅ Must be initialized during declaration

> **Important:** `const` makes the **reference** constant, not the value itself.

**1. Cannot be reassigned**

```js
const PI = 3.14159;
// PI = 3.14; ❌ Error
```

**2. Must be initialized at declaration**

```js
const PI = 3.14159; // ✅ Correct
const PI; // ❌ Incorrect — missing initializer
PI = 3.14159;
```

**3. Constant arrays and objects are NOT fully immutable**

`const` only locks the _reference_ — contents can still change.

```js
// JavaScript
const cars = ["Saab", "Volvo", "BMW"];
cars[0] = "Toyota"; // ✅ allowed
cars.push("Audi"); // ✅ allowed
// cars = ["Toyota", "Audi"]; ❌ reassigning the array itself is not allowed

const car = { type: "Fiat" };
car.color = "Red"; // ✅ allowed — modifying properties
// car = { type: "Volvo" }; ❌ reassigning the object is not allowed
```

```ts
// TypeScript
const cars: string[] = ["Saab", "Volvo", "BMW"];
cars[0] = "Toyota";
cars.push("Audi");
```

**4. Hoisting** — like `let`, `const` is hoisted but sits in the **Temporal Dead Zone (TDZ)** until its declaration line executes:

```js
console.log(carName); // ❌ ReferenceError
const carName = "Volvo";
```

---

### Hoisting

**Hoisting** is JavaScript's behavior of moving variable/function declarations to the top of their scope before execution.

**`var` hoisting** — hoisted _and_ initialized with `undefined`:

```js
console.log(carName); // undefined
var carName = "Volvo";

// Equivalent to:
var carName;
console.log(carName);
carName = "Volvo";
```

**`let`/`const` hoisting** — hoisted but **not initialized** (Temporal Dead Zone). Accessing before declaration throws a `ReferenceError`:

```js
console.log(carName); // ❌ ReferenceError
let carName = "Volvo";
```

### Destructuring

> **Note:** Not present in the source notes, but an essential companion to `let`/`const` — added here for completeness.

**Destructuring** unpacks values from arrays or properties from objects into distinct variables in one statement.

**Array destructuring:**

```js
// JavaScript
const coords = [10, 20, 30];
const [x, y, z] = coords;

const [first, , third] = coords; // skip an element with an empty slot
const [head, ...rest] = coords; // rest pattern → head=10, rest=[20,30]
```

```ts
// TypeScript
const coords: number[] = [10, 20, 30];
const [x, y, z]: number[] = coords;
```

**Object destructuring:**

```js
// JavaScript
const user = { name: "Alice", age: 30, city: "Delhi" };
const { name, age } = user;

const { name: fullName } = user; // rename while destructuring
const { role = "Guest" } = user; // default value if property is missing
const { city, ...otherDetails } = user; // rest pattern for remaining properties
```

```ts
// TypeScript
interface User {
  name: string;
  age: number;
  city: string;
}
const user: User = { name: "Alice", age: 30, city: "Delhi" };
const { name, age }: User = user;
```

**Destructuring in function parameters** — very common for options objects:

```ts
function greet({ name, age }: { name: string; age: number }) {
  return `${name} is ${age} years old`;
}
greet({ name: "Alice", age: 30 });
```

---

## Data Types

A **data type** classifies what kind of value a variable can store, and determines the operations allowed on it, how it's stored in memory, and how the compiler/interpreter validates it.

```js
// JavaScript — allowed
let age = 25;
age = "Twenty Five"; // ✅ Allowed
```

```ts
// TypeScript — prevented at compile time
let age: number = 25;
// age = "Twenty Five"; ❌ Error
```

**Category tree:**

```
Data Types
│
├── Primitive
│   ├── Number
│   ├── String
│   ├── Boolean
│   ├── Null
│   ├── Undefined
│   ├── Symbol
│   └── BigInt
│
└── Non-Primitive
    ├── Object
    ├── Array
    ├── Function
    ├── Tuple (TypeScript only)
    ├── Enum (TypeScript only)
    ├── Any / Unknown / Never (TypeScript only)
    └── Object Literals (TypeScript)
```

### Primitive Data Types

Primitive types store a single value and are immutable. Both languages support the same set.

| Type          | JavaScript                         | TypeScript                                 |
| ------------- | ---------------------------------- | ------------------------------------------ |
| **Number**    | `let age = 25;`                    | `let age: number = 25;`                    |
| **String**    | `let name = "Alice";`              | `let name: string = "Alice";`              |
| **Boolean**   | `let isLoggedIn = true;`           | `let isLoggedIn: boolean = true;`          |
| **Symbol**    | `const id = Symbol("user");`       | `const id: symbol = Symbol("user");`       |
| **BigInt**    | `const n = 12345678901234567890n;` | `const n: bigint = 12345678901234567890n;` |
| **Null**      | `let user = null;`                 | `let user: string \| null = null;`         |
| **Undefined** | `let city; // undefined`           | `let city: string \| undefined;`           |

Template literal example: ``console.log(`Hello ${name}`);``

### Non-Primitive (Reference) Data Types

Store multiple values / complex structures, and are passed **by reference**.

| Type                                               | JavaScript                                         | TypeScript                                                         |
| -------------------------------------------------- | -------------------------------------------------- | ------------------------------------------------------------------ |
| **Object**                                         | `const person = { name: "Alice", age: 30 };`       | `const person: object = { name: "Alice", age: 30 };`               |
| **Array**                                          | `const numbers = [10, 20, 30];`                    | `const numbers: number[] = [10, 20, 30];`                          |
| **Function**                                       | `function greet(name) { return `Hello ${name}`; }` | `function greet(name: string): string { return `Hello ${name}`; }` |
| **Tuple** _(TS only)_                              | ❌ Not available                                   | `let employee: [string, number] = ["Alice", 30];`                  |
| **Enum** _(TS only)_                               | ❌ Not available                                   | `enum Color { Red, Green, Blue }`                                  |
| **Any** _(TS only)_                                | ❌ Not available                                   | `let value: any = 10;`                                             |
| **Unknown** _(TS only)_                            | ❌ Not available                                   | `let value: unknown = "Hello";`                                    |
| **Never** _(TS only)_                              | ❌ Not available                                   | `function fail(): never { throw new Error(); }`                    |
| **Object Literal / type alias / interface** _(TS)_ | ❌ Not available                                   | `type Person = { name: string; age: number };`                     |

**Type Annotation vs Type Inference (quick preview — full detail below):**

```ts
let age: number = 25; // explicit annotation
let age = 25; // inferred as number automatically
```

> **🧠 Rule of Thumb**
>
> - **Primitive types** store a **single value** (e.g., `number`, `string`, `boolean`).
> - **Non-primitive types** store **collections or structured data** (e.g., `object`, `array`, `function`).
> - **JavaScript** supports dynamic typing, allowing variables to change types at runtime.
> - **TypeScript** adds static typing, providing compile-time checks and extra types like `tuple`, `enum`, `any`, `unknown`, and `never` to build safer, more maintainable applications.

---

## The `typeof` Operator

`typeof` returns a value's data type **as a string**.

- **JavaScript:** used at runtime.
- **TypeScript:** used at runtime (same as JS) **and** at compile time to derive a type from an existing variable.

**Common results:**

| Value          | Result                                              |
| -------------- | --------------------------------------------------- |
| `"Hello"`      | `"string"`                                          |
| `100`          | `"number"`                                          |
| `true`         | `"boolean"`                                         |
| `undefined`    | `"undefined"`                                       |
| `null`         | `"object"` ⚠️ (historical JS bug)                   |
| `{}`           | `"object"`                                          |
| `[]`           | `"object"` (use `Array.isArray()` to detect arrays) |
| `function(){}` | `"function"`                                        |
| `Symbol()`     | `"symbol"`                                          |
| `10n`          | `"bigint"`                                          |

```js
typeof "John"; // "string"
typeof 25; // "number"
typeof true; // "boolean"
typeof null; // "object" — known quirk
typeof [10, 20, 30]; // "object" — arrays are objects
Array.isArray([1, 2]); // true — correct way to check for arrays
typeof (10 + 20); // "number" — works on expressions too
```

**TypeScript-only: compile-time `typeof` for type inference.** Creates a new type from an existing variable, avoiding writing the shape twice:

```ts
const person = { name: "Alice", age: 30 };
type PersonType = typeof person;
// PersonType = { name: string; age: number }

const PI = 3.14;
type PIType = typeof PI; // equivalent to: type PIType = number;
```

> **✅ Best Practices**
>
> 1. Use `typeof` to check primitive data types at runtime.
> 2. Use `Array.isArray()` instead of `typeof` for arrays.
> 3. ⚠️ Remember that `typeof null` returns `"object"` due to a historical JavaScript quirk.
> 4. In TypeScript, use `typeof` to derive types from existing variables and avoid duplicating type definitions.

> **🧠 Rule of Thumb**
>
> - **JavaScript `typeof`** → checks the type of a value **at runtime** and returns it as a string.
> - **TypeScript `typeof`** → works the same at runtime **and** can also be used at compile time to create types from existing variables, making code more maintainable and type-safe.

---

## Arrays

An **array** is an ordered collection of values. Each value is an **element**; each element has an **index** starting at **0**.

**Creating arrays:**

```js
// JavaScript
const cars = ["Audi", "Tata", "Volvo"]; // literal (recommended)
const scores = new Array(); // constructor

const bikes = [];
bikes[0] = "Activa";
bikes[1] = "Jupiter";
```

```ts
// TypeScript
const cars: string[] = ["Audi", "Tata", "Volvo"];
const cars2: Array<string> = ["Audi", "Tata", "Volvo"];

const bikes: string[] = [];
bikes.push("Activa");
bikes.push("Jupiter");
```

**Access, update, length:**

```js
console.log(cars[0]); // "Audi"
cars[0] = "Maruti"; // update
console.log(cars.length); // total elements
```

### Array Methods Reference

| Method            | Description                          | JavaScript                        | TypeScript                                         |
| ----------------- | ------------------------------------ | --------------------------------- | -------------------------------------------------- |
| `push()`          | Add element(s) to end                | `arr.push(3);`                    | `arr.push(3);`                                     |
| `pop()`           | Remove & return last element         | `arr.pop();`                      | `const last: number \| undefined = arr.pop();`     |
| `unshift()`       | Add element(s) to start              | `arr.unshift(0);`                 | `arr.unshift(0);`                                  |
| `shift()`         | Remove & return first element        | `arr.shift();`                    | `const first: number \| undefined = arr.shift();`  |
| `concat()`        | Merge arrays, returns new array      | `[1,2].concat([3,4]);`            | `const r: number[] = [1,2].concat([3,4]);`         |
| `join()`          | Array → string                       | `["a","b"].join(",");`            | `const t: string = arr.join(",");`                 |
| `slice()`         | Shallow copy of a portion            | `arr.slice(1,3);`                 | `const p: number[] = arr.slice(1,3);`              |
| `splice()`        | Add/remove/replace in place          | `arr.splice(1,2);`                | `arr.splice(1,2);`                                 |
| `indexOf()`       | First index of a value               | `arr.indexOf(20);`                | `arr.indexOf(20);`                                 |
| `lastIndexOf()`   | Last matching index                  | `arr.lastIndexOf(20);`            | `arr.lastIndexOf(20);`                             |
| `includes()`      | Boolean existence check              | `arr.includes(5);`                | `arr.includes(5);`                                 |
| `Array.isArray()` | Checks if value is an array          | `Array.isArray(arr);`             | `Array.isArray(arr);`                              |
| `forEach()`       | Run function per element (no return) | `arr.forEach(x=>console.log(x));` | `arr.forEach((x:number)=>console.log(x));`         |
| `map()`           | Transform → new array                | `arr.map(x=>x*2);`                | `const r: number[] = arr.map(x=>x*2);`             |
| `filter()`        | Elements matching condition          | `arr.filter(x=>x>2);`             | `const r: number[] = arr.filter(x=>x>2);`          |
| `reduce()`        | Reduce to a single value             | `arr.reduce((a,b)=>a+b,0);`       | `const s: number = arr.reduce((a,b)=>a+b,0);`      |
| `find()`          | First matching element               | `arr.find(x=>x>5);`               | `const v: number \| undefined = arr.find(x=>x>5);` |
| `findIndex()`     | Index of first match                 | `arr.findIndex(x=>x>5);`          | `arr.findIndex(x=>x>5);`                           |
| `sort()`          | Sort in place                        | `arr.sort((a,b)=>a-b);`           | `arr.sort((a,b)=>a-b);`                            |
| `reverse()`       | Reverse in place                     | `arr.reverse();`                  | `arr.reverse();`                                   |
| `flat()`          | Flatten nested arrays                | `[[1],[2]].flat();`               | `const r: number[] = [[1],[2]].flat();`            |
| `flatMap()`       | Map then flatten one level           | `arr.flatMap(x=>[x,x]);`          | `const r: number[] = arr.flatMap(x=>[x,x]);`       |
| `every()`         | True if all match condition          | `arr.every(x=>x>0);`              | `arr.every(x=>x>0);`                               |
| `some()`          | True if any match condition          | `arr.some(x=>x>0);`               | `arr.some(x=>x>0);`                                |
| `fill()`          | Fill with a static value             | `arr.fill(0);`                    | `arr.fill(0);`                                     |
| `copyWithin()`    | Copy part of array within itself     | `arr.copyWithin(1,0);`            | `arr.copyWithin(1,0);`                             |
| `at()`            | Element at index (supports negative) | `arr.at(-1);`                     | `const l: number \| undefined = arr.at(-1);`       |
| `entries()`       | Iterator of `[index, value]` pairs   | `arr.entries();`                  | `for (const [i,v] of arr.entries()) {}`            |
| `keys()`          | Iterator of indexes                  | `arr.keys();`                     | `for (const i of arr.keys()) {}`                   |
| `values()`        | Iterator of values                   | `arr.values();`                   | `for (const v of arr.values()) {}`                 |

**Categorized quick reference**

- **Add:** `push()` (end), `unshift()` (start), `splice()` (anywhere)
- **Remove:** `pop()` (end), `shift()` (start), `splice()` (anywhere)
- **Search:** `indexOf()`, `lastIndexOf()`, `includes()`, `find()`, `findIndex()`
- **Transform:** `map()`, `filter()`, `reduce()`, `flat()`, `flatMap()`
- **Iterate:** `forEach()`, `map()`, `entries()`, `keys()`, `values()`
- **Check condition:** `every()`, `some()`
- **Utility:** `join()`, `concat()`, `slice()`, `sort()`, `reverse()`, `fill()`, `copyWithin()`, `Array.isArray()`, `at()`

**Worked example (JavaScript):**

```js
let seas = ["Black Sea", "Caribbean Sea"];
seas.push("Red Sea"); // ['Black Sea','Caribbean Sea','Red Sea']
seas.unshift("Baltic Sea"); // ['Baltic Sea', ...]

let lastElement = seas.pop(); // 'Red Sea'
let firstElement = seas.shift(); // 'Baltic Sea'

let fruits = ["apple", "banana", "cherry"];
let moreFruits = ["mango", "pineapple"];
let allFruits = fruits.concat(moreFruits);
let joined = fruits.join(", "); // "apple, banana, cherry"
let sliced = fruits.slice(1, 3); // ["banana", "cherry"]

fruits.forEach((fruit) => console.log(fruit));
let upperFruits = fruits.map((f) => f.toUpperCase());
let filtered = fruits.filter((f) => f.length > 5);

let numbers = [1, 2, 3, 4];
let sum = numbers.reduce((acc, curr) => acc + curr, 0); // 10

let found = fruits.find((f) => f.startsWith("c")); // "cherry"
let indexFirst = fruits.findIndex((f) => f.startsWith("c")); // 2

let nums = [4, 2, 9, 1];
nums.sort((a, b) => a - b); // [1,2,4,9]
nums.reverse(); // [9,4,2,1]

let nested = [1, [2, [3]]];
console.log(nested.flat(1)); // [1, 2, [3]]
console.log(nested.flat(2)); // [1, 2, 3]

let allAboveZero = nums.every((n) => n > 0);
let hasNegative = nums.some((n) => n < 0);
```

**Same example (TypeScript) — identical logic, typed:**

```ts
let seas: string[] = ["Black Sea", "Caribbean Sea"];
seas.push("Red Sea");
seas.unshift("Baltic Sea");

let lastElement: string | undefined = seas.pop();
let firstElement: string | undefined = seas.shift();

let fruits: string[] = ["apple", "banana", "cherry"];
let moreFruits: string[] = ["mango", "pineapple"];
let allFruits: string[] = fruits.concat(moreFruits);
let joined: string = fruits.join(", ");
let sliced: string[] = fruits.slice(1, 3);

fruits.forEach((fruit: string) => console.log(fruit));
let upperFruits: string[] = fruits.map((f: string) => f.toUpperCase());
let filtered: string[] = fruits.filter((f: string) => f.length > 5);

let numbers: number[] = [1, 2, 3, 4];
let sum: number = numbers.reduce((acc: number, n: number) => acc + n, 0);

let found: string | undefined = fruits.find((f: string) => f.startsWith("c"));
let indexFirst: number = fruits.findIndex((f: string) => f.startsWith("c"));

let nums: number[] = [4, 2, 9, 1];
nums.sort((a: number, b: number) => a - b);
nums.reverse();

let nested: (number | number[])[] = [1, [2, [3]]];
let allAboveZero: boolean = nums.every((n: number) => n > 0);
let hasNegative: boolean = nums.some((n: number) => n < 0);
```

---

### `map()`, `filter()`, `reduce()` Deep Dive

The foundation of functional programming in both languages. TypeScript versions work identically — the difference is type annotations and compile-time checking.

#### `map()` — transforms every element, returns a new array of the same length

**Syntax:**

```js
// JavaScript
array.map((element, index, array) => {
  // return new value
});
```

```ts
// TypeScript
array.map((element: Type, index: number, array: Type[]) => {
  // return new value
});
```

**Parameters:**

| Parameter            | Description                  |
| -------------------- | ---------------------------- |
| `element`            | Current element              |
| `index` _(optional)_ | Index of the current element |
| `array` _(optional)_ | The original array           |

**Examples:**

```js
// JavaScript
const nums = [1, 2, 3];
const doubled = nums.map((num) => num * 2); // [2,4,6]

const colors = ["red", "green", "blue"];
const colorInfo = colors.map(
  (color, index, array) => `Color ${index + 1}/${array.length}: ${color}`,
);

const products = [
  { name: "Phone", price: 699 },
  { name: "Tablet", price: 899 },
];
const formatted = products.map((p, i) => ({
  label: `${p.name} - $${p.price}`,
  index: i,
}));
```

```ts
// TypeScript
const nums: number[] = [1, 2, 3];
const doubled: number[] = nums.map((num: number) => num * 2);

type Product = { name: string; price: number };
const products: Product[] = [
  { name: "Phone", price: 699 },
  { name: "Tablet", price: 899 },
];
const formatted = products.map((p: Product, i: number) => ({
  label: `${p.name} - $${p.price}`,
  index: i,
}));
```

TypeScript catches misuse at compile time — e.g. calling `num.toUpperCase()` on a `number[]` fails to compile instead of failing at runtime.

#### `filter()` — returns a new array of elements matching a condition

**Syntax:**

```js
// JavaScript
array.filter((element, index, array) => {
  return true; // condition
});
```

```ts
// TypeScript
array.filter((element: Type, index: number, array: Type[]) => {
  return true;
});
```

**Parameters:**

| Parameter | Description          |
| --------- | -------------------- |
| `element` | Current item         |
| `index`   | Position of the item |
| `array`   | The original array   |

**Examples:**

```js
// JavaScript
const nums = [1, 2, 3, 4, 5];
const evens = nums.filter((num) => num % 2 === 0);

const users = [
  { name: "Alice", active: true },
  { name: "Bob", active: false },
];
const activeUsers = users.filter((user) => user.active);

const scores = [60, 80, 90, 70];
const aboveAverage = scores.filter((score, _, arr) => {
  const avg = arr.reduce((sum, s) => sum + s, 0) / arr.length;
  return score > avg;
});
```

```ts
// TypeScript
const nums: number[] = [1, 2, 3, 4, 5];
const evens: number[] = nums.filter((num: number) => num % 2 === 0);

type User = { name: string; active: boolean };
const users: User[] = [
  { name: "Alice", active: true },
  { name: "Bob", active: false },
];
const activeUsers = users.filter((user: User) => user.active);
```

#### `reduce()` — reduces an array to a single value (number, string, object, array...)

**Syntax:**

```js
// JavaScript
array.reduce((accumulator, currentValue, index, array) => {
  return accumulator;
}, initialValue);
```

```ts
// TypeScript
array.reduce(
  (
    accumulator: ReturnType,
    currentValue: Type,
    index: number,
    array: Type[],
  ) => {
    return accumulator;
  },
  initialValue,
);
```

**Parameters:**

| Parameter      | Description                                  |
| -------------- | -------------------------------------------- |
| `accumulator`  | Stores the previous result                   |
| `currentValue` | The current element                          |
| `index`        | Current index                                |
| `array`        | The original array                           |
| `initialValue` | Starting value passed as the second argument |

**Examples:**

```js
// JavaScript
const numbers = [1, 2, 3, 4];
const sum = numbers.reduce((acc, num) => acc + num, 0);

const words = ["apple", "banana", "apple", "cherry"];
const count = words.reduce((acc, word) => {
  acc[word] = (acc[word] || 0) + 1;
  return acc;
}, {});

const nested = [[1, 2], [3, 4], [5]];
const flat = nested.reduce((acc, val) => acc.concat(val), []);
```

```ts
// TypeScript
const numbers: number[] = [1, 2, 3, 4];
const sum: number = numbers.reduce((acc: number, n: number) => acc + n, 0);

const words: string[] = ["apple", "banana", "apple", "cherry"];
const count = words.reduce<Record<string, number>>((acc, word) => {
  acc[word] = (acc[word] || 0) + 1;
  return acc;
}, {});

const nested: number[][] = [[1, 2], [3, 4], [5]];
const flat: number[] = nested.reduce((acc, val) => acc.concat(val), []);
```

**Chaining `filter → map → reduce`:**

```ts
type ChainUser = { name: string; email: string; active: boolean };

const result = users
  .filter((user: ChainUser) => user.active)
  .map((user, index, arr) => `${index + 1}/${arr.length}: ${user.email}`)
  .reduce(
    (acc, email) => {
      acc.count++;
      acc.emails.push(email);
      return acc;
    },
    { count: 0, emails: [] as string[] },
  );
```

> **🎯 Key Takeaways**
>
> - `map()` → transforms each element and returns a new array.
> - `filter()` → returns elements that satisfy a condition.
> - `reduce()` → reduces an array into a single value (number, string, object, array, etc.).
> - JavaScript and TypeScript use the **same array methods**.
> - TypeScript adds static typing, better IntelliSense, and compile-time error checking without changing how these methods work.

---

## TypeScript — Explicit Types & Type Inference

TypeScript checks types **before code runs** (static typing), via two mechanisms:

### 1. Explicit Typing (Type Annotations)

You manually specify the type.

```ts
let greeting: string = "Hello TypeScript";
let age: number = 25;
let isLoggedIn: boolean = true;
let marks: number[] = [90, 85, 95];

function greet(name: string): string {
  return `Hello ${name}`;
}
greet("Alice"); // ✅
greet(10); // ❌ Error
```

Use explicit types for: function parameters, function return types, object definitions, and variables whose type isn't obvious.

### 2. Type Inference

TypeScript automatically determines the type from the assigned value.

```ts
let name = "Alice"; // inferred: string
let score = 100; // inferred: number
let flags = [true, false, true]; // inferred: boolean[]

function add(a: number, b: number) {
  return a + b; // return type inferred: number
}

const user = { name: "Alice", age: 30, isAdmin: true };
// inferred: { name: string; age: number; isAdmin: boolean }
console.log(user.email); // ❌ Error: Property 'email' does not exist.
```

Use inference for simple, immediately-initialized variables where the type is obvious.

### Type Safety

```ts
let username: string = "Alice";
username = 10; // ❌ Type 'number' is not assignable to type 'string'

let score = 100;
score = "High"; // ❌ Type 'string' is not assignable to type 'number'
```

**JS vs TS on the same bug:**

```js
// JavaScript — silently does string concatenation
function add(a, b) {
  return a + b;
}
console.log(add("5", 3)); // "53"
```

```ts
// TypeScript — caught at compile time
function add(a: number, b: number): number {
  return a + b;
}
console.log(add("5", 3)); // ❌ Compile-time error
```

### When TypeScript Can't Infer

Falls back to `any`:

```ts
const data = JSON.parse('{"name":"Alice","age":30}'); // type: any

let value; // type: any
value = "Hello";
value = 100;
```

**Why avoid `any`?** Disables type checking, removes IntelliSense, hides bugs until runtime.

**Better alternatives:**

```ts
let age: number = 25;                        // explicit annotation
interface User { name: string; age: number } // interfaces for objects
if (typeof value === "string") { ... }        // type guards
```

Enable `noImplicitAny` in `tsconfig.json` to prevent accidental `any`:

```json
{ "compilerOptions": { "noImplicitAny": true } }
```

**Quick comparison**

| Explicit Typing                   | Type Inference              |
| --------------------------------- | --------------------------- |
| Type manually specified           | Type automatically detected |
| More readable/maintainable        | Less code to write          |
| Best for functions, objects, APIs | Best for simple variables   |
| `let age: number = 25;`           | `let age = 25;`             |

> **✅ Best Practices**
>
> 1. Prefer **type inference** when the type is obvious.
> 2. Use **explicit types** for function parameters, return types, and complex objects.
> 3. Avoid using **`any`** unless absolutely necessary.
> 4. Prefer **`unknown`** over `any` for safer code.
> 5. Use interfaces or type aliases to define object structures.
> 6. Use **`const`** whenever possible.
> 7. Use **`enum`** when working with a fixed set of named values.
> 8. Use **`tuple`** when the order and type of elements are fixed.

> **🎯 Key Takeaways**
>
> - **Explicit Typing** → you define the type yourself.
> - **Type Inference** → TypeScript automatically detects the type.
> - TypeScript provides compile-time type checking, preventing common runtime bugs from incorrect data types.
> - Use explicit types for functions and complex objects; use inference when the type is obvious.
> - Avoid `any` to maintain strong type safety, and enable `noImplicitAny` for extra protection.

---

## TypeScript — Special Types

| Type        | Purpose                        |
| ----------- | ------------------------------ |
| `any`       | Disables type checking         |
| `unknown`   | Safer alternative to `any`     |
| `never`     | Values that never occur        |
| `undefined` | Declared but not assigned      |
| `null`      | Intentional absence of a value |

### `any`

Skips type checking entirely.

```ts
let user = true;
user = "John"; // ❌ Error without `any`

let user2: any = true;
user2 = "John"; // ✅ Allowed
user2 = 100; // ✅ Allowed
Math.round(user2); // ✅ No compile-time error
```

Use only for migrating JS projects, dynamic data, or temporary workarounds — otherwise avoid it (loses type safety, IntelliSense, and error detection).

### `unknown`

The type-safe version of `any` — can hold anything, but you **must narrow the type** before using it.

```ts
let data: unknown = "Hello";
console.log(data.toUpperCase()); // ❌ Error: Object is of type 'unknown'

if (typeof data === "string") {
  console.log(data.toUpperCase()); // ✅ narrowed to string
}

function processValue(value: unknown) {
  if (typeof value === "string") console.log(value.toUpperCase());
  else if (Array.isArray(value)) console.log(value.length);
}
```

| `any`                  | `unknown`              |
| ---------------------- | ---------------------- |
| No type checking       | Requires type checking |
| Unsafe                 | Safe                   |
| Direct property access | Must narrow type first |
| Not recommended        | Recommended            |

### `never`

Represents values that **never occur** — functions that always throw, infinite loops, or exhaustive `switch` checks.

```ts
function throwError(message: string): never {
  throw new Error(message);
}

function infiniteLoop(): never {
  while (true) {
    console.log("Running...");
  }
}

type Status = "success" | "error";
function handleStatus(status: Status) {
  switch (status) {
    case "success":
      return "Done";
    case "error":
      return "Failed";
    default:
      const exhaustiveCheck: never = status; // errors if a new Status is added later
      return exhaustiveCheck;
  }
}
```

### `undefined` and `null`

```ts
let value: undefined = undefined; // declared, never assigned
let user: null = null; // intentionally empty
```

| `undefined`                  | `null`                           |
| ---------------------------- | -------------------------------- |
| Declared but not initialized | Value intentionally set to empty |
| Automatically assigned       | Manually assigned                |
| Represents missing value     | Represents no value              |

**Optional parameters/properties** automatically become `T | undefined`:

```ts
function greet(name?: string) {
  return `Hello ${name ?? "Guest"}`;
}

interface User {
  name: string;
  age?: number; // same as: age: number | undefined
}
```

**`strictNullChecks`** enforces explicit handling:

```json
{ "compilerOptions": { "strictNullChecks": true } }
```

```ts
let name: string = null; // ❌ Error with strictNullChecks
let name2: string | null = null; // ✅ must opt in explicitly
```

### `void`

> **Note:** Not present in the source notes, but an important companion to `never` — added here for completeness.

`void` represents the **absence of a return value** — used almost exclusively as a function's return type when it doesn't return anything meaningful.

```ts
function logMessage(message: string): void {
  console.log(message);
  // no return statement (or `return;` with no value)
}
```

**`void` vs `never`:**

| `void`                                            | `never`                                                    |
| ------------------------------------------------- | ---------------------------------------------------------- |
| Function returns, just without a meaningful value | Function never returns at all (throws or loops forever)    |
| Common for callbacks, event handlers              | Used for exhaustive checks and functions that always throw |
| `let x: void = undefined;` is technically valid   | `let x: never = ...` accepts nothing                       |

### Optional Chaining & Nullish Coalescing

```ts
// Nullish coalescing (??) — default only when null/undefined
const username = input ?? "Guest";

// Optional chaining (?.) — safely access nested properties
const street = user?.address?.street; // undefined instead of throwing
```

---

## TypeScript — Tuples

A **Tuple** is an array with a **fixed length**, **fixed order**, and a **predefined type per position**. JavaScript has no tuple type.

```ts
let employee: [number, string, boolean];
employee = [101, "John", true]; // ✅

employee = [true, "John", 101]; // ❌ Error — wrong order/types
```

**Tuple vs Array:**

```ts
let numbers: number[] = [10, 20, 30]; // every element must be a number
let person: [string, number] = ["Alice", 25]; // each position has its own type
```

**Readonly tuples:**

```ts
const person: readonly [string, number] = ["Alice", 25];
person.push("Developer"); // ❌ Error — cannot modify a readonly tuple
```

**Named tuples** (improves readability):

```ts
const point: [x: number, y: number] = [50, 80];
```

**Destructuring:**

```ts
const point: [number, number] = [100, 200];
const [x, y] = point;
```

**React example** — `useState()` returns a tuple:

```ts
const [count, setCount] = useState(0);
```

Use tuples when order matters, the number of values is fixed, and each position has a different type — e.g. coordinates, DB records, API responses, React Hooks.

---

## TypeScript — Object Types

Define the type of every property in an object.

```ts
const car: { brand: string; model: string; year: number } = {
  brand: "Toyota",
  model: "Corolla",
  year: 2024,
};

car.brand = "Ford"; // ✅
car.brand = 100; // ❌ Error: Type 'number' is not assignable to type 'string'
```

**Optional properties** (`?`):

```ts
const car2: { brand: string; mileage?: number } = { brand: "Toyota" };
car2.mileage = 5000; // ✅ valid because mileage is optional
```

**Index signatures** — for dynamic property names:

```ts
const marks: { [student: string]: number } = {};
marks.John = 90;
marks.Alice = 95;
marks.Bob = "Ninety"; // ❌ Error — only numbers allowed
```

Common use cases: dictionaries, configuration objects, dynamic API responses, student marks, product prices.

---

## TypeScript — Enums

A group of related named constants. JavaScript has no built-in enum.

**Numeric enum (default: starts at 0, increments by 1):**

```ts
enum Direction {
  North,
  East,
  South,
  West,
}
// North=0, East=1, South=2, West=3
console.log(Direction.North); // 0
```

**Numeric enum (custom start):**

```ts
enum Direction2 {
  North = 1,
  East,
  South,
  West,
}
// North=1, East=2, South=3, West=4
```

**Fully initialized enum:**

```ts
enum StatusCode {
  Success = 200,
  Created = 201,
  BadRequest = 400,
  NotFound = 404,
}
console.log(StatusCode.Success); // 200
```

**String enum** (more readable/debuggable):

```ts
enum Direction3 {
  North = "North",
  East = "East",
  South = "South",
  West = "West",
}
console.log(Direction3.North); // "North"

let dir = Direction3.North;
dir = "North"; // ❌ Error — cannot assign a raw string to an enum variable
```

**`const enum`** _(not in the source notes — added for completeness)_: a variant that's fully removed at compile time and inlined wherever it's used, producing smaller, faster JavaScript output:

```ts
const enum Direction4 {
  North,
  East,
  South,
  West,
}
let dir2 = Direction4.North; // compiles directly to: let dir2 = 0;
```

Use `const enum` when you don't need to iterate over enum values at runtime — it trades that flexibility for better performance.

Use enums for fixed value sets: user roles, order status, HTTP status codes, days of week, payment status, themes, directions.

---

## TypeScript — Type Aliases & Interfaces

Two ways to create reusable custom types, improving readability, maintainability, and reuse.

### Type Aliases (`type`)

Creates a custom name for any type — primitives, objects, arrays, functions, unions, intersections.

```ts
type UserName = string; // primitive alias
type Numbers = number[]; // array alias
type Add = (a: number, b: number) => number; // function alias

type Car = { brand: string; model: string; year: number }; // object alias
const car: Car = { brand: "Toyota", model: "Corolla", year: 2024 };

// Union
type Status = "success" | "error";
let response: Status = "success";
response = "loading"; // ❌ Error

// Intersection — combines all properties from each type
type Animal = { name: string };
type Bear = Animal & { honey: boolean };
const bear: Bear = { name: "Winnie", honey: true };
```

### Interfaces

Defines the **structure (shape) of an object** specifically.

```ts
interface Rectangle {
  height: number;
  width: number;
}
const rectangle: Rectangle = { height: 20, width: 10 };
```

**Extending interfaces:**

```ts
interface ColoredRectangle extends Rectangle {
  color: string;
}
const box: ColoredRectangle = { height: 100, width: 50, color: "Red" };
```

**Declaration merging** — a unique interface feature; two interfaces with the same name automatically combine:

```ts
interface Animal2 {
  name: string;
}
interface Animal2 {
  age: number;
}
const dog: Animal2 = { name: "Rocky", age: 5 }; // merged automatically
```

**Extending a type alias** (via intersection, since `type` has no `extends`):

```ts
type Rectangle2 = { height: number; width: number };
type ColoredRectangle2 = Rectangle2 & { color: string };
```

### Type vs Interface

| Feature                  | `type`       | `interface`        |
| ------------------------ | ------------ | ------------------ |
| Primitive types          | ✅           | ❌                 |
| Objects                  | ✅           | ✅                 |
| Arrays                   | ✅           | ❌                 |
| Functions                | ✅           | ❌                 |
| Union types (`\|`)       | ✅           | ❌                 |
| Intersection types (`&`) | ✅           | ❌                 |
| Declaration merging      | ❌           | ✅                 |
| Extend other types       | ✅ (via `&`) | ✅ (via `extends`) |
| Implemented by classes   | ✅           | ✅                 |

> **✅ Best Practices**
>
> - Use **`interface`** for object definitions and public APIs.
> - Use **`type`** for primitives, unions, intersections, arrays, and function types.
> - Keep types small and reusable.
> - Use meaningful names for custom types.
> - Prefer composition (combining types) over deeply nested inheritance.

**Common pitfalls:**

```ts
// ❌ interface cannot represent a union
// interface Status = "success" | "error";

// ✅ Correct — use type instead
type Status = "success" | "error";
```

- ❌ Forgetting to update types when object properties change.
- ❌ Creating overly complex nested types that reduce readability.

**Real-world examples:**

```ts
interface User {
  id: number;
  name: string;
  email: string;
} // API response
type Role = "Admin" | "User" | "Guest"; // fixed roles
type PaymentStatus = "Pending" | "Success" | "Failed"; // status union
interface Product {
  id: number;
  name: string;
  price: number;
} // model
```

**Quick comparison**

| Feature                      | Type Alias                    | Interface               |
| ---------------------------- | ----------------------------- | ----------------------- |
| Purpose                      | Create reusable types         | Define object structure |
| Supports objects             | ✅                            | ✅                      |
| Supports primitives          | ✅                            | ❌                      |
| Supports arrays              | ✅                            | ❌                      |
| Supports functions           | ✅                            | ❌                      |
| Supports unions              | ✅                            | ❌                      |
| Supports intersections       | ✅                            | ❌                      |
| Supports declaration merging | ❌                            | ✅                      |
| Best for                     | Primitives, unions, functions | Objects, APIs, classes  |

> **🎯 Key Takeaways**
>
> - **Type Aliases (`type`)** create reusable names for any type, including primitives, objects, arrays, functions, unions, and intersections.
> - **Interfaces (`interface`)** define the structure of objects and are ideal for APIs, models, and classes.
> - Use **Union Types (`|`)** when a value can be one of several types.
> - Use **Intersection Types (`&`)** to combine multiple types into one.
> - Interfaces support **declaration merging**; type aliases do not.
> - Prefer **`interface`** for object shapes and **`type`** for everything else.

---

## TypeScript — Union Types

A **Union Type (`|`)** lets a value be **one of several types**. JavaScript has no equivalent compile-time restriction.

```ts
let id: string | number;
id = 101; // ✅
id = "EMP101"; // ✅
id = true; // ❌ Error
```

**In function parameters:**

```ts
function printStatusCode(code: string | number): void {
  console.log(`Status Code: ${code}`);
}
printStatusCode(404); // ✅
printStatusCode("404"); // ✅
```

Without unions you'd need separate functions per type — unions keep code DRY.

**Only shared operations are allowed** unless you narrow the type:

```ts
function printStatusCode2(code: string | number) {
  console.log(code.toUpperCase()); // ❌ Error — number has no toUpperCase()
}
```

**Type narrowing:**

```ts
function printStatusCode3(code: string | number) {
  if (typeof code === "string") {
    console.log(code.toUpperCase()); // narrowed to string
  } else {
    console.log(code); // narrowed to number
  }
}
```

**With arrays / objects:**

```ts
let values: (string | number)[] = ["John", 25, "Developer", 50000];

type Employee = { id: string | number };
const emp1: Employee = { id: 101 };
const emp2: Employee = { id: "EMP101" };
```

**Union of literal types** (very common pattern):

```ts
type Status = "Pending" | "Success" | "Failed";
let payment: Status = "Success";
payment = "Completed"; // ❌ Error — only the 3 listed values allowed
```

**Union vs `any`:** unions preserve type safety (`value = true` still errors on `string | number`), while `any` disables checking entirely.

**Common use cases:** API responses, status values, user roles, IDs that can be `string | number`, optional values (`string | null`).

**Discriminated unions** _(not in the source notes — added because it's one of the most useful real-world union patterns)_: a union of object types that share a common literal property (the "discriminant"), letting TypeScript narrow automatically inside a `switch`:

```ts
type Circle = { kind: "circle"; radius: number };
type Square = { kind: "square"; side: number };
type Shape = Circle | Square;

function getArea(shape: Shape): number {
  switch (shape.kind) {
    case "circle":
      return Math.PI * shape.radius ** 2; // shape narrowed to Circle
    case "square":
      return shape.side ** 2; // shape narrowed to Square
  }
}
```

TypeScript uses the `kind` property to know exactly which shape of object you're working with in each branch — no manual `typeof`/`instanceof` checks needed.

> **✅ Best Practices**
>
> - Use union types when a value can legitimately have multiple types.
> - Use **type narrowing** (`typeof`, `instanceof`, etc.) before calling type-specific methods.
> - Prefer unions over `any` to maintain type safety.
> - Use literal unions to restrict values to a predefined set.

**Quick comparison**

| Feature            | Description                              |
| ------------------ | ---------------------------------------- |
| `\|` (Pipe)        | Represents **OR** between types          |
| `string \| number` | Value can be either a string or a number |
| Type Narrowing     | Checks the actual type before using it   |
| Literal Union      | Restricts values to specific literals    |

> **🎯 Key Takeaways**
>
> - **Union Types (`\|`)** allow a value to have **one of multiple types**.
> - TypeScript only allows operations valid for **all possible types** in the union.
> - Use type narrowing before calling type-specific methods.
> - Union types are safer than `any` because they preserve compile-time type checking.
> - Literal unions restrict values to a fixed set of valid options.

---

## Operators

JavaScript and TypeScript use the **same operators**; TypeScript adds compile-time type checking on top.

> **🎯 Key Takeaways**
>
> - JavaScript and TypeScript use **the same operators** — TypeScript introduces no new ones.
> - The main difference is that **TypeScript validates operand types before execution**.
> - Use **`===`** instead of `==` whenever possible.
> - TypeScript helps prevent invalid assignments and operator misuse through static type checking.

### Assignment Operators

| Operator | Purpose           | JavaScript        | TypeScript                     |
| -------- | ----------------- | ----------------- | ------------------------------ |
| `=`      | Assign value      | `let x = 10;`     | `let x: number = 10;`          |
| `+=`     | Add & assign      | `x += 5; // 15`   | `let x: number = 10; x += 5;`  |
| `-=`     | Subtract & assign | `x -= 5; // 5`    | `let x: number = 10; x -= 5;`  |
| `*=`     | Multiply & assign | `x *= 5; // 50`   | `let x: number = 10; x *= 5;`  |
| `/=`     | Divide & assign   | `x /= 5; // 2`    | `let x: number = 10; x /= 5;`  |
| `%=`     | Modulus & assign  | `x %= 3; // 1`    | `let x: number = 10; x %= 3;`  |
| `**=`    | Power & assign    | `x **= 2; // 100` | `let x: number = 10; x **= 2;` |

**Logical assignment operators (ES2020):**

| Operator | Meaning                  | Example                            |
| -------- | ------------------------ | ---------------------------------- |
| `&&=`    | Assign if truthy         | `let x = true; x &&= 10; // 10`    |
| `\|\|=`  | Assign if falsy          | `let x = false; x \|\|= 10; // 10` |
| `??=`    | Assign if null/undefined | `let x = null; x ??= 10; // 10`    |

**Falsy values:** `false`, `0`, `-0`, `0n`, `""`, `null`, `undefined`, `NaN`

**Commonly-confused truthy values:** `"0"`, `"false"`, `[]`, `{}` — all truthy!

**Spread operator (`...`):**

```js
const numbers = [10, 20, 30];
console.log(...numbers); // 10 20 30
```

```ts
const numbers: number[] = [10, 20, 30];
console.log(...numbers);
```

> **📌 Key Points**
>
> | Topic                                      | Summary                                                                  |
> | ------------------------------------------ | ------------------------------------------------------------------------ |
> | Assignment (`=`)                           | Assigns a value to a variable.                                           |
> | Compound Assignment (`+=`, `-=`, etc.)     | Performs an operation and assigns the result.                            |
> | Logical Assignment (`&&=`, `\|\|=`, `??=`) | Conditionally assigns based on truthiness, falsiness, or nullish values. |
> | Spread (`...`)                             | Expands arrays, strings, or objects into individual elements.            |
> | TypeScript Advantage                       | Same operators as JavaScript, with compile-time type safety.             |

### Arithmetic Operators

| Operator | Description         | Example  |
| -------- | ------------------- | -------- |
| `+`      | Addition            | `a + b`  |
| `-`      | Subtraction         | `a - b`  |
| `*`      | Multiplication      | `a * b`  |
| `/`      | Division            | `a / b`  |
| `%`      | Modulus (remainder) | `a % b`  |
| `**`     | Exponentiation      | `a ** b` |
| `++`     | Increment           | `a++`    |
| `--`     | Decrement           | `a--`    |

```js
// JavaScript
let a = 10,
  b = 3;
console.log(a + b, a * b, a % b, a ** 2); // 13 30 1 100
```

```ts
// TypeScript
let a: number = 10;
let b: number = 3;
console.log(a + b, a * b, a % b, a ** 2);
// a = "10"; ❌ Error
```

**Operator precedence** follows BODMAS/PEMDAS: `()` → `**` → `*, /, %` → `+, -`, evaluated left-to-right at equal precedence.

```js
console.log(100 + 50 * 3); // 250 — multiplication first
console.log((100 + 50) * 3); // 450 — parentheses first
```

> **✅ Best Practices**
>
> - Use parentheses `()` to make expressions easier to read.
> - Use meaningful variable names.
> - Prefer `**` over `Math.pow()` for readability.
> - In TypeScript, use the `number` type for arithmetic operations.

> **🎯 Key Takeaways**
>
> - Arithmetic operators perform mathematical calculations; operands are the values, operators perform the operation.
> - `+`, `-`, `*`, `/`, `%`, `**`, `++`, and `--` are the most common arithmetic operators.
> - `%` returns the remainder after division; `**` performs exponentiation.
> - JavaScript follows operator precedence (**BODMAS/PEMDAS**).
> - JavaScript and TypeScript use the same arithmetic operators, but TypeScript adds compile-time type safety.

### Comparison Operators

| Operator          | Purpose                     | JavaScript            | TypeScript                    |
| ----------------- | --------------------------- | --------------------- | ----------------------------- |
| `==`              | Equal (value only)          | `5 == "5"` → `true`   | `let x: number = 5; x == 5;`  |
| `===`             | Strict equal (value & type) | `5 === "5"` → `false` | `let x: number = 5; x === 5;` |
| `!=`              | Not equal                   | `5 != 8` → `true`     | `let x: number = 5; x != 8;`  |
| `!==`             | Strict not equal            | `5 !== "5"` → `true`  | `let x: number = 5; x !== 8;` |
| `>` `<` `>=` `<=` | Relational comparisons      | `5 > 3` → `true`      | `let x: number = 5; x > 3;`   |

**`==` coerces types; `===` does not — always prefer `===`/`!==`.**

```js
console.log(5 == "5"); // true  — coercion
console.log(5 === "5"); // false — different types
```

**Mixed-type comparison gotchas:**

| Expression   | Result  | Reason              |
| ------------ | ------- | ------------------- |
| `2 < "12"`   | `true`  | `"12"` → `12`       |
| `2 == "2"`   | `true`  | Type conversion     |
| `2 === "2"`  | `false` | Different types     |
| `"2" > "12"` | `true`  | Compared as strings |
| `2 < "John"` | `false` | `"John"` → `NaN`    |

**Strings compare lexicographically:** `"A" < "B"` → `true`.

> **✅ Best Practices**
>
> | Recommendation                   | Reason                           |
> | -------------------------------- | -------------------------------- |
> | Use `===` and `!==`              | Avoids automatic type conversion |
> | Avoid `==`                       | Can produce unexpected results   |
> | Convert values before comparison | Prevents incorrect comparisons   |
> | Use TypeScript types             | Catches errors at compile time   |

> **📌 Key Points**
>
> | Topic                 | Summary                                                                     |
> | --------------------- | --------------------------------------------------------------------------- |
> | `==`                  | Compares only values (allows type coercion).                                |
> | `===`                 | Compares both value and type (recommended).                                 |
> | `!=` / `!==`          | Not-equal operators (`!==` preferred).                                      |
> | String Comparison     | Strings are compared alphabetically.                                        |
> | Mixed-Type Comparison | JavaScript performs automatic type conversion.                              |
> | TypeScript Advantage  | Same comparison operators, but catches type-related errors at compile time. |

### Logical Operators

| Operator | Purpose                 | JavaScript                  | TypeScript                                          |
| -------- | ----------------------- | --------------------------- | --------------------------------------------------- |
| `&&`     | AND — both must be true | `5 > 2 && 10 > 5 // true`   | `age > 18 && age < 60;`                             |
| `\|\|`   | OR — at least one true  | `5 < 2 \|\| 10 > 5 // true` | `age < 18 \|\| age > 60;`                           |
| `!`      | NOT — reverses result   | `!(5 > 2) // false`         | `!isAdmin;`                                         |
| `??`     | Nullish coalescing      | `name ?? "Guest"`           | `let name: string \| null = null; name ?? "Guest";` |

**`||` vs `??`** — `||` triggers on any falsy value; `??` triggers only on `null`/`undefined`:

```js
console.log(0 || 100); // 100 — 0 is falsy
console.log(0 ?? 100); // 0   — 0 is not nullish
console.log("" || "Guest"); // "Guest"
console.log("" ?? "Guest"); // ""
```

✅ Use `??` when `0`, `false`, or `""` are valid intended values.

---

## Conditional Statements

### if / else / else if

```ts
if (condition) {
  // true block
} else if (condition2) {
  // another check
} else {
  // fallback
}
```

```js
// JavaScript
let age = 16;
if (age >= 18) console.log("Adult");
else console.log("Minor");
```

```ts
// TypeScript
let age: number = 16;
if (age >= 18) console.log("Adult");
else console.log("Minor");
```

**Nested `if` vs `&&`** — prefer combining conditions with `&&` over deep nesting:

```ts
// Nested (avoid when possible)
if (country === "USA") {
  if (age >= 16) console.log("Can Drive");
}

// Preferred
if (country === "USA" && age >= 16) console.log("Can Drive");
```

**Flow table:**

| Condition                | Executed Block  |
| ------------------------ | --------------- |
| First condition true     | `if` block      |
| First false, second true | `else if` block |
| All false                | `else` block    |

### switch Statement

```ts
switch (expression) {
  case value1:
    // code
    break;
  case value2:
    // code
    break;
  default:
  // code
}
```

**`break` prevents fall-through:**

```js
let day = 1;
switch (day) {
  case 1:
    console.log("Monday");
    break; // stops here
  case 2:
    console.log("Tuesday");
}
// Output: Monday
```

**Without `break`** execution falls through to subsequent cases:

```js
let day = 1;
switch (day) {
  case 1:
    console.log("Monday");
  case 2:
    console.log("Tuesday");
}
// Output: Monday \n Tuesday
```

**Multiple cases sharing code:**

```js
switch (day) {
  case 6:
  case 0:
    console.log("Weekend");
    break;
  default:
    console.log("Weekday");
}
```

**`switch` uses strict comparison (`===`)** — `"0" === 0` is `false`, so it falls to `default`.

**`switch` vs `if...else`:**

| `switch`                                                  | `if...else`                                   |
| --------------------------------------------------------- | --------------------------------------------- |
| Best for comparing one variable against many fixed values | Best for ranges/complex logical conditions    |
| Cleaner for fixed options                                 | Better for `&&`, `\|\|`, `>`, `<` expressions |

### Ternary (Conditional) Operator

> **Note:** Not present in the source notes, but the most common shorthand for a simple `if...else` — added here for completeness.

```
condition ? valueIfTrue : valueIfFalse
```

```js
// JavaScript
let age = 20;
let type = age >= 18 ? "Adult" : "Minor";
console.log(type); // "Adult"
```

```ts
// TypeScript
let age: number = 20;
let type: string = age >= 18 ? "Adult" : "Minor";
```

**Chaining ternaries** (use sparingly — nested ternaries hurt readability):

```ts
let score: number = 85;
let grade: string = score >= 90 ? "A" : score >= 75 ? "B" : "C";
```

Use a ternary for simple, single-expression conditionals; prefer `if...else` once the logic spans multiple statements.

---

## Loops & Control Flow

| Loop         | Purpose                      | JavaScript                             | TypeScript                                    |
| ------------ | ---------------------------- | -------------------------------------- | --------------------------------------------- |
| `for`        | Fixed number of iterations   | `for(let i=0;i<5;i++) console.log(i);` | `for(let i:number=0;i<5;i++) console.log(i);` |
| `while`      | Runs while condition is true | `let i=0; while(i<5){i++;}`            | `let i:number=0; while(i<5){i++;}`            |
| `do...while` | Executes at least once       | `let i=0; do{i++;}while(i<5);`         | `let i:number=0; do{i++;}while(i<5);`         |
| `for...of`   | Iterate array **values**     | `for(const car of cars){}`             | `for(const car of cars){}` — `cars: string[]` |
| `for...in`   | Iterate object **keys**      | `for(const key in user){}`             | `for(const key in user){}`                    |
| `forEach()`  | Callback per array element   | `cars.forEach(c=>console.log(c));`     | `cars.forEach((c:string)=>console.log(c));`   |

**`for` vs `forEach()`:**

| `for`                        | `forEach()`                             |
| ---------------------------- | --------------------------------------- |
| Can use `break`/`continue`   | ❌ Cannot                               |
| Faster for complex loops     | Cleaner for simple array iteration      |
| Works with arrays & counters | Only works with arrays; no return value |

**`break`** exits a loop/switch immediately; **`continue`** skips to the next iteration:

```ts
for (let i: number = 0; i < 5; i++) {
  if (i === 3) break;
}
for (let i: number = 0; i < 5; i++) {
  if (i === 2) continue;
  console.log(i);
}
```

**Loop variable scope:** prefer `let` (block-scoped, cleaner) over `var` (function-scoped, leaks outside the loop).

**Labels** (rarely used) — let `break`/`continue` target a specific outer loop in nested loops:

| Statement         | Purpose                        |
| ----------------- | ------------------------------ |
| `break label;`    | Exit a specific outer loop     |
| `continue label;` | Continue a specific outer loop |

```js
outer: for (let i = 0; i < 3; i++) {
  for (let j = 0; j < 3; j++) {
    if (j === 1) continue outer;
    console.log(i, j);
  }
}
```

> ⚠️ Rarely used in real-world projects.

**Control flow types:**

| Type        | Examples                                |
| ----------- | --------------------------------------- |
| Sequential  | Normal top-to-bottom execution          |
| Conditional | `if`, `else`, `switch`, ternary         |
| Looping     | `for`, `while`, `do...while`, `forEach` |
| Jump        | `break`, `continue`, `return`, `throw`  |

**JavaScript is single-threaded** — one task at a time; long tasks can block execution, which is why asynchronous APIs (`Promise`, `async/await`) exist.

> **✅ Best Practices**
>
> | Recommendation                             | Reason                     |
> | ------------------------------------------ | -------------------------- |
> | Use `for` when the index is needed         | Better control             |
> | Use `for...of` for arrays                  | Cleaner than indexed loops |
> | Use `forEach()` for simple array iteration | Readable and concise       |
> | Use `for...in` only for objects            | Avoid with arrays          |
> | Avoid `var`                                | Use `let` or `const`       |
> | Always update loop variables               | Prevents infinite loops    |

> **📌 Key Points**
>
> | Topic                | Summary                                                             |
> | -------------------- | ------------------------------------------------------------------- |
> | `for`                | Best when the iteration count is known.                             |
> | `while`              | Runs while a condition is true.                                     |
> | `do...while`         | Executes at least once.                                             |
> | `for...of`           | Iterates array values.                                              |
> | `for...in`           | Iterates object keys.                                               |
> | `forEach()`          | Runs a callback per array element; no `break`/`continue`.           |
> | `break`              | Exits a loop or `switch`.                                           |
> | `continue`           | Skips the current iteration.                                        |
> | `var` vs `let`       | Prefer `let` — it's block-scoped.                                   |
> | Control Flow         | Governs execution order via conditions, loops, and jump statements. |
> | TypeScript Advantage | Same loop syntax as JavaScript, with compile-time type safety.      |

---

## Type Casting

> **Type Casting is TypeScript-only.** JavaScript has no casting — it uses runtime **type conversion** instead (e.g. `Number()`, `String()`).

Casting only changes how TypeScript _interprets_ a variable's type — it never changes the actual runtime value.

**`as` syntax (recommended):**

```ts
let value: unknown = "Hello";
console.log((value as string).length); // 5
```

**`<>` syntax (alternative — avoid in `.tsx` React files, conflicts with JSX):**

```ts
console.log((<string>value).length);
```

**Force casting** (bypasses TypeScript's safety checks — use sparingly):

```ts
let value2 = "Hello";
console.log(value2 as unknown as number);
```

**Casting vs Conversion:**

| Type Casting (TypeScript)     | Type Conversion (JavaScript)     |
| ----------------------------- | -------------------------------- |
| Changes only type information | Changes the actual value         |
| Compile-time feature          | Runtime feature                  |
| `value as string`             | `Number(value)`, `String(value)` |

```js
// JavaScript — actual value conversion
let value = "123";
console.log(Number(value)); // 123
```

> **✅ Best Practices**
>
> | Recommendation                              | Reason                              |
> | ------------------------------------------- | ----------------------------------- |
> | Use `as` for casting                        | Official and recommended syntax     |
> | Avoid `<>` in React (`.tsx`)                | Conflicts with JSX                  |
> | Prefer type conversion when changing values | Actually changes the data           |
> | Avoid force casting                         | Can bypass TypeScript safety checks |

> **📌 Key Points**
>
> | Topic                | Summary                                                              |
> | -------------------- | -------------------------------------------------------------------- |
> | Type Casting         | Tells TypeScript to treat a variable as another type.                |
> | `as`                 | Recommended syntax for type casting.                                 |
> | `<>`                 | Alternative syntax (not for React/TSX).                              |
> | Force Casting        | Cast via `unknown` to bypass compiler checks.                        |
> | Type Conversion      | JavaScript converts the actual value (e.g., `Number()`, `String()`). |
> | TypeScript Advantage | Compile-time type safety while allowing controlled type casting.     |

---

## TypeScript — Generics

> **Generics are TypeScript-only** — JavaScript has no compile-time type system to support them.

Generics let you write **reusable code that works across multiple types** while keeping type safety.

```ts
// Without generics — duplicated per type
function printString(value: string) {
  return value;
}
function printNumber(value: number) {
  return value;
}

// With generics — one function, all types
function print<T>(value: T): T {
  return value;
}
print<string>("Hello");
print<number>(100);
print("Hello"); // ✅ type inferred automatically — no need for print<string>
```

`<T>` is a **type parameter**; common names are `T` (Type), `K` (Key), `V` (Value), `U` (another type).

**Multiple type parameters:**

```ts
function createPair<T, U>(first: T, second: U): [T, U] {
  return [first, second];
}
const pair = createPair("John", 25); // T=string, U=number
```

**Generic classes:**

```ts
class Box<T> {
  constructor(private value: T) {}
  getValue(): T {
    return this.value;
  }
}
const numberBox = new Box<number>(100);
const stringBox = new Box<string>("Hello");
```

**Generic type alias:**

```ts
type ApiResponse<T> = { data: T };
const user: ApiResponse<string> = { data: "John" };
const age: ApiResponse<number> = { data: 25 };
```

**Generic interface:**

```ts
interface Response<T> {
  data: T;
}
const user2: Response<string> = { data: "John" };
```

**Default generic type:**

```ts
class Box2<T = string> {
  constructor(private value: T) {}
}
const box = new Box2("Hello"); // T = string (default)
const box2 = new Box2<number>(100); // T = number (explicit)
```

**Generic constraints (`extends`) — restrict which types are allowed:**

```ts
function printLength<T extends { length: number }>(value: T) {
  console.log(value.length);
}
printLength("Hello"); // ✅ strings have .length
printLength([1, 2, 3]); // ✅ arrays have .length
printLength(100); // ❌ number has no .length

function createPair2<T extends string | number, U extends string | number>(
  a: T,
  b: U,
) {
  return [a, b];
}
createPair2("John", 25); // ✅
createPair2(true, false); // ❌ boolean isn't allowed
```

**When to use generics:** reusable functions, API response wrappers, collections (`Array<T>`), classes, interfaces. Real-world examples: `Array<string>`, `Promise<User>`, `Map<string, number>`, `Set<number>`.

> **✅ Best Practices**
>
> | Recommendation                                         | Reason               |
> | ------------------------------------------------------ | -------------------- |
> | Use meaningful type names (`T`, `K`, `V`)              | Standard convention  |
> | Let TypeScript infer types when possible               | Cleaner code         |
> | Add `extends` when a generic needs specific properties | Improves type safety |
> | Use default generic types for flexibility              | Reduces boilerplate  |

> **📌 Key Points**
>
> | Topic                | Summary                                                  |
> | -------------------- | -------------------------------------------------------- |
> | Generic (`<T>`)      | A placeholder for a type.                                |
> | Generic Function     | One function works with multiple data types.             |
> | Generic Class        | One class can store different types safely.              |
> | Generic Type Alias   | Creates reusable generic types.                          |
> | Generic Interface    | Interfaces can also use generics.                        |
> | Default Generic      | Provides a fallback type if none is specified.           |
> | `extends` Constraint | Restricts the allowed types for a generic.               |
> | Type Inference       | TypeScript can automatically determine the generic type. |
> | TypeScript Advantage | Reusable, type-safe code without duplicating logic.      |

**Memory trick:**

| Generic         | Think of it as...                 |
| --------------- | --------------------------------- |
| `<T>`           | "Any type" placeholder            |
| `function<T>()` | One function for all data types   |
| `class<T>`      | One class for all data types      |
| `T = string`    | Default type if none provided     |
| `T extends X`   | Only allow types that satisfy `X` |

---

## TypeScript — Utility Types

> Built-in TypeScript types that **transform existing types** without rewriting them — common in APIs, forms, and state management.

```ts
interface User {
  id: number;
  name: string;
  email: string;
}

// Without utility types — duplicate interface
interface UpdateUser {
  id?: number;
  name?: string;
  email?: string;
}

// With utility types — one line
type UpdateUser2 = Partial<User>;
```

| Utility Type    | Purpose                               | Real-world Example              |
| --------------- | ------------------------------------- | ------------------------------- |
| `Partial<T>`    | Makes all properties optional         | Update APIs (PATCH), edit forms |
| `Required<T>`   | Makes all properties mandatory        | Final DB save, validation       |
| `Readonly<T>`   | Prevents modification                 | Config objects, immutable state |
| `Record<K,V>`   | Strongly-typed key-value object       | Maps, dictionaries, caches      |
| `Pick<T,K>`     | Keeps only selected properties        | Public profile, login response  |
| `Omit<T,K>`     | Removes selected properties           | Hide passwords, internal fields |
| `Exclude<T,U>`  | Removes members from a union          | Filter enum/union values        |
| `ReturnType<T>` | Extracts a function's return type     | API/service response types      |
| `Parameters<T>` | Extracts a function's parameter types | Wrappers, middleware            |

**Examples:**

```ts
// Partial — all fields optional
const user: Partial<User> = { name: "John" };

// Required — even optional fields become mandatory
interface Car {
  brand: string;
  model: string;
  mileage?: number;
}
const car: Required<Car> = { brand: "BMW", model: "X5", mileage: 20000 };

// Readonly — cannot reassign after creation
const user2: Readonly<User> = { id: 1, name: "John", email: "j@x.com" };
// user2.name = "New"; ❌ Error

// Record — key/value dictionary
const marks: Record<string, number> = { John: 95, Alice: 90, Bob: 88 };

// Pick — keep only specific properties
type UserName = Pick<User, "name">;
const u: UserName = { name: "John" };

// Omit — remove specific properties
type PublicUser = Omit<User, "email">; // keeps id, name

// Exclude — remove a member from a union
type Status = "Pending" | "Success" | "Failed";
type NewStatus = Exclude<Status, "Failed">; // "Pending" | "Success"

// ReturnType — capture a function's return shape
function getUser() {
  return { id: 1, name: "John" };
}
type UserFromFn = ReturnType<typeof getUser>; // { id: number; name: string }

// Parameters — capture a function's argument types
function login(username: string, password: string) {}
type LoginParams = Parameters<typeof login>; // [string, string]
type Username = Parameters<typeof login>[0]; // string
```

**Pick vs Omit:** Pick = _keep_, Omit = _remove_.

**Memory trick:**

| Utility Type | Memory Trick                      |
| ------------ | --------------------------------- |
| `Partial`    | Make everything optional          |
| `Required`   | Make everything mandatory         |
| `Readonly`   | Read only, can't modify           |
| `Record`     | Key → Value mapping               |
| `Pick`       | Pick what you want                |
| `Omit`       | Omit (remove) what you don't want |
| `Exclude`    | Exclude from a union              |
| `ReturnType` | Get what a function returns       |
| `Parameters` | Get what a function accepts       |

> **✅ Best Practices**
>
> | Recommendation                                          | Why?                                                |
> | ------------------------------------------------------- | --------------------------------------------------- |
> | Use `Partial` for update operations                     | Avoids creating separate update interfaces          |
> | Use `Readonly` for configuration and constants          | Prevents accidental mutations                       |
> | Use `Pick` and `Omit` instead of duplicating interfaces | Keeps types DRY                                     |
> | Use `ReturnType` and `Parameters` to derive types       | Reduces maintenance when function signatures change |
> | Prefer Utility Types over creating similar interfaces   | Cleaner, reusable, easier to maintain               |

> **📌 Key Points**
>
> | Topic                | Summary                                                       |
> | -------------------- | ------------------------------------------------------------- |
> | Utility Types        | Built-in helpers that transform existing types.               |
> | `Partial`            | Makes all properties optional.                                |
> | `Required`           | Makes all properties mandatory.                               |
> | `Readonly`           | Makes all properties immutable after assignment.              |
> | `Record`             | Creates strongly typed key-value objects.                     |
> | `Pick`               | Keeps only specified properties.                              |
> | `Omit`               | Removes specified properties.                                 |
> | `Exclude`            | Removes members from a union type.                            |
> | `ReturnType`         | Extracts the return type of a function.                       |
> | `Parameters`         | Extracts a function's parameter types as a tuple.             |
> | TypeScript Advantage | Reduces duplication, improves type safety, eases maintenance. |
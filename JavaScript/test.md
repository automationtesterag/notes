Absolutely! Here's an **enhanced and complete guide** to `map()`, `filter()`, and `reduce()` in JavaScript — now with **expanded real-world and technical examples**, all parameters explained, syntax, and chaining examples.

---

# 🚀 JavaScript Array Methods: `map()`, `filter()`, and `reduce()`

These 3 array methods are the foundation of functional programming in JavaScript. They help you process, transform, and summarize data cleanly and efficiently.

---

## ✅ 1. `map()`

### 🔍 Purpose:

Transforms **each element** of an array and returns a **new array** of the same length.

### 📦 Syntax:

```javascript
array.map((element, index, array) => {
  // return new value
});
```

### 🧩 Parameters:

- `element`: Current item.
- `index` (optional): Position of current element.
- `array` (optional): Reference to original array.

---

### 🧪 Examples:

#### ✅ Basic: Double each number

```javascript
const nums = [1, 2, 3];
const doubled = nums.map((num) => num * 2);
console.log(doubled); // [2, 4, 6]
```

#### ✅ With index and array

```javascript
const colors = ["red", "green", "blue"];

const colorInfo = colors.map((color, index, array) => {
  return `Color ${index + 1}/${array.length}: ${color}`;
});

console.log(colorInfo);
// ["Color 1/3: red", "Color 2/3: green", "Color 3/3: blue"]
```

#### ✅ Real-world: Format product prices

```javascript
const products = [
  { name: "Phone", price: 699 },
  { name: "Tablet", price: 899 },
];

const formatted = products.map((p, i) => ({
  label: `${p.name} - $${p.price}`,
  index: i,
}));

console.log(formatted);
// [{ label: 'Phone - $699', index: 0 }, { label: 'Tablet - $899', index: 1 }]
```

---

## ✅ 2. `filter()`

### 🔍 Purpose:

Filters elements based on a condition and returns a **new array** of matching items.

### 📦 Syntax:

```javascript
array.filter((element, index, array) => {
  // return true to keep the element
});
```

### 🧩 Parameters:

- `element`: Current item.
- `index` (optional): Position of item.
- `array` (optional): The original array.

---

### 🧪 Examples:

#### ✅ Basic: Filter even numbers

```javascript
const nums = [1, 2, 3, 4, 5];
const evens = nums.filter((num) => num % 2 === 0);
console.log(evens); // [2, 4]
```

#### ✅ With index: Filter every second item

```javascript
const items = ["a", "b", "c", "d", "e"];
const everySecond = items.filter((item, index) => index % 2 === 0);
console.log(everySecond); // ['a', 'c', 'e']
```

#### ✅ Real-world: Filter active users

```javascript
const users = [
  { name: "Alice", active: true },
  { name: "Bob", active: false },
];

const activeUsers = users.filter((user) => user.active);
console.log(activeUsers); // [{ name: 'Alice', active: true }]
```

#### ✅ Advanced: Filter above-average numbers

```javascript
const scores = [60, 80, 90, 70];

const aboveAverage = scores.filter((score, _, arr) => {
  const avg = arr.reduce((sum, s) => sum + s, 0) / arr.length;
  return score > avg;
});

console.log(aboveAverage); // [80, 90]
```

---

## ✅ 3. `reduce()`

### 🔍 Purpose:

Reduces an array to a **single value** (number, string, object, array, etc.)

### 📦 Syntax:

```javascript
array.reduce((accumulator, currentValue, index, array) => {
  // return updated accumulator
}, initialValue);
```

### 🧩 Parameters:

- `accumulator`: Carries result between iterations.
- `currentValue`: The current element.
- `index` (optional): Index of current element.
- `array` (optional): The original array.
- `initialValue`: Starting value of the accumulator.

---

### 🧪 Examples:

#### ✅ Sum of numbers

```javascript
const numbers = [1, 2, 3, 4];
const sum = numbers.reduce((acc, num) => acc + num, 0);
console.log(sum); // 10
```

#### ✅ Count word occurrences

```javascript
const words = ["apple", "banana", "apple", "cherry"];

const count = words.reduce((acc, word) => {
  acc[word] = (acc[word] || 0) + 1;
  return acc;
}, {});

console.log(count);
// { apple: 2, banana: 1, cherry: 1 }
```

#### ✅ Flatten an array

```javascript
const nested = [[1, 2], [3, 4], [5]];
const flat = nested.reduce((acc, val) => acc.concat(val), []);
console.log(flat); // [1, 2, 3, 4, 5]
```

#### ✅ Build a grouped object

```javascript
const books = [
  { title: "A", genre: "fiction" },
  { title: "B", genre: "non-fiction" },
  { title: "C", genre: "fiction" },
];

const grouped = books.reduce((acc, book) => {
  if (!acc[book.genre]) acc[book.genre] = [];
  acc[book.genre].push(book.title);
  return acc;
}, {});

console.log(grouped);
// { fiction: ['A', 'C'], 'non-fiction': ['B'] }
```

---

## 🔗 Chaining Example: Real-World Use Case

### 👉 Scenario: You have a list of users and you want to:

- Filter only **active** users
- Extract their **emails**
- Count how many you have

```javascript
const users = [
  { name: "Alice", email: "alice@example.com", active: true },
  { name: "Bob", email: "bob@example.com", active: false },
  { name: "Charlie", email: "charlie@example.com", active: true },
];

const result = users
  .filter((user) => user.active)
  .map((user, index, arr) => `${index + 1}/${arr.length}: ${user.email}`)
  .reduce(
    (acc, email) => {
      acc.count++;
      acc.emails.push(email);
      return acc;
    },
    { count: 0, emails: [] }
  );

console.log(result);
// { count: 2, emails: ['1/2: alice@example.com', '2/2: charlie@example.com'] }
```

---

## 🧠 Summary Comparison

| Method     | Purpose                    | Returns   | Mutates Original? | Common Use Cases                 |
| ---------- | -------------------------- | --------- | ----------------- | -------------------------------- |
| `map()`    | Transform each item        | New array | ❌ No             | Format data, modify properties   |
| `filter()` | Select items conditionally | New array | ❌ No             | Search, validation, cleanup      |
| `reduce()` | Accumulate to single value | Any value | ❌ No             | Totals, grouping, merging, stats |

---

Sure! In JavaScript, an **array** is a special variable that can hold multiple values at once. It’s a list-like object used to store sequences of data.

```js
let fruits = ["apple", "banana", "cherry"];
```

Below is a breakdown of **common array methods** in JavaScript with examples:

---

### 🔹 1. `push()` – Add item to the end

```js
fruits.push("orange");
console.log(fruits); // ["apple", "banana", "cherry", "orange"]
```

---

### 🔹 2. `pop()` – Remove item from the end

```js
fruits.pop();
console.log(fruits); // ["apple", "banana", "cherry"]
```

---

### 🔹 3. `unshift()` – Add item to the beginning

```js
fruits.unshift("grape");
console.log(fruits); // ["grape", "apple", "banana", "cherry"]
```

---

### 🔹 4. `shift()` – Remove item from the beginning

```js
fruits.shift();
console.log(fruits); // ["apple", "banana", "cherry"]
```

---

### 🔹 5. `concat()` – Merge arrays

```js
let moreFruits = ["mango", "pineapple"];
let allFruits = fruits.concat(moreFruits);
console.log(allFruits); // ["apple", "banana", "cherry", "mango", "pineapple"]
```

---

### 🔹 6. `join()` – Combine array into a string

```js
let joined = fruits.join(", ");
console.log(joined); // "apple, banana, cherry"
```

---

### 🔹 7. `slice(start, end)` – Copy a portion of array

```js
let sliced = fruits.slice(1, 3);
console.log(sliced); // ["banana", "cherry"]
```

---

### 🔹 8. `splice(start, deleteCount, item1, item2, ...)` – Add/Remove items

```js
fruits.splice(1, 1, "kiwi");
console.log(fruits); // ["apple", "kiwi", "cherry"]
```

---

### 🔹 9. `indexOf()` – Get index of item

```js
console.log(fruits.indexOf("kiwi")); // 1
```

---

### 🔹 10. `includes()` – Check if value exists

```js
console.log(fruits.includes("cherry")); // true
```

---

### 🔹 11. `forEach()` – Run function on each item

```js
fruits.forEach(function (fruit) {
  console.log(fruit);
});
```

---

### 🔹 12. `map()` – Transform each item

```js
let upperFruits = fruits.map((f) => f.toUpperCase());
console.log(upperFruits); // ["APPLE", "KIWI", "CHERRY"]
```

---

### 🔹 13. `filter()` – Filter items by condition

```js
let filtered = fruits.filter((f) => f.length > 4);
console.log(filtered); // ["apple", "cherry"]
```

---

### 🔹 14. `reduce()` – Reduce to a single value

```js
let numbers = [1, 2, 3, 4];
let sum = numbers.reduce((acc, curr) => acc + curr, 0);
console.log(sum); // 10
```

---

### 🔹 15. `find()` – Find first match

```js
let found = fruits.find((f) => f.startsWith("c"));
console.log(found); // "cherry"
```

---

### 🔹 16. `findIndex()` – Find index of first match

```js
let index = fruits.findIndex((f) => f.startsWith("c"));
console.log(index); // 2
```

---

### 🔹 17. `sort()` – Sort the array

```js
let nums = [4, 2, 9, 1];
nums.sort((a, b) => a - b);
console.log(nums); // [1, 2, 4, 9]
```

---

### 🔹 18. `reverse()` – Reverse the array

```js
nums.reverse();
console.log(nums); // [9, 4, 2, 1]
```

---

### 🔹 19. `flat()` – Flatten nested arrays

```js
let nested = [1, [2, [3]]];
console.log(nested.flat(2)); // [1, 2, 3]
```

---

### 🔹 20. `every()` – Check if all items match condition

```js
let allAboveZero = nums.every((n) => n > 0);
console.log(allAboveZero); // true
```

---

### 🔹 21. `some()` – Check if at least one item matches

```js
let hasNegative = nums.some((n) => n < 0);
console.log(hasNegative); // false
```

---

Here's a **summary table** of the array methods with their descriptions and simple examples:

---

### 📋 JavaScript Array Methods Summary

| Method        | Description                | Example                            | Output                   |
| ------------- | -------------------------- | ---------------------------------- | ------------------------ |
| `push()`      | Add to end                 | `arr.push("x")`                    | `["a", "b", "x"]`        |
| `pop()`       | Remove from end            | `arr.pop()`                        | `["a", "b"]`             |
| `unshift()`   | Add to beginning           | `arr.unshift("x")`                 | `["x", "a", "b"]`        |
| `shift()`     | Remove from beginning      | `arr.shift()`                      | `["b"]`                  |
| `concat()`    | Merge arrays               | `arr.concat([1,2])`                | `["a", "b", 1, 2]`       |
| `join()`      | Join elements into string  | `arr.join(", ")`                   | `"a, b"`                 |
| `slice()`     | Extract section            | `arr.slice(1, 3)`                  | `["b", "c"]`             |
| `splice()`    | Add/remove at index        | `arr.splice(1, 1, "x")`            | `["a", "x", "c"]`        |
| `indexOf()`   | Find index of value        | `arr.indexOf("b")`                 | `1`                      |
| `includes()`  | Check if value exists      | `arr.includes("b")`                | `true`                   |
| `forEach()`   | Run function for each item | `arr.forEach(x => console.log(x))` | `a b c` (console output) |
| `map()`       | Transform items            | `arr.map(x => x.toUpperCase())`    | `["A", "B", "C"]`        |
| `filter()`    | Filter items by condition  | `arr.filter(x => x !== "b")`       | `["a", "c"]`             |
| `reduce()`    | Reduce to single value     | `[1,2,3].reduce((a,b) => a+b)`     | `6`                      |
| `find()`      | First match                | `arr.find(x => x.startsWith("c"))` | `"cherry"`               |
| `findIndex()` | Index of first match       | `arr.findIndex(x => x === "b")`    | `1`                      |
| `sort()`      | Sort items                 | `[3,1,2].sort((a,b)=>a-b)`         | `[1, 2, 3]`              |
| `reverse()`   | Reverse order              | `[1,2,3].reverse()`                | `[3, 2, 1]`              |
| `flat()`      | Flatten nested arrays      | `[1,[2,[3]]].flat(2)`              | `[1, 2, 3]`              |
| `every()`     | All items match condition? | `[1,2,3].every(x => x > 0)`        | `true`                   |
| `some()`      | At least one item matches? | `[1,2,-3].some(x => x < 0)`        | `true`                   |

---

Absolutely! Let's combine everything into a **clear and concise explanation of the `Object` in JavaScript**, followed by the **most commonly used methods**, with **real-world examples**. This serves as a comprehensive, practical guide.

---

# ✅ **JavaScript `Object` Explained (With Most Used Methods)**

---

## 🧱 What is an Object in JavaScript?

An **object** in JavaScript is a collection of key–value pairs. It is used to store and manipulate data. Keys are always strings or symbols; values can be any data type.

```js
const person = {
  name: "Alice",
  age: 25,
  greet() {
    console.log(`Hi, I'm ${this.name}`);
  },
};
```

- **`name` and `age`** are **properties**
- **`greet()`** is a **method**
- The object can represent anything — user, config, DOM element, etc.

---

## 🧠 How to Create an Object

```js
const obj1 = {}; // Object literal (most common)
const obj2 = new Object(); // Using constructor
const obj3 = Object.create(null); // Without prototype
```

---

## ⭐ Most Commonly Used `Object` Methods

These methods come from the built-in global `Object` class.

---

### 1. ✅ **`Object.keys(obj)`**

Returns an array of the object's **own enumerable property names**.

```js
const user = { name: "Alice", age: 25 };
console.log(Object.keys(user)); // ["name", "age"]
```

---

### 2. ✅ **`Object.values(obj)`**

Returns an array of the object's **own enumerable values**.

```js
console.log(Object.values(user)); // ["Alice", 25]
```

---

### 3. ✅ **`Object.entries(obj)`**

Returns an array of `[key, value]` pairs.

```js
console.log(Object.entries(user));
// [["name", "Alice"], ["age", 25]]
```

Use case: Looping or converting to/from `Map`.

---

### 4. ✅ **`Object.fromEntries(entries)`**

Converts an array of `[key, value]` back into an object.

```js
const entries = [
  ["name", "Alice"],
  ["age", 25],
];
const obj = Object.fromEntries(entries);
console.log(obj); // { name: "Alice", age: 25 }
```

---

### 5. ✅ **`Object.assign(target, ...sources)`**

Copies properties from source to target object.

```js
const user = { name: "Alice" };
const updates = { age: 25 };
const updatedUser = Object.assign({}, user, updates);
console.log(updatedUser); // { name: "Alice", age: 25 }
```

---

### 6. ✅ **`Object.freeze(obj)`**

Makes an object immutable — properties cannot be changed or added.

```js
const config = Object.freeze({ debug: true });
config.debug = false; // Ignored
console.log(config.debug); // true
```

---

### 7. ✅ **`Object.hasOwn(obj, prop)`** (modern)

Checks if a property exists directly on the object (not inherited).

```js
console.log(Object.hasOwn(user, "name")); // true
```

---

### 8. ✅ **`Object.create(proto)`**

Creates a new object with a specified prototype.

```js
const animal = {
  speak() {
    return "Generic sound";
  },
};
const dog = Object.create(animal);
console.log(dog.speak()); // "Generic sound"
```

---

### 9. ✅ **`obj.hasOwnProperty(prop)`** (legacy but still common)

Same as `Object.has()`, but on the instance.

```js
console.log(user.hasOwnProperty("name")); // true
```

---

### 10. ✅ **`Object.getPrototypeOf(obj)`**

Returns the prototype of the object.

```js
console.log(Object.getPrototypeOf(dog) === animal); // true
```

---

## 🔁 Iterating Through an Object

```js
for (const [key, value] of Object.entries(user)) {
  console.log(`${key}: ${value}`);
}
```

---

## 📌 Summary Table

| Method                    | Purpose                            |
| ------------------------- | ---------------------------------- |
| `Object.keys()`           | Get property names                 |
| `Object.values()`         | Get property values                |
| `Object.entries()`        | Get key-value pairs                |
| `Object.fromEntries()`    | Convert pairs to object            |
| `Object.assign()`         | Clone or merge objects             |
| `Object.freeze()`         | Make object immutable              |
| `Object.hasOwn()`         | Check for direct property (modern) |
| `hasOwnProperty()`        | Check for direct property (legacy) |
| `Object.create()`         | Create object with prototype       |
| `Object.getPrototypeOf()` | Access prototype                   |

---

In JavaScript, a **string** is a sequence of characters used to represent text. Strings are enclosed in single quotes (`' '`), double quotes (`" "`), or backticks (`` ` ` `` for template literals).

### ✅ Declaring a String

```js
let str1 = "Hello";
let str2 = "World";
let str3 = `Hello, ${str2}!`; // Template literal
```

---

## 🔹 Common String Methods in JavaScript

Below are the most commonly used **String methods** in JavaScript, with examples:

### 1. `length`

Returns the length of the string.

```js
let str = "Hello";
console.log(str.length); // 5
```

---

### 2. `charAt(index)`

Returns the character at the specified index.

```js
console.log("Hello".charAt(1)); // "e"
```

---

### 3. `charCodeAt(index)`

Returns the Unicode of the character at the specified index.

```js
console.log("ABC".charCodeAt(0)); // 65
```

---

### 4. `toUpperCase()` / `toLowerCase()`

Converts the string to uppercase or lowercase.

```js
"hello".toUpperCase(); // "HELLO"
"HELLO".toLowerCase(); // "hello"
```

---

### 5. `indexOf(searchValue)` / `lastIndexOf(searchValue)`

Returns the index of the first or last occurrence of a substring.

```js
"hello world".indexOf("o"); // 4
"hello world".lastIndexOf("o"); // 7
```

---

### 6. `includes(searchValue)`

Checks if the string includes the given substring.

```js
"javascript".includes("script"); // true
```

---

### 7. `startsWith(searchValue)` / `endsWith(searchValue)`

Checks if the string starts or ends with the given substring.

```js
"hello".startsWith("he"); // true
"hello".endsWith("lo"); // true
```

---

### 8. `substring(start, end)` / `slice(start, end)`

Extracts parts of a string. `slice` can use negative indices.

```js
"Hello World".substring(0, 5); // "Hello"
"Hello World".slice(-5); // "World"
```

---

### 9. `replace(searchValue, newValue)`

Replaces part of the string.

```js
"Hi Bob".replace("Bob", "Alice"); // "Hi Alice"
```

---

### 10. `replaceAll(searchValue, newValue)`

Replaces all occurrences.

```js
"1-2-3-4".replaceAll("-", ","); // "1,2,3,4"
```

---

### 11. `split(separator)`

Splits the string into an array.

```js
"apple,banana,orange".split(","); // ["apple", "banana", "orange"]
```

---

### 12. `trim()` / `trimStart()` / `trimEnd()`

Removes whitespace from the start/end of a string.

```js
"  hello  ".trim(); // "hello"
"  hello".trimStart(); // "hello"
"hello  ".trimEnd(); // "hello"
```

---

### 13. `concat()`

Joins two or more strings.

```js
"Hello".concat(" ", "World"); // "Hello World"
```

---

### 14. `repeat(count)`

Repeats the string `count` times.

```js
"ha".repeat(3); // "hahaha"
```

---

### 15. `match(regex)`

Returns matched results using regular expressions.

```js
"abc123".match(/\d+/); // ["123"]
```

---

### 16. `padStart(targetLength, padString)` / `padEnd(targetLength, padString)`

Pads a string to a target length.

```js
"5".padStart(3, "0"); // "005"
"5".padEnd(3, "0"); // "500"
```

---

### 📊 String Methods Summary Table

| Method              | Description                                   | Example                        | Output       |
| ------------------- | --------------------------------------------- | ------------------------------ | ------------ |
| `length`            | Returns string length                         | `"abc".length`                 | `3`          |
| `charAt(n)`         | Character at index `n`                        | `"abc".charAt(1)`              | `"b"`        |
| `charCodeAt(n)`     | Unicode of char at index `n`                  | `"A".charCodeAt(0)`            | `65`         |
| `toUpperCase()`     | Convert to uppercase                          | `"abc".toUpperCase()`          | `"ABC"`      |
| `toLowerCase()`     | Convert to lowercase                          | `"ABC".toLowerCase()`          | `"abc"`      |
| `indexOf(str)`      | First index of substring                      | `"hello".indexOf("l")`         | `2`          |
| `lastIndexOf(str)`  | Last index of substring                       | `"hello".lastIndexOf("l")`     | `3`          |
| `includes(str)`     | Checks for substring                          | `"abc".includes("b")`          | `true`       |
| `startsWith(str)`   | Begins with substring                         | `"hello".startsWith("he")`     | `true`       |
| `endsWith(str)`     | Ends with substring                           | `"hello".endsWith("lo")`       | `true`       |
| `substring(a,b)`    | Substring from a to b (excludes b)            | `"hello".substring(0, 2)`      | `"he"`       |
| `slice(a,b)`        | Extract substring (supports negative indices) | `"hello".slice(-3)`            | `"llo"`      |
| `replace(a,b)`      | Replace first occurrence                      | `"cat".replace("c", "b")`      | `"bat"`      |
| `replaceAll(a,b)`   | Replace all occurrences                       | `"a-b-c".replaceAll("-", ":")` | `"a:b:c"`    |
| `split(char)`       | Splits string into array                      | `"a,b".split(",")`             | `["a", "b"]` |
| `trim()`            | Remove whitespace from both ends              | `"  hi  ".trim()`              | `"hi"`       |
| `concat()`          | Concatenates strings                          | `"Hi".concat(" ", "there")`    | `"Hi there"` |
| `repeat(n)`         | Repeats string `n` times                      | `"a".repeat(3)`                | `"aaa"`      |
| `match(regex)`      | Match pattern using regex                     | `"123".match(/\d+/)`           | `["123"]`    |
| `padStart(len,str)` | Pads start to length with str                 | `"5".padStart(3, "0")`         | `"005"`      |
| `padEnd(len,str)`   | Pads end to length with str                   | `"5".padEnd(3, "0")`           | `"500"`      |

---

# JavaScript Regular Expressions - Pattern Reference & Methods

## Pattern Reference Table

### Basic Characters

| Pattern | Description          | Example Pattern | Test String         | Matches   |
| ------- | -------------------- | --------------- | ------------------- | --------- |
| `a`     | Exact character 'a'  | `/cat/`         | "cat"               | ✅ "cat"  |
| `.`     | Any single character | `/c.t/`         | "cat", "cut", "c9t" | ✅ All    |
| `\.`    | Literal dot          | `/3\.14/`       | "3.14"              | ✅ "3.14" |
| `\`     | Escape character     | `/\$/`          | "$100"              | ✅ "$"    |

### Quantifiers (How Many Times)

| Pattern | Description       | Example Pattern | Test String         | Matches                                 |
| ------- | ----------------- | --------------- | ------------------- | --------------------------------------- |
| `*`     | 0 or more         | `/ab*/`         | "a", "ab", "abbb"   | ✅ All                                  |
| `+`     | 1 or more         | `/ab+/`         | "a", "ab", "abbb"   | ❌ "a", ✅ "ab", "abbb"                 |
| `?`     | 0 or 1 (optional) | `/colou?r/`     | "color", "colour"   | ✅ Both                                 |
| `{3}`   | Exactly 3 times   | `/a{3}/`        | "aa", "aaa", "aaaa" | ❌ "aa", ✅ "aaa", ✅ "aaaa"            |
| `{2,}`  | 2 or more times   | `/a{2,}/`       | "a", "aa", "aaa"    | ❌ "a", ✅ "aa", "aaa"                  |
| `{2,4}` | Between 2 and 4   | `/a{2,4}/`      | "a", "aa", "aaaaa"  | ❌ "a", ✅ "aa", ✅ "aaaa" from "aaaaa" |

### Position (Where to Match)

| Pattern | Description       | Example Pattern | Test String    | Matches              |
| ------- | ----------------- | --------------- | -------------- | -------------------- |
| `^`     | Start of string   | `/^hello/`      | "hello world"  | ✅ "hello"           |
| `$`     | End of string     | `/world$/`      | "hello world"  | ✅ "world"           |
| `\b`    | Word boundary     | `/\bcat\b/`     | "cat", "catch" | ✅ "cat", ❌ "catch" |
| `\B`    | Not word boundary | `/\Bcat\B/`     | "concatenate"  | ✅ "cat" in middle   |

### Character Sets (What Characters)

| Pattern    | Description          | Example Pattern | Test String | Matches          |
| ---------- | -------------------- | --------------- | ----------- | ---------------- |
| `[abc]`    | Any of a, b, or c    | `/[aeiou]/`     | "hello"     | ✅ "e", "o"      |
| `[^abc]`   | Not a, b, or c       | `/[^aeiou]/`    | "hello"     | ✅ "h", "l", "l" |
| `[a-z]`    | Any lowercase letter | `/[a-z]/`       | "Hello"     | ✅ "ello"        |
| `[A-Z]`    | Any uppercase letter | `/[A-Z]/`       | "Hello"     | ✅ "H"           |
| `[0-9]`    | Any digit            | `/[0-9]/`       | "abc123"    | ✅ "1", "2", "3" |
| `[a-zA-Z]` | Any letter           | `/[a-zA-Z]/`    | "Hello123"  | ✅ "Hello"       |

### Shortcuts (Common Patterns)

| Pattern | Description        | Example Pattern | Test String   | Same As             |
| ------- | ------------------ | --------------- | ------------- | ------------------- |
| `\d`    | Any digit          | `/\d/`          | "abc123"      | `[0-9]`             |
| `\D`    | Not a digit        | `/\D/`          | "abc123"      | `[^0-9]`            |
| `\w`    | Word character     | `/\w/`          | "hello_123"   | `[a-zA-Z0-9_]`      |
| `\W`    | Not word character | `/\W/`          | "hello!"      | `[^a-zA-Z0-9_]`     |
| `\s`    | Whitespace         | `/\s/`          | "hello world" | Space, tab, newline |
| `\S`    | Not whitespace     | `/\S/`          | "hello world" | Any non-space       |

### Groups and Choices

| Pattern | Description              | Example Pattern    | Test String    | Matches                 |
| ------- | ------------------------ | ------------------ | -------------- | ----------------------- |
| `()`    | Capture group            | `/(cat)\|(dog)/`   | "I have a cat" | ✅ "cat" (captured)     |
| `(?:)`  | Group without capture    | `/(?:cat)\|(dog)/` | "I have a cat" | ✅ "cat" (not captured) |
| `\|`    | OR (this or that)        | `/cat\|dog/`       | "I have a cat" | ✅ "cat"                |
| `\1`    | Use captured group again | `/(\w+) \1/`       | "hello hello"  | ✅ Repeated word        |

### Advanced (Look Around)

| Pattern | Description             | Example Pattern    | Test String     | Matches                                      |
| ------- | ----------------------- | ------------------ | --------------- | -------------------------------------------- |
| `(?=)`  | Must be followed by     | `/Java(?=Script)/` | "JavaScript"    | ✅ "Java" (only if followed by "Script")     |
| `(?!)`  | Must NOT be followed by | `/Java(?!Script)/` | "Java language" | ✅ "Java" (only if NOT followed by "Script") |

---

## Creating Regular Expressions

```javascript
// Two ways to create regex:

// 1. Literal notation (most common)
const regex1 = /hello/;

// 2. Constructor
const regex2 = new RegExp("hello");
```

## Flags (Modifiers)

```javascript
const regex = /hello/gi;
// g = global (find all matches, not just first)
// i = ignore case (Hello, HELLO, hello all match)
// m = multiline (^ and $ work on each line)
```

---

## JavaScript Methods with Simple Examples

### 1. test() - "Does this pattern exist?"

**Returns:** true or false

```javascript
const hasNumbers = /\d/; // Looking for any digit

console.log(hasNumbers.test("hello123")); // true (has numbers)
console.log(hasNumbers.test("hello")); // false (no numbers)

// Real example: Check if email looks valid
const emailCheck = /@/;
console.log(emailCheck.test("user@gmail.com")); // true
console.log(emailCheck.test("notanemail")); // false
```

### 2. match() - "What matches did you find?"

**Returns:** Array of matches or null

```javascript
const text = "My phone is 123-456-7890";
const phonePattern = /\d{3}-\d{3}-\d{4}/;

console.log(text.match(phonePattern));
// Result: ['123-456-7890']

// Find ALL matches with 'g' flag
const text2 = "Call 111-222-3333 or 444-555-6666";
console.log(text2.match(/\d{3}-\d{3}-\d{4}/g));
// Result: ['111-222-3333', '444-555-6666']
```

### 3. replace() - "Replace what you find"

**Returns:** New string with replacements

```javascript
const sentence = "I like cats and cats like me";

// Replace first match
console.log(sentence.replace(/cats/, "dogs"));
// Result: "I like dogs and cats like me"

// Replace ALL matches with 'g' flag
console.log(sentence.replace(/cats/g, "dogs"));
// Result: "I like dogs and dogs like me"

// Real example: Clean phone number
const messyPhone = "(123) 456-7890";
const cleanPhone = messyPhone.replace(/[^0-9]/g, "");
console.log(cleanPhone); // "1234567890"
```

### 4. search() - "Where is the first match?"

**Returns:** Position number or -1 if not found

```javascript
const text = "Find the word 'important' in this sentence";

console.log(text.search(/important/)); // 19 (position where 'important' starts)
console.log(text.search(/missing/)); // -1 (not found)

// Case insensitive search
console.log(text.search(/IMPORTANT/i)); // 19 (found because of 'i' flag)
```

### 5. split() - "Split text using pattern"

**Returns:** Array of pieces

```javascript
// Split on spaces
const sentence = "hello world javascript";
console.log(sentence.split(/\s/));
// Result: ['hello', 'world', 'javascript']

// Split on multiple delimiters
const data = "apple,banana;orange:grape";
console.log(data.split(/[,;:]/));
// Result: ['apple', 'banana', 'orange', 'grape']

// Split on multiple spaces
const messyText = "word1    word2     word3";
console.log(messyText.split(/\s+/));
// Result: ['word1', 'word2', 'word3']
```

### 6. exec() - "Give me detailed match info"

**Returns:** Detailed match object or null

```javascript
const datePattern = /(\d{4})-(\d{2})-(\d{2})/;
const text = "Today is 2023-12-25";

const result = datePattern.exec(text);
console.log(result[0]); // "2023-12-25" (full match)
console.log(result[1]); // "2023" (first group)
console.log(result[2]); // "12" (second group)
console.log(result[3]); // "25" (third group)
```

### 7. matchAll() - "Find all matches with details"

**Returns:** Iterator of detailed matches (need 'g' flag)

```javascript
const text = "Email john@gmail.com or mary@yahoo.com";
const emailPattern = /(\w+)@(\w+\.com)/g;

const matches = [...text.matchAll(emailPattern)];

matches.forEach((match) => {
  console.log(`Full email: ${match[0]}`);
  console.log(`Username: ${match[1]}`);
  console.log(`Domain: ${match[2]}`);
});
// Full email: john@gmail.com, Username: john, Domain: gmail.com
// Full email: mary@yahoo.com, Username: mary, Domain: yahoo.com
```

## Beginner-Friendly Real Examples

### Example 1: Find all numbers in text

```javascript
const text = "I bought 5 apples and 3 oranges for $8";
const numbers = text.match(/\d+/g);
console.log(numbers); // ['5', '3', '8']
```

### Example 2: Remove extra spaces

```javascript
const messyText = "hello    world     javascript";
const cleanText = messyText.replace(/\s+/g, " ");
console.log(cleanText); // "hello world javascript"
```

### Example 3: Check if string is all letters

```javascript
const onlyLetters = /^[a-zA-Z]+$/;
console.log(onlyLetters.test("hello")); // true
console.log(onlyLetters.test("hello123")); // false
```

### Example 4: Extract words from sentence

```javascript
const sentence = "Hello, world! How are you?";
const words = sentence.match(/\w+/g);
console.log(words); // ['Hello', 'world', 'How', 'are', 'you']
```

### Example 5: Simple email validation

```javascript
const simpleEmail = /\w+@\w+\.\w+/;
console.log(simpleEmail.test("user@gmail.com")); // true
console.log(simpleEmail.test("not-an-email")); // false
```

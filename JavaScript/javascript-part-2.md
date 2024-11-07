# Objects & Prototypes

## JavaScript Object Methods

In JavaScript, an object is a collection of key-value pairs. When a property holds a function, it’s called a **method**, and it usually defines the object’s behavior.

### Defining Object Methods

#### 1. Adding Methods Using Function Expressions

You can add a method by defining a function expression and assigning it to a property in an object:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};

person.greet = function () {
  console.log("Hello!");
};

person.greet(); // Output: Hello!
```

**Steps**:

1. Define a function and assign it to the `greet` property of the `person` object.
2. Call the `greet` method with `person.greet()`.

#### 2. Using a Standalone Function as a Method

Another way to add a method is by defining a standalone function and assigning it to an object property:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};

function greet() {
  console.log("Hello, World!");
}

person.greet = greet;
person.greet(); // Output: Hello, World!
```

**Steps**:

1. Define the `greet()` function separately.
2. Assign the function to `person.greet`.
3. Call `person.greet()` to execute the method.

### Object Method Syntax Variations

#### 1. Regular (Non-Concise) Syntax

You can define a method directly in an object literal using the standard function expression syntax:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
  greet: function () {
    console.log("Hello, World!");
  },
};

person.greet(); // Output: Hello, World!
```

#### 2. ES6 Concise Method Syntax

The ES6 shorthand method syntax allows for a cleaner definition of methods in an object:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
  greet() {
    console.log("Hello, World!");
  },
};

person.greet(); // Output: Hello, World!
```

This concise syntax is less verbose and achieves the same result.

### The `this` Keyword in Methods

Inside an object method, `this` refers to the object that invokes the method. This allows methods to access other properties within the same object.

For example, to create a method that returns a full name:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
  getFullName() {
    return this.firstName + " " + this.lastName;
  },
};

console.log(person.getFullName()); // Output: John Doe
```

**Explanation**:

- The `getFullName()` method uses `this` to access `firstName` and `lastName` of the `person` object.

### Summary

- An object’s method is a function stored in a property.
- Methods can be added using function expressions, standalone functions, or directly within an object literal.
- ES6 allows for concise syntax when defining methods in object literals.
- The `this` keyword in methods refers to the object that calls the method, giving access to its other properties.

**Note:**

---

In JavaScript and other programming languages, **methods** and **functions** are similar, but they have key distinctions based on their context and usage:

### 1. Definition and Context

- **Function**:

  - A function is a standalone block of code designed to perform a specific task.
  - It can exist independently and does not need to be associated with any object.
  - Functions are called by their name, followed by parentheses (e.g., `myFunction()`).

  ```javascript
  function greet() {
    console.log("Hello, World!");
  }
  greet(); // Output: Hello, World!
  ```

- **Method**:

  - A method is a function that is associated with an object.
  - It’s defined as a property of an object and is meant to represent a behavior of that object.
  - Methods are called using the object they belong to, followed by the method name (e.g., `object.method()`).

  ```javascript
  let person = {
    firstName: "John",
    greet: function () {
      console.log("Hello, " + this.firstName + "!");
    },
  };
  person.greet(); // Output: Hello, John!
  ```

## JavaScript Constructor Function

A **constructor function** in JavaScript is a regular function used to create multiple similar objects. This allows for a more flexible and efficient way of creating objects than using object literals.

### Introduction to Constructor Functions

Using object literals to create multiple similar objects can be repetitive:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};
```

A constructor function provides a reusable pattern for creating similar objects.

### Defining a Constructor Function

To define a custom object type, create a function with the following conventions:

- The name should start with a capital letter (e.g., `Person`, `Document`).
- It should be called with the `new` keyword.

```javascript
function Person(firstName, lastName) {
  this.firstName = firstName;
  this.lastName = lastName;
}
```

To create a new instance:

```javascript
let person = new Person("John", "Doe");
```

#### How `new` Works

When using `new Person('John', 'Doe')`, JavaScript:

1. Creates an empty object.
2. Binds `this` to that object.
3. Sets `firstName` and `lastName` properties on `this`.
4. Returns `this` (the new object).

This is equivalent to:

```javascript
let person = {
  firstName: "John",
  lastName: "Doe",
};
```

With the `Person` constructor, you can now create multiple instances:

```javascript
let person1 = new Person("Jane", "Doe");
let person2 = new Person("James", "Smith");
```

### Adding Methods to Constructor Functions

To add a method, use `this` within the constructor function:

```javascript
function Person(firstName, lastName) {
  this.firstName = firstName;
  this.lastName = lastName;

  this.getFullName = function () {
    return this.firstName + " " + this.lastName;
  };
}

let person = new Person("John", "Doe");
console.log(person.getFullName()); // Output: John Doe
```

**Note**: Defining methods this way adds a duplicate function to each instance, which is inefficient.

#### Optimizing with `prototype`

To avoid duplicating methods, define methods on the constructor’s `prototype` so all instances share the same function:

```javascript
Person.prototype.getFullName = function () {
  return this.firstName + " " + this.lastName;
};
```

### Returning from Constructor Functions

By default, constructor functions return `this`, but if a return statement is used:

- **If an object is returned**, it replaces `this`.
- **If a primitive value is returned**, it’s ignored.

### Avoiding Constructor Misuse

Calling a constructor without `new` binds `this` to the global object, leading to errors:

```javascript
let person = Person("John", "Doe"); // Incorrect usage
console.log(person.firstName); // Error: person is undefined
```

#### Enforcing `new` with `new.target`

To prevent a constructor function from being called without `new`, ES6 introduced `new.target`. It returns a reference to the function if it’s called with `new` and `undefined` if called without it.

```javascript
function Person(firstName, lastName) {
  if (!new.target) {
    throw Error("Cannot be called without the new keyword");
  }
  this.firstName = firstName;
  this.lastName = lastName;
}
```

Alternatively, make the syntax flexible by creating an instance automatically if `new` is missing:

```javascript
function Person(firstName, lastName) {
  if (!new.target) {
    return new Person(firstName, lastName);
  }
  this.firstName = firstName;
  this.lastName = lastName;
}

let person = Person("John", "Doe");
console.log(person.firstName); // Output: John
```

### Summary

- **Constructor functions** create multiple similar objects and are invoked with `new`.
- **Methods** can be added to each instance or optimized by using `prototype`.
- **`new.target`** helps enforce correct usage by ensuring the constructor is only called with `new`.
- **Flexible syntax** can be achieved by checking `new.target` and creating an instance if `new` is missing.

Constructor functions provide a powerful way to create and manage objects in JavaScript.

## JavaScript Prototype

### Introduction to JavaScript Prototype

In JavaScript, objects can inherit features from other objects through prototypes. Every object has a `[[Prototype]]` property (accessed with `__proto__`), creating a **prototype chain** that allows objects to access properties and methods higher up in the chain. The prototype chain ends when a prototype has `null` for its own prototype.

#### Example of Prototype Chain

Consider an object `person`:

```javascript
let person = { name: "John" };
```

If you inspect `person` in the console, you’ll see it has a `[[Prototype]]`, which refers to another object. This prototype also has its own prototype, creating a chain.

### Accessing Properties in the Prototype Chain

When accessing a property:

- JavaScript first checks if the property exists in the object itself.
- If not, it searches the object's prototype, then the prototype's prototype, continuing up the chain until it finds the property or reaches `null`.

```javascript
person.toString(); // Calls toString() from Object.prototype
```

In this case, `toString()` isn’t defined in `person`, so JavaScript finds it on `Object.prototype`.

### Defining Constructor Functions and Prototypes

JavaScript allows you to define "constructor functions" for creating similar objects. Each constructor has a `prototype` property pointing to an object with properties and methods shared by all instances.

```javascript
function Person(name) {
  this.name = name;
}
Person.prototype.greet = function () {
  return `Hi, I'm ${this.name}!`;
};
```

When creating a new `Person` instance:

```javascript
let p1 = new Person("John");
console.log(p1.greet()); // Hi, I'm John!
```

- The `greet` method is accessed through `p1`'s prototype chain, not directly on `p1`.

### Prototype Chain and Object Relationships

The prototype chain links objects:

- `Person.prototype` links to `Object.prototype`.
- `p1.__proto__` links to `Person.prototype`.

Using `Object.getPrototypeOf()` is preferred over `__proto__` for retrieving the prototype:

```javascript
console.log(Object.getPrototypeOf(p1) === Person.prototype); // true
```

### Shadowing in Prototype Chains

If an object has a property with the same name as one on its prototype, the object's property **shadows** the prototype's property.

```javascript
p1.greet = function () {
  return "Hello";
};
console.log(p1.greet()); // Hello (shadows Person.prototype.greet)
```

### Key Points

- `Object.prototype` contains common methods like `toString()` and `valueOf()`.
- The `Object.getPrototypeOf()` method retrieves an object’s prototype.
- **Shadowing** occurs when a property or method in an object overrides the same property/method in its prototype.

Using prototypes in JavaScript provides efficient inheritance by allowing objects to share methods, reducing memory use and improving performance.

For More Refer: [link](https://www.javascripttutorial.net/javascript-prototype/)

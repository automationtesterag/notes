# JavaScript & TypeScript Classes

---

# Table of Contents

1. What is a Class?
2. Classes Before ES6
3. ES6 Class Declaration
4. Constructor
5. Creating Objects
6. Methods
7. JavaScript Class Characteristics
8. JavaScript Class vs Custom Type
9. TypeScript Class

---

# 1. What is a Class?

A **class** is a blueprint used to create objects. It groups **data (properties)** and **functions (methods)** into a single unit, making the code easier to organize and reuse.

Although JavaScript introduced the `class` keyword in ES6, classes are **not a new object model**. They are simply a cleaner syntax (syntactic sugar) over JavaScript's existing **prototype-based inheritance**.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}
```

### TypeScript

```typescript
class Person {
  name: string;

  constructor(name: string) {
    this.name = name;
  }
}
```

> **TypeScript Difference:** TypeScript uses the same class syntax as JavaScript but allows you to define data types for properties and methods.

---

# 2. Classes Before ES6

Before ES6, JavaScript did not have the `class` keyword. Developers created "classes" using **constructor functions** and **prototypes**.

### JavaScript

```javascript
function Person(name) {
  this.name = name;
}

Person.prototype.getName = function () {
  return this.name;
};

let person = new Person("John");
console.log(person.getName());
```

### TypeScript (same concept with types)

```typescript
function Person(name: string) {
  this.name = name;
}

Person.prototype.getName = function (): string {
  return this.name;
};

let person = new Person("John");
```

> Constructor functions initialize object properties, while methods are added to the prototype so they are shared by all objects instead of being created repeatedly.

---

# 3. ES6 Class Declaration

ES6 introduced the `class` keyword, which provides a cleaner and more readable way to create objects. Internally, it still uses prototype-based inheritance.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }

  getName() {
    return this.name;
  }
}
```

### TypeScript

```typescript
class Person {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  getName(): string {
    return this.name;
  }
}
```

> The behavior is the same as constructor functions, but the syntax is simpler and easier to understand.

---

# 4. Constructor

A **constructor** is a special method that runs automatically whenever an object is created using the `new` keyword. It is mainly used to initialize the object's properties.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}

let person = new Person("John");
```

### TypeScript

```typescript
class Person {
  name: string;

  constructor(name: string) {
    this.name = name;
  }
}

let person = new Person("John");
```

---

# 5. Creating Objects

Objects are created using the `new` keyword. Creating an object automatically calls the constructor.

### JavaScript

```javascript
let person = new Person("John");
```

### TypeScript

```typescript
let person = new Person("John");
```

---

# 6. Methods

Methods are functions defined inside a class. They describe the behavior of objects created from that class.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }

  getName() {
    return this.name;
  }
}

let person = new Person("John");
console.log(person.getName());
```

### TypeScript

```typescript
class Person {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  getName(): string {
    return this.name;
  }
}

let person = new Person("John");
console.log(person.getName());
```

> The only difference is that TypeScript allows specifying the return type of the method.

---

# 7. JavaScript Class Characteristics

## 1. Classes are Functions

Even though classes have a different syntax, JavaScript treats them as functions internally.

### JavaScript

```javascript
class Person {}

console.log(typeof Person);
```

Output

```
function
```

### TypeScript

```typescript
class Person {}

console.log(typeof Person);
```

Output

```
function
```

---

## 2. Objects are Instances of the Class

The `instanceof` operator checks whether an object was created from a specific class.

### JavaScript

```javascript
class Person {}

let person = new Person();

console.log(person instanceof Person);
console.log(person instanceof Object);
```

### TypeScript

```typescript
class Person {}

let person = new Person();

console.log(person instanceof Person);
console.log(person instanceof Object);
```

---

# 8. JavaScript Class vs Custom Type

### 1. Class declarations are not hoisted

Unlike function declarations, classes must be declared before they are used.

### JavaScript

```javascript
let person = new Person(); // Error

class Person {}
```

### TypeScript

```typescript
let person = new Person(); // Error

class Person {}
```

---

### 2. Classes always run in Strict Mode

All code inside a class automatically runs in strict mode. You don't need to write `"use strict"` manually.

### JavaScript

```javascript
class Person {
  constructor() {}
}
```

### TypeScript

```typescript
class Person {
  constructor() {}
}
```

---

### 3. Class methods are Non-enumerable

Methods defined inside a class are automatically non-enumerable, meaning they won't appear when iterating through object properties.

### JavaScript

```javascript
class Person {
  getName() {}
}
```

### TypeScript

```typescript
class Person {
  getName(): void {}
}
```

---

### 4. Classes must be called using `new`

A class constructor cannot be called like a normal function.

### JavaScript

```javascript
class Person {}

let person = Person(); // Error
```

Correct

```javascript
let person = new Person();
```

### TypeScript

```typescript
class Person {}

let person = Person(); // Error
```

Correct

```typescript
let person = new Person();
```

---

# 9. TypeScript Class

TypeScript uses the same class syntax as JavaScript but adds **type annotations** to properties, constructor parameters, and methods. This helps detect type-related errors during compilation instead of at runtime.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }

  getName() {
    return this.name;
  }
}
```

### TypeScript

```typescript
class Person {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  getName(): string {
    return this.name;
  }
}
```

### Compile-time Type Checking

**JavaScript**

```javascript
let person = new Person(123);
```

â Allowed (no type checking)

**TypeScript**

```typescript
let person = new Person(123);
```

â Compile-time Error because the constructor expects a `string`.

> TypeScript performs type checking during compilation, making the code more reliable and reducing runtime errors.

---

You're right. For this topic, **JavaScript does not have access modifiers** (`private`, `protected`, `public`) like TypeScript (at least in the context of the content you shared). So the notes should compare **how JavaScript behaves** versus **how TypeScript improves it**, just like the previous notes.

---

# TypeScript Access Modifiers (JavaScript + TypeScript)

---

# Table of Contents

1. What are Access Modifiers?
2. Private Modifier
3. Public Modifier
4. Protected Modifier
5. Short-hand Property Declaration
6. JavaScript vs TypeScript
7. Summary

---

# 1. What are Access Modifiers?

**Access modifiers** control the visibility of class properties and methods.

Unlike TypeScript, **JavaScript does not support `public`, `private`, or `protected` access modifiers** (in the traditional TypeScript sense). By default, all class members are publicly accessible.

TypeScript adds these modifiers and checks them during **compile time**.

| JavaScript                 | TypeScript                                |
| -------------------------- | ----------------------------------------- |
| No access modifiers        | Supports `private`, `protected`, `public` |
| All members are accessible | Visibility can be controlled              |
| No compile-time checking   | Compile-time access checking              |

---

# 2. Private Modifier

The **private** modifier allows a property or method to be accessed **only within the same class**.

### JavaScript

JavaScript has **no `private` modifier** (based on the topic covered).

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}

let person = new Person("John");

console.log(person.name); // Accessible
```

### TypeScript

```typescript
class Person {
  constructor(private name: string) {}

  getName(): string {
    return this.name;
  }
}

let person = new Person("John");

console.log(person.name); // Compile Error
```

> **Difference:** JavaScript allows direct access, whereas TypeScript prevents access outside the class during compilation.

---

# 3. Public Modifier

The **public** modifier allows properties and methods to be accessed from anywhere.

In TypeScript, if no modifier is specified, it is automatically **public**.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}

let person = new Person("John");

console.log(person.name);
```

### TypeScript

```typescript
class Person {
  constructor(public name: string) {}
}

let person = new Person("John");

console.log(person.name);
```

> **Difference:** JavaScript members are public by default. TypeScript also uses `public` by default if no modifier is specified.

---

# 4. Protected Modifier

The **protected** modifier allows access only:

- Inside the same class
- Inside child (derived) classes

It cannot be accessed from outside the class.

### JavaScript

JavaScript has **no `protected` modifier**.

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}

class Employee extends Person {}

let emp = new Employee("John");

console.log(emp.name); // Accessible
```

### TypeScript

```typescript
class Person {
  constructor(protected name: string) {}
}

class Employee extends Person {
  display() {
    return this.name;
  }
}

let emp = new Employee("John");

console.log(emp.name); // Compile Error
```

> **Difference:** JavaScript allows direct access from outside, while TypeScript restricts it to the class and its subclasses.

---

# 5. Short-hand Property Declaration

TypeScript provides a shorter way to declare and initialize properties.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
}
```

### TypeScript (Normal)

```typescript
class Person {
  private name: string;

  constructor(name: string) {
    this.name = name;
  }
}
```

### TypeScript (Short-hand)

```typescript
class Person {
  constructor(private name: string) {}
}
```

> **Difference:** JavaScript requires manual property assignment. TypeScript constructor shorthand automatically declares and initializes the property.

---

# 6. JavaScript vs TypeScript

| Feature               | JavaScript                             | TypeScript        |
| --------------------- | -------------------------------------- | ----------------- |
| `private`             | â                                     | â                |
| `protected`           | â                                     | â                |
| `public`              | â (all members are public by default) | â                |
| Access checking       | â                                     | â (Compile time) |
| Constructor shorthand | â                                     | â                |

---

# 7. Summary

- **JavaScript** does not provide `private`, `protected`, or `public` access modifiers in the context of these notes.
- By default, **all JavaScript class members are publicly accessible**.
- **TypeScript** introduces `private`, `protected`, and `public` to control access to class members.
- `private` â Accessible only within the same class.
- `protected` â Accessible within the class and its child classes.
- `public` â Accessible from anywhere.
- If no modifier is specified, TypeScript automatically uses `public`.
- TypeScript also provides **constructor shorthand** for declaring and initializing properties together.
- Access modifiers are checked only during **compile time**, not at runtime.

# TypeScript `readonly` (JavaScript + TypeScript)

---

# Table of Contents

1. What is `readonly`?
2. Declaring a `readonly` Property
3. Initializing a `readonly` Property
4. Reassigning a `readonly` Property
5. Constructor Shorthand
6. `readonly` vs `const`
7. Summary

---

# 1. What is `readonly`?

The **`readonly`** modifier makes a **class property immutable**, meaning its value **cannot be changed after it is initialized**.

Unlike TypeScript, **JavaScript does not have a `readonly` modifier** for class properties.

TypeScript checks `readonly` only during **compile time**.

| JavaScript                 | TypeScript                                         |
| -------------------------- | -------------------------------------------------- |
| No `readonly` modifier     | Supports `readonly`                                |
| Properties can be modified | Properties cannot be modified after initialization |
| No compile-time checking   | Compile-time checking                              |

---

# 2. Declaring a `readonly` Property

A `readonly` property can be assigned **only once**.

### JavaScript

JavaScript has no `readonly` keyword.

```javascript
class Person {
  constructor(birthDate) {
    this.birthDate = birthDate;
  }
}
```

### TypeScript

```typescript
class Person {
  readonly birthDate: Date;

  constructor(birthDate: Date) {
    this.birthDate = birthDate;
  }
}
```

> **Difference:** JavaScript allows the property to be modified later, whereas TypeScript marks it as immutable.

---

# 3. Initializing a `readonly` Property

A `readonly` property can be initialized in **only two places**:

- During property declaration.
- Inside the constructor of the same class.

### TypeScript

**Property Declaration**

```typescript
class Person {
  readonly birthDate: Date = new Date(1990, 12, 25);
}
```

**Constructor Initialization**

```typescript
class Person {
  readonly birthDate: Date;

  constructor(birthDate: Date) {
    this.birthDate = birthDate;
  }
}
```

> After initialization, the property cannot be assigned a new value.

---

# 4. Reassigning a `readonly` Property

Trying to change a `readonly` property results in a compile-time error.

### JavaScript

```javascript
class Person {
  constructor(birthDate) {
    this.birthDate = birthDate;
  }
}

let person = new Person(new Date());

person.birthDate = new Date(); // Allowed
```

### TypeScript

```typescript
class Person {
  constructor(readonly birthDate: Date) {}
}

let person = new Person(new Date());

person.birthDate = new Date(); // Compile Error
```

**Compile Error**

> Cannot assign to 'birthDate' because it is a read-only property.

---

# 5. Constructor Shorthand

Like access modifiers, TypeScript allows you to declare and initialize a `readonly` property directly in the constructor.

### JavaScript

```javascript
class Person {
  constructor(birthDate) {
    this.birthDate = birthDate;
  }
}
```

### TypeScript (Normal)

```typescript
class Person {
  readonly birthDate: Date;

  constructor(birthDate: Date) {
    this.birthDate = birthDate;
  }
}
```

### TypeScript (Short-hand)

```typescript
class Person {
  constructor(readonly birthDate: Date) {}
}
```

> The shorthand syntax automatically declares and initializes the property.

---

# 6. `readonly` vs `const`

| Feature           | `readonly`                          | `const`                   |
| ----------------- | ----------------------------------- | ------------------------- |
| Used for          | Class properties                    | Variables                 |
| Where initialized | Property declaration or constructor | Variable declaration only |
| Can be reassigned | â                                  | â                        |

### `readonly` Example

```typescript
class Person {
  constructor(readonly birthDate: Date) {}
}
```

### `const` Example

```typescript
const age = 25;

// age = 30; â Error
```

> **Difference:** Use `readonly` for **class properties** and `const` for **variables**.

---

# JavaScript & TypeScript Getters and Setters (Short Notes)

---

# Table of Contents

1. What are Getters and Setters?
2. Traditional Getter and Setter Methods
3. ES6 `get` and `set` Keywords
4. Using Getter and Setter
5. Getter Without Setter
6. Getter in Object Literal (JavaScript)
7. TypeScript Getters and Setters
8. More TypeScript Example (`fullName`)
9. Summary

---

# 1. What are Getters and Setters?

**Getters** and **Setters** are special methods used to **control access to class properties**.

- **Getter** â Returns the value of a property (**Accessor**).
- **Setter** â Updates the value of a property after performing validation (**Mutator**).

Instead of accessing properties directly, getters and setters allow you to add validation or business logic before reading or updating data.

---

# 2. Traditional Getter and Setter Methods

Before ES6, developers created normal methods like `getName()` and `setName()`.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.setName(name);
  }

  getName() {
    return this.name;
  }

  setName(name) {
    this.name = name.trim();
  }
}

let person = new Person("John");

console.log(person.getName());

person.setName("Jane");
```

### TypeScript

```typescript
class Person {
  private name: string;

  constructor(name: string) {
    this.setName(name);
  }

  getName(): string {
    return this.name;
  }

  setName(name: string): void {
    this.name = name.trim();
  }
}
```

> These are normal methods and must be called using parentheses (`getName()` and `setName()`).

---

# 3. ES6 `get` and `set` Keywords

ES6 introduced the `get` and `set` keywords, allowing methods to behave like properties.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this._name = name;
  }

  get name() {
    return this._name;
  }

  set name(value) {
    this._name = value.trim();
  }
}
```

### TypeScript

```typescript
class Person {
  private _name: string;

  constructor(name: string) {
    this._name = name;
  }

  public get name(): string {
    return this._name;
  }

  public set name(value: string) {
    this._name = value.trim();
  }
}
```

> Notice the use of `_name`. It avoids a naming conflict between the property and the getter/setter.

---

# 4. Using Getter and Setter

Getters and setters are accessed like normal properties (without parentheses).

### JavaScript

```javascript
let person = new Person("John");

console.log(person.name); // Getter

person.name = "Jane"; // Setter
```

### TypeScript

```typescript
let person = new Person("John");

console.log(person.name);

person.name = "Jane";
```

> Although it looks like a property access, JavaScript/TypeScript automatically calls the getter or setter method.

---

# 5. Getter Without Setter

If a class has only a getter, the property becomes **read-only**.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this._name = name;
  }

  get name() {
    return this._name;
  }
}

let person = new Person("John");

person.name = "Jane";

console.log(person.name);
```

Output

```text
John
```

### TypeScript

```typescript
class Person {
  constructor(private _name: string) {}

  get name(): string {
    return this._name;
  }
}

let person = new Person("John");

// person.name = "Jane"; // Compile Error
```

> Without a setter, the value cannot be updated through the property.

---

# 6. Getter in Object Literal (JavaScript)

Getters are not limited to classes; they can also be used inside object literals.

### JavaScript

```javascript
let meeting = {
  attendees: [],

  add(name) {
    this.attendees.push(name);
  },

  get latest() {
    return this.attendees[this.attendees.length - 1];
  },
};

meeting.add("John");
meeting.add("Jane");

console.log(meeting.latest);
```

### TypeScript

```typescript
let meeting = {
  attendees: [] as string[],

  add(name: string) {
    this.attendees.push(name);
  },

  get latest(): string {
    return this.attendees[this.attendees.length - 1];
  },
};
```

> This allows computed properties in plain objects without defining a class.

---

# 7. TypeScript Getters and Setters

TypeScript uses getters and setters to **validate data before assigning values**.

### JavaScript

```javascript
class Person {
  constructor(age) {
    this._age = age;
  }

  get age() {
    return this._age;
  }

  set age(value) {
    if (value <= 0 || value >= 200) {
      throw "Invalid age";
    }

    this._age = value;
  }
}
```

### TypeScript

```typescript
class Person {
  constructor(private _age: number) {}

  public get age(): number {
    return this._age;
  }

  public set age(value: number) {
    if (value <= 0 || value >= 200) {
      throw new Error("Invalid age");
    }

    this._age = value;
  }
}
```

Using the setter

```typescript
let person = new Person(22);

person.age = 23;

console.log(person.age);
```

> Validation is performed automatically whenever the property is updated.

---

# 8. More TypeScript Example (`fullName`)

Getters and setters can also perform more complex operations.

### TypeScript

```typescript
class Person {
  constructor(
    private _firstName: string,
    private _lastName: string,
  ) {}

  get fullName(): string {
    return `${this._firstName} ${this._lastName}`;
  }

  set fullName(name: string) {
    const parts = name.split(" ");

    this._firstName = parts[0];
    this._lastName = parts[1];
  }
}
```

Using the getter and setter

```typescript
let person = new Person("John", "Doe");

person.fullName = "Jane Smith";

console.log(person.fullName);
```

Output

```text
Jane Smith
```

> The setter updates multiple properties (`firstName` and `lastName`) from a single value.

---

# 9. Summary

- **Getters** return the value of a property (**Accessor**).
- **Setters** update the value of a property (**Mutator**).
- Before ES6, getters and setters were implemented using normal methods like `getName()` and `setName()`.
- ES6 introduced the `get` and `set` keywords, allowing properties to behave like methods.
- Getters and setters are accessed **without parentheses**.
- A getter without a setter makes the property effectively **read-only**.
- JavaScript supports getters and setters in both **classes** and **object literals**.
- TypeScript uses getters and setters to **validate data**, **protect private properties**, and **encapsulate business logic** before reading or updating values.

# JavaScript Class Expressions (JavaScript + TypeScript)

> **Note:** TypeScript uses the same class expression syntax as JavaScript. The main difference is that TypeScript allows adding type annotations.

---

# Table of Contents

1. What is a Class Expression?
2. Creating a Class Expression
3. Creating Objects
4. `typeof` with Class Expressions
5. Hoisting
6. First-Class Citizen
7. Singleton using Class Expression
8. Summary

---

# 1. What is a Class Expression?

A **class expression** is an alternative way to define a class.

Unlike a class declaration, a class expression is usually **assigned to a variable**. It can be **named or unnamed**.

Just like function expressions, **class expressions are not hoisted**.

---

# 2. Creating a Class Expression

A class expression can be assigned directly to a variable.

### JavaScript

```javascript
let Person = class {
  constructor(name) {
    this.name = name;
  }

  getName() {
    return this.name;
  }
};
```

### TypeScript

```typescript
let Person = class {
  constructor(private name: string) {}

  getName(): string {
    return this.name;
  }
};
```

> **Difference:** TypeScript adds type annotations, but the syntax remains the same.

---

# 3. Creating Objects

Objects are created from a class expression in the same way as a normal class.

### JavaScript

```javascript
let person = new Person("John");

console.log(person.getName());
```

### TypeScript

```typescript
let person = new Person("John");

console.log(person.getName());
```

---

# 4. `typeof` with Class Expressions

Like class declarations, **class expressions are also functions internally**.

### JavaScript

```javascript
console.log(typeof Person);
```

Output

```text
function
```

### TypeScript

```typescript
console.log(typeof Person);
```

Output

```text
function
```

---

# 5. Hoisting

Class expressions are **not hoisted**.

They must be declared before they are used.

### JavaScript

```javascript
let person = new Person(); // Error

let Person = class {};
```

### TypeScript

```typescript
let person = new Person(); // Error

let Person = class {};
```

> Trying to create an object before defining the class expression results in an error.

---

# 6. First-Class Citizen

JavaScript classes are **first-class citizens**, meaning they can be:

- Assigned to variables
- Passed as function arguments
- Returned from functions

### JavaScript

```javascript
function factory(aClass) {
  return new aClass();
}

let greeting = factory(
  class {
    sayHi() {
      console.log("Hi");
    }
  },
);

greeting.sayHi();
```

### TypeScript

```typescript
function factory(aClass: any) {
  return new aClass();
}

let greeting = factory(
  class {
    sayHi(): void {
      console.log("Hi");
    }
  },
);

greeting.sayHi();
```

> Here, an unnamed class expression is passed as an argument to a function.

---

# 7. Singleton using Class Expression

A **Singleton** is a design pattern that allows **only one object** of a class to exist.

A class expression can be immediately instantiated using the `new` operator.

### JavaScript

```javascript
let app = new (class {
  constructor(name) {
    this.name = name;
  }

  start() {
    console.log(`Starting ${this.name}`);
  }
})("Awesome App");

app.start();
```

### TypeScript

```typescript
let app = new (class {
  constructor(private name: string) {}

  start(): void {
    console.log(`Starting ${this.name}`);
  }
})("Awesome App");

app.start();
```

> The class is created and its constructor is called immediately, producing a single object.

---

# 8. Summary

- A **class expression** is another way to define a class.
- It can be **named or unnamed**.
- Class expressions are usually **assigned to variables**.
- Objects are created using the **`new`** keyword, just like class declarations.
- `typeof` a class expression returns **`"function"`**.
- Class expressions are **not hoisted**.
- Classes are **first-class citizens**, meaning they can be passed to functions, returned from functions, or assigned to variables.
- Class expressions can be used to implement the **Singleton** design pattern by creating an object immediately after the class definition.
- TypeScript uses the same syntax as JavaScript, with the addition of **type annotations**.

# JavaScript Computed Properties (JavaScript + TypeScript)

> **Note:** TypeScript supports **computed properties** because they are an ES6 JavaScript feature. TypeScript simply adds type annotations where needed.

---

# Table of Contents

1. What are Computed Properties?
2. Basic Computed Property
3. Computed Property in a Class
4. Creating an Object from a Key/Value Pair
5. Summary

---

# 1. What are Computed Properties?

**Computed properties** allow you to **create object property names dynamically** using an expression inside square brackets `[]`.

Instead of hardcoding the property name, JavaScript evaluates the expression inside `[]` and uses its value as the property name.

### Syntax

### JavaScript

```javascript
let propertyName = "name";

const obj = {
  [propertyName]: "John",
};
```

### TypeScript

```typescript
let propertyName: string = "name";

const obj = {
  [propertyName]: "John",
};
```

> The expression inside `[]` is evaluated first, and its result becomes the property name.

---

# 2. Basic Computed Property

A variable can be used to generate a property name dynamically.

### JavaScript

```javascript
let propName = "c";

const rank = {
  a: 1,
  b: 2,
  [propName]: 3,
};

console.log(rank.c);
```

Output

```text
3
```

### TypeScript

```typescript
let propName: string = "c";

const rank = {
  a: 1,
  b: 2,
  [propName]: 3,
};

console.log(rank.c);
```

> Instead of creating a property named `propName`, JavaScript evaluates its value (`"c"`) and creates the property `c`.

---

# 3. Computed Property in a Class

Computed properties can also be used with **getters** and **setters** inside a class.

### JavaScript

```javascript
const property = "fullName";

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  get [property]() {
    return `${this.firstName} ${this.lastName}`;
  }
}

let person = new Person("John", "Doe");

console.log(person.fullName);
```

Output

```text
John Doe
```

### TypeScript

```typescript
const property: string = "fullName";

class Person {
  constructor(
    private firstName: string,
    private lastName: string,
  ) {}

  get [property](): string {
    return `${this.firstName} ${this.lastName}`;
  }
}

let person = new Person("John", "Doe");

console.log(person.fullName);
```

> Here, the getter name is created dynamically using the value of the `property` variable.

---

# 4. Creating an Object from a Key/Value Pair

Computed properties make it easy to create objects with dynamic property names.

### JavaScript

```javascript
const createObject = (key, value) => {
  return {
    [key]: value,
  };
};

const person = createObject("name", "John");

console.log(person);
```

Output

```text
{ name: "John" }
```

### TypeScript

```typescript
const createObject = (key: string, value: string) => {
  return {
    [key]: value,
  };
};

const person = createObject("name", "John");

console.log(person);
```

### Without Computed Property

### JavaScript

```javascript
const createObject = (key, value) => {
  let obj = {};

  obj[key] = value;

  return obj;
};
```

### TypeScript

```typescript
const createObject = (key: string, value: string) => {
  let obj: Record<string, string> = {};

  obj[key] = value;

  return obj;
};
```

> Computed properties provide a shorter and cleaner way to create objects with dynamic property names.

---

# 5. Summary

- **Computed properties** allow you to create property names dynamically using expressions inside `[]`.
- The expression inside `[]` is evaluated, and its result becomes the property name.
- Computed properties can be used in **object literals** and **classes** (including getters and setters).
- They are commonly used when property names are determined at runtime.
- TypeScript supports computed properties just like JavaScript, with the addition of **type annotations** where needed.

# JavaScript & TypeScript Inheritance (`extends` & `super`)

---

# Table of Contents

1. What is Inheritance?
2. Inheritance Before ES6
3. Inheritance using `extends`
4. `super()` Constructor
5. Child Class without Constructor
6. `super()` is Mandatory
7. Calling Parent Methods (`super.method()`)
8. Static Member Inheritance
9. Inheriting Built-in Types
10. Summary

---

# 1. What is Inheritance?

**Inheritance** allows one class to **reuse the properties and methods** of another class instead of rewriting them.

- **Parent (Base) Class** â Class being inherited.
- **Child (Derived) Class** â Class that inherits from the parent.

Inheritance promotes **code reusability** and **reduces duplication**.

---

# 2. Inheritance Before ES6

Before ES6, JavaScript used **constructor functions and prototypes** to implement inheritance.

### JavaScript

```javascript
function Animal(legs) {
  this.legs = legs;
}

Animal.prototype.walk = function () {
  console.log("Walking");
};

function Bird(legs) {
  Animal.call(this, legs);
}

Bird.prototype = Object.create(Animal.prototype);

Bird.prototype.fly = function () {
  console.log("Flying");
};
```

### TypeScript

> TypeScript doesn't use constructor/prototype syntax. It uses the ES6 class syntax.

> **ES6 introduced `extends` and `super`, making inheritance much simpler and easier to read.**

---

# 3. Inheritance using `extends`

The **`extends`** keyword allows a child class to inherit all the properties and methods of the parent class.

### JavaScript

```javascript
class Animal {
  constructor(legs) {
    this.legs = legs;
  }

  walk() {
    console.log("Walking");
  }
}

class Bird extends Animal {
  fly() {
    console.log("Flying");
  }
}
```

### TypeScript

```typescript
class Animal {
  constructor(public legs: number) {}

  walk(): void {
    console.log("Walking");
  }
}

class Bird extends Animal {
  fly(): void {
    console.log("Flying");
  }
}
```

> **Difference:** TypeScript adds type annotations, while the inheritance syntax is the same.

---

# 4. `super()` Constructor

The **`super()`** function calls the constructor of the parent class.

If the child class has its own constructor, it **must call `super()` first**.

### JavaScript

```javascript
class Animal {
  constructor(legs) {
    this.legs = legs;
  }
}

class Bird extends Animal {
  constructor(legs) {
    super(legs);
  }
}
```

### TypeScript

```typescript
class Animal {
  constructor(public legs: number) {}
}

class Bird extends Animal {
  constructor(legs: number) {
    super(legs);
  }
}
```

> `super()` initializes the parent class before the child class uses `this`.

---

# 5. Child Class without Constructor

If the child class **doesn't define a constructor**, JavaScript automatically calls the parent constructor.

### JavaScript

```javascript
class Bird extends Animal {
  fly() {
    console.log("Flying");
  }
}
```

### TypeScript

```typescript
class Bird extends Animal {
  fly(): void {
    console.log("Flying");
  }
}
```

> An implicit constructor is added automatically.

---

# 6. `super()` is Mandatory

If a child class has a constructor, it **must call `super()` before using `this`**.

### JavaScript

```javascript
class Bird extends Animal {
  constructor(legs) {
    this.legs = legs; // Error
  }
}
```

### TypeScript

```typescript
class Bird extends Animal {
  constructor(legs: number) {
    this.legs = legs; // Compile Error
  }
}
```

**Error**

```text
Must call super constructor before accessing 'this'
```

Correct

### JavaScript

```javascript
constructor(legs) {

    super(legs);

    this.color = "White";
}
```

### TypeScript

```typescript
constructor(legs: number) {

    super(legs);

    this.color = "White";
}
```

---

# 7. Calling Parent Methods (`super.method()`)

A child class can **override** a parent method.

If needed, it can still call the parent's implementation using `super.method()`.

### JavaScript

```javascript
class Animal {
  walk() {
    console.log("Walking");
  }
}

class Dog extends Animal {
  walk() {
    super.walk();

    console.log("Dog Walking");
  }
}
```

### TypeScript

```typescript
class Animal {
  walk(): void {
    console.log("Walking");
  }
}

class Dog extends Animal {
  walk(): void {
    super.walk();

    console.log("Dog Walking");
  }
}
```

> This is called **Method Overriding**.

---

# 8. Static Member Inheritance

A child class also inherits **static methods** of the parent class.

### JavaScript

```javascript
class Animal {
  static hello() {
    console.log("Hello");
  }
}

class Bird extends Animal {}

Bird.hello();
```

### TypeScript

```typescript
class Animal {
  static hello(): void {
    console.log("Hello");
  }
}

class Bird extends Animal {}

Bird.hello();
```

---

# 9. Inheriting Built-in Types

JavaScript allows inheritance from built-in classes like **Array**, **Map**, **Set**, and **String**.

### JavaScript

```javascript
class Queue extends Array {
  enqueue(item) {
    this.push(item);
  }

  dequeue() {
    return this.shift();
  }
}
```

### TypeScript

```typescript
class Queue<T> extends Array<T> {
  enqueue(item: T): void {
    this.push(item);
  }

  dequeue(): T | undefined {
    return this.shift();
  }
}
```

> Here, `Queue` inherits all the methods of `Array` and also adds its own methods.

---

# 10. Summary

- **Inheritance** allows one class to reuse the properties and methods of another class.
- Use **`extends`** to create a child class from a parent class.
- Use **`super()`** to call the parent class constructor.
- If a child class has a constructor, calling **`super()` is mandatory** before using `this`.
- Use **`super.method()`** to call a parent class method from the child class.
- Child classes inherit **static methods** from the parent class.
- JavaScript and TypeScript also allow extending built-in classes like **Array**, **Map**, and **Set**.
- TypeScript follows the same inheritance syntax as JavaScript, with the addition of **type annotations**.

# JavaScript & TypeScript Static Methods and Static Properties

---

# Table of Contents

1. What are Static Members?
2. Static Methods
3. Calling Static Methods
4. Calling Static Methods Inside a Class
5. Static Properties
6. Accessing Static Properties
7. Static Property in Constructor
8. JavaScript vs TypeScript
9. Summary

---

# 1. What are Static Members?

**Static members** (static methods and static properties) belong to the **class itself**, **not to the objects (instances)** of that class.

They are commonly used for:

- Helper methods
- Utility methods
- Shared data across all objects

---

# 2. Static Methods

A **static method** belongs to the class and can be called **without creating an object**.

### JavaScript (Before ES6)

```javascript
function Person(name) {
  this.name = name;
}

Person.createAnonymous = function (gender) {
  let name = gender === "male" ? "John Doe" : "Jane Doe";
  return new Person(name);
};
```

### JavaScript (ES6)

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }

  static createAnonymous(gender) {
    let name = gender === "male" ? "John Doe" : "Jane Doe";

    return new Person(name);
  }
}
```

### TypeScript

```typescript
class Person {
  constructor(private name: string) {}

  static createAnonymous(gender: string): Person {
    let name = gender === "male" ? "John Doe" : "Jane Doe";

    return new Person(name);
  }
}
```

> **Difference:** TypeScript uses the same syntax but adds type annotations.

---

# 3. Calling Static Methods

Static methods are called using the **class name**, not an object.

### JavaScript

```javascript
let person = Person.createAnonymous("male");
```

### TypeScript

```typescript
let person = Person.createAnonymous("male");
```

â Incorrect

### JavaScript

```javascript
let person = new Person("John");

person.createAnonymous("male");
```

### TypeScript

```typescript
let person = new Person("John");

person.createAnonymous("male");
```

**Error**

```text
TypeError:
person.createAnonymous is not a function
```

> Static methods belong to the class, not its instances.

---

# 4. Calling Static Methods Inside a Class

A static method can be called inside a constructor or instance method using:

- `ClassName.staticMethod()`
- `this.constructor.staticMethod()`

### JavaScript

```javascript
class Person {
  static display() {
    console.log("Hello");
  }

  constructor() {
    Person.display();
  }
}
```

### TypeScript

```typescript
class Person {
  static display(): void {
    console.log("Hello");
  }

  constructor() {
    Person.display();
  }
}
```

> Both `Person.display()` and `this.constructor.display()` can be used.

---

# 5. Static Properties

A **static property** is shared by **all objects** of the class.

### JavaScript

```javascript
class Employee {
  static count = 0;
}
```

### TypeScript

```typescript
class Employee {
  static count: number = 0;
}
```

Unlike normal properties, only **one copy** of a static property exists for the entire class.

---

# 6. Accessing Static Properties

Static properties are accessed using the **class name**.

### JavaScript

```javascript
console.log(Employee.count);
```

### TypeScript

```typescript
console.log(Employee.count);
```

They can also be accessed inside static methods.

### JavaScript

```javascript
class Employee {
  static count = 0;

  static getCount() {
    return Employee.count;
  }
}
```

### TypeScript

```typescript
class Employee {
  static count: number = 0;

  static getCount(): number {
    return Employee.count;
  }
}
```

---

# 7. Static Property in Constructor

A static property can be updated whenever a new object is created.

### JavaScript

```javascript
class Employee {
  static count = 0;

  constructor() {
    this.constructor.count++;
  }
}

new Employee();
new Employee();

console.log(Employee.count);
```

Output

```text
2
```

### TypeScript

```typescript
class Employee {
  static count: number = 0;

  constructor() {
    Employee.count++;
  }
}

new Employee();
new Employee();

console.log(Employee.count);
```

Output

```text
2
```

> Since `count` is shared by all objects, every new object increases the same value.

---

# 8. JavaScript vs TypeScript

| Feature                             | JavaScript | TypeScript |
| ----------------------------------- | ---------- | ---------- |
| Static Methods                      | â         | â         |
| Static Properties                   | â         | â         |
| Called using Class Name             | â         | â         |
| Accessible through Object           | â         | â         |
| Type Annotations                    | â         | â         |
| Access Modifiers (`private static`) | â         | â         |

---

# 9. Summary

- **Static methods** belong to the **class**, not to objects.
- **Static properties** are shared among all instances of a class.
- Use the **`static`** keyword to declare a static method or property.
- Call static methods and properties using the **class name** (`ClassName.method()` or `ClassName.property`).
- Static members **cannot be accessed through an object instance**.
- Inside a class, static members can be accessed using `ClassName.member` or `this.constructor.member`.
- TypeScript uses the same syntax as JavaScript and additionally supports **type annotations** and **access modifiers** such as `private static`.

# TypeScript Abstract Classes

> **Note:** **Abstract Classes are a TypeScript feature.** JavaScript does **not** support the `abstract` keyword directly. In JavaScript, similar behavior is achieved using conventions or runtime checks.

---

# Table of Contents

1. What is an Abstract Class?
2. Declaring an Abstract Class
3. Abstract Methods
4. Creating Objects
5. Implementing an Abstract Class
6. Abstract Class vs Regular Class
7. Summary

---

# 1. What is an Abstract Class?

An **Abstract Class** is a special class that is used as a **base class** for other classes.

- It **cannot be instantiated** (you cannot create objects from it).
- It provides **common properties and methods** for derived classes.
- It may contain both **implemented methods** and **abstract methods**.

### JavaScript

> JavaScript does **not** have abstract classes or the `abstract` keyword.

### TypeScript

```typescript
abstract class Employee {}
```

> Abstract classes are mainly used to define a common structure that child classes must follow.

---

# 2. Declaring an Abstract Class

An abstract class is declared using the **`abstract`** keyword.

### JavaScript

> JavaScript has no built-in support for abstract classes.

### TypeScript

```typescript
abstract class Employee {
  constructor(
    protected firstName: string,
    protected lastName: string,
  ) {}

  get fullName(): string {
    return `${this.firstName} ${this.lastName}`;
  }
}
```

> An abstract class can contain constructors, properties, getters, setters, and normal methods.

---

# 3. Abstract Methods

An **abstract method** has **only a declaration** and **no implementation**.

Every child class **must implement** the abstract method.

### JavaScript

> JavaScript doesn't support abstract methods directly.

### TypeScript

```typescript
abstract class Employee {
  abstract getSalary(): number;

  get fullName(): string {
    return "Employee";
  }
}
```

> Abstract methods define **what** should be done, while child classes define **how** it should be done.

---

# 4. Creating Objects

You **cannot create an object** of an abstract class.

### JavaScript

> Not applicable because JavaScript doesn't support abstract classes.

### TypeScript

```typescript
abstract class Employee {}

let emp = new Employee();
```

**Error**

```text
Cannot create an instance of an abstract class.
```

> Abstract classes are meant only to be inherited.

---

# 5. Implementing an Abstract Class

A child class extends the abstract class and provides implementations for all abstract methods.

### TypeScript

```typescript
abstract class Employee {
  constructor(
    protected firstName: string,
    protected lastName: string,
  ) {}

  abstract getSalary(): number;

  compensationStatement(): string {
    return `${this.firstName} earns ${this.getSalary()}`;
  }
}

class FullTimeEmployee extends Employee {
  constructor(
    firstName: string,
    lastName: string,
    private salary: number,
  ) {
    super(firstName, lastName);
  }

  getSalary(): number {
    return this.salary;
  }
}

let emp = new FullTimeEmployee("John", "Doe", 12000);

console.log(emp.compensationStatement());
```

**Output**

```text
John earns 12000
```

Another Example

```typescript
class Contractor extends Employee {
  constructor(
    firstName: string,
    lastName: string,
    private rate: number,
    private hours: number,
  ) {
    super(firstName, lastName);
  }

  getSalary(): number {
    return this.rate * this.hours;
  }
}
```

> Every child class must implement all abstract methods, otherwise it will produce a compile-time error.

---

# 6. Abstract Class vs Regular Class

| Feature                   | Regular Class | Abstract Class     |
| ------------------------- | ------------- | ------------------ |
| Can Create Object         | â Yes        | â No              |
| Can Have Constructor      | â Yes        | â Yes             |
| Can Have Normal Methods   | â Yes        | â Yes             |
| Can Have Abstract Methods | â No         | â Yes             |
| Must Be Extended          | â No         | â Yes (to use it) |

---

# 7. Summary

- An **Abstract Class** is a base class that **cannot be instantiated**.
- Use the **`abstract`** keyword to declare an abstract class.
- An abstract class can contain both **implemented methods** and **abstract methods**.
- An **abstract method** has only a declaration and **must be implemented** by every child class.
- Child classes use the **`extends`** keyword to inherit from an abstract class.
- Abstract classes are useful for **sharing common code** while forcing derived classes to implement specific behavior.
- **JavaScript does not support abstract classes directly**; this feature is available in **TypeScript**.

# JavaScript Private Fields & Private Methods (JavaScript + TypeScript)

> **Note:** Modern JavaScript (ES2022+) supports **private fields and methods** using the `#` symbol. TypeScript also supports this syntax (in addition to its own `private` keyword).

---

# Table of Contents

1. What are Private Fields?
2. Declaring Private Fields
3. Accessing Private Fields using Getters & Setters
4. Private Fields and Inheritance
5. Checking Private Fields with `in`
6. Static Private Fields
7. Private Methods
8. Private Static Methods
9. JavaScript `#private` vs TypeScript `private`
10. Summary

---

# 1. What are Private Fields?

A **private field** is a class property that can **only be accessed inside the class** where it is declared.

Private fields are created by prefixing the field name with **`#`**.

### JavaScript

```javascript
class Circle {
  #radius;
}
```

### TypeScript

```typescript
class Circle {
  #radius: number;
}
```

> Private fields cannot be accessed outside the class or by subclasses.

---

# 2. Declaring Private Fields

Private fields are initialized inside the constructor and used within class methods.

### JavaScript

```javascript
class Circle {
  #radius;

  constructor(radius) {
    this.#radius = radius;
  }

  get area() {
    return Math.PI * this.#radius ** 2;
  }
}

let circle = new Circle(10);

console.log(circle.area);
```

### TypeScript

```typescript
class Circle {
  #radius: number;

  constructor(radius: number) {
    this.#radius = radius;
  }

  get area(): number {
    return Math.PI * this.#radius ** 2;
  }
}
```

> The `#radius` field is accessible only inside the `Circle` class.

---

# 3. Accessing Private Fields using Getters & Setters

Getters and setters provide controlled access to private fields.

### JavaScript

```javascript
class Circle {
  #radius = 0;

  set radius(value) {
    if (value > 0) {
      this.#radius = value;
    }
  }

  get radius() {
    return this.#radius;
  }
}
```

### TypeScript

```typescript
class Circle {
  #radius: number = 0;

  set radius(value: number) {
    if (value > 0) {
      this.#radius = value;
    }
  }

  get radius(): number {
    return this.#radius;
  }
}
```

> Getters return the private value, while setters validate data before updating it.

---

# 4. Private Fields and Inheritance

Private fields are **not inherited**.

A subclass **cannot access** the parent's private fields.

### JavaScript

```javascript
class Circle {
  #radius = 10;
}

class Cylinder extends Circle {
  constructor() {
    super();

    // this.#radius â Error
  }
}
```

### TypeScript

```typescript
class Circle {
  #radius: number = 10;
}

class Cylinder extends Circle {
  constructor() {
    super();

    // this.#radius â Error
  }
}
```

> Private fields belong only to the class where they are declared.

---

# 5. Checking Private Fields with `in`

The `in` operator checks whether an object contains a private field.

### JavaScript

```javascript
class Circle {
  #radius = 0;

  static hasRadius(circle) {
    return #radius in circle;
  }
}

let circle = new Circle();

console.log(Circle.hasRadius(circle));
```

Output

```text
true
```

### TypeScript

```typescript
class Circle {
  #radius: number = 0;

  static hasRadius(circle: Circle): boolean {
    return #radius in circle;
  }
}
```

> The `in` operator can only be used inside the class that declares the private field.

---

# 6. Static Private Fields

Private fields can also be **static**, meaning they belong to the class instead of its objects.

### JavaScript

```javascript
class Circle {
  static #count = 0;

  constructor() {
    Circle.#count++;
  }

  static getCount() {
    return Circle.#count;
  }
}

new Circle();
new Circle();

console.log(Circle.getCount());
```

Output

```text
2
```

### TypeScript

```typescript
class Circle {
  static #count: number = 0;

  constructor() {
    Circle.#count++;
  }

  static getCount(): number {
    return Circle.#count;
  }
}
```

> Static private fields are shared by all objects but remain accessible only within the class.

---

# 7. Private Methods

Private methods can only be called from inside the class.

### JavaScript

```javascript
class Person {
  #firstName = "John";
  #lastName = "Doe";

  getFullName() {
    return this.#firstLast();
  }

  #firstLast() {
    return `${this.#firstName} ${this.#lastName}`;
  }
}

let person = new Person();

console.log(person.getFullName());
```

### TypeScript

```typescript
class Person {
  #firstName: string = "John";
  #lastName: string = "Doe";

  getFullName(): string {
    return this.#firstLast();
  }

  #firstLast(): string {
    return `${this.#firstName} ${this.#lastName}`;
  }
}
```

> Private methods help hide internal implementation details from users of the class.

---

# 8. Private Static Methods

Private static methods belong to the class and can only be called within that class.

### JavaScript

```javascript
class Person {
  constructor(name) {
    this.name = Person.#validate(name);
  }

  static #validate(name) {
    return name.trim();
  }
}
```

### TypeScript

```typescript
class Person {
  constructor(public name: string) {
    this.name = Person.#validate(name);
  }

  static #validate(name: string): string {
    return name.trim();
  }
}
```

> Private static methods are useful for helper or validation logic used only inside the class.

---

# 9. JavaScript `#private` vs TypeScript `private`

| Feature                  | JavaScript `#private` | TypeScript `private`      |
| ------------------------ | --------------------- | ------------------------- |
| Syntax                   | `#field`              | `private field`           |
| Runtime Privacy          | â Yes                | â No (compile-time only) |
| Accessible Outside Class | â No                 | â Compile-time error     |
| Accessible in Subclass   | â No                 | â No                     |
| Supported in JavaScript  | â Yes                | â No                     |

> **`#private` provides true runtime privacy**, whereas **`private` in TypeScript is enforced only during compilation**.

---

# 10. Summary

- Prefix a field or method with **`#`** to make it private.
- Private fields and methods are accessible **only inside the class** where they are declared.
- Subclasses **cannot access** a parent's private members.
- Use **getters and setters** to safely expose private fields.
- Use the **`in` operator** to check whether an object contains a private field.
- Static private fields and methods belong to the **class**, not its objects.
- JavaScript `#private` provides **true runtime encapsulation**, while TypeScript's `private` keyword provides **compile-time access checking**.

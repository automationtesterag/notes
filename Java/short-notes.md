# Java Short Notes — Syntax + Commonly Used Inbuilt Methods

A **quick revision guide for Java**, especially useful for **SDET/Automation interviews**.

*Improved version: every topic now has a one-line definition, missing syntax has been filled in, and extra interview notes are added (marked with 📝).*

---

## 1. Java Basics

Java is a class-based, object-oriented, platform-independent programming language — code is compiled to bytecode and run on the **JVM**, which is what makes it "write once, run anywhere."

### Basic Java Program

```java
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Java");
    }
}
```

📝 **Note:** `main` must be `public static void main(String[] args)` — this exact signature is what the JVM looks for to start execution.

### Important keywords

| Keyword      | Purpose                                |
| ------------ | --------------------------------------- |
| `class`      | Defines a class                        |
| `public`     | Accessible everywhere                  |
| `private`    | Accessible only within class           |
| `static`     | Belongs to class                       |
| `final`      | Cannot be changed/overridden/inherited |
| `void`       | Method returns nothing                 |
| `new`        | Creates an object                      |
| `this`       | Refers to current object               |
| `super`      | Refers to parent class                 |
| `extends`    | Inheritance                            |
| `implements` | Implements interface                   |
| `interface`  | Defines interface                      |
| `abstract`   | Defines abstract class/method          |
| `return`     | Returns a value                        |

### Packages & Import *(added)*

A **package** is a namespace/folder used to organize related classes; `import` brings a class from another package into scope.

```java
package com.myproject.utils;   // declares which package this file belongs to

import java.util.ArrayList;    // import a single class
import java.util.*;            // import everything in a package
```

📝 **JVM vs JDK vs JRE** (very common interview question):
```text
JVM → Java Virtual Machine: runs the compiled bytecode (.class files)
JRE → JVM + libraries: needed to RUN Java programs
JDK → JRE + compiler (javac) + dev tools: needed to WRITE & compile Java programs
```

---

# 2. Variables

A **variable** is a named memory location used to store a value of a particular type.

### General syntax

```java
dataType variableName = value;
```

```java
int age = 25;
double salary = 50000.50;
char grade = 'A';
boolean active = true;
String name = "Anudeep";
```

### `final`

`final` on a variable means its value can be assigned only once (a constant).

```java
final int MAX = 100;
// MAX = 200; // Error
```

### Variable types

```java
class Test {

    int instanceVariable = 10;

    static int staticVariable = 20;

    void method() {
        int localVariable = 30;
    }
}
```

* **Local** → declared inside a method/block; exists only during that call
* **Instance** → belongs to an object; each object has its own copy
* **Static** → belongs to the class; shared by all objects

---

# 3. Data Types

A **data type** specifies the kind of value a variable can hold and how much memory it occupies.

### Primitive

Primitive types store actual values directly (not references) and are not objects.

```java
byte b = 10;
short s = 100;
int i = 1000;
long l = 100000L;

float f = 10.5f;
double d = 20.50;

char c = 'A';
boolean flag = true;
```

📝 **Sizes (interview favorite):**

| Type    | Size   | Default |
| ------- | ------ | ------- |
| byte    | 1 byte | 0       |
| short   | 2 bytes| 0       |
| int     | 4 bytes| 0       |
| long    | 8 bytes| 0L      |
| float   | 4 bytes| 0.0f    |
| double  | 8 bytes| 0.0d    |
| char    | 2 bytes| '\u0000'|
| boolean | 1 bit (JVM-dependent) | false |

### Non-primitive (Reference types)

Non-primitive types store a reference (address) to an object, not the object itself.

```java
String name = "Java";
int[] numbers = {1, 2, 3};
```

---

# 4. Type Casting

**Type casting** is converting a value from one data type to another.

### Widening (Implicit)

Automatically converts smaller → larger type; no data loss.

```java
int a = 10;
double b = a;
```

```text
byte → short → int → long → float → double
```

### Narrowing (Explicit)

Converts larger → smaller type; requires an explicit cast and may lose data/precision.

```java
double a = 10.5;
int b = (int) a;   // b = 10
```

---

# 5. Operators

**Operators** are symbols that perform operations on variables and values.

### Arithmetic

```java
+   -   *   /   %
```

```java
int a = 10;
int b = 3;

System.out.println(a + b);
System.out.println(a - b);
System.out.println(a * b);
System.out.println(a / b);
System.out.println(a % b);
```

### Relational

Compares two values and returns a `boolean`.

```java
==  !=  >  <  >=  <=
```

### Logical

Combines boolean expressions.

```java
&&   ||   !
```

### Assignment / Compound Assignment *(added)*

```java
=   +=   -=   *=   /=   %=
```

```java
int x = 10;
x += 5;   // x = 15
x -= 2;   // x = 13
x *= 2;   // x = 26
```

### Increment / decrement

```java
i++;
i--;
++i;
--i;
```

### Ternary

Short form of if-else that returns a value.

```java
String result = age >= 18 ? "Adult" : "Minor";
```

### Bitwise *(added — occasionally asked)*

```java
&   |   ^   ~   <<   >>   >>>
```

---

# 6. If / Else

Conditional branching — executes a block only if the condition is true.

```java
if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}
```

### Else-if

```java
if (marks >= 90) {
    System.out.println("A");
} else if (marks >= 75) {
    System.out.println("B");
} else {
    System.out.println("C");
}
```

---

# 7. Switch

Selects one of many code blocks to execute based on a variable's value — an alternative to a long if-else-if chain.

```java
int day = 2;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    default:
        System.out.println("Invalid");
}
```

📝 **Note:** Forgetting `break` causes **fall-through** — execution continues into the next case.

### Modern switch expression (Java 14+)

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

---

# 8. Loops

Loops repeat a block of code while a condition holds.

## For Loop

Used when the number of iterations is known in advance.

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

## Enhanced For Loop (for-each)

Used to iterate over arrays/collections without manually tracking an index.

```java
int[] numbers = {10, 20, 30};

for (int number : numbers) {
    System.out.println(number);
}
```

## While

Condition checked **before** each iteration — may run zero times.

```java
int i = 0;

while (i < 5) {
    System.out.println(i);
    i++;
}
```

## Do-While

Condition checked **after** each iteration — always runs at least once.

```java
int i = 0;

do {
    System.out.println(i);
    i++;
} while (i < 5);
```

### `break`

Exits the loop immediately.

```java
for (int i = 0; i < 10; i++) {
    if (i == 5)
        break;
}
```

### `continue`

Skips the current iteration and moves to the next.

```java
for (int i = 0; i < 5; i++) {
    if (i == 2)
        continue;

    System.out.println(i);
}
```

---

# 9. Methods

A **method** is a reusable block of code that performs a specific task.

### Syntax

```java
accessModifier returnType methodName(parameters) {
    // code
}
```

Example:

```java
public int add(int a, int b) {
    return a + b;
}
```

### Void method

A method that performs an action but returns no value.

```java
public void printName(String name) {
    System.out.println(name);
}
```

### Static method

Belongs to the class, not an object — called without creating an instance.

```java
public static int add(int a, int b) {
    return a + b;
}
```

Call:

```java
int result = Test.add(10, 20);
```

---

# 10. Method Overloading

**Compile-time polymorphism** — same method name, different parameter list, within the same class.

```java
public int add(int a, int b) {
    return a + b;
}

public int add(int a, int b, int c) {
    return a + b + c;
}
```

Can differ by:

* Number of parameters
* Parameter types
* Parameter order

📝 Return type alone **cannot** overload a method.

---

# 11. Class & Object

A **class** is a blueprint; an **object** is an instance of that blueprint created in memory.

```java
class Car {

    String brand;

    void drive() {
        System.out.println("Driving");
    }
}
```

Create object:

```java
Car car = new Car();

car.brand = "BMW";
car.drive();
```

---

# 12. Constructor

A **constructor** is a special method (same name as the class, no return type) used to initialize an object when it's created.

```java
class User {

    String name;

    User(String name) {
        this.name = name;
    }
}
```

Create:

```java
User user = new User("Anudeep");
```

### Default constructor

A no-argument constructor Java auto-generates if you don't define any constructor yourself.

```java
User() {
}
```

📝 **Note:** As soon as you write *any* constructor (even parameterized), Java stops generating the default one automatically.

### Constructor overloading & chaining *(added)*

```java
class User {

    String name;
    int age;

    User() {
        this("Unknown", 0);   // calls the other constructor
    }

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

---

# 13. `this`

Refers to the **current object** — used to distinguish instance variables from parameters with the same name, or to call another constructor/method of the same object.

```java
class User {

    String name;

    User(String name) {
        this.name = name;
    }
}
```

Common uses:

```java
this.name = name;
this.method();
this(args);   // constructor chaining
```

---

# 14. Inheritance

Allows one class (**subclass**) to acquire the fields and methods of another class (**superclass**), enabling code reuse.

```java
class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}
```

```java
Dog dog = new Dog();

dog.eat();
dog.bark();
```

### Types commonly discussed

```text
Single
Multilevel
Hierarchical
```

📝 Java does **not** support multiple inheritance through classes (to avoid the "diamond problem"), but a class *can* implement multiple interfaces.

---

# 15. Method Overriding

**Runtime polymorphism** — a subclass provides its own implementation of a method already defined in its parent, with the **same signature**.

Parent:

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}
```

Child:

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

📝 **Rules:** same method name, same parameters, same (or covariant) return type; access modifier can't be more restrictive; `static`/`final`/`private` methods cannot be overridden.

---

# 16. `super`

Refers to the **immediate parent class** — used to access parent fields/methods or call the parent constructor.

```java
class Dog extends Animal {

    void test() {
        super.sound();
    }
}
```

Parent constructor:

```java
class Dog extends Animal {

    Dog() {
        super();   // must be the first statement in the constructor
    }
}
```

---

# 17. Encapsulation

Bundling data (fields) and the methods that operate on it into a single unit, while hiding internal details — typically done by making fields `private` and exposing `public` getters/setters.

```java
class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

Usage:

```java
User user = new User();

user.setName("Anudeep");

System.out.println(user.getName());
```

---

# 18. Abstract Class

A class that **cannot be instantiated** and may contain both abstract methods (no body — must be implemented by subclasses) and concrete methods.

```java
abstract class Animal {

    abstract void sound();

    void eat() {
        System.out.println("Eating");
    }
}
```

Child:

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

📝 **Note:** An abstract class *can* have constructors and instance variables — they run when a subclass is instantiated. `Animal a = new Animal();` is not allowed, but `Animal a = new Dog();` is.

---

# 19. Interface

A contract that defines **what** a class must do, not **how** — all methods are implicitly `public abstract` (unless `default`/`static`/`private`), and fields are implicitly `public static final`.

```java
interface Payment {

    void pay();
}
```

Implementation:

```java
class CreditCard implements Payment {

    @Override
    public void pay() {
        System.out.println("Payment done");
    }
}
```

### `default` and `static` methods (Java 8+) *(added)*

```java
interface Payment {

    void pay();

    default void refund() {              // has a body; implementing class may override it
        System.out.println("Refund processed");
    }

    static void showInfo() {              // called directly on the interface
        System.out.println("Payment interface");
    }
}
```

📝 A class can `implements` multiple interfaces (unlike single class inheritance): `class X implements A, B { }`.

---

# 20. Enum

A special type representing a fixed set of constants.

```java
enum Status {
    ACTIVE,
    INACTIVE,
    PENDING
}
```

Usage:

```java
Status status = Status.ACTIVE;
```

Common methods:

```java
Status.values();
Status.valueOf("ACTIVE");
status.name();
status.ordinal();
```

### Enum with fields/constructor *(added — commonly asked)*

```java
enum Status {
    ACTIVE(1),
    INACTIVE(0);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
```

---

# 21. String ⭐

An **immutable** sequence of characters — once created, its value can never change; every "modification" creates a new String object.

```java
String str = "Hello";
```

### Common String methods

| Method               | Example                         |
| -------------------- | -------------------------------- |
| `length()`           | `str.length()`                  |
| `charAt()`           | `str.charAt(0)`                 |
| `substring()`        | `str.substring(1, 4)`           |
| `equals()`           | `str.equals("Hello")`           |
| `equalsIgnoreCase()` | `str.equalsIgnoreCase("hello")` |
| `contains()`         | `str.contains("ell")`           |
| `startsWith()`       | `str.startsWith("He")`          |
| `endsWith()`         | `str.endsWith("lo")`            |
| `indexOf()`          | `str.indexOf("l")`              |
| `lastIndexOf()`      | `str.lastIndexOf("l")`          |
| `toUpperCase()`      | `str.toUpperCase()`             |
| `toLowerCase()`      | `str.toLowerCase()`             |
| `trim()`             | `str.trim()`                    |
| `strip()`            | `str.strip()`                   |
| `replace()`          | `str.replace("H","J")`          |
| `replaceAll()`       | `str.replaceAll("\\s+", "")`    |
| `split()`            | `str.split(" ")`                |
| `isEmpty()`          | `str.isEmpty()`                 |
| `isBlank()`          | `str.isBlank()`                 |
| `format()` *(added)* | `String.format("%s is %d", "age", 25)` |
| `valueOf()` *(added)*| `String.valueOf(100)`           |
| `toCharArray()` *(added)* | `str.toCharArray()`        |

### Important

Don't compare Strings using `==` for content:

```java
str1 == str2; // Avoid for content comparison — compares references, not content
```

Use:

```java
str1.equals(str2);
```

📝 **String Pool:** String literals (`"Hello"`) are stored in a special memory area called the **String Constant Pool** to save memory; `new String("Hello")` bypasses the pool and creates a new object on the heap.

---

# 22. StringBuilder ⭐

A **mutable** sequence of characters — unlike `String`, it can be changed in place without creating new objects, making it efficient for repeated concatenation. **Not thread-safe.**

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");
sb.append(" Java");

System.out.println(sb);
```

### Common methods

```java
sb.append("Hello");

sb.insert(0, "Hi ");

sb.delete(0, 3);

sb.deleteCharAt(0);

sb.reverse();

sb.replace(0, 5, "Java");

sb.length();

sb.charAt(0);

sb.setCharAt(0, 'A');

sb.toString();
```

### Reverse String

```java
String str = "Java";

String reverse = new StringBuilder(str)
        .reverse()
        .toString();
```

---

# 23. StringBuffer

Same purpose and API as `StringBuilder` (mutable character sequence), but **synchronized/thread-safe** — slightly slower as a result.

```java
StringBuffer sb = new StringBuffer("Java");

sb.append(" Selenium");
sb.reverse();
```

### Difference

```text
String        → Immutable
StringBuilder → Mutable, faster, not synchronized
StringBuffer  → Mutable, synchronized (thread-safe)
```

---

# 24. Arrays ⭐

A fixed-size, ordered collection of elements of the **same type**, stored in contiguous memory.

### Declaration

```java
int[] numbers = {10, 20, 30};
```

Or:

```java
int[] numbers = new int[5];
```

### Access

```java
numbers[0];
```

### Length

```java
numbers.length;   // property, not a method
```

### Multi-dimensional arrays *(added)*

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};

System.out.println(matrix[1][2]);   // 6
```

### Common `Arrays` methods

Import:

```java
import java.util.Arrays;
```

```java
Arrays.sort(numbers);

Arrays.toString(numbers);

Arrays.equals(arr1, arr2);

Arrays.copyOf(numbers, 5);

Arrays.fill(numbers, 10);

Arrays.binarySearch(numbers, 20);
```

---

# 25. ArrayList ⭐⭐⭐

A **resizable array** implementation of the `List` interface — grows automatically, allows duplicates, and maintains insertion order.

```java
import java.util.ArrayList;

ArrayList<String> names = new ArrayList<>();

names.add("Java");
names.add("Selenium");
names.add("Playwright");
```

### Common methods

```java
names.add("Python");

names.add(1, "API");

names.get(0);

names.set(0, "JavaScript");

names.remove(0);

names.remove("API");

names.contains("Java");

names.size();

names.isEmpty();

names.clear();
```

### Loop

```java
for (String name : names) {
    System.out.println(name);
}
```

---

# 26. LinkedList

A **doubly-linked list** implementation of `List` and `Deque` — efficient insertion/removal, but slower random access than `ArrayList`.

```java
LinkedList<String> list = new LinkedList<>();
```

Common methods:

```java
list.add("Java");
list.addFirst("Python");
list.addLast("Selenium");

list.getFirst();
list.getLast();

list.removeFirst();
list.removeLast();

list.contains("Java");
list.size();
```

---

# 27. HashSet ⭐

A collection that stores **unique values only** (no duplicates) with **no guaranteed order**, backed internally by a `HashMap`.

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Java");
set.add("Python");
```

Result:

```text
Java
Python
```

### Common methods

```java
set.add("Java");

set.remove("Java");

set.contains("Python");

set.size();

set.isEmpty();

set.clear();
```

### Related Set types *(added)*

```java
Set<String> linked = new LinkedHashSet<>();  // preserves insertion order
Set<String> sorted = new TreeSet<>();        // keeps elements sorted
```

---

# 28. HashMap ⭐⭐⭐

Stores data as **key-value pairs**; keys are unique, values can repeat, and iteration order is **not guaranteed**.

```java
Map<String, Integer> map = new HashMap<>();

map.put("Java", 90);
map.put("Python", 80);
```

### Common methods

```java
map.put("Java", 90);

map.get("Java");

map.getOrDefault("C++", 0);

map.containsKey("Java");

map.containsValue(90);

map.remove("Java");

map.size();

map.isEmpty();

map.putIfAbsent("Java", 100);

map.replace("Java", 95);

map.clear();
```

📝 **Note:** `HashMap` allows **one** `null` key and multiple `null` values; it is not thread-safe (use `ConcurrentHashMap` for that).

### Iterate

```java
for (Map.Entry<String, Integer> entry : map.entrySet()) {

    System.out.println(entry.getKey());
    System.out.println(entry.getValue());
}
```

Other useful loops:

```java
for (String key : map.keySet()) {
    System.out.println(key);
}
```

```java
for (Integer value : map.values()) {
    System.out.println(value);
}
```

---

# 29. Queue

A collection that holds elements for processing in a specific order — typically **FIFO** (First-In-First-Out).

```java
Queue<String> queue = new LinkedList<>();

queue.offer("A");
queue.offer("B");
queue.offer("C");
```

Common methods:

```java
queue.offer("D");

queue.peek();

queue.poll();

queue.isEmpty();

queue.size();
```

Difference:

```text
peek() → retrieves without removing
poll() → retrieves and removes
```

---

# 30. Stack / Deque

A **LIFO** (Last-In-First-Out) structure. `Deque` (Double-Ended Queue) is preferred over the legacy `java.util.Stack` class for stack behavior because it's faster and not synchronized.

```java
Deque<String> stack = new ArrayDeque<>();

stack.push("A");
stack.push("B");

stack.peek();

stack.pop();
```

📝 **Note:** `java.util.Stack` still exists (extends `Vector`, synchronized/legacy) — `Deque`/`ArrayDeque` is the modern recommended choice.

---

# 31. Wrapper Classes

Object versions of primitive types, allowing primitives to be used where objects are required (e.g., in collections, which can't hold primitives directly).

```java
int a = 10;

Integer b = a;       // Autoboxing (primitive → wrapper)

int c = b;           // Unboxing (wrapper → primitive)
```

Common wrappers:

```text
int     → Integer
long    → Long
double  → Double
float   → Float
char    → Character
boolean → Boolean
byte    → Byte
short   → Short
```

---

# 32. Integer Common Methods

`Integer` is the wrapper class for `int`, providing utility methods for parsing and comparing numbers.

```java
Integer.parseInt("100");

Integer.valueOf("100");

Integer.max(10, 20);

Integer.min(10, 20);

Integer.compare(10, 20);
```

Example:

```java
int number = Integer.parseInt("100");
```

---

# 33. Math ⭐

A utility class providing static methods for common mathematical operations.

```java
Math.max(10, 20);

Math.min(10, 20);

Math.abs(-10);

Math.pow(2, 3);

Math.sqrt(16);

Math.round(10.6);

Math.floor(10.9);

Math.ceil(10.1);

Math.random();
```

Random number:

```java
int random = (int)(Math.random() * 100);
```

---

# 34. Exception Handling ⭐⭐⭐

A mechanism to handle **runtime errors** gracefully using `try`/`catch`/`finally`, so the program doesn't crash abruptly.

```java
try {

    int result = 10 / 0;

} catch (ArithmeticException e) {

    System.out.println(e.getMessage());

} finally {

    System.out.println("Executed");
}
```

📝 **Checked vs Unchecked (common interview Q):**
```text
Checked exceptions   → checked at compile time (e.g., IOException) — must be declared/handled
Unchecked exceptions → RuntimeException subclasses (e.g., ArithmeticException, NullPointerException) — not enforced at compile time
```

### Multiple catch

```java
try {

} catch (ArithmeticException e) {

} catch (Exception e) {

}
```

### Throw

Used to explicitly throw an exception.

```java
throw new IllegalArgumentException("Invalid value");
```

### Throws

Declares that a method might throw a checked exception, passing the handling responsibility to the caller.

```java
public void readFile() throws IOException {
}
```

### try-with-resources *(added — auto-closes resources)*

```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    String line = br.readLine();
} catch (IOException e) {
    System.out.println(e.getMessage());
}
```

---

# 35. Custom Exception

A user-defined exception class, created by extending `Exception` (checked) or `RuntimeException` (unchecked).

```java
class InvalidUserException extends Exception {

    public InvalidUserException(String message) {
        super(message);
    }
}
```

Usage:

```java
throw new InvalidUserException("Invalid user");
```

---

# 36. File Handling

Java's `java.nio.file` package (`Path`/`Files`) is the modern way to read, write, and manage files.

```java
Path path = Path.of("data.txt");
```

Read:

```java
String content = Files.readString(path);
```

Write:

```java
Files.writeString(path, "Hello Java");
```

Check:

```java
Files.exists(path);
```

Other common methods:

```java
Files.createFile(path);

Files.delete(path);

Files.copy(source, target);

Files.move(source, target);
```

---

# 37. Date & Time ⭐

The `java.time` package (Java 8+) provides an **immutable, thread-safe** API for dates and times, replacing the old, mutable `java.util.Date`/`Calendar`.

### Current date

```java
LocalDate date = LocalDate.now();
```

### Current time

```java
LocalTime time = LocalTime.now();
```

### Date + Time

```java
LocalDateTime dateTime = LocalDateTime.now();
```

### Common methods

```java
date.getYear();

date.getMonth();

date.getDayOfMonth();

date.getDayOfWeek();

date.plusDays(5);

date.minusDays(5);
```

### Formatting

```java
DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd-MM-yyyy");

String result = date.format(formatter);
```

---

# 38. Java Streams ⭐⭐⭐

An abstraction for processing sequences of elements (collections, arrays) in a **declarative, functional style** — chaining operations like filter/map/reduce instead of writing manual loops.

Very important for interviews and automation.

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40);
```

### Filter

Keeps only elements matching a condition.

```java
numbers.stream()
       .filter(n -> n > 20)
       .forEach(System.out::println);
```

### Map

Transforms each element.

```java
numbers.stream()
       .map(n -> n * 2)
       .forEach(System.out::println);
```

### Collect

Gathers stream results back into a collection.

```java
List<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .collect(Collectors.toList());
```

### Sorted

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

### Distinct / Limit / Skip *(added)*

```java
numbers.stream().distinct().forEach(System.out::println);  // removes duplicates

numbers.stream().limit(2).forEach(System.out::println);    // first 2 elements

numbers.stream().skip(2).forEach(System.out::println);     // skips first 2 elements
```

### Count

```java
long count = numbers.stream()
                    .filter(n -> n > 20)
                    .count();
```

### Find First

```java
Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .findFirst();
```

### Any Match

```java
boolean result =
        numbers.stream()
               .anyMatch(n -> n > 30);
```

### All Match

```java
boolean result =
        numbers.stream()
               .allMatch(n -> n > 0);
```

📝 `noneMatch()` also exists — returns `true` if **no** element matches the condition.

### Reduce

Combines all elements into a single value.

```java
int sum =
        numbers.stream()
               .reduce(0, Integer::sum);
```

📝 **Note:** Streams are **lazy** (intermediate ops like `filter`/`map` don't run until a terminal op like `collect`/`forEach` is called) and can only be consumed **once**.

---

# 39. Lambda Expressions

A concise way to represent an anonymous function (a block of code you can pass around) — mainly used to implement functional interfaces.

Syntax:

```java
(parameters) -> expression
```

Examples of the different forms *(added)*:

```java
() -> System.out.println("No params");        // no parameters

a -> a * 2;                                    // single parameter (parentheses optional)

(a, b) -> a + b;                               // multiple parameters, expression body

(a, b) -> {                                    // block body with multiple statements
    int sum = a + b;
    return sum;
};
```

```java
names.forEach(name -> System.out.println(name));
```

---

# 40. Functional Interfaces

An interface with **exactly one abstract method** (it may have any number of `default`/`static` methods) — the target type for a lambda expression. All built-in ones live in `java.util.function`.

```java
import java.util.function.*;
```

### Predicate

Takes input → returns boolean.

```java
Predicate<Integer> p = n -> n > 10;

System.out.println(p.test(20));
```

### Function

Input → output.

```java
Function<String, Integer> length =
        str -> str.length();

System.out.println(length.apply("Java"));
```

### Consumer

Takes input → no return.

```java
Consumer<String> print =
        str -> System.out.println(str);

print.accept("Java");
```

### Supplier

No input → returns value.

```java
Supplier<Double> random =
        () -> Math.random();

System.out.println(random.get());
```

📝 You can also define your own with `@FunctionalInterface`:

```java
@FunctionalInterface
interface Calculator {
    int operate(int a, int b);
}

Calculator add = (a, b) -> a + b;
```

---

# 41. Optional

A container object used to represent a value that **may or may not be present**, avoiding explicit `null` checks and `NullPointerException`.

```java
Optional<String> name =
        Optional.ofNullable(null);
```

### Creating Optionals *(added)*

```java
Optional<String> a = Optional.of("Java");   // value must NOT be null
Optional<String> b = Optional.empty();      // no value
Optional<String> c = Optional.ofNullable(null); // may be null, safe
```

Common methods:

```java
name.isPresent();

name.isEmpty();

name.orElse("Default");

name.orElseGet(() -> "Default");

name.ifPresent(v -> System.out.println(v));   // added
```

---

# 42. Access Modifiers

Control the **visibility/accessibility** of classes, methods, and fields.

| Modifier    | Same Class | Same Package | Child | Everywhere |
| ----------- | ---------: | -----------: | ----: | ---------: |
| `private`   |          ✅ |            ❌ |     ❌ |          ❌ |
| default     |          ✅ |            ✅ |    ⚠️ |          ❌ |
| `protected` |          ✅ |            ✅ |     ✅ |          ❌ |
| `public`    |          ✅ |            ✅ |     ✅ |          ✅ |

---

# 43. `static`

Belongs to the **class** itself rather than to any individual object — shared across all instances.

```java
class Test {

    static int count = 0;

    static void display() {
        System.out.println(count);
    }
}
```

Call:

```java
Test.display();
```

### Static block *(added)*

Runs once when the class is first loaded — commonly used to initialize static data.

```java
class Test {

    static int count;

    static {
        count = 10;
        System.out.println("Static block executed");
    }
}
```

---

# 44. `final`

Prevents further modification, overriding, or inheritance, depending on what it's applied to.

### Final variable

```java
final int MAX = 100;
```

### Final method

```java
final void display() {
}
```

Cannot be overridden.

### Final class

```java
final class Test {
}
```

Cannot be extended.

---

# 45. Important Java Keywords

Reserved words in Java that have a predefined meaning to the compiler.

```text
class
interface
extends
implements
static
final
abstract
this
super
new
return
throw
throws
try
catch
finally
instanceof
synchronized
volatile
transient
enum
```

---

# 46. `instanceof`

Checks whether an object is an instance of a particular class/subclass/interface, returning a boolean.

```java
if (obj instanceof String) {
    System.out.println("String");
}
```

Modern pattern matching (Java 16+):

```java
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

---

# 47. Generics ⭐

Allow classes, interfaces, and methods to operate on **types specified by the caller**, giving compile-time type safety without casting.

```java
List<String> names = new ArrayList<>();

List<Integer> numbers = new ArrayList<>();
```

Generic method:

```java
public <T> void print(T value) {
    System.out.println(value);
}
```

### Bounded type parameters *(added)*

Restricts the generic type to a subtype of a given class/interface.

```java
public <T extends Number> double sum(List<T> list) {
    double total = 0;
    for (T item : list) {
        total += item.doubleValue();
    }
    return total;
}
```

---

# 48. Collections Quick Comparison

A quick reference for choosing the right collection based on ordering and duplicate/key requirements.

| Collection    | Duplicate | Ordered     | Key/Value |
| ------------- | --------- | ----------- | --------- |
| ArrayList     | ✅         | ✅           | ❌         |
| LinkedList    | ✅         | ✅           | ❌         |
| HashSet       | ❌         | ❌           | ❌         |
| LinkedHashSet | ❌         | ✅ insertion | ❌         |
| TreeSet       | ❌         | Sorted      | ❌         |
| HashMap       | Keys ❌    | ❌           | ✅         |
| LinkedHashMap | Keys ❌    | ✅ insertion | ✅         |
| TreeMap       | Keys ❌    | Sorted      | ✅         |

---

# 49. Sorting Collections

The `Collections` utility class provides static methods to sort and manipulate `List`s.

```java
Collections.sort(list);
```

Reverse:

```java
Collections.reverse(list);
```

Maximum:

```java
Collections.max(list);
```

Minimum:

```java
Collections.min(list);
```

Frequency:

```java
Collections.frequency(list, "Java");
```

### Sorting with a custom Comparator *(added — very common in interviews)*

```java
// Sort by string length
Collections.sort(names, (a, b) -> a.length() - b.length());

// Or using Comparator utility
names.sort(Comparator.comparing(String::length));
```

---

# 50. Common `Collections` Methods

```java
Collections.sort(list);

Collections.reverse(list);

Collections.shuffle(list);

Collections.max(list);

Collections.min(list);

Collections.frequency(list, value);

Collections.binarySearch(list, value);

Collections.swap(list, 0, 1);
```

---

# 51. Common Utility Methods

### `Objects`

A helper class for null-safe operations, commonly used to avoid `NullPointerException`.

```java
Objects.equals(a, b);

Objects.isNull(obj);

Objects.nonNull(obj);

Objects.requireNonNull(obj);
```

### `Arrays`

```java
Arrays.sort(array);

Arrays.toString(array);

Arrays.equals(a, b);

Arrays.copyOf(array, length);

Arrays.asList("A", "B", "C");
```

### Important difference

```java
array.length     // property (no parentheses) — for arrays
```

```java
string.length()  // method — for String
```

```java
list.size()      // method — for List/Collection
```

---

# 52. Regex

A **regular expression** is a pattern used to match, search, or manipulate character sequences.

```java
String email = "test@gmail.com";

boolean valid =
        email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
```

Common:

```java
str.matches(".*Java.*");
```

Split:

```java
String[] parts = str.split(",");
```

Replace:

```java
str.replaceAll("\\s+", "");
```

### Pattern & Matcher *(added — for repeated/complex matching)*

```java
import java.util.regex.*;

Pattern pattern = Pattern.compile("[0-9]+");
Matcher matcher = pattern.matcher("Order 123 for 456 units");

while (matcher.find()) {
    System.out.println(matcher.group());   // prints 123, then 456
}
```

---

# 53. Scanner

Reads input from the console (or other input sources) — commonly used for user input in simple programs.

```java
Scanner scanner = new Scanner(System.in);

String name = scanner.nextLine();

int age = scanner.nextInt();

scanner.close();
```

---

# 54. Random

Generates pseudo-random numbers/booleans.

```java
Random random = new Random();

int number = random.nextInt(100);

boolean flag = random.nextBoolean();

double value = random.nextDouble();
```

---

# 55. Important Interview Differences

### `==` vs `.equals()`

```java
==          → compares references for objects
.equals()   → compares content when properly overridden
```

### Array vs ArrayList

```text
Array       → fixed size
ArrayList   → dynamic size
```

### String vs StringBuilder

```text
String        → immutable
StringBuilder → mutable
```

### ArrayList vs LinkedList

```text
ArrayList  → fast random access
LinkedList → efficient insertion/removal at ends
```

### HashMap vs HashSet

```text
HashMap → key-value
HashSet → unique values
```

### Overloading vs Overriding

```text
Overloading  → same class, different parameters (compile-time polymorphism)
Overriding   → child class redefines parent method (runtime polymorphism)
```

### Abstract class vs Interface *(added)*

```text
Abstract class → can have constructors, instance fields, and mixed abstract/concrete methods;
                  a class can extend only ONE abstract class
Interface      → mostly contracts (default/static methods allowed since Java 8);
                  a class can implement MULTIPLE interfaces
```

### Checked vs Unchecked Exception *(added)*

```text
Checked   → verified at compile time (must be handled/declared), e.g. IOException
Unchecked → RuntimeException subclasses, not enforced at compile time, e.g. NullPointerException
```

---

# 56. Most Important Methods to Memorize ⭐⭐⭐

For **SDET/Automation interviews**, prioritize these:

### String

```java
length()
charAt()
substring()
equals()
equalsIgnoreCase()
contains()
indexOf()
lastIndexOf()
startsWith()
endsWith()
split()
trim()
strip()
replace()
replaceAll()
toUpperCase()
toLowerCase()
isEmpty()
isBlank()
```

### Array

```java
Arrays.sort()
Arrays.toString()
Arrays.equals()
Arrays.copyOf()
Arrays.fill()
Arrays.binarySearch()
```

### List

```java
add()
get()
set()
remove()
contains()
size()
isEmpty()
clear()
indexOf()
```

### Set

```java
add()
remove()
contains()
size()
isEmpty()
clear()
```

### Map

```java
put()
get()
getOrDefault()
putIfAbsent()
containsKey()
containsValue()
remove()
replace()
keySet()
values()
entrySet()
```

### Collections

```java
sort()
reverse()
max()
min()
shuffle()
frequency()
binarySearch()
```

### Math

```java
max()
min()
abs()
pow()
sqrt()
round()
floor()
ceil()
random()
```

### Java 8+ / Streams

```java
stream()
filter()
map()
sorted()
distinct()
limit()
skip()
count()
findFirst()
anyMatch()
allMatch()
noneMatch()
collect()
reduce()
forEach()
```

### Date/Time

```java
now()
plusDays()
minusDays()
getYear()
getMonth()
getDayOfMonth()
format()
```

---

## ⭐ SDET Priority

If you're preparing specifically for **Java + Selenium/Playwright/API automation interviews**, study in this order:

```text
1. Variables & Data Types
2. Operators
3. if / switch
4. for / while loops
5. Methods
6. String
7. Arrays
8. StringBuilder
9. OOP
10. Exception Handling
11. Collections
12. ArrayList
13. HashSet
14. HashMap
15. Wrapper Classes
16. Java 8 Lambda
17. Streams
18. Functional Interfaces
19. Optional
20. File Handling
21. Date & Time
22. Generics
23. Regex
```

**Most important for coding rounds:**
`String → Array → ArrayList → HashMap → loops → methods → OOP → Exception Handling → Streams → Lambda`.

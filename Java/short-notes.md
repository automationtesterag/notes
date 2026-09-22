# Java Short Notes — Syntax + Commonly Used Inbuilt Methods

A **quick revision guide for Java**, especially useful for **SDET/Automation interviews**.



---

## 📑 Table of Contents

**Fundamentals**

1. [Java Basics](#1-java-basics)
2. [Packages and Import](#2-packages-and-import)
3. [JVM vs JDK vs JRE](#3-jvm-vs-jdk-vs-jre)
4. [Variables](#4-variables)
5. [Data Types](#5-data-types)
6. [Type Casting](#6-type-casting)
7. [Operators](#7-operators)
8. [If Else](#8-if-else)
9. [Switch](#9-switch)
10. [Loops](#10-loops)
11. [Methods](#11-methods)
12. [Method Overloading](#12-method-overloading)

**Object-Oriented Programming**

13. [Class and Object](#13-class-and-object)
14. [Constructor](#14-constructor)
15. [this Keyword](#15-this-keyword)
16. [Inheritance](#16-inheritance)
17. [Method Overriding](#17-method-overriding)
18. [super Keyword](#18-super-keyword)
19. [Encapsulation](#19-encapsulation)
20. [Abstract Class](#20-abstract-class)
21. [Interface](#21-interface)
22. [Enum](#22-enum)
23. [Access Modifiers](#23-access-modifiers)
24. [static Keyword](#24-static-keyword)
25. [final Keyword](#25-final-keyword)
26. [instanceof](#26-instanceof)
27. [Important Java Keywords](#27-important-java-keywords)

**Core APIs**

28. [String](#28-string)
29. [StringBuilder](#29-stringbuilder)
30. [StringBuffer](#30-stringbuffer)
31. [Arrays](#31-arrays)
32. [Wrapper Classes](#32-wrapper-classes)
33. [Integer Common Methods](#33-integer-common-methods)
34. [Math Class](#34-math-class)
35. [Generics](#35-generics)

**Collections Framework**

36. [ArrayList](#36-arraylist)
37. [LinkedList](#37-linkedlist)
38. [HashSet](#38-hashset)
39. [HashMap](#39-hashmap)
40. [Queue](#40-queue)
41. [Stack and Deque](#41-stack-and-deque)
42. [Collections Quick Comparison](#42-collections-quick-comparison)
43. [Collections Utility Methods](#43-collections-utility-methods)

**Exception Handling**

44. [Exception Handling](#44-exception-handling)
45. [Custom Exception](#45-custom-exception)

**Functional Programming (Java 8+)**

46. [Lambda Expressions](#46-lambda-expressions)
47. [Functional Interfaces](#47-functional-interfaces)
48. [Java Streams](#48-java-streams)
49. [Optional](#49-optional)

**Utility APIs**

50. [Date and Time](#50-date-and-time)
51. [Regex](#51-regex)
52. [Scanner](#52-scanner)
53. [Random](#53-random)
54. [Objects and Arrays Utility Methods](#54-objects-and-arrays-utility-methods)

**Files & Data Formats**

55. [File Handling — Text Files](#55-file-handling--text-files)
56. [Reading Excel Files](#56-reading-excel-files)
57. [Reading JSON Files](#57-reading-json-files)
58. [Reading Properties Files](#58-reading-properties-files)

**Build Tools**

59. [Maven Build Lifecycle](#59-maven-build-lifecycle)

**Interview Prep**

60. [Important Interview Differences](#60-important-interview-differences)
61. [Most Important Methods to Memorize](#61-most-important-methods-to-memorize)
62. [SDET Priority Order](#62-sdet-priority-order)

---

## 1. Java Basics

Java is a class-based, object-oriented, platform-independent language — code compiles to bytecode and runs on the **JVM** ("write once, run anywhere").

### Syntax

```java
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Java");
    }
}
```

📝 **Note:** `main` must be exactly `public static void main(String[] args)` — this is the signature the JVM looks for to start execution.

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

[⬆ Back to top](#-table-of-contents)

---

## 2. Packages and Import

A **package** is a namespace/folder used to organize related classes; `import` brings a class from another package into scope.

### Syntax

```java
package com.myproject.utils;   // declares which package this file belongs to

import java.util.ArrayList;    // import a single class
import java.util.*;            // import everything in a package
```

[⬆ Back to top](#-table-of-contents)

---

## 3. JVM vs JDK vs JRE

Three related but distinct pieces of the Java platform — a very common interview question.

### Definitions / "syntax"

```text
JVM → Java Virtual Machine: runs the compiled bytecode (.class files)
JRE → JVM + libraries: needed to RUN Java programs
JDK → JRE + compiler (javac) + dev tools: needed to WRITE & compile Java programs
```

```bash
javac Main.java     # JDK: compiles .java -> .class
java Main            # JRE/JVM: runs the .class file
```

[⬆ Back to top](#-table-of-contents)

---

## 4. Variables

A **variable** is a named memory location used to store a value of a particular type.

### Syntax

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

### `final` variable

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

[⬆ Back to top](#-table-of-contents)

---

## 5. Data Types

A **data type** specifies the kind of value a variable can hold and how much memory it occupies.

### Primitive — syntax

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

| Type    | Size    | Default  |
| ------- | ------- | -------- |
| byte    | 1 byte  | 0        |
| short   | 2 bytes | 0        |
| int     | 4 bytes | 0        |
| long    | 8 bytes | 0L       |
| float   | 4 bytes | 0.0f     |
| double  | 8 bytes | 0.0d     |
| char    | 2 bytes | '\u0000' |
| boolean | 1 bit (JVM-dependent) | false |

### Non-primitive (Reference types) — syntax

```java
String name = "Java";
int[] numbers = {1, 2, 3};
```

[⬆ Back to top](#-table-of-contents)

---

## 6. Type Casting

**Type casting** is converting a value from one data type to another.

### Widening (Implicit) — syntax

```java
int a = 10;
double b = a;
```

```text
byte → short → int → long → float → double
```

### Narrowing (Explicit) — syntax

```java
double a = 10.5;
int b = (int) a;   // b = 10
```

[⬆ Back to top](#-table-of-contents)

---

## 7. Operators

**Operators** are symbols that perform operations on variables and values.

### Arithmetic

```java
+   -   *   /   %
```

```java
int a = 10, b = 3;
System.out.println(a + b);
System.out.println(a - b);
System.out.println(a * b);
System.out.println(a / b);
System.out.println(a % b);
```

### Relational

```java
==  !=  >  <  >=  <=
```

### Logical

```java
&&   ||   !
```

### Assignment / Compound Assignment

```java
=   +=   -=   *=   /=   %=
```

```java
int x = 10;
x += 5;   // 15
x -= 2;   // 13
x *= 2;   // 26
```

### Increment / Decrement

```java
i++;
i--;
++i;
--i;
```

### Ternary

```java
String result = age >= 18 ? "Adult" : "Minor";
```

### Bitwise

```java
&   |   ^   ~   <<   >>   >>>
```

[⬆ Back to top](#-table-of-contents)

---

## 8. If Else

Conditional branching — executes a block only if the condition is true.

### Syntax

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

[⬆ Back to top](#-table-of-contents)

---

## 9. Switch

Selects one of many code blocks to execute based on a variable's value.

### Syntax

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

📝 **Note:** Forgetting `break` causes **fall-through** into the next case.

### Modern switch expression (Java 14+)

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

[⬆ Back to top](#-table-of-contents)

---

## 10. Loops

Loops repeat a block of code while a condition holds.

### For Loop — syntax

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

### Enhanced For Loop (for-each) — syntax

```java
int[] numbers = {10, 20, 30};

for (int number : numbers) {
    System.out.println(number);
}
```

### While — syntax

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}
```

### Do-While — syntax

```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

### `break` — syntax

```java
for (int i = 0; i < 10; i++) {
    if (i == 5)
        break;
}
```

### `continue` — syntax

```java
for (int i = 0; i < 5; i++) {
    if (i == 2)
        continue;
    System.out.println(i);
}
```

[⬆ Back to top](#-table-of-contents)

---

## 11. Methods

A **method** is a reusable block of code that performs a specific task.

### Syntax

```java
accessModifier returnType methodName(parameters) {
    // code
}
```

```java
public int add(int a, int b) {
    return a + b;
}
```

### Void method

```java
public void printName(String name) {
    System.out.println(name);
}
```

### Static method

```java
public static int add(int a, int b) {
    return a + b;
}
```

Call:

```java
int result = Test.add(10, 20);
```

[⬆ Back to top](#-table-of-contents)

---

## 12. Method Overloading

**Compile-time polymorphism** — same method name, different parameter list, within the same class.

### Syntax

```java
public int add(int a, int b) {
    return a + b;
}

public int add(int a, int b, int c) {
    return a + b + c;
}
```

Can differ by: number of parameters, parameter types, parameter order.
📝 Return type alone **cannot** overload a method.

[⬆ Back to top](#-table-of-contents)

---

## 13. Class and Object

A **class** is a blueprint; an **object** is an instance of that blueprint created in memory.

### Syntax

```java
class Car {

    String brand;

    void drive() {
        System.out.println("Driving");
    }
}
```

```java
Car car = new Car();
car.brand = "BMW";
car.drive();
```

[⬆ Back to top](#-table-of-contents)

---

## 14. Constructor

A special method (same name as the class, no return type) used to initialize an object when it's created.

### Syntax

```java
class User {

    String name;

    User(String name) {
        this.name = name;
    }
}
```

```java
User user = new User("Anudeep");
```

### Default constructor

```java
User() {
}
```

📝 **Note:** As soon as you write *any* constructor, Java stops auto-generating the default one.

### Constructor overloading & chaining

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

[⬆ Back to top](#-table-of-contents)

---

## 15. this Keyword

Refers to the **current object** — distinguishes instance variables from parameters, or calls another constructor/method of the same object.

### Syntax

```java
class User {

    String name;

    User(String name) {
        this.name = name;
    }
}
```

```java
this.name = name;
this.method();
this(args);   // constructor chaining
```

[⬆ Back to top](#-table-of-contents)

---

## 16. Inheritance

Allows one class (**subclass**) to acquire the fields and methods of another (**superclass**), enabling code reuse.

### Syntax

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

### Types

```text
Single
Multilevel
Hierarchical
```

📝 Java does **not** support multiple inheritance through classes, but a class *can* implement multiple interfaces.

[⬆ Back to top](#-table-of-contents)

---

## 17. Method Overriding

**Runtime polymorphism** — a subclass provides its own implementation of a parent method with the **same signature**.

### Syntax

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

📝 **Rules:** same name/parameters/(covariant) return type; access can't be more restrictive; `static`/`final`/`private` methods cannot be overridden.

[⬆ Back to top](#-table-of-contents)

---

## 18. super Keyword

Refers to the **immediate parent class** — accesses parent fields/methods or calls the parent constructor.

### Syntax

```java
class Dog extends Animal {

    void test() {
        super.sound();
    }

    Dog() {
        super();   // must be the first statement in the constructor
    }
}
```

[⬆ Back to top](#-table-of-contents)

---

## 19. Encapsulation

Bundling data and methods into one unit while hiding internal details — fields `private`, exposed via `public` getters/setters.

### Syntax

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

```java
User user = new User();
user.setName("Anudeep");
System.out.println(user.getName());
```

[⬆ Back to top](#-table-of-contents)

---

## 20. Abstract Class

A class that **cannot be instantiated**, may contain abstract methods (no body) and concrete methods.

### Syntax

```java
abstract class Animal {

    abstract void sound();

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

📝 **Note:** An abstract class *can* have constructors and fields. `new Animal()` is not allowed, but `Animal a = new Dog();` is.

[⬆ Back to top](#-table-of-contents)

---

## 21. Interface

A contract defining **what** a class must do — methods are implicitly `public abstract` (unless `default`/`static`/`private`); fields are implicitly `public static final`.

### Syntax

```java
interface Payment {

    void pay();
}

class CreditCard implements Payment {

    @Override
    public void pay() {
        System.out.println("Payment done");
    }
}
```

### `default` and `static` methods (Java 8+)

```java
interface Payment {

    void pay();

    default void refund() {
        System.out.println("Refund processed");
    }

    static void showInfo() {
        System.out.println("Payment interface");
    }
}
```

📝 A class can implement multiple interfaces: `class X implements A, B { }`.

[⬆ Back to top](#-table-of-contents)

---

## 22. Enum

A special type representing a fixed set of constants.

### Syntax

```java
enum Status {
    ACTIVE,
    INACTIVE,
    PENDING
}
```

```java
Status status = Status.ACTIVE;

Status.values();
Status.valueOf("ACTIVE");
status.name();
status.ordinal();
```

### Enum with fields/constructor

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

[⬆ Back to top](#-table-of-contents)

---

## 23. Access Modifiers

Control the **visibility** of classes, methods, and fields.

### Syntax

```java
public class A { }
class B { }              // default (package-private)
private int x;           // only inside the class (fields/methods, not top-level classes)
protected int y;
```

| Modifier    | Same Class | Same Package | Child | Everywhere |
| ----------- | ---------: | -----------: | ----: | ---------: |
| `private`   |          ✅ |            ❌ |     ❌ |          ❌ |
| default     |          ✅ |            ✅ |    ⚠️ |          ❌ |
| `protected` |          ✅ |            ✅ |     ✅ |          ❌ |
| `public`    |          ✅ |            ✅ |     ✅ |          ✅ |

[⬆ Back to top](#-table-of-contents)

---

## 24. static Keyword

Belongs to the **class** itself rather than to any individual object.

### Syntax

```java
class Test {

    static int count = 0;

    static void display() {
        System.out.println(count);
    }

    static {                       // static block — runs once when class is loaded
        count = 10;
        System.out.println("Static block executed");
    }
}
```

```java
Test.display();
```

[⬆ Back to top](#-table-of-contents)

---

## 25. final Keyword

Prevents further modification, overriding, or inheritance.

### Syntax

```java
final int MAX = 100;        // final variable

final void display() { }    // final method — cannot be overridden

final class Test { }        // final class — cannot be extended
```

[⬆ Back to top](#-table-of-contents)

---

## 26. instanceof

Checks whether an object is an instance of a particular class/subclass/interface.

### Syntax

```java
if (obj instanceof String) {
    System.out.println("String");
}

// Modern pattern matching (Java 16+)
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

[⬆ Back to top](#-table-of-contents)

---

## 27. Important Java Keywords

Reserved words with predefined meaning to the compiler.

### List

```text
class      interface   extends      implements
static     final       abstract     this
super      new         return       throw
throws     try         catch        finally
instanceof synchronized volatile    transient
enum
```

[⬆ Back to top](#-table-of-contents)

---

## 28. String

An **immutable** sequence of characters — every "modification" creates a new String object.

### Syntax

```java
String str = "Hello";
```

| Method               | Example                                |
| -------------------- | ---------------------------------------- |
| `length()`           | `str.length()`                          |
| `charAt()`           | `str.charAt(0)`                         |
| `substring()`        | `str.substring(1, 4)`                   |
| `equals()`           | `str.equals("Hello")`                   |
| `equalsIgnoreCase()` | `str.equalsIgnoreCase("hello")`         |
| `contains()`         | `str.contains("ell")`                   |
| `startsWith()`       | `str.startsWith("He")`                  |
| `endsWith()`         | `str.endsWith("lo")`                    |
| `indexOf()`          | `str.indexOf("l")`                      |
| `lastIndexOf()`      | `str.lastIndexOf("l")`                  |
| `toUpperCase()`      | `str.toUpperCase()`                     |
| `toLowerCase()`      | `str.toLowerCase()`                     |
| `trim()`             | `str.trim()`                            |
| `strip()`            | `str.strip()`                           |
| `replace()`          | `str.replace("H","J")`                  |
| `replaceAll()`       | `str.replaceAll("\\s+", "")`            |
| `split()`            | `str.split(" ")`                        |
| `isEmpty()`          | `str.isEmpty()`                         |
| `isBlank()`          | `str.isBlank()`                         |
| `format()`           | `String.format("%s is %d", "age", 25)`  |
| `valueOf()`          | `String.valueOf(100)`                   |
| `toCharArray()`      | `str.toCharArray()`                     |

### Comparison — syntax

```java
str1 == str2;       // Avoid for content comparison — compares references
str1.equals(str2);  // Correct way to compare content
```

📝 **String Pool:** literals (`"Hello"`) are stored in the **String Constant Pool**; `new String("Hello")` bypasses the pool and creates a new heap object.

[⬆ Back to top](#-table-of-contents)

---

## 29. StringBuilder

A **mutable** sequence of characters, efficient for repeated concatenation. **Not thread-safe.**

### Syntax

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");
sb.append(" Java");

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

### Reverse a String

```java
String reverse = new StringBuilder("Java").reverse().toString();
```

[⬆ Back to top](#-table-of-contents)

---

## 30. StringBuffer

Same API as `StringBuilder` but **synchronized/thread-safe** — slightly slower.

### Syntax

```java
StringBuffer sb = new StringBuffer("Java");
sb.append(" Selenium");
sb.reverse();
```

```text
String        → Immutable
StringBuilder → Mutable, faster, not synchronized
StringBuffer  → Mutable, synchronized (thread-safe)
```

[⬆ Back to top](#-table-of-contents)

---

## 31. Arrays

A fixed-size, ordered collection of elements of the **same type**.

### Declaration — syntax

```java
int[] numbers = {10, 20, 30};
int[] numbers2 = new int[5];

numbers[0];        // access
numbers.length;    // property, not a method
```

### Multi-dimensional arrays

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};
System.out.println(matrix[1][2]);   // 6
```

### `Arrays` utility methods

```java
import java.util.Arrays;

Arrays.sort(numbers);
Arrays.toString(numbers);
Arrays.equals(arr1, arr2);
Arrays.copyOf(numbers, 5);
Arrays.fill(numbers, 10);
Arrays.binarySearch(numbers, 20);
```

[⬆ Back to top](#-table-of-contents)

---

## 32. Wrapper Classes

Object versions of primitive types — used where objects are required (e.g., collections).

### Syntax

```java
int a = 10;
Integer b = a;       // Autoboxing
int c = b;            // Unboxing
```

```text
int → Integer   long → Long     double → Double
float → Float   char → Character boolean → Boolean
byte → Byte     short → Short
```

[⬆ Back to top](#-table-of-contents)

---

## 33. Integer Common Methods

`Integer` is the wrapper class for `int`, providing parsing/comparison utilities.

### Syntax

```java
Integer.parseInt("100");
Integer.valueOf("100");
Integer.max(10, 20);
Integer.min(10, 20);
Integer.compare(10, 20);

int number = Integer.parseInt("100");
```

[⬆ Back to top](#-table-of-contents)

---

## 34. Math Class

A utility class of static methods for common mathematical operations.

### Syntax

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

int random = (int)(Math.random() * 100);
```

[⬆ Back to top](#-table-of-contents)

---

## 35. Generics

Allow classes/interfaces/methods to operate on **types specified by the caller**, giving compile-time type safety.

### Syntax

```java
List<String> names = new ArrayList<>();
List<Integer> numbers = new ArrayList<>();

public <T> void print(T value) {
    System.out.println(value);
}
```

### Bounded type parameters

```java
public <T extends Number> double sum(List<T> list) {
    double total = 0;
    for (T item : list) {
        total += item.doubleValue();
    }
    return total;
}
```

[⬆ Back to top](#-table-of-contents)

---

## 36. ArrayList

A **resizable array** implementation of `List` — allows duplicates, maintains insertion order.

### Syntax

```java
import java.util.ArrayList;

ArrayList<String> names = new ArrayList<>();

names.add("Java");
names.add(1, "API");
names.get(0);
names.set(0, "JavaScript");
names.remove(0);
names.remove("API");
names.contains("Java");
names.size();
names.isEmpty();
names.clear();

for (String name : names) {
    System.out.println(name);
}
```

[⬆ Back to top](#-table-of-contents)

---

## 37. LinkedList

A **doubly-linked list** implementation of `List`/`Deque` — efficient insert/remove, slower random access.

### Syntax

```java
LinkedList<String> list = new LinkedList<>();

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

[⬆ Back to top](#-table-of-contents)

---

## 38. HashSet

Stores **unique values only**, with **no guaranteed order**, backed by a `HashMap`.

### Syntax

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.remove("Java");
set.contains("Python");
set.size();
set.isEmpty();
set.clear();

Set<String> linked = new LinkedHashSet<>();  // preserves insertion order
Set<String> sorted = new TreeSet<>();        // keeps elements sorted
```

[⬆ Back to top](#-table-of-contents)

---

## 39. HashMap

Stores **key-value pairs**; keys unique, iteration order **not guaranteed**.

### Syntax

```java
Map<String, Integer> map = new HashMap<>();

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

for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " " + entry.getValue());
}
for (String key : map.keySet()) { }
for (Integer value : map.values()) { }
```

📝 **Note:** `HashMap` allows **one** `null` key and multiple `null` values; not thread-safe (use `ConcurrentHashMap`).

[⬆ Back to top](#-table-of-contents)

---

## 40. Queue

Holds elements for processing in a specific order — typically **FIFO**.

### Syntax

```java
Queue<String> queue = new LinkedList<>();

queue.offer("A");
queue.peek();   // retrieves without removing
queue.poll();   // retrieves and removes
queue.isEmpty();
queue.size();
```

[⬆ Back to top](#-table-of-contents)

---

## 41. Stack and Deque

A **LIFO** structure. `Deque` (Double-Ended Queue) is preferred over the legacy `Stack` class.

### Syntax

```java
Deque<String> stack = new ArrayDeque<>();

stack.push("A");
stack.push("B");
stack.peek();
stack.pop();
```

📝 `java.util.Stack` still exists (extends `Vector`, synchronized/legacy) — `ArrayDeque` is the modern choice.

[⬆ Back to top](#-table-of-contents)

---

## 42. Collections Quick Comparison

Reference for choosing the right collection.

### Syntax

```java
List<String> l1 = new ArrayList<>();
List<String> l2 = new LinkedList<>();
Set<String>  s1 = new HashSet<>();
Set<String>  s2 = new LinkedHashSet<>();
Set<String>  s3 = new TreeSet<>();
Map<String,String> m1 = new HashMap<>();
Map<String,String> m2 = new LinkedHashMap<>();
Map<String,String> m3 = new TreeMap<>();
```

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

[⬆ Back to top](#-table-of-contents)

---

## 43. Collections Utility Methods

The `Collections` and `Comparator` classes provide static helpers to sort/search/manipulate `List`s.

### Syntax

```java
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
Collections.max(list);
Collections.min(list);
Collections.frequency(list, value);
Collections.binarySearch(list, value);
Collections.swap(list, 0, 1);

// Custom Comparator (very common in interviews)
Collections.sort(names, (a, b) -> a.length() - b.length());
names.sort(Comparator.comparing(String::length));
```

[⬆ Back to top](#-table-of-contents)

---

## 44. Exception Handling

Handles **runtime errors** gracefully using `try`/`catch`/`finally` so the program doesn't crash abruptly.

### Syntax

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());
} finally {
    System.out.println("Executed");
}
```

📝 **Checked vs Unchecked:**
```text
Checked   → checked at compile time (e.g., IOException) — must be declared/handled
Unchecked → RuntimeException subclasses (e.g., ArithmeticException, NullPointerException)
```

### Throw / Throws

```java
throw new IllegalArgumentException("Invalid value");

public void readFile() throws IOException {
}
```

### try-with-resources (auto-closes resources)

```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    String line = br.readLine();
} catch (IOException e) {
    System.out.println(e.getMessage());
}
```

[⬆ Back to top](#-table-of-contents)

---

## 45. Custom Exception

A user-defined exception, created by extending `Exception` (checked) or `RuntimeException` (unchecked).

### Syntax

```java
class InvalidUserException extends Exception {

    public InvalidUserException(String message) {
        super(message);
    }
}
```

```java
throw new InvalidUserException("Invalid user");
```

[⬆ Back to top](#-table-of-contents)

---

## 46. Lambda Expressions

A concise way to represent an anonymous function — mainly used to implement functional interfaces.

### Syntax

```java
(parameters) -> expression
```

```java
() -> System.out.println("No params");   // no parameters

a -> a * 2;                              // single parameter

(a, b) -> a + b;                         // multiple parameters, expression body

(a, b) -> {                              // block body
    int sum = a + b;
    return sum;
};

names.forEach(name -> System.out.println(name));
```

[⬆ Back to top](#-table-of-contents)

---

## 47. Functional Interfaces

An interface with **exactly one abstract method** — the target type for a lambda. Built-ins live in `java.util.function`.

### Syntax

```java
import java.util.function.*;

Predicate<Integer> p = n -> n > 10;
p.test(20);

Function<String, Integer> length = str -> str.length();
length.apply("Java");

Consumer<String> print = str -> System.out.println(str);
print.accept("Java");

Supplier<Double> random = () -> Math.random();
random.get();
```

### Custom functional interface

```java
@FunctionalInterface
interface Calculator {
    int operate(int a, int b);
}

Calculator add = (a, b) -> a + b;
```

[⬆ Back to top](#-table-of-contents)

---

## 48. Java Streams

An abstraction for processing sequences of elements in a **declarative, functional style**.

### Syntax

```java
List<Integer> numbers = Arrays.asList(10, 20, 30, 40);

numbers.stream().filter(n -> n > 20).forEach(System.out::println);
numbers.stream().map(n -> n * 2).forEach(System.out::println);

List<Integer> result = numbers.stream()
        .filter(n -> n > 20)
        .collect(Collectors.toList());

numbers.stream().sorted().forEach(System.out::println);
numbers.stream().distinct().forEach(System.out::println);
numbers.stream().limit(2).forEach(System.out::println);
numbers.stream().skip(2).forEach(System.out::println);

long count = numbers.stream().filter(n -> n > 20).count();

Optional<Integer> first = numbers.stream().filter(n -> n > 20).findFirst();

boolean any = numbers.stream().anyMatch(n -> n > 30);
boolean all = numbers.stream().allMatch(n -> n > 0);
boolean none = numbers.stream().noneMatch(n -> n < 0);

int sum = numbers.stream().reduce(0, Integer::sum);
```

📝 **Note:** Streams are **lazy** and can only be consumed **once**.

[⬆ Back to top](#-table-of-contents)

---

## 49. Optional

A container representing a value that **may or may not be present**, avoiding explicit `null` checks.

### Syntax

```java
Optional<String> a = Optional.of("Java");        // value must NOT be null
Optional<String> b = Optional.empty();            // no value
Optional<String> c = Optional.ofNullable(null);    // may be null, safe

a.isPresent();
a.isEmpty();
a.orElse("Default");
a.orElseGet(() -> "Default");
a.ifPresent(v -> System.out.println(v));
```

[⬆ Back to top](#-table-of-contents)

---

## 50. Date and Time

The `java.time` package (Java 8+) provides an **immutable, thread-safe** API for dates and times.

### Syntax

```java
LocalDate date = LocalDate.now();
LocalTime time = LocalTime.now();
LocalDateTime dateTime = LocalDateTime.now();

date.getYear();
date.getMonth();
date.getDayOfMonth();
date.getDayOfWeek();
date.plusDays(5);
date.minusDays(5);

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
String result = date.format(formatter);
```

[⬆ Back to top](#-table-of-contents)

---

## 51. Regex

A **regular expression** matches, searches, or manipulates character sequences.

### Syntax

```java
String email = "test@gmail.com";
boolean valid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

str.matches(".*Java.*");
String[] parts = str.split(",");
str.replaceAll("\\s+", "");
```

### Pattern & Matcher (for repeated/complex matching)

```java
import java.util.regex.*;

Pattern pattern = Pattern.compile("[0-9]+");
Matcher matcher = pattern.matcher("Order 123 for 456 units");

while (matcher.find()) {
    System.out.println(matcher.group());   // 123, then 456
}
```

[⬆ Back to top](#-table-of-contents)

---

## 52. Scanner

Reads input from the console — used for simple user input.

### Syntax

```java
Scanner scanner = new Scanner(System.in);

String name = scanner.nextLine();
int age = scanner.nextInt();

scanner.close();
```

[⬆ Back to top](#-table-of-contents)

---

## 53. Random

Generates pseudo-random numbers/booleans.

### Syntax

```java
Random random = new Random();

int number = random.nextInt(100);
boolean flag = random.nextBoolean();
double value = random.nextDouble();
```

[⬆ Back to top](#-table-of-contents)

---

## 54. Objects and Arrays Utility Methods

Helper classes for null-safe operations and array manipulation.

### `Objects` — syntax

```java
Objects.equals(a, b);
Objects.isNull(obj);
Objects.nonNull(obj);
Objects.requireNonNull(obj);
```

### `Arrays` — syntax

```java
Arrays.sort(array);
Arrays.toString(array);
Arrays.equals(a, b);
Arrays.copyOf(array, length);
Arrays.asList("A", "B", "C");
```

### length vs length() vs size()

```java
array.length     // property (no parentheses) — for arrays
string.length()  // method — for String
list.size()      // method — for List/Collection
```

[⬆ Back to top](#-table-of-contents)

---

## 55. File Handling — Text Files

Java's `java.nio.file` package (`Path`/`Files`) is the modern way to read/write files.

### Syntax

```java
import java.nio.file.*;

Path path = Path.of("data.txt");

String content = Files.readString(path);
Files.writeString(path, "Hello Java");
Files.exists(path);
Files.createFile(path);
Files.delete(path);
Files.copy(source, target);
Files.move(source, target);
```

[⬆ Back to top](#-table-of-contents)

---

## 56. Reading Excel Files *(added)*

Java has no built-in Excel API — the standard library is **Apache POI** (`org.apache.poi`), which reads/writes both `.xls` (HSSF) and `.xlsx` (XSSF) files. Very commonly used in SDET/automation frameworks for data-driven testing.

### Maven dependency

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

### Syntax

```java
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

try (FileInputStream fis = new FileInputStream("data.xlsx");
     Workbook workbook = new XSSFWorkbook(fis)) {

    Sheet sheet = workbook.getSheetAt(0);

    for (Row row : sheet) {
        for (Cell cell : row) {
            System.out.print(cell.toString() + "\t");
        }
        System.out.println();
    }

    // Read a specific cell
    String value = sheet.getRow(0).getCell(0).getStringCellValue();

} catch (Exception e) {
    e.printStackTrace();
}
```

📝 **Package:** `org.apache.poi.ss.usermodel` (generic API) / `org.apache.poi.xssf.usermodel` (for `.xlsx`) / `org.apache.poi.hssf.usermodel` (for legacy `.xls`).

[⬆ Back to top](#-table-of-contents)

---

## 57. Reading JSON Files *(added)*

JSON parsing isn't in core Java either — the most common libraries are **Jackson** (`com.fasterxml.jackson`), **Gson** (`com.google.gson`), and **org.json**.

### Maven dependency (Jackson)

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version>
</dependency>
```

### Syntax — Jackson (most common in frameworks like Rest Assured)

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

ObjectMapper mapper = new ObjectMapper();

// Read JSON file into a JsonNode tree
JsonNode root = mapper.readTree(new File("data.json"));
String name = root.get("name").asText();

// Read JSON file directly into a Java object (POJO)
User user = mapper.readValue(new File("data.json"), User.class);

// Convert an object to a JSON string
String json = mapper.writeValueAsString(user);
```

### Syntax — org.json (lightweight alternative)

```java
import org.json.JSONObject;
import java.nio.file.*;

String content = new String(Files.readAllBytes(Paths.get("data.json")));
JSONObject obj = new JSONObject(content);
String name = obj.getString("name");
```

📝 **Package:** `com.fasterxml.jackson.databind` (Jackson) / `com.google.gson` (Gson) / `org.json` (org.json).

[⬆ Back to top](#-table-of-contents)

---

## 58. Reading Properties Files *(added)*

`.properties` files (key=value pairs) are typically used for configuration (e.g., `config.properties` storing URLs, credentials, environment settings) — handled with the built-in `java.util.Properties` class, no external library needed.

### Example `config.properties`

```properties
url=https://example.com
username=admin
timeout=30
```

### Syntax

```java
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

Properties props = new Properties();

try (FileInputStream fis = new FileInputStream("config.properties")) {
    props.load(fis);

    String url = props.getProperty("url");
    String username = props.getProperty("username");
    String timeout = props.getProperty("timeout", "10");  // default value if key is missing

} catch (IOException e) {
    e.printStackTrace();
}
```

### Writing to a properties file

```java
try (FileOutputStream fos = new FileOutputStream("config.properties")) {
    props.setProperty("url", "https://newsite.com");
    props.store(fos, "Updated config");
} catch (IOException e) {
    e.printStackTrace();
}
```

📝 **Package:** `java.util.Properties` (built into core Java — `java.util`).

[⬆ Back to top](#-table-of-contents)

---

## 59. Maven Build Lifecycle *(added)*

**Maven** is a build automation and dependency management tool for Java projects, configured via `pom.xml`. It defines a standard **build lifecycle** made of ordered **phases** — running a phase automatically runs all phases before it.

### Default lifecycle phases (in order)

```text
validate  → validate the project is correct and all necessary info is available
compile   → compile the source code
test      → run unit tests (using a testing framework, e.g. JUnit/TestNG)
package   → package compiled code into a distributable format (.jar / .war)
verify    → run checks on results of integration tests
install   → install the package into the local Maven repository (~/.m2)
deploy    → copy the final package to a remote repository for sharing
```

### Syntax — common CLI commands

```bash
mvn validate
mvn compile
mvn test
mvn package
mvn verify
mvn install
mvn deploy

mvn clean            # deletes the target/ directory (separate "clean" lifecycle)
mvn clean install    # cleans, then runs the full lifecycle up to install
mvn test -Dtest=LoginTest   # run a single test class
```

📝 **Note:** Maven has three built-in lifecycles — `clean`, `default` (the phases above), and `site`. In SDET/automation projects, `mvn clean test` and `mvn clean install` are the most frequently used commands to run test suites via CI/CD (Jenkins).

[⬆ Back to top](#-table-of-contents)

---

## 60. Important Interview Differences

### `==` vs `.equals()`

```text
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

### Abstract class vs Interface

```text
Abstract class → constructors/fields allowed; a class can extend only ONE abstract class
Interface      → default/static methods since Java 8; a class can implement MULTIPLE interfaces
```

### Checked vs Unchecked Exception

```text
Checked   → verified at compile time (must be handled/declared), e.g. IOException
Unchecked → RuntimeException subclasses, not enforced at compile time, e.g. NullPointerException
```

[⬆ Back to top](#-table-of-contents)

---

## 61. Most Important Methods to Memorize

For **SDET/Automation interviews**, prioritize these:

### String
```java
length() charAt() substring() equals() equalsIgnoreCase() contains()
indexOf() lastIndexOf() startsWith() endsWith() split() trim() strip()
replace() replaceAll() toUpperCase() toLowerCase() isEmpty() isBlank()
```

### Array
```java
Arrays.sort() Arrays.toString() Arrays.equals() Arrays.copyOf()
Arrays.fill() Arrays.binarySearch()
```

### List
```java
add() get() set() remove() contains() size() isEmpty() clear() indexOf()
```

### Set
```java
add() remove() contains() size() isEmpty() clear()
```

### Map
```java
put() get() getOrDefault() putIfAbsent() containsKey() containsValue()
remove() replace() keySet() values() entrySet()
```

### Collections
```java
sort() reverse() max() min() shuffle() frequency() binarySearch()
```

### Math
```java
max() min() abs() pow() sqrt() round() floor() ceil() random()
```

### Java 8+ / Streams
```java
stream() filter() map() sorted() distinct() limit() skip() count()
findFirst() anyMatch() allMatch() noneMatch() collect() reduce() forEach()
```

### Date/Time
```java
now() plusDays() minusDays() getYear() getMonth() getDayOfMonth() format()
```

[⬆ Back to top](#-table-of-contents)

---

## 62. SDET Priority Order

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
24. Excel / JSON / Properties file handling
25. Maven build lifecycle
```

**Most important for coding rounds:**
`String → Array → ArrayList → HashMap → loops → methods → OOP → Exception Handling → Streams → Lambda`.

[⬆ Back to top](#-table-of-contents)

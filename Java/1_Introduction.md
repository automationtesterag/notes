# Introduction to Java

===================================================================================================

Java is a popular, versatile, and object-oriented programming language first released by Sun Microsystems in 1995. It's designed to be platform-independent, following the principle of "Write Once, Run Anywhere" (WORA).

#### Key features of Java:
1. Object-Oriented: Java is built around the concept of "objects" that contain data and code. This paradigm helps in organizing complex programs and promotes code reusability.
2. Platform Independence: Java code is compiled into bytecode, which can run on any device with a Java Virtual Machine (JVM), regardless of the underlying hardware and operating system.
3. Robustness: Java's strong type checking, exception handling, and automatic memory management (garbage collection) make it a robust language for developing error-free, reliable applications.
4. Security: Java is designed with security in mind, making it suitable for developing systems that require a high level of security, such as banking applications.
5. Multithreading: Java provides built-in support for multithreaded programming, allowing developers to create efficient, concurrent applications.
6. Rich Standard Library: Java comes with a comprehensive set of standard libraries that provide ready-to-use functions for various programming tasks.


#### Here's a concise list of major Java versions released so far:
```
JDK 1.0 (1996)
JDK 1.1 (1997)
J2SE 1.2 (1998)
J2SE 1.3 (2000)
J2SE 1.4 (2002)
J2SE 5.0 (2004)
Java SE 6 (2006)
Java SE 7 (2011)
Java SE 8 (LTS) (2014)
Java SE 9 (2017)
Java SE 10 (2018)
Java SE 11 (LTS) (2018)
Java SE 12 (2019)
Java SE 13 (2019)
Java SE 14 (2020)
Java SE 15 (2020)
Java SE 16 (2021)
Java SE 17 (LTS) (2021)
Java SE 18 (2022)
Java SE 19 (2022)
Java SE 20 (2023)
Java SE 21 (LTS) (2023)
```

# Java Architecture

===================================================================================================
![java-architecture.png](Images/java-architecture.png)
Java architecture consists of several components that work together to enable the "Write Once, Run Anywhere" principle. The main components are:

1. Java Development Kit (JDK)
2. Java Runtime Environment (JRE)
3. Java Virtual Machine (JVM)

Let's explore each of these in detail:

### 1. Java Development Kit (JDK)

The JDK is a software development environment used for developing Java applications. It includes:

#### a) Java Compiler (javac)
- Converts Java source code (.java files) into Java bytecode (.class files).
- Usage: `javac MyProgram.java`

#### b) Java Runtime Environment (JRE)
- Necessary for running Java applications.

#### c) Development Tools
- **javadoc:** Generates HTML documentation from Java source code.
- **jar:** Creates and manages JAR (Java Archive) files.
- **jdb:** Java Debugger for finding and fixing bugs in Java programs.

#### 2. Java Runtime Environment (JRE)

The JRE is the runtime environment in which Java programs run. It contains:

#### a) Java Virtual Machine (JVM)
- The core component that runs Java bytecode.

#### b) Java Class Libraries
- A set of standard libraries providing common programming functions.

#### c) Java Class Loader
- Loads Java classes and interfaces.

### 3. Java Virtual Machine (JVM)

The JVM is an abstract computing machine that enables a computer to run a Java program. It has several components:

#### a) Class Loader Subsystem
- **Bootstrap Class Loader:** Loads core Java libraries.
- **Extension Class Loader:** Loads classes from ext directory.
- **Application Class Loader:** Loads classes from the application classpath.

#### b) Runtime Data Areas
- **Method Area:** Stores class structures, methods, and constants.
- **Heap:** Runtime data area where objects are allocated.
- **Java Stacks:** Stores local variables and partial results.
- **PC Registers:** Stores the address of the current executing instruction.
- **Native Method Stacks:** Used for native methods.

#### c) Execution Engine
- **Interpreter:** Reads bytecode and executes instructions.
- **Just-In-Time (JIT) Compiler:** Compiles frequently executed bytecode to native machine code.
- **Garbage Collector:** Automatically frees up memory by deleting unused objects.

#### d) Native Method Interface (JNI)
- Enables Java code to call and be called by native applications and libraries written in other languages like C, C++.

### Java Program Execution Flow

1. Java source code (.java file) is written by the programmer.
2. The Java compiler (javac) compiles the source code into bytecode (.class file).
3. The bytecode is loaded into memory by the Class Loader.
4. The bytecode verifier checks the code for any security violations.
5. The JVM interpreter reads the bytecode and executes it.
6. For frequently used code, the JIT compiler compiles bytecode to native machine code for faster execution.

### Key Points

- Java's architecture ensures platform independence.
- The JVM acts as a layer between the compiled Java program and the operating system.
- The JIT compiler significantly improves performance by converting bytecode to native machine code.
- Automatic memory management through garbage collection reduces the risk of memory leaks.

# Installation

===================================================================================================
### Windows
#### a) Download JDK:

- Go to the official Oracle website or adopt OpenJDK website.
- Choose the latest JDK version for Windows.
- Download the installer (.exe file).

#### b) Install JDK:

- Run the downloaded installer.
- Follow the installation wizard, selecting the installation directory.
- Let the installer set the PATH variable.

#### c) Set environment variables:

```
JAVA_HOME = "C:\Program Files\Java\jdk-21"
path = %JAVA_HOME%\bin
```
#### d) Verify installation:
- Open Command Prompt.
- Type "java -version" and "javac -version" to check if Java is correctly installed.

### MAC

#### a) Download JDK:

- Visit the official Oracle website or adopt OpenJDK website.
- Download the macOS .dmg file for the latest JDK version.

#### b) Install JDK:

- Open the downloaded .dmg file.
- Double-click the .pkg file inside and follow the installation wizard.

#### c) Set environment variables:

- Open Terminal.
- Edit your shell profile file (.bash_profile, .zshrc, etc.) using a text editor.

open profile file `nano ~/.zshrc`
Add following
```
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home"
export PATH=$JAVA_HOME/bin:$PATH
```
Save the file and run `source ~/.zshrc` (or your corresponding profile file).

#### d) Verify installation:

In Terminal, type `java -version` and `javac -version`.

### Linux (Ubuntu/Debian):
Update package index: `sudo apt update`

Install OpenJDK:`sudo apt install default-jdk`

Set environment variables:
- Open Terminal.
- Edit your shell profile file (.bashrc, .zshrc, etc.) using a text editor and Add these lines.
```
export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
export PATH=$JAVA_HOME/bin:$PATH
```
Save the file and run `source ~/.bashrc` (or your corresponding profile file).

#### d) Verify installation:

In Terminal, type `java -version` and `javac -version`.

# Basic Structure of Program
```
public class Demo
{
public static void main(String args[])
{
System.out.println("This is my first program");
}
}
```

### Every program has 3 main parts

1. **Class Declaration**
    - Example:
        ```
        public class Sample
        public class Demo
        ```
    - It consists of 3 things:
        1. **Access Modifier**: It indicates whether the program is accessible to other users or not.
            - There are 4 access modifiers in Java: `public`, `private`, `protected`, and `default`.
            - In the above section, the class is public, so it is freely accessible.
            - All access modifiers are in lowercase.
        2. **Class**: `class` is a keyword (reserved word or predefined word) in Java.
            - Every program must start with the `class` keyword.
            - All keywords must start with a lowercase letter, so `class` starts with a small "c".
        3. **Class Name**: Every class has a name, i.e., class name, program name, or file name.
            - The class name for standard should start with a capital letter.
            - The Java file name and class name must be the same for remembering purposes.
            - The class name can only be a combination of A-Z, a-z, 0-9, $, _.
            - Note: `{}` ----> scope of class.

2. **Defining Main Method**
    - Example:
        ```
        public static void main(String args[])
        {
            // LOGIC OF APP
        }
        ```
    - It consists of 5 parts:
        1. **Access Modifier**
        2. **Non-Access Modifier**
        3. **Return Type**
        4. **Method Name**
        5. **Command Line Arguments**

    1. **Access Modifier**
        - `public`:
            - `main()` is public so that it is accessible to everyone freely.

    2. **Non-Access Modifier**
        - We have 4 types of NAM: `static`, `non-static`, `final`, & `abstract`.
        - `static`: Method is accessible without object creation.
        - `non-static`: Method is accessible with object creation.

    3. **Return Type**
        - `void`: It indicates that the method is not going to return any value.

    4. **Method Name**
        - If any word contains `()`, we can identify it as a method.
        - Example: `main()`, `run()`, `display()`, etc.
        - `main` is the name of the method.

    5. **Command Line Arguments**
        - `String args[]`
            - Theoretically, we will call it as string arguments of array.
            - `String` - "S" is capital (predefined class).
            - This statement can be written in 3 ways:
                1. `String args[]`
                2. `String []args`
                3. `String[] args`
            - Note: In the syntax of the main method, only "String" starts with a capital letter, remaining all words start with small letters.

3. **Third Part - Printing Statement**
    - ```
      System.out.println("This is my sample program");
      ```
    - `System`: It is a predefined class.
    - `out`: It is an object (predefined).
    - `println()`: It is a method (predefined).
    - In simple terms, `println()` is accessed through the `out` object, but the `out` object is present in the `System` class.
    - `System` is a class that contains the `out` object, and the `out` object is referring to `println()`.
    - Whatever we give in double quotes, that message will be printed as it is.
    - variations in printing message
      println()--->print next message in new line.
      print() --->print next message in same line

# Comments

===================================================================================================

Comments in Java are used to add explanatory notes or disable code temporarily. They are ignored by the compiler and don't affect the program's execution. Java supports three types of comments:

## 1. Single-line Comments

- Start with two forward slashes (`//`)
- Everything after `//` on that line is treated as a comment

Example:
```java
// This is a single-line comment
int x = 5; // This comment is at the end of a line of code
```
## 2. Multi-line Comments

- Start with `/*`and end with `*/`
- Can span multiple lines
- Useful for longer explanations or commenting out blocks of code

Example:
```java
/* This is a multi-line comment.
   It can span several lines.
   Useful for longer explanations. */
int y = 10;
```

## 3. Javadoc Comments

- Start with `/**` and end with `*/`
- Used to generate documentation for Java code
- Can include special tags for formatting and linking

Example:
```java
/**
 * This is a Javadoc comment.
 * @param args command line arguments
 * @return void
 */
public static void main(String[] args) {
    // Method body
}
```

## More Examples
```
// Calculate age based on birth year
int age = currentYear - birthYear;

/* 
 * This algorithm uses the Sieve of Eratosthenes to find prime numbers.
 * Time complexity: O(n log log n)
 */

/**
 * Sorts the array using quicksort algorithm.
 * @param arr the array to be sorted
 * @param low starting index
 * @param high ending index
 */
public void quickSort(int[] arr, int low, int high) {
    // Method implementation
}

// TODO: Implement input validation for user data
```

## Data Types and Variables

===================================================================================================
### Data
Any information is called data.
- **Examples:** name, age, height, marks, percentage, salary, etc.

### Data Types
Defines the type of data.
- Divided into 2 types:
    1. Primitive (System-defined)
    2. Non-primitive (User-defined)

### Primitive Data Types
- These are system-defined data types.
- They have fixed memory sizes.
- There are 8 primitive data types:

| Name    | Size     | Examples                            | Default Values |
|---------|----------|-------------------------------------|----------------|
| byte    | 1 byte   | 10, 2, 5 (127 is max)               | 0              |
| boolean | 1 byte or no size | true or false              | false          |
| short   | 2 bytes  | 100, 220 (32,768 is max)            | 0              |
| char    | 2 bytes  | A, a, ...                           | empty space    |
| int     | 4 bytes  | 1, 2, 777 (2,147,483,647 is max)    | 0              |
| float   | 4 bytes  | 0.2, 0.3, 33.666...                 | 0.0            |
| long    | 8 bytes  | 33333333, 6565655...                | 0              |
| double  | 8 bytes  | 0.343434343, 99.5555555...          | 0.0            |

**Notes:**
- When 4-6 digits of accuracy are needed, use `float`; otherwise, use `double`.
- For values exceeding 32 bits in `long`, append `l` to the value.
    - **Example:** `long contact = 9878674534l;`

### Non-primitive Data Types
- These do not have fixed memory sizes.

**Examples:**

- **String:** Represents a group of characters.
    - **Example:** "java", "manual testing"
- **Arrays:**
    - **Example:** `{10, 20, ... 100}`

### Variables:

Variables are used to store the data for printing or using it in future.
#### 1.Variable Declaration

- Syntax for dec a variable is `AccessModifier Datatype variablename;`

Ex:
- public int a;
- public float b;
- public char ch;
- public String s;

variable name can be a combination of a-z,A-Z,0-9,$ and _.

Whenever we declare a variable one memory block will get created

#### 2. Variable Initialisation

Syntax: `Variablename=value;`

- a=100;
- b=0.3333f;//mandatory to write f
- ch='A';//mandatory to give ''
- s="java";//mandatory to give ""

WE CAN DECLARE AND INITIALISE A VARIABLE IN SINGLE STATEMENT ALSO.

syntax: `Access Modifier Datatype varname=value;`

public int a=22;

#### Examples

1. int a=22,b=33;//valid
2. int a=33,b;//valid
3. float percentage=60.0;//invalid- f is missing
4. char ch='AB';//invalid-character can't be more than one char
5. float h=100f;//valid--100.0
6. int i=0.334;//invalid--integer can't store decimal values
7. String s="123";//valid--->System.out.println("123");
8. String d="3334+ghijk"//valid
9. double marks=100.3434d//valid---->in double d is optional
10. long number=93939393939l//valid---> in long l is optional

# Operations in Java

===================================================================================================

Java supports various types of operations that can be performed on variables and values. These operations are fundamental to programming and allow you to manipulate data in your programs.

## 1. Arithmetic Operations

Arithmetic operations are used for mathematical calculations.

### Basic Arithmetic Operators:
- Addition (`+`)
- Subtraction (`-`)
- Multiplication (`*`)
- Division (`/`)
- Modulus (`%`) - returns the remainder of a division

```
int a = 10, b = 3;
System.out.println(a + b);  // 13
System.out.println(a - b);  // 7
System.out.println(a * b);  // 30
System.out.println(a / b);  // 3 (integer division)
System.out.println(a % b);  // 1
```

#### Addition
1. `int + int` (int/float/double/long/char/short/byte)
2. `char + char` (int/float/double/long/char/short/byte)


Java provides Unicode values for every character:
- A-65, B-66, C-67, ..., Z-90
- a-97, b-98, c-99, ..., z-122

### Examples
```
'A' + 65  // 65 + 65 --> 130
'a' + 'Z' // 97 + 90 --> 187

char ch = 65;
System.out.println(ch); // A
```
#### Concatenation
If any one operand is a String, the + operator will always act as concatenation.
```
String s = "java";
int a = 123;
System.out.println(s + a); // java123
System.out.println("I am " + s + " developer"); // I am java developer

// After concatenation, the result will always be a string.
System.out.println(123 + " "); // "123"
```

## 2. Relational Operations
Relational operations compare two values and return a boolean result.

- Equal to (==)
- Not equal to (!=)
- Greater than (>)
- Less than (<)
- Greater than or equal to (>=)
- Less than or equal to (<=)

```
int p = 5, q = 10;
System.out.println(p == q);  // false
System.out.println(p != q);  // true
System.out.println(p > q);   // false
System.out.println(p < q);   // true
System.out.println(p >= q);  // false
System.out.println(p <= q);  // true
```

## Logical Operators

#### AND

- It compares two inputs, and if both inputs are true, then the output is true; otherwise, the output is false.
- It is represented as `&&` (in the examples below, 0 indicates false, and 1 indicates true).

| a | b | a && b |
|---|---|--------|
| 0 | 0 | 0      |
| 0 | 1 | 0      |
| 1 | 0 | 0      |
| 1 | 1 | 1      |

#### OR

- It compares two inputs, and if any one input is true, then the output is true; otherwise, the output is false.
- It is represented as `||` (in the examples below, 0 indicates false, and 1 indicates true).

| a | b | a \|\| b |
|---|---|--------|
| 0 | 0 | 0      |
| 0 | 1 | 1      |
| 1 | 0 | 1      |
| 1 | 1 | 1      |

### NOT

- It inverts the input value.

| input | output |
|-------|--------|
| 0     | 1      |
| 1     | 0      |

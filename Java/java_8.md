# What you will learn?

1. Lambda Expressions
2. Functional Interfaces and Predefined Functional Interfaces
3. Method References and Types of Method References
4. Optional Class to Avoid NullPointerExceptions
5. Default and Static Methods in Interfaces
6. Stream API - filter, map, sorting, finding, collecting

# Lambda Expressions

- Lambda expressions were introduced in Java 8
- Lambda expression is an anonymous function. It's a function without name and does not belongs to any class
- Lambda expression is mainly used to implement functional interfaces.

**Syntax** `() -> {}`

# Functional Interfaces

- Functional interfaces were introduced in Java 8
- An Interface that contains exactly one abstract method is known as a functional interface.
- Functional interface can have any number of default, static methods but can contain only one abstract method.

Examples:

```JAVA
  interface MyFunctionalInterface {
  public void execute();
  }
```

```JAVA
interface MyFunctionalInterface {
    // Only one abstract method
    public void execute();

    // Default method
    public default void print(String text) {
        System.out.println(text);
    }

    // Static method
    public static void print(String text, PrintWriter writer) {
        writer.write(text);
    }
}

```

Note: The `public` modifier in interface methods is redundant because all methods in an `interface` are implicitly public by default.

### Lambda vs Method

Method is always belongs to class or object in Java where as Lambda does not belongs to any class or object

Lambda expression in Java has these main parts:

- **No Name** - As Lambda is an anonymous function so no need to have a name
- **Parameter list**
- **Body** - This is the main part of the function
- **No Return Type** - You don't need mention the return type in the lambda expression. The Java 8+ compiler is infer the return type by checking the code

```JAVA
Addable withLambdaD = (int a, int b) ー＞ (a+b);
```

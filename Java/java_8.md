# Java 8
1. Streams
2. Functional Interface

# Streams
```java
package string;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {
    public static void main(String[] args) {
        /*
        // ============================================================
        // STREAMS
        // ============================================================
        Streams in Java was introduced in Java 8 to simplify the processing of collections and data.
        It provides a modern and efficient way to perform operations on groups of objects using a functional programming approach.
        Streams help developers write cleaner, shorter, and more readable code for data processing tasks

        Allows processing of data without modifying the original collection.
        supports parallel processing, which can improve performance for large datasets.
        reduces boilerplate code and makes it easier to express complex data transformations.

        input ---> Stream --> intermediate operations --> terminal operation --> output

        ============================================================
        1. STREAM CREATION
        ============================================================
        Streams can be created from various data sources, such as collections, arrays, or I/O channels.
        Examples:
        - From Collections: List, Set, etc.
        - From Arrays: Arrays.stream(array)
        - From values: Stream.of(value1, value2, ...)
        - generated streams: Stream.generate() or Stream.iterate()


        Different Operations in Streams:
        1. Intermediate Operations: These operations transform a stream into another stream.
        They are lazy in nature, meaning they are not executed until a terminal operation is invoked. Examples include:
            - filter(): Filters elements based on a condition.
            - map(): Transforms elements into another form.
            - flatMap(): Flattens nested structures into a single stream.
            - sorted(): Sorts the elements in the stream.
            - distinct(): Removes duplicate elements.
            - peek(): Performs an action on each element without modifying the stream.
            - limit(): Limits the number of elements in the stream.
            - skip(): Skips a specified number of elements in the stream.
            - concat(): Concatenates two streams into one.
            - mapToInt(), mapToDouble(), mapToLong(): Converts elements to primitive streams (IntStream, DoubleStream, LongStream).


        2. Terminal Operations: These operations produce a result or a side-effect and mark the end of the stream processing.
          - collect(): Collects the elements into a collection or another data structure.
          - forEach(): Performs an action on each element of the stream.
          - reduce(): Reduces the elements to a single value using an accumulator function.
          - count(): Counts the number of elements in the stream.
          - anyMatch(): Checks if any element matches a given predicate.
          - allMatch(): Checks if all elements match a given predicate.
          - noneMatch(): Checks if no elements match a given predicate.
          - findFirst(): Returns the first element of the stream, if present.
          - findAny(): Returns any element of the stream, if present.

        ============================================================
        collectors
        ============================================================
        Collectors are utility classes in Java that provide various methods to collect the elements of a stream into different data structures
        or perform aggregation operations.
        They are part of the java.util.stream package and are commonly used in conjunction with the collect() terminal operation of streams.
        Commonly used collectors include:
        - toList(): Collects elements into a List.
        - toSet(): Collects elements into a Set.
        - toMap(): Collects elements into a Map.
        - joining(): Concatenates elements into a single String.
        - counting(): Counts the number of elements in the stream.
        - groupingBy(): Groups elements based on a classifier function.
        - partitioningBy(): Partitions elements into two groups based on a predicate.
        - mapping(): Applies a mapping function to elements before collecting them.
         */

        streamCreation();
        streamIntermediateOperations();
        streamTerminalOperations();
        streamCollectors();
    }

    public static void streamCreation() {
        // ============================================================
        // STREAM CREATION
        // ============================================================

        // 1. From Collections
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        numbers.stream().forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30 40 50

        System.out.println("\n");

        // 2. From Arrays
        int[] array = {1, 2, 3, 4, 5};
        Arrays.stream(array).forEach(n -> System.out.print(n + " "));
        // Output: 1 2 3 4 5

        System.out.println("\n");

        // 3. From Values
        Stream.of(100, 200, 300).forEach(n -> System.out.print(n + " "));
        // Output: 100 200 300

        // 4. Generated Streams
        Stream.generate(Math::random)
                .limit(5)
                .forEach(n -> System.out.print(n + " "));
        // Output: 5 random numbers
            }
    public static void streamIntermediateOperations() {

        // ============================================================
        // INTERMEDIATE OPERATIONS
        // ============================================================

        List<Integer> numbers =
                Arrays.asList(10, 20, 30, 40, 50, 20, 30);

        // 1. filter() - Select elements based on a condition
        System.out.print("filter(): ");
        numbers.stream()
                .filter(n -> n > 25)
                .forEach(n -> System.out.print(n + " "));
        // Output: 30 40 50 30

        System.out.println("\n");

        // 2. map() - Transform each element
        System.out.print("map(): ");
        numbers.stream()
                .map(n -> n * 2)
                .forEach(n -> System.out.print(n + " "));
        // Output: 20 40 60 80 100 40 60

        System.out.println("\n");

        // 3. flatMap() - Flatten nested lists into a single stream
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );

        System.out.print("flatMap(): ");
        nestedList.stream()
                .flatMap(list -> list.stream())
                .forEach(n -> System.out.print(n + " "));
        // Output: 1 2 3 4 5 6

        System.out.println("\n");

        // 4. sorted() - Sort elements in ascending order
        System.out.print("sorted(): ");
        numbers.stream()
                .sorted()
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 20 30 30 40 50

        System.out.println("\n");

        // 5. distinct() - Remove duplicate elements
        System.out.print("distinct(): ");
        numbers.stream()
                .distinct()
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30 40 50

        System.out.println("\n");

        // 6. peek() - Inspect elements during stream processing
        System.out.println("peek():");
        numbers.stream()
                .filter(n -> n > 30)
                .peek(n -> System.out.println("Processing: " + n))
                .collect(Collectors.toList());
        // Output:
        // Processing: 40
        // Processing: 50

        // peek() executes here because collect() is a terminal operation.

        System.out.println();

        // 7. limit() - Take only the first 3 elements
        System.out.print("limit(): ");
        numbers.stream()
                .limit(3)
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30

        System.out.println("\n");

        // 8. skip() - Skip the first 3 elements
        numbers.stream()
                .skip(3)
                .forEach(n -> System.out.print(n + " "));
        // Output: 40 50 20 30

        // 9. concat() - Concatenate two streams
        System.out.print("\nconcat(): ");
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        Stream<Integer> stream2 = Stream.of(4, 5, 6);
        Stream.concat(stream1, stream2)
                .forEach(n -> System.out.print(n + " "));
        // Output: 1 2 3 4 5 6

        // 10. mapToInt(), mapToDouble(), mapToLong() - Convert to primitive streams
        System.out.print("\nmapToInt(): ");
        numbers.stream()
                .mapToInt(n -> n)
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30 40 50 20 30

        numbers.stream()
                .mapToDouble(n -> n)
                .forEach(n -> System.out.print(n + " "));
        // Output: 10.0 20.0 30.0 40.0 50.0 20.0 30.0

        numbers.stream()
                .mapToLong(n -> n)
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30 40 50 20 30
    }

    public static void streamTerminalOperations() {

        // ============================================================
        // TERMINAL OPERATIONS
        // ============================================================

        List<Integer> numbers =
                Arrays.asList(10, 20, 30, 40, 50, 20, 30);

        // 1. collect() - Collect elements into a List
        List<Integer> result = numbers.stream()
                .filter(n -> n > 20)
                .collect(Collectors.toList());

        System.out.println("collect(): " + result);
        // Output: [30, 40, 50, 30]


        // 2. forEach() - Perform an action on each element
        System.out.print("forEach(): ");
        numbers.stream()
                .forEach(n -> System.out.print(n + " "));
        // Output: 10 20 30 40 50 20 30

        System.out.println();


        // 3. reduce() - Combine elements into a single value
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("reduce(): " + sum);
        // Output: 200


        // 4. count() - Count the total number of elements
        long count = numbers.stream()
                .count();

        System.out.println("count(): " + count);
        // Output: 7


        // 5. anyMatch() - True if at least one element matches
        boolean any = numbers.stream()
                .anyMatch(n -> n > 45);

        System.out.println("anyMatch(): " + any);
        // Output: true


        // 6. allMatch() - True if every element matches
        boolean all = numbers.stream()
                .allMatch(n -> n > 0);

        System.out.println("allMatch(): " + all);
        // Output: true


        // 7. noneMatch() - True if no element matches
        boolean none = numbers.stream()
                .noneMatch(n -> n < 0);

        System.out.println("noneMatch(): " + none);
        // Output: true


        // 8. findFirst() - Return the first element matching a condition
        numbers.stream()
                .filter(n -> n > 25)
                .findFirst()
                .ifPresent(n -> System.out.println("findFirst(): " + n));
        // Output: 30


        // 9. findAny() - Return any element from the stream
        numbers.stream()
                .findAny()
                .ifPresent(n -> System.out.println("findAny(): " + n));
        // Output: 10 for this sequential stream

        // 10. min() - Find the smallest element
        System.out.println("min(): "
                + numbers.stream()
                .min(Integer::compareTo)
                .orElse(0));
        // Output: 10

        // 11. max() - Find the largest element
        System.out.println("max(): "
                + numbers.stream()
                .max(Integer::compareTo)
                .orElse(0));
        // Output: 50

        // 12. toArray() - Convert the stream into an array
        Integer[] array = numbers.stream()
                .toArray(Integer[]::new);

        System.out.println("toArray(): "
                + Arrays.toString(array));

        // 13. forEachOrdered() - Preserve encounter order
        System.out.print("forEachOrdered(): ");
        numbers.parallelStream()
                .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();
    }
    public static void streamCollectors() {

        System.out.println("\n===== COLLECTORS =====");

        List<String> names = Arrays.asList(
                "Ravi", "Anu", "Ravi", "Kiran", "Anu"
        );

        // 1. toList() - Collect elements into a List
        List<String> list = names.stream()
                .collect(Collectors.toList());

        System.out.println("toList(): " + list);

        // 2. toSet() - Collect unique elements into a Set
        Set<String> set = names.stream()
                .collect(Collectors.toSet());

        System.out.println("toSet(): " + set);

        // 3. joining() - Join strings with a delimiter
        String joined = names.stream()
                .collect(Collectors.joining(", "));

        System.out.println("joining(): " + joined);

        // 4. groupingBy() - Group names by their length
        Map<Integer, List<String>> grouped = names.stream()
                .collect(Collectors.groupingBy(String::length));

        System.out.println("groupingBy(): " + grouped);

        // 5. partitioningBy() - Divide into true and false groups
        Map<Boolean, List<String>> partitioned = names.stream()
                .collect(Collectors.partitioningBy(
                        name -> name.length() > 3
                ));

        System.out.println("partitioningBy(): " + partitioned);

        // 6. counting() - Count occurrences of each name
        Map<String, Long> frequency = names.stream()
                .collect(Collectors.groupingBy(
                        name -> name,
                        Collectors.counting()
                ));

        System.out.println("counting(): " + frequency);

        // 7. mapping() - Transform values while collecting
        Map<Integer, List<String>> upperCaseNames = names.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(
                                String::toUpperCase,
                                Collectors.toList()
                        )
                ));

        System.out.println("mapping(): " + upperCaseNames);

        // 8. toMap() - Convert elements into key-value pairs
        Map<String, Integer> nameLengths = names.stream()
                .distinct()
                .collect(Collectors.toMap(
                        name -> name,
                        String::length
                ));

        System.out.println("toMap(): " + nameLengths);
    }

}

```

# Functional Interface
```java
package string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/*
    Functional Interface: An interface that contains only one abstract method.
    It can have multiple default or static methods.

    Requirements for a functional interface:
    1. It must have exactly one abstract method.
    2. It can have any number of default or static methods.
    3. It can declare methods from the Object class (like toString(), equals(), hashCode())
       without affecting its functional interface status.
    4. It can be annotated with @FunctionalInterface, but this is optional.
       The annotation helps to catch errors at compile time if the interface does not meet
       the functional interface requirements.
    5. It can be used as the target for lambda expressions and method references.

    Advantages of functional interfaces:
    1. They enable functional programming in Java, allowing for more concise and expressive code.
    2. They can be used with lambda expressions and method references, making it easier to pass behavior as parameters.
    3. They promote code reusability and modularity by allowing developers to define behavior in a single place and reuse it across different parts of the application.


    Types of built-in Functional Interfaces (java.util.function):
    1. Predicate<T>   : takes one argument, returns boolean.
    2. Function<T, R> : takes one argument, returns a result.
    3. Supplier<T>    : takes no argument, returns a result.
    4. Consumer<T>    : takes one argument, returns nothing.

    Lambda expression:
    1. A concise way to represent an anonymous function that can be passed around as an object.
    2. It provides a clear and concise way to implement functional interfaces.
    Syntax: (parameters) -> expression   or   (parameters) -> { statements; }

    Method reference:
    1. A shorthand notation of a lambda expression to call a method.
    2. It can refer to static methods, instance methods, or constructors.
    Syntax: ClassName::methodName   or   instance::methodName   or   ClassName::new
     */


import java.util.function.*;

public class FunctionalInterfaceExample {

    public static void main(String[] args) {

        // =====================================================
        // 1. PREDICATE<T> - Accepts one input and returns boolean
        // Abstract method: test()
        // =====================================================

        Predicate<Integer> isGreaterThan10 = n -> n > 10;

        System.out.println(isGreaterThan10.test(15)); // true
        System.out.println(isGreaterThan10.test(5));  // false

        // Combine conditions using and(), or(), and negate()
        Predicate<Integer> isEven = n -> n % 2 == 0;

        System.out.println(isGreaterThan10.and(isEven).test(12)); // true
        System.out.println(isEven.or(isGreaterThan10).test(5));   // false
        System.out.println(isEven.negate().test(7));              // true


        // =====================================================
        // 2. FUNCTION<T, R> - Accepts input and returns a result
        // Abstract method: apply()
        // =====================================================

        Function<String, Integer> getLength = s -> s.length();

        System.out.println(getLength.apply("Java")); // 4

        Function<String, String> toUpperCase = s -> s.toUpperCase();

        System.out.println(toUpperCase.apply("java")); // JAVA

        // Execute one function followed by another
        Function<String, String> addJava = s -> s + " Java";

        System.out.println(
                addJava.andThen(toUpperCase).apply("learn")
        ); // LEARN JAVA


        // =====================================================
        // 3. CONSUMER<T> - Accepts input and returns nothing
        // Abstract method: accept()
        // =====================================================

        Consumer<String> print = s -> System.out.println(s);

        print.accept("Hello Java"); // Hello Java

        Consumer<String> printUpperCase =
                s -> System.out.println(s.toUpperCase());

        printUpperCase.accept("java"); // JAVA

        // Execute two consumers in sequence
        Consumer<String> first = s -> System.out.println("First: " + s);
        Consumer<String> second = s -> System.out.println("Second: " + s);

        first.andThen(second).accept("Java");


        // =====================================================
        // 4. SUPPLIER<T> - Accepts no input and returns a value
        // Abstract method: get()
        // =====================================================

        Supplier<String> message = () -> "Hello Java";

        System.out.println(message.get()); // Hello Java

        // Generate a value whenever get() is called
        Supplier<Double> randomValue = () -> Math.random();

        System.out.println(randomValue.get());


        // =====================================================
        // 5. BIPREDICATE<T, U> - Accepts two inputs, returns boolean
        // Abstract method: test()
        // =====================================================

        BiPredicate<Integer, Integer> areEqual =
                (a, b) -> a.equals(b);

        System.out.println(areEqual.test(10, 10)); // true
        System.out.println(areEqual.test(10, 20)); // false


        // =====================================================
        // 6. BIFUNCTION<T, U, R> - Accepts two inputs, returns result
        // Abstract method: apply()
        // =====================================================

        BiFunction<Integer, Integer, Integer> add =
                (a, b) -> a + b;

        System.out.println(add.apply(10, 20)); // 30

        BiFunction<String, String, String> combine =
                (a, b) -> a + " " + b;

        System.out.println(combine.apply("Hello", "Java"));
        // Hello Java


        // =====================================================
        // 7. BICONSUMER<T, U> - Accepts two inputs, returns nothing
        // Abstract method: accept()
        // =====================================================

        BiConsumer<String, Integer> printDetails =
                (name, age) ->
                        System.out.println(name + " is " + age + " years old");

        printDetails.accept("Ravi", 25);
        // Ravi is 25 years old


        // =====================================================
        // 8. UNARYOPERATOR<T> - One input, same return type
        // Extends Function<T, T>
        // =====================================================

        UnaryOperator<Integer> square = n -> n * n;

        System.out.println(square.apply(5)); // 25

        UnaryOperator<String> upperCase =
                s -> s.toUpperCase();

        System.out.println(upperCase.apply("java")); // JAVA


        // =====================================================
        // 9. BINARYOPERATOR<T> - Two inputs, same return type
        // Extends BiFunction<T, T, T>
        // =====================================================

        BinaryOperator<Integer> sum = (a, b) -> a + b;

        System.out.println(sum.apply(10, 20)); // 30

        BinaryOperator<Integer> maximum =
                (a, b) -> a > b ? a : b;

        System.out.println(maximum.apply(15, 25)); // 25


        // =====================================================
        // 10. METHOD REFERENCES - Shorter alternative to lambdas
        // =====================================================

        // Lambda expression
        Function<String, Integer> length1 = s -> s.length();

        // Equivalent method reference
        Function<String, Integer> length2 = String::length;

        System.out.println(length1.apply("Java")); // 4
        System.out.println(length2.apply("Java")); // 4

        // Lambda expression
        Consumer<String> print1 = s -> System.out.println(s);

        // Equivalent method reference
        Consumer<String> print2 = System.out::println;

        print1.accept("Hello");
        print2.accept("Java");


        // =====================================================
        // 11. STREAM EXAMPLE - Using functional interfaces
        // =====================================================

        // Predicate filters even numbers.
        // Consumer prints each remaining number.
        java.util.Arrays.asList(1, 2, 3, 4, 5, 6)
                .stream()
                .filter(n -> n % 2 == 0)
                .forEach(n -> System.out.println(n));

        // Output: 2, 4, 6

        //======================================================
        // 12. CUSTOM FUNCTIONAL INTERFACES
        //======================================================


    // 1. Lambda with two parameters and a return value
    Calculator addition = (a, b) -> a + b;
    System.out.println(addition.calculate(10, 20)); // 30

    // 2. Lambda with one parameter and no return value
    Greeting greeting = name ->
            System.out.println("Hello, " + name);
    greeting.greet("Ravi"); // Hello, Ravi

    // 3. Lambda returning a boolean
    NumberCheck isEvenn = number -> number % 2 == 0;
    System.out.println(isEvenn.check(10)); // true
    System.out.println(isEvenn.check(7));  // false

    // 4. Lambda with no parameters
    Message messages = () -> System.out.println("Hello Java");
    messages.display(); // Hello Java

    // 5. Default and static methods
    Operations operation = (a, b) -> a + b;
    System.out.println(operation.calculate(5, 3)); // 8
    operation.display(); // Operation completed
    Operations.info();   // Custom Functional Interface

    // =====================================================
    // Method Reference Example
    //====================================================
        // 1. Static Method Reference
        Consumer<String> c1 = s -> System.out.println(s); // Without
        Consumer<String> c2 = System.out::println;       // With

        c1.accept("Hello");
        c2.accept("Hello");

        // 2. Instance Method Reference
        String str = "java";

        Supplier<String> s1 = () -> str.toUpperCase(); // Without
        Supplier<String> s2 = str::toUpperCase;       // With

        System.out.println(s1.get());
        System.out.println(s2.get());

        // 3. Arbitrary Object Method Reference
        List<String> names = Arrays.asList("java", "selenium");

        names.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.println(s)); // Without
        names.stream().map(String::toUpperCase).forEach(System.out::println);         // With

        // 4. Constructor Reference
        Supplier<ArrayList<String>> a1 = () -> new ArrayList<>(); // Without
        Supplier<ArrayList<String>> a2 = ArrayList::new;           // With

    }

    // =====================================================
    // 12. CUSTOM FUNCTIONAL INTERFACES
    // Create your own functional interfaces when predefined
    // interfaces do not fit your requirements.
    // =====================================================

    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }

    @FunctionalInterface
    interface Greeting {
        void greet(String name);
    }

    @FunctionalInterface
    interface NumberCheck {
        boolean check(int number);
    }

    @FunctionalInterface
    interface Message {
        void display();
    }

    // Custom functional interface with a default method
    @FunctionalInterface
    interface Operations {
        int calculate(int a, int b);

        // Default methods are allowed in functional interfaces
        default void display() {
            System.out.println("Operation completed");
        }

        // Static methods are also allowed
        static void info() {
            System.out.println("Custom Functional Interface");
        }
    }


}

```

# Java Notes

## Table of Contents

1. [Array](#array)
2. [Java Collections Framework](#collections-framework)
3. [Generics](#generics)
4. [Cursors in Java](#cursors)
5. [ArrayList](#arraylist)
6. [Set Interface](#set)
7. [LinkedList](#linkedlist)
8. [Queue Interface](#queue)
9. [Map Interface](#map)
10. [String Programming](#string)

---

<a id="array"></a>

# 1. Array

```java
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
1. ARRAY DEFINITION
 
An array is a data structure used to store multiple
values of the same data type in a single variable.
 
Features:
- Stores multiple elements of the same declared type.
- Index starts from 0.
- Has a fixed length after creation.
- Elements are accessed using indexes.
- Arrays are objects in Java.
*/

public class ArrayRevision {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 1. Create an Array
        int[] numbers = {10, 20, 30, 40, 50};
        System.out.println("Original: " + Arrays.toString(numbers));

        // 2. Create an Array with Fixed Size
        int[] arr = new int[5];
        System.out.println("Default values: " + Arrays.toString(arr));

        // 3. Access an Element
        System.out.println("First element: " + numbers[0]);

        // 4. Update an Element
        numbers[1] = 99;
        System.out.println("Updated: " + Arrays.toString(numbers));

        // 5. Get Array Length
        System.out.println("Length: " + numbers.length);

        // 6. Iterate Using for Loop
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();

        // 7. Iterate Using Enhanced for Loop
        for (int value : numbers) {
            System.out.print(value + " ");
        }
        System.out.println();

        // 8. Add Element at the End
        int[] extended = Arrays.copyOf(numbers, numbers.length + 1);
        extended[extended.length - 1] = 60;
        System.out.println("Added: " + Arrays.toString(extended));

        // 9. Add Element at the Beginning
        int[] beginning = new int[numbers.length + 1];
        beginning[0] = 5;
        System.arraycopy(numbers, 0, beginning, 1, numbers.length);
        System.out.println("Beginning: " + Arrays.toString(beginning));

        // 10. Remove Element from the End
        int[] shorter = Arrays.copyOf(numbers, numbers.length - 1);
        System.out.println("Removed last: " + Arrays.toString(shorter));

        // 11. Remove Element at a Specific Index
        int removeIndex = 1;
        int[] removed = new int[numbers.length - 1];

        for (int i = 0, j = 0; i < numbers.length; i++) {
            if (i != removeIndex) {
                removed[j++] = numbers[i];
            }
        }
        System.out.println("Removed index 1: " + Arrays.toString(removed));

        // 12. Find Index of an Element
        int target = 30;
        int index = -1;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                index = i;
                break;
            }
        }
        System.out.println("Index of 30: " + index);

        // 13. Check if an Element Exists
        boolean found = false;

        for (int value : numbers) {
            if (value == 30) {
                found = true;
                break;
            }
        }
        System.out.println("Contains 30: " + found);

        // 14. Merge Two Arrays
        int[] first = {1, 2, 3};
        int[] second = {4, 5, 6};
        int[] merged = new int[first.length + second.length];

        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);

        System.out.println("Merged: " + Arrays.toString(merged));

        // 15. Copy an Array
        int[] copy = Arrays.copyOf(first, first.length);
        System.out.println("Copy: " + Arrays.toString(copy));

        // 16. Copy a Portion of an Array
        int[] portion = Arrays.copyOfRange(first, 1, 3);
        System.out.println("Portion: " + Arrays.toString(portion));

        // 17. Sort in Ascending Order
        int[] nums = {4, 2, 9, 1};
        Arrays.sort(nums);
        System.out.println("Ascending: " + Arrays.toString(nums));

        // 18. Sort in Descending Order
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        System.out.println("Descending: " + Arrays.toString(nums));

        // 19. Reverse an Array
        int[] reverse = {10, 20, 30, 40};

        for (int i = 0, j = reverse.length - 1; i < j; i++, j--) {
            int temp = reverse[i];
            reverse[i] = reverse[j];
            reverse[j] = temp;
        }
        System.out.println("Reversed: " + Arrays.toString(reverse));

        // 20. Shuffle an Array
        Random random = new Random();

        for (int i = nums.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        System.out.println("Shuffled: " + Arrays.toString(nums));

        // 21. Find Sum
        int[] values = {1, 2, 3, 4};
        int sum = 0;

        for (int value : values) {
            sum += value;
        }
        System.out.println("Sum: " + sum);

        // 22. Find Average
        double average = (double) sum / values.length;
        System.out.println("Average: " + average);

        // 23. Find Maximum and Minimum
        int max = values[0];
        int min = values[0];

        for (int value : values) {
            if (value > max) max = value;
            if (value < min) min = value;
        }
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);

        // 24. Convert Array to String
        System.out.println("Array as String: " + Arrays.toString(values));

        // 25. Convert String to Array
        String text = "10,20,30,40";
        String[] parts = text.split(",");
        int[] converted = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            converted[i] = Integer.parseInt(parts[i].trim());
        }
        System.out.println("String to array: " + Arrays.toString(converted));

        // 26. Compare Two Arrays
        int[] x = {1, 2, 3};
        int[] y = {1, 2, 3};
        System.out.println("Equal: " + Arrays.equals(x, y));

        // 27. Fill an Array
        int[] filled = new int[5];
        Arrays.fill(filled, 7);
        System.out.println("Filled: " + Arrays.toString(filled));

        // 28. Binary Search (array must be sorted)
        int[] sorted = {10, 20, 30, 40, 50};
        System.out.println("Binary search: " +
                Arrays.binarySearch(sorted, 30));

        // 29. Count Even and Odd Numbers
        int even = 0, odd = 0;

        for (int value : values) {
            if (value % 2 == 0) even++;
            else odd++;
        }
        System.out.println("Even: " + even);
        System.out.println("Odd: " + odd);

        // 30. Find Duplicate Elements
        int[] duplicateArray = {10, 20, 10, 30, 20};
        System.out.print("Duplicates: ");

        for (int i = 0; i < duplicateArray.length; i++) {
            boolean alreadyPrinted = false;

            for (int k = 0; k < i; k++) {
                if (duplicateArray[i] == duplicateArray[k]) {
                    alreadyPrinted = true;
                    break;
                }
            }

            if (alreadyPrinted) continue;

            for (int j = i + 1; j < duplicateArray.length; j++) {
                if (duplicateArray[i] == duplicateArray[j]) {
                    System.out.print(duplicateArray[i] + " ");
                    break;
                }
            }
        }
        System.out.println();

        // 31. Remove Duplicate Elements
        int[] unique = new int[duplicateArray.length];
        int count = 0;

        for (int i = 0; i < duplicateArray.length; i++) {
            boolean exists = false;

            for (int j = 0; j < count; j++) {
                if (duplicateArray[i] == unique[j]) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                unique[count++] = duplicateArray[i];
            }
        }

        unique = Arrays.copyOf(unique, count);
        System.out.println("Without duplicates: " + Arrays.toString(unique));

        // 32. Find Second Largest Distinct Element
        int[] data = {10, 40, 20, 40, 30};
        Arrays.sort(data);

        int largest = data[data.length - 1];
        boolean hasSecond = false;

        for (int i = data.length - 2; i >= 0; i--) {
            if (data[i] != largest) {
                System.out.println("Second largest: " + data[i]);
                hasSecond = true;
                break;
            }
        }

        if (!hasSecond) {
            System.out.println("No second-largest distinct element");
        }

        // 33. Take Array Input from User
        System.out.print("Enter array size: ");
        int size = sc.nextInt();

        if (size < 0) {
            System.out.println("Size cannot be negative.");
        } else {
            int[] inputArray = new int[size];

            System.out.println("Enter " + size + " elements:");
            for (int i = 0; i < size; i++) {
                inputArray[i] = sc.nextInt();
            }

            System.out.println("Input array: " +
                    Arrays.toString(inputArray));
        }

        sc.close();
    }
}
```

[Back to top](#java-notes)

---

<a id="collections-framework"></a>

# 2. Java Collections Framework

## Collections Framework
- An architecture: a group of classes and interfaces for storing multiple objects as a single unit.
- All of it lives in the `java.util` package.
- **Collection** (singular) is the root interface; **Collections** (plural) is a utility class (`sort`, `reverse`, etc.).
- Check methods from the command line: `javap java.util.Collection`

## Hierarchy
`Collection` is extended by three interfaces:

| Interface | Implemented by |
|---|---|
| **List** | ArrayList, LinkedList, Vector |
| **Set** | HashSet, LinkedHashSet, TreeSet |
| **Queue** | PriorityQueue, LinkedList |

LinkedList implements both List and Queue.

**Characteristics to study for each class:** version, homogeneous or heterogeneous, null allowed, duplicates allowed, insertion order, data structure, cursors.

## List (since 1.2)
- Index-based; allows heterogeneous objects, nulls and duplicates.
- Preserves insertion order.

## Set
- Does not allow duplicates and has no index.
- Order depends on the class: HashSet has no guaranteed order, LinkedHashSet keeps insertion order, TreeSet keeps sorted order.

## Queue
- Holds elements for processing, typically FIFO (first in, first out).
- PriorityQueue orders elements by priority instead of arrival.

## Common Methods

| Method | Purpose |
|---|---|
| `add(obj)` | Add an object |
| `addAll(coll)` | Copy one collection's objects into another |
| `remove(obj)` / `remove(index)` | Remove by object / by index |
| `removeAll(coll)` | Remove all objects found in another collection |
| `set(index, obj)` | Replace the object at an index |
| `get(index)` | Retrieve the object at an index |
| `isEmpty()` | Check whether the collection is empty |
| `contains(obj)` | Check whether an object is present |
| `containsAll(coll)` | Check whether all of another collection's objects are present |
| `size()` | Number of elements (counts from 1; indexes start at 0) |
| `Collections.sort(list)` | Ascending order (chars by Unicode) |
| `Collections.reverse(list)` | Reverse order |

[Back to top](#java-notes)

---

<a id="generics"></a>

# 3. Generics

Generics provide compile-time type safety by specifying the type of elements a collection can store.

Syntax:

```java
ArrayList<String> names = new ArrayList<>();
```

Examples:

- `ArrayList<Integer>` stores integers.
- `ArrayList<String>` stores strings.
- `ArrayList<Character>` stores characters.
- `ArrayList<Object>` can store objects of different reference types.

Adding an incompatible type to a typed collection causes a compile-time error.

```java
ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(100);
// numbers.add("Java"); // Compile-time error
```

Important: Java generics cannot use primitive types directly. Use wrapper classes such as Integer instead of int.

[Back to top](#java-notes)

---

<a id="cursors"></a>

# 4. Cursors in Java

Cursors are used to traverse collection elements one by one.

## Iterator

- Works with all collections (List, Set, Queue, etc.).
- Traverses in the forward direction only.

**Methods:**

| Method | Description |
|---|---|
| `hasNext()` | Returns `true` if the collection has more elements ahead of the cursor. It does not move the cursor, so it is used as the loop condition. |
| `next()` | Returns the next element and advances the cursor by one position. Throws `NoSuchElementException` if no elements remain. |
| `remove()` | Removes the last element returned by `next()` from the underlying collection. Can be called only once per `next()` call, otherwise it throws `IllegalStateException`. |

## ListIterator

- Works only with lists (ArrayList, LinkedList, etc.).
- Traverses both forward and backward.

**Methods:**

| Method | Description |
|---|---|
| `hasNext()` | Returns `true` if there are more elements when moving forward. |
| `next()` | Returns the next element and moves the cursor forward by one position. |
| `hasPrevious()` | Returns `true` if there are more elements when moving backward. |
| `previous()` | Returns the previous element and moves the cursor backward by one position. |
| `add(E e)` | Inserts the specified element into the list immediately before the element that `next()` would return (and after the one `previous()` would return). The cursor moves past the new element. |
| `set(E e)` | Replaces the last element returned by `next()` or `previous()` with the specified element. Cannot be used if `add()` or `remove()` was called after the last `next()`/`previous()`. |
| `remove()` | Removes the last element returned by `next()` or `previous()` from the list. Cannot be called twice in a row without another `next()`/`previous()` in between. |

## Comparison

| Feature | Iterator | ListIterator |
|---|---|---|
| Direction | Forward | Forward and backward |
| Works with | Collections | Lists only |
| Add elements | No | Yes |
| Replace elements | No | Yes |
| Remove elements | Yes | Yes |

**Note:** `next()` returns the next element and advances the cursor. `previous()` returns the previous element and moves the cursor backward.

[Back to top](#java-notes)

---

<a id="arraylist"></a>

# 5. ArrayList

## ArrayList
- Implements `List`; introduced in Java 1.2.
- Heterogeneous data, nulls and duplicates are allowed.
- Insertion order is preserved.
- Data structure: growable array.
- Cursors: `Iterator` and `ListIterator`.

## ArrayList Example

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListCommonMethods {

    public static void main(String[] args) {

        // =====================================================
        // DIFFERENT WAYS TO CREATE AN ARRAYLIST
        // =====================================================

        // 1. Default constructor (empty list, default capacity 10)
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Java");
        System.out.println("1. Default constructor   : " + list1);

        // 2. With initial capacity (still empty, size = 0)
        // Useful when you already know roughly how many elements you will store
        ArrayList<String> list2 = new ArrayList<>(50);
        System.out.println("2. With capacity         : " + list2 + " size=" + list2.size());

        // 3. From another collection (copy constructor)
        ArrayList<String> list3 = new ArrayList<>(list1);
        System.out.println("3. From collection       : " + list3);


        // =====================================================
        // COMMON METHODS
        // =====================================================
        System.out.println("\n========== COMMON METHODS ==========");

        // ---------- add(obj) ----------
        // Adds an element at the END of the list
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Java");
        courses.add("SQL");
        courses.add("Selenium");
        courses.add("Aptitude");
        System.out.println("After add()          : " + courses);

        // ---------- addAll(coll) ----------
        // Copies all elements of one collection into another
        ArrayList<String> more = new ArrayList<>();
        more.add("Python");
        more.add("HTML");
        courses.addAll(more);
        System.out.println("After addAll()       : " + courses);

        // ---------- get(index) ----------
        // Retrieves the element at the given index (index starts at 0)
        System.out.println("get(0)               : " + courses.get(0));

        // ---------- set(index, obj) ----------
        // Replaces the element at the given index (returns the old value)
        courses.set(1, "MySQL");
        System.out.println("After set(1,MySQL)   : " + courses);

        // ---------- size() ----------
        // Number of elements: counts from 1, while indexes start at 0
        System.out.println("size()               : " + courses.size());

        // ---------- isEmpty() ----------
        // true if the list has no elements
        System.out.println("isEmpty()            : " + courses.isEmpty());

        // ---------- contains(obj) ----------
        // true if the element is present in the list
        System.out.println("contains(Java)       : " + courses.contains("Java"));
        System.out.println("contains(Ruby)       : " + courses.contains("Ruby"));

        // ---------- containsAll(coll) ----------
        // true only if ALL elements of the other collection are present
        System.out.println("containsAll(more)    : " + courses.containsAll(more));

        // ---------- remove(obj) ----------
        // Removes the first occurrence of the given object
        courses.remove("HTML");
        System.out.println("After remove(HTML)   : " + courses);

        // ---------- remove(index) ----------
        // Removes the element at the given index
        courses.remove(0);
        System.out.println("After remove(0)      : " + courses);

        // ---------- removeAll(coll) ----------
        // Removes all elements that are also present in the other collection
        courses.removeAll(more);
        System.out.println("After removeAll(more): " + courses);

        // ---------- Collections.sort(list) ----------
        // Sorts in ascending order (Strings are compared by Unicode values)
        courses.add("Java");
        courses.add("Angular");
        Collections.sort(courses);
        System.out.println("Ascending order      : " + courses);

        // ---------- Collections.reverse(list) ----------
        // Reverses the current order of the list
        Collections.reverse(courses);
        System.out.println("Reversed order       : " + courses);


        // =====================================================
        // CURSORS (TRAVERSAL)
        // =====================================================
        System.out.println("\n========== CURSORS ==========");

        // ---------- Iterator ----------
        // Forward only; supports safe removal using it.remove()
        Iterator<String> it = courses.iterator();
        while (it.hasNext()) {
            String c = it.next();
            if (c.equals("Angular")) {
                it.remove();   // safe removal (avoids ConcurrentModificationException)
            }
        }
        System.out.println("After Iterator remove: " + courses);

        // ---------- ListIterator ----------
        // Moves both forward and backward (works only on Lists)
        ListIterator<String> li = courses.listIterator();

        System.out.println("Forward:");
        while (li.hasNext()) {
            System.out.println(li.next());
        }

        // Cursor is now at the end, so backward traversal works
        System.out.println("Backward:");
        while (li.hasPrevious()) {
            System.out.println(li.previous());
        }
    }
}
```

[Back to top](#java-notes)

---

<a id="set"></a>

# 6. Java Set Interface – Notes

## 1. Introduction to Set

- `Set` is an interface in the `java.util` package.
- Stores unique elements; duplicates are not allowed.
- Does not support index-based access.
- Supports traversal using `Iterator`.
- Main implementations: `HashSet`, `LinkedHashSet`, and `TreeSet`.

## 2. Comparison of Set Implementations

| Feature        | HashSet                         | LinkedHashSet                      | TreeSet                         |
| -------------- | ------------------------------- | ---------------------------------- | ------------------------------- |
| Ordering       | No guaranteed order             | Insertion order                    | Sorted order                    |
| Duplicates     | Not allowed                     | Not allowed                        | Not allowed                     |
| Null values    | One allowed                     | One allowed                        | Not allowed in natural ordering |
| Data structure | Hash table                      | Hash table + linked structure      | Red-Black Tree                  |
| Performance    | O(1) average                    | O(1) average                       | O(log n)                        |
| Best use       | Fast storage of unique elements | Unique elements in insertion order | Unique elements in sorted order |

## 3. HashSet Example

- Does not guarantee insertion order.
- Allows one `null`.
- Supports heterogeneous objects.

```java
import java.util.*;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();

        // Adding elements
        set.add("Java");
        set.add("Selenium");
        set.add("Python");
        set.add("Java"); // Duplicate ignored
        set.add(null);

        System.out.println(set);
        System.out.println(set.contains("Java")); // true
        System.out.println(set.size());            // 4

        set.remove("Java");
        System.out.println(set);

        // Iteration
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        set.clear();
        System.out.println(set.isEmpty()); // true
    }
}
```

## 4. LinkedHashSet Example

- Preserves insertion order.
- Allows one `null`.
- Does not allow duplicates.

```java
import java.util.*;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        LinkedHashSet<String> set = new LinkedHashSet<>();

        // Adding elements
        set.add("Java");
        set.add("Selenium");
        set.add("Python");
        set.add("Java"); // Duplicate ignored

        System.out.println(set);
        // [Java, Selenium, Python]

        System.out.println(set.contains("Python")); // true
        System.out.println(set.size());             // 3

        set.remove("Selenium");
        System.out.println(set);
        // [Java, Python]

        set.add("Cypress");
        System.out.println(set);
        // [Java, Python, Cypress]

        set.clear();
        System.out.println(set.isEmpty()); // true
    }
}
```

## 5. TreeSet Example

- Maintains sorted order.
- Does not allow duplicates.
- Does not allow `null` with natural ordering.
- Elements must be mutually comparable.

```java
import java.util.*;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();

        // Adding elements
        set.add(30);
        set.add(10);
        set.add(20);
        set.add(10); // Duplicate ignored

        System.out.println(set);              // [10, 20, 30]
        System.out.println(set.contains(20)); // true
        System.out.println(set.size());       // 3

        // TreeSet-specific methods
        System.out.println(set.first());      // 10
        System.out.println(set.last());       // 30

        System.out.println(set.pollFirst());  // 10 (removed)
        System.out.println(set.pollLast());   // 30 (removed)

        System.out.println(set);              // [20]

        set.remove(20);
        System.out.println(set.isEmpty());    // true
    }
}
```

## 6. Commonly Used Set Methods

| Method                  | Purpose                               |
| ----------------------- | ------------------------------------- |
| `add(element)`          | Adds an element                       |
| `remove(element)`       | Removes an element                    |
| `contains(element)`     | Checks whether an element exists      |
| `size()`                | Returns the number of elements        |
| `isEmpty()`             | Checks whether the set is empty       |
| `clear()`               | Removes all elements                  |
| `iterator()`            | Returns an iterator                   |
| `addAll(collection)`    | Adds elements from another collection |
| `removeAll(collection)` | Removes matching elements             |
| `retainAll(collection)` | Retains only matching elements        |

Remember:

- `HashSet` → no guaranteed order.
- `LinkedHashSet` → insertion order.
- `TreeSet` → sorted order.
- `first()`, `last()`, `pollFirst()`, and `pollLast()` → commonly used `TreeSet` methods.

[Back to top](#java-notes)

---

<a id="linkedlist"></a>

# 7. Java LinkedList – Notes

## 1. Introduction to LinkedList

- `LinkedList` is a class in the `java.util` package.
- Implements the `List` and `Deque` interfaces.
- Stores elements in a doubly linked list.
- Allows duplicate elements and `null` values.
- Maintains insertion order.
- Supports index-based access, but accessing elements by index is slower than in `ArrayList`.
- Useful for adding or removing elements at the beginning and end.

## 2. LinkedList Example with Commonly Used Methods

```java
import java.util.*;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        // 1. Add elements
        list.add("Java");
        list.add("Selenium");
        list.add("Python");
        list.add("Java"); // Duplicates allowed
        System.out.println(list);

        // 2. Add at beginning and end
        list.addFirst("C");
        list.addLast("JavaScript");
        System.out.println(list);

        // 3. Access elements
        System.out.println(list.get(1));
        System.out.println(list.getFirst());
        System.out.println(list.getLast());

        // 4. Update element
        list.set(1, "SQL");
        System.out.println(list);

        // 5. Remove elements
        list.remove("Java");
        list.remove(0);
        list.removeFirst();
        list.removeLast();
        System.out.println(list);

        // 6. Check elements
        System.out.println(list.contains("Python"));
        System.out.println(list.size());
        System.out.println(list.isEmpty());

        // 7. Iterate using for-each
        for (String item : list) {
            System.out.println(item);
        }

        // 8. Queue operations
        list.offer("C++");
        list.offer("Ruby");
        System.out.println(list.peek()); // First element
        System.out.println(list.poll()); // Retrieves and removes first

        // 9. Clear all elements
        list.clear();
        System.out.println(list.isEmpty());
    }
}
```

## 3. Commonly Used LinkedList Methods

| Method                | Purpose                                         |
| --------------------- | ----------------------------------------------- |
| `add(element)`        | Adds an element at the end                      |
| `addFirst(element)`   | Adds at the beginning                           |
| `addLast(element)`    | Adds at the end                                 |
| `get(index)`          | Retrieves element by index                      |
| `getFirst()`          | Retrieves the first element                     |
| `getLast()`           | Retrieves the last element                      |
| `set(index, element)` | Updates an element                              |
| `remove(index)`       | Removes by index                                |
| `removeFirst()`       | Removes the first element                       |
| `removeLast()`        | Removes the last element                        |
| `contains(element)`   | Checks if an element exists                     |
| `size()`              | Returns the number of elements                  |
| `offer(element)`      | Adds an element to the queue                    |
| `peek()`              | Retrieves the first element without removing it |
| `poll()`              | Retrieves and removes the first element         |
| `clear()`             | Removes all elements                            |

## 4. ArrayList vs LinkedList

| Feature                 | ArrayList                                     | LinkedList                       |
| ----------------------- | --------------------------------------------- | -------------------------------- |
| Internal structure      | Dynamic array                                 | Doubly linked list               |
| Access by index         | Fast — O(1)                                   | Slow — O(n)                      |
| Add/remove at beginning | Usually slower — O(n)                         | O(1)                             |
| Add/remove at ends      | Adding at end is usually O(1) amortized       | O(1)                             |
| Memory usage            | Generally lower                               | Generally higher                 |
| Best use                | Frequent reading/access by index              | Frequent operations at both ends |

Remember: `ArrayList` is usually better for frequent index-based access. `LinkedList` is useful when you frequently add or remove elements at either end, or need queue/deque operations.

[Back to top](#java-notes)

---

<a id="queue"></a>

# 8. Java Queue Interface – Short Notes

## 1. Introduction to Queue

- `Queue` is an interface in the `java.util` package.
- Usually follows FIFO (First In, First Out).
- Common implementations include `PriorityQueue` and `LinkedList`.
- Duplicate elements are allowed.
- `PriorityQueue` does not allow `null` elements.

## 2. Comparison of Queue Implementations

| Feature        | PriorityQueue                            | LinkedList                                     |
| -------------- | ---------------------------------------- | ---------------------------------------------- |
| Ordering       | Priority/natural sorted order by default | Insertion order for queue operations           |
| Duplicates     | Allowed                                  | Allowed                                        |
| `null`         | Not allowed                              | Allowed by the class, but not by queue methods |
| Data structure | Heap                                     | Doubly linked list                             |
| Best use       | Process elements by priority             | Standard FIFO queue                            |

## 3. PriorityQueue Example

- Introduced in Java 1.5.
- Default initial capacity is 11.
- By default, elements are processed in natural order (smallest first for numbers).
- Elements must be mutually comparable, or a suitable `Comparator` must be provided.
- `peek()` retrieves the head without removing it.
- `poll()` retrieves and removes the head; returns `null` if empty.

```java
import java.util.*;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<String> q = new PriorityQueue<>();

        // Add elements
        q.add("JAVA");
        q.add("HTML");
        q.add("CSS");
        q.add("SOAP");
        q.add("AWS");
        q.add("Selenium");

        System.out.println(q.peek());         // AWS
        System.out.println(q.poll());         // AWS
        System.out.println(q.contains("AWS"));// false

        // Retrieve and remove elements
        while (q.peek() != null) {
            System.out.println(q.poll());
        }

        System.out.println(q); // []
    }
}
```

Output:

```
AWS
AWS
false
CSS
HTML
JAVA
SOAP
Selenium
[]
```

## 4. Commonly Used Queue Methods

| Method              | Purpose                                                       |
| ------------------- | ------------------------------------------------------------- |
| `add(element)`      | Adds an element; may throw an exception if insertion fails    |
| `offer(element)`    | Adds an element; returns `false` if insertion fails           |
| `peek()`            | Retrieves head without removing it; returns `null` if empty   |
| `poll()`            | Retrieves and removes head; returns `null` if empty           |
| `remove()`          | Removes and returns head; throws exception if empty           |
| `element()`         | Retrieves head without removing it; throws exception if empty |
| `contains(element)` | Checks whether an element exists                              |
| `size()`            | Returns the number of elements                                |
| `isEmpty()`         | Checks whether the queue is empty                             |
| `clear()`           | Removes all elements                                          |

Remember: A normal queue typically follows FIFO, but `PriorityQueue` processes elements according to priority, not insertion order.

[Back to top](#java-notes)

---

<a id="map"></a>

# 9. Java Map Interface – Short Notes

## 1. Introduction to Map

- `Map` is an interface in the `java.util` package.
- Stores elements as key-value pairs.
- Each key-value pair is called an entry.
- Keys must be unique, but values can be duplicated.
- Main implementations: `HashMap`, `LinkedHashMap`, and `TreeMap`.

## 2. Comparison of Map Implementations

| Feature            | HashMap               | LinkedHashMap                 | TreeMap                           |
| ------------------ | --------------------- | ----------------------------- | --------------------------------- |
| Ordering           | No guaranteed order   | Insertion order               | Sorted by keys                    |
| Duplicate keys     | Not allowed           | Not allowed                   | Not allowed                       |
| Duplicate values   | Allowed               | Allowed                       | Allowed                           |
| Null key           | One allowed           | One allowed                   | Not allowed with natural ordering |
| Null values        | Allowed               | Allowed                       | Allowed                           |
| Internal structure | Hash table            | Hash table + linked structure | Red-Black Tree                    |
| Performance        | O(1) average          | O(1) average                  | O(log n)                          |
| Best use           | Fast key-value lookup | Preserve insertion order      | Sorted keys                       |

## 3. HashMap Example

- Introduced in Java 1.2.
- Does not guarantee insertion order.
- Allows one `null` key and multiple `null` values.
- Adding an existing key replaces its value.

```java
import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(101, "Samsung");
        map.put(102, "Realme");
        map.put(103, "Oppo");
        map.put(104, "Vivo");

        System.out.println(map);

        map.put(103, "Redmi"); // Replaces value
        map.put(105, "Samsung"); // Duplicate value allowed

        System.out.println(map);
        System.out.println(map.get(102)); // Realme
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(map.containsKey(101)); // true
        System.out.println(map.containsValue("iPhone")); // false
        System.out.println(map.remove(104));
    }
}
```

## 4. LinkedHashMap Example

- Introduced in Java 1.4.
- Preserves insertion order by default.
- Allows one `null` key and multiple `null` values.
- Updating an existing key does not create a new entry.

```java
import java.util.*;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

        map.put(388, "Rahul");
        map.put(1, "Riya");
        map.put(103, "Pooja");

        System.out.println(map);
        // {388=Rahul, 1=Riya, 103=Pooja}

        map.put(103, "Pooja Updated");
        System.out.println(map);
        // {388=Rahul, 1=Riya, 103=Pooja Updated}

        map.put(null, "Unknown");
        System.out.println(map);

        System.out.println(map.get(1)); // Riya
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(map.containsKey(388)); // true
        System.out.println(map.size()); // 4
    }
}
```

## 5. TreeMap Example

- Introduced in Java 1.2.
- Sorts entries by keys in ascending order by default.
- Does not allow duplicate keys.
- Does not allow `null` keys with natural ordering.
- Values can be duplicated and can be `null`.

```java
import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();

        map.put(103, "Pooja");
        map.put(101, "Rahul");
        map.put(102, "Riya");

        System.out.println(map);
        // {101=Rahul, 102=Riya, 103=Pooja}

        map.put(101, "Sanju"); // Replaces value
        System.out.println(map);
        // {101=Sanju, 102=Riya, 103=Pooja}

        System.out.println(map.firstKey()); // 101
        System.out.println(map.lastKey());  // 103
        System.out.println(map.get(102));   // Riya
        System.out.println(map.keySet());
        System.out.println(map.values());

        System.out.println(map.containsKey(101)); // true
        System.out.println(map.remove(102)); // Riya
    }
}
```

## 6. Commonly Used Map Methods

| Method                            | Purpose                                  |
| --------------------------------- | ---------------------------------------- |
| `put(key, value)`                 | Adds or updates an entry                 |
| `get(key)`                        | Returns the value for a key              |
| `remove(key)`                     | Removes an entry                         |
| `keySet()`                        | Returns all keys                         |
| `values()`                        | Returns all values                       |
| `entrySet()`                      | Returns all key-value entries            |
| `containsKey(key)`                | Checks whether a key exists              |
| `containsValue(value)`            | Checks whether a value exists            |
| `size()`                          | Returns the number of entries            |
| `isEmpty()`                       | Checks whether the map is empty          |
| `clear()`                         | Removes all entries                      |
| `getOrDefault(key, defaultValue)` | Returns the default if the key is absent |

## 7. Quick Revision

- `Map` → stores key-value pairs.
- `HashMap` → no guaranteed order.
- `LinkedHashMap` → insertion order.
- `TreeMap` → sorted order by keys.
- Duplicate keys → not allowed; the existing value is replaced.
- Duplicate values → allowed.
- `keySet()` → all keys; `values()` → all values; `entrySet()` → all entries.

[Back to top](#java-notes)

---

<a id="string"></a>

# 10. Java String Programming – Short Notes

## 1. Introduction to String

- A string is a group of characters.
- `String` is a predefined class in the `java.lang` package.
- String objects are immutable — once created, their content cannot be modified.
- Strings can be created in two ways:
  1. Using `new` keyword.
  2. Using string literals.

```java
String s1 = new String("Java");
String s2 = "Java";
```

- String literals are stored in the String Constant Pool (SCP).
- `new String("Java")` creates a separate String object in the heap and uses the pool for the literal if needed.

## 2. Commonly Used String Methods

| Method                           | Purpose                                       |
| -------------------------------- | --------------------------------------------- |
| `length()`                       | Returns string length                         |
| `charAt(index)`                  | Returns character at an index                 |
| `equals(str)`                    | Compares content, case-sensitive              |
| `equalsIgnoreCase(str)`          | Compares content, ignoring case               |
| `trim()`                         | Removes leading and trailing basic whitespace |
| `substring(begin)`               | Extracts substring from index to end          |
| `substring(begin, end)`          | Extracts substring; end index excluded        |
| `indexOf(value)`                 | Returns first matching index; `-1` if absent  |
| `toUpperCase()`                  | Converts to uppercase                         |
| `toLowerCase()`                  | Converts to lowercase                         |
| `startsWith(str)`                | Checks starting text                          |
| `endsWith(str)`                  | Checks ending text                            |
| `contains(str)`                  | Checks whether text exists                    |
| `isEmpty()`                      | Checks whether length is zero                 |
| `concat(str)`                    | Joins strings                                 |
| `replace(old, new)`              | Replaces matching characters or sequences     |
| `replaceAll(regex, replacement)` | Replaces regex matches                        |
| `split(regex)`                   | Splits string into an array                   |
| `toCharArray()`                  | Converts string to a character array          |

## 3. Examples of Common String Methods

```java
public class StringMethodsDemo {
    public static void main(String[] args) {
        String s = "Java Development";

        System.out.println(s.length());             // 16
        System.out.println(s.charAt(2));            // v
        System.out.println(s.substring(5));         // Development
        System.out.println(s.substring(0, 4));      // Java
        System.out.println(s.indexOf("Dev"));       // 5
        System.out.println(s.toUpperCase());        // JAVA DEVELOPMENT
        System.out.println(s.toLowerCase());        // java development
        System.out.println(s.startsWith("Java"));   // true
        System.out.println(s.endsWith("ment"));     // true
        System.out.println(s.contains("Dev"));      // true
        System.out.println(s.replace('a', 'o'));    // Jovo Development
        System.out.println(s.concat(" Course"));    // Java Development Course
        System.out.println("  Java  ".trim());      // Java
        System.out.println("".isEmpty());           // true
    }
}
```

## 4. String Programming Examples

### A. Count a character

```java
public class CountCharacter {
    public static void main(String[] args) {
        String s = "javaEEdeve";
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
                count++;
            }
        }

        System.out.println(count); // 4
    }
}
```

### B. Print and count lowercase vowels

```java
public class Vowels {
    public static void main(String[] args) {
        String s = "javadev";
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {
                System.out.println(ch);
                count++;
            }
        }

        System.out.println("Vowel count: " + count); // 3
    }
}
```

### C. Reverse a string

```java
public class ReverseString {
    public static void main(String[] args) {
        String actual = "javadev";
        String rev = "";

        for (int i = actual.length() - 1; i >= 0; i--) {
            rev = rev + actual.charAt(i);
        }

        System.out.println(rev); // vedavaj
    }
}
```

### D. Check palindrome

```java
public class Palindrome {
    public static void main(String[] args) {
        String actual = "Mom";
        String rev = "";

        for (int i = actual.length() - 1; i >= 0; i--) {
            rev += actual.charAt(i);
        }

        if (actual.equalsIgnoreCase(rev)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a palindrome");
        }
    }
}
```

### E. Count words

```java
public class CountWords {
    public static void main(String[] args) {
        String s = " I am a java developer ";
        String trimmed = s.trim();

        String[] words = trimmed.split("\\s+");

        System.out.println(words.length); // 5
    }
}
```

### F. Print unique characters

```java
public class UniqueCharacters {
    public static void main(String[] args) {
        String s = "javajavajavadev";
        String unique = "";

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (unique.indexOf(ch) == -1) {
                unique += ch;
            }
        }

        System.out.println(unique); // javde
    }
}
```

### G. Replace using regular expressions

```java
public class ReplaceDemo {
    public static void main(String[] args) {
        String s = "jAvA DeVeloPer";

        System.out.println(s.replaceAll("[A-Z]", ""));
        // jv eeloer

        System.out.println(s.replaceAll("[a-z]", ""));
        // AA DVP

        String s2 = "ja123vaDEveloper";

        System.out.println(s2.replaceAll("[0-9]", ""));
        // javaDEveloper

        System.out.println(s2.replaceAll("[aeiouAEIOU]", ""));
        // j123vDvlpr
    }
}
```

### H. Split a string

```java
public class SplitDemo {
    public static void main(String[] args) {
        String s = "I am a java developer";

        String[] words = s.split(" ");

        for (String word : words) {
            System.out.println(word);
        }

        String[] chars = "java".split("");

        for (String ch : chars) {
            System.out.println(ch);
        }
    }
}
```

### I. Compare `equals()` and `==`

| `equals()`              | `==`                                               |
| ----------------------- | -------------------------------------------------- |
| Compares String content | Compares object references                         |
| Overridden in `String`  | Checks whether references point to the same object |

```java
public class StringComparison {
    public static void main(String[] args) {
        String s1 = new String("javadev");
        String s2 = "javadev";
        String s3 = "javadev";

        System.out.println(s1.equals(s2)); // true
        System.out.println(s1 == s2);      // false
        System.out.println(s2 == s3);      // true
    }
}
```

## 5. StringBuffer and StringBuilder

- Both classes belong to `java.lang`.
- Both are mutable, meaning their content can be changed.
- Objects are commonly created using `new`.
- Both provide `append()` to add content.

```java
public class MutableStrings {
    public static void main(String[] args) {
        StringBuffer sb1 = new StringBuffer("Java");
        sb1.append(" Development");
        System.out.println(sb1);

        StringBuilder sb2 = new StringBuilder("Java");
        sb2.append(" Selenium");
        System.out.println(sb2);
    }
}
```

## 6. String vs StringBuffer vs StringBuilder

| Feature                               | String                             | StringBuffer                                            | StringBuilder                        |
| ------------------------------------- | ---------------------------------- | ------------------------------------------------------- | ------------------------------------ |
| Mutability                            | Immutable                          | Mutable                                                 | Mutable                              |
| Thread safety                         | Immutable content is safe to share | Thread-safe methods                                     | Not thread-safe                      |
| Performance for repeated modification | Often slower                       | Usually slower than Builder                             | Usually faster                       |
| Common concatenation method           | `concat()`                         | `append()`                                              | `append()`                           |
| Typical use                           | Fixed text                         | Mutable text shared across threads with synchronization | Mutable text in single-threaded code |

Quick revision: `String` = immutable; `StringBuffer` = mutable and synchronized; `StringBuilder` = mutable and generally faster when synchronization is unnecessary.

[Back to top](#java-notes)

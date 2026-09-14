# Python Complete Notes

---

## 1. Introduction to Python

Python is a **high-level, interpreted, general-purpose** programming language created by **Guido van Rossum** (released 1991). It emphasizes readability (indentation-based syntax), is dynamically typed, and supports multiple paradigms: procedural, object-oriented, and functional.

**Key features:** Easy syntax, huge standard library, cross-platform, open-source, supports OOP, automatic memory management (garbage collection), extensible with C/C++.

```python
# Your first Python program
print("Hello, World!")

# Check Python version
import sys
print(sys.version)

# Python is interpreted - runs line by line
x = 10
print("x is:", x)
```

---

## 2. Variables in Python

A variable is a **name bound to a value/object** in memory. No explicit declaration or type needed (dynamic typing). Rules: must start with letter/underscore, case-sensitive, cannot use reserved keywords.

```python
# Variable assignment
name = "Alice"
age = 25
height = 5.6
is_student = True

# Multiple assignment
a, b, c = 1, 2, 3
x = y = z = 0          # all point to same value

# Swapping without temp variable
a, b = 10, 20
a, b = b, a
print(a, b)             # 20 10

# Checking type and id (memory reference)
print(type(name), id(name))

# Constants (convention: UPPERCASE, Python has no true constants)
PI = 3.14159

# Dynamic typing - variable can change type
var = 5
var = "now a string"
print(var)
```

---

## 3. Datatypes in Python

Python has several built-in data types:

| Category | Types |
|---|---|
| Numeric | `int`, `float`, `complex` |
| Sequence | `str`, `list`, `tuple`, `range` |
| Mapping | `dict` |
| Set | `set`, `frozenset` |
| Boolean | `bool` |
| Binary | `bytes`, `bytearray`, `memoryview` |
| None | `NoneType` |

```python
# Numeric types
i = 10                  # int
f = 10.5                # float
c = 2 + 3j               # complex

# Sequence types
s = "hello"              # str
l = [1, 2, 3]             # list
t = (1, 2, 3)             # tuple
r = range(5)              # range

# Mapping type
d = {"key": "value"}      # dict

# Set types
st = {1, 2, 3}             # set
fs = frozenset([1, 2, 3])  # frozenset (immutable set)

# Boolean
b = True                  # bool (subclass of int: True=1, False=0)

# None type
n = None

# bytes
by = b"hello"

# Checking type of each
for var in [i, f, c, s, l, t, r, d, st, fs, b, n, by]:
    print(type(var).__name__, "->", var)
```

---

## 4. Converting One Data Type to Another (Type Casting)

Python allows **implicit** (automatic) and **explicit** (manual, using functions) type conversion.

```python
# Implicit conversion (Python does it automatically - safe, no data loss)
x = 5          # int
y = 2.5        # float
z = x + y      # int automatically converted to float
print(z, type(z))    # 7.5 <class 'float'>

# Explicit conversion (type casting functions)
a = "100"
print(int(a) + 1)        # str -> int : 101

b = 25
print(str(b) + " apples") # int -> str : "25 apples"

c = "3.14"
print(float(c))           # str -> float : 3.14

d = 3.99
print(int(d))              # float -> int (truncates, not rounds): 3

# list/tuple/set conversions
lst = list((1, 2, 3))       # tuple -> list
tpl = tuple([1, 2, 3])      # list -> tuple
st = set([1, 2, 2, 3])      # list -> set (removes duplicates)
print(lst, tpl, st)

# int -> bool and bool -> int
print(bool(0), bool(1), bool(""), bool("hi"))   # False True False True
print(int(True), int(False))                    # 1 0

# char <-> ASCII
print(ord('A'))     # 65 (char to ASCII)
print(chr(65))       # 'A' (ASCII to char)

# invalid conversion raises error
try:
    int("abc")
except ValueError as e:
    print("Error:", e)
```

---

## 5. Strings in Python (In Detail)

Strings are **immutable** sequences of Unicode characters, enclosed in single, double, or triple quotes.

```python
s = "Python Programming"

# Indexing and slicing
print(s[0])          # 'P'
print(s[-1])          # 'g' (last char)
print(s[0:6])          # 'Python' (slicing)
print(s[::-1])          # reversed string
print(s[::2])            # every 2nd character

# String is immutable
try:
    s[0] = 'J'
except TypeError as e:
    print("Error:", e)

# Common string methods
print(s.upper())            # PYTHON PROGRAMMING
print(s.lower())             # python programming
print(s.title())              # Python Programming
print(s.strip())               # remove leading/trailing whitespace
print(s.replace("Python", "Java"))   # Java Programming
print(s.split())                # ['Python', 'Programming']
print("-".join(["a", "b", "c"]))  # a-b-c
print(s.find("Pro"))              # index of substring: 7
print(s.count("m"))                # count occurrences
print(s.startswith("Py"))           # True
print(s.endswith("ing"))             # True
print(s.isdigit(), s.isalpha())       # False False

# String formatting (3 ways)
name, age = "Alice", 25
print("Name: %s Age: %d" % (name, age))         # old style
print("Name: {} Age: {}".format(name, age))       # .format()
print(f"Name: {name} Age: {age}")                   # f-string (best/modern)

# f-string with expressions
print(f"Next year age: {age + 1}")
print(f"{name = }")     # Name debugging shortcut -> name = 'Alice'

# Multi-line strings
para = """This is
a multi-line
string"""
print(para)

# String concatenation & repetition
print("Hello" + " " + "World")
print("Ha" * 3)     # HaHaHa

# Escape sequences
print("Line1\nLine2\tTabbed\\Backslash\"Quote")

# Checking membership
print("Python" in s)   # True

# String length
print(len(s))
```

---

## 6. Read Data (Input from User)

`input()` always returns a **string**; convert as needed.

```python
# Basic input
name = input("Enter your name: ")
print("Hello,", name)

# Input with type conversion
age = int(input("Enter your age: "))
print("Next year you will be", age + 1)

# Reading multiple values in one line
a, b = input("Enter two numbers separated by space: ").split()
print(int(a) + int(b))

# Reading multiple integers using map
nums = list(map(int, input("Enter numbers: ").split()))
print(nums, sum(nums))

# Reading a file (basic)
with open("sample.txt", "w") as f:
    f.write("Hello File\nSecond Line")

with open("sample.txt", "r") as f:
    content = f.read()          # read whole file
    print(content)

with open("sample.txt", "r") as f:
    for line in f:               # read line by line
        print(line.strip())
```

---

## 7. Conditional Statements

Used for decision making: `if`, `elif`, `else`. Python uses **indentation** (not braces) for blocks.

```python
age = 20

# if-elif-else
if age < 13:
    print("Child")
elif age < 20:
    print("Teenager")
else:
    print("Adult")

# Nested conditions
num = 15
if num > 0:
    if num % 2 == 0:
        print("Positive Even")
    else:
        print("Positive Odd")

# Ternary (conditional) expression
status = "Adult" if age >= 18 else "Minor"
print(status)

# Logical operators in conditions
x = 5
if x > 0 and x < 10:
    print("Single digit positive")

# match-case (Python 3.10+, like switch)
day = 3
match day:
    case 1 | 7:
        print("Weekend")
    case 2 | 3 | 4 | 5 | 6:
        print("Weekday")
    case _:
        print("Invalid")
```

---

## 8. Loops

Python supports `for` (iterating over sequences) and `while` (condition-based) loops, with `break`, `continue`, `else` clauses.

```python
# for loop over range
for i in range(5):          # 0 to 4
    print(i, end=" ")
print()

# for loop over a list
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:
    print(fruit)

# enumerate - get index + value
for idx, fruit in enumerate(fruits):
    print(idx, fruit)

# while loop
n = 5
while n > 0:
    print(n, end=" ")
    n -= 1
print()

# break and continue
for i in range(10):
    if i == 3:
        continue     # skip this iteration
    if i == 7:
        break         # exit loop entirely
    print(i, end=" ")
print()

# else clause with loop (runs if loop completes without break)
for i in range(3):
    print("Checking", i)
else:
    print("Loop completed without break")

# Nested loops (multiplication table)
for i in range(1, 3):
    for j in range(1, 3):
        print(f"{i}x{j}={i*j}", end="  ")
    print()

# infinite loop with controlled exit
count = 0
while True:
    count += 1
    if count > 3:
        break
    print("count =", count)
```

---

## 9. Collections Types (Overview)

Python's built-in collection types store multiple items. Each has different properties:

| Type | Ordered | Mutable | Duplicates | Syntax |
|---|---|---|---|---|
| **List** | Yes | Yes | Yes | `[1, 2, 3]` |
| **Tuple** | Yes | No | Yes | `(1, 2, 3)` |
| **Set** | No | Yes | No | `{1, 2, 3}` |
| **Dictionary** | Yes (3.7+) | Yes | No (keys) | `{"a": 1}` |

```python
# Quick preview of all 4
my_list = [1, 2, 3, 3]           # allows duplicates, ordered, mutable
my_tuple = (1, 2, 3, 3)          # allows duplicates, ordered, immutable
my_set = {1, 2, 3, 3}            # no duplicates, unordered, mutable -> {1,2,3}
my_dict = {"a": 1, "b": 2}       # key-value pairs, unique keys

print(my_list, my_tuple, my_set, my_dict)
```

---

## 10. List in Detail (Including List Comprehension)

Lists are **ordered, mutable, allow duplicates**, and can hold mixed data types.

```python
# Creating lists
lst = [10, 20, 30, "hello", 3.5]
empty = []

# Indexing & slicing
print(lst[0], lst[-1])
print(lst[1:4])

# Adding elements
lst.append(40)                # add at end
lst.insert(1, 15)              # insert at index
lst.extend([50, 60])             # add multiple elements

# Removing elements
lst.remove("hello")               # remove by value
popped = lst.pop()                 # remove & return last item
popped2 = lst.pop(0)                # remove & return item at index
del lst[0]                           # delete by index
# lst.clear()                        # remove all elements

print(lst)

# Searching & counting
nums = [1, 2, 3, 2, 4, 2]
print(nums.index(2))       # first index of value 2
print(nums.count(2))        # count occurrences

# Sorting
nums.sort()                  # ascending, in-place
nums.sort(reverse=True)       # descending
print(nums)
print(sorted(nums))            # returns new sorted list, original unchanged

# Reversing
nums.reverse()
print(nums)

# Copying (avoid reference issues)
a = [1, 2, 3]
b = a.copy()          # shallow copy; b = a would just be a reference
b.append(4)
print(a, b)

# List operations
print([1, 2] + [3, 4])       # concatenation: [1, 2, 3, 4]
print([1, 2] * 3)               # repetition: [1, 2, 1, 2, 1, 2]
print(3 in [1, 2, 3])            # membership check

# Iterating
for item in [10, 20, 30]:
    print(item)

# Nested lists (2D)
matrix = [[1, 2], [3, 4], [5, 6]]
print(matrix[1][0])       # 3
for row in matrix:
    print(row)

# ---- LIST COMPREHENSION ----
# Syntax: [expression for item in iterable if condition]

squares = [x**2 for x in range(10)]
print(squares)                  # [0,1,4,9,...,81]

evens = [x for x in range(20) if x % 2 == 0]
print(evens)

# with if-else in expression
labels = ["even" if x % 2 == 0 else "odd" for x in range(5)]
print(labels)

# nested list comprehension (flatten a 2D list)
matrix = [[1, 2, 3], [4, 5, 6]]
flat = [num for row in matrix for num in row]
print(flat)                     # [1, 2, 3, 4, 5, 6]

# comprehension with function call
words = ["hello", "world", "python"]
upper_words = [w.upper() for w in words]
print(upper_words)

# nested comprehension building a matrix
grid = [[i * j for j in range(3)] for i in range(3)]
print(grid)
```

---

## 11. Tuples in Detail

Tuples are **ordered, immutable, allow duplicates**. Faster than lists, used for fixed data.

```python
# Creating tuples
t = (1, 2, 3)
single = (5,)              # comma is required for single-element tuple, else it's just int
t2 = 1, 2, 3                # parentheses optional
empty = ()

# Indexing & slicing (same as list)
print(t[0], t[-1], t[0:2])

# Tuples are immutable
try:
    t[0] = 100
except TypeError as e:
    print("Error:", e)

# But mutable objects INSIDE a tuple can be changed
t3 = (1, [2, 3], 4)
t3[1].append(99)
print(t3)                    # (1, [2, 3, 99], 4)

# Tuple methods (only 2 exist, since immutable)
nums = (1, 2, 2, 3)
print(nums.count(2))        # 2
print(nums.index(3))         # 3

# Packing and unpacking
person = ("Alice", 25, "Engineer")
name, age, job = person       # unpacking
print(name, age, job)

# unpacking with *
first, *middle, last = (1, 2, 3, 4, 5)
print(first, middle, last)    # 1 [2, 3, 4] 5

# Tuple as dictionary key (lists can't be, tuples can - because immutable/hashable)
locations = {(0, 0): "origin", (1, 1): "point A"}
print(locations[(0, 0)])

# Why use tuples over lists?
# 1. Immutability = data safety   2. Faster & less memory   3. Usable as dict keys / set elements

# Converting between list and tuple
lst = list(t)
back_to_tuple = tuple(lst)
print(lst, back_to_tuple)

# Iterating
for item in t:
    print(item, end=" ")
print()

# Nested tuples
nested = ((1, 2), (3, 4))
print(nested[1][0])    # 3
```

---

## 12. Set and Dictionary in Detail

### Set
Unordered collection of **unique, immutable** elements. Great for membership tests & removing duplicates.

```python
# Creating sets
s = {1, 2, 3, 3, 4}        # duplicates auto-removed -> {1,2,3,4}
s2 = set([1, 2, 2, 3])       # from a list
empty_set = set()               # NOT {} (that's an empty dict!)

# Adding & removing
s.add(5)
s.update([6, 7])            # add multiple
s.remove(1)                   # raises error if not present
s.discard(100)                  # no error if not present
popped = s.pop()                  # removes a random element
print(s)

# Set operations (mathematical)
A = {1, 2, 3, 4}
B = {3, 4, 5, 6}
print(A | B)          # union: {1,2,3,4,5,6}
print(A & B)            # intersection: {3,4}
print(A - B)              # difference: {1,2}
print(A ^ B)                # symmetric difference: {1,2,5,6}

# Subset / superset
print({1, 2}.issubset(A))       # True
print(A.issuperset({1, 2}))       # True

# Membership test (very fast - O(1))
print(3 in A)

# Removing duplicates from a list using set
lst = [1, 2, 2, 3, 3, 3]
unique = list(set(lst))
print(unique)

# Set comprehension
sq_set = {x**2 for x in range(6)}
print(sq_set)

# Frozenset - immutable version of set
fs = frozenset([1, 2, 3])
```

### Dictionary
Stores **key-value pairs**; keys must be unique and hashable (immutable types).

```python
# Creating dictionaries
d = {"name": "Alice", "age": 25, "city": "NYC"}
d2 = dict(name="Bob", age=30)         # using dict() constructor
empty = {}

# Accessing values
print(d["name"])                # direct access (KeyError if missing)
print(d.get("age"))               # safe access
print(d.get("salary", 0))           # default value if key missing

# Adding / updating
d["email"] = "alice@mail.com"       # add new key
d["age"] = 26                        # update existing key
d.update({"age": 27, "country": "USA"})   # update multiple

# Removing
d.pop("email")                # remove specific key
del d["country"]                # delete key
# d.clear()                      # remove all
popped_item = d.popitem()          # removes last inserted item (key, value)

print(d)

# Iterating
for key in d:
    print(key, "->", d[key])

for key, value in d.items():        # best way - key & value together
    print(key, value)

for value in d.values():
    print(value)

for key in d.keys():
    print(key)

# Checking key existence
print("name" in d)             # True (checks keys)

# Dictionary comprehension
squares = {x: x**2 for x in range(5)}
print(squares)                  # {0:0, 1:1, 2:4, 3:9, 4:16}

# filter using comprehension
even_sq = {x: x**2 for x in range(10) if x % 2 == 0}
print(even_sq)

# Nested dictionary
students = {
    "s1": {"name": "Alice", "grade": "A"},
    "s2": {"name": "Bob", "grade": "B"}
}
print(students["s1"]["name"])

# merging dicts (Python 3.9+)
d3 = {"a": 1} | {"b": 2}
print(d3)
```

---

## 13. Functions in Python (In Detail)

A function is a **reusable block of code**. Defined using `def`.

```python
# Basic function
def greet():
    print("Hello!")
greet()

# Function with parameters and return value
def add(a, b):
    return a + b
print(add(3, 5))

# Default arguments
def greet_user(name="Guest"):
    print(f"Hello, {name}")
greet_user()             # uses default
greet_user("Alice")        # overrides default

# Positional vs Keyword arguments
def student(name, age):
    print(name, age)
student("Bob", 20)             # positional
student(age=20, name="Bob")     # keyword (order doesn't matter)

# Variable-length arguments
def total(*args):              # *args -> tuple of positional args
    return sum(args)
print(total(1, 2, 3, 4))

def show_info(**kwargs):        # **kwargs -> dict of keyword args
    for k, v in kwargs.items():
        print(k, ":", v)
show_info(name="Alice", age=25)

# Combining all argument types
def demo(a, b=10, *args, **kwargs):
    print(a, b, args, kwargs)
demo(1, 2, 3, 4, x=5, y=6)      # 1 2 (3, 4) {'x': 5, 'y': 6}

# Return multiple values (returns a tuple)
def min_max(numbers):
    return min(numbers), max(numbers)
low, high = min_max([4, 2, 9, 1])
print(low, high)

# Lambda (anonymous) functions
square = lambda x: x ** 2
print(square(5))
add_lambda = lambda a, b: a + b
print(add_lambda(2, 3))

# Variable scope: local vs global
x = 10          # global
def scope_demo():
    x = 20        # local, shadows global
    print("Inside:", x)
scope_demo()
print("Outside:", x)

def modify_global():
    global x
    x = 100
modify_global()
print(x)              # 100

# Recursive function
def factorial(n):
    if n == 0:
        return 1
    return n * factorial(n - 1)
print(factorial(5))       # 120

# Function with docstring
def divide(a, b):
    """Divides a by b and returns the result."""
    return a / b
print(divide.__doc__)

# Type hints (optional, for clarity)
def multiply(a: int, b: int) -> int:
    return a * b
```

---

## 14. Map and Filter (with Lambda & Reduce)

Functional-programming tools that operate on iterables.

```python
nums = [1, 2, 3, 4, 5]

# map() - applies a function to every item
squared = list(map(lambda x: x ** 2, nums))
print(squared)                     # [1, 4, 9, 16, 25]

def celsius_to_fahrenheit(c):
    return (c * 9/5) + 32
temps = [0, 20, 30, 100]
print(list(map(celsius_to_fahrenheit, temps)))

# map with multiple iterables
a = [1, 2, 3]
b = [10, 20, 30]
summed = list(map(lambda x, y: x + y, a, b))
print(summed)                       # [11, 22, 33]

# filter() - keeps items where function returns True
evens = list(filter(lambda x: x % 2 == 0, nums))
print(evens)                          # [2, 4]

words = ["hi", "hello", "hey", "greetings"]
long_words = list(filter(lambda w: len(w) > 3, words))
print(long_words)

# reduce() - reduces iterable to single value (from functools)
from functools import reduce
product = reduce(lambda x, y: x * y, nums)
print(product)                          # 120 (1*2*3*4*5)

total_sum = reduce(lambda x, y: x + y, nums, 0)   # 0 is initial value
print(total_sum)

# Combining map + filter
result = list(map(lambda x: x**2, filter(lambda x: x % 2 == 0, nums)))
print(result)         # squares of even numbers: [4, 16]

# Equivalent using list comprehension (often more readable)
result2 = [x**2 for x in nums if x % 2 == 0]
print(result2)
```

---

## 15. Decorators and Generators

### Decorators
A decorator is a function that **wraps another function to extend/modify its behavior** without changing its code.

```python
# Basic decorator
def my_decorator(func):
    def wrapper():
        print("Before function call")
        func()
        print("After function call")
    return wrapper

@my_decorator
def say_hello():
    print("Hello!")

say_hello()
# Output:
# Before function call
# Hello!
# After function call

# Decorator with arguments (using *args, **kwargs)
def log_decorator(func):
    def wrapper(*args, **kwargs):
        print(f"Calling {func.__name__} with {args}, {kwargs}")
        result = func(*args, **kwargs)
        print(f"Result: {result}")
        return result
    return wrapper

@log_decorator
def add(a, b):
    return a + b

add(3, 5)

# Timing decorator (practical example)
import time
def timer(func):
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        print(f"{func.__name__} took {time.time()-start:.4f} sec")
        return result
    return wrapper

@timer
def slow_function():
    time.sleep(1)

slow_function()

# Decorator with parameters (decorator factory)
def repeat(times):
    def decorator(func):
        def wrapper(*args, **kwargs):
            for _ in range(times):
                func(*args, **kwargs)
        return wrapper
    return decorator

@repeat(3)
def greet(name):
    print(f"Hi {name}")

greet("Alice")     # prints 3 times
```

### Generators
Functions that use `yield` to produce values **lazily, one at a time**, saving memory (don't hold entire sequence in memory).

```python
# Basic generator function
def count_up_to(n):
    count = 1
    while count <= n:
        yield count          # pauses here, resumes on next call
        count += 1

gen = count_up_to(5)
print(next(gen))       # 1
print(next(gen))        # 2
for val in gen:           # continues from where it left off: 3,4,5
    print(val)

# Generator vs normal function (memory efficient for large data)
def squares_generator(n):
    for i in range(n):
        yield i ** 2

for sq in squares_generator(5):
    print(sq, end=" ")
print()

# Generator expression (like list comprehension but lazy)
gen_exp = (x**2 for x in range(5))    # note: () not []
print(list(gen_exp))

# Infinite generator (only possible because it's lazy)
def infinite_counter():
    n = 1
    while True:
        yield n
        n += 1

counter = infinite_counter()
for _ in range(5):
    print(next(counter), end=" ")   # 1 2 3 4 5
print()

# Fibonacci generator (practical example)
def fibonacci(limit):
    a, b = 0, 1
    while a < limit:
        yield a
        a, b = b, a + b

print(list(fibonacci(50)))
```

---

## 16. Modules and Packages

A **module** is a single `.py` file with reusable code. A **package** is a directory of modules containing an `__init__.py` file.

```python
# ---- math_utils.py (imagine this is a separate file/module) ----
# def add(a, b):
#     return a + b
# def subtract(a, b):
#     return a - b
# PI = 3.14159

# ---- Importing modules ----
import math                          # built-in module
print(math.sqrt(16), math.pi)

import math as m                      # import with alias
print(m.factorial(5))

from math import sqrt, pi              # import specific members
print(sqrt(25), pi)

from math import *                      # import everything (not recommended)

# Importing your own module (if math_utils.py exists in same folder)
# import math_utils
# print(math_utils.add(2, 3))
# from math_utils import add, PI

# Commonly used built-in modules
import random
print(random.randint(1, 10))            # random integer
print(random.choice(["a", "b", "c"]))     # random choice

import datetime
print(datetime.datetime.now())

import os
print(os.getcwd())                        # current working directory

# ---- Package structure example ----
# mypackage/
#     __init__.py
#     module1.py
#     module2.py
#     subpackage/
#         __init__.py
#         module3.py
#
# Usage: from mypackage import module1
#        from mypackage.subpackage import module3

# __name__ == "__main__" idiom (code runs only when file executed directly, not when imported)
def main():
    print("Running as main program")

if __name__ == "__main__":
    main()
```

---

## 17. Virtual Environment

A virtual environment is an **isolated Python environment** with its own dependencies, separate from the system-wide Python. Prevents version conflicts between projects.

```bash
# Create a virtual environment (creates a 'venv' folder)
python -m venv venv

# Activate it
# Windows:
venv\Scripts\activate
# macOS/Linux:
source venv/bin/activate

# Once activated, your terminal prompt shows (venv)
# Install packages inside the venv (isolated from global packages)
pip install requests pandas numpy

# List installed packages
pip list

# Save dependencies to a file
pip freeze > requirements.txt

# Install from requirements.txt (e.g. on another machine)
pip install -r requirements.txt

# Deactivate the environment
deactivate

# Delete environment: simply delete the venv folder
```

```python
# Verifying you're in a venv (run inside Python)
import sys
print(sys.prefix)              # different path than base Python when venv is active
print(sys.executable)           # shows path to venv's python.exe
```

---

## 18. Reading Data — Excel, JSON, YAML

```python
# ---- Reading/Writing JSON ----
import json

data = {"name": "Alice", "age": 25, "skills": ["Python", "SQL"]}

# Write JSON to a file
with open("data.json", "w") as f:
    json.dump(data, f, indent=4)

# Read JSON from a file
with open("data.json", "r") as f:
    loaded = json.load(f)
print(loaded)

# Convert Python object <-> JSON string
json_str = json.dumps(data, indent=2)    # dict -> JSON string
print(json_str)
parsed = json.loads(json_str)              # JSON string -> dict
print(parsed["name"])


# ---- Reading/Writing Excel (using pandas + openpyxl) ----
# pip install pandas openpyxl
import pandas as pd

df = pd.DataFrame({
    "Name": ["Alice", "Bob"],
    "Age": [25, 30]
})

# Write to Excel
df.to_excel("data.xlsx", index=False, sheet_name="Sheet1")

# Read from Excel
df_read = pd.read_excel("data.xlsx", sheet_name="Sheet1")
print(df_read)

# Read specific columns / rows
print(df_read["Name"])
print(df_read.head(1))


# ---- Reading/Writing YAML (using PyYAML) ----
# pip install pyyaml
import yaml

config = {"database": {"host": "localhost", "port": 5432}, "debug": True}

# Write YAML to a file
with open("config.yaml", "w") as f:
    yaml.dump(config, f)

# Read YAML from a file
with open("config.yaml", "r") as f:
    loaded_yaml = yaml.safe_load(f)
print(loaded_yaml)
print(loaded_yaml["database"]["host"])

# ---- Reading CSV (bonus, very common) ----
import csv
with open("data.csv", "w", newline="") as f:
    writer = csv.writer(f)
    writer.writerow(["Name", "Age"])
    writer.writerow(["Alice", 25])

with open("data.csv", "r") as f:
    reader = csv.reader(f)
    for row in reader:
        print(row)

# CSV using pandas (simpler)
df_csv = pd.read_csv("data.csv")
print(df_csv)
```

---

## 19. Introduction to OOPs (Object-Oriented Programming)

OOP organizes code around **objects** (instances of **classes**) that bundle data (attributes) and behavior (methods). Four pillars: **Encapsulation, Inheritance, Polymorphism, Abstraction**.

```python
# Defining a class and creating an object
class Car:
    # constructor - runs when object is created
    def __init__(self, brand, model):
        self.brand = brand       # instance attribute
        self.model = model

    def display(self):            # instance method
        print(f"{self.brand} {self.model}")

# Creating objects (instances)
car1 = Car("Toyota", "Corolla")
car2 = Car("Honda", "Civic")
car1.display()
car2.display()

# Class variables (shared across all instances) vs instance variables
class Employee:
    company = "TechCorp"          # class variable - shared
    def __init__(self, name):
        self.name = name           # instance variable - unique per object

e1 = Employee("Alice")
e2 = Employee("Bob")
print(e1.company, e2.company)      # both share "TechCorp"
Employee.company = "NewCorp"
print(e1.company, e2.company)        # both updated

# self keyword refers to the current instance
# __init__ is the constructor, called automatically on object creation

# Object methods vs class methods vs static methods
class MathHelper:
    def instance_method(self):
        return "I need an instance"

    @classmethod
    def class_method(cls):
        return "I work with the class itself"

    @staticmethod
    def static_method():
        return "I don't need class or instance data"

print(MathHelper.class_method())
print(MathHelper.static_method())
```

---

## 20. Encapsulation

Bundling data and methods together, and **restricting direct access** to some components (data hiding) using access modifiers.

```python
class BankAccount:
    def __init__(self, owner, balance):
        self.owner = owner              # public - accessible anywhere
        self._pin = "1234"               # protected (convention: single underscore)
        self.__balance = balance          # private (name-mangled: double underscore)

    # Getter method
    def get_balance(self):
        return self.__balance

    # Setter method (with validation - the real benefit of encapsulation)
    def deposit(self, amount):
        if amount > 0:
            self.__balance += amount
        else:
            print("Invalid deposit amount")

    def withdraw(self, amount):
        if 0 < amount <= self.__balance:
            self.__balance -= amount
        else:
            print("Insufficient funds")

account = BankAccount("Alice", 1000)
account.deposit(500)
account.withdraw(200)
print(account.get_balance())     # 1300

# Direct access to private variable fails (as expected)
try:
    print(account.__balance)
except AttributeError as e:
    print("Error:", e)

# But it's accessible via name mangling (shows it's convention, not true security)
print(account._BankAccount__balance)     # 1300

# Property decorator - pythonic getter/setter
class Temperature:
    def __init__(self, celsius):
        self._celsius = celsius

    @property
    def fahrenheit(self):                  # acts like an attribute, not a method call
        return (self._celsius * 9/5) + 32

    @fahrenheit.setter
    def fahrenheit(self, value):
        self._celsius = (value - 32) * 5/9

temp = Temperature(25)
print(temp.fahrenheit)      # 77.0  (called without parentheses!)
temp.fahrenheit = 100
print(temp._celsius)          # 37.77...
```

---

## 21. Inheritance

Allows a class (**child/derived**) to acquire properties and methods of another class (**parent/base**), enabling code reuse.

```python
# Single inheritance
class Animal:
    def __init__(self, name):
        self.name = name

    def eat(self):
        print(f"{self.name} is eating")

    def sound(self):
        print("Some generic sound")

class Dog(Animal):                     # Dog inherits from Animal
    def sound(self):                    # method overriding
        print(f"{self.name} says Woof!")

class Cat(Animal):
    def sound(self):
        print(f"{self.name} says Meow!")

d = Dog("Rex")
d.eat()          # inherited method
d.sound()          # overridden method

# super() - call parent class's methods/constructor
class Puppy(Dog):
    def __init__(self, name, age):
        super().__init__(name)       # call parent constructor
        self.age = age

    def sound(self):
        super().sound()               # call parent method too
        print(f"{self.name} is just a puppy though")

p = Puppy("Buddy", 1)
p.sound()

# Multiple inheritance
class Flyer:
    def fly(self):
        print("I can fly")

class Swimmer:
    def swim(self):
        print("I can swim")

class Duck(Flyer, Swimmer):     # inherits from both
    pass

duck = Duck()
duck.fly()
duck.swim()

# Multilevel inheritance
class A:
    def method_a(self): print("A")
class B(A):
    def method_b(self): print("B")
class C(B):
    def method_c(self): print("C")

c = C()
c.method_a(); c.method_b(); c.method_c()     # all inherited down the chain

# Method Resolution Order (MRO) - important for multiple inheritance
print(Duck.__mro__)

# isinstance() and issubclass()
print(isinstance(d, Dog))          # True
print(isinstance(d, Animal))        # True (Dog IS-A Animal)
print(issubclass(Dog, Animal))       # True
```

---

## 22. Polymorphism

"Many forms" — the **same interface/method name behaves differently** depending on the object calling it.

```python
# Method overriding (runtime polymorphism)
class Shape:
    def area(self):
        return 0

class Circle(Shape):
    def __init__(self, radius):
        self.radius = radius
    def area(self):
        return 3.14 * self.radius ** 2

class Rectangle(Shape):
    def __init__(self, l, w):
        self.l, self.w = l, w
    def area(self):
        return self.l * self.w

shapes = [Circle(5), Rectangle(4, 6)]
for shape in shapes:
    print(f"{type(shape).__name__} area: {shape.area()}")   # same method call, different behavior

# Duck typing (Python's dynamic polymorphism - "if it walks like a duck...")
class Duck:
    def sound(self): print("Quack")
class Human:
    def sound(self): print("I can quack too!")

def make_it_sound(obj):
    obj.sound()          # works on ANY object with a sound() method

make_it_sound(Duck())
make_it_sound(Human())

# Operator overloading (polymorphism for built-in operators)
class Point:
    def __init__(self, x, y):
        self.x, self.y = x, y

    def __add__(self, other):              # overload the + operator
        return Point(self.x + other.x, self.y + other.y)

    def __str__(self):                       # overload print() representation
        return f"Point({self.x}, {self.y})"

p1 = Point(1, 2)
p2 = Point(3, 4)
print(p1 + p2)          # Point(4, 6) - uses __add__

# Function polymorphism (built-in example)
print(len("hello"))       # works on strings
print(len([1, 2, 3]))       # works on lists
print(len({"a": 1}))          # works on dicts
```

---

## 23. Abstraction

Hiding complex implementation details and **exposing only essential features**, usually via abstract classes/methods (using the `abc` module).

```python
from abc import ABC, abstractmethod

# Abstract Base Class - cannot be instantiated directly
class Vehicle(ABC):
    @abstractmethod
    def start_engine(self):            # must be implemented by subclasses
        pass

    @abstractmethod
    def stop_engine(self):
        pass

    def fuel_type(self):                 # regular (concrete) method - can have implementation
        print("Runs on petrol/diesel/electric")

# Trying to instantiate abstract class directly fails
try:
    v = Vehicle()
except TypeError as e:
    print("Error:", e)

class Car(Vehicle):
    def start_engine(self):
        print("Car engine started with a key/button")
    def stop_engine(self):
        print("Car engine stopped")

class ElectricBike(Vehicle):
    def start_engine(self):
        print("Bike motor activated silently")
    def stop_engine(self):
        print("Bike motor deactivated")

car = Car()
car.start_engine()
car.fuel_type()

bike = ElectricBike()
bike.start_engine()

# Real-world analogy: driving a car - you press accelerator (interface)
# without needing to know the internal engine mechanics (implementation hidden)

# Abstraction ensures all subclasses implement required methods,
# enforcing a consistent structure/contract across the codebase
```

---

## 24. Exception Handling

Exceptions are **runtime errors**. Python handles them using `try`, `except`, `else`, `finally` to prevent program crashes.

```python
# Basic try-except
try:
    result = 10 / 0
except ZeroDivisionError:
    print("Cannot divide by zero!")

# Catching multiple exceptions
try:
    x = int("abc")
except (ValueError, TypeError) as e:
    print("Error occurred:", e)

# Catching multiple exception types separately
try:
    lst = [1, 2, 3]
    print(lst[10])
except IndexError:
    print("Index out of range")
except Exception as e:              # generic catch-all (should be last)
    print("Some other error:", e)

# else - runs only if NO exception occurred
try:
    num = int("100")
except ValueError:
    print("Conversion failed")
else:
    print("Conversion succeeded:", num)

# finally - ALWAYS runs (cleanup code: closing files, connections, etc.)
try:
    f = open("data.json")
    data = f.read()
except FileNotFoundError:
    print("File not found")
finally:
    print("This always executes (e.g., closing resources)")

# Raising custom exceptions
def withdraw(balance, amount):
    if amount > balance:
        raise ValueError("Insufficient balance!")
    return balance - amount

try:
    withdraw(100, 500)
except ValueError as e:
    print("Caught:", e)

# Custom Exception classes
class InsufficientFundsError(Exception):
    def __init__(self, message="Not enough funds in account"):
        self.message = message
        super().__init__(self.message)

def process_payment(balance, amount):
    if amount > balance:
        raise InsufficientFundsError(f"Need {amount - balance} more")
    print("Payment successful")

try:
    process_payment(100, 150)
except InsufficientFundsError as e:
    print("Custom error:", e)

# Exception hierarchy tip: catch specific exceptions before generic ones
# Common built-in exceptions: ValueError, TypeError, KeyError, IndexError,
# ZeroDivisionError, FileNotFoundError, AttributeError, ImportError

# Chained/nested try blocks
try:
    try:
        1 / 0
    except ZeroDivisionError:
        raise ValueError("Converted error") from None
except ValueError as e:
    print("Outer caught:", e)
```

---

## 25. Decorators and Custom Iterators

### Custom Iterators
An **iterator** implements `__iter__()` and `__next__()`. An **iterable** implements `__iter__()` and returns an iterator. This is what powers `for` loops internally.

```python
# Custom iterator class
class CountUpTo:
    def __init__(self, limit):
        self.limit = limit
        self.current = 1

    def __iter__(self):           # returns the iterator object itself
        return self

    def __next__(self):            # returns next value, raises StopIteration when done
        if self.current > self.limit:
            raise StopIteration
        value = self.current
        self.current += 1
        return value

counter = CountUpTo(5)
for num in counter:                # for loop uses __iter__ and __next__ automatically
    print(num, end=" ")
print()

# Using iter() and next() manually (how 'for' works under the hood)
it = iter(CountUpTo(3))
print(next(it))       # 1
print(next(it))         # 2
print(next(it))           # 3
try:
    print(next(it))          # raises StopIteration
except StopIteration:
    print("Iteration finished")

# Custom iterable class (separates the container from the iterator)
class NumberCollection:
    def __init__(self, numbers):
        self.numbers = numbers

    def __iter__(self):
        return iter(self.numbers)      # delegate to list's built-in iterator

nums = NumberCollection([10, 20, 30])
for n in nums:
    print(n, end=" ")
print()

# Fibonacci as a custom iterator class
class Fibonacci:
    def __init__(self, max_count):
        self.max_count = max_count
        self.count = 0
        self.a, self.b = 0, 1

    def __iter__(self):
        return self

    def __next__(self):
        if self.count >= self.max_count:
            raise StopIteration
        result = self.a
        self.a, self.b = self.b, self.a + self.b
        self.count += 1
        return result

print(list(Fibonacci(8)))     # [0, 1, 1, 2, 3, 5, 8, 13]
```

### Class-based Decorators (advanced decorator pattern)

```python
# Decorator implemented as a class using __call__
class CallCounter:
    def __init__(self, func):
        self.func = func
        self.count = 0

    def __call__(self, *args, **kwargs):
        self.count += 1
        print(f"{self.func.__name__} has been called {self.count} times")
        return self.func(*args, **kwargs)

@CallCounter
def say_hi():
    print("Hi!")

say_hi()
say_hi()
say_hi()

# Combining generators + decorators + iterators (putting it all together)
def debug(func):
    def wrapper(*args, **kwargs):
        print(f"Generator {func.__name__} started")
        yield from func(*args, **kwargs)     # yield from delegates to inner generator
        print(f"Generator {func.__name__} finished")
    return wrapper

@debug
def gen_numbers(n):
    for i in range(n):
        yield i

for val in gen_numbers(3):
    print("value:", val)
```

---

## Quick Recap Table

| # | Topic | Key Concept |
|---|---|---|
| 1 | Introduction | High-level, interpreted, readable syntax |
| 2 | Variables | Dynamic typing, no declaration needed |
| 3 | Data Types | int, float, str, bool, list, tuple, set, dict, None |
| 4 | Type Conversion | Implicit vs explicit casting |
| 5 | Strings | Immutable, rich methods, f-strings |
| 6 | Read Data | `input()`, file reading |
| 7 | Conditionals | if/elif/else, match-case |
| 8 | Loops | for, while, break/continue/else |
| 9 | Collections | List, Tuple, Set, Dict overview |
| 10 | List | Mutable, ordered, list comprehension |
| 11 | Tuple | Immutable, ordered, packing/unpacking |
| 12 | Set & Dict | Unique elements / key-value pairs |
| 13 | Functions | def, *args, **kwargs, lambda, recursion |
| 14 | Map/Filter | Functional programming with lambda, reduce |
| 15 | Decorators/Generators | Function wrapping, lazy evaluation with yield |
| 16 | Modules/Packages | import, __init__.py, __name__ == "__main__" |
| 17 | Virtual Env | Isolated dependency environments |
| 18 | Reading Data | JSON, Excel (pandas), YAML, CSV |
| 19 | OOP Intro | Classes, objects, self, __init__ |
| 20 | Encapsulation | Private/protected attrs, property decorator |
| 21 | Inheritance | Code reuse, super(), MRO |
| 22 | Polymorphism | Method overriding, duck typing, operator overloading |
| 23 | Abstraction | Abstract classes/methods via ABC |
| 24 | Exception Handling | try/except/else/finally, custom exceptions |
| 25 | Custom Iterators | __iter__, __next__, StopIteration |

---
*End of Notes — Happy Coding! 🐍*

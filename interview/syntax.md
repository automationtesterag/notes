# Loops & Conditionals — Java vs Python vs JavaScript

A quick side-by-side syntax reference for the three languages. Loops are organized by their actual name (`for`, `for-each`, `for-in`, `for-of`, `while`, `do-while`, ...) so you can see which construct each language has for it, or its closest equivalent.

## Table of Contents

**Conditionals**
- [if](#if)
- [if / else](#if--else)
- [if / else if / else](#if--else-if--else)
- [Ternary / conditional expression](#ternary--conditional-expression)
- [switch / match](#switch--match)
- [Logical AND / OR / NOT](#logical-and--or--not)

**Loops**
- [for](#for)
- [for-each](#for-each)
- [for-in](#for-in)
- [for-of](#for-of)
- [while](#while)
- [do-while](#do-while)
- [Labeled loop](#labeled-loop)
- [for-else](#for-else)
- [Async for](#async-for)
- [break](#break)
- [continue](#continue)
- [Infinite loop](#infinite-loop)

---

## Conditionals

### if

**Java**
```java
if (x > 10) {
    System.out.println("big");
}
```

**Python**
```python
if x > 10:
    print("big")
```

**JavaScript**
```javascript
if (x > 10) {
    console.log("big");
}
```

[Back to top](#table-of-contents)

---

### if / else

**Java**
```java
if (x > 10) {
    System.out.println("big");
} else {
    System.out.println("small");
}
```

**Python**
```python
if x > 10:
    print("big")
else:
    print("small")
```

**JavaScript**
```javascript
if (x > 10) {
    console.log("big");
} else {
    console.log("small");
}
```

[Back to top](#table-of-contents)

---

### if / else if / else

**Java**
```java
if (x > 10) {
    System.out.println("big");
} else if (x > 5) {
    System.out.println("medium");
} else {
    System.out.println("small");
}
```

**Python**
```python
if x > 10:
    print("big")
elif x > 5:
    print("medium")
else:
    print("small")
```

**JavaScript**
```javascript
if (x > 10) {
    console.log("big");
} else if (x > 5) {
    console.log("medium");
} else {
    console.log("small");
}
```

[Back to top](#table-of-contents)

---

### Ternary / conditional expression

**Java**
```java
String r = x > 10 ? "big" : "small";
```

**Python**
```python
r = "big" if x > 10 else "small"
```

**JavaScript**
```javascript
const r = x > 10 ? "big" : "small";
```

[Back to top](#table-of-contents)

---

### switch / match

**Java**
```java
switch (day) {
    case 1:
        System.out.println("Mon");
        break;
    default:
        System.out.println("?");
}
```

**Python**
```python
match day:
    case 1:
        print("Mon")
    case _:
        print("?")
```

**JavaScript**
```javascript
switch (day) {
    case 1:
        console.log("Mon");
        break;
    default:
        console.log("?");
}
```

> **Note:** Python's `match`/`case` (structural pattern matching) requires 3.10+. Dict-based dispatch is common in older Python code as a switch substitute.

[Back to top](#table-of-contents)

---

### Logical AND / OR / NOT

**Java**
```java
if (a > 0 && b > 0) { }
if (a > 0 || b > 0) { }
if (!found) { }
```

**Python**
```python
if a > 0 and b > 0:
    pass
if a > 0 or b > 0:
    pass
if not found:
    pass
```

**JavaScript**
```javascript
if (a > 0 && b > 0) { }
if (a > 0 || b > 0) { }
if (!found) { }
```

[Back to top](#table-of-contents)

---

## Loops

### for

The classic counter-based loop: `init; condition; update`.

**Java** — has it natively
```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

**Python** — no C-style `for`; `range()` inside a `for` is the substitute
```python
for i in range(5):
    print(i)
```

**JavaScript** — has it natively
```javascript
for (let i = 0; i < 5; i++) {
    console.log(i);
}
```

[Back to top](#table-of-contents)

---

### for-each

Loop directly over the elements of a collection (no index/counter). Java calls this the "enhanced for" loop; Python's plain `for` is inherently for-each; JS's version of this is `for...of`.

**Java** — `for (Type item : collection)`
```java
for (String name : names) {
    System.out.println(name);
}
```

**Python** — this *is* Python's normal `for`
```python
for name in names:
    print(name)
```

**JavaScript** — `for...of` (see [for-of](#for-of))
```javascript
for (const name of names) {
    console.log(name);
}
```

[Back to top](#table-of-contents)

---

### for-in

Iterates over **keys** (object properties, dictionary keys, or array indices) — not values.

**Java** — no `for-in`; closest is iterating `map.keySet()`
```java
for (String key : map.keySet()) {
    System.out.println(key);
}
```

**Python** — no separate `for-in` construct; iterating a `dict` directly gives you its keys
```python
for key in d:
    print(key)
```

**JavaScript** — `for...in` exists as its own keyword, and is meant for object keys
```javascript
for (const key in obj) {
    console.log(key);
}
```

> **Careful:** Using `for...in` on an *array* in JS gives you index strings (`"0"`, `"1"`, ...) and can pick up inherited enumerable properties. It's meant for plain objects — use `for...of` for arrays.

[Back to top](#table-of-contents)

---

### for-of

Iterates over **values** of an iterable (arrays, strings, Maps, Sets, generators). This is JavaScript-specific terminology — Java's for-each and Python's for loop already do this by default.

**Java** — the enhanced for loop is JS's `for-of` equivalent
```java
for (String name : names) {
    System.out.println(name);
}
```

**Python** — its `for` loop is JS's `for-of` equivalent
```python
for name in names:
    print(name)
```

**JavaScript** — `for...of` is its own keyword, distinct from `for...in`
```javascript
for (const name of names) {
    console.log(name);
}

// with index:
for (const [i, name] of names.entries()) {
    console.log(i, name);
}
```

[Back to top](#table-of-contents)

---

### while

Loop while a condition holds, checked **before** each iteration.

**Java**
```java
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}
```

**Python**
```python
i = 0
while i < 5:
    print(i)
    i += 1
```

**JavaScript**
```javascript
let i = 0;
while (i < 5) {
    console.log(i);
    i++;
}
```

[Back to top](#table-of-contents)

---

### do-while

Like `while`, but the condition is checked **after** each iteration — so the body always runs at least once.

**Java** — has it natively
```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

**Python** — no `do-while` keyword; emulate with `while True` + `break`
```python
i = 0
while True:
    print(i)
    i += 1
    if i >= 5:
        break
```

**JavaScript** — has it natively
```javascript
let i = 0;
do {
    console.log(i);
    i++;
} while (i < 5);
```

[Back to top](#table-of-contents)

---

### Labeled loop

Lets `break`/`continue` target an **outer** loop from inside a nested one.

**Java** — has native labels
```java
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) continue outer;
        if (i == 2) break outer;
        System.out.println(i + "," + j);
    }
}
```

**Python** — no labels; use a flag or a function with `return`
```python
def run():
    for i in range(3):
        for j in range(3):
            if j == 1:
                break      # only breaks the inner loop
            if i == 2:
                return     # exits both loops
            print(i, j)

run()
```

**JavaScript** — has native labels
```javascript
outer:
for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
        if (j === 1) continue outer;
        if (i === 2) break outer;
        console.log(i, j);
    }
}
```

[Back to top](#table-of-contents)

---

### for-else

The loop's `else` block runs only if the loop finished **without** hitting `break`. Python-only construct — no direct equivalent keyword in Java or JS.

**Python**
```python
for n in range(2, 10):
    if n % 7 == 0:
        print("found a multiple of 7:", n)
        break
else:
    print("no multiple of 7 found")
```

**Java** — no `for-else`; emulate with a flag
```java
boolean found = false;
for (int n = 2; n < 10; n++) {
    if (n % 7 == 0) {
        System.out.println("found a multiple of 7: " + n);
        found = true;
        break;
    }
}
if (!found) {
    System.out.println("no multiple of 7 found");
}
```

**JavaScript** — no `for-else`; emulate with a flag
```javascript
let found = false;
for (let n = 2; n < 10; n++) {
    if (n % 7 === 0) {
        console.log("found a multiple of 7:", n);
        found = true;
        break;
    }
}
if (!found) {
    console.log("no multiple of 7 found");
}
```

[Back to top](#table-of-contents)

---

### Async for

Loop over values as they arrive asynchronously (streams, async generators).

**Java** — no language-level async-for; reactive libraries (Project Reactor, RxJava) use operators/callbacks instead
```java
// Flux.fromIterable(items).subscribe(item -> process(item));
```

**Python** — `async for` over an async iterator/generator
```python
async for item in async_iterable:
    print(item)
```

**JavaScript** — `for await...of` over an async iterable
```javascript
for await (const item of asyncIterable) {
    console.log(item);
}
```

[Back to top](#table-of-contents)

---

### break

**Java**
```java
for (int i = 0; i < 10; i++) {
    if (i == 5) break;
}
```

**Python**
```python
for i in range(10):
    if i == 5:
        break
```

**JavaScript**
```javascript
for (let i = 0; i < 10; i++) {
    if (i === 5) break;
}
```

[Back to top](#table-of-contents)

---

### continue

**Java**
```java
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) continue;
    System.out.println(i);
}
```

**Python**
```python
for i in range(10):
    if i % 2 == 0:
        continue
    print(i)
```

**JavaScript**
```javascript
for (let i = 0; i < 10; i++) {
    if (i % 2 === 0) continue;
    console.log(i);
}
```

[Back to top](#table-of-contents)

---

### Infinite loop

**Java**
```java
while (true) {
    // ...
    break;
}
// or: for (;;) { ... break; }
```

**Python**
```python
while True:
    # ...
    break
```

**JavaScript**
```javascript
while (true) {
    // ...
    break;
}
// or: for (;;) { ... break; }
```

[Back to top](#table-of-contents)

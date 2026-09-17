# Loops & Conditionals — Java vs Python vs JavaScript

A quick side-by-side syntax reference for the three languages.

## Table of Contents

**Conditionals**
- [if](#if)
- [if / else](#if--else)
- [if / else if / else](#if--else-if--else)
- [Ternary / conditional expression](#ternary--conditional-expression)
- [switch / match](#switch--match)
- [Logical AND / OR / NOT](#logical-and--or--not)

**Loops**
- [Classic counting loop](#classic-counting-loop)
- [While loop](#while-loop)
- [Do-while loop](#do-while-loop)
- [Iterate over a collection](#iterate-over-a-collection)
- [Iterate with index](#iterate-with-index)
- [Iterate over map / dict / object keys](#iterate-over-map--dict--object-keys)
- [Iterate over map / dict entries](#iterate-over-map--dict-entries)
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

### Classic counting loop

**Java**
```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

**Python**
```python
for i in range(5):
    print(i)
```

**JavaScript**
```javascript
for (let i = 0; i < 5; i++) {
    console.log(i);
}
```

[Back to top](#table-of-contents)

---

### While loop

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

### Do-while loop

**Java**
```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

**Python** *(no native do-while — emulate with `while True` + `break`)*
```python
i = 0
while True:
    print(i)
    i += 1
    if i >= 5:
        break
```

**JavaScript**
```javascript
let i = 0;
do {
    console.log(i);
    i++;
} while (i < 5);
```

[Back to top](#table-of-contents)

---

### Iterate over a collection

**Java**
```java
for (String name : names) {
    System.out.println(name);
}
```

**Python**
```python
for name in names:
    print(name)
```

**JavaScript**
```javascript
for (const name of names) {
    console.log(name);
}
```

[Back to top](#table-of-contents)

---

### Iterate with index

**Java**
```java
for (int i = 0; i < names.size(); i++) {
    System.out.println(i + ": " + names.get(i));
}
```

**Python**
```python
for i, name in enumerate(names):
    print(i, name)
```

**JavaScript**
```javascript
names.forEach((name, i) => {
    console.log(i, name);
});
```

[Back to top](#table-of-contents)

---

### Iterate over map / dict / object keys

**Java**
```java
for (String key : map.keySet()) {
    System.out.println(key);
}
```

**Python**
```python
for key in d:
    print(key)
```

**JavaScript**
```javascript
for (const key in obj) {
    console.log(key);
}
```

[Back to top](#table-of-contents)

---

### Iterate over map / dict entries

**Java**
```java
for (Map.Entry<K, V> e : map.entrySet()) {
    System.out.println(e.getKey() + "=" + e.getValue());
}
```

**Python**
```python
for k, v in d.items():
    print(k, v)
```

**JavaScript**
```javascript
for (const [k, v] of Object.entries(obj)) {
    console.log(k, v);
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
```

[Back to top](#table-of-contents)

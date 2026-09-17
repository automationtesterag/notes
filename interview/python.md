Here's Python — same five programs, each with a built-in-methods version and a manual (no built-in) version.

## 1. Reverse a String

```python
# Using built-in method (slicing)
def reverse_built_in(s):
    return s[::-1]

# Without built-in method
def reverse_manual(s):
    result = ""
    for ch in s:
        result = ch + result
    return result

if __name__ == "__main__":
    text = "Hello World"
    print("Built-in:", reverse_built_in(text))
    print("Manual  :", reverse_manual(text))
```

## 2. Reverse Each Word (word position stays same)

```python
# Using built-in methods
def reverse_words_built_in(s):
    return " ".join(word[::-1] for word in s.split(" "))

# Without built-in methods
def reverse_words_manual(s):
    result = ""
    word = ""
    for ch in s:
        if ch == " ":
            # reverse current word manually
            rev = ""
            for c in word:
                rev = c + rev
            result += rev + " "
            word = ""
        else:
            word += ch
    # handle last word
    rev = ""
    for c in word:
        rev = c + rev
    result += rev
    return result

if __name__ == "__main__":
    text = "Hello World Python"
    print("Built-in:", reverse_words_built_in(text))
    print("Manual  :", reverse_words_manual(text))
```

## 3. Max Character Count (most frequent character)

```python
from collections import Counter

# Using built-in (Counter)
def max_char_built_in(s):
    freq = Counter(s)
    return max(freq, key=freq.get)

# Without built-in
def max_char_manual(s):
    counts = {}
    for ch in s:
        if ch in counts:
            counts[ch] += 1
        else:
            counts[ch] = 1

    max_char = s[0]
    max_count = 0
    for ch in counts:
        if counts[ch] > max_count:
            max_count = counts[ch]
            max_char = ch
    return max_char

if __name__ == "__main__":
    text = "programming"
    print("Built-in:", max_char_built_in(text))
    print("Manual  :", max_char_manual(text))
```

## 4. Maximum of 3 Numbers

```python
# Using built-in max()
def max_built_in(a, b, c):
    return max(a, b, c)

# Without built-in
def max_manual(a, b, c):
    result = a
    if b > result:
        result = b
    if c > result:
        result = c
    return result

if __name__ == "__main__":
    a, b, c = 10, 25, 17
    print("Built-in:", max_built_in(a, b, c))
    print("Manual  :", max_manual(a, b, c))
```

## 5. Sort Array (Ascending & Descending)

```python
# Using built-in sorted()
def sort_built_in(arr):
    ascending = sorted(arr)
    descending = sorted(arr, reverse=True)
    return ascending, descending

# Without built-in (manual bubble sort)
def sort_manual(arr):
    asc = arr.copy()
    n = len(asc)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if asc[j] > asc[j + 1]:
                asc[j], asc[j + 1] = asc[j + 1], asc[j]

    desc = asc.copy()
    left, right = 0, len(desc) - 1
    while left < right:
        desc[left], desc[right] = desc[right], desc[left]
        left += 1
        right -= 1

    return asc, desc

if __name__ == "__main__":
    numbers = [5, 2, 9, 1, 7]

    asc_b, desc_b = sort_built_in(numbers)
    print("Built-in Ascending :", asc_b)
    print("Built-in Descending:", desc_b)

    asc_m, desc_m = sort_manual(numbers)
    print("Manual Ascending :", asc_m)
    print("Manual Descending:", desc_m)
```

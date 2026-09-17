# Python Interview Programs — Built-in vs Manual

## Table of Contents

1. [Reverse a String](#1-reverse-a-string)
2. [Reverse Each Word (Keep Word Position)](#2-reverse-each-word-keep-word-position)
3. [Max Character Count (Most Frequent Char)](#3-max-character-count-most-frequent-char)
4. [Maximum of 3 Numbers](#4-maximum-of-3-numbers)
5. [Sort List — Ascending & Descending](#5-sort-list--ascending--descending)
6. [Palindrome Check](#6-palindrome-check)
7. [Anagram Check](#7-anagram-check)
8. [Count Vowels, Consonants, Special Characters](#8-count-vowels-consonants-special-characters)
9. [Check if String Contains Only Digits/Alphabets](#9-check-if-string-contains-only-digitsalphabets)
10. [Remove Whitespace/Duplicate Spaces from a String](#10-remove-whitespaceduplicate-spaces-from-a-string)
11. [Find Duplicates in a List](#11-find-duplicates-in-a-list)
12. [Compare Two Lists and Find Differences](#12-compare-two-lists-and-find-differences)
13. [Find Missing Elements Between Two Lists](#13-find-missing-elements-between-two-lists)
14. [Second Largest/Smallest Element](#14-second-largestsmallest-element)
15. [Check if a List is Sorted](#15-check-if-a-list-is-sorted)
16. [Remove Duplicates from a List](#16-remove-duplicates-from-a-list)
17. [Prime Number Check](#17-prime-number-check)
18. [Fibonacci Series](#18-fibonacci-series)
19. [Factorial](#19-factorial)
20. [Armstrong Number](#20-armstrong-number)
21. [FizzBuzz](#21-fizzbuzz)

---

## 1. Reverse a String

```python
def reverse_builtin(s: str) -> str:
    """Using built-in slicing."""
    return s[::-1]


def reverse_manual(s: str) -> str:
    """Without built-in reversal — manual two-pointer swap on a list."""
    chars = list(s)
    left, right = 0, len(chars) - 1
    while left < right:
        chars[left], chars[right] = chars[right], chars[left]
        left += 1
        right -= 1
    return "".join(chars)


if __name__ == "__main__":
    text = "Hello World"
    print("Built-in:", reverse_builtin(text))
    print("Manual  :", reverse_manual(text))
```

[Back to top](#table-of-contents)

---

## 2. Reverse Each Word (Keep Word Position)

```python
def reverse_words_builtin(s: str) -> str:
    """Using built-in split/join and slicing."""
    return " ".join(word[::-1] for word in s.split(" "))


def reverse_words_manual(s: str) -> str:
    """Without built-in helpers — manual char-by-char scan."""
    result = []
    word = []
    for i in range(len(s) + 1):
        if i == len(s) or s[i] == " ":
            for j in range(len(word) - 1, -1, -1):
                result.append(word[j])
            if i != len(s):
                result.append(" ")
            word = []
        else:
            word.append(s[i])
    return "".join(result)


if __name__ == "__main__":
    text = "Hello World Python"
    print("Built-in:", reverse_words_builtin(text))
    print("Manual  :", reverse_words_manual(text))
```

[Back to top](#table-of-contents)

---

## 3. Max Character Count (Most Frequent Char)

```python
from collections import Counter


def max_char_builtin(s: str) -> str:
    """Using built-in Counter + max()."""
    freq = Counter(s)
    return max(freq, key=freq.get)


def max_char_manual(s: str) -> str:
    """Without built-in counters — manual frequency array (ASCII assumed)."""
    counts = [0] * 256
    for ch in s:
        counts[ord(ch)] += 1
    max_char = s[0]
    max_count = 0
    for ch in s:
        c = counts[ord(ch)]
        if c > max_count:
            max_count = c
            max_char = ch
    return max_char


if __name__ == "__main__":
    text = "programming"
    print("Built-in:", max_char_builtin(text))
    print("Manual  :", max_char_manual(text))
```

[Back to top](#table-of-contents)

---

## 4. Maximum of 3 Numbers

```python
def max_of_three_builtin(a: int, b: int, c: int) -> int:
    """Using built-in max()."""
    return max(a, b, c)


def max_of_three_manual(a: int, b: int, c: int) -> int:
    """Without built-in max()."""
    result = a
    if b > result:
        result = b
    if c > result:
        result = c
    return result


if __name__ == "__main__":
    a, b, c = 10, 25, 17
    print("Built-in:", max_of_three_builtin(a, b, c))
    print("Manual  :", max_of_three_manual(a, b, c))
```

[Back to top](#table-of-contents)

---

## 5. Sort List — Ascending & Descending

```python
def sort_builtin(arr):
    """Using built-in sorted()."""
    asc = sorted(arr)
    desc = sorted(arr, reverse=True)
    print("Built-in Ascending :", asc)
    print("Built-in Descending:", desc)


def sort_manual(arr):
    """Without built-in sorting — manual bubble sort."""
    asc = arr.copy()
    n = len(asc)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if asc[j] > asc[j + 1]:
                asc[j], asc[j + 1] = asc[j + 1], asc[j]

    desc = asc.copy()
    for i in range(len(desc) // 2):
        desc[i], desc[-1 - i] = desc[-1 - i], desc[i]

    print("Manual Ascending :", asc)
    print("Manual Descending:", desc)


if __name__ == "__main__":
    numbers = [5, 2, 9, 1, 7]
    sort_builtin(numbers)
    sort_manual(numbers)
```

[Back to top](#table-of-contents)

---

## 6. Palindrome Check

```python
def is_palindrome_builtin(s: str) -> bool:
    """Using built-in slicing."""
    return s == s[::-1]


def is_palindrome_manual(s: str) -> bool:
    """Without built-in reversal — manual two-pointer compare."""
    left, right = 0, len(s) - 1
    while left < right:
        if s[left] != s[right]:
            return False
        left += 1
        right -= 1
    return True


if __name__ == "__main__":
    text = "madam"
    print("Built-in:", is_palindrome_builtin(text))
    print("Manual  :", is_palindrome_manual(text))
```

[Back to top](#table-of-contents)

---

## 7. Anagram Check

```python
def is_anagram_builtin(s1: str, s2: str) -> bool:
    """Using built-in sorted()."""
    if len(s1) != len(s2):
        return False
    return sorted(s1) == sorted(s2)


def is_anagram_manual(s1: str, s2: str) -> bool:
    """Without built-in sorting — manual frequency count."""
    if len(s1) != len(s2):
        return False
    counts = [0] * 256
    for c1, c2 in zip(s1, s2):
        counts[ord(c1)] += 1
        counts[ord(c2)] -= 1
    return all(c == 0 for c in counts)


if __name__ == "__main__":
    s1, s2 = "listen", "silent"
    print("Built-in:", is_anagram_builtin(s1, s2))
    print("Manual  :", is_anagram_manual(s1, s2))
```

[Back to top](#table-of-contents)

---

## 8. Count Vowels, Consonants, Special Characters

```python
def count_char_types_builtin(s: str):
    """Using built-in str.isalpha()/str.isspace()."""
    vowels = consonants = special = 0
    vowel_set = "aeiouAEIOU"
    for ch in s:
        if ch.isalpha():
            if ch in vowel_set:
                vowels += 1
            else:
                consonants += 1
        elif not ch.isspace():
            special += 1
    print(f"Built-in -> Vowels: {vowels}, Consonants: {consonants}, Special: {special}")


def count_char_types_manual(s: str):
    """Without built-in character classification helpers."""
    vowels = consonants = special = 0
    for ch in s:
        code = ord(ch)
        is_letter = (97 <= code <= 122) or (65 <= code <= 90)
        if is_letter:
            lower = chr(code + 32) if 65 <= code <= 90 else ch
            if lower in ("a", "e", "i", "o", "u"):
                vowels += 1
            else:
                consonants += 1
        elif ch != " ":
            special += 1
    print(f"Manual   -> Vowels: {vowels}, Consonants: {consonants}, Special: {special}")


if __name__ == "__main__":
    text = "Hello World! 123"
    count_char_types_builtin(text)
    count_char_types_manual(text)
```

[Back to top](#table-of-contents)

---

## 9. Check if String Contains Only Digits/Alphabets

```python
def is_digits_only_builtin(s: str) -> bool:
    """Using built-in str.isdigit()."""
    return s.isdigit()


def is_alphabets_only_builtin(s: str) -> bool:
    """Using built-in str.isalpha()."""
    return s.isalpha()


def is_digits_only_manual(s: str) -> bool:
    """Without built-in classification — manual char code check."""
    for ch in s:
        if ch < "0" or ch > "9":
            return False
    return True


def is_alphabets_only_manual(s: str) -> bool:
    """Without built-in classification — manual char code check."""
    for ch in s:
        if not (("a" <= ch <= "z") or ("A" <= ch <= "Z")):
            return False
    return True


if __name__ == "__main__":
    digits = "12345"
    alpha = "HelloWorld"

    print("Built-in digitsOnly:", is_digits_only_builtin(digits))
    print("Manual   digitsOnly:", is_digits_only_manual(digits))

    print("Built-in alphaOnly :", is_alphabets_only_builtin(alpha))
    print("Manual   alphaOnly :", is_alphabets_only_manual(alpha))
```

[Back to top](#table-of-contents)

---

## 10. Remove Whitespace/Duplicate Spaces from a String

```python
import re


def remove_all_whitespace_builtin(s: str) -> str:
    """Using built-in re module."""
    return re.sub(r"\s+", "", s)


def remove_duplicate_spaces_builtin(s: str) -> str:
    """Using built-in re module + str.strip()."""
    return re.sub(r"\s+", " ", s.strip())


def remove_all_whitespace_manual(s: str) -> str:
    """Without built-in regex — manual char filter."""
    result = []
    for ch in s:
        if ch not in (" ", "\t", "\n"):
            result.append(ch)
    return "".join(result)


def remove_duplicate_spaces_manual(s: str) -> str:
    """Without built-in regex — manual single-pass scan."""
    result = []
    last_was_space = False
    for ch in s:
        if ch == " ":
            if not last_was_space and result:
                result.append(ch)
            last_was_space = True
        else:
            result.append(ch)
            last_was_space = False
    if result and result[-1] == " ":
        result.pop()
    return "".join(result)


if __name__ == "__main__":
    text = "  Hello    World   Python  "

    print(f"Built-in removeAll  : [{remove_all_whitespace_builtin(text)}]")
    print(f"Manual   removeAll  : [{remove_all_whitespace_manual(text)}]")

    print(f"Built-in dedupSpaces: [{remove_duplicate_spaces_builtin(text)}]")
    print(f"Manual   dedupSpaces: [{remove_duplicate_spaces_manual(text)}]")
```

[Back to top](#table-of-contents)

---

## 11. Find Duplicates in a List

```python
def find_duplicates_builtin(arr):
    """Using built-in set()."""
    seen = set()
    duplicates = set()
    for num in arr:
        if num in seen:
            duplicates.add(num)
        else:
            seen.add(num)
    return duplicates


def find_duplicates_manual(arr):
    """Without built-in sets — manual nested-loop scan."""
    result = []
    n = len(arr)
    for i in range(n):
        is_dup = False
        for j in range(i):
            if arr[i] == arr[j]:
                is_dup = True
                break
        if is_dup:
            already_added = False
            for val in result:
                if val == arr[i]:
                    already_added = True
                    break
            if not already_added:
                result.append(arr[i])
    return result


if __name__ == "__main__":
    numbers = [1, 2, 3, 2, 4, 5, 1]
    print("Built-in:", find_duplicates_builtin(numbers))
    print("Manual  :", find_duplicates_manual(numbers))
```

[Back to top](#table-of-contents)

---

## 12. Compare Two Lists and Find Differences

```python
def compare_lists_builtin(arr1, arr2):
    """Using built-in set operations."""
    set1, set2 = set(arr1), set(arr2)
    only_in_arr1 = set1 - set2
    only_in_arr2 = set2 - set1
    print(f"Built-in -> Only in arr1: {only_in_arr1}, Only in arr2: {only_in_arr2}")


def compare_lists_manual(arr1, arr2):
    """Without built-in sets — manual nested-loop membership check."""
    only_in_arr1 = []
    for x in arr1:
        found = False
        for y in arr2:
            if x == y:
                found = True
                break
        if not found:
            only_in_arr1.append(x)

    only_in_arr2 = []
    for x in arr2:
        found = False
        for y in arr1:
            if x == y:
                found = True
                break
        if not found:
            only_in_arr2.append(x)

    print(f"Manual   -> Only in arr1: {only_in_arr1}, Only in arr2: {only_in_arr2}")


if __name__ == "__main__":
    arr1 = [1, 2, 3, 4, 5]
    arr2 = [3, 4, 5, 6, 7]
    compare_lists_builtin(arr1, arr2)
    compare_lists_manual(arr1, arr2)
```

[Back to top](#table-of-contents)

---

## 13. Find Missing Elements Between Two Lists

```python
def find_missing_builtin(full_set, subset):
    """Using built-in set()."""
    subset_lookup = set(subset)
    return [n for n in full_set if n not in subset_lookup]


def find_missing_manual(full_set, subset):
    """Without built-in sets — manual nested-loop membership check."""
    result = []
    for x in full_set:
        found = False
        for y in subset:
            if x == y:
                found = True
                break
        if not found:
            result.append(x)
    return result


if __name__ == "__main__":
    full_set = [1, 2, 3, 4, 5, 6, 7]
    subset = [1, 3, 5, 7]
    print("Built-in:", find_missing_builtin(full_set, subset))
    print("Manual  :", find_missing_manual(full_set, subset))
```

[Back to top](#table-of-contents)

---

## 14. Second Largest/Smallest Element

```python
def second_largest_smallest_builtin(arr):
    """Using built-in sorted()."""
    sorted_arr = sorted(arr)
    second_smallest = sorted_arr[1]
    second_largest = sorted_arr[-2]
    print(f"Built-in -> Second Largest: {second_largest}, Second Smallest: {second_smallest}")


def second_largest_smallest_manual(arr):
    """Without built-in sorting — single pass."""
    largest = second_largest = float("-inf")
    smallest = second_smallest = float("inf")

    for num in arr:
        # largest / second largest
        if num > largest:
            second_largest = largest
            largest = num
        elif num > second_largest and num != largest:
            second_largest = num

        # smallest / second smallest
        if num < smallest:
            second_smallest = smallest
            smallest = num
        elif num < second_smallest and num != smallest:
            second_smallest = num

    print(f"Manual   -> Second Largest: {second_largest}, Second Smallest: {second_smallest}")


if __name__ == "__main__":
    numbers = [5, 2, 9, 1, 7, 9, 2]
    second_largest_smallest_builtin(numbers)
    second_largest_smallest_manual(numbers)
```

[Back to top](#table-of-contents)

---

## 15. Check if a List is Sorted

```python
def is_sorted_builtin(arr) -> bool:
    """Using built-in sorted() comparison."""
    return arr == sorted(arr)


def is_sorted_manual(arr) -> bool:
    """Without built-in sorting — manual adjacent-pair scan."""
    for i in range(len(arr) - 1):
        if arr[i] > arr[i + 1]:
            return False
    return True


if __name__ == "__main__":
    sorted_arr = [1, 2, 3, 4, 5]
    unsorted_arr = [1, 3, 2, 4, 5]

    print("Built-in sortedArr  :", is_sorted_builtin(sorted_arr))
    print("Manual   sortedArr  :", is_sorted_manual(sorted_arr))

    print("Built-in unsortedArr:", is_sorted_builtin(unsorted_arr))
    print("Manual   unsortedArr:", is_sorted_manual(unsorted_arr))
```

[Back to top](#table-of-contents)

---

## 16. Remove Duplicates from a List

```python
def remove_duplicates_builtin(arr):
    """Using built-in dict.fromkeys() (preserves insertion order)."""
    return list(dict.fromkeys(arr))


def remove_duplicates_manual(arr):
    """Without built-in ordered-set helpers — manual scan."""
    result = []
    for num in arr:
        found = False
        for val in result:
            if val == num:
                found = True
                break
        if not found:
            result.append(num)
    return result


if __name__ == "__main__":
    numbers = [1, 2, 2, 3, 4, 4, 5, 1]
    print("Built-in:", remove_duplicates_builtin(numbers))
    print("Manual  :", remove_duplicates_manual(numbers))
```

[Back to top](#table-of-contents)

---

## 17. Prime Number Check

```python
import math


def is_prime_builtin(n: int) -> bool:
    """Using built-in math.sqrt()."""
    if n < 2:
        return False
    for i in range(2, int(math.sqrt(n)) + 1):
        if n % i == 0:
            return False
    return True


def is_prime_manual(n: int) -> bool:
    """Without built-in sqrt — manual i*i boundary check."""
    if n < 2:
        return False
    i = 2
    while i * i <= n:
        if n % i == 0:
            return False
        i += 1
    return True


if __name__ == "__main__":
    num = 29
    print("Built-in:", is_prime_builtin(num))
    print("Manual  :", is_prime_manual(num))
```

[Back to top](#table-of-contents)

---

## 18. Fibonacci Series

```python
def fibonacci_builtin(n: int):
    """Using a built-in generator expression."""
    def gen():
        x, y = 0, 1
        for _ in range(n):
            yield x
            x, y = y, x + y
    result = list(gen())
    print("Built-in:", " ".join(str(x) for x in result))


def fibonacci_manual(n: int):
    """Without built-ins — plain loop."""
    result = []
    a, b = 0, 1
    for _ in range(n):
        result.append(a)
        a, b = b, a + b
    print("Manual  :", " ".join(str(x) for x in result))


if __name__ == "__main__":
    n = 10
    fibonacci_builtin(n)
    fibonacci_manual(n)
```

[Back to top](#table-of-contents)

---

## 19. Factorial

```python
import math


def factorial_builtin(n: int) -> int:
    """Using built-in math.factorial() (arbitrary precision, like BigInteger)."""
    return math.factorial(n)


def factorial_manual(n: int) -> int:
    """Without built-ins — plain recursion."""
    if n <= 1:
        return 1
    return n * factorial_manual(n - 1)


if __name__ == "__main__":
    n = 10
    print("Built-in:", factorial_builtin(n))
    print("Manual  :", factorial_manual(n))
```

[Back to top](#table-of-contents)

---

## 20. Armstrong Number

```python
def is_armstrong_builtin(num: int) -> bool:
    """Using built-in str()/sum()."""
    num_str = str(num)
    digits = len(num_str)
    total = sum(int(ch) ** digits for ch in num_str)
    return total == num


def is_armstrong_manual(num: int) -> bool:
    """Without built-ins — manual digit extraction and power calc."""
    original = num
    digits = 0
    temp = num
    while temp != 0:
        digits += 1
        temp //= 10

    total = 0
    temp = num
    while temp != 0:
        digit = temp % 10
        power = 1
        for _ in range(digits):
            power *= digit
        total += power
        temp //= 10

    return total == original


if __name__ == "__main__":
    num = 153
    print("Built-in:", is_armstrong_builtin(num))
    print("Manual  :", is_armstrong_manual(num))
```

[Back to top](#table-of-contents)

---

## 21. FizzBuzz

```python
def fizzbuzz_builtin(n: int):
    """Using a built-in list comprehension."""
    print("Built-in:")
    lines = [
        "FizzBuzz" if i % 15 == 0 else
        "Fizz" if i % 3 == 0 else
        "Buzz" if i % 5 == 0 else
        str(i)
        for i in range(1, n + 1)
    ]
    for line in lines:
        print(line)


def fizzbuzz_manual(n: int):
    """Without built-ins — plain loop."""
    print("Manual:")
    for i in range(1, n + 1):
        if i % 15 == 0:
            print("FizzBuzz")
        elif i % 3 == 0:
            print("Fizz")
        elif i % 5 == 0:
            print("Buzz")
        else:
            print(i)


if __name__ == "__main__":
    n = 15
    fizzbuzz_builtin(n)
    fizzbuzz_manual(n)
```

[Back to top](#table-of-contents)

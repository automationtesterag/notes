# JavaScript Interview Programs — Built-in vs Manual

## Table of Contents

1. [Reverse a String](#1-reverse-a-string)
2. [Reverse Each Word (Keep Word Position)](#2-reverse-each-word-keep-word-position)
3. [Max Character Count (Most Frequent Char)](#3-max-character-count-most-frequent-char)
4. [Maximum of 3 Numbers](#4-maximum-of-3-numbers)
5. [Sort Array — Ascending & Descending](#5-sort-array--ascending--descending)
6. [Palindrome Check](#6-palindrome-check)
7. [Anagram Check](#7-anagram-check)
8. [Count Vowels, Consonants, Special Characters](#8-count-vowels-consonants-special-characters)
9. [Check if String Contains Only Digits/Alphabets](#9-check-if-string-contains-only-digitsalphabets)
10. [Remove Whitespace/Duplicate Spaces from a String](#10-remove-whitespaceduplicate-spaces-from-a-string)
11. [Find Duplicates in an Array](#11-find-duplicates-in-an-array)
12. [Compare Two Arrays and Find Differences](#12-compare-two-arrays-and-find-differences)
13. [Find Missing Elements Between Two Arrays](#13-find-missing-elements-between-two-arrays)
14. [Second Largest/Smallest Element](#14-second-largestsmallest-element)
15. [Check if an Array is Sorted](#15-check-if-an-array-is-sorted)
16. [Remove Duplicates from an Array](#16-remove-duplicates-from-an-array)
17. [Prime Number Check](#17-prime-number-check)
18. [Fibonacci Series](#18-fibonacci-series)
19. [Factorial](#19-factorial)
20. [Armstrong Number](#20-armstrong-number)
21. [FizzBuzz](#21-fizzbuzz)

---

## 1. Reverse a String

```javascript
// Using built-in methods
function reverseBuiltIn(str) {
  return str.split("").reverse().join("");
}

// Without built-in reversal — manual two-pointer swap
function reverseManual(str) {
  const chars = str.split("");
  let left = 0, right = chars.length - 1;
  while (left < right) {
    const temp = chars[left];
    chars[left] = chars[right];
    chars[right] = temp;
    left++;
    right--;
  }
  return chars.join("");
}

const input1 = "Hello World";
console.log("Built-in:", reverseBuiltIn(input1));
console.log("Manual  :", reverseManual(input1));
```

[Back to top](#table-of-contents)

---

## 2. Reverse Each Word (Keep Word Position)

```javascript
// Using built-in methods
function reverseWordsBuiltIn(str) {
  return str
    .split(" ")
    .map((word) => word.split("").reverse().join(""))
    .join(" ");
}

// Without built-in helpers — manual char-by-char scan
function reverseWordsManual(str) {
  let result = "";
  let word = "";

  for (let i = 0; i <= str.length; i++) {
    if (i === str.length || str[i] === " ") {
      for (let j = word.length - 1; j >= 0; j--) {
        result += word[j];
      }
      if (i !== str.length) result += " ";
      word = "";
    } else {
      word += str[i];
    }
  }
  return result;
}

const input2 = "Hello World JavaScript";
console.log("Built-in:", reverseWordsBuiltIn(input2));
console.log("Manual  :", reverseWordsManual(input2));
```

[Back to top](#table-of-contents)

---

## 3. Max Character Count (Most Frequent Char)

```javascript
// Using built-in Map
function maxCharBuiltIn(str) {
  const freq = new Map();
  for (const c of str) {
    freq.set(c, (freq.get(c) || 0) + 1);
  }
  let maxChar = str[0];
  let maxCount = 0;
  for (const [char, count] of freq) {
    if (count > maxCount) {
      maxCount = count;
      maxChar = char;
    }
  }
  return maxChar;
}

// Without built-in counters — manual frequency array (ASCII assumed)
function maxCharManual(str) {
  const counts = new Array(256).fill(0);
  for (let i = 0; i < str.length; i++) {
    counts[str.charCodeAt(i)]++;
  }
  let maxChar = str[0];
  let maxCount = 0;
  for (let i = 0; i < str.length; i++) {
    const code = str.charCodeAt(i);
    if (counts[code] > maxCount) {
      maxCount = counts[code];
      maxChar = str[i];
    }
  }
  return maxChar;
}

const input3 = "programming";
console.log("Built-in:", maxCharBuiltIn(input3));
console.log("Manual  :", maxCharManual(input3));
```

[Back to top](#table-of-contents)

---

## 4. Maximum of 3 Numbers

```javascript
// Using built-in Math.max
function maxBuiltIn(a, b, c) {
  return Math.max(a, b, c);
}

// Without built-in
function maxManual(a, b, c) {
  let max = a;
  if (b > max) max = b;
  if (c > max) max = c;
  return max;
}

const [a4, b4, c4] = [10, 25, 17];
console.log("Built-in:", maxBuiltIn(a4, b4, c4));
console.log("Manual  :", maxManual(a4, b4, c4));
```

[Back to top](#table-of-contents)

---

## 5. Sort Array — Ascending & Descending

```javascript
// Using built-in Array.prototype.sort
function sortBuiltIn(arr) {
  const asc = [...arr].sort((x, y) => x - y);
  const desc = [...arr].sort((x, y) => y - x);
  console.log("Built-in Ascending :", asc);
  console.log("Built-in Descending:", desc);
}

// Without built-in sorting — manual bubble sort
function sortManual(arr) {
  const asc = [...arr];
  const n = asc.length;
  for (let i = 0; i < n - 1; i++) {
    for (let j = 0; j < n - i - 1; j++) {
      if (asc[j] > asc[j + 1]) {
        const temp = asc[j];
        asc[j] = asc[j + 1];
        asc[j + 1] = temp;
      }
    }
  }

  const desc = [...asc];
  for (let i = 0; i < Math.floor(desc.length / 2); i++) {
    const temp = desc[i];
    desc[i] = desc[desc.length - 1 - i];
    desc[desc.length - 1 - i] = temp;
  }

  console.log("Manual Ascending :", asc);
  console.log("Manual Descending:", desc);
}

const numbers5 = [5, 2, 9, 1, 7];
sortBuiltIn(numbers5);
sortManual(numbers5);
```

[Back to top](#table-of-contents)

---

## 6. Palindrome Check

```javascript
// Using built-in methods
function isPalindromeBuiltIn(str) {
  const reversed = str.split("").reverse().join("");
  return str === reversed;
}

// Without built-in reversal — manual two-pointer compare
function isPalindromeManual(str) {
  let left = 0, right = str.length - 1;
  while (left < right) {
    if (str[left] !== str[right]) {
      return false;
    }
    left++;
    right--;
  }
  return true;
}

const input6 = "madam";
console.log("Built-in:", isPalindromeBuiltIn(input6));
console.log("Manual  :", isPalindromeManual(input6));
```

[Back to top](#table-of-contents)

---

## 7. Anagram Check

```javascript
// Using built-in sort
function isAnagramBuiltIn(s1, s2) {
  if (s1.length !== s2.length) return false;
  const a1 = s1.split("").sort().join("");
  const a2 = s2.split("").sort().join("");
  return a1 === a2;
}

// Without built-in sorting — manual frequency count
function isAnagramManual(s1, s2) {
  if (s1.length !== s2.length) return false;
  const counts = new Array(256).fill(0);
  for (let i = 0; i < s1.length; i++) {
    counts[s1.charCodeAt(i)]++;
    counts[s2.charCodeAt(i)]--;
  }
  return counts.every((c) => c === 0);
}

const [s1_7, s2_7] = ["listen", "silent"];
console.log("Built-in:", isAnagramBuiltIn(s1_7, s2_7));
console.log("Manual  :", isAnagramManual(s1_7, s2_7));
```

[Back to top](#table-of-contents)

---

## 8. Count Vowels, Consonants, Special Characters

```javascript
// Using built-in regex test
function countCharTypesBuiltIn(str) {
  let vowels = 0, consonants = 0, special = 0;
  const vowelSet = "aeiouAEIOU";
  for (const ch of str) {
    if (/[a-zA-Z]/.test(ch)) {
      if (vowelSet.includes(ch)) vowels++;
      else consonants++;
    } else if (!/\s/.test(ch)) {
      special++;
    }
  }
  console.log(`Built-in -> Vowels: ${vowels}, Consonants: ${consonants}, Special: ${special}`);
}

// Without built-in regex — manual char code check
function countCharTypesManual(str) {
  let vowels = 0, consonants = 0, special = 0;
  for (let i = 0; i < str.length; i++) {
    const c = str[i];
    const code = str.charCodeAt(i);
    const isLetter = (code >= 97 && code <= 122) || (code >= 65 && code <= 90);
    if (isLetter) {
      const lower = code >= 65 && code <= 90 ? String.fromCharCode(code + 32) : c;
      if ("aeiou".includes(lower)) {
        vowels++;
      } else {
        consonants++;
      }
    } else if (c !== " ") {
      special++;
    }
  }
  console.log(`Manual   -> Vowels: ${vowels}, Consonants: ${consonants}, Special: ${special}`);
}

const input8 = "Hello World! 123";
countCharTypesBuiltIn(input8);
countCharTypesManual(input8);
```

[Back to top](#table-of-contents)

---

## 9. Check if String Contains Only Digits/Alphabets

```javascript
// Using built-in regex
function isDigitsOnlyBuiltIn(str) {
  return /^[0-9]+$/.test(str);
}

function isAlphabetsOnlyBuiltIn(str) {
  return /^[a-zA-Z]+$/.test(str);
}

// Without built-in regex — manual char code check
function isDigitsOnlyManual(str) {
  for (let i = 0; i < str.length; i++) {
    const c = str[i];
    if (c < "0" || c > "9") return false;
  }
  return true;
}

function isAlphabetsOnlyManual(str) {
  for (let i = 0; i < str.length; i++) {
    const c = str[i];
    if (!((c >= "a" && c <= "z") || (c >= "A" && c <= "Z"))) return false;
  }
  return true;
}

const digits9 = "12345";
const alpha9 = "HelloWorld";

console.log("Built-in digitsOnly:", isDigitsOnlyBuiltIn(digits9));
console.log("Manual   digitsOnly:", isDigitsOnlyManual(digits9));

console.log("Built-in alphaOnly :", isAlphabetsOnlyBuiltIn(alpha9));
console.log("Manual   alphaOnly :", isAlphabetsOnlyManual(alpha9));
```

[Back to top](#table-of-contents)

---

## 10. Remove Whitespace/Duplicate Spaces from a String

```javascript
// Using built-in regex
function removeAllWhitespaceBuiltIn(str) {
  return str.replace(/\s+/g, "");
}

function removeDuplicateSpacesBuiltIn(str) {
  return str.trim().replace(/\s+/g, " ");
}

// Without built-in regex — manual char filter
function removeAllWhitespaceManual(str) {
  let result = "";
  for (let i = 0; i < str.length; i++) {
    const c = str[i];
    if (c !== " " && c !== "\t" && c !== "\n") {
      result += c;
    }
  }
  return result;
}

function removeDuplicateSpacesManual(str) {
  let result = "";
  let lastWasSpace = false;
  for (let i = 0; i < str.length; i++) {
    const c = str[i];
    if (c === " ") {
      if (!lastWasSpace && result.length > 0) {
        result += c;
      }
      lastWasSpace = true;
    } else {
      result += c;
      lastWasSpace = false;
    }
  }
  if (result.length > 0 && result[result.length - 1] === " ") {
    result = result.slice(0, -1);
  }
  return result;
}

const input10 = "  Hello    World   JavaScript  ";

console.log(`Built-in removeAll  : [${removeAllWhitespaceBuiltIn(input10)}]`);
console.log(`Manual   removeAll  : [${removeAllWhitespaceManual(input10)}]`);

console.log(`Built-in dedupSpaces: [${removeDuplicateSpacesBuiltIn(input10)}]`);
console.log(`Manual   dedupSpaces: [${removeDuplicateSpacesManual(input10)}]`);
```

[Back to top](#table-of-contents)

---

## 11. Find Duplicates in an Array

```javascript
// Using built-in Set
function findDuplicatesBuiltIn(arr) {
  const seen = new Set();
  const duplicates = new Set();
  for (const num of arr) {
    if (seen.has(num)) {
      duplicates.add(num);
    } else {
      seen.add(num);
    }
  }
  return duplicates;
}

// Without built-in sets — manual nested-loop scan
function findDuplicatesManual(arr) {
  const result = [];
  const n = arr.length;
  for (let i = 0; i < n; i++) {
    let isDup = false;
    for (let j = 0; j < i; j++) {
      if (arr[i] === arr[j]) {
        isDup = true;
        break;
      }
    }
    if (isDup) {
      let alreadyAdded = false;
      for (let k = 0; k < result.length; k++) {
        if (result[k] === arr[i]) {
          alreadyAdded = true;
          break;
        }
      }
      if (!alreadyAdded) {
        result.push(arr[i]);
      }
    }
  }
  return result;
}

const numbers11 = [1, 2, 3, 2, 4, 5, 1];
console.log("Built-in:", findDuplicatesBuiltIn(numbers11));
console.log("Manual  :", findDuplicatesManual(numbers11));
```

[Back to top](#table-of-contents)

---

## 12. Compare Two Arrays and Find Differences

```javascript
// Using built-in Set
function compareArraysBuiltIn(arr1, arr2) {
  const set1 = new Set(arr1);
  const set2 = new Set(arr2);

  const onlyInArr1 = [...set1].filter((x) => !set2.has(x));
  const onlyInArr2 = [...set2].filter((x) => !set1.has(x));

  console.log(`Built-in -> Only in arr1: [${onlyInArr1}], Only in arr2: [${onlyInArr2}]`);
}

// Without built-in sets — manual nested-loop membership check
function compareArraysManual(arr1, arr2) {
  const onlyInArr1 = [];
  for (let i = 0; i < arr1.length; i++) {
    let found = false;
    for (let j = 0; j < arr2.length; j++) {
      if (arr1[i] === arr2[j]) {
        found = true;
        break;
      }
    }
    if (!found) onlyInArr1.push(arr1[i]);
  }

  const onlyInArr2 = [];
  for (let i = 0; i < arr2.length; i++) {
    let found = false;
    for (let j = 0; j < arr1.length; j++) {
      if (arr2[i] === arr1[j]) {
        found = true;
        break;
      }
    }
    if (!found) onlyInArr2.push(arr2[i]);
  }

  console.log(`Manual   -> Only in arr1: [${onlyInArr1}], Only in arr2: [${onlyInArr2}]`);
}

const arr1_12 = [1, 2, 3, 4, 5];
const arr2_12 = [3, 4, 5, 6, 7];

compareArraysBuiltIn(arr1_12, arr2_12);
compareArraysManual(arr1_12, arr2_12);
```

[Back to top](#table-of-contents)

---

## 13. Find Missing Elements Between Two Arrays

```javascript
// Using built-in Set — elements in full set missing from subset
function findMissingBuiltIn(fullSet, subset) {
  const subsetLookup = new Set(subset);
  return fullSet.filter((n) => !subsetLookup.has(n));
}

// Without built-in sets — manual nested-loop membership check
function findMissingManual(fullSet, subset) {
  const result = [];
  for (let i = 0; i < fullSet.length; i++) {
    let found = false;
    for (let j = 0; j < subset.length; j++) {
      if (fullSet[i] === subset[j]) {
        found = true;
        break;
      }
    }
    if (!found) result.push(fullSet[i]);
  }
  return result;
}

const fullSet13 = [1, 2, 3, 4, 5, 6, 7];
const subset13 = [1, 3, 5, 7];

console.log("Built-in:", findMissingBuiltIn(fullSet13, subset13));
console.log("Manual  :", findMissingManual(fullSet13, subset13));
```

[Back to top](#table-of-contents)

---

## 14. Second Largest/Smallest Element

```javascript
// Using built-in sort
function secondLargestSmallestBuiltIn(arr) {
  const sorted = [...arr].sort((a, b) => a - b);
  const secondSmallest = sorted[1];
  const secondLargest = sorted[sorted.length - 2];
  console.log(`Built-in -> Second Largest: ${secondLargest}, Second Smallest: ${secondSmallest}`);
}

// Without built-in sorting — single pass
function secondLargestSmallestManual(arr) {
  let largest = -Infinity, secondLargest = -Infinity;
  let smallest = Infinity, secondSmallest = Infinity;

  for (const num of arr) {
    // largest / second largest
    if (num > largest) {
      secondLargest = largest;
      largest = num;
    } else if (num > secondLargest && num !== largest) {
      secondLargest = num;
    }

    // smallest / second smallest
    if (num < smallest) {
      secondSmallest = smallest;
      smallest = num;
    } else if (num < secondSmallest && num !== smallest) {
      secondSmallest = num;
    }
  }

  console.log(`Manual   -> Second Largest: ${secondLargest}, Second Smallest: ${secondSmallest}`);
}

const numbers14 = [5, 2, 9, 1, 7, 9, 2];
secondLargestSmallestBuiltIn(numbers14);
secondLargestSmallestManual(numbers14);
```

[Back to top](#table-of-contents)

---

## 15. Check if an Array is Sorted

```javascript
// Using built-in sort comparison
function isSortedBuiltIn(arr) {
  const sortedCopy = [...arr].sort((a, b) => a - b);
  return arr.every((val, i) => val === sortedCopy[i]);
}

// Without built-in sorting — manual adjacent-pair scan
function isSortedManual(arr) {
  for (let i = 0; i < arr.length - 1; i++) {
    if (arr[i] > arr[i + 1]) {
      return false;
    }
  }
  return true;
}

const sortedArr15 = [1, 2, 3, 4, 5];
const unsortedArr15 = [1, 3, 2, 4, 5];

console.log("Built-in sortedArr  :", isSortedBuiltIn(sortedArr15));
console.log("Manual   sortedArr  :", isSortedManual(sortedArr15));

console.log("Built-in unsortedArr:", isSortedBuiltIn(unsortedArr15));
console.log("Manual   unsortedArr:", isSortedManual(unsortedArr15));
```

[Back to top](#table-of-contents)

---

## 16. Remove Duplicates from an Array

```javascript
// Using built-in Set (preserves insertion order)
function removeDuplicatesBuiltIn(arr) {
  return [...new Set(arr)];
}

// Without built-in — manual scan
function removeDuplicatesManual(arr) {
  const result = [];
  for (let i = 0; i < arr.length; i++) {
    let found = false;
    for (let j = 0; j < result.length; j++) {
      if (result[j] === arr[i]) {
        found = true;
        break;
      }
    }
    if (!found) {
      result.push(arr[i]);
    }
  }
  return result;
}

const numbers16 = [1, 2, 2, 3, 4, 4, 5, 1];
console.log("Built-in:", removeDuplicatesBuiltIn(numbers16));
console.log("Manual  :", removeDuplicatesManual(numbers16));
```

[Back to top](#table-of-contents)

---

## 17. Prime Number Check

```javascript
// Using built-in Math.sqrt
function isPrimeBuiltIn(n) {
  if (n < 2) return false;
  for (let i = 2; i <= Math.sqrt(n); i++) {
    if (n % i === 0) return false;
  }
  return true;
}

// Without built-in sqrt — manual i*i boundary check
function isPrimeManual(n) {
  if (n < 2) return false;
  for (let i = 2; i * i <= n; i++) {
    if (n % i === 0) return false;
  }
  return true;
}

const num17 = 29;
console.log("Built-in:", isPrimeBuiltIn(num17));
console.log("Manual  :", isPrimeManual(num17));
```

[Back to top](#table-of-contents)

---

## 18. Fibonacci Series

```javascript
// Using built-in Array.from
function fibonacciBuiltIn(n) {
  const fib = Array.from({ length: n }, () => 0);
  if (n > 0) fib[0] = 0;
  if (n > 1) fib[1] = 1;
  for (let i = 2; i < n; i++) {
    fib[i] = fib[i - 1] + fib[i - 2];
  }
  console.log("Built-in:", fib.join(" "));
}

// Without built-ins — plain loop
function fibonacciManual(n) {
  const result = [];
  let a = 0, b = 1;
  for (let i = 0; i < n; i++) {
    result.push(a);
    const next = a + b;
    a = b;
    b = next;
  }
  console.log("Manual  :", result.join(" "));
}

const n18 = 10;
fibonacciBuiltIn(n18);
fibonacciManual(n18);
```

[Back to top](#table-of-contents)

---

## 19. Factorial

```javascript
// Using built-in BigInt (arbitrary precision, like BigInteger)
function factorialBuiltIn(n) {
  let result = 1n;
  for (let i = 2n; i <= BigInt(n); i++) {
    result *= i;
  }
  return result;
}

// Without built-ins — plain recursion (regular number, precision-limited)
function factorialManual(n) {
  if (n <= 1) return 1;
  return n * factorialManual(n - 1);
}

const n19 = 10;
console.log("Built-in:", factorialBuiltIn(n19).toString());
console.log("Manual  :", factorialManual(n19));
```

[Back to top](#table-of-contents)

---

## 20. Armstrong Number

```javascript
// Using built-in String/Math methods
function isArmstrongBuiltIn(num) {
  const numStr = String(num);
  const digits = numStr.length;
  let sum = 0;
  for (const c of numStr) {
    sum += Math.pow(Number(c), digits);
  }
  return sum === num;
}

// Without built-ins — manual digit extraction and power calc
function isArmstrongManual(num) {
  const original = num;
  let digits = 0;
  let temp = num;
  while (temp !== 0) {
    digits++;
    temp = Math.floor(temp / 10);
  }

  let sum = 0;
  temp = num;
  while (temp !== 0) {
    const digit = temp % 10;
    let power = 1;
    for (let i = 0; i < digits; i++) {
      power *= digit;
    }
    sum += power;
    temp = Math.floor(temp / 10);
  }

  return sum === original;
}

const num20 = 153;
console.log("Built-in:", isArmstrongBuiltIn(num20));
console.log("Manual  :", isArmstrongManual(num20));
```

[Back to top](#table-of-contents)

---

## 21. FizzBuzz

```javascript
// Using built-in Array.from + map
function fizzBuzzBuiltIn(n) {
  console.log("Built-in:");
  Array.from({ length: n }, (_, i) => i + 1).forEach((i) => {
    if (i % 15 === 0) console.log("FizzBuzz");
    else if (i % 3 === 0) console.log("Fizz");
    else if (i % 5 === 0) console.log("Buzz");
    else console.log(i);
  });
}

// Without built-ins — plain loop
function fizzBuzzManual(n) {
  console.log("Manual:");
  for (let i = 1; i <= n; i++) {
    if (i % 15 === 0) console.log("FizzBuzz");
    else if (i % 3 === 0) console.log("Fizz");
    else if (i % 5 === 0) console.log("Buzz");
    else console.log(i);
  }
}

const n21 = 15;
fizzBuzzBuiltIn(n21);
fizzBuzzManual(n21);
```

[Back to top](#table-of-contents)

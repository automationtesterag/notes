# JavaScript String Programs

## 1. Reverse a String

JavaScript

```
function reverseString(str) {
  return str.split("").reverse().join("");
}

console.log(reverseString("hello"));
// Output: olleh
```

### Using for loop

JavaScript

```
function reverseString(str) {
  let reversed = "";

  for (let i = str.length - 1; i >= 0; i--) {
    reversed += str[i];
  }

  return reversed;
}

console.log(reverseString("JavaScript"));
// Output: tpircSavaJ
```

## 2. Reverse Each Word Without Changing Position

JavaScript

```
function reverseWords(str) {
  return str
    .split(" ")
    .map(word => word.split("").reverse().join(""))
    .join(" ");
}

console.log(reverseWords("Hello World JavaScript"));
// Output: olleH dlroW tpircSavaJ
```

### Without using reverse()

JavaScript

```
function reverseWords(str) {
  const words = str.split(" ");
  let result = [];

  for (let word of words) {
    let reversedWord = "";

    for (let i = word.length - 1; i >= 0; i--) {
      reversedWord += word[i];
    }

    result.push(reversedWord);
  }

  return result.join(" ");
}

console.log(reverseWords("Hello World JavaScript"));
// Output: olleH dlroW tpircSavaJ
```

## 3. Find Duplicate Characters

JavaScript

```
function findDuplicates(str) {
  const charCount = {};
  const duplicates = [];

  for (const char of str) {
    charCount[char] = (charCount[char] || 0) + 1;
  }

  for (const char in charCount) {
    if (charCount[char] > 1) {
      duplicates.push(char);
    }
  }

  return duplicates;
}

console.log(findDuplicates("programming"));
// Output: [ 'r', 'g', 'm' ]
```

### Using Set

JavaScript

```
function findDuplicates(str) {
  const seen = new Set();
  const duplicates = new Set();

  for (const char of str) {
    if (seen.has(char)) {
      duplicates.add(char);
    } else {
      seen.add(char);
    }
  }

  return [...duplicates];
}

console.log(findDuplicates("programming"));
// Output: [ 'r', 'g', 'm' ]
```

## 4. Count Each Character in a String

JavaScript

```
function charCount(str) {
  const count = {};

  for (const char of str) {
    count[char] = (count[char] || 0) + 1;
  }

  return count;
}

console.log(charCount("hello"));
// Output: { h: 1, e: 1, l: 2, o: 1 }
```

### Ignore spaces and case

JavaScript

```
function charCount(str) {
  const count = {};

  for (const char of str.toLowerCase()) {
    if (char === " ") continue;

    count[char] = (count[char] || 0) + 1;
  }

  return count;
}

console.log(charCount("Hello World"));
// Output: { h: 1, e: 1, l: 3, o: 2, w: 1, r: 1, d: 1 }
```

## 5. Find Maximum Character Count

JavaScript

```
function maxCharCount(str) {
  const charCount = {};

  // Count characters
  for (const char of str) {
    charCount[char] = (charCount[char] || 0) + 1;
  }

  let maxChar = "";
  let maxCount = 0;

  // Find maximum count
  for (const char in charCount) {
    if (charCount[char] > maxCount) {
      maxCount = charCount[char];
      maxChar = char;
    }
  }

  return {
    char: maxChar,
    count: maxCount
  };
}

console.log(maxCharCount("programming"));
// Output: { char: 'r', count: 2 }
```

## All 5 Programs in One Code Block

JavaScript

```
// 1. Reverse a string
function reverseString(str) {
  return str.split("").reverse().join("");
}

console.log(reverseString("hello"));
// olleh


// 2. Reverse each word without changing word position
function reverseWords(str) {
  return str
    .split(" ")
    .map(word => word.split("").reverse().join(""))
    .join(" ");
}

console.log(reverseWords("Hello World JavaScript"));
// olleH dlroW tpircSavaJ


// 3. Find duplicate characters
function findDuplicates(str) {
  const charCount = {};
  const duplicates = [];

  for (const char of str) {
    charCount[char] = (charCount[char] || 0) + 1;
  }

  for (const char in charCount) {
    if (charCount[char] > 1) {
      duplicates.push(char);
    }
  }

  return duplicates;
}

console.log(findDuplicates("programming"));
// [ 'r', 'g', 'm' ]


// 4. Count each character
function charCount(str) {
  const count = {};

  for (const char of str) {
    count[char] = (count[char] || 0) + 1;
  }

  return count;
}

console.log(charCount("hello"));
// { h: 1, e: 1, l: 2, o: 1 }


// 5. Find maximum character count
function maxCharCount(str) {
  const charCount = {};

  for (const char of str) {
    charCount[char] = (charCount[char] || 0) + 1;
  }

  let maxChar = "";
  let maxCount = 0;

  for (const char in charCount) {
    if (charCount[char] > maxCount) {
      maxCount = charCount[char];
      maxChar = char;
    }
  }

  return {
    char: maxChar,
    count: maxCount
  };
}

console.log(maxCharCount("programming"));
// { char: 'r', count: 2 }
```

Key interview concept:

JavaScript

```
charCount[char] = (charCount[char] || 0) + 1;
```

This is the most important line for character-counting problems in JavaScript.

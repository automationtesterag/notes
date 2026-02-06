# Interview Programs

## 1. Reverse of String

### Method: 1

```javascript
function reverseString(str) {
  return str.split("").reverse().join("");
}

// Example usage
console.log(reverseString("hello")); // Output: "olleh"
```

### Method: 2 (without built-in methods)

```javascript
function reverseString(str) {
  let reversed = "";
  for (let i = str.length - 1; i >= 0; i--) {
    reversed += str[i];
  }
  return reversed;
}

// Example usage
console.log(reverseString("world")); // Output: "dlrow"
```

---

## 2. Reverse each word in a string without changing their position

### Method: 1 (Using Built-in Methods)

```javascript
function reverseWordsBuiltIn(str) {
  return str
    .split(" ")
    .map((word) => word.split("").reverse().join(""))
    .join(" ");
}

// Example
const input = "Hello World from Anudeep";
console.log(reverseWordsBuiltIn(input)); // Output: "olleH dlroW morf peeduna"
```

---

### Method: 2 (without built-in methods)

```javascript
function reverseWordsManual(str) {
  let result = "";
  let word = "";

  for (let i = 0; i <= str.length; i++) {
    const char = str[i] || " "; // Add space at end to process last word

    if (char !== " ") {
      word += char;
    } else {
      // Reverse the collected word manually
      let reversed = "";
      for (let j = word.length - 1; j >= 0; j--) {
        reversed += word[j];
      }
      result += reversed + " ";
      word = "";
    }
  }

  return result.trim(); // Remove trailing space
}

// Example
const input = "Hello World from ChatGPT";
console.log(reverseWordsManual(input)); // Output: "olleH dlroW morf TPGTahC"
```

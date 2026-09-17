Here's JavaScript — same five programs, each with a built-in-methods version and a manual (no built-in) version.

## 1. Reverse a String

```javascript
// Using built-in methods
function reverseBuiltIn(str) {
    return str.split("").reverse().join("");
}

// Without built-in methods
function reverseManual(str) {
    let result = "";
    for (let i = str.length - 1; i >= 0; i--) {
        result += str[i];
    }
    return result;
}

const text1 = "Hello World";
console.log("Built-in:", reverseBuiltIn(text1));
console.log("Manual  :", reverseManual(text1));
```

## 2. Reverse Each Word (word position stays same)

```javascript
// Using built-in methods
function reverseWordsBuiltIn(str) {
    return str.split(" ")
              .map(word => word.split("").reverse().join(""))
              .join(" ");
}

// Without built-in methods
function reverseWordsManual(str) {
    let result = "";
    let word = "";

    for (let i = 0; i <= str.length; i++) {
        if (i === str.length || str[i] === " ") {
            // reverse current word manually
            let rev = "";
            for (let j = word.length - 1; j >= 0; j--) {
                rev += word[j];
            }
            result += rev;
            if (i !== str.length) result += " ";
            word = "";
        } else {
            word += str[i];
        }
    }
    return result;
}

const text2 = "Hello World JavaScript";
console.log("Built-in:", reverseWordsBuiltIn(text2));
console.log("Manual  :", reverseWordsManual(text2));
```

## 3. Max Character Count (most frequent character)

```javascript
// Using built-in methods (Map)
function maxCharBuiltIn(str) {
    const freq = new Map();
    for (const ch of str) {
        freq.set(ch, (freq.get(ch) || 0) + 1);
    }
    let maxChar = str[0];
    let maxCount = 0;
    for (const [ch, count] of freq) {
        if (count > maxCount) {
            maxCount = count;
            maxChar = ch;
        }
    }
    return maxChar;
}

// Without built-in methods
function maxCharManual(str) {
    const counts = {};
    for (let i = 0; i < str.length; i++) {
        const ch = str[i];
        if (counts[ch] === undefined) {
            counts[ch] = 1;
        } else {
            counts[ch]++;
        }
    }

    let maxChar = str[0];
    let maxCount = 0;
    for (let i = 0; i < str.length; i++) {
        const ch = str[i];
        if (counts[ch] > maxCount) {
            maxCount = counts[ch];
            maxChar = ch;
        }
    }
    return maxChar;
}

const text3 = "programming";
console.log("Built-in:", maxCharBuiltIn(text3));
console.log("Manual  :", maxCharManual(text3));
```

## 4. Maximum of 3 Numbers

```javascript
// Using built-in Math.max
function maxBuiltIn(a, b, c) {
    return Math.max(a, b, c);
}

// Without built-in
function maxManual(a, b, c) {
    let result = a;
    if (b > result) result = b;
    if (c > result) result = c;
    return result;
}

const a = 10, b = 25, c = 17;
console.log("Built-in:", maxBuiltIn(a, b, c));
console.log("Manual  :", maxManual(a, b, c));
```

## 5. Sort Array (Ascending & Descending)

```javascript
// Using built-in sort()
function sortBuiltIn(arr) {
    const ascending = [...arr].sort((x, y) => x - y);
    const descending = [...arr].sort((x, y) => y - x);
    return { ascending, descending };
}

// Without built-in (manual bubble sort)
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
    let left = 0, right = desc.length - 1;
    while (left < right) {
        const temp = desc[left];
        desc[left] = desc[right];
        desc[right] = temp;
        left++;
        right--;
    }

    return { ascending: asc, descending: desc };
}

const numbers = [5, 2, 9, 1, 7];

const builtInResult = sortBuiltIn(numbers);
console.log("Built-in Ascending :", builtInResult.ascending);
console.log("Built-in Descending:", builtInResult.descending);

const manualResult = sortManual(numbers);
console.log("Manual Ascending :", manualResult.ascending);
console.log("Manual Descending:", manualResult.descending);
```

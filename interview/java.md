# Java Interview Programs — Built-in vs Manual

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

```java
public class ReverseString {
    // Using built-in method
    public static String reverseBuiltIn(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    // Without built-in method
    public static String reverseManual(String str) {
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }
        return reversed;
    }

    public static void main(String[] args) {
        String input = "Hello World";
        System.out.println("Built-in: " + reverseBuiltIn(input));
        System.out.println("Manual  : " + reverseManual(input));
    }
}
```

[Back to top](#table-of-contents)

---

## 2. Reverse Each Word (Keep Word Position)

```java
public class ReverseWordsKeepPosition {
    // Using built-in methods
    public static String reverseWordsBuiltIn(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(new StringBuilder(word).reverse()).append(" ");
        }
        return result.toString().trim();
    }

    // Without built-in methods
    public static String reverseWordsManual(String str) {
        StringBuilder result = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i <= str.length(); i++) {
            if (i == str.length() || str.charAt(i) == ' ') {
                for (int j = word.length() - 1; j >= 0; j--) {
                    result.append(word.charAt(j));
                }
                if (i != str.length()) result.append(' ');
                word.setLength(0);
            } else {
                word.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "Hello World Java";
        System.out.println("Built-in: " + reverseWordsBuiltIn(input));
        System.out.println("Manual  : " + reverseWordsManual(input));
    }
}
```

[Back to top](#table-of-contents)

---

## 3. Max Character Count (Most Frequent Char)

```java
import java.util.HashMap;
import java.util.Map;

public class MaxCharCount {
    // Using built-in HashMap
    public static char maxCharBuiltIn(String str) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        char maxChar = str.charAt(0);
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxChar = entry.getKey();
            }
        }
        return maxChar;
    }

    // Without built-in (manual counting using array, ASCII assumed)
    public static char maxCharManual(String str) {
        int[] counts = new int[256];
        for (int i = 0; i < str.length(); i++) {
            counts[str.charAt(i)]++;
        }
        char maxChar = str.charAt(0);
        int maxCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (counts[c] > maxCount) {
                maxCount = counts[c];
                maxChar = c;
            }
        }
        return maxChar;
    }

    public static void main(String[] args) {
        String input = "programming";
        System.out.println("Built-in: " + maxCharBuiltIn(input));
        System.out.println("Manual  : " + maxCharManual(input));
    }
}
```

[Back to top](#table-of-contents)

---

## 4. Maximum of 3 Numbers

```java
public class MaxOfThree {
    // Using built-in Math.max
    public static int maxBuiltIn(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    // Without built-in
    public static int maxManual(int a, int b, int c) {
        int max = a;
        if (b > max) max = b;
        if (c > max) max = c;
        return max;
    }

    public static void main(String[] args) {
        int a = 10, b = 25, c = 17;
        System.out.println("Built-in: " + maxBuiltIn(a, b, c));
        System.out.println("Manual  : " + maxManual(a, b, c));
    }
}
```

[Back to top](#table-of-contents)

---

## 5. Sort Array — Ascending & Descending

```java
import java.util.Arrays;
import java.util.Collections;

public class SortArray {
    // Using built-in Arrays.sort
    public static void sortBuiltIn(int[] arr) {
        int[] asc = arr.clone();
        Arrays.sort(asc);

        Integer[] descArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Arrays.sort(descArr, Collections.reverseOrder());

        System.out.print("Built-in Ascending : ");
        System.out.println(Arrays.toString(asc));
        System.out.print("Built-in Descending: ");
        System.out.println(Arrays.toString(descArr));
    }

    // Without built-in (manual bubble sort)
    public static void sortManual(int[] arr) {
        int[] asc = arr.clone();
        int n = asc.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (asc[j] > asc[j + 1]) {
                    int temp = asc[j];
                    asc[j] = asc[j + 1];
                    asc[j + 1] = temp;
                }
            }
        }

        int[] desc = asc.clone();
        for (int i = 0; i < desc.length / 2; i++) {
            int temp = desc[i];
            desc[i] = desc[desc.length - 1 - i];
            desc[desc.length - 1 - i] = temp;
        }

        System.out.print("Manual Ascending : ");
        printArray(asc);
        System.out.print("Manual Descending: ");
        printArray(desc);
    }

    private static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 7};
        sortBuiltIn(numbers);
        sortManual(numbers);
    }
}
```

[Back to top](#table-of-contents)

---

## 6. Palindrome Check

```java
public class PalindromeCheck {
    // Using built-in method
    public static boolean isPalindromeBuiltIn(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }

    // Without built-in
    public static boolean isPalindromeManual(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        String input = "madam";
        System.out.println("Built-in: " + isPalindromeBuiltIn(input));
        System.out.println("Manual  : " + isPalindromeManual(input));
    }
}
```

[Back to top](#table-of-contents)

---

## 7. Anagram Check

```java
import java.util.Arrays;

public class AnagramCheck {
    // Using built-in method (sorting)
    public static boolean isAnagramBuiltIn(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    // Without built-in (manual frequency count)
    public static boolean isAnagramManual(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] counts = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            counts[s1.charAt(i)]++;
            counts[s2.charAt(i)]--;
        }
        for (int c : counts) {
            if (c != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "listen", s2 = "silent";
        System.out.println("Built-in: " + isAnagramBuiltIn(s1, s2));
        System.out.println("Manual  : " + isAnagramManual(s1, s2));
    }
}
```

[Back to top](#table-of-contents)

---

## 8. Count Vowels, Consonants, Special Characters

```java
public class CountCharTypes {
    // Using built-in methods
    public static void countBuiltIn(String str) {
        int vowels = 0, consonants = 0, special = 0;
        String vowelSet = "aeiouAEIOU";
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                if (vowelSet.indexOf(c) != -1) vowels++;
                else consonants++;
            } else if (!Character.isWhitespace(c)) {
                special++;
            }
        }
        System.out.println("Built-in -> Vowels: " + vowels + ", Consonants: " + consonants + ", Special: " + special);
    }

    // Without built-in
    public static void countManual(String str) {
        int vowels = 0, consonants = 0, special = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean isLetter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            if (isLetter) {
                char lower = (c >= 'A' && c <= 'Z') ? (char) (c + 32) : c;
                if (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            } else if (c != ' ') {
                special++;
            }
        }
        System.out.println("Manual   -> Vowels: " + vowels + ", Consonants: " + consonants + ", Special: " + special);
    }

    public static void main(String[] args) {
        String input = "Hello World! 123";
        countBuiltIn(input);
        countManual(input);
    }
}
```

[Back to top](#table-of-contents)

---

## 9. Check if String Contains Only Digits/Alphabets

```java
public class OnlyDigitsOrAlphabets {
    // Using built-in methods
    public static boolean isDigitsOnlyBuiltIn(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static boolean isAlphabetsOnlyBuiltIn(String str) {
        return str.chars().allMatch(Character::isLetter);
    }

    // Without built-in
    public static boolean isDigitsOnlyManual(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public static boolean isAlphabetsOnlyManual(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String digits = "12345";
        String alpha = "HelloWorld";

        System.out.println("Built-in digitsOnly: " + isDigitsOnlyBuiltIn(digits));
        System.out.println("Manual   digitsOnly: " + isDigitsOnlyManual(digits));

        System.out.println("Built-in alphaOnly : " + isAlphabetsOnlyBuiltIn(alpha));
        System.out.println("Manual   alphaOnly : " + isAlphabetsOnlyManual(alpha));
    }
}
```

[Back to top](#table-of-contents)

---

## 10. Remove Whitespace/Duplicate Spaces from a String

```java
public class RemoveSpaces {
    // Using built-in methods
    public static String removeAllWhitespaceBuiltIn(String str) {
        return str.replaceAll("\\s+", "");
    }

    public static String removeDuplicateSpacesBuiltIn(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }

    // Without built-in (no regex)
    public static String removeAllWhitespaceManual(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\n') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String removeDuplicateSpacesManual(String str) {
        StringBuilder sb = new StringBuilder();
        boolean lastWasSpace = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                if (!lastWasSpace && sb.length() > 0) {
                    sb.append(c);
                }
                lastWasSpace = true;
            } else {
                sb.append(c);
                lastWasSpace = false;
            }
        }
        // trim trailing space if any
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "  Hello    World   Java  ";

        System.out.println("Built-in removeAll  : [" + removeAllWhitespaceBuiltIn(input) + "]");
        System.out.println("Manual   removeAll  : [" + removeAllWhitespaceManual(input) + "]");

        System.out.println("Built-in dedupSpaces: [" + removeDuplicateSpacesBuiltIn(input) + "]");
        System.out.println("Manual   dedupSpaces: [" + removeDuplicateSpacesManual(input) + "]");
    }
}
```

[Back to top](#table-of-contents)

---

## 11. Find Duplicates in an Array

```java
import java.util.HashSet;
import java.util.Set;

public class FindDuplicates {
    // Using built-in HashSet
    public static Set<Integer> findDuplicatesBuiltIn(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (int num : arr) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        return duplicates;
    }

    // Without built-in (manual, nested loop)
    public static int[] findDuplicatesManual(int[] arr) {
        int n = arr.length;
        int[] temp = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            boolean isDup = false;
            for (int j = 0; j < i; j++) {
                if (arr[i] == arr[j]) {
                    isDup = true;
                    break;
                }
            }
            if (isDup) {
                boolean alreadyAdded = false;
                for (int k = 0; k < count; k++) {
                    if (temp[k] == arr[i]) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    temp[count++] = arr[i];
                }
            }
        }

        int[] result = new int[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 2, 4, 5, 1};

        System.out.println("Built-in: " + findDuplicatesBuiltIn(numbers));

        int[] manualResult = findDuplicatesManual(numbers);
        System.out.print("Manual  : [");
        for (int i = 0; i < manualResult.length; i++) {
            System.out.print(manualResult[i]);
            if (i < manualResult.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
```

[Back to top](#table-of-contents)

---

## 12. Compare Two Arrays and Find Differences

```java
import java.util.HashSet;
import java.util.Set;

public class CompareArrays {
    // Using built-in HashSet
    public static void compareBuiltIn(int[] arr1, int[] arr2) {
        Set<Integer> set1 = new HashSet<>();
        for (int n : arr1) set1.add(n);
        Set<Integer> set2 = new HashSet<>();
        for (int n : arr2) set2.add(n);

        Set<Integer> onlyInArr1 = new HashSet<>(set1);
        onlyInArr1.removeAll(set2);

        Set<Integer> onlyInArr2 = new HashSet<>(set2);
        onlyInArr2.removeAll(set1);

        System.out.println("Built-in -> Only in arr1: " + onlyInArr1 + ", Only in arr2: " + onlyInArr2);
    }

    // Without built-in
    public static void compareManual(int[] arr1, int[] arr2) {
        System.out.print("Manual   -> Only in arr1: [");
        boolean first = true;
        for (int i = 0; i < arr1.length; i++) {
            boolean found = false;
            for (int j = 0; j < arr2.length; j++) {
                if (arr1[i] == arr2[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (!first) System.out.print(", ");
                System.out.print(arr1[i]);
                first = false;
            }
        }
        System.out.print("], Only in arr2: [");
        first = true;
        for (int i = 0; i < arr2.length; i++) {
            boolean found = false;
            for (int j = 0; j < arr1.length; j++) {
                if (arr2[i] == arr1[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (!first) System.out.print(", ");
                System.out.print(arr2[i]);
                first = false;
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {3, 4, 5, 6, 7};

        compareBuiltIn(arr1, arr2);
        compareManual(arr1, arr2);
    }
}
```

[Back to top](#table-of-contents)

---

## 13. Find Missing Elements Between Two Arrays

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindMissingElements {
    // Using built-in HashSet — elements in full set missing from subset
    public static List<Integer> findMissingBuiltIn(int[] fullSet, int[] subset) {
        Set<Integer> subsetLookup = new HashSet<>();
        for (int n : subset) subsetLookup.add(n);

        List<Integer> missing = new ArrayList<>();
        for (int n : fullSet) {
            if (!subsetLookup.contains(n)) {
                missing.add(n);
            }
        }
        return missing;
    }

    // Without built-in
    public static int[] findMissingManual(int[] fullSet, int[] subset) {
        int[] temp = new int[fullSet.length];
        int count = 0;

        for (int i = 0; i < fullSet.length; i++) {
            boolean found = false;
            for (int j = 0; j < subset.length; j++) {
                if (fullSet[i] == subset[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp[count++] = fullSet[i];
            }
        }

        int[] result = new int[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public static void main(String[] args) {
        int[] fullSet = {1, 2, 3, 4, 5, 6, 7};
        int[] subset = {1, 3, 5, 7};

        System.out.println("Built-in: " + findMissingBuiltIn(fullSet, subset));

        int[] manualResult = findMissingManual(fullSet, subset);
        System.out.print("Manual  : [");
        for (int i = 0; i < manualResult.length; i++) {
            System.out.print(manualResult[i]);
            if (i < manualResult.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
```

[Back to top](#table-of-contents)

---

## 14. Second Largest/Smallest Element

```java
import java.util.Arrays;

public class SecondLargestSmallest {
    // Using built-in Arrays.sort
    public static void secondLargestSmallestBuiltIn(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int secondSmallest = sorted[1];
        int secondLargest = sorted[sorted.length - 2];
        System.out.println("Built-in -> Second Largest: " + secondLargest + ", Second Smallest: " + secondSmallest);
    }

    // Without built-in (single pass)
    public static void secondLargestSmallestManual(int[] arr) {
        int largest = Integer.MIN_VALUE, secondLargest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE, secondSmallest = Integer.MAX_VALUE;

        for (int num : arr) {
            // largest / second largest
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }

            // smallest / second smallest
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }
        }

        System.out.println("Manual   -> Second Largest: " + secondLargest + ", Second Smallest: " + secondSmallest);
    }

    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 7, 9, 2};
        secondLargestSmallestBuiltIn(numbers);
        secondLargestSmallestManual(numbers);
    }
}
```

[Back to top](#table-of-contents)

---

## 15. Check if an Array is Sorted

```java
import java.util.Arrays;

public class IsArraySorted {
    // Using built-in method (compare with sorted copy)
    public static boolean isSortedBuiltIn(int[] arr) {
        int[] sortedCopy = arr.clone();
        Arrays.sort(sortedCopy);
        return Arrays.equals(arr, sortedCopy);
    }

    // Without built-in
    public static boolean isSortedManual(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] sortedArr = {1, 2, 3, 4, 5};
        int[] unsortedArr = {1, 3, 2, 4, 5};

        System.out.println("Built-in sortedArr  : " + isSortedBuiltIn(sortedArr));
        System.out.println("Manual   sortedArr  : " + isSortedManual(sortedArr));

        System.out.println("Built-in unsortedArr: " + isSortedBuiltIn(unsortedArr));
        System.out.println("Manual   unsortedArr: " + isSortedManual(unsortedArr));
    }
}
```

[Back to top](#table-of-contents)

---

## 16. Remove Duplicates from an Array

```java
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicatesArray {
    // Using built-in LinkedHashSet (preserves order)
    public static int[] removeDuplicatesBuiltIn(int[] arr) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int num : arr) set.add(num);

        int[] result = new int[set.size()];
        int i = 0;
        for (int num : set) result[i++] = num;
        return result;
    }

    // Without built-in
    public static int[] removeDuplicatesManual(int[] arr) {
        int[] temp = new int[arr.length];
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            boolean found = false;
            for (int j = 0; j < count; j++) {
                if (temp[j] == arr[i]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp[count++] = arr[i];
            }
        }

        int[] result = new int[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 2, 3, 4, 4, 5, 1};

        int[] builtInResult = removeDuplicatesBuiltIn(numbers);
        System.out.print("Built-in: [");
        for (int i = 0; i < builtInResult.length; i++) {
            System.out.print(builtInResult[i]);
            if (i < builtInResult.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        int[] manualResult = removeDuplicatesManual(numbers);
        System.out.print("Manual  : [");
        for (int i = 0; i < manualResult.length; i++) {
            System.out.print(manualResult[i]);
            if (i < manualResult.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
```

[Back to top](#table-of-contents)

---

## 17. Prime Number Check

```java
public class PrimeCheck {
    // Using built-in Math.sqrt
    public static boolean isPrimeBuiltIn(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Without built-in (manual square root boundary)
    public static boolean isPrimeManual(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int num = 29;
        System.out.println("Built-in: " + isPrimeBuiltIn(num));
        System.out.println("Manual  : " + isPrimeManual(num));
    }
}
```

[Back to top](#table-of-contents)

---

## 18. Fibonacci Series

```java
import java.util.stream.IntStream;

public class FibonacciSeries {
    // Using built-in Stream (iterative under the hood via IntStream)
    public static void fibonacciBuiltIn(int n) {
        int[] fib = new int[n];
        if (n > 0) fib[0] = 0;
        if (n > 1) fib[1] = 1;
        IntStream.range(2, n).forEach(i -> fib[i] = fib[i - 1] + fib[i - 2]);
        System.out.print("Built-in: ");
        for (int num : fib) System.out.print(num + " ");
        System.out.println();
    }

    // Without built-in (plain loop)
    public static void fibonacciManual(int n) {
        System.out.print("Manual  : ");
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        fibonacciBuiltIn(n);
        fibonacciManual(n);
    }
}
```

[Back to top](#table-of-contents)

---

## 19. Factorial

```java
import java.math.BigInteger;

public class Factorial {
    // Using built-in BigInteger
    public static BigInteger factorialBuiltIn(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // Without built-in (plain long, recursive)
    public static long factorialManual(int n) {
        if (n <= 1) return 1;
        return n * factorialManual(n - 1);
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Built-in: " + factorialBuiltIn(n));
        System.out.println("Manual  : " + factorialManual(n));
    }
}
```

[Back to top](#table-of-contents)

---

## 20. Armstrong Number

```java
public class ArmstrongNumber {
    // Using built-in String/Math methods
    public static boolean isArmstrongBuiltIn(int num) {
        String numStr = String.valueOf(num);
        int digits = numStr.length();
        int sum = 0;
        for (char c : numStr.toCharArray()) {
            sum += Math.pow(Character.getNumericValue(c), digits);
        }
        return sum == num;
    }

    // Without built-in
    public static boolean isArmstrongManual(int num) {
        int original = num;
        int digits = 0;
        int temp = num;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }

        int sum = 0;
        temp = num;
        while (temp != 0) {
            int digit = temp % 10;
            int power = 1;
            for (int i = 0; i < digits; i++) {
                power *= digit;
            }
            sum += power;
            temp /= 10;
        }

        return sum == original;
    }

    public static void main(String[] args) {
        int num = 153;
        System.out.println("Built-in: " + isArmstrongBuiltIn(num));
        System.out.println("Manual  : " + isArmstrongManual(num));
    }
}
```

[Back to top](#table-of-contents)

---

## 21. FizzBuzz

```java
import java.util.stream.IntStream;

public class FizzBuzz {
    // Using built-in Stream
    public static void fizzBuzzBuiltIn(int n) {
        System.out.println("Built-in:");
        IntStream.rangeClosed(1, n).forEach(i -> {
            if (i % 15 == 0) System.out.println("FizzBuzz");
            else if (i % 3 == 0) System.out.println("Fizz");
            else if (i % 5 == 0) System.out.println("Buzz");
            else System.out.println(i);
        });
    }

    // Without built-in (plain loop)
    public static void fizzBuzzManual(int n) {
        System.out.println("Manual:");
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) System.out.println("FizzBuzz");
            else if (i % 3 == 0) System.out.println("Fizz");
            else if (i % 5 == 0) System.out.println("Buzz");
            else System.out.println(i);
        }
    }

    public static void main(String[] args) {
        int n = 15;
        fizzBuzzBuiltIn(n);
        fizzBuzzManual(n);
    }
}
```

[Back to top](#table-of-contents)

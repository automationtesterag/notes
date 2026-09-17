Here's Java — five programs, each with a built-in-methods version and a manual (no built-in) version.

## 1. Reverse a String

```java
import java.util.Scanner;

public class ReverseString {
    // Using built-in method
    public static String reverseBuiltIn(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    // Without built-in method
    public static String reverseManual(String str) {
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String input = "Hello World";
        System.out.println("Built-in: " + reverseBuiltIn(input));
        System.out.println("Manual  : " + reverseManual(input));
    }
}
```

## 2. Reverse Each Word (word position stays same)

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
                // reverse the collected word manually
                for (int j = word.length() - 1; j >= 0; j--) {
                    result.append(word.charAt(j));
                }
                if (i != str.length()) result.append(' ');
                word.setLength(0); // reset for next word
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

## 3. Max Character Count (most frequent character)

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

## 5. Sort Array (Ascending & Descending)

```java
import java.util.Arrays;
import java.util.Collections;

public class SortArray {
    // Using built-in Arrays.sort
    public static void sortBuiltIn(int[] arr) {
        int[] asc = arr.clone();
        Arrays.sort(asc); // ascending

        Integer[] descArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Arrays.sort(descArr, Collections.reverseOrder()); // descending

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

That's all 5 Java programs done. Want me to move on to **Python** next?

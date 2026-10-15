# Easy Java Programs for Automation Testing Interviews

Here are simple, beginner-friendly Java programs for strings, arrays, numbers, file handling, Excel, and regex. Each program is separated so you can practice them individually.

Common imports for most programs:

```
import java.util.*;
import java.io.*;
```

## 1. Find duplicate characters in a string

Example: `automation` → `a` appears 2 times, `o` appears 2 times, `t` appears 2 times.

```
public class Main {
    public static void main(String[] args) {
        String str = "automation";

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char ch : str.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for (char ch : map.keySet()) {
            if (map.get(ch) > 1) {
                System.out.println(ch + " = " + map.get(ch));
            }
        }
    }
}
```

## 2. Find duplicate elements in an array

```
public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 10, 30, 20, 40};

        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new LinkedHashSet<>();

        for (int num : arr) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }

        System.out.println(duplicates);
    }
}
```

Output: `[10, 20]`

## 3. Remove duplicates from an array

```
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 10, 30, 20};

        Set<Integer> set = new LinkedHashSet<>();

        for (int num : arr) {
            set.add(num);
        }

        System.out.println(set);
    }
}
```

Output: `[10, 20, 30]`

## 4. Remove duplicate characters from a string

```
public class Main {
    public static void main(String[] args) {
        String str = "programming";
        String result = "";

        for (char ch : str.toCharArray()) {
            if (result.indexOf(ch) == -1) {
                result += ch;
            }
        }

        System.out.println(result);
    }
}
```

Output: `progamin`

## 5. Count each character in a string

```
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String str = "java";

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char ch : str.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        System.out.println(map);
    }
}
```

Output: `{j=1, a=2, v=1}`

## 6. Count each element in an array

```
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 10, 30, 20, 10};

        Map<Integer, Integer> map = new LinkedHashMap<>();

        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        System.out.println(map);
    }
}
```

Output: `{10=3, 20=2, 30=1}`

## 7. Reverse a string

```
public class Main {
    public static void main(String[] args) {
        String str = "Selenium";
        String reverse = "";

        for (int i = str.length() - 1; i >= 0; i--) {
            reverse += str.charAt(i);
        }

        System.out.println(reverse);
    }
}
```

Output: `muineleS`

## 8. Reverse an array

```
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};

        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }

        System.out.println(Arrays.toString(arr));
    }
}
```

Output: `[50, 40, 30, 20, 10]`

## 9. Sort an array

```
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {40, 10, 30, 20, 50};

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
```

Output: `[10, 20, 30, 40, 50]`

For descending order with an `Integer[]` array:

```
Integer[] arr = {40, 10, 30, 20, 50};

Arrays.sort(arr, Collections.reverseOrder());

System.out.println(Arrays.toString(arr));
```

## 10. Find the character with the maximum count in a string

```
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String str = "programming";

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char ch : str.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        char maxChar = str.charAt(0);
        int maxCount = 0;

        for (char ch : map.keySet()) {
            if (map.get(ch) > maxCount) {
                maxCount = map.get(ch);
                maxChar = ch;
            }
        }

        System.out.println(maxChar + " = " + maxCount);
    }
}
```

Output: `r = 2`

## 11. Find the element with the maximum count in an array

```
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 10, 30, 10, 20};

        Map<Integer, Integer> map = new LinkedHashMap<>();

        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxNum = arr[0];
        int maxCount = 0;

        for (int num : map.keySet()) {
            if (map.get(num) > maxCount) {
                maxCount = map.get(num);
                maxNum = num;
            }
        }

        System.out.println(maxNum + " occurs " + maxCount + " times");
    }
}
```

Output: `10 occurs 3 times`

## 12. Find the maximum number in an array

```
public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 50, 20, 90, 30};

        int max = arr[0];

        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }

        System.out.println("Maximum = " + max);
    }
}
```

Output: `Maximum = 90`

## 13. Check whether a number is prime

A prime number has exactly two positive factors: 1 and itself.

```
public class Main {
    public static void main(String[] args) {
        int num = 17;
        boolean prime = num > 1;

        for (int i = 2; i <= num / i; i++) {
            if (num % i == 0) {
                prime = false;
                break;
            }
        }

        System.out.println(prime ? "Prime" : "Not Prime");
    }
}
```

## 14. Check whether a number is odd or even

```
public class Main {
    public static void main(String[] args) {
        int num = 10;

        if (num % 2 == 0) {
            System.out.println("Even");
        } else {
            System.out.println("Odd");
        }
    }
}
```

## 15. Print prime numbers in an array

```
public class Main {
    public static void main(String[] args) {
        int[] arr = {2, 4, 5, 8, 11, 15, 17};

        for (int num : arr) {
            boolean prime = num > 1;

            for (int i = 2; i <= num / i; i++) {
                if (num % i == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime) {
                System.out.print(num + " ");
            }
        }
    }
}
```

Output: `2 5 11 17`

## 16. Separate alphabets and numbers in a string

Example: `Test123Java456` → Alphabets: `TestJava`, Numbers: `123456`

```
public class Main {
    public static void main(String[] args) {
        String str = "Test123Java456";

        String alphabets = "";
        String numbers = "";

        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                alphabets += ch;
            } else if (Character.isDigit(ch)) {
                numbers += ch;
            }
        }

        System.out.println("Alphabets: " + alphabets);
        System.out.println("Numbers: " + numbers);
    }
}
```

## 17. Create a text file, write and read it

This program creates `sample.txt`, writes text into it, and reads the contents.

```
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        // Write to file
        FileWriter writer = new FileWriter("sample.txt");
        writer.write("Hello Java\nAutomation Testing");
        writer.close();

        // Read from file
        BufferedReader reader =
            new BufferedReader(new FileReader("sample.txt"));

        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
    }
}
```

Remember:

- `FileWriter` writes data.
- `FileReader` reads characters.
- `BufferedReader` reads text line by line.
- `close()` closes the file resource.

The file is created in the program's working directory.

## 18. Create an Excel file, write and read data

For Excel files, use the Apache POI library. Add this Maven dependency to `pom.xml`:

```
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.4.1</version>
</dependency>
```

Java program to create `data.xlsx`, write data, and read it back:

```
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Create and write Excel file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestData");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Age");

        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("Anudeep");
        row2.createCell(1).setCellValue(28);

        FileOutputStream out = new FileOutputStream("data.xlsx");
        workbook.write(out);
        out.close();
        workbook.close();

        // Read Excel file
        FileInputStream in = new FileInputStream("data.xlsx");
        Workbook readWorkbook = new XSSFWorkbook(in);
        Sheet readSheet = readWorkbook.getSheet("TestData");

        for (Row r : readSheet) {
            for (Cell c : r) {
                System.out.print(c.toString() + "\t");
            }
            System.out.println();
        }

        readWorkbook.close();
        in.close();
    }
}
```

Output:

```
Name     Age
Anudeep  28.0
```

Note: Excel numeric cells may display as `28.0` with `Cell.toString()`. For production test frameworks, use `DataFormatter` to preserve Excel's displayed cell values.

## 19. Separate numbers, alphabets and special characters

Example: `Java123@Test#45`

```
public class Main {
    public static void main(String[] args) {
        String str = "Java123@Test#45";

        String letters = "";
        String numbers = "";
        String special = "";

        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                letters += ch;
            } else if (Character.isDigit(ch)) {
                numbers += ch;
            } else {
                special += ch;
            }
        }

        System.out.println("Letters: " + letters);
        System.out.println("Numbers: " + numbers);
        System.out.println("Special: " + special);
    }
}
```

Output:

```
Letters: JavaTest
Numbers: 12345
Special: @#
```

## 20. Regex in Java

Regex means Regular Expression. It is used to search, match, validate, or replace text.

| Regex            | Meaning                                                 |
| ---------------- | ------------------------------------------------------- |
| `[a-zA-Z]`       | Alphabets                                               |
| `[0-9]` or `\\d` | Digits                                                  |
| `\\w`            | Word characters                                         |
| `\\s`            | Whitespace                                              |
| `\\d+`           | One or more digits                                      |
| `^[0-9]+$`       | Only digits                                             |
| `^[a-zA-Z]+$`    | Only alphabets                                          |
| `.*`             | Any characters, including none                          |
| `.`              | Any single character except line terminators by default |

### Example 1: Check whether a string contains only numbers

```
public class Main {
    public static void main(String[] args) {
        String str = "12345";

        System.out.println(str.matches("\\d+"));
    }
}
```

Output: `true`

### Example 2: Extract numbers from a string

```
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String str = "Java123Test456";

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
```

Output:

```
123
456
```

### Example 3: Remove special characters

```
public class Main {
    public static void main(String[] args) {
        String str = "Java@123#Test!";

        String result = str.replaceAll("[^a-zA-Z0-9]", "");

        System.out.println(result);
    }
}
```

Output: `Java123Test`

## Quick revision for interviews

| Question                  | Main concept                                      |
| ------------------------- | ------------------------------------------------- |
| Find duplicates           | `HashSet`, `Map`                                  |
| Remove duplicates         | `LinkedHashSet`                                   |
| Count characters/elements | `Map`, `getOrDefault()`                           |
| Reverse string            | `charAt()`, loop                                  |
| Reverse array             | `temp`, swapping                                  |
| Sort array                | `Arrays.sort()`                                   |
| Maximum number            | `if (num > max)`                                  |
| Prime number              | `%` and loop                                      |
| Odd/even                  | `num % 2`                                         |
| Text file                 | `FileWriter`, `BufferedReader`                    |
| Excel                     | Apache POI                                        |
| Regex                     | `matches()`, `replaceAll()`, `Pattern`, `Matcher` |

Interview tip: Practice the duplicate-character, duplicate-array, character-count, reverse-string, maximum-number, and file-handling programs first. These are useful fundamentals for Java and Selenium automation interviews.

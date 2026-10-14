## Array
```Java
// ==================================================
// JAVA ARRAYS - SHORT NOTES
// ==================================================

/*
1. ARRAY DEFINITION

An array is a data structure used to store multiple
values of the same data type in a single variable.

Features:
- Stores multiple elements of the same declared type.
- Index starts from 0.
- Has a fixed length after creation.
- Elements are accessed using indexes.
- Arrays are objects in Java.
*/

// Imports must appear before the class declaration.
import java.util.Arrays;

public class ArrayNotes {

    // Executable statements must be inside a method, constructor, or initializer block.
    public static void main(String[] args) {

        // ==================================================
        // 2. MULTIPLE WAYS TO CREATE AN ARRAY
        // ==================================================

        // Way 1: Declare and initialize
        int[] arr1 = {10, 20, 30, 40, 50};

        // Way 2: Using new keyword with values
        int[] arr2 = new int[]{10, 20, 30};

        // Way 3: Create with default values (0, 0, 0, 0, 0)
        int[] arr3 = new int[5];


        // ==================================================
        // 3. ACCESS, UPDATE AND LENGTH
        // ==================================================

        int[] a = {10, 20, 30, 40, 50};

        System.out.println(a[0]);              // 10
        System.out.println(a[2]);              // 30

        a[1] = 25;                             // Update element

        System.out.println(a.length);          // 5
        System.out.println(a[a.length - 1]);   // Last element: 50


        // ==================================================
        // 4. TRAVERSE ARRAY
        // ==================================================

        // Standard for loop
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        // Enhanced for loop
        for (int num : a) {
            System.out.println(num);
        }


        // ==================================================
        // 5. PRINT ARRAY
        // ==================================================

        System.out.println(Arrays.toString(a));


        // ==================================================
        // 6. COMMON ARRAY OPERATIONS
        // ==================================================

        int[] values = {40, 10, 30, 20};

        Arrays.sort(values);                                  // Sort
        int[] copy = Arrays.copyOf(values, values.length);
        boolean equal = Arrays.equals(values, copy);
        int index = Arrays.binarySearch(values, 30);          // Sorted array only

        System.out.println(Arrays.toString(values));
        System.out.println(equal);
        System.out.println(index);


        // ==================================================
        // 7. SUM, MAXIMUM, MINIMUM AND AVERAGE
        // ==================================================

        int[] nums = {10, 50, 20, 30};

        int sum = 0;
        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            sum += num;

            if (num > max) max = num;
            if (num < min) min = num;
        }

        System.out.println("Sum: " + sum);
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
        System.out.println("Average: " + (double) sum / nums.length);


        // ==================================================
        // 8. REVERSE ARRAY (PRINT IN REVERSE)
        // ==================================================

        int[] reverse = {10, 20, 30, 40};

        for (int i = reverse.length - 1; i >= 0; i--) {
            System.out.print(reverse[i] + " ");
        }
        System.out.println();
        // Output: 40 30 20 10


        // ==================================================
        // 9. SEARCH AN ELEMENT
        // ==================================================

        int[] searchArr = {10, 20, 30, 40};
        int target = 30;
        boolean found = false;

        for (int num : searchArr) {
            if (num == target) {
                found = true;
                break;
            }
        }

        System.out.println(found ? "Found" : "Not Found");


        // ==================================================
        // 10. FIND DUPLICATE ELEMENTS
        // ==================================================

        int[] duplicates = {10, 20, 10, 30, 20};

        for (int i = 0; i < duplicates.length; i++) {
            for (int j = i + 1; j < duplicates.length; j++) {
                if (duplicates[i] == duplicates[j]) {
                    System.out.println(duplicates[i]);
                    break;
                }
            }
        }


        // ==================================================
        // 11. REMOVE DUPLICATES
        // ==================================================

        int[] duplicateArr = {10, 20, 10, 30, 20, 40};
        int[] result = new int[duplicateArr.length];
        int count = 0;

        for (int num : duplicateArr) {
            boolean exists = false;

            // Check if num is already in result
            for (int i = 0; i < count; i++) {
                if (result[i] == num) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                result[count++] = num;
            }
        }

        int[] unique = Arrays.copyOf(result, count);
        System.out.println(Arrays.toString(unique)); // [10, 20, 30, 40]


        // ==================================================
        // 12. TWO-DIMENSIONAL ARRAY
        // ==================================================

        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };

        System.out.println(matrix[0][1]); // 2

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}


// ==================================================
// 13. IMPORTANT SYNTAX
// ==================================================

// Declaration:       int[] arr;
// Creation:          new int[5];
// Initialization:    int[] arr = {1, 2, 3};
// Access:            arr[i];
// Update:            arr[i] = value;
// Length:            arr.length;
// Sort:              Arrays.sort(arr);
// Print:             Arrays.toString(arr);
// Copy:              Arrays.copyOf(arr, n);
// Compare:           Arrays.equals(a, b);
// Binary search:     Arrays.binarySearch(arr, key);


// ==================================================
// 14. TIME COMPLEXITY
// ==================================================

// Access / Update:               O(1)
// Linear search:                 O(n)
// Traversal:                     O(n)
// Sorting:                       Typically O(n log n)
// Nested-loop duplicate search:  O(n^2)


// ==================================================
// 15. IMPORTANT POINTS
// ==================================================

// - Index starts from 0.
// - Array length is fixed after creation.
// - Use arr.length, not arr.length().
// - Invalid indexes throw ArrayIndexOutOfBoundsException.
// - Arrays store elements of the same declared type.
// - Java arrays do not directly support push() or pop().
// - Use Arrays utility methods for common operations.
// - Imports must appear before the class declaration.
// - Executable statements must be inside a method,
//   constructor, or initializer block.
```

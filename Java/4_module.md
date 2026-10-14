```java
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ArrayRevision {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 1. Create an Array
        int[] numbers = {10, 20, 30, 40, 50};
        System.out.println("Original: " + Arrays.toString(numbers));

        // 2. Create an Array with Fixed Size
        int[] arr = new int[5];
        System.out.println("Default values: " + Arrays.toString(arr));

        // 3. Access an Element
        System.out.println("First element: " + numbers[0]);

        // 4. Update an Element
        numbers[1] = 99;
        System.out.println("Updated: " + Arrays.toString(numbers));

        // 5. Get Array Length
        System.out.println("Length: " + numbers.length);

        // 6. Iterate Using for Loop
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();

        // 7. Iterate Using Enhanced for Loop
        for (int value : numbers) {
            System.out.print(value + " ");
        }
        System.out.println();

        // 8. Add Element at the End
        int[] extended = Arrays.copyOf(numbers, numbers.length + 1);
        extended[extended.length - 1] = 60;
        System.out.println("Added: " + Arrays.toString(extended));

        // 9. Add Element at the Beginning
        int[] beginning = new int[numbers.length + 1];
        beginning[0] = 5;
        System.arraycopy(numbers, 0, beginning, 1, numbers.length);
        System.out.println("Beginning: " + Arrays.toString(beginning));

        // 10. Remove Element from the End
        int[] shorter = Arrays.copyOf(numbers, numbers.length - 1);
        System.out.println("Removed last: " + Arrays.toString(shorter));

        // 11. Remove Element at a Specific Index
        int removeIndex = 1;
        int[] removed = new int[numbers.length - 1];

        for (int i = 0, j = 0; i < numbers.length; i++) {
            if (i != removeIndex) {
                removed[j++] = numbers[i];
            }
        }
        System.out.println("Removed index 1: " + Arrays.toString(removed));

        // 12. Find Index of an Element
        int target = 30;
        int index = -1;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                index = i;
                break;
            }
        }
        System.out.println("Index of 30: " + index);

        // 13. Check if an Element Exists
        boolean found = false;

        for (int value : numbers) {
            if (value == 30) {
                found = true;
                break;
            }
        }
        System.out.println("Contains 30: " + found);

        // 14. Merge Two Arrays
        int[] first = {1, 2, 3};
        int[] second = {4, 5, 6};
        int[] merged = new int[first.length + second.length];

        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);

        System.out.println("Merged: " + Arrays.toString(merged));

        // 15. Copy an Array
        int[] copy = Arrays.copyOf(first, first.length);
        System.out.println("Copy: " + Arrays.toString(copy));

        // 16. Copy a Portion of an Array
        int[] portion = Arrays.copyOfRange(first, 1, 3);
        System.out.println("Portion: " + Arrays.toString(portion));

        // 17. Sort in Ascending Order
        int[] nums = {4, 2, 9, 1};
        Arrays.sort(nums);
        System.out.println("Ascending: " + Arrays.toString(nums));

        // 18. Sort in Descending Order
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        System.out.println("Descending: " + Arrays.toString(nums));

        // 19. Reverse an Array
        int[] reverse = {10, 20, 30, 40};

        for (int i = 0, j = reverse.length - 1; i < j; i++, j--) {
            int temp = reverse[i];
            reverse[i] = reverse[j];
            reverse[j] = temp;
        }
        System.out.println("Reversed: " + Arrays.toString(reverse));

        // 20. Shuffle an Array
        Random random = new Random();

        for (int i = nums.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        System.out.println("Shuffled: " + Arrays.toString(nums));

        // 21. Find Sum
        int[] values = {1, 2, 3, 4};
        int sum = 0;

        for (int value : values) {
            sum += value;
        }
        System.out.println("Sum: " + sum);

        // 22. Find Average
        double average = (double) sum / values.length;
        System.out.println("Average: " + average);

        // 23. Find Maximum and Minimum
        int max = values[0];
        int min = values[0];

        for (int value : values) {
            if (value > max) max = value;
            if (value < min) min = value;
        }
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);

        // 24. Convert Array to String
        System.out.println("Array as String: " + Arrays.toString(values));

        // 25. Convert String to Array
        String text = "10,20,30,40";
        String[] parts = text.split(",");
        int[] converted = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            converted[i] = Integer.parseInt(parts[i].trim());
        }
        System.out.println("String to array: " + Arrays.toString(converted));

        // 26. Compare Two Arrays
        int[] x = {1, 2, 3};
        int[] y = {1, 2, 3};
        System.out.println("Equal: " + Arrays.equals(x, y));

        // 27. Fill an Array
        int[] filled = new int[5];
        Arrays.fill(filled, 7);
        System.out.println("Filled: " + Arrays.toString(filled));

        // 28. Binary Search (array must be sorted)
        int[] sorted = {10, 20, 30, 40, 50};
        System.out.println("Binary search: " +
                Arrays.binarySearch(sorted, 30));

        // 29. Count Even and Odd Numbers
        int even = 0, odd = 0;

        for (int value : values) {
            if (value % 2 == 0) even++;
            else odd++;
        }
        System.out.println("Even: " + even);
        System.out.println("Odd: " + odd);

        // 30. Find Duplicate Elements
        int[] duplicateArray = {10, 20, 10, 30, 20};
        System.out.print("Duplicates: ");

        for (int i = 0; i < duplicateArray.length; i++) {
            boolean alreadyPrinted = false;

            for (int k = 0; k < i; k++) {
                if (duplicateArray[i] == duplicateArray[k]) {
                    alreadyPrinted = true;
                    break;
                }
            }

            if (alreadyPrinted) continue;

            for (int j = i + 1; j < duplicateArray.length; j++) {
                if (duplicateArray[i] == duplicateArray[j]) {
                    System.out.print(duplicateArray[i] + " ");
                    break;
                }
            }
        }
        System.out.println();

        // 31. Remove Duplicate Elements
        int[] unique = new int[duplicateArray.length];
        int count = 0;

        for (int i = 0; i < duplicateArray.length; i++) {
            boolean exists = false;

            for (int j = 0; j < count; j++) {
                if (duplicateArray[i] == unique[j]) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                unique[count++] = duplicateArray[i];
            }
        }

        unique = Arrays.copyOf(unique, count);
        System.out.println("Without duplicates: " + Arrays.toString(unique));

        // 32. Find Second Largest Distinct Element
        int[] data = {10, 40, 20, 40, 30};
        Arrays.sort(data);

        int largest = data[data.length - 1];
        boolean hasSecond = false;

        for (int i = data.length - 2; i >= 0; i--) {
            if (data[i] != largest) {
                System.out.println("Second largest: " + data[i]);
                hasSecond = true;
                break;
            }
        }

        if (!hasSecond) {
            System.out.println("No second-largest distinct element");
        }

        // 33. Take Array Input from User
        System.out.print("Enter array size: ");
        int size = sc.nextInt();

        if (size < 0) {
            System.out.println("Size cannot be negative.");
        } else {
            int[] inputArray = new int[size];

            System.out.println("Enter " + size + " elements:");
            for (int i = 0; i < size; i++) {
                inputArray[i] = sc.nextInt();
            }

            System.out.println("Input array: " +
                    Arrays.toString(inputArray));
        }

        sc.close();
    }
}
```

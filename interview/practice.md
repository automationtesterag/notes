```Java
// Online Java Compiler (Editor)
// Write and run Java online using this editor.
import java.util.*;
class Main {
    public static void main(String[] args) {
        // creating an array
        int[] arr1 = {1,2,3,4,5,5,6,6,8,9,7};
        int[] arr2 = new int[5]; // has default values
        // access array
        System.out.println("access element: "+ arr1[3]);
        System.out.println("access element: "+ arr2[3]);
        // updating element
        arr1[0]=0;
        System.out.println("update element: " + arr1[0]);
        //length
        System.out.println("length: "+arr1.length);
        // toString
        System.out.println("Array to string "+ Arrays.toString(arr1));
        //for loop
        for(int i=0; i<arr1.length;i++) {
            System.out.println("Array element at index "+ i +"is " +arr1[i]);
        }
        // for each
        for(int i: arr1) {
            System.out.println("for each loop element "+ i);
        }
        //sort
        Arrays.sort(arr1);
        System.out.println("Array to string "+ Arrays.toString(arr1));

        // compare
        System.out.println(Arrays.equals(arr1, arr2));

        // copy
        int[] a3 = Arrays.copyOf(arr1, arr1.length+1);
        System.out.println("Array to string "+ Arrays.toString(a3));
        
    }
}
output
-----------
access element: 4
access element: 0
update element: 0
length: 11
Array to string [0, 2, 3, 4, 5, 5, 6, 6, 8, 9, 7]
Array element at index 0is 0
Array element at index 1is 2
Array element at index 2is 3
Array element at index 3is 4
Array element at index 4is 5
Array element at index 5is 5
Array element at index 6is 6
Array element at index 7is 6
Array element at index 8is 8
Array element at index 9is 9
Array element at index 10is 7
for each loop element 0
for each loop element 2
for each loop element 3
for each loop element 4
for each loop element 5
for each loop element 5
for each loop element 6
for each loop element 6
for each loop element 8
for each loop element 9
for each loop element 7
Array to string [0, 2, 3, 4, 5, 5, 6, 6, 7, 8, 9]
false
Array to string [0, 2, 3, 4, 5, 5, 6, 6, 7, 8, 9, 0]

```

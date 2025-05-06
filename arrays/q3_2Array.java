import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class q3_2Array {
    private static final int N = 26; // Base for letters (a-z)
    private static int opCount = 0; // Counter for Primitive Operations

    public static void main(String[] args) {
        StringBuilder csvData = new StringBuilder();
        csvData.append("n,maxLength,opCount\n");
        
        for (int n = 10; n <= 100; n += 20) {
            for (int maxLength = 1; maxLength <= 5; maxLength++) {
                String[] arr = generateRandomStringArray(n, maxLength);
                opCount = 0;

                // Display the initial array before sorting
                displayArray("Initial Array", arr);
    
                // Perform radix sort
                radixSort(arr);

                // Display the sorted array
                displayArray("Sorted Array", arr);
    
                csvData.append(n).append(",").append(maxLength).append(",").append(opCount).append("\n");
            }
        }    
    
        writeToCSV("radix_string_ops.csv", csvData.toString());
    }

    private static String[] generateRandomStringArray(int size, int maxLen) {
        Random rand = new Random();
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            int len = rand.nextInt(maxLen) + 1; // length 1 to maxLen
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                char ch = (char) ('a' + rand.nextInt(26));
                sb.append(ch);
            }
            arr[i] = sb.toString();
        }
        return arr;
    }

    // Function to perform radix sort
    // This function sorts the array using radix sort algorithm
    // It takes an array of strings as input and sorts it in ascending order
    // It assumes that the strings contain only lowercase letters (a-z)
    private static void radixSort(String[] initArr) {
        // Find max length
        int maxLength = 0;
        opCount++; // <counter> 1 Assignment

        for (int i = 0; i < initArr.length; i++) {
            opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
            
            String word = initArr[i];
            opCount += 2; // <counter> 1 Assignment, 1 Array Access

            opCount += 2; // <counter> 1 Comparison, 1 Method Call (length())
            if (word.length() > maxLength){
                maxLength = word.length();
                opCount += 2; // <counter> 1 Assignment and 1 Method Call (length())
            }
        }

        String[][] array1 = new String[N][initArr.length]; // For even iterations
        opCount += 1 + N * initArr.length; // <counter> 1 Assignment for 2D array initializations and N row-allocs for each array

        String[][] array2 = new String[N][initArr.length]; // For odd iterations
        opCount += 1 + N * initArr.length; // <counter> 1 Assignment for 2D array initializations and N row-allocs for each array

        int[] count1 = new int[N];
        opCount += 1 + N; // <counter> 1 Assignment and N initialization for 1D memory allocation for array
        
        int[] count2 = new int[N];
        opCount += 1 + N; // <counter> 1 Assignment and N initialization for 1D memory allocation for array


        // Initialize the count arrays to start extracting from the right most character
        for (int pos = maxLength - 1; pos >= 0; pos--) {
            opCount += 4; // <counter> 1 Assignment, 1 Comparison, 1 Decrement, 1 Operation (-)
            
            // System.out.println("Position: " + pos);

            opCount += 2; // <counter> 1 Operation (pos % 2), 1 Comparison
            if (pos % 2 == 0) { // Even iteration (0, 2, 4, ...)
                // Reset count1 for even iterations
                for (int i = 0; i < N; i++){
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment

                    count1[i] = 0;
                    opCount += 2; // <counter> 1 Assignment and 1 Array Access
                }

                // Copy the sorted words back to initArr from array2 for even iterations
                for (int i = 0; i < initArr.length; i++) {
                    opCount += 4; // <counter> 1 Assignment, 1 Comparison, 1 Method Call (length()), 1 Increment

                    String word = initArr[i];
                    opCount += 2; // <counter> 1 Assignment, 1 Array Access

                    int index;
                    // Extract the character at the current position
                    opCount += 2; // <counter> 1 Method Call (length()), 1 Comparison
                    if (pos < word.length()) {
                        index = word.charAt(pos) - 'a';
                        opCount += 4; // <counter> 1 Operation (-), 1 Assignment, 1 Index Access, 1 Method Call (charAt())
                    } else {
                        index = 0;
                        opCount += 1; // <counter> 1 Assignment
                    }

                    array1[index][count1[index]++] = word;
                    opCount += 5; // <counter> 3 Array Accesses, 1 Assignment, 1 Increment

                }

                // Display the 2D array after sorting
                // display2DArray("Array 1", array1, count1);

                // Copy the sorted words back to initArr from array1
                int index = 0;
                opCount ++ ; // <counter> 1 assignment

                for (int i = 0; i < N; i++) {
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    for (int j = 0; j < count1[i]; j++) {
                        opCount += 4; // <counter> 1 Assignment, 1 Array Access, 1 Comparison, 1 Increment

                        initArr[index++] = array1[i][j];
                        opCount += 5; // <counter> 1 Assignment, 3 Array Accesses, 1 Increment
                    }
                }
            } else { // Odd iteration
                // Reset count2 for odd iterations (1, 3, 5, ...)
                for (int i = 0; i < N; i++){
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    
                    count2[i] = 0;
                    opCount += 2; // <counter> 1 Assignment and 1 Array Access
                }

                // Copy the sorted words back to initArr from array1 for odd iterations
                for (int i = 0; i < initArr.length; i++) {
                    opCount += 4; // <counter> 1 Assignment, 1 Comparison, 1 Method Call (length()), 1 Increment
                    
                    String word = initArr[i];
                    opCount += 2; // <counter> 1 Assignment, 1 Array Access

                    int index;
                    // Extract the character at the current position
                    opCount += 2; // <counter> 1 Method Call (length()), 1 Comparison
                    if (pos < word.length()) {
                        index = word.charAt(pos) - 'a';
                        opCount += 4; // <counter> 1 Operation (-), 1 Assignment, 1 Index Access, 1 Method Call (charAt())
                    } else {
                        index = 0;
                        opCount += 1; // <counter> 1 Assignment
                    }
                    
                    array2[index][count2[index]++] = word;
                    opCount += 5; // <counter> 3 Array Accesses, 1 Assignment, 1 Increment
                }

                // Display the 2D array after sorting
                // display2DArray("Array 2", array2, count2);

                // Copy the sorted words back to initArr from array2
                int index = 0;
                opCount ++ ; // <counter> 1 Increment

                for (int i = 0; i < N; i++) {
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    for (int j = 0; j < count2[i]; j++) {
                        opCount += 4; // <counter> 1 Assignment, 1 Array Access, 1 Comparison, 1 Increment
                        
                        initArr[index++] = array2[i][j];
                        opCount += 5; // <counter> 1 Assignment, 3 Array Accesses, 1 Increment
                    }
                }
            }
        }
    }

    // Function to display a 2D array with a message
    // This function is used to display the 2D array after each iteration
    // It takes a message, the 2D array, and the count of elements in each sub-array as input
    private static void display2DArray(String message, String[][] array, int[] count) {
        System.out.print(message + ": [");
        for (int i = 0; i < N; i++) {
            System.out.print("[");
            for (int j = 0; j < count[i]; j++) {
                System.out.print(array[i][j]);
                if (j < count[i] - 1)
                    System.out.print(", ");
            }
            System.out.print("]");
            if (i < N - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    // Function to display an array with a message
    // This function is used to display the array before and after sorting
    // It takes a message and the array as input to display the contents
    private static void displayArray(String message, String[] arr) {
        System.out.print(message + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    private static void writeToCSV(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Function to get user input for the array
    private static String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the number of word(s): ");
            int n = scanner.nextInt();
            
            String[] arr = new String[n];
            System.out.println("Enter " + n + " word(s) (separated by spaces):");
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.next();
            }
            
            return arr;
        } finally {
            scanner.close();
        }
    }
}

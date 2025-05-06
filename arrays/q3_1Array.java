import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class q3_1Array {
    private static final int N = 10; // Base for decimal numbers (0-9)
    private static int opCount = 0; // Counter for Primitive Operations

    /**
     * The main method runs multiple tests of the radix sort algorithm
     * on randomly generated arrays of varying sizes and digit lengths.
     * 
     * It prints out a CSV-formatted summary of:
     * - n: number of elements in the array
     * - k: number of digits in the maximum possible value
     * - opCount: total primitive operations counted during the sort
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        StringBuilder csvData = new StringBuilder();
        csvData.append("n,k,opCount\n");
        // System.out.println("n,k,opCount");
    
        for (int n = 10; n <= 100; n += 20) {
            for (int digits = 1; digits <= 5; digits++) {
                int max = (int) Math.pow(10, digits) - 1;
                int[] arr = generateRandomArray(n, max);
                opCount = 0;

                // Display the initial array before sorting
                displayArray("Initial array (n=" + n + ", digits=" + digits + ")", arr);

                // Perform radix sort
                radixSort(arr);
                
                // Display the array after sorting
                displayArray("Sorted array (n=" + n + ", digits=" + digits + ")", arr);

                csvData.append(n).append(",").append(digits).append(",").append(opCount).append("\n");
                // System.out.println(n + "," + digits + "," + opCount);
            }
        }
        writeToCSV("cpt212-asgn1/arrays/output/radix_digit_ops.csv", csvData.toString());
    }
    
    /**
     * Generates an array of random integers between 0 and maxValue.
     * 
     * @param size The number of elements in the array.
     * @param maxValue The maximum value (inclusive) for generated integers.
     * @return An array of random integers between 0 and maxValue.
     */
    private static int[] generateRandomArray(int size, int maxValue) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(maxValue + 1); // ensures digits controlled by maxValue
        }
        return arr;
    }

    /**
     * Performs radix sort on the given array of non-negative integers.
     *
     * This method sorts the input array in ascending order using the radix sort algorithm.
     * It alternates between two temporary 2D arrays (`array1` and `array2`) for each digit place iteration.
     * A detailed operation counter (`opCount`) is used to count primitive operations for analysis purposes.
     *
     * @param initArr The array of non-negative integers to be sorted.
     */
    private static void radixSort(int[] initArr) {
        // Find max digits
        int max = initArr[0];
        opCount++; // <counter> 1 Assignment

        for (int i = 0; i < initArr.length; i++) {
            opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment

            int num = initArr[i];
            opCount += 2; // <counter> 1 Assignment and 1 array access
            
            opCount++; // <counter> 1 Comparison (num > max)
            if (num > max) {
                max = num;
                opCount++; // <counter> 1 Assignment
            }
        }
        int maxDigits = String.valueOf(max).length(); // Number of digits in the largest number
        opCount += 3; // <counter> 1 Assignment and 2 Method calls (String.valueOf and length)

        int[][] array1 = new int[N][initArr.length]; // For even iterationsz
        opCount += 1 + N * initArr.length; // <counter> 1 Assignment for 2D array initializations and N row-allocs for each array
        
        int[][] array2 = new int[N][initArr.length]; // For odd iterations
        opCount += 1 + N * initArr.length; // <counter> 1 Assignment for 2D array initializations and N row-allocs for each array
        
        int[] count1 = new int[N]; // Count of elements in each sub-array for even iterations
        opCount += 1 + N; // <counter> 1 Assignment and N initialization for 1D memory allocation for array
        
        int[] count2 = new int[N]; // Count of elements in each sub-array for odd iterations
        opCount += 1 + N; // <counter> 1 Assignment and N initialization for 1D memory allocation for array

        // Initialize the count arrays to start extracting from the right most digit
        for (int digitPlace = 0; digitPlace < maxDigits; digitPlace++) {
            opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
            
            // System.out.println("Digit place: " + digitPlace);

            opCount += 2; // <counter> 1 Operation (digitPlace % 2) and 1 Comparison
            if (digitPlace % 2 == 0) { // Even iteration (0, 2, 4, ...)
                // Reset count1 for even iterations 
                for (int i = 0; i < N; i++){
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    
                    count1[i] = 0;
                    opCount += 2; // <counter> 1 Assignment and 1 Array Access
                }

                // Copy the sorted numbers back to initArr from array2 for even iterations
                for (int i = 0; i < initArr.length; i++){
                    opCount += 4; // <counter> 1 Assignment, 1 Comparison, 1 Method Call (length()), and 1 Increment

                    int number = initArr[i];
                    opCount += 2; // <counter> 1 Assignment and 1 Array Access
                    
                    // Extract the digit at the current place value
                    int digit = (number / pow(10, digitPlace)) % N;
                    opCount += 4; // <counter> 1 Method Call (pow), 2 Operation (/ and %), 1 Assignment
                    
                    array1[digit][count1[digit]++] = number;
                    opCount += 5; // <counter> 3 Array Accesses, 1 Assignment, 1 Increment
                }

                // Display the 2D array after sorting
                // display2DArray("Array 1", array1, count1);

                // Copy the sorted numbers back to initArr from array1
                int index = 0;
                opCount ++ ; // <counter> 1 assignment
                
                for (int i = 0; i < N; i++) {
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    for (int j = 0; j < count1[i]; j++) {
                        opCount += 4; // <counter> 1 Assignment, 1 Array Access, 1 Comparison, 1 Increment
                        
                        initArr[index++] = array1[i][j];
                        opCount += 5; // <counter> 1 assignment, 3 array accesses, 1 increment
                    }
                }
            } else { // Odd iteration
                // Reset count2 for odd iterations (1, 3, 5, ...)
                for (int i = 0; i < N; i++){
                    opCount += 3; // <counter> 1 Assignment, 1 Comparison, 1 Increment
                    
                    count2[i] = 0;
                    opCount += 2; // <counter> 1 Assignment and 1 Array Access
                }

                // Copy the sorted numbers back to initArr from array1 for odd iterations
                for (int i = 0; i < initArr.length; i++){
                    opCount += 4; // <counter> 1 Assignment, 1 Comparison, 1 Method Call (length()), 1 Increment

                    int number = initArr[i];
                    opCount += 2; // <counter> 1 Assignment, 1 Array Access

                    // Extract the digit at the current place value
                    int digit = (number / pow(10, digitPlace)) % N;
                    opCount += 4; // <counter> 1 Method Call (pow), 2 Operation (/ and %), 1 Assignment
                    
                    array2[digit][count2[digit]++] = number;
                    opCount += 5; // <counter> 3 Array Accesses, 1 Assignment, 1 Increment
                }

                // Display the 2D array after sorting
                // display2DArray("Array 2", array2, count2);

                // Copy the sorted numbers back to initArr from array2
                int index = 0;
                opCount ++; // <counter> 1 Increment

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

    /**
     * Displays a 2D array based on the counts in each sub-array.
     * 
     * @param message A message to display before the array output.
     * @param array The 2D array to be displayed.
     * @param count The count of elements in each sub-array.
     */
    private static void display2DArray(String message, int[][] array, int[] count) {
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

    /**
     * Displays a 1D array along with the current primitive operation count.
     * 
     * @param message A message to display before the array output.
     * @param arr The array to be displayed.
     */
    private static void displayArray(String message, int[] arr) {
        System.out.print(message + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Primitive Operations: " + opCount); // Display the count of primitive operations
    }

    /**
     * Computes the power of a number using simple repeated multiplication.
     * 
     * @param base The base number.
     * @param exponent The exponent (non-negative integer).
     * @return The result of raising base to the power of exponent.
    */
    private static int pow(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    
    /**
     * Writes the given data to a CSV file with the specified file name.
     *
     * @param fileName The name of the file to write the data to.
     * @param data     The data to be written to the file.
     *                 It should be formatted as a CSV string.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private static void writeToCSV(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads an array of integers from user input.
     * 
     * @return An integer array entered by the user.
     */
    private static int[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the number of element(s): ");
            int n = scanner.nextInt();
            
            int[] arr = new int[n];
            System.out.println("Enter " + n + " integer(s) (separated by spaces):");
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            
            return arr;
        } finally {
            scanner.close();
        }
    }
}

import java.util.Scanner;

public class q2RadixString {
    private static final int N = 26; // Base for letters (a-z)

    public static void main(String[] args) {
        // String[] initArr = { "apple", "fig", "date", "banana", "cherry" }; // Sample array to be sorted
        String[] initArr = getUserInput(); // Get user input for the array
        displayArray("Sample list", initArr);
        radixSort(initArr);
        displayArray("Sorted list", initArr);
    }

    /**
    * Performs radix sort on the given array of lowercase strings.
    *
    * The method sorts the input strings in ascending lexicographical order
    * using character-wise least significant digit (LSD) radix sort.
    * It handles variable-length strings by treating missing character as smallest ('null padding').
    * 
    * The sorting process is instrumented to count primitive operations
    * such as assignments, comparisons, array accesses, method calls, and arithmetic.
    *
    * @param initArr The array of lowercase strings to be sorted.
    */
    private static void radixSort(String[] initArr) {
        // Find max length
        int maxLength = 0;
        for (int i = 0; i < initArr.length; i++) {
            String word = initArr[i];
            if (word.length() > maxLength)
                maxLength = word.length();
        }

        String[][] array1 = new String[N][initArr.length]; // For even iterations
        String[][] array2 = new String[N][initArr.length]; // For odd iterations
        int[] count1 = new int[N];
        int[] count2 = new int[N];

        // Initialize the count arrays to start extracting from the right most character
        for (int pos = maxLength - 1; pos >= 0; pos--) {
            System.out.println("Position: " + pos);

            if (pos % 2 == 0) { // Even iteration (0, 2, 4, ...)
                // Reset count1 for even iterations
                for (int i = 0; i < N; i++)
                    count1[i] = 0;

                // Copy the sorted words back to initArr from array2 for even iterations
                for (int i = 0; i < initArr.length; i++) {
                    String word = initArr[i];
                    
                    // Extract the character at the current position
                    int index;
                    if (pos < word.length()) {
                        index = word.charAt(pos) - 'a';
                    } else {
                        index = 0;
                    }
                    array1[index][count1[index]++] = word;
                }

                // Display the 2D array after sorting
                display2DArray("Array 1", array1, count1);

                // Copy the sorted words back to initArr from array1
                int index = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < count1[i]; j++) {
                        initArr[index++] = array1[i][j];
                    }
                }
            } else { // Odd iteration
                // Reset count2 for odd iterations (1, 3, 5, ...)
                for (int i = 0; i < N; i++)
                    count2[i] = 0;

                // Copy the sorted words back to initArr from array1 for odd iterations
                for (int i = 0; i < initArr.length; i++) {
                    String word = initArr[i];

                    // Extract the character at the current position
                    int index;
                    if (pos < word.length()) {
                        index = word.charAt(pos) - 'a';
                    } else {
                        index = 0;
                    }
                    array2[index][count2[index]++] = word;
                }

                // Display the 2D array after sorting
                display2DArray("Array 2", array2, count2);

                // Copy the sorted words back to initArr from array2
                int index = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < count2[i]; j++) {
                        initArr[index++] = array2[i][j];
                    }
                }
            }
        }
    }

    /**
     * Displays a one-dimensional array of strings with a provided message.
     *
     * @param message A descriptive message shown before the array.
     * @param arr     The string array to be displayed.
     * @param count   The count of elements in each sub-array.
     */
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

    /**
     * Displays a 1D array along with the current primitive operation count.
     * 
     * @param message A message to display before the array output.
     * @param arr The array to be displayed.
     */
    private static void displayArray(String message, String[] arr) {
        System.out.print(message + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * Reads an array of integers from user input.
     * 
     * @return An integer array entered by the user.
     */    private static String[] getUserInput() {
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

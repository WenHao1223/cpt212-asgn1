import java.util.Scanner;

public class q1Array {
    private static final int N = 10; // Base for decimal numbers (0-9)

    public static void main(String[] args) {
        // int[] initArr = { 275, 87, 426, 61, 409, 170, 677, 503 }; // Sample array to be sorted
        int[] initArr = getUserInput(); // Get user input for the array
        displayArray("Sample list", initArr);
        radixSort(initArr);
        displayArray("Sorted list", initArr);
    }

    // Function to perform radix sort
    // This function sorts the array using radix sort algorithm
    // It takes an array of integers as input and sorts it in ascending order
    // It assumes that the integers are non-negative
    private static void radixSort(int[] initArr) {
        // Find max digits
        int max = initArr[0];
        for (int i = 0; i < initArr.length; i++) {
            int num = initArr[i];
            if (num > max)
                max = num;
        }
        int maxDigits = String.valueOf(max).length(); // Number of digits in the largest number

        int[][] array1 = new int[N][initArr.length]; // For even iterations
        int[][] array2 = new int[N][initArr.length]; // For odd iterations
        int[] count1 = new int[N]; // Count of elements in each sub-array for even iterations
        int[] count2 = new int[N]; // Count of elements in each sub-array for odd iterations

        // Initialize the count arrays to start extracting from the right most digit
        for (int digitPlace = 0; digitPlace < maxDigits; digitPlace++) {
            System.out.println("Digit place: " + digitPlace);

            if (digitPlace % 2 == 0) { // Even iteration (0, 2, 4, ...)
                // Reset count1 for even iterations
                for (int i = 0; i < N; i++)
                    count1[i] = 0;

                // Copy the sorted numbers back to initArr from array2 for even iterations
                for (int number : initArr) {
                    // Extract the digit at the current place value
                    int digit = (number / pow(10, digitPlace)) % N;
                    array1[digit][count1[digit]++] = number;
                }

                // Display the 2D array after sorting
                display2DArray("Array 1", array1, count1);

                // Copy the sorted numbers back to initArr from array1
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

                // Copy the sorted numbers back to initArr from array1 for odd iterations
                for (int i = 0; i < initArr.length; i++){
                    int number = initArr[i];
                    // Extract the digit at the current place value
                    int digit = (number / pow(10, digitPlace)) % N;
                    array2[digit][count2[digit]++] = number;
                }

                // Display the 2D array after sorting
                display2DArray("Array 2", array2, count2);

                // Copy the sorted numbers back to initArr from array2
                int index = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < count2[i]; j++) {
                        initArr[index++] = array2[i][j];
                    }
                }
            }
        }
    }

    // Function to display the 2D array with a message
    // This function is used to display the array after each iteration
    // It takes a message, the 2D array, and the count of elements in each sub-array
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

    // Function to display the 1D array
    // This function is used to display the sorted array after each iteration
    // It takes a message and the array as input to display the contents
    private static void displayArray(String message, int[] arr) {
        System.out.print(message + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    // Function to calculate power of a number
    // This function is used to calculate the power of a number
    // It takes a base and an exponent and returns the result
    private static int pow(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    // Function to get user input for the array
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

public class q2Array {
    private static final int N = 26; // Base for letters (a-z)

    public static void main(String[] args) {
        String[] initArr = { "apple", "fig", "date", "banana", "cherry" }; // Sample array to be sorted
        displayArray("Sample list", initArr);
        radixSort(initArr);
        displayArray("Sorted list", initArr);
    }

    // Function to perform radix sort
    // This function sorts the array using radix sort algorithm
    // It takes an array of strings as input and sorts it in ascending order
    // It assumes that the strings contain only lowercase letters (a-z)
    private static void radixSort(String[] initArr) {
        // Find max length
        int maxLength = 0;
        for (String word : initArr) {
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
                for (String word : initArr) {
                    // Extract the character at the current position
                    int index = pos < word.length() ? word.charAt(pos) - 'a' : 0;
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
                for (String word : initArr) {
                    // Extract the character at the current position
                    int index = pos < word.length() ? word.charAt(pos) - 'a' : 0;
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
}

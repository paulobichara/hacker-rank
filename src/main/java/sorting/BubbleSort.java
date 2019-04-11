package sorting;

import java.util.Scanner;

public class BubbleSort {

    // Complete the countSwaps function below.
    private static void countSwaps(int[] a) {
        int swapsCount = 0;
        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a.length - 1; j++) {
                // Swap adjacent elements if they are in decreasing order
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    swapsCount++;
                }
            }
        }
        System.out.println("Array is sorted in " + swapsCount + " swaps.");
        System.out.println("First Element: " + a[0]);
        System.out.println("Last Element: " + a[a.length - 1]);
    }

    private static void swap(int[] array, int from, int to) {
        int oldTo = array[to];
        array[to] = array[from];
        array[from] = oldTo;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] a = new int[n];

            String[] aItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int aItem = Integer.parseInt(aItems[i]);
                a[i] = aItem;
            }

            countSwaps(a);
        }
    }

}

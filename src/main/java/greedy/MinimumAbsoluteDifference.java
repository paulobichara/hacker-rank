package greedy;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MinimumAbsoluteDifference {

    // Complete the minimumAbsoluteDifferenceNaive function below.
    private static int minimumAbsoluteDifference(int[] array) {
        Arrays.sort(array);
        int minAbsDiff = Integer.MAX_VALUE;
        for (int index = 0; index < array.length - 1; index++) {
            minAbsDiff = Math.min(minAbsDiff, Math.abs(array[index] - array[index + 1]));
        }
        return minAbsDiff;
    }


    static int minimumAbsoluteDifferenceNaive(int[] array) {
        int minAbsDiff = Integer.MAX_VALUE;
        for (int index = 0; index < array.length; index++) {
            for (int index2 = index + 1; index2 < array.length; index2++) {
                minAbsDiff = Math.min(minAbsDiff, Math.abs(array[index] - array[index2]));
            }
        }
        return minAbsDiff;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[n];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }

            writer.println(minimumAbsoluteDifference(arr));
        }
    }
}

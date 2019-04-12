package greedy;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MaxMin {

    // Complete the maxMin function below.
    private static int maxMin(int windowSize, int[] array) {
        int minUnfairness = Integer.MAX_VALUE;
        Arrays.sort(array);

        for (int start = 0; start + (windowSize - 1) < array.length; start++) {
            minUnfairness = Math.min(minUnfairness, array[start + (windowSize - 1)] - array[start]);
        }

        return minUnfairness;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int k = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {
                int arrItem = scanner.nextInt();
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
                arr[i] = arrItem;
            }

            writer.println(maxMin(k, arr));
        }
    }

}

package arrays;

import java.io.PrintWriter;
import java.util.Scanner;

public class ArrayManipulation {
    // Complete the arrayManipulation function below.
    private static long arrayManipulation(int arraySize, int[][] queries) {
        long[] result = new long[arraySize];
        long maxValue = Long.MIN_VALUE;
        for (int[] operation : queries) {
            for (int index = operation[0] - 1; index < operation[1]; index++) {
                result[index] += operation[2];
                maxValue = Math.max(maxValue, result[index]);
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] queries = new int[m][3];

            for (int i = 0; i < m; i++) {
                String[] queriesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 3; j++) {
                    int queriesItem = Integer.parseInt(queriesRowItems[j]);
                    queries[i][j] = queriesItem;
                }
            }

            writer.println(arrayManipulation(n, queries));
        }
    }
}

package dp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.stream.LongStream;

public class Candies {

    // Complete the candies function below.
    private static long candies(int n, int[] ratings) {
        long[] candies = new long[n];
        candies[0] = 1;

        for (int index = 1; index < n; index++) {
            if (ratings[index] == ratings[index - 1]) {
                candies[index] = 1;
            } else if (ratings[index] > ratings[index - 1]) {
                candies[index] = candies[index - 1] + 1;
            } else {
                if (candies[index - 1] > 1) {
                    candies[index] = 1;
                } else {
                    candies[index] = 1;
                    for (int index2 = index - 1;
                        index2 >= 0 && (candies[index2] == candies[index2 + 1] && ratings[index2] > ratings[index2 + 1]);
                        index2--) {
                        candies[index2]++;
                    }
                }
            }
        }

        return LongStream.of(candies).sum();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            int arrItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            arr[i] = arrItem;
        }

        long result = candies(n, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

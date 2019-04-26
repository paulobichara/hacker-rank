package dp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MaxArraySum {
    // Complete the maxSubsetSum function below.
    private static int maxSubsetSum(int[] array) {
        int[] maxSum = new int[array.length];

        for (int index = 0; index < array.length; index++) {
            if (index >= 2) {
                maxSum[index] = Math.max(array[index], Math.max(maxSum[index - 2], maxSum[index - 2] + array[index]));
            } else  {
                maxSum[index] = array[index];
            }

            if (index >= 1) {
                maxSum[index] = Math.max(maxSum[index - 1], maxSum[index]);
            }
        }

        return maxSum[maxSum.length - 1];
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int res = maxSubsetSum(arr);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

package arrays;

import java.io.PrintWriter;
import java.util.Scanner;

public class MinimumSwaps2 {

    // Complete the minimumSwaps function below.
    private static int minimumSwaps(int[] numbers) {
        int countSwaps = 0;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] != index + 1) {
                for (int index2 = index + 1; index2 < numbers.length; index2++) {
                    if (numbers[index2] == index + 1) {
                        int old = numbers[index];
                        numbers[index] = numbers[index2];
                        numbers[index2] = old;
                        countSwaps++;
                        break;
                    }
                }
            }
        }
        return  countSwaps;
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

            writer.println(minimumSwaps(arr));
        }

    }
}

package arrays;

import java.io.PrintWriter;
import java.util.Scanner;

public class LeftRotation {
    // Complete the rotLeft function below.
    private static int[] rotLeft(int[] array, int rotations) {
        int[] result = new int[array.length];
        int startIndex = rotations % array.length;
        int resultIndex = 0;
        for (int index = startIndex; index < array.length; index++) {
            result[resultIndex] = array[index];
            resultIndex++;
        }
        for (int index = 0; index < startIndex; index++) {
            result[resultIndex] = array[index];
            resultIndex++;
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nd = scanner.nextLine().split(" ");
            int n = Integer.parseInt(nd[0]);
            int d = Integer.parseInt(nd[1]);
            int[] a = new int[n];

            String[] aItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int aItem = Integer.parseInt(aItems[i]);
                a[i] = aItem;
            }

            int[] result = rotLeft(a, d);
            for (int i = 0; i < result.length; i++) {
                writer.print(result[i]);
                if (i != result.length - 1) {
                    writer.print(" ");
                }
            }
            writer.println();
        }
    }
}

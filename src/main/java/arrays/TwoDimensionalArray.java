package arrays;

import java.io.PrintWriter;
import java.util.Scanner;

public class TwoDimensionalArray {

    // Complete the hourglassSum function below.
    private static int hourglassSum(int[][] array) {
        int maxHourglassSum = Integer.MIN_VALUE;
        for (int line = 0; line + 2 < array.length; line++) {
            for (int column = 0; column + 2 < array[0].length; column++) {
                maxHourglassSum = Math.max(maxHourglassSum, calculateHourglass(array, line, column));
            }
        }
        return maxHourglassSum;
    }

    private static int calculateHourglass(int[][] array, int startLine, int startColumn) {
        int hourglass = 0;

        for (int column = startColumn; column <= startColumn + 2; column++ ) {
            hourglass = hourglass + array[startLine][column];
        }

        hourglass = hourglass + array[startLine + 1][startColumn + 1];

        for (int column = startColumn; column <= startColumn + 2; column++ ) {
            hourglass  = hourglass + array[startLine + 2][column];
        }
        return hourglass;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int[][] arr = new int[6][6];

            for (int i = 0; i < 6; i++) {
                String[] arrRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 6; j++) {
                    int arrItem = Integer.parseInt(arrRowItems[j]);
                    arr[i][j] = arrItem;
                }
            }

            writer.println(hourglassSum(arr));
        }
    }

}

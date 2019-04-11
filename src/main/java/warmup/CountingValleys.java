package warmup;

import java.util.Scanner;

public class CountingValleys {

    // Complete the countingValleys function below.
    private static int countingValleys(int stepsQty, String steps) {
        int height = 0;
        int valleysQty = 0;

        for (int index = 0; index < stepsQty; index++) {
            switch(steps.charAt(index)) {
                case 'U':
                    height++;
                    break;
                case 'D':
                    height--;
                    if (height == -1) {
                        valleysQty++;
                    }
                    break;
            }
        }
        return valleysQty;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            System.out.println(countingValleys(n, scanner.nextLine()));
        }
    }

}

package sorting;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MarkAndToys {

    // Complete the maximumToys function below.
    private static int maximumToys(int[] prices, int money) {
        Arrays.sort(prices);

        int toysCount = 0;
        for (int index = 0; index < prices.length && money >= prices[index]; index++) {
            money = money - prices[index];
            toysCount++;
        }

        return toysCount;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nk = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nk[0]);

            int k = Integer.parseInt(nk[1]);

            int[] prices = new int[n];

            String[] pricesItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int pricesItem = Integer.parseInt(pricesItems[i]);
                prices[i] = pricesItem;
            }

            writer.println(maximumToys(prices, k));
        }
    }

}

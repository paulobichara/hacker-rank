package warmup;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PairsOfSocks {

    // Complete the sockMerchant function below.
    private static int sockMerchant(int n, int[] colors) {
        Map<Integer, Integer> colorOccurrences = new HashMap<>();
        for (int index = 0; index < n; index++) {
            int occurrences = colorOccurrences.get(colors[index]) == null ? 0 : colorOccurrences.get(colors[index]);
            colorOccurrences.put(colors[index], occurrences + 1);
        }

        int numPairs = 0;
        for (Integer occurrences : colorOccurrences.values()) {
            numPairs = numPairs + (occurrences / 2);
        }
        return numPairs;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] ar = new int[n];

            String[] arItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int arItem = Integer.parseInt(arItems[i]);
                ar[i] = arItem;
            }

            int result = sockMerchant(n, ar);
            writer.println(result);
        }
    }
}
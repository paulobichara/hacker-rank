package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pairs {

    // Complete the pairs function below.
    static int pairs(int factor, int[] numbers) {
        Map<Integer, Integer> indexesMap = new HashMap<>();
        for (int index = 0; index < numbers.length; index++) {
            indexesMap.put(numbers[index], indexesMap.getOrDefault(numbers[index], 0) + 1);
        }
        int numPairs = 0;
        Map<Integer, Boolean> analyzedMap = new HashMap<>();
        for (int index = 0; index < numbers.length; index++) {
            if (!analyzedMap.containsKey(numbers[index])) {
                analyzedMap.put(numbers[index], true);
                int other = Math.abs(numbers[index] - factor);
                if (indexesMap.containsKey(other)) {
                    numPairs += indexesMap.get(other) * indexesMap.get(numbers[index]);
                    analyzedMap.put(other, true);
                }
            }
        }
        return numPairs;
    }

    static int pairsNaive(int factor, int[] numbers) {
        int qtyPairs = 0;
        for (int index = 0; index < numbers.length; index++) {
            for (int index2 = index + 1; index2 < numbers.length; index2++) {
                if (Math.abs(numbers[index] - numbers[index2]) == factor) {
                    qtyPairs++;
                }
            }
        }
        return qtyPairs;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int result = pairs(k, arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

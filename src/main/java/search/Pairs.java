package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pairs {

    static class State {
        boolean analyzedUpper;
        boolean analyzedLower;
    }

    // Complete the pairs function below.
    static int pairs(int difference, int[] numbers) {
        Map<Integer, Integer> occurrenceMap = new HashMap<>();
        for (int index = 0; index < numbers.length; index++) {
            occurrenceMap.put(numbers[index], occurrenceMap.getOrDefault(numbers[index], 0) + 1);
        }
        int numPairs = 0;
        Map<Integer, State> stateMap = new HashMap<>();
        for (int index = 0; index < numbers.length; index++) {
            int occurrences = occurrenceMap.get(numbers[index]);
            int other = numbers[index] > difference ? numbers[index] - difference : difference + numbers[index];

            if (!stateMap.containsKey(numbers[index]) || shouldProcess(numbers[index], other, stateMap.get(numbers[index]))) {
                updateState(numbers[index], other, stateMap);
                if (other != numbers[index]) {
                    if (occurrenceMap.containsKey(other)) {
                        numPairs += occurrenceMap.get(other) * occurrences;
                        updateState(other, numbers[index], stateMap);
                    }
                } else if (occurrences > 1) {
                    numPairs += getTotalPairs(occurrenceMap.get(numbers[index]));
                }
            }
        }
        return numPairs;
    }

    private static boolean shouldProcess(int number, int other, State state) {
        return (number >= other && !state.analyzedLower) || (number < other && !state.analyzedUpper);
    }

    private static void updateState(int number, int other, Map<Integer, State> stateMap) {
        if (!stateMap.containsKey(number)) {
            stateMap.put(number, new State());
        }

        if (number > other) {
            stateMap.get(number).analyzedLower = true;
        } else if (number < other) {
            stateMap.get(number).analyzedUpper = true;
        } else {
            State state = stateMap.get(number);
            state.analyzedLower = true;
            state.analyzedUpper = true;
        }
    }

    private static BigInteger factorial(int value) {
        BigInteger factorial = BigInteger.ONE;
        for (int factor = value; factor > 1; factor-- ) {
            factorial = factorial.multiply(BigInteger.valueOf(factor));
        }
        return factorial;
    }

    private static int getTotalPairs(int total) {
        return (factorial(total).divide(factorial(total - 2).multiply(BigInteger.valueOf(2L)))).intValue();
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

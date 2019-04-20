package hashmaps;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CountTriplets {

    // Complete the countTriplets function below.
    private static long countTriplets(List<Long> numbers, long gpFactor) {
        Map<Long, Integer> occurrences = new HashMap<>();
        numbers.forEach(number -> occurrences.put(number, occurrences.getOrDefault(number, 0) + 1));
        long numTriplets = 0;
        for (Long number : occurrences.keySet()) {
            if (gpFactor > 1 && number % gpFactor == 0) {
                long numberBefore = number / gpFactor;
                long numberAfter = number * gpFactor;
                if (occurrences.containsKey(numberBefore) && occurrences.containsKey(numberAfter)) {
                    numTriplets += occurrences.get(numberBefore) * occurrences.get(number)
                        * occurrences.get(numberAfter);
                }
            } else if (gpFactor == 1) {
                numTriplets += getTotalTriplets(occurrences.get(number));
            }
        }
        return numTriplets;
    }

    private static BigInteger factorial(int value) {
        BigInteger factorial = BigInteger.ONE;
        for (int factor = value; factor > 1; factor-- ) {
            factorial = factorial.multiply(BigInteger.valueOf(factor));
        }
        return factorial;
    }

    private static long getTotalTriplets(int total) {
        return (factorial(total).divide(factorial(total - 3).multiply(BigInteger.valueOf(6)))).longValue();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter writer = new PrintWriter(System.out)) {
            String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            long r = Long.parseLong(nr[1]);

            List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

            writer.println(countTriplets(arr, r));
        }
    }
}

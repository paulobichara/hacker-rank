package arrays;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ArrayManipulation {

    private static class Operation implements Comparable<Operation> {
        int id;
        int startIndex;
        int endIndex;
        int factor;

        Operation(int id, int[] operation) {
            this.id = id;
            this.startIndex = operation[0] - 1;
            this.endIndex = operation[1] - 1;
            this.factor = operation[2];
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Operation)) {
                return false;
            }
            return this.id == ((Operation)obj).id;
        }

        @Override
        public int compareTo(Operation other) {
            if (other == null) {
                throw new NullPointerException();
            }

            return startIndex != other.startIndex ? startIndex - other.startIndex : endIndex - other.endIndex;
        }
    }

    private static long arrayManipulation(int arraySize, int[][] queries) {
        Operation[] operations = new Operation[queries.length];
        for (int index = 0; index < queries.length; index++) {
            operations[index] = new Operation(index, queries[index]);
        }

        Arrays.sort(operations);

        long[] result = new long[arraySize];
        long maxValue = Long.MIN_VALUE;
        long currentFactor = 0;
        int currentOpIndex = 0;
        Map<Integer, Long> activeByEndIndex = new HashMap<>();

        for (int index = operations[currentOpIndex].startIndex; index <= operations[operations.length - 1].endIndex;) {
            while (currentOpIndex < operations.length && operations[currentOpIndex] != null && operations[currentOpIndex].startIndex == index) {
                Operation currentOp = operations[currentOpIndex];
                currentFactor += currentOp.factor;
                activeByEndIndex.put(currentOp.endIndex, activeByEndIndex.getOrDefault(currentOp.endIndex, 0L) + currentOp.factor);
                currentOpIndex++;
            }

            result[index] += currentFactor;
            maxValue = Math.max(maxValue, result[index]);
            currentFactor -= activeByEndIndex.getOrDefault(index, 0L);

            if (currentFactor == 0 && currentOpIndex < operations.length) {
                index = operations[currentOpIndex].startIndex;
            } else {
                index++;
            }
        }

        return maxValue;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] queries = new int[m][3];

            for (int i = 0; i < m; i++) {
                String[] queriesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 3; j++) {
                    int queriesItem = Integer.parseInt(queriesRowItems[j]);
                    queries[i][j] = queriesItem;
                }
            }

            writer.println(arrayManipulation(n, queries));
        }
    }
}

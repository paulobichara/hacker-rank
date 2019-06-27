package arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class SparseArrays {
    // Complete the matchingStrings function below.
    static int[] matchingStrings(String[] words, String[] queries) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        Stream.of(words).forEach(word -> frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1));

        int[] result = new int[queries.length];
        for (int index = 0; index < queries.length; index++) {
            result[index] = frequencyMap.getOrDefault(queries[index], 0);
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int stringsCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String[] strings = new String[stringsCount];

            for (int i = 0; i < stringsCount; i++) {
                String stringsItem = scanner.nextLine();
                strings[i] = stringsItem;
            }

            int queriesCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String[] queries = new String[queriesCount];

            for (int i = 0; i < queriesCount; i++) {
                String queriesItem = scanner.nextLine();
                queries[i] = queriesItem;
            }

            int[] res = matchingStrings(strings, queries);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < res.length; i++) {
                builder.append(res[i]);

                if (i != res.length - 1) {
                    builder.append("\n");
                }
            }
            System.out.println(builder.toString());
        }


    }
}

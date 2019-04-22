package hashmaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FrequencyQueries {

    // Complete the freqQuery function below.
    private static List<Integer> freqQuery(List<List<Integer>> queries) {
        Map<Integer, Integer> frequencyByNumbers = new HashMap<>();
        Map<Integer, Integer> numbersByFrequency = new HashMap<>();

        List<Integer> result = new ArrayList<>();
        for (List<Integer> query : queries) {
            int operation = query.get(0);
            int data = query.get(1);

            switch (operation) {
                case 1:
                    int freq = frequencyByNumbers.getOrDefault(data, 0);
                    if (freq > 0) {
                        decrementOccurrence(numbersByFrequency, freq);
                    }
                    freq++;
                    frequencyByNumbers.put(data, freq);
                    incrementOccurrence(numbersByFrequency, freq);
                    break;
                case 2:
                    freq = frequencyByNumbers.getOrDefault(data, 0);
                    if (freq > 0) {
                        decrementOccurrence(frequencyByNumbers, data);
                        decrementOccurrence(numbersByFrequency, freq);
                        freq = freq - 1;
                        incrementOccurrence(numbersByFrequency, freq);
                    }
                    break;
                case 3:
                    freq = data;
                    if (numbersByFrequency.containsKey(freq) && numbersByFrequency.get(freq) > 0) {
                        result.add(1);
                    } else {
                        result.add(0);
                    }
                    break;
            }
        }
        return result;
    }

    private static void decrementOccurrence(Map<Integer, Integer> occurrences, int key) {
        if (occurrences.containsKey(key)) {
            if (occurrences.get(key) - 1 > 0) {
                occurrences.put(key, occurrences.get(key) - 1);
            } else {
                occurrences.remove(key);
            }
        }
    }

    private static void incrementOccurrence(Map<Integer, Integer> occurrences, int key) {
        occurrences.put(key, occurrences.getOrDefault(key, 0) + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> ans = freqQuery(queries);

        bufferedWriter.write(
            ans.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"))
                + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }

}

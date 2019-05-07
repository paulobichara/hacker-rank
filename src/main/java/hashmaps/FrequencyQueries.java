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
        List<Integer> frequencyCount = new ArrayList<>();
        frequencyCount.add(0);

        List<Integer> result = new ArrayList<>();
        for (List<Integer> query : queries) {
            int operation = query.get(0);
            int data = query.get(1);

            switch (operation) {
                case 1:
                    insertElement(data, frequencyByNumbers, frequencyCount);
                    break;
                case 2:
                    deleteElement(data, frequencyByNumbers, frequencyCount);
                    break;
                case 3:
                    searchByFrequency(data, frequencyCount, result);
                    break;
            }
        }
        return result;
    }

    private static void insertElement(int data, Map<Integer, Integer> frequencyByNumbers, List<Integer> frequencyCount) {
        int freq = frequencyByNumbers.getOrDefault(data, 0);
        if (freq > 0) {
            frequencyCount.set(freq, frequencyCount.get(freq) - 1);
        }
        freq++;
        frequencyByNumbers.put(data, freq);
        if (freq < frequencyCount.size()) {
            frequencyCount.set(freq, frequencyCount.get(freq) == null ? 1 : frequencyCount.get(freq) + 1);
        } else {
            frequencyCount.add(1);
        }
    }

    private static void deleteElement(int data, Map<Integer, Integer> frequencyByNumbers, List<Integer> frequencyCount) {
        int freq = frequencyByNumbers.getOrDefault(data, 0);
        if (freq > 0) {
            decrementOccurrence(frequencyByNumbers, data);
            if (freq < frequencyCount.size()) {
                frequencyCount.set(freq, frequencyCount.get(freq) - 1);
            } else {
                frequencyCount.add(0);
            }
            freq = freq - 1;
            frequencyCount.set(freq, frequencyCount.get(freq) == null ? 1 : frequencyCount.get(freq) + 1);
        }
    }

    private static void searchByFrequency(int freq, List<Integer> frequencyCount, List<Integer> result) {
        if (freq < frequencyCount.size() && frequencyCount.get(freq) != null && frequencyCount.get(freq) > 0) {
            result.add(1);
        } else {
            result.add(0);
        }
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

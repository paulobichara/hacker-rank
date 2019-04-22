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
        Map<Integer, Integer> fakeArray = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        for (List<Integer> query : queries) {
            int operation = query.get(0);
            int data = query.get(1);
            switch (operation) {
                case 1:
                    fakeArray.put(data, fakeArray.getOrDefault(data, 0) + 1);
                    break;
                case 2:
                    if (fakeArray.containsKey(data)) {
                        if (fakeArray.get(data) - 1 > 0) {
                            fakeArray.put(data, fakeArray.get(data) - 1);
                        } else {
                            fakeArray.remove(data);
                        }
                    }
                    break;
                case 3:
                    int sizeBefore = result.size();
                    for (int occurrences : fakeArray.values()) {
                        if (occurrences == data) {
                            result.add(1);
                            break;
                        }
                    }
                    if (sizeBefore == result.size()) {
                        result.add(0);
                    }
                    break;
            }
        }
        return result;
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

package hashmaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FrequencyQueries {

    static class Occurrence {
        int number;
        int occurrences;

         Occurrence(int number) {
            this.number = number;
            occurrences = 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Occurrence)) {
                return false;
            }
            return number == ((Occurrence)obj).number;
        }
    }

    static class HashFunction {
        static final int PRIME = 1_000_000_019;
        static final int CARDINALITY = 150_000_000;

        long param1;
        long param2;

        HashFunction() {
            Random random = new Random();
            this.param1 = random.nextInt(PRIME - 1) + 1;
            this.param2 = random.nextInt(PRIME);
        }

        int calculateHash(int number) {
            return (int) ((param1 * number + param2) % PRIME) % CARDINALITY;
        }
    }

    static class HashTable {
        HashFunction hashFunction;
        List<Occurrence>[] table;

        @SuppressWarnings("unchecked")
        HashTable() {
            hashFunction = new HashFunction();
            table = new List[HashFunction.CARDINALITY];
        }

        private void add(int number) {
            int hash = hashFunction.calculateHash(number);

            if (table[hash] == null) {
                table[hash] = new ArrayList<>();
            }

            Occurrence occurrence = findOccurrence(table[hash], number);
            if (occurrence == null) {
                table[hash].add(new Occurrence(number));
            } else {
                occurrence.occurrences++;
            }
        }

        private void del(int number) {
            int hash = hashFunction.calculateHash(number);

            if (table[hash] == null) {
                return;
            }

            Occurrence occurrence = findOccurrence(table[hash], number);
            if (occurrence != null && occurrence.occurrences > 0) {
                occurrence.occurrences--;
            }
        }

        private Occurrence findOccurrence(List<Occurrence> occurrences, int number) {
            if (occurrences == null) {
                return null;
            }

            Occurrence occurrence = null;
            for (Occurrence current : occurrences) {
                if (current.number == number) {
                    occurrence = current;
                    break;
                }
            }
            return occurrence;
        }

        private int find(int number) {
            int hash = hashFunction.calculateHash(number);
            Occurrence occurrence = findOccurrence(table[hash], number);
            if (occurrence != null) {
                return occurrence.occurrences;
            }
            return 0;
        }
    }

    // Complete the freqQuery function below.
    private static List<Integer> freqQuery(List<List<Integer>> queries) {
        HashTable frequencyByNumbers = new HashTable();
        int[] frequencyCount = new int[queries.size()];

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

    private static void insertElement(int data, HashTable frequencyByNumbers, int[] frequencyCount) {
        int freq = frequencyByNumbers.find(data);
        if (freq > 0) {
            frequencyCount[freq]--;
        }

        freq++;
        frequencyCount[freq]++;

        frequencyByNumbers.add(data);
    }

    private static void deleteElement(int data, HashTable frequencyByNumbers, int[] frequencyCount) {
        int freq = frequencyByNumbers.find(data);
        if (freq > 0) {
            frequencyByNumbers.del(data);
            frequencyCount[freq]--;
            freq = freq - 1;
            frequencyCount[freq]++;
        }
    }

    private static void searchByFrequency(int freq, int[] frequencyCount, List<Integer> result) {
        if (freq >= frequencyCount.length || frequencyCount[freq] == 0) {
            result.add(0);
        } else {
            result.add(1);
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

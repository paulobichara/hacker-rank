package hashmaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
        static final int MAX_NUMBER = 1_000_000_000;
        static final int PRIME = 1_000_000_007;

        int cardinality;

        HashFunction(int cardinality) {
            this.cardinality = cardinality;
        }

        int calculateHash(int number) {
            return (number % PRIME) % cardinality;
        }
    }

    static class OccurrenceHashTable {
        HashFunction hashFunction;
        Occurrence[][] table;
        int[] frequencyCount;


        OccurrenceHashTable(int numQueries) {
            this.hashFunction = new HashFunction(100_000_000);
            table = new Occurrence[hashFunction.cardinality][];
            frequencyCount = new int[numQueries];
        }

        private void add(int number) {
            int hash = hashFunction.calculateHash(number);

            Occurrence occurrence = findOccurrence(table[hash], number);
            int freq = occurrence == null ? 0 : occurrence.occurrences;
            if (table[hash] == null) {
                table[hash] = new Occurrence[HashFunction.MAX_NUMBER / hashFunction.cardinality];
            } else if (freq > 0) {
                frequencyCount[freq]--;
            }

            freq++;
            frequencyCount[freq]++;

            if (occurrence == null) {
                occurrence = new Occurrence(number);
                table[hash][getBucketIndex(number)] = occurrence;
            } else {
                occurrence.occurrences++;
            }
        }

        private void del(int number) {
            int hash = hashFunction.calculateHash(number);
            Occurrence occurrence = findOccurrence(table[hash], number);
            if (occurrence != null && occurrence.occurrences > 0) {
                frequencyCount[occurrence.occurrences]--;
                occurrence.occurrences--;
                frequencyCount[occurrence.occurrences]++;
            }
        }

        private Occurrence findOccurrence(Occurrence[] occurrences, int number) {
            if (occurrences == null) {
                return null;
            }
            return occurrences[getBucketIndex(number)];
        }

        int frequencyCount(int frequency) {
            return frequency >= frequencyCount.length ? 0 : frequencyCount[frequency];
        }

        private int getBucketIndex(int number) {
            return (number / hashFunction.cardinality);
        }
    }

    // Complete the freqQuery function below.
    private static List<Integer> freqQuery(List<List<Integer>> queries) {
        OccurrenceHashTable frequencyByNumbers = new OccurrenceHashTable(queries.size());

        List<Integer> result = new ArrayList<>();
        for (List<Integer> query : queries) {
            int operation = query.get(0);
            int data = query.get(1);

            switch (operation) {
                case 1:
                    frequencyByNumbers.add(data);
                    break;
                case 2:
                    frequencyByNumbers.del(data);
                    break;
                case 3:
                    result.add(frequencyByNumbers.frequencyCount(data) == 0 ? 0 : 1);
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

package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class TripleSum {

    private static class Triplet {
        int first;
        int second;
        int third;

        int occurrences;

        Triplet(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
            occurrences = 1;
        }

        boolean match(int a, int b, int c) {
            return first == a && second == b && third == c;
        }
    }

    // Complete the triplets function below.
    static long triplets(int[] a, int[] b, int[] c) {
        return 0;
    }

    static long tripletsNaive(int[] a, int[] b, int[] c) {
        Map<Integer, List<Triplet>> tripletsMap = new HashMap<>();
        int countTriplets = 0;
        for (int indexA = 0; indexA < a.length; indexA++) {
            for (int indexB = 0; indexB < b.length; indexB++) {
                for (int indexC = 0; indexC < c.length; indexC++) {
                    if (a[indexA] <= b[indexB] && b[indexB] >= c[indexC]) {
                        int key = a[indexA] + b[indexB] + c[indexC];
                        if (!tripletsMap.containsKey(key)) {
                            tripletsMap.put(key, new ArrayList<>());
                        }

                        List<Triplet> triplets = tripletsMap.get(key);
                        final int valueA = a[indexA], valueB = b[indexB], valueC = c[indexC];
                        Optional<Triplet> optional = triplets.stream().filter(triplet -> triplet.match(valueA, valueB, valueC)).findAny();
                        if (optional.isPresent()) {
                            optional.get().occurrences++;
                        } else {
                            triplets.add(new Triplet(valueA, valueB, valueC));
                            countTriplets++;
                        }
                    }
                }
            }
        }
        return countTriplets;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] lenaLenbLenc = scanner.nextLine().split(" ");

        int lena = Integer.parseInt(lenaLenbLenc[0]);

        int lenb = Integer.parseInt(lenaLenbLenc[1]);

        int lenc = Integer.parseInt(lenaLenbLenc[2]);

        int[] arra = new int[lena];

        String[] arraItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lena; i++) {
            int arraItem = Integer.parseInt(arraItems[i]);
            arra[i] = arraItem;
        }

        int[] arrb = new int[lenb];

        String[] arrbItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenb; i++) {
            int arrbItem = Integer.parseInt(arrbItems[i]);
            arrb[i] = arrbItem;
        }

        int[] arrc = new int[lenc];

        String[] arrcItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenc; i++) {
            int arrcItem = Integer.parseInt(arrcItems[i]);
            arrc[i] = arrcItem;
        }

        long ans = triplets(arra, arrb, arrc);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

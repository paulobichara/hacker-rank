package miscellaneous;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FriendCircleQueries {

    static class Element {
        Element parent;
        int rank;
        int data;
        int numberOfFriends;

        Element(int data) {
            this.data = data;
            rank = 0;
            parent = this;
            numberOfFriends = 0;
        }

        Element getParent() {
            if (parent != this) {
                parent = parent.getParent();
            }
            return parent;
        }
    }

    // Complete the maxCircle function below.
    static int[] maxCircle(int[][] queries) {
        Map<Integer, Element> elementsMap = new HashMap<>();
        int[] result = new int[queries.length];
        int maxCircle = -1;

        for (int index = 0; index < queries.length; index++) {
            Element first = createElementIfNeeded(elementsMap, queries[index][0]);
            Element second = createElementIfNeeded(elementsMap, queries[index][1]);
            Element root = mergeAndGetRoot(first, second);
            maxCircle = Math.max(root.numberOfFriends + 1, maxCircle);
            result[index] = maxCircle;
        }
        return result;
    }

    private static Element createElementIfNeeded(Map<Integer, Element> elementsMap, int data) {
        Element element = elementsMap.get(data);
        if (element == null) {
            element = new Element(data);
            elementsMap.put(data, element);
        }
        return element;
    }

    private static Element mergeAndGetRoot(Element first, Element second) {
        Element firstRoot = first.getParent();
        Element secondRoot = second.getParent();

        if (firstRoot == secondRoot) {
            return firstRoot.numberOfFriends == 0 ? secondRoot : firstRoot;
        }

        if (firstRoot.rank > secondRoot.rank) {
            secondRoot.parent = firstRoot;
            firstRoot.numberOfFriends = firstRoot.numberOfFriends + secondRoot.numberOfFriends + 1;
            secondRoot.numberOfFriends = 0;
            return firstRoot;
        } else {
            firstRoot.parent = secondRoot;
            secondRoot.numberOfFriends = secondRoot.numberOfFriends + firstRoot.numberOfFriends + 1;
            firstRoot.numberOfFriends = 0;
            if (secondRoot.rank == firstRoot.rank) {
                secondRoot.rank++;
            }
            return secondRoot;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] queries = new int[q][2];

        for (int i = 0; i < q; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 2; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }

        int[] ans = maxCircle(queries);

        for (int i = 0; i < ans.length; i++) {
            bufferedWriter.write(String.valueOf(ans[i]));

            if (i != ans.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

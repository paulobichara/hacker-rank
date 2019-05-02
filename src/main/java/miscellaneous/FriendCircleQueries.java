package miscellaneous;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class FriendCircleQueries {

    // Complete the maxCircle function below.
    private static int[] maxCircle(int[][] queries) {
        Map<Integer, Set<Integer>> groupsMap = new HashMap<>();
        Stack<Integer> maxGroupStack = new Stack<>();
        int[] result = new int[queries.length];

        for (int index = 0; index < queries.length; index++) {
            int[] query = queries[index];
            Set<Integer> firstFriends = createGroupIfNeeded(groupsMap, query[0]);
            Set<Integer> secondFriends = createGroupIfNeeded(groupsMap, query[1]);

            firstFriends.addAll(secondFriends);
            secondFriends.addAll(firstFriends);
            firstFriends.add(query[1]);
            secondFriends.add(query[0]);

            if (maxGroupStack.isEmpty()) {
                maxGroupStack.push(firstFriends.size() + 1);
            } else {
                maxGroupStack.push(Math.max(maxGroupStack.peek(), firstFriends.size() + 1));
            }
            result[index] = maxGroupStack.peek();
        }

        return result;
    }

    private static Set<Integer> createGroupIfNeeded(Map<Integer, Set<Integer>> groupsMap, int number) {
        Set<Integer> friends;
        if (!groupsMap.containsKey(number)) {
            friends = new HashSet<>();
            groupsMap.put(number, friends);
        } else {
            friends = groupsMap.get(number);
        }
        return friends;
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

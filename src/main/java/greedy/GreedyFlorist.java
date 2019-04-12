package greedy;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class GreedyFlorist {

    // Complete the getMinimumCost function below.
    private static int getMinimumCost(int groupSize, int[] costs) {
        Arrays.sort(costs);
        int extra = 1;
        int totalCost = 0;
        int friendsLeft = groupSize;
        for (int index = costs.length - 1; index >= 0; index--) {
            if (friendsLeft == 0) {
                extra++;
                friendsLeft = groupSize;
            }
            totalCost = totalCost + costs[index] * extra;
            friendsLeft--;
        }
        return totalCost;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nk = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nk[0]);

            int k = Integer.parseInt(nk[1]);

            int[] c = new int[n];

            String[] cItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int cItem = Integer.parseInt(cItems[i]);
                c[i] = cItem;
            }
            writer.println(getMinimumCost(k, c));
        }
    }

}

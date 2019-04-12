package greedy;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class LuckBalance {

    private static class ContestComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] o1, int[] o2) {
            int result;
            if (o1[0] == o2[0] && o1[1] == o2[1]) {
                result = 0;
            } else if (o1[0] > o2[0] || (o1[0] == o2[0] && o1[1] > o2[1])) {
                result = 1;
            } else {
                result = -1;
            }
            return result;
        }
    }

    // Complete the luckBalance function below.
    private static int luckBalance(int k, int[][] contests) {
        ContestComparator comparator = new ContestComparator();
        Arrays.sort(contests, comparator);

        int maxLuck = 0;
        int lossesLeft = k;
        int[] contest;
        for (int index = contests.length - 1; index >= 0; index--) {
            contest = contests[index];
            if (contest[1] == 0) {
                maxLuck = maxLuck + contest[0];
            } else if (lossesLeft > 0) {
                maxLuck = maxLuck + contest[0];
                lossesLeft--;
            } else {
                maxLuck = maxLuck - contest[0];
            }
        }
        return maxLuck;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nk = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nk[0]);

            int k = Integer.parseInt(nk[1]);

            int[][] contests = new int[n][2];

            for (int i = 0; i < n; i++) {
                String[] contestsRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int contestsItem = Integer.parseInt(contestsRowItems[j]);
                    contests[i][j] = contestsItem;
                }
            }

            writer.println(luckBalance(k, contests));
        }
    }
}

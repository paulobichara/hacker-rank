package sorting;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class FraudulentActivity {

    // Complete the activityNotificationsNaive function below.
    static int activityNotifications(int[] expenditure, int trailingDays) {
        int notificationCount = 0;
        int[] previous = null;
        for (int currentDay = trailingDays; currentDay < expenditure.length; currentDay++) {
            int[] trailing;
            if (previous == null) {
                trailing = Arrays.copyOfRange(expenditure, currentDay - trailingDays, currentDay);
                Arrays.sort(trailing);
            } else {
                trailing = includeNumber(previous, expenditure[currentDay - 1], expenditure[currentDay - trailingDays - 1]);
            }

            double median = trailingDays % 2 == 0 ? (trailing[(trailingDays / 2) - 1] + trailing[(trailingDays / 2)]) / 2.0
                : trailing[trailingDays / 2];
            if (expenditure[currentDay] >= 2 * median) {
                notificationCount++;
            }

            previous = trailing;
        }
        return notificationCount;
    }

    private static int[] includeNumber(int[] sorted, int number, int expired) {
        int[] result = new int[sorted.length + 1];
        int resultIndex = 0;

        int sortedIndex;
        for (sortedIndex = 0; sortedIndex < sorted.length && sorted[sortedIndex] <= number; sortedIndex++) {
            if (sorted[sortedIndex] == expired) {
                expired = -1;
            } else {
                result[resultIndex] = sorted[sortedIndex];
                resultIndex++;
            }
        }

        result[resultIndex] = number;
        resultIndex++;

        for (; sortedIndex < sorted.length; sortedIndex++) {
            if (sorted[sortedIndex] == expired) {
                expired = -1;
            } else {
                result[resultIndex] = sorted[sortedIndex];
                resultIndex++;
            }
        }
        return result;
    }

    static int activityNotificationsNaive(int[] expenditure, int trailingDays) {
        int notificationCount = 0;
        for (int currentDay = trailingDays; currentDay < expenditure.length; currentDay++) {
            int[] trailing = Arrays.copyOfRange(expenditure, currentDay - trailingDays, currentDay);
            Arrays.sort(trailing);
            double median = trailingDays % 2 == 0 ? (trailing[(trailingDays / 2) - 1] + trailing[(trailingDays / 2)]) / 2.0
                    : trailing[trailingDays / 2];
            if (expenditure[currentDay] >= 2 * median) {
                notificationCount++;
            }
        }
        return notificationCount;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String[] nd = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nd[0]);

            int d = Integer.parseInt(nd[1]);

            int[] expenditure = new int[n];

            String[] expenditureItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int expenditureItem = Integer.parseInt(expenditureItems[i]);
                expenditure[i] = expenditureItem;
            }

            writer.println(activityNotifications(expenditure, d));
        }
    }
}

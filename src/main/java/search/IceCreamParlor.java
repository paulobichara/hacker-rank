package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IceCreamParlor {

    // Complete the whatFlavors function below.
    private static void whatFlavors(int[] cost, int money) {
        Map<Integer, List<Integer>> costMap = new HashMap<>();
        for (int index = 0; index < cost.length; index++) {
            if (!costMap.containsKey(cost[index])) {
                costMap.put(cost[index], new ArrayList<>());
            }
            costMap.get(cost[index]).add(index + 1);
        }

        for (int index = 0; index < cost.length; index++) {
            if (costMap.containsKey(cost[index]) && costMap.containsKey(money - cost[index])) {
                if (cost[index] == money - cost[index] && costMap.get(cost[index]).size() >= 2) {
                    System.out.println(costMap.get(cost[index]).get(0) + " " + costMap.get(money - cost[index]).get(1));
                    return;
                } else if (cost[index] != money - cost[index]) {
                    System.out.println(costMap.get(cost[index]).get(0) + " " + costMap.get(money - cost[index]).get(0));
                    return;
                }

            }
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int money = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] cost = new int[n];

            String[] costItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int costItem = Integer.parseInt(costItems[i]);
                cost[i] = costItem;
            }

            whatFlavors(cost, money);
        }

        scanner.close();
    }
}

package search;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MinimumTimeRequired {

    // Complete the minTime function below.
    private static long minTime(long[] machines, long goal) {
        return 0;
    }

    private static long minTimeNaive(long[] machines, long goal) {
        long produced = 0;
        long day;
        for (day = 1; produced < goal; day++) {
            for (int index = 0; index < machines.length; index++) {
                if (day == machines[index] || (day > machines[index] &&  day % machines[index] == 0)) {
                    produced++;
                }
            }
        }
        return  day - 1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        String[] nGoal = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nGoal[0]);

        long goal = Long.parseLong(nGoal[1]);

        long[] machines = new long[n];

        String[] machinesItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            long machinesItem = Long.parseLong(machinesItems[i]);
            machines[i] = machinesItem;
        }

        long ans = minTime(machines, goal);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

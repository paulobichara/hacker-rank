package warmup;

import java.io.PrintWriter;
import java.util.Scanner;

public class JumpingOnClouds {

    private static final int SAFE_CLOUD = 0;

    // Complete the jumpingOnClouds function below.
    private static int jumpingOnClouds(int[] clouds) {
        return findMinimumJumps(clouds, 0);
    }

    private static int findMinimumJumps(int[] clouds, int currentIndex) {
        if (currentIndex >= clouds.length - 1) {
            return 0;
        }

        int minJumps = Integer.MAX_VALUE;
        for (int nextIndex = currentIndex + 2; nextIndex > currentIndex; nextIndex--) {
            if (nextIndex < clouds.length && clouds[nextIndex] == SAFE_CLOUD) {
                minJumps = Math.min(minJumps, findMinimumJumps(clouds, nextIndex));
            }
        }

        return minJumps == Integer.MAX_VALUE ? minJumps : minJumps + 1;
    }

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(System.out); Scanner scanner = new Scanner(System.in)) {

            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] c = new int[n];

            String[] cItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int cItem = Integer.parseInt(cItems[i]);
                c[i] = cItem;
            }

            writer.println(jumpingOnClouds(c));
        }
    }

}

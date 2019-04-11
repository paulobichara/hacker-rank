package arrays;

import java.util.Scanner;

public class NewYearChaos {

    private static long getNumberOfInversions(int[] original) {
        return getNumberOfInversions(original, new int[original.length], 0, original.length);
    }

    private static long getNumberOfInversions(int[] array, int[] sorted, int left, int right) {
        if (right <= left + 1) {
            sorted[left] = array[left];
            return 0;
        }

        int middle = (left + right) / 2;
        long numberOfInversions = getNumberOfInversions(array, sorted, left, middle);
        numberOfInversions += getNumberOfInversions(array, sorted, middle, right);
        numberOfInversions += mergeAndCountInversions(sorted, left, middle, right);
        return numberOfInversions;
    }

    private static long mergeAndCountInversions(int[] array, int leftBound, int middle, int rightBound) {
        int totalElements = rightBound - leftBound;
        int[] original = new int[totalElements];
        System.arraycopy(array, leftBound, original, 0, rightBound - leftBound);

        int nextLeftIndex = leftBound;
        int nextRightIndex = middle;
        long numberOfInversions = 0;

        for (int i = 0; i < totalElements; i++) {
            if (nextLeftIndex < middle) {

                int nextLeft = original[nextLeftIndex - leftBound];
                int nextRight;

                if (nextRightIndex < rightBound && ((nextRight = original[nextRightIndex - leftBound]) < nextLeft)) {
                    int realIndex = i + leftBound;
                    array[realIndex] = nextRight;
                    numberOfInversions = numberOfInversions + Math.abs(realIndex - nextRightIndex);
                    nextRightIndex++;
                } else {
                    array[i + leftBound] = nextLeft;
                    nextLeftIndex++;
                }

            } else {
                array[i + leftBound] = original[nextRightIndex - leftBound];
                nextRightIndex++;
            }
        }

        return numberOfInversions;
    }

    private static final String TOO_CHAOTIC = "Too chaotic";

    private static void minimumBribes(int[] numbers) {
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] != index + 1 && numbers[index] - (index + 1) > 2) {
                System.out.println(TOO_CHAOTIC);
                return;
            }
        }

        System.out.println(getNumberOfInversions(numbers));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int testIndex = 0; testIndex < testCases; testIndex++) {
                int personQty = scanner.nextInt();
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                int[] queue = new int[personQty];

                String[] qItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int i = 0; i < personQty; i++) {
                    int qItem = Integer.parseInt(qItems[i]);
                    queue[i] = qItem;
                }

                minimumBribes(queue);
            }
        }
    }

}

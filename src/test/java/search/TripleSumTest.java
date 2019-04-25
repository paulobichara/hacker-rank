package search;

import java.util.Random;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class TripleSumTest {
    private static final Random RANDOM = new Random();

    private static final int MIN_ARRAY_SIZE = 1;
    private static final int MAX_ARRAY_SIZE = 100_000;

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100_000_000;

    private static final String MESSAGE = "Failed testing input: \n";

    @Test
    public void testSamples() {
        assertInput(new int[] { 1, 3, 5 }, new int[] { 2, 3 }, new int[] { 1, 2, 3 });
        assertInput(new int[] { 1, 4, 5 }, new int[] { 2, 3, 3 }, new int[] { 1, 2, 3 });
        assertInput(new int[] { 1, 3, 5, 7 }, new int[] { 5, 7, 9 }, new int[] { 7, 9, 11, 13 });
    }

    private void assertInput(int[] array1, int[] array2, int[] array3) {
        Assert.assertEquals(MESSAGE + inputToString(array1, array2, array3),
                TripleSum.tripletsNaive(array1, array2, array3), TripleSum.triplets(array1, array2, array3));
    }

    @Test
    public void stressTest() {
        long startTime = System.nanoTime();
        double duration = 0;

        while (duration < 60) {
            int[] array1 = getRandomArray();
            int[] array2 = getRandomArray();
            int[] array3 = getRandomArray();
            try {
                assertInput(array1, array2, array3);
            } catch (Exception e) {
                Assert.fail(MESSAGE + inputToString(array1, array2, array3));
            }

            duration = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

    private int[] getRandomArray() {
        return RANDOM.ints(RANDOM.nextInt((MAX_ARRAY_SIZE + 1 - MIN_ARRAY_SIZE)) + MIN_ARRAY_SIZE,
                MIN_NUMBER, MAX_NUMBER).toArray();
    }

    private String inputToString(int[] array1, int[] array2, int[] array3) {
        StringBuilder builder = new StringBuilder().append(array1.length).append(" ").append(array2.length).append(" ")
                .append(array3.length).append("\n");
        IntStream.of(array1).forEach(number -> builder.append(number).append(" "));
        IntStream.of(array2).forEach(number -> builder.append(number).append(" "));
        IntStream.of(array3).forEach(number -> builder.append(number).append(" "));
        return builder.append("\n").toString();
    }
}

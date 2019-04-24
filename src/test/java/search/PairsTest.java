package search;

import java.util.Random;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class PairsTest {
    private static final Random RANDOM = new Random();

    private static final int MIN_ARRAY_SIZE = 2;
    private static final int MAX_ARRAY_SIZE = 5;

    private static final int MIN_FACTOR = 0;
    private static final int MAX_FACTOR = 10;

    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 10;

    private static final String MESSAGE = "Failed testing input: \n";

    @Test
    public void testSamples() {
        assertInput(new int[] { 1, 5, 3, 4, 2 }, 2);
        assertInput(new int[] { 9, 4, 5, 9, 8 }, 8);
        assertInput(new int[] { 0, 3, 5, 7 }, 5);
        assertInput(new int[] { 8, 0, 4, 9 }, 0);
    }

    private void assertInput(int[] array, int factor) {
        Assert.assertEquals(MESSAGE + inputToString(array, factor),
                Pairs.pairsNaive(factor, array), Pairs.pairs(factor, array));
    }

    @Test
    public void stressTest() {
        long startTime = System.nanoTime();
        double duration = 0;

        while (duration < 60) {
            int[] array = RANDOM.ints(RANDOM.nextInt((MAX_ARRAY_SIZE + 1 - MIN_ARRAY_SIZE)) + MIN_ARRAY_SIZE,
                    MIN_NUMBER, MAX_NUMBER).toArray();
            int factor = RANDOM.nextInt(MAX_FACTOR + 1);

            try {
                assertInput(array, factor);
            } catch (Exception e) {
                Assert.fail(MESSAGE + inputToString(array, factor));
            }

            duration = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

    private String inputToString(int[] array, int factor) {
        StringBuilder builder = new StringBuilder().append(array.length).append(" ").append(factor).append("\n");
        IntStream.of(array).forEach(number -> builder.append(number).append(" "));
        return builder.append("\n").toString();
    }
}

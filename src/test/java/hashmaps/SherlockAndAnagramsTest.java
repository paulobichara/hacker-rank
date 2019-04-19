package hashmaps;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class SherlockAndAnagramsTest {

    private static final Random RANDOM = new Random();

    private static final int MIN_CHAR_QTY = 2;
    private static final int MAX_CHAR_QTY = 100;

    @Test
    public void stressTest() {
        long startTime = System.nanoTime();
        double duration = 0;
        StringBuilder messageBuilder;

        while (duration < 60) {
            String text = randomString();

            try {
                SherlockAndAnagrams.sherlockAndAnagrams(text);
            } catch (Exception e) {
                Assert.fail("Failed testing " + text);
            }

            duration = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

    private String randomString() {
        int size = RANDOM.nextInt(MAX_CHAR_QTY - MIN_CHAR_QTY + 1) + MIN_CHAR_QTY;
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < size; index++) {
            builder.append((char)(RANDOM.nextInt(26) + 'a'));
        }
        return builder.toString();
    }

}

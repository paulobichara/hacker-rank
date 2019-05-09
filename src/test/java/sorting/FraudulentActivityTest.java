package sorting;

import java.util.Random;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class FraudulentActivityTest {

    private static final Random RANDOM = new Random();

    private static final int MAX_DAYS = 100_000;
    private static final int MIN_DAYS = 1;

    private static final int MAX_TRAILING_DAYS = MAX_DAYS;
    private static final int MIN_TRAILING_DAYS = MIN_DAYS;

    private static final int MAX_EXPENSE = 200;
    private static final int MIN_EXPENSE = 0;

    private static final int STRESS_MAX_DAYS = 10;
    private static final int STRESS_MAX_TRAILING_DAYS = STRESS_MAX_DAYS;

    @Test
    public void stressTest() {
        long startTime = System.nanoTime();
        double duration = 0;

        while (duration < 60) {
            int[] expenditure = RANDOM.ints(RANDOM.nextInt(STRESS_MAX_DAYS) + 1, MIN_EXPENSE, MAX_EXPENSE + 1).toArray();
            int trailingDays = RANDOM.ints(1, MIN_TRAILING_DAYS, STRESS_MAX_TRAILING_DAYS + 1).findFirst().getAsInt();

            final StringBuilder messageBuilder = new StringBuilder("Testing new input:\n\n");
            messageBuilder.append(expenditure.length).append(" ").append(trailingDays).append("\n");
            IntStream.of(expenditure).forEach(value -> messageBuilder.append(value).append(" "));

            try {
                Assert.assertEquals(messageBuilder.toString(),
                    FraudulentActivity.activityNotificationsNaive(expenditure, trailingDays),
                    FraudulentActivity.activityNotifications(expenditure, trailingDays));
            } catch (Exception e) {
                Assert.fail(messageBuilder.toString());
                throw e;
            }

            duration = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

}

package graphs;

import graphs.ConnectedCellInGrid.Graph;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class ConnectedCellInGridTest {
    private static final Random RANDOM = new Random();
    private static final int MAX_SLOTS = 10;

    private static final String MESSAGE = "Failed testing input: \n";

    @Test
    public void testSamples() {
        assertInput(new int[][] {   { 1, 1, 0, 0 },
                                    { 0, 1, 1, 0 },
                                    { 0, 0, 1, 0 },
                                    { 1, 0, 0, 0 } }, 5);
    }

    @Test
    public void stressTest() {
        long startTime = System.nanoTime();
        double duration = 0;

        while (duration < 60) {
            assertInput(getRandomGrid(), null);
            duration = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
    }

    private void assertInput(int[][] grid, Integer expected) {
        try {
            Graph graph = new Graph(grid);
            if (expected == null) {
                graph.getMaxRegion();
            } else {
                Assert.assertEquals(MESSAGE + inputToString(grid), expected.intValue(), graph.getMaxRegion());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(MESSAGE + inputToString(grid));
        }
    }

    private int[][] getRandomGrid() {
        int rows = RANDOM.nextInt(MAX_SLOTS);
        int columns = RANDOM.nextInt(MAX_SLOTS);
        int[][] grid = new int[rows][columns];

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            grid[rowIndex] = RANDOM.ints(columns, 0, 2).toArray();
        }

        return grid;
    }

    private String inputToString(int[][] grid) {
        StringBuilder builder = new StringBuilder().append(grid.length).append("\n");
        if (grid.length > 0) {
            builder.append(grid[0].length).append("\n");
        }

        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < grid[0].length; columnIndex++) {
                builder.append(grid[rowIndex][columnIndex]).append(" ");
            }
            builder.append("\n");
        }
        return builder.append("\n").toString();
    }
}

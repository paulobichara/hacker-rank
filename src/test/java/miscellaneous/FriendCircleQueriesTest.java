package miscellaneous;

import org.junit.Assert;
import org.junit.Test;

public class FriendCircleQueriesTest {

    @Test
    public void testSamples() {
        int[][] queries = new int[2][2];
        queries[0][0] = 1;
        queries[0][1] = 2;
        queries[1][0] = 1;
        queries[1][1] = 3;
        Assert.assertArrayEquals(new int[] { 2, 3 }, FriendCircleQueries.maxCircle(queries));

        queries = new int[4][2];
        queries[0][0] = 1000000000;
        queries[0][1] = 23;
        queries[1][0] = 11;
        queries[1][1] = 3778;
        queries[2][0] = 7;
        queries[2][1] = 47;
        queries[3][0] = 11;
        queries[3][1] = 1000000000;
        Assert.assertArrayEquals(new int[] { 2, 2, 2, 4 }, FriendCircleQueries.maxCircle(queries));

        queries = new int[6][2];
        queries[0][0] = 1;
        queries[0][1] = 2;
        queries[1][0] = 3;
        queries[1][1] = 4;
        queries[2][0] = 1;
        queries[2][1] = 3;
        queries[3][0] = 5;
        queries[3][1] = 7;
        queries[4][0] = 5;
        queries[4][1] = 6;
        queries[5][0] = 7;
        queries[5][1] = 4;
        Assert.assertArrayEquals(new int[] { 2, 2, 4, 4, 4, 7 }, FriendCircleQueries.maxCircle(queries));
    }

}

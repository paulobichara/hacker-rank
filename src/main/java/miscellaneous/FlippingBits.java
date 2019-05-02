package miscellaneous;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class FlippingBits {

    private static final int ONE = '1';
    private static final int ZERO = '0';


    // Complete the flippingBits function below.
    private static long flippingBits(long decimal) {
        char[] binary = new char[32];
        String binaryString = Long.toBinaryString(decimal);
        int startIndex = binary.length - binaryString.length();
        Arrays.fill(binary, 0, startIndex, '1');

        for (int index = startIndex; index < binary.length; index++) {
            if (binaryString.charAt(index - startIndex) == ONE) {
                binary[index] = ZERO;
            } else {
                binary[index] = ONE;
            }
        }

        return Long.parseUnsignedLong(String.valueOf(binary), 2);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            long n = scanner.nextLong();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            long result = flippingBits(n);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}

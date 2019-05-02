package miscellaneous;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Primality {

    private static final String PRIME = "Prime";
    private static final String NOT_PRIME = "Not prime";


    // Complete the primality function below.
    private static String primality(int number) {
        if (number == 1) {
            return NOT_PRIME;
        }

        if (number <= 3) {
            return PRIME;
        }

        for (int divisor = 2; divisor <= number / 2; divisor++) {
            if (number % divisor == 0) {
                return NOT_PRIME;
            }
        }

        return PRIME;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int p = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int pItr = 0; pItr < p; pItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String result = primality(n);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}

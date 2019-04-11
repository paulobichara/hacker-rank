package warmup;

import java.io.PrintWriter;
import java.util.Scanner;

public class RepeatedString {

    // Complete the repeatedString function below.
    private static long repeatedString(String token, long textLenght) {
        long occurrences = countLetterOccurrences(token, token.length());
        occurrences = occurrences * (textLenght / token.length());
        return occurrences + countLetterOccurrences(token, (int)(textLenght % token.length()));
    }

    private static int countLetterOccurrences(String text, int bound) {
        int occurrences = 0;
        for (int index = 0; index < bound; index++) {
            if (text.charAt(index) == 'a') {
                occurrences++;
            }
        }
        return occurrences;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String s = scanner.nextLine();

            long n = scanner.nextLong();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            writer.println(repeatedString(s, n));
        }
    }

}

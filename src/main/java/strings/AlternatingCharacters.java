package strings;

import java.io.PrintWriter;
import java.util.Scanner;

public class AlternatingCharacters {

    // Complete the alternatingCharacters function below.
    private static int alternatingCharacters(String text) {
        Character last = null;
        int numDeletions = 0;
        for (int index = 0; index < text.length(); index++) {
            if (last != null && last.equals(text.charAt(index))) {
                numDeletions++;
            }
            last = text.charAt(index);
        }
        return numDeletions;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {

            int q = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int qItr = 0; qItr < q; qItr++) {
                writer.println(alternatingCharacters(scanner.nextLine()));
            }
        }
    }

}

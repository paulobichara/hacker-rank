package hashmaps;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TwoStrings {
    // Complete the twoStrings function below.
    private static String twoStrings(String first, String second) {
        Map<Integer, Character> charMap = new HashMap<>();
        for (int index = 0; index < first.length(); index++) {
            Character character = first.charAt(index);
            charMap.put(character.hashCode(), character);
        }
        for (int index = 0; index < second.length(); index++) {
            if (charMap.containsKey(Character.valueOf(second.charAt(index)).hashCode())) {
                return "YES";
            }
        }
        return "NO";
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int q = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            for (int qItr = 0; qItr < q; qItr++) {
                String s1 = scanner.nextLine();
                String s2 = scanner.nextLine();
                writer.println(twoStrings(s1, s2));
            }
        }
    }
}

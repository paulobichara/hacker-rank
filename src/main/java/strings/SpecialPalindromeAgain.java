package strings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SpecialPalindromeAgain {

    // Complete the substrCount function below.
    private static long substrCount(int charQty, String text) {
        return 0L;
    }

    private static long substrCountNaive(int charQty, String text) {
        long count = 0;
        for (int index = 0; index < charQty; index++) {
            for (int length = 1; index + length <= charQty; length++) {
                String current = text.substring(index, index + length);
                if (isSpecialPalindrome(current)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isSpecialPalindrome(String text) {
        Map<Character, Integer> charCountMap = getCharCountMap(text);
        if (charCountMap.keySet().size() == 1) {
            return true;
        } else if (charCountMap.keySet().size() == 2 && text.length() % 2 != 0) {
            Iterator<Character> iterator = charCountMap.keySet().iterator();
            char character1 = iterator.next();
            char character2 = iterator.next();

            return (charCountMap.get(character1) == 1 && text.charAt(text.length() / 2) == character1
                    || charCountMap.get(character2) == 1 && text.charAt(text.length() / 2) == character2);
        }

        return false;
    }

    private static Map<Character, Integer> getCharCountMap(String value) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);
            charCountMap.put(current, charCountMap.getOrDefault(current, 0) + 1);
        }
        return  charCountMap;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

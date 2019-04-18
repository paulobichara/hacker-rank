package hashmaps;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SherlockAndAnagrams {

    // Complete the sherlockAndAnagrams function below.
    static int sherlockAndAnagrams(String text) {
        Map<Integer, String> substringsMap = new HashMap<>();
        int countAnagrams = 0;
        for (int index = 0; index < text.length(); index++) {
            for (int length = index + 1; length <= text.length(); length++) {
                String current = text.substring(index, length);

                String anagram = new StringBuilder(current).reverse().toString();
                int anagramHash = anagram.hashCode();

                if (substringsMap.containsKey(anagramHash)) {
                    countAnagrams++;
                }

                int hash = current.hashCode();
                substringsMap.putIfAbsent(hash, current);
            }
        }
        return countAnagrams;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int q = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            for (int qItr = 0; qItr < q; qItr++) {
                String s = scanner.nextLine();
                writer.println(sherlockAndAnagrams(s));
            }
        }
    }

}

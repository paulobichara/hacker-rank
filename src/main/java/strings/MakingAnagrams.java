package strings;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MakingAnagrams {

    // Complete the makeAnagram function below.
    private static int makeAnagram(String a, String b) {
        Map<Character, Integer> charMapA = getCharCountMap(a);
        Map<Character, Integer> charMapB = getCharCountMap(b);

        Set<Character> allChars = new HashSet<>();
        for (int index = 0; index < a.length(); index++) {
            allChars.add(a.charAt(index));
        }
        for (int index = 0; index < b.length(); index++) {
            allChars.add(b.charAt(index));
        }

        int numDeletions = 0;
        for (Character character : allChars) {
            numDeletions += Math.abs(charMapA.getOrDefault(character, 0) - charMapB.getOrDefault(character, 0));
        }
        return numDeletions;
    }

    private static Map<Character, Integer> getCharCountMap(String value) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);
            charCountMap.put(current, charCountMap.getOrDefault(current, 0) + 1);
        }
        return  charCountMap;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            writer.println(makeAnagram(a, b));
        }
    }

}

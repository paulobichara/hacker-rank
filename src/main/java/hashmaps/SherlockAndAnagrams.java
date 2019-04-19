package hashmaps;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class SherlockAndAnagrams {

    private static class AnagramGroup {
        Map<Character, Integer> charCountMap;
        int occurrences;

        AnagramGroup(String value) {
            charCountMap = getCharCountMap(value);
            occurrences = 1;
        }

        boolean matches(String value) {
            Map<Character, Integer> valueMap = getCharCountMap(value);
            if (valueMap.size() != charCountMap.size()) {
                return false;
            }
            for (Character character : charCountMap.keySet()) {
                if (!charCountMap.get(character).equals(valueMap.get(character))) {
                    return false;
                }
            }
            return true;
        }

        private Map<Character, Integer> getCharCountMap(String value) {
            Map<Character, Integer> charCountMap = new HashMap<>();
            for (int index = 0; index < value.length(); index++) {
                char current = value.charAt(index);
                charCountMap.put(current, charCountMap.getOrDefault(current, 0) + 1);
            }
            return  charCountMap;
        }
    }

    private static int computeHash(String value) {
        int hash = 0;
        for (int index = 0; index < value.length(); index++) {
            hash += value.charAt(index);
        }
        return hash;
    }

    private static long factorial(int value) {
        long factorial = 1;
        for (int factor = value; factor > 1; factor-- ) {
            factorial *= factor;
        }
        return factorial;
    }

    private static int getTotalPairs(int total) {
        return (int)(factorial(total) / (factorial(total - 2) * 2));
    }

    private static int sherlockAndAnagrams(String text) {
        Map<Integer, List<AnagramGroup>> occurrencesMap = new HashMap<>();
        for (int index = 0; index < text.length(); index++) {
            for (int length = 1; index + length <= text.length(); length++) {
                String current = text.substring(index, index + length);
                int hash = computeHash(current);
                List<AnagramGroup> groups = getListForHash(hash, occurrencesMap);

                Optional<AnagramGroup> optGroup = groups.stream().filter(group -> group.matches(current)).findAny();
                if (optGroup.isPresent()) {
                    optGroup.get().occurrences++;
                } else {
                    groups.add(new AnagramGroup(current));
                }
            }
        }
        return computeTotalAnagramPairs(occurrencesMap);
    }

    private static List<AnagramGroup> getListForHash(int hash, Map<Integer, List<AnagramGroup>> occurrencesMap) {
        List<AnagramGroup> groups;
        if (!occurrencesMap.containsKey(hash)) {
            groups = new ArrayList<>();
            occurrencesMap.put(hash, groups);
        } else {
            groups = occurrencesMap.get(hash);
        }
        return  groups;
    }

    private static int computeTotalAnagramPairs(Map<Integer, List<AnagramGroup>> occurrencesMap) {
        int anagramsQty = 0;
        for (List<AnagramGroup> groups : occurrencesMap.values()) {
            for (AnagramGroup group : groups ) {
                anagramsQty += getTotalPairs(group.occurrences);
            }
        }
        return anagramsQty;
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

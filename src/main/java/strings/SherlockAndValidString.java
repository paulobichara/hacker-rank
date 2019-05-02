package strings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SherlockAndValidString {

    private static final String YES = "YES";
    private static final String NO = "NO";

    private static String isValid(String text) {
        Map<Character, Integer> charCountMap = getCharCountMap(text);
        Map<Integer, Integer> frequenciesMap = new HashMap<>();
        charCountMap.forEach((key, value) -> frequenciesMap.put(value, frequenciesMap.getOrDefault(value, 0) + 1));

        if (frequenciesMap.size() == 1) {
            return YES;
        } else if (frequenciesMap.size() == 2) {
            if (frequenciesMap.containsKey(1) && frequenciesMap.get(1) == 1) {
                return YES;
            } else {
                Iterator<Integer> iterator = frequenciesMap.keySet().iterator();
                int key1 = iterator.next();
                int key2 = iterator.next();

                if (Math.abs(key1 - key2) == 1 && (frequenciesMap.get(key1) == 1 || frequenciesMap.get(key2) == 1)) {
                    return YES;
                }
            }
        }
        return NO;
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

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}

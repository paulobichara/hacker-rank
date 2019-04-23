package strings;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SherlockAndValidString {

    private static final String YES = "YES";
    private static final String NO = "NO";

    // Complete the isValid function below.
    private static String isValid(String text) {
        Map<Character, Integer> charCountMap = getCharCountMap(text);
        int requiredFrequency = charCountMap.get(charCountMap.keySet().iterator().next());
        int currentFrequency;
        int countAnalyzed = 0;
        boolean canDelete = true;

        for (Character character : charCountMap.keySet()) {
            currentFrequency = charCountMap.get(character);
            if (requiredFrequency != currentFrequency) {
                if (canDelete && Math.abs(currentFrequency - requiredFrequency) == 1) {
                    canDelete = false;
                    if (countAnalyzed == 1 && currentFrequency < requiredFrequency) {
                        requiredFrequency = currentFrequency;
                    } else if (currentFrequency < requiredFrequency) {
                        return NO;
                    }
                } else {
                    return NO;
                }
            }
            countAnalyzed++;
        }
        return YES;
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
            writer.println(isValid(scanner.nextLine()));
        }
    }

}

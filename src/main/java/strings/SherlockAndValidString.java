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
        int requiredFrequency = charCountMap.get(text.charAt(0));
        int currentFrequency;
        int countAnalyzed = 0;
        boolean canDelete = true;

        for (Character character : charCountMap.keySet()) {
            currentFrequency = charCountMap.get(character);
            if (requiredFrequency != currentFrequency) {
                boolean isDefaultCase = requiredFrequency + 1 == currentFrequency;
                boolean isInverseCase = requiredFrequency == currentFrequency + 1 && countAnalyzed <= 1;
                if (canDelete && (isDefaultCase || isInverseCase)) {
                    canDelete = false;
                    if (isInverseCase) {
                        requiredFrequency = currentFrequency;
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

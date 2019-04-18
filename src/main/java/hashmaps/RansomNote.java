package hashmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class RansomNote {

    // Complete the checkMagazine function below.
    private static void checkMagazine(String[] magazine, String[] note) {
        Map<Integer, List<String>> magazineMap = new HashMap<>();
        Stream.of(magazine).forEach(word -> {
            int hash = word.hashCode();
            if (!magazineMap.containsKey(hash)) {
                magazineMap.put(hash, new ArrayList<>());
            }
            magazineMap.get(hash).add(word);
        });
        for (int index = 0; index < note.length; index++) {
            String word = note[index];
            int hash = word.hashCode();
            if (magazineMap.get(hash) == null || !magazineMap.get(hash).contains(word)) {
                System.out.println("No");
                return;
            } else {
                magazineMap.get(hash).remove(word);
            }
        }
        System.out.println("Yes");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] mn = scanner.nextLine().split(" ");

            int qtyWordsMag = Integer.parseInt(mn[0]);
            int qtyWordsNote = Integer.parseInt(mn[1]);

            String[] magazine = new String[qtyWordsMag];

            String[] magazineItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < qtyWordsMag; i++) {
                String magazineItem = magazineItems[i];
                magazine[i] = magazineItem;
            }

            String[] note = new String[qtyWordsNote];

            String[] noteItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < qtyWordsNote; i++) {
                String noteItem = noteItems[i];
                note[i] = noteItem;
            }

            checkMagazine(magazine, note);
        }
    }
}

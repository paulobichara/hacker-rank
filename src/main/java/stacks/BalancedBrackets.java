package stacks;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class BalancedBrackets {

    private static final String YES = "YES";
    private static final String NO = "NO";

    // Complete the isBalanced function below.
    private static String isBalanced(String text) {
        Stack<Character> openingBrackets = new Stack<>();
        for (int index = 0; index < text.length(); index++) {
            char current = text.charAt(index);
            if (current == '(' || current == '{' || current == '[') {
                openingBrackets.push(current);
            } else if (current == ')' || current == '}' || current == ']') {
                if (!openingBrackets.isEmpty() && ((current == ')' && openingBrackets.peek().equals('('))
                        || (current == '}' && openingBrackets.peek().equals('{'))
                        || (current == ']' && openingBrackets.peek().equals('[')))) {
                    openingBrackets.pop();
                } else {
                    return NO;
                }
            }
        }

        if (openingBrackets.isEmpty()) {
            return YES;
        }

        return NO;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int t = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int tItr = 0; tItr < t; tItr++) {
                writer.println(isBalanced(scanner.nextLine()));
            }
        }
    }
}

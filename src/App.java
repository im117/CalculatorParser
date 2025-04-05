import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
        expression = Files.readString(Path.of("input.txt"));
        nextSymbol();
        while (symbol != null) {
            System.out.println(symbol.name());
            nextSymbol();
        };
        System.out.println("Hello, World!");
    }

    private static String expression;
    private static int scanIndex = 0;
    private static Symbol symbol;

    private static void nextSymbol() {
        // do not try to scan and set symbol to null if end of string is reached
        if (scanIndex >= expression.length()) {
            symbol = null;
            return;
        }

        // skip any leading whitespace
        Matcher whitespaceMatcher = Pattern.compile("\\s+", Pattern.MULTILINE).matcher(expression);
        if (whitespaceMatcher.find(scanIndex) && whitespaceMatcher.start() == scanIndex) { // leading whitespace found
            // scan starting after the end of the match (index exclusive)
            scanIndex = whitespaceMatcher.end(); 
        }

        // check for plus
        if (expression.charAt(scanIndex) == '+') {
            symbol = Symbol.PLUS;
            scanIndex++;
            return;
        }

        // check for minus
        if (expression.charAt(scanIndex) == '-') {
            symbol = Symbol.MINUS;
            scanIndex++;
            return;
        }

        // check for times
        if (expression.charAt(scanIndex) == '*') {
            symbol = Symbol.TIMES;
            scanIndex++;
            return;
        }

        // check for slash
        if (expression.charAt(scanIndex) == '/') {
            symbol = Symbol.SLASH;
            scanIndex++;
            return;
        }

        // check for number
        Matcher numberMatcher = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(expression);
        if (numberMatcher.find(scanIndex) && numberMatcher.start() == scanIndex) {
            symbol = Symbol.NUMBER;
            scanIndex = numberMatcher.end();
            return;
        }

        // if nothing was matched to a symbol, but there was something found, then throw an error:
        throw new RuntimeException("Unexpected character: " + expression.charAt(scanIndex));
    }
}

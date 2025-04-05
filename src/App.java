import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new RuntimeException("an argument must be specified");
        }
        expression = args[0];
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
        // the symbol will be changes soon anyways. if no sym is found, leave at null when exiting
        symbol = null;


        // do not try to scan if end of string is reached
        if (scanIndex >= expression.length()) return;

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
        }

        // check for minus
        if (expression.charAt(scanIndex) == '-') {
            symbol = Symbol.MINUS;
            scanIndex++;
        }

        // check for times
        if (expression.charAt(scanIndex) == '*') {
            symbol = Symbol.TIMES;
            scanIndex++;
        }

        // check for slash
        if (expression.charAt(scanIndex) == '/') {
            symbol = Symbol.SLASH;
            scanIndex++;
        }

        // check for number
        Matcher numberMatcher = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(expression);
        if (numberMatcher.find(scanIndex) && numberMatcher.start() == scanIndex) {
            symbol = Symbol.NUMBER;
            scanIndex = numberMatcher.end();
        }

    }



    
}

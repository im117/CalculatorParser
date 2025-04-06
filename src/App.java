import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
        expression = Files.readString(Path.of("input.txt"));
        /*nextSymbol();
        while (symbol != null) {
            System.out.print(symbol.name());
            if (symbol == Symbol.NUMBER) {
                System.out.print(" " + scannedNumber);
            }
            System.out.println();
            nextSymbol();
        };*/
        nextSymbol();
        Expression exp = expression();
        System.out.println(exp);
    }

    private static String expression;
    private static int scanIndex = 0;
    private static int scannedNumber = -1;
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
            scannedNumber = Integer.parseInt(expression.substring(numberMatcher.start(), numberMatcher.end()));
            return;
        }

        // if nothing was matched to a symbol, but there was something found, then throw an error:
        throw new RuntimeException("Unexpected character: " + expression.charAt(scanIndex));
    }



    // code adapted from 
    private static boolean accept(Symbol s) {
        if (symbol == s) {
            nextSymbol();
            return true;
        }
        return false;
    }

    private static void expect(Symbol s) {
        if (!accept(s)) {
            throw new RuntimeException("Did not find expected token: " + s.name());
        } 
    }


    private static Expression expression() {
        Expression exp = new Expression();
        exp.terms.add(term(false)); // scan non-negative term
        while (symbol == Symbol.PLUS || symbol == Symbol.MINUS) {
            boolean negate = (symbol == Symbol.MINUS);
            nextSymbol();
            Term term = term(negate);
            exp.terms.add(term);
        }

        return exp;
    }

    private static Term term(boolean negative) {
        Term term = new Term(negative);
        expect(Symbol.NUMBER);
        term.factors.add(new Factor(false, scannedNumber));
        while (symbol == Symbol.TIMES || symbol == Symbol.SLASH) {
            boolean divide = (symbol == Symbol.SLASH);

            nextSymbol();

            // read a new numbers
            expect(Symbol.NUMBER);

            Factor factor = new Factor(divide, scannedNumber);


            term.factors.add(factor);
        }

        return term;
    }
}
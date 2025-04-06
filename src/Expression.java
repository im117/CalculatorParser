import java.util.ArrayList;
import java.util.List;

public class Expression  {
    public List<Term> terms = new ArrayList<>();
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (terms.size() > 0) {
            Term firstTerm = terms.get(0);
            if (firstTerm.negative) builder.append("-");
            builder.append(firstTerm.toStringUnsigned());
        }


        // get the remaining factors
        for (int i = 1; i < terms.size(); i++) {
            Term term = terms.get(i);
            builder.append(term.negative ? " - " : " + ");
            builder.append(term.toStringUnsigned());
        }


        return builder.toString();
    }
}

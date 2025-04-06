import java.util.ArrayList;
import java.util.List;

public class Term  {
    public boolean negative;
    public List<Factor> factors = new ArrayList<>();
    public Term(boolean negative) {
        this.negative = negative;
    }

    public String toString() {
        if (negative) return "-" + toStringUnsigned();

        return toStringUnsigned();
    }

    public String toStringUnsigned() {
        StringBuilder builder = new StringBuilder();
        if (factors.size() > 0) {
            Factor firstFactor = factors.get(0);
            if (firstFactor.divide) builder.append("1/");
            builder.append(firstFactor.number);
        }


        // get the remaining factors
        for (int i = 1; i < factors.size(); i++) {
            Factor factor = factors.get(i);
            builder.append(factor.divide ? "/" : "*");
            builder.append(factor.number);
        }


        return builder.toString();
    }
}

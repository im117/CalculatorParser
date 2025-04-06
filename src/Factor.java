public class Factor {
    public boolean divide = false;
    public int number;

    public Factor(boolean divide, int number) {
        this.divide = divide;
        this.number = number;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (divide) builder.append("1/");
        builder.append(Integer.toString(number));
        return builder.toString();
    }
}

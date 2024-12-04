package expression;

import java.util.Objects;

public class Const implements MyExpression, TripleExpression {
    private final int c;
    public Const(int c) {
        this.c = c;
    }
    @Override
    public int evaluate(int x) {
        return c;
    }
    @Override
    public String toString() {
        return Integer.toString(c);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(c);
    }
    @Override
    public boolean equals(Object expression) {
        if (expression == null ||
                getClass() != expression.getClass()) {
            return false;
        }
        return c == ((Const) expression).getC();
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return c;
    }

    public int getC() {
        return c;
    }
}

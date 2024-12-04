package expression;

import java.util.Objects;

public abstract class BinaryExpression implements MyExpression, TripleExpression {
    private final MyExpression ex1;
    private final MyExpression ex2;
    private final char op;
    protected BinaryExpression(MyExpression ex1, MyExpression ex2, char op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }
    protected abstract int result(int x, int y);
    @Override
    public int evaluate(int x) {
        int res1 = ex1.evaluate(x);
        int res2 = ex2.evaluate(x);
        return result(res1, res2);
    }
    @Override
    public int evaluate(int x, int y, int z) {
        int res1 = ex1.evaluate(x, y, z);
        int res2 = ex2.evaluate(x, y, z);
        return result(res1, res2);
    }
    public String toString() {
        return "(" + ex1 + " " + op + " " +  ex2 + ")";
    }
    @Override
    public int hashCode() {
        return Objects.hash(Objects.hashCode(ex1), Objects.hashCode(ex2), Objects.hashCode(op));
    }
    @Override
    public boolean equals(Object expression) {
        if (expression == this) {
            return true;
        }
        if (expression == null ||
                getClass() != expression.getClass()) {
            return false;
        }
        return ex1.equals(((BinaryExpression) expression).ex1) &&
                ex2.equals(((BinaryExpression) expression).ex2);
    }
}

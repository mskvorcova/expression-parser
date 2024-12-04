package expression;

import java.util.Objects;

public abstract class UnaryExpression implements MyExpression, TripleExpression {
    private final MyExpression ex;
    private final char op;
    public UnaryExpression(MyExpression ex, char op) {
        this.ex = ex;
        this.op = op;
    }
    protected abstract int result(int x);
    @Override
    public int evaluate(int x) {
        return result(ex.evaluate(x));
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return result(ex.evaluate(x, y, z));
    }
    @Override
    public String toString() {
        return op +"(" + ex.toString() + ")";
    }
}

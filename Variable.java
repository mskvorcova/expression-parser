package expression;

import java.util.Objects;

public class Variable implements MyExpression, TripleExpression {
    private final String name;
    public Variable(String name) {
        this.name = name;
    }
    @Override
    public int evaluate(int x) {
        return x;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
    @Override
    public boolean equals(Object expression) {
        if (expression == null ||
                getClass() != expression.getClass()) {
            return false;
        }
        return Objects.equals(((Variable) expression).name, name);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (Objects.equals(name, "x")) {
            return x;
        } else if (Objects.equals(name, "y")) {
            return y;
        } else if (Objects.equals(name, "z")){
            return z;
        }
        return -1;
    }
}

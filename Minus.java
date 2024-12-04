package expression;

public class Minus extends UnaryExpression {
    public Minus(MyExpression ex1) {
        super(ex1, '-');
    }
    @Override
    public int result(int x) {
        return -x;
    }
}

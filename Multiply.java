package expression;

public class Multiply extends BinaryExpression {
    public Multiply(MyExpression ex1, MyExpression ex2) {
        super(ex1, ex2, '*');
    }
    @Override
    protected int result(int x, int y) {
        return x * y;
    }
}

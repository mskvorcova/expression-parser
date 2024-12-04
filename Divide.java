package expression;

public class Divide extends BinaryExpression {
    public Divide(MyExpression ex1, MyExpression ex2) {
        super(ex1, ex2, '/');
    }
    @Override
    protected int result(int x, int y) {
        return x / y;
    }
}
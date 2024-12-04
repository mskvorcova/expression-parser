package expression;

public class And extends BinaryExpression {
    public And(MyExpression ex1, MyExpression ex2) {
        super(ex1, ex2, '&');
    }

    @Override
    protected int result(int x, int y) {
        return x & y;
    }

}

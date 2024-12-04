package expression;

public class Not extends UnaryExpression {
    public Not(MyExpression ex1) {
        super(ex1, '~');
    }
    @Override
    protected int result(int x) {
        return ~x;
    }
}

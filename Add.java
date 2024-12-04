package expression;

public class Add extends BinaryExpression {
    public Add(MyExpression ex1, MyExpression ex2) {
        super(ex1, ex2, '+');
    }
    @Override
    public int result(int x, int y) {
        return x + y;
    }
}
